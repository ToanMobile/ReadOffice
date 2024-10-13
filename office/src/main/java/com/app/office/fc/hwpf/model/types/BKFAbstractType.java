package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public abstract class BKFAbstractType {
    private static BitField fCol = new BitField(32768);
    private static BitField fPub = new BitField(128);
    private static BitField itcFirst = new BitField(127);
    private static BitField itcLim = new BitField(32512);
    protected short field_1_ibkl;
    protected short field_2_bkf_flags;

    public static int getSize() {
        return 4;
    }

    protected BKFAbstractType() {
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_ibkl = LittleEndian.getShort(bArr, i + 0);
        this.field_2_bkf_flags = LittleEndian.getShort(bArr, i + 2);
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i + 0, this.field_1_ibkl);
        LittleEndian.putShort(bArr, i + 2, this.field_2_bkf_flags);
    }

    public String toString() {
        return "[BKF]\n" + "    .ibkl                 = " + " (" + getIbkl() + " )\n" + "    .bkf_flags            = " + " (" + getBkf_flags() + " )\n" + "         .itcFirst                 = " + getItcFirst() + 10 + "         .fPub                     = " + isFPub() + 10 + "         .itcLim                   = " + getItcLim() + 10 + "         .fCol                     = " + isFCol() + 10 + "[/BKF]\n";
    }

    public short getIbkl() {
        return this.field_1_ibkl;
    }

    public void setIbkl(short s) {
        this.field_1_ibkl = s;
    }

    public short getBkf_flags() {
        return this.field_2_bkf_flags;
    }

    public void setBkf_flags(short s) {
        this.field_2_bkf_flags = s;
    }

    public void setItcFirst(byte b) {
        this.field_2_bkf_flags = (short) itcFirst.setValue(this.field_2_bkf_flags, b);
    }

    public byte getItcFirst() {
        return (byte) itcFirst.getValue(this.field_2_bkf_flags);
    }

    public void setFPub(boolean z) {
        this.field_2_bkf_flags = (short) fPub.setBoolean(this.field_2_bkf_flags, z);
    }

    public boolean isFPub() {
        return fPub.isSet(this.field_2_bkf_flags);
    }

    public void setItcLim(byte b) {
        this.field_2_bkf_flags = (short) itcLim.setValue(this.field_2_bkf_flags, b);
    }

    public byte getItcLim() {
        return (byte) itcLim.getValue(this.field_2_bkf_flags);
    }

    public void setFCol(boolean z) {
        this.field_2_bkf_flags = (short) fCol.setBoolean(this.field_2_bkf_flags, z);
    }

    public boolean isFCol() {
        return fCol.isSet(this.field_2_bkf_flags);
    }
}
