package com.app.office.fc.hwpf.model.types;

import com.onesignal.OneSignalRemoteParams;
import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.hwpf.usermodel.DateAndTime;
import com.app.office.fc.util.Internal;
import com.app.office.pg.animate.IAnimation;

@Internal
public abstract class SEPAbstractType {
    public static final byte BKC_EVEN_PAGE = 3;
    public static final byte BKC_NEW_COLUMN = 1;
    public static final byte BKC_NEW_PAGE = 2;
    public static final byte BKC_NO_BREAK = 0;
    public static final byte BKC_ODD_PAGE = 4;
    public static final boolean DMORIENTPAGE_LANDSCAPE = false;
    public static final boolean DMORIENTPAGE_PORTRAIT = true;
    public static final byte NFCPGN_ARABIC = 0;
    public static final byte NFCPGN_LETTER_LOWER_CASE = 4;
    public static final byte NFCPGN_LETTER_UPPER_CASE = 3;
    public static final byte NFCPGN_ROMAN_LOWER_CASE = 2;
    public static final byte NFCPGN_ROMAN_UPPER_CASE = 1;
    protected byte field_10_grpfIhdt;
    protected int field_11_nLnnMod;
    protected int field_12_dxaLnn;
    protected int field_13_dxaPgn = IAnimation.AnimationInformation.ROTATION;
    protected int field_14_dyaPgn = IAnimation.AnimationInformation.ROTATION;
    protected boolean field_15_fLBetween;
    protected byte field_16_vjc;
    protected int field_17_dmBinFirst;
    protected int field_18_dmBinOther;
    protected int field_19_dmPaperReq;
    protected byte field_1_bkc = 2;
    protected BorderCode field_20_brcTop;
    protected BorderCode field_21_brcLeft;
    protected BorderCode field_22_brcBottom;
    protected BorderCode field_23_brcRight;
    protected boolean field_24_fPropMark;
    protected int field_25_ibstPropRMark;
    protected DateAndTime field_26_dttmPropRMark;
    protected int field_27_dxtCharSpace;
    protected int field_28_dyaLinePitch;
    protected int field_29_clm;
    protected boolean field_2_fTitlePage;
    protected int field_30_unused2;
    protected boolean field_31_dmOrientPage = true;
    protected byte field_32_iHeadingPgn;
    protected int field_33_pgnStart = 1;
    protected int field_34_lnnMin;
    protected int field_35_wTextFlow;
    protected short field_36_unused3;
    protected int field_37_pgbProp;
    protected short field_38_unused4;
    protected int field_39_xaPage = 12240;
    protected boolean field_3_fAutoPgn;
    protected int field_40_yaPage = 15840;
    protected int field_41_xaPageNUp = 12240;
    protected int field_42_yaPageNUp = 15840;
    protected int field_43_dxaLeft = 1800;
    protected int field_44_dxaRight = 1800;
    protected int field_45_dyaTop = OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW;
    protected int field_46_dyaBottom = OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW;
    protected int field_47_dzaGutter;
    protected int field_48_dyaHdrTop = IAnimation.AnimationInformation.ROTATION;
    protected int field_49_dyaHdrBottom = IAnimation.AnimationInformation.ROTATION;
    protected byte field_4_nfcPgn;
    protected int field_50_ccolM1;
    protected boolean field_51_fEvenlySpaced = true;
    protected byte field_52_unused5;
    protected int field_53_dxaColumns = IAnimation.AnimationInformation.ROTATION;
    protected int[] field_54_rgdxaColumn;
    protected int field_55_dxaColumnWidth;
    protected byte field_56_dmOrientFirst;
    protected byte field_57_fLayout;
    protected short field_58_unused6;
    protected byte[] field_59_olstAnm;
    protected boolean field_5_fUnlocked;
    protected byte field_6_cnsPgn;
    protected boolean field_7_fPgnRestart;
    protected boolean field_8_fEndNote = true;
    protected byte field_9_lnc;

    protected SEPAbstractType() {
    }

