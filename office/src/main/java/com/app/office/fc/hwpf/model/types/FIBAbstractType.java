package com.app.office.fc.hwpf.model.types;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.fc.hwpf.model.HDFType;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public abstract class FIBAbstractType implements HDFType {
    private static BitField cQuickSaves = BitFieldFactory.getInstance(ShapeTypes.Funnel);
    private static BitField fComplex = BitFieldFactory.getInstance(4);
    private static BitField fCrypto = BitFieldFactory.getInstance(32768);
    private static BitField fDot = BitFieldFactory.getInstance(1);
    private static BitField fEmptySpecial = BitFieldFactory.getInstance(2);
    private static BitField fEncrypted = BitFieldFactory.getInstance(256);
    private static BitField fExtChar = BitFieldFactory.getInstance(4096);
    private static BitField fFarEast = BitFieldFactory.getInstance(16384);
    private static BitField fFutureSavedUndo = BitFieldFactory.getInstance(8);
    private static BitField fGlsy = BitFieldFactory.getInstance(2);
    private static BitField fHasPic = BitFieldFactory.getInstance(8);
    private static BitField fLoadOverride = BitFieldFactory.getInstance(8192);
    private static BitField fLoadOverridePage = BitFieldFactory.getInstance(4);
    private static BitField fMac = BitFieldFactory.getInstance(1);
    private static BitField fReadOnlyRecommended = BitFieldFactory.getInstance(1024);
    private static BitField fSpare0 = BitFieldFactory.getInstance(TIFFConstants.TIFFTAG_SUBFILETYPE);
    private static BitField fWhichTblStm = BitFieldFactory.getInstance(512);
    private static BitField fWord97Saved = BitFieldFactory.getInstance(16);
    private static BitField fWriteReservation = BitFieldFactory.getInstance(2048);
    protected short field_10_history;
    protected int field_11_chs;
    protected int field_12_chsTables;
    protected int field_13_fcMin;
    protected int field_14_fcMac;
    protected int field_1_wIdent;
    protected int field_2_nFib;
    protected int field_3_nProduct;
    protected int field_4_lid;
    protected int field_5_pnNext;
    protected short field_6_options;
    protected int field_7_nFibBack;
    protected int field_8_lKey;
    protected int field_9_envr;

    public int getSize() {
        return 32;
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_wIdent = LittleEndian.getShort(bArr, i + 0);
        this.field_2_nFib = LittleEndian.getShort(bArr, i + 2);
        this.field_3_nProduct = LittleEndian.getShort(bArr, i + 4);
        this.field_4_lid = LittleEndian.getShort(bArr, i + 6);
        this.field_5_pnNext = LittleEndian.getShort(bArr, i + 8);
        this.field_6_options = LittleEndian.getShort(bArr, i + 10);
        this.field_7_nFibBack = LittleEndian.getShort(bArr, i + 12);
        this.field_8_lKey = LittleEndian.getShort(bArr, i + 14);
        this.field_9_envr = LittleEndian.getShort(bArr, i + 16);
        this.field_10_history = LittleEndian.getShort(bArr, i + 18);
        this.field_11_chs = LittleEndian.getShort(bArr, i + 20);
        this.field_12_chsTables = LittleEndian.getShort(bArr, i + 22);
        this.field_13_fcMin = LittleEndian.getInt(bArr, i + 24);
        this.field_14_fcMac = LittleEndian.getInt(bArr, i + 28);
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i + 0, (short) this.field_1_wIdent);
        LittleEndian.putShort(bArr, i + 2, (short) this.field_2_nFib);
        LittleEndian.putShort(bArr, i + 4, (short) this.field_3_nProduct);
        LittleEndian.putShort(bArr, i + 6, (short) this.field_4_lid);
        LittleEndian.putShort(bArr, i + 8, (short) this.field_5_pnNext);
        LittleEndian.putShort(bArr, i + 10, this.field_6_options);
        LittleEndian.putShort(bArr, i + 12, (short) this.field_7_nFibBack);
        LittleEndian.putShort(bArr, i + 14, (short) this.field_8_lKey);
        LittleEndian.putShort(bArr, i + 16, (short) this.field_9_envr);
        LittleEndian.putShort(bArr, i + 18, this.field_10_history);
        LittleEndian.putShort(bArr, i + 20, (short) this.field_11_chs);
        LittleEndian.putShort(bArr, i + 22, (short) this.field_12_chsTables);
        LittleEndian.putInt(bArr, i + 24, this.field_13_fcMin);
        LittleEndian.putInt(bArr, i + 28, this.field_14_fcMac);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FIB]\n");
        stringBuffer.append("    .wIdent               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWIdent());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .nFib                 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getNFib());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .nProduct             = ");
        stringBuffer.append(" (");
        stringBuffer.append(getNProduct());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .lid                  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getLid());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .pnNext               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getPnNext());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .options              = ");
        stringBuffer.append(" (");
        stringBuffer.append(getOptions());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fDot                     = ");
        stringBuffer.append(isFDot());
        stringBuffer.append(10);
        stringBuffer.append("         .fGlsy                    = ");
        stringBuffer.append(isFGlsy());
        stringBuffer.append(10);
        stringBuffer.append("         .fComplex                 = ");
        stringBuffer.append(isFComplex());
        stringBuffer.append(10);
        stringBuffer.append("         .fHasPic                  = ");
        stringBuffer.append(isFHasPic());
        stringBuffer.append(10);
        stringBuffer.append("         .cQuickSaves              = ");
        stringBuffer.append(getCQuickSaves());
        stringBuffer.append(10);
        stringBuffer.append("         .fEncrypted               = ");
        stringBuffer.append(isFEncrypted());
        stringBuffer.append(10);
        stringBuffer.append("         .fWhichTblStm             = ");
        stringBuffer.append(isFWhichTblStm());
        stringBuffer.append(10);
        stringBuffer.append("         .fReadOnlyRecommended     = ");
        stringBuffer.append(isFReadOnlyRecommended());
        stringBuffer.append(10);
        stringBuffer.append("         .fWriteReservation        = ");
        stringBuffer.append(isFWriteReservation());
        stringBuffer.append(10);
        stringBuffer.append("         .fExtChar                 = ");
        stringBuffer.append(isFExtChar());
        stringBuffer.append(10);
        stringBuffer.append("         .fLoadOverride            = ");
        stringBuffer.append(isFLoadOverride());
        stringBuffer.append(10);
        stringBuffer.append("         .fFarEast                 = ");
        stringBuffer.append(isFFarEast());
        stringBuffer.append(10);
        stringBuffer.append("         .fCrypto                  = ");
        stringBuffer.append(isFCrypto());
        stringBuffer.append(10);
        stringBuffer.append("    .nFibBack             = ");
        stringBuffer.append(" (");
        stringBuffer.append(getNFibBack());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .lKey                 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getLKey());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .envr                 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getEnvr());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .history              = ");
        stringBuffer.append(" (");
        stringBuffer.append(getHistory());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fMac                     = ");
        stringBuffer.append(isFMac());
        stringBuffer.append(10);
        stringBuffer.append("         .fEmptySpecial            = ");
        stringBuffer.append(isFEmptySpecial());
        stringBuffer.append(10);
        stringBuffer.append("         .fLoadOverridePage        = ");
        stringBuffer.append(isFLoadOverridePage());
        stringBuffer.append(10);
        stringBuffer.append("         .fFutureSavedUndo         = ");
        stringBuffer.append(isFFutureSavedUndo());
        stringBuffer.append(10);
        stringBuffer.append("         .fWord97Saved             = ");
        stringBuffer.append(isFWord97Saved());
        stringBuffer.append(10);
        stringBuffer.append("         .fSpare0                  = ");
        stringBuffer.append(getFSpare0());
        stringBuffer.append(10);
        stringBuffer.append("    .chs                  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getChs());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .chsTables            = ");
        stringBuffer.append(" (");
        stringBuffer.append(getChsTables());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fcMin                = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFcMin());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fcMac                = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFcMac());
        stringBuffer.append(" )\n");
        stringBuffer.append("[/FIB]\n");
        return stringBuffer.toString();
    }

    public int getWIdent() {
        return this.field_1_wIdent;
    }

    public void setWIdent(int i) {
        this.field_1_wIdent = i;
    }

    public int getNFib() {
        return this.field_2_nFib;
    }

    public void setNFib(int i) {
        this.field_2_nFib = i;
    }

    public int getNProduct() {
        return this.field_3_nProduct;
    }

    public void setNProduct(int i) {
        this.field_3_nProduct = i;
    }

    public int getLid() {
        return this.field_4_lid;
    }

    public void setLid(int i) {
        this.field_4_lid = i;
    }

    public int getPnNext() {
        return this.field_5_pnNext;
    }

    public void setPnNext(int i) {
        this.field_5_pnNext = i;
    }

    public short getOptions() {
        return this.field_6_options;
    }

    public void setOptions(short s) {
        this.field_6_options = s;
    }

    public int getNFibBack() {
        return this.field_7_nFibBack;
    }

    public void setNFibBack(int i) {
        this.field_7_nFibBack = i;
    }

    public int getLKey() {
        return this.field_8_lKey;
    }

    public void setLKey(int i) {
        this.field_8_lKey = i;
    }

    public int getEnvr() {
        return this.field_9_envr;
    }

    public void setEnvr(int i) {
        this.field_9_envr = i;
    }

    public short getHistory() {
        return this.field_10_history;
    }

    public void setHistory(short s) {
        this.field_10_history = s;
    }

    public int getChs() {
        return this.field_11_chs;
    }

    public void setChs(int i) {
        this.field_11_chs = i;
    }

    public int getChsTables() {
        return this.field_12_chsTables;
    }

    public void setChsTables(int i) {
        this.field_12_chsTables = i;
    }

    public int getFcMin() {
        return this.field_13_fcMin;
    }

    public void setFcMin(int i) {
        this.field_13_fcMin = i;
    }

    public int getFcMac() {
        return this.field_14_fcMac;
    }

    public void setFcMac(int i) {
        this.field_14_fcMac = i;
    }

    public void setFDot(boolean z) {
        this.field_6_options = (short) fDot.setBoolean(this.field_6_options, z);
    }

    public boolean isFDot() {
        return fDot.isSet(this.field_6_options);
    }

    public void setFGlsy(boolean z) {
        this.field_6_options = (short) fGlsy.setBoolean(this.field_6_options, z);
    }

    public boolean isFGlsy() {
        return fGlsy.isSet(this.field_6_options);
    }

    public void setFComplex(boolean z) {
        this.field_6_options = (short) fComplex.setBoolean(this.field_6_options, z);
    }

    public boolean isFComplex() {
        return fComplex.isSet(this.field_6_options);
    }

    public void setFHasPic(boolean z) {
        this.field_6_options = (short) fHasPic.setBoolean(this.field_6_options, z);
    }

    public boolean isFHasPic() {
        return fHasPic.isSet(this.field_6_options);
    }

    public void setCQuickSaves(byte b) {
        this.field_6_options = (short) cQuickSaves.setValue(this.field_6_options, b);
    }

    public byte getCQuickSaves() {
        return (byte) cQuickSaves.getValue(this.field_6_options);
    }

    public void setFEncrypted(boolean z) {
        this.field_6_options = (short) fEncrypted.setBoolean(this.field_6_options, z);
    }

    public boolean isFEncrypted() {
        return fEncrypted.isSet(this.field_6_options);
    }

    public void setFWhichTblStm(boolean z) {
        this.field_6_options = (short) fWhichTblStm.setBoolean(this.field_6_options, z);
    }

    public boolean isFWhichTblStm() {
        return fWhichTblStm.isSet(this.field_6_options);
    }

    public void setFReadOnlyRecommended(boolean z) {
        this.field_6_options = (short) fReadOnlyRecommended.setBoolean(this.field_6_options, z);
    }

    public boolean isFReadOnlyRecommended() {
        return fReadOnlyRecommended.isSet(this.field_6_options);
    }

    public void setFWriteReservation(boolean z) {
        this.field_6_options = (short) fWriteReservation.setBoolean(this.field_6_options, z);
    }

    public boolean isFWriteReservation() {
        return fWriteReservation.isSet(this.field_6_options);
    }

    public void setFExtChar(boolean z) {
        this.field_6_options = (short) fExtChar.setBoolean(this.field_6_options, z);
    }

    public boolean isFExtChar() {
        return fExtChar.isSet(this.field_6_options);
    }

    public void setFLoadOverride(boolean z) {
        this.field_6_options = (short) fLoadOverride.setBoolean(this.field_6_options, z);
    }

    public boolean isFLoadOverride() {
        return fLoadOverride.isSet(this.field_6_options);
    }

    public void setFFarEast(boolean z) {
        this.field_6_options = (short) fFarEast.setBoolean(this.field_6_options, z);
    }

    public boolean isFFarEast() {
        return fFarEast.isSet(this.field_6_options);
    }

    public void setFCrypto(boolean z) {
        this.field_6_options = (short) fCrypto.setBoolean(this.field_6_options, z);
    }

    public boolean isFCrypto() {
        return fCrypto.isSet(this.field_6_options);
    }

    public void setFMac(boolean z) {
        this.field_10_history = (short) fMac.setBoolean(this.field_10_history, z);
    }

    public boolean isFMac() {
        return fMac.isSet(this.field_10_history);
    }

    public void setFEmptySpecial(boolean z) {
        this.field_10_history = (short) fEmptySpecial.setBoolean(this.field_10_history, z);
    }

    public boolean isFEmptySpecial() {
        return fEmptySpecial.isSet(this.field_10_history);
    }

    public void setFLoadOverridePage(boolean z) {
        this.field_10_history = (short) fLoadOverridePage.setBoolean(this.field_10_history, z);
    }

    public boolean isFLoadOverridePage() {
        return fLoadOverridePage.isSet(this.field_10_history);
    }

    public void setFFutureSavedUndo(boolean z) {
        this.field_10_history = (short) fFutureSavedUndo.setBoolean(this.field_10_history, z);
    }

    public boolean isFFutureSavedUndo() {
        return fFutureSavedUndo.isSet(this.field_10_history);
    }

    public void setFWord97Saved(boolean z) {
        this.field_10_history = (short) fWord97Saved.setBoolean(this.field_10_history, z);
    }

    public boolean isFWord97Saved() {
        return fWord97Saved.isSet(this.field_10_history);
    }

    public void setFSpare0(byte b) {
        this.field_10_history = (short) fSpare0.setValue(this.field_10_history, b);
    }

    public byte getFSpare0() {
        return (byte) fSpare0.getValue(this.field_10_history);
    }
}
