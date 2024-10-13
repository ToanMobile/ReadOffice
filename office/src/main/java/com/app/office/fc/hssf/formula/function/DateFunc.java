package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.ss.util.DateUtil;
import java.util.GregorianCalendar;

public final class DateFunc extends Fixed3ArgFunction {
    public static final Function instance = new DateFunc();

    private static int getYear(double d) {
        int i = (int) d;
        if (i < 0) {
            return -1;
        }
        return i < 1900 ? i + 1900 : i;
    }

    private DateFunc() {
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            double evaluate = evaluate(getYear(NumericFunction.singleOperandEvaluate(valueEval, i, i2)), (int) (NumericFunction.singleOperandEvaluate(valueEval2, i, i2) - 1.0d), (int) NumericFunction.singleOperandEvaluate(valueEval3, i, i2));
            NumericFunction.checkValue(evaluate);
            return new NumberEval(evaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static double evaluate(int i, int i2, int i3) throws EvaluationException {
        if (i < 0 || i2 < 0 || i3 < 0) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        } else if (i == 1900 && i2 == 1 && i3 == 29) {
            return 60.0d;
        } else {
            if (i == 1900 && ((i2 == 0 && i3 >= 60) || (i2 == 1 && i3 >= 30))) {
                i3--;
            }
            int i4 = i3;
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(i, i2, i4, 0, 0, 0);
            gregorianCalendar.set(14, 0);
            return DateUtil.getExcelDate(gregorianCalendar.getTime(), false);
        }
    }
}
