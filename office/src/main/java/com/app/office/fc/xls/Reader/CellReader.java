package com.app.office.fc.xls.Reader;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ppt.attribute.ParaAttr;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.simpletext.model.AttributeSetImpl;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.util.ReferenceUtil;
import java.util.List;

public class CellReader {
    private static final short CELLTYPE_BOOLEAN = 0;
    private static final short CELLTYPE_ERROR = 2;
    private static final short CELLTYPE_INLINESTRING = 5;
    private static final short CELLTYPE_NUMBER = 1;
    private static final short CELLTYPE_SHAREDSTRING = 3;
    private static final short CELLTYPE_STRING = 4;
    private static CellReader reader = new CellReader();
    private AttributeSetImpl attrLayout;
    private LeafElement leaf;
    private int offset;
    private ParagraphElement paraElem;

    public static CellReader instance() {
        return reader;
    }

    private boolean isValidateCell(Sheet sheet, Element element) {
        if (element.attributeValue("t") != null || element.element("v") != null) {
            return true;
        }
        Workbook workbook = sheet.getWorkbook();
        if (element.attributeValue(HtmlTags.S) == null) {
            String attributeValue = element.attributeValue("r");
            int columnIndex = ReferenceUtil.instance().getColumnIndex(attributeValue);
            Row row = sheet.getRow(ReferenceUtil.instance().getRowIndex(attributeValue));
            if ((row == null || !Workbook.isValidateStyle(workbook.getCellStyle(row.getRowStyle()))) && !Workbook.isValidateStyle(workbook.getCellStyle(columnIndex))) {
                return false;
            }
            return true;
        } else if (Workbook.isValidateStyle(workbook.getCellStyle(Integer.parseInt(element.attributeValue(HtmlTags.S))))) {
            return true;
        } else {
            return false;
        }
    }

    public Cell getCell(Sheet sheet, Element element) {
        Cell cell;
        int i;
        if (!isValidateCell(sheet, element)) {
            return null;
        }
        short cellType = getCellType(element.attributeValue("t"));
        boolean z = false;
        if (cellType != 0) {
            cell = cellType != 1 ? (cellType == 2 || cellType == 3 || cellType == 4 || cellType == 5) ? new Cell(1) : new Cell(3) : new Cell(0);
        } else {
            cell = new Cell(4);
        }
        String attributeValue = element.attributeValue("r");
        cell.setColNumber(ReferenceUtil.instance().getColumnIndex(attributeValue));
        cell.setRowNumber(ReferenceUtil.instance().getRowIndex(attributeValue));
        Workbook workbook = sheet.getWorkbook();
        if (element.attributeValue(HtmlTags.S) != null) {
            i = Integer.parseInt(element.attributeValue(HtmlTags.S));
        } else {
            i = sheet.getColumnStyle(cell.getColNumber());
        }
        cell.setCellStyle(i);
        Element element2 = element.element("v");
        if (element2 != null) {
            String text = element2.getText();
            if (cellType == 3) {
                int parseInt = Integer.parseInt(text);
                Object sharedItem = workbook.getSharedItem(parseInt);
                if (sharedItem instanceof Element) {
                    cell.setSheet(sheet);
                    parseInt = workbook.addSharedString(processComplexSST(cell, (Element) sharedItem));
                }
                cell.setCellValue(Integer.valueOf(parseInt));
            } else if (cellType == 4 || cellType == 2) {
                cell.setCellValue(Integer.valueOf(workbook.addSharedString(text)));
            } else if (cellType == 1) {
                cell.setCellValue(Double.valueOf(Double.parseDouble(text)));
            } else if (cellType == 0) {
                if (Integer.parseInt(text) != 0) {
                    z = true;
                }
                cell.setCellValue(Boolean.valueOf(z));
            } else {
                cell.setCellValue(text);
            }
        }
        return cell;
    }

