package com.app.office.fc.poifs.filesystem;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public final class DocumentOutputStream extends OutputStream {
    private final int _limit;
    private final OutputStream _stream;
    private int _written = 0;

    public void close() {
    }

    DocumentOutputStream(OutputStream outputStream, int i) {
        this._stream = outputStream;
        this._limit = i;
    }

    public void write(int i) throws IOException {
        limitCheck(1);
        this._stream.write(i);
    }

    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        limitCheck(i2);
        this._stream.write(bArr, i, i2);
    }

    public void flush() throws IOException {
        this._stream.flush();
    }

    /* access modifiers changed from: package-private */
    public void writeFiller(int i, byte b) throws IOException {
        int i2 = this._written;
        if (i > i2) {
            byte[] bArr = new byte[(i - i2)];
            Arrays.fill(bArr, b);
            this._stream.write(bArr);
        }
    }

    private void limitCheck(int i) throws IOException {
        int i2 = this._written;
        if (i2 + i <= this._limit) {
            this._written = i2 + i;
            return;
        }
        throw new IOException("tried to write too much data");
    }
}
