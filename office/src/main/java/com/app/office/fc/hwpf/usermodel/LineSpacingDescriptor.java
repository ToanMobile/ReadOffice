package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.util.LittleEndian;

public final class LineSpacingDescriptor implements Cloneable {
    short _dyaLine;
    short _fMultiLinespace;

    public LineSpacingDescriptor() {
        this._dyaLine = EscherProperties.GEOTEXT__REVERSEROWORDER;
        this._fMultiLinespace = 1;
    }

    public LineSpacingDescriptor(byte[] bArr, int i) {
        this._dyaLine = LittleEndian.getShort(bArr, i);
        this._fMultiLinespace = LittleEndian.getShort(bArr, i + 2);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public short getDyaLine() {
        return this._dyaLine;
    }

    public short getMultiLinespace() {
        return this._fMultiLinespace;
    }

    public void setMultiLinespace(short s) {
        this._fMultiLinespace = s;
    }

    public int toInt() {
        byte[] bArr = new byte[4];
        serialize(bArr, 0);
        return LittleEndian.getInt(bArr);
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, this._dyaLine);
        LittleEndian.putShort(bArr, i + 2, this._fMultiLinespace);
    }

    public void setDyaLine(short s) {
        this._dyaLine = s;
    }

    public boolean equals(Object obj) {
        LineSpacingDescriptor lineSpacingDescriptor = (LineSpacingDescriptor) obj;
        return this._dyaLine == lineSpacingDescriptor._dyaLine && this._fMultiLinespace == lineSpacingDescriptor._fMultiLinespace;
    }

    public boolean isEmpty() {
        return this._dyaLine == 0 && this._fMultiLinespace == 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "[LSPD] EMPTY";
        }
        return "[LSPD] (dyaLine: " + this._dyaLine + "; fMultLinespace: " + this._fMultiLinespace + ")";
    }
}
