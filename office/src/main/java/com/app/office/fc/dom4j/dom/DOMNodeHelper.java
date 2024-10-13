package com.app.office.fc.dom4j.dom;

import com.app.office.fc.dom4j.Branch;
import com.app.office.fc.dom4j.CharacterData;
import com.app.office.fc.dom4j.Element;
import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DOMNodeHelper {
    public static final NodeList EMPTY_NODE_LIST = new EmptyNodeList();

    public static class EmptyNodeList implements NodeList {
        public int getLength() {
            return 0;
        }

        public Node item(int i) {
            return null;
        }
    }

    public static NamedNodeMap getAttributes(com.app.office.fc.dom4j.Node node) {
        return null;
    }

    public static Node getFirstChild(com.app.office.fc.dom4j.Node node) {
        return null;
    }

    public static Node getLastChild(com.app.office.fc.dom4j.Node node) {
        return null;
    }

    public static String getLocalName(com.app.office.fc.dom4j.Node node) {
        return null;
    }

    public static String getNamespaceURI(com.app.office.fc.dom4j.Node node) {
        return null;
    }

    public static String getPrefix(com.app.office.fc.dom4j.Node node) {
        return null;
    }

    public static boolean hasChildNodes(com.app.office.fc.dom4j.Node node) {
        return false;
    }

    public static boolean isSupported(com.app.office.fc.dom4j.Node node, String str, String str2) {
        return false;
    }

    public static boolean supports(com.app.office.fc.dom4j.Node node, String str, String str2) {
        return false;
    }

    protected DOMNodeHelper() {
    }

    public static void setPrefix(com.app.office.fc.dom4j.Node node, String str) throws DOMException {
        notSupported();
    }

    public static String getNodeValue(com.app.office.fc.dom4j.Node node) throws DOMException {
        return node.getText();
    }

    public static void setNodeValue(com.app.office.fc.dom4j.Node node, String str) throws DOMException {
        node.setText(str);
    }

    public static Node getParentNode(com.app.office.fc.dom4j.Node node) {
        return asDOMNode(node.getParent());
    }

    public static NodeList getChildNodes(com.app.office.fc.dom4j.Node node) {
        return EMPTY_NODE_LIST;
    }

    public static Node getPreviousSibling(com.app.office.fc.dom4j.Node node) {
        int indexOf;
        Element parent = node.getParent();
        if (parent == null || (indexOf = parent.indexOf(node)) <= 0) {
            return null;
        }
        return asDOMNode(parent.node(indexOf - 1));
    }

    public static Node getNextSibling(com.app.office.fc.dom4j.Node node) {
        int indexOf;
        int i;
        Element parent = node.getParent();
        if (parent == null || (indexOf = parent.indexOf(node)) < 0 || (i = indexOf + 1) >= parent.nodeCount()) {
            return null;
        }
        return asDOMNode(parent.node(i));
    }

    public static Document getOwnerDocument(com.app.office.fc.dom4j.Node node) {
        return asDOMDocument(node.getDocument());
    }

    public static Node insertBefore(com.app.office.fc.dom4j.Node node, Node node2, Node node3) throws DOMException {
        if (node instanceof Branch) {
            Branch branch = (Branch) node;
            List content = branch.content();
            int indexOf = content.indexOf(node3);
            if (indexOf < 0) {
                branch.add((com.app.office.fc.dom4j.Node) node2);
            } else {
                content.add(indexOf, node2);
            }
            return node2;
        }
        throw new DOMException(3, "Children not allowed for this node: " + node);
    }

    public static Node replaceChild(com.app.office.fc.dom4j.Node node, Node node2, Node node3) throws DOMException {
        if (node instanceof Branch) {
            List content = ((Branch) node).content();
            int indexOf = content.indexOf(node3);
            if (indexOf >= 0) {
                content.set(indexOf, node2);
                return node3;
            }
            throw new DOMException(8, "Tried to replace a non existing child for node: " + node);
        }
        throw new DOMException(3, "Children not allowed for this node: " + node);
    }

    public static Node removeChild(com.app.office.fc.dom4j.Node node, Node node2) throws DOMException {
        if (node instanceof Branch) {
            ((Branch) node).remove((com.app.office.fc.dom4j.Node) node2);
            return node2;
        }
        throw new DOMException(3, "Children not allowed for this node: " + node);
    }

    public static Node appendChild(com.app.office.fc.dom4j.Node node, Node node2) throws DOMException {
        if (node instanceof Branch) {
            Branch branch = (Branch) node;
            Node parentNode = node2.getParentNode();
            if (parentNode != null) {
                parentNode.removeChild(node2);
            }
            branch.add((com.app.office.fc.dom4j.Node) node2);
            return node2;
        }
        throw new DOMException(3, "Children not allowed for this node: " + node);
    }

    public static Node cloneNode(com.app.office.fc.dom4j.Node node, boolean z) {
        return asDOMNode((com.app.office.fc.dom4j.Node) node.clone());
    }

    public static void normalize(com.app.office.fc.dom4j.Node node) {
        notSupported();
    }

    public static boolean hasAttributes(com.app.office.fc.dom4j.Node node) {
        if (node == null || !(node instanceof Element) || ((Element) node).attributeCount() <= 0) {
            return false;
        }
        return true;
    }

    public static String getData(CharacterData characterData) throws DOMException {
        return characterData.getText();
    }

    public static void setData(CharacterData characterData, String str) throws DOMException {
        characterData.setText(str);
    }

    public static int getLength(CharacterData characterData) {
        String text = characterData.getText();
        if (text != null) {
            return text.length();
        }
        return 0;
    }

    public static String substringData(CharacterData characterData, int i, int i2) throws DOMException {
        if (i2 >= 0) {
            String text = characterData.getText();
            int length = text != null ? text.length() : 0;
            if (i < 0 || i >= length) {
                throw new DOMException(1, "No text at offset: " + i);
            }
            int i3 = i2 + i;
            if (i3 > length) {
                return text.substring(i);
            }
            return text.substring(i, i3);
        }
        throw new DOMException(1, "Illegal value for count: " + i2);
    }

    public static void appendData(CharacterData characterData, String str) throws DOMException {
        if (!characterData.isReadOnly()) {
            String text = characterData.getText();
            if (text == null) {
                characterData.setText(text);
                return;
            }
            characterData.setText(text + str);
            return;
        }
        throw new DOMException(7, "CharacterData node is read only: " + characterData);
    }

    public static void insertData(CharacterData characterData, int i, String str) throws DOMException {
        if (!characterData.isReadOnly()) {
            String text = characterData.getText();
            if (text == null) {
                characterData.setText(str);
                return;
            }
            int length = text.length();
            if (i < 0 || i > length) {
                throw new DOMException(1, "No text at offset: " + i);
            }
            StringBuffer stringBuffer = new StringBuffer(text);
            stringBuffer.insert(i, str);
            characterData.setText(stringBuffer.toString());
            return;
        }
        throw new DOMException(7, "CharacterData node is read only: " + characterData);
    }

    public static void deleteData(CharacterData characterData, int i, int i2) throws DOMException {
        if (characterData.isReadOnly()) {
            throw new DOMException(7, "CharacterData node is read only: " + characterData);
        } else if (i2 >= 0) {
            String text = characterData.getText();
            if (text != null) {
                int length = text.length();
                if (i < 0 || i >= length) {
                    throw new DOMException(1, "No text at offset: " + i);
                }
                StringBuffer stringBuffer = new StringBuffer(text);
                stringBuffer.delete(i, i2 + i);
                characterData.setText(stringBuffer.toString());
            }
        } else {
            throw new DOMException(1, "Illegal value for count: " + i2);
        }
    }

    public static void replaceData(CharacterData characterData, int i, int i2, String str) throws DOMException {
        if (characterData.isReadOnly()) {
            throw new DOMException(7, "CharacterData node is read only: " + characterData);
        } else if (i2 >= 0) {
            String text = characterData.getText();
            if (text != null) {
                int length = text.length();
                if (i < 0 || i >= length) {
                    throw new DOMException(1, "No text at offset: " + i);
                }
                StringBuffer stringBuffer = new StringBuffer(text);
                stringBuffer.replace(i, i2 + i, str);
                characterData.setText(stringBuffer.toString());
            }
        } else {
            throw new DOMException(1, "Illegal value for count: " + i2);
        }
    }

    public static void appendElementsByTagName(List list, Branch branch, String str) {
        boolean equals = "*".equals(str);
        int nodeCount = branch.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            com.app.office.fc.dom4j.Node node = branch.node(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (equals || str.equals(element.getName())) {
                    list.add(element);
                }
                appendElementsByTagName(list, element, str);
            }
        }
    }

    public static void appendElementsByTagNameNS(List list, Branch branch, String str, String str2) {
        boolean equals = "*".equals(str);
        boolean equals2 = "*".equals(str2);
        int nodeCount = branch.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            com.app.office.fc.dom4j.Node node = branch.node(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if ((equals || (((str == null || str.length() == 0) && (element.getNamespaceURI() == null || element.getNamespaceURI().length() == 0)) || (str != null && str.equals(element.getNamespaceURI())))) && (equals2 || str2.equals(element.getName()))) {
                    list.add(element);
                }
                appendElementsByTagNameNS(list, element, str, str2);
            }
        }
    }

    public static NodeList createNodeList(final List list) {
        return new NodeList() {
            public Node item(int i) {
                if (i >= getLength()) {
                    return null;
                }
                return DOMNodeHelper.asDOMNode((com.app.office.fc.dom4j.Node) list.get(i));
            }

            public int getLength() {
                return list.size();
            }
        };
    }

    public static Node asDOMNode(com.app.office.fc.dom4j.Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof Node) {
            return (Node) node;
        }
        notSupported();
        return null;
    }

    public static Document asDOMDocument(com.app.office.fc.dom4j.Document document) {
        if (document == null) {
            return null;
        }
        if (document instanceof Document) {
            return (Document) document;
        }
        notSupported();
        return null;
    }

    public static DocumentType asDOMDocumentType(com.app.office.fc.dom4j.DocumentType documentType) {
        if (documentType == null) {
            return null;
        }
        if (documentType instanceof DocumentType) {
            return (DocumentType) documentType;
        }
        notSupported();
        return null;
    }

    public static Text asDOMText(CharacterData characterData) {
        if (characterData == null) {
            return null;
        }
        if (characterData instanceof Text) {
            return (Text) characterData;
        }
        notSupported();
        return null;
    }

    public static org.w3c.dom.Element asDOMElement(com.app.office.fc.dom4j.Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof org.w3c.dom.Element) {
            return (org.w3c.dom.Element) node;
        }
        notSupported();
        return null;
    }

    public static Attr asDOMAttr(com.app.office.fc.dom4j.Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof Attr) {
            return (Attr) node;
        }
        notSupported();
        return null;
    }

    public static void notSupported() {
        throw new DOMException(9, "Not supported yet");
    }
}
