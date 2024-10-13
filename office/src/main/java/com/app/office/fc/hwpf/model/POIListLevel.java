package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.sprm.SprmIterator;
import com.app.office.fc.hwpf.sprm.SprmOperation;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;
import kotlin.text.Typography;

@Internal
public final class POIListLevel {
    private static final int RGBXCH_NUMS_SIZE = 9;
    private static BitField _fLegal;
    private static BitField _fNoRestart;
    private static BitField _fPrev;
    private static BitField _fPrevSpace;
    private static BitField _fWord6;
    private static BitField _jc = BitFieldFactory.getInstance(1);
    private int _cbGrpprlChpx;
    private int _cbGrpprlPapx;
    private int _dxaIndent;
    private int _dxaSpace;
    private byte[] _grpprlChpx;
    private byte[] _grpprlPapx;
    private int _iStartAt;
    private byte _info;
    private byte _ixchFollow;
    private byte _nfc;
    private char[] _numberText;
    private short _reserved;
    private byte[] _rgbxchNums;
    private int _speecialIndent;

    public POIListLevel(byte[] bArr, int i) {
        this._numberText = null;
        this._iStartAt = LittleEndian.getInt(bArr, i);
        int i2 = i + 4;
        int i3 = i2 + 1;
        this._nfc = bArr[i2];
        int i4 = i3 + 1;
        this._info = bArr[i3];
        byte[] bArr2 = new byte[9];
        this._rgbxchNums = bArr2;
        System.arraycopy(bArr, i4, bArr2, 0, 9);
        int i5 = i4 + 9;
        int i6 = i5 + 1;
        this._ixchFollow = bArr[i5];
        this._dxaSpace = LittleEndian.getInt(bArr, i6);
        int i7 = i6 + 4;
        this._dxaIndent = LittleEndian.getInt(bArr, i7);
        int i8 = i7 + 4;
        int i9 = i8 + 1;
        this._cbGrpprlChpx = LittleEndian.getUnsignedByte(bArr, i8);
        int i10 = i9 + 1;
        this._cbGrpprlPapx = LittleEndian.getUnsignedByte(bArr, i9);
        this._reserved = LittleEndian.getShort(bArr, i10);
        int i11 = i10 + 2;
        int i12 = this._cbGrpprlPapx;
        byte[] bArr3 = new byte[i12];
        this._grpprlPapx = bArr3;
        this._grpprlChpx = new byte[this._cbGrpprlChpx];
        System.arraycopy(bArr, i11, bArr3, 0, i12);
        int i13 = i11 + this._cbGrpprlPapx;
        System.arraycopy(bArr, i13, this._grpprlChpx, 0, this._cbGrpprlChpx);
        int i14 = i13 + this._cbGrpprlChpx;
        int i15 = LittleEndian.getShort(bArr, i14);
        if (i15 > 0) {
            this._numberText = new char[i15];
            int i16 = i14 + 2;
            for (int i17 = 0; i17 < i15 && i16 < bArr.length; i17++) {
                this._numberText[i17] = (char) LittleEndian.getShort(bArr, i16);
                i16 += 2;
            }
        }
        SprmIterator sprmIterator = new SprmIterator(this._grpprlPapx, 0);
        while (sprmIterator.hasNext()) {
            SprmOperation next = sprmIterator.next();
            if (next.getType() == 1) {
                int operation = next.getOperation();
                if (operation == 15) {
                    this._dxaIndent = next.getOperand();
                } else if (operation == 17) {
                    this._speecialIndent = next.getOperand();
                }
            }
        }
    }

    public POIListLevel(int i, boolean z) {
        this._numberText = null;
        this._iStartAt = 1;
        this._grpprlPapx = new byte[0];
        this._grpprlChpx = new byte[0];
        this._numberText = new char[0];
        byte[] bArr = new byte[9];
        this._rgbxchNums = bArr;
        if (z) {
            bArr[0] = 1;
            this._numberText = new char[]{(char) i, '.'};
            return;
        }
        this._numberText = new char[]{Typography.bullet};
    }

