package com.app.office.fc.hssf.formula.ptg;

public final class LessThanPtg extends ValueOperatorPtg {
    private static final String LESSTHAN = "<";
    public static final ValueOperatorPtg instance = new LessThanPtg();
    public static final byte sid = 9;

    public int getNumberOfOperands() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 9;
    }

    private LessThanPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(LESSTHAN);
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
