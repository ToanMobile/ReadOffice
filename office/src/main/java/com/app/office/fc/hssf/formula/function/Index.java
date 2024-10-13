package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.MissingArgEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Index implements Function2Arg, Function3Arg, Function4Arg {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        TwoDEval convertFirstArg = convertFirstArg(valueEval);
        try {
            int resolveIndexArg = resolveIndexArg(valueEval2, i, i2);
            int i3 = 0;
            if (!convertFirstArg.isColumn()) {
                if (!convertFirstArg.isRow()) {
                    return ErrorEval.REF_INVALID;
                }
                i3 = resolveIndexArg;
                resolveIndexArg = 0;
            }
            return getValueFromArea(convertFirstArg, resolveIndexArg, i3);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            return getValueFromArea(convertFirstArg(valueEval), resolveIndexArg(valueEval2, i, i2), resolveIndexArg(valueEval3, i, i2));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3, ValueEval valueEval4) {
        throw new RuntimeException("Incomplete code - don't know how to support the 'area_num' parameter yet)");
    }

    private static TwoDEval convertFirstArg(ValueEval valueEval) {
        if (valueEval instanceof RefEval) {
            return ((RefEval) valueEval).offset(0, 0, 0, 0);
        }
        if (valueEval instanceof TwoDEval) {
            return (TwoDEval) valueEval;
        }
        throw new RuntimeException("Incomplete code - cannot handle first arg of type (" + valueEval.getClass().getName() + ")");
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length;
        if (length == 2) {
            return evaluate(i, i2, valueEvalArr[0], valueEvalArr[1]);
        }
        if (length == 3) {
            return evaluate(i, i2, valueEvalArr[0], valueEvalArr[1], valueEvalArr[2]);
        } else if (length != 4) {
            return ErrorEval.VALUE_INVALID;
        } else {
            return evaluate(i, i2, valueEvalArr[0], valueEvalArr[1], valueEvalArr[2], valueEvalArr[3]);
        }
    }

    private static ValueEval getValueFromArea(TwoDEval twoDEval, int i, int i2) throws EvaluationException {
        TwoDEval twoDEval2;
        if (i == 0) {
            twoDEval2 = twoDEval;
        } else if (i <= twoDEval.getHeight()) {
            twoDEval2 = twoDEval.getRow(i - 1);
        } else {
            throw new EvaluationException(ErrorEval.REF_INVALID);
        }
        if (i2 == 0) {
            return twoDEval2;
        }
        if (i2 <= twoDEval.getWidth()) {
            return twoDEval2.getColumn(i2 - 1);
        }
        throw new EvaluationException(ErrorEval.REF_INVALID);
    }

    private static int resolveIndexArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
        if (singleValue == MissingArgEval.instance || singleValue == BlankEval.instance) {
            return 0;
        }
        int coerceValueToInt = OperandResolver.coerceValueToInt(singleValue);
        if (coerceValueToInt >= 0) {
            return coerceValueToInt;
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }
}
