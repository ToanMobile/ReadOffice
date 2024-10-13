package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.hwpf.usermodel.DateAndTime;
import com.app.office.fc.hwpf.usermodel.DropCapSpecifier;
import com.app.office.fc.hwpf.usermodel.LineSpacingDescriptor;
import com.app.office.fc.hwpf.usermodel.ParagraphProperties;
import com.app.office.fc.hwpf.usermodel.ShadingDescriptor;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Internal
public final class ParagraphSprmUncompressor extends SprmUncompressor {
    private static final POILogger logger = POILogFactory.getLogger(ParagraphSprmUncompressor.class);

    public static ParagraphProperties uncompressPAP(ParagraphProperties paragraphProperties, byte[] bArr, int i) {
        try {
            ParagraphProperties paragraphProperties2 = (ParagraphProperties) paragraphProperties.clone();
            SprmIterator sprmIterator = new SprmIterator(bArr, i);
            while (sprmIterator.hasNext()) {
                SprmOperation next = sprmIterator.next();
                if (next.getType() == 1) {
                    try {
                        unCompressPAPOperation(paragraphProperties2, next);
                    } catch (Exception e) {
                        POILogger pOILogger = logger;
                        int i2 = POILogger.ERROR;
                        pOILogger.log(i2, (Object) "Unable to apply SPRM operation '" + next.getOperation() + "': ", (Throwable) e);
                    }
                }
            }
            return paragraphProperties2;
        } catch (CloneNotSupportedException unused) {
            throw new RuntimeException("There is no way this exception should happen!!");
        }
    }

