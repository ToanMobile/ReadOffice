package com.app.office.fc.ppt;

import androidx.core.view.ViewCompat;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.onesignal.outcomes.OSOutcomeConstants;
import com.app.office.common.autoshape.ArbitraryPolygonShapePath;
import com.app.office.common.autoshape.AutoShapeTypes;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.common.shape.AbstractShape;
import com.app.office.common.shape.ArbitraryPolygonShape;
import com.app.office.common.shape.Arrow;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.LineShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.SmartArt;
import com.app.office.fc.LineKit;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.fc.ppt.reader.BackgroundReader;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.model.PGLayout;
import com.app.office.pg.model.PGMaster;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGPlaceholderUtil;
import com.app.office.pg.model.PGSlide;
import com.app.office.pg.model.PGStyle;
import com.app.office.system.IControl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapeManage {
    private static ShapeManage kit = new ShapeManage();

    public static ShapeManage instance() {
        return kit;
    }

    public Integer processShape(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGModel pGModel, PGMaster pGMaster, PGLayout pGLayout, PGStyle pGStyle, PGSlide pGSlide, byte b, Element element, GroupShape groupShape, float f, float f2) throws Exception {
        Element element2;
        GroupShape groupShape2;
        PGSlide pGSlide2;
        Element element3;
        PGSlide pGSlide3 = pGSlide;
        Element element4 = element;
        GroupShape groupShape3 = groupShape;
        float f3 = f;
        float f4 = f2;
        float[] fArr = null;
        if (ReaderKit.instance().isHidden(element4)) {
            return null;
        }
        boolean contains = packagePart.getPartName().getName().contains("/ppt/slides/");
        char c = 0;
        boolean z = contains || (!contains && ReaderKit.instance().isUserDrawn(element4));
        RunAttr.instance().setSlide(z);
        String name = element.getName();
        if (name.equals("sp") || name.equals("cxnSp")) {
            return Integer.valueOf(processAutoShapeAndTextShape(iControl, zipPackage, packagePart, pGModel, pGMaster, pGLayout, pGStyle, pGSlide, b, element, groupShape, f, f2, z));
        }
        if (name.equals(PGPlaceholderUtil.PICTURE)) {
            if (z) {
                return Integer.valueOf(processPicture(iControl, zipPackage, packagePart, pGMaster, pGLayout, pGSlide, element, groupShape, f, f2));
            }
        } else if (name.equals("graphicFrame")) {
            if (z) {
                return Integer.valueOf(processGraphicFrame(iControl, zipPackage, packagePart, pGModel, pGMaster, pGLayout, pGSlide, element, groupShape, f, f2));
            }
        } else if (name.equals("grpSp")) {
            Element element5 = element4.element("nvGrpSpPr");
            int parseInt = (element5 == null || (element3 = element5.element("cNvPr")) == null) ? 0 : Integer.parseInt(element3.attributeValue(OSOutcomeConstants.OUTCOME_ID));
            Element element6 = element4.element("grpSpPr");
            if (element6 != null) {
                Rectangle processGrpSpRect = processGrpSpRect(groupShape3, ReaderKit.instance().getShapeAnchor(element6.element("xfrm"), f3, f4));
                fArr = ReaderKit.instance().getAnchorFitZoom(element6.element("xfrm"));
                Rectangle childShapeAnchor = ReaderKit.instance().getChildShapeAnchor(element6.element("xfrm"), fArr[0] * f3, fArr[1] * f4);
                GroupShape groupShape4 = new GroupShape();
                groupShape4.setOffPostion(processGrpSpRect.x - childShapeAnchor.x, processGrpSpRect.y - childShapeAnchor.y);
                groupShape4.setShapeID(parseInt);
                groupShape4.setBounds(processGrpSpRect);
                groupShape4.setParent(groupShape3);
                processGrpRotation(groupShape3, groupShape4, element6);
                groupShape2 = groupShape4;
            } else {
                groupShape2 = null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator elementIterator = element.elementIterator();
            while (elementIterator.hasNext()) {
                ArrayList arrayList2 = arrayList;
                GroupShape groupShape5 = groupShape2;
                int i = parseInt;
                GroupShape groupShape6 = groupShape3;
                Integer processShape = processShape(iControl, zipPackage, packagePart, pGModel, pGMaster, pGLayout, pGStyle, pGSlide, b, (Element) elementIterator.next(), groupShape5, fArr[c] * f3, fArr[1] * f4);
                if (processShape != null) {
                    arrayList2.add(processShape);
                }
                groupShape2 = groupShape5;
                f3 = f;
                f4 = f2;
                groupShape3 = groupShape6;
                arrayList = arrayList2;
                parseInt = i;
                c = 0;
                PGSlide pGSlide4 = pGSlide;
            }
            ArrayList arrayList3 = arrayList;
            GroupShape groupShape7 = groupShape2;
            int i2 = parseInt;
            GroupShape groupShape8 = groupShape3;
            if (groupShape8 == null) {
                pGSlide2 = pGSlide;
                pGSlide2.appendShapes(groupShape7);
            } else {
                GroupShape groupShape9 = groupShape8;
                pGSlide2 = pGSlide;
                groupShape9.appendShapes(groupShape7);
            }
            int i3 = i2;
            pGSlide2.addGroupShape(i3, arrayList3);
            return Integer.valueOf(i3);
        } else if (name.equals("AlternateContent") && (element2 = element4.element("Fallback")) != null) {
            Iterator elementIterator2 = element2.elementIterator();
            while (elementIterator2.hasNext()) {
                processShape(iControl, zipPackage, packagePart, pGModel, pGMaster, pGLayout, pGStyle, pGSlide, b, (Element) elementIterator2.next(), groupShape, f, f2);
                GroupShape groupShape10 = groupShape;
            }
        }
        RunAttr.instance().setSlide(false);
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0264  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int processAutoShapeAndTextShape(com.app.office.system.IControl r30, com.app.office.fc.openxml4j.opc.ZipPackage r31, com.app.office.fc.openxml4j.opc.PackagePart r32, com.app.office.pg.model.PGModel r33, com.app.office.pg.model.PGMaster r34, com.app.office.pg.model.PGLayout r35, com.app.office.pg.model.PGStyle r36, com.app.office.pg.model.PGSlide r37, byte r38, com.app.office.fc.dom4j.Element r39, com.app.office.common.shape.GroupShape r40, float r41, float r42, boolean r43) throws java.lang.Exception {
        /*
            r29 = this;
            r15 = r29
            r14 = r34
            r13 = r35
            r12 = r37
            r11 = r38
            r10 = r39
            r9 = r40
            java.lang.String r0 = "nvSpPr"
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r0)
            if (r0 != 0) goto L_0x001c
            java.lang.String r0 = "nvCxnSpPr"
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r0)
        L_0x001c:
            java.lang.String r1 = "cNvPr"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            java.lang.String r1 = "id"
            java.lang.String r0 = r0.attributeValue((java.lang.String) r1)
            int r8 = java.lang.Integer.parseInt(r0)
            com.app.office.fc.ppt.reader.ReaderKit r0 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            java.lang.String r0 = r0.getPlaceholderType(r10)
            com.app.office.fc.ppt.reader.ReaderKit r1 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            int r7 = r1.getPlaceholderIdx(r10)
            r6 = 1
            if (r11 != r6) goto L_0x0043
            r13.addShapeType(r7, r0)
            goto L_0x004d
        L_0x0043:
            if (r0 != 0) goto L_0x004d
            if (r14 == 0) goto L_0x004d
            if (r7 < 0) goto L_0x004d
            java.lang.String r0 = r13.getShapeType(r7)
        L_0x004d:
            r5 = r0
            r0 = -1
            com.app.office.pg.model.PGPlaceholderUtil r1 = com.app.office.pg.model.PGPlaceholderUtil.instance()
            boolean r1 = r1.isTitleOrBody(r5)
            r17 = 0
            if (r1 == 0) goto L_0x0067
            if (r11 != 0) goto L_0x0061
            r14.addTitleBodyID(r7, r7)
            goto L_0x0077
        L_0x0061:
            if (r11 != r6) goto L_0x0077
            r13.addTitleBodyID(r7, r8)
            goto L_0x0077
        L_0x0067:
            if (r11 == 0) goto L_0x006b
            if (r11 != r6) goto L_0x0077
        L_0x006b:
            com.app.office.fc.ppt.reader.ReaderKit r1 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            boolean r1 = r1.isUserDrawn(r10)
            if (r1 == 0) goto L_0x0077
            r4 = 0
            goto L_0x0078
        L_0x0077:
            r4 = -1
        L_0x0078:
            java.lang.String r3 = "spPr"
            com.app.office.fc.dom4j.Element r0 = r10.element((java.lang.String) r3)
            if (r0 == 0) goto L_0x0093
            com.app.office.fc.ppt.reader.ReaderKit r1 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            java.lang.String r2 = "xfrm"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r2)
            r2 = r41
            r6 = r42
            com.app.office.java.awt.Rectangle r0 = r1.getShapeAnchor(r0, r2, r6)
            goto L_0x0094
        L_0x0093:
            r0 = 0
        L_0x0094:
            if (r0 != 0) goto L_0x00a4
            if (r13 == 0) goto L_0x00a4
            com.app.office.java.awt.Rectangle r0 = r13.getAnchor(r5, r7)
            if (r0 != 0) goto L_0x00a4
            if (r14 == 0) goto L_0x00a4
            com.app.office.java.awt.Rectangle r0 = r14.getAnchor(r5, r7)
        L_0x00a4:
            if (r0 == 0) goto L_0x0264
            com.app.office.java.awt.Rectangle r6 = r15.processGrpSpRect(r9, r0)
            if (r43 != 0) goto L_0x00c9
            if (r43 != 0) goto L_0x00b9
            com.app.office.pg.model.PGPlaceholderUtil r0 = com.app.office.pg.model.PGPlaceholderUtil.instance()
            boolean r0 = r0.isHeaderFooter(r5)
            if (r0 != 0) goto L_0x00b9
            goto L_0x00c9
        L_0x00b9:
            r21 = r3
            r22 = r4
            r31 = r5
            r32 = r6
            r33 = r7
            r41 = r8
            r10 = 0
            r18 = 1
            goto L_0x0113
        L_0x00c9:
            boolean r19 = r15.isRect(r5, r7)
            if (r43 != 0) goto L_0x00dc
            com.app.office.pg.model.PGPlaceholderUtil r0 = com.app.office.pg.model.PGPlaceholderUtil.instance()
            boolean r0 = r0.isTitleOrBody(r5)
            if (r0 == 0) goto L_0x00dc
            r20 = 1
            goto L_0x00de
        L_0x00dc:
            r20 = 0
        L_0x00de:
            r0 = r29
            r1 = r30
            r2 = r31
            r21 = r3
            r3 = r32
            r22 = r4
            r4 = r33
            r31 = r5
            r5 = r34
            r32 = r6
            r18 = 1
            r6 = r35
            r33 = r7
            r7 = r37
            r41 = r8
            r8 = r39
            r9 = r41
            r10 = r33
            r11 = r32
            r12 = r19
            r13 = r40
            r14 = r38
            r15 = r31
            r16 = r20
            com.app.office.common.shape.AbstractShape r2 = r0.processAutoShape(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            r10 = r2
        L_0x0113:
            if (r10 == 0) goto L_0x0137
            r11 = r40
            if (r11 != 0) goto L_0x011f
            r12 = r37
            r12.appendShapes(r10)
            goto L_0x0124
        L_0x011f:
            r12 = r37
            r11.appendShapes(r10)
        L_0x0124:
            r0 = r22
            r10.setPlaceHolderID(r0)
            r13 = r39
            r14 = r21
            com.app.office.fc.dom4j.Element r1 = r13.element((java.lang.String) r14)
            r15 = r29
            r15.processGrpRotation(r11, r10, r1)
            goto L_0x0143
        L_0x0137:
            r15 = r29
            r12 = r37
            r13 = r39
            r11 = r40
            r14 = r21
            r0 = r22
        L_0x0143:
            java.lang.String r1 = "txBody"
            com.app.office.fc.dom4j.Element r9 = r13.element((java.lang.String) r1)
            if (r9 == 0) goto L_0x0261
            if (r43 == 0) goto L_0x0261
            com.app.office.common.shape.TextBox r8 = new com.app.office.common.shape.TextBox
            r8.<init>()
            r1 = r32
            r8.setBounds(r1)
            r8.setPlaceHolderID(r0)
            r7 = r41
            r8.setShapeID(r7)
            com.app.office.simpletext.model.SectionElement r6 = new com.app.office.simpletext.model.SectionElement
            r6.<init>()
            r2 = 0
            r6.setStartOffset(r2)
            r8.setElement(r6)
            com.app.office.simpletext.model.IAttributeSet r0 = r6.getAttribute()
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            int r3 = r1.width
            float r3 = (float) r3
            r4 = 1097859072(0x41700000, float:15.0)
            float r3 = r3 * r4
            int r3 = (int) r3
            r2.setPageWidth(r0, r3)
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r1.height
            float r1 = (float) r1
            float r1 = r1 * r4
            int r1 = (int) r1
            r2.setPageHeight(r0, r1)
            r3 = r35
            r4 = r31
            r5 = r33
            if (r3 == 0) goto L_0x019d
            com.app.office.simpletext.model.IAttributeSet r2 = r3.getSectionAttr(r4, r5)
            r24 = r2
            r2 = r34
            goto L_0x01a1
        L_0x019d:
            r2 = r34
            r24 = 0
        L_0x01a1:
            if (r2 == 0) goto L_0x01aa
            com.app.office.simpletext.model.IAttributeSet r1 = r2.getSectionAttr(r4, r5)
            r25 = r1
            goto L_0x01ac
        L_0x01aa:
            r25 = 0
        L_0x01ac:
            com.app.office.fc.ppt.attribute.SectionAttr r21 = com.app.office.fc.ppt.attribute.SectionAttr.instance()
            java.lang.String r1 = "bodyPr"
            com.app.office.fc.dom4j.Element r22 = r9.element((java.lang.String) r1)
            r26 = 0
            r23 = r0
            r21.setSectionAttribute(r22, r23, r24, r25, r26)
            com.app.office.fc.ppt.attribute.ParaAttr r0 = com.app.office.fc.ppt.attribute.ParaAttr.instance()
            r31 = r1
            java.lang.String r1 = "style"
            com.app.office.fc.dom4j.Element r16 = r13.element((java.lang.String) r1)
            r27 = r31
            r1 = r30
            r2 = r34
            r3 = r35
            r19 = r4
            r4 = r36
            r20 = r5
            r5 = r6
            r31 = r10
            r10 = r6
            r6 = r16
            r16 = r7
            r7 = r9
            r30 = r8
            r8 = r19
            r28 = r9
            r9 = r20
            int r0 = r0.processParagraph(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            long r0 = (long) r0
            r10.setEndOffset(r0)
            com.app.office.simpletext.model.SectionElement r0 = r30.getElement()
            if (r0 == 0) goto L_0x0232
            com.app.office.simpletext.model.SectionElement r0 = r30.getElement()
            r1 = 0
            java.lang.String r0 = r0.getText(r1)
            if (r0 == 0) goto L_0x0232
            com.app.office.simpletext.model.SectionElement r0 = r30.getElement()
            java.lang.String r0 = r0.getText(r1)
            int r0 = r0.length()
            if (r0 <= 0) goto L_0x0232
            com.app.office.simpletext.model.SectionElement r0 = r30.getElement()
            java.lang.String r0 = r0.getText(r1)
            java.lang.String r1 = "\n"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x0232
            com.app.office.fc.dom4j.Element r0 = r13.element((java.lang.String) r14)
            r1 = r30
            r15.processGrpRotation(r11, r1, r0)
            if (r11 != 0) goto L_0x022e
            r12.appendShapes(r1)
            goto L_0x023f
        L_0x022e:
            r11.appendShapes(r1)
            goto L_0x023f
        L_0x0232:
            r1 = r30
            if (r31 == 0) goto L_0x023f
            com.app.office.fc.dom4j.Element r0 = r13.element((java.lang.String) r14)
            r2 = r31
            r15.processGrpRotation(r11, r2, r0)
        L_0x023f:
            r2 = r27
            r0 = r28
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r2)
            if (r0 == 0) goto L_0x0266
            java.lang.String r2 = "wrap"
            java.lang.String r0 = r0.attributeValue((java.lang.String) r2)
            if (r0 == 0) goto L_0x025c
            java.lang.String r2 = "square"
            boolean r0 = r2.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x025a
            goto L_0x025c
        L_0x025a:
            r6 = 0
            goto L_0x025d
        L_0x025c:
            r6 = 1
        L_0x025d:
            r1.setWrapLine(r6)
            goto L_0x0266
        L_0x0261:
            r16 = r41
            goto L_0x0266
        L_0x0264:
            r16 = r8
        L_0x0266:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.ShapeManage.processAutoShapeAndTextShape(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.pg.model.PGModel, com.app.office.pg.model.PGMaster, com.app.office.pg.model.PGLayout, com.app.office.pg.model.PGStyle, com.app.office.pg.model.PGSlide, byte, com.app.office.fc.dom4j.Element, com.app.office.common.shape.GroupShape, float, float, boolean):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0012, code lost:
        r1 = r1.element("cNvPr");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int processPicture(com.app.office.system.IControl r18, com.app.office.fc.openxml4j.opc.ZipPackage r19, com.app.office.fc.openxml4j.opc.PackagePart r20, com.app.office.pg.model.PGMaster r21, com.app.office.pg.model.PGLayout r22, com.app.office.pg.model.PGSlide r23, com.app.office.fc.dom4j.Element r24, com.app.office.common.shape.GroupShape r25, float r26, float r27) throws java.lang.Exception {
        /*
            r17 = this;
            r6 = r19
            r7 = r20
            r8 = r21
            r0 = r22
            r9 = r24
            java.lang.String r1 = "nvPicPr"
            com.app.office.fc.dom4j.Element r1 = r9.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x0026
            java.lang.String r2 = "cNvPr"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r2)
            if (r1 == 0) goto L_0x0026
            java.lang.String r2 = "id"
            java.lang.String r1 = r1.attributeValue((java.lang.String) r2)
            int r1 = java.lang.Integer.parseInt(r1)
            r11 = r1
            goto L_0x0028
        L_0x0026:
            r1 = 0
            r11 = 0
        L_0x0028:
            java.lang.String r1 = "blipFill"
            com.app.office.fc.dom4j.Element r2 = r9.element((java.lang.String) r1)
            if (r2 != 0) goto L_0x0044
            java.lang.String r3 = "AlternateContent"
            com.app.office.fc.dom4j.Element r3 = r9.element((java.lang.String) r3)
            if (r3 == 0) goto L_0x0044
            java.lang.String r4 = "Fallback"
            com.app.office.fc.dom4j.Element r3 = r3.element((java.lang.String) r4)
            if (r3 == 0) goto L_0x0044
            com.app.office.fc.dom4j.Element r2 = r3.element((java.lang.String) r1)
        L_0x0044:
            r10 = r2
            if (r10 == 0) goto L_0x00e6
            java.lang.String r1 = "blip"
            com.app.office.fc.dom4j.Element r1 = r10.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x00e6
            java.lang.String r2 = "embed"
            com.app.office.fc.dom4j.Attribute r3 = r1.attribute((java.lang.String) r2)
            if (r3 == 0) goto L_0x00e6
            java.lang.String r1 = r1.attributeValue((java.lang.String) r2)
            if (r1 == 0) goto L_0x00e6
            java.lang.String r12 = "spPr"
            com.app.office.fc.dom4j.Element r5 = r9.element((java.lang.String) r12)
            if (r5 == 0) goto L_0x00e6
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            java.lang.String r3 = "xfrm"
            com.app.office.fc.dom4j.Element r3 = r5.element((java.lang.String) r3)
            r4 = r26
            r13 = r27
            com.app.office.java.awt.Rectangle r2 = r2.getShapeAnchor(r3, r4, r13)
            if (r2 != 0) goto L_0x0099
            if (r0 == 0) goto L_0x0099
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            java.lang.String r2 = r2.getPlaceholderType(r9)
            com.app.office.fc.ppt.reader.ReaderKit r3 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            int r3 = r3.getPlaceholderIdx(r9)
            com.app.office.java.awt.Rectangle r0 = r0.getAnchor(r2, r3)
            if (r0 != 0) goto L_0x0098
            if (r8 == 0) goto L_0x0098
            com.app.office.java.awt.Rectangle r2 = r8.getAnchor(r2, r3)
            goto L_0x0099
        L_0x0098:
            r2 = r0
        L_0x0099:
            if (r2 == 0) goto L_0x00e6
            r13 = r17
            r14 = r25
            com.app.office.java.awt.Rectangle r15 = r13.processGrpSpRect(r14, r2)
            com.app.office.fc.openxml4j.opc.PackageRelationship r16 = r7.getRelationship(r1)
            if (r16 == 0) goto L_0x00e8
            com.app.office.fc.ppt.reader.BackgroundReader r0 = com.app.office.fc.ppt.reader.BackgroundReader.instance()
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            com.app.office.common.bg.BackgroundAndFill r0 = r0.processBackground(r1, r2, r3, r4, r5)
            com.app.office.common.borders.Line r8 = com.app.office.fc.LineKit.createShapeLine((com.app.office.system.IControl) r1, (com.app.office.fc.openxml4j.opc.ZipPackage) r6, (com.app.office.fc.openxml4j.opc.PackagePart) r7, (com.app.office.pg.model.PGMaster) r8, (com.app.office.fc.dom4j.Element) r9)
            java.net.URI r2 = r16.getTargetURI()
            com.app.office.fc.openxml4j.opc.PackagePart r4 = r6.getPart((java.net.URI) r2)
            com.app.office.fc.dom4j.Element r9 = r9.element((java.lang.String) r12)
            com.app.office.common.pictureefftect.PictureEffectInfo r10 = com.app.office.common.pictureefftect.PictureEffectInfoFactory.getPictureEffectInfor((com.app.office.fc.dom4j.Element) r10)
            r2 = r17
            r3 = r18
            r5 = r23
            r6 = r11
            r7 = r15
            r1 = r8
            r8 = r9
            r9 = r25
            com.app.office.common.shape.PictureShape r2 = r2.addPicture(r3, r4, r5, r6, r7, r8, r9, r10)
            if (r2 == 0) goto L_0x00e8
            r2.setBackgroundAndFill(r0)
            r2.setLine((com.app.office.common.borders.Line) r1)
            goto L_0x00e8
        L_0x00e6:
            r13 = r17
        L_0x00e8:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.ShapeManage.processPicture(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.pg.model.PGMaster, com.app.office.pg.model.PGLayout, com.app.office.pg.model.PGSlide, com.app.office.fc.dom4j.Element, com.app.office.common.shape.GroupShape, float, float):int");
    }

    public PictureShape addPicture(IControl iControl, PackagePart packagePart, PGSlide pGSlide, int i, Rectangle rectangle, Element element, GroupShape groupShape, PictureEffectInfo pictureEffectInfo) throws Exception {
        if (packagePart == null) {
            return null;
        }
        PictureShape pictureShape = new PictureShape();
        pictureShape.setPictureIndex(iControl.getSysKit().getPictureManage().addPicture(packagePart));
        pictureShape.setBounds(rectangle);
        processGrpRotation(groupShape, pictureShape, element);
        pictureShape.setShapeID(i);
        pictureShape.setPictureEffectInfor(pictureEffectInfo);
        if (groupShape == null) {
            pGSlide.appendShapes(pictureShape);
            return pictureShape;
        }
        groupShape.appendShapes(pictureShape);
        return pictureShape;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0018, code lost:
        r1 = r1.element("cNvPr");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int processGraphicFrame(com.app.office.system.IControl r18, com.app.office.fc.openxml4j.opc.ZipPackage r19, com.app.office.fc.openxml4j.opc.PackagePart r20, com.app.office.pg.model.PGModel r21, com.app.office.pg.model.PGMaster r22, com.app.office.pg.model.PGLayout r23, com.app.office.pg.model.PGSlide r24, com.app.office.fc.dom4j.Element r25, com.app.office.common.shape.GroupShape r26, float r27, float r28) throws java.lang.Exception {
        /*
            r17 = this;
            r11 = r17
            r2 = r19
            r3 = r20
            r5 = r22
            r6 = r23
            r8 = r24
            r0 = r25
            java.lang.String r1 = "nvGraphicFramePr"
            com.app.office.fc.dom4j.Element r1 = r0.element((java.lang.String) r1)
            java.lang.String r7 = "id"
            if (r1 == 0) goto L_0x002a
            java.lang.String r9 = "cNvPr"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r9)
            if (r1 == 0) goto L_0x002a
            java.lang.String r1 = r1.attributeValue((java.lang.String) r7)
            int r1 = java.lang.Integer.parseInt(r1)
            r12 = r1
            goto L_0x002b
        L_0x002a:
            r12 = 0
        L_0x002b:
            java.lang.String r1 = "xfrm"
            com.app.office.fc.dom4j.Element r1 = r0.element((java.lang.String) r1)
            com.app.office.fc.ppt.reader.ReaderKit r9 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            r10 = r27
            r13 = r28
            com.app.office.java.awt.Rectangle r1 = r9.getShapeAnchor(r1, r10, r13)
            if (r1 != 0) goto L_0x005f
            if (r6 == 0) goto L_0x005f
            com.app.office.fc.ppt.reader.ReaderKit r1 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            java.lang.String r1 = r1.getPlaceholderType(r0)
            com.app.office.fc.ppt.reader.ReaderKit r9 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            int r9 = r9.getPlaceholderIdx(r0)
            com.app.office.java.awt.Rectangle r14 = r6.getAnchor(r1, r9)
            if (r14 != 0) goto L_0x005e
            if (r5 == 0) goto L_0x005e
            com.app.office.java.awt.Rectangle r1 = r5.getAnchor(r1, r9)
            goto L_0x005f
        L_0x005e:
            r1 = r14
        L_0x005f:
            if (r1 == 0) goto L_0x0189
            r9 = r26
            com.app.office.java.awt.Rectangle r14 = r11.processGrpSpRect(r9, r1)
            java.lang.String r1 = "graphic"
            com.app.office.fc.dom4j.Element r1 = r0.element((java.lang.String) r1)
            if (r1 == 0) goto L_0x0189
            java.lang.String r15 = "graphicData"
            com.app.office.fc.dom4j.Element r1 = r1.element((java.lang.String) r15)
            if (r1 == 0) goto L_0x0189
            java.lang.String r15 = "uri"
            com.app.office.fc.dom4j.Attribute r16 = r1.attribute((java.lang.String) r15)
            if (r16 == 0) goto L_0x0189
            java.lang.String r15 = r1.attributeValue((java.lang.String) r15)
            java.lang.String r4 = "http://schemas.openxmlformats.org/presentationml/2006/ole"
            boolean r4 = r15.equals(r4)
            if (r4 == 0) goto L_0x00fa
            java.lang.String r4 = "oleObj"
            com.app.office.fc.dom4j.Element r7 = r1.element((java.lang.String) r4)
            if (r7 != 0) goto L_0x00ca
            java.lang.String r0 = "AlternateContent"
            com.app.office.fc.dom4j.Element r0 = r1.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x0189
            java.lang.String r1 = "Fallback"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            if (r0 == 0) goto L_0x0189
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r4)
            if (r0 == 0) goto L_0x0189
            java.lang.String r1 = "pic"
            com.app.office.fc.dom4j.Element r7 = r0.element((java.lang.String) r1)
            if (r7 == 0) goto L_0x0189
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r22
            r5 = r23
            r6 = r24
            r8 = r26
            r9 = r27
            r10 = r28
            r0.processPicture(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            goto L_0x0189
        L_0x00ca:
            java.lang.String r1 = "spid"
            com.app.office.fc.dom4j.Attribute r4 = r7.attribute((java.lang.String) r1)
            if (r4 == 0) goto L_0x0189
            java.lang.String r1 = r7.attributeValue((java.lang.String) r1)
            com.app.office.fc.ppt.reader.PictureReader r4 = com.app.office.fc.ppt.reader.PictureReader.instance()
            r5 = 0
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            com.app.office.fc.openxml4j.opc.PackagePart r2 = r4.getOLEPart(r2, r3, r1, r5)
            java.lang.String r1 = "spPr"
            com.app.office.fc.dom4j.Element r6 = r0.element((java.lang.String) r1)
            r10 = 0
            r0 = r17
            r1 = r18
            r3 = r24
            r4 = r12
            r5 = r14
            r7 = r26
            r8 = r10
            r0.addPicture(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x0189
        L_0x00fa:
            java.lang.String r0 = "http://schemas.openxmlformats.org/drawingml/2006/chart"
            boolean r0 = r15.equals(r0)
            if (r0 == 0) goto L_0x0147
            java.lang.String r0 = "chart"
            com.app.office.fc.dom4j.Element r0 = r1.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x0189
            com.app.office.fc.dom4j.Attribute r1 = r0.attribute((java.lang.String) r7)
            if (r1 == 0) goto L_0x0189
            java.lang.String r0 = r0.attributeValue((java.lang.String) r7)
            com.app.office.fc.openxml4j.opc.PackageRelationship r0 = r3.getRelationship(r0)
            if (r0 == 0) goto L_0x0189
            java.net.URI r0 = r0.getTargetURI()
            com.app.office.fc.openxml4j.opc.PackagePart r3 = r2.getPart((java.net.URI) r0)
            com.app.office.fc.xls.Reader.drawing.ChartReader r0 = com.app.office.fc.xls.Reader.drawing.ChartReader.instance()
            java.util.Map r4 = r22.getSchemeColor()
            r5 = 2
            r1 = r18
            r2 = r19
            com.app.office.thirdpart.achartengine.chart.AbstractChart r0 = r0.read(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x0189
            com.app.office.common.shape.AChart r1 = new com.app.office.common.shape.AChart
            r1.<init>()
            r1.setAChart(r0)
            r1.setBounds(r14)
            r1.setShapeID(r12)
            r8.appendShapes(r1)
            goto L_0x0189
        L_0x0147:
            java.lang.String r0 = "http://schemas.openxmlformats.org/drawingml/2006/table"
            boolean r0 = r15.equals(r0)
            if (r0 == 0) goto L_0x017e
            java.lang.String r0 = "tbl"
            com.app.office.fc.dom4j.Element r6 = r1.element((java.lang.String) r0)
            if (r6 == 0) goto L_0x0189
            java.lang.String r0 = "tblPr"
            com.app.office.fc.dom4j.Element r0 = r6.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x0189
            com.app.office.fc.ppt.reader.TableReader r0 = com.app.office.fc.ppt.reader.TableReader.instance()
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r7 = r14
            com.app.office.common.shape.TableShape r0 = r0.getTable(r1, r2, r3, r4, r5, r6, r7)
            if (r0 == 0) goto L_0x0189
            r0.setBounds(r14)
            r0.setShapeID(r12)
            r8.appendShapes(r0)
            goto L_0x0189
        L_0x017e:
            java.lang.String r0 = "http://schemas.openxmlformats.org/drawingml/2006/diagram"
            boolean r0 = r15.equals(r0)
            if (r0 == 0) goto L_0x0189
            r11.processSmartArt(r8, r1, r14)
        L_0x0189:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.ShapeManage.processGraphicFrame(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.pg.model.PGModel, com.app.office.pg.model.PGMaster, com.app.office.pg.model.PGLayout, com.app.office.pg.model.PGSlide, com.app.office.fc.dom4j.Element, com.app.office.common.shape.GroupShape, float, float):int");
    }

    private Rectangle processGrpSpRect(GroupShape groupShape, Rectangle rectangle) {
        if (groupShape != null) {
            rectangle.x += groupShape.getOffX();
            rectangle.y += groupShape.getOffY();
        }
        return rectangle;
    }

    private void processGrpRotation(IShape iShape, IShape iShape2, Element element) {
        ReaderKit.instance().processRotation(element, iShape2);
    }

    private boolean isRect(String str, int i) {
        if ((str == null || (!str.contains("Title") && !str.contains("title") && !str.contains(PGPlaceholderUtil.CTRTITLE) && !str.contains(PGPlaceholderUtil.SUBTITLE) && !str.contains("body") && !str.contains("body") && !str.contains(PGPlaceholderUtil.HALF) && !str.contains(PGPlaceholderUtil.DT) && !str.contains(PGPlaceholderUtil.FTR) && !str.contains(PGPlaceholderUtil.SLDNUM))) && i <= 0) {
            return false;
        }
        return true;
    }

    private BackgroundAndFill getBackgrouond(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGModel pGModel, PGMaster pGMaster, PGLayout pGLayout, PGSlide pGSlide, Element element, int i, byte b, String str, int i2) throws Exception {
        Integer titleBodyID;
        BackgroundAndFill processBackground;
        String attributeValue;
        PGModel pGModel2 = pGModel;
        PGMaster pGMaster2 = pGMaster;
        PGLayout pGLayout2 = pGLayout;
        Element element2 = element;
        int i3 = i;
        byte b2 = b;
        int i4 = i2;
        if (element2.attribute("useBgFill") == null || (attributeValue = element2.attributeValue("useBgFill")) == null || attributeValue.length() <= 0 || !"1".equals(attributeValue)) {
            Element element3 = element2.element("spPr");
            String name = element.getName();
            BackgroundAndFill backgroundAndFill = null;
            if (element3.element("noFill") == null && !name.equals("cxnSp") && ((processBackground = BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, element3)) != null || i4 == 19 || i4 == 185 || i4 == 85 || i4 == 86 || i4 == 186 || i4 == 87 || i4 == 88 || i4 == 233 || (processBackground = BackgroundReader.instance().processBackground(iControl, zipPackage, packagePart, pGMaster, element2.element((String) HtmlTags.STYLE))) == null || processBackground.getFillType() != 0 || (processBackground.getForegroundColor() & ViewCompat.MEASURED_SIZE_MASK) != 0)) {
                backgroundAndFill = processBackground;
            }
            if (backgroundAndFill == null && b2 == 2 && PGPlaceholderUtil.instance().isTitleOrBody(str) && pGLayout2 != null && pGLayout.getSlideMasterIndex() >= 0 && i3 >= 0) {
                PGSlide slideMaster = pGModel2.getSlideMaster(pGLayout.getSlideMasterIndex());
                Integer titleBodyID2 = pGLayout2.getTitleBodyID(i3);
                if (titleBodyID2 != null) {
                    IShape[] shapes = slideMaster.getShapes();
                    int i5 = 0;
                    while (true) {
                        if (i5 < shapes.length) {
                            if (titleBodyID2.intValue() == shapes[i5].getShapeID() && (shapes[i5] instanceof AutoShape)) {
                                backgroundAndFill = ((AutoShape) shapes[i5]).getBackgroundAndFill();
                                break;
                            }
                            i5++;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (backgroundAndFill != null || b2 != 2 || pGMaster2 == null || pGMaster.getSlideMasterIndex() < 0 || i3 < 0) {
                return backgroundAndFill;
            }
            IShape[] shapes2 = pGModel2.getSlideMaster(pGMaster.getSlideMasterIndex()).getShapes();
            if (pGMaster2.getTitleBodyID(i3) == null || (titleBodyID = pGMaster2.getTitleBodyID(i3)) == null) {
                return backgroundAndFill;
            }
            for (int i6 = 0; i6 < shapes2.length; i6++) {
                if (titleBodyID.intValue() == shapes2[i6].getShapeID() && (shapes2[i6] instanceof AutoShape)) {
                    return ((AutoShape) shapes2[i6]).getBackgroundAndFill();
                }
            }
            return backgroundAndFill;
        }
        BackgroundAndFill backgroundAndFill2 = pGSlide.getBackgroundAndFill();
        if (backgroundAndFill2 == null) {
            if (pGLayout2 != null) {
                backgroundAndFill2 = pGLayout.getBackgroundAndFill();
            }
            if (backgroundAndFill2 == null && pGMaster2 != null) {
                backgroundAndFill2 = pGMaster.getBackgroundAndFill();
            }
        }
        if (backgroundAndFill2 != null) {
            backgroundAndFill2.setSlideBackgroundFill(true);
        }
        return backgroundAndFill2;
    }

    public AbstractShape processAutoShape(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGModel pGModel, PGMaster pGMaster, PGLayout pGLayout, PGSlide pGSlide, Element element, int i, int i2, Rectangle rectangle, boolean z, GroupShape groupShape, byte b, String str, boolean z2) throws Exception {
        int i3;
        int i4;
        Float[] fArr;
        byte arrowType;
        byte arrowType2;
        Float[] fArr2;
        String attributeValue;
        Element element2 = element;
        int i5 = i;
        Rectangle rectangle2 = rectangle;
        boolean z3 = z2;
        Element element3 = element2.element("spPr");
        BackgroundAndFill backgroundAndFill = null;
        if (element3 != null) {
            String placeholderName = ReaderKit.instance().getPlaceholderName(element2);
            boolean z4 = false;
            if (element.getName().equals("cxnSp")) {
                i3 = 32;
            } else {
                i3 = (z || placeholderName.contains("Text Box") || placeholderName.contains("TextBox")) ? 1 : 0;
            }
            Element element4 = element3.element("prstGeom");
            if (element4 != null) {
                if (!(element4.attribute("prst") == null || (attributeValue = element4.attributeValue("prst")) == null || attributeValue.length() <= 0)) {
                    i3 = AutoShapeTypes.instance().getAutoShapeType(attributeValue);
                }
                Element element5 = element4.element("avLst");
                if (element5 != null) {
                    List elements = element5.elements("gd");
                    if (elements.size() > 0) {
                        fArr2 = new Float[elements.size()];
                        for (int i6 = 0; i6 < elements.size(); i6++) {
                            fArr2[i6] = Float.valueOf(Float.parseFloat(((Element) elements.get(i6)).attributeValue("fmla").substring(4)) / 100000.0f);
                        }
                        i4 = i3;
                        fArr = fArr2;
                    }
                }
                fArr2 = null;
                i4 = i3;
                fArr = fArr2;
            } else if (element3.element("custGeom") != null) {
                fArr = null;
                i4 = 233;
            } else if (z) {
                fArr = null;
                i4 = 1;
            } else {
                i4 = i3;
                fArr = null;
            }
            IControl iControl2 = iControl;
            ZipPackage zipPackage2 = zipPackage;
            PackagePart packagePart2 = packagePart;
            Float[] fArr3 = fArr;
            int i7 = i4;
            Element element6 = element3;
            BackgroundAndFill backgrouond = getBackgrouond(iControl2, zipPackage2, packagePart2, pGModel, pGMaster, pGLayout, pGSlide, element, i2, b, str, i7);
            Line createShapeLine = LineKit.createShapeLine(iControl2, zipPackage2, packagePart2, pGMaster, element2);
            Element element7 = element6.element("ln");
            Element element8 = element2.element(HtmlTags.STYLE);
            if (element7 == null ? !(element8 == null || element8.element("lnRef") == null) : element7.element("noFill") == null) {
                z4 = true;
            }
            int i8 = i7;
            if (i8 == 20 || i8 == 32 || i8 == 33 || i8 == 34 || i8 == 37 || i8 == 38 || i8 == 39 || i8 == 40) {
                Rectangle rectangle3 = rectangle;
                boolean z5 = z2;
                Float[] fArr4 = fArr3;
                if (!z4) {
                    return null;
                }
                LineShape lineShape = new LineShape();
                lineShape.setShapeType(i8);
                lineShape.setBounds(rectangle3);
                lineShape.setShapeID(i5);
                lineShape.setHidden(z5);
                lineShape.setAdjustData(fArr4);
                lineShape.setLine(createShapeLine);
                if (element7 != null) {
                    Element element9 = element7.element("headEnd");
                    if (!(element9 == null || element9.attribute(DublinCoreProperties.TYPE) == null || (arrowType2 = Arrow.getArrowType(element9.attributeValue((String) DublinCoreProperties.TYPE))) == 0)) {
                        lineShape.createStartArrow(arrowType2, Arrow.getArrowSize(element9.attributeValue("w")), Arrow.getArrowSize(element9.attributeValue("len")));
                    }
                    Element element10 = element7.element("tailEnd");
                    if (!(element10 == null || element10.attribute(DublinCoreProperties.TYPE) == null || (arrowType = Arrow.getArrowType(element10.attributeValue((String) DublinCoreProperties.TYPE))) == 0)) {
                        lineShape.createEndArrow(arrowType, Arrow.getArrowSize(element10.attributeValue("w")), Arrow.getArrowSize(element10.attributeValue("len")));
                    }
                }
                return lineShape;
            } else if (i8 == 233) {
                ArbitraryPolygonShape arbitraryPolygonShape = new ArbitraryPolygonShape();
                if (createShapeLine != null) {
                    backgroundAndFill = createShapeLine.getBackgroundAndFill();
                }
                ArbitraryPolygonShapePath.processArbitraryPolygonShape(arbitraryPolygonShape, element, backgrouond, z4, backgroundAndFill, element7, rectangle);
                arbitraryPolygonShape.setShapeType(i8);
                arbitraryPolygonShape.setShapeID(i5);
                processGrpRotation(groupShape, arbitraryPolygonShape, element6);
                arbitraryPolygonShape.setHidden(z2);
                arbitraryPolygonShape.setLine(createShapeLine);
                return arbitraryPolygonShape;
            } else {
                boolean z6 = z2;
                if (!(backgrouond == null && createShapeLine == null)) {
                    AutoShape autoShape = new AutoShape(i8);
                    autoShape.setBounds(rectangle);
                    autoShape.setShapeID(i5);
                    autoShape.setHidden(z6);
                    if (backgrouond != null) {
                        autoShape.setBackgroundAndFill(backgrouond);
                    }
                    if (createShapeLine != null) {
                        autoShape.setLine(createShapeLine);
                    }
                    autoShape.setAdjustData(fArr3);
                    return autoShape;
                }
            }
        }
        return null;
    }

    private void processSmartArt(PGSlide pGSlide, Element element, Rectangle rectangle) {
        SmartArt smartArt;
        if (element != null) {
            try {
                String attributeValue = element.element("relIds").attributeValue("dm");
                int parseInt = Integer.parseInt(attributeValue.substring(3));
                if (attributeValue != null && (smartArt = pGSlide.getSmartArt(attributeValue)) != null) {
                    smartArt.setBounds(rectangle);
                    for (IShape shapeID : smartArt.getShapes()) {
                        shapeID.setShapeID(parseInt);
                    }
                    pGSlide.appendShapes(smartArt);
                }
            } catch (Exception unused) {
            }
        }
    }
}
