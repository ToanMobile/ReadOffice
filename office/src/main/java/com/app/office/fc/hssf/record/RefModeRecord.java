package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class RefModeRecord extends StandardRecord {
    public static final short USE_A1_MODE = 1;
    public static final short USE_R1C1_MODE = 0;
    public static final short sid = 15;
    private short field_1_mode;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 15;
    }

    public RefModeRecord() {
    }

    public RefModeRecord(RecordInputStream recordInputStream) {
        this.field_1_mode = recordInputStream.readShort();
    }

    public void setMode(short s) {
        this.field_1_mode = s;
    }

    public short getMode() {
        return this.field_1_mode;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[REFMODE]\n");
        stringBuffer.append("    .mode           = ");
        stringBuffer.append(Integer.toHexString(getMode()));
        stringBuffer.append("\n");
        stringBuffer.append("[/REFMODE]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getMode());
    }

    public Object clone() {
        RefModeRecord refModeRecord = new RefModeRecord();
        refModeRecord.field_1_mode = this.field_1_mode;
        return refModeRecord;
    }
}
