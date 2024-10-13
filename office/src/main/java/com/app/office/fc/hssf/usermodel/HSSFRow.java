package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.RowRecord;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.ICellStyle;
import com.app.office.fc.ss.usermodel.IRow;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class HSSFRow implements IRow {
    public static final int INITIAL_CAPACITY = 5;
    private HSSFWorkbook book;
    /* access modifiers changed from: private */
    public HSSFCell[] cells;
    private RowRecord row;
    private int rowNum;
    private int rowPixelHeight;
    private HSSFSheet sheet;

    HSSFRow(HSSFWorkbook hSSFWorkbook, HSSFSheet hSSFSheet, int i) {
        this(hSSFWorkbook, hSSFSheet, new RowRecord(i));
    }

    HSSFRow(HSSFWorkbook hSSFWorkbook, HSSFSheet hSSFSheet, RowRecord rowRecord) {
        this.rowPixelHeight = 18;
        this.book = hSSFWorkbook;
        this.sheet = hSSFSheet;
        this.row = rowRecord;
        setRowNum(rowRecord.getRowNumber());
        this.cells = new HSSFCell[(rowRecord.getLastCol() + 5)];
        rowRecord.setEmpty();
    }

    public HSSFCell createCell(short s) {
        return createCell((int) s);
    }

    public HSSFCell createCell(short s, int i) {
        return createCell((int) s, i);
    }

    public HSSFCell createCell(int i) {
        return createCell(i, 3);
    }

    public HSSFCell createCell(int i, int i2) {
        short s = (short) i;
        if (i > 32767) {
            s = (short) (65535 - i);
        }
        HSSFCell hSSFCell = new HSSFCell(this.book, this.sheet, getRowNum(), s, i2);
        addCell(hSSFCell);
        this.sheet.getSheet().addValueRecord(getRowNum(), hSSFCell.getCellValueRecord());
        return hSSFCell;
    }

    public void removeCell(ICell iCell) {
        if (iCell != null) {
            removeCell((HSSFCell) iCell, true);
            return;
        }
        throw new IllegalArgumentException("cell must not be null");
    }

    private void removeCell(HSSFCell hSSFCell, boolean z) {
        int columnIndex = hSSFCell.getColumnIndex();
        if (columnIndex >= 0) {
            HSSFCell[] hSSFCellArr = this.cells;
            if (columnIndex >= hSSFCellArr.length || hSSFCell != hSSFCellArr[columnIndex]) {
                throw new RuntimeException("Specified cell is not from this row");
            }
            if (hSSFCell.isPartOfArrayFormulaGroup()) {
                hSSFCell.notifyArrayFormulaChanging();
            }
            this.cells[columnIndex] = null;
            if (z) {
                this.sheet.getSheet().removeValueRecord(getRowNum(), hSSFCell.getCellValueRecord());
            }
            if (hSSFCell.getColumnIndex() + 1 == this.row.getLastCol()) {
                RowRecord rowRecord = this.row;
                rowRecord.setLastCol(calculateNewLastCellPlusOne(rowRecord.getLastCol()));
            }
            if (hSSFCell.getColumnIndex() == this.row.getFirstCol()) {
                RowRecord rowRecord2 = this.row;
                rowRecord2.setFirstCol(calculateNewFirstCell(rowRecord2.getFirstCol()));
                return;
            }
            return;
        }
        throw new RuntimeException("Negative cell indexes not allowed");
    }

    /* access modifiers changed from: protected */
    public void removeAllCells() {
        int i = 0;
        while (true) {
            HSSFCell[] hSSFCellArr = this.cells;
            if (i < hSSFCellArr.length) {
                if (hSSFCellArr[i] != null) {
                    removeCell(hSSFCellArr[i], true);
                }
                i++;
            } else {
                this.cells = new HSSFCell[5];
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public HSSFCell createCellFromRecord(CellValueRecordInterface cellValueRecordInterface) {
        HSSFCell hSSFCell = new HSSFCell(this.book, this.sheet, cellValueRecordInterface);
        addCell(hSSFCell);
        short column = cellValueRecordInterface.getColumn();
        if (this.row.isEmpty()) {
            this.row.setFirstCol(column);
            this.row.setLastCol(column + 1);
        } else if (column < this.row.getFirstCol()) {
            this.row.setFirstCol(column);
        } else if (column > this.row.getLastCol()) {
            this.row.setLastCol(column + 1);
        }
        return hSSFCell;
    }

    public void setRowNum(int i) {
        int lastRowIndex = SpreadsheetVersion.EXCEL97.getLastRowIndex();
        if (i < 0 || i > lastRowIndex) {
            throw new IllegalArgumentException("Invalid row number (" + i + ") outside allowable range (0.." + lastRowIndex + ")");
        }
        this.rowNum = i;
        RowRecord rowRecord = this.row;
        if (rowRecord != null) {
            rowRecord.setRowNumber(i);
        }
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public HSSFSheet getSheet() {
        return this.sheet;
    }

    /* access modifiers changed from: protected */
    public int getOutlineLevel() {
        return this.row.getOutlineLevel();
    }

    public void moveCell(HSSFCell hSSFCell, short s) {
        HSSFCell[] hSSFCellArr = this.cells;
        if (hSSFCellArr.length > s && hSSFCellArr[s] != null) {
            throw new IllegalArgumentException("Asked to move cell to column " + s + " but there's already a cell there");
        } else if (hSSFCellArr[hSSFCell.getColumnIndex()].equals(hSSFCell)) {
            removeCell(hSSFCell, false);
            hSSFCell.updateCellNum(s);
            addCell(hSSFCell);
        } else {
            throw new IllegalArgumentException("Asked to move a cell, but it didn't belong to our row");
        }
    }

    private void addCell(HSSFCell hSSFCell) {
        int columnIndex = hSSFCell.getColumnIndex();
        HSSFCell[] hSSFCellArr = this.cells;
        if (columnIndex >= hSSFCellArr.length) {
            int length = ((hSSFCellArr.length * 3) / 2) + 1;
            if (length < columnIndex + 1) {
                length = columnIndex + 5;
            }
            HSSFCell[] hSSFCellArr2 = new HSSFCell[length];
            this.cells = hSSFCellArr2;
            System.arraycopy(hSSFCellArr, 0, hSSFCellArr2, 0, hSSFCellArr.length);
        }
        this.cells[columnIndex] = hSSFCell;
        if (this.row.isEmpty() || columnIndex < this.row.getFirstCol()) {
            this.row.setFirstCol((short) columnIndex);
        }
        if (this.row.isEmpty() || columnIndex >= this.row.getLastCol()) {
            this.row.setLastCol((short) (columnIndex + 1));
        }
    }

    private HSSFCell retrieveCell(int i) {
        if (i < 0) {
            return null;
        }
        HSSFCell[] hSSFCellArr = this.cells;
        if (i >= hSSFCellArr.length) {
            return null;
        }
        return hSSFCellArr[i];
    }

    public HSSFCell getCell(short s) {
        return getCell((int) s & 65535);
    }

    public HSSFCell getCell(int i) {
        return getCell(i, this.book.getMissingCellPolicy());
    }

    public HSSFCell getCell(int i, IRow.MissingCellPolicy missingCellPolicy) {
        HSSFCell retrieveCell = retrieveCell(i);
        if (missingCellPolicy == RETURN_NULL_AND_BLANK) {
            return retrieveCell;
        }
        if (missingCellPolicy == RETURN_BLANK_AS_NULL) {
            if (retrieveCell != null && retrieveCell.getCellType() == 3) {
                return null;
            }
            return retrieveCell;
        } else if (missingCellPolicy == CREATE_NULL_AS_BLANK) {
            return retrieveCell == null ? createCell(i, 3) : retrieveCell;
        } else {
            throw new IllegalArgumentException("Illegal policy " + missingCellPolicy + " (" + missingCellPolicy.id + ")");
        }
    }

    public short getFirstCellNum() {
        if (this.row.isEmpty()) {
            return -1;
        }
        return (short) this.row.getFirstCol();
    }

    public short getLastCellNum() {
        if (this.row.isEmpty()) {
            return -1;
        }
        return (short) this.row.getLastCol();
    }

    public int getPhysicalNumberOfCells() {
        int i = 0;
        int i2 = 0;
        while (true) {
            HSSFCell[] hSSFCellArr = this.cells;
            if (i >= hSSFCellArr.length) {
                return i2;
            }
            if (hSSFCellArr[i] != null) {
                i2++;
            }
            i++;
        }
    }

    public void setHeight(short s) {
        if (s == -1) {
            this.row.setHeight(-32513);
            return;
        }
        this.row.setBadFontHeight(true);
        this.row.setHeight(s);
    }

    public void setZeroHeight(boolean z) {
        this.row.setZeroHeight(z);
    }

    public boolean getZeroHeight() {
        return this.row.getZeroHeight();
    }

    public void setHeightInPoints(float f) {
        if (f == -1.0f) {
            this.row.setHeight(-32513);
            return;
        }
        this.row.setBadFontHeight(true);
        this.row.setHeight((short) ((int) (f * 20.0f)));
    }

    public short getHeight() {
        short height = this.row.getHeight();
        return (32768 & height) != 0 ? this.sheet.getSheet().getDefaultRowHeight() : (short) (height & Short.MAX_VALUE);
    }

    public float getHeightInPoints() {
        return ((float) getHeight()) / 20.0f;
    }

    /* access modifiers changed from: protected */
    public RowRecord getRowRecord() {
        return this.row;
    }

    private int calculateNewLastCellPlusOne(int i) {
        int i2 = i - 1;
        HSSFCell retrieveCell = retrieveCell(i2);
        while (retrieveCell == null) {
            if (i2 < 0) {
                return 0;
            }
            i2--;
            retrieveCell = retrieveCell(i2);
        }
        return i2 + 1;
    }

    private int calculateNewFirstCell(int i) {
        int i2 = i + 1;
        HSSFCell retrieveCell = retrieveCell(i2);
        while (retrieveCell == null) {
            if (i2 <= this.cells.length) {
                return 0;
            }
            i2++;
            retrieveCell = retrieveCell(i2);
        }
        return i2;
    }

    public boolean isFormatted() {
        return this.row.getFormatted();
    }

    public HSSFCellStyle getRowStyle() {
        if (!isFormatted()) {
            return null;
        }
        short xFIndex = this.row.getXFIndex();
        return new HSSFCellStyle(xFIndex, this.book.getWorkbook().getExFormatAt(xFIndex), this.book);
    }

    public int getRowStyleIndex() {
        if (!isFormatted()) {
            return 0;
        }
        return this.row.getXFIndex();
    }

    public void setRowStyle(HSSFCellStyle hSSFCellStyle) {
        this.row.setFormatted(true);
        this.row.setXFIndex(hSSFCellStyle.getIndex());
    }

    public void setRowStyle(ICellStyle iCellStyle) {
        setRowStyle((HSSFCellStyle) iCellStyle);
    }

    public Iterator<ICell> cellIterator() {
        return new CellIterator();
    }

    public Iterator iterator() {
        return cellIterator();
    }

    private class CellIterator implements Iterator<ICell> {
        int nextId = -1;
        int thisId = -1;

        public CellIterator() {
            findNext();
        }

        public boolean hasNext() {
            return this.nextId < HSSFRow.this.cells.length;
        }

        public ICell next() {
            if (hasNext()) {
                HSSFCell[] access$000 = HSSFRow.this.cells;
                int i = this.nextId;
                HSSFCell hSSFCell = access$000[i];
                this.thisId = i;
                findNext();
                return hSSFCell;
            }
            throw new NoSuchElementException("At last element");
        }

        public void remove() {
            if (this.thisId != -1) {
                HSSFRow.this.cells[this.thisId] = null;
                return;
            }
            throw new IllegalStateException("remove() called before next()");
        }

        private void findNext() {
            int i = this.nextId;
            do {
                i++;
                if (i >= HSSFRow.this.cells.length || HSSFRow.this.cells[i] != null) {
                    this.nextId = i;
                }
                i++;
                break;
            } while (HSSFRow.this.cells[i] != null);
            this.nextId = i;
        }
    }

    public int compareTo(Object obj) {
        HSSFRow hSSFRow = (HSSFRow) obj;
        if (getRowNum() == hSSFRow.getRowNum()) {
            return 0;
        }
        if (getRowNum() >= hSSFRow.getRowNum() && getRowNum() > hSSFRow.getRowNum()) {
            return 1;
        }
        return -1;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof HSSFRow) && getRowNum() == ((HSSFRow) obj).getRowNum()) {
            return true;
        }
        return false;
    }

    public int getRowPixelHeight() {
        return this.rowPixelHeight;
    }

    public void setRowPixelHeight(int i) {
        this.rowPixelHeight = i;
    }

    public boolean isEmpty() {
        return this.row.isEmpty();
    }
}
