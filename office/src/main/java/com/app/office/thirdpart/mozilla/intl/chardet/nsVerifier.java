package com.app.office.thirdpart.mozilla.intl.chardet;

public abstract class nsVerifier {
    static final int eBitSft4bits = 2;
    static final byte eError = 1;
    static final byte eItsMe = 2;
    static final int eSftMsk4bits = 7;
    static final byte eStart = 0;
    static final int eUnitMsk4bits = 15;
    static final int eidxSft4bits = 3;

    public abstract int[] cclass();

    public abstract String charset();

    public abstract boolean isUCS2();

    public abstract int stFactor();

    public abstract int[] states();

    nsVerifier() {
    }

    public static byte getNextState(nsVerifier nsverifier, byte b, byte b2) {
        int i = (b & 255) >> 3;
        int i2 = (b & 7) << 2;
        return (byte) ((nsverifier.states()[(((nsverifier.stFactor() * b2) + ((nsverifier.cclass()[i] >> i2) & 15)) & 255) >> 3] >> (((((b2 * nsverifier.stFactor()) + ((nsverifier.cclass()[i] >> i2) & 15)) & 255) & 7) << 2)) & 15 & 255);
    }
}
