package com.app.office.thirdpart.emf.font;

import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Stack;

public abstract class TTFInput {
    private Stack filePosStack = new Stack();
    private int tempFlags;

    public static boolean flagBit(int i, int i2) {
        return (i & (1 << i2)) > 0;
    }

    /* access modifiers changed from: package-private */
    public abstract long getPointer() throws IOException;

    public abstract int readByte() throws IOException;

    public abstract byte readChar() throws IOException;

    public abstract void readFully(byte[] bArr) throws IOException;

    public abstract int readLong() throws IOException;

    public abstract int readRawByte() throws IOException;

    public abstract short readShort() throws IOException;

    public abstract long readULong() throws IOException;

    public abstract int readUShort() throws IOException;

    public abstract void seek(long j) throws IOException;

    public void pushPos() throws IOException {
        this.filePosStack.push(new Long(getPointer()));
    }

    public void popPos() throws IOException {
        seek(((Long) this.filePosStack.pop()).longValue());
    }

    public final short readFWord() throws IOException {
        return readShort();
    }

    public final int readUFWord() throws IOException {
        return readUShort();
    }

    public final double readFixed() throws IOException {
        return ((double) readShort()) + (((double) readShort()) / 16384.0d);
    }

    public final double readF2Dot14() throws IOException {
        int readByte = readByte();
        int readByte2 = readByte() + ((readByte & 63) << 8);
        int i = readByte >> 6;
        if (i >= 2) {
            i -= 4;
        }
        return ((double) i) + (((double) readByte2) / 16384.0d);
    }

    public final void checkShortZero() throws IOException {
        if (readShort() != 0) {
            System.err.println("Reserved bit should be 0.");
        }
    }

    public static final boolean checkZeroBit(int i, int i2, String str) throws IOException {
        if (!flagBit(i, i2)) {
            return true;
        }
        PrintStream printStream = System.err;
        printStream.println("Reserved bit " + i2 + " in " + str + " not 0.");
        return false;
    }

    public void readUShortFlags() throws IOException {
        this.tempFlags = readUShort();
    }

    public void readByteFlags() throws IOException {
        this.tempFlags = readByte();
    }

    public boolean flagBit(int i) {
        return flagBit(this.tempFlags, i);
    }

    public int[] readFFFFTerminatedUShortArray() throws IOException {
        int readUShort;
        LinkedList<Integer> linkedList = new LinkedList<>();
        do {
            readUShort = readUShort();
            linkedList.add(new Integer(readUShort));
        } while (readUShort != 65535);
        int[] iArr = new int[linkedList.size()];
        int i = 0;
        for (Integer intValue : linkedList) {
            iArr[i] = intValue.intValue();
            i++;
        }
        return iArr;
    }

    public int[] readUShortArray(int i) throws IOException {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readUShort();
        }
        return iArr;
    }

    public short[] readShortArray(int i) throws IOException {
        short[] sArr = new short[i];
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = readShort();
        }
        return sArr;
    }
}
