package com.app.office.fc.hwpf.usermodel;

import androidx.core.view.MotionEventCompat;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndian;

public final class BorderCode implements Cloneable {
    public static final int SIZE = 4;
    private static final BitField _brcType = BitFieldFactory.getInstance(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    private static final BitField _dptLineWidth = BitFieldFactory.getInstance(255);
    private static final BitField _dptSpace = BitFieldFactory.getInstance(7936);
    private static final BitField _fFrame = BitFieldFactory.getInstance(16384);
    private static final BitField _fShadow = BitFieldFactory.getInstance(8192);
    private static final BitField _ico = BitFieldFactory.getInstance(255);
    private short _info;
    private short _info2;

    public BorderCode() {
    }

    public BorderCode(byte[] bArr, int i) {
        this._info = LittleEndian.getShort(bArr, i);
        this._info2 = LittleEndian.getShort(bArr, i + 2);
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, this._info);
        LittleEndian.putShort(bArr, i + 2, this._info2);
    }

    public int toInt() {
        byte[] bArr = new byte[4];
        serialize(bArr, 0);
        return LittleEndian.getInt(bArr);
    }

    public boolean isEmpty() {
        return this._info == 0 && this._info2 == 0;
    }

    public boolean equals(Object obj) {
        BorderCode borderCode = (BorderCode) obj;
        return this._info == borderCode._info && this._info2 == borderCode._info2;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getLineWidth() {
        return _dptLineWidth.getShortValue(this._info);
    }

    public void setLineWidth(int i) {
        _dptLineWidth.setValue(this._info, i);
    }

    public int getBorderType() {
        return _brcType.getShortValue(this._info);
    }

    public void setBorderType(int i) {
        _brcType.setValue(this._info, i);
    }

    public short getColor() {
        return _ico.getShortValue(this._info2);
    }

    public void setColor(short s) {
        _ico.setValue(this._info2, s);
    }

    public int getSpace() {
        return _dptSpace.getShortValue(this._info2);
    }

    public void setSpace(int i) {
        _dptSpace.setValue(this._info2, i);
    }

    public boolean isShadow() {
        return _fShadow.getValue(this._info2) != 0;
    }

    public void setShadow(boolean z) {
        _fShadow.setValue(this._info2, z ? 1 : 0);
    }

    public boolean isFrame() {
        return _fFrame.getValue(this._info2) != 0;
    }

    public void setFrame(boolean z) {
        _fFrame.setValue(this._info2, z ? 1 : 0);
    }

    public String toString() {
        if (isEmpty()) {
            return "[BRC] EMPTY";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BRC]\n");
        stringBuffer.append("        .dptLineWidth         = ");
        stringBuffer.append(" (");
        stringBuffer.append(getLineWidth());
        stringBuffer.append(" )\n");
        stringBuffer.append("        .brcType              = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBorderType());
        stringBuffer.append(" )\n");
        stringBuffer.append("        .ico                  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getColor());
        stringBuffer.append(" )\n");
        stringBuffer.append("        .dptSpace             = ");
        stringBuffer.append(" (");
        stringBuffer.append(getSpace());
        stringBuffer.append(" )\n");
        stringBuffer.append("        .fShadow              = ");
        stringBuffer.append(" (");
        stringBuffer.append(isShadow());
        stringBuffer.append(" )\n");
        stringBuffer.append("        .fFrame               = ");
        stringBuffer.append(" (");
        stringBuffer.append(isFrame());
        stringBuffer.append(" )\n");
        return stringBuffer.toString();
    }
}
