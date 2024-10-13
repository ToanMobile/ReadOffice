package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public abstract class TextFunction implements Function {
    public static final Function CHAR = new Fixed1ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            try {
                int evaluateIntArg = TextFunction.evaluateIntArg(valueEval, i, i2);
                if (evaluateIntArg >= 0 && evaluateIntArg < 256) {
                    return new StringEval(String.valueOf((char) evaluateIntArg));
                }
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function CLEAN = new SingleArgTextFunc() {
        private boolean isPrintable(char c) {
            return c >= ' ';
        }

        /* access modifiers changed from: protected */
        public ValueEval evaluate(String str) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (isPrintable(charAt)) {
                    sb.append(charAt);
                }
            }
            return new StringEval(sb.toString());
        }
    };
    public static final Function CODE = new SingleArgTextFunc() {
        /* access modifiers changed from: protected */
        public ValueEval evaluate(String str) {
            return new NumberEval((double) str.codePointAt(0));
        }
    };
    public static final Function CONCATENATE = new Function() {
        public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
            StringBuilder sb = new StringBuilder();
            int length = valueEvalArr.length;
            int i3 = 0;
            while (i3 < length) {
                try {
                    sb.append(TextFunction.evaluateStringArg(valueEvalArr[i3], i, i2));
                    i3++;
                } catch (EvaluationException e) {
                    return e.getErrorEval();
                }
            }
            return new StringEval(sb.toString());
        }
    };
    protected static final String EMPTY_STRING = "";
    public static final Function EXACT = new Fixed2ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                return BoolEval.valueOf(TextFunction.evaluateStringArg(valueEval, i, i2).equals(TextFunction.evaluateStringArg(valueEval2, i, i2)));
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function FIND = new SearchFind(true);
    public static final Function LEFT = new LeftRight(true);
    public static final Function LEN = new SingleArgTextFunc() {
        /* access modifiers changed from: protected */
        public ValueEval evaluate(String str) {
            return new NumberEval((double) str.length());
        }
    };
    public static final Function LOWER = new SingleArgTextFunc() {
        /* access modifiers changed from: protected */
        public ValueEval evaluate(String str) {
            return new StringEval(str.toLowerCase());
        }
    };
    public static final Function MID = new Fixed3ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
            try {
                String evaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
                int evaluateIntArg = TextFunction.evaluateIntArg(valueEval2, i, i2);
                int evaluateIntArg2 = TextFunction.evaluateIntArg(valueEval3, i, i2);
                int i3 = evaluateIntArg - 1;
                if (i3 < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                if (evaluateIntArg2 < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                int length = evaluateStringArg.length();
                if (evaluateIntArg2 < 0 || i3 > length) {
                    return new StringEval("");
                }
                return new StringEval(evaluateStringArg.substring(i3, Math.min(evaluateIntArg2 + i3, length)));
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function RIGHT = new LeftRight(false);
    public static final Function SEARCH = new SearchFind(false);
    public static final Function TEXT = new Fixed2ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            String[] strArr;
            int i3 = i;
            int i4 = i2;
            try {
                double evaluateDoubleArg = TextFunction.evaluateDoubleArg(valueEval, i3, i4);
                String evaluateStringArg = TextFunction.evaluateStringArg(valueEval2, i3, i4);
                if (evaluateStringArg.matches("[\\d,\\#,\\.,\\$,\\,]+")) {
                    return new StringEval(new DecimalFormat(evaluateStringArg).format(evaluateDoubleArg));
                }
                if (evaluateStringArg.indexOf(PackagingURIHelper.FORWARD_SLASH_STRING) != evaluateStringArg.lastIndexOf(PackagingURIHelper.FORWARD_SLASH_STRING) || evaluateStringArg.indexOf(PackagingURIHelper.FORWARD_SLASH_STRING) < 0 || evaluateStringArg.contains("-")) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(evaluateStringArg);
                        GregorianCalendar gregorianCalendar = new GregorianCalendar(1899, 11, 30, 0, 0, 0);
                        gregorianCalendar.add(5, (int) Math.floor(evaluateDoubleArg));
                        gregorianCalendar.add(14, (int) Math.round((evaluateDoubleArg - Math.floor(evaluateDoubleArg)) * 24.0d * 60.0d * 60.0d * 1000.0d));
                        return new StringEval(simpleDateFormat.format(gregorianCalendar.getTime()));
                    } catch (Exception unused) {
                        return ErrorEval.VALUE_INVALID;
                    }
                } else {
                    double floor = Math.floor(evaluateDoubleArg);
                    double d = evaluateDoubleArg - floor;
                    if (floor * d == 0.0d) {
                        return new StringEval("0");
                    }
                    String[] split = evaluateStringArg.split(" ");
                    if (split.length == 2) {
                        strArr = split[1].split(PackagingURIHelper.FORWARD_SLASH_STRING);
                    } else {
                        strArr = evaluateStringArg.split(PackagingURIHelper.FORWARD_SLASH_STRING);
                    }
                    if (strArr.length != 2) {
                        return ErrorEval.VALUE_INVALID;
                    }
                    double d2 = 10.0d;
                    double pow = Math.pow(10.0d, (double) strArr[1].length()) - 1.0d;
                    int pow2 = (int) (Math.pow(10.0d, (double) strArr[1].length()) - 1.0d);
                    double d3 = 1.0d;
                    double d4 = 0.0d;
                    while (pow2 > 0) {
                        double d5 = pow;
                        for (int pow3 = (int) (Math.pow(d2, (double) strArr[1].length()) - 1.0d); pow3 > 0; pow3--) {
                            double d6 = (double) pow3;
                            double d7 = d4;
                            double d8 = (double) pow2;
                            double d9 = (d6 / d8) - d;
                            if (d3 >= Math.abs(d9)) {
                                d3 = Math.abs(d9);
                                d5 = d8;
                                d4 = d6;
                            } else {
                                d4 = d7;
                            }
                        }
                        double d10 = d4;
                        pow2--;
                        pow = d5;
                        d2 = 10.0d;
                    }
                    double d11 = pow;
                    DecimalFormat decimalFormat = new DecimalFormat(strArr[0]);
                    DecimalFormat decimalFormat2 = new DecimalFormat(strArr[1]);
                    if (split.length == 2) {
                        DecimalFormat decimalFormat3 = new DecimalFormat(split[0]);
                        return new StringEval(decimalFormat3.format(floor) + " " + decimalFormat.format(d4) + PackagingURIHelper.FORWARD_SLASH_STRING + decimalFormat2.format(d11));
                    }
                    double d12 = d11;
                    return new StringEval(decimalFormat.format(d4 + (d12 * floor)) + PackagingURIHelper.FORWARD_SLASH_STRING + decimalFormat2.format(d12));
                }
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function TRIM = new SingleArgTextFunc() {
        /* access modifiers changed from: protected */
        public ValueEval evaluate(String str) {
            return new StringEval(str.trim());
        }
    };
    public static final Function UPPER = new SingleArgTextFunc() {
        /* access modifiers changed from: protected */
        public ValueEval evaluate(String str) {
            return new StringEval(str.toUpperCase());
        }
    };

    /* access modifiers changed from: protected */
    public abstract ValueEval evaluateFunc(ValueEval[] valueEvalArr, int i, int i2) throws EvaluationException;

    protected static final String evaluateStringArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToString(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    protected static final int evaluateIntArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    protected static final double evaluateDoubleArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        try {
            return evaluateFunc(valueEvalArr, i, i2);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static abstract class SingleArgTextFunc extends Fixed1ArgFunction {
        /* access modifiers changed from: protected */
        public abstract ValueEval evaluate(String str);

        protected SingleArgTextFunc() {
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            try {
                return evaluate(TextFunction.evaluateStringArg(valueEval, i, i2));
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    }

    private static final class LeftRight extends Var1or2ArgFunction {
        private static final ValueEval DEFAULT_ARG1 = new NumberEval(1.0d);
        private final boolean _isLeft;

        protected LeftRight(boolean z) {
            this._isLeft = z;
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            return evaluate(i, i2, valueEval, DEFAULT_ARG1);
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            String str;
            try {
                String evaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
                int evaluateIntArg = TextFunction.evaluateIntArg(valueEval2, i, i2);
                if (evaluateIntArg < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                if (this._isLeft) {
                    str = evaluateStringArg.substring(0, Math.min(evaluateStringArg.length(), evaluateIntArg));
                } else {
                    str = evaluateStringArg.substring(Math.max(0, evaluateStringArg.length() - evaluateIntArg));
                }
                return new StringEval(str);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    }

    private static final class SearchFind extends Var2or3ArgFunction {
        private final boolean _isCaseSensitive;

        public SearchFind(boolean z) {
            this._isCaseSensitive = z;
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                return eval(TextFunction.evaluateStringArg(valueEval2, i, i2), TextFunction.evaluateStringArg(valueEval, i, i2), 0);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }

        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
            try {
                String evaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
                String evaluateStringArg2 = TextFunction.evaluateStringArg(valueEval2, i, i2);
                int evaluateIntArg = TextFunction.evaluateIntArg(valueEval3, i, i2) - 1;
                if (evaluateIntArg < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                return eval(evaluateStringArg2, evaluateStringArg, evaluateIntArg);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }

        private ValueEval eval(String str, String str2, int i) {
            int i2;
            if (this._isCaseSensitive) {
                i2 = str.indexOf(str2, i);
            } else {
                i2 = str.toUpperCase().indexOf(str2.toUpperCase(), i);
            }
            if (i2 == -1) {
                return ErrorEval.VALUE_INVALID;
            }
            return new NumberEval((double) (i2 + 1));
        }
    }
}
