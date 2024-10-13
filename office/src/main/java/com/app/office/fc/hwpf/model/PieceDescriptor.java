package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public final class PieceDescriptor {
    short descriptor;
    int fc;
    PropertyModifier prm;
    boolean unicode;

    public static int getSizeInBytes() {
        return 8;
    }

    public PieceDescriptor(byte[] bArr, int i) {
        this.descriptor = LittleEndian.getShort(bArr, i);
        int i2 = i + 2;
        this.fc = LittleEndian.getInt(bArr, i2);
        this.prm = new PropertyModifier(LittleEndian.getShort(bArr, i2 + 4));
        int i3 = this.fc;
        if ((1073741824 & i3) == 0) {
            this.unicode = true;
            return;
        }
        this.unicode = false;
        int i4 = i3 & -1073741825;
        this.fc = i4;
        this.fc = i4 / 2;
    }

    public int getFilePosition() {
        return this.fc;
    }

    public void setFilePosition(int i) {
        this.fc = i;
    }

    public boolean isUnicode() {
        return this.unicode;
    }

    public PropertyModifier getPrm() {
        return this.prm;
    }

    /* access modifiers changed from: protected */
    public byte[] toByteArray() {
        int i = this.fc;
        if (!this.unicode) {
            i = (i * 2) | 1073741824;
        }
        byte[] bArr = new byte[8];
        LittleEndian.putShort(bArr, 0, this.descriptor);
        LittleEndian.putInt(bArr, 2, i);
        LittleEndian.putShort(bArr, 6, this.prm.getValue());
        return bArr;
    }

    public int hashCode() {
        int i = (this.descriptor + 31) * 31;
        PropertyModifier propertyModifier = this.prm;
        return ((i + (propertyModifier == null ? 0 : propertyModifier.hashCode())) * 31) + (this.unicode ? 1231 : 1237);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PieceDescriptor pieceDescriptor = (PieceDescriptor) obj;
        if (this.descriptor != pieceDescriptor.descriptor) {
            return false;
        }
        PropertyModifier propertyModifier = this.prm;
        if (propertyModifier == null) {
            if (pieceDescriptor.prm != null) {
                return false;
            }
        } else if (!propertyModifier.equals(pieceDescriptor.prm)) {
            return false;
        }
        return this.unicode == pieceDescriptor.unicode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PieceDescriptor (pos: ");
        sb.append(getFilePosition());
        sb.append("; ");
        sb.append(isUnicode() ? "unicode" : "non-unicode");
        sb.append("; prm: ");
        sb.append(getPrm());
        sb.append(")");
        return sb.toString();
    }
}
