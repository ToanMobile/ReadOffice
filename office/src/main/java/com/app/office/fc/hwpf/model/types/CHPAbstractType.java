package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.hwpf.model.Colorref;
import com.app.office.fc.hwpf.model.Hyphenation;
import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.hwpf.usermodel.DateAndTime;
import com.app.office.fc.hwpf.usermodel.ShadingDescriptor;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;

@Internal
public abstract class CHPAbstractType {
    protected static final byte ISS_NONE = 0;
    protected static final byte ISS_SUBSCRIPTED = 2;
    protected static final byte ISS_SUPERSCRIPTED = 1;
    protected static final byte KCD_CIRCLE = 3;
    protected static final byte KCD_COMMA = 2;
    protected static final byte KCD_DOT = 1;
    protected static final byte KCD_NON = 0;
    protected static final byte KCD_UNDER_DOT = 4;
    protected static final byte KUL_BY_WORD = 2;
    protected static final byte KUL_DASH = 7;
    protected static final byte KUL_DASHED_HEAVY = 23;
    protected static final byte KUL_DASH_LONG = 39;
    protected static final byte KUL_DASH_LONG_HEAVY = 55;
    protected static final byte KUL_DOT = 8;
    protected static final byte KUL_DOTTED = 4;
    protected static final byte KUL_DOTTED_HEAVY = 20;
    protected static final byte KUL_DOT_DASH = 9;
    protected static final byte KUL_DOT_DASH_HEAVY = 25;
    protected static final byte KUL_DOT_DOT_DASH = 10;
    protected static final byte KUL_DOT_DOT_DASH_HEAVY = 26;
    protected static final byte KUL_DOUBLE = 3;
    protected static final byte KUL_HIDDEN = 5;
    protected static final byte KUL_NONE = 0;
    protected static final byte KUL_SINGLE = 1;
    protected static final byte KUL_THICK = 6;
    protected static final byte KUL_WAVE = 11;
    protected static final byte KUL_WAVE_DOUBLE = 43;
    protected static final byte KUL_WAVE_HEAVY = 27;
    protected static final byte LBRCRJ_BOTH = 3;
    protected static final byte LBRCRJ_LEFT = 1;
    protected static final byte LBRCRJ_NONE = 0;
    protected static final byte LBRCRJ_RIGHT = 2;
    protected static final byte SFXTTEXT_BACKGROUND_BLINK = 2;
    protected static final byte SFXTTEXT_LAS_VEGAS_LIGHTS = 1;
    protected static final byte SFXTTEXT_MARCHING_ANTS = 4;
    protected static final byte SFXTTEXT_MARCHING_RED_ANTS = 5;
    protected static final byte SFXTTEXT_NO = 0;
    protected static final byte SFXTTEXT_SHIMMER = 6;
    protected static final byte SFXTTEXT_SPARKLE_TEXT = 3;
    private static BitField fBiDi = new BitField(8388608);
    private static BitField fBold = new BitField(1);
    private static BitField fBoldBi = new BitField(1048576);
    private static BitField fCaps = new BitField(64);
    private static BitField fCellFitText = new BitField(64);
    private static BitField fChsDiff = new BitField(1);
    private static BitField fComplexScripts = new BitField(2097152);
    private static BitField fDStrike = new BitField(262144);
    private static BitField fData = new BitField(16384);
    private static BitField fEmboss = new BitField(65536);
    private static BitField fFldVanish = new BitField(16);
    private static BitField fHighlight = new BitField(32);
    private static BitField fImprint = new BitField(131072);
    private static BitField fItalic = new BitField(2);
    private static BitField fItalicBi = new BitField(4194304);
    private static BitField fKumimoji = new BitField(1024);
    private static BitField fLSFitText = new BitField(4096);
    private static BitField fLowerCase = new BitField(8192);
    private static BitField fMacChs = new BitField(32);
    private static BitField fObj = new BitField(2048);
    private static BitField fOle2 = new BitField(32768);
    private static BitField fOutline = new BitField(8);
    private static BitField fRMark = new BitField(256);
    private static BitField fRMarkDel = new BitField(4);
    private static BitField fRuby = new BitField(2048);
    private static BitField fShadow = new BitField(4096);
    private static BitField fSmallCaps = new BitField(32);
    private static BitField fSpec = new BitField(512);
    private static BitField fStrike = new BitField(1024);
    private static BitField fTNY = new BitField(256);
    private static BitField fTNYCompress = new BitField(16);
    private static BitField fTNYFetchTxm = new BitField(32);
    private static BitField fUsePgsuSettings = new BitField(524288);
    private static BitField fVanish = new BitField(128);
    private static BitField fWarichu = new BitField(512);
    private static BitField fWarichuNoOpenBracket = new BitField(8);
    private static BitField iWarichuBracket = new BitField(7);
    private static BitField icoHighlight = new BitField(31);
    private static BitField itypFELayout = new BitField(255);
    private static BitField spare = new BitField(57344);
    private static BitField unused = new BitField(128);
    protected int field_10_pctCharWidth;
    protected int field_11_lidDefault = 1024;
    protected int field_12_lidFE = 1024;
    protected byte field_13_kcd;
    protected boolean field_14_fUndetermine;
    protected byte field_15_iss;
    protected boolean field_16_fSpecSymbol;
    protected byte field_17_idct;
    protected byte field_18_idctHint;
    protected byte field_19_kul;
    protected int field_1_grpfChp;
    protected Hyphenation field_20_hresi = new Hyphenation();
    protected int field_21_hpsKern;
    protected short field_22_hpsPos;
    protected ShadingDescriptor field_23_shd = new ShadingDescriptor();
    protected BorderCode field_24_brc = new BorderCode();
    protected int field_25_ibstRMark;
    protected byte field_26_sfxtText;
    protected boolean field_27_fDblBdr;
    protected boolean field_28_fBorderWS;
    protected short field_29_ufel;
    protected int field_2_hps = 20;
    protected byte field_30_copt;
    protected int field_31_hpsAsci;
    protected int field_32_hpsFE;
    protected int field_33_hpsBi;
    protected int field_34_ftcSym;
    protected int field_35_xchSym;
    protected int field_36_fcPic = -1;
    protected int field_37_fcObj;
    protected int field_38_lTagObj;
    protected int field_39_fcData;
    protected int field_3_ftcAscii;
    protected Hyphenation field_40_hresiOld = new Hyphenation();
    protected int field_41_ibstRMarkDel;
    protected DateAndTime field_42_dttmRMark = new DateAndTime();
    protected DateAndTime field_43_dttmRMarkDel = new DateAndTime();
    protected int field_44_istd = 10;
    protected int field_45_idslRMReason;
    protected int field_46_idslReasonDel;
    protected int field_47_cpg;
    protected short field_48_Highlight;
    protected short field_49_CharsetFlags;
    protected int field_4_ftcFE;
    protected short field_50_chse;
    protected boolean field_51_fPropRMark;
    protected int field_52_ibstPropRMark;
    protected DateAndTime field_53_dttmPropRMark = new DateAndTime();
    protected boolean field_54_fConflictOrig;
    protected boolean field_55_fConflictOtherDel;
    protected int field_56_wConflict;
    protected int field_57_IbstConflict;
    protected DateAndTime field_58_dttmConflict = new DateAndTime();
    protected boolean field_59_fDispFldRMark;
    protected int field_5_ftcOther;
    protected int field_60_ibstDispFldRMark;
    protected DateAndTime field_61_dttmDispFldRMark = new DateAndTime();
    protected byte[] field_62_xstDispFldRMark = new byte[0];
    protected int field_63_fcObjp;
    protected byte field_64_lbrCRJ;
    protected boolean field_65_fSpecVanish;
    protected boolean field_66_fHasOldProps;
    protected boolean field_67_fSdtVanish;
    protected int field_68_wCharScale = 100;
    protected Colorref field_69_underlineColor;
    protected int field_6_ftcBi;
    protected int field_7_dxaSpace;
    protected Colorref field_8_cv = new Colorref();
    protected byte field_9_ico;

