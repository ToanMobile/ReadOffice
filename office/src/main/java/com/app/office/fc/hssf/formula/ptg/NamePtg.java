package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hssf.formula.FormulaRenderingWorkbook;
import com.app.office.fc.hssf.formula.WorkbookDependentFormula;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class NamePtg extends OperandPtg implements WorkbookDependentFormula {
    private static final int SIZE = 5;
    public static final short sid = 35;
    private int field_1_label_index;
    private short field_2_zero;

    public byte getDefaultOperandClass() {
        return 0;
    }

    public int getSize() {
        return 5;
    }

    public NamePtg(int i) {
        this.field_1_label_index = i + 1;
    }

    public NamePtg(LittleEndianInput littleEndianInput) {
        this.field_1_label_index = littleEndianInput.readShort();
        this.field_2_zero = littleEndianInput.readShort();
    }

    public int getIndex() {
        return this.field_1_label_index - 1;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + Field.QUOTE);
        littleEndianOutput.writeShort(this.field_1_label_index);
        littleEndianOutput.writeShort(this.field_2_zero);
    }

    public String toFormulaString(FormulaRenderingWorkbook formulaRenderingWorkbook) {
        return formulaRenderingWorkbook.getNameText(this);
    }

    public String toFormulaString() {
        throw new RuntimeException("3D references need a workbook to determine formula text");
    }
}
