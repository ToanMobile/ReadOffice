package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.function.Fixed2ArgFunction;
import com.app.office.fc.hssf.formula.function.Function;

public final class RangeEval extends Fixed2ArgFunction {
    public static final Function instance = new RangeEval();

    private RangeEval() {
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        try {
            return resolveRange(evaluateRef(valueEval), evaluateRef(valueEval2));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static AreaEval resolveRange(AreaEval areaEval, AreaEval areaEval2) {
        int firstRow = areaEval.getFirstRow();
        int firstColumn = areaEval.getFirstColumn();
        return areaEval.offset(Math.min(firstRow, areaEval2.getFirstRow()) - firstRow, Math.max(areaEval.getLastRow(), areaEval2.getLastRow()) - firstRow, Math.min(firstColumn, areaEval2.getFirstColumn()) - firstColumn, Math.max(areaEval.getLastColumn(), areaEval2.getLastColumn()) - firstColumn);
    }

    private static AreaEval evaluateRef(ValueEval valueEval) throws EvaluationException {
        if (valueEval instanceof AreaEval) {
            return (AreaEval) valueEval;
        }
        if (valueEval instanceof RefEval) {
            return ((RefEval) valueEval).offset(0, 0, 0, 0);
        }
        if (valueEval instanceof ErrorEval) {
            throw new EvaluationException((ErrorEval) valueEval);
        }
        throw new IllegalArgumentException("Unexpected ref arg class (" + valueEval.getClass().getName() + ")");
    }
}
