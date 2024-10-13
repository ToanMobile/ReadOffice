package com.app.office.fc.dom4j.util;

import com.app.office.fc.dom4j.DocumentFactory;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.QName;

public class NonLazyDocumentFactory extends DocumentFactory {
    protected static transient NonLazyDocumentFactory singleton = new NonLazyDocumentFactory();

    public static DocumentFactory getInstance() {
        return singleton;
    }

    public Element createElement(QName qName) {
        return new NonLazyElement(qName);
    }
}
