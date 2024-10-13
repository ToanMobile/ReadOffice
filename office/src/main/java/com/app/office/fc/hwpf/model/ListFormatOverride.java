package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.LFOAbstractType;
import com.app.office.fc.util.Internal;

@Internal
public final class ListFormatOverride extends LFOAbstractType {
    private ListFormatOverrideLevel[] _levelOverrides = new ListFormatOverrideLevel[0];

    public ListFormatOverride(byte[] bArr, int i) {
        fillFields(bArr, i);
    }

    public ListFormatOverride(int i) {
        setLsid(i);
    }

    public ListFormatOverrideLevel[] getLevelOverrides() {
        return this._levelOverrides;
    }

    public ListFormatOverrideLevel getOverrideLevel(int i) {
        ListFormatOverrideLevel listFormatOverrideLevel = null;
        int i2 = 0;
        while (true) {
            ListFormatOverrideLevel[] listFormatOverrideLevelArr = this._levelOverrides;
            if (i2 >= listFormatOverrideLevelArr.length) {
                return listFormatOverrideLevel;
            }
            if (listFormatOverrideLevelArr[i2] != null && listFormatOverrideLevelArr[i2].getLevelNum() == i) {
                listFormatOverrideLevel = this._levelOverrides[i2];
            }
            i2++;
        }
    }

    public int numOverrides() {
        return getClfolvl();
    }

    public void setOverride(int i, ListFormatOverrideLevel listFormatOverrideLevel) {
        this._levelOverrides[i] = listFormatOverrideLevel;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[getSize()];
        serialize(bArr, 0);
        return bArr;
    }
}
