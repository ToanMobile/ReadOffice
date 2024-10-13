package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Element;
import java.util.Iterator;

public class ElementIterator extends FilterIterator {
    public ElementIterator(Iterator it) {
        super(it);
    }

    /* access modifiers changed from: protected */
    public boolean matches(Object obj) {
        return obj instanceof Element;
    }
}
