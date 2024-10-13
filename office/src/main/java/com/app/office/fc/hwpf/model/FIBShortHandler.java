package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public final class FIBShortHandler {
    public static final int LIDFE = 13;
    public static final int MAGICCREATED = 0;
    public static final int MAGICCREATEDPRIVATE = 2;
    public static final int MAGICREVISED = 1;
    public static final int MAGICREVISEDPRIVATE = 3;
    static final int START = 32;
    short[] _shorts;

    public FIBShortHandler(byte[] bArr) {
        int i = LittleEndian.getShort(bArr, 32);
        this._shorts = new short[i];
        int i2 = 34;
        for (int i3 = 0; i3 < i; i3++) {
            this._shorts[i3] = LittleEndian.getShort(bArr, i2);
            i2 += 2;
        }
    }

    public short getShort(int i) {
        return this._shorts[i];
    }

    /* access modifiers changed from: package-private */
    public int sizeInBytes() {
        return (this._shorts.length * 2) + 2;
    }

    /* access modifiers changed from: package-private */
    public void serialize(byte[] bArr) {
        LittleEndian.putShort(bArr, 32, (short) this._shorts.length);
        int i = 34;
        int i2 = 0;
        while (true) {
            short[] sArr = this._shorts;
            if (i2 < sArr.length) {
                LittleEndian.putShort(bArr, i, sArr[i2]);
                i += 2;
                i2++;
            } else {
                return;
            }
        }
    }
}
