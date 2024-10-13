package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class ObjectProtectRecord extends StandardRecord {
    public static final short sid = 99;
    private short field_1_protect;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 99;
    }

    public ObjectProtectRecord() {
    }

    public ObjectProtectRecord(RecordInputStream recordInputStream) {
        this.field_1_protect = recordInputStream.readShort();
    }

    public void setProtect(boolean z) {
        if (z) {
            this.field_1_protect = 1;
        } else {
            this.field_1_protect = 0;
        }
    }

    public boolean getProtect() {
        return this.field_1_protect == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SCENARIOPROTECT]\n");
        stringBuffer.append("    .protect         = ");
        stringBuffer.append(getProtect());
        stringBuffer.append("\n");
        stringBuffer.append("[/SCENARIOPROTECT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_protect);
    }

    public Object clone() {
        ObjectProtectRecord objectProtectRecord = new ObjectProtectRecord();
        objectProtectRecord.field_1_protect = this.field_1_protect;
        return objectProtectRecord;
    }
}