    static void unCompressPAPOperation(ParagraphProperties paragraphProperties, SprmOperation sprmOperation) {
        int operation = sprmOperation.getOperation();
        if (operation != 0) {
            boolean z = false;
            if (operation == 67) {
                if (sprmOperation.getOperand() != 0) {
                    z = true;
                }
                paragraphProperties.setFNumRMIns(z);
            } else if (operation != 69) {
                if (operation != 97) {
                    switch (operation) {
                        case 2:
                            if (paragraphProperties.getIstd() <= 9 || paragraphProperties.getIstd() >= 1) {
                                byte operand = (byte) sprmOperation.getOperand();
                                paragraphProperties.setIstd(paragraphProperties.getIstd() + operand);
                                paragraphProperties.setLvl((byte) (paragraphProperties.getLvl() + operand));
                                if (((operand >> 7) & 1) == 1) {
                                    paragraphProperties.setIstd(Math.max(paragraphProperties.getIstd(), 1));
                                    return;
                                } else {
                                    paragraphProperties.setIstd(Math.min(paragraphProperties.getIstd(), 9));
                                    return;
                                }
                            } else {
                                return;
                            }
                        case 3:
                            paragraphProperties.setJc((byte) sprmOperation.getOperand());
                            return;
                        case 4:
                            if (sprmOperation.getOperand() != 0) {
                                z = true;
                            }
                            paragraphProperties.setFSideBySide(z);
                            return;
                        case 5:
                            if (sprmOperation.getOperand() != 0) {
                                z = true;
                            }
                            paragraphProperties.setFKeep(z);
                            return;
                        case 6:
                            if (sprmOperation.getOperand() != 0) {
                                z = true;
                            }
                            paragraphProperties.setFKeepFollow(z);
                            return;
                        case 7:
                            if (sprmOperation.getOperand() != 0) {
                                z = true;
                            }
                            paragraphProperties.setFPageBreakBefore(z);
                            return;
                        case 8:
                            paragraphProperties.setBrcl((byte) sprmOperation.getOperand());
                            return;
                        case 9:
                            paragraphProperties.setBrcp((byte) sprmOperation.getOperand());
                            return;
                        case 10:
                            paragraphProperties.setIlvl((byte) sprmOperation.getOperand());
                            return;
                        case 11:
                            paragraphProperties.setIlfo(sprmOperation.getOperand());
                            return;
                        case 12:
                            if (sprmOperation.getOperand() != 0) {
                                z = true;
                            }
                            paragraphProperties.setFNoLnn(z);
                            return;
                        case 13:
                            handleTabs(paragraphProperties, sprmOperation);
                            return;
                        case 14:
                            paragraphProperties.setDxaRight(sprmOperation.getOperand());
                            return;
                        case 15:
                            paragraphProperties.setDxaLeft(sprmOperation.getOperand());
                            return;
                        case 16:
                            paragraphProperties.setDxaLeft(paragraphProperties.getDxaLeft() + sprmOperation.getOperand());
                            paragraphProperties.setDxaLeft(Math.max(0, paragraphProperties.getDxaLeft()));
                            return;
                        case 17:
                            paragraphProperties.setDxaLeft1(sprmOperation.getOperand());
                            return;
                        case 18:
                            paragraphProperties.setLspd(new LineSpacingDescriptor(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                            return;
                        case 19:
                            paragraphProperties.setDyaBefore(sprmOperation.getOperand());
                            return;
                        case 20:
                            paragraphProperties.setDyaAfter(sprmOperation.getOperand());
                            return;
                        default:
                            switch (operation) {
                                case 22:
                                    if (sprmOperation.getOperand() != 0) {
                                        z = true;
                                    }
                                    paragraphProperties.setFInTable(z);
                                    return;
                                case 23:
                                    if (sprmOperation.getOperand() != 0) {
                                        z = true;
                                    }
                                    paragraphProperties.setFTtp(z);
                                    return;
                                case 24:
                                    paragraphProperties.setDxaAbs(sprmOperation.getOperand());
                                    return;
                                case 25:
                                    paragraphProperties.setDyaAbs(sprmOperation.getOperand());
                                    return;
                                case 26:
                                    paragraphProperties.setDxaWidth(sprmOperation.getOperand());
                                    return;
                                case 27:
                                    byte operand2 = (byte) sprmOperation.getOperand();
                                    byte b = (byte) ((operand2 & 12) >> 2);
                                    byte b2 = (byte) (operand2 & 3);
                                    if (b != 3) {
                                        paragraphProperties.setPcVert(b);
                                    }
                                    if (b2 != 3) {
                                        paragraphProperties.setPcHorz(b2);
                                        return;
                                    }
                                    return;
                                default:
                                    switch (operation) {
                                        case 34:
                                            paragraphProperties.setDxaFromText(sprmOperation.getOperand());
                                            return;
                                        case 35:
                                            paragraphProperties.setWr((byte) sprmOperation.getOperand());
                                            return;
                                        case 36:
                                            paragraphProperties.setBrcTop(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                                            return;
                                        case 37:
                                            paragraphProperties.setBrcLeft(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                                            return;
                                        case 38:
                                            paragraphProperties.setBrcBottom(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                                            return;
                                        case 39:
                                            paragraphProperties.setBrcRight(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                                            return;
                                        case 40:
                                            paragraphProperties.setBrcBetween(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                                            return;
                                        case 41:
                                            paragraphProperties.setBrcBar(new BorderCode(sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset()));
                                            return;
                                        case 42:
                                            if (sprmOperation.getOperand() != 0) {
                                                z = true;
                                            }
                                            paragraphProperties.setFNoAutoHyph(z);
                                            return;
                                        case 43:
                                            paragraphProperties.setDyaHeight(sprmOperation.getOperand());
                                            return;
                                        case 44:
                                            paragraphProperties.setDcs(new DropCapSpecifier((short) sprmOperation.getOperand()));
                                            return;
                                        case 45:
                                            paragraphProperties.setShd(new ShadingDescriptor((short) sprmOperation.getOperand()));
                                            return;
                                        case 46:
                                            paragraphProperties.setDyaFromText(sprmOperation.getOperand());
                                            return;
                                        case 47:
                                            paragraphProperties.setDxaFromText(sprmOperation.getOperand());
                                            return;
                                        case 48:
                                            if (sprmOperation.getOperand() != 0) {
                                                z = true;
                                            }
                                            paragraphProperties.setFLocked(z);
                                            return;
                                        case 49:
                                            if (sprmOperation.getOperand() != 0) {
                                                z = true;
                                            }
                                            paragraphProperties.setFWidowControl(z);
                                            return;
                                        default:
                                            switch (operation) {
                                                case 51:
                                                    if (sprmOperation.getOperand() != 0) {
                                                        z = true;
                                                    }
                                                    paragraphProperties.setFKinsoku(z);
                                                    return;
                                                case 52:
                                                    if (sprmOperation.getOperand() != 0) {
                                                        z = true;
                                                    }
                                                    paragraphProperties.setFWordWrap(z);
                                                    return;
                                                case 53:
                                                    if (sprmOperation.getOperand() != 0) {
                                                        z = true;
                                                    }
                                                    paragraphProperties.setFOverflowPunct(z);
                                                    return;
                                                case 54:
                                                    if (sprmOperation.getOperand() != 0) {
                                                        z = true;
                                                    }
                                                    paragraphProperties.setFTopLinePunct(z);
                                                    return;
                                                case 55:
                                                    if (sprmOperation.getOperand() != 0) {
                                                        z = true;
                                                    }
                                                    paragraphProperties.setFAutoSpaceDE(z);
                                                    return;
                                                case 56:
                                                    if (sprmOperation.getOperand() != 0) {
                                                        z = true;
                                                    }
                                                    paragraphProperties.setFAutoSpaceDN(z);
                                                    return;
                                                case 57:
                                                    paragraphProperties.setWAlignFont(sprmOperation.getOperand());
                                                    return;
                                                case 58:
                                                    paragraphProperties.setFontAlign((short) sprmOperation.getOperand());
                                                    return;
                                                default:
                                                    switch (operation) {
                                                        case 62:
                                                            int size = sprmOperation.size() - 3;
                                                            byte[] bArr = new byte[size];
                                                            System.arraycopy(bArr, 0, sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset(), size);
                                                            paragraphProperties.setAnld(bArr);
                                                            return;
                                                        case 63:
                                                            try {
                                                                byte[] grpprl = sprmOperation.getGrpprl();
                                                                int grpprlOffset = sprmOperation.getGrpprlOffset();
                                                                if (grpprl[grpprlOffset] != 0) {
                                                                    z = true;
                                                                }
                                                                paragraphProperties.setFPropRMark(z);
                                                                paragraphProperties.setIbstPropRMark(LittleEndian.getShort(grpprl, grpprlOffset + 1));
                                                                paragraphProperties.setDttmPropRMark(new DateAndTime(grpprl, grpprlOffset + 3));
                                                                return;
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                                return;
                                                            }
                                                        case 64:
                                                            paragraphProperties.setLvl((byte) sprmOperation.getOperand());
                                                            return;
                                                        case 65:
                                                            if (sprmOperation.getOperand() != 0) {
                                                                z = true;
                                                            }
                                                            paragraphProperties.setFBiDi(z);
                                                            return;
                                                        default:
                                                            switch (operation) {
                                                                case 71:
                                                                    if (sprmOperation.getOperand() != 0) {
                                                                        z = true;
                                                                    }
                                                                    paragraphProperties.setFUsePgsuSettings(z);
                                                                    return;
                                                                case 72:
                                                                    if (sprmOperation.getOperand() != 0) {
                                                                        z = true;
                                                                    }
                                                                    paragraphProperties.setFAdjustRight(z);
                                                                    return;
                                                                case 73:
                                                                    paragraphProperties.setItap(sprmOperation.getOperand());
                                                                    return;
                                                                case 74:
                                                                    paragraphProperties.setItap((byte) (paragraphProperties.getItap() + sprmOperation.getOperand()));
                                                                    return;
                                                                case 75:
                                                                    if (sprmOperation.getOperand() != 0) {
                                                                        z = true;
                                                                    }
                                                                    paragraphProperties.setFInnerTableCell(z);
                                                                    return;
                                                                case 76:
                                                                    if (sprmOperation.getOperand() != 0) {
                                                                        z = true;
                                                                    }
                                                                    paragraphProperties.setFTtpEmbedded(z);
                                                                    return;
                                                                default:
                                                                    return;
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
                } else {
                    paragraphProperties.setJustificationLogical((byte) sprmOperation.getOperand());
                }
            } else if (sprmOperation.getSizeCode() == 6) {
                int size2 = sprmOperation.size() - 3;
                byte[] bArr2 = new byte[size2];
                System.arraycopy(bArr2, 0, sprmOperation.getGrpprl(), sprmOperation.getGrpprlOffset(), size2);
                paragraphProperties.setNumrm(bArr2);
            }
        } else {
            paragraphProperties.setIstd(sprmOperation.getOperand());
        }
    }

    private static void handleTabs(ParagraphProperties paragraphProperties, SprmOperation sprmOperation) {
        byte[] grpprl = sprmOperation.getGrpprl();
        int grpprlOffset = sprmOperation.getGrpprlOffset();
        int i = grpprlOffset + 1;
        byte b = grpprl[grpprlOffset];
        int[] rgdxaTab = paragraphProperties.getRgdxaTab();
        byte[] rgtbd = paragraphProperties.getRgtbd();
        HashMap hashMap = new HashMap();
        for (int i2 = 0; i2 < rgdxaTab.length; i2++) {
            hashMap.put(Integer.valueOf(rgdxaTab[i2]), Byte.valueOf(rgtbd[i2]));
        }
        for (int i3 = 0; i3 < b; i3++) {
            short s = LittleEndian.getShort(grpprl, i);
            hashMap.remove(Integer.valueOf(s));
            paragraphProperties.setTabClearPosition((short) Math.max(paragraphProperties.getTabClearPosition(), s));
            i += 2;
        }
        int i4 = i + 1;
        byte b2 = grpprl[i];
        int i5 = i4;
        for (int i6 = 0; i6 < b2; i6++) {
            hashMap.put(Integer.valueOf(LittleEndian.getShort(grpprl, i5)), Byte.valueOf(grpprl[(b2 * 2) + i6 + i4]));
            i5 += 2;
        }
        int size = hashMap.size();
        int[] iArr = new int[size];
        byte[] bArr = new byte[size];
        ArrayList arrayList = new ArrayList(hashMap.keySet());
        Collections.sort(arrayList);
        for (int i7 = 0; i7 < size; i7++) {
            Integer num = (Integer) arrayList.get(i7);
            iArr[i7] = num.intValue();
            bArr[i7] = ((Byte) hashMap.get(num)).byteValue();
        }
        paragraphProperties.setRgdxaTab(iArr);
        paragraphProperties.setRgtbd(bArr);
    }
}