    private short getCellType(String str) {
        if (str == null || str.equalsIgnoreCase(NotificationBundleProcessor.PUSH_MINIFIED_BUTTON_TEXT)) {
            return 1;
        }
        if (str.equalsIgnoreCase(HtmlTags.B)) {
            return 0;
        }
        if (str.equalsIgnoreCase(HtmlTags.S)) {
            return 3;
        }
        if (str.equalsIgnoreCase("str")) {
            return 4;
        }
        return str.equalsIgnoreCase("inlineStr") ? (short) 5 : 2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0046, code lost:
        if (r4 != 3) goto L_0x004d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.simpletext.model.SectionElement processComplexSST(com.app.office.ss.model.baseModel.Cell r10, com.app.office.fc.dom4j.Element r11) {
        /*
            r9 = this;
            com.app.office.simpletext.model.SectionElement r0 = new com.app.office.simpletext.model.SectionElement
            r0.<init>()
            r1 = 0
            r0.setStartOffset(r1)
            com.app.office.simpletext.model.IAttributeSet r3 = r0.getAttribute()
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r5 = 1106247680(0x41f00000, float:30.0)
            int r6 = java.lang.Math.round(r5)
            r4.setPageMarginLeft(r3, r6)
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            int r5 = java.lang.Math.round(r5)
            r4.setPageMarginRight(r3, r5)
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r5 = 0
            r4.setPageMarginTop(r3, r5)
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r4.setPageMarginBottom(r3, r5)
            com.app.office.ss.model.style.CellStyle r4 = r10.getCellStyle()
            short r4 = r4.getVerticalAlign()
            r6 = 3
            r7 = 2
            r8 = 1
            if (r4 == 0) goto L_0x004d
            if (r4 == r8) goto L_0x004b
            if (r4 == r7) goto L_0x0049
            if (r4 == r6) goto L_0x004e
            goto L_0x004d
        L_0x0049:
            r6 = 2
            goto L_0x004e
        L_0x004b:
            r6 = 1
            goto L_0x004e
        L_0x004d:
            r6 = 0
        L_0x004e:
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r4.setPageVerticalAlign(r3, r6)
            com.app.office.ss.model.style.CellStyle r3 = r10.getCellStyle()
            short r3 = r3.getFontIndex()
            r9.offset = r5
            com.app.office.simpletext.model.ParagraphElement r4 = new com.app.office.simpletext.model.ParagraphElement
            r4.<init>()
            r9.paraElem = r4
            int r6 = r9.offset
            long r6 = (long) r6
            r4.setStartOffset(r6)
            com.app.office.simpletext.model.AttributeSetImpl r4 = new com.app.office.simpletext.model.AttributeSetImpl
            r4.<init>()
            r9.attrLayout = r4
            com.app.office.fc.ppt.attribute.ParaAttr r4 = com.app.office.fc.ppt.attribute.ParaAttr.instance()
            com.app.office.ss.model.style.CellStyle r6 = r10.getCellStyle()
            com.app.office.simpletext.model.ParagraphElement r7 = r9.paraElem
            com.app.office.simpletext.model.IAttributeSet r7 = r7.getAttribute()
            com.app.office.simpletext.model.AttributeSetImpl r8 = r9.attrLayout
            r4.setParaAttribute(r6, r7, r8)
            com.app.office.simpletext.model.ParagraphElement r10 = r9.processRun(r10, r0, r11, r3)
            r9.paraElem = r10
            int r11 = r9.offset
            long r3 = (long) r11
            r10.setEndOffset(r3)
            com.app.office.simpletext.model.ParagraphElement r10 = r9.paraElem
            r0.appendParagraph(r10, r1)
            int r10 = r9.offset
            long r10 = (long) r10
            r0.setEndOffset(r10)
            r9.offset = r5
            r10 = 0
            r9.paraElem = r10
            r9.attrLayout = r10
            r9.leaf = r10
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.xls.Reader.CellReader.processComplexSST(com.app.office.ss.model.baseModel.Cell, com.app.office.fc.dom4j.Element):com.app.office.simpletext.model.SectionElement");
    }

    private ParagraphElement processRun(Cell cell, SectionElement sectionElement, Element element, int i) {
        Element element2;
        Workbook workbook = cell.getSheet().getWorkbook();
        List<Element> elements = element.elements();
        boolean z = !cell.getCellStyle().isWrapText();
        if (elements.size() == 0) {
            this.leaf = new LeafElement("\n");
            RunAttr.instance().setRunAttribute(workbook, i, (Element) null, this.leaf.getAttribute(), this.attrLayout);
            this.leaf.setStartOffset((long) this.offset);
            int i2 = this.offset + 1;
            this.offset = i2;
            this.leaf.setEndOffset((long) i2);
            this.paraElem.appendLeaf(this.leaf);
            return this.paraElem;
        }
        for (Element element3 : elements) {
            if (element3.getName().equalsIgnoreCase("r") && (element2 = element3.element("t")) != null) {
                String text = element2.getText();
                if (text.length() > 0) {
                    if (z) {
                        String replace = text.replace("\n", "");
                        this.leaf = new LeafElement(replace);
                        RunAttr.instance().setRunAttribute(workbook, i, element3.element("rPr"), this.leaf.getAttribute(), this.attrLayout);
                        this.leaf.setStartOffset((long) this.offset);
                        int length = this.offset + replace.length();
                        this.offset = length;
                        this.leaf.setEndOffset((long) length);
                        this.paraElem.appendLeaf(this.leaf);
                    } else if (!text.contains("\n")) {
                        this.leaf = new LeafElement(text);
                        RunAttr.instance().setRunAttribute(workbook, i, element3.element("rPr"), this.leaf.getAttribute(), this.attrLayout);
                        this.leaf.setStartOffset((long) this.offset);
                        int length2 = this.offset + text.length();
                        this.offset = length2;
                        this.leaf.setEndOffset((long) length2);
                        this.paraElem.appendLeaf(this.leaf);
                    } else {
                        processBreakLine(cell, sectionElement, i, element3, text);
                    }
                }
            }
        }
        LeafElement leafElement = this.leaf;
        if (leafElement != null) {
            leafElement.setText(this.leaf.getText((IDocument) null) + "\n");
            this.offset = this.offset + 1;
        }
        return this.paraElem;
    }

    private void processBreakLine(Cell cell, SectionElement sectionElement, int i, Element element, String str) {
        int i2;
        SectionElement sectionElement2 = sectionElement;
        Element element2 = element;
        String str2 = str;
        Workbook workbook = cell.getSheet().getWorkbook();
        if (str2 != null && str.length() != 0) {
            int length = str.length();
            if (str2.charAt(0) == 10) {
                LeafElement leafElement = this.leaf;
                if (leafElement != null) {
                    leafElement.setText(this.leaf.getText((IDocument) null) + "\n");
                    this.offset = this.offset + 1;
                    i2 = 1;
                } else {
                    this.leaf = new LeafElement("\n");
                    i2 = 1;
                    RunAttr.instance().setRunAttribute(workbook, i, element2.element("rPr"), this.leaf.getAttribute(), this.attrLayout);
                    this.leaf.setStartOffset((long) this.offset);
                    int i3 = this.offset + 1;
                    this.offset = i3;
                    this.leaf.setEndOffset((long) i3);
                    this.paraElem.appendLeaf(this.leaf);
                }
                this.paraElem.setEndOffset((long) this.offset);
                sectionElement2.appendParagraph(this.paraElem, 0);
                this.leaf = null;
                String substring = str2.substring(i2);
                ParagraphElement paragraphElement = new ParagraphElement();
                this.paraElem = paragraphElement;
                paragraphElement.setStartOffset((long) this.offset);
                this.attrLayout = new AttributeSetImpl();
                ParaAttr.instance().setParaAttribute(cell.getCellStyle(), this.paraElem.getAttribute(), this.attrLayout);
                processBreakLine(cell, sectionElement, i, element, substring);
            } else if (str2.charAt(length - 1) == 10) {
                this.leaf = new LeafElement(str2.substring(0, str2.indexOf("\n") + 1));
                RunAttr.instance().setRunAttribute(workbook, i, element2.element("rPr"), this.leaf.getAttribute(), this.attrLayout);
                this.leaf.setStartOffset((long) this.offset);
                int length2 = this.offset + this.leaf.getText((IDocument) null).length();
                this.offset = length2;
                this.leaf.setEndOffset((long) length2);
                this.paraElem.appendLeaf(this.leaf);
                this.paraElem.setEndOffset((long) this.offset);
                sectionElement2.appendParagraph(this.paraElem, 0);
                processBreakLine(cell, sectionElement, i, element, str2.substring(str2.indexOf("\n") + 1));
            } else {
                String str3 = "\n";
                String[] split = str2.split(str3);
                int length3 = split.length;
                String str4 = split[0] + str3;
                this.leaf = new LeafElement(str4);
                String str5 = "rPr";
                RunAttr.instance().setRunAttribute(workbook, i, element2.element("rPr"), this.leaf.getAttribute(), this.attrLayout);
                this.leaf.setStartOffset((long) this.offset);
                int length4 = this.offset + str4.length();
                this.offset = length4;
                this.leaf.setEndOffset((long) length4);
                this.paraElem.appendLeaf(this.leaf);
                this.paraElem.setEndOffset((long) this.offset);
                sectionElement2.appendParagraph(this.paraElem, 0);
                int i4 = 1;
                while (true) {
                    int i5 = length3 - 1;
                    if (i4 < i5) {
                        ParagraphElement paragraphElement2 = new ParagraphElement();
                        this.paraElem = paragraphElement2;
                        paragraphElement2.setStartOffset((long) this.offset);
                        this.attrLayout = new AttributeSetImpl();
                        ParaAttr.instance().setParaAttribute(cell.getCellStyle(), this.paraElem.getAttribute(), this.attrLayout);
                        String str6 = split[i4] + str3;
                        this.leaf = new LeafElement(str6);
                        String str7 = str5;
                        String str8 = str7;
                        RunAttr.instance().setRunAttribute(workbook, i, element2.element(str7), this.leaf.getAttribute(), this.attrLayout);
                        this.leaf.setStartOffset((long) this.offset);
                        int length5 = this.offset + str6.length();
                        this.offset = length5;
                        this.leaf.setEndOffset((long) length5);
                        this.paraElem.appendLeaf(this.leaf);
                        this.paraElem.setEndOffset((long) this.offset);
                        sectionElement2.appendParagraph(this.paraElem, 0);
                        i4++;
                        str5 = str8;
                    } else {
                        ParagraphElement paragraphElement3 = new ParagraphElement();
                        this.paraElem = paragraphElement3;
                        paragraphElement3.setStartOffset((long) this.offset);
                        this.attrLayout = new AttributeSetImpl();
                        ParaAttr.instance().setParaAttribute(cell.getCellStyle(), this.paraElem.getAttribute(), this.attrLayout);
                        String str9 = split[i5];
                        this.leaf = new LeafElement(str9);
                        RunAttr.instance().setRunAttribute(workbook, i, element2.element(str5), this.leaf.getAttribute(), this.attrLayout);
                        this.leaf.setStartOffset((long) this.offset);
                        int length6 = this.offset + str9.length();
                        this.offset = length6;
                        this.leaf.setEndOffset((long) length6);
                        this.paraElem.appendLeaf(this.leaf);
                        return;
                    }
                }
            }
        }
    }

    public boolean searchContent(Element element, String str) {
        Element element2 = element.element("v");
        return (element2 == null || getCellType(element.attributeValue("t")) == 3 || !element2.getText().toLowerCase().contains(str)) ? false : true;
    }
}
