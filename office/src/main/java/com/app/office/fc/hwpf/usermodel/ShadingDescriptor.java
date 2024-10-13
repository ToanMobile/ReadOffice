package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndian;

public final class ShadingDescriptor implements Cloneable {
    public static final int SIZE = 2;
    private static final BitField _icoBack = BitFieldFactory.getInstance(992);
    private static final BitField _icoFore = BitFieldFactory.getInstance(31);
    private static final BitField _ipat = BitFieldFactory.getInstance(64512);
    private short _info;

    public ShadingDescriptor() {
    }

    public ShadingDescriptor(byte[] bArr, int i) {
        this(LittleEndian.getShort(bArr, i));
    }

    public ShadingDescriptor(short s) {
        this._info = s;
    }

    public short toShort() {
        return this._info;
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, this._info);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isEmpty() {
        return this._info == 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "[SHD] EMPTY";
        }
        return "[SHD] (cvFore: " + _icoFore.getShortValue(this._info) + "; cvBack: " + _icoBack.getShortValue(this._info) + "; iPat: " + _ipat.getShortValue(this._info) + ")";
    }
}
