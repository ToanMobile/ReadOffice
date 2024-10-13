package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public abstract class BooleanFunction implements Function {
    public static final Function AND = new BooleanFunction() {
        /* access modifiers changed from: protected */
        public boolean getInitialResultValue() {
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean partialEvaluate(boolean z, boolean z2) {
            return z && z2;
        }
    };
    public static final Function FALSE = new Fixed0ArgFunction() {
        public ValueEval evaluate(int i, int i2) {
            return BoolEval.FALSE;
        }
    };
    public static final Function NOT = new Fixed1ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            try {
                ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
                boolean z = false;
                Boolean coerceValueToBoolean = OperandResolver.coerceValueToBoolean(singleValue, false);
                if (coerceValueToBoolean != null) {
                    z = coerceValueToBoolean.booleanValue();
                }
                return BoolEval.valueOf(!z);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function OR = new BooleanFunction() {
        /* access modifiers changed from: protected */
        public boolean getInitialResultValue() {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean partialEvaluate(boolean z, boolean z2) {
            return z || z2;
        }
    };
    public static final Function TRUE = new Fixed0ArgFunction() {
        public ValueEval evaluate(int i, int i2) {
            return BoolEval.TRUE;
        }
    };

    /* access modifiers changed from: protected */
    public abstract boolean getInitialResultValue();

    /* access modifiers changed from: protected */
    public abstract boolean partialEvaluate(boolean z, boolean z2);

    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (valueEvalArr.length < 1) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            return BoolEval.valueOf(calculate(valueEvalArr));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private boolean calculate(ValueEval[] valueEvalArr) throws EvaluationException {
        Boolean bool;
        boolean initialResultValue = getInitialResultValue();
        boolean z = false;
        for (TwoDEval twoDEval : valueEvalArr) {
            if (twoDEval instanceof TwoDEval) {
                TwoDEval twoDEval2 = twoDEval;
                int height = twoDEval2.getHeight();
                int width = twoDEval2.getWidth();
                for (int i = 0; i < height; i++) {
                    for (int i2 = 0; i2 < width; i2++) {
                        Boolean coerceValueToBoolean = OperandResolver.coerceValueToBoolean(twoDEval2.getValue(i, i2), true);
                        if (coerceValueToBoolean != null) {
                            initialResultValue = partialEvaluate(initialResultValue, coerceValueToBoolean.booleanValue());
                            z = true;
                        }
                    }
                }
            } else {
                if (twoDEval instanceof RefEval) {
                    bool = OperandResolver.coerceValueToBoolean(((RefEval) twoDEval).getInnerValueEval(), true);
                } else {
                    bool = OperandResolver.coerceValueToBoolean(twoDEval, false);
                }
                if (bool != null) {
                    initialResultValue = partialEvaluate(initialResultValue, bool.booleanValue());
                    z = true;
                }
            }
        }
        if (z) {
            return initialResultValue;
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }
}
