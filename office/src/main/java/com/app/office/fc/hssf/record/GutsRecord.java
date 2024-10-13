package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class GutsRecord extends StandardRecord {
    public static final short sid = 128;
    private short field_1_left_row_gutter;
    private short field_2_top_col_gutter;
    private short field_3_row_level_max;
    private short field_4_col_level_max;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return 128;
    }

    public GutsRecord() {
    }

    public GutsRecord(RecordInputStream recordInputStream) {
        this.field_1_left_row_gutter = recordInputStream.readShort();
        this.field_2_top_col_gutter = recordInputStream.readShort();
        this.field_3_row_level_max = recordInputStream.readShort();
        this.field_4_col_level_max = recordInputStream.readShort();
    }

    public void setLeftRowGutter(short s) {
        this.field_1_left_row_gutter = s;
    }

    public void setTopColGutter(short s) {
        this.field_2_top_col_gutter = s;
    }

    public void setRowLevelMax(short s) {
        this.field_3_row_level_max = s;
    }

    public void setColLevelMax(short s) {
        this.field_4_col_level_max = s;
    }

    public short getLeftRowGutter() {
        return this.field_1_left_row_gutter;
    }

    public short getTopColGutter() {
        return this.field_2_top_col_gutter;
    }

    public short getRowLevelMax() {
        return this.field_3_row_level_max;
    }

    public short getColLevelMax() {
        return this.field_4_col_level_max;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[GUTS]\n");
        stringBuffer.append("    .leftgutter     = ");
        stringBuffer.append(Integer.toHexString(getLeftRowGutter()));
        stringBuffer.append("\n");
        stringBuffer.append("    .topgutter      = ");
        stringBuffer.append(Integer.toHexString(getTopColGutter()));
        stringBuffer.append("\n");
        stringBuffer.append("    .rowlevelmax    = ");
        stringBuffer.append(Integer.toHexString(getRowLevelMax()));
        stringBuffer.append("\n");
        stringBuffer.append("    .collevelmax    = ");
        stringBuffer.append(Integer.toHexString(getColLevelMax()));
        stringBuffer.append("\n");
        stringBuffer.append("[/GUTS]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getLeftRowGutter());
        littleEndianOutput.writeShort(getTopColGutter());
        littleEndianOutput.writeShort(getRowLevelMax());
        littleEndianOutput.writeShort(getColLevelMax());
    }

    public Object clone() {
        GutsRecord gutsRecord = new GutsRecord();
        gutsRecord.field_1_left_row_gutter = this.field_1_left_row_gutter;
        gutsRecord.field_2_top_col_gutter = this.field_2_top_col_gutter;
        gutsRecord.field_3_row_level_max = this.field_3_row_level_max;
        gutsRecord.field_4_col_level_max = this.field_4_col_level_max;
        return gutsRecord;
    }
}
