package com.app.office.fc.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LittleEndianInputStream extends FilterInputStream implements LittleEndianInput {
    public LittleEndianInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public int available() {
        try {
            return super.available();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte readByte() {
        return (byte) readUByte();
    }

    public int readUByte() {
        try {
            int read = this.in.read();
            checkEOF(read);
            return read;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public int readInt() {
        try {
            int read = this.in.read();
            int read2 = this.in.read();
            int read3 = this.in.read();
            int read4 = this.in.read();
            checkEOF(read | read2 | read3 | read4);
            return (read4 << 24) + (read3 << 16) + (read2 << 8) + (read << 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long readLong() {
        try {
            int read = this.in.read();
            int read2 = this.in.read();
            int read3 = this.in.read();
            int read4 = this.in.read();
            int read5 = this.in.read();
            int read6 = this.in.read();
            int read7 = this.in.read();
            int read8 = this.in.read();
            checkEOF(read | read2 | read3 | read4 | read5 | read6 | read7 | read8);
            return (((long) read8) << 56) + (((long) read7) << 48) + (((long) read6) << 40) + (((long) read5) << 32) + (((long) read4) << 24) + ((long) (read3 << 16)) + ((long) (read2 << 8)) + ((long) (read << 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public short readShort() {
        return (short) readUShort();
    }

    public int readUShort() {
        try {
            int read = this.in.read();
            int read2 = this.in.read();
            checkEOF(read | read2);
            return (read2 << 8) + (read << 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkEOF(int i) {
        if (i < 0) {
            throw new RuntimeException("Unexpected end-of-file");
        }
    }

    public void readFully(byte[] bArr) {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            try {
                int read = this.in.read();
                checkEOF(read);
                bArr[i] = (byte) read;
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
