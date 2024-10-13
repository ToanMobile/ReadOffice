package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.hwpf.usermodel.DateAndTime;
import com.app.office.fc.hwpf.usermodel.DropCapSpecifier;
import com.app.office.fc.hwpf.usermodel.LineSpacingDescriptor;
import com.app.office.fc.hwpf.usermodel.ShadingDescriptor;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;

@Internal
public abstract class PAPAbstractType {
    public static final byte BRCL_DOUBLE = 2;
    public static final byte BRCL_SHADOW = 3;
    public static final byte BRCL_SINGLE = 0;
    public static final byte BRCL_THICK = 1;
    public static final byte BRCP_BAR_TO_LEFT_OF_PARAGRAPH = 16;
    public static final byte BRCP_BORDER_ABOVE = 1;
    public static final byte BRCP_BORDER_BELOW = 2;
    public static final byte BRCP_BOX_AROUND = 15;
    public static final byte BRCP_NONE = 0;
    public static final boolean FMINHEIGHT_AT_LEAST = true;
    public static final boolean FMINHEIGHT_EXACT = false;
    public static final byte WALIGNFONT_AUTO = 4;
    public static final byte WALIGNFONT_CENTERED = 1;
    public static final byte WALIGNFONT_HANGING = 0;
    public static final byte WALIGNFONT_ROMAN = 2;
    public static final byte WALIGNFONT_VARIABLE = 3;
    private static BitField fBackward = new BitField(2);
    private static BitField fRotateFont = new BitField(4);
    private static BitField fVertical = new BitField(1);
    protected boolean field_10_fNoLnn;
    protected LineSpacingDescriptor field_11_lspd;
    protected int field_12_dyaBefore;
    protected int field_13_dyaAfter;
    protected boolean field_14_fInTable;
    protected boolean field_15_finTableW97;
    protected boolean field_16_fTtp;
    protected int field_17_dxaAbs;
    protected int field_18_dyaAbs;
    protected int field_19_dxaWidth;
    protected int field_1_istd;
    protected boolean field_20_fBrLnAbove;
    protected boolean field_21_fBrLnBelow;
    protected byte field_22_pcVert;
    protected byte field_23_pcHorz;
    protected byte field_24_wr;
    protected boolean field_25_fNoAutoHyph;
    protected int field_26_dyaHeight;
    protected boolean field_27_fMinHeight;
    protected DropCapSpecifier field_28_dcs;
    protected int field_29_dyaFromText;
    protected boolean field_2_fSideBySide;
    protected int field_30_dxaFromText;
    protected boolean field_31_fLocked;
    protected boolean field_32_fWidowControl;
    protected boolean field_33_fKinsoku;
    protected boolean field_34_fWordWrap;
    protected boolean field_35_fOverflowPunct;
    protected boolean field_36_fTopLinePunct;
    protected boolean field_37_fAutoSpaceDE;
    protected boolean field_38_fAutoSpaceDN;
    protected int field_39_wAlignFont;
    protected boolean field_3_fKeep;
    protected short field_40_fontAlign;
    protected byte field_41_lvl;
    protected boolean field_42_fBiDi;
    protected boolean field_43_fNumRMIns;
    protected boolean field_44_fCrLf;
    protected boolean field_45_fUsePgsuSettings;
    protected boolean field_46_fAdjustRight;
    protected int field_47_itap;
    protected boolean field_48_fInnerTableCell;
    protected boolean field_49_fOpenTch;
    protected boolean field_4_fKeepFollow;
    protected boolean field_50_fTtpEmbedded;
    protected short field_51_dxcRight;
    protected short field_52_dxcLeft;
    protected short field_53_dxcLeft1;
    protected boolean field_54_fDyaBeforeAuto;
    protected boolean field_55_fDyaAfterAuto;
    protected int field_56_dxaRight;
    protected int field_57_dxaLeft;
    protected int field_58_dxaLeft1;
    protected byte field_59_jc;
    protected boolean field_5_fPageBreakBefore;
    protected boolean field_60_fNoAllowOverlap;
    protected BorderCode field_61_brcTop;
    protected BorderCode field_62_brcLeft;
    protected BorderCode field_63_brcBottom;
    protected BorderCode field_64_brcRight;
    protected BorderCode field_65_brcBetween;
    protected BorderCode field_66_brcBar;
    protected ShadingDescriptor field_67_shd;
    protected byte[] field_68_anld;
    protected byte[] field_69_phe;
    protected byte field_6_brcl;
    protected boolean field_70_fPropRMark;
    protected int field_71_ibstPropRMark;
    protected DateAndTime field_72_dttmPropRMark;
    protected int field_73_itbdMac;
    protected int[] field_74_rgdxaTab;
    protected byte[] field_75_rgtbd;
    protected byte[] field_76_numrm;
    protected byte[] field_77_ptap;
    protected byte field_7_brcp;
    protected byte field_8_ilvl;
    protected int field_9_ilfo;

