package com.app.office.fc.xls.Reader.shared;

import com.app.office.constant.SchemeClrConstant;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.ss.model.baseModel.Workbook;
import java.io.InputStream;

public class ThemeColorReader {
    private static ThemeColorReader reader = new ThemeColorReader();

    public static ThemeColorReader instance() {
        return reader;
    }

    public void getThemeColor(PackagePart packagePart, Workbook workbook) throws Exception {
        SAXReader sAXReader = new SAXReader();
        InputStream inputStream = packagePart.getInputStream();
        Document read = sAXReader.read(inputStream);
        inputStream.close();
        Element element = read.getRootElement().element("themeElements").element("clrScheme");
        int colorIndex = getColorIndex(element.element(SchemeClrConstant.SCHEME_LT1), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_LT1, colorIndex);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_BG1, colorIndex);
        workbook.addThemeColorIndex(0, colorIndex);
        int colorIndex2 = getColorIndex(element.element(SchemeClrConstant.SCHEME_DK1), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_DK1, colorIndex2);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_TX1, colorIndex2);
        workbook.addThemeColorIndex(1, colorIndex2);
        int colorIndex3 = getColorIndex(element.element(SchemeClrConstant.SCHEME_LT2), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_LT2, colorIndex3);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_BG2, colorIndex3);
        workbook.addThemeColorIndex(2, colorIndex3);
        int colorIndex4 = getColorIndex(element.element(SchemeClrConstant.SCHEME_DK2), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_DK2, colorIndex4);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_TX2, colorIndex4);
        workbook.addThemeColorIndex(3, colorIndex4);
        int colorIndex5 = getColorIndex(element.element(SchemeClrConstant.SCHEME_ACCENT1), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_ACCENT1, colorIndex5);
        workbook.addThemeColorIndex(4, colorIndex5);
        int colorIndex6 = getColorIndex(element.element(SchemeClrConstant.SCHEME_ACCENT2), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_ACCENT2, colorIndex6);
        workbook.addThemeColorIndex(5, colorIndex6);
        int colorIndex7 = getColorIndex(element.element(SchemeClrConstant.SCHEME_ACCENT3), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_ACCENT3, colorIndex7);
        workbook.addThemeColorIndex(6, colorIndex7);
        int colorIndex8 = getColorIndex(element.element(SchemeClrConstant.SCHEME_ACCENT4), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_ACCENT4, colorIndex8);
        workbook.addThemeColorIndex(7, colorIndex8);
        int colorIndex9 = getColorIndex(element.element(SchemeClrConstant.SCHEME_ACCENT5), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_ACCENT5, colorIndex9);
        workbook.addThemeColorIndex(8, colorIndex9);
        int colorIndex10 = getColorIndex(element.element(SchemeClrConstant.SCHEME_ACCENT6), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_ACCENT6, colorIndex10);
        workbook.addThemeColorIndex(9, colorIndex10);
        int colorIndex11 = getColorIndex(element.element(SchemeClrConstant.SCHEME_HLINK), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_HLINK, colorIndex11);
        workbook.addThemeColorIndex(10, colorIndex11);
        int colorIndex12 = getColorIndex(element.element(SchemeClrConstant.SCHEME_FOLHLINK), workbook);
        workbook.addSchemeColorIndex(SchemeClrConstant.SCHEME_FOLHLINK, colorIndex12);
        workbook.addThemeColorIndex(11, colorIndex12);
    }

    private int getColorIndex(Element element, Workbook workbook) {
        int i;
        if (element.element("srgbClr") != null) {
            i = Integer.parseInt(element.element("srgbClr").attributeValue("val"), 16);
        } else {
            i = element.element("sysClr") != null ? Integer.parseInt(element.element("sysClr").attributeValue("lastClr"), 16) : -16777216;
        }
        return workbook.addColor(i | -16777216);
    }
}
