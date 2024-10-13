package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.OperationEvaluationContext;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public interface FreeRefFunction {
    ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext);
}
