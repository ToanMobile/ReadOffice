package com.app.office.fc.hssf.formula.ptg;

public final class PercentPtg extends ValueOperatorPtg {
    private static final String PERCENT = "%";
    public static final int SIZE = 1;
    public static final ValueOperatorPtg instance = new PercentPtg();
    public static final byte sid = 20;

    public int getNumberOfOperands() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 20;
    }

    private PercentPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(PERCENT);
        return stringBuffer.toString();
    }
}
