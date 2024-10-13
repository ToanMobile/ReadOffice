package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianOutput;

public final class RangePtg extends OperationPtg {
    public static final int SIZE = 1;
    public static final OperationPtg instance = new RangePtg();
    public static final byte sid = 17;

    public int getNumberOfOperands() {
        return 2;
    }

    public int getSize() {
        return 1;
    }

    public final boolean isBaseToken() {
        return true;
    }

    public String toFormulaString() {
        return ":";
    }

    private RangePtg() {
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 17);
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(":");
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
