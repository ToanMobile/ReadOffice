package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.hwpf.usermodel.Paragraph;
import com.app.office.fc.hwpf.usermodel.ParagraphProperties;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.jvm.internal.ShortCompanionObject;

@Internal
public final class ParagraphSprmCompressor {
    public static byte[] compressParagraphProperty(ParagraphProperties paragraphProperties, ParagraphProperties paragraphProperties2) {
        ArrayList arrayList = new ArrayList();
        int addSprm = paragraphProperties.getIstd() != paragraphProperties2.getIstd() ? SprmUtils.addSprm(17920, paragraphProperties.getIstd(), (byte[]) null, arrayList) + 0 : 0;
        if (paragraphProperties.getJc() != paragraphProperties2.getJc()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_JC, paragraphProperties.getJc(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getFSideBySide() != paragraphProperties2.getFSideBySide()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FSIDEBYSIDE, paragraphProperties.getFSideBySide(), arrayList);
        }
        if (paragraphProperties.getFKeep() != paragraphProperties2.getFKeep()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FKEEP, paragraphProperties.getFKeep(), arrayList);
        }
        if (paragraphProperties.getFKeepFollow() != paragraphProperties2.getFKeepFollow()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FKEEPFOLLOW, paragraphProperties.getFKeepFollow(), arrayList);
        }
        if (paragraphProperties.getFPageBreakBefore() != paragraphProperties2.getFPageBreakBefore()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FPAGEBREAKBEFORE, paragraphProperties.getFPageBreakBefore(), arrayList);
        }
        if (paragraphProperties.getBrcl() != paragraphProperties2.getBrcl()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_BRCL, paragraphProperties.getBrcl(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getBrcp() != paragraphProperties2.getBrcp()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_BRCP, paragraphProperties.getBrcp(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getIlvl() != paragraphProperties2.getIlvl()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_ILVL, paragraphProperties.getIlvl(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getIlfo() != paragraphProperties2.getIlfo()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_ILFO, paragraphProperties.getIlfo(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getFNoLnn() != paragraphProperties2.getFNoLnn()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FNOLINENUMB, paragraphProperties.getFNoLnn(), arrayList);
        }
        if (paragraphProperties.getItbdMac() == paragraphProperties2.getItbdMac() && Arrays.equals(paragraphProperties.getRgdxaTab(), paragraphProperties2.getRgdxaTab())) {
            Arrays.equals(paragraphProperties.getRgtbd(), paragraphProperties2.getRgtbd());
        }
        if (paragraphProperties.getDxaLeft() != paragraphProperties2.getDxaLeft()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DXALEFT, paragraphProperties.getDxaLeft(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDxaLeft1() != paragraphProperties2.getDxaLeft1()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DXALEFT1, paragraphProperties.getDxaLeft1(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDxaRight() != paragraphProperties2.getDxaRight()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DXARIGHT, paragraphProperties.getDxaRight(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDxcLeft() != paragraphProperties2.getDxcLeft()) {
            addSprm += SprmUtils.addSprm(17494, paragraphProperties.getDxcLeft(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDxcLeft1() != paragraphProperties2.getDxcLeft1()) {
            addSprm += SprmUtils.addSprm(17495, paragraphProperties.getDxcLeft1(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDxcRight() != paragraphProperties2.getDxcRight()) {
            addSprm += SprmUtils.addSprm(17493, paragraphProperties.getDxcRight(), (byte[]) null, arrayList);
        }
        if (!paragraphProperties.getLspd().equals(paragraphProperties2.getLspd())) {
            byte[] bArr = new byte[4];
            paragraphProperties.getLspd().serialize(bArr, 0);
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DYALINE, LittleEndian.getInt(bArr), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDyaBefore() != paragraphProperties2.getDyaBefore()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DYABEFORE, paragraphProperties.getDyaBefore(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDyaAfter() != paragraphProperties2.getDyaAfter()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DYAAFTER, paragraphProperties.getDyaAfter(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getFDyaBeforeAuto() != paragraphProperties2.getFDyaBeforeAuto()) {
            addSprm += SprmUtils.addSprm(9307, paragraphProperties.getFDyaBeforeAuto(), arrayList);
        }
        if (paragraphProperties.getFDyaAfterAuto() != paragraphProperties2.getFDyaAfterAuto()) {
            addSprm += SprmUtils.addSprm(9308, paragraphProperties.getFDyaAfterAuto(), arrayList);
        }
        if (paragraphProperties.getFInTable() != paragraphProperties2.getFInTable()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FINTABLE, paragraphProperties.getFInTable(), arrayList);
        }
        if (paragraphProperties.getFTtp() != paragraphProperties2.getFTtp()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FTTP, paragraphProperties.getFTtp(), arrayList);
        }
        if (paragraphProperties.getDxaAbs() != paragraphProperties2.getDxaAbs()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DXAABS, paragraphProperties.getDxaAbs(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDyaAbs() != paragraphProperties2.getDyaAbs()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DYAABS, paragraphProperties.getDyaAbs(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDxaWidth() != paragraphProperties2.getDxaWidth()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DXAWIDTH, paragraphProperties.getDxaWidth(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getWr() != paragraphProperties2.getWr()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_WR, paragraphProperties.getWr(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getBrcBar().equals(paragraphProperties2.getBrcBar())) {
            addSprm += SprmUtils.addSprm(25640, paragraphProperties.getBrcBar().toInt(), (byte[]) null, arrayList);
        }
        if (!paragraphProperties.getBrcBottom().equals(paragraphProperties2.getBrcBottom())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_BRCBOTTOM, paragraphProperties.getBrcBottom().toInt(), (byte[]) null, arrayList);
        }
        if (!paragraphProperties.getBrcLeft().equals(paragraphProperties2.getBrcLeft())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_BRCLEFT, paragraphProperties.getBrcLeft().toInt(), (byte[]) null, arrayList);
        }
        if (!paragraphProperties.getBrcRight().equals(paragraphProperties2.getBrcRight())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_BRCRIGHT, paragraphProperties.getBrcRight().toInt(), (byte[]) null, arrayList);
        }
        if (!paragraphProperties.getBrcTop().equals(paragraphProperties2.getBrcTop())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_BRCTOP, paragraphProperties.getBrcTop().toInt(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getFNoAutoHyph() != paragraphProperties2.getFNoAutoHyph()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FNOAUTOHYPH, paragraphProperties.getFNoAutoHyph(), arrayList);
        }
        if (!(paragraphProperties.getDyaHeight() == paragraphProperties2.getDyaHeight() && paragraphProperties.getFMinHeight() == paragraphProperties2.getFMinHeight())) {
            short dyaHeight = (short) paragraphProperties.getDyaHeight();
            if (paragraphProperties.getFMinHeight()) {
                dyaHeight = (short) (dyaHeight | ShortCompanionObject.MIN_VALUE);
            }
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_WHEIGHTABS, dyaHeight, (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDcs() != null && !paragraphProperties.getDcs().equals(paragraphProperties2.getDcs())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DCS, paragraphProperties.getDcs().toShort(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getShd() != null && !paragraphProperties.getShd().equals(paragraphProperties2.getShd())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_SHD, paragraphProperties.getShd().toShort(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDyaFromText() != paragraphProperties2.getDyaFromText()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DYAFROMTEXT, paragraphProperties.getDyaFromText(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getDxaFromText() != paragraphProperties2.getDxaFromText()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_DXAFROMTEXT, paragraphProperties.getDxaFromText(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getFLocked() != paragraphProperties2.getFLocked()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FLOCKED, paragraphProperties.getFLocked(), arrayList);
        }
        if (paragraphProperties.getFWidowControl() != paragraphProperties2.getFWidowControl()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FWIDOWCONTROL, paragraphProperties.getFWidowControl(), arrayList);
        }
        if (paragraphProperties.getFKinsoku() != paragraphProperties2.getFKinsoku()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FKINSOKU, paragraphProperties.getDyaBefore(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getFWordWrap() != paragraphProperties2.getFWordWrap()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FWORDWRAP, paragraphProperties.getFWordWrap(), arrayList);
        }
        if (paragraphProperties.getFOverflowPunct() != paragraphProperties2.getFOverflowPunct()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FOVERFLOWPUNCT, paragraphProperties.getFOverflowPunct(), arrayList);
        }
        if (paragraphProperties.getFTopLinePunct() != paragraphProperties2.getFTopLinePunct()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FTOPLINEPUNCT, paragraphProperties.getFTopLinePunct(), arrayList);
        }
        if (paragraphProperties.getFAutoSpaceDE() != paragraphProperties2.getFAutoSpaceDE()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_AUTOSPACEDE, paragraphProperties.getFAutoSpaceDE(), arrayList);
        }
        if (paragraphProperties.getFAutoSpaceDN() != paragraphProperties2.getFAutoSpaceDN()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_AUTOSPACEDN, paragraphProperties.getFAutoSpaceDN(), arrayList);
        }
        if (paragraphProperties.getWAlignFont() != paragraphProperties2.getWAlignFont()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_WALIGNFONT, paragraphProperties.getWAlignFont(), (byte[]) null, arrayList);
        }
        if (!(paragraphProperties.isFBackward() == paragraphProperties2.isFBackward() && paragraphProperties.isFVertical() == paragraphProperties2.isFVertical() && paragraphProperties.isFRotateFont() == paragraphProperties2.isFRotateFont())) {
            int i = paragraphProperties.isFBackward() ? 2 : 0;
            if (paragraphProperties.isFVertical()) {
                i |= 1;
            }
            if (paragraphProperties.isFRotateFont()) {
                i |= 4;
            }
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FRAMETEXTFLOW, i, (byte[]) null, arrayList);
        }
        if (!Arrays.equals(paragraphProperties.getAnld(), paragraphProperties2.getAnld())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_ANLD, 0, paragraphProperties.getAnld(), arrayList);
        }
        if (!(paragraphProperties.getFPropRMark() == paragraphProperties2.getFPropRMark() && paragraphProperties.getIbstPropRMark() == paragraphProperties2.getIbstPropRMark() && paragraphProperties.getDttmPropRMark().equals(paragraphProperties2.getDttmPropRMark()))) {
            byte[] bArr2 = new byte[7];
            bArr2[0] = paragraphProperties.getFPropRMark() ? (byte) 1 : 0;
            LittleEndian.putShort(bArr2, 1, (short) paragraphProperties.getIbstPropRMark());
            paragraphProperties.getDttmPropRMark().serialize(bArr2, 3);
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_PROPRMARK, 0, bArr2, arrayList);
        }
        if (paragraphProperties.getLvl() != paragraphProperties2.getLvl()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_OUTLVL, paragraphProperties.getLvl(), (byte[]) null, arrayList);
        }
        if (paragraphProperties.getFBiDi() != paragraphProperties2.getFBiDi()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FBIDI, paragraphProperties.getFBiDi(), arrayList);
        }
        if (paragraphProperties.getFNumRMIns() != paragraphProperties2.getFNumRMIns()) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_FNUMRMLNS, paragraphProperties.getFNumRMIns(), arrayList);
        }
        if (!Arrays.equals(paragraphProperties.getNumrm(), paragraphProperties2.getNumrm())) {
            addSprm += SprmUtils.addSprm(Paragraph.SPRM_NUMRM, 0, paragraphProperties.getNumrm(), arrayList);
        }
        if (paragraphProperties.getFInnerTableCell() != paragraphProperties2.getFInnerTableCell()) {
            addSprm += SprmUtils.addSprm(9291, paragraphProperties.getFInnerTableCell(), arrayList);
        }
        if (paragraphProperties.getFTtpEmbedded() != paragraphProperties2.getFTtpEmbedded()) {
            addSprm += SprmUtils.addSprm(9292, paragraphProperties.getFTtpEmbedded(), arrayList);
        }
        if (paragraphProperties.getItap() != paragraphProperties2.getItap()) {
            addSprm += SprmUtils.addSprm(26185, paragraphProperties.getItap(), (byte[]) null, arrayList);
        }
        return SprmUtils.getGrpprl(arrayList, addSprm);
    }
}
