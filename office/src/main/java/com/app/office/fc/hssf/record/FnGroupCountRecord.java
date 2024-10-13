package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class FnGroupCountRecord extends StandardRecord {
    public static final short COUNT = 14;
    public static final short sid = 156;
    private short field_1_count;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public FnGroupCountRecord() {
    }

    public FnGroupCountRecord(RecordInputStream recordInputStream) {
        this.field_1_count = recordInputStream.readShort();
    }

    public void setCount(short s) {
        this.field_1_count = s;
    }

    public short getCount() {
        return this.field_1_count;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FNGROUPCOUNT]\n");
        stringBuffer.append("    .count            = ");
        stringBuffer.append(getCount());
        stringBuffer.append("\n");
        stringBuffer.append("[/FNGROUPCOUNT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getCount());
    }
}
