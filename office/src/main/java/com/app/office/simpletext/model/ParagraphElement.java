package com.app.office.simpletext.model;

public class ParagraphElement extends AbstractElement {
    private ElementCollectionImpl leaf = new ElementCollectionImpl(10);

    public String getText(IDocument iDocument) {
        int size = this.leaf.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(this.leaf.getElementForIndex(i).getText((IDocument) null));
        }
        return sb.toString();
    }

    public void appendLeaf(LeafElement leafElement) {
        this.leaf.addElement(leafElement);
    }

    public IElement getLeaf(long j) {
        return this.leaf.getElement(j);
    }

    public IElement getElementForIndex(int i) {
        return this.leaf.getElementForIndex(i);
    }

    public void dispose() {
        super.dispose();
        ElementCollectionImpl elementCollectionImpl = this.leaf;
        if (elementCollectionImpl != null) {
            elementCollectionImpl.dispose();
            this.leaf = null;
        }
    }
}
