package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class CodepageRecord extends StandardRecord {
    public static final short CODEPAGE = 1200;
    public static final short sid = 66;
    private short field_1_codepage;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 66;
    }

    public CodepageRecord() {
    }

    public CodepageRecord(RecordInputStream recordInputStream) {
        this.field_1_codepage = recordInputStream.readShort();
    }

    public void setCodepage(short s) {
        this.field_1_codepage = s;
    }

    public short getCodepage() {
        return this.field_1_codepage;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CODEPAGE]\n");
        stringBuffer.append("    .codepage        = ");
        stringBuffer.append(Integer.toHexString(getCodepage()));
        stringBuffer.append("\n");
        stringBuffer.append("[/CODEPAGE]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getCodepage());
    }
}
