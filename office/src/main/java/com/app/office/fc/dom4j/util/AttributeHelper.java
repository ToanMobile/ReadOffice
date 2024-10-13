package com.app.office.fc.dom4j.util;

import com.itextpdf.text.pdf.PdfBoolean;
import com.app.office.fc.dom4j.Attribute;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.QName;

public class AttributeHelper {
    protected AttributeHelper() {
    }

    public static boolean booleanValue(Element element, String str) {
        return booleanValue(element.attribute(str));
    }

    public static boolean booleanValue(Element element, QName qName) {
        return booleanValue(element.attribute(qName));
    }

    protected static boolean booleanValue(Attribute attribute) {
        Object data;
        if (attribute == null || (data = attribute.getData()) == null) {
            return false;
        }
        if (data instanceof Boolean) {
            return ((Boolean) data).booleanValue();
        }
        return PdfBoolean.TRUE.equalsIgnoreCase(data.toString());
    }
}
