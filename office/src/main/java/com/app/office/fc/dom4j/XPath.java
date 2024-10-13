package com.app.office.fc.dom4j;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;

public interface XPath extends NodeFilter {
    boolean booleanValueOf(Object obj);

    Object evaluate(Object obj);

    NamespaceContext getNamespaceContext();

    String getText();

    boolean matches(Node node);

    Number numberValueOf(Object obj);

    List selectNodes(Object obj);

    List selectNodes(Object obj, XPath xPath);

    List selectNodes(Object obj, XPath xPath, boolean z);

    Object selectObject(Object obj);

    Node selectSingleNode(Object obj);

    void setNamespaceContext(NamespaceContext namespaceContext);

    void setNamespaceURIs(Map map);

    void sort(List list);

    void sort(List list, boolean z);

    String valueOf(Object obj);
}
