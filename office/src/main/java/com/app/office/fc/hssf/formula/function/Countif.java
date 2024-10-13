package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.CountUtils;
import com.app.office.fc.ss.usermodel.ErrorConstants;
import java.util.regex.Pattern;

public final class Countif extends Fixed2ArgFunction {

    private static final class CmpOp {
        public static final int EQ = 1;
        public static final int GE = 6;
        public static final int GT = 5;
        public static final int LE = 3;
        public static final int LT = 4;
        public static final int NE = 2;
        public static final int NONE = 0;
        public static final CmpOp OP_EQ = op("=", 1);
        public static final CmpOp OP_GE = op(">=", 6);
        public static final CmpOp OP_GT = op(">", 5);
        public static final CmpOp OP_LE = op("<=", 3);
        public static final CmpOp OP_LT = op("<", 4);
        public static final CmpOp OP_NE = op("<>", 2);
        public static final CmpOp OP_NONE = op("", 0);
        private final int _code;
        private final String _representation;

        private static CmpOp op(String str, int i) {
            return new CmpOp(str, i);
        }

        private CmpOp(String str, int i) {
            this._representation = str;
            this._code = i;
        }

        public int getLength() {
            return this._representation.length();
        }

        public int getCode() {
            return this._code;
        }

        public static CmpOp getOperator(String str) {
            int length = str.length();
            if (length < 1) {
                return OP_NONE;
            }
            switch (str.charAt(0)) {
                case '<':
                    if (length > 1) {
                        char charAt = str.charAt(1);
                        if (charAt == '=') {
                            return OP_LE;
                        }
                        if (charAt == '>') {
                            return OP_NE;
                        }
                    }
                    return OP_LT;
                case '=':
                    return OP_EQ;
                case '>':
                    if (length <= 1 || str.charAt(1) != '=') {
                        return OP_GT;
                    }
                    return OP_GE;
                default:
                    return OP_NONE;
            }
        }

        public boolean evaluate(boolean z) {
            int i = this._code;
            if (i == 0 || i == 1) {
                return z;
            }
            if (i == 2) {
                return !z;
            }
            throw new RuntimeException("Cannot call boolean evaluate on non-equality operator '" + this._representation + "'");
        }

        public boolean evaluate(int i) {
            switch (this._code) {
                case 0:
                case 1:
                    if (i == 0) {
                        return true;
                    }
                    return false;
                case 2:
                    if (i != 0) {
                        return true;
                    }
                    return false;
                case 3:
                    if (i <= 0) {
                        return true;
                    }
                    return false;
                case 4:
                    if (i < 0) {
                        return true;
                    }
                    return false;
                case 5:
                    if (i > 0) {
                        return true;
                    }
                    return false;
                case 6:
                    if (i <= 0) {
                        return true;
                    }
                    return false;
                default:
                    throw new RuntimeException("Cannot call boolean evaluate on non-equality operator '" + this._representation + "'");
            }
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(this._representation);
            stringBuffer.append("]");
            return stringBuffer.toString();
        }

        public String getRepresentation() {
            return this._representation;
        }
    }

    private static abstract class MatcherBase implements CountUtils.I_MatchPredicate {
        private final CmpOp _operator;

        /* access modifiers changed from: protected */
        public abstract String getValueText();

        MatcherBase(CmpOp cmpOp) {
            this._operator = cmpOp;
        }

        /* access modifiers changed from: protected */
        public final int getCode() {
            return this._operator.getCode();
        }

        /* access modifiers changed from: protected */
        public final boolean evaluate(int i) {
            return this._operator.evaluate(i);
        }

        /* access modifiers changed from: protected */
        public final boolean evaluate(boolean z) {
            return this._operator.evaluate(z);
        }

        public final String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(this._operator.getRepresentation());
            stringBuffer.append(getValueText());
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private static final class NumberMatcher extends MatcherBase {
        private final double _value;

        public NumberMatcher(double d, CmpOp cmpOp) {
            super(cmpOp);
            this._value = d;
        }

        /* access modifiers changed from: protected */
        public String getValueText() {
            return String.valueOf(this._value);
        }

        public boolean matches(ValueEval valueEval) {
            if (valueEval instanceof StringEval) {
                int code = getCode();
                if (code == 0 || code == 1) {
                    Double parseDouble = OperandResolver.parseDouble(((StringEval) valueEval).getStringValue());
                    if (parseDouble != null && this._value == parseDouble.doubleValue()) {
                        return true;
                    }
                    return false;
                } else if (code != 2) {
                    return false;
                } else {
                    return true;
                }
            } else if (valueEval instanceof NumberEval) {
                return evaluate(Double.compare(((NumberEval) valueEval).getNumberValue(), this._value));
            } else {
                return false;
            }
        }
    }

