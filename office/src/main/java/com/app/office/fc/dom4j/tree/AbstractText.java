package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Text;
import com.app.office.fc.dom4j.Visitor;
import java.io.IOException;
import java.io.Writer;

public abstract class AbstractText extends AbstractCharacterData implements Text {
    public short getNodeType() {
        return 3;
    }

    public String toString() {
        return super.toString() + " [Text: \"" + getText() + "\"]";
    }

    public String asXML() {
        return getText();
    }

    public void write(Writer writer) throws IOException {
        writer.write(getText());
    }

    public void accept(Visitor visitor) {
        visitor.visit((Text) this);
    }
}
