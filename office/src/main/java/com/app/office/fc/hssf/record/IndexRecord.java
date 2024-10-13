package com.app.office.fc.hssf.record;

import com.app.office.fc.util.IntList;
import com.app.office.fc.util.LittleEndianOutput;

public class IndexRecord extends StandardRecord {
    public static final short sid = 523;
    private int field_2_first_row;
    private int field_3_last_row_add1;
    private int field_4_zero;
    private IntList field_5_dbcells;

    public static int getRecordSizeForBlockCount(int i) {
        return (i * 4) + 20;
    }

    public short getSid() {
        return 523;
    }

    public IndexRecord() {
    }

    public IndexRecord(RecordInputStream recordInputStream) {
        int readInt = recordInputStream.readInt();
        if (readInt == 0) {
            this.field_2_first_row = recordInputStream.readInt();
            this.field_3_last_row_add1 = recordInputStream.readInt();
            this.field_4_zero = recordInputStream.readInt();
            int remaining = recordInputStream.remaining() / 4;
            this.field_5_dbcells = new IntList(remaining);
            for (int i = 0; i < remaining; i++) {
                this.field_5_dbcells.add(recordInputStream.readInt());
            }
            return;
        }
        throw new RecordFormatException("Expected zero for field 1 but got " + readInt);
    }

    public void setFirstRow(int i) {
        this.field_2_first_row = i;
    }

    public void setLastRowAdd1(int i) {
        this.field_3_last_row_add1 = i;
    }

    public void addDbcell(int i) {
        if (this.field_5_dbcells == null) {
            this.field_5_dbcells = new IntList();
        }
        this.field_5_dbcells.add(i);
    }

    public void setDbcell(int i, int i2) {
        this.field_5_dbcells.set(i, i2);
    }

    public int getFirstRow() {
        return this.field_2_first_row;
    }

    public int getLastRowAdd1() {
        return this.field_3_last_row_add1;
    }

    public int getNumDbcells() {
        IntList intList = this.field_5_dbcells;
        if (intList == null) {
            return 0;
        }
        return intList.size();
    }

    public int getDbcellAt(int i) {
        return this.field_5_dbcells.get(i);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[INDEX]\n");
        stringBuffer.append("    .firstrow       = ");
        stringBuffer.append(Integer.toHexString(getFirstRow()));
        stringBuffer.append("\n");
        stringBuffer.append("    .lastrowadd1    = ");
        stringBuffer.append(Integer.toHexString(getLastRowAdd1()));
        stringBuffer.append("\n");
        for (int i = 0; i < getNumDbcells(); i++) {
            stringBuffer.append("    .dbcell_");
            stringBuffer.append(i);
            stringBuffer.append(" = ");
            stringBuffer.append(Integer.toHexString(getDbcellAt(i)));
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/INDEX]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(0);
        littleEndianOutput.writeInt(getFirstRow());
        littleEndianOutput.writeInt(getLastRowAdd1());
        littleEndianOutput.writeInt(this.field_4_zero);
        for (int i = 0; i < getNumDbcells(); i++) {
            littleEndianOutput.writeInt(getDbcellAt(i));
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (getNumDbcells() * 4) + 16;
    }

    public Object clone() {
        IndexRecord indexRecord = new IndexRecord();
        indexRecord.field_2_first_row = this.field_2_first_row;
        indexRecord.field_3_last_row_add1 = this.field_3_last_row_add1;
        indexRecord.field_4_zero = this.field_4_zero;
        IntList intList = new IntList();
        indexRecord.field_5_dbcells = intList;
        intList.addAll(this.field_5_dbcells);
        return indexRecord;
    }
}
