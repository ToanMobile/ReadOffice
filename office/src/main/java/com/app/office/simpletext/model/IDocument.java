package com.app.office.simpletext.model;

public interface IDocument {
    void appendElement(IElement iElement, long j);

    void appendParagraph(IElement iElement, long j);

    void appendSection(IElement iElement);

    void dispose();

    long getArea(long j);

    long getAreaEnd(long j);

    long getAreaStart(long j);

    IElement getFEElement(long j);

    IElement getHFElement(long j, byte b);

    IElement getLeaf(long j);

    long getLength(long j);

    int getParaCount(long j);

    IElement getParagraph(long j);

    IElement getParagraphForIndex(int i, long j);

    IElement getSection(long j);

    String getText(long j, long j2);

    void insertString(String str, IAttributeSet iAttributeSet, long j);

    void setLeafAttr(long j, int i, IAttributeSet iAttributeSet);

    void setParagraphAttr(long j, int i, IAttributeSet iAttributeSet);

    void setSectionAttr(long j, int i, IAttributeSet iAttributeSet);
}
