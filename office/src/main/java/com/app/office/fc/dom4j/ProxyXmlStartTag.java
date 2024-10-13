package com.app.office.fc.dom4j;

import com.itextpdf.text.pdf.security.SecurityConstants;
import java.util.Iterator;

public class ProxyXmlStartTag {
    private Element element;
    private DocumentFactory factory = DocumentFactory.getInstance();

    public ProxyXmlStartTag() {
    }

    public ProxyXmlStartTag(Element element2) {
        this.element = element2;
    }

    public void resetStartTag() {
        this.element = null;
    }

    public int getAttributeCount() {
        Element element2 = this.element;
        if (element2 != null) {
            return element2.attributeCount();
        }
        return 0;
    }

    public String getAttributeNamespaceUri(int i) {
        Attribute attribute;
        Element element2 = this.element;
        if (element2 == null || (attribute = element2.attribute(i)) == null) {
            return null;
        }
        return attribute.getNamespaceURI();
    }

    public String getAttributeLocalName(int i) {
        Attribute attribute;
        Element element2 = this.element;
        if (element2 == null || (attribute = element2.attribute(i)) == null) {
            return null;
        }
        return attribute.getName();
    }

    public String getAttributePrefix(int i) {
        Attribute attribute;
        String namespacePrefix;
        Element element2 = this.element;
        if (element2 == null || (attribute = element2.attribute(i)) == null || (namespacePrefix = attribute.getNamespacePrefix()) == null || namespacePrefix.length() <= 0) {
            return null;
        }
        return namespacePrefix;
    }

    public String getAttributeRawName(int i) {
        Attribute attribute;
        Element element2 = this.element;
        if (element2 == null || (attribute = element2.attribute(i)) == null) {
            return null;
        }
        return attribute.getQualifiedName();
    }

    public String getAttributeValue(int i) {
        Attribute attribute;
        Element element2 = this.element;
        if (element2 == null || (attribute = element2.attribute(i)) == null) {
            return null;
        }
        return attribute.getValue();
    }

    public String getAttributeValueFromRawName(String str) {
        Element element2 = this.element;
        if (element2 == null) {
            return null;
        }
        Iterator attributeIterator = element2.attributeIterator();
        while (attributeIterator.hasNext()) {
            Attribute attribute = (Attribute) attributeIterator.next();
            if (str.equals(attribute.getQualifiedName())) {
                return attribute.getValue();
            }
        }
        return null;
    }

    public String getAttributeValueFromName(String str, String str2) {
        Element element2 = this.element;
        if (element2 == null) {
            return null;
        }
        Iterator attributeIterator = element2.attributeIterator();
        while (attributeIterator.hasNext()) {
            Attribute attribute = (Attribute) attributeIterator.next();
            if (str.equals(attribute.getNamespaceURI()) && str2.equals(attribute.getName())) {
                return attribute.getValue();
            }
        }
        return null;
    }

    public boolean isAttributeNamespaceDeclaration(int i) {
        Attribute attribute;
        Element element2 = this.element;
        if (element2 == null || (attribute = element2.attribute(i)) == null) {
            return false;
        }
        return SecurityConstants.XMLNS.equals(attribute.getNamespacePrefix());
    }

    public String getLocalName() {
        return this.element.getName();
    }

    public String getNamespaceUri() {
        return this.element.getNamespaceURI();
    }

    public String getPrefix() {
        return this.element.getNamespacePrefix();
    }

    public String getRawName() {
        return this.element.getQualifiedName();
    }

    public void modifyTag(String str, String str2, String str3) {
        this.element = this.factory.createElement(str3, str);
    }

    public void resetTag() {
        this.element = null;
    }

    public DocumentFactory getDocumentFactory() {
        return this.factory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.factory = documentFactory;
    }

    public Element getElement() {
        return this.element;
    }
}
