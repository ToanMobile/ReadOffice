package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public abstract class LFOAbstractType {
    private static BitField fHtmlBuiltInBullet = new BitField(128);
    private static BitField fHtmlChecked = new BitField(1);
    private static BitField fHtmlFirstLineMismatch = new BitField(16);
    private static BitField fHtmlHangingIndentBeneathNumber = new BitField(64);
    private static BitField fHtmlListTextNotSharpDot = new BitField(4);
    private static BitField fHtmlNotPeriod = new BitField(8);
    private static BitField fHtmlTabLeftIndentMismatch = new BitField(32);
    private static BitField fHtmlUnsupported = new BitField(2);
    protected int field_1_lsid;
    protected int field_2_reserved1;
    protected int field_3_reserved2;
    protected byte field_4_clfolvl;
    protected byte field_5_ibstFltAutoNum;
    protected byte field_6_grfhic;
    protected byte field_7_reserved3;

    public static int getSize() {
        return 16;
    }

    protected LFOAbstractType() {
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_lsid = LittleEndian.getInt(bArr, i + 0);
        this.field_2_reserved1 = LittleEndian.getInt(bArr, i + 4);
        this.field_3_reserved2 = LittleEndian.getInt(bArr, i + 8);
        this.field_4_clfolvl = bArr[i + 12];
        this.field_5_ibstFltAutoNum = bArr[i + 13];
        this.field_6_grfhic = bArr[i + 14];
        this.field_7_reserved3 = bArr[i + 15];
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putInt(bArr, i + 0, this.field_1_lsid);
        LittleEndian.putInt(bArr, i + 4, this.field_2_reserved1);
        LittleEndian.putInt(bArr, i + 8, this.field_3_reserved2);
        bArr[i + 12] = this.field_4_clfolvl;
        bArr[i + 13] = this.field_5_ibstFltAutoNum;
        bArr[i + 14] = this.field_6_grfhic;
        bArr[i + 15] = this.field_7_reserved3;
    }

    public String toString() {
        return "[LFO]\n" + "    .lsid                 = " + " (" + getLsid() + " )\n" + "    .reserved1            = " + " (" + getReserved1() + " )\n" + "    .reserved2            = " + " (" + getReserved2() + " )\n" + "    .clfolvl              = " + " (" + getClfolvl() + " )\n" + "    .ibstFltAutoNum       = " + " (" + getIbstFltAutoNum() + " )\n" + "    .grfhic               = " + " (" + getGrfhic() + " )\n" + "         .fHtmlChecked             = " + isFHtmlChecked() + 10 + "         .fHtmlUnsupported         = " + isFHtmlUnsupported() + 10 + "         .fHtmlListTextNotSharpDot     = " + isFHtmlListTextNotSharpDot() + 10 + "         .fHtmlNotPeriod           = " + isFHtmlNotPeriod() + 10 + "         .fHtmlFirstLineMismatch     = " + isFHtmlFirstLineMismatch() + 10 + "         .fHtmlTabLeftIndentMismatch     = " + isFHtmlTabLeftIndentMismatch() + 10 + "         .fHtmlHangingIndentBeneathNumber     = " + isFHtmlHangingIndentBeneathNumber() + 10 + "         .fHtmlBuiltInBullet       = " + isFHtmlBuiltInBullet() + 10 + "    .reserved3            = " + " (" + getReserved3() + " )\n" + "[/LFO]\n";
    }

    public int getLsid() {
        return this.field_1_lsid;
    }

    public void setLsid(int i) {
        this.field_1_lsid = i;
    }

    public int getReserved1() {
        return this.field_2_reserved1;
    }

    public void setReserved1(int i) {
        this.field_2_reserved1 = i;
    }

    public int getReserved2() {
        return this.field_3_reserved2;
    }

    public void setReserved2(int i) {
        this.field_3_reserved2 = i;
    }

    public byte getClfolvl() {
        return this.field_4_clfolvl;
    }

    public void setClfolvl(byte b) {
        this.field_4_clfolvl = b;
    }

    public byte getIbstFltAutoNum() {
        return this.field_5_ibstFltAutoNum;
    }

    public void setIbstFltAutoNum(byte b) {
        this.field_5_ibstFltAutoNum = b;
    }

    public byte getGrfhic() {
        return this.field_6_grfhic;
    }

    public void setGrfhic(byte b) {
        this.field_6_grfhic = b;
    }

    public byte getReserved3() {
        return this.field_7_reserved3;
    }

    public void setReserved3(byte b) {
        this.field_7_reserved3 = b;
    }

    public void setFHtmlChecked(boolean z) {
        this.field_6_grfhic = (byte) fHtmlChecked.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlChecked() {
        return fHtmlChecked.isSet(this.field_6_grfhic);
    }

    public void setFHtmlUnsupported(boolean z) {
        this.field_6_grfhic = (byte) fHtmlUnsupported.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlUnsupported() {
        return fHtmlUnsupported.isSet(this.field_6_grfhic);
    }

    public void setFHtmlListTextNotSharpDot(boolean z) {
        this.field_6_grfhic = (byte) fHtmlListTextNotSharpDot.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlListTextNotSharpDot() {
        return fHtmlListTextNotSharpDot.isSet(this.field_6_grfhic);
    }

    public void setFHtmlNotPeriod(boolean z) {
        this.field_6_grfhic = (byte) fHtmlNotPeriod.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlNotPeriod() {
        return fHtmlNotPeriod.isSet(this.field_6_grfhic);
    }

    public void setFHtmlFirstLineMismatch(boolean z) {
        this.field_6_grfhic = (byte) fHtmlFirstLineMismatch.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlFirstLineMismatch() {
        return fHtmlFirstLineMismatch.isSet(this.field_6_grfhic);
    }

    public void setFHtmlTabLeftIndentMismatch(boolean z) {
        this.field_6_grfhic = (byte) fHtmlTabLeftIndentMismatch.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlTabLeftIndentMismatch() {
        return fHtmlTabLeftIndentMismatch.isSet(this.field_6_grfhic);
    }

    public void setFHtmlHangingIndentBeneathNumber(boolean z) {
        this.field_6_grfhic = (byte) fHtmlHangingIndentBeneathNumber.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlHangingIndentBeneathNumber() {
        return fHtmlHangingIndentBeneathNumber.isSet(this.field_6_grfhic);
    }

    public void setFHtmlBuiltInBullet(boolean z) {
        this.field_6_grfhic = (byte) fHtmlBuiltInBullet.setBoolean(this.field_6_grfhic, z);
    }

    public boolean isFHtmlBuiltInBullet() {
        return fHtmlBuiltInBullet.isSet(this.field_6_grfhic);
    }
}
