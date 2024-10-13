package com.app.office.wp.model;

import com.app.office.simpletext.model.AbstractElement;
import com.app.office.simpletext.model.ElementCollectionImpl;
import com.app.office.simpletext.model.IElement;

public class RowElement extends AbstractElement {
    private ElementCollectionImpl cellElement = new ElementCollectionImpl(10);

    public short getType() {
        return 3;
    }

    public void appendCell(CellElement cellElement2) {
        this.cellElement.addElement(cellElement2);
    }

    public IElement getCellElement(long j) {
        return this.cellElement.getElement(j);
    }

    public IElement getElementForIndex(int i) {
        return this.cellElement.getElementForIndex(i);
    }

    public void insertElementForIndex(IElement iElement, int i) {
        this.cellElement.insertElementForIndex(iElement, i);
    }

    public int getCellNumber() {
        return this.cellElement.size();
    }
}
