package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hssf.formula.FormulaRenderingWorkbook;
import com.app.office.fc.hssf.formula.WorkbookDependentFormula;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class NameXPtg extends OperandPtg implements WorkbookDependentFormula {
    private static final int SIZE = 7;
    public static final short sid = 57;
    private final int _nameNumber;
    private final int _reserved;
    private final int _sheetRefIndex;

    public byte getDefaultOperandClass() {
        return 32;
    }

    public int getSize() {
        return 7;
    }

    private NameXPtg(int i, int i2, int i3) {
        this._sheetRefIndex = i;
        this._nameNumber = i2;
        this._reserved = i3;
    }

    public NameXPtg(int i, int i2) {
        this(i, i2 + 1, 0);
    }

    public NameXPtg(LittleEndianInput littleEndianInput) {
        this(littleEndianInput.readUShort(), littleEndianInput.readUShort(), littleEndianInput.readUShort());
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + Field.SYMBOL);
        littleEndianOutput.writeShort(this._sheetRefIndex);
        littleEndianOutput.writeShort(this._nameNumber);
        littleEndianOutput.writeShort(this._reserved);
    }

    public String toFormulaString(FormulaRenderingWorkbook formulaRenderingWorkbook) {
        return formulaRenderingWorkbook.resolveNameXText(this);
    }

    public String toFormulaString() {
        throw new RuntimeException("3D references need a workbook to determine formula text");
    }

    public String toString() {
        return "NameXPtg:[sheetRefIndex:" + this._sheetRefIndex + " , nameNumber:" + this._nameNumber + "]";
    }

    public int getSheetRefIndex() {
        return this._sheetRefIndex;
    }

    public int getNameIndex() {
        return this._nameNumber - 1;
    }
}
