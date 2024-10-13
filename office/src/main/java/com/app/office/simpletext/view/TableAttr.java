package com.app.office.simpletext.view;

public class TableAttr {
    public int bottomMargin;
    public int cellBackground;
    public byte cellVerticalAlign;
    public int cellWidth;
    public int leftMargin;
    public int rightMargin;
    public int topMargin;

    public void reset() {
        this.topMargin = 0;
        this.leftMargin = 0;
        this.rightMargin = 0;
        this.bottomMargin = 0;
        this.cellVerticalAlign = 0;
        this.cellBackground = -1;
    }
}
