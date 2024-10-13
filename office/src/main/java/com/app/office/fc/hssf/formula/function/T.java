package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class T extends Fixed1ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        if (valueEval instanceof RefEval) {
            valueEval = ((RefEval) valueEval).getInnerValueEval();
        } else if (valueEval instanceof AreaEval) {
            valueEval = ((AreaEval) valueEval).getRelativeValue(0, 0);
        }
        if (!(valueEval instanceof StringEval) && !(valueEval instanceof ErrorEval)) {
            return StringEval.EMPTY_INSTANCE;
        }
        return valueEval;
    }
}
