package com.app.office.fc.xls;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.model.InternalSheet;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.BoolErrRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.NumberRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordFactory;
import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import com.app.office.ss.model.XLSModel.ACell;
import com.app.office.ss.model.XLSModel.AWorkbook;
import com.app.office.system.AbortReaderError;
import com.app.office.system.IControl;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

public class XLSReader extends SSReader {
    private String filePath;

    public XLSReader(IControl iControl, String str) {
        this.control = iControl;
        this.filePath = str;
    }

    public Object getModel() throws Exception {
        return new AWorkbook(new FileInputStream(this.filePath), this);
    }

    public boolean searchContent(File file, String str) throws Exception {
        try {
            String lowerCase = str.toLowerCase();
            DirectoryNode root = new POIFSFileSystem(new FileInputStream(file.getAbsolutePath())).getRoot();
            List<Record> createRecords = RecordFactory.createRecords(root.createDocumentInputStream(AWorkbook.getWorkbookDirEntryName(root)), this);
            InternalWorkbook createWorkbook = InternalWorkbook.createWorkbook(createRecords, this);
            int numSheets = createWorkbook.getNumSheets();
            int i = 0;
            while (i < numSheets) {
                int i2 = i + 1;
                if (createWorkbook.getSheetName(i).toLowerCase().contains(lowerCase)) {
                    return true;
                }
                i = i2;
            }
            int sSTUniqueStringSize = createWorkbook.getSSTUniqueStringSize();
            for (int i3 = 0; i3 < sSTUniqueStringSize; i3++) {
                checkAbortReader();
                if (createWorkbook.getSSTString(i3).getString().toLowerCase().contains(lowerCase)) {
                    return true;
                }
            }
            RecordStream recordStream = new RecordStream(createRecords, createWorkbook.getNumRecords());
            while (recordStream.hasNext()) {
                if (search_Sheet(InternalSheet.createSheet(recordStream, this), lowerCase)) {
                    return true;
                }
            }
            for (int i4 = 0; i4 < createWorkbook.getNumNames(); i4++) {
                if (createWorkbook.getNameRecord(i4).getNameText().toLowerCase().contains(lowerCase)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean search_Sheet(InternalSheet internalSheet, String str) {
        Iterator<CellValueRecordInterface> cellValueIterator = internalSheet.getCellValueIterator();
        while (cellValueIterator.hasNext()) {
            checkAbortReader();
            if (search_Cell(cellValueIterator.next(), str)) {
                return true;
            }
        }
        return false;
    }

    private boolean search_Cell(CellValueRecordInterface cellValueRecordInterface, String str) {
        short determineType = (short) ACell.determineType(cellValueRecordInterface);
        if (determineType == 0) {
            return String.valueOf(((NumberRecord) cellValueRecordInterface).getValue()).contains(str);
        }
        if (determineType == 4) {
            return String.valueOf(((BoolErrRecord) cellValueRecordInterface).getBooleanValue()).toLowerCase().contains(str);
        }
        if (determineType != 5) {
            return false;
        }
        return ErrorEval.getText(((BoolErrRecord) cellValueRecordInterface).getErrorValue()).toLowerCase().contains(str);
    }

    private void checkAbortReader() {
        if (this.abortReader) {
            throw new AbortReaderError("abort Reader");
        }
    }

    public void dispose() {
        super.dispose();
        this.filePath = null;
    }
}
