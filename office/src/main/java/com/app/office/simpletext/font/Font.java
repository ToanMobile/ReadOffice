package com.app.office.simpletext.font;

public class Font {
    public static final byte ANSI_CHARSET = 0;
    public static final int BOLD = 1;
    public static final short BOLDWEIGHT_BOLD = 700;
    public static final short BOLDWEIGHT_NORMAL = 400;
    public static final short COLOR_NORMAL = Short.MAX_VALUE;
    public static final short COLOR_RED = 10;
    public static final byte DEFAULT_CHARSET = 1;
    public static final int ITALIC = 2;
    public static final int PLAIN = 0;
    public static final short SS_NONE = 0;
    public static final short SS_SUB = 2;
    public static final short SS_SUPER = 1;
    public static final byte SYMBOL_CHARSET = 2;
    public static final byte U_DOUBLE = 2;
    public static final byte U_DOUBLE_ACCOUNTING = 34;
    public static final byte U_NONE = 0;
    public static final byte U_SINGLE = 1;
    public static final byte U_SINGLE_ACCOUNTING = 33;
    private int colorIndex;
    private double fontSize;
    private int index;
    private boolean isBold;
    private boolean isItalic;
    private String name;
    private boolean strikeline;
    protected int style;
    private byte superSubScript;
    private int underline;

    public Font() {
    }

    public Font(String str, int i, int i2) {
        this.name = str == null ? "Default" : str;
        this.style = (i & -4) != 0 ? 0 : i;
        this.fontSize = (double) i2;
    }

    public int getStyle() {
        return this.style;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public double getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(double d) {
        this.fontSize = d;
    }

    public boolean isItalic() {
        return this.isItalic;
    }

    public void setItalic(boolean z) {
        this.isItalic = z;
    }

    public boolean isBold() {
        return this.isBold;
    }

    public void setBold(boolean z) {
        this.isBold = z;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public void setColorIndex(int i) {
        this.colorIndex = i;
    }

    public byte getSuperSubScript() {
        return this.superSubScript;
    }

    public void setSuperSubScript(byte b) {
        this.superSubScript = b;
    }

    public int getUnderline() {
        return this.underline;
    }

    public void setUnderline(String str) {
        if (str.equalsIgnoreCase("none")) {
            setUnderline(0);
        } else if (str.equalsIgnoreCase("single")) {
            setUnderline(1);
        } else if (str.equalsIgnoreCase("double")) {
            setUnderline(2);
        } else if (str.equalsIgnoreCase("singleAccounting")) {
            setUnderline(33);
        } else if (str.equalsIgnoreCase("doubleAccounting")) {
            setUnderline(34);
        }
    }

    public void setUnderline(int i) {
        this.underline = i;
    }

    public boolean isStrikeline() {
        return this.strikeline;
    }

    public void setStrikeline(boolean z) {
        this.strikeline = z;
    }

    public void dispose() {
        this.name = null;
    }
}
