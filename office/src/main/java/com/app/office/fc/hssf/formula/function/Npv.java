package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.AggregateFunction;

public final class Npv implements Function {
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (valueEvalArr.length < 2) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            double singleOperandEvaluate = NumericFunction.singleOperandEvaluate(valueEvalArr[0], i, i2);
            int length = valueEvalArr.length - 1;
            ValueEval[] valueEvalArr2 = new ValueEval[length];
            System.arraycopy(valueEvalArr, 1, valueEvalArr2, 0, length);
            double npv = FinanceLib.npv(singleOperandEvaluate, AggregateFunction.ValueCollector.collectValues(valueEvalArr2));
            NumericFunction.checkValue(npv);
            return new NumberEval(npv);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
