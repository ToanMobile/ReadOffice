package com.app.office.fc.xls.Reader.drawing;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.TextBox;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ppt.attribute.ParaAttr;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.fc.ppt.attribute.SectionAttr;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.system.IControl;
import java.util.List;

public class SmartArtReader {
    private static SmartArtReader reader = new SmartArtReader();
    private int offset;
    private Sheet sheet;

    public static SmartArtReader instance() {
        return reader;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0055, code lost:
        r2 = (r2 = (r2 = r2.element("ext")).element("dataModelExt")).attributeValue("relId");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.common.shape.SmartArt read(com.app.office.system.IControl r17, com.app.office.fc.openxml4j.opc.ZipPackage r18, com.app.office.fc.openxml4j.opc.PackagePart r19, com.app.office.fc.openxml4j.opc.PackagePart r20, java.util.Map<java.lang.String, java.lang.Integer> r21, com.app.office.ss.model.baseModel.Sheet r22) throws java.lang.Exception {
        /*
            r16 = this;
            r0 = r16
            r8 = r17
            r9 = r18
            r10 = r20
            r11 = r21
            r1 = r22
            r0.sheet = r1
            com.app.office.fc.dom4j.io.SAXReader r1 = new com.app.office.fc.dom4j.io.SAXReader
            r1.<init>()
            java.io.InputStream r2 = r20.getInputStream()
            com.app.office.fc.dom4j.Document r3 = r1.read((java.io.InputStream) r2)
            r2.close()
            com.app.office.fc.dom4j.Element r2 = r3.getRootElement()
            java.lang.String r3 = "bg"
            com.app.office.fc.dom4j.Element r3 = r2.element((java.lang.String) r3)
            com.app.office.common.bg.BackgroundAndFill r3 = com.app.office.common.autoshape.AutoShapeDataKit.processBackground(r8, r9, r10, r3, r11)
            java.lang.String r4 = "whole"
            com.app.office.fc.dom4j.Element r4 = r2.element((java.lang.String) r4)
            java.lang.String r5 = "ln"
            com.app.office.fc.dom4j.Element r4 = r4.element((java.lang.String) r5)
            com.app.office.common.borders.Line r4 = com.app.office.fc.LineKit.createLine((com.app.office.system.IControl) r8, (com.app.office.fc.openxml4j.opc.ZipPackage) r9, (com.app.office.fc.openxml4j.opc.PackagePart) r10, (com.app.office.fc.dom4j.Element) r4, (java.util.Map<java.lang.String, java.lang.Integer>) r11)
            java.lang.String r5 = "extLst"
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r5)
            r12 = 0
            if (r2 == 0) goto L_0x006c
            java.lang.String r5 = "ext"
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r5)
            if (r2 == 0) goto L_0x006c
            java.lang.String r5 = "dataModelExt"
            com.app.office.fc.dom4j.Element r2 = r2.element((java.lang.String) r5)
            if (r2 == 0) goto L_0x006c
            java.lang.String r5 = "relId"
            java.lang.String r2 = r2.attributeValue((java.lang.String) r5)
            if (r2 == 0) goto L_0x006c
            r5 = r19
            com.app.office.fc.openxml4j.opc.PackageRelationship r2 = r5.getRelationship(r2)
            java.net.URI r2 = r2.getTargetURI()
            com.app.office.fc.openxml4j.opc.PackagePart r2 = r9.getPart((java.net.URI) r2)
            goto L_0x006d
        L_0x006c:
            r2 = r12
        L_0x006d:
            if (r2 != 0) goto L_0x0070
            return r12
        L_0x0070:
            java.io.InputStream r2 = r2.getInputStream()
            com.app.office.fc.dom4j.Document r1 = r1.read((java.io.InputStream) r2)
            r2.close()
            com.app.office.common.shape.SmartArt r13 = new com.app.office.common.shape.SmartArt
            r13.<init>()
            r13.setBackgroundAndFill(r3)
            r13.setLine((com.app.office.common.borders.Line) r4)
            com.app.office.fc.dom4j.Element r1 = r1.getRootElement()
            java.lang.String r2 = "spTree"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r2)
            java.lang.String r2 = "sp"
            java.util.Iterator r14 = r1.elementIterator((java.lang.String) r2)
        L_0x0096:
            boolean r1 = r14.hasNext()
            if (r1 == 0) goto L_0x00db
            java.lang.Object r1 = r14.next()
            r15 = r1
            com.app.office.fc.dom4j.Element r15 = (com.app.office.fc.dom4j.Element) r15
            java.lang.String r1 = "spPr"
            com.app.office.fc.dom4j.Element r1 = r15.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x00bd
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            java.lang.String r3 = "xfrm"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r3)
            r3 = 1065353216(0x3f800000, float:1.0)
            com.app.office.java.awt.Rectangle r1 = r2.getShapeAnchor(r1, r3, r3)
            r5 = r1
            goto L_0x00be
        L_0x00bd:
            r5 = r12
        L_0x00be:
            r7 = 1
            r1 = r17
            r2 = r18
            r3 = r20
            r4 = r15
            r6 = r21
            com.app.office.common.shape.AbstractShape r1 = com.app.office.common.autoshape.AutoShapeDataKit.getAutoShape(r1, r2, r3, r4, r5, r6, r7)
            if (r1 == 0) goto L_0x00d1
            r13.appendShapes(r1)
        L_0x00d1:
            com.app.office.common.shape.TextBox r1 = r0.getTextBoxData(r8, r15)
            if (r1 == 0) goto L_0x0096
            r13.appendShapes(r1)
            goto L_0x0096
        L_0x00db:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.Reader.drawing.SmartArtReader.read(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.fc.openxml4j.opc.PackagePart, java.util.Map, com.app.office.ss.model.baseModel.Sheet):com.app.office.common.shape.SmartArt");
    }

    private TextBox getTextBoxData(IControl iControl, Element element) {
        Element element2 = element;
        Element element3 = element2.element("txXfrm");
        Rectangle shapeAnchor = element3 != null ? ReaderKit.instance().getShapeAnchor(element3, 1.0f, 1.0f) : null;
        Element element4 = element2.element("txBody");
        if (element4 != null) {
            TextBox textBox = new TextBox();
            SectionElement sectionElement = new SectionElement();
            sectionElement.setStartOffset(0);
            textBox.setElement(sectionElement);
            IAttributeSet attribute = sectionElement.getAttribute();
            AttrManage.instance().setPageWidth(attribute, Math.round(((float) shapeAnchor.width) * 15.0f));
            AttrManage.instance().setPageHeight(attribute, Math.round(((float) shapeAnchor.height) * 15.0f));
            AttrManage.instance().setPageMarginLeft(attribute, Math.round(30.0f));
            AttrManage.instance().setPageMarginRight(attribute, Math.round(30.0f));
            boolean z = false;
            AttrManage.instance().setPageMarginTop(attribute, 0);
            AttrManage.instance().setPageMarginBottom(attribute, 0);
            Element element5 = element3.element("bodyPr");
            SectionAttr.instance().setSectionAttribute(element5, attribute, (IAttributeSet) null, (IAttributeSet) null, false);
            if (element5 != null) {
                String attributeValue = element5.attributeValue("wrap");
                if (attributeValue == null || "square".equalsIgnoreCase(attributeValue)) {
                    z = true;
                }
                textBox.setWrapLine(z);
            }
            sectionElement.setEndOffset((long) processParagraph(iControl, sectionElement, element4));
            textBox.setBounds(shapeAnchor);
            if (textBox.getElement() != null && textBox.getElement().getText((IDocument) null) != null && textBox.getElement().getText((IDocument) null).length() > 0 && !"\n".equals(textBox.getElement().getText((IDocument) null))) {
                ReaderKit.instance().processRotation((IShape) textBox, element2.element("txXfrm"));
            }
            return textBox;
        }
        return null;
    }

    private int processParagraph(IControl iControl, SectionElement sectionElement, Element element) {
        this.offset = 0;
        for (Element element2 : element.elements("p")) {
            Element element3 = element2.element("pPr");
            ParagraphElement paragraphElement = new ParagraphElement();
            paragraphElement.setStartOffset((long) this.offset);
            ParaAttr.instance().setParaAttribute(iControl, element3, paragraphElement.getAttribute(), (IAttributeSet) null, -1, -1, 0, false, false);
            ParagraphElement processRun = processRun(iControl, sectionElement, paragraphElement, element2, (IAttributeSet) null);
            processRun.setEndOffset((long) this.offset);
            sectionElement.appendParagraph(processRun, 0);
        }
        return this.offset;
    }

    private ParagraphElement processRun(IControl iControl, SectionElement sectionElement, ParagraphElement paragraphElement, Element element, IAttributeSet iAttributeSet) {
        String text;
        int length;
        Element element2;
        ParagraphElement paragraphElement2 = paragraphElement;
        Element element3 = element;
        List<Element> elements = element3.elements("r");
        if (elements.size() == 0) {
            LeafElement leafElement = new LeafElement("\n");
            Element element4 = element3.element("pPr");
            if (!(element4 == null || (element2 = element4.element("rPr")) == null)) {
                RunAttr.instance().setRunAttribute(this.sheet, element2, leafElement.getAttribute(), iAttributeSet);
            }
            leafElement.setStartOffset((long) this.offset);
            int i = this.offset + 1;
            this.offset = i;
            leafElement.setEndOffset((long) i);
            paragraphElement2.appendLeaf(leafElement);
            return paragraphElement2;
        }
        IAttributeSet iAttributeSet2 = iAttributeSet;
        LeafElement leafElement2 = null;
        for (Element element5 : elements) {
            if (element5.getName().equalsIgnoreCase("r")) {
                Element element6 = element5.element("t");
                if (element6 != null && (length = text.length()) >= 0) {
                    leafElement2 = new LeafElement((text = element6.getText()));
                    RunAttr.instance().setRunAttribute(this.sheet, element5.element("rPr"), leafElement2.getAttribute(), iAttributeSet2);
                    leafElement2.setStartOffset((long) this.offset);
                    int i2 = this.offset + length;
                    this.offset = i2;
                    leafElement2.setEndOffset((long) i2);
                    paragraphElement2.appendLeaf(leafElement2);
                }
            } else if (element5.getName().equalsIgnoreCase(HtmlTags.BR)) {
                if (leafElement2 != null) {
                    leafElement2.setText(leafElement2.getText((IDocument) null) + "\n");
                    this.offset = this.offset + 1;
                }
                paragraphElement2.setEndOffset((long) this.offset);
                sectionElement.appendParagraph(paragraphElement2, 0);
                paragraphElement2 = new ParagraphElement();
                paragraphElement2.setStartOffset((long) this.offset);
                iAttributeSet2 = null;
                ParaAttr.instance().setParaAttribute(iControl, element3.element("pPr"), paragraphElement2.getAttribute(), (IAttributeSet) null, -1, -1, 0, false, false);
            }
            SectionElement sectionElement2 = sectionElement;
        }
        if (leafElement2 != null) {
            leafElement2.setText(leafElement2.getText((IDocument) null) + "\n");
            this.offset = this.offset + 1;
        }
        return paragraphElement2;
    }
}
