package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.StringPtg;

public final class StringEval implements StringValueEval {
    public static final StringEval EMPTY_INSTANCE = new StringEval("");
    private final String _value;

    public StringEval(Ptg ptg) {
        this(((StringPtg) ptg).getValue());
    }

    public StringEval(String str) {
        if (str != null) {
            this._value = str;
            return;
        }
        throw new IllegalArgumentException("value must not be null");
    }

    public String getStringValue() {
        return this._value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append(" [");
        sb.append(this._value);
        sb.append("]");
        return sb.toString();
    }
}
