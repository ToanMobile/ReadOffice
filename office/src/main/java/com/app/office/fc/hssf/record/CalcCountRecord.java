package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class CalcCountRecord extends StandardRecord {
    public static final short sid = 12;
    private short field_1_iterations;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 12;
    }

    public CalcCountRecord() {
    }

    public CalcCountRecord(RecordInputStream recordInputStream) {
        this.field_1_iterations = recordInputStream.readShort();
    }

    public void setIterations(short s) {
        this.field_1_iterations = s;
    }

    public short getIterations() {
        return this.field_1_iterations;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CALCCOUNT]\n");
        stringBuffer.append("    .iterations     = ");
        stringBuffer.append(Integer.toHexString(getIterations()));
        stringBuffer.append("\n");
        stringBuffer.append("[/CALCCOUNT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getIterations());
    }

    public Object clone() {
        CalcCountRecord calcCountRecord = new CalcCountRecord();
        calcCountRecord.field_1_iterations = this.field_1_iterations;
        return calcCountRecord;
    }
}
