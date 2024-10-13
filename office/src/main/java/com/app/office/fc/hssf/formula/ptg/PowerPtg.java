package com.app.office.fc.hssf.formula.ptg;

public final class PowerPtg extends ValueOperatorPtg {
    public static final ValueOperatorPtg instance = new PowerPtg();
    public static final byte sid = 7;

    public int getNumberOfOperands() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 7;
    }

    private PowerPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append("^");
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