    private static final class BooleanMatcher extends MatcherBase {
        private final int _value;

        private static int boolToInt(boolean z) {
            return z ? 1 : 0;
        }

        public BooleanMatcher(boolean z, CmpOp cmpOp) {
            super(cmpOp);
            this._value = boolToInt(z);
        }

        /* access modifiers changed from: protected */
        public String getValueText() {
            return this._value == 1 ? "TRUE" : "FALSE";
        }

        public boolean matches(ValueEval valueEval) {
            if (!(valueEval instanceof StringEval) && (valueEval instanceof BoolEval)) {
                return evaluate(boolToInt(((BoolEval) valueEval).getBooleanValue()) - this._value);
            }
            return false;
        }
    }

    private static final class ErrorMatcher extends MatcherBase {
        private final int _value;

        public ErrorMatcher(int i, CmpOp cmpOp) {
            super(cmpOp);
            this._value = i;
        }

        /* access modifiers changed from: protected */
        public String getValueText() {
            return ErrorConstants.getText(this._value);
        }

        public boolean matches(ValueEval valueEval) {
            if (valueEval instanceof ErrorEval) {
                return evaluate(((ErrorEval) valueEval).getErrorCode() - this._value);
            }
            return false;
        }
    }

    private static final class StringMatcher extends MatcherBase {
        private final Pattern _pattern;
        private final String _value;

        public StringMatcher(String str, CmpOp cmpOp) {
            super(cmpOp);
            this._value = str;
            int code = cmpOp.getCode();
            if (code == 0 || code == 1 || code == 2) {
                this._pattern = getWildCardPattern(str);
            } else {
                this._pattern = null;
            }
        }

        /* access modifiers changed from: protected */
        public String getValueText() {
            Pattern pattern = this._pattern;
            if (pattern == null) {
                return this._value;
            }
            return pattern.pattern();
        }

        public boolean matches(ValueEval valueEval) {
            if (valueEval instanceof BlankEval) {
                int code = getCode();
                if ((code == 0 || code == 1) && this._value.length() == 0) {
                    return true;
                }
                return false;
            } else if (!(valueEval instanceof StringEval)) {
                return false;
            } else {
                String stringValue = ((StringEval) valueEval).getStringValue();
                if (stringValue.length() >= 1 || this._value.length() >= 1) {
                    Pattern pattern = this._pattern;
                    if (pattern != null) {
                        return evaluate(pattern.matcher(stringValue).matches());
                    }
                    return evaluate(stringValue.compareTo(this._value));
                }
                int code2 = getCode();
                return code2 == 0 || code2 == 2;
            }
        }

        private static Pattern getWildCardPattern(String str) {
            char charAt;
            int length = str.length();
            StringBuffer stringBuffer = new StringBuffer(length);
            int i = 0;
            boolean z = false;
            while (i < length) {
                char charAt2 = str.charAt(i);
                if (!(charAt2 == '$' || charAt2 == '.')) {
                    if (charAt2 == '?') {
                        stringBuffer.append('.');
                    } else if (charAt2 != '[') {
                        if (charAt2 != '~') {
                            if (!(charAt2 == ']' || charAt2 == '^')) {
                                switch (charAt2) {
                                    case '(':
                                    case ')':
                                        break;
                                    case '*':
                                        stringBuffer.append(".*");
                                        break;
                                    default:
                                        stringBuffer.append(charAt2);
                                        break;
                                }
                            }
                        } else {
                            int i2 = i + 1;
                            if (i2 >= length || !((charAt = str.charAt(i2)) == '*' || charAt == '?')) {
                                stringBuffer.append('~');
                                i++;
                            } else {
                                stringBuffer.append('[');
                                stringBuffer.append(charAt);
                                stringBuffer.append(']');
                                i = i2;
                            }
                        }
                    }
                    z = true;
                    i++;
                }
                stringBuffer.append("\\");
                stringBuffer.append(charAt2);
                i++;
            }
            if (z) {
                return Pattern.compile(stringBuffer.toString());
            }
            return null;
        }
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        CountUtils.I_MatchPredicate createCriteriaPredicate = createCriteriaPredicate(valueEval2, i, i2);
        if (createCriteriaPredicate == null) {
            return NumberEval.ZERO;
        }
        return new NumberEval(countMatchingCellsInArea(valueEval, createCriteriaPredicate));
    }

