package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

abstract class Var3or4ArgFunction implements Function3Arg, Function4Arg {
    Var3or4ArgFunction() {
    }

    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length;
        if (length == 3) {
            return evaluate(i, i2, valueEvalArr[0], valueEvalArr[1], valueEvalArr[2]);
        } else if (length != 4) {
            return ErrorEval.VALUE_INVALID;
        } else {
            return evaluate(i, i2, valueEvalArr[0], valueEvalArr[1], valueEvalArr[2], valueEvalArr[3]);
        }
    }
}
