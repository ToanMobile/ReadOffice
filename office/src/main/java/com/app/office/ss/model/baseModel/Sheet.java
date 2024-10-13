package com.app.office.ss.model.baseModel;

import androidx.core.view.ViewCompat;
import com.app.office.common.shape.IShape;
import com.app.office.simpletext.view.STRoot;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.interfacePart.IReaderListener;
import com.app.office.ss.model.sheetProperty.ColumnInfo;
import com.app.office.ss.model.sheetProperty.PaneInformation;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.model.table.SSTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sheet {
    public static final short ACTIVECELL_COLUMN = 2;
    public static final short ACTIVECELL_ROW = 1;
    public static final short ACTIVECELL_SINGLE = 0;
    public static final int INITIAL_CAPACITY = 20;
    public static final short State_Accomplished = 2;
    public static final short State_NotAccomplished = 0;
    public static final short State_Reading = 1;
    public static final short TYPE_CHARTSHEET = 1;
    public static final short TYPE_WORKSHEET = 0;
    private Cell activeCell;
    private int activeCellColumn;
    private int activeCellRow;
    private short activeCellType = 0;
    protected Workbook book;
    private List<ColumnInfo> columnInfoList;
    private int defaultColWidth = 72;
    private int defaultRowHeight = 18;
    private int firstRow;
    private IReaderListener iReaderListener;
    private boolean isGridsPrinted;
    private int lastRow;
    private float maxScrollX = 2.14748365E9f;
    private float maxScrollY = 2.14748365E9f;
    private List<CellRangeAddress> merges = new ArrayList();
    private PaneInformation paneInformation;
    private List<STRoot> rootViewMap;
    protected Map<Integer, Row> rows = new HashMap();
    private int scrollX;
    private int scrollY;
    protected List<IShape> shapesList = new ArrayList();
    private String sheetName;
    private short state;
    private List<SSTable> tableList;
    private short type;
    private float zoom = 1.0f;

    public PaneInformation getPaneInformation() {
        return null;
    }

    public void setColumnHidden(int i, boolean z) {
    }

    public void setWorkbook(Workbook workbook) {
        this.book = workbook;
    }

    public Workbook getWorkbook() {
        return this.book;
    }

    public void addRow(Row row) {
        if (row != null) {
            this.rows.put(Integer.valueOf(row.getRowNumber()), row);
            if (this.rows.size() == 1) {
                this.firstRow = row.getRowNumber();
                this.lastRow = row.getRowNumber();
                return;
            }
            this.firstRow = Math.min(this.firstRow, row.getRowNumber());
            this.lastRow = Math.max(this.lastRow, row.getRowNumber());
        }
    }

    public int addMergeRange(CellRangeAddress cellRangeAddress) {
        this.merges.add(cellRangeAddress);
        return this.merges.size();
    }

    public int getMergeRangeCount() {
        return this.merges.size();
    }

    public CellRangeAddress getMergeRange(int i) {
        if (i < 0 || i >= this.merges.size()) {
            return null;
        }
        return this.merges.get(i);
    }

    public Row getRow(int i) {
        return this.rows.get(Integer.valueOf(i));
    }

    public Row getRowByColumnsStyle(int i) {
        Row row = this.rows.get(Integer.valueOf(i));
        if (row != null) {
            return row;
        }
        List<ColumnInfo> list = this.columnInfoList;
        if (!(list == null || list.size() == 0)) {
            int i2 = 0;
            while (i2 < this.columnInfoList.size()) {
                int i3 = i2 + 1;
                CellStyle cellStyle = this.book.getCellStyle(this.columnInfoList.get(i2).getStyle());
                if (cellStyle == null || ((cellStyle.getFillPatternType() != 0 || (cellStyle.getFgColor() & ViewCompat.MEASURED_SIZE_MASK) == 16777215) && cellStyle.getBorderLeft() <= 0 && cellStyle.getBorderTop() <= 0 && cellStyle.getBorderRight() <= 0 && cellStyle.getBorderBottom() <= 0)) {
                    i2 = i3;
                } else {
                    Row row2 = new Row(1);
                    row2.setRowNumber(i);
                    row2.setRowPixelHeight((float) this.defaultRowHeight);
                    row2.setSheet(this);
                    row2.completed();
                    this.rows.put(Integer.valueOf(i), row2);
                    return row2;
                }
            }
        }
        return null;
    }

    public int getPhysicalNumberOfRows() {
        return this.rows.size();
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String str) {
        this.sheetName = str;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float f) {
        this.zoom = f;
    }

    public float getMaxScrollX() {
        return this.maxScrollX;
    }

    public float getMaxScrollY() {
        return this.maxScrollY;
    }

    public int getScrollX() {
        return this.scrollX;
    }

    public void setScrollX(int i) {
        this.scrollX = i;
    }

    public int getScrollY() {
        return this.scrollY;
    }

    public void setScrollY(int i) {
        this.scrollY = i;
    }

    public void setScroll(int i, int i2) {
        this.scrollX = i;
        this.scrollY = i2;
    }

    public int getFirstRowNum() {
        return this.firstRow;
    }

    public void setFirstRowNum(int i) {
        this.firstRow = i;
    }

    public int getLastRowNum() {
        return this.lastRow;
    }

    public void setLastRowNum(int i) {
        this.lastRow = i;
    }

    public void addColumnInfo(ColumnInfo columnInfo) {
        if (this.columnInfoList == null) {
            this.columnInfoList = new ArrayList();
        }
        this.columnInfoList.add(columnInfo);
    }

    public int getColumnStyle(int i) {
        if (this.columnInfoList != null) {
            int i2 = 0;
            while (i2 < this.columnInfoList.size()) {
                int i3 = i2 + 1;
                ColumnInfo columnInfo = this.columnInfoList.get(i2);
                if (columnInfo.getFirstCol() <= i && columnInfo.getLastCol() >= i) {
                    return columnInfo.getStyle();
                }
                i2 = i3;
            }
        }
        return 0;
    }

    public void setColumnPixelWidth(int i, int i2) {
        int i3 = i;
        int i4 = i2;
        if (this.columnInfoList != null) {
            int i5 = 0;
            while (i5 < this.columnInfoList.size()) {
                int i6 = i5 + 1;
                ColumnInfo columnInfo = this.columnInfoList.get(i5);
                if (columnInfo.getFirstCol() == i3 && columnInfo.getLastCol() == i3) {
                    columnInfo.setColWidth((float) i4);
                    return;
                } else if (columnInfo.getFirstCol() == i3) {
                    ColumnInfo columnInfo2 = new ColumnInfo(i3 + 1, columnInfo.getLastCol(), columnInfo.getColWidth(), columnInfo.getStyle(), columnInfo.isHidden());
                    columnInfo.setColWidth((float) i4);
                    columnInfo.setLastCol(i3);
                    this.columnInfoList.add(columnInfo2);
                    return;
                } else if (columnInfo.getLastCol() == i3) {
                    ColumnInfo columnInfo3 = new ColumnInfo(columnInfo.getFirstCol(), i3 - 1, columnInfo.getColWidth(), columnInfo.getStyle(), columnInfo.isHidden());
                    columnInfo.setColWidth((float) i4);
                    columnInfo.setFirstCol(i3);
                    this.columnInfoList.add(columnInfo3);
                    return;
                } else if (columnInfo.getFirstCol() >= i3 || columnInfo.getLastCol() <= i3) {
                    i5 = i6;
                } else {
                    ColumnInfo columnInfo4 = new ColumnInfo(columnInfo.getFirstCol(), i3 - 1, columnInfo.getColWidth(), columnInfo.getStyle(), columnInfo.isHidden());
                    ColumnInfo columnInfo5 = new ColumnInfo(i3 + 1, columnInfo.getLastCol(), columnInfo.getColWidth(), columnInfo.getStyle(), columnInfo.isHidden());
                    columnInfo.setFirstCol(i3);
                    columnInfo.setLastCol(i3);
                    columnInfo.setColWidth((float) i4);
                    this.columnInfoList.add(columnInfo4);
                    this.columnInfoList.add(columnInfo5);
                    return;
                }
            }
            this.columnInfoList.add(new ColumnInfo(i, i, (float) i4, 0, false));
            return;
        }
        ArrayList arrayList = new ArrayList();
        this.columnInfoList = arrayList;
        arrayList.add(new ColumnInfo(i, i, (float) i4, 0, false));
    }

    public float getColumnPixelWidth(int i) {
        if (this.columnInfoList != null) {
            int i2 = 0;
            while (i2 < this.columnInfoList.size()) {
                int i3 = i2 + 1;
                ColumnInfo columnInfo = this.columnInfoList.get(i2);
                if (columnInfo.getFirstCol() <= i && columnInfo.getLastCol() >= i) {
                    return columnInfo.getColWidth();
                }
                i2 = i3;
            }
        }
        return (float) this.defaultColWidth;
    }

    public ColumnInfo getColumnInfo(int i) {
        if (this.columnInfoList == null) {
            return null;
        }
        int i2 = 0;
        while (i2 < this.columnInfoList.size()) {
            int i3 = i2 + 1;
            ColumnInfo columnInfo = this.columnInfoList.get(i2);
            if (columnInfo.getFirstCol() <= i && columnInfo.getLastCol() >= i) {
                return columnInfo;
            }
            i2 = i3;
        }
        return null;
    }

    public boolean isGridsPrinted() {
        return this.isGridsPrinted;
    }

    public void setGridsPrinted(boolean z) {
        this.isGridsPrinted = z;
    }

    public void setPaneInformation(PaneInformation paneInformation2) {
        this.paneInformation = paneInformation2;
    }

    public boolean isColumnHidden(int i) {
        if (this.columnInfoList != null) {
            int i2 = 0;
            while (i2 < this.columnInfoList.size()) {
                int i3 = i2 + 1;
                ColumnInfo columnInfo = this.columnInfoList.get(i2);
                if (columnInfo.getFirstCol() <= i && columnInfo.getLastCol() >= i) {
                    return columnInfo.isHidden();
                }
                i2 = i3;
            }
        }
        return false;
    }

    public void setActiveCellType(short s) {
        this.activeCellType = s;
    }

    public short getActiveCellType() {
        return this.activeCellType;
    }

    private void checkActiveRowAndColumnBounds() {
        if (this.book.isBefore07Version()) {
            this.activeCellRow = Math.min(this.activeCellRow, 65535);
            this.activeCellColumn = Math.min(this.activeCellColumn, 255);
            return;
        }
        this.activeCellRow = Math.min(this.activeCellRow, 1048575);
        this.activeCellColumn = Math.min(this.activeCellColumn, 16383);
    }

    public void setActiveCellRow(int i) {
        this.activeCellRow = i;
        checkActiveRowAndColumnBounds();
    }

    public int getActiveCellRow() {
        return this.activeCellRow;
    }

    public void setActiveCellColumn(int i) {
        this.activeCellColumn = i;
        checkActiveRowAndColumnBounds();
    }

    public int getActiveCellColumn() {
        return this.activeCellColumn;
    }

    public void setActiveCellRowCol(int i, int i2) {
        int i3 = 0;
        this.activeCellType = 0;
        this.activeCellRow = i;
        this.activeCellColumn = i2;
        checkActiveRowAndColumnBounds();
        while (i3 < this.merges.size()) {
            int i4 = i3 + 1;
            CellRangeAddress cellRangeAddress = this.merges.get(i3);
            if (cellRangeAddress.isInRange(i, i2)) {
                this.activeCellRow = cellRangeAddress.getFirstRow();
                this.activeCellColumn = cellRangeAddress.getFirstColumn();
            }
            i3 = i4;
        }
        if (getRow(i) != null) {
            this.activeCell = getRow(i).getCell(i2);
        } else {
            this.activeCell = null;
        }
    }

    public Cell getActiveCell() {
        return this.activeCell;
    }

    public void setActiveCell(Cell cell) {
        this.activeCell = cell;
        if (cell != null) {
            this.activeCellRow = cell.getRowNumber();
            this.activeCellColumn = cell.getColNumber();
            return;
        }
        this.activeCellRow = -1;
        this.activeCellColumn = -1;
    }

    public void appendShapes(IShape iShape) {
        this.shapesList.add(iShape);
    }

    public IShape[] getShapes() {
        List<IShape> list = this.shapesList;
        return (IShape[]) list.toArray(new IShape[list.size()]);
    }

    public int getShapeCount() {
        return this.shapesList.size();
    }

    public IShape getShape(int i) {
        if (i < 0 || i >= this.shapesList.size()) {
            return null;
        }
        return this.shapesList.get(i);
    }

    public void setDefaultRowHeight(int i) {
        this.defaultRowHeight = i;
    }

    public int getDefaultRowHeight() {
        return this.defaultRowHeight;
    }

    public void setDefaultColWidth(int i) {
        this.defaultColWidth = i;
    }

    public int getDefaultColWidth() {
        return this.defaultColWidth;
    }

    public void setSheetType(short s) {
        this.type = s;
    }

    public short getSheetType() {
        return this.type;
    }

    public void setState(short s) {
        IReaderListener iReaderListener2;
        this.state = s;
        if (s == 2 && (iReaderListener2 = this.iReaderListener) != null) {
            iReaderListener2.OnReadingFinished();
        }
        this.maxScrollX = 0.0f;
        this.maxScrollY = 0.0f;
        int i = 0;
        List<ColumnInfo> list = this.columnInfoList;
        if (list != null) {
            for (ColumnInfo next : list) {
                i += (next.getLastCol() - next.getFirstCol()) + 1;
                if (!next.isHidden()) {
                    this.maxScrollX += next.getColWidth() * ((float) ((next.getLastCol() - next.getFirstCol()) + 1));
                }
            }
        }
        int size = this.rows.size();
        for (Row rowPixelHeight : this.rows.values()) {
            this.maxScrollY += rowPixelHeight.getRowPixelHeight();
        }
        if (!this.book.isBefore07Version()) {
            this.maxScrollX += (float) ((16384 - i) * this.defaultColWidth);
            this.maxScrollY += (float) ((1048576 - size) * this.defaultRowHeight);
            return;
        }
        this.maxScrollX += (float) ((256 - i) * this.defaultColWidth);
        this.maxScrollY += (float) ((65536 - size) * this.defaultRowHeight);
    }

    public synchronized short getState() {
        return this.state;
    }

    public boolean isAccomplished() {
        return this.state == 2;
    }

    public void setReaderListener(IReaderListener iReaderListener2) {
        this.iReaderListener = iReaderListener2;
    }

    public int addSTRoot(STRoot sTRoot) {
        if (this.rootViewMap == null) {
            this.rootViewMap = new ArrayList();
        }
        int size = this.rootViewMap.size();
        this.rootViewMap.add(size, sTRoot);
        return size;
    }

    public STRoot getSTRoot(int i) {
        if (i < 0 || i >= this.rootViewMap.size()) {
            return null;
        }
        return this.rootViewMap.get(i);
    }

    public void removeSTRoot() {
        List<STRoot> list = this.rootViewMap;
        if (list != null) {
            int size = list.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                STRoot sTRoot = this.rootViewMap.get(i);
                if (sTRoot != null) {
                    sTRoot.dispose();
                }
                i = i2;
            }
            this.rootViewMap.clear();
        }
        int i3 = this.firstRow;
        while (i3 <= this.lastRow) {
            int i4 = i3 + 1;
            Row row = getRow(i3);
            if (row != null && (row == null || !row.isZeroHeight())) {
                row.setInitExpandedRangeAddress(false);
                for (Cell removeSTRoot : row.cellCollection()) {
                    removeSTRoot.removeSTRoot();
                }
            }
            i3 = i4;
        }
    }

    public void addTable(SSTable sSTable) {
        if (this.tableList == null) {
            this.tableList = new ArrayList();
        }
        this.tableList.add(sSTable);
    }

    public SSTable[] getTables() {
        List<SSTable> list = this.tableList;
        if (list != null) {
            return (SSTable[]) list.toArray(new SSTable[list.size()]);
        }
        return null;
    }

    public void dispose() {
        this.book = null;
        this.sheetName = null;
        this.paneInformation = null;
        this.iReaderListener = null;
        Cell cell = this.activeCell;
        if (cell != null) {
            cell.dispose();
            this.activeCell = null;
        }
        Map<Integer, Row> map = this.rows;
        if (map != null) {
            for (Row dispose : map.values()) {
                dispose.dispose();
            }
            this.rows.clear();
            this.rows = null;
        }
        List<CellRangeAddress> list = this.merges;
        if (list != null) {
            for (CellRangeAddress dispose2 : list) {
                dispose2.dispose();
            }
            this.merges.clear();
            this.merges = null;
        }
        List<ColumnInfo> list2 = this.columnInfoList;
        if (list2 != null) {
            list2.clear();
            this.columnInfoList = null;
        }
        List<IShape> list3 = this.shapesList;
        if (list3 != null) {
            for (IShape dispose3 : list3) {
                dispose3.dispose();
            }
            this.shapesList.clear();
            this.shapesList = null;
        }
        if (this.rootViewMap != null) {
            removeSTRoot();
            this.rootViewMap = null;
        }
        List<SSTable> list4 = this.tableList;
        if (list4 != null) {
            list4.clear();
            this.tableList = null;
        }
    }
}
