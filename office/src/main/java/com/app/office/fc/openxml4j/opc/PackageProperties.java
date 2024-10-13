package com.app.office.fc.openxml4j.opc;

import com.app.office.fc.openxml4j.util.Nullable;
import java.util.Date;

public interface PackageProperties {
    public static final String NAMESPACE_DC = "http://purl.org/dc/elements/1.1/";
    public static final String NAMESPACE_DCTERMS = "http://purl.org/dc/terms/";

    Nullable<String> getCategoryProperty();

    Nullable<String> getContentStatusProperty();

    Nullable<String> getContentTypeProperty();

    Nullable<Date> getCreatedProperty();

    Nullable<String> getCreatorProperty();

    Nullable<String> getDescriptionProperty();

    Nullable<String> getIdentifierProperty();

    Nullable<String> getKeywordsProperty();

    Nullable<String> getLanguageProperty();

    Nullable<String> getLastModifiedByProperty();

    Nullable<Date> getLastPrintedProperty();

    Nullable<Date> getModifiedProperty();

    Nullable<String> getRevisionProperty();

    Nullable<String> getSubjectProperty();

    Nullable<String> getTitleProperty();

    Nullable<String> getVersionProperty();

    void setCategoryProperty(String str);

    void setContentStatusProperty(String str);

    void setContentTypeProperty(String str);

    void setCreatedProperty(Nullable<Date> nullable);

    void setCreatedProperty(String str);

    void setCreatorProperty(String str);

    void setDescriptionProperty(String str);

    void setIdentifierProperty(String str);

    void setKeywordsProperty(String str);

    void setLanguageProperty(String str);

    void setLastModifiedByProperty(String str);

    void setLastPrintedProperty(Nullable<Date> nullable);

    void setLastPrintedProperty(String str);

    void setModifiedProperty(Nullable<Date> nullable);

    void setModifiedProperty(String str);

    void setRevisionProperty(String str);

    void setSubjectProperty(String str);

    void setTitleProperty(String str);

    void setVersionProperty(String str);
}
