package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.CHPX;
import com.app.office.fc.hwpf.model.Ffn;
import com.app.office.fc.hwpf.model.StyleSheet;
import com.app.office.fc.hwpf.sprm.SprmBuffer;

public final class CharacterRun extends Range implements Cloneable {
    public static final short SPRM_BRC = 26725;
    public static final short SPRM_CHARSCALE = 18514;
    public static final short SPRM_CPG = 18539;
    public static final short SPRM_DISPFLDRMARK = -13726;
    public static final short SPRM_DTTMRMARK = 26629;
    public static final short SPRM_DTTMRMARKDEL = 26724;
    public static final short SPRM_DXASPACE = -30656;
    public static final short SPRM_FBOLD = 2101;
    public static final short SPRM_FCAPS = 2107;
    public static final short SPRM_FDATA = 2054;
    public static final short SPRM_FDSTRIKE = 10835;
    public static final short SPRM_FELID = 18542;
    public static final short SPRM_FEMBOSS = 2136;
    public static final short SPRM_FFLDVANISH = 2050;
    public static final short SPRM_FIMPRINT = 2132;
    public static final short SPRM_FITALIC = 2102;
    public static final short SPRM_FOBJ = 2134;
    public static final short SPRM_FOLE2 = 2058;
    public static final short SPRM_FOUTLINE = 2104;
    public static final short SPRM_FRMARK = 2049;
    public static final short SPRM_FRMARKDEL = 2048;
    public static final short SPRM_FSHADOW = 2105;
    public static final short SPRM_FSMALLCAPS = 2106;
    public static final short SPRM_FSPEC = 2133;
    public static final short SPRM_FSTRIKE = 2103;
    public static final short SPRM_FVANISH = 2108;
    public static final short SPRM_HIGHLIGHT = 10764;
    public static final short SPRM_HPS = 19011;
    public static final short SPRM_HPSKERN = 18507;
    public static final short SPRM_HPSPOS = 18501;
    public static final short SPRM_IBSTRMARK = 18436;
    public static final short SPRM_IBSTRMARKDEL = 18531;
    public static final short SPRM_ICO = 10818;
    public static final short SPRM_IDCTHINT = 10351;
    public static final short SPRM_IDSIRMARKDEL = 18535;
    public static final short SPRM_ISS = 10824;
    public static final short SPRM_ISTD = 18992;
    public static final short SPRM_KUL = 10814;
    public static final short SPRM_LID = 19009;
    public static final short SPRM_NONFELID = 18541;
    public static final short SPRM_OBJLOCATION = 26638;
    public static final short SPRM_PICLOCATION = 27139;
    public static final short SPRM_PROPRMARK = -13737;
    public static final short SPRM_RGFTCASCII = 19023;
    public static final short SPRM_RGFTCFAREAST = 19024;
    public static final short SPRM_RGFTCNOTFAREAST = 19025;
    public static final short SPRM_SFXTEXT = 10329;
    public static final short SPRM_SHD = 18534;
    public static final short SPRM_SYMBOL = 27145;
    public static final short SPRM_YSRI = 18510;
    SprmBuffer _chpx;
    CharacterProperties _props;
    private String text;

    public int type() {
        return 1;
    }

    CharacterRun(CHPX chpx, StyleSheet styleSheet, short s, Range range) {
        super(Math.max(range._start, chpx.getStart()), Math.min(range._end, chpx.getEnd()), range);
        this._props = chpx.getCharacterProperties(styleSheet, s);
        this._chpx = chpx.getSprmBuf();
    }

    public boolean isMarkedDeleted() {
        return this._props.isFRMarkDel();
    }

    public void markDeleted(boolean z) {
        this._props.setFRMarkDel(z);
        this._chpx.updateSprm(2048, z ? (byte) 1 : 0);
    }

    public boolean isBold() {
        return this._props.isFBold();
    }

    public void setBold(boolean z) {
        this._props.setFBold(z);
        this._chpx.updateSprm(2101, z ? (byte) 1 : 0);
    }

    public boolean isItalic() {
        return this._props.isFItalic();
    }

    public void setItalic(boolean z) {
        this._props.setFItalic(z);
        this._chpx.updateSprm(2102, z ? (byte) 1 : 0);
    }

