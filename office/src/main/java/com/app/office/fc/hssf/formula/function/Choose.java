package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.MissingArgEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Choose implements Function {
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (valueEvalArr.length < 2) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            int evaluateFirstArg = evaluateFirstArg(valueEvalArr[0], i, i2);
            if (evaluateFirstArg >= 1) {
                if (evaluateFirstArg < valueEvalArr.length) {
                    ValueEval singleValue = OperandResolver.getSingleValue(valueEvalArr[evaluateFirstArg], i, i2);
                    return singleValue == MissingArgEval.instance ? BlankEval.instance : singleValue;
                }
            }
            return ErrorEval.VALUE_INVALID;
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public static int evaluateFirstArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, i2));
    }
}
