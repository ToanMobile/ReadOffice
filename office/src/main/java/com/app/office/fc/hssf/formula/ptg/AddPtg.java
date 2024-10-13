package com.app.office.fc.hssf.formula.ptg;

public final class AddPtg extends ValueOperatorPtg {
    private static final String ADD = "+";
    public static final ValueOperatorPtg instance = new AddPtg();
    public static final byte sid = 3;

    public int getNumberOfOperands() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 3;
    }

    private AddPtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(ADD);
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
