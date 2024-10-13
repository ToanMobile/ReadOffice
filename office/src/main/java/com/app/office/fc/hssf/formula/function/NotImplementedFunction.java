package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class NotImplementedFunction implements Function {
    private final String _functionName;

    protected NotImplementedFunction() {
        this._functionName = getClass().getName();
    }

    public NotImplementedFunction(String str) {
        this._functionName = str;
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        return ErrorEval.valueOf(29);
    }

    public String getFunctionName() {
        return this._functionName;
    }
}
