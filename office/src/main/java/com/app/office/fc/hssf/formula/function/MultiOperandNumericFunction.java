package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public abstract class MultiOperandNumericFunction implements Function {
    private static final int DEFAULT_MAX_NUM_OPERANDS = 30;
    static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    private final boolean _isBlankCounted;
    private final boolean _isReferenceBoolCounted;

    /* access modifiers changed from: protected */
    public abstract double evaluate(double[] dArr) throws EvaluationException;

    /* access modifiers changed from: protected */
    public int getMaxNumOperands() {
        return 30;
    }

    public boolean isSubtotalCounted() {
        return true;
    }

    protected MultiOperandNumericFunction(boolean z, boolean z2) {
        this._isReferenceBoolCounted = z;
        this._isBlankCounted = z2;
    }

    private static class DoubleList {
        private double[] _array = new double[8];
        private int _count = 0;

        public double[] toArray() {
            int i = this._count;
            if (i < 1) {
                return MultiOperandNumericFunction.EMPTY_DOUBLE_ARRAY;
            }
            double[] dArr = new double[i];
            System.arraycopy(this._array, 0, dArr, 0, i);
            return dArr;
        }

        private void ensureCapacity(int i) {
            double[] dArr = this._array;
            if (i > dArr.length) {
                double[] dArr2 = new double[((i * 3) / 2)];
                System.arraycopy(dArr, 0, dArr2, 0, this._count);
                this._array = dArr2;
            }
        }

        public void add(double d) {
            ensureCapacity(this._count + 1);
            double[] dArr = this._array;
            int i = this._count;
            dArr[i] = d;
            this._count = i + 1;
        }
    }

    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        try {
            double evaluate = evaluate(getNumberArray(valueEvalArr));
            if (Double.isNaN(evaluate) || Double.isInfinite(evaluate)) {
                return ErrorEval.NUM_ERROR;
            }
            return new NumberEval(evaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    /* access modifiers changed from: protected */
    public final double[] getNumberArray(ValueEval[] valueEvalArr) throws EvaluationException {
        if (valueEvalArr.length <= getMaxNumOperands()) {
            DoubleList doubleList = new DoubleList();
            for (ValueEval collectValues : valueEvalArr) {
                collectValues(collectValues, doubleList);
            }
            return doubleList.toArray();
        }
        throw EvaluationException.invalidValue();
    }

    private void collectValues(ValueEval valueEval, DoubleList doubleList) throws EvaluationException {
        if (valueEval instanceof TwoDEval) {
            TwoDEval twoDEval = (TwoDEval) valueEval;
            int width = twoDEval.getWidth();
            int height = twoDEval.getHeight();
            for (int i = 0; i < height; i++) {
                for (int i2 = 0; i2 < width; i2++) {
                    ValueEval value = twoDEval.getValue(i, i2);
                    if (isSubtotalCounted() || !twoDEval.isSubTotal(i, i2)) {
                        while (value instanceof RefEval) {
                            value = OperandResolver.getSingleValue(value, 0, 0);
                        }
                        collectValue(value, true, doubleList);
                    }
                }
            }
        } else if (valueEval instanceof RefEval) {
            collectValue(((RefEval) valueEval).getInnerValueEval(), true, doubleList);
        } else {
            collectValue(valueEval, false, doubleList);
        }
    }

    private void collectValue(ValueEval valueEval, boolean z, DoubleList doubleList) throws EvaluationException {
        if (valueEval == null) {
            throw new IllegalArgumentException("ve must not be null");
        } else if (valueEval instanceof NumberEval) {
            doubleList.add(((NumberEval) valueEval).getNumberValue());
        } else if (valueEval instanceof ErrorEval) {
            throw new EvaluationException((ErrorEval) valueEval);
        } else if (valueEval instanceof StringEval) {
            if (!z) {
                Double parseDouble = OperandResolver.parseDouble(((StringEval) valueEval).getStringValue());
                if (parseDouble != null) {
                    doubleList.add(parseDouble.doubleValue());
                    return;
                }
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
        } else if (valueEval instanceof BoolEval) {
            if (!z || this._isReferenceBoolCounted) {
                doubleList.add(((BoolEval) valueEval).getNumberValue());
            }
        } else if (valueEval != BlankEval.instance) {
            throw new RuntimeException("Invalid ValueEval type passed for conversion: (" + valueEval.getClass() + ")");
        } else if (this._isBlankCounted) {
            doubleList.add(0.0d);
        }
    }
}
