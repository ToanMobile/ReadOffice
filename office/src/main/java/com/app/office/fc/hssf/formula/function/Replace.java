package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Replace extends Fixed4ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3, ValueEval valueEval4) {
        try {
            String evaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
            int evaluateIntArg = TextFunction.evaluateIntArg(valueEval2, i, i2);
            int evaluateIntArg2 = TextFunction.evaluateIntArg(valueEval3, i, i2);
            String evaluateStringArg2 = TextFunction.evaluateStringArg(valueEval4, i, i2);
            if (evaluateIntArg < 1 || evaluateIntArg2 < 0) {
                return ErrorEval.VALUE_INVALID;
            }
            StringBuffer stringBuffer = new StringBuffer(evaluateStringArg);
            if (evaluateIntArg <= evaluateStringArg.length() && evaluateIntArg2 != 0) {
                int i3 = evaluateIntArg - 1;
                stringBuffer.delete(i3, evaluateIntArg2 + i3);
            }
            if (evaluateIntArg > stringBuffer.length()) {
                stringBuffer.append(evaluateStringArg2);
            } else {
                stringBuffer.insert(evaluateIntArg - 1, evaluateStringArg2);
            }
            return new StringEval(stringBuffer.toString());
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
