package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.ss.usermodel.ErrorConstants;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class RefErrorPtg extends OperandPtg {
    private static final int SIZE = 5;
    public static final byte sid = 42;
    private int field_1_reserved;

    public byte getDefaultOperandClass() {
        return 0;
    }

    public int getSize() {
        return 5;
    }

    public RefErrorPtg() {
        this.field_1_reserved = 0;
    }

    public RefErrorPtg(LittleEndianInput littleEndianInput) {
        this.field_1_reserved = littleEndianInput.readInt();
    }

    public String toString() {
        return getClass().getName();
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 42);
        littleEndianOutput.writeInt(this.field_1_reserved);
    }

    public String toFormulaString() {
        return ErrorConstants.getText(23);
    }
}
