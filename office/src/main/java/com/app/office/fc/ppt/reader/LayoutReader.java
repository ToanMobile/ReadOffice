package com.app.office.fc.ppt.reader;

import androidx.core.view.PointerIconCompat;
import com.app.office.common.shape.GroupShape;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
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

public class LayoutReader {
    private static LayoutReader layoutReader = new LayoutReader();
    private int style = PointerIconCompat.TYPE_CONTEXT_MENU;

    public static LayoutReader instance() {
        return layoutReader;
    }

    public PGLayout getLayouts(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGModel pGModel, PGMaster pGMaster, PGStyle pGStyle) throws Exception {
        PGLayout pGLayout;
        PGLayout pGLayout2;
        Element element;
        String attributeValue;
        SAXReader sAXReader = new SAXReader();
        InputStream inputStream = packagePart.getInputStream();
        Element rootElement = sAXReader.read(inputStream).getRootElement();
        if (rootElement != null) {
            PGLayout pGLayout3 = new PGLayout();
            if (rootElement.attribute("showMasterSp") != null && (attributeValue = rootElement.attributeValue("showMasterSp")) != null && attributeValue.length() > 0 && Integer.valueOf(attributeValue).intValue() == 0) {
                pGLayout3.setAddShapes(false);
            }
            Element element2 = rootElement.element("cSld");
            if (element2 == null || (element = element2.element("spTree")) == null) {
                pGLayout2 = pGLayout3;
            } else {
                IControl iControl2 = iControl;
                processBackgroundAndFill(iControl2, zipPackage, packagePart, pGMaster, pGLayout3, element2);
                processTextStyle(iControl2, packagePart, pGMaster, pGLayout3, element);
                PGSlide pGSlide = new PGSlide();
                pGSlide.setSlideType(1);
                Iterator elementIterator = element.elementIterator();
                while (elementIterator.hasNext()) {
                    ShapeManage.instance().processShape(iControl, zipPackage, packagePart, (PGModel) null, pGMaster, pGLayout3, pGStyle, pGSlide, (byte) 1, (Element) elementIterator.next(), (GroupShape) null, 1.0f, 1.0f);
                    pGLayout3 = pGLayout3;
                    elementIterator = elementIterator;
                    pGSlide = pGSlide;
                }
                PGSlide pGSlide2 = pGSlide;
                pGLayout2 = pGLayout3;
                if (pGSlide2.getShapeCount() > 0) {
                    pGLayout2.setSlideMasterIndex(pGModel.appendSlideMaster(pGSlide2));
                }
            }
            pGLayout = pGLayout2;
        } else {
            pGLayout = null;
        }
        inputStream.close();
        return pGLayout;
    }

    private void processTextStyle(IControl iControl, PackagePart packagePart, PGMaster pGMaster, PGLayout pGLayout, Element element) {
        Iterator elementIterator = element.elementIterator();
        while (elementIterator.hasNext()) {
            Element element2 = (Element) elementIterator.next();
            String placeholderType = ReaderKit.instance().getPlaceholderType(element2);
            int placeholderIdx = ReaderKit.instance().getPlaceholderIdx(element2);
            Element element3 = element2.element("txBody");
            if (element3 != null) {
                Element element4 = element3.element("lstStyle");
                StyleReader.instance().setStyleIndex(this.style);
                if (!PGPlaceholderUtil.instance().isBody(placeholderType)) {
                    pGLayout.setStyleByType(placeholderType, StyleReader.instance().getStyles(iControl, pGMaster, element2, element4));
                } else if (placeholderIdx > 0) {
                    pGLayout.setStyleByIdx(placeholderIdx, StyleReader.instance().getStyles(iControl, pGMaster, element2, element4));
                }
                this.style = StyleReader.instance().getStyleIndex();
            }
        }
    }

    private void processBackgroundAndFill(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, PGLayout pGLayout, Element element) throws Exception {
        Element element2 = element.element("bg");
        if (element2 != null) {
            pGLayout.setBackgroundAndFill(BackgroundReader.instance().getBackground(iControl, zipPackage, packagePart, pGMaster, element2));
        }
    }

    public void dispose() {
        this.style = PointerIconCompat.TYPE_CONTEXT_MENU;
    }
}
