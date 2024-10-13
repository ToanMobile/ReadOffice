package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.ptg.IntPtg;
import com.app.office.fc.hssf.formula.ptg.NumberPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.ss.util.NumberToTextConverter;

public final class NumberEval implements NumericValueEval, StringValueEval {
    public static final NumberEval ZERO = new NumberEval(0.0d);
    private String _stringValue;
    private final double _value;

    public NumberEval(Ptg ptg) {
        if (ptg == null) {
            throw new IllegalArgumentException("ptg must not be null");
        } else if (ptg instanceof IntPtg) {
            this._value = (double) ((IntPtg) ptg).getValue();
        } else if (ptg instanceof NumberPtg) {
            this._value = ((NumberPtg) ptg).getValue();
        } else {
            throw new IllegalArgumentException("bad argument type (" + ptg.getClass().getName() + ")");
        }
    }

    public NumberEval(double d) {
        this._value = d;
    }

    public double getNumberValue() {
        return this._value;
    }

    public String getStringValue() {
        if (this._stringValue == null) {
            this._stringValue = NumberToTextConverter.toText(this._value);
        }
        return this._stringValue;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(getStringValue());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
