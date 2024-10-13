package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.OperationEvaluationContext;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.MissingArgEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Indirect implements FreeRefFunction {
    public static final FreeRefFunction instance = new Indirect();

    private Indirect() {
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        boolean z = true;
        if (valueEvalArr.length < 1) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            String coerceValueToString = OperandResolver.coerceValueToString(OperandResolver.getSingleValue(valueEvalArr[0], operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex()));
            int length = valueEvalArr.length;
            if (length != 1) {
                if (length != 2) {
                    return ErrorEval.VALUE_INVALID;
                }
                z = evaluateBooleanArg(valueEvalArr[1], operationEvaluationContext);
            }
            return evaluateIndirect(operationEvaluationContext, coerceValueToString, z);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static boolean evaluateBooleanArg(ValueEval valueEval, OperationEvaluationContext operationEvaluationContext) throws EvaluationException {
        ValueEval singleValue = OperandResolver.getSingleValue(valueEval, operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex());
        if (singleValue == BlankEval.instance || singleValue == MissingArgEval.instance) {
            return false;
        }
        return OperandResolver.coerceValueToBoolean(singleValue, false).booleanValue();
    }

    private static ValueEval evaluateIndirect(OperationEvaluationContext operationEvaluationContext, String str, boolean z) {
        String str2;
        String str3;
        String str4;
        String str5;
        int lastIndexOf = str.lastIndexOf(33);
        if (lastIndexOf < 0) {
            str3 = null;
            str2 = null;
        } else {
            String[] parseWorkbookAndSheetName = parseWorkbookAndSheetName(str.subSequence(0, lastIndexOf));
            if (parseWorkbookAndSheetName == null) {
                return ErrorEval.REF_INVALID;
            }
            str3 = parseWorkbookAndSheetName[0];
            String str6 = parseWorkbookAndSheetName[1];
            str = str.substring(lastIndexOf + 1);
            str2 = str6;
        }
        int indexOf = str.indexOf(58);
        if (indexOf < 0) {
            str5 = str.trim();
            str4 = null;
        } else {
            String trim = str.substring(0, indexOf).trim();
            str4 = str.substring(indexOf + 1).trim();
            str5 = trim;
        }
        return operationEvaluationContext.getDynamicReference(str3, str2, str5, str4, z);
    }

    private static String[] parseWorkbookAndSheetName(CharSequence charSequence) {
        String str;
        int i;
        int length = charSequence.length() - 1;
        if (length < 0 || canTrim(charSequence)) {
            return null;
        }
        char charAt = charSequence.charAt(0);
        if (Character.isWhitespace(charAt)) {
            return null;
        }
        if (charAt == '\'') {
            if (charSequence.charAt(length) != '\'') {
                return null;
            }
            char charAt2 = charSequence.charAt(1);
            if (Character.isWhitespace(charAt2)) {
                return null;
            }
            if (charAt2 == '[') {
                int lastIndexOf = charSequence.toString().lastIndexOf(93);
                if (lastIndexOf < 0 || (str = unescapeString(charSequence.subSequence(2, lastIndexOf))) == null || canTrim(str)) {
                    return null;
                }
                i = lastIndexOf + 1;
            } else {
                str = null;
                i = 1;
            }
            String unescapeString = unescapeString(charSequence.subSequence(i, length));
            if (unescapeString == null) {
                return null;
            }
            return new String[]{str, unescapeString};
        } else if (charAt == '[') {
            int lastIndexOf2 = charSequence.toString().lastIndexOf(93);
            if (lastIndexOf2 < 0) {
                return null;
            }
            CharSequence subSequence = charSequence.subSequence(1, lastIndexOf2);
            if (canTrim(subSequence)) {
                return null;
            }
            CharSequence subSequence2 = charSequence.subSequence(lastIndexOf2 + 1, charSequence.length());
            if (canTrim(subSequence2)) {
                return null;
            }
            return new String[]{subSequence.toString(), subSequence2.toString()};
        } else {
            return new String[]{null, charSequence.toString()};
        }
    }

    private static String unescapeString(CharSequence charSequence) {
        char charAt;
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            char charAt2 = charSequence.charAt(i);
            if (charAt2 == '\'') {
                i++;
                if (i >= length || (charAt = charSequence.charAt(i)) != '\'') {
                    return null;
                }
                charAt2 = charAt;
            }
            sb.append(charAt2);
            i++;
        }
        return sb.toString();
    }

    private static boolean canTrim(CharSequence charSequence) {
        int length = charSequence.length() - 1;
        if (length < 0) {
            return false;
        }
        return Character.isWhitespace(charSequence.charAt(0)) || Character.isWhitespace(charSequence.charAt(length));
    }
}
