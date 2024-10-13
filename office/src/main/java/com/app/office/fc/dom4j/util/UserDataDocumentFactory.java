package com.app.office.fc.dom4j.util;

import com.app.office.fc.dom4j.Attribute;
import com.app.office.fc.dom4j.DocumentFactory;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.QName;

public class UserDataDocumentFactory extends DocumentFactory {
    protected static transient UserDataDocumentFactory singleton = new UserDataDocumentFactory();

    public static DocumentFactory getInstance() {
        return singleton;
    }

    public Element createElement(QName qName) {
        return new UserDataElement(qName);
    }

    public Attribute createAttribute(Element element, QName qName, String str) {
        return new UserDataAttribute(qName, str);
    }
}
