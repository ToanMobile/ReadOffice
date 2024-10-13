package com.app.office.fc.xls.Reader;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.itextpdf.text.html.HtmlTags;
import com.onesignal.outcomes.OSOutcomeConstants;
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipCollection;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.ppt.reader.PictureReader;
import com.app.office.fc.ss.util.CellUtil;
import com.app.office.fc.xls.Reader.drawing.DrawingReader;
import com.app.office.fc.xls.Reader.table.TableReader;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.sheetProperty.ColumnInfo;
import com.app.office.ss.model.sheetProperty.PaneInformation;
import com.app.office.ss.model.table.SSTable;
import com.app.office.ss.util.ReferenceUtil;
import com.app.office.system.AbortReaderError;
import com.app.office.system.IControl;
import com.app.office.system.IReader;
import com.app.office.system.StopReaderError;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SheetReader {
    private static SheetReader reader = new SheetReader();
    /* access modifiers changed from: private */
    public int defaultColWidth;
    /* access modifiers changed from: private */
    public int defaultRowHeight;
    /* access modifiers changed from: private */
    public IReader iReader;
    /* access modifiers changed from: private */
    public String key;
    /* access modifiers changed from: private */
    public boolean searched;
    /* access modifiers changed from: private */
    public Sheet sheet;

    public static SheetReader instance() {
        return reader;
    }

    /* JADX INFO: finally extract failed */
    public void getSheet(IControl iControl, ZipPackage zipPackage, Sheet sheet2, PackagePart packagePart, IReader iReader2) throws Exception {
        this.sheet = sheet2;
        this.iReader = iReader2;
        SAXReader sAXReader = new SAXReader();
        try {
            XLSXSaxHandler xLSXSaxHandler = new XLSXSaxHandler();
            sAXReader.addHandler("/worksheet/sheetFormatPr", xLSXSaxHandler);
            sAXReader.addHandler("/worksheet/cols/col", xLSXSaxHandler);
            sAXReader.addHandler("/worksheet/sheetData/row", xLSXSaxHandler);
            sAXReader.addHandler("/worksheet/sheetData/row/c", xLSXSaxHandler);
            sAXReader.addHandler("/worksheet/mergeCells/mergeCell", xLSXSaxHandler);
            InputStream inputStream = packagePart.getInputStream();
            Document read = sAXReader.read(inputStream);
            inputStream.close();
            Element rootElement = read.getRootElement();
            sAXReader.resetHandlers();
            Element element = rootElement.element("sheetViews").element("sheetView");
            if (element.element("pane") != null) {
                PaneInformation paneInformation = new PaneInformation();
                Element element2 = element.element("pane");
                if (element2.attributeValue("xSplit") != null) {
                    paneInformation.setVerticalSplitLeftColumn((short) Integer.parseInt(element2.attributeValue("xSplit")));
                }
                if (element2.attributeValue("ySplit") != null) {
                    paneInformation.setHorizontalSplitTopRow((short) Integer.parseInt(element2.attributeValue("ySplit")));
                }
                sheet2.setPaneInformation(paneInformation);
            }
            Map<String, String> sheetHyperlinkByRelation = getSheetHyperlinkByRelation(packagePart);
            PackageRelationshipCollection relationshipsByType = packagePart.getRelationshipsByType(PackageRelationshipTypes.TABLE_PART);
            if (relationshipsByType.size() > 0) {
                Iterator<PackageRelationship> it = relationshipsByType.iterator();
                while (it.hasNext()) {
                    TableReader.instance().read(iControl, zipPackage.getPart(it.next().getTargetURI()), sheet2);
                }
            }
            PackageRelationshipCollection relationshipsByType2 = packagePart.getRelationshipsByType(PackageRelationshipTypes.DRAWING_PART);
            if (relationshipsByType2.size() > 0) {
                DrawingReader.instance().read(iControl, zipPackage, zipPackage.getPart(relationshipsByType2.getRelationship(0).getTargetURI()), sheet2);
            }
            DrawingReader.instance().processOLEPicture(iControl, zipPackage, packagePart, sheet2, rootElement.element("oleObjects"));
            PictureReader.instance().dispose();
            getSheetHyperlink(sheet2, sheetHyperlinkByRelation, rootElement.element("hyperlinks"));
            checkTableCell(sheet2);
            sheet2.setState(2);
            dispose();
        } catch (Throwable th) {
            sAXReader.resetHandlers();
            throw th;
        }
    }

    private Map<String, String> getSheetHyperlinkByRelation(PackagePart packagePart) throws Exception {
        PackageRelationshipCollection relationshipsByType = packagePart.getRelationshipsByType(PackageRelationshipTypes.HYPERLINK_PART);
        HashMap hashMap = new HashMap(relationshipsByType.size());
        Iterator<PackageRelationship> it = relationshipsByType.iterator();
        while (it.hasNext()) {
            PackageRelationship next = it.next();
            hashMap.put(next.getId(), next.getTargetURI().toString());
        }
        return hashMap;
    }

    private void getSheetHyperlink(Sheet sheet2, Map<String, String> map, Element element) {
        Cell cell;
        if (element != null) {
            Iterator elementIterator = element.elementIterator();
            while (elementIterator.hasNext()) {
                Element element2 = (Element) elementIterator.next();
                String attributeValue = element2.attributeValue(OSOutcomeConstants.OUTCOME_ID);
                String attributeValue2 = element2.attributeValue("ref");
                Row row = sheet2.getRow(ReferenceUtil.instance().getRowIndex(attributeValue2));
                if (!(row == null || (cell = row.getCell(ReferenceUtil.instance().getColumnIndex(attributeValue2))) == null)) {
                    Hyperlink hyperlink = new Hyperlink();
                    String str = map.get(attributeValue);
                    if (str == null) {
                        hyperlink.setLinkType(2);
                        str = element2.attributeValue(FirebaseAnalytics.Param.LOCATION);
                    } else if (str.contains("mailto")) {
                        hyperlink.setLinkType(3);
                    } else if (str.contains("http")) {
                        hyperlink.setLinkType(1);
                    } else {
                        hyperlink.setLinkType(4);
                    }
                    hyperlink.setAddress(str);
                    cell.setHyperLink(hyperlink);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void setColumnProperty(Element element) {
        boolean z = true;
        int parseInt = Integer.parseInt(element.attributeValue("min")) - 1;
        int parseInt2 = Integer.parseInt(element.attributeValue("max")) - 1;
        double parseDouble = element.attributeValue(HtmlTags.WIDTH) != null ? Double.parseDouble(element.attributeValue(HtmlTags.WIDTH)) * 6.0d * 1.3333333730697632d : 0.0d;
        if (element.attributeValue(CellUtil.HIDDEN) == null || Integer.parseInt(element.attributeValue(CellUtil.HIDDEN)) == 0) {
            z = false;
        }
        this.sheet.addColumnInfo(new ColumnInfo(parseInt, parseInt2, (float) ((int) parseDouble), element.attributeValue(HtmlTags.STYLE) != null ? Integer.parseInt(element.attributeValue(HtmlTags.STYLE)) : 0, z));
    }

    /* access modifiers changed from: private */
    public void getSheetMergerdCells(Element element) {
        CellRangeAddress cellRangeAddress = getCellRangeAddress(element.attributeValue("ref"));
        if (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow() != 1048575 && cellRangeAddress.getLastColumn() - cellRangeAddress.getFirstColumn() != 16383) {
            int addMergeRange = this.sheet.addMergeRange(cellRangeAddress) - 1;
            for (int firstRow = cellRangeAddress.getFirstRow(); firstRow <= cellRangeAddress.getLastRow(); firstRow++) {
                Row row = this.sheet.getRow(firstRow);
                if (row == null) {
                    row = new Row(cellRangeAddress.getLastColumn() - cellRangeAddress.getFirstColumn());
                    row.setSheet(this.sheet);
                    row.setRowNumber(firstRow);
                    this.sheet.addRow(row);
                }
                for (int firstColumn = cellRangeAddress.getFirstColumn(); firstColumn <= cellRangeAddress.getLastColumn(); firstColumn++) {
                    Cell cell = row.getCell(firstColumn);
                    if (cell == null) {
                        cell = new Cell(3);
                        cell.setRowNumber(firstRow);
                        cell.setColNumber(firstColumn);
                        cell.setSheet(this.sheet);
                        cell.setCellStyle(row.getRowStyle());
                        row.addCell(cell);
                    }
                    cell.setRangeAddressIndex(addMergeRange);
                }
            }
        }
    }

    private CellRangeAddress getCellRangeAddress(String str) {
        String[] split = str.split(":");
        return new CellRangeAddress(ReferenceUtil.instance().getRowIndex(split[0]), ReferenceUtil.instance().getColumnIndex(split[0]), ReferenceUtil.instance().getRowIndex(split[1]), ReferenceUtil.instance().getColumnIndex(split[1]));
    }

    private void checkTableCell(Sheet sheet2) {
        SSTable[] tables = sheet2.getTables();
        if (tables != null) {
            sheet2.getWorkbook();
            for (SSTable sSTable : tables) {
                CellRangeAddress tableReference = sSTable.getTableReference();
                for (int firstRow = tableReference.getFirstRow(); firstRow <= tableReference.getLastRow(); firstRow++) {
                    Row row = sheet2.getRow(firstRow);
                    if (row == null) {
                        row = new Row((tableReference.getLastColumn() - tableReference.getFirstColumn()) + 1);
                        row.setSheet(sheet2);
                        row.setRowNumber(firstRow);
                        row.setFirstCol(tableReference.getFirstColumn());
                        row.setLastCol(tableReference.getLastColumn());
                        row.setInitExpandedRangeAddress(true);
                        sheet2.addRow(row);
                    }
                    for (int firstColumn = tableReference.getFirstColumn(); firstColumn <= tableReference.getLastColumn(); firstColumn++) {
                        Cell cell = row.getCell(firstColumn);
                        if (cell == null) {
                            cell = new Cell(3);
                            cell.setColNumber(firstColumn);
                            cell.setRowNumber(row.getRowNumber());
                            cell.setSheet(sheet2);
                            cell.setCellStyle(row.getRowStyle());
                            row.addCell(cell);
                        }
                        cell.setTableInfo(sSTable);
                    }
                }
            }
        }
    }

    public boolean searchContent(ZipPackage zipPackage, IReader iReader2, PackagePart packagePart, String str) throws Exception {
        this.key = str;
        this.searched = false;
        this.iReader = iReader2;
        SAXReader sAXReader = new SAXReader();
        try {
            sAXReader.addHandler("/worksheet/sheetData/row/c", new XLSXSearchSaxHandler());
            InputStream inputStream = packagePart.getInputStream();
            sAXReader.read(inputStream);
            inputStream.close();
            sAXReader.resetHandlers();
            return this.searched;
        } catch (StopReaderError unused) {
            sAXReader.resetHandlers();
            return true;
        } catch (Throwable th) {
            sAXReader.resetHandlers();
            throw th;
        }
    }

    class XLSXSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        XLSXSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!SheetReader.this.iReader.isAborted()) {
                Element current = elementPath.getCurrent();
                String name = current.getName();
                if (name.equals("sheetFormatPr")) {
                    if (current.attributeValue("defaultRowHeight") != null) {
                        int unused = SheetReader.this.defaultRowHeight = (int) (Double.parseDouble(current.attributeValue("defaultRowHeight")) * 1.3333333730697632d);
                        SheetReader.this.sheet.setDefaultRowHeight(SheetReader.this.defaultRowHeight);
                    }
                    if (current.attributeValue("defaultColWidth") != null) {
                        int unused2 = SheetReader.this.defaultColWidth = (int) (Double.parseDouble(current.attributeValue("defaultColWidth")) * 6.0d * 1.3333333730697632d);
                        SheetReader.this.sheet.setDefaultColWidth(SheetReader.this.defaultColWidth);
                    }
                } else if (name.equals("col")) {
                    SheetReader.this.setColumnProperty(current);
                } else if (name.equals("row")) {
                    int parseInt = Integer.parseInt(current.attributeValue("r")) - 1;
                    if (SheetReader.this.sheet.getRow(parseInt) == null) {
                        Sheet access$200 = SheetReader.this.sheet;
                        SheetReader sheetReader = SheetReader.this;
                        access$200.addRow(sheetReader.createRow(current, sheetReader.defaultRowHeight));
                    } else {
                        SheetReader sheetReader2 = SheetReader.this;
                        sheetReader2.modifyRow(sheetReader2.sheet.getRow(parseInt), current, SheetReader.this.defaultRowHeight);
                    }
                } else if (name.equals("c")) {
                    String attributeValue = current.attributeValue("r");
                    int rowIndex = ReferenceUtil.instance().getRowIndex(attributeValue);
                    int columnIndex = ReferenceUtil.instance().getColumnIndex(attributeValue);
                    Row row = SheetReader.this.sheet.getRow(rowIndex);
                    Cell cell = null;
                    if (row != null) {
                        cell = row.getCell(columnIndex, false);
                    } else {
                        row = new Row(columnIndex);
                        row.setRowNumber(rowIndex);
                        row.setSheet(SheetReader.this.sheet);
                        SheetReader.this.sheet.addRow(row);
                    }
                    if (cell == null) {
                        cell = CellReader.instance().getCell(SheetReader.this.sheet, current);
                    }
                    if (cell != null) {
                        cell.setSheet(SheetReader.this.sheet);
                        row.addCell(cell);
                    }
                } else if (name.equals("mergeCell")) {
                    SheetReader.this.getSheetMergerdCells(current);
                }
                current.detach();
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    private boolean isValidateRow(Element element) {
        if (element.attributeValue("ht") != null) {
            return true;
        }
        if (element.attributeValue(HtmlTags.S) == null) {
            return false;
        }
        if (Workbook.isValidateStyle(this.sheet.getWorkbook().getCellStyle(Integer.parseInt(element.attributeValue(HtmlTags.S))))) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public Row createRow(Element element, int i) {
        if (!isValidateRow(element)) {
            return null;
        }
        boolean z = true;
        int parseInt = Integer.parseInt(element.attributeValue("r")) - 1;
        String attributeValue = element.attributeValue("spans");
        float f = (float) i;
        if (element.attributeValue("ht") != null) {
            f = Float.parseFloat(element.attributeValue("ht")) * 1.3333334f;
        }
        int i2 = 0;
        if (element.attributeValue(CellUtil.HIDDEN) == null || Integer.parseInt(element.attributeValue(CellUtil.HIDDEN)) == 0) {
            z = false;
        }
        if (element.attributeValue(HtmlTags.S) != null) {
            i2 = Integer.parseInt(element.attributeValue(HtmlTags.S));
        }
        Row row = new Row(getEndBySpans(attributeValue));
        row.setRowNumber(parseInt);
        row.setRowPixelHeight(f);
        row.setZeroHeight(z);
        row.setSheet(this.sheet);
        row.setRowStyle(i2);
        row.completed();
        return row;
    }

    /* access modifiers changed from: private */
    public void modifyRow(Row row, Element element, int i) {
        if (element.attributeValue("ht") != null) {
            i = (int) (Double.parseDouble(element.attributeValue("ht")) * 1.3333333730697632d);
        }
        int i2 = 0;
        boolean z = (element.attributeValue(CellUtil.HIDDEN) == null || Integer.parseInt(element.attributeValue(CellUtil.HIDDEN)) == 0) ? false : true;
        if (element.attributeValue(HtmlTags.S) != null) {
            i2 = Integer.parseInt(element.attributeValue(HtmlTags.S));
        }
        row.setRowPixelHeight((float) i);
        row.setZeroHeight(z);
        row.setRowStyle(i2);
        row.completed();
    }

    private int getEndBySpans(String str) {
        if (str == null) {
            return 0;
        }
        String[] split = str.split(" ");
        return Integer.parseInt(split[split.length - 1].split(":")[1], 16) - 1;
    }

    class XLSXSearchSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        XLSXSearchSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!SheetReader.this.iReader.isAborted()) {
                Element current = elementPath.getCurrent();
                if (current.getName().equals("c") && CellReader.instance().searchContent(current, SheetReader.this.key)) {
                    boolean unused = SheetReader.this.searched = true;
                }
                current.detach();
                if (SheetReader.this.searched) {
                    throw new StopReaderError("stop");
                }
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    private void dispose() {
        this.sheet = null;
        this.iReader = null;
        this.key = null;
    }
}
