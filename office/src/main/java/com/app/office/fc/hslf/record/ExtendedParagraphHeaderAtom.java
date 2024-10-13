package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class ExtendedParagraphHeaderAtom extends RecordAtom {
    private static long _type = 4015;
    private byte[] _header;
    private int refSlideID;
    private int textType;

    public ExtendedParagraphHeaderAtom(byte[] bArr, int i, int i2) {
        i2 = i2 < 8 ? 8 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        if (i2 >= 16) {
            this.refSlideID = LittleEndian.getInt(bArr, i + 8);
            this.textType = LittleEndian.getInt(bArr, i + 12);
        }
    }

    public int getRefSlideID() {
        return this.refSlideID;
    }

    public int getTextType() {
        return this.textType;
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
    }
}
