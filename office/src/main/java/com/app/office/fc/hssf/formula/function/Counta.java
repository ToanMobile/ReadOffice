package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.CountUtils;

public final class Counta implements Function {
    /* access modifiers changed from: private */
    public static final CountUtils.I_MatchPredicate defaultPredicate = new CountUtils.I_MatchPredicate() {
        public boolean matches(ValueEval valueEval) {
            return valueEval != BlankEval.instance;
        }
    };
    private static final CountUtils.I_MatchPredicate subtotalPredicate = new CountUtils.I_MatchAreaPredicate() {
        public boolean matches(ValueEval valueEval) {
            return Counta.defaultPredicate.matches(valueEval);
        }

        public boolean matches(TwoDEval twoDEval, int i, int i2) {
            return !twoDEval.isSubTotal(i, i2);
        }
    };
    private final CountUtils.I_MatchPredicate _predicate;

    public Counta() {
        this._predicate = defaultPredicate;
    }

    private Counta(CountUtils.I_MatchPredicate i_MatchPredicate) {
        this._predicate = i_MatchPredicate;
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (r5 < 1) {
            return ErrorEval.VALUE_INVALID;
        }
        if (r5 > 30) {
            return ErrorEval.VALUE_INVALID;
        }
        int i3 = 0;
        for (ValueEval countArg : valueEvalArr) {
            i3 += CountUtils.countArg(countArg, this._predicate);
        }
        return new NumberEval((double) i3);
    }

    public static Counta subtotalInstance() {
        return new Counta(subtotalPredicate);
    }
}
