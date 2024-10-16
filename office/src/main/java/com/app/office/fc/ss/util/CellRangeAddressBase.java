package com.app.office.fc.ss.util;

import com.app.office.fc.ss.SpreadsheetVersion;

public abstract class CellRangeAddressBase {
    private int _firstCol;
    private int _firstRow;
    private int _lastCol;
    private int _lastRow;

    protected CellRangeAddressBase(int i, int i2, int i3, int i4) {
        this._firstRow = i;
        this._lastRow = i2;
        this._firstCol = i3;
        this._lastCol = i4;
    }

    public void validate(SpreadsheetVersion spreadsheetVersion) {
        validateRow(this._firstRow, spreadsheetVersion);
        validateRow(this._lastRow, spreadsheetVersion);
        validateColumn(this._firstCol, spreadsheetVersion);
        validateColumn(this._lastCol, spreadsheetVersion);
    }

    private static void validateRow(int i, SpreadsheetVersion spreadsheetVersion) {
        int lastRowIndex = spreadsheetVersion.getLastRowIndex();
        if (i > lastRowIndex) {
            throw new IllegalArgumentException("Maximum row number is " + lastRowIndex);
        } else if (i < 0) {
            throw new IllegalArgumentException("Minumum row number is 0");
        }
    }

    private static void validateColumn(int i, SpreadsheetVersion spreadsheetVersion) {
        int lastColumnIndex = spreadsheetVersion.getLastColumnIndex();
        if (i > lastColumnIndex) {
            throw new IllegalArgumentException("Maximum column number is " + lastColumnIndex);
        } else if (i < 0) {
            throw new IllegalArgumentException("Minimum column number is 0");
        }
    }

    public final boolean isFullColumnRange() {
        return this._firstRow == 0 && this._lastRow == SpreadsheetVersion.EXCEL97.getLastRowIndex();
    }

    public final boolean isFullRowRange() {
        return this._firstCol == 0 && this._lastCol == SpreadsheetVersion.EXCEL97.getLastColumnIndex();
    }

    public final int getFirstColumn() {
        return this._firstCol;
    }

    public final int getFirstRow() {
        return this._firstRow;
    }

    public final int getLastColumn() {
        return this._lastCol;
    }

    public final int getLastRow() {
        return this._lastRow;
    }

    public boolean isInRange(int i, int i2) {
        return this._firstRow <= i && i <= this._lastRow && this._firstCol <= i2 && i2 <= this._lastCol;
    }

    public final void setFirstColumn(int i) {
        this._firstCol = i;
    }

    public final void setFirstRow(int i) {
        this._firstRow = i;
    }

    public final void setLastColumn(int i) {
        this._lastCol = i;
    }

    public final void setLastRow(int i) {
        this._lastRow = i;
    }

    public int getNumberOfCells() {
        return ((this._lastRow - this._firstRow) + 1) * ((this._lastCol - this._firstCol) + 1);
    }

    public final String toString() {
        CellReference cellReference = new CellReference(this._firstRow, this._firstCol);
        CellReference cellReference2 = new CellReference(this._lastRow, this._lastCol);
        return getClass().getName() + " [" + cellReference.formatAsString() + ":" + cellReference2.formatAsString() + "]";
    }
}