    public String toString() {
        return "[SEP]\n" + "    .bkc                  = " + " (" + getBkc() + " )\n" + "    .fTitlePage           = " + " (" + getFTitlePage() + " )\n" + "    .fAutoPgn             = " + " (" + getFAutoPgn() + " )\n" + "    .nfcPgn               = " + " (" + getNfcPgn() + " )\n" + "    .fUnlocked            = " + " (" + getFUnlocked() + " )\n" + "    .cnsPgn               = " + " (" + getCnsPgn() + " )\n" + "    .fPgnRestart          = " + " (" + getFPgnRestart() + " )\n" + "    .fEndNote             = " + " (" + getFEndNote() + " )\n" + "    .lnc                  = " + " (" + getLnc() + " )\n" + "    .grpfIhdt             = " + " (" + getGrpfIhdt() + " )\n" + "    .nLnnMod              = " + " (" + getNLnnMod() + " )\n" + "    .dxaLnn               = " + " (" + getDxaLnn() + " )\n" + "    .dxaPgn               = " + " (" + getDxaPgn() + " )\n" + "    .dyaPgn               = " + " (" + getDyaPgn() + " )\n" + "    .fLBetween            = " + " (" + getFLBetween() + " )\n" + "    .vjc                  = " + " (" + getVjc() + " )\n" + "    .dmBinFirst           = " + " (" + getDmBinFirst() + " )\n" + "    .dmBinOther           = " + " (" + getDmBinOther() + " )\n" + "    .dmPaperReq           = " + " (" + getDmPaperReq() + " )\n" + "    .brcTop               = " + " (" + getBrcTop() + " )\n" + "    .brcLeft              = " + " (" + getBrcLeft() + " )\n" + "    .brcBottom            = " + " (" + getBrcBottom() + " )\n" + "    .brcRight             = " + " (" + getBrcRight() + " )\n" + "    .fPropMark            = " + " (" + getFPropMark() + " )\n" + "    .ibstPropRMark        = " + " (" + getIbstPropRMark() + " )\n" + "    .dttmPropRMark        = " + " (" + getDttmPropRMark() + " )\n" + "    .dxtCharSpace         = " + " (" + getDxtCharSpace() + " )\n" + "    .dyaLinePitch         = " + " (" + getDyaLinePitch() + " )\n" + "    .clm                  = " + " (" + getClm() + " )\n" + "    .unused2              = " + " (" + getUnused2() + " )\n" + "    .dmOrientPage         = " + " (" + getDmOrientPage() + " )\n" + "    .iHeadingPgn          = " + " (" + getIHeadingPgn() + " )\n" + "    .pgnStart             = " + " (" + getPgnStart() + " )\n" + "    .lnnMin               = " + " (" + getLnnMin() + " )\n" + "    .wTextFlow            = " + " (" + getWTextFlow() + " )\n" + "    .unused3              = " + " (" + getUnused3() + " )\n" + "    .pgbProp              = " + " (" + getPgbProp() + " )\n" + "    .unused4              = " + " (" + getUnused4() + " )\n" + "    .xaPage               = " + " (" + getXaPage() + " )\n" + "    .yaPage               = " + " (" + getYaPage() + " )\n" + "    .xaPageNUp            = " + " (" + getXaPageNUp() + " )\n" + "    .yaPageNUp            = " + " (" + getYaPageNUp() + " )\n" + "    .dxaLeft              = " + " (" + getDxaLeft() + " )\n" + "    .dxaRight             = " + " (" + getDxaRight() + " )\n" + "    .dyaTop               = " + " (" + getDyaTop() + " )\n" + "    .dyaBottom            = " + " (" + getDyaBottom() + " )\n" + "    .dzaGutter            = " + " (" + getDzaGutter() + " )\n" + "    .dyaHdrTop            = " + " (" + getDyaHdrTop() + " )\n" + "    .dyaHdrBottom         = " + " (" + getDyaHdrBottom() + " )\n" + "    .ccolM1               = " + " (" + getCcolM1() + " )\n" + "    .fEvenlySpaced        = " + " (" + getFEvenlySpaced() + " )\n" + "    .unused5              = " + " (" + getUnused5() + " )\n" + "    .dxaColumns           = " + " (" + getDxaColumns() + " )\n" + "    .rgdxaColumn          = " + " (" + getRgdxaColumn() + " )\n" + "    .dxaColumnWidth       = " + " (" + getDxaColumnWidth() + " )\n" + "    .dmOrientFirst        = " + " (" + getDmOrientFirst() + " )\n" + "    .fLayout              = " + " (" + getFLayout() + " )\n" + "    .unused6              = " + " (" + getUnused6() + " )\n" + "    .olstAnm              = " + " (" + getOlstAnm() + " )\n" + "[/SEP]\n";
    }

    public byte getBkc() {
        return this.field_1_bkc;
    }

    public void setBkc(byte b) {
        this.field_1_bkc = b;
    }

    public boolean getFTitlePage() {
        return this.field_2_fTitlePage;
    }

    public void setFTitlePage(boolean z) {
        this.field_2_fTitlePage = z;
    }

    public boolean getFAutoPgn() {
        return this.field_3_fAutoPgn;
    }

    public void setFAutoPgn(boolean z) {
        this.field_3_fAutoPgn = z;
    }

    public byte getNfcPgn() {
        return this.field_4_nfcPgn;
    }

    public void setNfcPgn(byte b) {
        this.field_4_nfcPgn = b;
    }

    public boolean getFUnlocked() {
        return this.field_5_fUnlocked;
    }

