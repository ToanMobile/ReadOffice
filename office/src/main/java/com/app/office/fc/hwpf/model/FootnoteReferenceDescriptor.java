package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.FRDAbstractType;
import com.app.office.fc.util.Internal;

@Internal
public final class FootnoteReferenceDescriptor extends FRDAbstractType implements Cloneable {
    public FootnoteReferenceDescriptor() {
    }

    public FootnoteReferenceDescriptor(byte[] bArr, int i) {
        fillFields(bArr, i);
    }

    /* access modifiers changed from: protected */
    public FootnoteReferenceDescriptor clone() {
        try {
            return (FootnoteReferenceDescriptor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.field_1_nAuto == ((FootnoteReferenceDescriptor) obj).field_1_nAuto;
    }

    public int hashCode() {
        return 31 + this.field_1_nAuto;
    }

    public boolean isEmpty() {
        return this.field_1_nAuto == 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "[FRD] EMPTY";
        }
        return super.toString();
    }
}
