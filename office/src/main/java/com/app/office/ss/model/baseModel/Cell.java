package com.app.office.ss.model.baseModel;

import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.simpletext.view.STRoot;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.model.table.SSTable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Cell {
    private static Calendar CALENDAR = new GregorianCalendar();
    public static final short CELL_TYPE_BLANK = 3;
    public static final short CELL_TYPE_BOOLEAN = 4;
    public static final short CELL_TYPE_ERROR = 5;
    public static final short CELL_TYPE_FORMULA = 2;
    public static final short CELL_TYPE_NUMERIC = 0;
    public static final short CELL_TYPE_NUMERIC_ACCOUNTING = 8;
    public static final short CELL_TYPE_NUMERIC_DECIMAL = 7;
    public static final short CELL_TYPE_NUMERIC_FRACTIONAL = 9;
    public static final short CELL_TYPE_NUMERIC_GENERAL = 6;
    public static final short CELL_TYPE_NUMERIC_SIMPLEDATE = 10;
    public static final short CELL_TYPE_NUMERIC_STRING = 11;
    public static final short CELL_TYPE_STRING = 1;
    private static final long DAY_MILLISECONDS = 86400000;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_MINUTE = 60;
    protected short cellType;
    protected int colNumber;
    private CellProperty prop = new CellProperty();
    protected int rowNumber;
    protected Sheet sheet;
    protected int styleIndex;
    protected Object value;

    public Cell(short s) {
        this.cellType = s;
    }

    public void setSheet(Sheet sheet2) {
        this.sheet = sheet2;
    }

    public Sheet getSheet() {
        return this.sheet;
    }

    public void setCellType(short s) {
        this.cellType = s;
    }

    public short getCellType() {
        return this.cellType;
    }

    public void setCellNumericType(short s) {
        if (this.cellType == 0) {
            this.prop.setCellProp(0, Short.valueOf(s));
        }
    }

    public short getCellNumericType() {
        return this.prop.getCellNumericType();
    }

    public void setCellValue(Object obj) {
        this.value = obj;
    }

    public int getStringCellValueIndex() {
        Object obj;
        if (this.cellType != 1 || (obj = this.value) == null) {
            return -1;
        }
        return ((Integer) obj).intValue();
    }

    public double getNumberValue() {
        Object obj;
        if (this.cellType != 0 || (obj = this.value) == null) {
            return Double.NaN;
        }
        return ((Double) obj).doubleValue();
    }

    public int getErrorValue() {
        Object obj;
        if (this.cellType != 5 || (obj = this.value) == null) {
            return -1;
        }
        return ((Byte) obj).byteValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
        r0 = r2.value;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte getErrorCodeValue() {
        /*
            r2 = this;
            short r0 = r2.cellType
            r1 = 5
            if (r0 != r1) goto L_0x0010
            java.lang.Object r0 = r2.value
            if (r0 == 0) goto L_0x0010
            java.lang.Byte r0 = (java.lang.Byte) r0
            byte r0 = r0.byteValue()
            return r0
        L_0x0010:
            r0 = -128(0xffffffffffffff80, float:NaN)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.model.baseModel.Cell.getErrorCodeValue():byte");
    }

    public String getCellFormulaValue() {
        Object obj;
        if (this.cellType != 2 || (obj = this.value) == null) {
            return null;
        }
        return (String) obj;
    }

    public boolean getBooleanValue() {
        Object obj;
        if (this.cellType != 4 || (obj = this.value) == null) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }

    public Date getDateCellValue(boolean z) {
        Object obj;
        if (this.cellType != 0 || (obj = this.value) == null) {
            return null;
        }
        double doubleValue = ((Double) obj).doubleValue();
        int floor = (int) Math.floor(doubleValue);
        int i = (int) (((doubleValue - ((double) floor)) * 8.64E7d) + 0.5d);
        int i2 = z ? 1904 : 1900;
        int i3 = z ? 1 : floor < 61 ? 0 : -1;
        CALENDAR.clear();
        CALENDAR.set(i2, 0, floor + i3, 0, 0, 0);
        CALENDAR.set(14, i);
        return CALENDAR.getTime();
    }

    public int getRangeAddressIndex() {
        return this.prop.getCellMergeRangeAddressIndex();
    }

    public void setRangeAddressIndex(int i) {
        this.prop.setCellProp(1, Integer.valueOf(i));
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(int i) {
        this.rowNumber = i;
    }

    public int getColNumber() {
        return this.colNumber;
    }

    public void setColNumber(int i) {
        this.colNumber = i;
    }

    public Hyperlink getHyperLink() {
        return this.prop.getCellHyperlink();
    }

    public void setHyperLink(Hyperlink hyperlink) {
        this.prop.setCellProp(3, hyperlink);
    }

    public CellStyle getCellStyle() {
        return this.sheet.getWorkbook().getCellStyle(this.styleIndex);
    }

    public void setCellStyle(int i) {
        this.styleIndex = i;
    }

    public boolean hasValidValue() {
        return this.value != null;
    }

    public void setSTRoot(STRoot sTRoot) {
        if (this.sheet.getState() == 2) {
            this.prop.setCellProp(4, Integer.valueOf(this.sheet.addSTRoot(sTRoot)));
        }
    }

    public STRoot getSTRoot() {
        return this.sheet.getSTRoot(this.prop.getCellSTRoot());
    }

    public void removeSTRoot() {
        this.prop.removeCellSTRoot();
    }

    public void setExpandedRangeAddressIndex(int i) {
        this.prop.setCellProp(2, Integer.valueOf(i));
    }

    public int getExpandedRangeAddressIndex() {
        return this.prop.getExpandCellRangeAddressIndex();
    }

    public void setTableInfo(SSTable sSTable) {
        this.prop.setCellProp(5, sSTable);
    }

    public SSTable getTableInfo() {
        return this.prop.getTableInfo();
    }

    public void dispose() {
        this.sheet = null;
        this.value = null;
        CellProperty cellProperty = this.prop;
        if (cellProperty != null) {
            cellProperty.dispose();
            this.prop = null;
        }
    }
}
