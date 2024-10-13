package com.app.office.simpletext.model;

public interface IAttributeSet {
    IAttributeSet clone();

    void dispose();

    int getAttribute(short s);

    int getID();

    void mergeAttribute(IAttributeSet iAttributeSet);

    void removeAttribute(short s);

    void setAttribute(short s, int i);
}
