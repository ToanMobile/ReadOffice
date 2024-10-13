package com.app.office.fc.hwpf.sprm;

import com.app.office.constant.wp.AttrIDConstant;
import com.app.office.fc.hwpf.usermodel.SectionProperties;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.Arrays;

@Internal
public final class SectionSprmCompressor {
    private static final SectionProperties DEFAULT_SEP = new SectionProperties();

    public static byte[] compressSectionProperty(SectionProperties sectionProperties) {
        ArrayList arrayList = new ArrayList();
        byte cnsPgn = sectionProperties.getCnsPgn();
        SectionProperties sectionProperties2 = DEFAULT_SEP;
        int addSprm = cnsPgn != sectionProperties2.getCnsPgn() ? SprmUtils.addSprm(AttrIDConstant.TABLE_TOP_BORDER_ID, sectionProperties.getCnsPgn(), (byte[]) null, arrayList) + 0 : 0;
        if (sectionProperties.getIHeadingPgn() != sectionProperties2.getIHeadingPgn()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_TOP_BORDER_COLOR_ID, sectionProperties.getIHeadingPgn(), (byte[]) null, arrayList);
        }
        if (!Arrays.equals(sectionProperties.getOlstAnm(), sectionProperties2.getOlstAnm())) {
            addSprm += SprmUtils.addSprm(-11774, 0, sectionProperties.getOlstAnm(), arrayList);
        }
        if (sectionProperties.getFEvenlySpaced() != sectionProperties2.getFEvenlySpaced()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_LEFT_BORDER_COLOR_ID, sectionProperties.getFEvenlySpaced() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getFUnlocked() != sectionProperties2.getFUnlocked()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_RIGHT_BORDER_ID, sectionProperties.getFUnlocked() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getDmBinFirst() != sectionProperties2.getDmBinFirst()) {
            addSprm += SprmUtils.addSprm(20487, sectionProperties.getDmBinFirst(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDmBinOther() != sectionProperties2.getDmBinOther()) {
            addSprm += SprmUtils.addSprm(20488, sectionProperties.getDmBinOther(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getBkc() != sectionProperties2.getBkc()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_CELL_WIDTH_ID, sectionProperties.getBkc(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getFTitlePage() != sectionProperties2.getFTitlePage()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_ROW_HEADER_ID, sectionProperties.getFTitlePage() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getCcolM1() != sectionProperties2.getCcolM1()) {
            addSprm += SprmUtils.addSprm(20491, sectionProperties.getCcolM1(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDxaColumns() != sectionProperties2.getDxaColumns()) {
            addSprm += SprmUtils.addSprm(-28660, sectionProperties.getDxaColumns(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getFAutoPgn() != sectionProperties2.getFAutoPgn()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_CELL_HORIZONTAL_MERGED_ID, sectionProperties.getFAutoPgn() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getNfcPgn() != sectionProperties2.getNfcPgn()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_CELL_VER_FIRST_MERGED_ID, sectionProperties.getNfcPgn(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDyaPgn() != sectionProperties2.getDyaPgn()) {
            addSprm += SprmUtils.addSprm(-20465, sectionProperties.getDyaPgn(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDxaPgn() != sectionProperties2.getDxaPgn()) {
            addSprm += SprmUtils.addSprm(-20464, sectionProperties.getDxaPgn(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getFPgnRestart() != sectionProperties2.getFPgnRestart()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_TOP_MARGIN_ID, sectionProperties.getFPgnRestart() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getFEndNote() != sectionProperties2.getFEndNote()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_BOTTOM_MARGIN_ID, sectionProperties.getFEndNote() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getLnc() != sectionProperties2.getLnc()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_LEFT_MARGIN_ID, sectionProperties.getLnc(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getGrpfIhdt() != sectionProperties2.getGrpfIhdt()) {
            addSprm += SprmUtils.addSprm(AttrIDConstant.TABLE_RIGHT_MARGIN_ID, sectionProperties.getGrpfIhdt(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getNLnnMod() != sectionProperties2.getNLnnMod()) {
            addSprm += SprmUtils.addSprm(20501, sectionProperties.getNLnnMod(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDxaLnn() != sectionProperties2.getDxaLnn()) {
            addSprm += SprmUtils.addSprm(-28650, sectionProperties.getDxaLnn(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDyaHdrTop() != sectionProperties2.getDyaHdrTop()) {
            addSprm += SprmUtils.addSprm(-20457, sectionProperties.getDyaHdrTop(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDyaHdrBottom() != sectionProperties2.getDyaHdrBottom()) {
            addSprm += SprmUtils.addSprm(-20456, sectionProperties.getDyaHdrBottom(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getFLBetween() != sectionProperties2.getFLBetween()) {
            addSprm += SprmUtils.addSprm(12313, sectionProperties.getFLBetween() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getVjc() != sectionProperties2.getVjc()) {
            addSprm += SprmUtils.addSprm(12314, sectionProperties.getVjc(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getLnnMin() != sectionProperties2.getLnnMin()) {
            addSprm += SprmUtils.addSprm(20507, sectionProperties.getLnnMin(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getPgnStart() != sectionProperties2.getPgnStart()) {
            addSprm += SprmUtils.addSprm(20508, sectionProperties.getPgnStart(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDmOrientPage() != sectionProperties2.getDmOrientPage()) {
            addSprm += SprmUtils.addSprm(12317, sectionProperties.getDmOrientPage() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (sectionProperties.getXaPage() != sectionProperties2.getXaPage()) {
            addSprm += SprmUtils.addSprm(-20449, sectionProperties.getXaPage(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getYaPage() != sectionProperties2.getYaPage()) {
            addSprm += SprmUtils.addSprm(-20448, sectionProperties.getYaPage(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDxaLeft() != sectionProperties2.getDxaLeft()) {
            addSprm += SprmUtils.addSprm(-20447, sectionProperties.getDxaLeft(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDxaRight() != sectionProperties2.getDxaRight()) {
            addSprm += SprmUtils.addSprm(-20446, sectionProperties.getDxaRight(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDyaTop() != sectionProperties2.getDyaTop()) {
            addSprm += SprmUtils.addSprm(-28637, sectionProperties.getDyaTop(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDyaBottom() != sectionProperties2.getDyaBottom()) {
            addSprm += SprmUtils.addSprm(-28636, sectionProperties.getDyaBottom(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDzaGutter() != sectionProperties2.getDzaGutter()) {
            addSprm += SprmUtils.addSprm(-20443, sectionProperties.getDzaGutter(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDmPaperReq() != sectionProperties2.getDmPaperReq()) {
            addSprm += SprmUtils.addSprm(20518, sectionProperties.getDmPaperReq(), (byte[]) null, arrayList);
        }
        if (!(sectionProperties.getFPropMark() == sectionProperties2.getFPropMark() && sectionProperties.getIbstPropRMark() == sectionProperties2.getIbstPropRMark() && sectionProperties.getDttmPropRMark().equals(sectionProperties2.getDttmPropRMark()))) {
            byte[] bArr = new byte[7];
            bArr[0] = sectionProperties.getFPropMark() ? (byte) 1 : 0;
            LittleEndian.putShort(bArr, (short) sectionProperties.getIbstPropRMark());
            sectionProperties.getDttmPropRMark().serialize(bArr, 3);
            addSprm += SprmUtils.addSprm(-11737, -1, bArr, arrayList);
        }
        if (!sectionProperties.getBrcTop().equals(sectionProperties2.getBrcTop())) {
            addSprm += SprmUtils.addSprm(28715, sectionProperties.getBrcTop().toInt(), (byte[]) null, arrayList);
        }
        if (!sectionProperties.getBrcLeft().equals(sectionProperties2.getBrcLeft())) {
            addSprm += SprmUtils.addSprm(28716, sectionProperties.getBrcLeft().toInt(), (byte[]) null, arrayList);
        }
        if (!sectionProperties.getBrcBottom().equals(sectionProperties2.getBrcBottom())) {
            addSprm += SprmUtils.addSprm(28717, sectionProperties.getBrcBottom().toInt(), (byte[]) null, arrayList);
        }
        if (!sectionProperties.getBrcRight().equals(sectionProperties2.getBrcRight())) {
            addSprm += SprmUtils.addSprm(28718, sectionProperties.getBrcRight().toInt(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getPgbProp() != sectionProperties2.getPgbProp()) {
            addSprm += SprmUtils.addSprm(21039, sectionProperties.getPgbProp(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDxtCharSpace() != sectionProperties2.getDxtCharSpace()) {
            addSprm += SprmUtils.addSprm(28720, sectionProperties.getDxtCharSpace(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getDyaLinePitch() != sectionProperties2.getDyaLinePitch()) {
            addSprm += SprmUtils.addSprm(-28623, sectionProperties.getDyaLinePitch(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getClm() != sectionProperties2.getClm()) {
            addSprm += SprmUtils.addSprm(20530, sectionProperties.getClm(), (byte[]) null, arrayList);
        }
        if (sectionProperties.getWTextFlow() != sectionProperties2.getWTextFlow()) {
            addSprm += SprmUtils.addSprm(20531, sectionProperties.getWTextFlow(), (byte[]) null, arrayList);
        }
        return SprmUtils.getGrpprl(arrayList, addSprm);
    }
}