    protected CHPAbstractType() {
    }

    public String toString() {
        return "[CHP]\n" + "    .grpfChp              = " + " (" + getGrpfChp() + " )\n" + "         .fBold                    = " + isFBold() + 10 + "         .fItalic                  = " + isFItalic() + 10 + "         .fRMarkDel                = " + isFRMarkDel() + 10 + "         .fOutline                 = " + isFOutline() + 10 + "         .fFldVanish               = " + isFFldVanish() + 10 + "         .fSmallCaps               = " + isFSmallCaps() + 10 + "         .fCaps                    = " + isFCaps() + 10 + "         .fVanish                  = " + isFVanish() + 10 + "         .fRMark                   = " + isFRMark() + 10 + "         .fSpec                    = " + isFSpec() + 10 + "         .fStrike                  = " + isFStrike() + 10 + "         .fObj                     = " + isFObj() + 10 + "         .fShadow                  = " + isFShadow() + 10 + "         .fLowerCase               = " + isFLowerCase() + 10 + "         .fData                    = " + isFData() + 10 + "         .fOle2                    = " + isFOle2() + 10 + "         .fEmboss                  = " + isFEmboss() + 10 + "         .fImprint                 = " + isFImprint() + 10 + "         .fDStrike                 = " + isFDStrike() + 10 + "         .fUsePgsuSettings         = " + isFUsePgsuSettings() + 10 + "         .fBoldBi                  = " + isFBoldBi() + 10 + "         .fComplexScripts          = " + isFComplexScripts() + 10 + "         .fItalicBi                = " + isFItalicBi() + 10 + "         .fBiDi                    = " + isFBiDi() + 10 + "    .hps                  = " + " (" + getHps() + " )\n" + "    .ftcAscii             = " + " (" + getFtcAscii() + " )\n" + "    .ftcFE                = " + " (" + getFtcFE() + " )\n" + "    .ftcOther             = " + " (" + getFtcOther() + " )\n" + "    .ftcBi                = " + " (" + getFtcBi() + " )\n" + "    .dxaSpace             = " + " (" + getDxaSpace() + " )\n" + "    .cv                   = " + " (" + getCv() + " )\n" + "    .ico                  = " + " (" + getIco() + " )\n" + "    .pctCharWidth         = " + " (" + getPctCharWidth() + " )\n" + "    .lidDefault           = " + " (" + getLidDefault() + " )\n" + "    .lidFE                = " + " (" + getLidFE() + " )\n" + "    .kcd                  = " + " (" + getKcd() + " )\n" + "    .fUndetermine         = " + " (" + getFUndetermine() + " )\n" + "    .iss                  = " + " (" + getIss() + " )\n" + "    .fSpecSymbol          = " + " (" + getFSpecSymbol() + " )\n" + "    .idct                 = " + " (" + getIdct() + " )\n" + "    .idctHint             = " + " (" + getIdctHint() + " )\n" + "    .kul                  = " + " (" + getKul() + " )\n" + "    .hresi                = " + " (" + getHresi() + " )\n" + "    .hpsKern              = " + " (" + getHpsKern() + " )\n" + "    .hpsPos               = " + " (" + getHpsPos() + " )\n" + "    .shd                  = " + " (" + getShd() + " )\n" + "    .brc                  = " + " (" + getBrc() + " )\n" + "    .ibstRMark            = " + " (" + getIbstRMark() + " )\n" + "    .sfxtText             = " + " (" + getSfxtText() + " )\n" + "    .fDblBdr              = " + " (" + getFDblBdr() + " )\n" + "    .fBorderWS            = " + " (" + getFBorderWS() + " )\n" + "    .ufel                 = " + " (" + getUfel() + " )\n" + "         .itypFELayout             = " + getItypFELayout() + 10 + "         .fTNY                     = " + isFTNY() + 10 + "         .fWarichu                 = " + isFWarichu() + 10 + "         .fKumimoji                = " + isFKumimoji() + 10 + "         .fRuby                    = " + isFRuby() + 10 + "         .fLSFitText               = " + isFLSFitText() + 10 + "         .spare                    = " + getSpare() + 10 + "    .copt                 = " + " (" + getCopt() + " )\n" + "         .iWarichuBracket          = " + getIWarichuBracket() + 10 + "         .fWarichuNoOpenBracket     = " + isFWarichuNoOpenBracket() + 10 + "         .fTNYCompress             = " + isFTNYCompress() + 10 + "         .fTNYFetchTxm             = " + isFTNYFetchTxm() + 10 + "         .fCellFitText             = " + isFCellFitText() + 10 + "         .unused                   = " + isUnused() + 10 + "    .hpsAsci              = " + " (" + getHpsAsci() + " )\n" + "    .hpsFE                = " + " (" + getHpsFE() + " )\n" + "    .hpsBi                = " + " (" + getHpsBi() + " )\n" + "    .ftcSym               = " + " (" + getFtcSym() + " )\n" + "    .xchSym               = " + " (" + getXchSym() + " )\n" + "    .fcPic                = " + " (" + getFcPic() + " )\n" + "    .fcObj                = " + " (" + getFcObj() + " )\n" + "    .lTagObj              = " + " (" + getLTagObj() + " )\n" + "    .fcData               = " + " (" + getFcData() + " )\n" + "    .hresiOld             = " + " (" + getHresiOld() + " )\n" + "    .ibstRMarkDel         = " + " (" + getIbstRMarkDel() + " )\n" + "    .dttmRMark            = " + " (" + getDttmRMark() + " )\n" + "    .dttmRMarkDel         = " + " (" + getDttmRMarkDel() + " )\n" + "    .istd                 = " + " (" + getIstd() + " )\n" + "    .idslRMReason         = " + " (" + getIdslRMReason() + " )\n" + "    .idslReasonDel        = " + " (" + getIdslReasonDel() + " )\n" + "    .cpg                  = " + " (" + getCpg() + " )\n" + "    .Highlight            = " + " (" + getHighlight() + " )\n" + "         .icoHighlight             = " + getIcoHighlight() + 10 + "         .fHighlight               = " + isFHighlight() + 10 + "    .CharsetFlags         = " + " (" + getCharsetFlags() + " )\n" + "         .fChsDiff                 = " + isFChsDiff() + 10 + "         .fMacChs                  = " + isFMacChs() + 10 + "    .chse                 = " + " (" + getChse() + " )\n" + "    .fPropRMark           = " + " (" + getFPropRMark() + " )\n" + "    .ibstPropRMark        = " + " (" + getIbstPropRMark() + " )\n" + "    .dttmPropRMark        = " + " (" + getDttmPropRMark() + " )\n" + "    .fConflictOrig        = " + " (" + getFConflictOrig() + " )\n" + "    .fConflictOtherDel    = " + " (" + getFConflictOtherDel() + " )\n" + "    .wConflict            = " + " (" + getWConflict() + " )\n" + "    .IbstConflict         = " + " (" + getIbstConflict() + " )\n" + "    .dttmConflict         = " + " (" + getDttmConflict() + " )\n" + "    .fDispFldRMark        = " + " (" + getFDispFldRMark() + " )\n" + "    .ibstDispFldRMark     = " + " (" + getIbstDispFldRMark() + " )\n" + "    .dttmDispFldRMark     = " + " (" + getDttmDispFldRMark() + " )\n" + "    .xstDispFldRMark      = " + " (" + getXstDispFldRMark() + " )\n" + "    .fcObjp               = " + " (" + getFcObjp() + " )\n" + "    .lbrCRJ               = " + " (" + getLbrCRJ() + " )\n" + "    .fSpecVanish          = " + " (" + getFSpecVanish() + " )\n" + "    .fHasOldProps         = " + " (" + getFHasOldProps() + " )\n" + "    .fSdtVanish           = " + " (" + getFSdtVanish() + " )\n" + "    .wCharScale           = " + " (" + getWCharScale() + " )\n" + "[/CHP]\n";
    }

