package com.app.office.fc.hwpf.usermodel;

import com.app.office.common.shape.ShapeTypes;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndian;

public final class DropCapSpecifier implements Cloneable {
    private static BitField _lines = BitFieldFactory.getInstance(ShapeTypes.Curve);
    private static BitField _type = BitFieldFactory.getInstance(7);
    private short _fdct;

    public DropCapSpecifier() {
        this._fdct = 0;
    }

    public DropCapSpecifier(byte[] bArr, int i) {
        this(LittleEndian.getShort(bArr, i));
    }

    public DropCapSpecifier(short s) {
        this._fdct = s;
    }

    public DropCapSpecifier clone() {
        return new DropCapSpecifier(this._fdct);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this._fdct == ((DropCapSpecifier) obj)._fdct;
    }

    public byte getCountOfLinesToDrop() {
        return (byte) _lines.getValue(this._fdct);
    }

    public byte getDropCapType() {
        return (byte) _type.getValue(this._fdct);
    }

    public int hashCode() {
        return this._fdct;
    }

    public boolean isEmpty() {
        return this._fdct == 0;
    }

    public void setCountOfLinesToDrop(byte b) {
        this._fdct = (short) _lines.setValue(this._fdct, b);
    }

    public void setDropCapType(byte b) {
        this._fdct = (short) _type.setValue(this._fdct, b);
    }

    public short toShort() {
        return this._fdct;
    }

    public String toString() {
        if (isEmpty()) {
            return "[DCS] EMPTY";
        }
        return "[DCS] (type: " + getDropCapType() + "; count: " + getCountOfLinesToDrop() + ")";
    }
}
