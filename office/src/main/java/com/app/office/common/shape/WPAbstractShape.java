package com.app.office.common.shape;

public class WPAbstractShape extends ArbitraryPolygonShape {
    public static final byte ALIGNMENT_ABSOLUTE = 0;
    public static final byte ALIGNMENT_BOTTOM = 5;
    public static final byte ALIGNMENT_CENTER = 2;
    public static final byte ALIGNMENT_INSIDE = 6;
    public static final byte ALIGNMENT_LEFT = 1;
    public static final byte ALIGNMENT_OUTSIDE = 7;
    public static final byte ALIGNMENT_RIGHT = 3;
    public static final byte ALIGNMENT_TOP = 4;
    public static final byte POSITIONTYPE_ABSOLUTE = 0;
    public static final byte POSITIONTYPE_RELATIVE = 1;
    public static final byte RELATIVE_BOTTOM = 7;
    public static final byte RELATIVE_CHARACTER = 3;
    public static final byte RELATIVE_COLUMN = 0;
    public static final byte RELATIVE_INNER = 8;
    public static final byte RELATIVE_LEFT = 4;
    public static final byte RELATIVE_LINE = 11;
    public static final byte RELATIVE_MARGIN = 1;
    public static final byte RELATIVE_OUTER = 9;
    public static final byte RELATIVE_PAGE = 2;
    public static final byte RELATIVE_PARAGRAPH = 10;
    public static final byte RELATIVE_RIGHT = 5;
    public static final byte RELATIVE_TOP = 6;
    public static final short WRAP_BOTTOM = 6;
    public static final short WRAP_OLE = 2;
    public static final short WRAP_SQUARE = 1;
    public static final short WRAP_THROUGH = 4;
    public static final short WRAP_TIGHT = 0;
    public static final short WRAP_TOP = 3;
    public static final short WRAP_TOPANDBOTTOM = 5;
    private int elementIndex = -1;
    private byte horAlignment = 0;
    private byte horPositionType;
    private byte horRelativeTo;
    private int horRelativeValue;
    private boolean isTextWrapLine = true;
    private byte verAlignment = 0;
    private byte verPositionType;
    private byte verRelativeTo = 10;
    private int verRelativeValue;
    private short wrapType = 3;

    public byte getHorPositionType() {
        return this.horPositionType;
    }

    public void setHorPositionType(byte b) {
        this.horPositionType = b;
    }

    public byte getVerPositionType() {
        return this.verPositionType;
    }

    public void setVerPositionType(byte b) {
        this.verPositionType = b;
    }

    public int getWrap() {
        return this.wrapType;
    }

    public void setWrap(short s) {
        this.wrapType = s;
    }

    public byte getHorizontalRelativeTo() {
        return this.horRelativeTo;
    }

    public void setHorizontalRelativeTo(byte b) {
        this.horRelativeTo = b;
    }

    public byte getHorizontalAlignment() {
        return this.horAlignment;
    }

    public void setHorizontalAlignment(byte b) {
        this.horAlignment = b;
    }

    public byte getVerticalRelativeTo() {
        return this.verRelativeTo;
    }

    public void setVerticalRelativeTo(byte b) {
        this.verRelativeTo = b;
    }

    public byte getVerticalAlignment() {
        return this.verAlignment;
    }

    public void setVerticalAlignment(byte b) {
        this.verAlignment = b;
    }

    public int getHorRelativeValue() {
        return this.horRelativeValue;
    }

    public void setHorRelativeValue(int i) {
        this.horRelativeValue = i;
    }

    public int getVerRelativeValue() {
        return this.verRelativeValue;
    }

    public void setVerRelativeValue(int i) {
        this.verRelativeValue = i;
    }

    public int getElementIndex() {
        return this.elementIndex;
    }

    public void setElementIndex(int i) {
        this.elementIndex = i;
    }

    public boolean isTextWrapLine() {
        return this.isTextWrapLine;
    }

    public void setTextWrapLine(boolean z) {
        this.isTextWrapLine = z;
    }
}
