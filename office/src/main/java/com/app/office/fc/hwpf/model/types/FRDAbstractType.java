package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public abstract class FRDAbstractType {
    protected short field_1_nAuto;

    public static int getSize() {
        return 2;
    }

    protected FRDAbstractType() {
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_nAuto = LittleEndian.getShort(bArr, i + 0);
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i + 0, this.field_1_nAuto);
    }

    public String toString() {
        return "[FRD]\n" + "    .nAuto                = " + " (" + getNAuto() + " )\n" + "[/FRD]\n";
    }

    public short getNAuto() {
        return this.field_1_nAuto;
    }

    public void setNAuto(short s) {
        this.field_1_nAuto = s;
    }
}
