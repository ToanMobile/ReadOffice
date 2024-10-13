package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ss.usermodel.RichTextString;

public class HSSFTextbox extends HSSFSimpleShape {
    public static final short HORIZONTAL_ALIGNMENT_CENTERED = 2;
    public static final short HORIZONTAL_ALIGNMENT_DISTRIBUTED = 7;
    public static final short HORIZONTAL_ALIGNMENT_JUSTIFIED = 4;
    public static final short HORIZONTAL_ALIGNMENT_LEFT = 1;
    public static final short HORIZONTAL_ALIGNMENT_RIGHT = 3;
    public static final short OBJECT_TYPE_TEXT = 6;
    public static final short VERTICAL_ALIGNMENT_BOTTOM = 3;
    public static final short VERTICAL_ALIGNMENT_CENTER = 2;
    public static final short VERTICAL_ALIGNMENT_DISTRIBUTED = 7;
    public static final short VERTICAL_ALIGNMENT_JUSTIFY = 4;
    public static final short VERTICAL_ALIGNMENT_TOP = 1;
    private int fontColor;
    private short halign;
    private boolean isWordArt;
    private boolean isWrapLine;
    private int marginBottom;
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    HSSFRichTextString string = new HSSFRichTextString("");
    private short valign;

    public HSSFTextbox(EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
        setShapeType(6);
        this.halign = 1;
        this.valign = 1;
        this.marginLeft = Math.round(ShapeKit.getTextboxMarginLeft(escherContainerRecord));
        this.marginTop = Math.round(ShapeKit.getTextboxMarginTop(escherContainerRecord));
        this.marginRight = Math.round(ShapeKit.getTextboxMarginRight(escherContainerRecord));
        this.marginBottom = Math.round(ShapeKit.getTextboxMarginBottom(escherContainerRecord));
        this.isWrapLine = ShapeKit.isTextboxWrapLine(escherContainerRecord);
    }

    public HSSFRichTextString getString() {
        return this.string;
    }

    public void setString(RichTextString richTextString) {
        HSSFRichTextString hSSFRichTextString = (HSSFRichTextString) richTextString;
        if (hSSFRichTextString.numFormattingRuns() == 0) {
            hSSFRichTextString.applyFont(0);
        }
        this.string = hSSFRichTextString;
    }

    public boolean isTextboxWrapLine() {
        return this.isWrapLine;
    }

    public int getMarginLeft() {
        return this.marginLeft;
    }

    public void setMarginLeft(int i) {
        this.marginLeft = i;
    }

    public int getMarginRight() {
        return this.marginRight;
    }

    public void setMarginRight(int i) {
        this.marginRight = i;
    }

    public int getMarginTop() {
        return this.marginTop;
    }

    public void setMarginTop(int i) {
        this.marginTop = i;
    }

    public int getMarginBottom() {
        return this.marginBottom;
    }

    public void setMarginBottom(int i) {
        this.marginBottom = i;
    }

    public short getHorizontalAlignment() {
        return this.halign;
    }

    public void setHorizontalAlignment(short s) {
        this.halign = s;
    }

    public short getVerticalAlignment() {
        return this.valign;
    }

    public void setVerticalAlignment(short s) {
        this.valign = s;
    }

    public boolean isWordArt() {
        return this.isWordArt;
    }

    public void setWordArt(boolean z) {
        this.isWordArt = z;
    }

    public void setFontColor(int i) {
        this.fontColor = i;
    }

    public int getFontColor() {
        return this.fontColor;
    }
}
