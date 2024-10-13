package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.ss.util.DateUtil;
import java.util.Date;
import java.util.GregorianCalendar;

public final class CalendarFieldFunction extends Fixed1ArgFunction {
    public static final Function DAY = new CalendarFieldFunction(5);
    public static final Function HOUR = new CalendarFieldFunction(11);
    public static final Function MINUTE = new CalendarFieldFunction(12);
    public static final Function MONTH = new CalendarFieldFunction(2);
    public static final Function SECOND = new CalendarFieldFunction(13);
    public static final Function WEEKDAY = new CalendarFieldFunction(7);
    public static final Function YEAR = new CalendarFieldFunction(1);
    private final int _dateFieldId;

    private CalendarFieldFunction(int i) {
        this._dateFieldId = i;
    }

    public final ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        try {
            double coerceValueToDouble = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, i2));
            if (coerceValueToDouble < 0.0d) {
                return ErrorEval.NUM_ERROR;
            }
            return new NumberEval((double) getCalField(coerceValueToDouble));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private int getCalField(double d) {
        if (((int) d) == 0) {
            int i = this._dateFieldId;
            if (i == 1) {
                return 1900;
            }
            if (i == 2) {
                return 1;
            }
            if (i == 5) {
                return 0;
            }
        }
        Date javaDate = DateUtil.getJavaDate(d, false);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(javaDate);
        int i2 = gregorianCalendar.get(this._dateFieldId);
        return this._dateFieldId == 2 ? i2 + 1 : i2;
    }
}
