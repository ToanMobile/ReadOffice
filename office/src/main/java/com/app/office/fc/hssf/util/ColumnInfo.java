package com.app.office.fc.hssf.util;

public class ColumnInfo {
    private int _colWidth;
    private int _firstCol;
    private int _lastCol;
    private boolean hidden;
    private int style;

    public ColumnInfo(int i, int i2, int i3, int i4, boolean z) {
        this._firstCol = i;
        this._lastCol = i2;
        this._colWidth = i3;
        setStyle(i4);
        this.hidden = z;
    }

    public int getFirstCol() {
        return this._firstCol;
    }

    public void setFirstCol(int i) {
        this._firstCol = i;
    }

    public int getLastCol() {
        return this._lastCol;
    }

    public void setLastCol(int i) {
        this._lastCol = i;
    }

    public int getColWidth() {
        return this._colWidth;
    }

    public void setColWidth(int i) {
        this._colWidth = i;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean z) {
        this.hidden = z;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int i) {
        this.style = i;
    }
}
