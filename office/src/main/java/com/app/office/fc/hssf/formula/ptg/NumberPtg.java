package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.ss.util.NumberToTextConverter;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class NumberPtg extends ScalarConstantPtg {
    public static final int SIZE = 9;
    public static final byte sid = 31;
    private final double field_1_value;

    public int getSize() {
        return 9;
    }

    public NumberPtg(LittleEndianInput littleEndianInput) {
        this(littleEndianInput.readDouble());
    }

    public NumberPtg(String str) {
        this(Double.parseDouble(str));
    }

    public NumberPtg(double d) {
        this.field_1_value = d;
    }

    public double getValue() {
        return this.field_1_value;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 31);
        littleEndianOutput.writeDouble(getValue());
    }

    public String toFormulaString() {
        return NumberToTextConverter.toText(this.field_1_value);
    }
}
