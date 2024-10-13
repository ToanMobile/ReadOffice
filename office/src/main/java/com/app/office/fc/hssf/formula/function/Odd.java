package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.function.NumericFunction;

public final class Odd extends NumericFunction.OneArg {
    private static final long PARITY_MASK = -2;

    private static long calcOdd(double d) {
        double d2 = d + 1.0d;
        long j = ((long) d2) & -2;
        return ((double) j) == d2 ? j - 1 : j + 1;
    }

    /* access modifiers changed from: protected */
    public double evaluate(double d) {
        long j;
        int i = (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1));
        if (i == 0) {
            return 1.0d;
        }
        if (i > 0) {
            j = calcOdd(d);
        } else {
            j = -calcOdd(-d);
        }
        return (double) j;
    }
}
