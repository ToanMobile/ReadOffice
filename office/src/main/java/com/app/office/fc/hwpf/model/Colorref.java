package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public class Colorref implements Cloneable {
    private int value;

    public Colorref() {
        this.value = -1;
    }

    public Colorref(byte[] bArr, int i) {
        this.value = LittleEndian.getInt(bArr, i);
    }

    public Colorref(int i) {
        this.value = i;
    }

    public Colorref clone() throws CloneNotSupportedException {
        return new Colorref(this.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((Colorref) obj).value;
    }

    public int getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean isEmpty() {
        return this.value == -1;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public byte[] toByteArray() {
        if (!isEmpty()) {
            byte[] bArr = new byte[4];
            LittleEndian.putInt(bArr, 0, this.value);
            return bArr;
        }
        throw new IllegalStateException("Structure state (EMPTY) is not good for serialization");
    }

    public String toString() {
        if (isEmpty()) {
            return "[COLORREF] EMPTY";
        }
        return "[COLORREF] 0x" + Integer.toHexString(this.value);
    }
}