    protected PAPAbstractType() {
        this.field_11_lspd = new LineSpacingDescriptor();
        this.field_11_lspd = new LineSpacingDescriptor();
        this.field_28_dcs = new DropCapSpecifier();
        this.field_32_fWidowControl = true;
        this.field_41_lvl = 9;
        this.field_61_brcTop = new BorderCode();
        this.field_62_brcLeft = new BorderCode();
        this.field_63_brcBottom = new BorderCode();
        this.field_64_brcRight = new BorderCode();
        this.field_65_brcBetween = new BorderCode();
        this.field_66_brcBar = new BorderCode();
        this.field_67_shd = new ShadingDescriptor();
        this.field_68_anld = new byte[0];
        this.field_69_phe = new byte[0];
        this.field_72_dttmPropRMark = new DateAndTime();
        this.field_74_rgdxaTab = new int[0];
        this.field_75_rgtbd = new byte[0];
        this.field_76_numrm = new byte[0];
        this.field_77_ptap = new byte[0];
    }

    public String toString() {
        return "[PAP]\n" + "    .istd                 = " + " (" + getIstd() + " )\n" + "    .fSideBySide          = " + " (" + getFSideBySide() + " )\n" + "    .fKeep                = " + " (" + getFKeep() + " )\n" + "    .fKeepFollow          = " + " (" + getFKeepFollow() + " )\n" + "    .fPageBreakBefore     = " + " (" + getFPageBreakBefore() + " )\n" + "    .brcl                 = " + " (" + getBrcl() + " )\n" + "    .brcp                 = " + " (" + getBrcp() + " )\n" + "    .ilvl                 = " + " (" + getIlvl() + " )\n" + "    .ilfo                 = " + " (" + getIlfo() + " )\n" + "    .fNoLnn               = " + " (" + getFNoLnn() + " )\n" + "    .lspd                 = " + " (" + getLspd() + " )\n" + "    .dyaBefore            = " + " (" + getDyaBefore() + " )\n" + "    .dyaAfter             = " + " (" + getDyaAfter() + " )\n" + "    .fInTable             = " + " (" + getFInTable() + " )\n" + "    .finTableW97          = " + " (" + getFinTableW97() + " )\n" + "    .fTtp                 = " + " (" + getFTtp() + " )\n" + "    .dxaAbs               = " + " (" + getDxaAbs() + " )\n" + "    .dyaAbs               = " + " (" + getDyaAbs() + " )\n" + "    .dxaWidth             = " + " (" + getDxaWidth() + " )\n" + "    .fBrLnAbove           = " + " (" + getFBrLnAbove() + " )\n" + "    .fBrLnBelow           = " + " (" + getFBrLnBelow() + " )\n" + "    .pcVert               = " + " (" + getPcVert() + " )\n" + "    .pcHorz               = " + " (" + getPcHorz() + " )\n" + "    .wr                   = " + " (" + getWr() + " )\n" + "    .fNoAutoHyph          = " + " (" + getFNoAutoHyph() + " )\n" + "    .dyaHeight            = " + " (" + getDyaHeight() + " )\n" + "    .fMinHeight           = " + " (" + getFMinHeight() + " )\n" + "    .dcs                  = " + " (" + getDcs() + " )\n" + "    .dyaFromText          = " + " (" + getDyaFromText() + " )\n" + "    .dxaFromText          = " + " (" + getDxaFromText() + " )\n" + "    .fLocked              = " + " (" + getFLocked() + " )\n" + "    .fWidowControl        = " + " (" + getFWidowControl() + " )\n" + "    .fKinsoku             = " + " (" + getFKinsoku() + " )\n" + "    .fWordWrap            = " + " (" + getFWordWrap() + " )\n" + "    .fOverflowPunct       = " + " (" + getFOverflowPunct() + " )\n" + "    .fTopLinePunct        = " + " (" + getFTopLinePunct() + " )\n" + "    .fAutoSpaceDE         = " + " (" + getFAutoSpaceDE() + " )\n" + "    .fAutoSpaceDN         = " + " (" + getFAutoSpaceDN() + " )\n" + "    .wAlignFont           = " + " (" + getWAlignFont() + " )\n" + "    .fontAlign            = " + " (" + getFontAlign() + " )\n" + "         .fVertical                = " + isFVertical() + 10 + "         .fBackward                = " + isFBackward() + 10 + "         .fRotateFont              = " + isFRotateFont() + 10 + "    .lvl                  = " + " (" + getLvl() + " )\n" + "    .fBiDi                = " + " (" + getFBiDi() + " )\n" + "    .fNumRMIns            = " + " (" + getFNumRMIns() + " )\n" + "    .fCrLf                = " + " (" + getFCrLf() + " )\n" + "    .fUsePgsuSettings     = " + " (" + getFUsePgsuSettings() + " )\n" + "    .fAdjustRight         = " + " (" + getFAdjustRight() + " )\n" + "    .itap                 = " + " (" + getItap() + " )\n" + "    .fInnerTableCell      = " + " (" + getFInnerTableCell() + " )\n" + "    .fOpenTch             = " + " (" + getFOpenTch() + " )\n" + "    .fTtpEmbedded         = " + " (" + getFTtpEmbedded() + " )\n" + "    .dxcRight             = " + " (" + getDxcRight() + " )\n" + "    .dxcLeft              = " + " (" + getDxcLeft() + " )\n" + "    .dxcLeft1             = " + " (" + getDxcLeft1() + " )\n" + "    .fDyaBeforeAuto       = " + " (" + getFDyaBeforeAuto() + " )\n" + "    .fDyaAfterAuto        = " + " (" + getFDyaAfterAuto() + " )\n" + "    .dxaRight             = " + " (" + getDxaRight() + " )\n" + "    .dxaLeft              = " + " (" + getDxaLeft() + " )\n" + "    .dxaLeft1             = " + " (" + getDxaLeft1() + " )\n" + "    .jc                   = " + " (" + getJc() + " )\n" + "    .fNoAllowOverlap      = " + " (" + getFNoAllowOverlap() + " )\n" + "    .brcTop               = " + " (" + getBrcTop() + " )\n" + "    .brcLeft              = " + " (" + getBrcLeft() + " )\n" + "    .brcBottom            = " + " (" + getBrcBottom() + " )\n" + "    .brcRight             = " + " (" + getBrcRight() + " )\n" + "    .brcBetween           = " + " (" + getBrcBetween() + " )\n" + "    .brcBar               = " + " (" + getBrcBar() + " )\n" + "    .shd                  = " + " (" + getShd() + " )\n" + "    .anld                 = " + " (" + getAnld() + " )\n" + "    .phe                  = " + " (" + getPhe() + " )\n" + "    .fPropRMark           = " + " (" + getFPropRMark() + " )\n" + "    .ibstPropRMark        = " + " (" + getIbstPropRMark() + " )\n" + "    .dttmPropRMark        = " + " (" + getDttmPropRMark() + " )\n" + "    .itbdMac              = " + " (" + getItbdMac() + " )\n" + "    .rgdxaTab             = " + " (" + getRgdxaTab() + " )\n" + "    .rgtbd                = " + " (" + getRgtbd() + " )\n" + "    .numrm                = " + " (" + getNumrm() + " )\n" + "    .ptap                 = " + " (" + getPtap() + " )\n" + "[/PAP]\n";
    }

