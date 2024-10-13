package com.app.office.fc.ppt.reader;

import com.app.office.common.shape.GroupShape;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.ShapeManage;
import com.app.office.pg.model.PGLayout;
import com.app.office.pg.model.PGMaster;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGPlaceholderUtil;
import com.app.office.pg.model.PGSlide;
import com.app.office.pg.model.PGStyle;
import com.app.office.system.IControl;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class MasterReader {
    private static MasterReader masterReader = new MasterReader();
    private int styleIndex = 10;

    public static MasterReader instance() {
        return masterReader;
    }

    public PGMaster getMasterData(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGModel pGModel) throws Exception {
        PGMaster pGMaster;
        InputStream inputStream;
        PGMaster pGMaster2;
        IControl iControl2 = iControl;
        SAXReader sAXReader = new SAXReader();
        InputStream inputStream2 = packagePart.getInputStream();
        Element rootElement = sAXReader.read(inputStream2).getRootElement();
        if (rootElement != null) {
            PGMaster pGMaster3 = new PGMaster();
            processClrMap(pGMaster3, zipPackage, packagePart, rootElement);
            processStyle(iControl2, pGMaster3, rootElement);
            Element element = rootElement.element("cSld");
            if (element != null) {
                processBackgroundAndFill(iControl, pGMaster3, zipPackage, packagePart, element);
                Element element2 = element.element("spTree");
                if (element2 != null) {
                    processTextStyle(iControl2, pGMaster3, element2);
                    PGSlide pGSlide = new PGSlide();
                    pGSlide.setSlideType(0);
                    Iterator elementIterator = element2.elementIterator();
                    while (elementIterator.hasNext()) {
                        ShapeManage.instance().processShape(iControl, zipPackage, packagePart, (PGModel) null, pGMaster3, (PGLayout) null, (PGStyle) null, pGSlide, (byte) 0, (Element) elementIterator.next(), (GroupShape) null, 1.0f, 1.0f);
                        IControl iControl3 = iControl;
                        ZipPackage zipPackage2 = zipPackage;
                        PackagePart packagePart2 = packagePart;
                        pGMaster3 = pGMaster3;
                        inputStream2 = inputStream2;
                    }
                    pGMaster2 = pGMaster3;
                    inputStream = inputStream2;
                    if (pGSlide.getShapeCount() > 0) {
                        pGMaster2.setSlideMasterIndex(pGModel.appendSlideMaster(pGSlide));
                    }
                    pGMaster = pGMaster2;
                }
            }
            pGMaster2 = pGMaster3;
            inputStream = inputStream2;
            pGMaster = pGMaster2;
        } else {
            inputStream = inputStream2;
            pGMaster = null;
        }
        inputStream.close();
        return pGMaster;
    }

    private void processClrMap(PGMaster pGMaster, ZipPackage zipPackage, PackagePart packagePart, Element element) throws Exception {
        PackagePart part;
        PackageRelationship relationship = packagePart.getRelationshipsByType(PackageRelationshipTypes.THEME_PART).getRelationship(0);
        if (relationship != null && (part = zipPackage.getPart(relationship.getTargetURI())) != null) {
            Map<String, Integer> themeColorMap = ThemeReader.instance().getThemeColorMap(part);
            Element element2 = element.element("clrMap");
            if (element2 != null) {
                for (int i = 0; i < element2.attributeCount(); i++) {
                    String name = element2.attribute(i).getName();
                    String attributeValue = element2.attributeValue(name);
                    if (!name.equals(attributeValue)) {
                        pGMaster.addColor(attributeValue, themeColorMap.get(attributeValue).intValue());
                    }
                    pGMaster.addColor(name, themeColorMap.get(attributeValue).intValue());
                }
            }
        }
    }

    private void processBackgroundAndFill(IControl iControl, PGMaster pGMaster, ZipPackage zipPackage, PackagePart packagePart, Element element) throws Exception {
        Element element2 = element.element("bg");
        if (element2 != null) {
            pGMaster.setBackgroundAndFill(BackgroundReader.instance().getBackground(iControl, zipPackage, packagePart, pGMaster, element2));
        }
    }

    private void processTextStyle(IControl iControl, PGMaster pGMaster, Element element) throws Exception {
        Iterator elementIterator = element.elementIterator();
        while (elementIterator.hasNext()) {
            Element element2 = (Element) elementIterator.next();
            String checkTypeName = PGPlaceholderUtil.instance().checkTypeName(ReaderKit.instance().getPlaceholderType(element2));
            int placeholderIdx = ReaderKit.instance().getPlaceholderIdx(element2);
            Element element3 = element2.element("txBody");
            if (element3 != null) {
                Element element4 = element3.element("lstStyle");
                StyleReader.instance().setStyleIndex(this.styleIndex);
                if (!PGPlaceholderUtil.instance().isBody(checkTypeName)) {
                    pGMaster.addStyleByType(checkTypeName, StyleReader.instance().getStyles(iControl, pGMaster, element2, element4));
                } else if (placeholderIdx > 0) {
                    pGMaster.addStyleByIdx(placeholderIdx, StyleReader.instance().getStyles(iControl, pGMaster, element2, element4));
                }
                this.styleIndex = StyleReader.instance().getStyleIndex();
            }
        }
    }

    private void processStyle(IControl iControl, PGMaster pGMaster, Element element) {
        Element element2 = element.element("txStyles");
        if (element2 != null) {
            StyleReader.instance().setStyleIndex(this.styleIndex);
            pGMaster.setTitleStyle(StyleReader.instance().getStyles(iControl, pGMaster, (Element) null, element2.element("titleStyle")));
            pGMaster.setBodyStyle(StyleReader.instance().getStyles(iControl, pGMaster, (Element) null, element2.element("bodyStyle")));
            pGMaster.setDefaultStyle(StyleReader.instance().getStyles(iControl, pGMaster, (Element) null, element2.element("otherStyle")));
            this.styleIndex = StyleReader.instance().getStyleIndex();
        }
    }

    public void dispose() {
        this.styleIndex = 10;
    }
}
