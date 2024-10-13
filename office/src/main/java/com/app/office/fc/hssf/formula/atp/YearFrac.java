package com.app.office.fc.hssf.formula.atp;

import com.app.office.fc.hssf.formula.OperationEvaluationContext;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.ss.util.DateUtil;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

final class YearFrac implements FreeRefFunction {
    public static final FreeRefFunction instance = new YearFrac();

    private YearFrac() {
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        int i;
        int rowIndex = operationEvaluationContext.getRowIndex();
        int columnIndex = operationEvaluationContext.getColumnIndex();
        try {
            int length = valueEvalArr.length;
            if (length == 2) {
                i = 0;
            } else if (length != 3) {
                return ErrorEval.VALUE_INVALID;
            } else {
                i = evaluateIntArg(valueEvalArr[2], rowIndex, columnIndex);
            }
            return new NumberEval(YearFracCalculator.calculate(evaluateDateArg(valueEvalArr[0], rowIndex, columnIndex), evaluateDateArg(valueEvalArr[1], rowIndex, columnIndex), i));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static double evaluateDateArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, (short) i2);
        if (!(singleValue instanceof StringEval)) {
            return OperandResolver.coerceValueToDouble(singleValue);
        }
        String stringValue = ((StringEval) singleValue).getStringValue();
        Double parseDouble = OperandResolver.parseDouble(stringValue);
        if (parseDouble != null) {
            return parseDouble.doubleValue();
        }
        return DateUtil.getExcelDate(parseDate(stringValue), false);
    }

    private static Calendar parseDate(String str) throws EvaluationException {
        String[] split = Pattern.compile(PackagingURIHelper.FORWARD_SLASH_STRING).split(str);
        if (split.length == 3) {
            String str2 = split[2];
            int indexOf = str2.indexOf(32);
            if (indexOf > 0) {
                str2 = str2.substring(0, indexOf);
            }
            try {
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                int parseInt3 = Integer.parseInt(str2);
                if (parseInt < 0 || parseInt2 < 0 || parseInt3 < 0 || (parseInt > 12 && parseInt2 > 12 && parseInt3 > 12)) {
                    throw new EvaluationException(ErrorEval.VALUE_INVALID);
                } else if (parseInt >= 1900 && parseInt < 9999) {
                    return makeDate(parseInt, parseInt2, parseInt3);
                } else {
                    throw new RuntimeException("Unable to determine date format for text '" + str + "'");
                }
            } catch (NumberFormatException unused) {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
        } else {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
    }

    private static Calendar makeDate(int i, int i2, int i3) throws EvaluationException {
        if (i2 < 1 || i2 > 12) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(i, i2 - 1, 1, 0, 0, 0);
        gregorianCalendar.set(14, 0);
        if (i3 < 1 || i3 > gregorianCalendar.getActualMaximum(5)) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
        gregorianCalendar.set(5, i3);
        return gregorianCalendar;
    }

    private static int evaluateIntArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, (short) i2));
    }
}
