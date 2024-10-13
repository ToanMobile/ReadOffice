package com.app.office.fc.hssf.formula.ptg;

public final class UnaryMinusPtg extends ValueOperatorPtg {
    private static final String MINUS = "-";
    public static final ValueOperatorPtg instance = new UnaryMinusPtg();
    public static final byte sid = 19;

    public int getNumberOfOperands() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 19;
    }

    private UnaryMinusPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(MINUS);
        stringBuffer.append(strArr[0]);
        return stringBuffer.toString();
    }
}
