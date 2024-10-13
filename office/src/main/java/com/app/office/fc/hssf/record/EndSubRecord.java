package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class EndSubRecord extends SubRecord {
    private static final int ENCODED_SIZE = 0;
    public static final short sid = 0;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return 0;
    }

    public boolean isTerminating() {
        return true;
    }

    public EndSubRecord() {
    }

    public EndSubRecord(LittleEndianInput littleEndianInput, int i) {
        if ((i & 255) != 0) {
            throw new RecordFormatException("Unexpected size (" + i + ")");
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ftEnd]\n");
        stringBuffer.append("[/ftEnd]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(0);
        littleEndianOutput.writeShort(0);
    }

    public Object clone() {
        return new EndSubRecord();
    }
}
