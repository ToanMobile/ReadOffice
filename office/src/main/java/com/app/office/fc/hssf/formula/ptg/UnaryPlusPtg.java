package com.app.office.fc.hssf.formula.ptg;

public final class UnaryPlusPtg extends ValueOperatorPtg {
    private static final String ADD = "+";
    public static final ValueOperatorPtg instance = new UnaryPlusPtg();
    public static final byte sid = 18;

    public int getNumberOfOperands() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 18;
    }

    private UnaryPlusPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ADD);
        stringBuffer.append(strArr[0]);
        return stringBuffer.toString();
    }
}
