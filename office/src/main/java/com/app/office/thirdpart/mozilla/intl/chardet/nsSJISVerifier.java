package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsSJISVerifier extends nsVerifier {
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

    public nsSJISVerifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 286331152;
        iArr[1] = 1118481;
        iArr[2] = 286331153;
        iArr[3] = 286327057;
        iArr[4] = 286331153;
        iArr[5] = 286331153;
        iArr[6] = 286331153;
        iArr[7] = 286331153;
        iArr[8] = 572662306;
        iArr[9] = 572662306;
        iArr[10] = 572662306;
        iArr[11] = 572662306;
        iArr[12] = 572662306;
        iArr[13] = 572662306;
        iArr[14] = 572662306;
        iArr[15] = 304226850;
        iArr[16] = 858993459;
        iArr[17] = 858993459;
        iArr[18] = 858993459;
        iArr[19] = 858993459;
        iArr[20] = 572662308;
        iArr[21] = 572662306;
        iArr[22] = 572662306;
        iArr[23] = 572662306;
        iArr[24] = 572662306;
        iArr[25] = 572662306;
        iArr[26] = 572662306;
        iArr[27] = 572662306;
        iArr[28] = 858993459;
        iArr[29] = 1145393971;
        iArr[30] = 1145324612;
        iArr[31] = 279620;
        int[] iArr2 = new int[3];
        states = iArr2;
        iArr2[0] = 286339073;
        iArr2[1] = 572657937;
        iArr2[2] = 4386;
        charset = "Shift_JIS";
        stFactor = 6;
    }
}
