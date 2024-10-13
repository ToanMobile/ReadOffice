package com.app.office.ss.model.style;

public class CellBorder {
    private BorderStyle bottom = new BorderStyle();
    private BorderStyle left = new BorderStyle();
    private BorderStyle right = new BorderStyle();
    private BorderStyle top = new BorderStyle();

    public void setLeftBorder(BorderStyle borderStyle) {
        this.left = borderStyle;
    }

    public BorderStyle getLeftBorder() {
        return this.left;
    }

    public void setTopBorder(BorderStyle borderStyle) {
        this.top = borderStyle;
    }

    public BorderStyle getTopBorder() {
        return this.top;
    }

    public void setRightBorder(BorderStyle borderStyle) {
        this.right = borderStyle;
    }

    public BorderStyle getRightBorder() {
        return this.right;
    }

    public void setBottomBorder(BorderStyle borderStyle) {
        this.bottom = borderStyle;
    }

    public BorderStyle getBottomBorder() {
        return this.bottom;
    }

    public void dispose() {
        BorderStyle borderStyle = this.left;
        if (borderStyle != null) {
            borderStyle.dispose();
            this.left = null;
        }
        BorderStyle borderStyle2 = this.top;
        if (borderStyle2 != null) {
            borderStyle2.dispose();
            this.top = null;
        }
        BorderStyle borderStyle3 = this.right;
        if (borderStyle3 != null) {
            borderStyle3.dispose();
            this.right = null;
        }
        BorderStyle borderStyle4 = this.bottom;
        if (borderStyle4 != null) {
            borderStyle4.dispose();
            this.bottom = null;
        }
    }
}
