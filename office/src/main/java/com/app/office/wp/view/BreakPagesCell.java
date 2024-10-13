package com.app.office.wp.view;

import com.app.office.wp.model.CellElement;

public class BreakPagesCell {
    private long breakOffset;
    private CellElement cell;

    public BreakPagesCell(CellElement cellElement, long j) {
        this.cell = cellElement;
        this.breakOffset = j;
    }

    public CellElement getCell() {
        return this.cell;
    }

    public long getBreakOffset() {
        return this.breakOffset;
    }
}
