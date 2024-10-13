package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsBIG5Verifier extends nsVerifier {
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

    public nsBIG5Verifier() {
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
        iArr[8] = 572662306;
        iArr[9] = 572662306;
        iArr[10] = 572662306;
        iArr[11] = 572662306;
        iArr[12] = 572662306;
        iArr[13] = 572662306;
        iArr[14] = 572662306;
        iArr[15] = 304226850;
        iArr[16] = 1145324612;
        iArr[17] = 1145324612;
        iArr[18] = 1145324612;
        iArr[19] = 1145324612;
        iArr[20] = 858993460;
        iArr[21] = 858993459;
        iArr[22] = 858993459;
        iArr[23] = 858993459;
        iArr[24] = 858993459;
        iArr[25] = 858993459;
        iArr[26] = 858993459;
        iArr[27] = 858993459;
        iArr[28] = 858993459;
        iArr[29] = 858993459;
        iArr[30] = 858993459;
        iArr[31] = 53687091;
        int[] iArr2 = new int[3];
        states = iArr2;
        iArr2[0] = 286339073;
        iArr2[1] = 304226833;
        iArr2[2] = 1;
        charset = "Big5";
        stFactor = 5;
    }
}
