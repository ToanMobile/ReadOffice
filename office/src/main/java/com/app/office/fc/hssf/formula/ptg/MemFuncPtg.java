package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class MemFuncPtg extends OperandPtg {
    public static final byte sid = 41;
    private final int field_1_len_ref_subexpression;

    public byte getDefaultOperandClass() {
        return 0;
    }

    public int getSize() {
        return 3;
    }

    public String toFormulaString() {
        return "";
    }

    public MemFuncPtg(LittleEndianInput littleEndianInput) {
        this(littleEndianInput.readUShort());
    }

    public MemFuncPtg(int i) {
        this.field_1_len_ref_subexpression = i;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 41);
        littleEndianOutput.writeShort(this.field_1_len_ref_subexpression);
    }

    public int getNumberOfOperands() {
        return this.field_1_len_ref_subexpression;
    }

    public int getLenRefSubexpression() {
        return this.field_1_len_ref_subexpression;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [len=");
        stringBuffer.append(this.field_1_len_ref_subexpression);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
