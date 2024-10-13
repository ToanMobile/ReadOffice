package com.app.office.fc.dom4j.xpath;

import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.Node;
import java.io.Serializable;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class DefaultNamespaceContext implements NamespaceContext, Serializable {
    private final Element element;

    public String getNamespaceURI(String str) {
        return null;
    }

    public String getPrefix(String str) {
        return null;
    }

    public Iterator getPrefixes(String str) {
        return null;
    }

    public DefaultNamespaceContext(Element element2) {
        this.element = element2;
    }

    public static DefaultNamespaceContext create(Object obj) {
        Element element2;
        if (obj instanceof Element) {
            element2 = (Element) obj;
        } else if (obj instanceof Document) {
            element2 = ((Document) obj).getRootElement();
        } else {
            element2 = obj instanceof Node ? ((Node) obj).getParent() : null;
        }
        if (element2 != null) {
            return new DefaultNamespaceContext(element2);
        }
        return null;
    }

    public String translateNamespacePrefixToUri(String str) {
        Namespace namespaceForPrefix;
        if (str == null || str.length() <= 0 || (namespaceForPrefix = this.element.getNamespaceForPrefix(str)) == null) {
            return null;
        }
        return namespaceForPrefix.getURI();
    }
}
