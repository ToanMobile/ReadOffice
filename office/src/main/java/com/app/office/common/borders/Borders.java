package com.app.office.common.borders;

public class Borders {
    private Border bottom;
    private Border left;
    private byte onType;
    private Border right;
    private Border top;

    public Border getTopBorder() {
        return this.top;
    }

    public void setTopBorder(Border border) {
        this.top = border;
    }

    public Border getLeftBorder() {
        return this.left;
    }

    public void setLeftBorder(Border border) {
        this.left = border;
    }

    public Border getRightBorder() {
        return this.right;
    }

    public void setRightBorder(Border border) {
        this.right = border;
    }

    public Border getBottomBorder() {
        return this.bottom;
    }

    public void setBottomBorder(Border border) {
        this.bottom = border;
    }

    public byte getOnType() {
        return this.onType;
    }

    public void setOnType(byte b) {
        this.onType = b;
    }
}
