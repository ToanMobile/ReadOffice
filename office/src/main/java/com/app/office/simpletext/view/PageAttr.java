package com.app.office.simpletext.view;

public class PageAttr {
    public static final byte GRIDTYPE_CHAR = 3;
    public static final byte GRIDTYPE_LINE = 2;
    public static final byte GRIDTYPE_LINE_AND_CHAR = 1;
    public static final byte GRIDTYPE_NONE = 0;
    public int bottomMargin;
    public int footerMargin;
    public int headerMargin;
    public byte horizontalAlign;
    public int leftMargin;
    public int pageBRColor;
    public int pageBorder;
    public int pageHeight;
    public float pageLinePitch;
    public int pageWidth;
    public int rightMargin;
    public int topMargin;
    public byte verticalAlign;

    public void dispose() {
    }

    public void reset() {
        this.verticalAlign = 0;
        this.horizontalAlign = 0;
        this.pageWidth = 0;
        this.pageHeight = 0;
        this.topMargin = 0;
        this.bottomMargin = 0;
        this.leftMargin = 0;
        this.rightMargin = 0;
        this.headerMargin = 0;
        this.footerMargin = 0;
        this.pageBorder = 0;
        this.pageBRColor = -1;
        this.pageLinePitch = 0.0f;
    }
}
