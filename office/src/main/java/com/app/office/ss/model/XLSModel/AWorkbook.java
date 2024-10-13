package com.app.office.ss.model.XLSModel;

import android.os.Message;
import com.app.office.fc.hssf.OldExcelFormatException;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.hssf.model.InternalSheet;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.ExtendedFormatRecord;
import com.app.office.fc.hssf.record.FontRecord;
import com.app.office.fc.hssf.record.LabelRecord;
import com.app.office.fc.hssf.record.NameRecord;
import com.app.office.fc.hssf.record.PaletteRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordFactory;
import com.app.office.fc.hssf.usermodel.HSSFDataFormat;
import com.app.office.fc.hssf.usermodel.HSSFName;
import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import com.app.office.fc.xls.SSReader;
import com.app.office.simpletext.font.Font;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.util.ColorUtil;
import com.app.office.system.AbstractReader;
import com.app.office.system.IControl;
import com.app.office.system.ReaderHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AWorkbook extends Workbook implements com.app.office.fc.ss.usermodel.Workbook {
    public static final int AUTOMATIC_COLOR = 64;
    public static final int INITIAL_CAPACITY = 3;
    private static final String[] WORKBOOK_DIR_ENTRY_NAMES = {"Workbook", "WORKBOOK"};
    private UDFFinder _udfFinder = UDFFinder.DEFAULT;
    /* access modifiers changed from: private */
    public int currentSheet;
    /* access modifiers changed from: private */
    public SSReader iAbortListener;
    private ArrayList<HSSFName> names;
    private InternalWorkbook workbook;

    static class ShapesThread extends Thread {
        private AWorkbook book;
        private IControl control;
        private SSReader iAbortListener;
        private int sheetIndex;
        private Map<Integer, Sheet> sheets;

        public ShapesThread(AWorkbook aWorkbook, Map<Integer, Sheet> map, int i, SSReader sSReader) {
            this.book = aWorkbook;
            this.sheets = map;
            this.sheetIndex = i;
            this.iAbortListener = sSReader;
            this.control = sSReader.getControl();
        }

        public void run() {
            SSReader sSReader;
            try {
                if (this.sheetIndex >= 0 && (sSReader = this.iAbortListener) != null) {
                    sSReader.abortCurrentReading();
                    sleep(50);
                    ((ASheet) this.book.getSheet(this.sheetIndex)).processSheet(this.iAbortListener);
                    processOtherSheets();
                }
            } catch (OutOfMemoryError e) {
                this.book.dispose();
                this.iAbortListener.dispose();
                this.iAbortListener.getControl().getSysKit().getErrorKit().writerLog(e, true);
            } catch (Exception e2) {
                this.book.dispose();
                this.iAbortListener.dispose();
                this.iAbortListener.getControl().getSysKit().getErrorKit().writerLog(e2, true);
            } catch (Throwable th) {
                this.book = null;
                this.sheets = null;
                this.iAbortListener = null;
                this.control = null;
                throw th;
            }
            this.book = null;
            this.sheets = null;
            this.iAbortListener = null;
            this.control = null;
        }

        private void processOtherSheets() {
            for (Integer intValue : this.sheets.keySet()) {
                ((ASheet) this.book.getSheet(intValue.intValue())).processSheet(this.iAbortListener);
            }
            for (Integer intValue2 : this.sheets.keySet()) {
                this.book.processShapesBySheetIndex(this.control, intValue2.intValue());
            }
        }
    }

    public AWorkbook(InputStream inputStream, SSReader sSReader) throws IOException {
        super(true);
        this.iAbortListener = sSReader;
        DirectoryNode root = new POIFSFileSystem(inputStream).getRoot();
        List<Record> createRecords = RecordFactory.createRecords(root.createDocumentInputStream(getWorkbookDirEntryName(root)), sSReader);
        InternalWorkbook createWorkbook = InternalWorkbook.createWorkbook(createRecords, sSReader);
        this.workbook = createWorkbook;
        int numRecords = createWorkbook.getNumRecords();
        int sSTUniqueStringSize = this.workbook.getSSTUniqueStringSize();
        for (int i = 0; i < sSTUniqueStringSize; i++) {
            addSharedString(i, this.workbook.getSSTString(i));
        }
        convertLabelRecords(createRecords, numRecords);
        this.isUsing1904DateWindowing = this.workbook.isUsing1904DateWindowing();
        PaletteRecord customPalette = this.workbook.getCustomPalette();
        int i2 = 9;
        addColor(8, ColorUtil.rgb(0, 0, 0));
        byte[] color = customPalette.getColor(9);
        while (color != null) {
            int i3 = i2 + 1;
            addColor(i2, ColorUtil.rgb(color[0], color[1], color[2]));
            color = customPalette.getColor(i3);
            i2 = i3;
        }
        processCellStyle(this.workbook);
        RecordStream recordStream = new RecordStream(createRecords, numRecords);
        int i4 = 0;
        while (recordStream.hasNext()) {
            InternalSheet createSheet = InternalSheet.createSheet(recordStream, sSReader);
            ASheet aSheet = new ASheet(this, createSheet);
            aSheet.setSheetName(this.workbook.getSheetName(i4));
            if (createSheet.isChartSheet()) {
                aSheet.setSheetType(1);
            }
            this.sheets.put(Integer.valueOf(i4), aSheet);
            i4++;
        }
        createRecords.clear();
        this.names = new ArrayList<>(3);
        for (int i5 = 0; i5 < this.workbook.getNumNames(); i5++) {
            NameRecord nameRecord = this.workbook.getNameRecord(i5);
            this.names.add(new HSSFName(this, nameRecord, this.workbook.getNameCommentRecord(nameRecord)));
        }
        processSheet();
    }

    /* access modifiers changed from: private */
    public void processShapesBySheetIndex(IControl iControl, int i) {
        ASheet aSheet = (ASheet) this.sheets.get(Integer.valueOf(i));
        try {
            if (aSheet.getState() != 2) {
                aSheet.processSheetShapes(iControl);
                aSheet.setState(2);
            }
        } catch (Exception unused) {
            aSheet.setState(2);
        }
    }

    private void processSheet() {
        this.readerHandler = new ReaderHandler(this) {
            private AWorkbook book;

            {
                this.book = r2;
            }

            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    int unused = AWorkbook.this.currentSheet = ((Integer) message.obj).intValue();
                    if (((Sheet) AWorkbook.this.sheets.get(Integer.valueOf(AWorkbook.this.currentSheet))).getState() != 2) {
                        new ShapesThread(this.book, AWorkbook.this.sheets, AWorkbook.this.currentSheet, AWorkbook.this.iAbortListener).start();
                    }
                } else if (i == 1 || i == 4) {
                    this.book = null;
                }
            }
        };
        Message message = new Message();
        message.what = 0;
        message.obj = 0;
        this.readerHandler.handleMessage(message);
    }

    public static String getWorkbookDirEntryName(DirectoryNode directoryNode) {
        String[] strArr = WORKBOOK_DIR_ENTRY_NAMES;
        int i = 0;
        while (i < strArr.length) {
            String str = strArr[i];
            try {
                directoryNode.getEntry(str);
                return str;
            } catch (FileNotFoundException unused) {
                i++;
            }
        }
        try {
            directoryNode.getEntry("Book");
            throw new OldExcelFormatException("The supplied spreadsheet seems to be Excel 5.0/7.0 (BIFF5) format. POI only supports BIFF8 format (from Excel versions 97/2000/XP/2003)");
        } catch (FileNotFoundException unused2) {
            throw new IllegalArgumentException("The supplied POIFSFileSystem does not contain a BIFF8 'Workbook' entry. Is it really an excel file?");
        }
    }

    private void convertLabelRecords(List list, int i) {
        while (i < list.size()) {
            Record record = (Record) list.get(i);
            if (record.getSid() == 516) {
                this.sharedString.put(Integer.valueOf(this.sharedString.size()), ((LabelRecord) record).getValue());
            }
            i++;
        }
    }

    private void processCellStyle(InternalWorkbook internalWorkbook) {
        processFont(internalWorkbook);
        short numExFormats = (short) internalWorkbook.getNumExFormats();
        short s = 0;
        while (s < numExFormats) {
            ExtendedFormatRecord exFormatAt = internalWorkbook.getExFormatAt(s);
            if (exFormatAt != null) {
                CellStyle cellStyle = new CellStyle();
                cellStyle.setIndex(s);
                cellStyle.setNumberFormatID(exFormatAt.getFormatIndex());
                cellStyle.setFormatCode(HSSFDataFormat.getFormatCode(internalWorkbook, exFormatAt.getFormatIndex()));
                cellStyle.setFontIndex(exFormatAt.getFontIndex());
                cellStyle.setHidden(exFormatAt.isHidden());
                cellStyle.setLocked(exFormatAt.isLocked());
                cellStyle.setWrapText(exFormatAt.getWrapText());
                cellStyle.setHorizontalAlign(exFormatAt.getAlignment());
                cellStyle.setVerticalAlign(exFormatAt.getVerticalAlignment());
                cellStyle.setRotation(exFormatAt.getRotation());
                cellStyle.setIndent(exFormatAt.getIndent());
                cellStyle.setBorderLeft(exFormatAt.getBorderLeft());
                short leftBorderPaletteIdx = exFormatAt.getLeftBorderPaletteIdx();
                short s2 = 8;
                if (leftBorderPaletteIdx == 64) {
                    leftBorderPaletteIdx = 8;
                }
                cellStyle.setBorderLeftColorIdx(leftBorderPaletteIdx);
                cellStyle.setBorderRight(exFormatAt.getBorderRight());
                short rightBorderPaletteIdx = exFormatAt.getRightBorderPaletteIdx();
                if (rightBorderPaletteIdx == 64) {
                    rightBorderPaletteIdx = 8;
                }
                cellStyle.setBorderRightColorIdx(rightBorderPaletteIdx);
                cellStyle.setBorderTop(exFormatAt.getBorderTop());
                short topBorderPaletteIdx = exFormatAt.getTopBorderPaletteIdx();
                if (topBorderPaletteIdx == 64) {
                    topBorderPaletteIdx = 8;
                }
                cellStyle.setBorderTopColorIdx(topBorderPaletteIdx);
                cellStyle.setBorderBottom(exFormatAt.getBorderBottom());
                short bottomBorderPaletteIdx = exFormatAt.getBottomBorderPaletteIdx();
                if (bottomBorderPaletteIdx != 64) {
                    s2 = bottomBorderPaletteIdx;
                }
                cellStyle.setBorderBottomColorIdx(s2);
                cellStyle.setBgColor(getColor(exFormatAt.getFillBackground()));
                short fillForeground = exFormatAt.getFillForeground();
                if (fillForeground == 64) {
                    fillForeground = 9;
                }
                cellStyle.setFgColor(getColor(fillForeground));
                cellStyle.setFillPatternType((byte) (exFormatAt.getAdtlFillPattern() - 1));
                addCellStyle(s, cellStyle);
                s = (short) (s + 1);
            }
        }
    }

    private void processFont(InternalWorkbook internalWorkbook) {
        int numberOfFontRecords = internalWorkbook.getNumberOfFontRecords();
        if (numberOfFontRecords <= 4) {
            numberOfFontRecords--;
        }
        for (int i = 0; i <= numberOfFontRecords; i++) {
            FontRecord fontRecordAt = internalWorkbook.getFontRecordAt(i);
            Font font = new Font();
            font.setIndex(i);
            font.setName(fontRecordAt.getFontName());
            font.setFontSize((double) ((short) (fontRecordAt.getFontHeight() / 20)));
            short colorPaletteIndex = fontRecordAt.getColorPaletteIndex();
            if (colorPaletteIndex == Short.MAX_VALUE) {
                colorPaletteIndex = 8;
            }
            font.setColorIndex(colorPaletteIndex);
            font.setItalic(fontRecordAt.isItalic());
            font.setBold(fontRecordAt.getBoldWeight() > 400);
            font.setSuperSubScript((byte) fontRecordAt.getSuperSubScript());
            font.setStrikeline(fontRecordAt.isStruckout());
            font.setUnderline((int) fontRecordAt.getUnderline());
            addFont(i, font);
        }
    }

    public InternalWorkbook getInternalWorkbook() {
        return this.workbook;
    }

    public UDFFinder getUDFFinder() {
        return this._udfFinder;
    }

    public int getNumberOfSheets() {
        return this.sheets.size();
    }

    public ASheet getSheetAt(int i) {
        if (i < 0 || i >= this.sheets.size()) {
            return null;
        }
        return (ASheet) this.sheets.get(Integer.valueOf(i));
    }

    public int getSheetIndex(String str) {
        return this.workbook.getSheetIndex(str);
    }

    public int getSheetIndex(Sheet sheet) {
        for (int i = 0; i < this.sheets.size(); i++) {
            if (this.sheets.get(Integer.valueOf(i)) == sheet) {
                return i;
            }
        }
        return -1;
    }

    public int getNumberOfNames() {
        return this.names.size();
    }

    public int getNameIndex(String str) {
        for (int i = 0; i < this.names.size(); i++) {
            if (getNameName(i).equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }

    public HSSFName getName(String str) {
        int nameIndex = getNameIndex(str);
        if (nameIndex < 0) {
            return null;
        }
        return this.names.get(nameIndex);
    }

    public HSSFName getNameAt(int i) {
        int size = this.names.size();
        if (size < 1) {
            throw new IllegalStateException("There are no defined names in this workbook");
        } else if (i >= 0 && i <= size) {
            return this.names.get(i);
        } else {
            throw new IllegalArgumentException("Specified name index " + i + " is outside the allowable range (0.." + (size - 1) + ").");
        }
    }

    public NameRecord getNameRecord(int i) {
        return this.workbook.getNameRecord(i);
    }

    public String getNameName(int i) {
        return getNameAt(i).getNameName();
    }

    public AbstractReader getAbstractReader() {
        return this.iAbortListener;
    }

    public void dispose() {
        destroy();
        this.workbook = null;
        ArrayList<HSSFName> arrayList = this.names;
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<HSSFName> it = this.names.iterator();
            while (it.hasNext()) {
                it.next().dispose();
            }
            this.names.clear();
            this.names = null;
        }
        this._udfFinder = null;
        this.iAbortListener = null;
    }
}
