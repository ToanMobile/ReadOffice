package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ValueEval;

public interface Function {
    ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2);
}
