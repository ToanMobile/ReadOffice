package com.app.office.fc.hslf.record;

import com.app.office.fc.hslf.util.SystemTimeUtils;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public final class Comment2000Atom extends RecordAtom {
    private byte[] _data;
    private byte[] _header;

    protected Comment2000Atom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this._data = new byte[28];
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    protected Comment2000Atom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public int getNumber() {
        return LittleEndian.getInt(this._data, 0);
    }

    public void setNumber(int i) {
        LittleEndian.putInt(this._data, 0, i);
    }

    public Date getDate() {
        return SystemTimeUtils.getDate(this._data, 4);
    }

    public void setDate(Date date) {
        SystemTimeUtils.storeDate(date, this._data, 4);
    }

    public int getXOffset() {
        return LittleEndian.getInt(this._data, 20);
    }

    public void setXOffset(int i) {
        LittleEndian.putInt(this._data, 20, i);
    }

    public int getYOffset() {
        return LittleEndian.getInt(this._data, 24);
    }

    public void setYOffset(int i) {
        LittleEndian.putInt(this._data, 24, i);
    }

    public long getRecordType() {
        return (long) RecordTypes.Comment2000Atom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        outputStream.write(this._data);
    }

    public void dispose() {
        this._header = null;
        this._data = null;
    }
}
