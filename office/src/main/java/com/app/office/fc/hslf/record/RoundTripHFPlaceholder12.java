package com.app.office.fc.hslf.record;

public final class RoundTripHFPlaceholder12 extends RecordAtom {
    private byte[] _header;
    private byte _placeholderId;

    protected RoundTripHFPlaceholder12(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._placeholderId = bArr[i + 8];
    }

    public int getPlaceholderId() {
        return this._placeholderId;
    }

    public void setPlaceholderId(int i) {
        this._placeholderId = (byte) i;
    }

    public long getRecordType() {
        return (long) RecordTypes.RoundTripHFPlaceholder12.typeID;
    }

    public void dispose() {
        this._header = null;
    }
}
