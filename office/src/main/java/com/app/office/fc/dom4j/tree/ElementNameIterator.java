package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Element;
import java.util.Iterator;

public class ElementNameIterator extends FilterIterator {
    private String name;

    public ElementNameIterator(Iterator it, String str) {
        super(it);
        this.name = str;
    }

    /* access modifiers changed from: protected */
    public boolean matches(Object obj) {
        if (obj instanceof Element) {
            return this.name.equals(((Element) obj).getName());
        }
        return false;
    }
}
