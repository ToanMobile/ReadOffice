package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.function.XYNumericFunction;

public final class Sumx2py2 extends XYNumericFunction {
    private static final XYNumericFunction.Accumulator XSquaredPlusYSquaredAccumulator = new XYNumericFunction.Accumulator() {
        public double accumulate(double d, double d2) {
            return (d * d) + (d2 * d2);
        }
    };

    /* access modifiers changed from: protected */
    public XYNumericFunction.Accumulator createAccumulator() {
        return XSquaredPlusYSquaredAccumulator;
    }
}
