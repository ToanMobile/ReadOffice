package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianOutput;

public final class IntersectionPtg extends OperationPtg {
    public static final OperationPtg instance = new IntersectionPtg();
    public static final byte sid = 15;

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
        return " ";
    }

    private IntersectionPtg() {
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 15);
    }

    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(" ");
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
