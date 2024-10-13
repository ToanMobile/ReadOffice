package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianOutput;

public final class MissingArgPtg extends ScalarConstantPtg {
    private static final int SIZE = 1;
    public static final Ptg instance = new MissingArgPtg();
    public static final byte sid = 22;

    public int getSize() {
        return 1;
    }

    public String toFormulaString() {
        return " ";
    }

    private MissingArgPtg() {
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 22);
    }
}
