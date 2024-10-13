package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.hwpf.model.HDFType;
import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.hwpf.usermodel.ShadingDescriptor;
import com.app.office.fc.hwpf.usermodel.TableAutoformatLookSpecifier;
import com.app.office.fc.hwpf.usermodel.TableCellDescriptor;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;

@Internal
public abstract class TAPAbstractType implements HDFType {
    private static BitField fAdjusted = new BitField(16);
    private static BitField fAutofit = new BitField(1);
    private static BitField fCellSpacing = new BitField(16);
    private static BitField fFirstRow = new BitField(1);
    private static BitField fInvalAutofit = new BitField(32768);
    private static BitField fKeepFollow = new BitField(2);
    private static BitField fLastRow = new BitField(2);
    private static BitField fNeverBeenAutofit = new BitField(16384);
    private static BitField fNotPageView = new BitField(2);
    private static BitField fOrigWordTableRules = new BitField(8);
    private static BitField fOutline = new BitField(4);
    private static BitField fVert = new BitField(524288);
    private static BitField fWebView = new BitField(8);
    private static BitField fWrapToWwd = new BitField(1);
    private static BitField ftsWidth = new BitField(28);
    private static BitField ftsWidthAfter = new BitField(14336);
    private static BitField ftsWidthBefore = new BitField(1792);
    private static BitField ftsWidthIndent = new BitField(224);
    private static BitField grpfTap_unused = new BitField(65504);
    private static BitField pcHorz = new BitField(12582912);
    private static BitField pcVert = new BitField(3145728);
    private static BitField viewFlags_unused1 = new BitField(4);
    private static BitField viewFlags_unused2 = new BitField(65504);
    private static BitField widthAndFitsFlags_empty1 = new BitField(458752);
    private static BitField widthAndFitsFlags_empty2 = new BitField(-16777216);
    protected short field_10_wWidthIndent;
    protected short field_11_wWidthBefore;
    protected short field_12_wWidthAfter;
    protected int field_13_widthAndFitsFlags;
    protected int field_14_dxaAbs;
    protected int field_15_dyaAbs;
    protected int field_16_dxaFromText;
    protected int field_17_dyaFromText;
    protected int field_18_dxaFromTextRight;
    protected int field_19_dyaFromTextBottom;
    protected short field_1_istd;
    protected byte field_20_fBiDi;
    protected byte field_21_fRTL;
    protected byte field_22_fNoAllowOverlap;
    protected byte field_23_fSpare;
    protected int field_24_grpfTap;
    protected int field_25_internalFlags;
    protected short field_26_itcMac;
    protected int field_27_dxaAdjust;
    protected int field_28_dxaWebView;
    protected int field_29_dxaRTEWrapWidth;
    protected short field_2_jc;
    protected int field_30_dxaColWidthWwd;
    protected short field_31_pctWwd;
    protected int field_32_viewFlags;
    protected short[] field_33_rgdxaCenter;
    protected short[] field_34_rgdxaCenterPrint;
    protected ShadingDescriptor field_35_shdTable;
    protected BorderCode field_36_brcBottom;
    protected BorderCode field_37_brcTop;
    protected BorderCode field_38_brcLeft;
    protected BorderCode field_39_brcRight;
    protected int field_3_dxaGapHalf;
    protected BorderCode field_40_brcVertical;
    protected BorderCode field_41_brcHorizontal;
    protected short field_42_wCellPaddingDefaultTop;
    protected short field_43_wCellPaddingDefaultLeft;
    protected short field_44_wCellPaddingDefaultBottom;
    protected short field_45_wCellPaddingDefaultRight;
    protected byte field_46_ftsCellPaddingDefaultTop;
    protected byte field_47_ftsCellPaddingDefaultLeft;
    protected byte field_48_ftsCellPaddingDefaultBottom;
    protected byte field_49_ftsCellPaddingDefaultRight;
    protected int field_4_dyaRowHeight;
    protected short field_50_wCellSpacingDefaultTop;
    protected short field_51_wCellSpacingDefaultLeft;
    protected short field_52_wCellSpacingDefaultBottom;
    protected short field_53_wCellSpacingDefaultRight;
    protected byte field_54_ftsCellSpacingDefaultTop;
    protected byte field_55_ftsCellSpacingDefaultLeft;
    protected byte field_56_ftsCellSpacingDefaultBottom;
    protected byte field_57_ftsCellSpacingDefaultRight;
    protected short field_58_wCellPaddingOuterTop;
    protected short field_59_wCellPaddingOuterLeft;
    protected boolean field_5_fCantSplit;
    protected short field_60_wCellPaddingOuterBottom;
    protected short field_61_wCellPaddingOuterRight;
    protected byte field_62_ftsCellPaddingOuterTop;
    protected byte field_63_ftsCellPaddingOuterLeft;
    protected byte field_64_ftsCellPaddingOuterBottom;
    protected byte field_65_ftsCellPaddingOuterRight;
    protected short field_66_wCellSpacingOuterTop;
    protected short field_67_wCellSpacingOuterLeft;
    protected short field_68_wCellSpacingOuterBottom;
    protected short field_69_wCellSpacingOuterRight;
    protected boolean field_6_fCantSplit90;
    protected byte field_70_ftsCellSpacingOuterTop;
    protected byte field_71_ftsCellSpacingOuterLeft;
    protected byte field_72_ftsCellSpacingOuterBottom;
    protected byte field_73_ftsCellSpacingOuterRight;
    protected TableCellDescriptor[] field_74_rgtc;
    protected ShadingDescriptor[] field_75_rgshd;
    protected byte field_76_fPropRMark;
    protected byte field_77_fHasOldProps;
    protected short field_78_cHorzBands;
    protected short field_79_cVertBands;
    protected boolean field_7_fTableHeader;
    protected BorderCode field_80_rgbrcInsideDefault_0;
    protected BorderCode field_81_rgbrcInsideDefault_1;
    protected short field_82_tableIndent;
    protected short field_83_tCellSpacingDefault;
    protected TableAutoformatLookSpecifier field_8_tlp;
    protected short field_9_wWidth;

