package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianOutput;

public final class ParenthesisPtg extends ControlPtg {
    private static final int SIZE = 1;
    public static final ControlPtg instance = new ParenthesisPtg();
    public static final byte sid = 21;

    public int getSize() {
        return 1;
    }

    public String toFormulaString() {
        return "()";
    }

    private ParenthesisPtg() {
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 21);
    }

    public String toFormulaString(String[] strArr) {
        return "(" + strArr[0] + ")";
    }
}
