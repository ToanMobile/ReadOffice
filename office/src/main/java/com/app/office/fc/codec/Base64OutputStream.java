package com.app.office.fc.codec;

import java.io.OutputStream;

public class Base64OutputStream extends BaseNCodecOutputStream {
    public Base64OutputStream(OutputStream outputStream) {
        this(outputStream, true);
    }

    public Base64OutputStream(OutputStream outputStream, boolean z) {
        super(outputStream, new Base64(false), z);
    }

    public Base64OutputStream(OutputStream outputStream, boolean z, int i, byte[] bArr) {
        super(outputStream, new Base64(i, bArr), z);
    }
}