    public boolean isOutlined() {
        return this._props.isFOutline();
    }

    public void setOutline(boolean z) {
        this._props.setFOutline(z);
        this._chpx.updateSprm(2104, z ? (byte) 1 : 0);
    }

    public boolean isFldVanished() {
        return this._props.isFFldVanish();
    }

    public void setFldVanish(boolean z) {
        this._props.setFFldVanish(z);
        this._chpx.updateSprm(2050, z ? (byte) 1 : 0);
    }

    public boolean isSmallCaps() {
        return this._props.isFSmallCaps();
    }

    public void setSmallCaps(boolean z) {
        this._props.setFSmallCaps(z);
        this._chpx.updateSprm(2106, z ? (byte) 1 : 0);
    }

    public boolean isCapitalized() {
        return this._props.isFCaps();
    }

    public void setCapitalized(boolean z) {
        this._props.setFCaps(z);
        this._chpx.updateSprm(2107, z ? (byte) 1 : 0);
    }

    public boolean isVanished() {
        return this._props.isFVanish();
    }

    public void setVanished(boolean z) {
        this._props.setFVanish(z);
        this._chpx.updateSprm(2108, z ? (byte) 1 : 0);
    }

    public boolean isMarkedInserted() {
        return this._props.isFRMark();
    }

    public void markInserted(boolean z) {
        this._props.setFRMark(z);
        this._chpx.updateSprm(2049, z ? (byte) 1 : 0);
    }

    public boolean isStrikeThrough() {
        return this._props.isFStrike();
    }

    public void strikeThrough(boolean z) {
        this._props.setFStrike(z);
        this._chpx.updateSprm(2103, z ? (byte) 1 : 0);
    }

    public boolean isShadowed() {
        return this._props.isFShadow();
    }

    public void setShadow(boolean z) {
        this._props.setFShadow(z);
        this._chpx.updateSprm(2105, z ? (byte) 1 : 0);
    }

    public boolean isEmbossed() {
        return this._props.isFEmboss();
    }

    public void setEmbossed(boolean z) {
        this._props.setFEmboss(z);
        this._chpx.updateSprm(2136, z ? (byte) 1 : 0);
    }

    public boolean isImprinted() {
        return this._props.isFImprint();
    }

    public void setImprinted(boolean z) {
        this._props.setFImprint(z);
        this._chpx.updateSprm(2132, z ? (byte) 1 : 0);
    }

    public boolean isDoubleStrikeThrough() {
        return this._props.isFDStrike();
    }

    public void setDoubleStrikethrough(boolean z) {
        this._props.setFDStrike(z);
        this._chpx.updateSprm(10835, z ? (byte) 1 : 0);
    }

    public void setFtcAscii(int i) {
        this._props.setFtcAscii(i);
        this._chpx.updateSprm(19023, (short) i);
    }

    public void setFtcFE(int i) {
        this._props.setFtcFE(i);
        this._chpx.updateSprm(19024, (short) i);
    }

    public void setFtcOther(int i) {
        this._props.setFtcOther(i);
        this._chpx.updateSprm(19025, (short) i);
    }

    public int getFontSize() {
        return this._props.getHps();
    }

    public void setFontSize(int i) {
        this._props.setHps(i);
        this._chpx.updateSprm(19011, (short) i);
    }

    public int getCharacterSpacing() {
        return this._props.getDxaSpace();
    }

    public void setCharacterSpacing(int i) {
        this._props.setDxaSpace(i);
        this._chpx.updateSprm(-30656, i);
    }

    public short getSubSuperScriptIndex() {
        return (short) this._props.getIss();
    }

    public void setSubSuperScriptIndex(short s) {
        this._props.setDxaSpace(s);
        this._chpx.updateSprm(-30656, s);
    }

    public int getUnderlineCode() {
        return this._props.getKul();
    }

    public void setUnderlineCode(int i) {
        byte b = (byte) i;
        this._props.setKul(b);
        this._chpx.updateSprm(10814, b);
    }

    public int getColor() {
        return this._props.getIco();
    }

    public void setColor(int i) {
        byte b = (byte) i;
        this._props.setIco(b);
        this._chpx.updateSprm(10818, b);
    }

    public int getVerticalOffset() {
        return this._props.getHpsPos();
    }

