package com.app.office.fc.hwpf.model.types;

import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hssf.record.UnknownRecord;
import com.app.office.fc.hwpf.model.HDFType;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.thirdpart.emf.EMFConstants;

@Internal
public abstract class DOPAbstractType implements HDFType {
    private static BitField KeyVirusSession30 = new BitField(-4);
    private static BitField epc = new BitField(3);
    private static BitField fAutoHyphen = new BitField(16);
    private static BitField fAutoVersions = new BitField(2);
    private static BitField fBackup = new BitField(1);
    private static BitField fConvMailMergeEsc = new BitField(64);
    private static BitField fDfltTrueType = new BitField(128);
    private static BitField fDispFormFldSel = new BitField(4);
    private static BitField fEmbedFonts = new BitField(128);
    private static BitField fExactCWords = new BitField(2);
    private static BitField fFacingPages = new BitField(1);
    private static BitField fForcePageSizePag = new BitField(16384);
    private static BitField fFormNoFields = new BitField(32);
    private static BitField fGramAllClean = new BitField(64);
    private static BitField fGramAllDone = new BitField(32);
    private static BitField fHaveVersions = new BitField(1);
    private static BitField fHideLastVersion = new BitField(256);
    private static BitField fHtmlDoc = new BitField(512);
    private static BitField fHyphCapitals = new BitField(8);
    private static BitField fIncludeFooter = new BitField(8192);
    private static BitField fIncludeHeader = new BitField(4096);
    private static BitField fLabelDoc = new BitField(4);
    private static BitField fLinkStyles = new BitField(64);
    private static BitField fLockAtn = new BitField(16);
    private static BitField fLockRev = new BitField(64);
    private static BitField fMWSmallCaps = new BitField(2097152);
    private static BitField fMapPrintTextColor = new BitField(16);
    private static BitField fMinFontSizePag = new BitField(32768);
    private static BitField fMirrorMargins = new BitField(32);
    private static BitField fNoColumnBalance = new BitField(32);
    private static BitField fNoLeading = new BitField(524288);
    private static BitField fNoSpaceRaiseLower = new BitField(2);
    private static BitField fNoTabForInd = new BitField(1);
    private static BitField fOnlyMacPics = new BitField(1);
    private static BitField fOnlyWinPics = new BitField(2);
    private static BitField fOrigWordTableRules = new BitField(256);
    private static BitField fPMHMainDoc = new BitField(4);
    private static BitField fPagHidden = new BitField(4);
    private static BitField fPagResults = new BitField(8);
    private static BitField fPagSupressTopSpacing = new BitField(1);
    private static BitField fPrintBodyBeforeHdr = new BitField(262144);
    private static BitField fPrintFormData = new BitField(1024);
    private static BitField fProtEnabled = new BitField(2);
    private static BitField fRMPrint = new BitField(16);
    private static BitField fRMView = new BitField(8);
    private static BitField fRevMarking = new BitField(128);
    private static BitField fRotateFontW6 = new BitField(16384);
    private static BitField fSaveFormData = new BitField(2048);
    private static BitField fShadeFormData = new BitField(4096);
    private static BitField fShowBreaksInFrames = new BitField(1024);
    private static BitField fSnapBorder = new BitField(2048);
    private static BitField fSubsetFonts = new BitField(128);
    private static BitField fSuppressTopSPacingMac5 = new BitField(65536);
    private static BitField fSupressSpdfAfterPageBreak = new BitField(4);
    private static BitField fSupressTopSpacing = new BitField(128);
    private static BitField fSwapBordersFacingPgs = new BitField(2048);
    private static BitField fTransparentMetafiles = new BitField(512);
    private static BitField fTruncDxaExpand = new BitField(131072);
    private static BitField fVirusLoadSafe = new BitField(2);
    private static BitField fVirusPrompted = new BitField(1);
    private static BitField fWCFtnEdn = new BitField(32768);
    private static BitField fWidowControl = new BitField(2);
    private static BitField fWrapTrailSpaces = new BitField(8);
    private static BitField fpc = new BitField(96);
    private static BitField grfSupression = new BitField(24);
    private static BitField iGutterPos = new BitField(32768);
    private static BitField lvl = new BitField(30);
    private static BitField nEdn = new BitField(65532);
    private static BitField nFtn = new BitField(65532);
    private static BitField nfcEdnRef1 = new BitField(960);
    private static BitField nfcFtnRef1 = new BitField(60);
    private static BitField oldfConvMailMergeEsc = new BitField(64);
    private static BitField oldfMapPrintTextColor = new BitField(16);
    private static BitField oldfNoColumnBalance = new BitField(32);
    private static BitField oldfNoSpaceRaiseLower = new BitField(2);
    private static BitField oldfNoTabForInd = new BitField(1);
    private static BitField oldfOrigWordTableRules = new BitField(256);
    private static BitField oldfShowBreaksInFrames = new BitField(1024);
    private static BitField oldfSuppressSpbfAfterPageBreak = new BitField(4);
    private static BitField oldfSupressTopSpacing = new BitField(128);
    private static BitField oldfSwapBordersFacingPgs = new BitField(2048);
    private static BitField oldfTransparentMetafiles = new BitField(512);
    private static BitField oldfWrapTrailSpaces = new BitField(8);
    private static BitField rncEdn = new BitField(3);
    private static BitField rncFtn = new BitField(3);
    private static BitField unused1 = new BitField(128);
    private static BitField unused3 = new BitField(64);
    private static BitField unused4 = new BitField(32);
    private static BitField unused5 = new BitField(RecordTypes.EscherDggContainer);
    private static BitField wScaleSaved = new BitField(4088);
    private static BitField wvkSaved = new BitField(7);
    private static BitField zkSaved = new BitField(12288);
    protected int field_10_wSpare;
    protected int field_11_dxaHotz;
    protected int field_12_cConsexHypLim;
    protected int field_13_wSpare2;
    protected int field_14_dttmCreated;
    protected int field_15_dttmRevised;
    protected int field_16_dttmLastPrint;
    protected int field_17_nRevision;
    protected int field_18_tmEdited;
    protected int field_19_cWords;
    protected byte field_1_formatFlags;
    protected int field_20_cCh;
    protected int field_21_cPg;
    protected int field_22_cParas;
    protected short field_23_Edn;
    protected short field_24_Edn1;
    protected int field_25_cLines;
    protected int field_26_cWordsFtnEnd;
    protected int field_27_cChFtnEdn;
    protected short field_28_cPgFtnEdn;
    protected int field_29_cParasFtnEdn;
    protected byte field_2_unused2;
    protected int field_30_cLinesFtnEdn;
    protected int field_31_lKeyProtDoc;
    protected short field_32_view;
    protected int field_33_docinfo4;
    protected short field_34_adt;
    protected byte[] field_35_doptypography = new byte[0];
    protected byte[] field_36_dogrid = new byte[0];
    protected short field_37_docinfo5;
    protected short field_38_docinfo6;
    protected byte[] field_39_asumyi = new byte[0];
    protected short field_3_footnoteInfo;
    protected int field_40_cChWS;
    protected int field_41_cChWSFtnEdn;
    protected int field_42_grfDocEvents;
    protected int field_43_virusinfo;
    protected byte[] field_44_Spare = new byte[0];
    protected int field_45_reserved1;
    protected int field_46_reserved2;
    protected int field_47_cDBC;
    protected int field_48_cDBCFtnEdn;
    protected int field_49_reserved;
    protected byte field_4_fOutlineDirtySave;
    protected short field_50_nfcFtnRef;
    protected short field_51_nfcEdnRef;
    protected short field_52_hpsZoonFontPag;
    protected short field_53_dywDispPag;
    protected byte field_5_docinfo;
    protected byte field_6_docinfo1;
    protected byte field_7_docinfo2;
    protected short field_8_docinfo3;
    protected int field_9_dxaTab;

