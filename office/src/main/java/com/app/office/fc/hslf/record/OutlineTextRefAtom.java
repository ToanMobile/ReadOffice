package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class OutlineTextRefAtom extends RecordAtom {
    private byte[] _header;
    private int _index;

    protected OutlineTextRefAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._index = LittleEndian.getInt(bArr, i + 8);
    }

    protected OutlineTextRefAtom() {
        this._index = 0;
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 0);
        LittleEndian.putUShort(this._header, 2, (int) getRecordType());
        LittleEndian.putInt(this._header, 4, 4);
    }

    public long getRecordType() {
        return (long) RecordTypes.OutlineTextRefAtom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, 0, this._index);
        outputStream.write(bArr);
    }

    public void setTextIndex(int i) {
        this._index = i;
    }

    public int getTextIndex() {
        return this._index;
    }

    public void dispose() {
        this._header = null;
    }
}
