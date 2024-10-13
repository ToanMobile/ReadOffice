package com.app.office.fc.poifs.storage;

public final class DataInputBlock {
    private final byte[] _buf;
    private int _maxIndex;
    private int _readIndex;

    DataInputBlock(byte[] bArr, int i) {
        this._buf = bArr;
        this._readIndex = i;
        this._maxIndex = bArr.length;
    }

    public int available() {
        return this._maxIndex - this._readIndex;
    }

    public int readUByte() {
        byte[] bArr = this._buf;
        int i = this._readIndex;
        this._readIndex = i + 1;
        return bArr[i] & 255;
    }

    public int readUShortLE() {
        int i = this._readIndex;
        byte[] bArr = this._buf;
        int i2 = i + 1;
        this._readIndex = i2 + 1;
        return ((bArr[i2] & 255) << 8) + ((bArr[i] & 255) << 0);
    }

    public int readUShortLE(DataInputBlock dataInputBlock) {
        byte[] bArr = dataInputBlock._buf;
        byte[] bArr2 = this._buf;
        int i = this._readIndex;
        this._readIndex = i + 1;
        return ((bArr2[i] & 255) << 8) + ((bArr[bArr.length - 1] & 255) << 0);
    }

    public int readIntLE() {
        int i = this._readIndex;
        byte[] bArr = this._buf;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        this._readIndex = i4 + 1;
        return ((bArr[i4] & 255) << 24) + ((bArr[i3] & 255) << 16) + ((bArr[i2] & 255) << 8) + ((bArr[i] & 255) << 0);
    }

    public int readIntLE(DataInputBlock dataInputBlock, int i) {
        byte[] bArr = new byte[4];
        readSpanning(dataInputBlock, i, bArr);
        return ((bArr[3] & 255) << 24) + ((bArr[2] & 255) << 16) + ((bArr[1] & 255) << 8) + ((bArr[0] & 255) << 0);
    }

    public long readLongLE() {
        int i = this._readIndex;
        byte[] bArr = this._buf;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        this._readIndex = i8 + 1;
        return (((long) (bArr[i8] & 255)) << 56) + (((long) (bArr[i7] & 255)) << 48) + (((long) (bArr[i6] & 255)) << 40) + (((long) (bArr[i5] & 255)) << 32) + (((long) (bArr[i4] & 255)) << 24) + ((long) ((bArr[i3] & 255) << 16)) + ((long) ((bArr[i2] & 255) << 8)) + ((long) ((bArr[i] & 255) << 0));
    }

    public long readLongLE(DataInputBlock dataInputBlock, int i) {
        byte[] bArr = new byte[8];
        readSpanning(dataInputBlock, i, bArr);
        return (((long) (bArr[7] & 255)) << 56) + (((long) (bArr[6] & 255)) << 48) + (((long) (bArr[5] & 255)) << 40) + (((long) (bArr[4] & 255)) << 32) + (((long) (bArr[3] & 255)) << 24) + ((long) ((bArr[2] & 255) << 16)) + ((long) ((bArr[1] & 255) << 8)) + ((long) ((bArr[0] & 255) << 0));
    }

    private void readSpanning(DataInputBlock dataInputBlock, int i, byte[] bArr) {
        System.arraycopy(dataInputBlock._buf, dataInputBlock._readIndex, bArr, 0, i);
        int length = bArr.length - i;
        System.arraycopy(this._buf, 0, bArr, i, length);
        this._readIndex = length;
    }

    public void readFully(byte[] bArr, int i, int i2) {
        System.arraycopy(this._buf, this._readIndex, bArr, i, i2);
        this._readIndex += i2;
    }
}