    public void setVerticalOffset(int i) {
        this._props.setHpsPos((short) i);
        this._chpx.updateSprm(18501, (byte) i);
    }

    public int getKerning() {
        return this._props.getHpsKern();
    }

    public void setKerning(int i) {
        this._props.setHpsKern(i);
        this._chpx.updateSprm(18507, (short) i);
    }

    public boolean isHighlighted() {
        return this._props.isFHighlight();
    }

    public byte getHighlightedColor() {
        return this._props.getIcoHighlight();
    }

    public void setHighlighted(byte b) {
        this._props.setFHighlight(true);
        this._props.setIcoHighlight(b);
        this._chpx.updateSprm(10764, b);
    }

    public String getFontName() {
        if (this._doc.getFontTable() == null) {
            return null;
        }
        return this._doc.getFontTable().getMainFont(this._props.getFtcAscii());
    }

    public boolean isSpecialCharacter() {
        return this._props.isFSpec();
    }

    public void setSpecialCharacter(boolean z) {
        this._props.setFSpec(z);
        this._chpx.updateSprm(2133, z ? (byte) 1 : 0);
    }

    public boolean isObj() {
        return this._props.isFObj();
    }

    public void setObj(boolean z) {
        this._props.setFObj(z);
        this._chpx.updateSprm(2134, z ? (byte) 1 : 0);
    }

    public int getPicOffset() {
        return this._props.getFcPic();
    }

    public void setPicOffset(int i) {
        this._props.setFcPic(i);
        this._chpx.updateSprm(27139, i);
    }

    public boolean isData() {
        return this._props.isFData();
    }

    public void setData(boolean z) {
        this._props.setFData(z);
        this._chpx.updateSprm(2134, z ? (byte) 1 : 0);
    }

    public boolean isOle2() {
        return this._props.isFOle2();
    }

    public void setOle2(boolean z) {
        this._props.setFOle2(z);
        this._chpx.updateSprm(2134, z ? (byte) 1 : 0);
    }

    public int getObjOffset() {
        return this._props.getFcObj();
    }

    public void setObjOffset(int i) {
        this._props.setFcObj(i);
        this._chpx.updateSprm(26638, i);
    }

    public int getIco24() {
        return this._props.getIco24();
    }

    public void setIco24(int i) {
        this._props.setIco24(i);
    }

    public int getUnderlineColor() {
        return this._props.getUnderlineColor();
    }

    @Deprecated
    public CharacterProperties cloneProperties() {
        try {
            return (CharacterProperties) this._props.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        CharacterRun characterRun = (CharacterRun) super.clone();
        characterRun._props.setDttmRMark((DateAndTime) this._props.getDttmRMark().clone());
        characterRun._props.setDttmRMarkDel((DateAndTime) this._props.getDttmRMarkDel().clone());
        characterRun._props.setDttmPropRMark((DateAndTime) this._props.getDttmPropRMark().clone());
        characterRun._props.setDttmDispFldRMark((DateAndTime) this._props.getDttmDispFldRMark().clone());
        characterRun._props.setXstDispFldRMark((byte[]) this._props.getXstDispFldRMark().clone());
        characterRun._props.setShd((ShadingDescriptor) this._props.getShd().clone());
        return characterRun;
    }

    public boolean isSymbol() {
        return isSpecialCharacter() && text().equals("(");
    }

    public char getSymbolCharacter() {
        if (isSymbol()) {
            return (char) this._props.getXchSym();
        }
        throw new IllegalStateException("Not a symbol CharacterRun");
    }

    public Ffn getSymbolFont() {
        if (isSymbol()) {
            Ffn[] fontNames = this._doc.getFontTable().getFontNames();
            if (fontNames.length <= this._props.getFtcSym()) {
                return null;
            }
            return fontNames[this._props.getFtcSym()];
        }
        throw new IllegalStateException("Not a symbol CharacterRun");
    }

    public BorderCode getBorder() {
        return this._props.getBrc();
    }

    public int getLanguageCode() {
        return this._props.getLidDefault();
    }

    public String text() {
        if (this.text == null) {
            this.text = super.text();
        }
        return this.text;
    }

    public String toString() {
        String text2 = text();
        return "CharacterRun of " + text2.length() + " characters - " + text2;
    }
}
