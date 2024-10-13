package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;

@Internal
public final class Ffn {
    private static BitField _fTrueType = BitFieldFactory.getInstance(4);
    private static BitField _ff = BitFieldFactory.getInstance(112);
    private static BitField _prq = BitFieldFactory.getInstance(3);
    private int _cbFfnM1;
    private byte _chs;
    private byte[] _fontSig = new byte[24];
    private byte _info;
    private byte _ixchSzAlt;
    private byte[] _panose = new byte[10];
    private short _wWeight;
    private char[] _xszFfn;
    private int _xszFfnLength;

    public Ffn(byte[] bArr, int i) {
        this._cbFfnM1 = LittleEndian.getUnsignedByte(bArr, i);
        int i2 = i + 1;
        this._info = bArr[i2];
        int i3 = i2 + 1;
        this._wWeight = LittleEndian.getShort(bArr, i3);
        int i4 = i3 + 2;
        this._chs = bArr[i4];
        int i5 = i4 + 1;
        this._ixchSzAlt = bArr[i5];
        int i6 = i5 + 1;
        byte[] bArr2 = this._panose;
        System.arraycopy(bArr, i6, bArr2, 0, bArr2.length);
        int length = i6 + this._panose.length;
        byte[] bArr3 = this._fontSig;
        System.arraycopy(bArr, length, bArr3, 0, bArr3.length);
        int length2 = length + this._fontSig.length;
        int size = (getSize() - (length2 - i)) / 2;
        this._xszFfnLength = size;
        this._xszFfn = new char[size];
        for (int i7 = 0; i7 < this._xszFfnLength; i7++) {
            this._xszFfn[i7] = (char) LittleEndian.getShort(bArr, length2);
            length2 += 2;
        }
    }

    public int get_cbFfnM1() {
        return this._cbFfnM1;
    }

    public short getWeight() {
        return this._wWeight;
    }

    public byte getChs() {
        return this._chs;
    }

    public byte[] getPanose() {
        return this._panose;
    }

    public byte[] getFontSig() {
        return this._fontSig;
    }

    public int getSize() {
        return this._cbFfnM1 + 1;
    }

    public String getMainFontName() {
        int i = 0;
        while (i < this._xszFfnLength && this._xszFfn[i] != 0) {
            i++;
        }
        return new String(this._xszFfn, 0, i);
    }

    public String getAltFontName() {
        int i = this._ixchSzAlt;
        while (i < this._xszFfnLength && this._xszFfn[i] != 0) {
            i++;
        }
        return new String(this._xszFfn, this._ixchSzAlt, i);
    }

    public void set_cbFfnM1(int i) {
        this._cbFfnM1 = i;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[getSize()];
        int i = 0;
        bArr[0] = (byte) this._cbFfnM1;
        bArr[1] = this._info;
        LittleEndian.putShort(bArr, 2, this._wWeight);
        bArr[4] = this._chs;
        bArr[5] = this._ixchSzAlt;
        byte[] bArr2 = this._panose;
        System.arraycopy(bArr2, 0, bArr, 6, bArr2.length);
        int length = 6 + this._panose.length;
        byte[] bArr3 = this._fontSig;
        System.arraycopy(bArr3, 0, bArr, length, bArr3.length);
        int length2 = length + this._fontSig.length;
        while (true) {
            char[] cArr = this._xszFfn;
            if (i >= cArr.length) {
                return bArr;
            }
            LittleEndian.putShort(bArr, length2, (short) cArr[i]);
            length2 += 2;
            i++;
        }
    }

    public boolean equals(Object obj) {
        Ffn ffn = (Ffn) obj;
        if (ffn.get_cbFfnM1() == this._cbFfnM1 && ffn._info == this._info && ffn._wWeight == this._wWeight && ffn._chs == this._chs && ffn._ixchSzAlt == this._ixchSzAlt && Arrays.equals(ffn._panose, this._panose) && Arrays.equals(ffn._fontSig, this._fontSig) && Arrays.equals(ffn._xszFfn, this._xszFfn)) {
            return true;
        }
        return false;
    }
}
