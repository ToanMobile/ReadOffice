package com.app.office.fc.hssf.formula.ptg;

public final class NotEqualPtg extends ValueOperatorPtg {
    public static final ValueOperatorPtg instance = new NotEqualPtg();
    public static final byte sid = 14;

    public int getNumberOfOperands() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 14;
    }

    private NotEqualPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append("<>");
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
