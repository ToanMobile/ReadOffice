package com.app.office.simpletext.view;

public class CharAttr {
    public byte encloseType;
    public int fontColor;
    public int fontIndex;
    public int fontScale;
    public int fontSize;
    public int highlightedColor;
    public boolean isBold;
    public boolean isDoubleStrikeThrough;
    public boolean isItalic;
    public boolean isStrikeThrough;
    public byte pageNumberType;
    public short subSuperScriptType;
    public int underlineColor;
    public int underlineType;

    public void reset() {
        this.underlineType = 0;
        this.fontColor = -1;
        this.underlineColor = -1;
        this.isStrikeThrough = false;
        this.isDoubleStrikeThrough = false;
        this.subSuperScriptType = 0;
        this.fontIndex = -1;
        this.encloseType = -1;
        this.pageNumberType = -1;
    }
}