    public POIListLevel(int i, int i2, int i3, byte[] bArr, byte[] bArr2, String str) {
        this._numberText = null;
        this._iStartAt = i;
        this._nfc = (byte) i2;
        _jc.setValue(this._info, i3);
        this._grpprlChpx = bArr;
        this._grpprlPapx = bArr2;
        this._numberText = str.toCharArray();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        POIListLevel pOIListLevel = (POIListLevel) obj;
        if (this._cbGrpprlChpx == pOIListLevel._cbGrpprlChpx && pOIListLevel._cbGrpprlPapx == this._cbGrpprlPapx && pOIListLevel._dxaIndent == this._dxaIndent && pOIListLevel._dxaSpace == this._dxaSpace && Arrays.equals(pOIListLevel._grpprlChpx, this._grpprlChpx) && Arrays.equals(pOIListLevel._grpprlPapx, this._grpprlPapx) && pOIListLevel._info == this._info && pOIListLevel._iStartAt == this._iStartAt && pOIListLevel._ixchFollow == this._ixchFollow && pOIListLevel._nfc == this._nfc && Arrays.equals(pOIListLevel._numberText, this._numberText) && Arrays.equals(pOIListLevel._rgbxchNums, this._rgbxchNums) && pOIListLevel._reserved == this._reserved) {
            return true;
        }
        return false;
    }

    public int getAlignment() {
        return _jc.getValue(this._info);
    }

    public byte[] getLevelProperties() {
        return this._grpprlPapx;
    }

    public int getNumberFormat() {
        return this._nfc;
    }

    public String getNumberText() {
        if (this._numberText == null) {
            return null;
        }
        return new String(this._numberText);
    }

    public char[] getNumberChar() {
        return this._numberText;
    }

    public int getTextIndent() {
        return this._dxaIndent;
    }

    public int getSpecialIndnet() {
        return this._speecialIndent;
    }

    public int getSizeInBytes() {
        int i = this._cbGrpprlChpx + 28 + this._cbGrpprlPapx + 2;
        char[] cArr = this._numberText;
        return cArr != null ? i + (cArr.length * 2) : i;
    }

    public int getStartAt() {
        return this._iStartAt;
    }

    public byte getTypeOfCharFollowingTheNumber() {
        return this._ixchFollow;
    }

    public void setAlignment(int i) {
        _jc.setValue(this._info, i);
    }

    public void setLevelProperties(byte[] bArr) {
        this._grpprlPapx = bArr;
    }

    public void setNumberFormat(int i) {
        this._nfc = (byte) i;
    }

    public void setNumberProperties(byte[] bArr) {
        this._grpprlChpx = bArr;
    }

    public void setStartAt(int i) {
        this._iStartAt = i;
    }

    public void setTypeOfCharFollowingTheNumber(byte b) {
        this._ixchFollow = b;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[getSizeInBytes()];
        int i = 0;
        LittleEndian.putInt(bArr, 0, this._iStartAt);
        bArr[4] = this._nfc;
        bArr[5] = this._info;
        System.arraycopy(this._rgbxchNums, 0, bArr, 6, 9);
        bArr[15] = this._ixchFollow;
        LittleEndian.putInt(bArr, 16, this._dxaSpace);
        LittleEndian.putInt(bArr, 20, this._dxaIndent);
        bArr[24] = (byte) this._cbGrpprlChpx;
        bArr[25] = (byte) this._cbGrpprlPapx;
        LittleEndian.putShort(bArr, 26, this._reserved);
        System.arraycopy(this._grpprlPapx, 0, bArr, 28, this._cbGrpprlPapx);
        int i2 = 28 + this._cbGrpprlPapx;
        System.arraycopy(this._grpprlChpx, 0, bArr, i2, this._cbGrpprlChpx);
        int i3 = i2 + this._cbGrpprlChpx;
        char[] cArr = this._numberText;
        if (cArr != null) {
            LittleEndian.putUShort(bArr, i3, cArr.length);
            int i4 = i3 + 2;
            while (true) {
                char[] cArr2 = this._numberText;
                if (i >= cArr2.length) {
                    break;
                }
                LittleEndian.putUShort(bArr, i4, cArr2[i]);
                i4 += 2;
                i++;
            }
        } else {
            LittleEndian.putUShort(bArr, i3, 0);
        }
        return bArr;
    }
}
