package com.app.office.fc.ppt.reader;

import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.app.office.common.autoshape.ArbitraryPolygonShapePath;
import com.app.office.common.autoshape.AutoShapeTypes;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.common.shape.ArbitraryPolygonShape;
import com.app.office.common.shape.Arrow;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.LineShape;
import com.app.office.common.shape.TextBox;
import com.app.office.fc.LineKit;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.attribute.ParaAttr;
import com.app.office.fc.ppt.attribute.SectionAttr;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.model.PGLayout;
import com.app.office.pg.model.PGMaster;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGPlaceholderUtil;
import com.app.office.pg.model.PGSlide;
import com.app.office.pg.model.PGStyle;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.system.IControl;
import java.util.List;

public class SmartArtReader {
    private static SmartArtReader reader = new SmartArtReader();

    public static SmartArtReader instance() {
        return reader;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0063, code lost:
        r2 = r23.getRelationship((r2 = (r2 = (r2 = r2.element("ext")).element("dataModelExt")).attributeValue("relId")));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.common.shape.SmartArt read(com.app.office.system.IControl r17, com.app.office.fc.openxml4j.opc.ZipPackage r18, com.app.office.pg.model.PGModel r19, com.app.office.pg.model.PGMaster r20, com.app.office.pg.model.PGLayout r21, com.app.office.pg.model.PGSlide r22, com.app.office.fc.openxml4j.opc.PackagePart r23, com.app.office.fc.openxml4j.opc.PackagePart r24) throws java.lang.Exception {
        /*
            r16 = this;
            r9 = r17
            r10 = r18
            r11 = r20
            com.app.office.fc.dom4j.io.SAXReader r6 = new com.app.office.fc.dom4j.io.SAXReader
            r6.<init>()
            java.io.InputStream r0 = r24.getInputStream()
            com.app.office.fc.dom4j.Document r1 = r6.read((java.io.InputStream) r0)
            r0.close()
            com.app.office.fc.dom4j.Element r7 = r1.getRootElement()
            com.app.office.fc.ppt.reader.BackgroundReader r0 = com.app.office.fc.ppt.reader.BackgroundReader.instance()
            java.lang.String r1 = "bg"
            com.app.office.fc.dom4j.Element r5 = r7.element((java.lang.String) r1)
            r1 = r17
            r2 = r18
            r3 = r24
            r4 = r20
            com.app.office.common.bg.BackgroundAndFill r0 = r0.processBackground(r1, r2, r3, r4, r5)
            java.lang.String r1 = "whole"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            java.lang.String r2 = "ln"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r2)
            r2 = r24
            com.app.office.common.borders.Line r1 = com.app.office.fc.LineKit.createLine((com.app.office.system.IControl) r9, (com.app.office.fc.openxml4j.opc.ZipPackage) r10, (com.app.office.fc.openxml4j.opc.PackagePart) r2, (com.app.office.pg.model.PGMaster) r11, (com.app.office.fc.dom4j.Element) r1)
            java.lang.String r2 = "extLst"
            com.app.office.fc.dom4j.Element r2 = r7.element((java.lang.String) r2)
            r3 = 0
            if (r2 == 0) goto L_0x0075
            java.lang.String r4 = "ext"
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r4)
            if (r2 == 0) goto L_0x0075
            java.lang.String r4 = "dataModelExt"
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r4)
            if (r2 == 0) goto L_0x0075
            java.lang.String r4 = "relId"
            java.lang.String r2 = r2.attributeValue((java.lang.String) r4)
            if (r2 == 0) goto L_0x0075
            r4 = r23
            com.app.office.fc.openxml4j.opc.PackageRelationship r2 = r4.getRelationship(r2)
            if (r2 == 0) goto L_0x0075
            java.net.URI r2 = r2.getTargetURI()
            com.app.office.fc.openxml4j.opc.PackagePart r2 = r10.getPart((java.net.URI) r2)
            r12 = r2
            goto L_0x0076
        L_0x0075:
            r12 = r3
        L_0x0076:
            if (r12 != 0) goto L_0x0079
            return r3
        L_0x0079:
            java.io.InputStream r2 = r12.getInputStream()
            com.app.office.fc.dom4j.Document r3 = r6.read((java.io.InputStream) r2)
            r2.close()
            com.app.office.common.shape.SmartArt r13 = new com.app.office.common.shape.SmartArt
            r13.<init>()
            r13.setBackgroundAndFill(r0)
            r13.setLine((com.app.office.common.borders.Line) r1)
            com.app.office.fc.dom4j.Element r0 = r3.getRootElement()
            java.lang.String r1 = "spTree"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            if (r0 == 0) goto L_0x00d8
            java.lang.String r1 = "sp"
            java.util.Iterator r14 = r0.elementIterator((java.lang.String) r1)
        L_0x00a1:
            boolean r0 = r14.hasNext()
            if (r0 == 0) goto L_0x00d8
            java.lang.Object r0 = r14.next()
            r15 = r0
            com.app.office.fc.dom4j.Element r15 = (com.app.office.fc.dom4j.Element) r15
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r12
            r4 = r19
            r5 = r20
            r6 = r21
            r7 = r22
            r8 = r15
            com.app.office.common.shape.IShape r0 = r0.processAutoShape(r1, r2, r3, r4, r5, r6, r7, r8)
            if (r0 == 0) goto L_0x00ca
            r0.setParent(r13)
            r13.appendShapes(r0)
        L_0x00ca:
            r0 = r16
            r1 = r21
            com.app.office.common.shape.IShape r2 = r0.getTextBoxData(r9, r11, r1, r15)
            if (r2 == 0) goto L_0x00a1
            r13.appendShapes(r2)
            goto L_0x00a1
        L_0x00d8:
            r0 = r16
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.reader.SmartArtReader.read(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.pg.model.PGModel, com.app.office.pg.model.PGMaster, com.app.office.pg.model.PGLayout, com.app.office.pg.model.PGSlide, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.fc.openxml4j.opc.PackagePart):com.app.office.common.shape.SmartArt");
    }

    private BackgroundAndFill getBackgrouond(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGModel pGModel, PGMaster pGMaster, PGLayout pGLayout, PGSlide pGSlide, Element element, int i) throws Exception {
        BackgroundAndFill backgroundAndFill;
        String attributeValue;
        if (element.attribute("useBgFill") == null || (attributeValue = element.attributeValue("useBgFill")) == null || attributeValue.length() <= 0 || Integer.parseInt(attributeValue) <= 0) {
            backgroundAndFill = null;
        } else {
            backgroundAndFill = pGSlide.getBackgroundAndFill();
            if (backgroundAndFill == null) {
                if (pGLayout != null) {
                    backgroundAndFill = pGLayout.getBackgroundAndFill();
                }
                if (backgroundAndFill == null && pGMaster != null) {
                    backgroundAndFill = pGMaster.getBackgroundAndFill();
                }
            }
        }
        Element element2 = element.element("spPr");
        String name = element.getName();
        if (backgroundAndFill != null || element2.element("noFill") != null || name.equals("cxnSp")) {
            return backgroundAndFill;
        }
        BackgroundAndFill processBackground = BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, element2);
        if (processBackground != null || i == 19 || i == 185 || i == 85 || i == 86 || i == 186 || i == 87 || i == 88 || i == 233) {
            return processBackground;
        }
        return BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, element.element(HtmlTags.STYLE));
    }

    private void processGrpRotation(IShape iShape, Element element) {
        ReaderKit.instance().processRotation(element, iShape);
    }

    private IShape processAutoShape(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGModel pGModel, PGMaster pGMaster, PGLayout pGLayout, PGSlide pGSlide, Element element) throws Exception {
        int i;
        int i2;
        Float[] fArr;
        int i3;
        byte arrowType;
        byte arrowType2;
        Float[] fArr2;
        List elements;
        String attributeValue;
        Element element2 = element;
        Element element3 = element2.element("spPr");
        if (element3 == null) {
            return null;
        }
        Rectangle shapeAnchor = ReaderKit.instance().getShapeAnchor(element3.element("xfrm"), 1.0f, 1.0f);
        String placeholderName = ReaderKit.instance().getPlaceholderName(element2);
        boolean z = false;
        if (element.getName().equals("cxnSp")) {
            i = 20;
        } else {
            i = (placeholderName.contains("Text Box") || placeholderName.contains("TextBox")) ? 1 : 0;
        }
        Element element4 = element3.element("prstGeom");
        if (element4 != null) {
            if (!(element4.attribute("prst") == null || (attributeValue = element4.attributeValue("prst")) == null || attributeValue.length() <= 0)) {
                i = AutoShapeTypes.instance().getAutoShapeType(attributeValue);
            }
            Element element5 = element4.element("avLst");
            if (element5 == null || (elements = element5.elements("gd")) == null || elements.size() <= 0) {
                fArr2 = null;
            } else {
                fArr2 = new Float[elements.size()];
                for (int i4 = 0; i4 < elements.size(); i4++) {
                    fArr2[i4] = Float.valueOf(Float.parseFloat(((Element) elements.get(i4)).attributeValue("fmla").substring(4)) / 100000.0f);
                }
            }
            i2 = i;
            fArr = fArr2;
        } else if (element3.element("custGeom") != null) {
            fArr = null;
            i2 = 233;
        } else {
            i2 = i;
            fArr = null;
        }
        IControl iControl2 = iControl;
        ZipPackage zipPackage2 = zipPackage;
        PackagePart packagePart2 = packagePart;
        Float[] fArr3 = fArr;
        int i5 = i2;
        BackgroundAndFill backgrouond = getBackgrouond(iControl2, zipPackage2, packagePart2, pGModel, pGMaster, pGLayout, pGSlide, element, i5);
        Line createShapeLine = LineKit.createShapeLine(iControl2, zipPackage2, packagePart2, pGMaster, element2);
        Element element6 = element3.element("ln");
        Element element7 = element2.element(HtmlTags.STYLE);
        if (element6 == null ? element7 == null || element7.element("lnRef") == null : element6.element("noFill") != null) {
            i3 = i5;
        } else {
            i3 = i5;
            z = true;
        }
        if (i3 == 20 || i3 == 32 || i3 == 34 || i3 == 38) {
            LineShape lineShape = new LineShape();
            lineShape.setShapeType(i3);
            lineShape.setBounds(shapeAnchor);
            lineShape.setAdjustData(fArr3);
            lineShape.setLine(createShapeLine);
            if (element6 != null) {
                Element element8 = element6.element("headEnd");
                if (!(element8 == null || element8.attribute(DublinCoreProperties.TYPE) == null || (arrowType2 = Arrow.getArrowType(element8.attributeValue((String) DublinCoreProperties.TYPE))) == 0)) {
                    lineShape.createStartArrow(arrowType2, Arrow.getArrowSize(element8.attributeValue("w")), Arrow.getArrowSize(element8.attributeValue("len")));
                }
                Element element9 = element6.element("tailEnd");
                if (!(element9 == null || element9.attribute(DublinCoreProperties.TYPE) == null || (arrowType = Arrow.getArrowType(element9.attributeValue((String) DublinCoreProperties.TYPE))) == 0)) {
                    lineShape.createStartArrow(arrowType, Arrow.getArrowSize(element9.attributeValue("w")), Arrow.getArrowSize(element9.attributeValue("len")));
                }
            }
            processGrpRotation(lineShape, element3);
            return lineShape;
        } else if (i3 == 233) {
            ArbitraryPolygonShape arbitraryPolygonShape = new ArbitraryPolygonShape();
            ArbitraryPolygonShapePath.processArbitraryPolygonShape(arbitraryPolygonShape, element, backgrouond, z, createShapeLine != null ? createShapeLine.getBackgroundAndFill() : null, element6, shapeAnchor);
            arbitraryPolygonShape.setShapeType(i3);
            processGrpRotation(arbitraryPolygonShape, element3);
            arbitraryPolygonShape.setLine(createShapeLine);
            return arbitraryPolygonShape;
        } else if (backgrouond == null && createShapeLine == null) {
            return null;
        } else {
            AutoShape autoShape = new AutoShape(i3);
            autoShape.setBounds(shapeAnchor);
            if (backgrouond != null) {
                autoShape.setBackgroundAndFill(backgrouond);
            }
            if (createShapeLine != null) {
                autoShape.setLine(createShapeLine);
            }
            autoShape.setAdjustData(fArr3);
            processGrpRotation(autoShape, element3);
            return autoShape;
        }
    }

    private IShape getTextBoxData(IControl iControl, PGMaster pGMaster, PGLayout pGLayout, Element element) {
        PGMaster pGMaster2 = pGMaster;
        PGLayout pGLayout2 = pGLayout;
        Element element2 = element;
        Element element3 = element2.element("txXfrm");
        Rectangle shapeAnchor = element3 != null ? ReaderKit.instance().getShapeAnchor(element3, 1.0f, 1.0f) : null;
        Element element4 = element2.element("txBody");
        if (element4 == null) {
            return null;
        }
        TextBox textBox = new TextBox();
        textBox.setBounds(shapeAnchor);
        SectionElement sectionElement = new SectionElement();
        sectionElement.setStartOffset(0);
        textBox.setElement(sectionElement);
        IAttributeSet attribute = sectionElement.getAttribute();
        AttrManage.instance().setPageWidth(attribute, (int) (((float) shapeAnchor.width) * 15.0f));
        AttrManage.instance().setPageHeight(attribute, (int) (((float) shapeAnchor.height) * 15.0f));
        SectionAttr.instance().setSectionAttribute(element4.element("bodyPr"), attribute, pGLayout2 != null ? pGLayout2.getSectionAttr((String) null, 0) : null, pGMaster2 != null ? pGMaster2.getSectionAttr((String) null, 0) : null, false);
        String str = "bodyPr";
        sectionElement.setEndOffset((long) ParaAttr.instance().processParagraph(iControl, pGMaster, pGLayout, (PGStyle) null, sectionElement, element2.element(HtmlTags.STYLE), element4, PGPlaceholderUtil.DIAGRAM, 0));
        if (textBox.getElement() != null && textBox.getElement().getText((IDocument) null) != null && textBox.getElement().getText((IDocument) null).length() > 0 && !"\n".equals(textBox.getElement().getText((IDocument) null))) {
            ReaderKit.instance().processRotation((IShape) textBox, element2.element("txXfrm"));
        }
        Element element5 = element4.element(str);
        if (element5 != null) {
            String attributeValue = element5.attributeValue("wrap");
            textBox.setWrapLine(attributeValue == null || "square".equalsIgnoreCase(attributeValue));
        }
        return textBox;
    }
}
