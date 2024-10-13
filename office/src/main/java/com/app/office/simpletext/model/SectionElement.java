package com.app.office.simpletext.model;

public class SectionElement extends AbstractElement {
    private IElementCollection paraCollection = new ElementCollectionImpl(10);

    public short getType() {
        return 0;
    }

    public void appendParagraph(IElement iElement, long j) {
        ((ElementCollectionImpl) this.paraCollection).addElement(iElement);
    }

    public IElementCollection getParaCollection() {
        return this.paraCollection;
    }

    public String getText(IDocument iDocument) {
        String str = "";
        for (int i = 0; i < this.paraCollection.size(); i++) {
            str = str + this.paraCollection.getElementForIndex(i).getText((IDocument) null);
        }
        return str;
    }

    public IElement getElement(long j) {
        return this.paraCollection.getElement(j);
    }

    public void dispose() {
        super.dispose();
        IElementCollection iElementCollection = this.paraCollection;
        if (iElementCollection != null) {
            iElementCollection.dispose();
            this.paraCollection = null;
        }
    }
}
