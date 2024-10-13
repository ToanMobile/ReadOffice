package com.app.office.fc.hwpf.usermodel;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.hwpf.model.Colorref;
import com.app.office.fc.hwpf.model.types.CHPAbstractType;

public final class CharacterProperties extends CHPAbstractType implements Cloneable {
    public static final short SPRM_BRC = 26725;
    public static final short SPRM_CCV = 26736;
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

    public CharacterProperties() {
        setFUsePgsuSettings(true);
        setXstDispFldRMark(new byte[36]);
    }

    public boolean isMarkedDeleted() {
        return isFRMarkDel();
    }

    public void markDeleted(boolean z) {
        super.setFRMarkDel(z);
    }

    public boolean isBold() {
        return isFBold();
    }

    public void setBold(boolean z) {
        super.setFBold(z);
    }

    public boolean isItalic() {
        return isFItalic();
    }

    public void setItalic(boolean z) {
        super.setFItalic(z);
    }

    public boolean isOutlined() {
        return isFOutline();
    }

    public void setOutline(boolean z) {
        super.setFOutline(z);
    }

    public boolean isFldVanished() {
        return isFFldVanish();
    }

    public void setFldVanish(boolean z) {
        super.setFFldVanish(z);
    }

    public boolean isSmallCaps() {
        return isFSmallCaps();
    }

    public void setSmallCaps(boolean z) {
        super.setFSmallCaps(z);
    }

    public boolean isCapitalized() {
        return isFCaps();
    }

    public void setCapitalized(boolean z) {
        super.setFCaps(z);
    }

    public boolean isVanished() {
        return isFVanish();
    }

    public void setVanished(boolean z) {
        super.setFVanish(z);
    }

    public boolean isMarkedInserted() {
        return isFRMark();
    }

    public void markInserted(boolean z) {
        super.setFRMark(z);
    }

    public boolean isStrikeThrough() {
        return isFStrike();
    }

    public void strikeThrough(boolean z) {
        super.setFStrike(z);
    }

    public boolean isShadowed() {
        return isFShadow();
    }

    public void setShadow(boolean z) {
        super.setFShadow(z);
    }

    public boolean isEmbossed() {
        return isFEmboss();
    }

    public void setEmbossed(boolean z) {
        super.setFEmboss(z);
    }

    public boolean isImprinted() {
        return isFImprint();
    }

    public void setImprinted(boolean z) {
        super.setFImprint(z);
    }

    public boolean isDoubleStrikeThrough() {
        return isFDStrike();
    }

    public void setDoubleStrikeThrough(boolean z) {
        super.setFDStrike(z);
    }

    public int getFontSize() {
        return getHps();
    }

    public void setFontSize(int i) {
        super.setHps(i);
    }

    public int getCharacterSpacing() {
        return getDxaSpace();
    }

    public void setCharacterSpacing(int i) {
        super.setDxaSpace(i);
    }

    public short getSubSuperScriptIndex() {
        return (short) getIss();
    }

    public void setSubSuperScriptIndex(short s) {
        super.setDxaSpace(s);
    }

    public int getUnderlineCode() {
        return super.getKul();
    }

    public void setUnderlineCode(int i) {
        super.setKul((byte) i);
    }

    public int getColor() {
        return super.getIco();
    }

    public void setColor(int i) {
        super.setIco((byte) i);
    }

    public int getVerticalOffset() {
        return super.getHpsPos();
    }

    public void setVerticalOffset(int i) {
        super.setHpsPos((short) i);
    }

    public int getKerning() {
        return super.getHpsKern();
    }

    public void setKerning(int i) {
        super.setHpsKern(i);
    }

    public boolean isHighlighted() {
        return super.isFHighlight();
    }

    public void setHighlighted(byte b) {
        super.setIcoHighlight(b);
    }

    public int getIco24() {
        if (!getCv().isEmpty()) {
            return getCv().getValue();
        }
        switch (getIco()) {
            case 1:
                return 0;
            case 2:
                return 16711680;
            case 3:
                return 16776960;
            case 4:
                return MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            case 5:
                return 16711935;
            case 6:
                return 255;
            case 7:
                return 65535;
            case 8:
                return ViewCompat.MEASURED_SIZE_MASK;
            case 9:
                return 8388608;
            case 10:
                return 8421376;
            case 11:
                return 32768;
            case 12:
                return 8388736;
            case 13:
                return 128;
            case 14:
                return TIFFConstants.COMPRESSION_IT8LW;
            case 15:
                return 8421504;
            case 16:
                return 12632256;
            default:
                return -1;
        }
    }

    public void setIco24(int i) {
        setCv(new Colorref(i & ViewCompat.MEASURED_SIZE_MASK));
    }

    public Object clone() throws CloneNotSupportedException {
        CharacterProperties characterProperties = (CharacterProperties) super.clone();
        characterProperties.setCv(getCv().clone());
        characterProperties.setDttmRMark((DateAndTime) getDttmRMark().clone());
        characterProperties.setDttmRMarkDel((DateAndTime) getDttmRMarkDel().clone());
        characterProperties.setDttmPropRMark((DateAndTime) getDttmPropRMark().clone());
        characterProperties.setDttmDispFldRMark((DateAndTime) getDttmDispFldRMark().clone());
        characterProperties.setXstDispFldRMark((byte[]) getXstDispFldRMark().clone());
        characterProperties.setShd((ShadingDescriptor) getShd().clone());
        characterProperties.setBrc((BorderCode) getBrc().clone());
        return characterProperties;
    }
}
