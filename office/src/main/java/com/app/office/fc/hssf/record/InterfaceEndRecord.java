package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class InterfaceEndRecord extends StandardRecord {
    public static final InterfaceEndRecord instance = new InterfaceEndRecord();
    public static final short sid = 226;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return sid;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
    }

    public String toString() {
        return "[INTERFACEEND/]\n";
    }

    private InterfaceEndRecord() {
    }

    public static Record create(RecordInputStream recordInputStream) {
        int remaining = recordInputStream.remaining();
        if (remaining == 0) {
            return instance;
        }
        if (remaining == 2) {
            return new InterfaceHdrRecord(recordInputStream);
        }
        throw new RecordFormatException("Invalid record data size: " + recordInputStream.remaining());
    }
}