    public int getIstd() {
        return this.field_1_istd;
    }

    public void setIstd(int i) {
        this.field_1_istd = i;
    }

    public boolean getFSideBySide() {
        return this.field_2_fSideBySide;
    }

    public void setFSideBySide(boolean z) {
        this.field_2_fSideBySide = z;
    }

    public boolean getFKeep() {
        return this.field_3_fKeep;
    }

    public void setFKeep(boolean z) {
        this.field_3_fKeep = z;
    }

    public boolean getFKeepFollow() {
        return this.field_4_fKeepFollow;
    }

    public void setFKeepFollow(boolean z) {
        this.field_4_fKeepFollow = z;
    }

    public boolean getFPageBreakBefore() {
        return this.field_5_fPageBreakBefore;
    }

    public void setFPageBreakBefore(boolean z) {
        this.field_5_fPageBreakBefore = z;
    }

    public byte getBrcl() {
        return this.field_6_brcl;
    }

    public void setBrcl(byte b) {
        this.field_6_brcl = b;
    }

    public byte getBrcp() {
        return this.field_7_brcp;
    }

    public void setBrcp(byte b) {
        this.field_7_brcp = b;
    }

    public byte getIlvl() {
        return this.field_8_ilvl;
    }

    public void setIlvl(byte b) {
        this.field_8_ilvl = b;
    }

