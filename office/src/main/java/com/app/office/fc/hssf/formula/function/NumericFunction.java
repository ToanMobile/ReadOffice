package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public abstract class NumericFunction implements Function {
    public static final Function ABS = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.abs(d);
        }
    };
    public static final Function ACOS = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.acos(d);
        }
    };
    public static final Function ACOSH = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return MathX.acosh(d);
        }
    };
    public static final Function ASIN = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.asin(d);
        }
    };
    public static final Function ASINH = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return MathX.asinh(d);
        }
    };
    public static final Function ATAN = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.atan(d);
        }
    };
    public static final Function ATAN2 = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) throws EvaluationException {
            if (d != NumericFunction.LOG_10_TO_BASE_e || d2 != NumericFunction.LOG_10_TO_BASE_e) {
                return Math.atan2(d2, d);
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };
    public static final Function ATANH = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return MathX.atanh(d);
        }
    };
    public static final Function CEILING = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return MathX.ceiling(d, d2);
        }
    };
    public static final Function COMBIN = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) throws EvaluationException {
            if (d <= 2.147483647E9d && d2 <= 2.147483647E9d) {
                return MathX.nChooseK((int) d, (int) d2);
            }
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    };
    public static final Function COS = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.cos(d);
        }
    };
    public static final Function COSH = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return MathX.cosh(d);
        }
    };
    public static final Function DEGREES = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.toDegrees(d);
        }
    };
    public static final Function DOLLAR = new Var1or2ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            return evaluate(i, i2, valueEval, NumericFunction.DOLLAR_ARG2_DEFAULT);
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                double singleOperandEvaluate = NumericFunction.singleOperandEvaluate(valueEval, i, i2);
                if (((int) NumericFunction.singleOperandEvaluate(valueEval2, i, i2)) > 127) {
                    return ErrorEval.VALUE_INVALID;
                }
                return new NumberEval(singleOperandEvaluate);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    static final NumberEval DOLLAR_ARG2_DEFAULT = new NumberEval(2.0d);
    public static final Function EXP = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.pow(2.718281828459045d, d);
        }
    };
    public static final Function FACT = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return MathX.factorial((int) d);
        }
    };
    public static final Function FLOOR = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) throws EvaluationException {
            if (d2 != NumericFunction.LOG_10_TO_BASE_e) {
                return MathX.floor(d, d2);
            }
            if (d == NumericFunction.LOG_10_TO_BASE_e) {
                return NumericFunction.LOG_10_TO_BASE_e;
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };
    public static final Function INT = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return (double) Math.round(d - 0.5d);
        }
    };
    public static final Function LN = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.log(d);
        }
    };
    public static final Function LOG = new Log();
    public static final Function LOG10 = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.log(d) / NumericFunction.LOG_10_TO_BASE_e;
        }
    };
    static final double LOG_10_TO_BASE_e = Math.log(TEN);
    public static final Function MOD = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) throws EvaluationException {
            if (d2 != NumericFunction.LOG_10_TO_BASE_e) {
                return MathX.mod(d, d2);
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };
    public static final Function PI = new Fixed0ArgFunction() {
        public ValueEval evaluate(int i, int i2) {
            return NumericFunction.PI_EVAL;
        }
    };
    static final NumberEval PI_EVAL = new NumberEval(3.141592653589793d);
    public static final Function POISSON = new Fixed3ArgFunction() {
        private static final double DEFAULT_RETURN_RESULT = 1.0d;
        private final long[] FACTORIALS = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};

        private boolean isDefaultResult(double d, double d2) {
            return d == NumericFunction.LOG_10_TO_BASE_e && d2 == NumericFunction.LOG_10_TO_BASE_e;
        }

        private boolean checkArgument(double d) throws EvaluationException {
            NumericFunction.checkValue(d);
            if (d >= NumericFunction.LOG_10_TO_BASE_e) {
                return true;
            }
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }

        private double probability(int i, double d) {
            return (Math.pow(d, (double) i) * Math.exp(-d)) / ((double) factorial(i));
        }

        private double cumulativeProbability(int i, double d) {
            double d2 = NumericFunction.LOG_10_TO_BASE_e;
            for (int i2 = 0; i2 <= i; i2++) {
                d2 += probability(i2, d);
            }
            return d2;
        }

        public long factorial(int i) {
            if (i >= 0 && i <= 20) {
                return this.FACTORIALS[i];
            }
            throw new IllegalArgumentException("Valid argument should be in the range [0..20]");
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
            double d;
            boolean booleanValue = ((BoolEval) valueEval3).getBooleanValue();
            try {
                double singleOperandEvaluate = NumericFunction.singleOperandEvaluate(valueEval, i, i2);
                double singleOperandEvaluate2 = NumericFunction.singleOperandEvaluate(valueEval2, i, i2);
                if (isDefaultResult(singleOperandEvaluate, singleOperandEvaluate2)) {
                    return new NumberEval((double) DEFAULT_RETURN_RESULT);
                }
                checkArgument(singleOperandEvaluate);
                checkArgument(singleOperandEvaluate2);
                if (booleanValue) {
                    d = cumulativeProbability((int) singleOperandEvaluate, singleOperandEvaluate2);
                } else {
                    d = probability((int) singleOperandEvaluate, singleOperandEvaluate2);
                }
                NumericFunction.checkValue(d);
                return new NumberEval(d);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function POWER = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return Math.pow(d, d2);
        }
    };
    public static final Function RADIANS = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.toRadians(d);
        }
    };
    public static final Function RAND = new Fixed0ArgFunction() {
        public ValueEval evaluate(int i, int i2) {
            return new NumberEval(Math.random());
        }
    };
    public static final Function ROUND = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return MathX.round(d, (int) d2);
        }
    };
    public static final Function ROUNDDOWN = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return MathX.roundDown(d, (int) d2);
        }
    };
    public static final Function ROUNDUP = new TwoArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d, double d2) {
            return MathX.roundUp(d, (int) d2);
        }
    };
    public static final Function SIGN = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return (double) MathX.sign(d);
        }
    };
    public static final Function SIN = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.sin(d);
        }
    };
    public static final Function SINH = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return MathX.sinh(d);
        }
    };
    public static final Function SQRT = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.sqrt(d);
        }
    };
    public static final Function TAN = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return Math.tan(d);
        }
    };
    public static final Function TANH = new OneArg() {
        /* access modifiers changed from: protected */
        public double evaluate(double d) {
            return MathX.tanh(d);
        }
    };
    static final double TEN = 10.0d;
    public static final Function TRUNC = new Var1or2ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            return evaluate(i, i2, valueEval, NumericFunction.TRUNC_ARG2_DEFAULT);
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                double singleOperandEvaluate = NumericFunction.singleOperandEvaluate(valueEval, i, i2);
                double pow = Math.pow(NumericFunction.TEN, NumericFunction.singleOperandEvaluate(valueEval2, i, i2));
                double floor = Math.floor(singleOperandEvaluate * pow) / pow;
                NumericFunction.checkValue(floor);
                return new NumberEval(floor);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    static final NumberEval TRUNC_ARG2_DEFAULT = new NumberEval((double) LOG_10_TO_BASE_e);
    static final double ZERO = 0.0d;

    /* access modifiers changed from: protected */
    public abstract double eval(ValueEval[] valueEvalArr, int i, int i2) throws EvaluationException;

    protected static final double singleOperandEvaluate(ValueEval valueEval, int i, int i2) throws EvaluationException {
        if (valueEval != null) {
            double coerceValueToDouble = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, i2));
            checkValue(coerceValueToDouble);
            return coerceValueToDouble;
        }
        throw new IllegalArgumentException("arg must not be null");
    }

    public static final void checkValue(double d) throws EvaluationException {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    }

    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        try {
            double eval = eval(valueEvalArr, i, i2);
            checkValue(eval);
            return new NumberEval(eval);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public static abstract class OneArg extends Fixed1ArgFunction {
        /* access modifiers changed from: protected */
        public abstract double evaluate(double d) throws EvaluationException;

        protected OneArg() {
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            try {
                double evaluate = evaluate(NumericFunction.singleOperandEvaluate(valueEval, i, i2));
                NumericFunction.checkValue(evaluate);
                return new NumberEval(evaluate);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }

        /* access modifiers changed from: protected */
        public final double eval(ValueEval[] valueEvalArr, int i, int i2) throws EvaluationException {
            if (valueEvalArr.length == 1) {
                return evaluate(NumericFunction.singleOperandEvaluate(valueEvalArr[0], i, i2));
            }
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
    }

    public static abstract class TwoArg extends Fixed2ArgFunction {
        /* access modifiers changed from: protected */
        public abstract double evaluate(double d, double d2) throws EvaluationException;

        protected TwoArg() {
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                double evaluate = evaluate(NumericFunction.singleOperandEvaluate(valueEval, i, i2), NumericFunction.singleOperandEvaluate(valueEval2, i, i2));
                NumericFunction.checkValue(evaluate);
                return new NumberEval(evaluate);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    }

    private static final class Log extends Var1or2ArgFunction {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            try {
                double log = Math.log(NumericFunction.singleOperandEvaluate(valueEval, i, i2)) / NumericFunction.LOG_10_TO_BASE_e;
                NumericFunction.checkValue(log);
                return new NumberEval(log);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                double singleOperandEvaluate = NumericFunction.singleOperandEvaluate(valueEval, i, i2);
                double singleOperandEvaluate2 = NumericFunction.singleOperandEvaluate(valueEval2, i, i2);
                double log = Math.log(singleOperandEvaluate);
                if (singleOperandEvaluate2 != 2.718281828459045d) {
                    log /= Math.log(singleOperandEvaluate2);
                }
                NumericFunction.checkValue(log);
                return new NumberEval(log);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    }
}
