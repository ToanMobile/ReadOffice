package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.ss.util.DateUtil;
import java.util.Date;

public final class Now extends Fixed0ArgFunction {
    public ValueEval evaluate(int i, int i2) {
        return new NumberEval(DateUtil.getExcelDate(new Date(System.currentTimeMillis())));
    }
}
