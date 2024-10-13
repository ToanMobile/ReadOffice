package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.HRESIAbstractType;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public final class Hyphenation extends HRESIAbstractType implements Cloneable {
    public Hyphenation() {
    }

    public Hyphenation(short s) {
        byte[] bArr = new byte[2];
        LittleEndian.putShort(bArr, s);
        fillFields(bArr, 0);
    }

    public Hyphenation clone() {
        try {
            return (Hyphenation) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Hyphenation hyphenation = (Hyphenation) obj;
        return this.field_1_hres == hyphenation.field_1_hres && this.field_2_chHres == hyphenation.field_2_chHres;
    }

    public short getValue() {
        byte[] bArr = new byte[2];
        serialize(bArr, 0);
        return LittleEndian.getShort(bArr);
    }

    public int hashCode() {
        return ((this.field_1_hres + 31) * 31) + this.field_2_chHres;
    }

    public boolean isEmpty() {
        return this.field_1_hres == 0 && this.field_2_chHres == 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "[HRESI] EMPTY";
        }
        return super.toString();
    }
}