    public int getIlfo() {
        return this.field_9_ilfo;
    }

    public void setIlfo(int i) {
        this.field_9_ilfo = i;
    }

    public boolean getFNoLnn() {
        return this.field_10_fNoLnn;
    }

    public void setFNoLnn(boolean z) {
        this.field_10_fNoLnn = z;
    }

    public LineSpacingDescriptor getLspd() {
        return this.field_11_lspd;
    }

    public void setLspd(LineSpacingDescriptor lineSpacingDescriptor) {
        this.field_11_lspd = lineSpacingDescriptor;
    }

    public int getDyaBefore() {
        return this.field_12_dyaBefore;
    }

    public void setDyaBefore(int i) {
        this.field_12_dyaBefore = i;
    }

    public int getDyaAfter() {
        return this.field_13_dyaAfter;
    }

    public void setDyaAfter(int i) {
        this.field_13_dyaAfter = i;
    }

    public boolean getFInTable() {
        return this.field_14_fInTable;
    }

    public void setFInTable(boolean z) {
        this.field_14_fInTable = z;
    }

    public boolean getFinTableW97() {
        return this.field_15_finTableW97;
    }

    public void setFinTableW97(boolean z) {
        this.field_15_finTableW97 = z;
    }

    public boolean getFTtp() {
        return this.field_16_fTtp;
    }

    public void setFTtp(boolean z) {
        this.field_16_fTtp = z;
    }

    public int getDxaAbs() {
        return this.field_17_dxaAbs;
    }

    public void setDxaAbs(int i) {
        this.field_17_dxaAbs = i;
    }

    public int getDyaAbs() {
        return this.field_18_dyaAbs;
    }

    public void setDyaAbs(int i) {
        this.field_18_dyaAbs = i;
    }

    public int getDxaWidth() {
        return this.field_19_dxaWidth;
    }

