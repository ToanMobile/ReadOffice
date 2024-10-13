package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.CountUtils;

public final class Sumif extends Var2or3ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        try {
            AreaEval convertRangeArg = convertRangeArg(valueEval);
            return eval(i, i2, valueEval2, convertRangeArg, convertRangeArg);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            AreaEval convertRangeArg = convertRangeArg(valueEval);
            return eval(i, i2, valueEval2, convertRangeArg, createSumRange(valueEval3, convertRangeArg));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static ValueEval eval(int i, int i2, ValueEval valueEval, AreaEval areaEval, AreaEval areaEval2) {
        return new NumberEval(sumMatchingCells(areaEval, Countif.createCriteriaPredicate(valueEval, i, i2), areaEval2));
    }

    private static double sumMatchingCells(AreaEval areaEval, CountUtils.I_MatchPredicate i_MatchPredicate, AreaEval areaEval2) {
        int height = areaEval.getHeight();
        int width = areaEval.getWidth();
        double d = 0.0d;
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                d += accumulate(areaEval, i_MatchPredicate, areaEval2, i, i2);
            }
        }
        return d;
    }

    private static double accumulate(AreaEval areaEval, CountUtils.I_MatchPredicate i_MatchPredicate, AreaEval areaEval2, int i, int i2) {
        if (!i_MatchPredicate.matches(areaEval.getRelativeValue(i, i2))) {
            return 0.0d;
        }
        ValueEval relativeValue = areaEval2.getRelativeValue(i, i2);
        if (relativeValue instanceof NumberEval) {
            return ((NumberEval) relativeValue).getNumberValue();
        }
        return 0.0d;
    }

    private static AreaEval createSumRange(ValueEval valueEval, AreaEval areaEval) throws EvaluationException {
        if (valueEval instanceof AreaEval) {
            return ((AreaEval) valueEval).offset(0, areaEval.getHeight() - 1, 0, areaEval.getWidth() - 1);
        }
        if (valueEval instanceof RefEval) {
            return ((RefEval) valueEval).offset(0, areaEval.getHeight() - 1, 0, areaEval.getWidth() - 1);
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }

    private static AreaEval convertRangeArg(ValueEval valueEval) throws EvaluationException {
        if (valueEval instanceof AreaEval) {
            return (AreaEval) valueEval;
        }
        if (valueEval instanceof RefEval) {
            return ((RefEval) valueEval).offset(0, 0, 0, 0);
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }
}
