package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.ss.util.DateUtil;
import java.util.GregorianCalendar;

public final class Today extends Fixed0ArgFunction {
    public ValueEval evaluate(int i, int i2) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(gregorianCalendar.get(1), gregorianCalendar.get(2), gregorianCalendar.get(5), 0, 0, 0);
        gregorianCalendar.set(14, 0);
        return new NumberEval(DateUtil.getExcelDate(gregorianCalendar.getTime()));
    }
}
