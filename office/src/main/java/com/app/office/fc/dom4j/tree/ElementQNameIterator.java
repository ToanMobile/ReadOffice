package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.QName;
import java.util.Iterator;

public class ElementQNameIterator extends FilterIterator {
    private QName qName;

    public ElementQNameIterator(Iterator it, QName qName2) {
        super(it);
        this.qName = qName2;
    }

    /* access modifiers changed from: protected */
    public boolean matches(Object obj) {
        if (obj instanceof Element) {
            return this.qName.equals(((Element) obj).getQName());
        }
        return false;
    }
}
