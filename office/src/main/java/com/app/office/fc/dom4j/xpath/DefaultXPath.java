package com.app.office.fc.dom4j.xpath;

import com.app.office.fc.dom4j.InvalidXPathException;
import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.NodeFilter;
import com.app.office.fc.dom4j.XPath;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;

public class DefaultXPath implements XPath, NodeFilter, Serializable {
    private NamespaceContext namespaceContext;
    private String text;
    private XPath xpath;

    protected static XPath parse(String str) {
        return null;
    }

    public boolean booleanValueOf(Object obj) {
        return false;
    }

    public Object evaluate(Object obj) {
        return null;
    }

    public boolean matches(Node node) {
        return false;
    }

    public Number numberValueOf(Object obj) {
        return null;
    }

    public Node selectSingleNode(Object obj) {
        return null;
    }

    public void setNamespaceContext1(NamespaceContext namespaceContext2) {
    }

    public void setNamespaceURIs(Map map) {
    }

    public String valueOf(Object obj) {
        return "";
    }

    public DefaultXPath(String str) throws InvalidXPathException {
        this.text = str;
        this.xpath = parse(str);
    }

    public String toString() {
        return "[XPath: " + this.xpath + "]";
    }

    public String getText() {
        return this.text;
    }

    public NamespaceContext getNamespaceContext() {
        return this.namespaceContext;
    }

    public void setNamespaceContext(NamespaceContext namespaceContext2) {
        this.namespaceContext = namespaceContext2;
        this.xpath.setNamespaceContext(namespaceContext2);
    }

    public Object selectObject(Object obj) {
        return evaluate(obj);
    }

    public List selectNodes(Object obj) {
        return Collections.EMPTY_LIST;
    }

    public List selectNodes(Object obj, XPath xPath) {
        List selectNodes = selectNodes(obj);
        xPath.sort(selectNodes);
        return selectNodes;
    }

    public List selectNodes(Object obj, XPath xPath, boolean z) {
        List selectNodes = selectNodes(obj);
        xPath.sort(selectNodes, z);
        return selectNodes;
    }

    public void sort(List list) {
        sort(list, false);
    }

    public void sort(List list, boolean z) {
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            HashMap hashMap = new HashMap(size);
            for (int i = 0; i < size; i++) {
                Object obj = list.get(i);
                if (obj instanceof Node) {
                    Node node = (Node) obj;
                    hashMap.put(node, getCompareValue(node));
                }
            }
            sort(list, (Map) hashMap);
            if (z) {
                removeDuplicates(list, hashMap);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sort(List list, final Map map) {
        Collections.sort(list, new Comparator() {
            public int compare(Object obj, Object obj2) {
                Object obj3 = map.get(obj);
                Object obj4 = map.get(obj2);
                if (obj3 == obj4) {
                    return 0;
                }
                if (obj3 instanceof Comparable) {
                    return ((Comparable) obj3).compareTo(obj4);
                }
                if (obj3 == null) {
                    return 1;
                }
                if (obj4 != null && obj3.equals(obj4)) {
                    return 0;
                }
                return -1;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void removeDuplicates(List list, Map map) {
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object obj = map.get(it.next());
            if (hashSet.contains(obj)) {
                it.remove();
            } else {
                hashSet.add(obj);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object getCompareValue(Node node) {
        return valueOf(node);
    }

    /* access modifiers changed from: protected */
    public void setNSContext(Object obj) {
        if (this.namespaceContext == null) {
            this.xpath.setNamespaceContext(DefaultNamespaceContext.create(obj));
        }
    }
}
