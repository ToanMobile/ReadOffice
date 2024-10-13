package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.function.Fixed2ArgFunction;
import com.app.office.fc.hssf.formula.function.Function;

public abstract class TwoOperandNumericOperation extends Fixed2ArgFunction {
    public static final Function AddEval = new TwoOperandNumericOperation() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return d + d2;
        }
    };
    public static final Function DivideEval = new TwoOperandNumericOperation() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) throws EvaluationException {
            if (d2 != 0.0d) {
                return d / d2;
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };
    public static final Function MultiplyEval = new TwoOperandNumericOperation() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return d * d2;
        }
    };
    public static final Function PowerEval = new TwoOperandNumericOperation() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return Math.pow(d, d2);
        }
    };
    public static final Function SubtractEval = new SubtractEvalClass();

    private static final class SubtractEvalClass extends TwoOperandNumericOperation {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return d - d2;
        }
    }

    /* access modifiers changed from: protected */
    public abstract double evaluate(double d, double d2) throws EvaluationException;

    /* access modifiers changed from: protected */
    public final double singleOperandEvaluate(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        try {
            double evaluate = evaluate(singleOperandEvaluate(valueEval, i, i2), singleOperandEvaluate(valueEval2, i, i2));
            if (evaluate != 0.0d || (this instanceof SubtractEvalClass)) {
                return (Double.isNaN(evaluate) || Double.isInfinite(evaluate)) ? ErrorEval.NUM_ERROR : new NumberEval(evaluate);
            }
            return NumberEval.ZERO;
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