    private double countMatchingCellsInArea(ValueEval valueEval, CountUtils.I_MatchPredicate i_MatchPredicate) {
        int countMatchingCellsInArea;
        if (valueEval instanceof RefEval) {
            countMatchingCellsInArea = CountUtils.countMatchingCell((RefEval) valueEval, i_MatchPredicate);
        } else if (valueEval instanceof TwoDEval) {
            countMatchingCellsInArea = CountUtils.countMatchingCellsInArea((TwoDEval) valueEval, i_MatchPredicate);
        } else {
            throw new IllegalArgumentException("Bad range arg type (" + valueEval.getClass().getName() + ")");
        }
        return (double) countMatchingCellsInArea;
    }

    static CountUtils.I_MatchPredicate createCriteriaPredicate(ValueEval valueEval, int i, int i2) {
        ValueEval evaluateCriteriaArg = evaluateCriteriaArg(valueEval, i, i2);
        if (evaluateCriteriaArg instanceof NumberEval) {
            return new NumberMatcher(((NumberEval) evaluateCriteriaArg).getNumberValue(), CmpOp.OP_NONE);
        }
        if (evaluateCriteriaArg instanceof BoolEval) {
            return new BooleanMatcher(((BoolEval) evaluateCriteriaArg).getBooleanValue(), CmpOp.OP_NONE);
        }
        if (evaluateCriteriaArg instanceof StringEval) {
            return createGeneralMatchPredicate((StringEval) evaluateCriteriaArg);
        }
        if (evaluateCriteriaArg instanceof ErrorEval) {
            return new ErrorMatcher(((ErrorEval) evaluateCriteriaArg).getErrorCode(), CmpOp.OP_NONE);
        }
        if (evaluateCriteriaArg == BlankEval.instance) {
            return null;
        }
        throw new RuntimeException("Unexpected type for criteria (" + evaluateCriteriaArg.getClass().getName() + ")");
    }

    private static ValueEval evaluateCriteriaArg(ValueEval valueEval, int i, int i2) {
        try {
            return OperandResolver.getSingleValue(valueEval, i, (short) i2);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static CountUtils.I_MatchPredicate createGeneralMatchPredicate(StringEval stringEval) {
        String stringValue = stringEval.getStringValue();
        CmpOp operator = CmpOp.getOperator(stringValue);
        String substring = stringValue.substring(operator.getLength());
        Boolean parseBoolean = parseBoolean(substring);
        if (parseBoolean != null) {
            return new BooleanMatcher(parseBoolean.booleanValue(), operator);
        }
        Double parseDouble = OperandResolver.parseDouble(substring);
        if (parseDouble != null) {
            return new NumberMatcher(parseDouble.doubleValue(), operator);
        }
        ErrorEval parseError = parseError(substring);
        if (parseError != null) {
            return new ErrorMatcher(parseError.getErrorCode(), operator);
        }
        return new StringMatcher(substring, operator);
    }

    private static ErrorEval parseError(String str) {
        if (str.length() >= 4 && str.charAt(0) == '#') {
            if (str.equals("#NULL!")) {
                return ErrorEval.NULL_INTERSECTION;
            }
            if (str.equals("#DIV/0!")) {
                return ErrorEval.DIV_ZERO;
            }
            if (str.equals("#VALUE!")) {
                return ErrorEval.VALUE_INVALID;
            }
            if (str.equals("#REF!")) {
                return ErrorEval.REF_INVALID;
            }
            if (str.equals("#NAME?")) {
                return ErrorEval.NAME_INVALID;
            }
            if (str.equals("#NUM!")) {
                return ErrorEval.NUM_ERROR;
            }
            if (str.equals("#N/A")) {
                return ErrorEval.NA;
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        if (r0 != 't') goto L_0x0035;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Boolean parseBoolean(java.lang.String r3) {
        /*
            int r0 = r3.length()
            r1 = 0
            r2 = 1
            if (r0 >= r2) goto L_0x0009
            return r1
        L_0x0009:
            r0 = 0
            char r0 = r3.charAt(r0)
            r2 = 70
            if (r0 == r2) goto L_0x002a
            r2 = 84
            if (r0 == r2) goto L_0x001f
            r2 = 102(0x66, float:1.43E-43)
            if (r0 == r2) goto L_0x002a
            r2 = 116(0x74, float:1.63E-43)
            if (r0 == r2) goto L_0x001f
            goto L_0x0035
        L_0x001f:
            java.lang.String r0 = "TRUE"
            boolean r3 = r0.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0035
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            return r3
        L_0x002a:
            java.lang.String r0 = "FALSE"
            boolean r3 = r0.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0035
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            return r3
        L_0x0035:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.function.Countif.parseBoolean(java.lang.String):java.lang.Boolean");
    }
}