    public void setDxaWidth(int i) {
        this.field_19_dxaWidth = i;
    }

    public boolean getFBrLnAbove() {
        return this.field_20_fBrLnAbove;
    }

    public void setFBrLnAbove(boolean z) {
        this.field_20_fBrLnAbove = z;
    }

    public boolean getFBrLnBelow() {
        return this.field_21_fBrLnBelow;
    }

    public void setFBrLnBelow(boolean z) {
        this.field_21_fBrLnBelow = z;
    }

    public byte getPcVert() {
        return this.field_22_pcVert;
    }

    public void setPcVert(byte b) {
        this.field_22_pcVert = b;
    }

    public byte getPcHorz() {
        return this.field_23_pcHorz;
    }

    public void setPcHorz(byte b) {
        this.field_23_pcHorz = b;
    }

    public byte getWr() {
        return this.field_24_wr;
    }

    public void setWr(byte b) {
        this.field_24_wr = b;
    }

    public boolean getFNoAutoHyph() {
        return this.field_25_fNoAutoHyph;
    }

    public void setFNoAutoHyph(boolean z) {
        this.field_25_fNoAutoHyph = z;
    }

    public int getDyaHeight() {
        return this.field_26_dyaHeight;
    }

    public void setDyaHeight(int i) {
        this.field_26_dyaHeight = i;
    }

    public boolean getFMinHeight() {
        return this.field_27_fMinHeight;
    }

    public void setFMinHeight(boolean z) {
        this.field_27_fMinHeight = z;
    }

    public DropCapSpecifier getDcs() {
        return this.field_28_dcs;
    }

    public void setDcs(DropCapSpecifier dropCapSpecifier) {
        this.field_28_dcs = dropCapSpecifier;
    }

    public int getDyaFromText() {
        return this.field_29_dyaFromText;
    }

    public void setDyaFromText(int i) {
        this.field_29_dyaFromText = i;
    }

    public int getDxaFromText() {
        return this.field_30_dxaFromText;
    }

    public void setDxaFromText(int i) {
        this.field_30_dxaFromText = i;
    }

    public boolean getFLocked() {
        return this.field_31_fLocked;
    }

    public void setFLocked(boolean z) {
        this.field_31_fLocked = z;
    }

    public boolean getFWidowControl() {
        return this.field_32_fWidowControl;
    }

    public void setFWidowControl(boolean z) {
        this.field_32_fWidowControl = z;
    }

    public boolean getFKinsoku() {
        return this.field_33_fKinsoku;
    }

    public void setFKinsoku(boolean z) {
        this.field_33_fKinsoku = z;
    }

    public boolean getFWordWrap() {
        return this.field_34_fWordWrap;
    }

    public void setFWordWrap(boolean z) {
        this.field_34_fWordWrap = z;
    }

    public boolean getFOverflowPunct() {
        return this.field_35_fOverflowPunct;
    }

    public void setFOverflowPunct(boolean z) {
        this.field_35_fOverflowPunct = z;
    }

    public boolean getFTopLinePunct() {
        return this.field_36_fTopLinePunct;
    }

    public void setFTopLinePunct(boolean z) {
        this.field_36_fTopLinePunct = z;
    }

    public boolean getFAutoSpaceDE() {
        return this.field_37_fAutoSpaceDE;
    }

    public void setFAutoSpaceDE(boolean z) {
        this.field_37_fAutoSpaceDE = z;
    }

    public boolean getFAutoSpaceDN() {
        return this.field_38_fAutoSpaceDN;
    }

    public void setFAutoSpaceDN(boolean z) {
        this.field_38_fAutoSpaceDN = z;
    }

    public int getWAlignFont() {
        return this.field_39_wAlignFont;
    }

    public void setWAlignFont(int i) {
        this.field_39_wAlignFont = i;
    }

    public short getFontAlign() {
        return this.field_40_fontAlign;
    }

