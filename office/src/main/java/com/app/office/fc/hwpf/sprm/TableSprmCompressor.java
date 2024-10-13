package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.hwpf.usermodel.TableCellDescriptor;
import com.app.office.fc.hwpf.usermodel.TableProperties;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.Arrays;

@Internal
public final class TableSprmCompressor {
    public static byte[] compressTableProperty(TableProperties tableProperties) {
        ArrayList arrayList = new ArrayList();
        int addSprm = tableProperties.getJc() != 0 ? SprmUtils.addSprm(21504, tableProperties.getJc(), (byte[]) null, arrayList) + 0 : 0;
        if (tableProperties.getFCantSplit()) {
            addSprm += SprmUtils.addSprm(13315, 1, (byte[]) null, arrayList);
        }
        if (tableProperties.getFTableHeader()) {
            addSprm += SprmUtils.addSprm(13316, 1, (byte[]) null, arrayList);
        }
        byte[] bArr = new byte[24];
        tableProperties.getBrcTop().serialize(bArr, 0);
        tableProperties.getBrcLeft().serialize(bArr, 4);
        tableProperties.getBrcBottom().serialize(bArr, 8);
        tableProperties.getBrcRight().serialize(bArr, 12);
        tableProperties.getBrcHorizontal().serialize(bArr, 16);
        tableProperties.getBrcVertical().serialize(bArr, 20);
        if (!Arrays.equals(bArr, new byte[24])) {
            addSprm += SprmUtils.addSprm(-10747, 0, bArr, arrayList);
        }
        if (tableProperties.getDyaRowHeight() != 0) {
            addSprm += SprmUtils.addSprm(-27641, tableProperties.getDyaRowHeight(), (byte[]) null, arrayList);
        }
        if (tableProperties.getItcMac() > 0) {
            short itcMac = tableProperties.getItcMac();
            int i = ((itcMac + 1) * 2) + 1;
            byte[] bArr2 = new byte[((itcMac * 20) + i)];
            bArr2[0] = (byte) itcMac;
            short[] rgdxaCenter = tableProperties.getRgdxaCenter();
            for (int i2 = 0; i2 < rgdxaCenter.length; i2++) {
                LittleEndian.putShort(bArr2, (i2 * 2) + 1, rgdxaCenter[i2]);
            }
            TableCellDescriptor[] rgtc = tableProperties.getRgtc();
            for (int i3 = 0; i3 < rgtc.length; i3++) {
                rgtc[i3].serialize(bArr2, (i3 * 20) + i);
            }
            addSprm += SprmUtils.addSpecialSprm(-10744, bArr2, arrayList);
        }
        if (tableProperties.getTlp() != null && !tableProperties.getTlp().isEmpty()) {
            byte[] bArr3 = new byte[4];
            tableProperties.getTlp().serialize(bArr3, 0);
            addSprm += SprmUtils.addSprm(29706, 0, bArr3, arrayList);
        }
        return SprmUtils.getGrpprl(arrayList, addSprm);
    }
}
