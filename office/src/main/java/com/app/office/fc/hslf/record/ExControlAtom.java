package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class ExControlAtom extends RecordAtom {
    private byte[] _header;
    private int _id;

    protected ExControlAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, 4);
    }

    protected ExControlAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._id = LittleEndian.getInt(bArr, i + 8);
    }

    public int getSlideId() {
        return this._id;
    }

    public void setSlideId(int i) {
        this._id = i;
    }

    public long getRecordType() {
        return (long) RecordTypes.ExControlAtom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, this._id);
        outputStream.write(bArr);
    }

    public void dispose() {
        this._header = null;
    }
}
