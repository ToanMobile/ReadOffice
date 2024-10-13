package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.ss.util.AreaReference;
import com.app.office.fc.ss.util.CellReference;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public abstract class AreaPtgBase extends OperandPtg implements AreaI {
    private static final BitField colRelative = BitFieldFactory.getInstance(16384);
    private static final BitField columnMask = BitFieldFactory.getInstance(16383);
    private static final BitField rowRelative = BitFieldFactory.getInstance(32768);
    private int field_1_first_row;
    private int field_2_last_row;
    private int field_3_first_column;
    private int field_4_last_column;

    public byte getDefaultOperandClass() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public final RuntimeException notImplemented() {
        return new RuntimeException("Coding Error: This method should never be called. This ptg should be converted");
    }

    protected AreaPtgBase() {
    }

    protected AreaPtgBase(AreaReference areaReference) {
        CellReference firstCell = areaReference.getFirstCell();
        CellReference lastCell = areaReference.getLastCell();
        setFirstRow(firstCell.getRow());
        setFirstColumn(firstCell.getCol() == -1 ? 0 : firstCell.getCol());
        setLastRow(lastCell.getRow());
        setLastColumn(lastCell.getCol() == -1 ? 255 : lastCell.getCol());
        setFirstColRelative(!firstCell.isColAbsolute());
        setLastColRelative(!lastCell.isColAbsolute());
        setFirstRowRelative(!firstCell.isRowAbsolute());
        setLastRowRelative(!lastCell.isRowAbsolute());
    }

    protected AreaPtgBase(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, boolean z4) {
        if (i2 > i) {
            setFirstRow(i);
            setLastRow(i2);
            setFirstRowRelative(z);
            setLastRowRelative(z2);
        } else {
            setFirstRow(i2);
            setLastRow(i);
            setFirstRowRelative(z2);
            setLastRowRelative(z);
        }
        if (i4 > i3) {
            setFirstColumn(i3);
            setLastColumn(i4);
            setFirstColRelative(z3);
            setLastColRelative(z4);
            return;
        }
        setFirstColumn(i4);
        setLastColumn(i3);
        setFirstColRelative(z4);
        setLastColRelative(z3);
    }

    /* access modifiers changed from: protected */
    public final void readCoordinates(LittleEndianInput littleEndianInput) {
        this.field_1_first_row = littleEndianInput.readUShort();
        this.field_2_last_row = littleEndianInput.readUShort();
        this.field_3_first_column = littleEndianInput.readUShort();
        this.field_4_last_column = littleEndianInput.readUShort();
    }

    /* access modifiers changed from: protected */
    public final void writeCoordinates(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_first_row);
        littleEndianOutput.writeShort(this.field_2_last_row);
        littleEndianOutput.writeShort(this.field_3_first_column);
        littleEndianOutput.writeShort(this.field_4_last_column);
    }

    public final int getFirstRow() {
        return this.field_1_first_row;
    }

    public final void setFirstRow(int i) {
        this.field_1_first_row = i;
    }

    public final int getLastRow() {
        return this.field_2_last_row;
    }

    public final void setLastRow(int i) {
        this.field_2_last_row = i;
    }

    public final int getFirstColumn() {
        return columnMask.getValue(this.field_3_first_column);
    }

    public final short getFirstColumnRaw() {
        return (short) this.field_3_first_column;
    }

    public final boolean isFirstRowRelative() {
        return rowRelative.isSet(this.field_3_first_column);
    }

    public final void setFirstRowRelative(boolean z) {
        this.field_3_first_column = rowRelative.setBoolean(this.field_3_first_column, z);
    }

    public final boolean isFirstColRelative() {
        return colRelative.isSet(this.field_3_first_column);
    }

    public final void setFirstColRelative(boolean z) {
        this.field_3_first_column = colRelative.setBoolean(this.field_3_first_column, z);
    }

    public final void setFirstColumn(int i) {
        this.field_3_first_column = columnMask.setValue(this.field_3_first_column, i);
    }

    public final void setFirstColumnRaw(int i) {
        this.field_3_first_column = i;
    }

    public final int getLastColumn() {
        return columnMask.getValue(this.field_4_last_column);
    }

    public final short getLastColumnRaw() {
        return (short) this.field_4_last_column;
    }

    public final boolean isLastRowRelative() {
        return rowRelative.isSet(this.field_4_last_column);
    }

    public final void setLastRowRelative(boolean z) {
        this.field_4_last_column = rowRelative.setBoolean(this.field_4_last_column, z);
    }

    public final boolean isLastColRelative() {
        return colRelative.isSet(this.field_4_last_column);
    }

    public final void setLastColRelative(boolean z) {
        this.field_4_last_column = colRelative.setBoolean(this.field_4_last_column, z);
    }

    public final void setLastColumn(int i) {
        this.field_4_last_column = columnMask.setValue(this.field_4_last_column, i);
    }

    public final void setLastColumnRaw(short s) {
        this.field_4_last_column = s;
    }

    /* access modifiers changed from: protected */
    public final String formatReferenceAsString() {
        CellReference cellReference = new CellReference(getFirstRow(), getFirstColumn(), !isFirstRowRelative(), !isFirstColRelative());
        CellReference cellReference2 = new CellReference(getLastRow(), getLastColumn(), !isLastRowRelative(), !isLastColRelative());
        if (AreaReference.isWholeColumnReference(cellReference, cellReference2)) {
            return new AreaReference(cellReference, cellReference2).formatAsString();
        }
        return cellReference.formatAsString() + ":" + cellReference2.formatAsString();
    }

    public String toFormulaString() {
        return formatReferenceAsString();
    }
}
