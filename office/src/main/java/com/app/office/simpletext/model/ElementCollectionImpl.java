package com.app.office.simpletext.model;

public class ElementCollectionImpl implements IElementCollection {
    public static final int CAPACITY = 5;
    protected IElement[] elems;
    private int size;

    public ElementCollectionImpl(int i) {
        this.elems = new IElement[i];
    }

    public void addElement(IElement iElement) {
        if (this.size >= this.elems.length) {
            ensureCapacity();
        }
        IElement[] iElementArr = this.elems;
        int i = this.size;
        iElementArr[i] = iElement;
        this.size = i + 1;
    }

    public void insertElementForIndex(IElement iElement, int i) {
        if (this.size + 1 >= this.elems.length) {
            ensureCapacity();
        }
        for (int i2 = this.size; i2 >= i; i2--) {
            IElement[] iElementArr = this.elems;
            iElementArr[i2] = iElementArr[i2 - 1];
        }
        this.elems[i] = iElement;
        this.size++;
    }

    public void removeElement(long j) {
        int index = getIndex(j);
        if (index >= 0) {
            removeElement((long) index);
        }
    }

    public void removeElementForIndex(int i) {
        if (i >= 0) {
            IElement iElement = this.elems[i];
            while (true) {
                i++;
                int i2 = this.size;
                if (i < i2) {
                    IElement[] iElementArr = this.elems;
                    iElementArr[i - 1] = iElementArr[i];
                } else {
                    this.elems[i2] = null;
                    this.size = i2 - 1;
                    iElement.dispose();
                    return;
                }
            }
        }
    }

    public IElement getElement(long j) {
        return getElementForIndex(getIndex(j));
    }

    public IElement getElementForIndex(int i) {
        if (i < 0 || i >= this.size) {
            return null;
        }
        return this.elems[i];
    }

    public int size() {
        return this.size;
    }

    /* access modifiers changed from: protected */
    public int getIndex(long j) {
        int i = this.size;
        if (i == 0 || j < 0 || j >= this.elems[i - 1].getEndOffset()) {
            return -1;
        }
        int i2 = this.size;
        int i3 = 0;
        while (true) {
            int i4 = (i2 + i3) / 2;
            IElement iElement = this.elems[i4];
            long startOffset = iElement.getStartOffset();
            long endOffset = iElement.getEndOffset();
            if (j >= startOffset && j < endOffset) {
                return i4;
            }
            if (startOffset > j) {
                i2 = i4 - 1;
            } else if (endOffset <= j) {
                i3 = i4 + 1;
            }
        }
    }

    private void ensureCapacity() {
        int i = this.size;
        IElement[] iElementArr = new IElement[(i + 5)];
        System.arraycopy(this.elems, 0, iElementArr, 0, i);
        this.elems = iElementArr;
    }

    public void dispose() {
        if (this.elems != null) {
            for (int i = 0; i < this.size; i++) {
                this.elems[i].dispose();
                this.elems[i] = null;
            }
            this.elems = null;
        }
        this.size = 0;
    }
}
