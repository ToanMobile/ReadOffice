package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.function.Fixed2ArgFunction;
import com.app.office.fc.hssf.formula.function.Function;

public final class IntersectionEval extends Fixed2ArgFunction {
    public static final Function instance = new IntersectionEval();

    private IntersectionEval() {
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        try {
            AreaEval resolveRange = resolveRange(evaluateRef(valueEval), evaluateRef(valueEval2));
            return resolveRange == null ? ErrorEval.NULL_INTERSECTION : resolveRange;
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static AreaEval resolveRange(AreaEval areaEval, AreaEval areaEval2) {
        int firstColumn;
        int lastRow;
        int firstRow;
        int lastRow2;
        int firstRow2 = areaEval.getFirstRow();
        int firstColumn2 = areaEval.getFirstColumn();
        int lastColumn = areaEval2.getLastColumn();
        if (firstColumn2 > lastColumn || (firstColumn = areaEval2.getFirstColumn()) > areaEval.getLastColumn() || firstRow2 > (lastRow = areaEval2.getLastRow()) || (firstRow = areaEval2.getFirstRow()) > (lastRow2 = areaEval.getLastRow())) {
            return null;
        }
        return areaEval.offset(Math.max(firstRow2, firstRow) - firstRow2, Math.min(lastRow2, lastRow) - firstRow2, Math.max(firstColumn2, firstColumn) - firstColumn2, Math.min(areaEval.getLastColumn(), lastColumn) - firstColumn2);
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
