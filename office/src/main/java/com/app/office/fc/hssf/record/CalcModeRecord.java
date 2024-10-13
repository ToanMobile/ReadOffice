package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class CalcModeRecord extends StandardRecord {
    public static final short AUTOMATIC = 1;
    public static final short AUTOMATIC_EXCEPT_TABLES = -1;
    public static final short MANUAL = 0;
    public static final short sid = 13;
    private short field_1_calcmode;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 13;
    }

    public CalcModeRecord() {
    }

    public CalcModeRecord(RecordInputStream recordInputStream) {
        this.field_1_calcmode = recordInputStream.readShort();
    }

    public void setCalcMode(short s) {
        this.field_1_calcmode = s;
    }

    public short getCalcMode() {
        return this.field_1_calcmode;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CALCMODE]\n");
        stringBuffer.append("    .calcmode       = ");
        stringBuffer.append(Integer.toHexString(getCalcMode()));
        stringBuffer.append("\n");
        stringBuffer.append("[/CALCMODE]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getCalcMode());
    }

    public Object clone() {
        CalcModeRecord calcModeRecord = new CalcModeRecord();
        calcModeRecord.field_1_calcmode = this.field_1_calcmode;
        return calcModeRecord;
    }
}
