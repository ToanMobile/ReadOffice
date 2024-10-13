package com.app.office.fc.hssf.formula.ptg;

public final class ConcatPtg extends ValueOperatorPtg {
    private static final String CONCAT = "&";
    public static final ValueOperatorPtg instance = new ConcatPtg();
    public static final byte sid = 8;

    public int getNumberOfOperands() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 8;
    }

    private ConcatPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(CONCAT);
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
