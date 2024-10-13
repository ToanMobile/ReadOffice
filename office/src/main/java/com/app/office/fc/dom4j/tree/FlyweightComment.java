package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Comment;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Node;

public class FlyweightComment extends AbstractComment implements Comment {
    protected String text;

    public FlyweightComment(String str) {
        this.text = str;
    }

    public String getText() {
        return this.text;
    }

    /* access modifiers changed from: protected */
    public Node createXPathResult(Element element) {
        return new DefaultComment(element, getText());
    }
}