    public static int getSize() {
        return 500;
    }

    protected DOPAbstractType() {
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_formatFlags = bArr[i + 0];
        this.field_2_unused2 = bArr[i + 1];
        this.field_3_footnoteInfo = LittleEndian.getShort(bArr, i + 2);
        this.field_4_fOutlineDirtySave = bArr[i + 4];
        this.field_5_docinfo = bArr[i + 5];
        this.field_6_docinfo1 = bArr[i + 6];
        this.field_7_docinfo2 = bArr[i + 7];
        this.field_8_docinfo3 = LittleEndian.getShort(bArr, i + 8);
        this.field_9_dxaTab = LittleEndian.getShort(bArr, i + 10);
        this.field_10_wSpare = LittleEndian.getShort(bArr, i + 12);
        this.field_11_dxaHotz = LittleEndian.getShort(bArr, i + 14);
        this.field_12_cConsexHypLim = LittleEndian.getShort(bArr, i + 16);
        this.field_13_wSpare2 = LittleEndian.getShort(bArr, i + 18);
        this.field_14_dttmCreated = LittleEndian.getInt(bArr, i + 20);
        this.field_15_dttmRevised = LittleEndian.getInt(bArr, i + 24);
        this.field_16_dttmLastPrint = LittleEndian.getInt(bArr, i + 28);
        this.field_17_nRevision = LittleEndian.getShort(bArr, i + 32);
        this.field_18_tmEdited = LittleEndian.getInt(bArr, i + 34);
        this.field_19_cWords = LittleEndian.getInt(bArr, i + 38);
        this.field_20_cCh = LittleEndian.getInt(bArr, i + 42);
        this.field_21_cPg = LittleEndian.getShort(bArr, i + 46);
        this.field_22_cParas = LittleEndian.getInt(bArr, i + 48);
        this.field_23_Edn = LittleEndian.getShort(bArr, i + 52);
        this.field_24_Edn1 = LittleEndian.getShort(bArr, i + 54);
        this.field_25_cLines = LittleEndian.getInt(bArr, i + 56);
        this.field_26_cWordsFtnEnd = LittleEndian.getInt(bArr, i + 60);
        this.field_27_cChFtnEdn = LittleEndian.getInt(bArr, i + 64);
        this.field_28_cPgFtnEdn = LittleEndian.getShort(bArr, i + 68);
        this.field_29_cParasFtnEdn = LittleEndian.getInt(bArr, i + 70);
        this.field_30_cLinesFtnEdn = LittleEndian.getInt(bArr, i + 74);
        this.field_31_lKeyProtDoc = LittleEndian.getInt(bArr, i + 78);
        this.field_32_view = LittleEndian.getShort(bArr, i + 82);
        this.field_33_docinfo4 = LittleEndian.getInt(bArr, i + 84);
        this.field_34_adt = LittleEndian.getShort(bArr, i + 88);
        this.field_35_doptypography = LittleEndian.getByteArray(bArr, i + 90, 310);
        this.field_36_dogrid = LittleEndian.getByteArray(bArr, i + EMFConstants.FW_NORMAL, 10);
        this.field_37_docinfo5 = LittleEndian.getShort(bArr, i + 410);
        this.field_38_docinfo6 = LittleEndian.getShort(bArr, i + 412);
        this.field_39_asumyi = LittleEndian.getByteArray(bArr, i + 414, 12);
        this.field_40_cChWS = LittleEndian.getInt(bArr, i + 426);
        this.field_41_cChWSFtnEdn = LittleEndian.getInt(bArr, i + 430);
        this.field_42_grfDocEvents = LittleEndian.getInt(bArr, i + 434);
        this.field_43_virusinfo = LittleEndian.getInt(bArr, i + 438);
        this.field_44_Spare = LittleEndian.getByteArray(bArr, i + UnknownRecord.CODENAME_1BA, 30);
        this.field_45_reserved1 = LittleEndian.getInt(bArr, i + 472);
        this.field_46_reserved2 = LittleEndian.getInt(bArr, i + 476);
        this.field_47_cDBC = LittleEndian.getInt(bArr, i + 480);
        this.field_48_cDBCFtnEdn = LittleEndian.getInt(bArr, i + 484);
        this.field_49_reserved = LittleEndian.getInt(bArr, i + 488);
        this.field_50_nfcFtnRef = LittleEndian.getShort(bArr, i + 492);
        this.field_51_nfcEdnRef = LittleEndian.getShort(bArr, i + 494);
        this.field_52_hpsZoonFontPag = LittleEndian.getShort(bArr, i + MetaDo.META_DELETEOBJECT);
        this.field_53_dywDispPag = LittleEndian.getShort(bArr, i + 498);
    }

