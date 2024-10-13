package com.app.office.ss.model.style;

public class BorderStyle {
    public static final short BORDER_DASHED = 3;
    public static final short BORDER_DASH_DOT = 9;
    public static final short BORDER_DASH_DOT_DOT = 11;
    public static final short BORDER_DOTTED = 7;
    public static final short BORDER_DOUBLE = 6;
    public static final short BORDER_HAIR = 4;
    public static final short BORDER_MEDIUM = 2;
    public static final short BORDER_MEDIUM_DASHED = 8;
    public static final short BORDER_MEDIUM_DASH_DOT = 10;
    public static final short BORDER_MEDIUM_DASH_DOT_DOT = 12;
    public static final short BORDER_NONE = 0;
    public static final short BORDER_SLANTED_DASH_DOT = 13;
    public static final short BORDER_THICK = 5;
    public static final short BORDER_THIN = 1;
    private short colorIdx = 0;
    private short style = 0;

    public void dispose() {
    }

    public BorderStyle() {
    }

    public BorderStyle(short s, short s2) {
        this.style = s;
        this.colorIdx = s2;
    }

    public BorderStyle(String str, short s) {
        this.style = getStyle(str);
        this.colorIdx = s;
    }

    private short getStyle(String str) {
        if (str != null && !str.equals("none")) {
            if (str.equals("thin")) {
                return 1;
            }
            if (str.equals("medium")) {
                return 2;
            }
            if (str.equals("dashed")) {
                return 3;
            }
            if (str.equals("dotted")) {
                return 7;
            }
            if (str.equals("thick")) {
                return 5;
            }
            if (str.equals("double")) {
                return 6;
            }
            if (str.equals("hair")) {
                return 4;
            }
            if (str.equals("mediumDashed")) {
                return 8;
            }
            if (str.equals("dashDot")) {
                return 9;
            }
            if (str.equals("mediumDashDot")) {
                return 10;
            }
            if (str.equals("dashDotDot")) {
                return 11;
            }
            if (str.equals("mediumDashDotDot")) {
                return 12;
            }
            if (str.equals("slantDashDot")) {
                return 13;
            }
        }
        return 0;
    }

    public void setStyle(short s) {
        this.style = s;
    }

    public short getStyle() {
        return this.style;
    }

    public void setColor(short s) {
        this.colorIdx = s;
    }

    public short getColor() {
        return this.colorIdx;
    }
}
