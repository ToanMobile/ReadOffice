package com.app.office.fc.dom4j.dom;

import com.karumi.dexter.BuildConfig;
import com.app.office.fc.dom4j.Attribute;
import com.app.office.fc.dom4j.CDATA;
import com.app.office.fc.dom4j.Comment;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.DocumentFactory;
import com.app.office.fc.dom4j.DocumentType;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Entity;
import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.ProcessingInstruction;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.Text;
import com.app.office.fc.dom4j.util.SingletonStrategy;
import java.util.Map;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;

public class DOMDocumentFactory extends DocumentFactory implements DOMImplementation {
    private static SingletonStrategy singleton;

    public Object getFeature(String str, String str2) {
        return null;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:3|4) */
    /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
        r1 = java.lang.Class.forName("org.dom4j.util.SimpleSingleton");
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0012 */
    static {
        /*
            java.lang.String r0 = "org.dom4j.util.SimpleSingleton"
            r1 = 0
            java.lang.String r2 = "org.dom4j.dom.DOMDocumentFactory.singleton.strategy"
            java.lang.String r2 = java.lang.System.getProperty(r2, r0)     // Catch:{ Exception -> 0x000e }
            java.lang.Class r1 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x000e }
            goto L_0x0012
        L_0x000e:
            java.lang.Class r1 = java.lang.Class.forName(r0)     // Catch:{ Exception -> 0x0012 }
        L_0x0012:
            java.lang.Object r0 = r1.newInstance()     // Catch:{ Exception -> 0x0023 }
            com.app.office.fc.dom4j.util.SingletonStrategy r0 = (com.app.office.fc.dom4j.util.SingletonStrategy) r0     // Catch:{ Exception -> 0x0023 }
            singleton = r0     // Catch:{ Exception -> 0x0023 }
            java.lang.Class<com.app.office.fc.dom4j.dom.DOMDocumentFactory> r1 = com.app.office.fc.dom4j.dom.DOMDocumentFactory.class
            java.lang.String r1 = r1.getName()     // Catch:{ Exception -> 0x0023 }
            r0.setSingletonClassName(r1)     // Catch:{ Exception -> 0x0023 }
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.dom4j.dom.DOMDocumentFactory.<clinit>():void");
    }

    public static DocumentFactory getInstance() {
        return (DOMDocumentFactory) singleton.instance();
    }

    public Document createDocument() {
        DOMDocument dOMDocument = new DOMDocument();
        dOMDocument.setDocumentFactory(this);
        return dOMDocument;
    }

    public DocumentType createDocType(String str, String str2, String str3) {
        return new DOMDocumentType(str, str2, str3);
    }

    public Element createElement(QName qName) {
        return new DOMElement(qName);
    }

    public Element createElement(QName qName, int i) {
        return new DOMElement(qName, i);
    }

    public Attribute createAttribute(Element element, QName qName, String str) {
        return new DOMAttribute(qName, str);
    }

    public CDATA createCDATA(String str) {
        return new DOMCDATA(str);
    }

    public Comment createComment(String str) {
        return new DOMComment(str);
    }

    public Text createText(String str) {
        return new DOMText(str);
    }

    public Entity createEntity(String str) {
        return new DOMEntityReference(str);
    }

    public Entity createEntity(String str, String str2) {
        return new DOMEntityReference(str, str2);
    }

    public Namespace createNamespace(String str, String str2) {
        return new DOMNamespace(str, str2);
    }

    public ProcessingInstruction createProcessingInstruction(String str, String str2) {
        return new DOMProcessingInstruction(str, str2);
    }

    public ProcessingInstruction createProcessingInstruction(String str, Map map) {
        return new DOMProcessingInstruction(str, map);
    }

    public boolean hasFeature(String str, String str2) {
        if (!"XML".equalsIgnoreCase(str) && !"Core".equalsIgnoreCase(str)) {
            return false;
        }
        if (str2 == null || str2.length() == 0 || BuildConfig.VERSION_NAME.equals(str2) || "2.0".equals(str2)) {
            return true;
        }
        return false;
    }

    public org.w3c.dom.DocumentType createDocumentType(String str, String str2, String str3) throws DOMException {
        return new DOMDocumentType(str, str2, str3);
    }

    public org.w3c.dom.Document createDocument(String str, String str2, org.w3c.dom.DocumentType documentType) throws DOMException {
        DOMDocument dOMDocument;
        if (documentType != null) {
            dOMDocument = new DOMDocument(asDocumentType(documentType));
        } else {
            dOMDocument = new DOMDocument();
        }
        dOMDocument.addElement(createQName(str2, str));
        return dOMDocument;
    }

    /* access modifiers changed from: protected */
    public DOMDocumentType asDocumentType(org.w3c.dom.DocumentType documentType) {
        if (documentType instanceof DOMDocumentType) {
            return (DOMDocumentType) documentType;
        }
        return new DOMDocumentType(documentType.getName(), documentType.getPublicId(), documentType.getSystemId());
    }
}
