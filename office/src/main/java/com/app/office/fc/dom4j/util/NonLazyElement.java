package com.app.office.fc.dom4j.util;

import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.tree.BaseElement;

public class NonLazyElement extends BaseElement {
    public NonLazyElement(String str) {
        super(str);
        this.attributes = createAttributeList();
        this.content = createContentList();
    }

    public NonLazyElement(QName qName) {
        super(qName);
        this.attributes = createAttributeList();
        this.content = createContentList();
    }

    public NonLazyElement(String str, Namespace namespace) {
        super(str, namespace);
        this.attributes = createAttributeList();
        this.content = createContentList();
    }

    public NonLazyElement(QName qName, int i) {
        super(qName);
        this.attributes = createAttributeList(i);
        this.content = createContentList();
    }
}
