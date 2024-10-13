package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;

public final class EscherArrayProperty extends EscherComplexProperty {
    private static final int FIXED_SIZE = 6;
    private boolean emptyComplexPart = false;
    private boolean sizeIncludesHeaderSize = true;

    public static int getActualSizeOfElements(short s) {
        return s < 0 ? (short) ((-s) >> 2) : s;
    }

    public EscherArrayProperty(short s, byte[] bArr) {
        super(s, checkComplexData(bArr));
        boolean z = true;
        this.emptyComplexPart = bArr.length != 0 ? false : z;
    }

    public EscherArrayProperty(short s, boolean z, byte[] bArr) {
        super(s, z, checkComplexData(bArr));
    }

    private static byte[] checkComplexData(byte[] bArr) {
        return (bArr == null || bArr.length == 0) ? new byte[6] : bArr;
    }

    public int getNumberOfElementsInArray() {
        this._complexData = checkComplexData(this._complexData);
        return LittleEndian.getUShort(this._complexData, 0);
    }

    public void setNumberOfElementsInArray(int i) {
        int actualSizeOfElements = (getActualSizeOfElements(getSizeOfElements()) * i) + 6;
        if (actualSizeOfElements != this._complexData.length) {
            byte[] bArr = new byte[actualSizeOfElements];
            System.arraycopy(this._complexData, 0, bArr, 0, this._complexData.length);
            this._complexData = bArr;
        }
        LittleEndian.putShort(this._complexData, 0, (short) i);
    }

    public int getNumberOfElementsInMemory() {
        this._complexData = checkComplexData(this._complexData);
        return LittleEndian.getUShort(this._complexData, 2);
    }

    public void setNumberOfElementsInMemory(int i) {
        int actualSizeOfElements = (getActualSizeOfElements(getSizeOfElements()) * i) + 6;
        if (actualSizeOfElements != this._complexData.length) {
            byte[] bArr = new byte[actualSizeOfElements];
            System.arraycopy(this._complexData, 0, bArr, 0, actualSizeOfElements);
            this._complexData = bArr;
        }
        LittleEndian.putShort(this._complexData, 2, (short) i);
    }

    public short getSizeOfElements() {
        this._complexData = checkComplexData(this._complexData);
        return LittleEndian.getShort(this._complexData, 4);
    }

    public void setSizeOfElements(int i) {
        LittleEndian.putShort(this._complexData, 4, (short) i);
        int numberOfElementsInArray = (getNumberOfElementsInArray() * getActualSizeOfElements(getSizeOfElements())) + 6;
        if (numberOfElementsInArray != this._complexData.length) {
            byte[] bArr = new byte[numberOfElementsInArray];
            System.arraycopy(this._complexData, 0, bArr, 0, 6);
            this._complexData = bArr;
        }
    }

    public byte[] getElement(int i) {
        int actualSizeOfElements = getActualSizeOfElements(getSizeOfElements());
        byte[] bArr = new byte[actualSizeOfElements];
        int i2 = (i * actualSizeOfElements) + 6;
        if (i2 + actualSizeOfElements <= this._complexData.length) {
            System.arraycopy(this._complexData, i2, bArr, 0, actualSizeOfElements);
        }
        return bArr;
    }

    public void setElement(int i, byte[] bArr) {
        int actualSizeOfElements = getActualSizeOfElements(getSizeOfElements());
        System.arraycopy(bArr, 0, this._complexData, (i * actualSizeOfElements) + 6, actualSizeOfElements);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("    {EscherArrayProperty:\n");
        stringBuffer.append("     Num Elements: " + getNumberOfElementsInArray() + 10);
        stringBuffer.append("     Num Elements In Memory: " + getNumberOfElementsInMemory() + 10);
        stringBuffer.append("     Size of elements: " + getSizeOfElements() + 10);
        for (int i = 0; i < getNumberOfElementsInArray(); i++) {
            stringBuffer.append("     Element " + i + ": " + HexDump.toHex(getElement(i)) + 10);
        }
        stringBuffer.append("}\n");
        return "propNum: " + getPropertyNumber() + ", propName: " + EscherProperties.getPropertyName(getPropertyNumber()) + ", complex: " + isComplex() + ", blipId: " + isBlipId() + ", data: " + 10 + stringBuffer.toString();
    }

    public int setArrayData(byte[] bArr, int i) {
        if (this.emptyComplexPart) {
            this._complexData = new byte[0];
        } else {
            short s = LittleEndian.getShort(bArr, i);
            LittleEndian.getShort(bArr, i + 2);
            int actualSizeOfElements = getActualSizeOfElements(LittleEndian.getShort(bArr, i + 4)) * s;
            if (actualSizeOfElements == this._complexData.length) {
                this._complexData = new byte[(actualSizeOfElements + 6)];
                this.sizeIncludesHeaderSize = false;
            }
            System.arraycopy(bArr, i, this._complexData, 0, this._complexData.length);
        }
        return this._complexData.length;
    }

    public int serializeSimplePart(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, getId());
        int length = this._complexData.length;
        if (!this.sizeIncludesHeaderSize) {
            length -= 6;
        }
        LittleEndian.putInt(bArr, i + 2, length);
        return 6;
    }
}
