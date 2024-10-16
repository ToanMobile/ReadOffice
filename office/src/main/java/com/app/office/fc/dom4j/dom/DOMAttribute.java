package com.app.office.fc.dom4j.dom;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.tree.DefaultAttribute;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class DOMAttribute extends DefaultAttribute implements Attr {
    public short compareDocumentPosition(Node node) throws DOMException {
        return 0;
    }

    public NamedNodeMap getAttributes() {
        return null;
    }

    public String getBaseURI() {
        return null;
    }

    public Object getFeature(String str, String str2) {
        return null;
    }

    public Node getParentNode() {
        return null;
    }

    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    public boolean getSpecified() {
        return true;
    }

    public String getTextContent() throws DOMException {
        return null;
    }

    public Object getUserData(String str) {
        return null;
    }

    public boolean isDefaultNamespace(String str) {
        return false;
    }

    public boolean isEqualNode(Node node) {
        return false;
    }

    public boolean isId() {
        return false;
    }

    public boolean isSameNode(Node node) {
        return false;
    }

    public String lookupNamespaceURI(String str) {
        return null;
    }

    public String lookupPrefix(String str) {
        return null;
    }

    public void setTextContent(String str) throws DOMException {
    }

    public Object setUserData(String str, Object obj, UserDataHandler userDataHandler) {
        return null;
    }

    public DOMAttribute(QName qName) {
        super(qName);
    }

    public DOMAttribute(QName qName, String str) {
        super(qName, str);
    }

    public DOMAttribute(Element element, QName qName, String str) {
        super(element, qName, str);
    }

    public boolean supports(String str, String str2) {
        return DOMNodeHelper.supports(this, str, str2);
    }

    public String getNamespaceURI() {
        return getQName().getNamespaceURI();
    }

    public String getPrefix() {
        return getQName().getNamespacePrefix();
    }

    public void setPrefix(String str) throws DOMException {
        DOMNodeHelper.setPrefix(this, str);
    }

    public String getLocalName() {
        return getQName().getName();
    }

    public String getNodeName() {
        return getName();
    }

    public String getNodeValue() throws DOMException {
        return DOMNodeHelper.getNodeValue(this);
    }

    public void setNodeValue(String str) throws DOMException {
        DOMNodeHelper.setNodeValue(this, str);
    }

    public NodeList getChildNodes() {
        return DOMNodeHelper.getChildNodes(this);
    }

    public Node getFirstChild() {
        return DOMNodeHelper.getFirstChild(this);
    }

    public Node getLastChild() {
        return DOMNodeHelper.getLastChild(this);
    }

    public Node getPreviousSibling() {
        return DOMNodeHelper.getPreviousSibling(this);
    }

    public Node getNextSibling() {
        return DOMNodeHelper.getNextSibling(this);
    }

    public Document getOwnerDocument() {
        return DOMNodeHelper.getOwnerDocument(this);
    }

    public Node insertBefore(Node node, Node node2) throws DOMException {
        checkNewChildNode(node);
        return DOMNodeHelper.insertBefore(this, node, node2);
    }

    public Node replaceChild(Node node, Node node2) throws DOMException {
        checkNewChildNode(node);
        return DOMNodeHelper.replaceChild(this, node, node2);
    }

    public Node removeChild(Node node) throws DOMException {
        return DOMNodeHelper.removeChild(this, node);
    }

    public Node appendChild(Node node) throws DOMException {
        checkNewChildNode(node);
        return DOMNodeHelper.appendChild(this, node);
    }

    private void checkNewChildNode(Node node) throws DOMException {
        short nodeType = node.getNodeType();
        if (nodeType != 3 && nodeType != 5) {
            throw new DOMException(3, "The node cannot be a child of attribute");
        }
    }

    public boolean hasChildNodes() {
        return DOMNodeHelper.hasChildNodes(this);
    }

    public Node cloneNode(boolean z) {
        return DOMNodeHelper.cloneNode(this, z);
    }

    public void normalize() {
        DOMNodeHelper.normalize(this);
    }

    public boolean isSupported(String str, String str2) {
        return DOMNodeHelper.isSupported(this, str, str2);
    }

    public boolean hasAttributes() {
        return DOMNodeHelper.hasAttributes(this);
    }

    public org.w3c.dom.Element getOwnerElement() {
        return DOMNodeHelper.asDOMElement(getParent());
    }
}
