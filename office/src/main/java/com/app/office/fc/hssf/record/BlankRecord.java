package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class BlankRecord extends StandardRecord implements CellValueRecordInterface {
    public static final short sid = 513;
    private int field_1_row;
    private short field_2_col;
    private short field_3_xf;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 6;
    }

    public short getSid() {
        return 513;
    }

    public BlankRecord() {
    }

    public BlankRecord(RecordInputStream recordInputStream) {
        this.field_1_row = recordInputStream.readUShort();
        this.field_2_col = recordInputStream.readShort();
        this.field_3_xf = recordInputStream.readShort();
    }

    public void setRow(int i) {
        this.field_1_row = i;
    }

    public int getRow() {
        return this.field_1_row;
    }

    public short getColumn() {
        return this.field_2_col;
    }

    public void setXFIndex(short s) {
        this.field_3_xf = s;
    }

    public short getXFIndex() {
        return this.field_3_xf;
    }

    public void setColumn(short s) {
        this.field_2_col = s;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BLANK]\n");
        stringBuffer.append("    row= ");
        stringBuffer.append(HexDump.shortToHex(getRow()));
        stringBuffer.append("\n");
        stringBuffer.append("    col= ");
        stringBuffer.append(HexDump.shortToHex(getColumn()));
        stringBuffer.append("\n");
        stringBuffer.append("    xf = ");
        stringBuffer.append(HexDump.shortToHex(getXFIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("[/BLANK]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getRow());
        littleEndianOutput.writeShort(getColumn());
        littleEndianOutput.writeShort(getXFIndex());
    }

    public Object clone() {
        BlankRecord blankRecord = new BlankRecord();
        blankRecord.field_1_row = this.field_1_row;
        blankRecord.field_2_col = this.field_2_col;
        blankRecord.field_3_xf = this.field_3_xf;
        return blankRecord;
    }
}
