package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.LittleEndian;

public class PlcfTxbxBkd {
    private int[] CPs;
    private Tbkd[] tbkds;

    public PlcfTxbxBkd(byte[] bArr, int i, int i2) {
        int size = Tbkd.getSize();
        int i3 = (i2 - 4) / (size + 4);
        int i4 = (i2 - (i3 * size)) / 4;
        this.CPs = new int[i4];
        this.tbkds = new Tbkd[i3];
        for (int i5 = 0; i5 < i4; i5++) {
            this.CPs[i5] = LittleEndian.getUShort(bArr, i);
            i += 4;
        }
        for (int i6 = 0; i6 < i3; i6++) {
            this.tbkds[i6] = new Tbkd(bArr, i);
            i += size;
        }
    }

    public int[] getCharPositions() {
        return this.CPs;
    }

    public int getCharPosition(int i) {
        int[] iArr = this.CPs;
        if (iArr == null || iArr.length <= i) {
            return -1;
        }
        return iArr[i];
    }

    public Tbkd[] getTbkds() {
        return this.tbkds;
    }
}
