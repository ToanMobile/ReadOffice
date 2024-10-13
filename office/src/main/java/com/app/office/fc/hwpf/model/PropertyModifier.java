package com.app.office.fc.hwpf.model;

import androidx.core.view.MotionEventCompat;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;

@Internal
public final class PropertyModifier implements Cloneable {
    private static BitField _fComplex = new BitField(1);
    private static BitField _figrpprl = new BitField(65534);
    private static BitField _fisprm = new BitField(TIFFConstants.TIFFTAG_SUBFILETYPE);
    private static BitField _fval = new BitField(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    private short value;

    public PropertyModifier(short s) {
        this.value = s;
    }

    /* access modifiers changed from: protected */
    public PropertyModifier clone() throws CloneNotSupportedException {
        return new PropertyModifier(this.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((PropertyModifier) obj).value;
    }

    public short getIgrpprl() {
        if (isComplex()) {
            return _figrpprl.getShortValue(this.value);
        }
        throw new IllegalStateException("Not complex");
    }

    public short getIsprm() {
        if (!isComplex()) {
            return _fisprm.getShortValue(this.value);
        }
        throw new IllegalStateException("Not simple");
    }

    public short getVal() {
        if (!isComplex()) {
            return _fval.getShortValue(this.value);
        }
        throw new IllegalStateException("Not simple");
    }

    public short getValue() {
        return this.value;
    }

    public int hashCode() {
        return 31 + this.value;
    }

    public boolean isComplex() {
        return _fComplex.isSet(this.value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[PRM] (complex: ");
        sb.append(isComplex());
        sb.append("; ");
        if (isComplex()) {
            sb.append("igrpprl: ");
            sb.append(getIgrpprl());
            sb.append("; ");
        } else {
            sb.append("isprm: ");
            sb.append(getIsprm());
            sb.append("; ");
            sb.append("val: ");
            sb.append(getVal());
            sb.append("; ");
        }
        sb.append(")");
        return sb.toString();
    }
}
