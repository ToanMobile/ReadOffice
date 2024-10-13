package com.app.office.fc.xls.Reader.drawing;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.text.html.HtmlTags;
import com.onesignal.outcomes.OSOutcomeConstants;
import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.common.pictureefftect.PictureEffectInfoFactory;
import com.app.office.common.shape.AChart;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.SmartArt;
import com.app.office.common.shape.TextBox;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipCollection;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.attribute.ParaAttr;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.fc.ppt.attribute.SectionAttr;
import com.app.office.fc.ppt.reader.PictureReader;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.fc.xls.Reader.SchemeColorUtil;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.font.Font;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.AttributeSetImpl;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.drawing.AnchorPoint;
import com.app.office.ss.model.drawing.CellAnchor;
import com.app.office.ss.model.drawing.TextParagraph;
import com.app.office.ss.util.ModelUtil;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DrawingReader {
    private static DrawingReader reader = new DrawingReader();
    private Map<String, AbstractChart> chartList;
    private Map<String, Integer> drawingList;
    private int offset;
    private Sheet sheet;
    private Map<String, SmartArt> smartArtList;

    public static DrawingReader instance() {
        return reader;
    }

    public void read(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, Sheet sheet2) throws Exception {
        ZipPackage zipPackage2 = zipPackage;
        PackagePart packagePart2 = packagePart;
        this.sheet = sheet2;
        Map<String, Integer> schemeColor = SchemeColorUtil.getSchemeColor(sheet2.getWorkbook());
        PackageRelationshipCollection relationshipsByType = packagePart2.getRelationshipsByType(PackageRelationshipTypes.CHART_PART);
        this.chartList = new HashMap();
        Iterator<PackageRelationship> it = relationshipsByType.iterator();
        while (it.hasNext()) {
            PackageRelationship next = it.next();
            this.chartList.put(next.getId(), ChartReader.instance().read(iControl, zipPackage, zipPackage2.getPart(next.getTargetURI()), schemeColor, (byte) 1));
        }
        this.smartArtList = new HashMap();
        PackageRelationshipCollection relationshipsByType2 = packagePart2.getRelationshipsByType(PackageRelationshipTypes.DIAGRAM_DATA);
        if (relationshipsByType2 != null && relationshipsByType2.size() > 0) {
            int size = relationshipsByType2.size();
            int i = 0;
            while (i < size) {
                PackageRelationship relationship = relationshipsByType2.getRelationship(i);
                this.smartArtList.put(relationship.getId(), SmartArtReader.instance().read(iControl, zipPackage, packagePart, zipPackage2.getPart(relationship.getTargetURI()), schemeColor, sheet2));
                i++;
                Sheet sheet3 = sheet2;
            }
        }
        PackageRelationshipCollection relationshipsByType3 = packagePart2.getRelationshipsByType(PackageRelationshipTypes.IMAGE_PART);
        this.drawingList = new HashMap(10);
        Iterator<PackageRelationship> it2 = relationshipsByType3.iterator();
        while (it2.hasNext()) {
            PackageRelationship next2 = it2.next();
            this.drawingList.put(next2.getId(), Integer.valueOf(iControl.getSysKit().getPictureManage().addPicture(zipPackage2.getPart(next2.getTargetURI()))));
        }
        SAXReader sAXReader = new SAXReader();
        InputStream inputStream = packagePart.getInputStream();
        Document read = sAXReader.read(inputStream);
        inputStream.close();
        getCellAnchors(iControl, zipPackage2, packagePart2, read.getRootElement());
        dispose();
    }

    private void getCellAnchors(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, Element element) throws Exception {
        if (element != null && element.hasContent()) {
            Iterator elementIterator = element.elementIterator();
            CellAnchor cellAnchor = null;
            while (elementIterator.hasNext()) {
                Element element2 = (Element) elementIterator.next();
                if (element2.getName().equalsIgnoreCase("twoCellAnchor")) {
                    cellAnchor = getTwoCellAnchor(element2);
                } else if (element2.getName().equalsIgnoreCase("oneCellAnchor")) {
                    cellAnchor = getOneCellAnchor(element2);
                }
                CellAnchor cellAnchor2 = cellAnchor;
                Iterator elementIterator2 = element2.elementIterator();
                while (elementIterator2.hasNext()) {
                    IControl iControl2 = iControl;
                    ZipPackage zipPackage2 = zipPackage;
                    PackagePart packagePart2 = packagePart;
                    processShape(iControl2, zipPackage2, packagePart2, (Element) elementIterator2.next(), (GroupShape) null, 1.0f, 1.0f, ModelUtil.instance().getCellAnchor(this.sheet, cellAnchor2));
                }
                cellAnchor = cellAnchor2;
            }
        }
    }

    private AnchorPoint getCellAnchor(Element element) {
        if (element == null) {
            return null;
        }
        AnchorPoint anchorPoint = new AnchorPoint();
        anchorPoint.setColumn((short) Integer.parseInt(element.element("col").getText()));
        anchorPoint.setDX((int) ((((float) Long.parseLong(element.element("colOff").getText())) * 96.0f) / 914400.0f));
        anchorPoint.setRow(Integer.parseInt(element.element("row").getText()));
        anchorPoint.setDY((int) ((((float) Long.parseLong(element.element("rowOff").getText())) * 96.0f) / 914400.0f));
        return anchorPoint;
    }

    private CellAnchor getTwoCellAnchor(Element element) {
        CellAnchor cellAnchor = new CellAnchor(1);
        cellAnchor.setStart(getCellAnchor(element.element("from")));
        cellAnchor.setEnd(getCellAnchor(element.element(TypedValues.TransitionType.S_TO)));
        return cellAnchor;
    }

    private CellAnchor getOneCellAnchor(Element element) {
        AnchorPoint cellAnchor = getCellAnchor(element.element("from"));
        CellAnchor cellAnchor2 = new CellAnchor(0);
        cellAnchor2.setStart(cellAnchor);
        Element element2 = element.element("ext");
        cellAnchor2.setWidth((int) ((((float) Long.parseLong(element2.attributeValue("cx"))) * 96.0f) / 914400.0f));
        cellAnchor2.setHeight((int) ((((float) Long.parseLong(element2.attributeValue("cy"))) * 96.0f) / 914400.0f));
        return cellAnchor2;
    }

    private TextBox getTextBoxData(IControl iControl, Element element, Rectangle rectangle) {
        TextBox textBox = new TextBox();
        SectionElement sectionElement = new SectionElement();
        sectionElement.setStartOffset(0);
        textBox.setElement(sectionElement);
        IAttributeSet attribute = sectionElement.getAttribute();
        AttrManage.instance().setPageWidth(attribute, Math.round(((float) rectangle.width) * 15.0f));
        AttrManage.instance().setPageHeight(attribute, Math.round(((float) rectangle.height) * 15.0f));
        Element element2 = element.element("txBody");
        if (element2 != null) {
            AttributeSetImpl attributeSetImpl = new AttributeSetImpl();
            AttrManage.instance().setPageMarginLeft(attributeSetImpl, Math.round(144.0f));
            AttrManage.instance().setPageMarginRight(attributeSetImpl, Math.round(144.0f));
            AttrManage.instance().setPageMarginTop(attributeSetImpl, Math.round(72.0f));
            AttrManage.instance().setPageMarginBottom(attributeSetImpl, Math.round(72.0f));
            Element element3 = element2.element("bodyPr");
            SectionAttr.instance().setSectionAttribute(element3, attribute, attributeSetImpl, (IAttributeSet) null, false);
            if (element3 != null) {
                String attributeValue = element3.attributeValue("wrap");
                textBox.setWrapLine(attributeValue == null || "square".equalsIgnoreCase(attributeValue));
            }
            sectionElement.setEndOffset((long) processParagraph(iControl, sectionElement, element2));
        }
        textBox.setBounds(rectangle);
        if (textBox.getElement() == null || textBox.getElement().getText((IDocument) null) == null || textBox.getElement().getText((IDocument) null).length() <= 0 || "\n".equals(textBox.getElement().getText((IDocument) null))) {
            return null;
        }
        ReaderKit.instance().processRotation(element.element("spPr"), (IShape) textBox);
        return textBox;
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

    public static short getVerticalByString(String str) {
        if (str != null && !str.equalsIgnoreCase("ctr")) {
            if (str.equalsIgnoreCase("t")) {
                return 0;
            }
            if (str.equalsIgnoreCase(HtmlTags.B)) {
                return 2;
            }
            if (!str.equalsIgnoreCase("just") && !str.equalsIgnoreCase("dist")) {
                return 1;
            }
            return 3;
        }
        return 1;
    }

    public static short getHorizontalByString(String str) {
        if (str == null || str.equalsIgnoreCase("l")) {
            return 1;
        }
        if (str.equalsIgnoreCase("ctr")) {
            return 2;
        }
        if (str.equalsIgnoreCase("r")) {
            return 3;
        }
        return str.equalsIgnoreCase("just") ? (short) 5 : 0;
    }

    private static Font getFont(Element element) {
        Font font = new Font();
        if (element.attributeValue("sz") != null) {
            font.setFontSize((double) (Integer.parseInt(element.attributeValue("sz")) / 100));
        }
        if (!(element.attributeValue(HtmlTags.B) == null || Integer.parseInt(element.attributeValue(HtmlTags.B)) == 0)) {
            font.setBold(true);
        }
        if (!(element.attributeValue("i") == null || Integer.parseInt(element.attributeValue("i")) == 0)) {
            font.setItalic(true);
        }
        if (element.attributeValue(HtmlTags.U) != null) {
            if (element.attributeValue(HtmlTags.U).equalsIgnoreCase("sng")) {
                font.setUnderline(1);
            } else if (element.attributeValue(HtmlTags.U).equalsIgnoreCase("dbl")) {
                font.setUnderline(2);
            }
        }
        if (element.attributeValue(HtmlTags.STRIKE) != null && !element.attributeValue(HtmlTags.STRIKE).equalsIgnoreCase("noStrike")) {
            font.setStrikeline(true);
        }
        element.element("solidFill");
        font.setColorIndex(8);
        return font;
    }

    public static TextParagraph getTextParagraph(Element element) {
        Element element2;
        TextParagraph textParagraph = new TextParagraph();
        Element element3 = element.element("pPr");
        if (element3 != null) {
            textParagraph.setHorizontalAlign(getHorizontalByString(element3.attributeValue("algn")));
        }
        Font font = null;
        String str = "";
        for (Element element4 : element.elements("r")) {
            if (font == null && (element2 = element4.element("rPr")) != null) {
                font = getFont(element2);
            }
            if (element4.element("t") != null) {
                str = str + element4.element("t").getText();
            }
        }
        textParagraph.setFont(font);
        textParagraph.setTextRun(str);
        return textParagraph;
    }

    private PictureShape getImageData(Element element, Rectangle rectangle) {
        Element element2 = element.element("blipFill");
        if (element2 == null) {
            return null;
        }
        PictureEffectInfo pictureEffectInfor = PictureEffectInfoFactory.getPictureEffectInfor(element2);
        Element element3 = element2.element("blip");
        if (element3 == null || element3.attributeValue("embed") == null || element3.attributeValue("embed") == null || this.drawingList.get(element3.attributeValue("embed")) == null) {
            return null;
        }
        PictureShape pictureShape = new PictureShape();
        int intValue = this.drawingList.get(element3.attributeValue("embed")).intValue();
        pictureShape.setBounds(rectangle);
        pictureShape.setPictureIndex(intValue);
        pictureShape.setPictureEffectInfor(pictureEffectInfor);
        ReaderKit.instance().processRotation(element.element("spPr"), (IShape) pictureShape);
        return pictureShape;
    }

    private AChart getChart(Element element, Rectangle rectangle) {
        String attributeValue;
        if (element == null || (attributeValue = element.attributeValue((String) OSOutcomeConstants.OUTCOME_ID)) == null) {
            return null;
        }
        AChart aChart = new AChart();
        aChart.setBounds(rectangle);
        aChart.setAChart(this.chartList.get(attributeValue));
        return aChart;
    }

    private SmartArt getSmartArt(Element element, Rectangle rectangle) {
        if (element != null) {
            try {
                String attributeValue = element.element("relIds").attributeValue("dm");
                Integer.parseInt(attributeValue.substring(3));
                if (attributeValue != null) {
                    SmartArt smartArt = this.smartArtList.get(attributeValue);
                    smartArt.setBounds(rectangle);
                    return smartArt;
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: com.app.office.java.awt.Rectangle} */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [com.app.office.common.shape.AbstractShape] */
    /* JADX WARNING: type inference failed for: r4v4, types: [com.app.office.common.shape.IShape] */
    /* JADX WARNING: type inference failed for: r4v17 */
    /* JADX WARNING: type inference failed for: r4v18 */
    /* JADX WARNING: type inference failed for: r4v19 */
    /* JADX WARNING: type inference failed for: r4v20 */
    /* JADX WARNING: type inference failed for: r4v21 */
    /* JADX WARNING: type inference failed for: r4v22 */
    /* JADX WARNING: type inference failed for: r4v23 */
    /* JADX WARNING: type inference failed for: r4v24 */
    /* JADX WARNING: type inference failed for: r4v25 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processShape(com.app.office.system.IControl r19, com.app.office.fc.openxml4j.opc.ZipPackage r20, com.app.office.fc.openxml4j.opc.PackagePart r21, com.app.office.fc.dom4j.Element r22, com.app.office.common.shape.GroupShape r23, float r24, float r25, com.app.office.java.awt.Rectangle r26) throws java.lang.Exception {
        /*
            r18 = this;
            r9 = r18
            r7 = r22
            r10 = r23
            r11 = r24
            r12 = r25
            r0 = r26
            java.lang.String r1 = r22.getName()
            java.lang.String r2 = "grpSp"
            boolean r2 = r1.equals(r2)
            java.lang.String r3 = "xfrm"
            r4 = 0
            if (r2 == 0) goto L_0x0102
            java.lang.String r1 = "grpSpPr"
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r1)
            r13 = 1
            r14 = 0
            if (r1 == 0) goto L_0x007f
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r4 = r1.element((java.lang.String) r3)
            com.app.office.java.awt.Rectangle r2 = r2.getShapeAnchor(r4, r11, r12)
            int r4 = r2.width
            if (r4 == 0) goto L_0x007e
            int r4 = r2.height
            if (r4 != 0) goto L_0x003a
            goto L_0x007e
        L_0x003a:
            com.app.office.java.awt.Rectangle r4 = r9.processGrpSpRect(r10, r2)
            com.app.office.fc.ppt.reader.ReaderKit r2 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r5 = r1.element((java.lang.String) r3)
            float[] r2 = r2.getAnchorFitZoom(r5)
            com.app.office.fc.ppt.reader.ReaderKit r5 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r3 = r1.element((java.lang.String) r3)
            r6 = r2[r14]
            float r6 = r6 * r11
            r8 = r2[r13]
            float r8 = r8 * r12
            com.app.office.java.awt.Rectangle r3 = r5.getChildShapeAnchor(r3, r6, r8)
            com.app.office.common.shape.GroupShape r5 = new com.app.office.common.shape.GroupShape
            r5.<init>()
            r5.setBounds(r4)
            int r6 = r4.x
            int r8 = r3.x
            int r6 = r6 - r8
            int r8 = r4.y
            int r3 = r3.y
            int r8 = r8 - r3
            r5.setOffPostion(r6, r8)
            com.app.office.fc.ppt.reader.ReaderKit r3 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            r3.processRotation((com.app.office.fc.dom4j.Element) r1, (com.app.office.common.shape.IShape) r5)
            r16 = r2
            r15 = r5
            goto L_0x0082
        L_0x007e:
            return
        L_0x007f:
            r15 = r4
            r16 = r15
        L_0x0082:
            if (r10 != 0) goto L_0x0086
            r8 = r0
            goto L_0x00c8
        L_0x0086:
            com.app.office.java.awt.Rectangle r1 = new com.app.office.java.awt.Rectangle
            r1.<init>()
            com.app.office.java.awt.Rectangle r2 = r23.getBounds()
            int r3 = r0.x
            int r5 = r4.x
            int r6 = r2.x
            int r5 = r5 - r6
            int r6 = r0.width
            int r5 = r5 * r6
            int r6 = r2.width
            int r5 = r5 / r6
            int r3 = r3 + r5
            r1.x = r3
            int r3 = r0.y
            int r5 = r4.y
            int r6 = r2.y
            int r5 = r5 - r6
            int r6 = r0.height
            int r5 = r5 * r6
            int r6 = r2.height
            int r5 = r5 / r6
            int r3 = r3 + r5
            r1.y = r3
            int r3 = r0.width
            int r5 = r4.width
            int r3 = r3 * r5
            int r5 = r2.width
            int r3 = r3 / r5
            r1.width = r3
            int r0 = r0.height
            int r3 = r4.height
            int r0 = r0 * r3
            int r2 = r2.height
            int r0 = r0 / r2
            r1.height = r0
            r8 = r1
        L_0x00c8:
            java.util.Iterator r17 = r22.elementIterator()
        L_0x00cc:
            boolean r0 = r17.hasNext()
            if (r0 == 0) goto L_0x00f0
            java.lang.Object r0 = r17.next()
            r4 = r0
            com.app.office.fc.dom4j.Element r4 = (com.app.office.fc.dom4j.Element) r4
            r0 = r16[r14]
            float r6 = r0 * r11
            r0 = r16[r13]
            float r7 = r0 * r12
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r5 = r15
            r22 = r8
            r0.processShape(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x00cc
        L_0x00f0:
            r0 = r8
            r15.setBounds(r0)
            if (r10 != 0) goto L_0x00fd
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet
            r0.appendShapes(r15)
            goto L_0x0297
        L_0x00fd:
            r10.appendShapes(r15)
            goto L_0x0297
        L_0x0102:
            java.lang.String r2 = "AlternateContent"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0136
            java.lang.String r0 = "Choice"
            com.app.office.fc.dom4j.Element r0 = r7.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x0297
            java.util.Iterator r13 = r0.elementIterator()
        L_0x0116:
            boolean r0 = r13.hasNext()
            if (r0 == 0) goto L_0x0297
            java.lang.Object r0 = r13.next()
            r4 = r0
            com.app.office.fc.dom4j.Element r4 = (com.app.office.fc.dom4j.Element) r4
            r8 = 0
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r5 = r23
            r6 = r24
            r7 = r25
            r0.processShape(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x0116
        L_0x0136:
            java.lang.String r2 = "spPr"
            if (r10 != 0) goto L_0x013c
            r8 = r0
            goto L_0x0190
        L_0x013c:
            com.app.office.fc.dom4j.Element r5 = r7.element((java.lang.String) r2)
            if (r5 != 0) goto L_0x0143
            return
        L_0x0143:
            com.app.office.fc.ppt.reader.ReaderKit r6 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r3 = r5.element((java.lang.String) r3)
            com.app.office.java.awt.Rectangle r3 = r6.getShapeAnchor(r3, r11, r12)
            com.app.office.java.awt.Rectangle r3 = r9.processGrpSpRect(r10, r3)
            com.app.office.java.awt.Rectangle r5 = r23.getBounds()
            int r6 = r0.x
            int r8 = r3.x
            int r11 = r5.x
            int r8 = r8 - r11
            int r11 = r0.width
            int r8 = r8 * r11
            int r11 = r5.width
            int r8 = r8 / r11
            int r6 = r6 + r8
            r3.x = r6
            int r6 = r0.y
            int r8 = r3.y
            int r11 = r5.y
            int r8 = r8 - r11
            int r11 = r0.height
            int r8 = r8 * r11
            int r11 = r5.height
            int r8 = r8 / r11
            int r6 = r6 + r8
            r3.y = r6
            int r6 = r0.width
            int r8 = r3.width
            int r6 = r6 * r8
            int r8 = r5.width
            int r6 = r6 / r8
            r3.width = r6
            int r0 = r0.height
            int r6 = r3.height
            int r0 = r0 * r6
            int r5 = r5.height
            int r0 = r0 / r5
            r3.height = r0
            r8 = r3
        L_0x0190:
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet
            short r0 = r0.getSheetType()
            if (r0 != 0) goto L_0x019b
            if (r8 != 0) goto L_0x019b
            return
        L_0x019b:
            java.lang.String r0 = "sp"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x0240
            java.lang.String r0 = "cxnSp"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x01ad
            goto L_0x0240
        L_0x01ad:
            java.lang.String r0 = "pic"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x01e4
            com.app.office.common.shape.PictureShape r4 = r9.getImageData(r7, r8)
            if (r4 == 0) goto L_0x0278
            com.app.office.fc.dom4j.Element r14 = r7.element((java.lang.String) r2)
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet
            com.app.office.ss.model.baseModel.Workbook r0 = r0.getWorkbook()
            java.util.Map r15 = com.app.office.fc.xls.Reader.SchemeColorUtil.getSchemeColor(r0)
            r16 = r4
            com.app.office.common.shape.PictureShape r16 = (com.app.office.common.shape.PictureShape) r16
            r11 = r19
            r12 = r20
            r13 = r21
            com.app.office.common.autoshape.AutoShapeDataKit.processPictureShape(r11, r12, r13, r14, r15, r16)
            if (r10 != 0) goto L_0x01df
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet
            r0.appendShapes(r4)
            goto L_0x0278
        L_0x01df:
            r10.appendShapes(r4)
            goto L_0x0278
        L_0x01e4:
            java.lang.String r0 = "graphicFrame"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0278
            java.lang.String r0 = "graphic"
            com.app.office.fc.dom4j.Element r0 = r7.element((java.lang.String) r0)
            if (r0 == 0) goto L_0x0278
            java.lang.String r1 = "graphicData"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            if (r0 == 0) goto L_0x0278
            java.lang.String r1 = "uri"
            com.app.office.fc.dom4j.Attribute r3 = r0.attribute((java.lang.String) r1)
            if (r3 == 0) goto L_0x0278
            java.lang.String r1 = r0.attributeValue((java.lang.String) r1)
            java.lang.String r3 = "http://schemas.openxmlformats.org/drawingml/2006/chart"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x021b
            java.lang.String r1 = "chart"
            com.app.office.fc.dom4j.Element r0 = r0.element((java.lang.String) r1)
            com.app.office.common.shape.AChart r4 = r9.getChart(r0, r8)
            goto L_0x0227
        L_0x021b:
            java.lang.String r3 = "http://schemas.openxmlformats.org/drawingml/2006/diagram"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0227
            com.app.office.common.shape.SmartArt r4 = r9.getSmartArt(r0, r8)
        L_0x0227:
            if (r4 == 0) goto L_0x0278
            com.app.office.fc.ppt.reader.ReaderKit r0 = com.app.office.fc.ppt.reader.ReaderKit.instance()
            com.app.office.fc.dom4j.Element r1 = r7.element((java.lang.String) r2)
            r0.processRotation((com.app.office.fc.dom4j.Element) r1, (com.app.office.common.shape.IShape) r4)
            if (r10 != 0) goto L_0x023c
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet
            r0.appendShapes(r4)
            goto L_0x0278
        L_0x023c:
            r10.appendShapes(r4)
            goto L_0x0278
        L_0x0240:
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet
            com.app.office.ss.model.baseModel.Workbook r0 = r0.getWorkbook()
            java.util.Map r5 = com.app.office.fc.xls.Reader.SchemeColorUtil.getSchemeColor(r0)
            r6 = 1
            r0 = r19
            r1 = r20
            r2 = r21
            r3 = r22
            r4 = r8
            com.app.office.common.shape.AbstractShape r4 = com.app.office.common.autoshape.AutoShapeDataKit.getAutoShape(r0, r1, r2, r3, r4, r5, r6)
            if (r4 == 0) goto L_0x0265
            if (r10 != 0) goto L_0x0262
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet
            r0.appendShapes(r4)
            goto L_0x0265
        L_0x0262:
            r10.appendShapes(r4)
        L_0x0265:
            r0 = r19
            com.app.office.common.shape.TextBox r0 = r9.getTextBoxData(r0, r7, r8)
            if (r0 == 0) goto L_0x0278
            if (r10 != 0) goto L_0x0275
            com.app.office.ss.model.baseModel.Sheet r1 = r9.sheet
            r1.appendShapes(r0)
            goto L_0x0278
        L_0x0275:
            r10.appendShapes(r0)
        L_0x0278:
            if (r4 == 0) goto L_0x0297
            float r0 = r4.getRotation()
            float r0 = java.lang.Math.abs(r0)
            r1 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x0297
            com.app.office.java.awt.Rectangle r0 = r4.getBounds()
            float r1 = r4.getRotation()
            com.app.office.java.awt.Rectangle r0 = com.app.office.ss.util.ModelUtil.processRect(r0, r1)
            r4.setBounds(r0)
        L_0x0297:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.Reader.drawing.DrawingReader.processShape(com.app.office.system.IControl, com.app.office.fc.openxml4j.opc.ZipPackage, com.app.office.fc.openxml4j.opc.PackagePart, com.app.office.fc.dom4j.Element, com.app.office.common.shape.GroupShape, float, float, com.app.office.java.awt.Rectangle):void");
    }

    private Rectangle processGrpSpRect(GroupShape groupShape, Rectangle rectangle) {
        if (groupShape != null) {
            rectangle.x += groupShape.getOffX();
            rectangle.y += groupShape.getOffY();
        }
        return rectangle;
    }

    public void processOLEPicture(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, Sheet sheet2, Element element) throws Exception {
        PackagePart oLEPart;
        CellAnchor excelShapeAnchor;
        this.sheet = sheet2;
        if (element != null) {
            for (Element attributeValue : element.elements("oleObject")) {
                String attributeValue2 = attributeValue.attributeValue("shapeId");
                if (!(attributeValue2 == null || (oLEPart = PictureReader.instance().getOLEPart(zipPackage, packagePart, attributeValue2, true)) == null || (excelShapeAnchor = PictureReader.instance().getExcelShapeAnchor(attributeValue2)) == null)) {
                    PictureShape pictureShape = new PictureShape();
                    pictureShape.setPictureIndex(iControl.getSysKit().getPictureManage().addPicture(oLEPart));
                    pictureShape.setBounds(ModelUtil.instance().getCellAnchor(sheet2, excelShapeAnchor));
                    sheet2.appendShapes(pictureShape);
                }
            }
        }
    }

    private void dispose() {
        this.sheet = null;
        Map<String, Integer> map = this.drawingList;
        if (map != null) {
            map.clear();
            this.drawingList = null;
        }
        Map<String, AbstractChart> map2 = this.chartList;
        if (map2 != null) {
            map2.clear();
            this.chartList = null;
        }
        Map<String, SmartArt> map3 = this.smartArtList;
        if (map3 != null) {
            map3.clear();
            this.smartArtList = null;
        }
    }
}
