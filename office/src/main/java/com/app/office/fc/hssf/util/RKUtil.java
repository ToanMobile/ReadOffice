package com.app.office.fc.hssf.util;

public final class RKUtil {
    private RKUtil() {
    }

    public static double decodeNumber(int i) {
        long j = ((long) i) >> 2;
        double longBitsToDouble = (i & 2) == 2 ? (double) j : Double.longBitsToDouble(j << 34);
        return (i & 1) == 1 ? longBitsToDouble / 100.0d : longBitsToDouble;
    }
}
