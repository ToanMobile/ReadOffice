package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.function.NumericFunction;

public final class Even extends NumericFunction.OneArg {
    private static final long PARITY_MASK = -2;

    private static long calcEven(double d) {
        long j = ((long) d) & -2;
        return ((double) j) == d ? j : j + 2;
    }

    /* access modifiers changed from: protected */
    public double evaluate(double d) {
        long j;
        int i = (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1));
        if (i == 0) {
            return 0.0d;
        }
        if (i > 0) {
            j = calcEven(d);
        } else {
            j = -calcEven(-d);
        }
        return (double) j;
    }
}
