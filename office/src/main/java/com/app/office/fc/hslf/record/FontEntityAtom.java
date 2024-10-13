package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.UnsupportedEncodingException;

public final class FontEntityAtom extends RecordAtom {
    private byte[] _header;
    private byte[] _recdata;

    protected FontEntityAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._recdata = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public FontEntityAtom() {
        this._recdata = new byte[68];
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._recdata.length);
    }

    public long getRecordType() {
        return (long) RecordTypes.FontEntityAtom.typeID;
    }

    public String getFontName() {
        int i = 0;
        while (i < 64) {
            try {
                byte[] bArr = this._recdata;
                if (bArr[i] == 0 && bArr[i + 1] == 0) {
                    return new String(this._recdata, 0, i, "UTF-16LE");
                }
                i += 2;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return null;
    }

    public void setFontName(String str) {
        if (!str.endsWith("\u0000")) {
            str = str + "\u0000";
        }
        if (str.length() <= 32) {
            try {
                byte[] bytes = str.getBytes("UTF-16LE");
                System.arraycopy(bytes, 0, this._recdata, 0, bytes.length);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("The length of the font name, including null termination, must not exceed 32 characters");
        }
    }

    public void setFontIndex(int i) {
        LittleEndian.putShort(this._header, 0, (short) i);
    }

    public int getFontIndex() {
        return LittleEndian.getShort(this._header, 0) >> 4;
    }

    public void setCharSet(int i) {
        this._recdata[64] = (byte) i;
    }

    public int getCharSet() {
        return this._recdata[64];
    }

    public void setFontFlags(int i) {
        this._recdata[65] = (byte) i;
    }

    public int getFontFlags() {
        return this._recdata[65];
    }

    public void setFontType(int i) {
        this._recdata[66] = (byte) i;
    }

    public int getFontType() {
        return this._recdata[66];
    }

    public void setPitchAndFamily(int i) {
        this._recdata[67] = (byte) i;
    }

    public int getPitchAndFamily() {
        return this._recdata[67];
    }

    public void dispose() {
        this._header = null;
        this._recdata = null;
    }
}