    @Internal
    public int getGrpfChp() {
        return this.field_1_grpfChp;
    }

    @Internal
    public void setGrpfChp(int i) {
        this.field_1_grpfChp = i;
    }

    @Internal
    public int getHps() {
        return this.field_2_hps;
    }

    @Internal
    public void setHps(int i) {
        this.field_2_hps = i;
    }

    @Internal
    public int getFtcAscii() {
        return this.field_3_ftcAscii;
    }

    @Internal
    public void setFtcAscii(int i) {
        this.field_3_ftcAscii = i;
    }

    @Internal
    public int getFtcFE() {
        return this.field_4_ftcFE;
    }

    @Internal
    public void setFtcFE(int i) {
        this.field_4_ftcFE = i;
    }

    @Internal
    public int getFtcOther() {
        return this.field_5_ftcOther;
    }

    @Internal
    public void setFtcOther(int i) {
        this.field_5_ftcOther = i;
    }

    @Internal
    public int getFtcBi() {
        return this.field_6_ftcBi;
    }

    @Internal
    public void setFtcBi(int i) {
        this.field_6_ftcBi = i;
    }

    @Internal
    public int getDxaSpace() {
        return this.field_7_dxaSpace;
    }

    @Internal
    public void setDxaSpace(int i) {
        this.field_7_dxaSpace = i;
    }

