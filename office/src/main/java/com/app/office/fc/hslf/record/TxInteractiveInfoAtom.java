package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class TxInteractiveInfoAtom extends RecordAtom {
    private byte[] _data;
    private byte[] _header;

    public TxInteractiveInfoAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this._data = new byte[8];
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    protected TxInteractiveInfoAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public int getStartIndex() {
        return LittleEndian.getInt(this._data, 0);
    }

    public void setStartIndex(int i) {
        LittleEndian.putInt(this._data, 0, i);
    }

    public int getEndIndex() {
        return LittleEndian.getInt(this._data, 4);
    }

    public void setEndIndex(int i) {
        LittleEndian.putInt(this._data, 4, i);
    }

    public long getRecordType() {
        return (long) RecordTypes.TxInteractiveInfoAtom.typeID;
    }

    public void dispose() {
        this._header = null;
        this._data = null;
    }
}
