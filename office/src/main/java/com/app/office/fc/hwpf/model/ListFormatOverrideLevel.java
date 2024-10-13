package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;

@Internal
public final class ListFormatOverrideLevel {
    private static final int BASE_SIZE = 8;
    private static BitField _fFormatting = BitFieldFactory.getInstance(32);
    private static BitField _fStartAt = BitFieldFactory.getInstance(16);
    private static BitField _ilvl = BitFieldFactory.getInstance(15);
    int _iStartAt;
    byte _info;
    POIListLevel _lvl;
    byte[] _reserved = new byte[3];

    public ListFormatOverrideLevel(byte[] bArr, int i) {
        this._iStartAt = LittleEndian.getInt(bArr, i);
        int i2 = i + 4;
        int i3 = i2 + 1;
        this._info = bArr[i2];
        byte[] bArr2 = this._reserved;
        System.arraycopy(bArr, i3, bArr2, 0, bArr2.length);
        int length = i3 + this._reserved.length;
        if (_fFormatting.getValue(this._info) > 0) {
            this._lvl = new POIListLevel(bArr, length);
        }
    }

    public POIListLevel getLevel() {
        return this._lvl;
    }

    public int getLevelNum() {
        return _ilvl.getValue(this._info);
    }

    public boolean isFormatting() {
        return _fFormatting.getValue(this._info) != 0;
    }

    public boolean isStartAt() {
        return _fStartAt.getValue(this._info) != 0;
    }

    public int getSizeInBytes() {
        POIListLevel pOIListLevel = this._lvl;
        if (pOIListLevel == null) {
            return 8;
        }
        return 8 + pOIListLevel.getSizeInBytes();
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj == null) {
            return false;
        }
        ListFormatOverrideLevel listFormatOverrideLevel = (ListFormatOverrideLevel) obj;
        POIListLevel pOIListLevel = this._lvl;
        if (pOIListLevel != null) {
            z = pOIListLevel.equals(listFormatOverrideLevel._lvl);
        } else {
            z = listFormatOverrideLevel._lvl == null;
        }
        if (!z || listFormatOverrideLevel._iStartAt != this._iStartAt || listFormatOverrideLevel._info != this._info || !Arrays.equals(listFormatOverrideLevel._reserved, this._reserved)) {
            return false;
        }
        return true;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[getSizeInBytes()];
        LittleEndian.putInt(bArr, this._iStartAt);
        bArr[4] = this._info;
        System.arraycopy(this._reserved, 0, bArr, 5, 3);
        POIListLevel pOIListLevel = this._lvl;
        if (pOIListLevel != null) {
            byte[] byteArray = pOIListLevel.toByteArray();
            System.arraycopy(byteArray, 0, bArr, 8, byteArray.length);
        }
        return bArr;
    }

    public int getIStartAt() {
        return this._iStartAt;
    }
}
