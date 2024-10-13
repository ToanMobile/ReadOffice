package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.function.XYNumericFunction;

public final class Sumxmy2 extends XYNumericFunction {
    private static final XYNumericFunction.Accumulator XMinusYSquaredAccumulator = new XYNumericFunction.Accumulator() {
        public double accumulate(double d, double d2) {
            double d3 = d - d2;
            return d3 * d3;
        }
    };

    /* access modifiers changed from: protected */
    public XYNumericFunction.Accumulator createAccumulator() {
        return XMinusYSquaredAccumulator;
    }
}
