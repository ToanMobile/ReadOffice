package com.app.office.fc.dom4j.jaxb;

import com.app.office.fc.dom4j.Element;

public interface JAXBObjectModifier {
    Element modifyObject(Element element) throws Exception;
}
