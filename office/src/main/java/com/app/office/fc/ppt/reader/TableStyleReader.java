package com.app.office.fc.ppt.reader;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.tableStyle.TableCellBorders;
import com.app.office.pg.model.tableStyle.TableCellStyle;
import com.app.office.pg.model.tableStyle.TableStyle;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.AttributeSetImpl;
import java.io.InputStream;
import kotlinx.coroutines.DebugKt;

public class TableStyleReader {
    private static TableStyleReader tableStyleReader = new TableStyleReader();
    private int defaultFontSize = 12;
    private PGModel pgModel = null;

    public static TableStyleReader instance() {
        return tableStyleReader;
    }

    public void read(PGModel pGModel, PackagePart packagePart, int i) throws Exception {
        this.pgModel = pGModel;
        this.defaultFontSize = i;
        SAXReader sAXReader = new SAXReader();
        try {
            InputStream inputStream = packagePart.getInputStream();
            sAXReader.addHandler("/tblStyleLst/tblStyle", new TableStyleSaxHandler());
            sAXReader.read(inputStream);
            inputStream.close();
            sAXReader.resetHandlers();
        } catch (Exception e) {
            throw e;
        } catch (Throwable th) {
            sAXReader.resetHandlers();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void processTableStyle(Element element) {
        TableStyle tableStyle = new TableStyle();
        String attributeValue = element.attributeValue("styleId");
        Element element2 = element.element("wholeTbl");
        if (element2 != null) {
            tableStyle.setWholeTable(processTableCellStyle(element2));
        }
        Element element3 = element.element("band1H");
        if (element3 != null) {
            tableStyle.setBand1H(processTableCellStyle(element3));
        }
        Element element4 = element.element("band2H");
        if (element4 != null) {
            tableStyle.setBand2H(processTableCellStyle(element4));
        }
        Element element5 = element.element("band1V");
        if (element5 != null) {
            tableStyle.setBand1V(processTableCellStyle(element5));
        }
        Element element6 = element.element("band2V");
        if (element6 != null) {
            tableStyle.setBand2V(processTableCellStyle(element6));
        }
        Element element7 = element.element("lastCol");
        if (element7 != null) {
            tableStyle.setLastCol(processTableCellStyle(element7));
        }
        Element element8 = element.element("firstCol");
        if (element8 != null) {
            tableStyle.setFirstCol(processTableCellStyle(element8));
        }
        Element element9 = element.element("lastRow");
        if (element9 != null) {
            tableStyle.setLastRow(processTableCellStyle(element9));
        }
        Element element10 = element.element("firstRow");
        if (element10 != null) {
            tableStyle.setFirstRow(processTableCellStyle(element10));
        }
        this.pgModel.putTableStyle(attributeValue, tableStyle);
    }

    private TableCellStyle processTableCellStyle(Element element) {
        TableCellStyle tableCellStyle = new TableCellStyle();
        Element element2 = element.element("tcTxStyle");
        if (element2 != null) {
            AttributeSetImpl attributeSetImpl = new AttributeSetImpl();
            if (DebugKt.DEBUG_PROPERTY_VALUE_ON.equals(element2.attributeValue(HtmlTags.B))) {
                AttrManage.instance().setFontBold(attributeSetImpl, true);
            }
            if (DebugKt.DEBUG_PROPERTY_VALUE_ON.equals(element2.attributeValue("i"))) {
                AttrManage.instance().setFontItalic(attributeSetImpl, true);
            }
            AttrManage.instance().setFontSize(attributeSetImpl, this.defaultFontSize);
            tableCellStyle.setFontAttributeSet(attributeSetImpl);
        }
        Element element3 = element.element("tcStyle");
        Element element4 = element3.element("tcBdr");
        if (element4 != null) {
            tableCellStyle.setTableCellBorders(getTableCellBorders(element4));
        }
        tableCellStyle.setTableCellBgFill(element3.element("fill"));
        return tableCellStyle;
    }

    private TableCellBorders getTableCellBorders(Element element) {
        TableCellBorders tableCellBorders = new TableCellBorders();
        Element element2 = element.element(HtmlTags.ALIGN_LEFT);
        if (element2 != null) {
            tableCellBorders.setLeftBorder(element2.element("ln"));
        }
        Element element3 = element.element(HtmlTags.ALIGN_RIGHT);
        if (element3 != null) {
            tableCellBorders.setRightBorder(element3.element("ln"));
        }
        Element element4 = element.element(HtmlTags.ALIGN_TOP);
        if (element4 != null) {
            tableCellBorders.setTopBorder(element4.element("ln"));
        }
        Element element5 = element.element(HtmlTags.ALIGN_BOTTOM);
        if (element5 != null) {
            tableCellBorders.setBottomBorder(element5.element("ln"));
        }
        return tableCellBorders;
    }

    class TableStyleSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        TableStyleSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            Element current = elementPath.getCurrent();
            try {
                if (current.getName().equals("tblStyle")) {
                    TableStyleReader.this.processTableStyle(current);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            current.detach();
        }
    }
}
