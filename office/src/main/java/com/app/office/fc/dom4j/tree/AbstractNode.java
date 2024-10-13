package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.DocumentFactory;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.NodeFilter;
import com.app.office.fc.dom4j.XPath;
import com.app.office.fc.dom4j.rule.Pattern;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

public abstract class AbstractNode implements Node, Cloneable, Serializable {
    private static final DocumentFactory DOCUMENT_FACTORY = DocumentFactory.getInstance();
    protected static final String[] NODE_TYPE_NAMES = {"Node", "Element", "Attribute", "Text", "CDATA", "Entity", "Entity", "ProcessingInstruction", "Comment", "Document", "DocumentType", "DocumentFragment", "Notation", "Namespace", "Unknown"};

    public String getName() {
        return null;
    }

    public short getNodeType() {
        return 14;
    }

    public Element getParent() {
        return null;
    }

    public String getText() {
        return null;
    }

    public boolean hasContent() {
        return false;
    }

    public boolean isReadOnly() {
        return true;
    }

    public void setDocument(Document document) {
    }

    public void setParent(Element element) {
    }

    public boolean supportsParent() {
        return false;
    }

    public String getNodeTypeName() {
        short nodeType = getNodeType();
        if (nodeType < 0) {
            return "Unknown";
        }
        String[] strArr = NODE_TYPE_NAMES;
        return nodeType >= strArr.length ? "Unknown" : strArr[nodeType];
    }

    public Document getDocument() {
        Element parent = getParent();
        if (parent != null) {
            return parent.getDocument();
        }
        return null;
    }

    public String getPath() {
        return getPath((Element) null);
    }

    public String getUniquePath() {
        return getUniquePath((Element) null);
    }

    public Object clone() {
        if (isReadOnly()) {
            return this;
        }
        try {
            Node node = (Node) super.clone();
            node.setParent((Element) null);
            node.setDocument((Document) null);
            return node;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("This should never happen. Caught: " + e);
        }
    }

    public Node detach() {
        Element parent = getParent();
        if (parent != null) {
            parent.remove((Node) this);
        } else {
            Document document = getDocument();
            if (document != null) {
                document.remove((Node) this);
            }
        }
        setParent((Element) null);
        setDocument((Document) null);
        return this;
    }

    public void setName(String str) {
        throw new UnsupportedOperationException("This node cannot be modified");
    }

    public String getStringValue() {
        return getText();
    }

    public void setText(String str) {
        throw new UnsupportedOperationException("This node cannot be modified");
    }

    public void write(Writer writer) throws IOException {
        writer.write(asXML());
    }

    public Object selectObject(String str) {
        return createXPath(str).evaluate(this);
    }

    public List selectNodes(String str) {
        return createXPath(str).selectNodes(this);
    }

    public List selectNodes(String str, String str2) {
        return selectNodes(str, str2, false);
    }

    public List selectNodes(String str, String str2, boolean z) {
        return createXPath(str).selectNodes(this, createXPath(str2), z);
    }

    public Node selectSingleNode(String str) {
        return createXPath(str).selectSingleNode(this);
    }

    public String valueOf(String str) {
        return createXPath(str).valueOf(this);
    }

    public Number numberValueOf(String str) {
        return createXPath(str).numberValueOf(this);
    }

    public boolean matches(String str) {
        return createXPathFilter(str).matches(this);
    }

    public XPath createXPath(String str) {
        return getDocumentFactory().createXPath(str);
    }

    public NodeFilter createXPathFilter(String str) {
        return getDocumentFactory().createXPathFilter(str);
    }

    public Pattern createPattern(String str) {
        return getDocumentFactory().createPattern(str);
    }

    public Node asXPathResult(Element element) {
        if (supportsParent()) {
            return this;
        }
        return createXPathResult(element);
    }

    /* access modifiers changed from: protected */
    public DocumentFactory getDocumentFactory() {
        return DOCUMENT_FACTORY;
    }

    /* access modifiers changed from: protected */
    public Node createXPathResult(Element element) {
        throw new RuntimeException("asXPathResult() not yet implemented fully for: " + this);
    }
}
