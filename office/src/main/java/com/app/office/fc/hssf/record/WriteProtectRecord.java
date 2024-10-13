package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class WriteProtectRecord extends StandardRecord {
    public static final short sid = 134;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return 134;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
    }

    public WriteProtectRecord() {
    }

    public WriteProtectRecord(RecordInputStream recordInputStream) {
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[WRITEPROTECT]\n");
        stringBuffer.append("[/WRITEPROTECT]\n");
        return stringBuffer.toString();
    }
}
