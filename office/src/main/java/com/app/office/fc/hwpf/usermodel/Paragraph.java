package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.PAPX;
import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.hwpf.sprm.TableSprmCompressor;

public class Paragraph extends Range implements Cloneable {
    public static final short SPRM_ANLD = -14786;
    public static final short SPRM_AUTOSPACEDE = 9271;
    public static final short SPRM_AUTOSPACEDN = 9272;
    public static final short SPRM_BRCBAR = 26153;
    public static final short SPRM_BRCBOTTOM = 25638;
    public static final short SPRM_BRCL = 9224;
    public static final short SPRM_BRCLEFT = 25637;
    public static final short SPRM_BRCP = 9225;
    public static final short SPRM_BRCRIGHT = 25639;
    public static final short SPRM_BRCTOP = 25636;
    public static final short SPRM_CHGTABS = -14827;
    public static final short SPRM_CHGTABSPAPX = -14835;
    public static final short SPRM_CRLF = 9284;
    public static final short SPRM_DCS = 17452;
    public static final short SPRM_DXAABS = -31720;
    public static final short SPRM_DXAFROMTEXT = -31697;
    public static final short SPRM_DXALEFT = -31729;
    public static final short SPRM_DXALEFT1 = -31727;
    public static final short SPRM_DXARIGHT = -31730;
    public static final short SPRM_DXAWIDTH = -31718;
    public static final short SPRM_DYAABS = -31719;
    public static final short SPRM_DYAAFTER = -23532;
    public static final short SPRM_DYABEFORE = -23533;
    public static final short SPRM_DYAFROMTEXT = -31698;
    public static final short SPRM_DYALINE = 25618;
    public static final short SPRM_FADJUSTRIGHT = 9288;
    public static final short SPRM_FBIDI = 9281;
    public static final short SPRM_FINTABLE = 9238;
    public static final short SPRM_FKEEP = 9221;
    public static final short SPRM_FKEEPFOLLOW = 9222;
    public static final short SPRM_FKINSOKU = 9267;
    public static final short SPRM_FLOCKED = 9264;
    public static final short SPRM_FNOAUTOHYPH = 9258;
    public static final short SPRM_FNOLINENUMB = 9228;
    public static final short SPRM_FNUMRMLNS = 9283;
    public static final short SPRM_FOVERFLOWPUNCT = 9269;
    public static final short SPRM_FPAGEBREAKBEFORE = 9223;
    public static final short SPRM_FRAMETEXTFLOW = 17466;
    public static final short SPRM_FSIDEBYSIDE = 9220;
    public static final short SPRM_FTOPLINEPUNCT = 9270;
    public static final short SPRM_FTTP = 9239;
    public static final short SPRM_FWIDOWCONTROL = 9265;
    public static final short SPRM_FWORDWRAP = 9268;
    public static final short SPRM_ILFO = 17931;
    public static final short SPRM_ILVL = 9738;
    public static final short SPRM_JC = 9219;
    public static final short SPRM_NUMRM = -14779;
    public static final short SPRM_OUTLVL = 9792;
    public static final short SPRM_PC = 9755;
    public static final short SPRM_PROPRMARK = -14785;
    public static final short SPRM_RULER = -14798;
    public static final short SPRM_SHD = 17453;
    public static final short SPRM_USEPGSUSETTINGS = 9287;
    public static final short SPRM_WALIGNFONT = 17465;
    public static final short SPRM_WHEIGHTABS = 17451;
    public static final short SPRM_WR = 9251;
    protected short _istd;
    protected SprmBuffer _papx;
    protected ParagraphProperties _props;

    public int type() {
        return 0;
    }

    protected Paragraph(int i, int i2, Table table) {
        super(i, i2, (Range) table);
        initAll();
        PAPX papx = (PAPX) this._paragraphs.get(this._parEnd - 1);
        this._props = papx.getParagraphProperties(this._doc.getStyleSheet());
        this._papx = papx.getSprmBuf();
        this._istd = papx.getIstd();
    }

    protected Paragraph(PAPX papx, Range range) {
        super(Math.max(range._start, papx.getStart()), Math.min(range._end, papx.getEnd()), range);
        this._props = papx.getParagraphProperties(this._doc.getStyleSheet());
        this._papx = papx.getSprmBuf();
        this._istd = papx.getIstd();
    }

    protected Paragraph(PAPX papx, Range range, int i) {
        super(Math.max(range._start, i), Math.min(range._end, papx.getEnd()), range);
        this._props = papx.getParagraphProperties(this._doc.getStyleSheet());
        this._papx = papx.getSprmBuf();
        this._istd = papx.getIstd();
    }

