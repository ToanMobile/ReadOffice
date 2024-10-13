package com.app.office.ss.util;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ppt.attribute.ParaAttr;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.AttributeSetImpl;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.style.CellStyle;

public class SectionElementFactory {
    private static AttributeSetImpl attrLayout;
    private static Workbook book;
    private static LeafElement leaf;
    private static int offset;
    private static ParagraphElement paraElem;

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0048, code lost:
        if (r2 != 3) goto L_0x004f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0067  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.simpletext.model.SectionElement getSectionElement(com.app.office.ss.model.baseModel.Workbook r7, com.app.office.fc.hssf.record.common.UnicodeString r8, com.app.office.ss.model.baseModel.Cell r9) {
        /*
            book = r7
            com.app.office.ss.model.style.CellStyle r7 = r9.getCellStyle()
            com.app.office.simpletext.model.SectionElement r0 = new com.app.office.simpletext.model.SectionElement
            r0.<init>()
            r1 = 0
            r0.setStartOffset(r1)
            com.app.office.simpletext.model.IAttributeSet r1 = r0.getAttribute()
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            r3 = 1106247680(0x41f00000, float:30.0)
            int r4 = java.lang.Math.round(r3)
            r2.setPageMarginLeft(r1, r4)
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            int r3 = java.lang.Math.round(r3)
            r2.setPageMarginRight(r1, r3)
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            r3 = 0
            r2.setPageMarginTop(r1, r3)
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            r2.setPageMarginBottom(r1, r3)
            short r2 = r7.getVerticalAlign()
            r4 = 3
            r5 = 2
            r6 = 1
            if (r2 == 0) goto L_0x004f
            if (r2 == r6) goto L_0x004d
            if (r2 == r5) goto L_0x004b
            if (r2 == r4) goto L_0x0050
            goto L_0x004f
        L_0x004b:
            r4 = 2
            goto L_0x0050
        L_0x004d:
            r4 = 1
            goto L_0x0050
        L_0x004f:
            r4 = 0
        L_0x0050:
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            r2.setPageVerticalAlign(r1, r4)
            r7.getFontIndex()
            offset = r3
            int r7 = processParagraph(r0, r8, r7, r9)
            if (r7 == 0) goto L_0x0067
            long r7 = (long) r7
            r0.setEndOffset(r7)
            goto L_0x006b
        L_0x0067:
            r0.dispose()
            r0 = 0
        L_0x006b:
            dispose()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.SectionElementFactory.getSectionElement(com.app.office.ss.model.baseModel.Workbook, com.app.office.fc.hssf.record.common.UnicodeString, com.app.office.ss.model.baseModel.Cell):com.app.office.simpletext.model.SectionElement");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x008c, code lost:
        if (r7 != 7) goto L_0x008e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.simpletext.model.SectionElement getSectionElement(com.app.office.ss.model.baseModel.Workbook r5, com.app.office.fc.hssf.usermodel.HSSFTextbox r6, com.app.office.java.awt.Rectangle r7) {
        /*
            book = r5
            com.app.office.simpletext.model.SectionElement r5 = new com.app.office.simpletext.model.SectionElement
            r5.<init>()
            r0 = 0
            r5.setStartOffset(r0)
            com.app.office.simpletext.model.IAttributeSet r0 = r5.getAttribute()
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            int r2 = r7.width
            float r2 = (float) r2
            r3 = 1097859072(0x41700000, float:15.0)
            float r2 = r2 * r3
            int r2 = java.lang.Math.round(r2)
            r1.setPageWidth(r0, r2)
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            int r7 = r7.height
            float r7 = (float) r7
            float r7 = r7 * r3
            int r7 = java.lang.Math.round(r7)
            r1.setPageHeight(r0, r7)
            com.app.office.simpletext.model.AttrManage r7 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r6.getMarginLeft()
            float r1 = (float) r1
            float r1 = r1 * r3
            int r1 = java.lang.Math.round(r1)
            r7.setPageMarginLeft(r0, r1)
            com.app.office.simpletext.model.AttrManage r7 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r6.getMarginTop()
            float r1 = (float) r1
            float r1 = r1 * r3
            int r1 = java.lang.Math.round(r1)
            r7.setPageMarginTop(r0, r1)
            com.app.office.simpletext.model.AttrManage r7 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r6.getMarginRight()
            float r1 = (float) r1
            float r1 = r1 * r3
            int r1 = java.lang.Math.round(r1)
            r7.setPageMarginRight(r0, r1)
            com.app.office.simpletext.model.AttrManage r7 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r6.getMarginBottom()
            float r1 = (float) r1
            float r1 = r1 * r3
            int r1 = java.lang.Math.round(r1)
            r7.setPageMarginBottom(r0, r1)
            short r7 = r6.getVerticalAlignment()
            r1 = 2
            r2 = 1
            r3 = 0
            if (r7 == r2) goto L_0x008e
            if (r7 == r1) goto L_0x0090
            r4 = 3
            if (r7 == r4) goto L_0x0091
            r1 = 4
            if (r7 == r1) goto L_0x0090
            r1 = 7
            if (r7 == r1) goto L_0x0090
        L_0x008e:
            r1 = 0
            goto L_0x0091
        L_0x0090:
            r1 = 1
        L_0x0091:
            com.app.office.simpletext.model.AttrManage r7 = com.app.office.simpletext.model.AttrManage.instance()
            r7.setPageVerticalAlign(r0, r1)
            int r6 = processParagraph(r5, r6)
            long r6 = (long) r6
            r5.setEndOffset(r6)
            dispose()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.SectionElementFactory.getSectionElement(com.app.office.ss.model.baseModel.Workbook, com.app.office.fc.hssf.usermodel.HSSFTextbox, com.app.office.java.awt.Rectangle):com.app.office.simpletext.model.SectionElement");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
        if (r1 != 6) goto L_0x001a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int processParagraph(com.app.office.simpletext.model.SectionElement r8, com.app.office.fc.hssf.record.common.UnicodeString r9, com.app.office.ss.model.style.CellStyle r10, com.app.office.ss.model.baseModel.Cell r11) {
        /*
            r11 = 0
            offset = r11
            java.lang.String r0 = r9.getString()
            short r1 = r10.getHorizontalAlign()
            r2 = 2
            r3 = 1
            if (r1 == r3) goto L_0x001a
            if (r1 == r2) goto L_0x001c
            r4 = 3
            if (r1 == r4) goto L_0x001d
            r2 = 5
            if (r1 == r2) goto L_0x001c
            r2 = 6
            if (r1 == r2) goto L_0x001c
        L_0x001a:
            r2 = 0
            goto L_0x001d
        L_0x001c:
            r2 = 1
        L_0x001d:
            com.app.office.simpletext.model.ParagraphElement r1 = new com.app.office.simpletext.model.ParagraphElement
            r1.<init>()
            paraElem = r1
            int r4 = offset
            long r4 = (long) r4
            r1.setStartOffset(r4)
            com.app.office.simpletext.model.AttributeSetImpl r1 = new com.app.office.simpletext.model.AttributeSetImpl
            r1.<init>()
            attrLayout = r1
            com.app.office.fc.ppt.attribute.ParaAttr r1 = com.app.office.fc.ppt.attribute.ParaAttr.instance()
            com.app.office.simpletext.model.ParagraphElement r4 = paraElem
            com.app.office.simpletext.model.IAttributeSet r4 = r4.getAttribute()
            com.app.office.simpletext.model.AttributeSetImpl r5 = attrLayout
            r1.setParaAttribute(r10, r4, r5)
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.ParagraphElement r4 = paraElem
            com.app.office.simpletext.model.IAttributeSet r4 = r4.getAttribute()
            r1.setParaHorizontalAlign(r4, r2)
            int r1 = r9.getFormatRunCount()
            if (r1 != 0) goto L_0x005c
            short r9 = r10.getFontIndex()
            processParagraph_SubString(r8, r10, r0, r9, r2)
            goto L_0x00f4
        L_0x005c:
            java.util.Iterator r9 = r9.formatIterator()
            java.lang.Object r1 = r9.next()
            com.app.office.fc.hssf.record.common.UnicodeString$FormatRun r1 = (com.app.office.fc.hssf.record.common.UnicodeString.FormatRun) r1
            short r4 = r1.getCharacterPos()
            java.lang.String r11 = r0.substring(r11, r4)
            boolean r4 = r10.isWrapText()
            java.lang.String r5 = ""
            java.lang.String r6 = "\n"
            if (r4 != 0) goto L_0x007c
            java.lang.String r11 = r11.replace(r6, r5)
        L_0x007c:
            short r4 = r10.getFontIndex()
            processParagraph_SubString(r8, r10, r11, r4, r2)
        L_0x0083:
            boolean r11 = r9.hasNext()
            if (r11 == 0) goto L_0x00b9
            java.lang.Object r11 = r9.next()
            com.app.office.fc.hssf.record.common.UnicodeString$FormatRun r11 = (com.app.office.fc.hssf.record.common.UnicodeString.FormatRun) r11
            short r4 = r11.getCharacterPos()
            int r7 = r0.length()
            if (r4 <= r7) goto L_0x009a
            goto L_0x00b9
        L_0x009a:
            short r4 = r1.getCharacterPos()
            short r7 = r11.getCharacterPos()
            java.lang.String r4 = r0.substring(r4, r7)
            boolean r7 = r10.isWrapText()
            if (r7 != 0) goto L_0x00b0
            java.lang.String r4 = r4.replace(r6, r5)
        L_0x00b0:
            short r1 = r1.getFontIndex()
            processParagraph_SubString(r8, r10, r4, r1, r2)
            r1 = r11
            goto L_0x0083
        L_0x00b9:
            short r9 = r1.getCharacterPos()
            java.lang.String r9 = r0.substring(r9)
            boolean r11 = r10.isWrapText()
            if (r11 != 0) goto L_0x00cb
            java.lang.String r9 = r9.replace(r6, r5)
        L_0x00cb:
            short r11 = r1.getFontIndex()
            processParagraph_SubString(r8, r10, r9, r11, r2)
            com.app.office.simpletext.model.LeafElement r9 = leaf
            if (r9 == 0) goto L_0x00f4
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            com.app.office.simpletext.model.LeafElement r11 = leaf
            r0 = 0
            java.lang.String r11 = r11.getText(r0)
            r10.append(r11)
            r10.append(r6)
            java.lang.String r10 = r10.toString()
            r9.setText(r10)
            int r9 = offset
            int r9 = r9 + r3
            offset = r9
        L_0x00f4:
            com.app.office.simpletext.model.LeafElement r9 = leaf
            if (r9 == 0) goto L_0x0113
            com.app.office.simpletext.model.ParagraphElement r10 = paraElem
            long r0 = r9.getStartOffset()
            com.app.office.simpletext.model.IElement r9 = r10.getLeaf(r0)
            if (r9 != 0) goto L_0x0113
            com.app.office.simpletext.model.LeafElement r9 = leaf
            int r10 = offset
            long r10 = (long) r10
            r9.setEndOffset(r10)
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            com.app.office.simpletext.model.LeafElement r10 = leaf
            r9.appendLeaf(r10)
        L_0x0113:
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            if (r9 == 0) goto L_0x0130
            long r9 = r9.getStartOffset()
            com.app.office.simpletext.model.IElement r9 = r8.getElement(r9)
            if (r9 != 0) goto L_0x0130
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            int r10 = offset
            long r10 = (long) r10
            r9.setEndOffset(r10)
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            r10 = 0
            r8.appendParagraph(r9, r10)
        L_0x0130:
            int r8 = offset
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.SectionElementFactory.processParagraph(com.app.office.simpletext.model.SectionElement, com.app.office.fc.hssf.record.common.UnicodeString, com.app.office.ss.model.style.CellStyle, com.app.office.ss.model.baseModel.Cell):int");
    }

    private static void processParagraph_SubString(SectionElement sectionElement, CellStyle cellStyle, String str, int i, byte b) {
        if (!str.contains("\n")) {
            leaf = new LeafElement(str);
            RunAttr.instance().setRunAttribute(book, i, (Element) null, leaf.getAttribute(), attrLayout);
            leaf.setStartOffset((long) offset);
            int length = offset + str.length();
            offset = length;
            leaf.setEndOffset((long) length);
            paraElem.appendLeaf(leaf);
            return;
        }
        int indexOf = str.indexOf(10);
        while (true) {
            if (indexOf < 0) {
                break;
            }
            offset = processBreakLine(sectionElement, cellStyle, i, b, str.substring(0, indexOf), true);
            int i2 = indexOf + 1;
            if (i2 >= str.length()) {
                str = null;
                break;
            } else {
                str = str.substring(i2, str.length());
                indexOf = str.indexOf(10);
            }
        }
        String str2 = str;
        if (str2 != null) {
            offset = processBreakLine(sectionElement, cellStyle, i, b, str2, true);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
        if (r9 != 7) goto L_0x0022;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int processParagraph(com.app.office.simpletext.model.SectionElement r8, com.app.office.fc.hssf.usermodel.HSSFTextbox r9) {
        /*
            r0 = 0
            offset = r0
            com.app.office.fc.hssf.usermodel.HSSFRichTextString r1 = r9.getString()
            java.lang.String r2 = r1.getString()
            short r9 = r9.getHorizontalAlignment()
            r3 = 2
            r4 = 1
            if (r9 == r4) goto L_0x0022
            if (r9 == r3) goto L_0x0021
            r5 = 3
            if (r9 == r5) goto L_0x001f
            r3 = 4
            if (r9 == r3) goto L_0x0021
            r3 = 7
            if (r9 == r3) goto L_0x0021
            goto L_0x0022
        L_0x001f:
            r0 = 2
            goto L_0x0022
        L_0x0021:
            r0 = 1
        L_0x0022:
            com.app.office.simpletext.model.ParagraphElement r9 = new com.app.office.simpletext.model.ParagraphElement
            r9.<init>()
            paraElem = r9
            int r3 = offset
            long r5 = (long) r3
            r9.setStartOffset(r5)
            com.app.office.simpletext.model.AttributeSetImpl r9 = new com.app.office.simpletext.model.AttributeSetImpl
            r9.<init>()
            attrLayout = r9
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.ParagraphElement r3 = paraElem
            com.app.office.simpletext.model.IAttributeSet r3 = r3.getAttribute()
            r9.setParaHorizontalAlign(r3, r0)
            com.app.office.fc.hssf.record.common.UnicodeString r9 = r1.getUnicodeString()
            java.util.Iterator r9 = r9.formatIterator()
            java.lang.Object r1 = r9.next()
            com.app.office.fc.hssf.record.common.UnicodeString$FormatRun r1 = (com.app.office.fc.hssf.record.common.UnicodeString.FormatRun) r1
        L_0x0051:
            boolean r3 = r9.hasNext()
            r5 = 0
            if (r3 == 0) goto L_0x007e
            java.lang.Object r3 = r9.next()
            com.app.office.fc.hssf.record.common.UnicodeString$FormatRun r3 = (com.app.office.fc.hssf.record.common.UnicodeString.FormatRun) r3
            short r6 = r3.getCharacterPos()
            int r7 = r2.length()
            if (r6 <= r7) goto L_0x0069
            goto L_0x007e
        L_0x0069:
            short r6 = r1.getCharacterPos()
            short r7 = r3.getCharacterPos()
            java.lang.String r6 = r2.substring(r6, r7)
            short r1 = r1.getFontIndex()
            processParagraph_SubString(r8, r5, r6, r1, r0)
            r1 = r3
            goto L_0x0051
        L_0x007e:
            short r9 = r1.getCharacterPos()
            java.lang.String r9 = r2.substring(r9)
            short r1 = r1.getFontIndex()
            processParagraph_SubString(r8, r5, r9, r1, r0)
            com.app.office.simpletext.model.LeafElement r9 = leaf
            if (r9 == 0) goto L_0x00cb
            com.app.office.simpletext.model.ParagraphElement r0 = paraElem
            long r1 = r9.getStartOffset()
            com.app.office.simpletext.model.IElement r9 = r0.getLeaf(r1)
            if (r9 != 0) goto L_0x00cb
            com.app.office.simpletext.model.LeafElement r9 = leaf
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            com.app.office.simpletext.model.LeafElement r1 = leaf
            java.lang.String r1 = r1.getText(r5)
            r0.append(r1)
            java.lang.String r1 = "\n"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r9.setText(r0)
            int r9 = offset
            int r9 = r9 + r4
            offset = r9
            com.app.office.simpletext.model.LeafElement r0 = leaf
            long r1 = (long) r9
            r0.setEndOffset(r1)
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            com.app.office.simpletext.model.LeafElement r0 = leaf
            r9.appendLeaf(r0)
        L_0x00cb:
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            if (r9 == 0) goto L_0x00e8
            long r0 = r9.getStartOffset()
            com.app.office.simpletext.model.IElement r9 = r8.getElement(r0)
            if (r9 != 0) goto L_0x00e8
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            int r0 = offset
            long r0 = (long) r0
            r9.setEndOffset(r0)
            com.app.office.simpletext.model.ParagraphElement r9 = paraElem
            r0 = 0
            r8.appendParagraph(r9, r0)
        L_0x00e8:
            int r8 = offset
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.SectionElementFactory.processParagraph(com.app.office.simpletext.model.SectionElement, com.app.office.fc.hssf.usermodel.HSSFTextbox):int");
    }

    private static int processBreakLine(SectionElement sectionElement, CellStyle cellStyle, int i, byte b, String str, boolean z) {
        SectionElement sectionElement2 = sectionElement;
        CellStyle cellStyle2 = cellStyle;
        byte b2 = b;
        String str2 = str;
        if (str2 == null || str.length() == 0) {
            LeafElement leafElement = leaf;
            if (leafElement != null) {
                leafElement.setText(leaf.getText((IDocument) null) + "\n");
                int i2 = offset + 1;
                offset = i2;
                leaf.setEndOffset((long) i2);
            } else {
                leaf = new LeafElement("\n");
                RunAttr.instance().setRunAttribute(book, i, (Element) null, leaf.getAttribute(), attrLayout);
                leaf.setStartOffset((long) offset);
                int i3 = offset + 1;
                offset = i3;
                leaf.setEndOffset((long) i3);
                paraElem.appendLeaf(leaf);
            }
            paraElem.setEndOffset((long) offset);
            sectionElement.appendParagraph(paraElem, 0);
            ParagraphElement paragraphElement = new ParagraphElement();
            paraElem = paragraphElement;
            paragraphElement.setStartOffset((long) offset);
            attrLayout = new AttributeSetImpl();
            ParaAttr.instance().setParaAttribute(cellStyle2, paraElem.getAttribute(), attrLayout);
            AttrManage.instance().setParaHorizontalAlign(paraElem.getAttribute(), b2);
            leaf = null;
        } else {
            leaf = new LeafElement(str2);
            RunAttr.instance().setRunAttribute(book, i, (Element) null, leaf.getAttribute(), attrLayout);
            leaf.setStartOffset((long) offset);
            int length = offset + str.length();
            offset = length;
            leaf.setEndOffset((long) length);
            paraElem.appendLeaf(leaf);
            if (z) {
                LeafElement leafElement2 = leaf;
                leafElement2.setText(leaf.getText((IDocument) null) + "\n");
                int i4 = offset + 1;
                offset = i4;
                leaf.setEndOffset((long) i4);
                paraElem.setEndOffset((long) offset);
                sectionElement.appendParagraph(paraElem, 0);
                ParagraphElement paragraphElement2 = new ParagraphElement();
                paraElem = paragraphElement2;
                paragraphElement2.setStartOffset((long) offset);
                attrLayout = new AttributeSetImpl();
                ParaAttr.instance().setParaAttribute(cellStyle2, paraElem.getAttribute(), attrLayout);
                AttrManage.instance().setParaHorizontalAlign(paraElem.getAttribute(), b2);
                leaf = null;
            }
        }
        return offset;
    }

    private static void dispose() {
        leaf = null;
        paraElem = null;
        book = null;
        offset = 0;
        attrLayout = null;
    }
}
