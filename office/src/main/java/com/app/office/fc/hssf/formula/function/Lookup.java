package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.LookupUtils;

public final class Lookup extends Var2or3ArgFunction {
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        throw new RuntimeException("Two arg version of LOOKUP not supported yet");
    }

    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
            TwoDEval resolveTableArrayArg = LookupUtils.resolveTableArrayArg(valueEval2);
            TwoDEval resolveTableArrayArg2 = LookupUtils.resolveTableArrayArg(valueEval3);
            LookupUtils.ValueVector createVector = createVector(resolveTableArrayArg);
            LookupUtils.ValueVector createVector2 = createVector(resolveTableArrayArg2);
            if (createVector.getSize() <= createVector2.getSize()) {
                int lookupIndexOfValue = LookupUtils.lookupIndexOfValue(singleValue, createVector, true);
                if (lookupIndexOfValue >= 0) {
                    return createVector2.getItem(lookupIndexOfValue);
                }
                return null;
            }
            throw new RuntimeException("Lookup vector and result vector of differing sizes not supported yet");
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static LookupUtils.ValueVector createVector(TwoDEval twoDEval) {
        LookupUtils.ValueVector createVector = LookupUtils.createVector(twoDEval);
        if (createVector != null) {
            return createVector;
        }
        throw new RuntimeException("non-vector lookup or result areas not supported yet");
    }
}