    public void serialize(byte[] bArr, int i) {
        bArr[i + 0] = this.field_1_formatFlags;
        bArr[i + 1] = this.field_2_unused2;
        LittleEndian.putShort(bArr, i + 2, this.field_3_footnoteInfo);
        bArr[i + 4] = this.field_4_fOutlineDirtySave;
        bArr[i + 5] = this.field_5_docinfo;
        bArr[i + 6] = this.field_6_docinfo1;
        bArr[i + 7] = this.field_7_docinfo2;
        LittleEndian.putShort(bArr, i + 8, this.field_8_docinfo3);
        LittleEndian.putShort(bArr, i + 10, (short) this.field_9_dxaTab);
        LittleEndian.putShort(bArr, i + 12, (short) this.field_10_wSpare);
        LittleEndian.putShort(bArr, i + 14, (short) this.field_11_dxaHotz);
        LittleEndian.putShort(bArr, i + 16, (short) this.field_12_cConsexHypLim);
        LittleEndian.putShort(bArr, i + 18, (short) this.field_13_wSpare2);
        LittleEndian.putInt(bArr, i + 20, this.field_14_dttmCreated);
        LittleEndian.putInt(bArr, i + 24, this.field_15_dttmRevised);
        LittleEndian.putInt(bArr, i + 28, this.field_16_dttmLastPrint);
        LittleEndian.putShort(bArr, i + 32, (short) this.field_17_nRevision);
        LittleEndian.putInt(bArr, i + 34, this.field_18_tmEdited);
        LittleEndian.putInt(bArr, i + 38, this.field_19_cWords);
        LittleEndian.putInt(bArr, i + 42, this.field_20_cCh);
        LittleEndian.putShort(bArr, i + 46, (short) this.field_21_cPg);
        LittleEndian.putInt(bArr, i + 48, this.field_22_cParas);
        LittleEndian.putShort(bArr, i + 52, this.field_23_Edn);
        LittleEndian.putShort(bArr, i + 54, this.field_24_Edn1);
        LittleEndian.putInt(bArr, i + 56, this.field_25_cLines);
        LittleEndian.putInt(bArr, i + 60, this.field_26_cWordsFtnEnd);
        LittleEndian.putInt(bArr, i + 64, this.field_27_cChFtnEdn);
        LittleEndian.putShort(bArr, i + 68, this.field_28_cPgFtnEdn);
        LittleEndian.putInt(bArr, i + 70, this.field_29_cParasFtnEdn);
        LittleEndian.putInt(bArr, i + 74, this.field_30_cLinesFtnEdn);
        LittleEndian.putInt(bArr, i + 78, this.field_31_lKeyProtDoc);
        LittleEndian.putShort(bArr, i + 82, this.field_32_view);
        LittleEndian.putInt(bArr, i + 84, this.field_33_docinfo4);
        LittleEndian.putShort(bArr, i + 88, this.field_34_adt);
        byte[] bArr2 = this.field_35_doptypography;
        System.arraycopy(bArr2, 0, bArr, i + 90, bArr2.length);
        byte[] bArr3 = this.field_36_dogrid;
        System.arraycopy(bArr3, 0, bArr, i + EMFConstants.FW_NORMAL, bArr3.length);
        LittleEndian.putShort(bArr, i + 410, this.field_37_docinfo5);
        LittleEndian.putShort(bArr, i + 412, this.field_38_docinfo6);
        byte[] bArr4 = this.field_39_asumyi;
        System.arraycopy(bArr4, 0, bArr, i + 414, bArr4.length);
        LittleEndian.putInt(bArr, i + 426, this.field_40_cChWS);
        LittleEndian.putInt(bArr, i + 430, this.field_41_cChWSFtnEdn);
        LittleEndian.putInt(bArr, i + 434, this.field_42_grfDocEvents);
        LittleEndian.putInt(bArr, i + 438, this.field_43_virusinfo);
        byte[] bArr5 = this.field_44_Spare;
        System.arraycopy(bArr5, 0, bArr, i + UnknownRecord.CODENAME_1BA, bArr5.length);
        LittleEndian.putInt(bArr, i + 472, this.field_45_reserved1);
        LittleEndian.putInt(bArr, i + 476, this.field_46_reserved2);
        LittleEndian.putInt(bArr, i + 480, this.field_47_cDBC);
        LittleEndian.putInt(bArr, i + 484, this.field_48_cDBCFtnEdn);
        LittleEndian.putInt(bArr, i + 488, this.field_49_reserved);
        LittleEndian.putShort(bArr, i + 492, this.field_50_nfcFtnRef);
        LittleEndian.putShort(bArr, i + 494, this.field_51_nfcEdnRef);
        LittleEndian.putShort(bArr, i + MetaDo.META_DELETEOBJECT, this.field_52_hpsZoonFontPag);
        LittleEndian.putShort(bArr, i + 498, this.field_53_dywDispPag);
    }

