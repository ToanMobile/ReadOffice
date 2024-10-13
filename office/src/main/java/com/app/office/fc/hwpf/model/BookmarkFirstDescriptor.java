package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.BKFAbstractType;
import com.app.office.fc.util.Internal;

@Internal
public final class BookmarkFirstDescriptor extends BKFAbstractType implements Cloneable {
    public BookmarkFirstDescriptor() {
    }

    public BookmarkFirstDescriptor(byte[] bArr, int i) {
        fillFields(bArr, i);
    }

    /* access modifiers changed from: protected */
    public BookmarkFirstDescriptor clone() {
        try {
            return (BookmarkFirstDescriptor) super.clone();
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
        BookmarkFirstDescriptor bookmarkFirstDescriptor = (BookmarkFirstDescriptor) obj;
        return this.field_1_ibkl == bookmarkFirstDescriptor.field_1_ibkl && this.field_2_bkf_flags == bookmarkFirstDescriptor.field_2_bkf_flags;
    }

    public int hashCode() {
        return ((this.field_1_ibkl + 31) * 31) + this.field_2_bkf_flags;
    }

    public boolean isEmpty() {
        return this.field_1_ibkl == 0 && this.field_2_bkf_flags == 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "[BKF] EMPTY";
        }
        return super.toString();
    }
}
