package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class MMSRecord extends StandardRecord {
    public static final short sid = 193;
    private byte field_1_addMenuCount;
    private byte field_2_delMenuCount;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 193;
    }

    public MMSRecord() {
    }

    public MMSRecord(RecordInputStream recordInputStream) {
        if (recordInputStream.remaining() != 0) {
            this.field_1_addMenuCount = recordInputStream.readByte();
            this.field_2_delMenuCount = recordInputStream.readByte();
        }
    }

    public void setAddMenuCount(byte b) {
        this.field_1_addMenuCount = b;
    }

    public void setDelMenuCount(byte b) {
        this.field_2_delMenuCount = b;
    }

    public byte getAddMenuCount() {
        return this.field_1_addMenuCount;
    }

    public byte getDelMenuCount() {
        return this.field_2_delMenuCount;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[MMS]\n");
        stringBuffer.append("    .addMenu        = ");
        stringBuffer.append(Integer.toHexString(getAddMenuCount()));
        stringBuffer.append("\n");
        stringBuffer.append("    .delMenu        = ");
        stringBuffer.append(Integer.toHexString(getDelMenuCount()));
        stringBuffer.append("\n");
        stringBuffer.append("[/MMS]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getAddMenuCount());
        littleEndianOutput.writeByte(getDelMenuCount());
    }
}
