package com.app.office.fc.hwpf.usermodel;

public final class TableCell extends Range {
    private int _leftEdge;
    private int _levelNum;
    private TableCellDescriptor _tcd;
    private int _width;

    public TableCell(int i, int i2, TableRow tableRow, int i3, TableCellDescriptor tableCellDescriptor, int i4, int i5) {
        super(i, i2, (Range) tableRow);
        this._tcd = tableCellDescriptor;
        this._leftEdge = i4;
        this._width = i5;
        this._levelNum = i3;
    }

    public boolean isFirstMerged() {
        return this._tcd.isFFirstMerged();
    }

    public boolean isMerged() {
        return this._tcd.isFMerged();
    }

    public boolean isVertical() {
        return this._tcd.isFVertical();
    }

    public boolean isBackward() {
        return this._tcd.isFBackward();
    }

    public boolean isRotateFont() {
        return this._tcd.isFRotateFont();
    }

    public boolean isVerticallyMerged() {
        return this._tcd.isFVertMerge();
    }

    public boolean isFirstVerticallyMerged() {
        return this._tcd.isFVertRestart();
    }

    public byte getVertAlign() {
        return this._tcd.getVertAlign();
    }

    public BorderCode getBrcTop() {
        return this._tcd.getBrcTop();
    }

    public BorderCode getBrcBottom() {
        return this._tcd.getBrcBottom();
    }

    public BorderCode getBrcLeft() {
        return this._tcd.getBrcLeft();
    }

    public BorderCode getBrcRight() {
        return this._tcd.getBrcRight();
    }

    public int getLeftEdge() {
        return this._leftEdge;
    }

    public int getWidth() {
        return this._width;
    }

    public TableCellDescriptor getDescriptor() {
        return this._tcd;
    }
}
