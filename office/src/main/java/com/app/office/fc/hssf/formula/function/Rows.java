package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Rows extends Fixed1ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        int i3;
        if (valueEval instanceof TwoDEval) {
            i3 = ((TwoDEval) valueEval).getHeight();
        } else if (!(valueEval instanceof RefEval)) {
            return ErrorEval.VALUE_INVALID;
        } else {
            i3 = 1;
        }
        return new NumberEval((double) i3);
    }
}
