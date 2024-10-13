package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.List;

@Internal
public final class SprmUtils {
    public static byte[] shortArrayToByteArray(short[] sArr) {
        byte[] bArr = new byte[(sArr.length * 2)];
        for (int i = 0; i < sArr.length; i++) {
            LittleEndian.putShort(bArr, i * 2, sArr[i]);
        }
        return bArr;
    }

    public static int addSpecialSprm(short s, byte[] bArr, List<byte[]> list) {
        int length = bArr.length + 4;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 4, bArr.length);
        LittleEndian.putShort(bArr2, s);
        LittleEndian.putShort(bArr2, 2, (short) (bArr.length + 1));
        list.add(bArr2);
        return length;
    }

    public static int addSprm(short s, boolean z, List<byte[]> list) {
        return addSprm(s, z ? 1 : 0, (byte[]) null, list);
    }

    public static int addSprm(short s, int i, byte[] bArr, List<byte[]> list) {
        byte[] bArr2;
        switch ((57344 & s) >> 13) {
            case 0:
            case 1:
                bArr2 = new byte[3];
                bArr2[2] = (byte) i;
                break;
            case 2:
                bArr2 = new byte[4];
                LittleEndian.putShort(bArr2, 2, (short) i);
                break;
            case 3:
                bArr2 = new byte[6];
                LittleEndian.putInt(bArr2, 2, i);
                break;
            case 4:
            case 5:
                bArr2 = new byte[4];
                LittleEndian.putShort(bArr2, 2, (short) i);
                break;
            case 6:
                byte[] bArr3 = new byte[(bArr.length + 3)];
                bArr3[2] = (byte) bArr.length;
                System.arraycopy(bArr, 0, bArr3, 3, bArr.length);
                bArr2 = bArr3;
                break;
            case 7:
                bArr2 = new byte[5];
                byte[] bArr4 = new byte[4];
                LittleEndian.putInt(bArr4, 0, i);
                System.arraycopy(bArr4, 0, bArr2, 2, 3);
                break;
            default:
                bArr2 = null;
                break;
        }
        LittleEndian.putShort(bArr2, 0, s);
        list.add(bArr2);
        return bArr2.length;
    }

    public static byte[] getGrpprl(List<byte[]> list, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (int size = list.size() - 1; size >= 0; size--) {
            byte[] remove = list.remove(0);
            System.arraycopy(remove, 0, bArr, i2, remove.length);
            i2 += remove.length;
        }
        return bArr;
    }

    public static int convertBrcToInt(short[] sArr) {
        byte[] bArr = new byte[4];
        LittleEndian.putShort(bArr, sArr[0]);
        LittleEndian.putShort(bArr, 2, sArr[1]);
        return LittleEndian.getInt(bArr);
    }
}
