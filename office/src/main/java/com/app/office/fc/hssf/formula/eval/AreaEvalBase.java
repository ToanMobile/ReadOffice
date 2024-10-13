package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.ptg.AreaI;

public abstract class AreaEvalBase implements AreaEval {
    private final int _firstColumn;
    private final int _firstRow;
    private final int _lastColumn;
    private final int _lastRow;
    private final int _nColumns;
    private final int _nRows;

    public abstract ValueEval getRelativeValue(int i, int i2);

    public boolean isSubTotal(int i, int i2) {
        return false;
    }

    protected AreaEvalBase(int i, int i2, int i3, int i4) {
        this._firstColumn = i2;
        this._firstRow = i;
        this._lastColumn = i4;
        this._lastRow = i3;
        this._nColumns = (i4 - i2) + 1;
        this._nRows = (i3 - i) + 1;
    }

    protected AreaEvalBase(AreaI areaI) {
        int firstRow = areaI.getFirstRow();
        this._firstRow = firstRow;
        int firstColumn = areaI.getFirstColumn();
        this._firstColumn = firstColumn;
        int lastRow = areaI.getLastRow();
        this._lastRow = lastRow;
        int lastColumn = areaI.getLastColumn();
        this._lastColumn = lastColumn;
        this._nColumns = (lastColumn - firstColumn) + 1;
        this._nRows = (lastRow - firstRow) + 1;
    }

    public final int getFirstColumn() {
        return this._firstColumn;
    }

    public final int getFirstRow() {
        return this._firstRow;
    }

    public final int getLastColumn() {
        return this._lastColumn;
    }

    public final int getLastRow() {
        return this._lastRow;
    }

    public final ValueEval getAbsoluteValue(int i, int i2) {
        int i3 = i - this._firstRow;
        int i4 = i2 - this._firstColumn;
        if (i3 < 0 || i3 >= this._nRows) {
            throw new IllegalArgumentException("Specified row index (" + i + ") is outside the allowed range (" + this._firstRow + ".." + this._lastRow + ")");
        } else if (i4 >= 0 && i4 < this._nColumns) {
            return getRelativeValue(i3, i4);
        } else {
            throw new IllegalArgumentException("Specified column index (" + i2 + ") is outside the allowed range (" + this._firstColumn + ".." + i2 + ")");
        }
    }

    public final boolean contains(int i, int i2) {
        return this._firstRow <= i && this._lastRow >= i && this._firstColumn <= i2 && this._lastColumn >= i2;
    }

    public final boolean containsRow(int i) {
        return this._firstRow <= i && this._lastRow >= i;
    }

    public final boolean containsColumn(int i) {
        return this._firstColumn <= i && this._lastColumn >= i;
    }

    public final boolean isColumn() {
        return this._firstColumn == this._lastColumn;
    }

    public final boolean isRow() {
        return this._firstRow == this._lastRow;
    }

    public int getHeight() {
        return (this._lastRow - this._firstRow) + 1;
    }

    public final ValueEval getValue(int i, int i2) {
        return getRelativeValue(i, i2);
    }

    public int getWidth() {
        return (this._lastColumn - this._firstColumn) + 1;
    }
}
