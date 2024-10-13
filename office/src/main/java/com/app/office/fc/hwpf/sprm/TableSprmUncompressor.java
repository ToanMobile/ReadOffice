package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.hwpf.usermodel.TableProperties;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;

@Internal
public final class TableSprmUncompressor extends SprmUncompressor {
    private static final POILogger logger = POILogFactory.getLogger(TableSprmUncompressor.class);

    @Deprecated
    public static TableProperties uncompressTAP(byte[] bArr, int i) {
        TableProperties tableProperties = new TableProperties();
        SprmIterator sprmIterator = new SprmIterator(bArr, i);
        while (sprmIterator.hasNext()) {
            SprmOperation next = sprmIterator.next();
            if (next.getType() == 5) {
                try {
                    unCompressTAPOperation(tableProperties, next);
                } catch (ArrayIndexOutOfBoundsException e) {
                    logger.log(POILogger.ERROR, (Object) "Unable to apply ", (Object) next, (Object) ": ", (Object) e, (Throwable) e);
                }
            }
        }
        return tableProperties;
    }

    public static TableProperties uncompressTAP(SprmBuffer sprmBuffer) {
        TableProperties tableProperties;
        SprmOperation findSprm = sprmBuffer.findSprm(-10744);
        if (findSprm != null) {
            tableProperties = new TableProperties((short) findSprm.getGrpprl()[findSprm.getGrpprlOffset()]);
        } else {
            logger.log(POILogger.WARN, (Object) "Some table rows didn't specify number of columns in SPRMs");
            tableProperties = new TableProperties(1);
        }
        sprmBuffer.findSprm(-10701);
        SprmIterator it = sprmBuffer.iterator();
        while (it.hasNext()) {
            SprmOperation next = it.next();
            if (next.getType() == 5 || next.getType() == 1) {
                try {
                    unCompressTAPOperation(tableProperties, next);
                } catch (ArrayIndexOutOfBoundsException e) {
                    logger.log(POILogger.ERROR, (Object) "Unable to apply ", (Object) next, (Object) ": ", (Object) e, (Throwable) e);
                }
            }
        }
        return tableProperties;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void unCompressTAPOperation(com.app.office.fc.hwpf.usermodel.TableProperties r10, com.app.office.fc.hwpf.sprm.SprmOperation r11) {
        /*
            int r0 = r11.getOperation()
            if (r0 == 0) goto L_0x0229
            r1 = 0
            r2 = 1
            if (r0 == r2) goto L_0x020a
            r3 = 2
            if (r0 == r3) goto L_0x01ed
            r4 = 3
            if (r0 == r4) goto L_0x01e1
            r5 = 4
            if (r0 == r5) goto L_0x01d5
            r6 = 5
            if (r0 == r6) goto L_0x0197
            r6 = 7
            if (r0 == r6) goto L_0x018e
            r6 = 8
            if (r0 == r6) goto L_0x012b
            r7 = 33
            if (r0 == r7) goto L_0x00b7
            r1 = 97
            if (r0 == r1) goto L_0x00ac
            r1 = 51
            if (r0 == r1) goto L_0x009a
            r1 = 52
            if (r0 == r1) goto L_0x002f
            goto L_0x0231
        L_0x002f:
            byte[] r0 = r11.getGrpprl()
            int r1 = r11.getGrpprlOffset()
            byte r0 = r0[r1]
            byte[] r1 = r11.getGrpprl()
            int r6 = r11.getGrpprlOffset()
            int r6 = r6 + r2
            byte r1 = r1[r6]
            byte[] r2 = r11.getGrpprl()
            int r6 = r11.getGrpprlOffset()
            int r6 = r6 + r3
            byte r2 = r2[r6]
            byte[] r3 = r11.getGrpprl()
            int r6 = r11.getGrpprlOffset()
            int r6 = r6 + r4
            byte r3 = r3[r6]
            byte[] r4 = r11.getGrpprl()
            int r11 = r11.getGrpprlOffset()
            int r11 = r11 + r5
            short r11 = com.app.office.fc.util.LittleEndian.getShort(r4, r11)
        L_0x0067:
            if (r0 >= r1) goto L_0x0231
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor[] r4 = r10.getRgtc()
            r4 = r4[r0]
            r5 = r2 & 1
            if (r5 == 0) goto L_0x0079
            r4.setFtsCellPaddingTop(r3)
            r4.setWCellPaddingTop(r11)
        L_0x0079:
            r5 = r2 & 2
            if (r5 == 0) goto L_0x0083
            r4.setFtsCellPaddingLeft(r3)
            r4.setWCellPaddingLeft(r11)
        L_0x0083:
            r5 = r2 & 4
            if (r5 == 0) goto L_0x008d
            r4.setFtsCellPaddingBottom(r3)
            r4.setWCellPaddingBottom(r11)
        L_0x008d:
            r5 = r2 & 8
            if (r5 == 0) goto L_0x0097
            r4.setFtsCellPaddingRight(r3)
            r4.setWCellPaddingRight(r11)
        L_0x0097:
            int r0 = r0 + 1
            goto L_0x0067
        L_0x009a:
            byte[] r0 = r11.getGrpprl()
            int r11 = r11.getGrpprlOffset()
            int r11 = r11 + r5
            short r11 = com.app.office.fc.util.LittleEndian.getShort(r0, r11)
            r10.setTCellSpacingDefault(r11)
            goto L_0x0231
        L_0x00ac:
            int r11 = r11.getOperand()
            int r11 = r11 >> r6
            short r11 = (short) r11
            r10.setTableIndent(r11)
            goto L_0x0231
        L_0x00b7:
            int r11 = r11.getOperand()
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0 = r0 & r11
            int r0 = r0 >> 24
            r2 = 16711680(0xff0000, float:2.3418052E-38)
            r2 = r2 & r11
            int r2 = r2 >> 16
            r3 = 65535(0xffff, float:9.1834E-41)
            r11 = r11 & r3
            short r3 = r10.getItcMac()
            int r4 = r3 + r2
            int r5 = r4 + 1
            short[] r5 = new short[r5]
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor[] r4 = new com.app.office.fc.hwpf.usermodel.TableCellDescriptor[r4]
            if (r0 < r3) goto L_0x00e9
            short[] r0 = r10.getRgdxaCenter()
            int r6 = r3 + 1
            java.lang.System.arraycopy(r0, r1, r5, r1, r6)
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor[] r10 = r10.getRgtc()
            java.lang.System.arraycopy(r10, r1, r4, r1, r3)
            r0 = r3
            goto L_0x010a
        L_0x00e9:
            short[] r6 = r10.getRgdxaCenter()
            int r7 = r0 + 1
            java.lang.System.arraycopy(r6, r1, r5, r1, r7)
            short[] r6 = r10.getRgdxaCenter()
            int r8 = r0 + r2
            int r3 = r3 - r0
            java.lang.System.arraycopy(r6, r7, r5, r8, r3)
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor[] r6 = r10.getRgtc()
            java.lang.System.arraycopy(r6, r1, r4, r1, r0)
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor[] r10 = r10.getRgtc()
            java.lang.System.arraycopy(r10, r0, r4, r8, r3)
        L_0x010a:
            r10 = r0
        L_0x010b:
            int r1 = r0 + r2
            if (r10 >= r1) goto L_0x0121
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor r1 = new com.app.office.fc.hwpf.usermodel.TableCellDescriptor
            r1.<init>()
            r4[r10] = r1
            int r1 = r10 + -1
            short r1 = r5[r1]
            int r1 = r1 + r11
            short r1 = (short) r1
            r5[r10] = r1
            int r10 = r10 + 1
            goto L_0x010b
        L_0x0121:
            int r10 = r1 + -1
            short r10 = r5[r10]
            int r10 = r10 + r11
            short r10 = (short) r10
            r5[r1] = r10
            goto L_0x0231
        L_0x012b:
            byte[] r0 = r11.getGrpprl()
            int r4 = r11.getGrpprlOffset()
            byte r5 = r0[r4]
            short r5 = (short) r5
            int r6 = r5 + 1
            short[] r7 = new short[r6]
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor[] r8 = new com.app.office.fc.hwpf.usermodel.TableCellDescriptor[r5]
            r10.setItcMac(r5)
            r10.setRgdxaCenter(r7)
            r10.setRgtc(r8)
            r10 = 0
        L_0x0146:
            if (r10 >= r5) goto L_0x0155
            int r9 = r10 * 2
            int r9 = r9 + r2
            int r9 = r9 + r4
            short r9 = com.app.office.fc.util.LittleEndian.getShort(r0, r9)
            r7[r10] = r9
            int r10 = r10 + 1
            goto L_0x0146
        L_0x0155:
            int r10 = r11.size()
            int r10 = r10 + r4
            int r10 = r10 + -6
            int r6 = r6 * 2
            int r6 = r6 + r2
            int r11 = r4 + r6
            if (r11 >= r10) goto L_0x0165
            r10 = 1
            goto L_0x0166
        L_0x0165:
            r10 = 0
        L_0x0166:
            if (r1 >= r5) goto L_0x0182
            if (r10 == 0) goto L_0x0178
            int r11 = r1 * 20
            int r11 = r11 + r6
            int r11 = r11 + r4
            int r3 = r0.length
            if (r11 >= r3) goto L_0x0178
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor r11 = com.app.office.fc.hwpf.usermodel.TableCellDescriptor.convertBytesToTC(r0, r11)
            r8[r1] = r11
            goto L_0x017f
        L_0x0178:
            com.app.office.fc.hwpf.usermodel.TableCellDescriptor r11 = new com.app.office.fc.hwpf.usermodel.TableCellDescriptor
            r11.<init>()
            r8[r1] = r11
        L_0x017f:
            int r1 = r1 + 1
            goto L_0x0166
        L_0x0182:
            int r10 = r5 * 2
            int r10 = r10 + r2
            int r4 = r4 + r10
            short r10 = com.app.office.fc.util.LittleEndian.getShort(r0, r4)
            r7[r5] = r10
            goto L_0x0231
        L_0x018e:
            int r11 = r11.getOperand()
            r10.setDyaRowHeight(r11)
            goto L_0x0231
        L_0x0197:
            byte[] r0 = r11.getGrpprl()
            int r11 = r11.getGrpprlOffset()
            com.app.office.fc.hwpf.usermodel.BorderCode r1 = new com.app.office.fc.hwpf.usermodel.BorderCode
            r1.<init>(r0, r11)
            r10.setBrcTop(r1)
            int r11 = r11 + r5
            com.app.office.fc.hwpf.usermodel.BorderCode r1 = new com.app.office.fc.hwpf.usermodel.BorderCode
            r1.<init>(r0, r11)
            r10.setBrcLeft(r1)
            int r11 = r11 + r5
            com.app.office.fc.hwpf.usermodel.BorderCode r1 = new com.app.office.fc.hwpf.usermodel.BorderCode
            r1.<init>(r0, r11)
            r10.setBrcBottom(r1)
            int r11 = r11 + r5
            com.app.office.fc.hwpf.usermodel.BorderCode r1 = new com.app.office.fc.hwpf.usermodel.BorderCode
            r1.<init>(r0, r11)
            r10.setBrcRight(r1)
            int r11 = r11 + r5
            com.app.office.fc.hwpf.usermodel.BorderCode r1 = new com.app.office.fc.hwpf.usermodel.BorderCode
            r1.<init>(r0, r11)
            r10.setBrcHorizontal(r1)
            int r11 = r11 + r5
            com.app.office.fc.hwpf.usermodel.BorderCode r1 = new com.app.office.fc.hwpf.usermodel.BorderCode
            r1.<init>(r0, r11)
            r10.setBrcVertical(r1)
            goto L_0x0231
        L_0x01d5:
            int r11 = r11.getOperand()
            boolean r11 = getFlag(r11)
            r10.setFTableHeader(r11)
            goto L_0x0231
        L_0x01e1:
            int r11 = r11.getOperand()
            boolean r11 = getFlag(r11)
            r10.setFCantSplit(r11)
            goto L_0x0231
        L_0x01ed:
            short[] r0 = r10.getRgdxaCenter()
            if (r0 == 0) goto L_0x0202
            int r2 = r10.getDxaGapHalf()
            int r3 = r11.getOperand()
            int r2 = r2 - r3
            short r3 = r0[r1]
            int r3 = r3 + r2
            short r2 = (short) r3
            r0[r1] = r2
        L_0x0202:
            int r11 = r11.getOperand()
            r10.setDxaGapHalf(r11)
            goto L_0x0231
        L_0x020a:
            short[] r0 = r10.getRgdxaCenter()
            short r2 = r10.getItcMac()
            int r11 = r11.getOperand()
            short r3 = r0[r1]
            int r10 = r10.getDxaGapHalf()
            int r3 = r3 + r10
            int r11 = r11 - r3
        L_0x021e:
            if (r1 >= r2) goto L_0x0231
            short r10 = r0[r1]
            int r10 = r10 + r11
            short r10 = (short) r10
            r0[r1] = r10
            int r1 = r1 + 1
            goto L_0x021e
        L_0x0229:
            int r11 = r11.getOperand()
            short r11 = (short) r11
            r10.setJc(r11)
        L_0x0231:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hwpf.sprm.TableSprmUncompressor.unCompressTAPOperation(com.app.office.fc.hwpf.usermodel.TableProperties, com.app.office.fc.hwpf.sprm.SprmOperation):void");
    }
}
