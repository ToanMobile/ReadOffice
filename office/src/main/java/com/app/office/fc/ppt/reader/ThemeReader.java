package com.app.office.fc.ppt.reader;

import android.graphics.Color;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ThemeReader {
    private static ThemeReader themeReader = new ThemeReader();

    public static ThemeReader instance() {
        return themeReader;
    }

    public Map<String, Integer> getThemeColorMap(PackagePart packagePart) throws Exception {
        Element element;
        SAXReader sAXReader = new SAXReader();
        InputStream inputStream = packagePart.getInputStream();
        Element rootElement = sAXReader.read(inputStream).getRootElement();
        if (rootElement == null || (element = rootElement.element("themeElements")) == null) {
            inputStream.close();
            return null;
        }
        Element element2 = element.element("clrScheme");
        HashMap hashMap = new HashMap();
        Iterator elementIterator = element2.elementIterator();
        while (elementIterator.hasNext()) {
            Element element3 = (Element) elementIterator.next();
            String name = element3.getName();
            Element element4 = element3.element("srgbClr");
            Element element5 = element3.element("sysClr");
            if (element4 != null) {
                hashMap.put(name, Integer.valueOf(Color.parseColor("#" + element4.attributeValue("val"))));
            } else if (element5 != null) {
                hashMap.put(name, Integer.valueOf(Color.parseColor("#" + element5.attributeValue("lastClr"))));
            } else {
                hashMap.put(name, -1);
            }
        }
        return hashMap;
    }
}
