package com.app.office.common.shape;

public class TableShape extends AbstractShape {
    private boolean bandCol = false;
    private boolean bandRow = false;
    private TableCell[] cells;
    private int colCnt;
    private boolean firstCol = false;
    private boolean firstRow = false;
    private boolean lastCol = false;
    private boolean lastRow = false;
    private int rowCnt;
    private boolean shape07 = true;

    public short getType() {
        return 6;
    }

    public TableShape(int i, int i2) {
        if (i < 1) {
            throw new IllegalArgumentException("The number of rows must be greater than 1");
        } else if (i2 >= 1) {
            this.rowCnt = i;
            this.colCnt = i2;
            this.cells = new TableCell[(i * i2)];
        } else {
            throw new IllegalArgumentException("The number of columns must be greater than 1");
        }
    }

    public TableCell getCell(int i) {
        TableCell[] tableCellArr = this.cells;
        if (i >= tableCellArr.length) {
            return null;
        }
        return tableCellArr[i];
    }

    public void addCell(int i, TableCell tableCell) {
        this.cells[i] = tableCell;
    }

    public int getCellCount() {
        return this.cells.length;
    }

    public void setTable07(boolean z) {
        this.shape07 = z;
    }

    public boolean isTable07() {
        return this.shape07;
    }

    public boolean isFirstRow() {
        return this.firstRow;
    }

    public void setFirstRow(boolean z) {
        this.firstRow = z;
    }

    public boolean isLastRow() {
        return this.lastRow;
    }

    public void setLastRow(boolean z) {
        this.lastRow = z;
    }

    public boolean isFirstCol() {
        return this.firstCol;
    }

    public void setFirstCol(boolean z) {
        this.firstCol = z;
    }

    public boolean isLastCol() {
        return this.lastCol;
    }

    public void setLastCol(boolean z) {
        this.lastCol = z;
    }

    public boolean isBandRow() {
        return this.bandRow;
    }

    public void setBandRow(boolean z) {
        this.bandRow = z;
    }

    public boolean isBandCol() {
        return this.bandCol;
    }

    public void setBandCol(boolean z) {
        this.bandCol = z;
    }

    public int getRowCount() {
        return this.rowCnt;
    }

    public void setRowCount(int i) {
        this.rowCnt = i;
    }

    public int getColumnCount() {
        return this.colCnt;
    }

    public void setColumnCount(int i) {
        this.colCnt = i;
    }

    public void dispose() {
        if (this.cells != null) {
            int i = 0;
            while (true) {
                TableCell[] tableCellArr = this.cells;
                if (i < tableCellArr.length) {
                    TableCell tableCell = tableCellArr[i];
                    if (tableCell != null) {
                        tableCell.dispose();
                    }
                    i++;
                } else {
                    this.cells = null;
                    return;
                }
            }
        }
    }
}
