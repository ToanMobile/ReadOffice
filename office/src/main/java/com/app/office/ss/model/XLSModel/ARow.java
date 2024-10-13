package com.app.office.ss.model.XLSModel;

import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.RowRecord;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import java.util.Iterator;

public class ARow extends Row {
    public static final int INITIAL_CAPACITY = 5;

    public ARow(Workbook workbook, Sheet sheet, RowRecord rowRecord) {
        super((rowRecord.getLastCol() - rowRecord.getFirstCol()) + 5);
        int i;
        setSheet(sheet);
        rowRecord.setEmpty();
        this.rowNumber = rowRecord.getRowNumber();
        this.firstCol = rowRecord.getFirstCol();
        this.lastCol = Math.max(this.lastCol, rowRecord.getLastCol());
        this.styleIndex = rowRecord.getXFIndex();
        int i2 = 0;
        while (true) {
            i = 65535 >> i2;
            if ((this.styleIndex & i) <= workbook.getNumStyles()) {
                break;
            }
            i2++;
        }
        this.styleIndex &= i;
        setZeroHeight(rowRecord.getZeroHeight());
        short height = rowRecord.getHeight();
        setRowPixelHeight((float) ((int) (((float) (((32768 & height) != 0 ? 255 : (short) (height & Short.MAX_VALUE)) / 20)) * 1.3333334f)));
    }

    private boolean isValidateCell(CellValueRecordInterface cellValueRecordInterface) {
        if (ACell.determineType(cellValueRecordInterface) != 3) {
            return true;
        }
        Workbook workbook = this.sheet.getWorkbook();
        if (Workbook.isValidateStyle(workbook.getCellStyle(cellValueRecordInterface.getXFIndex())) || Workbook.isValidateStyle(workbook.getCellStyle(getRowStyle())) || Workbook.isValidateStyle(workbook.getCellStyle(this.sheet.getColumnStyle(cellValueRecordInterface.getColumn())))) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public ACell createCellFromRecord(CellValueRecordInterface cellValueRecordInterface) {
        Cell cell = (Cell) this.cells.get(Short.valueOf(cellValueRecordInterface.getColumn()));
        if (cell != null) {
            return (ACell) cell;
        }
        if (!isValidateCell(cellValueRecordInterface)) {
            return null;
        }
        ACell aCell = new ACell(this.sheet, cellValueRecordInterface);
        short column = cellValueRecordInterface.getColumn();
        if (column < this.firstCol) {
            this.firstCol = column;
        } else if (column > this.lastCol) {
            this.lastCol = column;
        }
        addCell(aCell);
        return aCell;
    }

    public Iterator<Cell> cellIterator() {
        return this.cells.values().iterator();
    }
}
