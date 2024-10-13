package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public final class UnhandledDataStructure {
    byte[] _buf;

    public UnhandledDataStructure(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        this._buf = bArr2;
        if (i + i2 <= bArr.length) {
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return;
        }
        throw new IndexOutOfBoundsException("buffer length is " + bArr.length + "but code is trying to read " + i2 + " from offset " + i);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBuf() {
        return this._buf;
    }
}
