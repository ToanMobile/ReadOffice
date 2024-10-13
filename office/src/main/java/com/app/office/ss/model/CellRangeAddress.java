package com.app.office.ss.model;

public class CellRangeAddress {
    private int firstCol;
    private int firstRow;
    private int lastCol;
    private int lastRow;

    public void dispose() {
    }

    public CellRangeAddress(int i, int i2, int i3, int i4) {
        this.firstRow = i;
        this.firstCol = i2;
        this.lastRow = i3;
        this.lastCol = i4;
    }

    public int getFirstRow() {
        return this.firstRow;
    }

    public void setFirstRow(int i) {
        this.firstRow = i;
    }

    public int getFirstColumn() {
        return this.firstCol;
    }

    public void setFirstColumn(int i) {
        this.firstCol = i;
    }

    public int getLastRow() {
        return this.lastRow;
    }

    public void setLastRow(int i) {
        this.lastRow = i;
    }

    public int getLastColumn() {
        return this.lastCol;
    }

    public void setLastColumn(int i) {
        this.lastCol = i;
    }

    public boolean isInRange(int i, int i2) {
        return this.firstRow <= i && i <= this.lastRow && this.firstCol <= i2 && i2 <= this.lastCol;
    }
}
