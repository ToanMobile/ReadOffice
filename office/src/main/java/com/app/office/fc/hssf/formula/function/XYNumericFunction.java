package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.LookupUtils;

public abstract class XYNumericFunction extends Fixed2ArgFunction {

    protected interface Accumulator {
        double accumulate(double d, double d2);
    }

    /* access modifiers changed from: protected */
    public abstract Accumulator createAccumulator();

    private static abstract class ValueArray implements LookupUtils.ValueVector {
        private final int _size;

        /* access modifiers changed from: protected */
        public abstract ValueEval getItemInternal(int i);

        protected ValueArray(int i) {
            this._size = i;
        }

        public ValueEval getItem(int i) {
            if (i >= 0 && i <= this._size) {
                return getItemInternal(i);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Specified index ");
            sb.append(i);
            sb.append(" is outside range (0..");
            sb.append(this._size - 1);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }

        public final int getSize() {
            return this._size;
        }
    }

    private static final class SingleCellValueArray extends ValueArray {
        private final ValueEval _value;

        public SingleCellValueArray(ValueEval valueEval) {
            super(1);
            this._value = valueEval;
        }

        /* access modifiers changed from: protected */
        public ValueEval getItemInternal(int i) {
            return this._value;
        }
    }

    private static final class RefValueArray extends ValueArray {
        private final RefEval _ref;

        public RefValueArray(RefEval refEval) {
            super(1);
            this._ref = refEval;
        }

        /* access modifiers changed from: protected */
        public ValueEval getItemInternal(int i) {
            return this._ref.getInnerValueEval();
        }
    }

    private static final class AreaValueArray extends ValueArray {
        private final TwoDEval _ae;
        private final int _width;

        public AreaValueArray(TwoDEval twoDEval) {
            super(twoDEval.getWidth() * twoDEval.getHeight());
            this._ae = twoDEval;
            this._width = twoDEval.getWidth();
        }

        /* access modifiers changed from: protected */
        public ValueEval getItemInternal(int i) {
            int i2 = this._width;
            return this._ae.getValue(i / i2, i % i2);
        }
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        try {
            LookupUtils.ValueVector createValueVector = createValueVector(valueEval);
            LookupUtils.ValueVector createValueVector2 = createValueVector(valueEval2);
            int size = createValueVector.getSize();
            if (size != 0) {
                if (createValueVector2.getSize() == size) {
                    double evaluateInternal = evaluateInternal(createValueVector, createValueVector2, size);
                    if (Double.isNaN(evaluateInternal) || Double.isInfinite(evaluateInternal)) {
                        return ErrorEval.NUM_ERROR;
                    }
                    return new NumberEval(evaluateInternal);
                }
            }
            return ErrorEval.NA;
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private double evaluateInternal(LookupUtils.ValueVector valueVector, LookupUtils.ValueVector valueVector2, int i) throws EvaluationException {
        Accumulator createAccumulator = createAccumulator();
        double d = 0.0d;
        ErrorEval errorEval = null;
        ErrorEval errorEval2 = null;
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            ValueEval item = valueVector.getItem(i2);
            ValueEval item2 = valueVector2.getItem(i2);
            if ((item instanceof ErrorEval) && errorEval == null) {
                errorEval = (ErrorEval) item;
            } else if ((item2 instanceof ErrorEval) && errorEval2 == null) {
                errorEval2 = (ErrorEval) item2;
            } else if ((item instanceof NumberEval) && (item2 instanceof NumberEval)) {
                d += createAccumulator.accumulate(((NumberEval) item).getNumberValue(), ((NumberEval) item2).getNumberValue());
                z = true;
            }
        }
        if (errorEval != null) {
            throw new EvaluationException(errorEval);
        } else if (errorEval2 != null) {
            throw new EvaluationException(errorEval2);
        } else if (z) {
            return d;
        } else {
            throw new EvaluationException(ErrorEval.DIV_ZERO);
        }
    }

    private static LookupUtils.ValueVector createValueVector(ValueEval valueEval) throws EvaluationException {
        if (valueEval instanceof ErrorEval) {
            throw new EvaluationException((ErrorEval) valueEval);
        } else if (valueEval instanceof TwoDEval) {
            return new AreaValueArray((TwoDEval) valueEval);
        } else {
            if (valueEval instanceof RefEval) {
                return new RefValueArray((RefEval) valueEval);
            }
            return new SingleCellValueArray(valueEval);
        }
    }
}
