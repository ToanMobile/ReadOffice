package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public abstract class AggregateFunction extends MultiOperandNumericFunction {
    public static final Function AVEDEV = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            return StatsLib.avedev(dArr);
        }
    };
    public static final Function AVERAGE = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) throws EvaluationException {
            if (dArr.length >= 1) {
                return MathX.average(dArr);
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };
    public static final Function DB = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) throws EvaluationException {
            double[] dArr2 = dArr;
            checkParas(dArr);
            int length = dArr2.length;
            if (length == 4) {
                return db(dArr2[0], dArr2[1], dArr2[2], dArr2[3], 12.0d);
            } else if (length == 5) {
                return db(dArr2[0], dArr2[1], dArr2[2], dArr2[3], dArr2[4]);
            } else {
                throw new EvaluationException(ErrorEval.NA);
            }
        }

        private void checkParas(double[] dArr) throws EvaluationException {
            int length = dArr.length;
            if (length != 4 && length != 5) {
                throw new EvaluationException(ErrorEval.NA);
            } else if (dArr[2] <= 0.0d || dArr[3] <= 0.0d || dArr[3] - dArr[2] > 1.0d) {
                throw new EvaluationException(ErrorEval.NA);
            } else if (length != 5) {
            } else {
                if (dArr[4] > 12.0d || dArr[4] <= 0.0d) {
                    throw new EvaluationException(ErrorEval.NA);
                }
            }
        }

        private double db(double d, double d2, double d3, double d4, double d5) throws EvaluationException {
            double d6 = d4;
            if (Math.abs(d6 - ((double) ((int) d6))) <= 0.001d) {
                double round = ((double) Math.round(((float) (1.0d - Math.pow(d2 / d, 1.0d / d3))) * 1000.0f)) / 1000.0d;
                if (Math.abs(d6 - 1.0d) < 0.001d) {
                    return ((d * round) * d5) / 12.0d;
                }
                double d7 = ((d * round) * d5) / 12.0d;
                double d8 = d - d7;
                int i = 2;
                if (d6 <= d3) {
                    while (((double) i) <= d6) {
                        d7 = d8 * round;
                        d8 -= d7;
                        i++;
                    }
                    return d7;
                } else if (d6 - d3 > 1.0d) {
                    throw new EvaluationException(ErrorEval.NA);
                } else if (Math.abs(d5 - 12.0d) < 0.001d) {
                    return 0.0d;
                } else {
                    while (((double) i) <= d3) {
                        d8 -= d8 * round;
                        i++;
                    }
                    return ((d8 * round) * (12.0d - d5)) / 12.0d;
                }
            } else {
                throw new EvaluationException(ErrorEval.NA);
            }
        }
    };
    public static final Function DDB = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) throws EvaluationException {
            double[] dArr2 = dArr;
            checkParas(dArr);
            int length = dArr2.length;
            if (length == 4) {
                return ddb(dArr2[0], dArr2[1], dArr2[2], dArr2[3], 2.0d);
            } else if (length == 5) {
                return ddb(dArr2[0], dArr2[1], dArr2[2], dArr2[3], dArr2[4]);
            } else {
                throw new EvaluationException(ErrorEval.NA);
            }
        }

        private void checkParas(double[] dArr) throws EvaluationException {
            int length = dArr.length;
            if (length != 4 && length != 5) {
                throw new EvaluationException(ErrorEval.NA);
            } else if (dArr[2] <= 0.0d || dArr[3] <= 0.0d || dArr[3] > dArr[2]) {
                throw new EvaluationException(ErrorEval.NA);
            }
        }

        private double ddb(double d, double d2, double d3, double d4, double d5) throws EvaluationException {
            if (Math.abs(d4 - ((double) ((int) d4))) <= 0.001d) {
                double round = ((double) Math.round(((float) (d5 / d3)) * 1000.0f)) / 1000.0d;
                double min = Math.min(d * round, d - d2);
                for (int i = 2; ((double) i) <= d4; i++) {
                    min = Math.min(d * round, d - d2);
                    d -= min;
                }
                return min;
            }
            throw new EvaluationException(ErrorEval.NA);
        }
    };
    public static final Function DEVSQ = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            return StatsLib.devsq(dArr);
        }
    };
    public static final Function LARGE = new LargeSmall(true);
    public static final Function MAX = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            if (dArr.length > 0) {
                return MathX.max(dArr);
            }
            return 0.0d;
        }
    };
    public static final Function MEDIAN = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            return StatsLib.median(dArr);
        }
    };
    public static final Function MIN = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            if (dArr.length > 0) {
                return MathX.min(dArr);
            }
            return 0.0d;
        }
    };
    public static final Function PRODUCT = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            return MathX.product(dArr);
        }
    };
    public static final Function SMALL = new LargeSmall(false);
    public static final Function STDEV = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) throws EvaluationException {
            if (dArr.length >= 1) {
                return StatsLib.stdev(dArr);
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };
    public static final Function SUM = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            return MathX.sum(dArr);
        }
    };
    public static final Function SUMSQ = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            return MathX.sumsq(dArr);
        }
    };
    public static final Function VAR = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) throws EvaluationException {
            if (dArr.length >= 1) {
                return StatsLib.var(dArr);
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };
    public static final Function VARP = new AggregateFunction() {
        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) throws EvaluationException {
            if (dArr.length >= 1) {
                return StatsLib.varp(dArr);
            }
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    };

    private static final class LargeSmall extends Fixed2ArgFunction {
        private final boolean _isLarge;

        protected LargeSmall(boolean z) {
            this._isLarge = z;
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                double coerceValueToDouble = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval2, i, i2));
                if (coerceValueToDouble < 1.0d) {
                    return ErrorEval.NUM_ERROR;
                }
                int ceil = (int) Math.ceil(coerceValueToDouble);
                try {
                    double[] collectValues = ValueCollector.collectValues(valueEval);
                    if (ceil > collectValues.length) {
                        return ErrorEval.NUM_ERROR;
                    }
                    double kthLargest = this._isLarge ? StatsLib.kthLargest(collectValues, ceil) : StatsLib.kthSmallest(collectValues, ceil);
                    NumericFunction.checkValue(kthLargest);
                    return new NumberEval(kthLargest);
                } catch (EvaluationException e) {
                    return e.getErrorEval();
                }
            } catch (EvaluationException unused) {
                return ErrorEval.VALUE_INVALID;
            }
        }
    }

    static final class ValueCollector extends MultiOperandNumericFunction {
        private static final ValueCollector instance = new ValueCollector();

        public ValueCollector() {
            super(false, false);
        }

        public static double[] collectValues(ValueEval... valueEvalArr) throws EvaluationException {
            return instance.getNumberArray(valueEvalArr);
        }

        /* access modifiers changed from: protected */
        public double evaluate(double[] dArr) {
            throw new IllegalStateException("should not be called");
        }
    }

    protected AggregateFunction() {
        super(false, false);
    }

    static Function subtotalInstance(Function function) {
        return new AggregateFunction() {
            public boolean isSubtotalCounted() {
                return false;
            }

            /* access modifiers changed from: protected */
            public double evaluate(double[] dArr) throws EvaluationException {
                return AggregateFunction.this.evaluate(dArr);
            }
        };
    }
}
