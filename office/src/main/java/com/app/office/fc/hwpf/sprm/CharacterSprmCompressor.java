package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.hwpf.usermodel.CharacterProperties;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;

@Internal
public final class CharacterSprmCompressor {
    public static byte[] compressCharacterProperty(CharacterProperties characterProperties, CharacterProperties characterProperties2) {
        ArrayList arrayList = new ArrayList();
        int addSprm = characterProperties.isFRMarkDel() != characterProperties2.isFRMarkDel() ? SprmUtils.addSprm(2048, characterProperties.isFRMarkDel() ? 1 : 0, (byte[]) null, arrayList) + 0 : 0;
        if (characterProperties.isFRMark() != characterProperties2.isFRMark()) {
            addSprm += SprmUtils.addSprm(2049, characterProperties.isFRMark() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFFldVanish() != characterProperties2.isFFldVanish()) {
            addSprm += SprmUtils.addSprm(2050, characterProperties.isFFldVanish() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (!(characterProperties.isFSpec() == characterProperties2.isFSpec() && characterProperties.getFcPic() == characterProperties2.getFcPic())) {
            addSprm += SprmUtils.addSprm(27139, characterProperties.getFcPic(), (byte[]) null, arrayList);
        }
        if (characterProperties.getIbstRMark() != characterProperties2.getIbstRMark()) {
            addSprm += SprmUtils.addSprm(18436, characterProperties.getIbstRMark(), (byte[]) null, arrayList);
        }
        if (!characterProperties.getDttmRMark().equals(characterProperties2.getDttmRMark())) {
            byte[] bArr = new byte[4];
            characterProperties.getDttmRMark().serialize(bArr, 0);
            addSprm += SprmUtils.addSprm(26629, LittleEndian.getInt(bArr), (byte[]) null, arrayList);
        }
        if (characterProperties.isFData() != characterProperties2.isFData()) {
            addSprm += SprmUtils.addSprm(2054, characterProperties.isFData() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFSpec() && characterProperties.getFtcSym() != 0) {
            byte[] bArr2 = new byte[4];
            LittleEndian.putShort(bArr2, 0, (short) characterProperties.getFtcSym());
            LittleEndian.putShort(bArr2, 2, (short) characterProperties.getXchSym());
            addSprm += SprmUtils.addSprm(27145, 0, bArr2, arrayList);
        }
        if (characterProperties.isFOle2() != characterProperties.isFOle2()) {
            addSprm += SprmUtils.addSprm(2058, characterProperties.isFOle2() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.getIcoHighlight() != characterProperties2.getIcoHighlight()) {
            addSprm += SprmUtils.addSprm(10764, characterProperties.getIcoHighlight(), (byte[]) null, arrayList);
        }
        if (characterProperties.getFcObj() != characterProperties2.getFcObj()) {
            addSprm += SprmUtils.addSprm(26638, characterProperties.getFcObj(), (byte[]) null, arrayList);
        }
        if (characterProperties.getIstd() != characterProperties2.getIstd()) {
            addSprm += SprmUtils.addSprm(18992, characterProperties.getIstd(), (byte[]) null, arrayList);
        }
        if (characterProperties.isFBold() != characterProperties2.isFBold()) {
            addSprm += SprmUtils.addSprm(2101, characterProperties.isFBold() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFItalic() != characterProperties2.isFItalic()) {
            addSprm += SprmUtils.addSprm(2102, characterProperties.isFItalic() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFStrike() != characterProperties2.isFStrike()) {
            addSprm += SprmUtils.addSprm(2103, characterProperties.isFStrike() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFOutline() != characterProperties2.isFOutline()) {
            addSprm += SprmUtils.addSprm(2104, characterProperties.isFOutline() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFShadow() != characterProperties2.isFShadow()) {
            addSprm += SprmUtils.addSprm(2105, characterProperties.isFShadow() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFSmallCaps() != characterProperties2.isFSmallCaps()) {
            addSprm += SprmUtils.addSprm(2106, characterProperties.isFSmallCaps() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFCaps() != characterProperties2.isFCaps()) {
            addSprm += SprmUtils.addSprm(2107, characterProperties.isFCaps() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFVanish() != characterProperties2.isFVanish()) {
            addSprm += SprmUtils.addSprm(2108, characterProperties.isFVanish() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.getKul() != characterProperties2.getKul()) {
            addSprm += SprmUtils.addSprm(10814, characterProperties.getKul(), (byte[]) null, arrayList);
        }
        if (characterProperties.getDxaSpace() != characterProperties2.getDxaSpace()) {
            addSprm += SprmUtils.addSprm(-30656, characterProperties.getDxaSpace(), (byte[]) null, arrayList);
        }
        if (characterProperties.getIco() != characterProperties2.getIco()) {
            addSprm += SprmUtils.addSprm(10818, characterProperties.getIco(), (byte[]) null, arrayList);
        }
        if (characterProperties.getHps() != characterProperties2.getHps()) {
            addSprm += SprmUtils.addSprm(19011, characterProperties.getHps(), (byte[]) null, arrayList);
        }
        if (characterProperties.getHpsPos() != characterProperties2.getHpsPos()) {
            addSprm += SprmUtils.addSprm(18501, characterProperties.getHpsPos(), (byte[]) null, arrayList);
        }
        if (characterProperties.getHpsKern() != characterProperties2.getHpsKern()) {
            addSprm += SprmUtils.addSprm(18507, characterProperties.getHpsKern(), (byte[]) null, arrayList);
        }
        if (characterProperties.getHresi().equals(characterProperties2.getHresi())) {
            addSprm += SprmUtils.addSprm(18510, characterProperties.getHresi().getValue(), (byte[]) null, arrayList);
        }
        if (characterProperties.getFtcAscii() != characterProperties2.getFtcAscii()) {
            addSprm += SprmUtils.addSprm(19023, characterProperties.getFtcAscii(), (byte[]) null, arrayList);
        }
        if (characterProperties.getFtcFE() != characterProperties2.getFtcFE()) {
            addSprm += SprmUtils.addSprm(19024, characterProperties.getFtcFE(), (byte[]) null, arrayList);
        }
        if (characterProperties.getFtcOther() != characterProperties2.getFtcOther()) {
            addSprm += SprmUtils.addSprm(19025, characterProperties.getFtcOther(), (byte[]) null, arrayList);
        }
        if (characterProperties.isFDStrike() != characterProperties2.isFDStrike()) {
            addSprm += SprmUtils.addSprm(10835, characterProperties.isFDStrike() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFImprint() != characterProperties2.isFImprint()) {
            addSprm += SprmUtils.addSprm(2132, characterProperties.isFImprint() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFSpec() != characterProperties2.isFSpec()) {
            addSprm += SprmUtils.addSprm(2133, characterProperties.isFSpec() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFObj() != characterProperties2.isFObj()) {
            addSprm += SprmUtils.addSprm(2134, characterProperties.isFObj() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.isFEmboss() != characterProperties2.isFEmboss()) {
            addSprm += SprmUtils.addSprm(2136, characterProperties.isFEmboss() ? 1 : 0, (byte[]) null, arrayList);
        }
        if (characterProperties.getSfxtText() != characterProperties2.getSfxtText()) {
            addSprm += SprmUtils.addSprm(10329, characterProperties.getSfxtText(), (byte[]) null, arrayList);
        }
        if (!characterProperties.getCv().equals(characterProperties2.getCv()) && !characterProperties.getCv().isEmpty()) {
            addSprm += SprmUtils.addSprm(CharacterProperties.SPRM_CCV, characterProperties.getCv().getValue(), (byte[]) null, arrayList);
        }
        return SprmUtils.getGrpprl(arrayList, addSprm);
    }
}
