package com.app.office.fc.dom4j;

import com.app.office.fc.dom4j.tree.QNameCache;
import com.app.office.fc.dom4j.util.SingletonStrategy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class QName implements Serializable {
    private static SingletonStrategy singleton;
    private DocumentFactory documentFactory;
    private int hashCode;
    private String name;
    private transient Namespace namespace;
    private String qualifiedName;

    /* JADX WARNING: Can't wrap try/catch for region: R(2:3|4) */
    /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
        r1 = java.lang.Class.forName("com.wxiwei.fc.dom4j.util.SimpleSingleton");
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0012 */
    static {
        /*
            java.lang.String r0 = "com.wxiwei.fc.dom4j.util.SimpleSingleton"
            r1 = 0
            java.lang.String r2 = "com.wxiwei.fc.dom4j.QName.singleton.strategy"
            java.lang.String r2 = java.lang.System.getProperty(r2, r0)     // Catch:{ Exception -> 0x000e }
            java.lang.Class r1 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x000e }
            goto L_0x0012
        L_0x000e:
            java.lang.Class r1 = java.lang.Class.forName(r0)     // Catch:{ Exception -> 0x0012 }
        L_0x0012:
            java.lang.Object r0 = r1.newInstance()     // Catch:{ Exception -> 0x0023 }
            com.app.office.fc.dom4j.util.SingletonStrategy r0 = (com.app.office.fc.dom4j.util.SingletonStrategy) r0     // Catch:{ Exception -> 0x0023 }
            singleton = r0     // Catch:{ Exception -> 0x0023 }
            java.lang.Class<com.app.office.fc.dom4j.tree.QNameCache> r1 = com.app.office.fc.dom4j.tree.QNameCache.class
            java.lang.String r1 = r1.getName()     // Catch:{ Exception -> 0x0023 }
            r0.setSingletonClassName(r1)     // Catch:{ Exception -> 0x0023 }
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.dom4j.QName.<clinit>():void");
    }

    public QName(String str) {
        this(str, Namespace.NO_NAMESPACE);
    }

    public QName(String str, Namespace namespace2) {
        this.name = str == null ? "" : str;
        this.namespace = namespace2 == null ? Namespace.NO_NAMESPACE : namespace2;
    }

    public QName(String str, Namespace namespace2, String str2) {
        this.name = str == null ? "" : str;
        this.qualifiedName = str2;
        this.namespace = namespace2 == null ? Namespace.NO_NAMESPACE : namespace2;
    }

    public static QName get(String str) {
        return getCache().get(str);
    }

    public static QName get(String str, Namespace namespace2) {
        return getCache().get(str, namespace2);
    }

    public static QName get(String str, String str2, String str3) {
        if ((str2 == null || str2.length() == 0) && str3 == null) {
            return get(str);
        }
        if (str2 == null || str2.length() == 0) {
            return getCache().get(str, Namespace.get(str3));
        }
        if (str3 == null) {
            return get(str);
        }
        return getCache().get(str, Namespace.get(str2, str3));
    }

    public static QName get(String str, String str2) {
        if (str2 == null) {
            return getCache().get(str);
        }
        return getCache().get(str, str2);
    }

    public static QName get(String str, Namespace namespace2, String str2) {
        return getCache().get(str, namespace2, str2);
    }

    public String getName() {
        return this.name;
    }

    public String getQualifiedName() {
        if (this.qualifiedName == null) {
            String namespacePrefix = getNamespacePrefix();
            if (namespacePrefix == null || namespacePrefix.length() <= 0) {
                this.qualifiedName = this.name;
            } else {
                this.qualifiedName = namespacePrefix + ":" + this.name;
            }
        }
        return this.qualifiedName;
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public String getNamespacePrefix() {
        Namespace namespace2 = this.namespace;
        if (namespace2 == null) {
            return "";
        }
        return namespace2.getPrefix();
    }

    public String getNamespaceURI() {
        Namespace namespace2 = this.namespace;
        if (namespace2 == null) {
            return "";
        }
        return namespace2.getURI();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int hashCode2 = getName().hashCode() ^ getNamespaceURI().hashCode();
            this.hashCode = hashCode2;
            if (hashCode2 == 0) {
                this.hashCode = 47806;
            }
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof QName) {
            QName qName = (QName) obj;
            if (hashCode() != qName.hashCode() || !getName().equals(qName.getName()) || !getNamespaceURI().equals(qName.getNamespaceURI())) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String toString() {
        return super.toString() + " [name: " + getName() + " namespace: \"" + getNamespace() + "\"]";
    }

    public DocumentFactory getDocumentFactory() {
        return this.documentFactory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory2) {
        this.documentFactory = documentFactory2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.namespace.getPrefix());
        objectOutputStream.writeObject(this.namespace.getURI());
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.namespace = Namespace.get((String) objectInputStream.readObject(), (String) objectInputStream.readObject());
    }

    private static QNameCache getCache() {
        return (QNameCache) singleton.instance();
    }
}
