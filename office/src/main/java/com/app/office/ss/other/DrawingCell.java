package com.app.office.ss.other;

public class DrawingCell {
    private int columnIndex;
    private float height;
    private float left;
    private int rowIndex;
    private float top;
    private float visibleHeight;
    private float visibleWidth;
    private float width;

    public void dispose() {
    }

    public void reset() {
        setRowIndex(0);
        setColumnIndex(0);
        setLeft(0.0f);
        setTop(0.0f);
        setWidth(0.0f);
        setHeight(0.0f);
        setVisibleWidth(0.0f);
        setVisibleHeight(0.0f);
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int i) {
        this.rowIndex = i;
    }

    public void increaseRow() {
        this.rowIndex++;
    }

    public void increaseColumn() {
        this.columnIndex++;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int i) {
        this.columnIndex = i;
    }

    public float getLeft() {
        return this.left;
    }

    public void setLeft(float f) {
        this.left = f;
    }

    public void increaseLeftWithVisibleWidth() {
        this.left += this.visibleWidth;
    }

    public float getTop() {
        return this.top;
    }

    public void setTop(float f) {
        this.top = f;
    }

    public void increaseTopWithVisibleHeight() {
        this.top += this.visibleHeight;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public float getVisibleWidth() {
        return this.visibleWidth;
    }

    public void setVisibleWidth(float f) {
        this.visibleWidth = f;
    }

    public float getVisibleHeight() {
        return this.visibleHeight;
    }

    public void setVisibleHeight(float f) {
        this.visibleHeight = f;
    }
}
