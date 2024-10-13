package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.types.TLPAbstractType;

public class TableAutoformatLookSpecifier extends TLPAbstractType implements Cloneable {
    public static final int SIZE = 4;

    public TableAutoformatLookSpecifier() {
    }

    public TableAutoformatLookSpecifier(byte[] bArr, int i) {
        fillFields(bArr, i);
    }

    public TableAutoformatLookSpecifier clone() {
        try {
            return (TableAutoformatLookSpecifier) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e.getMessage(), e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TableAutoformatLookSpecifier tableAutoformatLookSpecifier = (TableAutoformatLookSpecifier) obj;
        return this.field_1_itl == tableAutoformatLookSpecifier.field_1_itl && this.field_2_tlp_flags == tableAutoformatLookSpecifier.field_2_tlp_flags;
    }

    public int hashCode() {
        return ((this.field_1_itl + 31) * 31) + this.field_2_tlp_flags;
    }

    public boolean isEmpty() {
        return this.field_1_itl == 0 && this.field_2_tlp_flags == 0;
    }
}
