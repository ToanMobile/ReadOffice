package com.app.office.pg.model.tableStyle;

import com.app.office.fc.dom4j.Element;

public class TableCellBorders {
    private Element bottom;
    private Element left;
    private Element right;
    private Element top;

    public Element getLeftBorder() {
        return this.left;
    }

    public void setLeftBorder(Element element) {
        this.left = element;
    }

    public Element getTopBorder() {
        return this.top;
    }

    public void setTopBorder(Element element) {
        this.top = element;
    }

    public Element getRightBorder() {
        return this.right;
    }

    public void setRightBorder(Element element) {
        this.right = element;
    }

    public Element getBottomBorder() {
        return this.bottom;
    }

    public void setBottomBorder(Element element) {
        this.bottom = element;
    }
}
