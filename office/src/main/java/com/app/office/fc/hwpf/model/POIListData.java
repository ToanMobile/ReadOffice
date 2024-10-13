package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;

@Internal
public final class POIListData {
    private static BitField _fRestartHdn = BitFieldFactory.getInstance(2);
    private static BitField _fSimpleList = BitFieldFactory.getInstance(1);
    private byte _info;
    POIListLevel[] _levels;
    private int _lsid;
    private byte _reserved;
    private short[] _rgistd = new short[9];
    private int _tplc;

    public POIListData(int i, boolean z) {
        this._lsid = i;
        int i2 = 0;
        for (int i3 = 0; i3 < 9; i3++) {
            this._rgistd[i3] = 4095;
        }
        this._levels = new POIListLevel[9];
        while (true) {
            POIListLevel[] pOIListLevelArr = this._levels;
            if (i2 < pOIListLevelArr.length) {
                pOIListLevelArr[i2] = new POIListLevel(i2, z);
                i2++;
            } else {
                return;
            }
        }
    }

    POIListData(byte[] bArr, int i) {
        this._lsid = LittleEndian.getInt(bArr, i);
        int i2 = i + 4;
        this._tplc = LittleEndian.getInt(bArr, i2);
        int i3 = i2 + 4;
        for (int i4 = 0; i4 < 9; i4++) {
            this._rgistd[i4] = LittleEndian.getShort(bArr, i3);
            i3 += 2;
        }
        int i5 = i3 + 1;
        byte b = bArr[i3];
        this._info = b;
        this._reserved = bArr[i5];
        if (_fSimpleList.getValue(b) > 0) {
            this._levels = new POIListLevel[1];
        } else {
            this._levels = new POIListLevel[9];
        }
    }

    public int getLsid() {
        return this._lsid;
    }

    public int numLevels() {
        return this._levels.length;
    }

    public void setLevel(int i, POIListLevel pOIListLevel) {
        this._levels[i] = pOIListLevel;
    }

    public POIListLevel[] getLevels() {
        return this._levels;
    }

    public POIListLevel getLevel(int i) {
        return this._levels[i - 1];
    }

    public int getLevelStyle(int i) {
        return this._rgistd[i];
    }

    public void setLevelStyle(int i, int i2) {
        this._rgistd[i] = (short) i2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        POIListData pOIListData = (POIListData) obj;
        if (pOIListData._info == this._info && Arrays.equals(pOIListData._levels, this._levels) && pOIListData._lsid == this._lsid && pOIListData._reserved == this._reserved && pOIListData._tplc == this._tplc && Arrays.equals(pOIListData._rgistd, this._rgistd)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public int resetListID() {
        int random = (int) (Math.random() * ((double) System.currentTimeMillis()));
        this._lsid = random;
        return random;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[28];
        LittleEndian.putInt(bArr, this._lsid);
        LittleEndian.putInt(bArr, 4, this._tplc);
        int i = 8;
        for (int i2 = 0; i2 < 9; i2++) {
            LittleEndian.putShort(bArr, i, this._rgistd[i2]);
            i += 2;
        }
        bArr[i] = this._info;
        bArr[i + 1] = this._reserved;
        return bArr;
    }
}
