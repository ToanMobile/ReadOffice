package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.CharacterData;
import com.app.office.fc.dom4j.Element;

public abstract class AbstractCharacterData extends AbstractNode implements CharacterData {
    public String getPath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "text()";
        }
        return parent.getPath(element) + "/text()";
    }

    public String getUniquePath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "text()";
        }
        return parent.getUniquePath(element) + "/text()";
    }

    public void appendText(String str) {
        setText(getText() + str);
    }
}
