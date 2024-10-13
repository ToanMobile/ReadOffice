package com.app.office.thirdpart.emf.font;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TTFFileInput extends TTFInput {
    private long checksum;
    private long length;
    private long offset;
    private RandomAccessFile ttf;

    public TTFFileInput(RandomAccessFile randomAccessFile, long j, long j2, long j3) throws IOException {
        this.ttf = randomAccessFile;
        this.offset = j;
        this.length = j2;
        this.checksum = j3;
    }

    public void seek(long j) throws IOException {
        this.ttf.seek(this.offset + j);
    }

    /* access modifiers changed from: package-private */
    public long getPointer() throws IOException {
        return this.ttf.getFilePointer() - this.offset;
    }

    public int readByte() throws IOException {
        return this.ttf.readUnsignedByte();
    }

    public int readRawByte() throws IOException {
        return this.ttf.readByte() & 255;
    }

    public short readShort() throws IOException {
        return this.ttf.readShort();
    }

    public int readUShort() throws IOException {
        return this.ttf.readUnsignedShort();
    }

    public int readLong() throws IOException {
        return this.ttf.readInt();
    }

    public long readULong() throws IOException {
        byte[] bArr = new byte[4];
        this.ttf.readFully(bArr);
        long j = 0;
        long j2 = 1;
        for (int i = 0; i < 4; i++) {
            j += ((long) (bArr[3 - i] & 255)) * j2;
            j2 *= 256;
        }
        return j;
    }

    public byte readChar() throws IOException {
        return this.ttf.readByte();
    }

    public void readFully(byte[] bArr) throws IOException {
        this.ttf.readFully(bArr);
    }

    public String toString() {
        return this.offset + "-" + ((this.offset + this.length) - 1) + " - " + this.checksum;
    }
}