    @Internal
    public Colorref getCv() {
        return this.field_8_cv;
    }

    @Internal
    public void setCv(Colorref colorref) {
        this.field_8_cv = colorref;
    }

    @Internal
    public byte getIco() {
        return this.field_9_ico;
    }

    @Internal
    public void setIco(byte b) {
        this.field_9_ico = b;
    }

    @Internal
    public int getPctCharWidth() {
        return this.field_10_pctCharWidth;
    }

    @Internal
    public void setPctCharWidth(int i) {
        this.field_10_pctCharWidth = i;
    }

    @Internal
    public int getLidDefault() {
        return this.field_11_lidDefault;
    }

    @Internal
    public void setLidDefault(int i) {
        this.field_11_lidDefault = i;
    }

    @Internal
    public int getLidFE() {
        return this.field_12_lidFE;
    }

    @Internal
    public void setLidFE(int i) {
        this.field_12_lidFE = i;
    }

    @Internal
    public byte getKcd() {
        return this.field_13_kcd;
    }

    @Internal
    public void setKcd(byte b) {
        this.field_13_kcd = b;
    }

    @Internal
    public boolean getFUndetermine() {
        return this.field_14_fUndetermine;
    }

    @Internal
    public void setFUndetermine(boolean z) {
        this.field_14_fUndetermine = z;
    }

    @Internal
    public byte getIss() {
        return this.field_15_iss;
    }

    @Internal
    public void setIss(byte b) {
        this.field_15_iss = b;
    }

    @Internal
    public boolean getFSpecSymbol() {
        return this.field_16_fSpecSymbol;
    }

    @Internal
    public void setFSpecSymbol(boolean z) {
        this.field_16_fSpecSymbol = z;
    }

