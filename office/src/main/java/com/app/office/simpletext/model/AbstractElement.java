package com.app.office.simpletext.model;

public abstract class AbstractElement implements IElement {
    protected IAttributeSet attr = new AttributeSetImpl();
    protected long end;
    protected long start;

    public String getText(IDocument iDocument) {
        return null;
    }

    public short getType() {
        return -1;
    }

    public void setStartOffset(long j) {
        this.start = j;
    }

    public long getStartOffset() {
        return this.start;
    }

    public void setEndOffset(long j) {
        this.end = j;
    }

    public long getEndOffset() {
        return this.end;
    }

    public void setAttribute(IAttributeSet iAttributeSet) {
        this.attr = iAttributeSet;
    }

    public IAttributeSet getAttribute() {
        return this.attr;
    }

    public String toString() {
        return "[" + this.start + ", " + this.end + "]：" + getText((IDocument) null);
    }

    public void dispose() {
        IAttributeSet iAttributeSet = this.attr;
        if (iAttributeSet != null) {
            iAttributeSet.dispose();
            this.attr = null;
        }
    }
}
