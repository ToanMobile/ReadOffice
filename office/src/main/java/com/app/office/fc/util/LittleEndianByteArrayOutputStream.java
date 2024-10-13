package com.app.office.fc.util;

public final class LittleEndianByteArrayOutputStream implements LittleEndianOutput, DelayableLittleEndianOutput {
    private final byte[] _buf;
    private final int _endIndex;
    private int _writeIndex;

    public LittleEndianByteArrayOutputStream(byte[] bArr, int i, int i2) {
        if (i < 0 || i > bArr.length) {
            throw new IllegalArgumentException("Specified startOffset (" + i + ") is out of allowable range (0.." + bArr.length + ")");
        }
        this._buf = bArr;
        this._writeIndex = i;
        int i3 = i2 + i;
        this._endIndex = i3;
        if (i3 < i || i3 > bArr.length) {
            throw new IllegalArgumentException("calculated end index (" + i3 + ") is out of allowable range (" + this._writeIndex + ".." + bArr.length + ")");
        }
    }

    public LittleEndianByteArrayOutputStream(byte[] bArr, int i) {
        this(bArr, i, bArr.length - i);
    }

    private void checkPosition(int i) {
        if (i > this._endIndex - this._writeIndex) {
            throw new RuntimeException("Buffer overrun");
        }
    }

    public void writeByte(int i) {
        checkPosition(1);
        byte[] bArr = this._buf;
        int i2 = this._writeIndex;
        this._writeIndex = i2 + 1;
        bArr[i2] = (byte) i;
    }

    public void writeDouble(double d) {
        writeLong(Double.doubleToLongBits(d));
    }

    public void writeInt(int i) {
        checkPosition(4);
        int i2 = this._writeIndex;
        byte[] bArr = this._buf;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 0) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 8) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 16) & 255);
        bArr[i5] = (byte) ((i >>> 24) & 255);
        this._writeIndex = i5 + 1;
    }

    public void writeLong(long j) {
        writeInt((int) (j >> 0));
        writeInt((int) (j >> 32));
    }

    public void writeShort(int i) {
        checkPosition(2);
        int i2 = this._writeIndex;
        byte[] bArr = this._buf;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 0) & 255);
        bArr[i3] = (byte) ((i >>> 8) & 255);
        this._writeIndex = i3 + 1;
    }

    public void write(byte[] bArr) {
        int length = bArr.length;
        checkPosition(length);
        System.arraycopy(bArr, 0, this._buf, this._writeIndex, length);
        this._writeIndex += length;
    }

    public void write(byte[] bArr, int i, int i2) {
        checkPosition(i2);
        System.arraycopy(bArr, i, this._buf, this._writeIndex, i2);
        this._writeIndex += i2;
    }

    public int getWriteIndex() {
        return this._writeIndex;
    }

    public LittleEndianOutput createDelayedOutput(int i) {
        checkPosition(i);
        LittleEndianByteArrayOutputStream littleEndianByteArrayOutputStream = new LittleEndianByteArrayOutputStream(this._buf, this._writeIndex, i);
        this._writeIndex += i;
        return littleEndianByteArrayOutputStream;
    }
}