    @Internal
    public byte getIdct() {
        return this.field_17_idct;
    }

    @Internal
    public void setIdct(byte b) {
        this.field_17_idct = b;
    }

    @Internal
    public byte getIdctHint() {
        return this.field_18_idctHint;
    }

    @Internal
    public void setIdctHint(byte b) {
        this.field_18_idctHint = b;
    }

    @Internal
    public byte getKul() {
        return this.field_19_kul;
    }

    @Internal
    public void setKul(byte b) {
        this.field_19_kul = b;
    }

    @Internal
    public Hyphenation getHresi() {
        return this.field_20_hresi;
    }

    @Internal
    public void setHresi(Hyphenation hyphenation) {
        this.field_20_hresi = hyphenation;
    }

    @Internal
    public int getHpsKern() {
        return this.field_21_hpsKern;
    }

    @Internal
    public void setHpsKern(int i) {
        this.field_21_hpsKern = i;
    }

    @Internal
    public short getHpsPos() {
        return this.field_22_hpsPos;
    }

    @Internal
    public void setHpsPos(short s) {
        this.field_22_hpsPos = s;
    }

    @Internal
    public ShadingDescriptor getShd() {
        return this.field_23_shd;
    }

    @Internal
    public void setShd(ShadingDescriptor shadingDescriptor) {
        this.field_23_shd = shadingDescriptor;
    }

    @Internal
    public BorderCode getBrc() {
        return this.field_24_brc;
    }

    @Internal
    public void setBrc(BorderCode borderCode) {
        this.field_24_brc = borderCode;
    }

    @Internal
    public int getIbstRMark() {
        return this.field_25_ibstRMark;
    }

    @Internal
    public void setIbstRMark(int i) {
        this.field_25_ibstRMark = i;
    }

    @Internal
    public byte getSfxtText() {
        return this.field_26_sfxtText;
    }

    @Internal
    public void setSfxtText(byte b) {
        this.field_26_sfxtText = b;
    }

    @Internal
    public boolean getFDblBdr() {
        return this.field_27_fDblBdr;
    }

    @Internal
    public void setFDblBdr(boolean z) {
        this.field_27_fDblBdr = z;
    }

    @Internal
    public boolean getFBorderWS() {
        return this.field_28_fBorderWS;
    }

    @Internal
    public void setFBorderWS(boolean z) {
        this.field_28_fBorderWS = z;
    }

    @Internal
    public short getUfel() {
        return this.field_29_ufel;
    }

    @Internal
    public void setUfel(short s) {
        this.field_29_ufel = s;
    }

    @Internal
    public byte getCopt() {
        return this.field_30_copt;
    }

    @Internal
    public void setCopt(byte b) {
        this.field_30_copt = b;
    }

    @Internal
    public int getHpsAsci() {
        return this.field_31_hpsAsci;
    }

    @Internal
    public void setHpsAsci(int i) {
        this.field_31_hpsAsci = i;
    }

    @Internal
    public int getHpsFE() {
        return this.field_32_hpsFE;
    }

    @Internal
    public void setHpsFE(int i) {
        this.field_32_hpsFE = i;
    }

    @Internal
    public int getHpsBi() {
        return this.field_33_hpsBi;
    }

    @Internal
    public void setHpsBi(int i) {
        this.field_33_hpsBi = i;
    }

    @Internal
    public int getFtcSym() {
        return this.field_34_ftcSym;
    }

    @Internal
    public void setFtcSym(int i) {
        this.field_34_ftcSym = i;
    }

    @Internal
    public int getXchSym() {
        return this.field_35_xchSym;
    }

    @Internal
    public void setXchSym(int i) {
        this.field_35_xchSym = i;
    }

    @Internal
    public int getFcPic() {
        return this.field_36_fcPic;
    }

    @Internal
    public void setFcPic(int i) {
        this.field_36_fcPic = i;
    }

    @Internal
    public int getFcObj() {
        return this.field_37_fcObj;
    }

    @Internal
    public void setFcObj(int i) {
        this.field_37_fcObj = i;
    }

    @Internal
    public int getLTagObj() {
        return this.field_38_lTagObj;
    }

    @Internal
    public void setLTagObj(int i) {
        this.field_38_lTagObj = i;
    }

    @Internal
    public int getFcData() {
        return this.field_39_fcData;
    }

    @Internal
    public void setFcData(int i) {
        this.field_39_fcData = i;
    }

    @Internal
    public Hyphenation getHresiOld() {
        return this.field_40_hresiOld;
    }

    @Internal
    public void setHresiOld(Hyphenation hyphenation) {
        this.field_40_hresiOld = hyphenation;
    }

    @Internal
    public int getIbstRMarkDel() {
        return this.field_41_ibstRMarkDel;
    }

    @Internal
    public void setIbstRMarkDel(int i) {
        this.field_41_ibstRMarkDel = i;
    }

