package com.app.office.fc.xls.Reader.table;

import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.table.SSTable;
import com.app.office.ss.util.ReferenceUtil;
import com.app.office.system.IControl;
import java.io.InputStream;

public class TableReader {
    private static TableReader reader = new TableReader();

    public static TableReader instance() {
        return reader;
    }

    public void read(IControl iControl, PackagePart packagePart, Sheet sheet) throws Exception {
        SAXReader sAXReader = new SAXReader();
        try {
            InputStream inputStream = packagePart.getInputStream();
            Document read = sAXReader.read(inputStream);
            inputStream.close();
            SSTable sSTable = new SSTable();
            Element rootElement = read.getRootElement();
            String[] split = rootElement.attributeValue("ref").split(":");
            if (split != null && split.length == 2) {
                sSTable.setTableReference(new CellRangeAddress(ReferenceUtil.instance().getRowIndex(split[0]), ReferenceUtil.instance().getColumnIndex(split[0]), ReferenceUtil.instance().getRowIndex(split[1]), ReferenceUtil.instance().getColumnIndex(split[1])));
            }
            String attributeValue = rootElement.attributeValue("totalsRowDxfId");
            if (attributeValue != null) {
                sSTable.setTotalsRowDxfId(Integer.parseInt(attributeValue));
            }
            String attributeValue2 = rootElement.attributeValue("totalsRowBorderDxfId");
            if (attributeValue2 != null) {
                sSTable.setTotalsRowBorderDxfId(Integer.parseInt(attributeValue2));
            }
            String attributeValue3 = rootElement.attributeValue("headerRowDxfId");
            if (attributeValue3 != null) {
                sSTable.setHeaderRowDxfId(Integer.parseInt(attributeValue3));
            }
            String attributeValue4 = rootElement.attributeValue("headerRowBorderDxfId");
            if (attributeValue4 != null) {
                sSTable.setHeaderRowBorderDxfId(Integer.parseInt(attributeValue4));
            }
            String attributeValue5 = rootElement.attributeValue("tableBorderDxfId");
            if (attributeValue5 != null) {
                sSTable.setTableBorderDxfId(Integer.parseInt(attributeValue5));
            }
            if ("0".equalsIgnoreCase(rootElement.attributeValue("headerRowCount"))) {
                sSTable.setHeaderRowShown(false);
            }
            String attributeValue6 = rootElement.attributeValue("totalsRowCount");
            if (attributeValue6 == null) {
                attributeValue6 = "0";
            }
            if (!"0".equalsIgnoreCase(rootElement.attributeValue("totalsRowShown")) && "1".equalsIgnoreCase(attributeValue6)) {
                sSTable.setTotalRowShown(true);
            }
            Element element = rootElement.element("tableStyleInfo");
            if (element != null) {
                sSTable.setName(element.attributeValue("name"));
                if (!"0".equalsIgnoreCase(element.attributeValue("showFirstColumn"))) {
                    sSTable.setShowFirstColumn(true);
                }
                if (!"0".equalsIgnoreCase(element.attributeValue("showLastColumn"))) {
                    sSTable.setShowLastColumn(true);
                }
                if (!"0".equalsIgnoreCase(element.attributeValue("showRowStripes"))) {
                    sSTable.setShowRowStripes(true);
                }
                if (!"0".equalsIgnoreCase(element.attributeValue("showColumnStripes"))) {
                    sSTable.setShowColumnStripes(true);
                }
                sheet.addTable(sSTable);
            }
            sAXReader.resetHandlers();
        } catch (Exception e) {
            throw e;
        } catch (Throwable th) {
            sAXReader.resetHandlers();
            throw th;
        }
    }
}
