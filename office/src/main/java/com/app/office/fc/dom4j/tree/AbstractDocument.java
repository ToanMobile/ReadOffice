package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Branch;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.DocumentType;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.IllegalAddException;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.Visitor;
import com.app.office.fc.dom4j.io.OutputFormat;
import com.app.office.fc.dom4j.io.XMLWriter;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public abstract class AbstractDocument extends AbstractBranch implements Document {
    protected String encoding;

    public Node asXPathResult(Element element) {
        return this;
    }

    public Document getDocument() {
        return this;
    }

    public short getNodeType() {
        return 9;
    }

    public String getPath(Element element) {
        return PackagingURIHelper.FORWARD_SLASH_STRING;
    }

    public String getUniquePath(Element element) {
        return PackagingURIHelper.FORWARD_SLASH_STRING;
    }

    public String getXMLEncoding() {
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract void rootElementAdded(Element element);

    public String getStringValue() {
        Element rootElement = getRootElement();
        return rootElement != null ? rootElement.getStringValue() : "";
    }

    public String asXML() {
        OutputFormat outputFormat = new OutputFormat();
        outputFormat.setEncoding(this.encoding);
        try {
            StringWriter stringWriter = new StringWriter();
            XMLWriter xMLWriter = new XMLWriter((Writer) stringWriter, outputFormat);
            xMLWriter.write((Document) this);
            xMLWriter.flush();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException while generating textual representation: " + e.getMessage());
        }
    }

    public void write(Writer writer) throws IOException {
        OutputFormat outputFormat = new OutputFormat();
        outputFormat.setEncoding(this.encoding);
        new XMLWriter(writer, outputFormat).write((Document) this);
    }

    public void accept(Visitor visitor) {
        visitor.visit((Document) this);
        DocumentType docType = getDocType();
        if (docType != null) {
            visitor.visit(docType);
        }
        List content = content();
        if (content != null) {
            for (Object next : content) {
                if (next instanceof String) {
                    visitor.visit(getDocumentFactory().createText((String) next));
                } else {
                    ((Node) next).accept(visitor);
                }
            }
        }
    }

    public String toString() {
        return super.toString() + " [Document: name " + getName() + "]";
    }

    public void normalize() {
        Element rootElement = getRootElement();
        if (rootElement != null) {
            rootElement.normalize();
        }
    }

    public Document addComment(String str) {
        add(getDocumentFactory().createComment(str));
        return this;
    }

    public Document addProcessingInstruction(String str, String str2) {
        add(getDocumentFactory().createProcessingInstruction(str, str2));
        return this;
    }

    public Document addProcessingInstruction(String str, Map map) {
        add(getDocumentFactory().createProcessingInstruction(str, map));
        return this;
    }

    public Element addElement(String str) {
        Element createElement = getDocumentFactory().createElement(str);
        add(createElement);
        return createElement;
    }

    public Element addElement(String str, String str2) {
        Element createElement = getDocumentFactory().createElement(str, str2);
        add(createElement);
        return createElement;
    }

    public Element addElement(QName qName) {
        Element createElement = getDocumentFactory().createElement(qName);
        add(createElement);
        return createElement;
    }

    public void setRootElement(Element element) {
        clearContent();
        if (element != null) {
            super.add(element);
            rootElementAdded(element);
        }
    }

    public void add(Element element) {
        checkAddElementAllowed(element);
        super.add(element);
        rootElementAdded(element);
    }

    public boolean remove(Element element) {
        boolean remove = super.remove(element);
        if (getRootElement() != null && remove) {
            setRootElement((Element) null);
        }
        element.setDocument((Document) null);
        return remove;
    }

    /* access modifiers changed from: protected */
    public void childAdded(Node node) {
        if (node != null) {
            node.setDocument(this);
        }
    }

    /* access modifiers changed from: protected */
    public void childRemoved(Node node) {
        if (node != null) {
            node.setDocument((Document) null);
        }
    }

    /* access modifiers changed from: protected */
    public void checkAddElementAllowed(Element element) {
        Element rootElement = getRootElement();
        if (rootElement != null) {
            throw new IllegalAddException((Branch) this, (Node) element, "Cannot add another element to this Document as it already has a root element of: " + rootElement.getQualifiedName());
        }
    }

    public void setXMLEncoding(String str) {
        this.encoding = str;
    }
}
