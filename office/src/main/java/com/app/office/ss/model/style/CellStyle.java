package com.app.office.ss.model.style;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.common.bg.BackgroundAndFill;

public class CellStyle {
    public static final short ALIGN_CENTER = 2;
    public static final short ALIGN_CENTER_SELECTION = 6;
    public static final short ALIGN_FILL = 4;
    public static final short ALIGN_GENERAL = 0;
    public static final short ALIGN_JUSTIFY = 5;
    public static final short ALIGN_LEFT = 1;
    public static final short ALIGN_RIGHT = 3;
    public static final short ALT_BARS = 3;
    public static final short BIG_SPOTS = 9;
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
    public static final short BRICKS = 10;
    public static final short DIAMONDS = 16;
    public static final short FINE_DOTS = 2;
    public static final short LEAST_DOTS = 18;
    public static final short LESS_DOTS = 17;
    public static final short NO_FILL = 0;
    public static final short SOLID_FOREGROUND = 1;
    public static final short SPARSE_DOTS = 4;
    public static final short SQUARES = 15;
    public static final short THICK_BACKWARD_DIAG = 7;
    public static final short THICK_FORWARD_DIAG = 8;
    public static final short THICK_HORZ_BANDS = 5;
    public static final short THICK_VERT_BANDS = 6;
    public static final short THIN_BACKWARD_DIAG = 13;
    public static final short THIN_FORWARD_DIAG = 14;
    public static final short THIN_HORZ_BANDS = 11;
    public static final short THIN_VERT_BANDS = 12;
    public static final short VERTICAL_BOTTOM = 2;
    public static final short VERTICAL_CENTER = 1;
    public static final short VERTICAL_JUSTIFY = 3;
    public static final short VERTICAL_TOP = 0;
    private Alignment alignment;
    private CellBorder cellBorder;
    private BackgroundAndFill fill;
    private short fontIndex;
    private short index;
    private boolean isHidden;
    private boolean isLocked;
    private NumberFormat numFmt;

    public short getIndex() {
        return this.index;
    }

    public void setIndex(short s) {
        this.index = s;
    }

    private void checkDataFormat() {
        if (this.numFmt == null) {
            this.numFmt = new NumberFormat();
        }
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numFmt = numberFormat;
    }

    public void setNumberFormatID(short s) {
        checkDataFormat();
        this.numFmt.setNumberFormatID(s);
    }

    public short getNumberFormatID() {
        checkDataFormat();
        return this.numFmt.getNumberFormatID();
    }

    public void setFormatCode(String str) {
        checkDataFormat();
        this.numFmt.setFormatCode(str);
    }

    public String getFormatCode() {
        checkDataFormat();
        return this.numFmt.getFormatCode();
    }

    public short getFontIndex() {
        return this.fontIndex;
    }

