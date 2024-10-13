package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public class Weekday extends Var1or2ArgFunction {
    private static final ValueEval DEFAULT_ARG1 = new NumberEval(1.0d);

    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        return evaluate(i, i2, valueEval, DEFAULT_ARG1);
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        ValueEval evaluate = ((CalendarFieldFunction) CalendarFieldFunction.WEEKDAY).evaluate(i, i2, valueEval);
        if (!(valueEval2 instanceof NumberEval)) {
            return evaluate;
        }
        int round = (int) Math.round(((NumberEval) evaluate).getNumberValue());
        int round2 = (int) Math.round(((NumberEval) valueEval2).getNumberValue());
        if (round2 == 1) {
            return evaluate;
        }
        if (round2 == 2) {
            int i3 = round - 1;
            if (i3 == 0) {
                i3 = 7;
            }
            return new NumberEval((double) i3);
        } else if (round2 != 3) {
            return ErrorEval.valueOf(36);
        } else {
            int i4 = round - 2;
            if (i4 < 0) {
                i4 = 6;
            }
            return new NumberEval((double) i4);
        }
    }
}
