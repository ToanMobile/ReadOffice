package com.app.office.fc.openxml4j.opc;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.text.xml.xmp.PdfProperties;
import com.app.office.fc.EncryptedDocumentException;
import com.app.office.fc.fs.storage.LittleEndian;
import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import com.app.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.app.office.fc.openxml4j.exceptions.OpenXML4JRuntimeException;
import com.app.office.fc.openxml4j.opc.internal.ContentType;
import com.app.office.fc.openxml4j.opc.internal.ContentTypeManager;
import com.app.office.fc.openxml4j.opc.internal.FileHelper;
import com.app.office.fc.openxml4j.opc.internal.PackagePropertiesPart;
import com.app.office.fc.openxml4j.opc.internal.PartMarshaller;
import com.app.office.fc.openxml4j.opc.internal.PartUnmarshaller;
import com.app.office.fc.openxml4j.opc.internal.ZipHelper;
import com.app.office.fc.openxml4j.opc.internal.marshallers.DefaultMarshaller;
import com.app.office.fc.openxml4j.opc.internal.marshallers.ZipPackagePropertiesMarshaller;
import com.app.office.fc.openxml4j.opc.internal.marshallers.ZipPartMarshaller;
import com.app.office.fc.openxml4j.opc.internal.unmarshallers.PackagePropertiesUnmarshaller;
import com.app.office.fc.openxml4j.opc.internal.unmarshallers.UnmarshallContext;
import com.app.office.fc.openxml4j.util.ZipEntrySource;
import com.app.office.fc.openxml4j.util.ZipFileZipEntrySource;
import com.app.office.fc.openxml4j.util.ZipInputStreamZipEntrySource;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipPackage implements RelationshipSource, Closeable {
    protected ContentTypeManager contentTypeManager;
    protected PartMarshaller defaultPartMarshaller;
    protected boolean isDirty = false;
    protected String originalPackagePath;
    protected OutputStream output;
    protected PackagePropertiesPart packageProperties;
    protected PackagePartCollection partList;
    protected Hashtable<ContentType, PartMarshaller> partMarshallers;
    protected Hashtable<ContentType, PartUnmarshaller> partUnmarshallers;
    protected PackageRelationshipCollection relationships;
    private ZipEntrySource zipArchive;

    /* access modifiers changed from: protected */
    public PackagePart createPartImpl(PackagePartName packagePartName, String str, boolean z) {
        return null;
    }

    public ZipPackage(String str) {
        if (str == null || "".equals(str.trim()) || (new File(str).exists() && new File(str).isDirectory())) {
            throw new IllegalArgumentException("path");
        }
        init();
        try {
            this.zipArchive = new ZipFileZipEntrySource(new ZipFile(new File(str)));
            getParts();
            this.originalPackagePath = new File(str).getAbsolutePath();
            return;
        } catch (Exception unused) {
            File file = new File(str);
            if (file.length() != 0) {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[16];
                fileInputStream.read(bArr);
                if (LittleEndian.getLong(bArr, 0) == -2226271756974174256L) {
                    throw new EncryptedDocumentException("Cannot process encrypted office files!");
                }
            } else {
                throw new EncryptedDocumentException("Format error");
            }
        } catch (IOException unused2) {
        }
        throw new EncryptedDocumentException("Invalid header signature");
    }

    public ZipPackage(InputStream inputStream) throws IOException {
        try {
            init();
            this.zipArchive = new ZipInputStreamZipEntrySource(new ZipInputStream(inputStream));
            getParts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        this.partMarshallers = new Hashtable<>(5);
        Hashtable<ContentType, PartUnmarshaller> hashtable = new Hashtable<>(2);
        this.partUnmarshallers = hashtable;
        try {
            hashtable.put(new ContentType(ContentTypes.CORE_PROPERTIES_PART), new PackagePropertiesUnmarshaller());
            this.defaultPartMarshaller = new DefaultMarshaller();
            this.partMarshallers.put(new ContentType(ContentTypes.CORE_PROPERTIES_PART), new ZipPackagePropertiesMarshaller());
        } catch (InvalidFormatException e) {
            throw new OpenXML4JRuntimeException("Package.init() : this exception should never happen, if you read this message please send a mail to the developers team. : " + e.getMessage());
        }
    }

    public void flush() {
        PackagePropertiesPart packagePropertiesPart = this.packageProperties;
        if (packagePropertiesPart != null) {
            packagePropertiesPart.flush();
        }
    }

    /* JADX INFO: finally extract failed */
    public void close() throws IOException {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        try {
            reentrantReadWriteLock.writeLock().lock();
            String str = this.originalPackagePath;
            if (str == null || "".equals(str.trim())) {
                OutputStream outputStream = this.output;
                if (outputStream != null) {
                    save(outputStream);
                    this.output.close();
                }
            } else {
                File file = new File(this.originalPackagePath);
                if (file.exists()) {
                    if (this.originalPackagePath.equalsIgnoreCase(file.getAbsolutePath())) {
                        closeImpl();
                    }
                }
                save(file);
            }
            reentrantReadWriteLock.writeLock().unlock();
            this.contentTypeManager.clearAll();
        } catch (Throwable th) {
            reentrantReadWriteLock.writeLock().unlock();
            throw th;
        }
    }

    public void revert() {
        revertImpl();
    }

    public PackageProperties getPackageProperties() throws InvalidFormatException {
        if (this.packageProperties == null) {
            this.packageProperties = new PackagePropertiesPart(this, PackagingURIHelper.CORE_PROPERTIES_PART_NAME);
        }
        return this.packageProperties;
    }

    public PackagePart getPart(URI uri) {
        if (uri != null) {
            try {
                if (this.partList == null) {
                    getParts();
                }
                return getPartImpl(PackagingURIHelper.createPartName(uri));
            } catch (InvalidFormatException unused) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("partName");
        }
    }

    public PackagePart getPart(PackagePartName packagePartName) {
        if (packagePartName != null) {
            if (this.partList == null) {
                try {
                    getParts();
                } catch (InvalidFormatException unused) {
                    return null;
                }
            }
            return getPartImpl(packagePartName);
        }
        throw new IllegalArgumentException("partName");
    }

    public ArrayList<PackagePart> getPartsByContentType(String str) {
        ArrayList<PackagePart> arrayList = new ArrayList<>();
        for (PackagePart packagePart : this.partList.values()) {
            if (packagePart.getContentType().equals(str)) {
                arrayList.add(packagePart);
            }
        }
        return arrayList;
    }

    public ArrayList<PackagePart> getPartsByRelationshipType(String str) {
        if (str != null) {
            ArrayList<PackagePart> arrayList = new ArrayList<>();
            Iterator<PackageRelationship> it = getRelationshipsByType(str).iterator();
            while (it.hasNext()) {
                arrayList.add(getPart(it.next()));
            }
            return arrayList;
        }
        throw new IllegalArgumentException("relationshipType");
    }

    public List<PackagePart> getPartsByName(Pattern pattern) {
        if (pattern != null) {
            ArrayList arrayList = new ArrayList();
            for (PackagePart packagePart : this.partList.values()) {
                if (pattern.matcher(packagePart.getPartName().getName()).matches()) {
                    arrayList.add(packagePart);
                }
            }
            return arrayList;
        }
        throw new IllegalArgumentException("name pattern must not be null");
    }

    public PackagePart getPart(PackageRelationship packageRelationship) {
        ensureRelationships();
        Iterator<PackageRelationship> it = this.relationships.iterator();
        while (it.hasNext()) {
            PackageRelationship next = it.next();
            if (next.getRelationshipType().equals(packageRelationship.getRelationshipType())) {
                try {
                    return getPart(PackagingURIHelper.createPartName(next.getTargetURI()));
                } catch (InvalidFormatException unused) {
                }
            }
        }
        return null;
    }

    public ArrayList<PackagePart> getParts() throws InvalidFormatException {
        if (this.partList == null) {
            try {
                this.partList = new PackagePartCollection();
                Enumeration<? extends ZipEntry> entries = this.zipArchive.getEntries();
                while (true) {
                    if (!entries.hasMoreElements()) {
                        break;
                    }
                    ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                    if (zipEntry.getName().equalsIgnoreCase(ContentTypeManager.CONTENT_TYPES_PART_NAME)) {
                        InputStream inputStream = this.zipArchive.getInputStream(zipEntry);
                        this.contentTypeManager = new ContentTypeManager(inputStream, this);
                        inputStream.close();
                        break;
                    }
                }
                Enumeration<? extends ZipEntry> entries2 = this.zipArchive.getEntries();
                while (entries2.hasMoreElements()) {
                    ZipEntry zipEntry2 = (ZipEntry) entries2.nextElement();
                    PackagePartName buildPartName = buildPartName(zipEntry2);
                    if (buildPartName != null) {
                        String contentType = this.contentTypeManager.getContentType(buildPartName);
                        if (contentType != null) {
                            ZipPackagePart zipPackagePart = new ZipPackagePart(this, zipEntry2, buildPartName, contentType);
                            if (contentType.equals(ContentTypes.CORE_PROPERTIES_PART)) {
                                PartUnmarshaller partUnmarshaller = this.partUnmarshallers.get(contentType);
                                if (partUnmarshaller != null) {
                                    PackagePart unmarshall = partUnmarshaller.unmarshall(new UnmarshallContext(this, zipPackagePart._partName), zipPackagePart.getInputStream());
                                    this.partList.put(unmarshall._partName, unmarshall);
                                    if (unmarshall instanceof PackagePropertiesPart) {
                                        this.packageProperties = (PackagePropertiesPart) unmarshall;
                                    }
                                }
                            } else {
                                this.partList.put(buildPartName, (PackagePart) zipPackagePart);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayList<PackagePart> arrayList = new ArrayList<>(this.partList.values());
        Iterator<PackagePart> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().loadRelationships();
        }
        return arrayList;
    }

    public PackagePart createPart(PackagePartName packagePartName, String str) {
        return createPart(packagePartName, str, true);
    }

    /* access modifiers changed from: protected */
    public PackagePart createPart(PackagePartName packagePartName, String str, boolean z) {
        if (packagePartName == null) {
            throw new IllegalArgumentException("partName");
        } else if (str == null || str.equals("")) {
            throw new IllegalArgumentException("contentType");
        } else if (this.partList.containsKey(packagePartName) && !((PackagePart) this.partList.get(packagePartName)).isDeleted()) {
            throw new InvalidOperationException("A part with the name '" + packagePartName.getName() + "' already exists : Packages shall not contain equivalent part names and package implementers shall neither create nor recognize packages with equivalent part names. [M1.12]");
        } else if (!str.equals(ContentTypes.CORE_PROPERTIES_PART) || this.packageProperties == null) {
            PackagePart createPartImpl = createPartImpl(packagePartName, str, z);
            this.contentTypeManager.addContentType(packagePartName, str);
            this.partList.put(packagePartName, createPartImpl);
            this.isDirty = true;
            return createPartImpl;
        } else {
            throw new InvalidOperationException("OPC Compliance error [M4.1]: you try to add more than one core properties relationship in the package !");
        }
    }

    public PackagePart createPart(PackagePartName packagePartName, String str, ByteArrayOutputStream byteArrayOutputStream) {
        PackagePart createPart = createPart(packagePartName, str);
        if (!(createPart == null || byteArrayOutputStream == null)) {
            try {
                OutputStream outputStream = createPart.getOutputStream();
                if (outputStream == null) {
                    return null;
                }
                outputStream.write(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                outputStream.close();
                return createPart;
            } catch (IOException unused) {
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public PackagePart addPackagePart(PackagePart packagePart) {
        if (packagePart != null) {
            if (this.partList.containsKey(packagePart._partName)) {
                if (((PackagePart) this.partList.get(packagePart._partName)).isDeleted()) {
                    packagePart.setDeleted(false);
                    this.partList.remove((Object) packagePart._partName);
                } else {
                    throw new InvalidOperationException("A part with the name '" + packagePart._partName.getName() + "' already exists : Packages shall not contain equivalent part names and package implementers shall neither create nor recognize packages with equivalent part names. [M1.12]");
                }
            }
            this.partList.put(packagePart._partName, packagePart);
            this.isDirty = true;
            return packagePart;
        }
        throw new IllegalArgumentException(PdfProperties.PART);
    }

    public void removePart(PackagePart packagePart) {
        if (packagePart != null) {
            removePart(packagePart.getPartName());
        }
    }

    public void removePart(PackagePartName packagePartName) {
        PackagePart part;
        if (packagePartName == null || !containPart(packagePartName)) {
            throw new IllegalArgumentException("partName");
        }
        if (this.partList.containsKey(packagePartName)) {
            ((PackagePart) this.partList.get(packagePartName)).setDeleted(true);
            this.partList.remove((Object) packagePartName);
        }
        this.contentTypeManager.removeContentType(packagePartName);
        if (packagePartName.isRelationshipPartURI()) {
            try {
                PackagePartName createPartName = PackagingURIHelper.createPartName(PackagingURIHelper.getSourcePartUriFromRelationshipPartUri(packagePartName.getURI()));
                if (createPartName.getURI().equals(PackagingURIHelper.PACKAGE_ROOT_URI)) {
                    clearRelationships();
                } else if (containPart(createPartName) && (part = getPart(createPartName)) != null) {
                    part.clearRelationships();
                }
            } catch (InvalidFormatException unused) {
                return;
            }
        }
        this.isDirty = true;
    }

    public void removePartRecursive(PackagePartName packagePartName) throws InvalidFormatException {
        PackagePart packagePart = (PackagePart) this.partList.get(PackagingURIHelper.getRelationshipPartName(packagePartName));
        PackagePart packagePart2 = (PackagePart) this.partList.get(packagePartName);
        if (packagePart != null) {
            Iterator<PackageRelationship> it = new PackageRelationshipCollection(packagePart2).iterator();
            while (it.hasNext()) {
                PackageRelationship next = it.next();
                removePart(PackagingURIHelper.createPartName(PackagingURIHelper.resolvePartUri(next.getSourceURI(), next.getTargetURI())));
            }
            removePart(packagePart._partName);
        }
        removePart(packagePart2._partName);
    }

    public void deletePart(PackagePartName packagePartName) {
        if (packagePartName != null) {
            removePart(packagePartName);
            removePart(PackagingURIHelper.getRelationshipPartName(packagePartName));
            return;
        }
        throw new IllegalArgumentException("partName");
    }

    public void deletePartRecursive(PackagePartName packagePartName) {
        if (packagePartName == null || !containPart(packagePartName)) {
            throw new IllegalArgumentException("partName");
        }
        PackagePart part = getPart(packagePartName);
        removePart(packagePartName);
        try {
            Iterator<PackageRelationship> it = part.getRelationships().iterator();
            while (it.hasNext()) {
                deletePartRecursive(PackagingURIHelper.createPartName(PackagingURIHelper.resolvePartUri(packagePartName.getURI(), it.next().getTargetURI())));
            }
            PackagePartName relationshipPartName = PackagingURIHelper.getRelationshipPartName(packagePartName);
            if (relationshipPartName != null && containPart(relationshipPartName)) {
                removePart(relationshipPartName);
            }
        } catch (InvalidFormatException unused) {
        }
    }

    public boolean containPart(PackagePartName packagePartName) {
        return getPart(packagePartName) != null;
    }

    public PackageRelationship addRelationship(PackagePartName packagePartName, TargetMode targetMode, String str, String str2) {
        if (str.equals(PackageRelationshipTypes.CORE_PROPERTIES) && this.packageProperties != null) {
            throw new InvalidOperationException("OPC Compliance error [M4.1]: can't add another core properties part ! Use the built-in package method instead.");
        } else if (!packagePartName.isRelationshipPartURI()) {
            ensureRelationships();
            PackageRelationship addRelationship = this.relationships.addRelationship(packagePartName.getURI(), targetMode, str, str2);
            this.isDirty = true;
            return addRelationship;
        } else {
            throw new InvalidOperationException("Rule M1.25: The Relationships part shall not have relationships to any other part.");
        }
    }

    public PackageRelationship addRelationship(PackagePartName packagePartName, TargetMode targetMode, String str) {
        return addRelationship(packagePartName, targetMode, str, (String) null);
    }

    public PackageRelationship addExternalRelationship(String str, String str2) {
        return addExternalRelationship(str, str2, (String) null);
    }

    public PackageRelationship addExternalRelationship(String str, String str2, String str3) {
        if (str == null) {
            throw new IllegalArgumentException(TypedValues.AttributesType.S_TARGET);
        } else if (str2 != null) {
            try {
                URI uri = new URI(str);
                ensureRelationships();
                PackageRelationship addRelationship = this.relationships.addRelationship(uri, TargetMode.EXTERNAL, str2, str3);
                this.isDirty = true;
                return addRelationship;
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid target - " + e);
            }
        } else {
            throw new IllegalArgumentException("relationshipType");
        }
    }

    public void removeRelationship(String str) {
        PackageRelationshipCollection packageRelationshipCollection = this.relationships;
        if (packageRelationshipCollection != null) {
            packageRelationshipCollection.removeRelationship(str);
            this.isDirty = true;
        }
    }

    public PackageRelationshipCollection getRelationships() {
        return getRelationshipsHelper((String) null);
    }

    public PackageRelationshipCollection getRelationshipsByType(String str) {
        if (str != null) {
            return getRelationshipsHelper(str);
        }
        throw new IllegalArgumentException("relationshipType");
    }

    private PackageRelationshipCollection getRelationshipsHelper(String str) {
        ensureRelationships();
        return this.relationships.getRelationships(str);
    }

    public void clearRelationships() {
        PackageRelationshipCollection packageRelationshipCollection = this.relationships;
        if (packageRelationshipCollection != null) {
            packageRelationshipCollection.clear();
            this.isDirty = true;
        }
    }

    public void ensureRelationships() {
        if (this.relationships == null) {
            try {
                this.relationships = new PackageRelationshipCollection(this);
            } catch (InvalidFormatException unused) {
                this.relationships = new PackageRelationshipCollection();
            }
        }
    }

    public PackageRelationship getRelationship(String str) {
        return this.relationships.getRelationshipByID(str);
    }

    public boolean hasRelationships() {
        return this.relationships.size() > 0;
    }

    public boolean isRelationshipExists(PackageRelationship packageRelationship) {
        Iterator<PackageRelationship> it = getRelationships().iterator();
        while (it.hasNext()) {
            if (it.next() == packageRelationship) {
                return true;
            }
        }
        return false;
    }

    public void addMarshaller(String str, PartMarshaller partMarshaller) {
        try {
            this.partMarshallers.put(new ContentType(str), partMarshaller);
        } catch (InvalidFormatException unused) {
        }
    }

    public void addUnmarshaller(String str, PartUnmarshaller partUnmarshaller) {
        try {
            this.partUnmarshallers.put(new ContentType(str), partUnmarshaller);
        } catch (InvalidFormatException unused) {
        }
    }

    public void removeMarshaller(String str) {
        this.partMarshallers.remove(str);
    }

    public void removeUnmarshaller(String str) {
        this.partUnmarshallers.remove(str);
    }

    public boolean validatePackage(ZipPackage zipPackage) throws InvalidFormatException {
        throw new InvalidOperationException("Not implemented yet !!!");
    }

    public void save(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("targetFile");
        } else if (!file.exists() || !file.getAbsolutePath().equals(this.originalPackagePath)) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                save((OutputStream) fileOutputStream);
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                throw new IOException(e.getLocalizedMessage());
            }
        } else {
            throw new InvalidOperationException("You can't call save(File) to save to the currently open file. To save to the current file, please just call close()");
        }
    }

    public void save(OutputStream outputStream) throws IOException {
        saveImpl(outputStream);
    }

    private PackagePartName buildPartName(ZipEntry zipEntry) {
        try {
            if (zipEntry.getName().equalsIgnoreCase(ContentTypeManager.CONTENT_TYPES_PART_NAME)) {
                return null;
            }
            return PackagingURIHelper.createPartName(ZipHelper.getOPCNameFromZipItemName(zipEntry.getName()));
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void closeImpl() throws IOException {
        flush();
        String str = this.originalPackagePath;
        if (str != null && !"".equals(str)) {
            File file = new File(this.originalPackagePath);
            if (file.exists()) {
                File createTempFile = File.createTempFile(generateTempFileName(FileHelper.getDirectory(file)), ".tmp");
                try {
                    save(createTempFile);
                    this.zipArchive.close();
                    FileHelper.copyFile(createTempFile, file);
                } finally {
                    createTempFile.delete();
                }
            } else {
                throw new InvalidOperationException("Can't close a package not previously open with the open() method !");
            }
        }
    }

    private synchronized String generateTempFileName(File file) {
        File file2;
        do {
            file2 = new File(file.getAbsoluteFile() + File.separator + "OpenXML4J" + System.nanoTime());
        } while (file2.exists());
        return FileHelper.getFilename(file2.getAbsoluteFile());
    }

    /* access modifiers changed from: protected */
    public void revertImpl() {
        try {
            ZipEntrySource zipEntrySource = this.zipArchive;
            if (zipEntrySource != null) {
                zipEntrySource.close();
            }
        } catch (IOException unused) {
        }
    }

    /* access modifiers changed from: protected */
    public PackagePart getPartImpl(PackagePartName packagePartName) {
        if (this.partList.containsKey(packagePartName)) {
            return (PackagePart) this.partList.get(packagePartName);
        }
        return null;
    }

    public void saveImpl(OutputStream outputStream) {
        ZipOutputStream zipOutputStream;
        try {
            if (!(outputStream instanceof ZipOutputStream)) {
                zipOutputStream = new ZipOutputStream(outputStream);
            } else {
                zipOutputStream = (ZipOutputStream) outputStream;
            }
            if (getPartsByRelationshipType(PackageRelationshipTypes.CORE_PROPERTIES).size() == 0 && getPartsByRelationshipType(PackageRelationshipTypes.CORE_PROPERTIES_ECMA376).size() == 0) {
                new ZipPackagePropertiesMarshaller().marshall(this.packageProperties, zipOutputStream);
                this.relationships.addRelationship(this.packageProperties.getPartName().getURI(), TargetMode.INTERNAL, PackageRelationshipTypes.CORE_PROPERTIES, (String) null);
                if (!this.contentTypeManager.isContentTypeRegister(ContentTypes.CORE_PROPERTIES_PART)) {
                    this.contentTypeManager.addContentType(this.packageProperties.getPartName(), ContentTypes.CORE_PROPERTIES_PART);
                }
            }
            ZipPartMarshaller.marshallRelationshipPart(getRelationships(), PackagingURIHelper.PACKAGE_RELATIONSHIPS_ROOT_PART_NAME, zipOutputStream);
            this.contentTypeManager.save(zipOutputStream);
            Iterator<PackagePart> it = getParts().iterator();
            while (it.hasNext()) {
                PackagePart next = it.next();
                if (!next.isRelationshipPart()) {
                    PartMarshaller partMarshaller = this.partMarshallers.get(next._contentType);
                    if (partMarshaller != null) {
                        if (!partMarshaller.marshall(next, zipOutputStream)) {
                            throw new OpenXML4JException("The part " + next.getPartName().getURI() + " fail to be saved in the stream with marshaller " + partMarshaller);
                        }
                    } else if (!this.defaultPartMarshaller.marshall(next, zipOutputStream)) {
                        throw new OpenXML4JException("The part " + next.getPartName().getURI() + " fail to be saved in the stream with marshaller " + this.defaultPartMarshaller);
                    }
                }
            }
            zipOutputStream.close();
        } catch (Exception e) {
            throw new OpenXML4JRuntimeException("Fail to save: an error occurs while saving the package : " + e.getMessage(), e);
        }
    }

    public ZipEntrySource getZipArchive() {
        return this.zipArchive;
    }
}
