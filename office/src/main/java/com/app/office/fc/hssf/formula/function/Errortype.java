package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Errortype extends Fixed1ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        try {
            OperandResolver.getSingleValue(valueEval, i, i2);
            return ErrorEval.NA;
        } catch (EvaluationException e) {
            return new NumberEval((double) translateErrorCodeToErrorTypeValue(e.getErrorEval().getErrorCode()));
        }
    }

    private int translateErrorCodeToErrorTypeValue(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 7) {
            return 2;
        }
        if (i == 15) {
            return 3;
        }
        if (i == 23) {
            return 4;
        }
        if (i == 29) {
            return 5;
        }
        if (i == 36) {
            return 6;
        }
        if (i == 42) {
            return 7;
        }
        throw new IllegalArgumentException("Invalid error code (" + i + ")");
    }
}
