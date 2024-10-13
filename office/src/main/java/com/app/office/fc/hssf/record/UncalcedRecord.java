package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class UncalcedRecord extends StandardRecord {
    public static final short sid = 94;
    private short _reserved;

    public static int getStaticRecordSize() {
        return 6;
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 94;
    }

    public UncalcedRecord() {
        this._reserved = 0;
    }

    public UncalcedRecord(RecordInputStream recordInputStream) {
        this._reserved = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[UNCALCED]\n");
        stringBuffer.append("    _reserved: ");
        stringBuffer.append(this._reserved);
        stringBuffer.append(10);
        stringBuffer.append("[/UNCALCED]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._reserved);
    }
}
