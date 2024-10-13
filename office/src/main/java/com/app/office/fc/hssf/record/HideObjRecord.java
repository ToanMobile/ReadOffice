package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class HideObjRecord extends StandardRecord {
    public static final short HIDE_ALL = 2;
    public static final short SHOW_ALL = 0;
    public static final short SHOW_PLACEHOLDERS = 1;
    public static final short sid = 141;
    private short field_1_hide_obj;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public HideObjRecord() {
    }

    public HideObjRecord(RecordInputStream recordInputStream) {
        this.field_1_hide_obj = recordInputStream.readShort();
    }

    public void setHideObj(short s) {
        this.field_1_hide_obj = s;
    }

    public short getHideObj() {
        return this.field_1_hide_obj;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[HIDEOBJ]\n");
        stringBuffer.append("    .hideobj         = ");
        stringBuffer.append(Integer.toHexString(getHideObj()));
        stringBuffer.append("\n");
        stringBuffer.append("[/HIDEOBJ]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getHideObj());
    }
}
