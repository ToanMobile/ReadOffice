package com.app.office.fc.hslf.record;

public final class SoundCollection extends RecordContainer {
    private byte[] _header;

    protected SoundCollection(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
    }

    public long getRecordType() {
        return (long) RecordTypes.SoundCollection.typeID;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
    }
}
