package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.MissingArgEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class TimeFunc extends Fixed3ArgFunction {
    private static final int HOURS_PER_DAY = 24;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            return new NumberEval(evaluate(evalArg(valueEval, i, i2), evalArg(valueEval2, i, i2), evalArg(valueEval3, i, i2)));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static int evalArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        if (valueEval == MissingArgEval.instance) {
            return 0;
        }
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    private static double evaluate(int i, int i2, int i3) throws EvaluationException {
        if (i > 32767 || i2 > 32767 || i3 > 32767) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
        int i4 = (i * SECONDS_PER_HOUR) + (i2 * 60) + i3;
        if (i4 >= 0) {
            return ((double) (i4 % SECONDS_PER_DAY)) / 86400.0d;
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }
}
