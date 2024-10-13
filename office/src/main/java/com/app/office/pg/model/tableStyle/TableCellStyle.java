package com.app.office.pg.model.tableStyle;

import com.app.office.fc.dom4j.Element;
import com.app.office.simpletext.model.IAttributeSet;

public class TableCellStyle {
    private Element bgFill;
    private TableCellBorders cellBorders;
    private IAttributeSet fontAttr;

    public TableCellBorders getTableCellBorders() {
        return this.cellBorders;
    }

    public void setTableCellBorders(TableCellBorders tableCellBorders) {
        this.cellBorders = tableCellBorders;
    }

    public Element getTableCellBgFill() {
        return this.bgFill;
    }

    public void setTableCellBgFill(Element element) {
        this.bgFill = element;
    }

    public void setFontAttributeSet(IAttributeSet iAttributeSet) {
        this.fontAttr = iAttributeSet;
    }

    public IAttributeSet getFontAttributeSet() {
        return this.fontAttr;
    }

    public void dispose() {
        this.cellBorders = null;
        this.bgFill = null;
        this.fontAttr = null;
    }
}