    public void setFUnlocked(boolean z) {
        this.field_5_fUnlocked = z;
    }

    public byte getCnsPgn() {
        return this.field_6_cnsPgn;
    }

    public void setCnsPgn(byte b) {
        this.field_6_cnsPgn = b;
    }

    public boolean getFPgnRestart() {
        return this.field_7_fPgnRestart;
    }

    public void setFPgnRestart(boolean z) {
        this.field_7_fPgnRestart = z;
    }

    public boolean getFEndNote() {
        return this.field_8_fEndNote;
    }

    public void setFEndNote(boolean z) {
        this.field_8_fEndNote = z;
    }

    public byte getLnc() {
        return this.field_9_lnc;
    }

    public void setLnc(byte b) {
        this.field_9_lnc = b;
    }

    public byte getGrpfIhdt() {
        return this.field_10_grpfIhdt;
    }

    public void setGrpfIhdt(byte b) {
        this.field_10_grpfIhdt = b;
    }

    public int getNLnnMod() {
        return this.field_11_nLnnMod;
    }

    public void setNLnnMod(int i) {
        this.field_11_nLnnMod = i;
    }

    public int getDxaLnn() {
        return this.field_12_dxaLnn;
    }

    public void setDxaLnn(int i) {
        this.field_12_dxaLnn = i;
    }

    public int getDxaPgn() {
        return this.field_13_dxaPgn;
    }

    public void setDxaPgn(int i) {
        this.field_13_dxaPgn = i;
    }

    public int getDyaPgn() {
        return this.field_14_dyaPgn;
    }

    public void setDyaPgn(int i) {
        this.field_14_dyaPgn = i;
    }

    public boolean getFLBetween() {
        return this.field_15_fLBetween;
    }

    public void setFLBetween(boolean z) {
        this.field_15_fLBetween = z;
    }

    public byte getVjc() {
        return this.field_16_vjc;
    }

    public void setVjc(byte b) {
        this.field_16_vjc = b;
    }

    public int getDmBinFirst() {
        return this.field_17_dmBinFirst;
    }

    public void setDmBinFirst(int i) {
        this.field_17_dmBinFirst = i;
    }

    public int getDmBinOther() {
        return this.field_18_dmBinOther;
    }

    public void setDmBinOther(int i) {
        this.field_18_dmBinOther = i;
    }

    public int getDmPaperReq() {
        return this.field_19_dmPaperReq;
    }

    public void setDmPaperReq(int i) {
        this.field_19_dmPaperReq = i;
    }

    public BorderCode getBrcTop() {
        return this.field_20_brcTop;
    }

    public void setBrcTop(BorderCode borderCode) {
        this.field_20_brcTop = borderCode;
    }

    public BorderCode getBrcLeft() {
        return this.field_21_brcLeft;
    }

    public void setBrcLeft(BorderCode borderCode) {
        this.field_21_brcLeft = borderCode;
    }

    public BorderCode getBrcBottom() {
        return this.field_22_brcBottom;
    }

    public void setBrcBottom(BorderCode borderCode) {
        this.field_22_brcBottom = borderCode;
    }

    public BorderCode getBrcRight() {
        return this.field_23_brcRight;
    }

    public void setBrcRight(BorderCode borderCode) {
        this.field_23_brcRight = borderCode;
    }

    public boolean getFPropMark() {
        return this.field_24_fPropMark;
    }

    public void setFPropMark(boolean z) {
        this.field_24_fPropMark = z;
    }

    public int getIbstPropRMark() {
        return this.field_25_ibstPropRMark;
    }

    public void setIbstPropRMark(int i) {
        this.field_25_ibstPropRMark = i;
    }

    public DateAndTime getDttmPropRMark() {
        return this.field_26_dttmPropRMark;
    }

    public void setDttmPropRMark(DateAndTime dateAndTime) {
        this.field_26_dttmPropRMark = dateAndTime;
    }

    public int getDxtCharSpace() {
        return this.field_27_dxtCharSpace;
    }

    public void setDxtCharSpace(int i) {
        this.field_27_dxtCharSpace = i;
    }

    public int getDyaLinePitch() {
        return this.field_28_dyaLinePitch;
    }

    public void setDyaLinePitch(int i) {
        this.field_28_dyaLinePitch = i;
    }

    public int getClm() {
        return this.field_29_clm;
    }

    public void setClm(int i) {
        this.field_29_clm = i;
    }

    public int getUnused2() {
        return this.field_30_unused2;
    }

    public void setUnused2(int i) {
        this.field_30_unused2 = i;
    }

    public boolean getDmOrientPage() {
        return this.field_31_dmOrientPage;
    }

    public void setDmOrientPage(boolean z) {
        this.field_31_dmOrientPage = z;
    }

    public byte getIHeadingPgn() {
        return this.field_32_iHeadingPgn;
    }

