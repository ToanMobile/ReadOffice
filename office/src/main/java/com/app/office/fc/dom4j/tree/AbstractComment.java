package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Comment;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Visitor;
import java.io.IOException;
import java.io.Writer;

public abstract class AbstractComment extends AbstractCharacterData implements Comment {
    public short getNodeType() {
        return 8;
    }

    public String getPath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "comment()";
        }
        return parent.getPath(element) + "/comment()";
    }

    public String getUniquePath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "comment()";
        }
        return parent.getUniquePath(element) + "/comment()";
    }

    public String toString() {
        return super.toString() + " [Comment: \"" + getText() + "\"]";
    }

    public String asXML() {
        return "<!--" + getText() + "-->";
    }

    public void write(Writer writer) throws IOException {
        writer.write("<!--");
        writer.write(getText());
        writer.write("-->");
    }

    public void accept(Visitor visitor) {
        visitor.visit((Comment) this);
    }
}
