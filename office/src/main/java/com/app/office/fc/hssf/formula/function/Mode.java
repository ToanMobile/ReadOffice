package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Mode implements Function {
    public static double evaluate(double[] dArr) throws EvaluationException {
        if (dArr.length >= 2) {
            int length = dArr.length;
            int[] iArr = new int[length];
            Arrays.fill(iArr, 1);
            int length2 = dArr.length;
            int i = 0;
            while (i < length2) {
                int i2 = i + 1;
                int length3 = dArr.length;
                for (int i3 = i2; i3 < length3; i3++) {
                    if (dArr[i] == dArr[i3]) {
                        iArr[i] = iArr[i] + 1;
                    }
                }
                i = i2;
            }
            double d = 0.0d;
            int i4 = 0;
            for (int i5 = 0; i5 < length; i5++) {
                if (iArr[i5] > i4) {
                    d = dArr[i5];
                    i4 = iArr[i5];
                }
            }
            if (i4 > 1) {
                return d;
            }
            throw new EvaluationException(ErrorEval.NA);
        }
        throw new EvaluationException(ErrorEval.NA);
    }

    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        try {
            ArrayList arrayList = new ArrayList();
            for (ValueEval collectValues : valueEvalArr) {
                collectValues(collectValues, arrayList);
            }
            int size = arrayList.size();
            double[] dArr = new double[size];
            for (int i3 = 0; i3 < size; i3++) {
                dArr[i3] = ((Double) arrayList.get(i3)).doubleValue();
            }
            return new NumberEval(evaluate(dArr));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static void collectValues(ValueEval valueEval, List<Double> list) throws EvaluationException {
        if (valueEval instanceof TwoDEval) {
            TwoDEval twoDEval = (TwoDEval) valueEval;
            int width = twoDEval.getWidth();
            int height = twoDEval.getHeight();
            for (int i = 0; i < height; i++) {
                for (int i2 = 0; i2 < width; i2++) {
                    collectValue(twoDEval.getValue(i, i2), list, false);
                }
            }
        } else if (valueEval instanceof RefEval) {
            collectValue(((RefEval) valueEval).getInnerValueEval(), list, true);
        } else {
            collectValue(valueEval, list, true);
        }
    }

    private static void collectValue(ValueEval valueEval, List<Double> list, boolean z) throws EvaluationException {
        if (valueEval instanceof ErrorEval) {
            throw new EvaluationException((ErrorEval) valueEval);
        } else if (valueEval == BlankEval.instance || (valueEval instanceof BoolEval) || (valueEval instanceof StringEval)) {
            if (z) {
                throw EvaluationException.invalidValue();
            }
        } else if (valueEval instanceof NumberEval) {
            list.add(new Double(((NumberEval) valueEval).getNumberValue()));
        } else {
            throw new RuntimeException("Unexpected value type (" + valueEval.getClass().getName() + ")");
        }
    }
}
