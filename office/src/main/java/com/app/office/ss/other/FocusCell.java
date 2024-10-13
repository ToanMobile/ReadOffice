package com.app.office.ss.other;

import android.graphics.Rect;

public class FocusCell {
    public static final short CELL = 3;
    public static final short COLUMNHEADER = 2;
    public static final short ROWHEADER = 1;
    public static final short UNKNOWN = 0;
    private Rect area;
    private int col;
    private short headerType = 0;
    private int row;

    public void dispose() {
    }

    public FocusCell() {
    }

    public FocusCell(short s, Rect rect, int i, int i2) {
        this.headerType = s;
        this.area = rect;
        if (s == 1) {
            this.row = i;
        } else if (s == 2) {
            this.col = i2;
        }
    }

    public FocusCell clone() {
        return new FocusCell(this.headerType, new Rect(this.area), this.row, this.col);
    }

    public void setType(short s) {
        this.headerType = s;
    }

    public int getType() {
        return this.headerType;
    }

    public void setRow(int i) {
        short s = this.headerType;
        if (s == 1 || s == 3) {
            this.row = i;
        }
    }

    public int getRow() {
        short s = this.headerType;
        if (s == 1 || s == 3) {
            return this.row;
        }
        return -1;
    }

    public void setColumn(int i) {
        short s = this.headerType;
        if (s == 2 || s == 3) {
            this.col = i;
        }
    }

    public int getColumn() {
        short s = this.headerType;
        if (s == 2 || s == 3) {
            return this.col;
        }
        return -1;
    }

    public void setRect(Rect rect) {
        this.area = rect;
    }

    public Rect getRect() {
        return this.area;
    }
}
