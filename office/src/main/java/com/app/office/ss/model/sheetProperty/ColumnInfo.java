package com.app.office.ss.model.sheetProperty;

public class ColumnInfo {
    private float _colWidth;
    private int _firstCol;
    private int _lastCol;
    private boolean hidden;
    private int style;

    public ColumnInfo(int i, int i2, float f, int i3, boolean z) {
        this._firstCol = i;
        this._lastCol = i2;
        this._colWidth = f;
        this.style = i3;
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

    public float getColWidth() {
        return this._colWidth;
    }

    public void setColWidth(float f) {
        this._colWidth = f;
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
