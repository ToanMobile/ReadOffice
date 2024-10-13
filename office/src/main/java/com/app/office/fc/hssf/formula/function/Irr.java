package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.AggregateFunction;

public final class Irr implements Function {
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (valueEvalArr.length == 0 || valueEvalArr.length > 2) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            double irr = irr(AggregateFunction.ValueCollector.collectValues(valueEvalArr[0]), valueEvalArr.length == 2 ? NumericFunction.singleOperandEvaluate(valueEvalArr[1], i, i2) : 0.1d);
            NumericFunction.checkValue(irr);
            return new NumberEval(irr);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public static double irr(double[] dArr) {
        return irr(dArr, 0.1d);
    }

    public static double irr(double[] dArr, double d) {
        int i = 0;
        while (i < 20) {
            double d2 = 0.0d;
            double d3 = 0.0d;
            int i2 = 0;
            while (i2 < dArr.length) {
                double d4 = 1.0d + d;
                d2 += dArr[i2] / Math.pow(d4, (double) i2);
                i2++;
                d3 += (((double) (-i2)) * dArr[i2]) / Math.pow(d4, (double) i2);
            }
            double d5 = d - (d2 / d3);
            if (Math.abs(d5 - d) <= 1.0E-7d) {
                return d5;
            }
            i++;
            d = d5;
        }
        return Double.NaN;
    }
}
