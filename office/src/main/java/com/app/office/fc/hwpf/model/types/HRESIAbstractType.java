package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.util.Internal;

@Internal
public abstract class HRESIAbstractType {
    public static final byte HRES_ADD_LETTER_BEFORE = 2;
    public static final byte HRES_CHANGE_LETTER_AFTER = 5;
    public static final byte HRES_CHANGE_LETTER_BEFORE = 3;
    public static final byte HRES_DELETE_BEFORE_CHANGE_BEFORE = 6;
    public static final byte HRES_DELETE_LETTER_BEFORE = 4;
    public static final byte HRES_NO = 0;
    public static final byte HRES_NORMAL = 1;
    protected byte field_1_hres;
    protected byte field_2_chHres;

    public static int getSize() {
        return 6;
    }

    protected HRESIAbstractType() {
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_hres = bArr[i + 0];
        this.field_2_chHres = bArr[i + 1];
    }

    public void serialize(byte[] bArr, int i) {
        bArr[i + 0] = this.field_1_hres;
        bArr[i + 1] = this.field_2_chHres;
    }

    public String toString() {
        return "[HRESI]\n" + "    .hres                 = " + " (" + getHres() + " )\n" + "    .chHres               = " + " (" + getChHres() + " )\n" + "[/HRESI]\n";
    }

    public byte getHres() {
        return this.field_1_hres;
    }

    public void setHres(byte b) {
        this.field_1_hres = b;
    }

    public byte getChHres() {
        return this.field_2_chHres;
    }

    public void setChHres(byte b) {
        this.field_2_chHres = b;
    }
}
