package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Column implements Function0Arg, Function1Arg {
    public ValueEval evaluate(int i, int i2) {
        return new NumberEval((double) (i2 + 1));
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        int i3;
        if (valueEval instanceof AreaEval) {
            i3 = ((AreaEval) valueEval).getFirstColumn();
        } else if (!(valueEval instanceof RefEval)) {
            return ErrorEval.VALUE_INVALID;
        } else {
            i3 = ((RefEval) valueEval).getColumn();
        }
        return new NumberEval((double) (i3 + 1));
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length;
        if (length == 0) {
            return new NumberEval((double) (i2 + 1));
        }
        if (length != 1) {
            return ErrorEval.VALUE_INVALID;
        }
        return evaluate(i, i2, valueEvalArr[0]);
    }
}