    public String toString() {
        return "[DOP]\n" + "    .formatFlags          = " + " (" + getFormatFlags() + " )\n" + "         .fFacingPages             = " + isFFacingPages() + 10 + "         .fWidowControl            = " + isFWidowControl() + 10 + "         .fPMHMainDoc              = " + isFPMHMainDoc() + 10 + "         .grfSupression            = " + getGrfSupression() + 10 + "         .fpc                      = " + getFpc() + 10 + "         .unused1                  = " + isUnused1() + 10 + "    .unused2              = " + " (" + getUnused2() + " )\n" + "    .footnoteInfo         = " + " (" + getFootnoteInfo() + " )\n" + "         .rncFtn                   = " + getRncFtn() + 10 + "         .nFtn                     = " + getNFtn() + 10 + "    .fOutlineDirtySave    = " + " (" + getFOutlineDirtySave() + " )\n" + "    .docinfo              = " + " (" + getDocinfo() + " )\n" + "         .fOnlyMacPics             = " + isFOnlyMacPics() + 10 + "         .fOnlyWinPics             = " + isFOnlyWinPics() + 10 + "         .fLabelDoc                = " + isFLabelDoc() + 10 + "         .fHyphCapitals            = " + isFHyphCapitals() + 10 + "         .fAutoHyphen              = " + isFAutoHyphen() + 10 + "         .fFormNoFields            = " + isFFormNoFields() + 10 + "         .fLinkStyles              = " + isFLinkStyles() + 10 + "         .fRevMarking              = " + isFRevMarking() + 10 + "    .docinfo1             = " + " (" + getDocinfo1() + " )\n" + "         .fBackup                  = " + isFBackup() + 10 + "         .fExactCWords             = " + isFExactCWords() + 10 + "         .fPagHidden               = " + isFPagHidden() + 10 + "         .fPagResults              = " + isFPagResults() + 10 + "         .fLockAtn                 = " + isFLockAtn() + 10 + "         .fMirrorMargins           = " + isFMirrorMargins() + 10 + "         .unused3                  = " + isUnused3() + 10 + "         .fDfltTrueType            = " + isFDfltTrueType() + 10 + "    .docinfo2             = " + " (" + getDocinfo2() + " )\n" + "         .fPagSupressTopSpacing     = " + isFPagSupressTopSpacing() + 10 + "         .fProtEnabled             = " + isFProtEnabled() + 10 + "         .fDispFormFldSel          = " + isFDispFormFldSel() + 10 + "         .fRMView                  = " + isFRMView() + 10 + "         .fRMPrint                 = " + isFRMPrint() + 10 + "         .unused4                  = " + isUnused4() + 10 + "         .fLockRev                 = " + isFLockRev() + 10 + "         .fEmbedFonts              = " + isFEmbedFonts() + 10 + "    .docinfo3             = " + " (" + getDocinfo3() + " )\n" + "         .oldfNoTabForInd          = " + isOldfNoTabForInd() + 10 + "         .oldfNoSpaceRaiseLower     = " + isOldfNoSpaceRaiseLower() + 10 + "         .oldfSuppressSpbfAfterPageBreak     = " + isOldfSuppressSpbfAfterPageBreak() + 10 + "         .oldfWrapTrailSpaces      = " + isOldfWrapTrailSpaces() + 10 + "         .oldfMapPrintTextColor     = " + isOldfMapPrintTextColor() + 10 + "         .oldfNoColumnBalance      = " + isOldfNoColumnBalance() + 10 + "         .oldfConvMailMergeEsc     = " + isOldfConvMailMergeEsc() + 10 + "         .oldfSupressTopSpacing     = " + isOldfSupressTopSpacing() + 10 + "         .oldfOrigWordTableRules     = " + isOldfOrigWordTableRules() + 10 + "         .oldfTransparentMetafiles     = " + isOldfTransparentMetafiles() + 10 + "         .oldfShowBreaksInFrames     = " + isOldfShowBreaksInFrames() + 10 + "         .oldfSwapBordersFacingPgs     = " + isOldfSwapBordersFacingPgs() + 10 + "         .unused5                  = " + getUnused5() + 10 + "    .dxaTab               = " + " (" + getDxaTab() + " )\n" + "    .wSpare               = " + " (" + getWSpare() + " )\n" + "    .dxaHotz              = " + " (" + getDxaHotz() + " )\n" + "    .cConsexHypLim        = " + " (" + getCConsexHypLim() + " )\n" + "    .wSpare2              = " + " (" + getWSpare2() + " )\n" + "    .dttmCreated          = " + " (" + getDttmCreated() + " )\n" + "    .dttmRevised          = " + " (" + getDttmRevised() + " )\n" + "    .dttmLastPrint        = " + " (" + getDttmLastPrint() + " )\n" + "    .nRevision            = " + " (" + getNRevision() + " )\n" + "    .tmEdited             = " + " (" + getTmEdited() + " )\n" + "    .cWords               = " + " (" + getCWords() + " )\n" + "    .cCh                  = " + " (" + getCCh() + " )\n" + "    .cPg                  = " + " (" + getCPg() + " )\n" + "    .cParas               = " + " (" + getCParas() + " )\n" + "    .Edn                  = " + " (" + getEdn() + " )\n" + "         .rncEdn                   = " + getRncEdn() + 10 + "         .nEdn                     = " + getNEdn() + 10 + "    .Edn1                 = " + " (" + getEdn1() + " )\n" + "         .epc                      = " + getEpc() + 10 + "         .nfcFtnRef1               = " + getNfcFtnRef1() + 10 + "         .nfcEdnRef1               = " + getNfcEdnRef1() + 10 + "         .fPrintFormData           = " + isFPrintFormData() + 10 + "         .fSaveFormData            = " + isFSaveFormData() + 10 + "         .fShadeFormData           = " + isFShadeFormData() + 10 + "         .fWCFtnEdn                = " + isFWCFtnEdn() + 10 + "    .cLines               = " + " (" + getCLines() + " )\n" + "    .cWordsFtnEnd         = " + " (" + getCWordsFtnEnd() + " )\n" + "    .cChFtnEdn            = " + " (" + getCChFtnEdn() + " )\n" + "    .cPgFtnEdn            = " + " (" + getCPgFtnEdn() + " )\n" + "    .cParasFtnEdn         = " + " (" + getCParasFtnEdn() + " )\n" + "    .cLinesFtnEdn         = " + " (" + getCLinesFtnEdn() + " )\n" + "    .lKeyProtDoc          = " + " (" + getLKeyProtDoc() + " )\n" + "    .view                 = " + " (" + getView() + " )\n" + "         .wvkSaved                 = " + getWvkSaved() + 10 + "         .wScaleSaved              = " + getWScaleSaved() + 10 + "         .zkSaved                  = " + getZkSaved() + 10 + "         .fRotateFontW6            = " + isFRotateFontW6() + 10 + "         .iGutterPos               = " + isIGutterPos() + 10 + "    .docinfo4             = " + " (" + getDocinfo4() + " )\n" + "         .fNoTabForInd             = " + isFNoTabForInd() + 10 + "         .fNoSpaceRaiseLower       = " + isFNoSpaceRaiseLower() + 10 + "         .fSupressSpdfAfterPageBreak     = " + isFSupressSpdfAfterPageBreak() + 10 + "         .fWrapTrailSpaces         = " + isFWrapTrailSpaces() + 10 + "         .fMapPrintTextColor       = " + isFMapPrintTextColor() + 10 + "         .fNoColumnBalance         = " + isFNoColumnBalance() + 10 + "         .fConvMailMergeEsc        = " + isFConvMailMergeEsc() + 10 + "         .fSupressTopSpacing       = " + isFSupressTopSpacing() + 10 + "         .fOrigWordTableRules      = " + isFOrigWordTableRules() + 10 + "         .fTransparentMetafiles     = " + isFTransparentMetafiles() + 10 + "         .fShowBreaksInFrames      = " + isFShowBreaksInFrames() + 10 + "         .fSwapBordersFacingPgs     = " + isFSwapBordersFacingPgs() + 10 + "         .fSuppressTopSPacingMac5     = " + isFSuppressTopSPacingMac5() + 10 + "         .fTruncDxaExpand          = " + isFTruncDxaExpand() + 10 + "         .fPrintBodyBeforeHdr      = " + isFPrintBodyBeforeHdr() + 10 + "         .fNoLeading               = " + isFNoLeading() + 10 + "         .fMWSmallCaps             = " + isFMWSmallCaps() + 10 + "    .adt                  = " + " (" + getAdt() + " )\n" + "    .doptypography        = " + " (" + getDoptypography() + " )\n" + "    .dogrid               = " + " (" + getDogrid() + " )\n" + "    .docinfo5             = " + " (" + getDocinfo5() + " )\n" + "         .lvl                      = " + getLvl() + 10 + "         .fGramAllDone             = " + isFGramAllDone() + 10 + "         .fGramAllClean            = " + isFGramAllClean() + 10 + "         .fSubsetFonts             = " + isFSubsetFonts() + 10 + "         .fHideLastVersion         = " + isFHideLastVersion() + 10 + "         .fHtmlDoc                 = " + isFHtmlDoc() + 10 + "         .fSnapBorder              = " + isFSnapBorder() + 10 + "         .fIncludeHeader           = " + isFIncludeHeader() + 10 + "         .fIncludeFooter           = " + isFIncludeFooter() + 10 + "         .fForcePageSizePag        = " + isFForcePageSizePag() + 10 + "         .fMinFontSizePag          = " + isFMinFontSizePag() + 10 + "    .docinfo6             = " + " (" + getDocinfo6() + " )\n" + "         .fHaveVersions            = " + isFHaveVersions() + 10 + "         .fAutoVersions            = " + isFAutoVersions() + 10 + "    .asumyi               = " + " (" + getAsumyi() + " )\n" + "    .cChWS                = " + " (" + getCChWS() + " )\n" + "    .cChWSFtnEdn          = " + " (" + getCChWSFtnEdn() + " )\n" + "    .grfDocEvents         = " + " (" + getGrfDocEvents() + " )\n" + "    .virusinfo            = " + " (" + getVirusinfo() + " )\n" + "         .fVirusPrompted           = " + isFVirusPrompted() + 10 + "         .fVirusLoadSafe           = " + isFVirusLoadSafe() + 10 + "         .KeyVirusSession30        = " + getKeyVirusSession30() + 10 + "    .Spare                = " + " (" + getSpare() + " )\n" + "    .reserved1            = " + " (" + getReserved1() + " )\n" + "    .reserved2            = " + " (" + getReserved2() + " )\n" + "    .cDBC                 = " + " (" + getCDBC() + " )\n" + "    .cDBCFtnEdn           = " + " (" + getCDBCFtnEdn() + " )\n" + "    .reserved             = " + " (" + getReserved() + " )\n" + "    .nfcFtnRef            = " + " (" + getNfcFtnRef() + " )\n" + "    .nfcEdnRef            = " + " (" + getNfcEdnRef() + " )\n" + "    .hpsZoonFontPag       = " + " (" + getHpsZoonFontPag() + " )\n" + "    .dywDispPag           = " + " (" + getDywDispPag() + " )\n" + "[/DOP]\n";
    }

    @Internal
    public byte getFormatFlags() {
        return this.field_1_formatFlags;
    }

    @Internal
    public void setFormatFlags(byte b) {
        this.field_1_formatFlags = b;
    }

    @Internal
    public byte getUnused2() {
        return this.field_2_unused2;
    }

    @Internal
    public void setUnused2(byte b) {
        this.field_2_unused2 = b;
    }

    @Internal
    public short getFootnoteInfo() {
        return this.field_3_footnoteInfo;
    }

    @Internal
    public void setFootnoteInfo(short s) {
        this.field_3_footnoteInfo = s;
    }

    @Internal
    public byte getFOutlineDirtySave() {
        return this.field_4_fOutlineDirtySave;
    }

    @Internal
    public void setFOutlineDirtySave(byte b) {
        this.field_4_fOutlineDirtySave = b;
    }

    @Internal
    public byte getDocinfo() {
        return this.field_5_docinfo;
    }

    @Internal
    public void setDocinfo(byte b) {
        this.field_5_docinfo = b;
    }

    @Internal
    public byte getDocinfo1() {
        return this.field_6_docinfo1;
    }

    @Internal
    public void setDocinfo1(byte b) {
        this.field_6_docinfo1 = b;
    }

