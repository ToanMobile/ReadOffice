package com.app.office.thirdpart.emf.io;

import java.io.IOException;
import java.io.OutputStream;

public class EEXECEncryption extends OutputStream implements EEXECConstants {
    private int c1;
    private int c2;
    private boolean first;
    private int n;
    private OutputStream out;
    private int r;

    public EEXECEncryption(OutputStream outputStream) {
        this(outputStream, 55665, 4);
    }

    public EEXECEncryption(OutputStream outputStream, int i) {
        this(outputStream, i, 4);
    }

    public EEXECEncryption(OutputStream outputStream, int i, int i2) {
        this.first = true;
        this.out = outputStream;
        this.c1 = 52845;
        this.c2 = 22719;
        this.r = i;
        this.n = i2;
    }

    private int encrypt(int i) {
        int i2 = this.r;
        int i3 = (i ^ (i2 >>> 8)) % 256;
        this.r = (((i2 + i3) * this.c1) + this.c2) % 65536;
        return i3;
    }

    public void write(int i) throws IOException {
        if (this.first) {
            for (int i2 = 0; i2 < this.n; i2++) {
                this.out.write(encrypt(0));
            }
            this.first = false;
        }
        this.out.write(encrypt(i));
    }

    public void flush() throws IOException {
        super.flush();
        this.out.flush();
    }

    public void close() throws IOException {
        flush();
        super.close();
        this.out.close();
    }

    private static class IntOutputStream extends OutputStream {
        int[] chars;
        int i;

        private IntOutputStream(int i2) {
            this.chars = new int[i2];
            this.i = 0;
        }

        public void write(int i2) {
            int[] iArr = this.chars;
            int i3 = this.i;
            this.i = i3 + 1;
            iArr[i3] = i2;
        }

        /* access modifiers changed from: private */
        public int[] getInts() {
            return this.chars;
        }
    }

    public static int[] encryptString(int[] iArr, int i, int i2) throws IOException {
        IntOutputStream intOutputStream = new IntOutputStream(iArr.length + 4);
        EEXECEncryption eEXECEncryption = new EEXECEncryption(intOutputStream, i, i2);
        for (int write : iArr) {
            eEXECEncryption.write(write);
        }
        return intOutputStream.getInts();
    }
}
