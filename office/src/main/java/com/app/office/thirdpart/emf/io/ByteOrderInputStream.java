package com.app.office.thirdpart.emf.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ByteOrderInputStream extends BitInputStream implements DataInput {
    protected boolean little;

    public ByteOrderInputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public ByteOrderInputStream(InputStream inputStream, boolean z) {
        super(inputStream);
        this.little = z;
    }

    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        if (i2 >= 0) {
            int i3 = 0;
            while (i3 < i2) {
                int read = read(bArr, i + i3, i2 - i3);
                if (read >= 0) {
                    i3 += read;
                } else {
                    throw new EOFException();
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public int skipBytes(int i) throws IOException {
        int i2 = 0;
        while (i2 < i) {
            int skip = (int) skip((long) (i - i2));
            if (skip <= 0) {
                break;
            }
            i2 += skip;
        }
        return i2;
    }

    public boolean readBoolean() throws IOException {
        return readUnsignedByte() != 0;
    }

    public char readChar() throws IOException {
        int readUnsignedByte = readUnsignedByte();
        int readUnsignedByte2 = readUnsignedByte();
        return this.little ? (char) ((readUnsignedByte << 8) + readUnsignedByte2) : (char) ((readUnsignedByte2 << 8) + readUnsignedByte);
    }

    public byte readByte() throws IOException {
        byteAlign();
        int read = read();
        if (read >= 0) {
            return (byte) read;
        }
        throw new EOFException();
    }

    public byte[] readByte(int i) throws IOException {
        byteAlign();
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = read();
            if (read >= 0) {
                bArr[i2] = (byte) read;
                i2++;
            } else {
                throw new EOFException();
            }
        }
        return bArr;
    }

    public int readUnsignedByte() throws IOException {
        byteAlign();
        int read = read();
        if (read >= 0) {
            return read;
        }
        throw new EOFException();
    }

    public int[] readUnsignedByte(int i) throws IOException {
        byteAlign();
        int[] iArr = new int[i];
        int i2 = 0;
        while (i2 < i) {
            int read = read();
            if (read >= 0) {
                iArr[i2] = read;
                i2++;
            } else {
                throw new EOFException();
            }
        }
        return iArr;
    }

    public short readShort() throws IOException {
        int readUnsignedByte = readUnsignedByte();
        int readUnsignedByte2 = readUnsignedByte();
        return this.little ? (short) ((readUnsignedByte2 << 8) + readUnsignedByte) : (short) ((readUnsignedByte << 8) + readUnsignedByte2);
    }

    public short[] readShort(int i) throws IOException {
        short[] sArr = new short[i];
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = readShort();
        }
        return sArr;
    }

    public int readUnsignedShort() throws IOException {
        byteAlign();
        int readUnsignedByte = readUnsignedByte();
        int readUnsignedByte2 = readUnsignedByte();
        return this.little ? (readUnsignedByte2 << 8) + readUnsignedByte : readUnsignedByte2 + (readUnsignedByte << 8);
    }

    public int[] readUnsignedShort(int i) throws IOException {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readUnsignedShort();
        }
        return iArr;
    }

    public int readInt() throws IOException {
        int readUnsignedByte = readUnsignedByte();
        int readUnsignedByte2 = readUnsignedByte();
        int readUnsignedByte3 = readUnsignedByte();
        int readUnsignedByte4 = readUnsignedByte();
        return this.little ? (readUnsignedByte4 << 24) + (readUnsignedByte3 << 16) + (readUnsignedByte2 << 8) + readUnsignedByte : readUnsignedByte4 + (readUnsignedByte << 24) + (readUnsignedByte2 << 16) + (readUnsignedByte3 << 8);
    }

    public int[] readInt(int i) throws IOException {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readInt();
        }
        return iArr;
    }

    public long readUnsignedInt() throws IOException {
        long readUnsignedByte = (long) readUnsignedByte();
        long readUnsignedByte2 = (long) readUnsignedByte();
        long readUnsignedByte3 = (long) readUnsignedByte();
        long readUnsignedByte4 = (long) readUnsignedByte();
        return this.little ? (readUnsignedByte4 << 24) + (readUnsignedByte3 << 16) + (readUnsignedByte2 << 8) + readUnsignedByte : readUnsignedByte4 + (readUnsignedByte << 24) + (readUnsignedByte2 << 16) + (readUnsignedByte3 << 8);
    }

    public long[] readUnsignedInt(int i) throws IOException {
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = readUnsignedInt();
        }
        return jArr;
    }

    public long readLong() throws IOException {
        long readInt = (long) readInt();
        long readInt2 = (long) readInt();
        if (this.little) {
            return (readInt2 << 32) + (readInt & 4294967295L);
        }
        return (readInt2 & 4294967295L) + (readInt << 32);
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public String readLine() throws IOException {
        throw new IOException("ByteOrderInputStream.readLine() is deprecated and not implemented.");
    }

    public String readString() throws IOException {
        return readUTF();
    }

    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    public String readAsciiZString() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        int readUnsignedByte = readUnsignedByte();
        while (true) {
            char c = (char) readUnsignedByte;
            if (c == 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c);
            readUnsignedByte = readUnsignedByte();
        }
    }
}
