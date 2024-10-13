package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.LittleEndian;

public class Tbkd {
    private short dcpDepend;
    private boolean fMarkDelete;
    private boolean fTextOverflow;
    private boolean fUnk;
    private short itxbxs;

    public static int getSize() {
        return 6;
    }

    public Tbkd(byte[] bArr, int i) {
        this.itxbxs = LittleEndian.getShort(bArr, i);
        this.dcpDepend = LittleEndian.getShort(bArr, i + 2);
        short s = LittleEndian.getShort(bArr, i + 4);
        boolean z = true;
        this.fMarkDelete = (s & 32) != 0;
        this.fUnk = (s & 16) != 0;
        this.fTextOverflow = (s & 8) == 0 ? false : z;
    }

    public int getTxbxIndex() {
        return this.itxbxs;
    }
}
