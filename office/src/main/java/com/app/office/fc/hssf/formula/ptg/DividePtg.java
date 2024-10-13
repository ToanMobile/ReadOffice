package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.openxml4j.opc.PackagingURIHelper;

public final class DividePtg extends ValueOperatorPtg {
    public static final ValueOperatorPtg instance = new DividePtg();
    public static final byte sid = 6;

    public int getNumberOfOperands() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 6;
    }

    private DividePtg() {
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(PackagingURIHelper.FORWARD_SLASH_STRING);
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
