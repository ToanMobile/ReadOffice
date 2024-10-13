package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianOutput;

public class UnknownPtg extends Ptg {
    private final int _sid;
    private short size = 1;

    public byte getDefaultOperandClass() {
        return 32;
    }

    public boolean isBaseToken() {
        return true;
    }

    public String toFormulaString() {
        return "UNKNOWN";
    }

    public UnknownPtg(int i) {
        this._sid = i;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(this._sid);
    }

    public int getSize() {
        return this.size;
    }
}
