package com.app.office.fc;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.common.autoshape.AutoShapeDataKit;
import com.app.office.common.bg.Gradient;
import com.app.office.common.bg.LinearGradientShader;
import com.app.office.common.bg.RadialGradientShader;
import com.app.office.common.bg.TileShader;
import com.app.office.common.picture.Picture;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.pg.model.PGMaster;
import java.util.List;
import java.util.Map;

public class ShaderKit {
    public static Gradient readGradient(PGMaster pGMaster, Element element) {
        List elements = element.element("gsLst").elements("gs");
        if (!(elements == null || elements.size() == 0)) {
            int size = elements.size();
            int[] iArr = new int[size];
            float[] fArr = new float[size];
            for (int i = 0; i < size; i++) {
                Element element2 = (Element) elements.get(i);
                fArr[i] = ((float) Integer.parseInt(element2.attributeValue("pos"))) / 100000.0f;
                iArr[i] = ReaderKit.instance().getColor(pGMaster, element2);
            }
            Element element3 = element.element("lin");
            if (element3 != null) {
                return new LinearGradientShader((float) (Integer.parseInt(element3.attributeValue("ang")) / 60000), iArr, fArr);
            }
            Element element4 = element.element("path");
            if (element4 != null) {
                byte gradientType = getGradientType(element);
                int radialCenterType = getRadialCenterType(element4.element("fillToRect"));
                if (gradientType == 4 || gradientType == 5 || gradientType == 6) {
                    return new RadialGradientShader(radialCenterType, iArr, fArr);
                }
            }
        }
        return null;
    }

    public static Gradient readGradient(Map<String, Integer> map, Element element) {
        List elements = element.element("gsLst").elements("gs");
        if (elements == null || elements.size() == 0) {
            return null;
        }
        int size = elements.size();
        int[] iArr = new int[size];
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            Element element2 = (Element) elements.get(i);
            fArr[i] = ((float) Integer.parseInt(element2.attributeValue("pos"))) / 100000.0f;
            iArr[i] = AutoShapeDataKit.getColor(map, element2);
        }
        Element element3 = element.element("lin");
        if (element3 != null) {
            return new LinearGradientShader((float) (Integer.parseInt(element3.attributeValue("ang")) / 60000), iArr, fArr);
        }
        Element element4 = element.element("path");
        if (element4 == null) {
            return new LinearGradientShader(270.0f, iArr, fArr);
        }
        byte gradientType = getGradientType(element);
        int radialCenterType = getRadialCenterType(element4.element("fillToRect"));
        if (gradientType == 4 || gradientType == 5 || gradientType == 6) {
            return new RadialGradientShader(radialCenterType, iArr, fArr);
        }
        return null;
    }

    public static byte getGradientType(Element element) {
        Element element2;
        if (element.element("lin") == null && (element2 = element.element("path")) != null) {
            String attributeValue = element2.attributeValue("path");
            if ("circle".equalsIgnoreCase(attributeValue)) {
                return 4;
            }
            if ("rect".equalsIgnoreCase(attributeValue)) {
                return 5;
            }
            if ("shape".equalsIgnoreCase(attributeValue)) {
                return 6;
            }
        }
        return 7;
    }

    private static int getRadialCenterType(Element element) {
        if (element != null) {
            String attributeValue = element.attributeValue("l");
            String attributeValue2 = element.attributeValue("t");
            String attributeValue3 = element.attributeValue("r");
            String attributeValue4 = element.attributeValue(HtmlTags.B);
            if ("100000".equalsIgnoreCase(attributeValue3) && "100000".equalsIgnoreCase(attributeValue4)) {
                return 0;
            }
            if ("100000".equalsIgnoreCase(attributeValue) && "100000".equalsIgnoreCase(attributeValue4)) {
                return 1;
            }
            if ("100000".equalsIgnoreCase(attributeValue3) && "100000".equalsIgnoreCase(attributeValue2)) {
                return 2;
            }
            if ("100000".equalsIgnoreCase(attributeValue) && "100000".equalsIgnoreCase(attributeValue2)) {
                return 3;
            }
            if (!"50000".equalsIgnoreCase(attributeValue) || !"50000".equalsIgnoreCase(attributeValue2) || !"50000".equalsIgnoreCase(attributeValue3) || !"50000".equalsIgnoreCase(attributeValue4)) {
                return 0;
            }
            return 4;
        }
        return 0;
    }

    public static TileShader readTile(Picture picture, Element element) {
        return new TileShader(picture, getFlipType(element.attributeValue("flip")), ((float) Integer.parseInt(element.attributeValue("sx"))) / 100000.0f, ((float) Integer.parseInt(element.attributeValue("sy"))) / 100000.0f, Math.round((((float) Integer.parseInt(element.attributeValue("tx"))) * 96.0f) / 914400.0f), Math.round((((float) Integer.parseInt(element.attributeValue("ty"))) * 96.0f) / 914400.0f));
    }

    private static int getFlipType(String str) {
        if ("x".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("y".equalsIgnoreCase(str)) {
            return 2;
        }
        return "xy".equalsIgnoreCase(str) ? 3 : 0;
    }
}
