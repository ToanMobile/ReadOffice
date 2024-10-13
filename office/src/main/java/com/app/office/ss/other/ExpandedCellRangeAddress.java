package com.app.office.ss.other;

import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;

public class ExpandedCellRangeAddress {
    private Cell expandedCell;
    private CellRangeAddress rangeAddr;

    public ExpandedCellRangeAddress(Cell cell, int i, int i2, int i3, int i4) {
        this.expandedCell = cell;
        this.rangeAddr = new CellRangeAddress(i, i2, i3, i4);
    }

    public CellRangeAddress getRangedAddress() {
        return this.rangeAddr;
    }

    public Cell getExpandedCell() {
        return this.expandedCell;
    }

    public void dispose() {
        this.rangeAddr = null;
        this.expandedCell = null;
    }
}
