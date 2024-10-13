package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public abstract class FSPAAbstractType {
    private static BitField bx = new BitField(6);
    private static BitField by = new BitField(24);
    private static BitField fAnchorLock = new BitField(32768);
    private static BitField fBelowText = new BitField(16384);
    private static BitField fHdr = new BitField(1);
    private static BitField fRcaSimple = new BitField(8192);
    private static BitField wr = new BitField(480);
    private static BitField wrk = new BitField(7680);
    protected int field_1_spid;
    protected int field_2_xaLeft;
    protected int field_3_yaTop;
    protected int field_4_xaRight;
    protected int field_5_yaBottom;
    protected short field_6_flags;
    protected int field_7_cTxbx;

    public static int getSize() {
        return 26;
    }

    protected FSPAAbstractType() {
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_spid = LittleEndian.getInt(bArr, i + 0);
        this.field_2_xaLeft = LittleEndian.getInt(bArr, i + 4);
        this.field_3_yaTop = LittleEndian.getInt(bArr, i + 8);
        this.field_4_xaRight = LittleEndian.getInt(bArr, i + 12);
        this.field_5_yaBottom = LittleEndian.getInt(bArr, i + 16);
        this.field_6_flags = LittleEndian.getShort(bArr, i + 20);
        this.field_7_cTxbx = LittleEndian.getInt(bArr, i + 22);
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putInt(bArr, i + 0, this.field_1_spid);
        LittleEndian.putInt(bArr, i + 4, this.field_2_xaLeft);
        LittleEndian.putInt(bArr, i + 8, this.field_3_yaTop);
        LittleEndian.putInt(bArr, i + 12, this.field_4_xaRight);
        LittleEndian.putInt(bArr, i + 16, this.field_5_yaBottom);
        LittleEndian.putShort(bArr, i + 20, this.field_6_flags);
        LittleEndian.putInt(bArr, i + 22, this.field_7_cTxbx);
    }

    public String toString() {
        return "[FSPA]\n" + "    .spid                 = " + " (" + getSpid() + " )\n" + "    .xaLeft               = " + " (" + getXaLeft() + " )\n" + "    .yaTop                = " + " (" + getYaTop() + " )\n" + "    .xaRight              = " + " (" + getXaRight() + " )\n" + "    .yaBottom             = " + " (" + getYaBottom() + " )\n" + "    .flags                = " + " (" + getFlags() + " )\n" + "         .fHdr                     = " + isFHdr() + 10 + "         .bx                       = " + getBx() + 10 + "         .by                       = " + getBy() + 10 + "         .wr                       = " + getWr() + 10 + "         .wrk                      = " + getWrk() + 10 + "         .fRcaSimple               = " + isFRcaSimple() + 10 + "         .fBelowText               = " + isFBelowText() + 10 + "         .fAnchorLock              = " + isFAnchorLock() + 10 + "    .cTxbx                = " + " (" + getCTxbx() + " )\n" + "[/FSPA]\n";
    }

    @Internal
    public int getSpid() {
        return this.field_1_spid;
    }

    @Internal
    public void setSpid(int i) {
        this.field_1_spid = i;
    }

    @Internal
    public int getXaLeft() {
        return this.field_2_xaLeft;
    }

    @Internal
    public void setXaLeft(int i) {
        this.field_2_xaLeft = i;
    }

    @Internal
    public int getYaTop() {
        return this.field_3_yaTop;
    }

    @Internal
    public void setYaTop(int i) {
        this.field_3_yaTop = i;
    }

    @Internal
    public int getXaRight() {
        return this.field_4_xaRight;
    }

    @Internal
    public void setXaRight(int i) {
        this.field_4_xaRight = i;
    }

    @Internal
    public int getYaBottom() {
        return this.field_5_yaBottom;
    }

    @Internal
    public void setYaBottom(int i) {
        this.field_5_yaBottom = i;
    }

    @Internal
    public short getFlags() {
        return this.field_6_flags;
    }

    @Internal
    public void setFlags(short s) {
        this.field_6_flags = s;
    }

    @Internal
    public int getCTxbx() {
        return this.field_7_cTxbx;
    }

    @Internal
    public void setCTxbx(int i) {
        this.field_7_cTxbx = i;
    }

    @Internal
    public void setFHdr(boolean z) {
        this.field_6_flags = (short) fHdr.setBoolean(this.field_6_flags, z);
    }

    @Internal
    public boolean isFHdr() {
        return fHdr.isSet(this.field_6_flags);
    }

    @Internal
    public void setBx(byte b) {
        this.field_6_flags = (short) bx.setValue(this.field_6_flags, b);
    }

    @Internal
    public byte getBx() {
        return (byte) bx.getValue(this.field_6_flags);
    }

    @Internal
    public void setBy(byte b) {
        this.field_6_flags = (short) by.setValue(this.field_6_flags, b);
    }

    @Internal
    public byte getBy() {
        return (byte) by.getValue(this.field_6_flags);
    }

    @Internal
    public void setWr(byte b) {
        this.field_6_flags = (short) wr.setValue(this.field_6_flags, b);
    }

    @Internal
    public byte getWr() {
        return (byte) wr.getValue(this.field_6_flags);
    }

    @Internal
    public void setWrk(byte b) {
        this.field_6_flags = (short) wrk.setValue(this.field_6_flags, b);
    }

    @Internal
    public byte getWrk() {
        return (byte) wrk.getValue(this.field_6_flags);
    }

    @Internal
    public void setFRcaSimple(boolean z) {
        this.field_6_flags = (short) fRcaSimple.setBoolean(this.field_6_flags, z);
    }

    @Internal
    public boolean isFRcaSimple() {
        return fRcaSimple.isSet(this.field_6_flags);
    }

    @Internal
    public void setFBelowText(boolean z) {
        this.field_6_flags = (short) fBelowText.setBoolean(this.field_6_flags, z);
    }

    @Internal
    public boolean isFBelowText() {
        return fBelowText.isSet(this.field_6_flags);
    }

    @Internal
    public void setFAnchorLock(boolean z) {
        this.field_6_flags = (short) fAnchorLock.setBoolean(this.field_6_flags, z);
    }

    @Internal
    public boolean isFAnchorLock() {
        return fAnchorLock.isSet(this.field_6_flags);
    }
}
