package com.app.office.wp.model;

import com.app.office.simpletext.model.ElementCollectionImpl;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;

public class TableElement extends ParagraphElement {
    private ElementCollectionImpl rowElement = new ElementCollectionImpl(10);

    public void appendLeaf(LeafElement leafElement) {
    }

    public IElement getLeaf(long j) {
        return null;
    }

    public String getText(IDocument iDocument) {
        return "";
    }

    public short getType() {
        return 2;
    }

    public void appendRow(RowElement rowElement2) {
        this.rowElement.addElement(rowElement2);
    }

    public IElement getRowElement(long j) {
        return this.rowElement.getElement(j);
    }

    public IElement getElementForIndex(int i) {
        return this.rowElement.getElementForIndex(i);
    }
}
