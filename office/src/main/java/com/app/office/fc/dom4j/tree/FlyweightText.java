package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.Text;

public class FlyweightText extends AbstractText implements Text {
    protected String text;

    public FlyweightText(String str) {
        this.text = str;
    }

    public String getText() {
        return this.text;
    }

    /* access modifiers changed from: protected */
    public Node createXPathResult(Element element) {
        return new DefaultText(element, getText());
    }
}
