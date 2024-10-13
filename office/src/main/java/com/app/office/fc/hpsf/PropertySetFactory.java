package com.app.office.fc.hpsf;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class PropertySetFactory {
    public static PropertySet create(InputStream inputStream) throws NoPropertySetStreamException, MarkUnsupportedException, UnsupportedEncodingException, IOException {
        PropertySet propertySet = new PropertySet(inputStream);
        try {
            if (propertySet.isSummaryInformation()) {
                return new SummaryInformation(propertySet);
            }
            return propertySet.isDocumentSummaryInformation() ? new DocumentSummaryInformation(propertySet) : propertySet;
        } catch (UnexpectedPropertySetTypeException e) {
            throw new Error(e.toString());
        }
    }

    public static SummaryInformation newSummaryInformation() {
        MutablePropertySet mutablePropertySet = new MutablePropertySet();
        ((MutableSection) mutablePropertySet.getFirstSection()).setFormatID(SectionIDMap.SUMMARY_INFORMATION_ID);
        try {
            return new SummaryInformation(mutablePropertySet);
        } catch (UnexpectedPropertySetTypeException e) {
            throw new HPSFRuntimeException((Throwable) e);
        }
    }

    public static DocumentSummaryInformation newDocumentSummaryInformation() {
        MutablePropertySet mutablePropertySet = new MutablePropertySet();
        ((MutableSection) mutablePropertySet.getFirstSection()).setFormatID(SectionIDMap.DOCUMENT_SUMMARY_INFORMATION_ID[0]);
        try {
            return new DocumentSummaryInformation(mutablePropertySet);
        } catch (UnexpectedPropertySetTypeException e) {
            throw new HPSFRuntimeException((Throwable) e);
        }
    }
}
