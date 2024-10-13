package com.app.office.simpletext.model;

public interface IElementCollection {
    void dispose();

    IElement getElement(long j);

    IElement getElementForIndex(int i);

    int size();
}