    public void setFontAlign(short s) {
        this.field_40_fontAlign = s;
    }

    public byte getLvl() {
        return this.field_41_lvl;
    }

    public void setLvl(byte b) {
        this.field_41_lvl = b;
    }

    public boolean getFBiDi() {
        return this.field_42_fBiDi;
    }

    public void setFBiDi(boolean z) {
        this.field_42_fBiDi = z;
    }

    public boolean getFNumRMIns() {
        return this.field_43_fNumRMIns;
    }

    public void setFNumRMIns(boolean z) {
        this.field_43_fNumRMIns = z;
    }

    public boolean getFCrLf() {
        return this.field_44_fCrLf;
    }

    public void setFCrLf(boolean z) {
        this.field_44_fCrLf = z;
    }

    public boolean getFUsePgsuSettings() {
        return this.field_45_fUsePgsuSettings;
    }

    public void setFUsePgsuSettings(boolean z) {
        this.field_45_fUsePgsuSettings = z;
    }

    public boolean getFAdjustRight() {
        return this.field_46_fAdjustRight;
    }

    public void setFAdjustRight(boolean z) {
        this.field_46_fAdjustRight = z;
    }

    public int getItap() {
        return this.field_47_itap;
    }

    public void setItap(int i) {
        this.field_47_itap = i;
    }

    public boolean getFInnerTableCell() {
        return this.field_48_fInnerTableCell;
    }

    public void setFInnerTableCell(boolean z) {
        this.field_48_fInnerTableCell = z;
    }

    public boolean getFOpenTch() {
        return this.field_49_fOpenTch;
    }

    public void setFOpenTch(boolean z) {
        this.field_49_fOpenTch = z;
    }

    public boolean getFTtpEmbedded() {
        return this.field_50_fTtpEmbedded;
    }

    public void setFTtpEmbedded(boolean z) {
        this.field_50_fTtpEmbedded = z;
    }

    public short getDxcRight() {
        return this.field_51_dxcRight;
    }

    public void setDxcRight(short s) {
        this.field_51_dxcRight = s;
    }

    public short getDxcLeft() {
        return this.field_52_dxcLeft;
    }

    public void setDxcLeft(short s) {
        this.field_52_dxcLeft = s;
    }

    public short getDxcLeft1() {
        return this.field_53_dxcLeft1;
    }

    public void setDxcLeft1(short s) {
        this.field_53_dxcLeft1 = s;
    }

    public boolean getFDyaBeforeAuto() {
        return this.field_54_fDyaBeforeAuto;
    }

    public void setFDyaBeforeAuto(boolean z) {
        this.field_54_fDyaBeforeAuto = z;
    }

    public boolean getFDyaAfterAuto() {
        return this.field_55_fDyaAfterAuto;
    }

    public void setFDyaAfterAuto(boolean z) {
        this.field_55_fDyaAfterAuto = z;
    }

    public int getDxaRight() {
        return this.field_56_dxaRight;
    }

    public void setDxaRight(int i) {
        this.field_56_dxaRight = i;
    }

    public int getDxaLeft() {
        return this.field_57_dxaLeft;
    }

    public void setDxaLeft(int i) {
        this.field_57_dxaLeft = i;
    }

    public int getDxaLeft1() {
        return this.field_58_dxaLeft1;
    }

    public void setDxaLeft1(int i) {
        this.field_58_dxaLeft1 = i;
    }

    public byte getJc() {
        return this.field_59_jc;
    }

    public void setJc(byte b) {
        this.field_59_jc = b;
    }

    public boolean getFNoAllowOverlap() {
        return this.field_60_fNoAllowOverlap;
    }

    public void setFNoAllowOverlap(boolean z) {
        this.field_60_fNoAllowOverlap = z;
    }

    public BorderCode getBrcTop() {
        return this.field_61_brcTop;
    }

