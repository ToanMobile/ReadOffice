package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.SEPX;

public final class Section extends Range {
    private SectionProperties _props;

    public int type() {
        return 2;
    }

    public Section(SEPX sepx, Range range) {
        super(Math.max(range._start, sepx.getStart()), Math.min(range._end, sepx.getEnd()), range);
        this._props = sepx.getSectionProperties();
    }

    public Object clone() throws CloneNotSupportedException {
        Section section = (Section) super.clone();
        section._props = (SectionProperties) this._props.clone();
        return section;
    }

    public int getDistanceBetweenColumns() {
        return this._props.getDxaColumns();
    }

    public int getMarginBottom() {
        return this._props.getDyaBottom();
    }

    public int getMarginLeft() {
        return this._props.getDxaLeft();
    }

    public int getMarginRight() {
        return this._props.getDxaRight();
    }

    public int getMarginTop() {
        return this._props.getDyaTop();
    }

    public int getMarginHeader() {
        return this._props.getDyaHdrTop();
    }

    public int getMarginFooter() {
        return this._props.getDyaHdrBottom();
    }

    public int getNumColumns() {
        return this._props.getCcolM1() + 1;
    }

    public int getPageHeight() {
        return this._props.getYaPage();
    }

    public int getPageWidth() {
        return this._props.getXaPage();
    }

    public int getGridType() {
        return this._props.getClm();
    }

    public int getLinePitch() {
        return this._props.getDyaLinePitch();
    }

    public boolean isColumnsEvenlySpaced() {
        return this._props.getFEvenlySpaced();
    }

    public BorderCode getTopBorder() {
        return this._props.getTopBorder();
    }

    public BorderCode getBottomBorder() {
        return this._props.getBottomBorder();
    }

    public BorderCode getLeftBorder() {
        return this._props.getLeftBorder();
    }

    public BorderCode getRightBorder() {
        return this._props.getRightBorder();
    }

    public int getPageBorderInfo() {
        return this._props.getPgbProp();
    }

    public String toString() {
        return "Section [" + getStartOffset() + "; " + getEndOffset() + ")";
    }
}
