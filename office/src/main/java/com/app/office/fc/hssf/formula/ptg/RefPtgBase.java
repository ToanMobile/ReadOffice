package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.ss.util.CellReference;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public abstract class RefPtgBase extends OperandPtg {
    private static final BitField colRelative = BitFieldFactory.getInstance(16384);
    private static final BitField column = BitFieldFactory.getInstance(16383);
    private static final BitField rowRelative = BitFieldFactory.getInstance(32768);
    private int field_1_row;
    private int field_2_col;

    public final byte getDefaultOperandClass() {
        return 0;
    }

    protected RefPtgBase() {
    }

    protected RefPtgBase(CellReference cellReference) {
        setRow(cellReference.getRow());
        setColumn(cellReference.getCol());
        setColRelative(!cellReference.isColAbsolute());
        setRowRelative(!cellReference.isRowAbsolute());
    }

    /* access modifiers changed from: protected */
    public final void readCoordinates(LittleEndianInput littleEndianInput) {
        this.field_1_row = littleEndianInput.readUShort();
        this.field_2_col = littleEndianInput.readUShort();
    }

    /* access modifiers changed from: protected */
    public final void writeCoordinates(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_row);
        littleEndianOutput.writeShort(this.field_2_col);
    }

    public final void setRow(int i) {
        this.field_1_row = i;
    }

    public final int getRow() {
        return this.field_1_row;
    }

    public final boolean isRowRelative() {
        return rowRelative.isSet(this.field_2_col);
    }

    public final void setRowRelative(boolean z) {
        this.field_2_col = rowRelative.setBoolean(this.field_2_col, z);
    }

    public final boolean isColRelative() {
        return colRelative.isSet(this.field_2_col);
    }

    public final void setColRelative(boolean z) {
        this.field_2_col = colRelative.setBoolean(this.field_2_col, z);
    }

    public final void setColumn(int i) {
        this.field_2_col = column.setValue(this.field_2_col, i);
    }

    public final int getColumn() {
        return column.getValue(this.field_2_col);
    }

    /* access modifiers changed from: protected */
    public final String formatReferenceAsString() {
        return new CellReference(getRow(), getColumn(), !isRowRelative(), !isColRelative()).formatAsString();
    }
}
