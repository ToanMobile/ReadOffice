package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsUCS2BEVerifier extends nsVerifier {
    static int[] cclass;
    static String charset;
    static int stFactor;
    static int[] states;

    public boolean isUCS2() {
        return true;
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

    public nsUCS2BEVerifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 0;
        iArr[1] = 2097408;
        iArr[2] = 0;
        iArr[3] = 12288;
        iArr[4] = 0;
        iArr[5] = 3355440;
        iArr[6] = 0;
        iArr[7] = 0;
        iArr[8] = 0;
        iArr[9] = 0;
        iArr[10] = 0;
        iArr[11] = 0;
        iArr[12] = 0;
        iArr[13] = 0;
        iArr[14] = 0;
        iArr[15] = 0;
        iArr[16] = 0;
        iArr[17] = 0;
        iArr[18] = 0;
        iArr[19] = 0;
        iArr[20] = 0;
        iArr[21] = 0;
        iArr[22] = 0;
        iArr[23] = 0;
        iArr[24] = 0;
        iArr[25] = 0;
        iArr[26] = 0;
        iArr[27] = 0;
        iArr[28] = 0;
        iArr[29] = 0;
        iArr[30] = 0;
        iArr[31] = 1409286144;
        int[] iArr2 = new int[7];
        states = iArr2;
        iArr2[0] = 288626549;
        iArr2[1] = 572657937;
        iArr2[2] = 291923490;
        iArr2[3] = 1713792614;
        iArr2[4] = 393569894;
        iArr2[5] = 1717659269;
        iArr2[6] = 1140326;
        charset = "UTF-16BE";
        stFactor = 6;
    }
}
