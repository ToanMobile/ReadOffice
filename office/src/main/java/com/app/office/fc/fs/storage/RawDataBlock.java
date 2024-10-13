package com.app.office.fc.fs.storage;

public class RawDataBlock {
    private byte[] _data;

    public RawDataBlock(byte[] bArr) {
        this._data = bArr;
    }

    public byte[] getData() {
        return this._data;
    }
}
