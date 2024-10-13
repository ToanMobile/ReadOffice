package com.app.office.fc.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class LittleEndianOutputStream extends FilterOutputStream implements LittleEndianOutput {
    public LittleEndianOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void writeByte(int i) {
        try {
            this.out.write(i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeDouble(double d) {
        writeLong(Double.doubleToLongBits(d));
    }

    public void writeInt(int i) {
        int i2 = (i >>> 24) & 255;
        int i3 = (i >>> 16) & 255;
        int i4 = (i >>> 8) & 255;
        try {
            this.out.write((i >>> 0) & 255);
            this.out.write(i4);
            this.out.write(i3);
            this.out.write(i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLong(long j) {
        writeInt((int) (j >> 0));
        writeInt((int) (j >> 32));
    }

    public void writeShort(int i) {
        int i2 = (i >>> 8) & 255;
        try {
            this.out.write((i >>> 0) & 255);
            this.out.write(i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(byte[] bArr) {
        try {
            super.write(bArr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(byte[] bArr, int i, int i2) {
        try {
            super.write(bArr, i, i2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
