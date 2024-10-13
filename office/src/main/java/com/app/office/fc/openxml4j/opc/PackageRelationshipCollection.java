package com.app.office.fc.openxml4j.opc;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.itextpdf.text.xml.xmp.PdfProperties;
import com.app.office.fc.dom4j.Attribute;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public final class PackageRelationshipCollection implements Iterable<PackageRelationship> {
    /* access modifiers changed from: private */
    public static POILogger logger = POILogFactory.getLogger(PackageRelationshipCollection.class);
    private ZipPackage container;
    boolean fCorePropertiesRelationship;
    private PackagePartName partName;
    private PackagePart relationshipPart;
    private TreeMap<String, PackageRelationship> relationshipsByID;
    private TreeMap<String, PackageRelationship> relationshipsByType;
    private PackagePart sourcePart;

    PackageRelationshipCollection() {
        this.relationshipsByID = new TreeMap<>();
        this.relationshipsByType = new TreeMap<>();
    }

    public PackageRelationshipCollection(PackageRelationshipCollection packageRelationshipCollection, String str) {
        this();
        for (PackageRelationship next : packageRelationshipCollection.relationshipsByID.values()) {
            if (str == null || next.getRelationshipType().equals(str)) {
                addRelationship(next);
            }
        }
    }

    public PackageRelationshipCollection(ZipPackage zipPackage) throws InvalidFormatException {
        this(zipPackage, (PackagePart) null);
    }

    public PackageRelationshipCollection(PackagePart packagePart) throws InvalidFormatException {
        this(packagePart._container, packagePart);
    }

    public PackageRelationshipCollection(ZipPackage zipPackage, PackagePart packagePart) throws InvalidFormatException {
        this();
        if (zipPackage == null) {
            throw new IllegalArgumentException("container");
        } else if (packagePart == null || !packagePart.isRelationshipPart()) {
            this.container = zipPackage;
            this.sourcePart = packagePart;
            PackagePartName relationshipPartName = getRelationshipPartName(packagePart);
            this.partName = relationshipPartName;
            if (zipPackage.containPart(relationshipPartName)) {
                PackagePart part = zipPackage.getPart(this.partName);
                this.relationshipPart = part;
                parseRelationshipsPart(part);
            }
        } else {
            throw new IllegalArgumentException(PdfProperties.PART);
        }
    }

    private static PackagePartName getRelationshipPartName(PackagePart packagePart) throws InvalidOperationException {
        PackagePartName packagePartName;
        if (packagePart == null) {
            packagePartName = PackagingURIHelper.PACKAGE_ROOT_PART_NAME;
        } else {
            packagePartName = packagePart.getPartName();
        }
        return PackagingURIHelper.getRelationshipPartName(packagePartName);
    }

    public void addRelationship(PackageRelationship packageRelationship) {
        this.relationshipsByID.put(packageRelationship.getId(), packageRelationship);
        this.relationshipsByType.put(packageRelationship.getRelationshipType(), packageRelationship);
    }

    public PackageRelationship addRelationship(URI uri, TargetMode targetMode, String str, String str2) {
        String str3;
        String sb;
        if (str2 == null) {
            int i = 0;
            do {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("rId");
                i++;
                sb2.append(i);
                sb = sb2.toString();
            } while (this.relationshipsByID.get(sb) != null);
            str3 = sb;
        } else {
            str3 = str2;
        }
        PackageRelationship packageRelationship = new PackageRelationship(this.container, this.sourcePart, uri, targetMode, str, str3);
        this.relationshipsByID.put(packageRelationship.getId(), packageRelationship);
        this.relationshipsByType.put(packageRelationship.getRelationshipType(), packageRelationship);
        return packageRelationship;
    }

    public void removeRelationship(String str) {
        PackageRelationship packageRelationship;
        TreeMap<String, PackageRelationship> treeMap = this.relationshipsByID;
        if (treeMap != null && this.relationshipsByType != null && (packageRelationship = treeMap.get(str)) != null) {
            this.relationshipsByID.remove(packageRelationship.getId());
            this.relationshipsByType.values().remove(packageRelationship);
        }
    }

    public void removeRelationship(PackageRelationship packageRelationship) {
        if (packageRelationship != null) {
            this.relationshipsByID.values().remove(packageRelationship);
            this.relationshipsByType.values().remove(packageRelationship);
            return;
        }
        throw new IllegalArgumentException("rel");
    }

    public PackageRelationship getRelationship(int i) {
        if (i < 0 || i > this.relationshipsByID.values().size()) {
            throw new IllegalArgumentException(FirebaseAnalytics.Param.INDEX);
        }
        int i2 = 0;
        for (PackageRelationship next : this.relationshipsByID.values()) {
            int i3 = i2 + 1;
            if (i == i2) {
                return next;
            }
            i2 = i3;
        }
        return null;
    }

    public PackageRelationship getRelationshipByID(String str) {
        return this.relationshipsByID.get(str);
    }

    public int size() {
        return this.relationshipsByID.values().size();
    }

    private void parseRelationshipsPart(PackagePart packagePart) throws InvalidFormatException {
        try {
            this.fCorePropertiesRelationship = false;
            SAXReader sAXReader = new SAXReader();
            POILogger pOILogger = logger;
            int i = POILogger.DEBUG;
            pOILogger.log(i, (Object) "Parsing relationship: " + packagePart.getPartName());
            InputStream inputStream = packagePart.getInputStream();
            sAXReader.addHandler("/Relationships/Relationship", new SaxHandler());
            sAXReader.read(inputStream);
            inputStream.close();
        } catch (Exception e) {
            logger.log(POILogger.ERROR, (Throwable) e);
            throw new InvalidFormatException(e.getMessage());
        }
    }

    public PackageRelationshipCollection getRelationships(String str) {
        return new PackageRelationshipCollection(this, str);
    }

    public Iterator<PackageRelationship> iterator() {
        return this.relationshipsByID.values().iterator();
    }

    public Iterator<PackageRelationship> iterator(String str) {
        ArrayList arrayList = new ArrayList();
        for (PackageRelationship next : this.relationshipsByID.values()) {
            if (next.getRelationshipType().equals(str)) {
                arrayList.add(next);
            }
        }
        return arrayList.iterator();
    }

    public void clear() {
        this.relationshipsByID.clear();
        this.relationshipsByType.clear();
    }

    public String toString() {
        String str;
        String str2;
        String str3;
        String str4;
        if (this.relationshipsByID == null) {
            str = "relationshipsByID=null";
        } else {
            str = this.relationshipsByID.size() + " relationship(s) = [";
        }
        PackagePart packagePart = this.relationshipPart;
        if (packagePart == null || packagePart._partName == null) {
            str2 = str + ",relationshipPart=null";
        } else {
            str2 = str + "," + this.relationshipPart._partName;
        }
        PackagePart packagePart2 = this.sourcePart;
        if (packagePart2 == null || packagePart2._partName == null) {
            str3 = str2 + ",sourcePart=null";
        } else {
            str3 = str2 + "," + this.sourcePart._partName;
        }
        if (this.partName != null) {
            str4 = str3 + "," + this.partName;
        } else {
            str4 = str3 + ",uri=null)";
        }
        return str4 + "]";
    }

    class SaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        SaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            String value;
            Element current = elementPath.getCurrent();
            try {
                if (current.getName().equals(PackageRelationship.RELATIONSHIP_TAG_NAME)) {
                    String value2 = current.attribute("Id").getValue();
                    String value3 = current.attribute(PackageRelationship.TYPE_ATTRIBUTE_NAME).getValue();
                    if (value3.equals(PackageRelationshipTypes.CORE_PROPERTIES)) {
                        if (!PackageRelationshipCollection.this.fCorePropertiesRelationship) {
                            PackageRelationshipCollection.this.fCorePropertiesRelationship = true;
                        } else {
                            throw new InvalidFormatException("OPC Compliance error [M4.1]: there is more than one core properties relationship in the package !");
                        }
                    }
                    Attribute attribute = current.attribute(PackageRelationship.TARGET_MODE_ATTRIBUTE_NAME);
                    TargetMode targetMode = TargetMode.INTERNAL;
                    if (attribute != null) {
                        targetMode = attribute.getValue().toLowerCase().equals("internal") ? TargetMode.INTERNAL : TargetMode.EXTERNAL;
                    }
                    try {
                        value = current.attribute("Target").getValue();
                        PackageRelationshipCollection.this.addRelationship(PackagingURIHelper.toURI(value), targetMode, value3, value2);
                    } catch (URISyntaxException e) {
                        POILogger access$000 = PackageRelationshipCollection.logger;
                        int i = POILogger.ERROR;
                        access$000.log(i, (Object) "Cannot convert " + value + " in a valid relationship URI-> ignored", (Throwable) e);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            current.detach();
        }
    }
}
