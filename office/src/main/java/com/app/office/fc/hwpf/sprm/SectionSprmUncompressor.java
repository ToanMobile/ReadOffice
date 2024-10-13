package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.hwpf.usermodel.SectionProperties;
import com.app.office.fc.util.Internal;

@Internal
public final class SectionSprmUncompressor extends SprmUncompressor {
    public static SectionProperties uncompressSEP(byte[] bArr, int i) {
        SectionProperties sectionProperties = new SectionProperties();
        SprmIterator sprmIterator = new SprmIterator(bArr, i);
        while (sprmIterator.hasNext()) {
            unCompressSEPOperation(sectionProperties, sprmIterator.next());
        }
        return sectionProperties;
    }

    static void unCompressSEPOperation(SectionProperties sectionProperties, SprmOperation sprmOperation) {
        boolean z = false;
        switch (sprmOperation.getOperation()) {
            case 0:
                sectionProperties.setCnsPgn((byte) sprmOperation.getOperand());
                return;
            case 1:
                sectionProperties.setIHeadingPgn((byte) sprmOperation.getOperand());
                return;
            case 2:
                int size = sprmOperation.size() - 3;
                byte[] bArr = new byte[size];
                System.arraycopy(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset(), bArr, 0, size);
                sectionProperties.setOlstAnm(bArr);
                return;
            case 5:
                sectionProperties.setFEvenlySpaced(getFlag(sprmOperation.getOperand()));
                return;
            case 6:
                sectionProperties.setFUnlocked(getFlag(sprmOperation.getOperand()));
                return;
            case 7:
                sectionProperties.setDmBinFirst((short) sprmOperation.getOperand());
                return;
            case 8:
                sectionProperties.setDmBinOther((short) sprmOperation.getOperand());
                return;
            case 9:
                sectionProperties.setBkc((byte) sprmOperation.getOperand());
                return;
            case 10:
                sectionProperties.setFTitlePage(getFlag(sprmOperation.getOperand()));
                return;
            case 11:
                sectionProperties.setCcolM1((short) sprmOperation.getOperand());
                return;
            case 12:
                sectionProperties.setDxaColumns(sprmOperation.getOperand());
                return;
            case 13:
                sectionProperties.setFAutoPgn(getFlag(sprmOperation.getOperand()));
                return;
            case 14:
                sectionProperties.setNfcPgn((byte) sprmOperation.getOperand());
                return;
            case 15:
                sectionProperties.setDyaPgn((short) sprmOperation.getOperand());
                return;
            case 16:
                sectionProperties.setDxaPgn((short) sprmOperation.getOperand());
                return;
            case 17:
                sectionProperties.setFPgnRestart(getFlag(sprmOperation.getOperand()));
                return;
            case 18:
                sectionProperties.setFEndNote(getFlag(sprmOperation.getOperand()));
                return;
            case 19:
                sectionProperties.setLnc((byte) sprmOperation.getOperand());
                return;
            case 20:
                sectionProperties.setGrpfIhdt((byte) sprmOperation.getOperand());
                return;
            case 21:
                sectionProperties.setNLnnMod((short) sprmOperation.getOperand());
                return;
            case 22:
                sectionProperties.setDxaLnn(sprmOperation.getOperand());
                return;
            case 23:
                sectionProperties.setDyaHdrTop(sprmOperation.getOperand());
                return;
            case 24:
                sectionProperties.setDyaHdrBottom(sprmOperation.getOperand());
                return;
            case 25:
                sectionProperties.setFLBetween(getFlag(sprmOperation.getOperand()));
                return;
            case 26:
                sectionProperties.setVjc((byte) sprmOperation.getOperand());
                return;
            case 27:
                sectionProperties.setLnnMin((short) sprmOperation.getOperand());
                return;
            case 28:
                sectionProperties.setPgnStart((short) sprmOperation.getOperand());
                return;
            case 29:
                if (sprmOperation.getOperand() != 0) {
                    z = true;
                }
                sectionProperties.setDmOrientPage(z);
                return;
            case 31:
                sectionProperties.setXaPage(sprmOperation.getOperand());
                return;
            case 32:
                sectionProperties.setYaPage(sprmOperation.getOperand());
                return;
            case 33:
                sectionProperties.setDxaLeft(sprmOperation.getOperand());
                return;
            case 34:
                sectionProperties.setDxaRight(sprmOperation.getOperand());
                return;
            case 35:
                sectionProperties.setDyaTop(sprmOperation.getOperand());
                return;
            case 36:
                sectionProperties.setDyaBottom(sprmOperation.getOperand());
                return;
            case 37:
                sectionProperties.setDzaGutter(sprmOperation.getOperand());
                return;
            case 38:
                sectionProperties.setDmPaperReq((short) sprmOperation.getOperand());
                return;
            case 39:
                sectionProperties.setFPropMark(getFlag(sprmOperation.getOperand()));
                return;
            case 43:
                sectionProperties.setBrcTop(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                return;
            case 44:
                sectionProperties.setBrcLeft(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                return;
            case 45:
                sectionProperties.setBrcBottom(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                return;
            case 46:
                sectionProperties.setBrcRight(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                return;
            case 47:
                sectionProperties.setPgbProp(sprmOperation.getOperand());
                return;
            case 48:
                sectionProperties.setDxtCharSpace(sprmOperation.getOperand());
                return;
            case 49:
                sectionProperties.setDyaLinePitch(sprmOperation.getOperand());
                return;
            case 50:
                sectionProperties.setClm(sprmOperation.getOperand());
                return;
            case 51:
                sectionProperties.setWTextFlow((short) sprmOperation.getOperand());
                return;
            default:
                return;
        }
    }
}