    @Internal
    public DateAndTime getDttmRMark() {
        return this.field_42_dttmRMark;
    }

    @Internal
    public void setDttmRMark(DateAndTime dateAndTime) {
        this.field_42_dttmRMark = dateAndTime;
    }

    @Internal
    public DateAndTime getDttmRMarkDel() {
        return this.field_43_dttmRMarkDel;
    }

    @Internal
    public void setDttmRMarkDel(DateAndTime dateAndTime) {
        this.field_43_dttmRMarkDel = dateAndTime;
    }

    @Internal
    public int getIstd() {
        return this.field_44_istd;
    }

    @Internal
    public void setIstd(int i) {
        this.field_44_istd = i;
    }

    @Internal
    public int getIdslRMReason() {
        return this.field_45_idslRMReason;
    }

    @Internal
    public void setIdslRMReason(int i) {
        this.field_45_idslRMReason = i;
    }

    @Internal
    public int getIdslReasonDel() {
        return this.field_46_idslReasonDel;
    }

    @Internal
    public void setIdslReasonDel(int i) {
        this.field_46_idslReasonDel = i;
    }

    @Internal
    public int getCpg() {
        return this.field_47_cpg;
    }

    @Internal
    public void setCpg(int i) {
        this.field_47_cpg = i;
    }

    @Internal
    public short getHighlight() {
        return this.field_48_Highlight;
    }

    @Internal
    public void setHighlight(short s) {
        this.field_48_Highlight = s;
    }

    @Internal
    public short getCharsetFlags() {
        return this.field_49_CharsetFlags;
    }

    @Internal
    public void setCharsetFlags(short s) {
        this.field_49_CharsetFlags = s;
    }

    @Internal
    public short getChse() {
        return this.field_50_chse;
    }

    @Internal
    public void setChse(short s) {
        this.field_50_chse = s;
    }

    @Internal
    public boolean getFPropRMark() {
        return this.field_51_fPropRMark;
    }

    @Internal
    public void setFPropRMark(boolean z) {
        this.field_51_fPropRMark = z;
    }

    @Internal
    public int getIbstPropRMark() {
        return this.field_52_ibstPropRMark;
    }

    @Internal
    public void setIbstPropRMark(int i) {
        this.field_52_ibstPropRMark = i;
    }

    @Internal
    public DateAndTime getDttmPropRMark() {
        return this.field_53_dttmPropRMark;
    }

    @Internal
    public void setDttmPropRMark(DateAndTime dateAndTime) {
        this.field_53_dttmPropRMark = dateAndTime;
    }

    @Internal
    public boolean getFConflictOrig() {
        return this.field_54_fConflictOrig;
    }

    @Internal
    public void setFConflictOrig(boolean z) {
        this.field_54_fConflictOrig = z;
    }

    @Internal
    public boolean getFConflictOtherDel() {
        return this.field_55_fConflictOtherDel;
    }

    @Internal
    public void setFConflictOtherDel(boolean z) {
        this.field_55_fConflictOtherDel = z;
    }

    @Internal
    public int getWConflict() {
        return this.field_56_wConflict;
    }

    @Internal
    public void setWConflict(int i) {
        this.field_56_wConflict = i;
    }

    @Internal
    public int getIbstConflict() {
        return this.field_57_IbstConflict;
    }

    @Internal
    public void setIbstConflict(int i) {
        this.field_57_IbstConflict = i;
    }

    @Internal
    public DateAndTime getDttmConflict() {
        return this.field_58_dttmConflict;
    }

    @Internal
    public void setDttmConflict(DateAndTime dateAndTime) {
        this.field_58_dttmConflict = dateAndTime;
    }

    @Internal
    public boolean getFDispFldRMark() {
        return this.field_59_fDispFldRMark;
    }

    @Internal
    public void setFDispFldRMark(boolean z) {
        this.field_59_fDispFldRMark = z;
    }

    @Internal
    public int getIbstDispFldRMark() {
        return this.field_60_ibstDispFldRMark;
    }

    @Internal
    public void setIbstDispFldRMark(int i) {
        this.field_60_ibstDispFldRMark = i;
    }

    @Internal
    public DateAndTime getDttmDispFldRMark() {
        return this.field_61_dttmDispFldRMark;
    }

    @Internal
    public void setDttmDispFldRMark(DateAndTime dateAndTime) {
        this.field_61_dttmDispFldRMark = dateAndTime;
    }

    @Internal
    public byte[] getXstDispFldRMark() {
        return this.field_62_xstDispFldRMark;
    }

    @Internal
    public void setXstDispFldRMark(byte[] bArr) {
        this.field_62_xstDispFldRMark = bArr;
    }

    @Internal
    public int getFcObjp() {
        return this.field_63_fcObjp;
    }

