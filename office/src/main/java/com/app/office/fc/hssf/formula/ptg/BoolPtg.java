package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class BoolPtg extends ScalarConstantPtg {
    private static final BoolPtg FALSE = new BoolPtg(false);
    public static final int SIZE = 2;
    private static final BoolPtg TRUE = new BoolPtg(true);
    public static final byte sid = 29;
    private final boolean _value;

    public int getSize() {
        return 2;
    }

    private BoolPtg(boolean z) {
        this._value = z;
    }

    public static BoolPtg valueOf(boolean z) {
        return z ? TRUE : FALSE;
    }

    public static BoolPtg read(LittleEndianInput littleEndianInput) {
        boolean z = true;
        if (littleEndianInput.readByte() != 1) {
            z = false;
        }
        return valueOf(z);
    }

    public boolean getValue() {
        return this._value;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 29);
        littleEndianOutput.writeByte(this._value ? 1 : 0);
    }

    public String toFormulaString() {
        return this._value ? "TRUE" : "FALSE";
    }
}
