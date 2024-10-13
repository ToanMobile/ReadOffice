package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class DateWindow1904Record extends StandardRecord {
    public static final short sid = 34;
    private short field_1_window;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 34;
    }

    public DateWindow1904Record() {
    }

    public DateWindow1904Record(RecordInputStream recordInputStream) {
        this.field_1_window = recordInputStream.readShort();
    }

    public void setWindowing(short s) {
        this.field_1_window = s;
    }

    public short getWindowing() {
        return this.field_1_window;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[1904]\n");
        stringBuffer.append("    .is1904          = ");
        stringBuffer.append(Integer.toHexString(getWindowing()));
        stringBuffer.append("\n");
        stringBuffer.append("[/1904]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getWindowing());
    }
}