    public void setIHeadingPgn(byte b) {
        this.field_32_iHeadingPgn = b;
    }

    public int getPgnStart() {
        return this.field_33_pgnStart;
    }

    public void setPgnStart(int i) {
        this.field_33_pgnStart = i;
    }

    public int getLnnMin() {
        return this.field_34_lnnMin;
    }

    public void setLnnMin(int i) {
        this.field_34_lnnMin = i;
    }

    public int getWTextFlow() {
        return this.field_35_wTextFlow;
    }

    public void setWTextFlow(int i) {
        this.field_35_wTextFlow = i;
    }

    public short getUnused3() {
        return this.field_36_unused3;
    }

    public void setUnused3(short s) {
        this.field_36_unused3 = s;
    }

    public int getPgbProp() {
        return this.field_37_pgbProp;
    }

    public void setPgbProp(int i) {
        this.field_37_pgbProp = i;
    }

    public short getUnused4() {
        return this.field_38_unused4;
    }

    public void setUnused4(short s) {
        this.field_38_unused4 = s;
    }

    public int getXaPage() {
        return this.field_39_xaPage;
    }

    public void setXaPage(int i) {
        this.field_39_xaPage = i;
    }

    public int getYaPage() {
        return this.field_40_yaPage;
    }

    public void setYaPage(int i) {
        this.field_40_yaPage = i;
    }

    public int getXaPageNUp() {
        return this.field_41_xaPageNUp;
    }

    public void setXaPageNUp(int i) {
        this.field_41_xaPageNUp = i;
    }

    public int getYaPageNUp() {
        return this.field_42_yaPageNUp;
    }

    public void setYaPageNUp(int i) {
        this.field_42_yaPageNUp = i;
    }

    public int getDxaLeft() {
        return this.field_43_dxaLeft;
    }

    public void setDxaLeft(int i) {
        this.field_43_dxaLeft = i;
    }

    public int getDxaRight() {
        return this.field_44_dxaRight;
    }

    public void setDxaRight(int i) {
        this.field_44_dxaRight = i;
    }

    public int getDyaTop() {
        return this.field_45_dyaTop;
    }

    public void setDyaTop(int i) {
        this.field_45_dyaTop = i;
    }

    public int getDyaBottom() {
        return this.field_46_dyaBottom;
    }

    public void setDyaBottom(int i) {
        this.field_46_dyaBottom = i;
    }

    public int getDzaGutter() {
        return this.field_47_dzaGutter;
    }

    public void setDzaGutter(int i) {
        this.field_47_dzaGutter = i;
    }

    public int getDyaHdrTop() {
        return this.field_48_dyaHdrTop;
    }

    public void setDyaHdrTop(int i) {
        this.field_48_dyaHdrTop = i;
    }

    public int getDyaHdrBottom() {
        return this.field_49_dyaHdrBottom;
    }

    public void setDyaHdrBottom(int i) {
        this.field_49_dyaHdrBottom = i;
    }

    public int getCcolM1() {
        return this.field_50_ccolM1;
    }

    public void setCcolM1(int i) {
        this.field_50_ccolM1 = i;
    }

    public boolean getFEvenlySpaced() {
        return this.field_51_fEvenlySpaced;
    }

    public void setFEvenlySpaced(boolean z) {
        this.field_51_fEvenlySpaced = z;
    }

    public byte getUnused5() {
        return this.field_52_unused5;
    }

    public void setUnused5(byte b) {
        this.field_52_unused5 = b;
    }

    public int getDxaColumns() {
        return this.field_53_dxaColumns;
    }

    public void setDxaColumns(int i) {
        this.field_53_dxaColumns = i;
    }

    public int[] getRgdxaColumn() {
        return this.field_54_rgdxaColumn;
    }

    public void setRgdxaColumn(int[] iArr) {
        this.field_54_rgdxaColumn = iArr;
    }

    public int getDxaColumnWidth() {
        return this.field_55_dxaColumnWidth;
    }

    public void setDxaColumnWidth(int i) {
        this.field_55_dxaColumnWidth = i;
    }

    public byte getDmOrientFirst() {
        return this.field_56_dmOrientFirst;
    }

    public void setDmOrientFirst(byte b) {
        this.field_56_dmOrientFirst = b;
    }

    public byte getFLayout() {
        return this.field_57_fLayout;
    }

    public void setFLayout(byte b) {
        this.field_57_fLayout = b;
    }

    public short getUnused6() {
        return this.field_58_unused6;
    }

    public void setUnused6(short s) {
        this.field_58_unused6 = s;
    }

    public byte[] getOlstAnm() {
        return this.field_59_olstAnm;
    }

    public void setOlstAnm(byte[] bArr) {
        this.field_59_olstAnm = bArr;
    }
}
