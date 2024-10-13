package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public abstract class Fixed0ArgFunction implements Function0Arg {
    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (valueEvalArr.length != 0) {
            return ErrorEval.VALUE_INVALID;
        }
        return evaluate(i, i2);
    }
}
