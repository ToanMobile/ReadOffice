package com.app.office.ss.model.drawing;

public class AnchorPoint {
    protected short col;
    protected int dx;
    protected int dy;
    protected int row;

    public void dispose() {
    }

    public AnchorPoint() {
    }

    public AnchorPoint(short s, int i, int i2, int i3) {
        this.row = i;
        this.col = s;
        this.dx = i2;
        this.dy = i3;
    }

    public void setRow(int i) {
        this.row = i;
    }

    public int getRow() {
        return this.row;
    }

    public void setColumn(short s) {
        this.col = s;
    }

    public short getColumn() {
        return this.col;
    }

    public void setDX(int i) {
        this.dx = i;
    }

    public int getDX() {
        return this.dx;
    }

    public void setDY(int i) {
        this.dy = i;
    }

    public int getDY() {
        return this.dy;
    }
}
