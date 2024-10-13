package com.app.office.fc.hssf.formula.atp;

import com.app.office.fc.hssf.formula.OperationEvaluationContext;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;

final class ParityFunction implements FreeRefFunction {
    public static final FreeRefFunction IS_EVEN = new ParityFunction(0);
    public static final FreeRefFunction IS_ODD = new ParityFunction(1);
    private final int _desiredParity;

    private ParityFunction(int i) {
        this._desiredParity = i;
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        boolean z = true;
        if (valueEvalArr.length != 1) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            if (evaluateArgParity(valueEvalArr[0], operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex()) != this._desiredParity) {
                z = false;
            }
            return BoolEval.valueOf(z);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static int evaluateArgParity(ValueEval valueEval, int i, int i2) throws EvaluationException {
        double coerceValueToDouble = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, (short) i2));
        if (coerceValueToDouble < 0.0d) {
            coerceValueToDouble = -coerceValueToDouble;
        }
        return (int) (((long) Math.floor(coerceValueToDouble)) & 1);
    }
}
