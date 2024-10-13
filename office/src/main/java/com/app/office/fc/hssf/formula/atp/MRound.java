package com.app.office.fc.hssf.formula.atp;

import com.app.office.fc.hssf.formula.OperationEvaluationContext;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;
import com.app.office.fc.hssf.formula.function.NumericFunction;

final class MRound implements FreeRefFunction {
    public static final FreeRefFunction instance = new MRound();

    private MRound() {
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        if (valueEvalArr.length != 2) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            double coerceValueToDouble = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEvalArr[0], operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex()));
            double coerceValueToDouble2 = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEvalArr[1], operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex()));
            double d = 0.0d;
            if (coerceValueToDouble2 != 0.0d) {
                if (coerceValueToDouble * coerceValueToDouble2 >= 0.0d) {
                    d = coerceValueToDouble2 * ((double) Math.round(coerceValueToDouble / coerceValueToDouble2));
                } else {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            }
            NumericFunction.checkValue(d);
            return new NumberEval(d);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