    @Internal
    public byte getDocinfo2() {
        return this.field_7_docinfo2;
    }

    @Internal
    public void setDocinfo2(byte b) {
        this.field_7_docinfo2 = b;
    }

    @Internal
    public short getDocinfo3() {
        return this.field_8_docinfo3;
    }

    @Internal
    public void setDocinfo3(short s) {
        this.field_8_docinfo3 = s;
    }

    @Internal
    public int getDxaTab() {
        return this.field_9_dxaTab;
    }

    @Internal
    public void setDxaTab(int i) {
        this.field_9_dxaTab = i;
    }

    @Internal
    public int getWSpare() {
        return this.field_10_wSpare;
    }

    @Internal
    public void setWSpare(int i) {
        this.field_10_wSpare = i;
    }

    @Internal
    public int getDxaHotz() {
        return this.field_11_dxaHotz;
    }

    @Internal
    public void setDxaHotz(int i) {
        this.field_11_dxaHotz = i;
    }

    @Internal
    public int getCConsexHypLim() {
        return this.field_12_cConsexHypLim;
    }

    @Internal
    public void setCConsexHypLim(int i) {
        this.field_12_cConsexHypLim = i;
    }

    @Internal
    public int getWSpare2() {
        return this.field_13_wSpare2;
    }

    @Internal
    public void setWSpare2(int i) {
        this.field_13_wSpare2 = i;
    }

    @Internal
    public int getDttmCreated() {
        return this.field_14_dttmCreated;
    }

    @Internal
    public void setDttmCreated(int i) {
        this.field_14_dttmCreated = i;
    }

    @Internal
    public int getDttmRevised() {
        return this.field_15_dttmRevised;
    }

    @Internal
    public void setDttmRevised(int i) {
        this.field_15_dttmRevised = i;
    }

    @Internal
    public int getDttmLastPrint() {
        return this.field_16_dttmLastPrint;
    }

    @Internal
    public void setDttmLastPrint(int i) {
        this.field_16_dttmLastPrint = i;
    }

    @Internal
    public int getNRevision() {
        return this.field_17_nRevision;
    }

    @Internal
    public void setNRevision(int i) {
        this.field_17_nRevision = i;
    }

    @Internal
    public int getTmEdited() {
        return this.field_18_tmEdited;
    }

    @Internal
    public void setTmEdited(int i) {
        this.field_18_tmEdited = i;
    }

    @Internal
    public int getCWords() {
        return this.field_19_cWords;
    }

    @Internal
    public void setCWords(int i) {
        this.field_19_cWords = i;
    }

    @Internal
    public int getCCh() {
        return this.field_20_cCh;
    }

    @Internal
    public void setCCh(int i) {
        this.field_20_cCh = i;
    }

    @Internal
    public int getCPg() {
        return this.field_21_cPg;
    }

    @Internal
    public void setCPg(int i) {
        this.field_21_cPg = i;
    }

    @Internal
    public int getCParas() {
        return this.field_22_cParas;
    }

    @Internal
    public void setCParas(int i) {
        this.field_22_cParas = i;
    }

    @Internal
    public short getEdn() {
        return this.field_23_Edn;
    }

    @Internal
    public void setEdn(short s) {
        this.field_23_Edn = s;
    }

    @Internal
    public short getEdn1() {
        return this.field_24_Edn1;
    }

    @Internal
    public void setEdn1(short s) {
        this.field_24_Edn1 = s;
    }

    @Internal
    public int getCLines() {
        return this.field_25_cLines;
    }

    @Internal
    public void setCLines(int i) {
        this.field_25_cLines = i;
    }

    @Internal
    public int getCWordsFtnEnd() {
        return this.field_26_cWordsFtnEnd;
    }

    @Internal
    public void setCWordsFtnEnd(int i) {
        this.field_26_cWordsFtnEnd = i;
    }

    @Internal
    public int getCChFtnEdn() {
        return this.field_27_cChFtnEdn;
    }

    @Internal
    public void setCChFtnEdn(int i) {
        this.field_27_cChFtnEdn = i;
    }

    @Internal
    public short getCPgFtnEdn() {
        return this.field_28_cPgFtnEdn;
    }

    @Internal
    public void setCPgFtnEdn(short s) {
        this.field_28_cPgFtnEdn = s;
    }

    @Internal
    public int getCParasFtnEdn() {
        return this.field_29_cParasFtnEdn;
    }

    @Internal
    public void setCParasFtnEdn(int i) {
        this.field_29_cParasFtnEdn = i;
    }

    @Internal
    public int getCLinesFtnEdn() {
        return this.field_30_cLinesFtnEdn;
    }

    @Internal
    public void setCLinesFtnEdn(int i) {
        this.field_30_cLinesFtnEdn = i;
    }

    @Internal
    public int getLKeyProtDoc() {
        return this.field_31_lKeyProtDoc;
    }

    @Internal
    public void setLKeyProtDoc(int i) {
        this.field_31_lKeyProtDoc = i;
    }

    @Internal
    public short getView() {
        return this.field_32_view;
    }

    @Internal
    public void setView(short s) {
        this.field_32_view = s;
    }

    @Internal
    public int getDocinfo4() {
        return this.field_33_docinfo4;
    }

    @Internal
    public void setDocinfo4(int i) {
        this.field_33_docinfo4 = i;
    }

    @Internal
    public short getAdt() {
        return this.field_34_adt;
    }

    @Internal
    public void setAdt(short s) {
        this.field_34_adt = s;
    }

    @Internal
    public byte[] getDoptypography() {
        return this.field_35_doptypography;
    }

    @Internal
    public void setDoptypography(byte[] bArr) {
        this.field_35_doptypography = bArr;
    }

    @Internal
    public byte[] getDogrid() {
        return this.field_36_dogrid;
    }

    @Internal
    public void setDogrid(byte[] bArr) {
        this.field_36_dogrid = bArr;
    }

    @Internal
    public short getDocinfo5() {
        return this.field_37_docinfo5;
    }

    @Internal
    public void setDocinfo5(short s) {
        this.field_37_docinfo5 = s;
    }

    @Internal
    public short getDocinfo6() {
        return this.field_38_docinfo6;
    }

    @Internal
    public void setDocinfo6(short s) {
        this.field_38_docinfo6 = s;
    }

    @Internal
    public byte[] getAsumyi() {
        return this.field_39_asumyi;
    }

    @Internal
    public void setAsumyi(byte[] bArr) {
        this.field_39_asumyi = bArr;
    }

    @Internal
    public int getCChWS() {
        return this.field_40_cChWS;
    }

    @Internal
    public void setCChWS(int i) {
        this.field_40_cChWS = i;
    }

    @Internal
    public int getCChWSFtnEdn() {
        return this.field_41_cChWSFtnEdn;
    }

    @Internal
    public void setCChWSFtnEdn(int i) {
        this.field_41_cChWSFtnEdn = i;
    }

    @Internal
    public int getGrfDocEvents() {
        return this.field_42_grfDocEvents;
    }

    @Internal
    public void setGrfDocEvents(int i) {
        this.field_42_grfDocEvents = i;
    }

    @Internal
    public int getVirusinfo() {
        return this.field_43_virusinfo;
    }

    @Internal
    public void setVirusinfo(int i) {
        this.field_43_virusinfo = i;
    }

    @Internal
    public byte[] getSpare() {
        return this.field_44_Spare;
    }

    @Internal
    public void setSpare(byte[] bArr) {
        this.field_44_Spare = bArr;
    }

