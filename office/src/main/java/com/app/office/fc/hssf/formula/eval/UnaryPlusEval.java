package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.function.Fixed1ArgFunction;
import com.app.office.fc.hssf.formula.function.Function;

public final class UnaryPlusEval extends Fixed1ArgFunction {
    public static final Function instance = new UnaryPlusEval();

    private UnaryPlusEval() {
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        try {
            ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
            if (singleValue instanceof StringEval) {
                return singleValue;
            }
            return new NumberEval(OperandResolver.coerceValueToDouble(singleValue));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