    public void setBrcTop(BorderCode borderCode) {
        this.field_61_brcTop = borderCode;
    }

    public BorderCode getBrcLeft() {
        return this.field_62_brcLeft;
    }

    public void setBrcLeft(BorderCode borderCode) {
        this.field_62_brcLeft = borderCode;
    }

    public BorderCode getBrcBottom() {
        return this.field_63_brcBottom;
    }

    public void setBrcBottom(BorderCode borderCode) {
        this.field_63_brcBottom = borderCode;
    }

    public BorderCode getBrcRight() {
        return this.field_64_brcRight;
    }

    public void setBrcRight(BorderCode borderCode) {
        this.field_64_brcRight = borderCode;
    }

    public BorderCode getBrcBetween() {
        return this.field_65_brcBetween;
    }

    public void setBrcBetween(BorderCode borderCode) {
        this.field_65_brcBetween = borderCode;
    }

    public BorderCode getBrcBar() {
        return this.field_66_brcBar;
    }

    public void setBrcBar(BorderCode borderCode) {
        this.field_66_brcBar = borderCode;
    }

    public ShadingDescriptor getShd() {
        return this.field_67_shd;
    }

    public void setShd(ShadingDescriptor shadingDescriptor) {
        this.field_67_shd = shadingDescriptor;
    }

    public byte[] getAnld() {
        return this.field_68_anld;
    }

    public void setAnld(byte[] bArr) {
        this.field_68_anld = bArr;
    }

    public byte[] getPhe() {
        return this.field_69_phe;
    }

    public void setPhe(byte[] bArr) {
        this.field_69_phe = bArr;
    }

    public boolean getFPropRMark() {
        return this.field_70_fPropRMark;
    }

    public void setFPropRMark(boolean z) {
        this.field_70_fPropRMark = z;
    }

    public int getIbstPropRMark() {
        return this.field_71_ibstPropRMark;
    }

    public void setIbstPropRMark(int i) {
        this.field_71_ibstPropRMark = i;
    }

    public DateAndTime getDttmPropRMark() {
        return this.field_72_dttmPropRMark;
    }

    public void setDttmPropRMark(DateAndTime dateAndTime) {
        this.field_72_dttmPropRMark = dateAndTime;
    }

    public int getItbdMac() {
        return this.field_73_itbdMac;
    }

    public void setItbdMac(int i) {
        this.field_73_itbdMac = i;
    }

    public int[] getRgdxaTab() {
        return this.field_74_rgdxaTab;
    }

    public void setRgdxaTab(int[] iArr) {
        this.field_74_rgdxaTab = iArr;
    }

    public byte[] getRgtbd() {
        return this.field_75_rgtbd;
    }

    public void setRgtbd(byte[] bArr) {
        this.field_75_rgtbd = bArr;
    }

    public byte[] getNumrm() {
        return this.field_76_numrm;
    }

    public void setNumrm(byte[] bArr) {
        this.field_76_numrm = bArr;
    }

    public byte[] getPtap() {
        return this.field_77_ptap;
    }

    public void setPtap(byte[] bArr) {
        this.field_77_ptap = bArr;
    }

    public void setFVertical(boolean z) {
        this.field_40_fontAlign = (short) fVertical.setBoolean(this.field_40_fontAlign, z);
    }

    public boolean isFVertical() {
        return fVertical.isSet(this.field_40_fontAlign);
    }

    public void setFBackward(boolean z) {
        this.field_40_fontAlign = (short) fBackward.setBoolean(this.field_40_fontAlign, z);
    }

    public boolean isFBackward() {
        return fBackward.isSet(this.field_40_fontAlign);
    }

    public void setFRotateFont(boolean z) {
        this.field_40_fontAlign = (short) fRotateFont.setBoolean(this.field_40_fontAlign, z);
    }

    public boolean isFRotateFont() {
        return fRotateFont.isSet(this.field_40_fontAlign);
    }
}
