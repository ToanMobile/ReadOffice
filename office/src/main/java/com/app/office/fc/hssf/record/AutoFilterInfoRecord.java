package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class AutoFilterInfoRecord extends StandardRecord {
    public static final short sid = 157;
    private short _cEntries;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public AutoFilterInfoRecord() {
    }

    public AutoFilterInfoRecord(RecordInputStream recordInputStream) {
        this._cEntries = recordInputStream.readShort();
    }

    public void setNumEntries(short s) {
        this._cEntries = s;
    }

    public short getNumEntries() {
        return this._cEntries;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[AUTOFILTERINFO]\n");
        stringBuffer.append("    .numEntries          = ");
        stringBuffer.append(this._cEntries);
        stringBuffer.append("\n");
        stringBuffer.append("[/AUTOFILTERINFO]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._cEntries);
    }

    public Object clone() {
        return cloneViaReserialise();
    }
}