    @Internal
    public int getReserved1() {
        return this.field_45_reserved1;
    }

    @Internal
    public void setReserved1(int i) {
        this.field_45_reserved1 = i;
    }

    @Internal
    public int getReserved2() {
        return this.field_46_reserved2;
    }

    @Internal
    public void setReserved2(int i) {
        this.field_46_reserved2 = i;
    }

    @Internal
    public int getCDBC() {
        return this.field_47_cDBC;
    }

    @Internal
    public void setCDBC(int i) {
        this.field_47_cDBC = i;
    }

    @Internal
    public int getCDBCFtnEdn() {
        return this.field_48_cDBCFtnEdn;
    }

    @Internal
    public void setCDBCFtnEdn(int i) {
        this.field_48_cDBCFtnEdn = i;
    }

    @Internal
    public int getReserved() {
        return this.field_49_reserved;
    }

    @Internal
    public void setReserved(int i) {
        this.field_49_reserved = i;
    }

    @Internal
    public short getNfcFtnRef() {
        return this.field_50_nfcFtnRef;
    }

    @Internal
    public void setNfcFtnRef(short s) {
        this.field_50_nfcFtnRef = s;
    }

    @Internal
    public short getNfcEdnRef() {
        return this.field_51_nfcEdnRef;
    }

    @Internal
    public void setNfcEdnRef(short s) {
        this.field_51_nfcEdnRef = s;
    }

    @Internal
    public short getHpsZoonFontPag() {
        return this.field_52_hpsZoonFontPag;
    }

    @Internal
    public void setHpsZoonFontPag(short s) {
        this.field_52_hpsZoonFontPag = s;
    }

    @Internal
    public short getDywDispPag() {
        return this.field_53_dywDispPag;
    }

    @Internal
    public void setDywDispPag(short s) {
        this.field_53_dywDispPag = s;
    }

    @Internal
    public void setFFacingPages(boolean z) {
        this.field_1_formatFlags = (byte) fFacingPages.setBoolean(this.field_1_formatFlags, z);
    }

    @Internal
    public boolean isFFacingPages() {
        return fFacingPages.isSet(this.field_1_formatFlags);
    }

    @Internal
    public void setFWidowControl(boolean z) {
        this.field_1_formatFlags = (byte) fWidowControl.setBoolean(this.field_1_formatFlags, z);
    }

    @Internal
    public boolean isFWidowControl() {
        return fWidowControl.isSet(this.field_1_formatFlags);
    }

    @Internal
    public void setFPMHMainDoc(boolean z) {
        this.field_1_formatFlags = (byte) fPMHMainDoc.setBoolean(this.field_1_formatFlags, z);
    }

    @Internal
    public boolean isFPMHMainDoc() {
        return fPMHMainDoc.isSet(this.field_1_formatFlags);
    }

    @Internal
    public void setGrfSupression(byte b) {
        this.field_1_formatFlags = (byte) grfSupression.setValue(this.field_1_formatFlags, b);
    }

    @Internal
    public byte getGrfSupression() {
        return (byte) grfSupression.getValue(this.field_1_formatFlags);
    }

    @Internal
    public void setFpc(byte b) {
        this.field_1_formatFlags = (byte) fpc.setValue(this.field_1_formatFlags, b);
    }

    @Internal
    public byte getFpc() {
        return (byte) fpc.getValue(this.field_1_formatFlags);
    }

    @Internal
    public void setUnused1(boolean z) {
        this.field_1_formatFlags = (byte) unused1.setBoolean(this.field_1_formatFlags, z);
    }

    @Internal
    public boolean isUnused1() {
        return unused1.isSet(this.field_1_formatFlags);
    }

    @Internal
    public void setRncFtn(byte b) {
        this.field_3_footnoteInfo = (short) rncFtn.setValue(this.field_3_footnoteInfo, b);
    }

    @Internal
    public byte getRncFtn() {
        return (byte) rncFtn.getValue(this.field_3_footnoteInfo);
    }

    @Internal
    public void setNFtn(short s) {
        this.field_3_footnoteInfo = (short) nFtn.setValue(this.field_3_footnoteInfo, s);
    }

    @Internal
    public short getNFtn() {
        return (short) nFtn.getValue(this.field_3_footnoteInfo);
    }

