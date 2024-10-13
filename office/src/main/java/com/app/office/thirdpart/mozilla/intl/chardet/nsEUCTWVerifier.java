package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsEUCTWVerifier extends nsVerifier {
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

    public nsEUCTWVerifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 572662306;
        iArr[1] = 2236962;
        iArr[2] = 572662306;
        iArr[3] = 572654114;
        iArr[4] = 572662306;
        iArr[5] = 572662306;
        iArr[6] = 572662306;
        iArr[7] = 572662306;
        iArr[8] = 572662306;
        iArr[9] = 572662306;
        iArr[10] = 572662306;
        iArr[11] = 572662306;
        iArr[12] = 572662306;
        iArr[13] = 572662306;
        iArr[14] = 572662306;
        iArr[15] = 572662306;
        iArr[16] = 0;
        iArr[17] = 100663296;
        iArr[18] = 0;
        iArr[19] = 0;
        iArr[20] = 1145324592;
        iArr[21] = 286331221;
        iArr[22] = 286331153;
        iArr[23] = 286331153;
        iArr[24] = 858985233;
        iArr[25] = 858993459;
        iArr[26] = 858993459;
        iArr[27] = 858993459;
        iArr[28] = 858993459;
        iArr[29] = 858993459;
        iArr[30] = 858993459;
        iArr[31] = 53687091;
        int[] iArr2 = new int[6];
        states = iArr2;
        iArr2[0] = 338898961;
        iArr2[1] = 571543825;
        iArr2[2] = 269623842;
        iArr2[3] = 286330880;
        iArr2[4] = 1052949;
        iArr2[5] = 16;
        charset = "x-euc-tw";
        stFactor = 7;
    }
}
