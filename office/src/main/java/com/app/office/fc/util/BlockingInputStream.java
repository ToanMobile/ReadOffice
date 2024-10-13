package com.app.office.fc.util;

import java.io.IOException;
import java.io.InputStream;

public class BlockingInputStream extends InputStream {
    protected InputStream is;

    public BlockingInputStream(InputStream inputStream) {
        this.is = inputStream;
    }

    public int available() throws IOException {
        return this.is.available();
    }

    public void close() throws IOException {
        this.is.close();
    }

    public void mark(int i) {
        this.is.mark(i);
    }

    public boolean markSupported() {
        return this.is.markSupported();
    }

    public int read() throws IOException {
        return this.is.read();
    }

    public int read(byte[] bArr) throws IOException {
        int i = 0;
        int i2 = 4611;
        while (i < bArr.length && (i2 = this.is.read()) != -1) {
            bArr[i] = (byte) i2;
            i++;
        }
        if (i == 0 && i2 == -1) {
            return -1;
        }
        return i;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.is.read(bArr, i, i2);
    }

    public void reset() throws IOException {
        this.is.reset();
    }

    public long skip(long j) throws IOException {
        return this.is.skip(j);
    }
}
