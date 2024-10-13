package com.app.office.fc.doc;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.common.PaintKit;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.bookmark.Bookmark;
import com.app.office.common.borders.Border;
import com.app.office.common.borders.Borders;
import com.app.office.common.borders.Line;
import com.app.office.common.bulletnumber.ListData;
import com.app.office.common.bulletnumber.ListLevel;
import com.app.office.common.picture.Picture;
import com.app.office.common.pictureefftect.PictureEffectInfoFactory;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.LineShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.WPAutoShape;
import com.app.office.common.shape.WPGroupShape;
import com.app.office.common.shape.WPPictureShape;
import com.app.office.constant.wp.WPModelConstant;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherTextboxRecord;
import com.app.office.fc.hwpf.HWPFDocument;
import com.app.office.fc.hwpf.model.ListTables;
import com.app.office.fc.hwpf.model.POIListData;
import com.app.office.fc.hwpf.model.POIListLevel;
import com.app.office.fc.hwpf.model.PicturesTable;
import com.app.office.fc.hwpf.usermodel.Bookmarks;
import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.hwpf.usermodel.CharacterRun;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.hwpf.usermodel.HWPFAutoShape;
import com.app.office.fc.hwpf.usermodel.HWPFShape;
import com.app.office.fc.hwpf.usermodel.HWPFShapeGroup;
import com.app.office.fc.hwpf.usermodel.HeaderStories;
import com.app.office.fc.hwpf.usermodel.InlineWordArt;
import com.app.office.fc.hwpf.usermodel.LineSpacingDescriptor;
import com.app.office.fc.hwpf.usermodel.OfficeDrawing;
import com.app.office.fc.hwpf.usermodel.POIBookmark;
import com.app.office.fc.hwpf.usermodel.Paragraph;
import com.app.office.fc.hwpf.usermodel.PictureType;
import com.app.office.fc.hwpf.usermodel.Range;
import com.app.office.fc.hwpf.usermodel.Section;
import com.app.office.fc.hwpf.usermodel.Table;
import com.app.office.fc.hwpf.usermodel.TableCell;
import com.app.office.fc.hwpf.usermodel.TableRow;
import com.app.office.fc.openxml4j.opc.ContentTypes;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.util.Arrays;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.ss.util.ModelUtil;
import com.app.office.system.AbstractReader;
import com.app.office.system.IControl;
import com.app.office.wp.model.CellElement;
import com.app.office.wp.model.HFElement;
import com.app.office.wp.model.RowElement;
import com.app.office.wp.model.TableElement;
import com.app.office.wp.model.WPDocument;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public class DOCReader extends AbstractReader {
    private List<Bookmark> bms = new ArrayList();
    private long docRealOffset;
    private String filePath;
    private String hyperlinkAddress;
    private Pattern hyperlinkPattern = Pattern.compile("[ \\t\\r\\n]*HYPERLINK \"(.*)\"[ \\t\\r\\n]*");
    private boolean isBreakChar;
    private long offset;
    private HWPFDocument poiDoc;
    private long textboxIndex;
    private WPDocument wpdoc;

    private int converterColorForIndex(short s) {
        switch (s) {
            case 1:
                return -16777216;
            case 2:
                return -16776961;
            case 3:
                return -16711681;
            case 4:
                return -16711936;
            case 5:
                return -65281;
            case 6:
                return SupportMenu.CATEGORY_MASK;
            case 7:
                return InputDeviceCompat.SOURCE_ANY;
            case 9:
                return -16776961;
            case 10:
                return -12303292;
            case 11:
                return -16711936;
            case 12:
                return -65281;
            case 13:
                return SupportMenu.CATEGORY_MASK;
            case 14:
                return InputDeviceCompat.SOURCE_ANY;
            case 15:
                return -7829368;
            case 16:
                return -3355444;
            default:
                return -1;
        }
    }

    private byte converterParaHorAlign(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 5) {
                    if (i != 8) {
                        return 0;
                    }
                }
            }
            return 2;
        }
        return 1;
    }

    public DOCReader(IControl iControl, String str) {
        this.control = iControl;
        this.filePath = str;
    }

    public Object getModel() throws Exception {
        WPDocument wPDocument = this.wpdoc;
        if (wPDocument != null) {
            return wPDocument;
        }
        this.wpdoc = new WPDocument();
        processDoc();
        return this.wpdoc;
    }

    private void processDoc() throws Exception {
        IElement leaf;
        String text;
        this.poiDoc = new HWPFDocument(new FileInputStream(new File(this.filePath)));
        processBulletNumber();
        processBookmark();
        this.offset = 0;
        this.docRealOffset = 0;
        Range range = this.poiDoc.getRange();
        int numSections = range.numSections();
        for (int i = 0; i < numSections && !this.abortReader; i++) {
            processSection(range.getSection(i));
            if (this.isBreakChar && (leaf = this.wpdoc.getLeaf(this.offset - 1)) != null && (leaf instanceof LeafElement) && (text = leaf.getText(this.wpdoc)) != null && text.length() == 1 && text.charAt(0) == 12) {
                ((LeafElement) leaf).setText(String.valueOf(10));
            }
        }
        processHeaderFooter();
    }

    private void processBookmark() {
        Bookmarks bookmarks = this.poiDoc.getBookmarks();
        if (bookmarks != null) {
            for (int i = 0; i < bookmarks.getBookmarksCount(); i++) {
                POIBookmark bookmark = bookmarks.getBookmark(i);
                Bookmark bookmark2 = new Bookmark(bookmark.getName(), (long) bookmark.getStart(), (long) bookmark.getEnd());
                this.control.getSysKit().getBookmarkManage().addBookmark(bookmark2);
                this.bms.add(bookmark2);
            }
        }
    }

    private void processBulletNumber() {
        ListTables listTables = this.poiDoc.getListTables();
        if (listTables != null) {
            int overrideCount = listTables.getOverrideCount();
            int i = 0;
            while (i < overrideCount) {
                ListData listData = new ListData();
                i++;
                POIListData listData2 = listTables.getListData(listTables.getOverride(i).getLsid());
                if (listData2 != null) {
                    listData.setListID(listData2.getLsid());
                    POIListLevel[] levels = listData2.getLevels();
                    int length = levels.length;
                    ListLevel[] listLevelArr = new ListLevel[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        listLevelArr[i2] = new ListLevel();
                        processListLevel(levels[i2], listLevelArr[i2]);
                    }
                    listData.setLevels(listLevelArr);
                    listData.setSimpleList((byte) length);
                    this.control.getSysKit().getListManage().putListData(Integer.valueOf(listData.getListID()), listData);
                }
            }
        }
    }

    private void processListLevel(POIListLevel pOIListLevel, ListLevel listLevel) {
        listLevel.setStartAt(pOIListLevel.getStartAt());
        listLevel.setAlign((byte) pOIListLevel.getAlignment());
        listLevel.setFollowChar(pOIListLevel.getTypeOfCharFollowingTheNumber());
        listLevel.setNumberFormat(pOIListLevel.getNumberFormat());
        listLevel.setNumberText(converterNumberChar(pOIListLevel.getNumberChar()));
        listLevel.setSpecialIndent(pOIListLevel.getSpecialIndnet());
        listLevel.setTextIndent(pOIListLevel.getTextIndent());
    }

    private char[] converterNumberChar(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        for (int i = 0; i < cArr.length; i++) {
            if (cArr[i] == 61548) {
                cArr[i] = 9679;
            } else if (cArr[i] == 61550) {
                cArr[i] = 9632;
            } else if (cArr[i] == 61557) {
                cArr[i] = 9670;
            } else if (cArr[i] == 61692) {
                cArr[i] = 8730;
            } else if (cArr[i] == 61656) {
                cArr[i] = 9733;
            } else if (cArr[i] == 61618) {
                cArr[i] = 9734;
            } else if (cArr[i] >= 61536) {
                cArr[i] = 9679;
            }
        }
        return cArr;
    }

    private void processSection(Section section) {
        SectionElement sectionElement = new SectionElement();
        IAttributeSet attribute = sectionElement.getAttribute();
        AttrManage.instance().setPageWidth(attribute, section.getPageWidth());
        AttrManage.instance().setPageHeight(attribute, section.getPageHeight());
        AttrManage.instance().setPageMarginLeft(attribute, section.getMarginLeft());
        AttrManage.instance().setPageMarginRight(attribute, section.getMarginRight());
        AttrManage.instance().setPageMarginTop(attribute, section.getMarginTop());
        AttrManage.instance().setPageMarginBottom(attribute, section.getMarginBottom());
        AttrManage.instance().setPageHeaderMargin(attribute, section.getMarginHeader());
        AttrManage.instance().setPageFooterMargin(attribute, section.getMarginFooter());
        if (section.getGridType() != 0) {
            AttrManage.instance().setPageLinePitch(attribute, section.getLinePitch());
        }
        processSectionBorder(sectionElement, section);
        sectionElement.setStartOffset(this.offset);
        int numParagraphs = section.numParagraphs();
        int i = 0;
        while (i < numParagraphs && !this.abortReader) {
            Paragraph paragraph = section.getParagraph(i);
            if (paragraph.isInTable()) {
                Table table = section.getTable(paragraph);
                processTable(table);
                i += table.numParagraphs() - 1;
            } else {
                processParagraph(section.getParagraph(i));
            }
            i++;
        }
        sectionElement.setEndOffset(this.offset);
        this.wpdoc.appendSection(sectionElement);
    }

    private void processSectionBorder(SectionElement sectionElement, Section section) {
        BorderCode topBorder = section.getTopBorder();
        BorderCode bottomBorder = section.getBottomBorder();
        BorderCode leftBorder = section.getLeftBorder();
        BorderCode rightBorder = section.getRightBorder();
        if (topBorder != null || bottomBorder != null || leftBorder != null || rightBorder != null) {
            Borders borders = new Borders();
            borders.setOnType((byte) ((((byte) section.getPageBorderInfo()) >> 5) & 7));
            int i = -16777216;
            if (topBorder != null) {
                Border border = new Border();
                border.setColor(topBorder.getColor() == 0 ? -16777216 : converterColorForIndex(topBorder.getColor()));
                border.setSpace((short) ((int) (((float) topBorder.getSpace()) * 1.3333334f)));
                borders.setTopBorder(border);
            }
            if (bottomBorder != null) {
                Border border2 = new Border();
                border2.setColor(bottomBorder.getColor() == 0 ? -16777216 : converterColorForIndex(bottomBorder.getColor()));
                border2.setSpace((short) ((int) (((float) bottomBorder.getSpace()) * 1.3333334f)));
                borders.setBottomBorder(border2);
            }
            if (leftBorder != null) {
                Border border3 = new Border();
                border3.setColor(leftBorder.getColor() == 0 ? -16777216 : converterColorForIndex(leftBorder.getColor()));
                border3.setSpace((short) ((int) (((float) leftBorder.getSpace()) * 1.3333334f)));
                borders.setLeftBorder(border3);
            }
            if (rightBorder != null) {
                Border border4 = new Border();
                if (rightBorder.getColor() != 0) {
                    i = converterColorForIndex(rightBorder.getColor());
                }
                border4.setColor(i);
                border4.setSpace((short) ((int) (((float) rightBorder.getSpace()) * 1.3333334f)));
                borders.setRightBorder(border4);
            }
            AttrManage.instance().setPageBorder(sectionElement.getAttribute(), this.control.getSysKit().getBordersManage().addBorders(borders));
        }
    }

    private void processHeaderFooter() {
        HeaderStories headerStories = new HeaderStories(this.poiDoc);
        this.offset = 1152921504606846976L;
        this.docRealOffset = 1152921504606846976L;
        Range oddHeaderSubrange = headerStories.getOddHeaderSubrange();
        if (oddHeaderSubrange != null) {
            processHeaderFooterPara(oddHeaderSubrange, 5, (byte) 1);
        }
        this.offset = 2305843009213693952L;
        this.docRealOffset = 2305843009213693952L;
        Range oddFooterSubrange = headerStories.getOddFooterSubrange();
        if (oddFooterSubrange != null) {
            processHeaderFooterPara(oddFooterSubrange, 6, (byte) 1);
        }
    }

    private void processHeaderFooterPara(Range range, short s, byte b) {
        HFElement hFElement = new HFElement(s, b);
        hFElement.setStartOffset(this.offset);
        int numParagraphs = range.numParagraphs();
        int i = 0;
        while (i < numParagraphs && !this.abortReader) {
            Paragraph paragraph = range.getParagraph(i);
            if (paragraph.isInTable()) {
                Table table = range.getTable(paragraph);
                processTable(table);
                i += table.numParagraphs() - 1;
            } else {
                processParagraph(paragraph);
            }
            i++;
        }
        hFElement.setEndOffset(this.offset);
        this.wpdoc.appendElement(hFElement, this.offset);
    }

    private void processTable(Table table) {
        TableElement tableElement = new TableElement();
        tableElement.setStartOffset(this.offset);
        Vector vector = new Vector();
        int numRows = table.numRows();
        for (int i = 0; i < numRows; i++) {
            TableRow row = table.getRow(i);
            if (i == 0) {
                processTableAttribute(row, tableElement.getAttribute());
            }
            RowElement rowElement = new RowElement();
            rowElement.setStartOffset(this.offset);
            processRowAttribute(row, rowElement.getAttribute());
            int numCells = row.numCells();
            int i2 = 0;
            for (int i3 = 0; i3 < numCells; i3++) {
                TableCell cell = row.getCell(i3);
                cell.isBackward();
                CellElement cellElement = new CellElement();
                cellElement.setStartOffset(this.offset);
                processCellAttribute(cell, cellElement.getAttribute());
                int numParagraphs = cell.numParagraphs();
                for (int i4 = 0; i4 < numParagraphs; i4++) {
                    processParagraph(cell.getParagraph(i4));
                }
                cellElement.setEndOffset(this.offset);
                if (this.offset > cellElement.getStartOffset()) {
                    rowElement.appendCell(cellElement);
                }
                i2 += cell.getWidth();
                if (!vector.contains(Integer.valueOf(i2))) {
                    vector.add(Integer.valueOf(i2));
                }
            }
            rowElement.setEndOffset(this.offset);
            if (this.offset > rowElement.getStartOffset()) {
                tableElement.appendRow(rowElement);
            }
        }
        tableElement.setEndOffset(this.offset);
        if (this.offset > tableElement.getStartOffset()) {
            this.wpdoc.appendParagraph(tableElement, this.offset);
            int size = vector.size();
            int[] iArr = new int[size];
            for (int i5 = 0; i5 < size; i5++) {
                iArr[i5] = ((Integer) vector.get(i5)).intValue();
            }
            Arrays.sort(iArr);
            RowElement rowElement2 = (RowElement) tableElement.getElementForIndex(0);
            int i6 = 1;
            while (rowElement2 != null) {
                IElement elementForIndex = rowElement2.getElementForIndex(0);
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                while (elementForIndex != null) {
                    i7 += AttrManage.instance().getTableCellWidth(elementForIndex.getAttribute());
                    while (true) {
                        if (i9 < size) {
                            if (i7 <= iArr[i9]) {
                                i9++;
                                break;
                            }
                            i8++;
                            rowElement2.insertElementForIndex(new CellElement(), i8);
                            i9++;
                        } else {
                            break;
                        }
                    }
                    i8++;
                    elementForIndex = rowElement2.getElementForIndex(i8);
                }
                RowElement rowElement3 = (RowElement) tableElement.getElementForIndex(i6);
                i6++;
                rowElement2 = rowElement3;
            }
        }
    }

    private void processTableAttribute(TableRow tableRow, IAttributeSet iAttributeSet) {
        if (tableRow.getRowJustification() != 0) {
            AttrManage.instance().setParaHorizontalAlign(iAttributeSet, tableRow.getRowJustification());
        }
        if (tableRow.getTableIndent() != 0) {
            AttrManage.instance().setParaIndentLeft(iAttributeSet, tableRow.getTableIndent());
        }
    }

    private void processRowAttribute(TableRow tableRow, IAttributeSet iAttributeSet) {
        if (tableRow.getRowHeight() != 0) {
            AttrManage.instance().setTableRowHeight(iAttributeSet, tableRow.getRowHeight());
        }
        if (tableRow.isTableHeader()) {
            AttrManage.instance().setTableHeaderRow(iAttributeSet, true);
        }
        if (tableRow.cantSplit()) {
            AttrManage.instance().setTableRowSplit(iAttributeSet, true);
        }
    }

    private void processCellAttribute(TableCell tableCell, IAttributeSet iAttributeSet) {
        if (tableCell.isFirstMerged()) {
            AttrManage.instance().setTableHorFirstMerged(iAttributeSet, true);
        }
        if (tableCell.isMerged()) {
            AttrManage.instance().setTableHorMerged(iAttributeSet, true);
        }
        if (tableCell.isFirstVerticallyMerged()) {
            AttrManage.instance().setTableVerFirstMerged(iAttributeSet, true);
        }
        if (tableCell.isVerticallyMerged()) {
            AttrManage.instance().setTableVerMerged(iAttributeSet, true);
        }
        AttrManage.instance().setTableCellVerAlign(iAttributeSet, tableCell.getVertAlign());
        AttrManage.instance().setTableCellWidth(iAttributeSet, tableCell.getWidth());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: com.app.office.fc.hwpf.usermodel.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: com.app.office.fc.hwpf.usermodel.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: com.app.office.fc.hwpf.usermodel.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: com.app.office.fc.hwpf.usermodel.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: com.app.office.fc.hwpf.usermodel.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processParagraph(com.app.office.fc.hwpf.usermodel.Paragraph r24) {
        /*
            r23 = this;
            r7 = r23
            com.app.office.simpletext.model.ParagraphElement r8 = new com.app.office.simpletext.model.ParagraphElement
            r8.<init>()
            com.app.office.simpletext.model.IAttributeSet r9 = r8.getAttribute()
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getSpacingBefore()
            r0.setParaBefore(r9, r1)
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getSpacingAfter()
            r0.setParaAfter(r9, r1)
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getIndentFromLeft()
            r0.setParaIndentLeft(r9, r1)
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getIndentFromRight()
            r0.setParaIndentRight(r9, r1)
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getJustification()
            byte r1 = r7.converterParaHorAlign(r1)
            r0.setParaHorizontalAlign(r9, r1)
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getFontAlignment()
            r0.setParaVerticalAlign(r9, r1)
            int r0 = r24.getFirstLineIndent()
            r7.converterSpecialIndent(r9, r0)
            com.app.office.fc.hwpf.usermodel.LineSpacingDescriptor r0 = r24.getLineSpacing()
            r7.converterLineSpace(r0, r9)
            int r0 = r24.getIlfo()
            if (r0 <= 0) goto L_0x008d
            com.app.office.fc.hwpf.HWPFDocument r0 = r7.poiDoc
            com.app.office.fc.hwpf.model.ListTables r0 = r0.getListTables()
            if (r0 == 0) goto L_0x008d
            int r1 = r24.getIlfo()
            com.app.office.fc.hwpf.model.ListFormatOverride r0 = r0.getOverride(r1)
            if (r0 == 0) goto L_0x0082
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            int r0 = r0.getLsid()
            r1.setParaListID(r9, r0)
        L_0x0082:
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getIlvl()
            r0.setParaListLevel(r9, r1)
        L_0x008d:
            boolean r0 = r24.isInTable()
            if (r0 == 0) goto L_0x009e
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            int r1 = r24.getTableLevel()
            r0.setParaLevel(r9, r1)
        L_0x009e:
            long r0 = r7.offset
            r8.setStartOffset(r0)
            int r10 = r24.numCharacterRuns()
            long r11 = r7.docRealOffset
            java.lang.String r13 = ""
            r4 = r13
            r5 = r4
            r1 = 0
            r3 = 0
            r6 = 0
            r16 = 0
            r17 = 0
        L_0x00b4:
            if (r3 >= r10) goto L_0x0234
            boolean r0 = r7.abortReader
            if (r0 != 0) goto L_0x0234
            r2 = r24
            com.app.office.fc.hwpf.usermodel.CharacterRun r18 = r2.getCharacterRun(r3)
            java.lang.String r0 = r18.text()
            int r19 = r0.length()
            if (r19 == 0) goto L_0x0225
            boolean r19 = r18.isMarkedDeleted()
            if (r19 == 0) goto L_0x00d2
            goto L_0x0225
        L_0x00d2:
            long r14 = r7.docRealOffset
            int r2 = r0.length()
            r20 = r3
            long r2 = (long) r2
            long r14 = r14 + r2
            r7.docRealOffset = r14
            r2 = 0
            char r3 = r0.charAt(r2)
            int r2 = r0.length()
            r14 = 1
            int r2 = r2 - r14
            char r2 = r0.charAt(r2)
            r15 = 9
            if (r3 != r15) goto L_0x00f7
            int r15 = r0.length()
            if (r15 == r14) goto L_0x0227
        L_0x00f7:
            r14 = 5
            if (r3 != r14) goto L_0x00fc
            goto L_0x0227
        L_0x00fc:
            r14 = 19
            r15 = 21
            if (r3 == r14) goto L_0x01f2
            if (r2 != r14) goto L_0x0106
            goto L_0x01f2
        L_0x0106:
            r14 = 20
            if (r3 == r14) goto L_0x01e4
            if (r2 != r14) goto L_0x010e
            goto L_0x01e4
        L_0x010e:
            if (r3 == r15) goto L_0x0165
            if (r2 != r15) goto L_0x0113
            goto L_0x0165
        L_0x0113:
            if (r16 == 0) goto L_0x012b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r5)
            java.lang.String r1 = r18.text()
            r0.append(r1)
            java.lang.String r5 = r0.toString()
        L_0x0128:
            r0 = 0
            goto L_0x01ef
        L_0x012b:
            if (r17 == 0) goto L_0x0147
            boolean r0 = r7.isPageNumber(r6, r5)
            if (r0 == 0) goto L_0x0147
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r4)
            java.lang.String r1 = r18.text()
            r0.append(r1)
            java.lang.String r4 = r0.toString()
            goto L_0x0128
        L_0x0147:
            r14 = 0
            r15 = 0
            r0 = r23
            r1 = r18
            r2 = r24
            r3 = r6
            r21 = r4
            r4 = r8
            r22 = r5
            r5 = r14
            r14 = r6
            r6 = r15
            r0.processRun(r1, r2, r3, r4, r5, r6)
            r1 = r14
            r6 = r21
            r5 = r22
            r0 = 0
            r19 = 0
            goto L_0x022c
        L_0x0165:
            r21 = r4
            r22 = r5
            r14 = r6
            r6 = r21
            if (r1 == 0) goto L_0x01c5
            if (r6 == 0) goto L_0x01c5
            if (r14 == 0) goto L_0x01c5
            int r3 = r14.getType()
            r4 = 58
            if (r3 != r4) goto L_0x01c5
            java.lang.String r3 = "EQ"
            int r3 = r6.indexOf(r3)
            if (r3 < 0) goto L_0x0198
            java.lang.String r3 = "jc"
            int r3 = r6.indexOf(r3)
            if (r3 < 0) goto L_0x0198
            r0 = r23
            r2 = r24
            r3 = r14
            r4 = r8
            r5 = r22
            r0.processRun(r1, r2, r3, r4, r5, r6)
            r0 = 0
            r15 = 0
            goto L_0x01da
        L_0x0198:
            if (r2 != r15) goto L_0x01b6
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            int r2 = r0.length()
            r4 = 1
            int r2 = r2 - r4
            r15 = 0
            java.lang.String r0 = r0.substring(r15, r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r6 = r0
            goto L_0x01b7
        L_0x01b6:
            r15 = 0
        L_0x01b7:
            r0 = r23
            r1 = r18
            r2 = r24
            r3 = r14
            r4 = r8
            r5 = r22
            r0.processRun(r1, r2, r3, r4, r5, r6)
            goto L_0x01d9
        L_0x01c5:
            r15 = 0
            r5 = r22
            boolean r0 = r7.isPageNumber(r14, r5)
            if (r0 == 0) goto L_0x01d9
            r0 = r23
            r1 = r18
            r2 = r24
            r3 = r14
            r4 = r8
            r0.processRun(r1, r2, r3, r4, r5, r6)
        L_0x01d9:
            r0 = 0
        L_0x01da:
            r7.hyperlinkAddress = r0
            r6 = r0
            r4 = r13
            r5 = r4
            r16 = 0
            r17 = 0
            goto L_0x01ef
        L_0x01e4:
            r14 = r6
            r0 = 0
            r15 = 0
            r6 = r4
            r4 = 1
            r4 = r6
            r6 = r14
            r16 = 0
            r17 = 1
        L_0x01ef:
            r19 = 0
            goto L_0x022e
        L_0x01f2:
            r1 = r6
            r0 = 0
            r19 = 0
            r6 = r4
            r4 = 1
            if (r3 != r15) goto L_0x01fc
            if (r2 == r14) goto L_0x022c
        L_0x01fc:
            long r1 = r7.offset
            r14 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r1 = r1 & r14
            r14 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            int r3 = (r1 > r14 ? 1 : (r1 == r14 ? 0 : -1))
            if (r3 == 0) goto L_0x0211
            r14 = 2305843009213693952(0x2000000000000000, double:1.4916681462400413E-154)
            int r3 = (r1 > r14 ? 1 : (r1 == r14 ? 0 : -1))
            if (r3 != 0) goto L_0x020e
            goto L_0x0211
        L_0x020e:
            com.app.office.fc.hwpf.model.FieldsDocumentPart r1 = com.app.office.fc.hwpf.model.FieldsDocumentPart.MAIN
            goto L_0x0213
        L_0x0211:
            com.app.office.fc.hwpf.model.FieldsDocumentPart r1 = com.app.office.fc.hwpf.model.FieldsDocumentPart.HEADER
        L_0x0213:
            com.app.office.fc.hwpf.HWPFDocument r2 = r7.poiDoc
            com.app.office.fc.hwpf.usermodel.Fields r2 = r2.getFields()
            int r3 = r18.getStartOffset()
            com.app.office.fc.hwpf.usermodel.Field r1 = r2.getFieldByStartOffset(r1, r3)
            r4 = r6
            r16 = 1
            goto L_0x022d
        L_0x0225:
            r20 = r3
        L_0x0227:
            r1 = r6
            r0 = 0
            r19 = 0
            r6 = r4
        L_0x022c:
            r4 = r6
        L_0x022d:
            r6 = r1
        L_0x022e:
            int r3 = r20 + 1
            r1 = r18
            goto L_0x00b4
        L_0x0234:
            short r0 = r24.getTabClearPosition()
            if (r0 <= 0) goto L_0x0245
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            short r1 = r24.getTabClearPosition()
            r0.setParaTabsClearPostion(r9, r1)
        L_0x0245:
            long r0 = r7.offset
            long r2 = r8.getStartOffset()
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x0253
            r8.dispose()
            return
        L_0x0253:
            long r0 = r7.offset
            r8.setEndOffset(r0)
            com.app.office.wp.model.WPDocument r0 = r7.wpdoc
            long r1 = r7.offset
            r0.appendParagraph(r8, r1)
            long r0 = r7.docRealOffset
            r7.adjustBookmarkOffset(r11, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCReader.processParagraph(com.app.office.fc.hwpf.usermodel.Paragraph):void");
    }

    private boolean isPageNumber(Field field, String str) {
        if (field != null && (field.getType() == 33 || field.getType() == 26)) {
            return true;
        }
        if (str == null) {
            return false;
        }
        if (str.contains("NUMPAGES") || str.contains("PAGE")) {
            return true;
        }
        return false;
    }

    private void converterSpecialIndent(IAttributeSet iAttributeSet, int i) {
        AttrManage.instance().setParaSpecialIndent(iAttributeSet, i);
        if (i < 0) {
            AttrManage.instance().setParaIndentLeft(iAttributeSet, AttrManage.instance().getParaIndentLeft(iAttributeSet) + i);
        }
    }

    private void converterLineSpace(LineSpacingDescriptor lineSpacingDescriptor, IAttributeSet iAttributeSet) {
        float f;
        int i = 1;
        if (lineSpacingDescriptor.getMultiLinespace() == 1) {
            f = ((float) lineSpacingDescriptor.getDyaLine()) / 240.0f;
            if (f == 1.0f) {
                i = 0;
                f = 1.0f;
            } else if (((double) f) == 1.5d) {
                f = 1.5f;
            } else {
                if (f == 2.0f) {
                    f = 2.0f;
                }
                i = 2;
            }
        } else {
            f = (float) lineSpacingDescriptor.getDyaLine();
            if (f >= 0.0f) {
                i = 3;
            } else {
                i = 4;
                f = -f;
            }
        }
        AttrManage.instance().setParaLineSpace(iAttributeSet, f);
        AttrManage.instance().setParaLineSpaceType(iAttributeSet, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01da, code lost:
        if (r13.contains("PAGE") != false) goto L_0x01de;
     */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01e0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processRun(com.app.office.fc.hwpf.usermodel.CharacterRun r9, com.app.office.fc.hwpf.usermodel.Range r10, com.app.office.fc.hwpf.usermodel.Field r11, com.app.office.simpletext.model.ParagraphElement r12, java.lang.String r13, java.lang.String r14) {
        /*
            r8 = this;
            java.lang.String r0 = r9.text()
            if (r14 == 0) goto L_0x0007
            goto L_0x0008
        L_0x0007:
            r14 = r0
        L_0x0008:
            r0 = 1
            if (r14 == 0) goto L_0x0065
            int r1 = r14.length()
            if (r1 <= 0) goto L_0x0065
            r1 = 0
            char r2 = r14.charAt(r1)
            r3 = 12
            if (r2 != r3) goto L_0x001c
            r3 = 1
            goto L_0x001d
        L_0x001c:
            r3 = 0
        L_0x001d:
            r8.isBreakChar = r3
            r3 = 8
            if (r2 == r3) goto L_0x0025
            if (r2 != r0) goto L_0x0065
        L_0x0025:
            r10 = 0
        L_0x0026:
            int r11 = r14.length()
            if (r10 >= r11) goto L_0x0064
            boolean r11 = r9.isVanished()
            if (r11 != 0) goto L_0x0064
            char r11 = r14.charAt(r10)
            if (r11 == r3) goto L_0x003a
            if (r11 != r0) goto L_0x0061
        L_0x003a:
            com.app.office.simpletext.model.LeafElement r13 = new com.app.office.simpletext.model.LeafElement
            java.lang.String r2 = java.lang.String.valueOf(r11)
            r13.<init>(r2)
            if (r11 != r3) goto L_0x0047
            r11 = 1
            goto L_0x0048
        L_0x0047:
            r11 = 0
        L_0x0048:
            boolean r11 = r8.processShape(r9, r13, r11, r10)
            if (r11 != 0) goto L_0x004f
            return
        L_0x004f:
            long r4 = r8.offset
            r13.setStartOffset(r4)
            long r4 = r8.offset
            r6 = 1
            long r4 = r4 + r6
            r8.offset = r4
            r13.setEndOffset(r4)
            r12.appendLeaf(r13)
        L_0x0061:
            int r10 = r10 + 1
            goto L_0x0026
        L_0x0064:
            return
        L_0x0065:
            com.app.office.simpletext.model.LeafElement r1 = new com.app.office.simpletext.model.LeafElement
            r1.<init>(r14)
            com.app.office.simpletext.model.IAttributeSet r2 = r1.getAttribute()
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = r9.getFontSize()
            float r4 = (float) r4
            r5 = 1073741824(0x40000000, float:2.0)
            float r4 = r4 / r5
            double r4 = (double) r4
            r6 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            double r4 = r4 + r6
            int r4 = (int) r4
            r3.setFontSize(r2, r4)
            com.app.office.simpletext.font.FontTypefaceManage r3 = com.app.office.simpletext.font.FontTypefaceManage.instance()
            java.lang.String r4 = r9.getFontName()
            int r3 = r3.addFontName(r4)
            if (r3 < 0) goto L_0x0097
            com.app.office.simpletext.model.AttrManage r4 = com.app.office.simpletext.model.AttrManage.instance()
            r4.setFontName(r2, r3)
        L_0x0097:
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = r9.getIco24()
            int r4 = com.app.office.fc.FCKit.BGRtoRGB(r4)
            r3.setFontColor(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            boolean r4 = r9.isBold()
            r3.setFontBold(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            boolean r4 = r9.isItalic()
            r3.setFontItalic(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            boolean r4 = r9.isStrikeThrough()
            r3.setFontStrike(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            boolean r4 = r9.isDoubleStrikeThrough()
            r3.setFontDoubleStrike(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = r9.getUnderlineCode()
            r3.setFontUnderline(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            int r4 = r9.getUnderlineColor()
            int r4 = com.app.office.fc.FCKit.BGRtoRGB(r4)
            r3.setFontUnderlineColr(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            short r4 = r9.getSubSuperScriptIndex()
            r3.setFontScript(r2, r4)
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()
            byte r9 = r9.getHighlightedColor()
            short r9 = (short) r9
            int r9 = r8.converterColorForIndex(r9)
            r3.setFontHighLight(r2, r9)
            r9 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            if (r11 == 0) goto L_0x0168
            int r3 = r11.getType()
            r4 = 88
            if (r3 != r4) goto L_0x0168
            java.lang.String r13 = r8.hyperlinkAddress
            if (r13 != 0) goto L_0x0134
            com.app.office.fc.hwpf.usermodel.Range r10 = r11.firstSubrange(r10)
            if (r10 == 0) goto L_0x0134
            java.lang.String r10 = r10.text()
            java.util.regex.Pattern r11 = r8.hyperlinkPattern
            java.util.regex.Matcher r10 = r11.matcher(r10)
            boolean r11 = r10.find()
            if (r11 == 0) goto L_0x0134
            java.lang.String r10 = r10.group(r0)
            r8.hyperlinkAddress = r10
        L_0x0134:
            java.lang.String r10 = r8.hyperlinkAddress
            if (r10 == 0) goto L_0x01eb
            com.app.office.system.IControl r10 = r8.control
            com.app.office.system.SysKit r10 = r10.getSysKit()
            com.app.office.common.hyperlink.HyperlinkManage r10 = r10.getHyperlinkManage()
            java.lang.String r11 = r8.hyperlinkAddress
            int r10 = r10.addHyperlink(r11, r0)
            if (r10 < 0) goto L_0x01eb
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            r11.setFontColor(r2, r9)
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            r11.setFontUnderline(r2, r0)
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            r11.setFontUnderlineColr(r2, r9)
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            r9.setHyperlinkID(r2, r10)
            goto L_0x01eb
        L_0x0168:
            if (r13 == 0) goto L_0x01eb
            java.lang.String r10 = "HYPERLINK"
            int r10 = r13.indexOf(r10)
            if (r10 <= 0) goto L_0x01b6
            java.lang.String r10 = "_Toc"
            int r10 = r13.indexOf(r10)
            if (r10 <= 0) goto L_0x01eb
            r11 = 34
            int r11 = r13.lastIndexOf(r11)
            if (r11 <= 0) goto L_0x01eb
            if (r11 <= r10) goto L_0x01eb
            java.lang.String r10 = r13.substring(r10, r11)
            com.app.office.system.IControl r11 = r8.control
            com.app.office.system.SysKit r11 = r11.getSysKit()
            com.app.office.common.hyperlink.HyperlinkManage r11 = r11.getHyperlinkManage()
            r13 = 5
            int r10 = r11.addHyperlink(r10, r13)
            if (r10 < 0) goto L_0x01eb
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            r11.setFontColor(r2, r9)
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            r11.setFontUnderline(r2, r0)
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            r11.setFontUnderlineColr(r2, r9)
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            r9.setHyperlinkID(r2, r10)
            goto L_0x01eb
        L_0x01b6:
            long r9 = r8.offset
            r2 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r9 = r9 & r2
            r2 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            int r11 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r11 == 0) goto L_0x01c7
            r2 = 2305843009213693952(0x2000000000000000, double:1.4916681462400413E-154)
            int r11 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r11 != 0) goto L_0x01eb
        L_0x01c7:
            r9 = -1
            if (r13 == 0) goto L_0x01dd
            java.lang.String r10 = "NUMPAGES"
            boolean r10 = r13.contains(r10)
            if (r10 == 0) goto L_0x01d4
            r0 = 2
            goto L_0x01de
        L_0x01d4:
            java.lang.String r10 = "PAGE"
            boolean r10 = r13.contains(r10)
            if (r10 == 0) goto L_0x01dd
            goto L_0x01de
        L_0x01dd:
            r0 = -1
        L_0x01de:
            if (r0 <= 0) goto L_0x01eb
            com.app.office.simpletext.model.AttrManage r9 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r10 = r1.getAttribute()
            r9.setFontPageNumberType(r10, r0)
        L_0x01eb:
            long r9 = r8.offset
            r1.setStartOffset(r9)
            long r9 = r8.offset
            int r11 = r14.length()
            long r13 = (long) r11
            long r9 = r9 + r13
            r8.offset = r9
            r1.setEndOffset(r9)
            r12.appendLeaf(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCReader.processRun(com.app.office.fc.hwpf.usermodel.CharacterRun, com.app.office.fc.hwpf.usermodel.Range, com.app.office.fc.hwpf.usermodel.Field, com.app.office.simpletext.model.ParagraphElement, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0148, code lost:
        if (r11 != 0) goto L_0x0153;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.common.bg.BackgroundAndFill converFill(com.app.office.fc.hwpf.usermodel.HWPFAutoShape r19, com.app.office.fc.hwpf.usermodel.OfficeDrawing r20, int r21) {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            r2 = r21
            r3 = 0
            r4 = 20
            if (r2 == r4) goto L_0x01d3
            r4 = 32
            if (r2 == r4) goto L_0x01d3
            r4 = 33
            if (r2 == r4) goto L_0x01d3
            r4 = 34
            if (r2 == r4) goto L_0x01d3
            r4 = 38
            if (r2 != r4) goto L_0x001d
            goto L_0x01d3
        L_0x001d:
            if (r19 == 0) goto L_0x01d3
            int r2 = r19.getFillType()
            r4 = 0
            if (r2 == 0) goto L_0x01ba
            r5 = 9
            if (r2 != r5) goto L_0x002c
            goto L_0x01ba
        L_0x002c:
            r5 = 1
            r6 = 6
            r7 = 5
            r8 = 4
            r9 = 7
            r10 = 2
            if (r2 == r9) goto L_0x0133
            if (r2 == r8) goto L_0x0133
            if (r2 == r7) goto L_0x0133
            if (r2 != r6) goto L_0x003c
            goto L_0x0133
        L_0x003c:
            if (r2 != r10) goto L_0x00b3
            com.app.office.system.IControl r2 = r0.control
            int r5 = r19.getBackgroundPictureIdx()
            byte[] r2 = r1.getPictureData(r2, r5)
            if (r2 == 0) goto L_0x01d3
            com.app.office.fc.hwpf.usermodel.PictureType r5 = com.app.office.fc.hwpf.usermodel.PictureType.findMatchingType(r2)
            boolean r5 = r0.isSupportPicture(r5)
            if (r5 == 0) goto L_0x01d3
            com.app.office.common.bg.BackgroundAndFill r3 = new com.app.office.common.bg.BackgroundAndFill
            r3.<init>()
            r3.setFillType(r10)
            com.app.office.system.IControl r5 = r0.control
            com.app.office.system.SysKit r5 = r5.getSysKit()
            com.app.office.common.picture.PictureManage r5 = r5.getPictureManage()
            com.app.office.system.IControl r6 = r0.control
            java.lang.String r6 = r1.getTempFilePath(r6)
            int r5 = r5.getPictureIndex(r6)
            if (r5 >= 0) goto L_0x01d3
            com.app.office.common.picture.Picture r5 = new com.app.office.common.picture.Picture
            r5.<init>()
            com.app.office.system.IControl r6 = r0.control
            java.lang.String r1 = r1.getTempFilePath(r6)
            r5.setTempFilePath(r1)
            com.app.office.fc.hwpf.usermodel.PictureType r1 = com.app.office.fc.hwpf.usermodel.PictureType.findMatchingType(r2)
            java.lang.String r1 = r1.getExtension()
            r5.setPictureType((java.lang.String) r1)
            com.app.office.system.IControl r1 = r0.control
            com.app.office.system.SysKit r1 = r1.getSysKit()
            com.app.office.common.picture.PictureManage r1 = r1.getPictureManage()
            int r1 = r1.addPicture((com.app.office.common.picture.Picture) r5)
            com.app.office.common.bg.TileShader r2 = new com.app.office.common.bg.TileShader
            com.app.office.system.IControl r5 = r0.control
            com.app.office.system.SysKit r5 = r5.getSysKit()
            com.app.office.common.picture.PictureManage r5 = r5.getPictureManage()
            com.app.office.common.picture.Picture r1 = r5.getPicture(r1)
            r5 = 1065353216(0x3f800000, float:1.0)
            r2.<init>(r1, r4, r5, r5)
            r3.setShader(r2)
            goto L_0x01d3
        L_0x00b3:
            r6 = 3
            if (r2 != r6) goto L_0x0116
            com.app.office.system.IControl r2 = r0.control
            int r4 = r19.getBackgroundPictureIdx()
            byte[] r2 = r1.getPictureData(r2, r4)
            if (r2 == 0) goto L_0x01d3
            com.app.office.fc.hwpf.usermodel.PictureType r4 = com.app.office.fc.hwpf.usermodel.PictureType.findMatchingType(r2)
            boolean r4 = r0.isSupportPicture(r4)
            if (r4 == 0) goto L_0x01d3
            com.app.office.common.bg.BackgroundAndFill r3 = new com.app.office.common.bg.BackgroundAndFill
            r3.<init>()
            r3.setFillType(r6)
            com.app.office.system.IControl r4 = r0.control
            com.app.office.system.SysKit r4 = r4.getSysKit()
            com.app.office.common.picture.PictureManage r4 = r4.getPictureManage()
            com.app.office.system.IControl r5 = r0.control
            java.lang.String r5 = r1.getTempFilePath(r5)
            int r4 = r4.getPictureIndex(r5)
            if (r4 >= 0) goto L_0x0111
            com.app.office.common.picture.Picture r4 = new com.app.office.common.picture.Picture
            r4.<init>()
            com.app.office.system.IControl r5 = r0.control
            java.lang.String r1 = r1.getTempFilePath(r5)
            r4.setTempFilePath(r1)
            com.app.office.fc.hwpf.usermodel.PictureType r1 = com.app.office.fc.hwpf.usermodel.PictureType.findMatchingType(r2)
            java.lang.String r1 = r1.getExtension()
            r4.setPictureType((java.lang.String) r1)
            com.app.office.system.IControl r1 = r0.control
            com.app.office.system.SysKit r1 = r1.getSysKit()
            com.app.office.common.picture.PictureManage r1 = r1.getPictureManage()
            int r4 = r1.addPicture((com.app.office.common.picture.Picture) r4)
        L_0x0111:
            r3.setPictureIndex(r4)
            goto L_0x01d3
        L_0x0116:
            if (r2 != r5) goto L_0x01d3
            com.app.office.java.awt.Color r1 = r19.getFillbackColor()
            if (r1 == 0) goto L_0x01d3
            com.app.office.common.bg.BackgroundAndFill r3 = new com.app.office.common.bg.BackgroundAndFill
            r3.<init>()
            r3.setFillType(r4)
            com.app.office.java.awt.Color r1 = r19.getFillbackColor()
            int r1 = r1.getRGB()
            r3.setForegroundColor(r1)
            goto L_0x01d3
        L_0x0133:
            com.app.office.common.bg.BackgroundAndFill r1 = new com.app.office.common.bg.BackgroundAndFill
            r1.<init>()
            int r11 = r19.getFillAngle()
            r12 = -135(0xffffffffffffff79, float:NaN)
            if (r11 == r12) goto L_0x0151
            r12 = -90
            if (r11 == r12) goto L_0x014e
            r12 = -45
            if (r11 == r12) goto L_0x014b
            if (r11 == 0) goto L_0x014e
            goto L_0x0153
        L_0x014b:
            r11 = 135(0x87, float:1.89E-43)
            goto L_0x0153
        L_0x014e:
            int r11 = r11 + 90
            goto L_0x0153
        L_0x0151:
            r11 = 45
        L_0x0153:
            int r12 = r19.getFillFocus()
            com.app.office.java.awt.Color r13 = r19.getForegroundColor()
            com.app.office.java.awt.Color r14 = r19.getFillbackColor()
            boolean r15 = r19.isShaderPreset()
            if (r15 == 0) goto L_0x016e
            int[] r15 = r19.getShaderColors()
            float[] r16 = r19.getShaderPositions()
            goto L_0x0171
        L_0x016e:
            r15 = r3
            r16 = r15
        L_0x0171:
            if (r15 != 0) goto L_0x018a
            int[] r15 = new int[r10]
            r17 = -1
            if (r13 != 0) goto L_0x017b
            r13 = -1
            goto L_0x017f
        L_0x017b:
            int r13 = r13.getRGB()
        L_0x017f:
            r15[r4] = r13
            if (r14 != 0) goto L_0x0184
            goto L_0x0188
        L_0x0184:
            int r17 = r14.getRGB()
        L_0x0188:
            r15[r5] = r17
        L_0x018a:
            if (r16 != 0) goto L_0x0192
            float[] r4 = new float[r10]
            r4 = {0, 1065353216} // fill-array
            goto L_0x0194
        L_0x0192:
            r4 = r16
        L_0x0194:
            if (r2 != r9) goto L_0x019d
            com.app.office.common.bg.LinearGradientShader r3 = new com.app.office.common.bg.LinearGradientShader
            float r5 = (float) r11
            r3.<init>(r5, r15, r4)
            goto L_0x01ac
        L_0x019d:
            if (r2 == r8) goto L_0x01a3
            if (r2 == r7) goto L_0x01a3
            if (r2 != r6) goto L_0x01ac
        L_0x01a3:
            com.app.office.common.bg.RadialGradientShader r3 = new com.app.office.common.bg.RadialGradientShader
            int r5 = r19.getRadialGradientPositionType()
            r3.<init>(r5, r15, r4)
        L_0x01ac:
            if (r3 == 0) goto L_0x01b1
            r3.setFocus(r12)
        L_0x01b1:
            byte r2 = (byte) r2
            r1.setFillType(r2)
            r1.setShader(r3)
            r3 = r1
            goto L_0x01d3
        L_0x01ba:
            com.app.office.java.awt.Color r1 = r19.getForegroundColor()
            if (r1 == 0) goto L_0x01d3
            com.app.office.common.bg.BackgroundAndFill r3 = new com.app.office.common.bg.BackgroundAndFill
            r3.<init>()
            r3.setFillType(r4)
            com.app.office.java.awt.Color r1 = r19.getForegroundColor()
            int r1 = r1.getRGB()
            r3.setForegroundColor(r1)
        L_0x01d3:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.DOCReader.converFill(com.app.office.fc.hwpf.usermodel.HWPFAutoShape, com.app.office.fc.hwpf.usermodel.OfficeDrawing, int):com.app.office.common.bg.BackgroundAndFill");
    }

    private void processRotation(HWPFAutoShape hWPFAutoShape, IShape iShape) {
        float rotation = (float) hWPFAutoShape.getRotation();
        if (hWPFAutoShape.getFlipHorizontal()) {
            iShape.setFlipHorizontal(true);
            rotation = -rotation;
        }
        if (hWPFAutoShape.getFlipVertical()) {
            iShape.setFlipVertical(true);
            rotation = -rotation;
        }
        if ((iShape instanceof LineShape) && ((rotation == 45.0f || rotation == 135.0f || rotation == 225.0f) && !iShape.getFlipHorizontal() && !iShape.getFlipVertical())) {
            rotation -= 90.0f;
        }
        iShape.setRotation(rotation);
    }

    private Rectangle processGrpSpRect(GroupShape groupShape, Rectangle rectangle) {
        if (groupShape != null) {
            rectangle.x += groupShape.getOffX();
            rectangle.y += groupShape.getOffY();
        }
        return rectangle;
    }

    private void processAutoshapePosition(HWPFAutoShape hWPFAutoShape, WPAutoShape wPAutoShape) {
        int position_H = hWPFAutoShape.getPosition_H();
        if (position_H == 0) {
            wPAutoShape.setHorPositionType((byte) 0);
        } else if (position_H == 1) {
            wPAutoShape.setHorizontalAlignment((byte) 1);
        } else if (position_H == 2) {
            wPAutoShape.setHorizontalAlignment((byte) 2);
        } else if (position_H == 3) {
            wPAutoShape.setHorizontalAlignment((byte) 3);
        } else if (position_H == 4) {
            wPAutoShape.setHorizontalAlignment((byte) 6);
        } else if (position_H == 5) {
            wPAutoShape.setHorizontalAlignment((byte) 7);
        }
        int positionRelTo_H = hWPFAutoShape.getPositionRelTo_H();
        if (positionRelTo_H == 0) {
            wPAutoShape.setHorizontalRelativeTo((byte) 1);
        } else if (positionRelTo_H == 1) {
            wPAutoShape.setHorizontalRelativeTo((byte) 2);
        } else if (positionRelTo_H == 2) {
            wPAutoShape.setHorizontalRelativeTo((byte) 0);
        } else if (positionRelTo_H == 3) {
            wPAutoShape.setHorizontalRelativeTo((byte) 3);
        }
        int position_V = hWPFAutoShape.getPosition_V();
        if (position_V == 0) {
            wPAutoShape.setVerPositionType((byte) 0);
        } else if (position_V == 1) {
            wPAutoShape.setVerticalAlignment((byte) 4);
        } else if (position_V == 2) {
            wPAutoShape.setVerticalAlignment((byte) 2);
        } else if (position_V == 3) {
            wPAutoShape.setVerticalAlignment((byte) 5);
        } else if (position_V == 4) {
            wPAutoShape.setVerticalAlignment((byte) 6);
        } else if (position_V == 5) {
            wPAutoShape.setVerticalAlignment((byte) 7);
        }
        int positionRelTo_V = hWPFAutoShape.getPositionRelTo_V();
        if (positionRelTo_V == 0) {
            wPAutoShape.setVerticalRelativeTo((byte) 1);
        } else if (positionRelTo_V == 1) {
            wPAutoShape.setVerticalRelativeTo((byte) 2);
        } else if (positionRelTo_V == 2) {
            wPAutoShape.setVerticalRelativeTo((byte) 10);
        } else if (positionRelTo_V == 3) {
            wPAutoShape.setVerticalRelativeTo((byte) 11);
        }
    }

    private byte[] getPictureframeData(OfficeDrawing officeDrawing, HWPFShape hWPFShape) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) hWPFShape.getSpContainer().getChildById(EscherOptRecord.RECORD_ID);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) escherOptRecord.lookup(MetaDo.META_SETROP2)) == null) {
            return null;
        }
        return officeDrawing.getPictureData(this.control, escherSimpleProperty.getPropertyValue());
    }

    private boolean convertShape(IElement iElement, OfficeDrawing officeDrawing, GroupShape groupShape, HWPFShape hWPFShape, Rectangle rectangle, float f, float f2) {
        WPAutoShape wPAutoShape;
        WPAutoShape wPAutoShape2;
        PointF pointF;
        PointF pointF2;
        ArrowPathAndTail endArrowPath;
        ArrowPathAndTail startArrowPath;
        OfficeDrawing officeDrawing2 = officeDrawing;
        GroupShape groupShape2 = groupShape;
        HWPFShape hWPFShape2 = hWPFShape;
        Rectangle rectangle2 = rectangle;
        float f3 = f;
        float f4 = f2;
        if (rectangle2 == null) {
            return false;
        }
        char c = 1;
        if (hWPFShape2 instanceof HWPFAutoShape) {
            HWPFAutoShape hWPFAutoShape = (HWPFAutoShape) hWPFShape2;
            int shapeType = hWPFAutoShape.getShapeType();
            BackgroundAndFill converFill = converFill(hWPFAutoShape, officeDrawing2, shapeType);
            Line line = hWPFShape2.getLine(shapeType == 20);
            if (line == null && converFill == null && shapeType != 202 && shapeType != 75) {
                return false;
            }
            Rectangle processGrpSpRect = processGrpSpRect(groupShape2, rectangle2);
            if (shapeType == 75) {
                wPAutoShape2 = new WPPictureShape();
            } else {
                wPAutoShape2 = new WPAutoShape();
            }
            wPAutoShape2.setShapeType(shapeType);
            wPAutoShape2.setAuotShape07(false);
            wPAutoShape2.setBounds(ModelUtil.processRect(processGrpSpRect, (float) Math.abs(hWPFAutoShape.getRotation())));
            wPAutoShape2.setBackgroundAndFill(converFill);
            if (line != null) {
                wPAutoShape2.setLine(line);
            }
            Float[] adjustmentValue = hWPFAutoShape.getAdjustmentValue();
            wPAutoShape2.setAdjustData(adjustmentValue);
            processRotation(hWPFAutoShape, wPAutoShape2);
            processAutoshapePosition(hWPFAutoShape, wPAutoShape2);
            if (shapeType == 75) {
                byte[] pictureframeData = getPictureframeData(officeDrawing2, hWPFAutoShape);
                if (pictureframeData != null && isSupportPicture(PictureType.findMatchingType(pictureframeData))) {
                    PictureShape pictureShape = new PictureShape();
                    int pictureIndex = this.control.getSysKit().getPictureManage().getPictureIndex(officeDrawing2.getTempFilePath(this.control));
                    if (pictureIndex < 0) {
                        Picture picture = new Picture();
                        picture.setTempFilePath(officeDrawing2.getTempFilePath(this.control));
                        picture.setPictureType(PictureType.findMatchingType(pictureframeData).getExtension());
                        pictureIndex = this.control.getSysKit().getPictureManage().addPicture(picture);
                    }
                    pictureShape.setPictureIndex(pictureIndex);
                    pictureShape.setBounds(processGrpSpRect);
                    pictureShape.setZoomX(1000);
                    pictureShape.setZoomY(1000);
                    pictureShape.setPictureEffectInfor(officeDrawing.getPictureEffectInfor());
                    ((WPPictureShape) wPAutoShape2).setPictureShape(pictureShape);
                }
            } else if (shapeType == 20 || shapeType == 32 || shapeType == 33 || shapeType == 34 || shapeType == 38) {
                if (wPAutoShape2.getShapeType() == 33 && adjustmentValue == null) {
                    wPAutoShape2.setAdjustData(new Float[]{Float.valueOf(1.0f)});
                }
                int startArrowType = hWPFAutoShape.getStartArrowType();
                if (startArrowType > 0) {
                    wPAutoShape2.createStartArrow((byte) startArrowType, hWPFAutoShape.getStartArrowWidth(), hWPFAutoShape.getStartArrowLength());
                }
                int endArrowType = hWPFAutoShape.getEndArrowType();
                if (endArrowType > 0) {
                    wPAutoShape2.createEndArrow((byte) endArrowType, hWPFAutoShape.getEndArrowWidth(), hWPFAutoShape.getEndArrowLength());
                }
            } else if (shapeType == 0 || shapeType == 100) {
                wPAutoShape2.setShapeType(233);
                int startArrowType2 = hWPFAutoShape.getStartArrowType();
                if (startArrowType2 <= 0 || (startArrowPath = hWPFAutoShape.getStartArrowPath(processGrpSpRect)) == null || startArrowPath.getArrowPath() == null) {
                    pointF = null;
                } else {
                    PointF arrowTailCenter = startArrowPath.getArrowTailCenter();
                    ExtendPath extendPath = new ExtendPath();
                    extendPath.setPath(startArrowPath.getArrowPath());
                    extendPath.setArrowFlag(true);
                    if (startArrowType2 == 5) {
                        extendPath.setLine(line);
                    } else if ((line == null || line.getBackgroundAndFill() == null) && hWPFShape.getLineColor() != null) {
                        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
                        backgroundAndFill.setFillType((byte) 0);
                        backgroundAndFill.setForegroundColor(hWPFShape.getLineColor().getRGB());
                        extendPath.setBackgroundAndFill(backgroundAndFill);
                    } else {
                        extendPath.setBackgroundAndFill(line.getBackgroundAndFill());
                    }
                    wPAutoShape2.appendPath(extendPath);
                    pointF = arrowTailCenter;
                }
                int endArrowType2 = hWPFAutoShape.getEndArrowType();
                if (endArrowType2 <= 0 || (endArrowPath = hWPFAutoShape.getEndArrowPath(processGrpSpRect)) == null || endArrowPath.getArrowPath() == null) {
                    pointF2 = null;
                } else {
                    PointF arrowTailCenter2 = endArrowPath.getArrowTailCenter();
                    ExtendPath extendPath2 = new ExtendPath();
                    extendPath2.setPath(endArrowPath.getArrowPath());
                    extendPath2.setArrowFlag(true);
                    if (endArrowType2 == 5) {
                        extendPath2.setLine(line);
                    } else if ((line == null || line.getBackgroundAndFill() == null) && hWPFShape.getLineColor() != null) {
                        BackgroundAndFill backgroundAndFill2 = new BackgroundAndFill();
                        backgroundAndFill2.setFillType((byte) 0);
                        backgroundAndFill2.setForegroundColor(hWPFShape.getLineColor().getRGB());
                        extendPath2.setBackgroundAndFill(backgroundAndFill2);
                    } else {
                        extendPath2.setBackgroundAndFill(line.getBackgroundAndFill());
                    }
                    wPAutoShape2.appendPath(extendPath2);
                    pointF2 = arrowTailCenter2;
                }
                Path[] freeformPath = hWPFAutoShape.getFreeformPath(processGrpSpRect, pointF, (byte) startArrowType2, pointF2, (byte) endArrowType2);
                for (Path path : freeformPath) {
                    ExtendPath extendPath3 = new ExtendPath();
                    extendPath3.setPath(path);
                    if (line != null) {
                        extendPath3.setLine(line);
                    }
                    if (converFill != null) {
                        extendPath3.setBackgroundAndFill(converFill);
                    }
                    wPAutoShape2.appendPath(extendPath3);
                }
            } else {
                processTextbox(hWPFAutoShape.getSpContainer(), wPAutoShape2, this.poiDoc.getMainTextboxRange().getSection(0));
            }
            if (groupShape2 == null) {
                if (officeDrawing.getWrap() != 3 || officeDrawing.isAnchorLock()) {
                    wPAutoShape2.setWrap(2);
                } else if (officeDrawing.isBelowText()) {
                    wPAutoShape2.setWrap(6);
                } else {
                    wPAutoShape2.setWrap(3);
                    wPAutoShape2.getBackgroundAndFill();
                }
                AttrManage.instance().setShapeID(iElement.getAttribute(), this.control.getSysKit().getWPShapeManage().addShape(wPAutoShape2));
                return true;
            }
            groupShape2.appendShapes(wPAutoShape2);
            return false;
        } else if (!(hWPFShape2 instanceof HWPFShapeGroup)) {
            return false;
        } else {
            HWPFShapeGroup hWPFShapeGroup = (HWPFShapeGroup) hWPFShape2;
            WPGroupShape wPGroupShape = new WPGroupShape();
            if (groupShape2 == null) {
                WPAutoShape wPAutoShape3 = new WPAutoShape();
                wPAutoShape3.addGroupShape(wPGroupShape);
                wPAutoShape = wPAutoShape3;
            } else {
                wPAutoShape = wPGroupShape;
            }
            float[] shapeAnchorFit = hWPFShapeGroup.getShapeAnchorFit(rectangle2, f3, f4);
            Rectangle processGrpSpRect2 = processGrpSpRect(groupShape2, rectangle2);
            Rectangle coordinates = hWPFShapeGroup.getCoordinates(shapeAnchorFit[0] * f3, shapeAnchorFit[1] * f4);
            wPGroupShape.setOffPostion(processGrpSpRect2.x - coordinates.x, processGrpSpRect2.y - coordinates.y);
            wPGroupShape.setBounds(processGrpSpRect2);
            wPGroupShape.setParent(groupShape2);
            wPGroupShape.setRotation((float) hWPFShapeGroup.getGroupRotation());
            wPGroupShape.setFlipHorizontal(hWPFShapeGroup.getFlipHorizontal());
            wPGroupShape.setFlipVertical(hWPFShapeGroup.getFlipVertical());
            HWPFShape[] shapes = hWPFShapeGroup.getShapes();
            if (shapes != null) {
                int i = 0;
                while (i < shapes.length) {
                    HWPFShape hWPFShape3 = shapes[i];
                    Rectangle anchor = shapes[i].getAnchor(processGrpSpRect2, shapeAnchorFit[0] * f3, shapeAnchorFit[c] * f4);
                    int i2 = i;
                    HWPFShape hWPFShape4 = hWPFShape3;
                    convertShape(iElement, officeDrawing, wPGroupShape, hWPFShape4, anchor, shapeAnchorFit[0] * f3, shapeAnchorFit[c] * f4);
                    i = i2 + 1;
                    shapes = shapes;
                    processGrpSpRect2 = processGrpSpRect2;
                    c = 1;
                }
            }
            if (groupShape2 == null) {
                if (officeDrawing.getWrap() != 3 || officeDrawing.isAnchorLock()) {
                    ((WPAutoShape) wPAutoShape).setWrap(2);
                } else {
                    ((WPAutoShape) wPAutoShape).setWrap(3);
                }
                AttrManage.instance().setShapeID(iElement.getAttribute(), this.control.getSysKit().getWPShapeManage().addShape(wPAutoShape));
            } else {
                wPAutoShape.setParent(groupShape2);
                groupShape2.appendShapes(wPAutoShape);
            }
            return true;
        }
    }

    private short getTextboxId(EscherContainerRecord escherContainerRecord) {
        byte[] data;
        EscherTextboxRecord escherTextboxRecord = (EscherTextboxRecord) escherContainerRecord.getChildById(EscherTextboxRecord.RECORD_ID);
        if (escherTextboxRecord == null || (data = escherTextboxRecord.getData()) == null || data.length != 4) {
            return -1;
        }
        return LittleEndian.getShort(data, 2);
    }

    private void processTextbox(EscherContainerRecord escherContainerRecord, WPAutoShape wPAutoShape, Section section) {
        if (section != null) {
            if (getTextboxId(escherContainerRecord) - 1 >= 0) {
                processSimpleTextBox(escherContainerRecord, wPAutoShape, section);
            } else {
                processWordArtTextbox(escherContainerRecord, wPAutoShape);
            }
        }
    }

    private void processSimpleTextBox(EscherContainerRecord escherContainerRecord, WPAutoShape wPAutoShape, Section section) {
        int textboxId = getTextboxId(escherContainerRecord) - 1;
        int textboxStart = this.poiDoc.getTextboxStart(textboxId);
        int textboxEnd = this.poiDoc.getTextboxEnd(textboxId);
        long j = this.offset;
        long j2 = this.textboxIndex;
        this.offset = (j2 << 32) + WPModelConstant.TEXTBOX;
        wPAutoShape.setElementIndex((int) j2);
        SectionElement sectionElement = new SectionElement();
        sectionElement.setStartOffset(this.offset);
        this.wpdoc.appendElement(sectionElement, this.offset);
        IAttributeSet attribute = sectionElement.getAttribute();
        AttrManage.instance().setPageWidth(attribute, (int) (((float) wPAutoShape.getBounds().width) * 15.0f));
        AttrManage.instance().setPageHeight(attribute, (int) (((float) wPAutoShape.getBounds().height) * 15.0f));
        if (section.getGridType() != 0) {
            AttrManage.instance().setPageLinePitch(attribute, section.getLinePitch());
        }
        AttrManage.instance().setPageMarginTop(attribute, (int) (ShapeKit.getTextboxMarginTop(escherContainerRecord) * 15.0f));
        AttrManage.instance().setPageMarginBottom(attribute, (int) (ShapeKit.getTextboxMarginBottom(escherContainerRecord) * 15.0f));
        AttrManage.instance().setPageMarginLeft(attribute, (int) (ShapeKit.getTextboxMarginLeft(escherContainerRecord) * 15.0f));
        AttrManage.instance().setPageMarginRight(attribute, (int) (ShapeKit.getTextboxMarginRight(escherContainerRecord) * 15.0f));
        int i = 0;
        AttrManage.instance().setPageVerticalAlign(attribute, (byte) 0);
        wPAutoShape.setTextWrapLine(ShapeKit.isTextboxWrapLine(escherContainerRecord));
        sectionElement.setStartOffset(this.offset);
        int numParagraphs = section.numParagraphs();
        int i2 = 0;
        while (i < numParagraphs && !this.abortReader) {
            Paragraph paragraph = section.getParagraph(i);
            i2 += paragraph.text().length();
            if (i2 > textboxStart && i2 <= textboxEnd) {
                if (paragraph.isInTable()) {
                    Table table = section.getTable(paragraph);
                    processTable(table);
                    i += table.numParagraphs() - 1;
                } else {
                    processParagraph(section.getParagraph(i));
                }
            }
            i++;
        }
        wPAutoShape.setElementIndex((int) this.textboxIndex);
        sectionElement.setEndOffset(this.offset);
        this.textboxIndex++;
        this.offset = j;
    }

    private void processWordArtTextbox(EscherContainerRecord escherContainerRecord, WPAutoShape wPAutoShape) {
        String unicodeGeoText = ShapeKit.getUnicodeGeoText(escherContainerRecord);
        if (unicodeGeoText != null && unicodeGeoText.length() > 0) {
            long j = this.offset;
            long j2 = this.textboxIndex;
            this.offset = (j2 << 32) + WPModelConstant.TEXTBOX;
            wPAutoShape.setElementIndex((int) j2);
            SectionElement sectionElement = new SectionElement();
            sectionElement.setStartOffset(this.offset);
            this.wpdoc.appendElement(sectionElement, this.offset);
            IAttributeSet attribute = sectionElement.getAttribute();
            AttrManage.instance().setPageWidth(attribute, (int) (((float) wPAutoShape.getBounds().width) * 15.0f));
            AttrManage.instance().setPageHeight(attribute, (int) (((float) wPAutoShape.getBounds().height) * 15.0f));
            AttrManage.instance().setPageMarginTop(attribute, (int) (ShapeKit.getTextboxMarginTop(escherContainerRecord) * 15.0f));
            AttrManage.instance().setPageMarginBottom(attribute, (int) (ShapeKit.getTextboxMarginBottom(escherContainerRecord) * 15.0f));
            AttrManage.instance().setPageMarginLeft(attribute, (int) (ShapeKit.getTextboxMarginLeft(escherContainerRecord) * 15.0f));
            AttrManage.instance().setPageMarginRight(attribute, (int) (ShapeKit.getTextboxMarginRight(escherContainerRecord) * 15.0f));
            AttrManage.instance().setPageVerticalAlign(attribute, (byte) 0);
            wPAutoShape.setTextWrapLine(ShapeKit.isTextboxWrapLine(escherContainerRecord));
            int textboxMarginLeft = (int) ((((float) wPAutoShape.getBounds().width) - ShapeKit.getTextboxMarginLeft(escherContainerRecord)) - ShapeKit.getTextboxMarginRight(escherContainerRecord));
            int textboxMarginTop = (int) ((((float) wPAutoShape.getBounds().height) - ShapeKit.getTextboxMarginTop(escherContainerRecord)) - ShapeKit.getTextboxMarginBottom(escherContainerRecord));
            int i = 12;
            Paint paint = PaintKit.instance().getPaint();
            paint.setTextSize((float) 12);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            while (((int) paint.measureText(unicodeGeoText)) < textboxMarginLeft && ((int) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent))) < textboxMarginTop) {
                i++;
                paint.setTextSize((float) i);
                fontMetrics = paint.getFontMetrics();
            }
            sectionElement.setStartOffset(this.offset);
            ParagraphElement paragraphElement = new ParagraphElement();
            paragraphElement.setStartOffset(this.offset);
            long j3 = this.docRealOffset;
            LeafElement leafElement = new LeafElement(unicodeGeoText);
            IAttributeSet attribute2 = leafElement.getAttribute();
            AttrManage.instance().setFontSize(attribute2, (int) (((float) (i - 1)) * 0.75f));
            Color foregroundColor = ShapeKit.getForegroundColor(escherContainerRecord, (Object) null, 2);
            if (foregroundColor != null) {
                AttrManage.instance().setFontColor(attribute2, foregroundColor.getRGB());
            }
            leafElement.setStartOffset(this.offset);
            long length = this.offset + ((long) unicodeGeoText.length());
            this.offset = length;
            leafElement.setEndOffset(length);
            paragraphElement.appendLeaf(leafElement);
            paragraphElement.setEndOffset(this.offset);
            this.wpdoc.appendParagraph(paragraphElement, this.offset);
            adjustBookmarkOffset(j3, this.docRealOffset);
            wPAutoShape.setElementIndex((int) this.textboxIndex);
            sectionElement.setEndOffset(this.offset);
            this.textboxIndex++;
            this.offset = j;
        }
    }

    private void processPicturePosition(OfficeDrawing officeDrawing, WPAutoShape wPAutoShape) {
        byte horizontalPositioning = officeDrawing.getHorizontalPositioning();
        if (horizontalPositioning == 0) {
            wPAutoShape.setHorPositionType((byte) 0);
        } else if (horizontalPositioning == 1) {
            wPAutoShape.setHorizontalAlignment((byte) 1);
        } else if (horizontalPositioning == 2) {
            wPAutoShape.setHorizontalAlignment((byte) 2);
        } else if (horizontalPositioning == 3) {
            wPAutoShape.setHorizontalAlignment((byte) 3);
        } else if (horizontalPositioning == 4) {
            wPAutoShape.setHorizontalAlignment((byte) 6);
        } else if (horizontalPositioning == 5) {
            wPAutoShape.setHorizontalAlignment((byte) 7);
        }
        byte horizontalRelative = officeDrawing.getHorizontalRelative();
        if (horizontalRelative == 0) {
            wPAutoShape.setHorizontalRelativeTo((byte) 1);
        } else if (horizontalRelative == 1) {
            wPAutoShape.setHorizontalRelativeTo((byte) 2);
        } else if (horizontalRelative == 2) {
            wPAutoShape.setHorizontalRelativeTo((byte) 0);
        } else if (horizontalRelative == 3) {
            wPAutoShape.setHorizontalRelativeTo((byte) 3);
        }
        byte verticalPositioning = officeDrawing.getVerticalPositioning();
        if (verticalPositioning == 0) {
            wPAutoShape.setVerPositionType((byte) 0);
        } else if (verticalPositioning == 1) {
            wPAutoShape.setVerticalAlignment((byte) 4);
        } else if (verticalPositioning == 2) {
            wPAutoShape.setVerticalAlignment((byte) 2);
        } else if (verticalPositioning == 3) {
            wPAutoShape.setVerticalAlignment((byte) 5);
        } else if (verticalPositioning == 4) {
            wPAutoShape.setVerticalAlignment((byte) 6);
        } else if (verticalPositioning == 5) {
            wPAutoShape.setVerticalAlignment((byte) 7);
        }
        byte verticalRelativeElement = officeDrawing.getVerticalRelativeElement();
        if (verticalRelativeElement == 0) {
            wPAutoShape.setVerticalRelativeTo((byte) 1);
        } else if (verticalRelativeElement == 1) {
            wPAutoShape.setVerticalRelativeTo((byte) 2);
        } else if (verticalRelativeElement == 2) {
            wPAutoShape.setVerticalRelativeTo((byte) 10);
        } else if (verticalRelativeElement == 3) {
            wPAutoShape.setVerticalRelativeTo((byte) 11);
        }
    }

    private boolean processShape(CharacterRun characterRun, IElement iElement, boolean z, int i) {
        if (z) {
            OfficeDrawing officeDrawingAt = this.poiDoc.getOfficeDrawingsMain().getOfficeDrawingAt(characterRun.getStartOffset() + i);
            if (officeDrawingAt == null) {
                return false;
            }
            Rectangle rectangle = new Rectangle();
            rectangle.x = (int) (((float) officeDrawingAt.getRectangleLeft()) * 0.06666667f);
            rectangle.y = (int) (((float) officeDrawingAt.getRectangleTop()) * 0.06666667f);
            rectangle.width = (int) (((float) (officeDrawingAt.getRectangleRight() - officeDrawingAt.getRectangleLeft())) * 0.06666667f);
            rectangle.height = (int) (((float) (officeDrawingAt.getRectangleBottom() - officeDrawingAt.getRectangleTop())) * 0.06666667f);
            byte[] pictureData = officeDrawingAt.getPictureData(this.control);
            if (pictureData == null) {
                HWPFShape autoShape = officeDrawingAt.getAutoShape();
                if (autoShape != null) {
                    return convertShape(iElement, officeDrawingAt, (GroupShape) null, autoShape, rectangle, 1.0f, 1.0f);
                }
            } else if (isSupportPicture(PictureType.findMatchingType(pictureData))) {
                PictureShape pictureShape = new PictureShape();
                int pictureIndex = this.control.getSysKit().getPictureManage().getPictureIndex(officeDrawingAt.getTempFilePath(this.control));
                if (pictureIndex < 0) {
                    Picture picture = new Picture();
                    picture.setTempFilePath(officeDrawingAt.getTempFilePath(this.control));
                    picture.setPictureType(PictureType.findMatchingType(pictureData).getExtension());
                    pictureIndex = this.control.getSysKit().getPictureManage().addPicture(picture);
                }
                pictureShape.setPictureIndex(pictureIndex);
                pictureShape.setBounds(rectangle);
                pictureShape.setZoomX(1000);
                pictureShape.setZoomY(1000);
                pictureShape.setPictureEffectInfor(officeDrawingAt.getPictureEffectInfor());
                WPPictureShape wPPictureShape = new WPPictureShape();
                wPPictureShape.setPictureShape(pictureShape);
                if (officeDrawingAt.getWrap() != 3 || officeDrawingAt.isAnchorLock()) {
                    wPPictureShape.setWrap(2);
                } else {
                    if (officeDrawingAt.isBelowText()) {
                        wPPictureShape.setWrap(6);
                    } else {
                        wPPictureShape.setWrap(3);
                    }
                    processPicturePosition(officeDrawingAt, wPPictureShape);
                }
                AttrManage.instance().setShapeID(iElement.getAttribute(), this.control.getSysKit().getWPShapeManage().addShape(wPPictureShape));
                return true;
            }
        } else {
            PicturesTable picturesTable = this.poiDoc.getPicturesTable();
            com.app.office.fc.hwpf.usermodel.Picture extractPicture = picturesTable.extractPicture(this.control.getSysKit().getPictureManage().getPicTempPath(), characterRun, false);
            if (extractPicture == null || !isSupportPicture(extractPicture.suggestPictureType())) {
                InlineWordArt extracInlineWordArt = picturesTable.extracInlineWordArt(characterRun);
                if (!(extracInlineWordArt == null || extracInlineWordArt.getInlineWordArt() == null)) {
                    WPAutoShape wPAutoShape = new WPAutoShape();
                    Rectangle rectangle2 = new Rectangle();
                    rectangle2.width = (int) (((((float) extracInlineWordArt.getDxaGoal()) * 0.06666667f) * ((float) extracInlineWordArt.getHorizontalScalingFactor())) / 1000.0f);
                    rectangle2.height = (int) (((((float) extracInlineWordArt.getDyaGoal()) * 0.06666667f) * ((float) extracInlineWordArt.getVerticalScalingFactor())) / 1000.0f);
                    wPAutoShape.setBounds(rectangle2);
                    wPAutoShape.setWrap(2);
                    processWordArtTextbox(extracInlineWordArt.getInlineWordArt().getSpContainer(), wPAutoShape);
                    AttrManage.instance().setShapeID(iElement.getAttribute(), this.control.getSysKit().getWPShapeManage().addShape(wPAutoShape));
                    return true;
                }
            } else {
                PictureShape pictureShape2 = new PictureShape();
                int pictureIndex2 = this.control.getSysKit().getPictureManage().getPictureIndex(extractPicture.getTempFilePath());
                if (pictureIndex2 < 0) {
                    Picture picture2 = new Picture();
                    picture2.setTempFilePath(extractPicture.getTempFilePath());
                    picture2.setPictureType(extractPicture.suggestPictureType().getExtension());
                    pictureIndex2 = this.control.getSysKit().getPictureManage().addPicture(picture2);
                }
                pictureShape2.setPictureIndex(pictureIndex2);
                Rectangle rectangle3 = new Rectangle();
                rectangle3.width = (int) (((((float) extractPicture.getDxaGoal()) * 0.06666667f) * ((float) extractPicture.getHorizontalScalingFactor())) / 1000.0f);
                rectangle3.height = (int) (((((float) extractPicture.getDyaGoal()) * 0.06666667f) * ((float) extractPicture.getVerticalScalingFactor())) / 1000.0f);
                pictureShape2.setBounds(rectangle3);
                pictureShape2.setZoomX(extractPicture.getZoomX());
                pictureShape2.setZoomY(extractPicture.getZoomY());
                pictureShape2.setPictureEffectInfor(PictureEffectInfoFactory.getPictureEffectInfor(extractPicture));
                WPPictureShape wPPictureShape2 = new WPPictureShape();
                wPPictureShape2.setPictureShape(pictureShape2);
                wPPictureShape2.setWrap(2);
                AttrManage.instance().setShapeID(iElement.getAttribute(), this.control.getSysKit().getWPShapeManage().addShape(wPPictureShape2));
                return true;
            }
        }
        return false;
    }

    private boolean isSupportPicture(PictureType pictureType) {
        String extension = pictureType.getExtension();
        return extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase(ContentTypes.EXTENSION_JPG_1) || extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase(Picture.WMF_TYPE) || extension.equalsIgnoreCase(Picture.EMF_TYPE);
    }

    public boolean searchContent(File file, String str) throws Exception {
        Range range = new HWPFDocument(new FileInputStream(file)).getRange();
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (int i = 0; i < range.numSections(); i++) {
            Section section = range.getSection(i);
            int i2 = 0;
            while (true) {
                if (i2 >= section.numParagraphs()) {
                    break;
                }
                Paragraph paragraph = section.getParagraph(i2);
                for (int i3 = 0; i3 < paragraph.numCharacterRuns(); i3++) {
                    sb.append(paragraph.getCharacterRun(i3).text());
                }
                if (sb.indexOf(str) >= 0) {
                    z = true;
                    break;
                }
                sb.delete(0, sb.length());
                i2++;
            }
        }
        return z;
    }

    private void adjustBookmarkOffset(long j, long j2) {
        for (Bookmark next : this.bms) {
            if (next.getStart() >= j && next.getStart() <= j2) {
                next.setStart(this.offset);
            }
        }
    }

    public void dispose() {
        if (isReaderFinish()) {
            this.wpdoc = null;
            this.filePath = null;
            this.poiDoc = null;
            this.control = null;
            this.hyperlinkAddress = null;
            List<Bookmark> list = this.bms;
            if (list != null) {
                list.clear();
                this.bms = null;
            }
        }
    }
}
