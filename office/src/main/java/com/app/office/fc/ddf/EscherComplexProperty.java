package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;

public class EscherComplexProperty extends EscherProperty {
    protected byte[] _complexData;

    public EscherComplexProperty(short s, byte[] bArr) {
        super(s);
        this._complexData = bArr;
    }

    public EscherComplexProperty(short s, boolean z, byte[] bArr) {
        super(s, true, z);
        this._complexData = bArr;
    }

    public int serializeSimplePart(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, getId());
        LittleEndian.putInt(bArr, i + 2, this._complexData.length);
        return 6;
    }

    public int serializeComplexPart(byte[] bArr, int i) {
        byte[] bArr2 = this._complexData;
        System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
        return this._complexData.length;
    }

    public byte[] getComplexData() {
        return this._complexData;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EscherComplexProperty) && Arrays.equals(this._complexData, ((EscherComplexProperty) obj)._complexData);
    }

    public int getPropertySize() {
        return this._complexData.length + 6;
    }

    public int hashCode() {
        return getId() * 11;
    }

    public String toString() {
        String hex = HexDump.toHex(this._complexData, 32);
        return "propNum: " + getPropertyNumber() + ", propName: " + EscherProperties.getPropertyName(getPropertyNumber()) + ", complex: " + isComplex() + ", blipId: " + isBlipId() + ", data: " + System.getProperty("line.separator") + hex;
    }
}
