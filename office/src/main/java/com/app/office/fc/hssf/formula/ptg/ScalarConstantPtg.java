package com.app.office.fc.hssf.formula.ptg;

public abstract class ScalarConstantPtg extends Ptg {
    public final byte getDefaultOperandClass() {
        return 32;
    }

    public final boolean isBaseToken() {
        return true;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(toFormulaString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
