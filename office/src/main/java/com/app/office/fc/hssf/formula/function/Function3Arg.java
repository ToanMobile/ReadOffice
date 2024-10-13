package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ValueEval;

public interface Function3Arg extends Function {
    ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3);
}
