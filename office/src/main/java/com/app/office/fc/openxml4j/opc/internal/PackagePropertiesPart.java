package com.app.office.fc.openxml4j.opc.internal;

import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import com.app.office.fc.openxml4j.opc.ContentTypes;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackagePartName;
import com.app.office.fc.openxml4j.opc.PackageProperties;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.openxml4j.util.Nullable;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class PackagePropertiesPart extends PackagePart implements PackageProperties {
    public static final String NAMESPACE_CP_URI = "http://schemas.openxmlformats.org/package/2006/metadata/core-properties";
    public static final String NAMESPACE_DCTERMS_URI = "http://purl.org/dc/terms/";
    public static final String NAMESPACE_DC_URI = "http://purl.org/dc/elements/1.1/";
    public static final String NAMESPACE_XSI_URI = "http://www.w3.org/2001/XMLSchema-instance";
    protected Nullable<String> category = new Nullable<>();
    protected Nullable<String> contentStatus = new Nullable<>();
    protected Nullable<String> contentType = new Nullable<>();
    protected Nullable<Date> created = new Nullable<>();
    protected Nullable<String> creator = new Nullable<>();
    protected Nullable<String> description = new Nullable<>();
    protected Nullable<String> identifier = new Nullable<>();
    protected Nullable<String> keywords = new Nullable<>();
    protected Nullable<String> language = new Nullable<>();
    protected Nullable<String> lastModifiedBy = new Nullable<>();
    protected Nullable<Date> lastPrinted = new Nullable<>();
    protected Nullable<Date> modified = new Nullable<>();
    protected Nullable<String> revision = new Nullable<>();
    protected Nullable<String> subject = new Nullable<>();
    protected Nullable<String> title = new Nullable<>();
    protected Nullable<String> version = new Nullable<>();

    public void close() {
    }

    public void flush() {
    }

    public PackagePropertiesPart(ZipPackage zipPackage, PackagePartName packagePartName) throws InvalidFormatException {
        super(zipPackage, packagePartName, ContentTypes.CORE_PROPERTIES_PART);
    }

    public Nullable<String> getCategoryProperty() {
        return this.category;
    }

    public Nullable<String> getContentStatusProperty() {
        return this.contentStatus;
    }

    public Nullable<String> getContentTypeProperty() {
        return this.contentType;
    }

    public Nullable<Date> getCreatedProperty() {
        return this.created;
    }

    public String getCreatedPropertyString() {
        return getDateValue(this.created);
    }

    public Nullable<String> getCreatorProperty() {
        return this.creator;
    }

    public Nullable<String> getDescriptionProperty() {
        return this.description;
    }

    public Nullable<String> getIdentifierProperty() {
        return this.identifier;
    }

    public Nullable<String> getKeywordsProperty() {
        return this.keywords;
    }

    public Nullable<String> getLanguageProperty() {
        return this.language;
    }

    public Nullable<String> getLastModifiedByProperty() {
        return this.lastModifiedBy;
    }

    public Nullable<Date> getLastPrintedProperty() {
        return this.lastPrinted;
    }

    public String getLastPrintedPropertyString() {
        return getDateValue(this.lastPrinted);
    }

    public Nullable<Date> getModifiedProperty() {
        return this.modified;
    }

    public String getModifiedPropertyString() {
        if (this.modified.hasValue()) {
            return getDateValue(this.modified);
        }
        return getDateValue(new Nullable(new Date()));
    }

    public Nullable<String> getRevisionProperty() {
        return this.revision;
    }

    public Nullable<String> getSubjectProperty() {
        return this.subject;
    }

    public Nullable<String> getTitleProperty() {
        return this.title;
    }

    public Nullable<String> getVersionProperty() {
        return this.version;
    }

    public void setCategoryProperty(String str) {
        this.category = setStringValue(str);
    }

    public void setContentStatusProperty(String str) {
        this.contentStatus = setStringValue(str);
    }

    public void setContentTypeProperty(String str) {
        this.contentType = setStringValue(str);
    }

    public void setCreatedProperty(String str) {
        try {
            this.created = setDateValue(str);
        } catch (InvalidFormatException e) {
            new IllegalArgumentException("created  : " + e.getLocalizedMessage());
        }
    }

    public void setCreatedProperty(Nullable<Date> nullable) {
        if (nullable.hasValue()) {
            this.created = nullable;
        }
    }

    public void setCreatorProperty(String str) {
        this.creator = setStringValue(str);
    }

    public void setDescriptionProperty(String str) {
        this.description = setStringValue(str);
    }

    public void setIdentifierProperty(String str) {
        this.identifier = setStringValue(str);
    }

    public void setKeywordsProperty(String str) {
        this.keywords = setStringValue(str);
    }

    public void setLanguageProperty(String str) {
        this.language = setStringValue(str);
    }

    public void setLastModifiedByProperty(String str) {
        this.lastModifiedBy = setStringValue(str);
    }

    public void setLastPrintedProperty(String str) {
        try {
            this.lastPrinted = setDateValue(str);
        } catch (InvalidFormatException e) {
            new IllegalArgumentException("lastPrinted  : " + e.getLocalizedMessage());
        }
    }

    public void setLastPrintedProperty(Nullable<Date> nullable) {
        if (nullable.hasValue()) {
            this.lastPrinted = nullable;
        }
    }

    public void setModifiedProperty(String str) {
        try {
            this.modified = setDateValue(str);
        } catch (InvalidFormatException e) {
            new IllegalArgumentException("modified  : " + e.getLocalizedMessage());
        }
    }

    public void setModifiedProperty(Nullable<Date> nullable) {
        if (nullable.hasValue()) {
            this.modified = nullable;
        }
    }

    public void setRevisionProperty(String str) {
        this.revision = setStringValue(str);
    }

    public void setSubjectProperty(String str) {
        this.subject = setStringValue(str);
    }

    public void setTitleProperty(String str) {
        this.title = setStringValue(str);
    }

    public void setVersionProperty(String str) {
        this.version = setStringValue(str);
    }

    private Nullable<String> setStringValue(String str) {
        if (str == null || str.equals("")) {
            return new Nullable<>();
        }
        return new Nullable<>(str);
    }

    private Nullable<Date> setDateValue(String str) throws InvalidFormatException {
        if (str == null || str.equals("")) {
            return new Nullable<>();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date parse = simpleDateFormat.parse(str, new ParsePosition(0));
        if (parse != null) {
            return new Nullable<>(parse);
        }
        throw new InvalidFormatException("Date not well formated");
    }

    private String getDateValue(Nullable<Date> nullable) {
        Date value;
        if (nullable == null || (value = nullable.getValue()) == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(value);
    }

    /* access modifiers changed from: protected */
    public InputStream getInputStreamImpl() {
        throw new InvalidOperationException("Operation not authorized. This part may only be manipulated using the getters and setters on PackagePropertiesPart");
    }

    /* access modifiers changed from: protected */
    public OutputStream getOutputStreamImpl() {
        throw new InvalidOperationException("Can't use output stream to set properties !");
    }

    public boolean save(OutputStream outputStream) {
        throw new InvalidOperationException("Operation not authorized. This part may only be manipulated using the getters and setters on PackagePropertiesPart");
    }

    public boolean load(InputStream inputStream) {
        throw new InvalidOperationException("Operation not authorized. This part may only be manipulated using the getters and setters on PackagePropertiesPart");
    }
}
