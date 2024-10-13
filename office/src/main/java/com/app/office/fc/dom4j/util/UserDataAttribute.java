package com.app.office.fc.dom4j.util;

import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.tree.DefaultAttribute;

public class UserDataAttribute extends DefaultAttribute {
    private Object data;

    public UserDataAttribute(QName qName) {
        super(qName);
    }

    public UserDataAttribute(QName qName, String str) {
        super(qName, str);
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object obj) {
        this.data = obj;
    }
}
