package com.app.office.fc.hpsf;

import com.app.office.fc.POIDocument;
import com.app.office.fc.POITextExtractor;
import com.app.office.fc.poifs.filesystem.NPOIFSFileSystem;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import com.app.office.fc.util.LittleEndian;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class HPSFPropertiesExtractor extends POITextExtractor {
    public HPSFPropertiesExtractor(POITextExtractor pOITextExtractor) {
        super(pOITextExtractor);
    }

    public HPSFPropertiesExtractor(POIDocument pOIDocument) {
        super(pOIDocument);
    }

    public HPSFPropertiesExtractor(POIFSFileSystem pOIFSFileSystem) {
        super((POIDocument) new PropertiesOnlyDocument(pOIFSFileSystem));
    }

    public HPSFPropertiesExtractor(NPOIFSFileSystem nPOIFSFileSystem) {
        super((POIDocument) new PropertiesOnlyDocument(nPOIFSFileSystem));
    }

    public String getDocumentSummaryInformationText() {
        CustomProperties customProperties;
        DocumentSummaryInformation documentSummaryInformation = this.document.getDocumentSummaryInformation();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getPropertiesText(documentSummaryInformation));
        if (documentSummaryInformation == null) {
            customProperties = null;
        } else {
            customProperties = documentSummaryInformation.getCustomProperties();
        }
        if (customProperties != null) {
            for (String next : customProperties.nameSet()) {
                String propertyValueText = getPropertyValueText(customProperties.get(next));
                stringBuffer.append(next + " = " + propertyValueText + "\n");
            }
        }
        return stringBuffer.toString();
    }

    public String getSummaryInformationText() {
        return getPropertiesText(this.document.getSummaryInformation());
    }

    private static String getPropertiesText(SpecialPropertySet specialPropertySet) {
        if (specialPropertySet == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        PropertyIDMap propertySetIDMap = specialPropertySet.getPropertySetIDMap();
        Property[] properties = specialPropertySet.getProperties();
        for (int i = 0; i < properties.length; i++) {
            String l = Long.toString(properties[i].getID());
            Object obj = propertySetIDMap.get(properties[i].getID());
            if (obj != null) {
                l = obj.toString();
            }
            String propertyValueText = getPropertyValueText(properties[i].getValue());
            stringBuffer.append(l + " = " + propertyValueText + "\n");
        }
        return stringBuffer.toString();
    }

    private static String getPropertyValueText(Object obj) {
        if (obj == null) {
            return "(not set)";
        }
        if (!(obj instanceof byte[])) {
            return obj.toString();
        }
        byte[] bArr = (byte[]) obj;
        if (bArr.length == 0) {
            return "";
        }
        if (bArr.length == 1) {
            return Byte.toString(bArr[0]);
        }
        if (bArr.length == 2) {
            return Integer.toString(LittleEndian.getUShort(bArr));
        }
        if (bArr.length == 4) {
            return Long.toString(LittleEndian.getUInt(bArr));
        }
        return new String(bArr);
    }

    public String getText() {
        return getSummaryInformationText() + getDocumentSummaryInformationText();
    }

    public POITextExtractor getMetadataTextExtractor() {
        throw new IllegalStateException("You already have the Metadata Text Extractor, not recursing!");
    }

    private static final class PropertiesOnlyDocument extends POIDocument {
        public PropertiesOnlyDocument(NPOIFSFileSystem nPOIFSFileSystem) {
            super(nPOIFSFileSystem.getRoot());
        }

        public PropertiesOnlyDocument(POIFSFileSystem pOIFSFileSystem) {
            super(pOIFSFileSystem);
        }

        public void write(OutputStream outputStream) {
            throw new IllegalStateException("Unable to write, only for properties!");
        }
    }

    public static void main(String[] strArr) throws IOException {
        for (String file : strArr) {
            System.out.println(new HPSFPropertiesExtractor(new NPOIFSFileSystem(new File(file))).getText());
        }
    }
}
