package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public final class SectionDescriptor {
    private int fcMpr;
    private int fcSepx;
    private short fn;
    private short fnMpr;

    public SectionDescriptor() {
    }

    public SectionDescriptor(byte[] bArr, int i) {
        this.fn = LittleEndian.getShort(bArr, i);
        int i2 = i + 2;
        this.fcSepx = LittleEndian.getInt(bArr, i2);
        int i3 = i2 + 4;
        this.fnMpr = LittleEndian.getShort(bArr, i3);
        this.fcMpr = LittleEndian.getInt(bArr, i3 + 2);
    }

    public int getFc() {
        return this.fcSepx;
    }

    public void setFc(int i) {
        this.fcSepx = i;
    }

    public boolean equals(Object obj) {
        SectionDescriptor sectionDescriptor = (SectionDescriptor) obj;
        return sectionDescriptor.fn == this.fn && sectionDescriptor.fnMpr == this.fnMpr;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[12];
        LittleEndian.putShort(bArr, 0, this.fn);
        LittleEndian.putInt(bArr, 2, this.fcSepx);
        LittleEndian.putShort(bArr, 6, this.fnMpr);
        LittleEndian.putInt(bArr, 8, this.fcMpr);
        return bArr;
    }

    public String toString() {
        return "[SED] (fn: " + this.fn + "; fcSepx: " + this.fcSepx + "; fnMpr: " + this.fnMpr + "; fcMpr: " + this.fcMpr + ")";
    }
}
