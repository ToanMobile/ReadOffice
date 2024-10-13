package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class EOFRecord extends StandardRecord {
    public static final int ENCODED_SIZE = 4;
    public static final EOFRecord instance = new EOFRecord();
    public static final short sid = 10;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return 10;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
    }

    private EOFRecord() {
    }

    public EOFRecord(RecordInputStream recordInputStream) {
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[EOF]\n");
        stringBuffer.append("[/EOF]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        return instance;
    }
}
