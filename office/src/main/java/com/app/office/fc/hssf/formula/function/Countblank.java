package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.CountUtils;

public final class Countblank extends Fixed1ArgFunction {
    private static final CountUtils.I_MatchPredicate predicate = new CountUtils.I_MatchPredicate() {
        public boolean matches(ValueEval valueEval) {
            return valueEval == BlankEval.instance;
        }
    };

    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        int countMatchingCellsInArea;
        if (valueEval instanceof RefEval) {
            countMatchingCellsInArea = CountUtils.countMatchingCell((RefEval) valueEval, predicate);
        } else if (valueEval instanceof TwoDEval) {
            countMatchingCellsInArea = CountUtils.countMatchingCellsInArea((TwoDEval) valueEval, predicate);
        } else {
            throw new IllegalArgumentException("Bad range arg type (" + valueEval.getClass().getName() + ")");
        }
        return new NumberEval((double) countMatchingCellsInArea);
    }
}
