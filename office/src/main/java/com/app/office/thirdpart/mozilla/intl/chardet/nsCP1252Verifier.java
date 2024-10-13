package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsCP1252Verifier extends nsVerifier {
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

    public nsCP1252Verifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 572662305;
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
        iArr[16] = 572662274;
        iArr[17] = 16851234;
        iArr[18] = 572662304;
        iArr[19] = 285286690;
        iArr[20] = 572662306;
        iArr[21] = 572662306;
        iArr[22] = 572662306;
        iArr[23] = 572662306;
        iArr[24] = 286331153;
        iArr[25] = 286331153;
        iArr[26] = 554766609;
        iArr[27] = 286331153;
        iArr[28] = 286331153;
        iArr[29] = 286331153;
        iArr[30] = 554766609;
        iArr[31] = 286331153;
        int[] iArr2 = new int[3];
        states = iArr2;
        iArr2[0] = 571543601;
        iArr2[1] = 340853778;
        iArr2[2] = 65;
        charset = "windows-1252";
        stFactor = 3;
    }
}
