package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class OEShapeAtom extends RecordAtom {
    private byte[] _header;
    private byte[] _recdata;

    public OEShapeAtom() {
        this._recdata = new byte[4];
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._recdata.length);
    }

    protected OEShapeAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._recdata = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public long getRecordType() {
        return (long) RecordTypes.OEShapeAtom.typeID;
    }

    public int getOptions() {
        return LittleEndian.getInt(this._recdata, 0);
    }

    public void setOptions(int i) {
        LittleEndian.putInt(this._recdata, 0, i);
    }

    public void dispose() {
        this._header = null;
        this._recdata = null;
    }
}
