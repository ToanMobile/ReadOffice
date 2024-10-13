package com.app.office.ss.model.baseModel;

import androidx.core.view.ViewCompat;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.other.ExpandedCellRangeAddress;
import java.util.Collection;
import java.util.Hashtable;

public class Row {
    protected Hashtable<Integer, Cell> cells;
    protected int firstCol;
    protected int lastCol;
    protected int rowNumber;
    private float rowPixelHeight = 18.0f;
    private RowProperty rowProp;
    protected Sheet sheet;
    protected int styleIndex;

    public Row(int i) {
        this.lastCol = i;
        this.cells = new Hashtable<>(i);
        this.rowProp = new RowProperty();
    }

    public void setSheet(Sheet sheet2) {
        this.sheet = sheet2;
    }

    private Cell retrieveCell(int i, boolean z) {
        if (i < 0) {
            return null;
        }
        try {
            Cell cell = this.cells.get(Integer.valueOf(i));
            if (cell != null || !z) {
                return cell;
            }
            Cell createCellByStyle = createCellByStyle(this.styleIndex, i);
            return createCellByStyle == null ? createCellByStyle(this.sheet.getColumnStyle(i), i) : createCellByStyle;
        } catch (Exception unused) {
            return null;
        }
    }

    private Cell createCellByStyle(int i, int i2) {
        CellStyle cellStyle = this.sheet.getWorkbook().getCellStyle(i);
        if (cellStyle == null || ((cellStyle.getFillPatternType() != 0 || (cellStyle.getFgColor() & ViewCompat.MEASURED_SIZE_MASK) == 16777215) && cellStyle.getBorderLeft() <= 0 && cellStyle.getBorderTop() <= 0 && cellStyle.getBorderRight() <= 0 && cellStyle.getBorderBottom() <= 0)) {
            return null;
        }
        Cell cell = new Cell(0);
        cell.setColNumber(i2);
        cell.setRowNumber(this.rowNumber);
        cell.setCellStyle(i);
        cell.setSheet(this.sheet);
        this.cells.put(Integer.valueOf(i2), cell);
        return cell;
    }

    public Cell getCell(int i) {
        return retrieveCell(i, true);
    }

    public Cell getCell(int i, boolean z) {
        return retrieveCell(i, z);
    }

    public Collection<Cell> cellCollection() {
        return this.cells.values();
    }

    public void addCell(Cell cell) {
        int colNumber = cell.getColNumber();
        this.cells.put(Integer.valueOf(colNumber), cell);
        this.firstCol = Math.min(this.firstCol, colNumber);
        this.lastCol = Math.max(this.lastCol, colNumber + 1);
    }

    public int getFirstCol() {
        return this.firstCol;
    }

    public void setFirstCol(int i) {
        this.firstCol = i;
    }

    public int getLastCol() {
        return this.lastCol;
    }

    public void setLastCol(int i) {
        this.lastCol = i;
    }

    public int getRowStyle() {
        return this.styleIndex;
    }

    public void setRowStyle(int i) {
        this.styleIndex = i;
    }

    public float getRowPixelHeight() {
        return this.rowPixelHeight;
    }

    public void setRowPixelHeight(float f) {
        this.rowPixelHeight = f;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(int i) {
        this.rowNumber = i;
    }

    public boolean isEmpty() {
        return this.cells.size() == 0;
    }

    public boolean isZeroHeight() {
        return this.rowProp.isZeroHeight();
    }

    public void setZeroHeight(boolean z) {
        this.rowProp.setRowProperty(0, Boolean.valueOf(z));
    }

    public int getPhysicalNumberOfCells() {
        return this.cells.size();
    }

    public void removeAllCells() {
        for (Cell dispose : this.cells.values()) {
            dispose.dispose();
        }
        this.cells.clear();
    }

    public void removeCellsForHiddenRow() {
        if (this.rowProp.isZeroHeight()) {
            for (Cell next : this.cells.values()) {
                if (next.getRangeAddressIndex() < 0) {
                    next.dispose();
                }
            }
        }
    }

    public void completed() {
        this.rowProp.setRowProperty(1, true);
    }

    public boolean isCompleted() {
        return this.rowProp.isCompleted();
    }

    public void setInitExpandedRangeAddress(boolean z) {
        this.rowProp.setRowProperty(2, Boolean.valueOf(z));
    }

    public boolean isInitExpandedRangeAddress() {
        return this.rowProp.isInitExpandedRangeAddr();
    }

    public void addExpandedRangeAddress(int i, ExpandedCellRangeAddress expandedCellRangeAddress) {
        this.rowProp.setRowProperty(3, expandedCellRangeAddress);
    }

    public int getExpandedCellCount() {
        return this.rowProp.getExpandedCellCount();
    }

    public ExpandedCellRangeAddress getExpandedRangeAddress(int i) {
        return this.rowProp.getExpandedCellRangeAddr(i);
    }

    public void dispose() {
        removeAllCells();
        RowProperty rowProperty = this.rowProp;
        if (rowProperty != null) {
            rowProperty.dispose();
            this.rowProp = null;
        }
        this.sheet = null;
        this.cells = null;
    }
}
