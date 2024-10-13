package com.app.office.fc.hssf.formula.function;

public final class FinanceLib {
    private FinanceLib() {
    }

    public static double fv(double d, double d2, double d3, double d4, boolean z) {
        if (d == 0.0d) {
            return (d4 + (d2 * d3)) * -1.0d;
        }
        double d5 = 1.0d;
        double d6 = d + 1.0d;
        double pow = 1.0d - Math.pow(d6, d2);
        if (z) {
            d5 = d6;
        }
        return (((pow * d5) * d3) / d) - (d4 * Math.pow(d6, d2));
    }

    public static double pv(double d, double d2, double d3, double d4, boolean z) {
        if (d == 0.0d) {
            return ((d2 * d3) + d4) * -1.0d;
        }
        double d5 = 1.0d;
        double d6 = d + 1.0d;
        double pow = (1.0d - Math.pow(d6, d2)) / d;
        if (z) {
            d5 = d6;
        }
        return (((pow * d5) * d3) - d4) / Math.pow(d6, d2);
    }

    public static double npv(double d, double[] dArr) {
        double d2 = d + 1.0d;
        double d3 = 0.0d;
        double d4 = d2;
        for (double d5 : dArr) {
            d3 += d5 / d4;
            d4 *= d2;
        }
        return d3;
    }

    public static double pmt(double d, double d2, double d3, double d4, boolean z) {
        if (d == 0.0d) {
            return ((d4 + d3) * -1.0d) / d2;
        }
        double d5 = d + 1.0d;
        return ((d4 + (d3 * Math.pow(d5, d2))) * d) / ((z ? d5 : 1.0d) * (1.0d - Math.pow(d5, d2)));
    }

    public static double nper(double d, double d2, double d3, double d4, boolean z) {
        double d5;
        double d6;
        if (d == 0.0d) {
            return ((d4 + d3) * -1.0d) / d2;
        }
        double d7 = 1.0d;
        double d8 = d + 1.0d;
        if (z) {
            d7 = d8;
        }
        double d9 = (d7 * d2) / d;
        double d10 = d9 - d4;
        int i = (d10 > 0.0d ? 1 : (d10 == 0.0d ? 0 : -1));
        if (i < 0) {
            d5 = Math.log(d4 - d9);
        } else {
            d5 = Math.log(d10);
        }
        if (i < 0) {
            d6 = Math.log((-d3) - d9);
        } else {
            d6 = Math.log(d3 + d9);
        }
        return (d5 - d6) / Math.log(d8);
    }
}
