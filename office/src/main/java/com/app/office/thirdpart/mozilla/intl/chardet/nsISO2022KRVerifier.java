package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsISO2022KRVerifier extends nsVerifier {
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

    public nsISO2022KRVerifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 2;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 4096;
        iArr[4] = 196608;
        iArr[5] = 64;
        iArr[6] = 0;
        iArr[7] = 0;
        iArr[8] = 20480;
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
        int[] iArr2 = new int[5];
        states = iArr2;
        iArr2[0] = 285212976;
        iArr2[1] = 572657937;
        iArr2[2] = 289476898;
        iArr2[3] = 286593297;
        iArr2[4] = 8465;
        charset = "ISO-2022-KR";
        stFactor = 6;
    }
}
