package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsEUCJPVerifier extends nsVerifier {
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

    public nsEUCJPVerifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 1145324612;
        iArr[1] = 1430537284;
        iArr[2] = 1145324612;
        iArr[3] = 1145328708;
        iArr[4] = 1145324612;
        iArr[5] = 1145324612;
        iArr[6] = 1145324612;
        iArr[7] = 1145324612;
        iArr[8] = 1145324612;
        iArr[9] = 1145324612;
        iArr[10] = 1145324612;
        iArr[11] = 1145324612;
        iArr[12] = 1145324612;
        iArr[13] = 1145324612;
        iArr[14] = 1145324612;
        iArr[15] = 1145324612;
        iArr[16] = 1431655765;
        iArr[17] = 827675989;
        iArr[18] = 1431655765;
        iArr[19] = 1431655765;
        iArr[20] = 572662309;
        iArr[21] = 572662306;
        iArr[22] = 572662306;
        iArr[23] = 572662306;
        iArr[24] = 572662306;
        iArr[25] = 572662306;
        iArr[26] = 572662306;
        iArr[27] = 572662306;
        iArr[28] = 0;
        iArr[29] = 0;
        iArr[30] = 0;
        iArr[31] = 1342177280;
        int[] iArr2 = new int[5];
        states = iArr2;
        iArr2[0] = 286282563;
        iArr2[1] = 572657937;
        iArr2[2] = 286265378;
        iArr2[3] = 319885329;
        iArr2[4] = 4371;
        charset = "EUC-JP";
        stFactor = 6;
    }
}