    @Internal
    public void setFcObjp(int i) {
        this.field_63_fcObjp = i;
    }

    @Internal
    public byte getLbrCRJ() {
        return this.field_64_lbrCRJ;
    }

    @Internal
    public void setLbrCRJ(byte b) {
        this.field_64_lbrCRJ = b;
    }

    @Internal
    public boolean getFSpecVanish() {
        return this.field_65_fSpecVanish;
    }

    @Internal
    public void setFSpecVanish(boolean z) {
        this.field_65_fSpecVanish = z;
    }

    @Internal
    public boolean getFHasOldProps() {
        return this.field_66_fHasOldProps;
    }

    @Internal
    public void setFHasOldProps(boolean z) {
        this.field_66_fHasOldProps = z;
    }

    @Internal
    public boolean getFSdtVanish() {
        return this.field_67_fSdtVanish;
    }

    @Internal
    public void setFSdtVanish(boolean z) {
        this.field_67_fSdtVanish = z;
    }

    @Internal
    public int getWCharScale() {
        return this.field_68_wCharScale;
    }

    @Internal
    public void setWCharScale(int i) {
        this.field_68_wCharScale = i;
    }

    @Internal
    public void setFBold(boolean z) {
        this.field_1_grpfChp = fBold.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFBold() {
        return fBold.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFItalic(boolean z) {
        this.field_1_grpfChp = fItalic.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFItalic() {
        return fItalic.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFRMarkDel(boolean z) {
        this.field_1_grpfChp = fRMarkDel.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFRMarkDel() {
        return fRMarkDel.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFOutline(boolean z) {
        this.field_1_grpfChp = fOutline.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFOutline() {
        return fOutline.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFFldVanish(boolean z) {
        this.field_1_grpfChp = fFldVanish.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFFldVanish() {
        return fFldVanish.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFSmallCaps(boolean z) {
        this.field_1_grpfChp = fSmallCaps.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFSmallCaps() {
        return fSmallCaps.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFCaps(boolean z) {
        this.field_1_grpfChp = fCaps.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFCaps() {
        return fCaps.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFVanish(boolean z) {
        this.field_1_grpfChp = fVanish.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFVanish() {
        return fVanish.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFRMark(boolean z) {
        this.field_1_grpfChp = fRMark.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFRMark() {
        return fRMark.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFSpec(boolean z) {
        this.field_1_grpfChp = fSpec.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFSpec() {
        return fSpec.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFStrike(boolean z) {
        this.field_1_grpfChp = fStrike.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFStrike() {
        return fStrike.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFObj(boolean z) {
        this.field_1_grpfChp = fObj.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFObj() {
        return fObj.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFShadow(boolean z) {
        this.field_1_grpfChp = fShadow.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFShadow() {
        return fShadow.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFLowerCase(boolean z) {
        this.field_1_grpfChp = fLowerCase.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFLowerCase() {
        return fLowerCase.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFData(boolean z) {
        this.field_1_grpfChp = fData.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFData() {
        return fData.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFOle2(boolean z) {
        this.field_1_grpfChp = fOle2.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFOle2() {
        return fOle2.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFEmboss(boolean z) {
        this.field_1_grpfChp = fEmboss.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFEmboss() {
        return fEmboss.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFImprint(boolean z) {
        this.field_1_grpfChp = fImprint.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFImprint() {
        return fImprint.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFDStrike(boolean z) {
        this.field_1_grpfChp = fDStrike.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFDStrike() {
        return fDStrike.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFUsePgsuSettings(boolean z) {
        this.field_1_grpfChp = fUsePgsuSettings.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFUsePgsuSettings() {
        return fUsePgsuSettings.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFBoldBi(boolean z) {
        this.field_1_grpfChp = fBoldBi.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFBoldBi() {
        return fBoldBi.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFComplexScripts(boolean z) {
        this.field_1_grpfChp = fComplexScripts.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFComplexScripts() {
        return fComplexScripts.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFItalicBi(boolean z) {
        this.field_1_grpfChp = fItalicBi.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFItalicBi() {
        return fItalicBi.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setFBiDi(boolean z) {
        this.field_1_grpfChp = fBiDi.setBoolean(this.field_1_grpfChp, z);
    }

    @Internal
    public boolean isFBiDi() {
        return fBiDi.isSet(this.field_1_grpfChp);
    }

    @Internal
    public void setItypFELayout(short s) {
        this.field_29_ufel = (short) itypFELayout.setValue(this.field_29_ufel, s);
    }

    @Internal
    public short getItypFELayout() {
        return (short) itypFELayout.getValue(this.field_29_ufel);
    }

    @Internal
    public void setFTNY(boolean z) {
        this.field_29_ufel = (short) fTNY.setBoolean(this.field_29_ufel, z);
    }

    @Internal
    public boolean isFTNY() {
        return fTNY.isSet(this.field_29_ufel);
    }

    @Internal
    public void setFWarichu(boolean z) {
        this.field_29_ufel = (short) fWarichu.setBoolean(this.field_29_ufel, z);
    }

    @Internal
    public boolean isFWarichu() {
        return fWarichu.isSet(this.field_29_ufel);
    }

    @Internal
    public void setFKumimoji(boolean z) {
        this.field_29_ufel = (short) fKumimoji.setBoolean(this.field_29_ufel, z);
    }

    @Internal
    public boolean isFKumimoji() {
        return fKumimoji.isSet(this.field_29_ufel);
    }

    @Internal
    public void setFRuby(boolean z) {
        this.field_29_ufel = (short) fRuby.setBoolean(this.field_29_ufel, z);
    }

    @Internal
    public boolean isFRuby() {
        return fRuby.isSet(this.field_29_ufel);
    }

    @Internal
    public void setFLSFitText(boolean z) {
        this.field_29_ufel = (short) fLSFitText.setBoolean(this.field_29_ufel, z);
    }

    @Internal
    public boolean isFLSFitText() {
        return fLSFitText.isSet(this.field_29_ufel);
    }

    @Internal
    public void setSpare(byte b) {
        this.field_29_ufel = (short) spare.setValue(this.field_29_ufel, b);
    }

    @Internal
    public byte getSpare() {
        return (byte) spare.getValue(this.field_29_ufel);
    }

    @Internal
    public void setIWarichuBracket(byte b) {
        this.field_30_copt = (byte) iWarichuBracket.setValue(this.field_30_copt, b);
    }

    @Internal
    public byte getIWarichuBracket() {
        return (byte) iWarichuBracket.getValue(this.field_30_copt);
    }

    @Internal
    public void setFWarichuNoOpenBracket(boolean z) {
        this.field_30_copt = (byte) fWarichuNoOpenBracket.setBoolean(this.field_30_copt, z);
    }

    @Internal
    public boolean isFWarichuNoOpenBracket() {
        return fWarichuNoOpenBracket.isSet(this.field_30_copt);
    }

    @Internal
    public void setFTNYCompress(boolean z) {
        this.field_30_copt = (byte) fTNYCompress.setBoolean(this.field_30_copt, z);
    }

    @Internal
    public boolean isFTNYCompress() {
        return fTNYCompress.isSet(this.field_30_copt);
    }

    @Internal
    public void setFTNYFetchTxm(boolean z) {
        this.field_30_copt = (byte) fTNYFetchTxm.setBoolean(this.field_30_copt, z);
    }

    @Internal
    public boolean isFTNYFetchTxm() {
        return fTNYFetchTxm.isSet(this.field_30_copt);
    }

    @Internal
    public void setFCellFitText(boolean z) {
        this.field_30_copt = (byte) fCellFitText.setBoolean(this.field_30_copt, z);
    }

    @Internal
    public boolean isFCellFitText() {
        return fCellFitText.isSet(this.field_30_copt);
    }

    @Internal
    public void setUnused(boolean z) {
        this.field_30_copt = (byte) unused.setBoolean(this.field_30_copt, z);
    }

    @Internal
    public boolean isUnused() {
        return unused.isSet(this.field_30_copt);
    }

    @Internal
    public void setIcoHighlight(byte b) {
        this.field_48_Highlight = (short) icoHighlight.setValue(this.field_48_Highlight, b);
    }

    @Internal
    public byte getIcoHighlight() {
        return (byte) icoHighlight.getValue(this.field_48_Highlight);
    }

    @Internal
    public void setFHighlight(boolean z) {
        this.field_48_Highlight = (short) fHighlight.setBoolean(this.field_48_Highlight, z);
    }

    @Internal
    public boolean isFHighlight() {
        return fHighlight.isSet(this.field_48_Highlight);
    }

    @Internal
    public void setFChsDiff(boolean z) {
        this.field_49_CharsetFlags = (short) fChsDiff.setBoolean(this.field_49_CharsetFlags, z);
    }

    @Internal
    public boolean isFChsDiff() {
        return fChsDiff.isSet(this.field_49_CharsetFlags);
    }

    @Internal
    public void setFMacChs(boolean z) {
        this.field_49_CharsetFlags = (short) fMacChs.setBoolean(this.field_49_CharsetFlags, z);
    }

    @Internal
    public boolean isFMacChs() {
        return fMacChs.isSet(this.field_49_CharsetFlags);
    }

    @Internal
    public int getUnderlineColor() {
        Colorref colorref = this.field_69_underlineColor;
        if (colorref != null) {
            return colorref.getValue();
        }
        return -1;
    }

    @Internal
    public void setUnderlineColor(Colorref colorref) {
        this.field_69_underlineColor = colorref;
    }
}