    public int getSize() {
        return 448;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TAP]\n");
        stringBuffer.append("    .istd                 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getIstd());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .jc                   = ");
        stringBuffer.append(" (");
        stringBuffer.append(getJc());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dxaGapHalf           = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaGapHalf());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dyaRowHeight         = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDyaRowHeight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fCantSplit           = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFCantSplit());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fCantSplit90         = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFCantSplit90());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fTableHeader         = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFTableHeader());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .tlp                  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getTlp());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wWidth               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWWidth());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wWidthIndent         = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWWidthIndent());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wWidthBefore         = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWWidthBefore());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wWidthAfter          = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWWidthAfter());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .widthAndFitsFlags    = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWidthAndFitsFlags());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fAutofit                 = ");
        stringBuffer.append(isFAutofit());
        stringBuffer.append(10);
        stringBuffer.append("         .fKeepFollow              = ");
        stringBuffer.append(isFKeepFollow());
        stringBuffer.append(10);
        stringBuffer.append("         .ftsWidth                 = ");
        stringBuffer.append(getFtsWidth());
        stringBuffer.append(10);
        stringBuffer.append("         .ftsWidthIndent           = ");
        stringBuffer.append(getFtsWidthIndent());
        stringBuffer.append(10);
        stringBuffer.append("         .ftsWidthBefore           = ");
        stringBuffer.append(getFtsWidthBefore());
        stringBuffer.append(10);
        stringBuffer.append("         .ftsWidthAfter            = ");
        stringBuffer.append(getFtsWidthAfter());
        stringBuffer.append(10);
        stringBuffer.append("         .fNeverBeenAutofit        = ");
        stringBuffer.append(isFNeverBeenAutofit());
        stringBuffer.append(10);
        stringBuffer.append("         .fInvalAutofit            = ");
        stringBuffer.append(isFInvalAutofit());
        stringBuffer.append(10);
        stringBuffer.append("         .widthAndFitsFlags_empty1     = ");
        stringBuffer.append(getWidthAndFitsFlags_empty1());
        stringBuffer.append(10);
        stringBuffer.append("         .fVert                    = ");
        stringBuffer.append(isFVert());
        stringBuffer.append(10);
        stringBuffer.append("         .pcVert                   = ");
        stringBuffer.append(getPcVert());
        stringBuffer.append(10);
        stringBuffer.append("         .pcHorz                   = ");
        stringBuffer.append(getPcHorz());
        stringBuffer.append(10);
        stringBuffer.append("         .widthAndFitsFlags_empty2     = ");
        stringBuffer.append(getWidthAndFitsFlags_empty2());
        stringBuffer.append(10);
        stringBuffer.append("    .dxaAbs               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaAbs());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dyaAbs               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDyaAbs());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dxaFromText          = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaFromText());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dyaFromText          = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDyaFromText());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dxaFromTextRight     = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaFromTextRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dyaFromTextBottom    = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDyaFromTextBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fBiDi                = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFBiDi());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fRTL                 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFRTL());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fNoAllowOverlap      = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFNoAllowOverlap());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fSpare               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFSpare());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .grpfTap              = ");
        stringBuffer.append(" (");
        stringBuffer.append(getGrpfTap());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .internalFlags        = ");
        stringBuffer.append(" (");
        stringBuffer.append(getInternalFlags());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fFirstRow                = ");
        stringBuffer.append(isFFirstRow());
        stringBuffer.append(10);
        stringBuffer.append("         .fLastRow                 = ");
        stringBuffer.append(isFLastRow());
        stringBuffer.append(10);
        stringBuffer.append("         .fOutline                 = ");
        stringBuffer.append(isFOutline());
        stringBuffer.append(10);
        stringBuffer.append("         .fOrigWordTableRules      = ");
        stringBuffer.append(isFOrigWordTableRules());
        stringBuffer.append(10);
        stringBuffer.append("         .fCellSpacing             = ");
        stringBuffer.append(isFCellSpacing());
        stringBuffer.append(10);
        stringBuffer.append("         .grpfTap_unused           = ");
        stringBuffer.append(getGrpfTap_unused());
        stringBuffer.append(10);
        stringBuffer.append("    .itcMac               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getItcMac());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dxaAdjust            = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaAdjust());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dxaWebView           = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaWebView());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dxaRTEWrapWidth      = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaRTEWrapWidth());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .dxaColWidthWwd       = ");
        stringBuffer.append(" (");
        stringBuffer.append(getDxaColWidthWwd());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .pctWwd               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getPctWwd());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .viewFlags            = ");
        stringBuffer.append(" (");
        stringBuffer.append(getViewFlags());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fWrapToWwd               = ");
        stringBuffer.append(isFWrapToWwd());
        stringBuffer.append(10);
        stringBuffer.append("         .fNotPageView             = ");
        stringBuffer.append(isFNotPageView());
        stringBuffer.append(10);
        stringBuffer.append("         .viewFlags_unused1        = ");
        stringBuffer.append(isViewFlags_unused1());
        stringBuffer.append(10);
        stringBuffer.append("         .fWebView                 = ");
        stringBuffer.append(isFWebView());
        stringBuffer.append(10);
        stringBuffer.append("         .fAdjusted                = ");
        stringBuffer.append(isFAdjusted());
        stringBuffer.append(10);
        stringBuffer.append("         .viewFlags_unused2        = ");
        stringBuffer.append(getViewFlags_unused2());
        stringBuffer.append(10);
        stringBuffer.append("    .rgdxaCenter          = ");
        stringBuffer.append(" (");
        stringBuffer.append(getRgdxaCenter());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .rgdxaCenterPrint     = ");
        stringBuffer.append(" (");
        stringBuffer.append(getRgdxaCenterPrint());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .shdTable             = ");
        stringBuffer.append(" (");
        stringBuffer.append(getShdTable());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcBottom            = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcTop               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcLeft              = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcRight             = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcVertical          = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcVertical());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcHorizontal        = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcHorizontal());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingDefaultTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingDefaultTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingDefaultLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingDefaultLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingDefaultBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingDefaultBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingDefaultRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingDefaultRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingDefaultTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingDefaultTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingDefaultLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingDefaultLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingDefaultBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingDefaultBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingDefaultRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingDefaultRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingDefaultTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingDefaultTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingDefaultLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingDefaultLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingDefaultBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingDefaultBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingDefaultRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingDefaultRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingDefaultTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingDefaultTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingDefaultLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingDefaultLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingDefaultBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingDefaultBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingDefaultRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingDefaultRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingOuterTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingOuterTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingOuterLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingOuterLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingOuterBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingOuterBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingOuterRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingOuterRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingOuterTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingOuterTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingOuterLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingOuterLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingOuterBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingOuterBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingOuterRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingOuterRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingOuterTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingOuterTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingOuterLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingOuterLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingOuterBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingOuterBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingOuterRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingOuterRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingOuterTop = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingOuterTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingOuterLeft = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingOuterLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingOuterBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingOuterBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingOuterRight = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingOuterRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .rgtc                 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getRgtc());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .rgshd                = ");
        stringBuffer.append(" (");
        stringBuffer.append(getRgshd());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fPropRMark           = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFPropRMark());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .fHasOldProps         = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFHasOldProps());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .cHorzBands           = ");
        stringBuffer.append(" (");
        stringBuffer.append(getCHorzBands());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .cVertBands           = ");
        stringBuffer.append(" (");
        stringBuffer.append(getCVertBands());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .rgbrcInsideDefault_0 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getRgbrcInsideDefault_0());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .rgbrcInsideDefault_1 = ");
        stringBuffer.append(" (");
        stringBuffer.append(getRgbrcInsideDefault_1());
        stringBuffer.append(" )\n");
        stringBuffer.append("[/TAP]\n");
        return stringBuffer.toString();
    }

    public short getIstd() {
        return this.field_1_istd;
    }

    public void setIstd(short s) {
        this.field_1_istd = s;
    }

    public short getJc() {
        return this.field_2_jc;
    }

    public void setJc(short s) {
        this.field_2_jc = s;
    }

    public short getTableInIndent() {
        return this.field_82_tableIndent;
    }

    public void setTableIndent(short s) {
        this.field_82_tableIndent = s;
    }

    public void setTCellSpacingDefault(short s) {
        this.field_83_tCellSpacingDefault = s;
    }

    public short getTCellSpacingDefault() {
        return this.field_83_tCellSpacingDefault;
    }

    public int getDxaGapHalf() {
        return this.field_3_dxaGapHalf;
    }

    public void setDxaGapHalf(int i) {
        this.field_3_dxaGapHalf = i;
    }

    public int getDyaRowHeight() {
        return this.field_4_dyaRowHeight;
    }

    public void setDyaRowHeight(int i) {
        this.field_4_dyaRowHeight = i;
    }

    public boolean getFCantSplit() {
        return this.field_5_fCantSplit;
    }

    public void setFCantSplit(boolean z) {
        this.field_5_fCantSplit = z;
    }

    public boolean getFCantSplit90() {
        return this.field_6_fCantSplit90;
    }

    public void setFCantSplit90(boolean z) {
        this.field_6_fCantSplit90 = z;
    }

    public boolean getFTableHeader() {
        return this.field_7_fTableHeader;
    }

    public void setFTableHeader(boolean z) {
        this.field_7_fTableHeader = z;
    }

    public TableAutoformatLookSpecifier getTlp() {
        return this.field_8_tlp;
    }

    public void setTlp(TableAutoformatLookSpecifier tableAutoformatLookSpecifier) {
        this.field_8_tlp = tableAutoformatLookSpecifier;
    }

    public short getWWidth() {
        return this.field_9_wWidth;
    }

    public void setWWidth(short s) {
        this.field_9_wWidth = s;
    }

    public short getWWidthIndent() {
        return this.field_10_wWidthIndent;
    }

    public void setWWidthIndent(short s) {
        this.field_10_wWidthIndent = s;
    }

    public short getWWidthBefore() {
        return this.field_11_wWidthBefore;
    }

    public void setWWidthBefore(short s) {
        this.field_11_wWidthBefore = s;
    }

    public short getWWidthAfter() {
        return this.field_12_wWidthAfter;
    }

    public void setWWidthAfter(short s) {
        this.field_12_wWidthAfter = s;
    }

    public int getWidthAndFitsFlags() {
        return this.field_13_widthAndFitsFlags;
    }

    public void setWidthAndFitsFlags(int i) {
        this.field_13_widthAndFitsFlags = i;
    }

    public int getDxaAbs() {
        return this.field_14_dxaAbs;
    }

    public void setDxaAbs(int i) {
        this.field_14_dxaAbs = i;
    }

    public int getDyaAbs() {
        return this.field_15_dyaAbs;
    }

    public void setDyaAbs(int i) {
        this.field_15_dyaAbs = i;
    }

    public int getDxaFromText() {
        return this.field_16_dxaFromText;
    }

    public void setDxaFromText(int i) {
        this.field_16_dxaFromText = i;
    }

    public int getDyaFromText() {
        return this.field_17_dyaFromText;
    }

    public void setDyaFromText(int i) {
        this.field_17_dyaFromText = i;
    }

    public int getDxaFromTextRight() {
        return this.field_18_dxaFromTextRight;
    }

    public void setDxaFromTextRight(int i) {
        this.field_18_dxaFromTextRight = i;
    }

    public int getDyaFromTextBottom() {
        return this.field_19_dyaFromTextBottom;
    }

    public void setDyaFromTextBottom(int i) {
        this.field_19_dyaFromTextBottom = i;
    }

    public byte getFBiDi() {
        return this.field_20_fBiDi;
    }

    public void setFBiDi(byte b) {
        this.field_20_fBiDi = b;
    }

    public byte getFRTL() {
        return this.field_21_fRTL;
    }

    public void setFRTL(byte b) {
        this.field_21_fRTL = b;
    }

    public byte getFNoAllowOverlap() {
        return this.field_22_fNoAllowOverlap;
    }

    public void setFNoAllowOverlap(byte b) {
        this.field_22_fNoAllowOverlap = b;
    }

    public byte getFSpare() {
        return this.field_23_fSpare;
    }

    public void setFSpare(byte b) {
        this.field_23_fSpare = b;
    }

    public int getGrpfTap() {
        return this.field_24_grpfTap;
    }

    public void setGrpfTap(int i) {
        this.field_24_grpfTap = i;
    }

    public int getInternalFlags() {
        return this.field_25_internalFlags;
    }

    public void setInternalFlags(int i) {
        this.field_25_internalFlags = i;
    }

    public short getItcMac() {
        return this.field_26_itcMac;
    }

    public void setItcMac(short s) {
        this.field_26_itcMac = s;
    }

    public int getDxaAdjust() {
        return this.field_27_dxaAdjust;
    }

    public void setDxaAdjust(int i) {
        this.field_27_dxaAdjust = i;
    }

    public int getDxaWebView() {
        return this.field_28_dxaWebView;
    }

    public void setDxaWebView(int i) {
        this.field_28_dxaWebView = i;
    }

    public int getDxaRTEWrapWidth() {
        return this.field_29_dxaRTEWrapWidth;
    }

    public void setDxaRTEWrapWidth(int i) {
        this.field_29_dxaRTEWrapWidth = i;
    }

    public int getDxaColWidthWwd() {
        return this.field_30_dxaColWidthWwd;
    }

    public void setDxaColWidthWwd(int i) {
        this.field_30_dxaColWidthWwd = i;
    }

    public short getPctWwd() {
        return this.field_31_pctWwd;
    }

    public void setPctWwd(short s) {
        this.field_31_pctWwd = s;
    }

    public int getViewFlags() {
        return this.field_32_viewFlags;
    }

    public void setViewFlags(int i) {
        this.field_32_viewFlags = i;
    }

    public short[] getRgdxaCenter() {
        return this.field_33_rgdxaCenter;
    }

    public void setRgdxaCenter(short[] sArr) {
        this.field_33_rgdxaCenter = sArr;
    }

    public short[] getRgdxaCenterPrint() {
        return this.field_34_rgdxaCenterPrint;
    }

    public void setRgdxaCenterPrint(short[] sArr) {
        this.field_34_rgdxaCenterPrint = sArr;
    }

    public ShadingDescriptor getShdTable() {
        return this.field_35_shdTable;
    }

    public void setShdTable(ShadingDescriptor shadingDescriptor) {
        this.field_35_shdTable = shadingDescriptor;
    }

    public BorderCode getBrcBottom() {
        return this.field_36_brcBottom;
    }

    public void setBrcBottom(BorderCode borderCode) {
        this.field_36_brcBottom = borderCode;
    }

    public BorderCode getBrcTop() {
        return this.field_37_brcTop;
    }

    public void setBrcTop(BorderCode borderCode) {
        this.field_37_brcTop = borderCode;
    }

    public BorderCode getBrcLeft() {
        return this.field_38_brcLeft;
    }

    public void setBrcLeft(BorderCode borderCode) {
        this.field_38_brcLeft = borderCode;
    }

    public BorderCode getBrcRight() {
        return this.field_39_brcRight;
    }

    public void setBrcRight(BorderCode borderCode) {
        this.field_39_brcRight = borderCode;
    }

    public BorderCode getBrcVertical() {
        return this.field_40_brcVertical;
    }

    public void setBrcVertical(BorderCode borderCode) {
        this.field_40_brcVertical = borderCode;
    }

    public BorderCode getBrcHorizontal() {
        return this.field_41_brcHorizontal;
    }

    public void setBrcHorizontal(BorderCode borderCode) {
        this.field_41_brcHorizontal = borderCode;
    }

    public short getWCellPaddingDefaultTop() {
        return this.field_42_wCellPaddingDefaultTop;
    }

    public void setWCellPaddingDefaultTop(short s) {
        this.field_42_wCellPaddingDefaultTop = s;
    }

    public short getWCellPaddingDefaultLeft() {
        return this.field_43_wCellPaddingDefaultLeft;
    }

    public void setWCellPaddingDefaultLeft(short s) {
        this.field_43_wCellPaddingDefaultLeft = s;
    }

    public short getWCellPaddingDefaultBottom() {
        return this.field_44_wCellPaddingDefaultBottom;
    }

    public void setWCellPaddingDefaultBottom(short s) {
        this.field_44_wCellPaddingDefaultBottom = s;
    }

    public short getWCellPaddingDefaultRight() {
        return this.field_45_wCellPaddingDefaultRight;
    }

    public void setWCellPaddingDefaultRight(short s) {
        this.field_45_wCellPaddingDefaultRight = s;
    }

    public byte getFtsCellPaddingDefaultTop() {
        return this.field_46_ftsCellPaddingDefaultTop;
    }

    public void setFtsCellPaddingDefaultTop(byte b) {
        this.field_46_ftsCellPaddingDefaultTop = b;
    }

    public byte getFtsCellPaddingDefaultLeft() {
        return this.field_47_ftsCellPaddingDefaultLeft;
    }

    public void setFtsCellPaddingDefaultLeft(byte b) {
        this.field_47_ftsCellPaddingDefaultLeft = b;
    }

    public byte getFtsCellPaddingDefaultBottom() {
        return this.field_48_ftsCellPaddingDefaultBottom;
    }

    public void setFtsCellPaddingDefaultBottom(byte b) {
        this.field_48_ftsCellPaddingDefaultBottom = b;
    }

    public byte getFtsCellPaddingDefaultRight() {
        return this.field_49_ftsCellPaddingDefaultRight;
    }

    public void setFtsCellPaddingDefaultRight(byte b) {
        this.field_49_ftsCellPaddingDefaultRight = b;
    }

    public short getWCellSpacingDefaultTop() {
        return this.field_50_wCellSpacingDefaultTop;
    }

    public void setWCellSpacingDefaultTop(short s) {
        this.field_50_wCellSpacingDefaultTop = s;
    }

    public short getWCellSpacingDefaultLeft() {
        return this.field_51_wCellSpacingDefaultLeft;
    }

    public void setWCellSpacingDefaultLeft(short s) {
        this.field_51_wCellSpacingDefaultLeft = s;
    }

    public short getWCellSpacingDefaultBottom() {
        return this.field_52_wCellSpacingDefaultBottom;
    }

    public void setWCellSpacingDefaultBottom(short s) {
        this.field_52_wCellSpacingDefaultBottom = s;
    }

    public short getWCellSpacingDefaultRight() {
        return this.field_53_wCellSpacingDefaultRight;
    }

    public void setWCellSpacingDefaultRight(short s) {
        this.field_53_wCellSpacingDefaultRight = s;
    }

    public byte getFtsCellSpacingDefaultTop() {
        return this.field_54_ftsCellSpacingDefaultTop;
    }

    public void setFtsCellSpacingDefaultTop(byte b) {
        this.field_54_ftsCellSpacingDefaultTop = b;
    }

    public byte getFtsCellSpacingDefaultLeft() {
        return this.field_55_ftsCellSpacingDefaultLeft;
    }

    public void setFtsCellSpacingDefaultLeft(byte b) {
        this.field_55_ftsCellSpacingDefaultLeft = b;
    }

    public byte getFtsCellSpacingDefaultBottom() {
        return this.field_56_ftsCellSpacingDefaultBottom;
    }

    public void setFtsCellSpacingDefaultBottom(byte b) {
        this.field_56_ftsCellSpacingDefaultBottom = b;
    }

    public byte getFtsCellSpacingDefaultRight() {
        return this.field_57_ftsCellSpacingDefaultRight;
    }

    public void setFtsCellSpacingDefaultRight(byte b) {
        this.field_57_ftsCellSpacingDefaultRight = b;
    }

    public short getWCellPaddingOuterTop() {
        return this.field_58_wCellPaddingOuterTop;
    }

    public void setWCellPaddingOuterTop(short s) {
        this.field_58_wCellPaddingOuterTop = s;
    }

    public short getWCellPaddingOuterLeft() {
        return this.field_59_wCellPaddingOuterLeft;
    }

    public void setWCellPaddingOuterLeft(short s) {
        this.field_59_wCellPaddingOuterLeft = s;
    }

    public short getWCellPaddingOuterBottom() {
        return this.field_60_wCellPaddingOuterBottom;
    }

    public void setWCellPaddingOuterBottom(short s) {
        this.field_60_wCellPaddingOuterBottom = s;
    }

    public short getWCellPaddingOuterRight() {
        return this.field_61_wCellPaddingOuterRight;
    }

    public void setWCellPaddingOuterRight(short s) {
        this.field_61_wCellPaddingOuterRight = s;
    }

    public byte getFtsCellPaddingOuterTop() {
        return this.field_62_ftsCellPaddingOuterTop;
    }

    public void setFtsCellPaddingOuterTop(byte b) {
        this.field_62_ftsCellPaddingOuterTop = b;
    }

    public byte getFtsCellPaddingOuterLeft() {
        return this.field_63_ftsCellPaddingOuterLeft;
    }

    public void setFtsCellPaddingOuterLeft(byte b) {
        this.field_63_ftsCellPaddingOuterLeft = b;
    }

    public byte getFtsCellPaddingOuterBottom() {
        return this.field_64_ftsCellPaddingOuterBottom;
    }

    public void setFtsCellPaddingOuterBottom(byte b) {
        this.field_64_ftsCellPaddingOuterBottom = b;
    }

    public byte getFtsCellPaddingOuterRight() {
        return this.field_65_ftsCellPaddingOuterRight;
    }

    public void setFtsCellPaddingOuterRight(byte b) {
        this.field_65_ftsCellPaddingOuterRight = b;
    }

    public short getWCellSpacingOuterTop() {
        return this.field_66_wCellSpacingOuterTop;
    }

    public void setWCellSpacingOuterTop(short s) {
        this.field_66_wCellSpacingOuterTop = s;
    }

    public short getWCellSpacingOuterLeft() {
        return this.field_67_wCellSpacingOuterLeft;
    }

    public void setWCellSpacingOuterLeft(short s) {
        this.field_67_wCellSpacingOuterLeft = s;
    }

    public short getWCellSpacingOuterBottom() {
        return this.field_68_wCellSpacingOuterBottom;
    }

    public void setWCellSpacingOuterBottom(short s) {
        this.field_68_wCellSpacingOuterBottom = s;
    }

    public short getWCellSpacingOuterRight() {
        return this.field_69_wCellSpacingOuterRight;
    }

    public void setWCellSpacingOuterRight(short s) {
        this.field_69_wCellSpacingOuterRight = s;
    }

    public byte getFtsCellSpacingOuterTop() {
        return this.field_70_ftsCellSpacingOuterTop;
    }

    public void setFtsCellSpacingOuterTop(byte b) {
        this.field_70_ftsCellSpacingOuterTop = b;
    }

    public byte getFtsCellSpacingOuterLeft() {
        return this.field_71_ftsCellSpacingOuterLeft;
    }

    public void setFtsCellSpacingOuterLeft(byte b) {
        this.field_71_ftsCellSpacingOuterLeft = b;
    }

    public byte getFtsCellSpacingOuterBottom() {
        return this.field_72_ftsCellSpacingOuterBottom;
    }

    public void setFtsCellSpacingOuterBottom(byte b) {
        this.field_72_ftsCellSpacingOuterBottom = b;
    }

    public byte getFtsCellSpacingOuterRight() {
        return this.field_73_ftsCellSpacingOuterRight;
    }

    public void setFtsCellSpacingOuterRight(byte b) {
        this.field_73_ftsCellSpacingOuterRight = b;
    }

    public TableCellDescriptor[] getRgtc() {
        return this.field_74_rgtc;
    }

    public void setRgtc(TableCellDescriptor[] tableCellDescriptorArr) {
        this.field_74_rgtc = tableCellDescriptorArr;
    }

    public ShadingDescriptor[] getRgshd() {
        return this.field_75_rgshd;
    }

    public void setRgshd(ShadingDescriptor[] shadingDescriptorArr) {
        this.field_75_rgshd = shadingDescriptorArr;
    }

    public byte getFPropRMark() {
        return this.field_76_fPropRMark;
    }

    public void setFPropRMark(byte b) {
        this.field_76_fPropRMark = b;
    }

    public byte getFHasOldProps() {
        return this.field_77_fHasOldProps;
    }

    public void setFHasOldProps(byte b) {
        this.field_77_fHasOldProps = b;
    }

    public short getCHorzBands() {
        return this.field_78_cHorzBands;
    }

    public void setCHorzBands(short s) {
        this.field_78_cHorzBands = s;
    }

    public short getCVertBands() {
        return this.field_79_cVertBands;
    }

    public void setCVertBands(short s) {
        this.field_79_cVertBands = s;
    }

    public BorderCode getRgbrcInsideDefault_0() {
        return this.field_80_rgbrcInsideDefault_0;
    }

    public void setRgbrcInsideDefault_0(BorderCode borderCode) {
        this.field_80_rgbrcInsideDefault_0 = borderCode;
    }

    public BorderCode getRgbrcInsideDefault_1() {
        return this.field_81_rgbrcInsideDefault_1;
    }

    public void setRgbrcInsideDefault_1(BorderCode borderCode) {
        this.field_81_rgbrcInsideDefault_1 = borderCode;
    }

    public void setFAutofit(boolean z) {
        this.field_13_widthAndFitsFlags = fAutofit.setBoolean(this.field_13_widthAndFitsFlags, z);
    }

    public boolean isFAutofit() {
        return fAutofit.isSet(this.field_13_widthAndFitsFlags);
    }

    public void setFKeepFollow(boolean z) {
        this.field_13_widthAndFitsFlags = fKeepFollow.setBoolean(this.field_13_widthAndFitsFlags, z);
    }

    public boolean isFKeepFollow() {
        return fKeepFollow.isSet(this.field_13_widthAndFitsFlags);
    }

    public void setFtsWidth(byte b) {
        this.field_13_widthAndFitsFlags = ftsWidth.setValue(this.field_13_widthAndFitsFlags, b);
    }

    public byte getFtsWidth() {
        return (byte) ftsWidth.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setFtsWidthIndent(byte b) {
        this.field_13_widthAndFitsFlags = ftsWidthIndent.setValue(this.field_13_widthAndFitsFlags, b);
    }

    public byte getFtsWidthIndent() {
        return (byte) ftsWidthIndent.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setFtsWidthBefore(byte b) {
        this.field_13_widthAndFitsFlags = ftsWidthBefore.setValue(this.field_13_widthAndFitsFlags, b);
    }

    public byte getFtsWidthBefore() {
        return (byte) ftsWidthBefore.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setFtsWidthAfter(byte b) {
        this.field_13_widthAndFitsFlags = ftsWidthAfter.setValue(this.field_13_widthAndFitsFlags, b);
    }

    public byte getFtsWidthAfter() {
        return (byte) ftsWidthAfter.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setFNeverBeenAutofit(boolean z) {
        this.field_13_widthAndFitsFlags = fNeverBeenAutofit.setBoolean(this.field_13_widthAndFitsFlags, z);
    }

    public boolean isFNeverBeenAutofit() {
        return fNeverBeenAutofit.isSet(this.field_13_widthAndFitsFlags);
    }

    public void setFInvalAutofit(boolean z) {
        this.field_13_widthAndFitsFlags = fInvalAutofit.setBoolean(this.field_13_widthAndFitsFlags, z);
    }

    public boolean isFInvalAutofit() {
        return fInvalAutofit.isSet(this.field_13_widthAndFitsFlags);
    }

    public void setWidthAndFitsFlags_empty1(byte b) {
        this.field_13_widthAndFitsFlags = widthAndFitsFlags_empty1.setValue(this.field_13_widthAndFitsFlags, b);
    }

    public byte getWidthAndFitsFlags_empty1() {
        return (byte) widthAndFitsFlags_empty1.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setFVert(boolean z) {
        this.field_13_widthAndFitsFlags = fVert.setBoolean(this.field_13_widthAndFitsFlags, z);
    }

    public boolean isFVert() {
        return fVert.isSet(this.field_13_widthAndFitsFlags);
    }

    public void setPcVert(byte b) {
        this.field_13_widthAndFitsFlags = pcVert.setValue(this.field_13_widthAndFitsFlags, b);
    }

    public byte getPcVert() {
        return (byte) pcVert.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setPcHorz(byte b) {
        this.field_13_widthAndFitsFlags = pcHorz.setValue(this.field_13_widthAndFitsFlags, b);
    }

    public byte getPcHorz() {
        return (byte) pcHorz.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setWidthAndFitsFlags_empty2(short s) {
        this.field_13_widthAndFitsFlags = widthAndFitsFlags_empty2.setValue(this.field_13_widthAndFitsFlags, s);
    }

    public short getWidthAndFitsFlags_empty2() {
        return (short) widthAndFitsFlags_empty2.getValue(this.field_13_widthAndFitsFlags);
    }

    public void setFFirstRow(boolean z) {
        this.field_25_internalFlags = fFirstRow.setBoolean(this.field_25_internalFlags, z);
    }

    public boolean isFFirstRow() {
        return fFirstRow.isSet(this.field_25_internalFlags);
    }

    public void setFLastRow(boolean z) {
        this.field_25_internalFlags = fLastRow.setBoolean(this.field_25_internalFlags, z);
    }

    public boolean isFLastRow() {
        return fLastRow.isSet(this.field_25_internalFlags);
    }

    public void setFOutline(boolean z) {
        this.field_25_internalFlags = fOutline.setBoolean(this.field_25_internalFlags, z);
    }

    public boolean isFOutline() {
        return fOutline.isSet(this.field_25_internalFlags);
    }

    public void setFOrigWordTableRules(boolean z) {
        this.field_25_internalFlags = fOrigWordTableRules.setBoolean(this.field_25_internalFlags, z);
    }

    public boolean isFOrigWordTableRules() {
        return fOrigWordTableRules.isSet(this.field_25_internalFlags);
    }

    public void setFCellSpacing(boolean z) {
        this.field_25_internalFlags = fCellSpacing.setBoolean(this.field_25_internalFlags, z);
    }

    public boolean isFCellSpacing() {
        return fCellSpacing.isSet(this.field_25_internalFlags);
    }

    public void setGrpfTap_unused(short s) {
        this.field_25_internalFlags = grpfTap_unused.setValue(this.field_25_internalFlags, s);
    }

    public short getGrpfTap_unused() {
        return (short) grpfTap_unused.getValue(this.field_25_internalFlags);
    }

    public void setFWrapToWwd(boolean z) {
        this.field_32_viewFlags = fWrapToWwd.setBoolean(this.field_32_viewFlags, z);
    }

    public boolean isFWrapToWwd() {
        return fWrapToWwd.isSet(this.field_32_viewFlags);
    }

    public void setFNotPageView(boolean z) {
        this.field_32_viewFlags = fNotPageView.setBoolean(this.field_32_viewFlags, z);
    }

    public boolean isFNotPageView() {
        return fNotPageView.isSet(this.field_32_viewFlags);
    }

    public void setViewFlags_unused1(boolean z) {
        this.field_32_viewFlags = viewFlags_unused1.setBoolean(this.field_32_viewFlags, z);
    }

    public boolean isViewFlags_unused1() {
        return viewFlags_unused1.isSet(this.field_32_viewFlags);
    }

    public void setFWebView(boolean z) {
        this.field_32_viewFlags = fWebView.setBoolean(this.field_32_viewFlags, z);
    }

    public boolean isFWebView() {
        return fWebView.isSet(this.field_32_viewFlags);
    }

    public void setFAdjusted(boolean z) {
        this.field_32_viewFlags = fAdjusted.setBoolean(this.field_32_viewFlags, z);
    }

    public boolean isFAdjusted() {
        return fAdjusted.isSet(this.field_32_viewFlags);
    }

    public void setViewFlags_unused2(short s) {
        this.field_32_viewFlags = viewFlags_unused2.setValue(this.field_32_viewFlags, s);
    }

    public short getViewFlags_unused2() {
        return (short) viewFlags_unused2.getValue(this.field_32_viewFlags);
    }
}
