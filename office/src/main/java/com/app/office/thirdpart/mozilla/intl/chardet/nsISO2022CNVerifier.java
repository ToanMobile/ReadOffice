package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsISO2022CNVerifier extends nsVerifier {
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

    public nsISO2022CNVerifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 2;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 4096;
        iArr[4] = 0;
        iArr[5] = 48;
        iArr[6] = 0;
        iArr[7] = 0;
        iArr[8] = 16384;
        iArr[9] = 0;
        iArr[10] = 0;
        iArr[11] = 0;
        iArr[12] = 0;
        iArr[13] = 0;
        iArr[14] = 0;
        iArr[15] = 0;
        iArr[16] = 572662306;
        iArr[17] = 572662306;
        iArr[18] = 572662306;
        iArr[19] = 572662306;
        iArr[20] = 572662306;
        iArr[21] = 572662306;
        iArr[22] = 572662306;
        iArr[23] = 572662306;
        iArr[24] = 572662306;
        iArr[25] = 572662306;
        iArr[26] = 572662306;
        iArr[27] = 572662306;
        iArr[28] = 572662306;
        iArr[29] = 572662306;
        iArr[30] = 572662306;
        iArr[31] = 572662306;
        int[] iArr2 = new int[8];
        states = iArr2;
        iArr2[0] = 304;
        iArr2[1] = 286331152;
        iArr2[2] = 572662289;
        iArr2[3] = 336663074;
        iArr2[4] = 286335249;
        iArr2[5] = 286331237;
        iArr2[6] = 286335249;
        iArr2[7] = 18944273;
        charset = "ISO-2022-CN";
        stFactor = 9;
    }
}
