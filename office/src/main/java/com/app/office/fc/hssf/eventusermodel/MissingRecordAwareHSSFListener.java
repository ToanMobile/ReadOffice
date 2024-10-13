package com.app.office.fc.hssf.eventusermodel;

import com.app.office.fc.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import com.app.office.fc.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import com.app.office.fc.hssf.eventusermodel.dummyrecord.MissingRowDummyRecord;
import com.app.office.fc.hssf.record.BOFRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.MulBlankRecord;
import com.app.office.fc.hssf.record.MulRKRecord;
import com.app.office.fc.hssf.record.NoteRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordFactory;
import com.app.office.fc.hssf.record.RowRecord;

public final class MissingRecordAwareHSSFListener implements HSSFListener {
    private HSSFListener childListener;
    private int lastCellColumn;
    private int lastCellRow;
    private int lastRowRow;

    public MissingRecordAwareHSSFListener(HSSFListener hSSFListener) {
        resetCounts();
        this.childListener = hSSFListener;
    }

    public void processRecord(Record record) {
        int i;
        int i2;
        int i3;
        CellValueRecordInterface[] cellValueRecordInterfaceArr = null;
        if (record instanceof CellValueRecordInterface) {
            CellValueRecordInterface cellValueRecordInterface = (CellValueRecordInterface) record;
            i = cellValueRecordInterface.getRow();
            i2 = cellValueRecordInterface.getColumn();
        } else {
            short sid = record.getSid();
            if (sid != 28) {
                if (sid == 520) {
                    RowRecord rowRecord = (RowRecord) record;
                    if (this.lastRowRow + 1 < rowRecord.getRowNumber()) {
                        int i4 = this.lastRowRow;
                        while (true) {
                            i4++;
                            if (i4 >= rowRecord.getRowNumber()) {
                                break;
                            }
                            this.childListener.processRecord(new MissingRowDummyRecord(i4));
                        }
                    }
                    this.lastRowRow = rowRecord.getRowNumber();
                } else if (sid == 1212) {
                    this.childListener.processRecord(record);
                    return;
                } else if (sid == 2057) {
                    BOFRecord bOFRecord = (BOFRecord) record;
                    if (bOFRecord.getType() == 5 || bOFRecord.getType() == 16) {
                        resetCounts();
                    }
                } else if (sid == 189) {
                    cellValueRecordInterfaceArr = RecordFactory.convertRKRecords((MulRKRecord) record);
                } else if (sid == 190) {
                    cellValueRecordInterfaceArr = RecordFactory.convertBlankRecords((MulBlankRecord) record);
                }
                i2 = -1;
                i = -1;
            } else {
                NoteRecord noteRecord = (NoteRecord) record;
                i = noteRecord.getRow();
                i2 = noteRecord.getColumn();
            }
        }
        if (cellValueRecordInterfaceArr != null && cellValueRecordInterfaceArr.length > 0) {
            i = cellValueRecordInterfaceArr[0].getRow();
            i2 = cellValueRecordInterfaceArr[0].getColumn();
        }
        int i5 = this.lastCellRow;
        if (i != i5 && i5 > -1) {
            while (i5 < i) {
                this.childListener.processRecord(new LastCellOfRowDummyRecord(i5, i5 == this.lastCellRow ? this.lastCellColumn : -1));
                i5++;
            }
        }
        int i6 = this.lastCellRow;
        if (!(i6 == -1 || (i3 = this.lastCellColumn) == -1 || i != -1)) {
            this.childListener.processRecord(new LastCellOfRowDummyRecord(i6, i3));
            this.lastCellRow = -1;
            this.lastCellColumn = -1;
        }
        if (i != this.lastCellRow) {
            this.lastCellColumn = -1;
        }
        int i7 = this.lastCellColumn;
        if (i7 != i2 - 1) {
            while (true) {
                i7++;
                if (i7 >= i2) {
                    break;
                }
                this.childListener.processRecord(new MissingCellDummyRecord(i, i7));
            }
        }
        if (cellValueRecordInterfaceArr != null && cellValueRecordInterfaceArr.length > 0) {
            i2 = cellValueRecordInterfaceArr[cellValueRecordInterfaceArr.length - 1].getColumn();
        }
        if (i2 != -1) {
            this.lastCellColumn = i2;
            this.lastCellRow = i;
        }
        if (cellValueRecordInterfaceArr == null || cellValueRecordInterfaceArr.length <= 0) {
            this.childListener.processRecord(record);
            return;
        }
        for (CellValueRecordInterface cellValueRecordInterface2 : cellValueRecordInterfaceArr) {
            this.childListener.processRecord((Record) cellValueRecordInterface2);
        }
    }

    private void resetCounts() {
        this.lastRowRow = -1;
        this.lastCellRow = -1;
        this.lastCellColumn = -1;
    }
}
