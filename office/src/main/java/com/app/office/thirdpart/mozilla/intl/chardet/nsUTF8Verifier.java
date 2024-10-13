package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsUTF8Verifier extends nsVerifier {
    static int[] cclass;
    static String charset;
    static int stFactor;
    static int[] states;

    public boolean isUCS2() {
        return false;
    }

    public int[] cclass() {
        return cclass;
    }

    public int[] states() {
        return states;
    }

    public int stFactor() {
        return stFactor;
    }

    public String charset() {
        return charset;
    }

    public nsUTF8Verifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 286331153;
        iArr[1] = 1118481;
        iArr[2] = 286331153;
        iArr[3] = 286327057;
        iArr[4] = 286331153;
        iArr[5] = 286331153;
        iArr[6] = 286331153;
        iArr[7] = 286331153;
        iArr[8] = 286331153;
        iArr[9] = 286331153;
        iArr[10] = 286331153;
        iArr[11] = 286331153;
        iArr[12] = 286331153;
        iArr[13] = 286331153;
        iArr[14] = 286331153;
        iArr[15] = 286331153;
        iArr[16] = 858989090;
        iArr[17] = 1145324612;
        iArr[18] = 1145324612;
        iArr[19] = 1145324612;
        iArr[20] = 1431655765;
        iArr[21] = 1431655765;
        iArr[22] = 1431655765;
        iArr[23] = 1431655765;
        iArr[24] = 1717986816;
        iArr[25] = 1717986918;
        iArr[26] = 1717986918;
        iArr[27] = 1717986918;
        iArr[28] = -2004318073;
        iArr[29] = -2003269496;
        iArr[30] = -1145324614;
        iArr[31] = 16702940;
        int[] iArr2 = new int[26];
        states = iArr2;
        iArr2[0] = -1408167679;
        iArr2[1] = 878082233;
        iArr2[2] = 286331153;
        iArr2[3] = 286331153;
        iArr2[4] = 572662306;
        iArr2[5] = 572662306;
        iArr2[6] = 290805009;
        iArr2[7] = 286331153;
        iArr2[8] = 290803985;
        iArr2[9] = 286331153;
        iArr2[10] = 293041937;
        iArr2[11] = 286331153;
        iArr2[12] = 293015825;
        iArr2[13] = 286331153;
        iArr2[14] = 295278865;
        iArr2[15] = 286331153;
        iArr2[16] = 294719761;
        iArr2[17] = 286331153;
        iArr2[18] = 298634257;
        iArr2[19] = 286331153;
        iArr2[20] = 297865489;
        iArr2[21] = 286331153;
        iArr2[22] = 287099921;
        iArr2[23] = 286331153;
        iArr2[24] = 285212689;
        iArr2[25] = 286331153;
        charset = "UTF-8";
        stFactor = 16;
    }
}