    public void setFontIndex(short s) {
        this.fontIndex = s;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean z) {
        this.isHidden = z;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void setLocked(boolean z) {
        this.isLocked = z;
    }

    private void checkAlignmeng() {
        if (this.alignment == null) {
            this.alignment = new Alignment();
        }
    }

    public boolean isWrapText() {
        checkAlignmeng();
        return this.alignment.isWrapText() || this.alignment.getHorizontalAlign() == 5 || this.alignment.getVerticalAlign() == 3;
    }

    public void setWrapText(boolean z) {
        checkAlignmeng();
        this.alignment.setWrapText(z);
    }

    public short getHorizontalAlign() {
        checkAlignmeng();
        return this.alignment.getHorizontalAlign();
    }

    public void setHorizontalAlign(String str) {
        checkAlignmeng();
        if (str == null || str.equalsIgnoreCase("general")) {
            this.alignment.setHorizontalAlign(0);
        } else if (str.equalsIgnoreCase(HtmlTags.ALIGN_LEFT)) {
            this.alignment.setHorizontalAlign(1);
        } else if (str.equalsIgnoreCase(HtmlTags.ALIGN_CENTER)) {
            this.alignment.setHorizontalAlign(2);
        } else if (str.equalsIgnoreCase(HtmlTags.ALIGN_RIGHT)) {
            this.alignment.setHorizontalAlign(3);
        } else if (str.equalsIgnoreCase("fill")) {
            this.alignment.setHorizontalAlign(4);
        } else if (str.equalsIgnoreCase(HtmlTags.ALIGN_JUSTIFY)) {
            this.alignment.setHorizontalAlign(5);
        } else if (str.equalsIgnoreCase("distributed")) {
            this.alignment.setHorizontalAlign(5);
        }
    }

    public void setHorizontalAlign(short s) {
        checkAlignmeng();
        this.alignment.setHorizontalAlign(s);
    }

    public short getVerticalAlign() {
        checkAlignmeng();
        return this.alignment.getVerticalAlign();
    }

    public void setVerticalAlign(String str) {
        checkAlignmeng();
        if (str.equalsIgnoreCase(HtmlTags.ALIGN_TOP)) {
            this.alignment.setVerticalAlign(0);
        } else if (str == null || str.equalsIgnoreCase(HtmlTags.ALIGN_CENTER)) {
            this.alignment.setVerticalAlign(1);
        } else if (str.equalsIgnoreCase(HtmlTags.ALIGN_BOTTOM)) {
            this.alignment.setVerticalAlign(2);
        } else if (str.equalsIgnoreCase(HtmlTags.ALIGN_JUSTIFY)) {
            this.alignment.setVerticalAlign(3);
        } else if (str.equalsIgnoreCase("distributed")) {
            this.alignment.setVerticalAlign(3);
        }
    }

    public void setVerticalAlign(short s) {
        checkAlignmeng();
        this.alignment.setVerticalAlign(s);
    }

    public short getRotation() {
        checkAlignmeng();
        return this.alignment.getRotaion();
    }

    public void setRotation(short s) {
        checkAlignmeng();
        this.alignment.setRotation(s);
    }

    public short getIndent() {
        checkAlignmeng();
        return this.alignment.getIndent();
    }

    public void setIndent(short s) {
        checkAlignmeng();
        this.alignment.setIndent(s);
    }

    private void checkBorder() {
        if (this.cellBorder == null) {
            this.cellBorder = new CellBorder();
        }
    }

    public void setBorder(CellBorder cellBorder2) {
        this.cellBorder = cellBorder2;
    }

    public short getBorderLeft() {
        checkBorder();
        return this.cellBorder.getLeftBorder().getStyle();
    }

    public void setBorderLeft(short s) {
        checkBorder();
        this.cellBorder.getLeftBorder().setStyle(s);
    }

    public short getBorderLeftColorIdx() {
        checkBorder();
        return this.cellBorder.getLeftBorder().getColor();
    }

    public void setBorderLeftColorIdx(short s) {
        checkBorder();
        this.cellBorder.getLeftBorder().setColor(s);
    }

    public short getBorderRight() {
        checkBorder();
        return this.cellBorder.getRightBorder().getStyle();
    }

    public void setBorderRight(short s) {
        checkBorder();
        this.cellBorder.getRightBorder().setStyle(s);
    }

    public short getBorderRightColorIdx() {
        checkBorder();
        return this.cellBorder.getRightBorder().getColor();
    }

    public void setBorderRightColorIdx(short s) {
        checkBorder();
        this.cellBorder.getRightBorder().setColor(s);
    }

    public short getBorderTop() {
        checkBorder();
        return this.cellBorder.getTopBorder().getStyle();
    }

    public void setBorderTop(short s) {
        checkBorder();
        this.cellBorder.getTopBorder().setStyle(s);
    }

    public short getBorderTopColorIdx() {
        checkBorder();
        return this.cellBorder.getTopBorder().getColor();
    }

    public void setBorderTopColorIdx(short s) {
        checkBorder();
        this.cellBorder.getTopBorder().setColor(s);
    }

    public short getBorderBottom() {
        checkBorder();
        return this.cellBorder.getBottomBorder().getStyle();
    }

    public void setBorderBottom(short s) {
        checkBorder();
        this.cellBorder.getBottomBorder().setStyle(s);
    }

    public short getBorderBottomColorIdx() {
        checkBorder();
        return this.cellBorder.getBottomBorder().getColor();
    }

    public void setBorderBottomColorIdx(short s) {
        checkBorder();
        this.cellBorder.getBottomBorder().setColor(s);
    }

    private void checkFillPattern() {
        if (this.fill == null) {
            BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
            this.fill = backgroundAndFill;
            backgroundAndFill.setFillType((byte) -1);
        }
    }

    public void setFillPattern(BackgroundAndFill backgroundAndFill) {
        this.fill = backgroundAndFill;
    }

    public BackgroundAndFill getFillPattern() {
        return this.fill;
    }

    public void setFillPatternType(byte b) {
        checkFillPattern();
        this.fill.setFillType(b);
    }

    public byte getFillPatternType() {
        checkFillPattern();
        return this.fill.getFillType();
    }

    public void setBgColor(int i) {
        checkFillPattern();
        this.fill.setBackgoundColor(i);
    }

    public int getBgColor() {
        checkFillPattern();
        return this.fill.getBackgoundColor();
    }

    public void setFgColor(int i) {
        checkFillPattern();
        this.fill.setForegroundColor(i);
    }

    public int getFgColor() {
        checkFillPattern();
        return this.fill.getForegroundColor();
    }

    public void dispose() {
        this.numFmt = null;
        this.fill = null;
        CellBorder cellBorder2 = this.cellBorder;
        if (cellBorder2 != null) {
            cellBorder2.dispose();
            this.cellBorder = null;
        }
        Alignment alignment2 = this.alignment;
        if (alignment2 != null) {
            alignment2.dispose();
            this.alignment = null;
        }
    }
}
