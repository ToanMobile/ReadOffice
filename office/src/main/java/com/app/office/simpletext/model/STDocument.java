package com.app.office.simpletext.model;

import com.app.office.constant.wp.WPModelConstant;

public class STDocument implements IDocument {
    private SectionElement section;

    public void appendElement(IElement iElement, long j) {
    }

    public long getArea(long j) {
        return j & WPModelConstant.AREA_MASK;
    }

    public long getAreaStart(long j) {
        long j2 = WPModelConstant.AREA_MASK & j;
        return j2 == WPModelConstant.TEXTBOX ? j & WPModelConstant.TEXTBOX_MASK : j2;
    }

    public IElement getFEElement(long j) {
        return null;
    }

    public IElement getHFElement(long j, byte b) {
        return null;
    }

    public void insertString(String str, IAttributeSet iAttributeSet, long j) {
    }

    public long getAreaEnd(long j) {
        return getAreaStart(j) + getLength(j);
    }

    public long getLength(long j) {
        return this.section.getEndOffset() - this.section.getStartOffset();
    }

    public IElement getSection(long j) {
        return this.section;
    }

    public void appendSection(IElement iElement) {
        this.section = (SectionElement) iElement;
    }

    public IElement getParagraph(long j) {
        return this.section.getParaCollection().getElement(j);
    }

    public IElement getParagraphForIndex(int i, long j) {
        return this.section.getParaCollection().getElementForIndex(i);
    }

    public void appendParagraph(IElement iElement, long j) {
        this.section.appendParagraph(iElement, j);
    }

    public IElement getLeaf(long j) {
        IElement paragraph = getParagraph(j);
        if (paragraph != null) {
            return ((ParagraphElement) paragraph).getLeaf(j);
        }
        return null;
    }

    public void setSectionAttr(long j, int i, IAttributeSet iAttributeSet) {
        this.section.getAttribute().mergeAttribute(iAttributeSet);
    }

    public void setParagraphAttr(long j, int i, IAttributeSet iAttributeSet) {
        long j2 = ((long) i) + j;
        while (j < j2) {
            IElement element = this.section.getParaCollection().getElement(j);
            element.getAttribute().mergeAttribute(iAttributeSet);
            j = element.getEndOffset();
        }
    }

    public void setLeafAttr(long j, int i, IAttributeSet iAttributeSet) {
        long j2 = ((long) i) + j;
        while (j < j2) {
            IElement leaf = getLeaf(j);
            leaf.getAttribute().mergeAttribute(iAttributeSet);
            j = leaf.getEndOffset();
        }
    }

    public int getParaCount(long j) {
        return this.section.getParaCollection().size();
    }

    public String getText(long j, long j2) {
        if (j2 - j == 0 || getArea(j) != getArea(j2)) {
            return "";
        }
        IElement leaf = getLeaf(j);
        String text = leaf.getText((IDocument) null);
        String substring = text.substring((int) (j - leaf.getStartOffset()), (int) (j2 >= leaf.getEndOffset() ? (long) text.length() : j2 - leaf.getStartOffset()));
        long endOffset = leaf.getEndOffset();
        while (endOffset < j2) {
            IElement leaf2 = getLeaf(endOffset);
            String text2 = leaf2.getText((IDocument) null);
            String substring2 = text2.substring(0, (int) (j2 >= leaf2.getEndOffset() ? (long) text2.length() : j2 - leaf2.getStartOffset()));
            endOffset = leaf2.getEndOffset();
            substring = substring2;
        }
        return substring;
    }

    public void dispose() {
        SectionElement sectionElement = this.section;
        if (sectionElement != null) {
            sectionElement.dispose();
            this.section = null;
        }
    }
}
