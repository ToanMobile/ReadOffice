package com.app.office.fc.openxml4j.opc.internal.marshallers;

import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.itextpdf.text.xml.xmp.DublinCoreSchema;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.DocumentHelper;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.Namespace;
import com.app.office.fc.dom4j.QName;
import com.app.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.internal.PackagePropertiesPart;
import com.app.office.fc.openxml4j.opc.internal.PartMarshaller;
import java.io.OutputStream;

public class PackagePropertiesMarshaller implements PartMarshaller {
    protected static final String KEYWORD_CATEGORY = "category";
    protected static final String KEYWORD_CONTENT_STATUS = "contentStatus";
    protected static final String KEYWORD_CONTENT_TYPE = "contentType";
    protected static final String KEYWORD_CREATED = "created";
    protected static final String KEYWORD_CREATOR = "creator";
    protected static final String KEYWORD_DESCRIPTION = "description";
    protected static final String KEYWORD_IDENTIFIER = "identifier";
    protected static final String KEYWORD_KEYWORDS = "keywords";
    protected static final String KEYWORD_LANGUAGE = "language";
    protected static final String KEYWORD_LAST_MODIFIED_BY = "lastModifiedBy";
    protected static final String KEYWORD_LAST_PRINTED = "lastPrinted";
    protected static final String KEYWORD_MODIFIED = "modified";
    protected static final String KEYWORD_REVISION = "revision";
    protected static final String KEYWORD_SUBJECT = "subject";
    protected static final String KEYWORD_TITLE = "title";
    protected static final String KEYWORD_VERSION = "version";
    private static final Namespace namespaceCoreProperties = new Namespace("", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
    private static final Namespace namespaceDC = new Namespace(DublinCoreSchema.DEFAULT_XPATH_ID, "http://purl.org/dc/elements/1.1/");
    private static final Namespace namespaceDcTerms = new Namespace("dcterms", "http://purl.org/dc/terms/");
    private static final Namespace namespaceXSI = new Namespace("xsi", PackagePropertiesPart.NAMESPACE_XSI_URI);
    PackagePropertiesPart propsPart;
    Document xmlDoc = null;

    public boolean marshall(PackagePart packagePart, OutputStream outputStream) throws OpenXML4JException {
        if (packagePart instanceof PackagePropertiesPart) {
            this.propsPart = (PackagePropertiesPart) packagePart;
            Document createDocument = DocumentHelper.createDocument();
            this.xmlDoc = createDocument;
            Element addElement = createDocument.addElement(new QName("coreProperties", namespaceCoreProperties));
            addElement.addNamespace("cp", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
            addElement.addNamespace(DublinCoreSchema.DEFAULT_XPATH_ID, "http://purl.org/dc/elements/1.1/");
            addElement.addNamespace("dcterms", "http://purl.org/dc/terms/");
            addElement.addNamespace("xsi", PackagePropertiesPart.NAMESPACE_XSI_URI);
            addCategory();
            addContentStatus();
            addContentType();
            addCreated();
            addCreator();
            addDescription();
            addIdentifier();
            addKeywords();
            addLanguage();
            addLastModifiedBy();
            addLastPrinted();
            addModified();
            addRevision();
            addSubject();
            addTitle();
            addVersion();
            return true;
        }
        throw new IllegalArgumentException("'part' must be a PackagePropertiesPart instance.");
    }

    private void addCategory() {
        if (this.propsPart.getCategoryProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName(KEYWORD_CATEGORY, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_CATEGORY, namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getCategoryProperty().getValue());
        }
    }

    private void addContentStatus() {
        if (this.propsPart.getContentStatusProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName(KEYWORD_CONTENT_STATUS, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_CONTENT_STATUS, namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getContentStatusProperty().getValue());
        }
    }

    private void addContentType() {
        if (this.propsPart.getContentTypeProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName(KEYWORD_CONTENT_TYPE, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_CONTENT_TYPE, namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getContentTypeProperty().getValue());
        }
    }

    private void addCreated() {
        if (this.propsPart.getCreatedProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDcTerms;
            Element element = rootElement.element(new QName(KEYWORD_CREATED, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_CREATED, namespace));
            } else {
                element.clearContent();
            }
            element.addAttribute(new QName(DublinCoreProperties.TYPE, namespaceXSI), "dcterms:W3CDTF");
            element.addText(this.propsPart.getCreatedPropertyString());
        }
    }

    private void addCreator() {
        if (this.propsPart.getCreatorProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDC;
            Element element = rootElement.element(new QName("creator", namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName("creator", namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getCreatorProperty().getValue());
        }
    }

    private void addDescription() {
        if (this.propsPart.getDescriptionProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDC;
            Element element = rootElement.element(new QName("description", namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName("description", namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getDescriptionProperty().getValue());
        }
    }

    private void addIdentifier() {
        if (this.propsPart.getIdentifierProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDC;
            Element element = rootElement.element(new QName("identifier", namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName("identifier", namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getIdentifierProperty().getValue());
        }
    }

    private void addKeywords() {
        if (this.propsPart.getKeywordsProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName("keywords", namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName("keywords", namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getKeywordsProperty().getValue());
        }
    }

    private void addLanguage() {
        if (this.propsPart.getLanguageProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDC;
            Element element = rootElement.element(new QName("language", namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName("language", namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getLanguageProperty().getValue());
        }
    }

    private void addLastModifiedBy() {
        if (this.propsPart.getLastModifiedByProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName(KEYWORD_LAST_MODIFIED_BY, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_LAST_MODIFIED_BY, namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getLastModifiedByProperty().getValue());
        }
    }

    private void addLastPrinted() {
        if (this.propsPart.getLastPrintedProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName(KEYWORD_LAST_PRINTED, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_LAST_PRINTED, namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getLastPrintedPropertyString());
        }
    }

    private void addModified() {
        if (this.propsPart.getModifiedProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDcTerms;
            Element element = rootElement.element(new QName(KEYWORD_MODIFIED, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_MODIFIED, namespace));
            } else {
                element.clearContent();
            }
            element.addAttribute(new QName(DublinCoreProperties.TYPE, namespaceXSI), "dcterms:W3CDTF");
            element.addText(this.propsPart.getModifiedPropertyString());
        }
    }

    private void addRevision() {
        if (this.propsPart.getRevisionProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName(KEYWORD_REVISION, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_REVISION, namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getRevisionProperty().getValue());
        }
    }

    private void addSubject() {
        if (this.propsPart.getSubjectProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDC;
            Element element = rootElement.element(new QName("subject", namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName("subject", namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getSubjectProperty().getValue());
        }
    }

    private void addTitle() {
        if (this.propsPart.getTitleProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceDC;
            Element element = rootElement.element(new QName("title", namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName("title", namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getTitleProperty().getValue());
        }
    }

    private void addVersion() {
        if (this.propsPart.getVersionProperty().hasValue()) {
            Element rootElement = this.xmlDoc.getRootElement();
            Namespace namespace = namespaceCoreProperties;
            Element element = rootElement.element(new QName(KEYWORD_VERSION, namespace));
            if (element == null) {
                element = this.xmlDoc.getRootElement().addElement(new QName(KEYWORD_VERSION, namespace));
            } else {
                element.clearContent();
            }
            element.addText(this.propsPart.getVersionProperty().getValue());
        }
    }
}
