package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NameEval;
import com.app.office.fc.hssf.formula.eval.NameXEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;

final class UserDefinedFunction implements FreeRefFunction {
    public static final FreeRefFunction instance = new UserDefinedFunction();

    private UserDefinedFunction() {
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        String str;
        int length = valueEvalArr.length;
        if (length >= 1) {
            NameEval nameEval = valueEvalArr[0];
            if (nameEval instanceof NameEval) {
                str = nameEval.getFunctionName();
            } else if (nameEval instanceof NameXEval) {
                str = operationEvaluationContext.getWorkbook().resolveNameXText(((NameXEval) nameEval).getPtg());
            } else {
                throw new RuntimeException("First argument should be a NameEval, but got (" + nameEval.getClass().getName() + ")");
            }
            FreeRefFunction findUserDefinedFunction = operationEvaluationContext.findUserDefinedFunction(str);
            if (findUserDefinedFunction == null) {
                return ErrorEval.valueOf(29);
            }
            int i = length - 1;
            ValueEval[] valueEvalArr2 = new ValueEval[i];
            System.arraycopy(valueEvalArr, 1, valueEvalArr2, 0, i);
            return findUserDefinedFunction.evaluate(valueEvalArr2, operationEvaluationContext);
        }
        throw new RuntimeException("function name argument missing");
    }
}
