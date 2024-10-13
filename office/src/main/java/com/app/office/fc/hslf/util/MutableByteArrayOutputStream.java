package com.app.office.fc.hslf.util;

import java.io.ByteArrayOutputStream;

public final class MutableByteArrayOutputStream extends ByteArrayOutputStream {
    public int getBytesWritten() {
        return -1;
    }

    public void overwrite(byte[] bArr, int i) {
    }

    public void write(int i) {
    }

    public void write(byte[] bArr) {
    }
}
