package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.NumericValueEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.LookupUtils;

public final class Match extends Var2or3ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        return eval(i, i2, valueEval, valueEval2, 1.0d);
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            return eval(i, i2, valueEval, valueEval2, evaluateMatchTypeArg(valueEval3, i, i2));
        } catch (EvaluationException unused) {
            return ErrorEval.REF_INVALID;
        }
    }

    private static ValueEval eval(int i, int i2, ValueEval valueEval, ValueEval valueEval2, double d) {
        boolean z = false;
        int i3 = (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1));
        boolean z2 = i3 == 0;
        if (i3 > 0) {
            z = true;
        }
        try {
            return new NumberEval((double) (findIndexOfValue(OperandResolver.getSingleValue(valueEval, i, i2), evaluateLookupRange(valueEval2), z2, z) + 1));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static final class SingleValueVector implements LookupUtils.ValueVector {
        private final ValueEval _value;

        public int getSize() {
            return 1;
        }

        public SingleValueVector(ValueEval valueEval) {
            this._value = valueEval;
        }

        public ValueEval getItem(int i) {
            if (i == 0) {
                return this._value;
            }
            throw new RuntimeException("Invalid index (" + i + ") only zero is allowed");
        }
    }

    private static LookupUtils.ValueVector evaluateLookupRange(ValueEval valueEval) throws EvaluationException {
        if (valueEval instanceof RefEval) {
            return new SingleValueVector(((RefEval) valueEval).getInnerValueEval());
        }
        if (valueEval instanceof TwoDEval) {
            LookupUtils.ValueVector createVector = LookupUtils.createVector((TwoDEval) valueEval);
            if (createVector != null) {
                return createVector;
            }
            throw new EvaluationException(ErrorEval.NA);
        } else if (valueEval instanceof NumericValueEval) {
            throw new EvaluationException(ErrorEval.NA);
        } else if (!(valueEval instanceof StringEval)) {
            throw new RuntimeException("Unexpected eval type (" + valueEval.getClass().getName() + ")");
        } else if (OperandResolver.parseDouble(((StringEval) valueEval).getStringValue()) == null) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        } else {
            throw new EvaluationException(ErrorEval.NA);
        }
    }

    private static double evaluateMatchTypeArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
        if (singleValue instanceof ErrorEval) {
            throw new EvaluationException((ErrorEval) singleValue);
        } else if (singleValue instanceof NumericValueEval) {
            return ((NumericValueEval) singleValue).getNumberValue();
        } else {
            if (singleValue instanceof StringEval) {
                Double parseDouble = OperandResolver.parseDouble(((StringEval) singleValue).getStringValue());
                if (parseDouble != null) {
                    return parseDouble.doubleValue();
                }
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
            throw new RuntimeException("Unexpected match_type type (" + singleValue.getClass().getName() + ")");
        }
    }

    private static int findIndexOfValue(ValueEval valueEval, LookupUtils.ValueVector valueVector, boolean z, boolean z2) throws EvaluationException {
        LookupUtils.LookupValueComparer createLookupComparer = createLookupComparer(valueEval, z);
        int size = valueVector.getSize();
        int i = 0;
        if (z) {
            while (i < size) {
                if (createLookupComparer.compareTo(valueVector.getItem(i)).isEqual()) {
                    return i;
                }
                i++;
            }
            throw new EvaluationException(ErrorEval.NA);
        } else if (z2) {
            for (int i2 = size - 1; i2 >= 0; i2--) {
                LookupUtils.CompareResult compareTo = createLookupComparer.compareTo(valueVector.getItem(i2));
                if (!compareTo.isTypeMismatch() && !compareTo.isLessThan()) {
                    return i2;
                }
            }
            throw new EvaluationException(ErrorEval.NA);
        } else {
            while (i < size) {
                LookupUtils.CompareResult compareTo2 = createLookupComparer.compareTo(valueVector.getItem(i));
                if (compareTo2.isEqual()) {
                    return i;
                }
                if (!compareTo2.isGreaterThan()) {
                    i++;
                } else if (i >= 1) {
                    return i - 1;
                } else {
                    throw new EvaluationException(ErrorEval.NA);
                }
            }
            throw new EvaluationException(ErrorEval.NA);
        }
    }

    private static LookupUtils.LookupValueComparer createLookupComparer(ValueEval valueEval, boolean z) {
        if (z && (valueEval instanceof StringEval)) {
            String stringValue = ((StringEval) valueEval).getStringValue();
            if (isLookupValueWild(stringValue)) {
                throw new RuntimeException("Wildcard lookup values '" + stringValue + "' not supported yet");
            }
        }
        return LookupUtils.createLookupComparer(valueEval);
    }

    private static boolean isLookupValueWild(String str) {
        return str.indexOf(63) >= 0 || str.indexOf(42) >= 0;
    }
}
