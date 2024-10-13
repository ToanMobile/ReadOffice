package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class BookBoolRecord extends StandardRecord {
    public static final short sid = 218;
    private short field_1_save_link_values;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public BookBoolRecord() {
    }

    public BookBoolRecord(RecordInputStream recordInputStream) {
        this.field_1_save_link_values = recordInputStream.readShort();
    }

    public void setSaveLinkValues(short s) {
        this.field_1_save_link_values = s;
    }

    public short getSaveLinkValues() {
        return this.field_1_save_link_values;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BOOKBOOL]\n");
        stringBuffer.append("    .savelinkvalues  = ");
        stringBuffer.append(Integer.toHexString(getSaveLinkValues()));
        stringBuffer.append("\n");
        stringBuffer.append("[/BOOKBOOL]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_save_link_values);
    }
}
