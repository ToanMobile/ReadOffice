package com.app.office.fc.dom4j.dom;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.tree.DefaultProcessingInstruction;
import java.util.Map;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.UserDataHandler;

public class DOMProcessingInstruction extends DefaultProcessingInstruction implements ProcessingInstruction {
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

    public DOMProcessingInstruction(String str, Map map) {
        super(str, map);
    }

    public DOMProcessingInstruction(String str, String str2) {
        super(str, str2);
    }

    public DOMProcessingInstruction(Element element, String str, String str2) {
        super(element, str, str2);
    }

    public boolean supports(String str, String str2) {
        return DOMNodeHelper.supports(this, str, str2);
    }

    public String getNamespaceURI() {
        return DOMNodeHelper.getNamespaceURI(this);
    }

    public String getPrefix() {
        return DOMNodeHelper.getPrefix(this);
    }

    public void setPrefix(String str) throws DOMException {
        DOMNodeHelper.setPrefix(this, str);
    }

    public String getLocalName() {
        return DOMNodeHelper.getLocalName(this);
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

    public Node getParentNode() {
        return DOMNodeHelper.getParentNode(this);
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
        throw new DOMException(3, "PI nodes cannot have children");
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

    public String getData() {
        return getText();
    }

    public void setData(String str) throws DOMException {
        if (!isReadOnly()) {
            setText(str);
            return;
        }
        throw new DOMException(7, "This ProcessingInstruction is read only");
    }
}
