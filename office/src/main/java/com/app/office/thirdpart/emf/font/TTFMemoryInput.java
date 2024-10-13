package com.app.office.thirdpart.emf.font;

public class TTFMemoryInput extends TTFInput {
    private byte[] data;
    private int pointer;

    public TTFMemoryInput(byte[] bArr) {
        this.data = bArr;
    }

    public void seek(long j) {
        this.pointer = (int) j;
    }

    /* access modifiers changed from: package-private */
    public long getPointer() {
        return (long) this.pointer;
    }

    public byte readChar() {
        byte[] bArr = this.data;
        int i = this.pointer;
        this.pointer = i + 1;
        return bArr[i];
    }

    public int readRawByte() {
        byte[] bArr = this.data;
        int i = this.pointer;
        this.pointer = i + 1;
        return bArr[i] & 255;
    }

    public int readByte() {
        byte[] bArr = this.data;
        int i = this.pointer;
        this.pointer = i + 1;
        return bArr[i] & 255;
    }

    public short readShort() {
        byte[] bArr = this.data;
        int i = this.pointer;
        int i2 = i + 1;
        this.pointer = i2;
        this.pointer = i2 + 1;
        return (short) (bArr[i2] | (bArr[i] << 8));
    }

    public int readUShort() {
        byte[] bArr = this.data;
        int i = this.pointer;
        int i2 = i + 1;
        this.pointer = i2;
        this.pointer = i2 + 1;
        return bArr[i2] | (bArr[i] << 8);
    }

    public int readLong() {
        byte[] bArr = this.data;
        int i = this.pointer;
        int i2 = i + 1;
        this.pointer = i2;
        int i3 = i2 + 1;
        this.pointer = i3;
        byte b = (bArr[i] << 24) | (bArr[i2] << 16);
        int i4 = i3 + 1;
        this.pointer = i4;
        byte b2 = b | (bArr[i3] << 8);
        this.pointer = i4 + 1;
        return (short) (bArr[i4] | b2);
    }

    public long readULong() {
        byte[] bArr = new byte[4];
        readFully(bArr);
        long j = 0;
        for (int i = 0; i < 4; i++) {
            j |= (long) ((bArr[3 - i] & 255) << (i * 8));
        }
        return j;
    }

    public void readFully(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            byte[] bArr2 = this.data;
            int i2 = this.pointer;
            this.pointer = i2 + 1;
            bArr[i] = bArr2[i2];
        }
    }
}