    @Internal
    public void setFOnlyMacPics(boolean z) {
        this.field_5_docinfo = (byte) fOnlyMacPics.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFOnlyMacPics() {
        return fOnlyMacPics.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFOnlyWinPics(boolean z) {
        this.field_5_docinfo = (byte) fOnlyWinPics.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFOnlyWinPics() {
        return fOnlyWinPics.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFLabelDoc(boolean z) {
        this.field_5_docinfo = (byte) fLabelDoc.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFLabelDoc() {
        return fLabelDoc.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFHyphCapitals(boolean z) {
        this.field_5_docinfo = (byte) fHyphCapitals.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFHyphCapitals() {
        return fHyphCapitals.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFAutoHyphen(boolean z) {
        this.field_5_docinfo = (byte) fAutoHyphen.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFAutoHyphen() {
        return fAutoHyphen.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFFormNoFields(boolean z) {
        this.field_5_docinfo = (byte) fFormNoFields.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFFormNoFields() {
        return fFormNoFields.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFLinkStyles(boolean z) {
        this.field_5_docinfo = (byte) fLinkStyles.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFLinkStyles() {
        return fLinkStyles.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFRevMarking(boolean z) {
        this.field_5_docinfo = (byte) fRevMarking.setBoolean(this.field_5_docinfo, z);
    }

    @Internal
    public boolean isFRevMarking() {
        return fRevMarking.isSet(this.field_5_docinfo);
    }

    @Internal
    public void setFBackup(boolean z) {
        this.field_6_docinfo1 = (byte) fBackup.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isFBackup() {
        return fBackup.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setFExactCWords(boolean z) {
        this.field_6_docinfo1 = (byte) fExactCWords.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isFExactCWords() {
        return fExactCWords.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setFPagHidden(boolean z) {
        this.field_6_docinfo1 = (byte) fPagHidden.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isFPagHidden() {
        return fPagHidden.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setFPagResults(boolean z) {
        this.field_6_docinfo1 = (byte) fPagResults.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isFPagResults() {
        return fPagResults.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setFLockAtn(boolean z) {
        this.field_6_docinfo1 = (byte) fLockAtn.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isFLockAtn() {
        return fLockAtn.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setFMirrorMargins(boolean z) {
        this.field_6_docinfo1 = (byte) fMirrorMargins.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isFMirrorMargins() {
        return fMirrorMargins.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setUnused3(boolean z) {
        this.field_6_docinfo1 = (byte) unused3.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isUnused3() {
        return unused3.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setFDfltTrueType(boolean z) {
        this.field_6_docinfo1 = (byte) fDfltTrueType.setBoolean(this.field_6_docinfo1, z);
    }

    @Internal
    public boolean isFDfltTrueType() {
        return fDfltTrueType.isSet(this.field_6_docinfo1);
    }

    @Internal
    public void setFPagSupressTopSpacing(boolean z) {
        this.field_7_docinfo2 = (byte) fPagSupressTopSpacing.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isFPagSupressTopSpacing() {
        return fPagSupressTopSpacing.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setFProtEnabled(boolean z) {
        this.field_7_docinfo2 = (byte) fProtEnabled.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isFProtEnabled() {
        return fProtEnabled.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setFDispFormFldSel(boolean z) {
        this.field_7_docinfo2 = (byte) fDispFormFldSel.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isFDispFormFldSel() {
        return fDispFormFldSel.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setFRMView(boolean z) {
        this.field_7_docinfo2 = (byte) fRMView.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isFRMView() {
        return fRMView.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setFRMPrint(boolean z) {
        this.field_7_docinfo2 = (byte) fRMPrint.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isFRMPrint() {
        return fRMPrint.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setUnused4(boolean z) {
        this.field_7_docinfo2 = (byte) unused4.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isUnused4() {
        return unused4.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setFLockRev(boolean z) {
        this.field_7_docinfo2 = (byte) fLockRev.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isFLockRev() {
        return fLockRev.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setFEmbedFonts(boolean z) {
        this.field_7_docinfo2 = (byte) fEmbedFonts.setBoolean(this.field_7_docinfo2, z);
    }

    @Internal
    public boolean isFEmbedFonts() {
        return fEmbedFonts.isSet(this.field_7_docinfo2);
    }

    @Internal
    public void setOldfNoTabForInd(boolean z) {
        this.field_8_docinfo3 = (short) oldfNoTabForInd.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfNoTabForInd() {
        return oldfNoTabForInd.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfNoSpaceRaiseLower(boolean z) {
        this.field_8_docinfo3 = (short) oldfNoSpaceRaiseLower.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfNoSpaceRaiseLower() {
        return oldfNoSpaceRaiseLower.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfSuppressSpbfAfterPageBreak(boolean z) {
        this.field_8_docinfo3 = (short) oldfSuppressSpbfAfterPageBreak.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfSuppressSpbfAfterPageBreak() {
        return oldfSuppressSpbfAfterPageBreak.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfWrapTrailSpaces(boolean z) {
        this.field_8_docinfo3 = (short) oldfWrapTrailSpaces.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfWrapTrailSpaces() {
        return oldfWrapTrailSpaces.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfMapPrintTextColor(boolean z) {
        this.field_8_docinfo3 = (short) oldfMapPrintTextColor.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfMapPrintTextColor() {
        return oldfMapPrintTextColor.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfNoColumnBalance(boolean z) {
        this.field_8_docinfo3 = (short) oldfNoColumnBalance.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfNoColumnBalance() {
        return oldfNoColumnBalance.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfConvMailMergeEsc(boolean z) {
        this.field_8_docinfo3 = (short) oldfConvMailMergeEsc.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfConvMailMergeEsc() {
        return oldfConvMailMergeEsc.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfSupressTopSpacing(boolean z) {
        this.field_8_docinfo3 = (short) oldfSupressTopSpacing.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfSupressTopSpacing() {
        return oldfSupressTopSpacing.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfOrigWordTableRules(boolean z) {
        this.field_8_docinfo3 = (short) oldfOrigWordTableRules.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfOrigWordTableRules() {
        return oldfOrigWordTableRules.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfTransparentMetafiles(boolean z) {
        this.field_8_docinfo3 = (short) oldfTransparentMetafiles.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfTransparentMetafiles() {
        return oldfTransparentMetafiles.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfShowBreaksInFrames(boolean z) {
        this.field_8_docinfo3 = (short) oldfShowBreaksInFrames.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfShowBreaksInFrames() {
        return oldfShowBreaksInFrames.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setOldfSwapBordersFacingPgs(boolean z) {
        this.field_8_docinfo3 = (short) oldfSwapBordersFacingPgs.setBoolean(this.field_8_docinfo3, z);
    }

    @Internal
    public boolean isOldfSwapBordersFacingPgs() {
        return oldfSwapBordersFacingPgs.isSet(this.field_8_docinfo3);
    }

    @Internal
    public void setUnused5(byte b) {
        this.field_8_docinfo3 = (short) unused5.setValue(this.field_8_docinfo3, b);
    }

    @Internal
    public byte getUnused5() {
        return (byte) unused5.getValue(this.field_8_docinfo3);
    }

    @Internal
    public void setRncEdn(byte b) {
        this.field_23_Edn = (short) rncEdn.setValue(this.field_23_Edn, b);
    }

    @Internal
    public byte getRncEdn() {
        return (byte) rncEdn.getValue(this.field_23_Edn);
    }

    @Internal
    public void setNEdn(short s) {
        this.field_23_Edn = (short) nEdn.setValue(this.field_23_Edn, s);
    }

    @Internal
    public short getNEdn() {
        return (short) nEdn.getValue(this.field_23_Edn);
    }

    @Internal
    public void setEpc(byte b) {
        this.field_24_Edn1 = (short) epc.setValue(this.field_24_Edn1, b);
    }

    @Internal
    public byte getEpc() {
        return (byte) epc.getValue(this.field_24_Edn1);
    }

    @Internal
    public void setNfcFtnRef1(byte b) {
        this.field_24_Edn1 = (short) nfcFtnRef1.setValue(this.field_24_Edn1, b);
    }

    @Internal
    public byte getNfcFtnRef1() {
        return (byte) nfcFtnRef1.getValue(this.field_24_Edn1);
    }

    @Internal
    public void setNfcEdnRef1(byte b) {
        this.field_24_Edn1 = (short) nfcEdnRef1.setValue(this.field_24_Edn1, b);
    }

    @Internal
    public byte getNfcEdnRef1() {
        return (byte) nfcEdnRef1.getValue(this.field_24_Edn1);
    }

    @Internal
    public void setFPrintFormData(boolean z) {
        this.field_24_Edn1 = (short) fPrintFormData.setBoolean(this.field_24_Edn1, z);
    }

    @Internal
    public boolean isFPrintFormData() {
        return fPrintFormData.isSet(this.field_24_Edn1);
    }

    @Internal
    public void setFSaveFormData(boolean z) {
        this.field_24_Edn1 = (short) fSaveFormData.setBoolean(this.field_24_Edn1, z);
    }

    @Internal
    public boolean isFSaveFormData() {
        return fSaveFormData.isSet(this.field_24_Edn1);
    }

    @Internal
    public void setFShadeFormData(boolean z) {
        this.field_24_Edn1 = (short) fShadeFormData.setBoolean(this.field_24_Edn1, z);
    }

    @Internal
    public boolean isFShadeFormData() {
        return fShadeFormData.isSet(this.field_24_Edn1);
    }

    @Internal
    public void setFWCFtnEdn(boolean z) {
        this.field_24_Edn1 = (short) fWCFtnEdn.setBoolean(this.field_24_Edn1, z);
    }

    @Internal
    public boolean isFWCFtnEdn() {
        return fWCFtnEdn.isSet(this.field_24_Edn1);
    }

    @Internal
    public void setWvkSaved(byte b) {
        this.field_32_view = (short) wvkSaved.setValue(this.field_32_view, b);
    }

    @Internal
    public byte getWvkSaved() {
        return (byte) wvkSaved.getValue(this.field_32_view);
    }

    @Internal
    public void setWScaleSaved(short s) {
        this.field_32_view = (short) wScaleSaved.setValue(this.field_32_view, s);
    }

    @Internal
    public short getWScaleSaved() {
        return (short) wScaleSaved.getValue(this.field_32_view);
    }

    @Internal
    public void setZkSaved(byte b) {
        this.field_32_view = (short) zkSaved.setValue(this.field_32_view, b);
    }

    @Internal
    public byte getZkSaved() {
        return (byte) zkSaved.getValue(this.field_32_view);
    }

    @Internal
    public void setFRotateFontW6(boolean z) {
        this.field_32_view = (short) fRotateFontW6.setBoolean(this.field_32_view, z);
    }

    @Internal
    public boolean isFRotateFontW6() {
        return fRotateFontW6.isSet(this.field_32_view);
    }

    @Internal
    public void setIGutterPos(boolean z) {
        this.field_32_view = (short) iGutterPos.setBoolean(this.field_32_view, z);
    }

    @Internal
    public boolean isIGutterPos() {
        return iGutterPos.isSet(this.field_32_view);
    }

    @Internal
    public void setFNoTabForInd(boolean z) {
        this.field_33_docinfo4 = fNoTabForInd.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFNoTabForInd() {
        return fNoTabForInd.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFNoSpaceRaiseLower(boolean z) {
        this.field_33_docinfo4 = fNoSpaceRaiseLower.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFNoSpaceRaiseLower() {
        return fNoSpaceRaiseLower.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFSupressSpdfAfterPageBreak(boolean z) {
        this.field_33_docinfo4 = fSupressSpdfAfterPageBreak.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFSupressSpdfAfterPageBreak() {
        return fSupressSpdfAfterPageBreak.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFWrapTrailSpaces(boolean z) {
        this.field_33_docinfo4 = fWrapTrailSpaces.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFWrapTrailSpaces() {
        return fWrapTrailSpaces.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFMapPrintTextColor(boolean z) {
        this.field_33_docinfo4 = fMapPrintTextColor.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFMapPrintTextColor() {
        return fMapPrintTextColor.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFNoColumnBalance(boolean z) {
        this.field_33_docinfo4 = fNoColumnBalance.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFNoColumnBalance() {
        return fNoColumnBalance.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFConvMailMergeEsc(boolean z) {
        this.field_33_docinfo4 = fConvMailMergeEsc.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFConvMailMergeEsc() {
        return fConvMailMergeEsc.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFSupressTopSpacing(boolean z) {
        this.field_33_docinfo4 = fSupressTopSpacing.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFSupressTopSpacing() {
        return fSupressTopSpacing.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFOrigWordTableRules(boolean z) {
        this.field_33_docinfo4 = fOrigWordTableRules.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFOrigWordTableRules() {
        return fOrigWordTableRules.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFTransparentMetafiles(boolean z) {
        this.field_33_docinfo4 = fTransparentMetafiles.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFTransparentMetafiles() {
        return fTransparentMetafiles.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFShowBreaksInFrames(boolean z) {
        this.field_33_docinfo4 = fShowBreaksInFrames.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFShowBreaksInFrames() {
        return fShowBreaksInFrames.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFSwapBordersFacingPgs(boolean z) {
        this.field_33_docinfo4 = fSwapBordersFacingPgs.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFSwapBordersFacingPgs() {
        return fSwapBordersFacingPgs.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFSuppressTopSPacingMac5(boolean z) {
        this.field_33_docinfo4 = fSuppressTopSPacingMac5.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFSuppressTopSPacingMac5() {
        return fSuppressTopSPacingMac5.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFTruncDxaExpand(boolean z) {
        this.field_33_docinfo4 = fTruncDxaExpand.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFTruncDxaExpand() {
        return fTruncDxaExpand.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFPrintBodyBeforeHdr(boolean z) {
        this.field_33_docinfo4 = fPrintBodyBeforeHdr.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFPrintBodyBeforeHdr() {
        return fPrintBodyBeforeHdr.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFNoLeading(boolean z) {
        this.field_33_docinfo4 = fNoLeading.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFNoLeading() {
        return fNoLeading.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setFMWSmallCaps(boolean z) {
        this.field_33_docinfo4 = fMWSmallCaps.setBoolean(this.field_33_docinfo4, z);
    }

    @Internal
    public boolean isFMWSmallCaps() {
        return fMWSmallCaps.isSet(this.field_33_docinfo4);
    }

    @Internal
    public void setLvl(byte b) {
        this.field_37_docinfo5 = (short) lvl.setValue(this.field_37_docinfo5, b);
    }

    @Internal
    public byte getLvl() {
        return (byte) lvl.getValue(this.field_37_docinfo5);
    }

    @Internal
    public void setFGramAllDone(boolean z) {
        this.field_37_docinfo5 = (short) fGramAllDone.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFGramAllDone() {
        return fGramAllDone.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFGramAllClean(boolean z) {
        this.field_37_docinfo5 = (short) fGramAllClean.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFGramAllClean() {
        return fGramAllClean.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFSubsetFonts(boolean z) {
        this.field_37_docinfo5 = (short) fSubsetFonts.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFSubsetFonts() {
        return fSubsetFonts.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFHideLastVersion(boolean z) {
        this.field_37_docinfo5 = (short) fHideLastVersion.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFHideLastVersion() {
        return fHideLastVersion.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFHtmlDoc(boolean z) {
        this.field_37_docinfo5 = (short) fHtmlDoc.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFHtmlDoc() {
        return fHtmlDoc.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFSnapBorder(boolean z) {
        this.field_37_docinfo5 = (short) fSnapBorder.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFSnapBorder() {
        return fSnapBorder.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFIncludeHeader(boolean z) {
        this.field_37_docinfo5 = (short) fIncludeHeader.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFIncludeHeader() {
        return fIncludeHeader.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFIncludeFooter(boolean z) {
        this.field_37_docinfo5 = (short) fIncludeFooter.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFIncludeFooter() {
        return fIncludeFooter.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFForcePageSizePag(boolean z) {
        this.field_37_docinfo5 = (short) fForcePageSizePag.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFForcePageSizePag() {
        return fForcePageSizePag.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFMinFontSizePag(boolean z) {
        this.field_37_docinfo5 = (short) fMinFontSizePag.setBoolean(this.field_37_docinfo5, z);
    }

    @Internal
    public boolean isFMinFontSizePag() {
        return fMinFontSizePag.isSet(this.field_37_docinfo5);
    }

    @Internal
    public void setFHaveVersions(boolean z) {
        this.field_38_docinfo6 = (short) fHaveVersions.setBoolean(this.field_38_docinfo6, z);
    }

    @Internal
    public boolean isFHaveVersions() {
        return fHaveVersions.isSet(this.field_38_docinfo6);
    }

    @Internal
    public void setFAutoVersions(boolean z) {
        this.field_38_docinfo6 = (short) fAutoVersions.setBoolean(this.field_38_docinfo6, z);
    }

    @Internal
    public boolean isFAutoVersions() {
        return fAutoVersions.isSet(this.field_38_docinfo6);
    }

    @Internal
    public void setFVirusPrompted(boolean z) {
        this.field_43_virusinfo = fVirusPrompted.setBoolean(this.field_43_virusinfo, z);
    }

    @Internal
    public boolean isFVirusPrompted() {
        return fVirusPrompted.isSet(this.field_43_virusinfo);
    }

    @Internal
    public void setFVirusLoadSafe(boolean z) {
        this.field_43_virusinfo = fVirusLoadSafe.setBoolean(this.field_43_virusinfo, z);
    }

    @Internal
    public boolean isFVirusLoadSafe() {
        return fVirusLoadSafe.isSet(this.field_43_virusinfo);
    }

    @Internal
    public void setKeyVirusSession30(int i) {
        this.field_43_virusinfo = KeyVirusSession30.setValue(this.field_43_virusinfo, i);
    }

    @Internal
    public int getKeyVirusSession30() {
        return KeyVirusSession30.getValue(this.field_43_virusinfo);
    }
}
