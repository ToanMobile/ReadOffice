package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class MemAreaPtg extends OperandPtg {
    private static final int SIZE = 7;
    public static final short sid = 38;
    private final int field_1_reserved;
    private final int field_2_subex_len;

    public byte getDefaultOperandClass() {
        return 32;
    }

    public int getSize() {
        return 7;
    }

    public String toFormulaString() {
        return "";
    }

    public MemAreaPtg(int i) {
        this.field_1_reserved = 0;
        this.field_2_subex_len = i;
    }

    public MemAreaPtg(LittleEndianInput littleEndianInput) {
        this.field_1_reserved = littleEndianInput.readInt();
        this.field_2_subex_len = littleEndianInput.readShort();
    }

    public int getLenRefSubexpression() {
        return this.field_2_subex_len;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + Field.ASK);
        littleEndianOutput.writeInt(this.field_1_reserved);
        littleEndianOutput.writeShort(this.field_2_subex_len);
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [len=");
        stringBuffer.append(this.field_2_subex_len);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
