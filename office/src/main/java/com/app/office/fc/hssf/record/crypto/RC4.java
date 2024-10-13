package com.app.office.fc.hssf.record.crypto;

import com.app.office.fc.util.HexDump;

final class RC4 {
    private int _i;
    private int _j;
    private final byte[] _s = new byte[256];

    public RC4(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < 256; i++) {
            this._s[i] = (byte) i;
        }
        byte b = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = b + bArr[i2 % length];
            byte[] bArr2 = this._s;
            b = (i3 + bArr2[i2]) & 255;
            byte b2 = bArr2[i2];
            bArr2[i2] = bArr2[b];
            bArr2[b] = b2;
        }
        this._i = 0;
        this._j = 0;
    }

    public byte output() {
        int i = (this._i + 1) & 255;
        this._i = i;
        int i2 = this._j;
        byte[] bArr = this._s;
        int i3 = (i2 + bArr[i]) & 255;
        this._j = i3;
        byte b = bArr[i];
        bArr[i] = bArr[i3];
        bArr[i3] = b;
        return bArr[(bArr[i] + bArr[i3]) & 255];
    }

    public void encrypt(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ output());
        }
    }

    public void encrypt(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            bArr[i] = (byte) (bArr[i] ^ output());
            i++;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append("i=");
        stringBuffer.append(this._i);
        stringBuffer.append(" j=");
        stringBuffer.append(this._j);
        stringBuffer.append("]");
        stringBuffer.append("\n");
        stringBuffer.append(HexDump.dump(this._s, 0, 0));
        return stringBuffer.toString();
    }
}
