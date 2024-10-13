package com.app.office.fc.dom4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Element extends Branch {
    void add(Attribute attribute);

    void add(CDATA cdata);

    void add(Entity entity);

    void add(Namespace namespace);

    void add(Text text);

    Element addAttribute(QName qName, String str);

    Element addAttribute(String str, String str2);

    Element addCDATA(String str);

    Element addComment(String str);

    Element addEntity(String str, String str2);

    Element addNamespace(String str, String str2);

    Element addProcessingInstruction(String str, String str2);

    Element addProcessingInstruction(String str, Map map);

    Element addText(String str);

    List additionalNamespaces();

    void appendAttributes(Element element);

    Attribute attribute(int i);

    Attribute attribute(QName qName);

    Attribute attribute(String str);

    int attributeCount();

    Iterator attributeIterator();

    String attributeValue(QName qName);

    String attributeValue(QName qName, String str);

    String attributeValue(String str);

    String attributeValue(String str, String str2);

    List attributes();

    Element createCopy();

    Element createCopy(QName qName);

    Element createCopy(String str);

    List declaredNamespaces();

    Element element(QName qName);

    Element element(String str);

    Iterator elementIterator();

    Iterator elementIterator(QName qName);

    Iterator elementIterator(String str);

    String elementText(QName qName);

    String elementText(String str);

    String elementTextTrim(QName qName);

    String elementTextTrim(String str);

    List elements();

    List elements(QName qName);

    List elements(String str);

    Object getData();

    Namespace getNamespace();

    Namespace getNamespaceForPrefix(String str);

    Namespace getNamespaceForURI(String str);

    String getNamespacePrefix();

    String getNamespaceURI();

    List getNamespacesForURI(String str);

    QName getQName();

    QName getQName(String str);

    String getQualifiedName();

    String getStringValue();

    String getText();

    String getTextTrim();

    Node getXPathResult(int i);

    boolean hasMixedContent();

    boolean isRootElement();

    boolean isTextOnly();

    boolean remove(Attribute attribute);

    boolean remove(CDATA cdata);

    boolean remove(Entity entity);

    boolean remove(Namespace namespace);

    boolean remove(Text text);

    void setAttributeValue(QName qName, String str);

    void setAttributeValue(String str, String str2);

    void setAttributes(List list);

    void setData(Object obj);

    void setQName(QName qName);
}
