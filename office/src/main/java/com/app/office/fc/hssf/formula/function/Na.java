package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Na extends Fixed0ArgFunction {
    public ValueEval evaluate(int i, int i2) {
        return ErrorEval.NA;
    }
}
