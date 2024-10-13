package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public abstract class LogicalFunction extends Fixed1ArgFunction {
    public static final Function ISBLANK = new LogicalFunction() {
        /* access modifiers changed from: protected */
        public boolean evaluate(ValueEval valueEval) {
            return valueEval instanceof BlankEval;
        }
    };
    public static final Function ISERROR = new LogicalFunction() {
        /* access modifiers changed from: protected */
        public boolean evaluate(ValueEval valueEval) {
            return valueEval instanceof ErrorEval;
        }
    };
    public static final Function ISLOGICAL = new LogicalFunction() {
        /* access modifiers changed from: protected */
        public boolean evaluate(ValueEval valueEval) {
            return valueEval instanceof BoolEval;
        }
    };
    public static final Function ISNA = new LogicalFunction() {
        /* access modifiers changed from: protected */
        public boolean evaluate(ValueEval valueEval) {
            return valueEval == ErrorEval.NA;
        }
    };
    public static final Function ISNONTEXT = new LogicalFunction() {
        /* access modifiers changed from: protected */
        public boolean evaluate(ValueEval valueEval) {
            return !(valueEval instanceof StringEval);
        }
    };
    public static final Function ISNUMBER = new LogicalFunction() {
        /* access modifiers changed from: protected */
        public boolean evaluate(ValueEval valueEval) {
            return valueEval instanceof NumberEval;
        }
    };
    public static final Function ISREF = new Fixed1ArgFunction() {
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            if ((valueEval instanceof RefEval) || (valueEval instanceof AreaEval)) {
                return BoolEval.TRUE;
            }
            return BoolEval.FALSE;
        }
    };
    public static final Function ISTEXT = new LogicalFunction() {
        /* access modifiers changed from: protected */
        public boolean evaluate(ValueEval valueEval) {
            return valueEval instanceof StringEval;
        }
    };

    /* access modifiers changed from: protected */
    public abstract boolean evaluate(ValueEval valueEval);

    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        ValueEval valueEval2;
        try {
            valueEval2 = OperandResolver.getSingleValue(valueEval, i, i2);
        } catch (EvaluationException e) {
            valueEval2 = e.getErrorEval();
        }
        return BoolEval.valueOf(evaluate(valueEval2));
    }
}
