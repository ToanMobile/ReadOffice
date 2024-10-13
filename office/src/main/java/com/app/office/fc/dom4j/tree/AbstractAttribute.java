package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Attribute;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.Visitor;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.io.IOException;
import java.io.Writer;

public abstract class AbstractAttribute extends AbstractNode implements Attribute {
    public short getNodeType() {
        return 2;
    }

    public void setNamespace(Namespace namespace) {
        throw new UnsupportedOperationException("This Attribute is read only and cannot be changed");
    }

    public String getText() {
        return getValue();
    }

    public void setText(String str) {
        setValue(str);
    }

    public void setValue(String str) {
        throw new UnsupportedOperationException("This Attribute is read only and cannot be changed");
    }

    public Object getData() {
        return getValue();
    }

    public void setData(Object obj) {
        setValue(obj == null ? null : obj.toString());
    }

    public String toString() {
        return super.toString() + " [Attribute: name " + getQualifiedName() + " value \"" + getValue() + "\"]";
    }

    public String asXML() {
        return getQualifiedName() + "=\"" + getValue() + "\"";
    }

    public void write(Writer writer) throws IOException {
        writer.write(getQualifiedName());
        writer.write("=\"");
        writer.write(getValue());
        writer.write("\"");
    }

    public void accept(Visitor visitor) {
        visitor.visit((Attribute) this);
    }

    public Namespace getNamespace() {
        return getQName().getNamespace();
    }

    public String getName() {
        return getQName().getName();
    }

    public String getNamespacePrefix() {
        return getQName().getNamespacePrefix();
    }

    public String getNamespaceURI() {
        return getQName().getNamespaceURI();
    }

    public String getQualifiedName() {
        return getQName().getQualifiedName();
    }

    public String getPath(Element element) {
        StringBuffer stringBuffer = new StringBuffer();
        Element parent = getParent();
        if (!(parent == null || parent == element)) {
            stringBuffer.append(parent.getPath(element));
            stringBuffer.append(PackagingURIHelper.FORWARD_SLASH_STRING);
        }
        stringBuffer.append("@");
        String namespaceURI = getNamespaceURI();
        String namespacePrefix = getNamespacePrefix();
        if (namespaceURI == null || namespaceURI.length() == 0 || namespacePrefix == null || namespacePrefix.length() == 0) {
            stringBuffer.append(getName());
        } else {
            stringBuffer.append(getQualifiedName());
        }
        return stringBuffer.toString();
    }

    public String getUniquePath(Element element) {
        StringBuffer stringBuffer = new StringBuffer();
        Element parent = getParent();
        if (!(parent == null || parent == element)) {
            stringBuffer.append(parent.getUniquePath(element));
            stringBuffer.append(PackagingURIHelper.FORWARD_SLASH_STRING);
        }
        stringBuffer.append("@");
        String namespaceURI = getNamespaceURI();
        String namespacePrefix = getNamespacePrefix();
        if (namespaceURI == null || namespaceURI.length() == 0 || namespacePrefix == null || namespacePrefix.length() == 0) {
            stringBuffer.append(getName());
        } else {
            stringBuffer.append(getQualifiedName());
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public Node createXPathResult(Element element) {
        return new DefaultAttribute(element, getQName(), getValue());
    }
}
