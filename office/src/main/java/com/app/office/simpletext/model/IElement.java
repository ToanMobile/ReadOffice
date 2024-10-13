package com.app.office.simpletext.model;

public interface IElement {
    void dispose();

    IAttributeSet getAttribute();

    long getEndOffset();

    long getStartOffset();

    String getText(IDocument iDocument);

    short getType();

    void setAttribute(IAttributeSet iAttributeSet);

    void setEndOffset(long j);

    void setStartOffset(long j);
}
