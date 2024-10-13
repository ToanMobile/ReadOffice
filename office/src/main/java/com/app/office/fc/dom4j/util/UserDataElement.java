package com.app.office.fc.dom4j.util;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.tree.DefaultElement;

public class UserDataElement extends DefaultElement {
    private Object data;

    public UserDataElement(String str) {
        super(str);
    }

    public UserDataElement(QName qName) {
        super(qName);
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public String toString() {
        return super.toString() + " userData: " + this.data;
    }

    public Object clone() {
        UserDataElement userDataElement = (UserDataElement) super.clone();
        if (userDataElement != this) {
            userDataElement.data = getCopyOfUserData();
        }
        return userDataElement;
    }

    /* access modifiers changed from: protected */
    public Object getCopyOfUserData() {
        return this.data;
    }

    /* access modifiers changed from: protected */
    public Element createElement(String str) {
        Element createElement = getDocumentFactory().createElement(str);
        createElement.setData(getCopyOfUserData());
        return createElement;
    }

    /* access modifiers changed from: protected */
    public Element createElement(QName qName) {
        Element createElement = getDocumentFactory().createElement(qName);
        createElement.setData(getCopyOfUserData());
        return createElement;
    }
}
