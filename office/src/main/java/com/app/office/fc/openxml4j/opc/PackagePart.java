package com.app.office.fc.openxml4j.opc;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import com.app.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.app.office.fc.openxml4j.opc.internal.ContentType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

public abstract class PackagePart implements RelationshipSource {
    protected ZipPackage _container;
    protected ContentType _contentType;
    private boolean _isDeleted;
    private boolean _isRelationshipPart;
    protected PackagePartName _partName;
    private PackageRelationshipCollection _relationships;

    public abstract void close();

    public abstract void flush();

    /* access modifiers changed from: protected */
    public abstract InputStream getInputStreamImpl() throws IOException;

    /* access modifiers changed from: protected */
    public abstract OutputStream getOutputStreamImpl();

    public abstract boolean load(InputStream inputStream) throws InvalidFormatException;

    public abstract boolean save(OutputStream outputStream) throws OpenXML4JException;

    protected PackagePart(ZipPackage zipPackage, PackagePartName packagePartName, ContentType contentType) throws InvalidFormatException {
        this(zipPackage, packagePartName, contentType, true);
    }

    protected PackagePart(ZipPackage zipPackage, PackagePartName packagePartName, ContentType contentType, boolean z) throws InvalidFormatException {
        this._partName = packagePartName;
        this._contentType = contentType;
        this._container = zipPackage;
        this._isRelationshipPart = packagePartName.isRelationshipPartURI();
        if (z) {
            loadRelationships();
        }
    }

    public PackagePart(ZipPackage zipPackage, PackagePartName packagePartName, String str) throws InvalidFormatException {
        this(zipPackage, packagePartName, new ContentType(str));
    }

    public PackageRelationship addExternalRelationship(String str, String str2) {
        return addExternalRelationship(str, str2, (String) null);
    }

    public PackageRelationship addExternalRelationship(String str, String str2, String str3) {
        if (str == null) {
            throw new IllegalArgumentException(TypedValues.AttributesType.S_TARGET);
        } else if (str2 != null) {
            if (this._relationships == null) {
                this._relationships = new PackageRelationshipCollection();
            }
            try {
                return this._relationships.addRelationship(new URI(str), TargetMode.EXTERNAL, str2, str3);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid target - " + e);
            }
        } else {
            throw new IllegalArgumentException("relationshipType");
        }
    }

    public PackageRelationship addRelationship(PackagePartName packagePartName, TargetMode targetMode, String str) {
        return addRelationship(packagePartName, targetMode, str, (String) null);
    }

    public PackageRelationship addRelationship(PackagePartName packagePartName, TargetMode targetMode, String str, String str2) {
        if (packagePartName == null) {
            throw new IllegalArgumentException("targetPartName");
        } else if (targetMode == null) {
            throw new IllegalArgumentException("targetMode");
        } else if (str == null) {
            throw new IllegalArgumentException("relationshipType");
        } else if (this._isRelationshipPart || packagePartName.isRelationshipPartURI()) {
            throw new InvalidOperationException("Rule M1.25: The Relationships part shall not have relationships to any other part.");
        } else {
            if (this._relationships == null) {
                this._relationships = new PackageRelationshipCollection();
            }
            return this._relationships.addRelationship(packagePartName.getURI(), targetMode, str, str2);
        }
    }

    public PackageRelationship addRelationship(URI uri, TargetMode targetMode, String str) {
        return addRelationship(uri, targetMode, str, (String) null);
    }

    public PackageRelationship addRelationship(URI uri, TargetMode targetMode, String str, String str2) {
        if (uri == null) {
            throw new IllegalArgumentException("targetPartName");
        } else if (targetMode == null) {
            throw new IllegalArgumentException("targetMode");
        } else if (str == null) {
            throw new IllegalArgumentException("relationshipType");
        } else if (this._isRelationshipPart || PackagingURIHelper.isRelationshipPartURI(uri)) {
            throw new InvalidOperationException("Rule M1.25: The Relationships part shall not have relationships to any other part.");
        } else {
            if (this._relationships == null) {
                this._relationships = new PackageRelationshipCollection();
            }
            return this._relationships.addRelationship(uri, targetMode, str, str2);
        }
    }

