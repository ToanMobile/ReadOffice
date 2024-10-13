package com.app.office.thirdpart.mozilla.intl.chardet;

public class nsGB18030Verifier extends nsVerifier {
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

    public nsGB18030Verifier() {
        int[] iArr = new int[32];
        cclass = iArr;
        iArr[0] = 286331153;
        iArr[1] = 1118481;
        iArr[2] = 286331153;
        iArr[3] = 286327057;
        iArr[4] = 286331153;
        iArr[5] = 286331153;
        iArr[6] = 858993459;
        iArr[7] = 286331187;
        iArr[8] = 572662306;
        iArr[9] = 572662306;
        iArr[10] = 572662306;
        iArr[11] = 572662306;
        iArr[12] = 572662306;
        iArr[13] = 572662306;
        iArr[14] = 572662306;
        iArr[15] = 1109533218;
        iArr[16] = 1717986917;
        iArr[17] = 1717986918;
        iArr[18] = 1717986918;
        iArr[19] = 1717986918;
        iArr[20] = 1717986918;
        iArr[21] = 1717986918;
        iArr[22] = 1717986918;
        iArr[23] = 1717986918;
        iArr[24] = 1717986918;
        iArr[25] = 1717986918;
        iArr[26] = 1717986918;
        iArr[27] = 1717986918;
        iArr[28] = 1717986918;
        iArr[29] = 1717986918;
        iArr[30] = 1717986918;
        iArr[31] = 107374182;
        int[] iArr2 = new int[6];
        states = iArr2;
        iArr2[0] = 318767105;
        iArr2[1] = 571543825;
        iArr2[2] = 17965602;
        iArr2[3] = 286326804;
        iArr2[4] = 303109393;
        iArr2[5] = 17;
        charset = "GB18030";
        stFactor = 7;
    }
}
