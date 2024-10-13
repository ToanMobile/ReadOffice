package com.app.office.fc.openxml4j.opc.internal;

import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.DocumentException;
import com.app.office.fc.dom4j.DocumentHelper;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import com.app.office.fc.openxml4j.exceptions.OpenXML4JRuntimeException;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackagePartName;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ContentTypeManager {
    public static final String CONTENT_TYPES_PART_NAME = "[Content_Types].xml";
    private static final String CONTENT_TYPE_ATTRIBUTE_NAME = "ContentType";
    private static final String DEFAULT_TAG_NAME = "Default";
    private static final String EXTENSION_ATTRIBUTE_NAME = "Extension";
    private static final String OVERRIDE_TAG_NAME = "Override";
    private static final String PART_NAME_ATTRIBUTE_NAME = "PartName";
    public static final String TYPES_NAMESPACE_URI = "http://schemas.openxmlformats.org/package/2006/content-types";
    private static final String TYPES_TAG_NAME = "Types";
    protected ZipPackage container;
    private TreeMap<String, String> defaultContentType = new TreeMap<>();
    private TreeMap<PackagePartName, String> overrideContentType;

    public boolean saveImpl(Document document, OutputStream outputStream) {
        return true;
    }

    public ContentTypeManager(InputStream inputStream, ZipPackage zipPackage) throws InvalidFormatException {
        this.container = zipPackage;
        if (inputStream != null) {
            try {
                parseContentTypesFile(inputStream);
            } catch (InvalidFormatException unused) {
                throw new InvalidFormatException("Can't read content types part !");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001c, code lost:
        if (r1 == false) goto L_0x0026;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addContentType(com.app.office.fc.openxml4j.opc.PackagePartName r3, java.lang.String r4) {
        /*
            r2 = this;
            java.lang.String r0 = r3.getExtension()
            java.lang.String r0 = r0.toLowerCase()
            int r1 = r0.length()
            if (r1 == 0) goto L_0x0026
            java.util.TreeMap<java.lang.String, java.lang.String> r1 = r2.defaultContentType
            boolean r1 = r1.containsKey(r0)
            if (r1 == 0) goto L_0x001f
            java.util.TreeMap<java.lang.String, java.lang.String> r1 = r2.defaultContentType
            boolean r1 = r1.containsValue(r4)
            if (r1 != 0) goto L_0x0020
            goto L_0x0026
        L_0x001f:
            r1 = 0
        L_0x0020:
            if (r1 != 0) goto L_0x0029
            r2.addDefaultContentType(r0, r4)
            goto L_0x0029
        L_0x0026:
            r2.addOverrideContentType(r3, r4)
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.openxml4j.opc.internal.ContentTypeManager.addContentType(com.app.office.fc.openxml4j.opc.PackagePartName, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public void addOverrideContentType(PackagePartName packagePartName, String str) {
        if (this.overrideContentType == null) {
            this.overrideContentType = new TreeMap<>();
        }
        this.overrideContentType.put(packagePartName, str);
    }

    /* access modifiers changed from: private */
    public void addDefaultContentType(String str, String str2) {
        this.defaultContentType.put(str.toLowerCase(), str2);
    }

    public void removeContentType(PackagePartName packagePartName) throws InvalidOperationException {
        if (packagePartName != null) {
            TreeMap<PackagePartName, String> treeMap = this.overrideContentType;
            if (treeMap == null || treeMap.get(packagePartName) == null) {
                String extension = packagePartName.getExtension();
                boolean z = true;
                ZipPackage zipPackage = this.container;
                if (zipPackage != null) {
                    try {
                        Iterator<PackagePart> it = zipPackage.getParts().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            PackagePart next = it.next();
                            if (!next.getPartName().equals(packagePartName) && next.getPartName().getExtension().equalsIgnoreCase(extension)) {
                                z = false;
                                break;
                            }
                        }
                    } catch (InvalidFormatException e) {
                        throw new InvalidOperationException(e.getMessage());
                    }
                }
                if (z) {
                    this.defaultContentType.remove(extension);
                }
                ZipPackage zipPackage2 = this.container;
                if (zipPackage2 != null) {
                    try {
                        Iterator<PackagePart> it2 = zipPackage2.getParts().iterator();
                        while (it2.hasNext()) {
                            PackagePart next2 = it2.next();
                            if (!next2.getPartName().equals(packagePartName)) {
                                if (getContentType(next2.getPartName()) == null) {
                                    throw new InvalidOperationException("Rule M2.4 is not respected: Nor a default element or override element is associated with the part: " + next2.getPartName().getName());
                                }
                            }
                        }
                    } catch (InvalidFormatException e2) {
                        throw new InvalidOperationException(e2.getMessage());
                    }
                }
            } else {
                this.overrideContentType.remove(packagePartName);
            }
        } else {
            throw new IllegalArgumentException("partName");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000e, code lost:
        r0 = r1.overrideContentType;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isContentTypeRegister(java.lang.String r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L_0x0021
            java.util.TreeMap<java.lang.String, java.lang.String> r0 = r1.defaultContentType
            java.util.Collection r0 = r0.values()
            boolean r0 = r0.contains(r2)
            if (r0 != 0) goto L_0x001f
            java.util.TreeMap<com.app.office.fc.openxml4j.opc.PackagePartName, java.lang.String> r0 = r1.overrideContentType
            if (r0 == 0) goto L_0x001d
            java.util.Collection r0 = r0.values()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r2 = 0
            goto L_0x0020
        L_0x001f:
            r2 = 1
        L_0x0020:
            return r2
        L_0x0021:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "contentType"
            r2.<init>(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.openxml4j.opc.internal.ContentTypeManager.isContentTypeRegister(java.lang.String):boolean");
    }

    public String getContentType(PackagePartName packagePartName) {
        if (packagePartName != null) {
            TreeMap<PackagePartName, String> treeMap = this.overrideContentType;
            if (treeMap != null && treeMap.containsKey(packagePartName)) {
                return this.overrideContentType.get(packagePartName);
            }
            String lowerCase = packagePartName.getExtension().toLowerCase();
            if (this.defaultContentType.containsKey(lowerCase)) {
                return this.defaultContentType.get(lowerCase);
            }
            ZipPackage zipPackage = this.container;
            if (zipPackage == null || zipPackage.getPart(packagePartName) == null) {
                return null;
            }
            throw new OpenXML4JRuntimeException("Rule M2.4 exception : this error should NEVER happen, if so please send a mail to the developers team, thanks !");
        }
        throw new IllegalArgumentException("partName");
    }

    public void clearAll() {
        this.defaultContentType.clear();
        TreeMap<PackagePartName, String> treeMap = this.overrideContentType;
        if (treeMap != null) {
            treeMap.clear();
        }
    }

    public void clearOverrideContentTypes() {
        TreeMap<PackagePartName, String> treeMap = this.overrideContentType;
        if (treeMap != null) {
            treeMap.clear();
        }
    }

    private void parseContentTypesFile(InputStream inputStream) throws InvalidFormatException {
        try {
            SAXReader sAXReader = new SAXReader();
            XLSXSaxHandler xLSXSaxHandler = new XLSXSaxHandler();
            sAXReader.addHandler("/Types/Default", xLSXSaxHandler);
            sAXReader.addHandler("/Types/Override", xLSXSaxHandler);
            sAXReader.read(inputStream);
        } catch (DocumentException e) {
            throw new InvalidFormatException(e.getMessage());
        }
    }

    public boolean save(OutputStream outputStream) {
        Document createDocument = DocumentHelper.createDocument();
        Element addElement = createDocument.addElement(new QName(TYPES_TAG_NAME, Namespace.get("", "http://schemas.openxmlformats.org/package/2006/content-types")));
        for (Map.Entry<String, String> appendDefaultType : this.defaultContentType.entrySet()) {
            appendDefaultType(addElement, appendDefaultType);
        }
        TreeMap<PackagePartName, String> treeMap = this.overrideContentType;
        if (treeMap != null) {
            for (Map.Entry<PackagePartName, String> appendSpecificTypes : treeMap.entrySet()) {
                appendSpecificTypes(addElement, appendSpecificTypes);
            }
        }
        createDocument.normalize();
        return saveImpl(createDocument, outputStream);
    }

    private void appendSpecificTypes(Element element, Map.Entry<PackagePartName, String> entry) {
        element.addElement(OVERRIDE_TAG_NAME).addAttribute(PART_NAME_ATTRIBUTE_NAME, entry.getKey().getName()).addAttribute(CONTENT_TYPE_ATTRIBUTE_NAME, entry.getValue());
    }

    private void appendDefaultType(Element element, Map.Entry<String, String> entry) {
        element.addElement(DEFAULT_TAG_NAME).addAttribute(EXTENSION_ATTRIBUTE_NAME, entry.getKey()).addAttribute(CONTENT_TYPE_ATTRIBUTE_NAME, entry.getValue());
    }

    class XLSXSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        XLSXSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            Element current = elementPath.getCurrent();
            String name = current.getName();
            if (name.equals(ContentTypeManager.DEFAULT_TAG_NAME)) {
                ContentTypeManager.this.addDefaultContentType(current.attribute(ContentTypeManager.EXTENSION_ATTRIBUTE_NAME).getValue(), current.attribute(ContentTypeManager.CONTENT_TYPE_ATTRIBUTE_NAME).getValue());
            } else if (name.equals(ContentTypeManager.OVERRIDE_TAG_NAME)) {
                try {
                    ContentTypeManager.this.addOverrideContentType(PackagingURIHelper.createPartName(new URI(current.attribute(ContentTypeManager.PART_NAME_ATTRIBUTE_NAME).getValue())), current.attribute(ContentTypeManager.CONTENT_TYPE_ATTRIBUTE_NAME).getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            current.detach();
        }
    }
}
