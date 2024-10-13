package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class ContinueRecord extends StandardRecord {
    public static final short sid = 60;
    private byte[] _data;

    public short getSid() {
        return 60;
    }

    public ContinueRecord(byte[] bArr) {
        this._data = bArr;
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        byte[] bArr = this._data;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.write(this._data);
    }

    public byte[] getData() {
        return this._data;
    }

    public void resetData() {
        this._data = null;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CONTINUE RECORD]\n");
        stringBuffer.append("    .data = ");
        stringBuffer.append(HexDump.toHex(this._data));
        stringBuffer.append("\n");
        stringBuffer.append("[/CONTINUE RECORD]\n");
        return stringBuffer.toString();
    }

    public ContinueRecord(RecordInputStream recordInputStream) {
        this._data = recordInputStream.readRemainder();
    }

    public Object clone() {
        return new ContinueRecord(this._data);
    }
}
