package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class ExpPtg extends ControlPtg {
    private static final int SIZE = 5;
    public static final short sid = 1;
    private final int field_1_first_row;
    private final int field_2_first_col;

    public int getSize() {
        return 5;
    }

    public ExpPtg(LittleEndianInput littleEndianInput) {
        this.field_1_first_row = littleEndianInput.readShort();
        this.field_2_first_col = littleEndianInput.readShort();
    }

    public ExpPtg(int i, int i2) {
        this.field_1_first_row = i;
        this.field_2_first_col = i2;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 1);
        littleEndianOutput.writeShort(this.field_1_first_row);
        littleEndianOutput.writeShort(this.field_2_first_col);
    }

    public int getRow() {
        return this.field_1_first_row;
    }

    public int getColumn() {
        return this.field_2_first_col;
    }

    public String toFormulaString() {
        throw new RuntimeException("Coding Error: Expected ExpPtg to be converted from Shared to Non-Shared Formula by ValueRecordsAggregate, but it wasn't");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[Array Formula or Shared Formula]\n");
        stringBuffer.append("row = ");
        stringBuffer.append(getRow());
        stringBuffer.append("\n");
        stringBuffer.append("col = ");
        stringBuffer.append(getColumn());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}
