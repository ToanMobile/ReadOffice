package com.app.office.ss.model.style;

public class Alignment {
    private short horizontal;
    private short indent = 0;
    private short rotation = 0;
    private short vertival = 2;
    private boolean wrapText = false;

    public void dispose() {
    }

    public void setHorizontalAlign(short s) {
        this.horizontal = s;
    }

    public short getHorizontalAlign() {
        return this.horizontal;
    }

    public void setVerticalAlign(short s) {
        this.vertival = s;
    }

    public short getVerticalAlign() {
        return this.vertival;
    }

    public void setRotation(short s) {
        this.rotation = s;
    }

    public short getRotaion() {
        return this.rotation;
    }

    public void setWrapText(boolean z) {
        this.wrapText = z;
    }

    public boolean isWrapText() {
        return this.wrapText;
    }

    public void setIndent(short s) {
        this.indent = s;
    }

    public short getIndent() {
        return this.indent;
    }
}
