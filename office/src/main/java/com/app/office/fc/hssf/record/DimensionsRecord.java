package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class DimensionsRecord extends StandardRecord {
    public static final short sid = 512;
    private int field_1_first_row;
    private int field_2_last_row;
    private short field_3_first_col;
    private short field_4_last_col;
    private short field_5_zero;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 14;
    }

    public short getSid() {
        return 512;
    }

    public DimensionsRecord() {
    }

    public DimensionsRecord(RecordInputStream recordInputStream) {
        this.field_1_first_row = recordInputStream.readInt();
        this.field_2_last_row = recordInputStream.readInt();
        this.field_3_first_col = recordInputStream.readShort();
        this.field_4_last_col = recordInputStream.readShort();
        this.field_5_zero = recordInputStream.readShort();
    }

    public void setFirstRow(int i) {
        this.field_1_first_row = i;
    }

    public void setLastRow(int i) {
        this.field_2_last_row = i;
    }

    public void setFirstCol(short s) {
        this.field_3_first_col = s;
    }

    public void setLastCol(short s) {
        this.field_4_last_col = s;
    }

    public int getFirstRow() {
        return this.field_1_first_row;
    }

    public int getLastRow() {
        return this.field_2_last_row;
    }

    public short getFirstCol() {
        return this.field_3_first_col;
    }

    public short getLastCol() {
        return this.field_4_last_col;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[DIMENSIONS]\n");
        stringBuffer.append("    .firstrow       = ");
        stringBuffer.append(Integer.toHexString(getFirstRow()));
        stringBuffer.append("\n");
        stringBuffer.append("    .lastrow        = ");
        stringBuffer.append(Integer.toHexString(getLastRow()));
        stringBuffer.append("\n");
        stringBuffer.append("    .firstcol       = ");
        stringBuffer.append(Integer.toHexString(getFirstCol()));
        stringBuffer.append("\n");
        stringBuffer.append("    .lastcol        = ");
        stringBuffer.append(Integer.toHexString(getLastCol()));
        stringBuffer.append("\n");
        stringBuffer.append("    .zero           = ");
        stringBuffer.append(Integer.toHexString(this.field_5_zero));
        stringBuffer.append("\n");
        stringBuffer.append("[/DIMENSIONS]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(getFirstRow());
        littleEndianOutput.writeInt(getLastRow());
        littleEndianOutput.writeShort(getFirstCol());
        littleEndianOutput.writeShort(getLastCol());
        littleEndianOutput.writeShort(0);
    }

    public Object clone() {
        DimensionsRecord dimensionsRecord = new DimensionsRecord();
        dimensionsRecord.field_1_first_row = this.field_1_first_row;
        dimensionsRecord.field_2_last_row = this.field_2_last_row;
        dimensionsRecord.field_3_first_col = this.field_3_first_col;
        dimensionsRecord.field_4_last_col = this.field_4_last_col;
        dimensionsRecord.field_5_zero = this.field_5_zero;
        return dimensionsRecord;
    }
}