    public short getStyleIndex() {
        return this._istd;
    }

    public boolean isInTable() {
        return this._props.getFInTable();
    }

    public boolean isTableRowEnd() {
        return this._props.getFTtp() || this._props.getFTtpEmbedded();
    }

    public int getTableLevel() {
        return this._props.getItap();
    }

    public boolean isEmbeddedCellMark() {
        return this._props.getFInnerTableCell();
    }

    public int getJustification() {
        return this._props.getJc();
    }

    public void setJustification(byte b) {
        this._props.setJc(b);
        this._papx.updateSprm((short) SPRM_JC, b);
    }

    public boolean keepOnPage() {
        return this._props.getFKeep();
    }

    public void setKeepOnPage(boolean z) {
        this._props.setFKeep(z);
        this._papx.updateSprm((short) SPRM_FKEEP, z);
    }

    public boolean keepWithNext() {
        return this._props.getFKeepFollow();
    }

    public void setKeepWithNext(boolean z) {
        this._props.setFKeepFollow(z);
        this._papx.updateSprm((short) SPRM_FKEEPFOLLOW, z);
    }

    public boolean pageBreakBefore() {
        return this._props.getFPageBreakBefore();
    }

    public void setPageBreakBefore(boolean z) {
        this._props.setFPageBreakBefore(z);
        this._papx.updateSprm((short) SPRM_FPAGEBREAKBEFORE, z);
    }

    public boolean isLineNotNumbered() {
        return this._props.getFNoLnn();
    }

    public void setLineNotNumbered(boolean z) {
        this._props.setFNoLnn(z);
        this._papx.updateSprm((short) SPRM_FNOLINENUMB, z);
    }

    public boolean isSideBySide() {
        return this._props.getFSideBySide();
    }

    public void setSideBySide(boolean z) {
        this._props.setFSideBySide(z);
        this._papx.updateSprm((short) SPRM_FSIDEBYSIDE, z);
    }

    public boolean isAutoHyphenated() {
        return !this._props.getFNoAutoHyph();
    }

    public void setAutoHyphenated(boolean z) {
        this._props.setFNoAutoHyph(!z);
        this._papx.updateSprm((short) SPRM_FNOAUTOHYPH, !z);
    }

    public boolean isWidowControlled() {
        return this._props.getFWidowControl();
    }

    public void setWidowControl(boolean z) {
        this._props.setFWidowControl(z);
        this._papx.updateSprm((short) SPRM_FWIDOWCONTROL, z);
    }

    public int getIndentFromRight() {
        return this._props.getDxaRight();
    }

    public void setIndentFromRight(int i) {
        this._props.setDxaRight(i);
        this._papx.updateSprm((short) SPRM_DXARIGHT, (short) i);
    }

    public int getIndentFromLeft() {
        return this._props.getDxaLeft();
    }

    public void setIndentFromLeft(int i) {
        this._props.setDxaLeft(i);
        this._papx.updateSprm((short) SPRM_DXALEFT, (short) i);
    }

    public int getFirstLineIndent() {
        return this._props.getDxaLeft1();
    }

    public void setFirstLineIndent(int i) {
        this._props.setDxaLeft1(i);
        this._papx.updateSprm((short) SPRM_DXALEFT1, (short) i);
    }

    public LineSpacingDescriptor getLineSpacing() {
        return this._props.getLspd();
    }

    public void setLineSpacing(LineSpacingDescriptor lineSpacingDescriptor) {
        this._props.setLspd(lineSpacingDescriptor);
        this._papx.updateSprm((short) SPRM_DYALINE, lineSpacingDescriptor.toInt());
    }

    public int getSpacingBefore() {
        return this._props.getDyaBefore();
    }

    public void setSpacingBefore(int i) {
        this._props.setDyaBefore(i);
        this._papx.updateSprm((short) SPRM_DYABEFORE, (short) i);
    }

    public int getSpacingAfter() {
        return this._props.getDyaAfter();
    }

    public void setSpacingAfter(int i) {
        this._props.setDyaAfter(i);
        this._papx.updateSprm((short) SPRM_DYAAFTER, (short) i);
    }

    public boolean isKinsoku() {
        return this._props.getFKinsoku();
    }

    public void setKinsoku(boolean z) {
        this._props.setFKinsoku(z);
        this._papx.updateSprm((short) SPRM_FKINSOKU, z);
    }

