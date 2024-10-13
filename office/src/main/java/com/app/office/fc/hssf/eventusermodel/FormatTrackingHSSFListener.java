package com.app.office.fc.hssf.eventusermodel;

import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.ExtendedFormatRecord;
import com.app.office.fc.hssf.record.FormatRecord;
import com.app.office.fc.hssf.record.FormulaRecord;
import com.app.office.fc.hssf.record.NumberRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.usermodel.HSSFDataFormat;
import com.app.office.fc.hssf.usermodel.HSSFDataFormatter;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FormatTrackingHSSFListener implements HSSFListener {
    private final HSSFListener _childListener;
    private final Map<Integer, FormatRecord> _customFormatRecords;
    private final NumberFormat _defaultFormat;
    private final HSSFDataFormatter _formatter;
    private final List<ExtendedFormatRecord> _xfRecords;

    public FormatTrackingHSSFListener(HSSFListener hSSFListener) {
        this(hSSFListener, Locale.getDefault());
    }

    public FormatTrackingHSSFListener(HSSFListener hSSFListener, Locale locale) {
        this._customFormatRecords = new Hashtable();
        this._xfRecords = new ArrayList();
        this._childListener = hSSFListener;
        this._formatter = new HSSFDataFormatter(locale);
        this._defaultFormat = NumberFormat.getInstance(locale);
    }

    /* access modifiers changed from: protected */
    public int getNumberOfCustomFormats() {
        return this._customFormatRecords.size();
    }

    /* access modifiers changed from: protected */
    public int getNumberOfExtendedFormats() {
        return this._xfRecords.size();
    }

    public void processRecord(Record record) {
        processRecordInternally(record);
        this._childListener.processRecord(record);
    }

    public void processRecordInternally(Record record) {
        if (record instanceof FormatRecord) {
            FormatRecord formatRecord = (FormatRecord) record;
            this._customFormatRecords.put(Integer.valueOf(formatRecord.getIndexCode()), formatRecord);
        }
        if (record instanceof ExtendedFormatRecord) {
            this._xfRecords.add((ExtendedFormatRecord) record);
        }
    }

    public String formatNumberDateCell(CellValueRecordInterface cellValueRecordInterface) {
        double d;
        if (cellValueRecordInterface instanceof NumberRecord) {
            d = ((NumberRecord) cellValueRecordInterface).getValue();
        } else if (cellValueRecordInterface instanceof FormulaRecord) {
            d = ((FormulaRecord) cellValueRecordInterface).getValue();
        } else {
            throw new IllegalArgumentException("Unsupported CellValue Record passed in " + cellValueRecordInterface);
        }
        int formatIndex = getFormatIndex(cellValueRecordInterface);
        String formatString = getFormatString(cellValueRecordInterface);
        if (formatString == null) {
            return this._defaultFormat.format(d);
        }
        return this._formatter.formatRawCellContents(d, formatIndex, formatString);
    }

    public String getFormatString(int i) {
        if (i < HSSFDataFormat.getNumberOfBuiltinBuiltinFormats()) {
            return HSSFDataFormat.getBuiltinFormat((short) i);
        }
        FormatRecord formatRecord = this._customFormatRecords.get(Integer.valueOf(i));
        if (formatRecord != null) {
            return formatRecord.getFormatString();
        }
        PrintStream printStream = System.err;
        printStream.println("Requested format at index " + i + ", but it wasn't found");
        return null;
    }

    public String getFormatString(CellValueRecordInterface cellValueRecordInterface) {
        int formatIndex = getFormatIndex(cellValueRecordInterface);
        if (formatIndex == -1) {
            return null;
        }
        return getFormatString(formatIndex);
    }

    public int getFormatIndex(CellValueRecordInterface cellValueRecordInterface) {
        ExtendedFormatRecord extendedFormatRecord = this._xfRecords.get(cellValueRecordInterface.getXFIndex());
        if (extendedFormatRecord != null) {
            return extendedFormatRecord.getFormatIndex();
        }
        PrintStream printStream = System.err;
        printStream.println("Cell " + cellValueRecordInterface.getRow() + "," + cellValueRecordInterface.getColumn() + " uses XF with index " + cellValueRecordInterface.getXFIndex() + ", but we don't have that");
        return -1;
    }
}
