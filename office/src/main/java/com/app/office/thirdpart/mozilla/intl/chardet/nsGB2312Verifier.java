package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsGB2312Verifier extends nsVerifier {
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

    public nsGB2312Verifier() {
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
        iArr[16] = 0;
        iArr[17] = 0;
        iArr[18] = 0;
        iArr[19] = 0;
        iArr[20] = 572662304;
        iArr[21] = 858993442;
        iArr[22] = 572662306;
        iArr[23] = 572662306;
        iArr[24] = 572662306;
        iArr[25] = 572662306;
        iArr[26] = 572662306;
        iArr[27] = 572662306;
        iArr[28] = 572662306;
        iArr[29] = 572662306;
        iArr[30] = 572662306;
        iArr[31] = 35791394;
        int[] iArr2 = new int[2];
        states = iArr2;
        iArr2[0] = 286331649;
        iArr2[1] = 1122850;
        charset = "GB2312";
        stFactor = 4;
    }
}