    public boolean isWordWrapped() {
        return this._props.getFWordWrap();
    }

    public void setWordWrapped(boolean z) {
        this._props.setFWordWrap(z);
        this._papx.updateSprm((short) SPRM_FWORDWRAP, z);
    }

    public int getFontAlignment() {
        return this._props.getWAlignFont();
    }

    public void setFontAlignment(int i) {
        this._props.setWAlignFont(i);
        this._papx.updateSprm((short) SPRM_WALIGNFONT, (short) i);
    }

    public boolean isVertical() {
        return this._props.isFVertical();
    }

    public void setVertical(boolean z) {
        this._props.setFVertical(z);
        this._papx.updateSprm((short) SPRM_FRAMETEXTFLOW, getFrameTextFlow());
    }

    public boolean isBackward() {
        return this._props.isFBackward();
    }

    public void setBackward(boolean z) {
        this._props.setFBackward(z);
        this._papx.updateSprm((short) SPRM_FRAMETEXTFLOW, getFrameTextFlow());
    }

    public BorderCode getTopBorder() {
        return this._props.getBrcTop();
    }

    public void setTopBorder(BorderCode borderCode) {
        this._props.setBrcTop(borderCode);
        this._papx.updateSprm((short) SPRM_BRCTOP, borderCode.toInt());
    }

    public BorderCode getLeftBorder() {
        return this._props.getBrcLeft();
    }

    public void setLeftBorder(BorderCode borderCode) {
        this._props.setBrcLeft(borderCode);
        this._papx.updateSprm((short) SPRM_BRCLEFT, borderCode.toInt());
    }

    public BorderCode getBottomBorder() {
        return this._props.getBrcBottom();
    }

    public void setBottomBorder(BorderCode borderCode) {
        this._props.setBrcBottom(borderCode);
        this._papx.updateSprm((short) SPRM_BRCBOTTOM, borderCode.toInt());
    }

    public BorderCode getRightBorder() {
        return this._props.getBrcRight();
    }

    public void setRightBorder(BorderCode borderCode) {
        this._props.setBrcRight(borderCode);
        this._papx.updateSprm((short) SPRM_BRCRIGHT, borderCode.toInt());
    }

    public BorderCode getBarBorder() {
        return this._props.getBrcBar();
    }

    public void setBarBorder(BorderCode borderCode) {
        this._props.setBrcBar(borderCode);
        this._papx.updateSprm((short) SPRM_BRCBAR, borderCode.toInt());
    }

    public ShadingDescriptor getShading() {
        return this._props.getShd();
    }

    public void setShading(ShadingDescriptor shadingDescriptor) {
        this._props.setShd(shadingDescriptor);
        this._papx.updateSprm((short) SPRM_SHD, shadingDescriptor.toShort());
    }

    public DropCapSpecifier getDropCap() {
        return this._props.getDcs();
    }

    public void setDropCap(DropCapSpecifier dropCapSpecifier) {
        this._props.setDcs(dropCapSpecifier);
        this._papx.updateSprm((short) SPRM_DCS, dropCapSpecifier.toShort());
    }

    public int getIlfo() {
        return this._props.getIlfo();
    }

    public int getIlvl() {
        return this._props.getIlvl();
    }

    public int getLvl() {
        return this._props.getLvl();
    }

    /* access modifiers changed from: package-private */
    public void setTableRowEnd(TableProperties tableProperties) {
        setTableRowEnd(true);
        this._papx.append(TableSprmCompressor.compressTableProperty(tableProperties));
    }

    private void setTableRowEnd(boolean z) {
        this._props.setFTtp(z);
        this._papx.updateSprm((short) SPRM_FTTP, z);
    }

    public short getTabClearPosition() {
        return this._props.getTabClearPosition();
    }

    public ParagraphProperties cloneProperties() {
        try {
            return (ParagraphProperties) this._props.clone();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        Paragraph paragraph = (Paragraph) super.clone();
        paragraph._props = (ParagraphProperties) this._props.clone();
        paragraph._papx = new SprmBuffer(0);
        return paragraph;
    }

    private short getFrameTextFlow() {
        short s = this._props.isFVertical() ? (short) 1 : 0;
        if (this._props.isFBackward()) {
            s = (short) (s | 2);
        }
        return this._props.isFRotateFont() ? (short) (s | 4) : s;
    }

    public String toString() {
        return "Paragraph [" + getStartOffset() + "; " + getEndOffset() + ")";
    }
}
