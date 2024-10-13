package com.app.office.ss.other;

public class MergeCell {
    private boolean frozenColumn;
    private boolean frozenRow;
    private float height;
    private float noVisibleHeight;
    private float novisibleWidth;
    private float width;

    public void dispose() {
    }

    public void reset() {
        setWidth(0.0f);
        setHeight(0.0f);
        setFrozenRow(false);
        setFrozenColumn(false);
        setNovisibleWidth(0.0f);
        setNoVisibleHeight(0.0f);
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

    public boolean isFrozenRow() {
        return this.frozenRow;
    }

    public void setFrozenRow(boolean z) {
        this.frozenRow = z;
    }

    public boolean isFrozenColumn() {
        return this.frozenColumn;
    }

    public void setFrozenColumn(boolean z) {
        this.frozenColumn = z;
    }

    public float getNovisibleWidth() {
        return this.novisibleWidth;
    }

    public void setNovisibleWidth(float f) {
        this.novisibleWidth = f;
    }

    public float getNoVisibleHeight() {
        return this.noVisibleHeight;
    }

    public void setNoVisibleHeight(float f) {
        this.noVisibleHeight = f;
    }
}
