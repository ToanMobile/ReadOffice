package com.app.office.fc.dom4j.util;

import com.app.office.fc.dom4j.Attribute;
import com.app.office.fc.dom4j.CDATA;
import com.app.office.fc.dom4j.Comment;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.DocumentFactory;
import com.app.office.fc.dom4j.DocumentType;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Entity;
import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.NodeFilter;
import com.app.office.fc.dom4j.ProcessingInstruction;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.Text;
import com.app.office.fc.dom4j.XPath;
import com.app.office.fc.dom4j.rule.Pattern;
import java.util.Map;

public abstract class ProxyDocumentFactory {
    private DocumentFactory proxy;

    public ProxyDocumentFactory() {
        this.proxy = DocumentFactory.getInstance();
    }

    public ProxyDocumentFactory(DocumentFactory documentFactory) {
        this.proxy = documentFactory;
    }

    public Document createDocument() {
        return this.proxy.createDocument();
    }

    public Document createDocument(Element element) {
        return this.proxy.createDocument(element);
    }

    public DocumentType createDocType(String str, String str2, String str3) {
        return this.proxy.createDocType(str, str2, str3);
    }

    public Element createElement(QName qName) {
        return this.proxy.createElement(qName);
    }

    public Element createElement(String str) {
        return this.proxy.createElement(str);
    }

    public Attribute createAttribute(Element element, QName qName, String str) {
        return this.proxy.createAttribute(element, qName, str);
    }

    public Attribute createAttribute(Element element, String str, String str2) {
        return this.proxy.createAttribute(element, str, str2);
    }

    public CDATA createCDATA(String str) {
        return this.proxy.createCDATA(str);
    }

    public Comment createComment(String str) {
        return this.proxy.createComment(str);
    }

    public Text createText(String str) {
        return this.proxy.createText(str);
    }

    public Entity createEntity(String str, String str2) {
        return this.proxy.createEntity(str, str2);
    }

    public Namespace createNamespace(String str, String str2) {
        return this.proxy.createNamespace(str, str2);
    }

    public ProcessingInstruction createProcessingInstruction(String str, String str2) {
        return this.proxy.createProcessingInstruction(str, str2);
    }

    public ProcessingInstruction createProcessingInstruction(String str, Map map) {
        return this.proxy.createProcessingInstruction(str, map);
    }

    public QName createQName(String str, Namespace namespace) {
        return this.proxy.createQName(str, namespace);
    }

    public QName createQName(String str) {
        return this.proxy.createQName(str);
    }

    public QName createQName(String str, String str2, String str3) {
        return this.proxy.createQName(str, str2, str3);
    }

    public QName createQName(String str, String str2) {
        return this.proxy.createQName(str, str2);
    }

    public XPath createXPath(String str) {
        return this.proxy.createXPath(str);
    }

    public NodeFilter createXPathFilter(String str) {
        return this.proxy.createXPathFilter(str);
    }

    public Pattern createPattern(String str) {
        return this.proxy.createPattern(str);
    }

    /* access modifiers changed from: protected */
    public DocumentFactory getProxy() {
        return this.proxy;
    }

    /* access modifiers changed from: protected */
    public void setProxy(DocumentFactory documentFactory) {
        if (documentFactory == null) {
            documentFactory = DocumentFactory.getInstance();
        }
        this.proxy = documentFactory;
    }
}
