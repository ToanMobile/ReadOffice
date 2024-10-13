package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Entity;
import com.app.office.fc.dom4j.Visitor;
import java.io.IOException;
import java.io.Writer;

public abstract class AbstractEntity extends AbstractNode implements Entity {
    public short getNodeType() {
        return 5;
    }

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

    public String toString() {
        return super.toString() + " [Entity: &" + getName() + ";]";
    }

    public String getStringValue() {
        return "&" + getName() + ";";
    }

    public String asXML() {
        return "&" + getName() + ";";
    }

    public void write(Writer writer) throws IOException {
        writer.write("&");
        writer.write(getName());
        writer.write(";");
    }

    public void accept(Visitor visitor) {
        visitor.visit((Entity) this);
    }
}
