package com.app.office.common.borders;

public class Border {
    private int color;
    private byte lineType;
    private int lineWidth;
    private short space;

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public int getLineWidth() {
        return this.lineWidth;
    }

    public void setLineWidth(int i) {
        this.lineWidth = i;
    }

    public byte getLineType() {
        return this.lineType;
    }

    public void setLineType(byte b) {
        this.lineType = b;
    }

    public short getSpace() {
        return this.space;
    }

    public void setSpace(short s) {
        this.space = s;
    }
}
