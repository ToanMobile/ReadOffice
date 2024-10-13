package com.app.office.simpletext.model;

import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Workbook;

public class CellLeafElement extends LeafElement {
    private boolean appendNewline;
    private Workbook book;
    private int offEnd;
    private int offStart;
    private int sharedStringIndex;

    public CellLeafElement(Cell cell, int i, int i2) {
        super((String) null);
        this.book = cell.getSheet().getWorkbook();
        this.sharedStringIndex = cell.getStringCellValueIndex();
        this.offStart = i;
        this.offEnd = i2;
    }

    public String getText(IDocument iDocument) {
        if (!this.appendNewline) {
            return this.book.getSharedString(this.sharedStringIndex).substring(this.offStart, this.offEnd);
        }
        return this.book.getSharedString(this.sharedStringIndex).substring(this.offStart, this.offEnd) + "\n";
    }

    public void appendNewlineFlag() {
        this.appendNewline = true;
    }

    public void dispose() {
        this.book = null;
    }
}
