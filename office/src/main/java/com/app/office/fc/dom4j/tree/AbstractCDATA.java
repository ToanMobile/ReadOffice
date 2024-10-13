package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.CDATA;
import com.app.office.fc.dom4j.Visitor;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class AbstractCDATA extends AbstractCharacterData implements CDATA {
    public short getNodeType() {
        return 4;
    }

    public String toString() {
        return super.toString() + " [CDATA: \"" + getText() + "\"]";
    }

    public String asXML() {
        StringWriter stringWriter = new StringWriter();
        try {
            write(stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public void write(Writer writer) throws IOException {
        writer.write("<![CDATA[");
        if (getText() != null) {
            writer.write(getText());
        }
        writer.write("]]>");
    }

    public void accept(Visitor visitor) {
        visitor.visit((CDATA) this);
    }
}
