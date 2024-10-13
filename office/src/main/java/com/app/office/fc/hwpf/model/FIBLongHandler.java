package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public final class FIBLongHandler {
    public static final int CBMAC = 0;
    public static final int CCPATN = 7;
    public static final int CCPEDN = 8;
    public static final int CCPFTN = 4;
    public static final int CCPHDD = 5;
    public static final int CCPHDRTXBX = 10;
    public static final int CCPMCR = 6;
    public static final int CCPTEXT = 3;
    public static final int CCPTXBX = 9;
    public static final int CPNBTECHP = 13;
    public static final int CPNBTELVC = 19;
    public static final int CPNBTEPAP = 16;
    public static final int FCISLANDFIRST = 20;
    public static final int FCISLANDLIM = 21;
    public static final int PNCHPFIRST = 12;
    public static final int PNFBPCHPFIRST = 11;
    public static final int PNFBPLVCFIRST = 17;
    public static final int PNFBPPAPFIRST = 14;
    public static final int PNLVCFIRST = 18;
    public static final int PNPAPFIRST = 15;
    public static final int PRODUCTCREATED = 1;
    public static final int PRODUCTREVISED = 2;
    int[] _longs;

    public FIBLongHandler(byte[] bArr, int i) {
        int i2 = LittleEndian.getShort(bArr, i);
        int i3 = i + 2;
        this._longs = new int[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            this._longs[i4] = LittleEndian.getInt(bArr, (i4 * 4) + i3);
        }
    }

    public int getLong(int i) {
        return this._longs[i];
    }

    public void setLong(int i, int i2) {
        this._longs[i] = i2;
    }

    /* access modifiers changed from: package-private */
    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, (short) this._longs.length);
        int i2 = i + 2;
        int i3 = 0;
        while (true) {
            int[] iArr = this._longs;
            if (i3 < iArr.length) {
                LittleEndian.putInt(bArr, i2, iArr[i3]);
                i2 += 4;
                i3++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int sizeInBytes() {
        return (this._longs.length * 4) + 2;
    }
}
