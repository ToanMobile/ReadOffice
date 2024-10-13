package com.app.office.fc.openxml4j.opc;

import com.onesignal.outcomes.OSOutcomeConstants;
import java.net.URI;
import java.net.URISyntaxException;

public final class PackageRelationship {
    public static final String ID_ATTRIBUTE_NAME = "Id";
    public static final String RELATIONSHIPS_TAG_NAME = "Relationships";
    public static final String RELATIONSHIP_TAG_NAME = "Relationship";
    public static final String TARGET_ATTRIBUTE_NAME = "Target";
    public static final String TARGET_MODE_ATTRIBUTE_NAME = "TargetMode";
    public static final String TYPE_ATTRIBUTE_NAME = "Type";
    private static URI containerRelationshipPart;
    private ZipPackage container;
    private String id;
    private String relationshipType;
    private PackagePart source;
    private TargetMode targetMode;
    private URI targetUri;

    static {
        try {
            containerRelationshipPart = new URI("/_rels/.rels");
        } catch (URISyntaxException unused) {
        }
    }

    public PackageRelationship(ZipPackage zipPackage, PackagePart packagePart, URI uri, TargetMode targetMode2, String str, String str2) {
        if (zipPackage == null) {
            throw new IllegalArgumentException("pkg");
        } else if (uri == null) {
            throw new IllegalArgumentException("targetUri");
        } else if (str == null) {
            throw new IllegalArgumentException("relationshipType");
        } else if (str2 != null) {
            this.container = zipPackage;
            this.source = packagePart;
            this.targetUri = uri;
            this.targetMode = targetMode2;
            this.relationshipType = str;
            this.id = str2;
        } else {
            throw new IllegalArgumentException(OSOutcomeConstants.OUTCOME_ID);
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PackageRelationship)) {
            return false;
        }
        PackageRelationship packageRelationship = (PackageRelationship) obj;
        if (!this.id.equals(packageRelationship.id) || !this.relationshipType.equals(packageRelationship.relationshipType)) {
            return false;
        }
        PackagePart packagePart = packageRelationship.source;
        if ((packagePart == null || packagePart.equals(this.source)) && this.targetMode == packageRelationship.targetMode && this.targetUri.equals(packageRelationship.targetUri)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.id.hashCode() + this.relationshipType.hashCode();
        PackagePart packagePart = this.source;
        return hashCode + (packagePart == null ? 0 : packagePart.hashCode()) + this.targetMode.hashCode() + this.targetUri.hashCode();
    }

    public static URI getContainerPartRelationship() {
        return containerRelationshipPart;
    }

    public ZipPackage getPackage() {
        return this.container;
    }

    public String getId() {
        return this.id;
    }

    public String getRelationshipType() {
        return this.relationshipType;
    }

    public PackagePart getSource() {
        return this.source;
    }

    public URI getSourceURI() {
        PackagePart packagePart = this.source;
        if (packagePart == null) {
            return PackagingURIHelper.PACKAGE_ROOT_URI;
        }
        return packagePart._partName.getURI();
    }

    public TargetMode getTargetMode() {
        return this.targetMode;
    }

    public URI getTargetURI() {
        if (this.targetMode == TargetMode.EXTERNAL) {
            return this.targetUri;
        }
        if (!this.targetUri.toASCIIString().startsWith(PackagingURIHelper.FORWARD_SLASH_STRING)) {
            return PackagingURIHelper.resolvePartUri(getSourceURI(), this.targetUri);
        }
        return this.targetUri;
    }

    public String toString() {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        StringBuilder sb = new StringBuilder();
        if (this.id == null) {
            str = "id=null";
        } else {
            str = "id=" + this.id;
        }
        sb.append(str);
        if (this.container == null) {
            str2 = " - container=null";
        } else {
            str2 = " - container=" + this.container.toString();
        }
        sb.append(str2);
        if (this.relationshipType == null) {
            str3 = " - relationshipType=null";
        } else {
            str3 = " - relationshipType=" + this.relationshipType;
        }
        sb.append(str3);
        if (this.source == null) {
            str4 = " - source=null";
        } else {
            str4 = " - source=" + getSourceURI().toASCIIString();
        }
        sb.append(str4);
        if (this.targetUri == null) {
            str5 = " - target=null";
        } else {
            str5 = " - target=" + getTargetURI().toASCIIString();
        }
        sb.append(str5);
        if (this.targetMode == null) {
            str6 = ",targetMode=null";
        } else {
            str6 = ",targetMode=" + this.targetMode.toString();
        }
        sb.append(str6);
        return sb.toString();
    }
}
