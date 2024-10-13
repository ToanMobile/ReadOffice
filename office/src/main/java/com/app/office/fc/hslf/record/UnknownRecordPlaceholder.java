package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class UnknownRecordPlaceholder extends RecordAtom {
    private byte[] _contents;
    private long _type;

    protected UnknownRecordPlaceholder(byte[] bArr, int i, int i2) {
        i2 = i2 < 0 ? 0 : i2;
        byte[] bArr2 = new byte[i2];
        this._contents = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, i2);
        this._type = (long) LittleEndian.getUShort(this._contents, 2);
    }

    public long getRecordType() {
        return this._type;
    }

    public void dispose() {
        this._contents = null;
    }
}
