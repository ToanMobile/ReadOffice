package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ValueEval;

public interface Function2Arg extends Function {
    ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2);
}
