package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.ss.util.DateUtil;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Days360 extends Var2or3ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        try {
            return new NumberEval(evaluate(NumericFunction.singleOperandEvaluate(valueEval, i, i2), NumericFunction.singleOperandEvaluate(valueEval2, i, i2), false));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            double singleOperandEvaluate = NumericFunction.singleOperandEvaluate(valueEval, i, i2);
            double singleOperandEvaluate2 = NumericFunction.singleOperandEvaluate(valueEval2, i, i2);
            ValueEval singleValue = OperandResolver.getSingleValue(valueEval3, i, i2);
            boolean z = false;
            Boolean coerceValueToBoolean = OperandResolver.coerceValueToBoolean(singleValue, false);
            if (coerceValueToBoolean != null) {
                z = coerceValueToBoolean.booleanValue();
            }
            return new NumberEval(evaluate(singleOperandEvaluate, singleOperandEvaluate2, z));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static double evaluate(double d, double d2, boolean z) {
        Calendar startingDate = getStartingDate(d);
        Calendar endingDateAccordingToStartingDate = getEndingDateAccordingToStartingDate(d2, startingDate);
        return (double) (((long) ((((endingDateAccordingToStartingDate.get(1) - startingDate.get(1)) * 360) + (endingDateAccordingToStartingDate.get(2) * 30)) + endingDateAccordingToStartingDate.get(5))) - ((long) ((startingDate.get(2) * 30) + startingDate.get(5))));
    }

    private static Calendar getDate(double d) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(DateUtil.getJavaDate(d, false));
        return gregorianCalendar;
    }

    private static Calendar getStartingDate(double d) {
        Calendar date = getDate(d);
        if (isLastDayOfMonth(date)) {
            date.set(5, 30);
        }
        return date;
    }

    private static Calendar getEndingDateAccordingToStartingDate(double d, Calendar calendar) {
        Calendar date = getDate(d);
        date.setTime(DateUtil.getJavaDate(d, false));
        return (!isLastDayOfMonth(date) || calendar.get(5) >= 30) ? date : getFirstDayOfNextMonth(date);
    }

    private static boolean isLastDayOfMonth(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.add(2, 1);
        calendar2.add(5, -1);
        if (calendar.get(5) == calendar2.get(5)) {
            return true;
        }
        return false;
    }

    private static Calendar getFirstDayOfNextMonth(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        if (calendar.get(2) < 11) {
            calendar2.set(2, calendar.get(2) + 1);
        } else {
            calendar2.set(2, 1);
            calendar2.set(1, calendar.get(1) + 1);
        }
        calendar2.set(5, 1);
        return calendar2;
    }
}