    public void clearRelationships() {
        PackageRelationshipCollection packageRelationshipCollection = this._relationships;
        if (packageRelationshipCollection != null) {
            packageRelationshipCollection.clear();
        }
    }

    public void removeRelationship(String str) {
        PackageRelationshipCollection packageRelationshipCollection = this._relationships;
        if (packageRelationshipCollection != null) {
            packageRelationshipCollection.removeRelationship(str);
        }
    }

    public PackageRelationshipCollection getRelationships() throws InvalidFormatException {
        return getRelationshipsCore((String) null);
    }

    public PackageRelationship getRelationship(String str) {
        return this._relationships.getRelationshipByID(str);
    }

    public PackageRelationshipCollection getRelationshipsByType(String str) throws InvalidFormatException {
        return getRelationshipsCore(str);
    }

    private PackageRelationshipCollection getRelationshipsCore(String str) throws InvalidFormatException {
        if (this._relationships == null) {
            throwExceptionIfRelationship();
            this._relationships = new PackageRelationshipCollection(this);
        }
        return new PackageRelationshipCollection(this._relationships, str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1._relationships;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasRelationships() {
        /*
            r1 = this;
            boolean r0 = r1._isRelationshipPart
            if (r0 != 0) goto L_0x0010
            com.app.office.fc.openxml4j.opc.PackageRelationshipCollection r0 = r1._relationships
            if (r0 == 0) goto L_0x0010
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.openxml4j.opc.PackagePart.hasRelationships():boolean");
    }

    public boolean isRelationshipExists(PackageRelationship packageRelationship) {
        try {
            Iterator<PackageRelationship> it = getRelationships().iterator();
            while (it.hasNext()) {
                if (it.next() == packageRelationship) {
                    return true;
                }
            }
            return false;
        } catch (InvalidFormatException unused) {
            return false;
        }
    }

    public InputStream getInputStream() throws IOException {
        InputStream inputStreamImpl = getInputStreamImpl();
        if (inputStreamImpl != null) {
            return inputStreamImpl;
        }
        throw new IOException("Can't obtain the input stream from " + this._partName.getName());
    }

    public OutputStream getOutputStream() {
        if (!(this instanceof ZipPackagePart)) {
            return getOutputStreamImpl();
        }
        this._container.removePart(this._partName);
        PackagePart createPart = this._container.createPart(this._partName, this._contentType.toString(), false);
        if (createPart != null) {
            createPart._relationships = this._relationships;
            return createPart.getOutputStreamImpl();
        }
        throw new InvalidOperationException("Can't create a temporary part !");
    }

    private void throwExceptionIfRelationship() throws InvalidOperationException {
        if (this._isRelationshipPart) {
            throw new InvalidOperationException("Can do this operation on a relationship part !");
        }
    }

    public void loadRelationships() throws InvalidFormatException {
        PackageRelationshipCollection packageRelationshipCollection = this._relationships;
        if ((packageRelationshipCollection == null || packageRelationshipCollection.size() == 0) && !this._isRelationshipPart) {
            throwExceptionIfRelationship();
            this._relationships = new PackageRelationshipCollection(this);
        }
    }

    public PackagePartName getPartName() {
        return this._partName;
    }

    public String getContentType() {
        return this._contentType.toString();
    }

    public void setContentType(String str) throws InvalidFormatException {
        if (this._container == null) {
            this._contentType = new ContentType(str);
            return;
        }
        throw new InvalidOperationException("You can't change the content type of a part.");
    }

    public ZipPackage getPackage() {
        return this._container;
    }

    public boolean isRelationshipPart() {
        return this._isRelationshipPart;
    }

    public boolean isDeleted() {
        return this._isDeleted;
    }

    public void setDeleted(boolean z) {
        this._isDeleted = z;
    }

    public String toString() {
        return "Name: " + this._partName + " - Content Type: " + this._contentType.toString();
    }
}
