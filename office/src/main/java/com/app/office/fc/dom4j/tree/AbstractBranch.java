package com.app.office.fc.dom4j.tree;

import com.app.office.fc.dom4j.Branch;
import com.app.office.fc.dom4j.Comment;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.IllegalAddException;
import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.ProcessingInstruction;
import com.app.office.fc.dom4j.QName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public abstract class AbstractBranch extends AbstractNode implements Branch {
    protected static final int DEFAULT_CONTENT_LIST_SIZE = 5;

    /* access modifiers changed from: protected */
    public abstract void addNode(int i, Node node);

    /* access modifiers changed from: protected */
    public abstract void addNode(Node node);

    /* access modifiers changed from: protected */
    public abstract void childAdded(Node node);

    /* access modifiers changed from: protected */
    public abstract void childRemoved(Node node);

    /* access modifiers changed from: protected */
    public abstract List contentList();

    public boolean isReadOnly() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract boolean removeNode(Node node);

    public boolean hasContent() {
        return nodeCount() > 0;
    }

    public List content() {
        return new ContentListFacade(this, contentList());
    }

    public String getText() {
        List contentList = contentList();
        if (contentList == null) {
            return "";
        }
        int size = contentList.size();
        if (size < 1) {
            return "";
        }
        String contentAsText = getContentAsText(contentList.get(0));
        if (size == 1) {
            return contentAsText;
        }
        StringBuffer stringBuffer = new StringBuffer(contentAsText);
        for (int i = 1; i < size; i++) {
            stringBuffer.append(getContentAsText(contentList.get(i)));
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public String getContentAsText(Object obj) {
        if (!(obj instanceof Node)) {
            return obj instanceof String ? (String) obj : "";
        }
        Node node = (Node) obj;
        short nodeType = node.getNodeType();
        if (nodeType == 3 || nodeType == 4 || nodeType == 5) {
            return node.getText();
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public String getContentAsStringValue(Object obj) {
        if (!(obj instanceof Node)) {
            return obj instanceof String ? (String) obj : "";
        }
        Node node = (Node) obj;
        short nodeType = node.getNodeType();
        if (nodeType == 1 || nodeType == 3 || nodeType == 4 || nodeType == 5) {
            return node.getStringValue();
        }
        return "";
    }

    public String getTextTrim() {
        String text = getText();
        StringBuffer stringBuffer = new StringBuffer();
        StringTokenizer stringTokenizer = new StringTokenizer(text);
        while (stringTokenizer.hasMoreTokens()) {
            stringBuffer.append(stringTokenizer.nextToken());
            if (stringTokenizer.hasMoreTokens()) {
                stringBuffer.append(" ");
            }
        }
        return stringBuffer.toString();
    }

    public void setProcessingInstructions(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            addNode((ProcessingInstruction) it.next());
        }
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

    public Element addElement(String str, String str2, String str3) {
        return addElement(getDocumentFactory().createQName(str, Namespace.get(str2, str3)));
    }

    public void add(Node node) {
        short nodeType = node.getNodeType();
        if (nodeType == 1) {
            add((Element) node);
        } else if (nodeType == 7) {
            add((ProcessingInstruction) node);
        } else if (nodeType != 8) {
            invalidNodeTypeAddException(node);
        } else {
            add((Comment) node);
        }
    }

    public boolean remove(Node node) {
        short nodeType = node.getNodeType();
        if (nodeType == 1) {
            return remove((Element) node);
        }
        if (nodeType == 7) {
            return remove((ProcessingInstruction) node);
        }
        if (nodeType == 8) {
            return remove((Comment) node);
        }
        invalidNodeTypeAddException(node);
        return false;
    }

    public void add(Comment comment) {
        addNode(comment);
    }

    public void add(Element element) {
        addNode(element);
    }

    public void add(ProcessingInstruction processingInstruction) {
        addNode(processingInstruction);
    }

    public boolean remove(Comment comment) {
        return removeNode(comment);
    }

    public boolean remove(Element element) {
        return removeNode(element);
    }

    public boolean remove(ProcessingInstruction processingInstruction) {
        return removeNode(processingInstruction);
    }

    public Element elementByID(String str) {
        int nodeCount = nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = node(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                String elementID = elementID(element);
                if (elementID != null && elementID.equals(str)) {
                    return element;
                }
                Element elementByID = element.elementByID(str);
                if (elementByID != null) {
                    return elementByID;
                }
            }
        }
        return null;
    }

    public void appendContent(Branch branch) {
        int nodeCount = branch.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            add((Node) branch.node(i).clone());
        }
    }

    public Node node(int i) {
        Object obj = contentList().get(i);
        if (obj instanceof Node) {
            return (Node) obj;
        }
        if (obj instanceof String) {
            return getDocumentFactory().createText(obj.toString());
        }
        return null;
    }

    public int nodeCount() {
        return contentList().size();
    }

    public int indexOf(Node node) {
        return contentList().indexOf(node);
    }

    public Iterator nodeIterator() {
        return contentList().iterator();
    }

    /* access modifiers changed from: protected */
    public String elementID(Element element) {
        return element.attributeValue("ID");
    }

    /* access modifiers changed from: protected */
    public List createContentList() {
        return new ArrayList(5);
    }

    /* access modifiers changed from: protected */
    public List createContentList(int i) {
        return new ArrayList(i);
    }

    /* access modifiers changed from: protected */
    public BackedList createResultList() {
        return new BackedList(this, contentList());
    }

    /* access modifiers changed from: protected */
    public List createSingleResultList(Object obj) {
        BackedList backedList = new BackedList(this, contentList(), 1);
        backedList.addLocal(obj);
        return backedList;
    }

    /* access modifiers changed from: protected */
    public List createEmptyList() {
        return new BackedList(this, contentList(), 0);
    }

    /* access modifiers changed from: protected */
    public void contentRemoved() {
        List contentList = contentList();
        int size = contentList.size();
        for (int i = 0; i < size; i++) {
            Object obj = contentList.get(i);
            if (obj instanceof Node) {
                childRemoved((Node) obj);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void invalidNodeTypeAddException(Node node) {
        throw new IllegalAddException("Invalid node type. Cannot add node: " + node + " to this branch: " + this);
    }
}
