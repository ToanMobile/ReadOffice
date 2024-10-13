package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsHZVerifier extends nsVerifier {
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

    public nsHZVerifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 1;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 4096;
        iArr[4] = 0;
        iArr[5] = 0;
        iArr[6] = 0;
        iArr[7] = 0;
        iArr[8] = 0;
        iArr[9] = 0;
        iArr[10] = 0;
        iArr[11] = 0;
        iArr[12] = 0;
        iArr[13] = 0;
        iArr[14] = 0;
        iArr[15] = 38813696;
        iArr[16] = 286331153;
        iArr[17] = 286331153;
        iArr[18] = 286331153;
        iArr[19] = 286331153;
        iArr[20] = 286331153;
        iArr[21] = 286331153;
        iArr[22] = 286331153;
        iArr[23] = 286331153;
        iArr[24] = 286331153;
        iArr[25] = 286331153;
        iArr[26] = 286331153;
        iArr[27] = 286331153;
        iArr[28] = 286331153;
        iArr[29] = 286331153;
        iArr[30] = 286331153;
        iArr[31] = 286331153;
        int[] iArr2 = new int[6];
        states = iArr2;
        iArr2[0] = 285213456;
        iArr2[1] = 572657937;
        iArr2[2] = 335548706;
        iArr2[3] = 341120533;
        iArr2[4] = 336872468;
        iArr2[5] = 36;
        charset = "HZ-GB-2312";
        stFactor = 6;
    }
}
