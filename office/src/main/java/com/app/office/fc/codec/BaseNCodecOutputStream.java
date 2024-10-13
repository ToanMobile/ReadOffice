package com.app.office.fc.codec;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class BaseNCodecOutputStream extends FilterOutputStream {
    private final BaseNCodec baseNCodec;
    private final boolean doEncode;
    private final byte[] singleByte = new byte[1];

    public BaseNCodecOutputStream(OutputStream outputStream, BaseNCodec baseNCodec2, boolean z) {
        super(outputStream);
        this.baseNCodec = baseNCodec2;
        this.doEncode = z;
    }

    public void write(int i) throws IOException {
        byte[] bArr = this.singleByte;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        Objects.requireNonNull(bArr);
        if (i < 0 || i2 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i > bArr.length || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        } else if (i2 > 0) {
            if (this.doEncode) {
                this.baseNCodec.encode(bArr, i, i2);
            } else {
                this.baseNCodec.decode(bArr, i, i2);
            }
            flush(false);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r1 = new byte[r0];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void flush(boolean r5) throws java.io.IOException {
        /*
            r4 = this;
            com.app.office.fc.codec.BaseNCodec r0 = r4.baseNCodec
            int r0 = r0.available()
            if (r0 <= 0) goto L_0x0018
            byte[] r1 = new byte[r0]
            com.app.office.fc.codec.BaseNCodec r2 = r4.baseNCodec
            r3 = 0
            int r0 = r2.readResults(r1, r3, r0)
            if (r0 <= 0) goto L_0x0018
            java.io.OutputStream r2 = r4.out
            r2.write(r1, r3, r0)
        L_0x0018:
            if (r5 == 0) goto L_0x001f
            java.io.OutputStream r5 = r4.out
            r5.flush()
        L_0x001f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.codec.BaseNCodecOutputStream.flush(boolean):void");
    }

    public void flush() throws IOException {
        flush(true);
    }

    public void close() throws IOException {
        if (this.doEncode) {
            this.baseNCodec.encode(this.singleByte, 0, -1);
        } else {
            this.baseNCodec.decode(this.singleByte, 0, -1);
        }
        flush();
        this.out.close();
    }
}
