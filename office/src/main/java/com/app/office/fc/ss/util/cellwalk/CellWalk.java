package com.app.office.fc.ss.util.cellwalk;

import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.Sheet;
import com.app.office.fc.ss.util.DataMarker;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;

public class CellWalk {
    private HSSFCellRangeAddress range;
    private Sheet sheet;
    private boolean traverseEmptyCells;

    public CellWalk(DataMarker dataMarker) {
        this(dataMarker.getSheet(), dataMarker.getRange());
    }

    public CellWalk(Sheet sheet2, HSSFCellRangeAddress hSSFCellRangeAddress) {
        this.sheet = sheet2;
        this.range = hSSFCellRangeAddress;
        this.traverseEmptyCells = false;
    }

    public boolean isTraverseEmptyCells() {
        return this.traverseEmptyCells;
    }

    public void setTraverseEmptyCells(boolean z) {
        this.traverseEmptyCells = z;
    }

    public void traverse(CellHandler cellHandler) {
        this.range.getFirstRow();
        this.range.getLastRow();
        this.range.getFirstColumn();
        this.range.getLastColumn();
        new SimpleCellWalkContext();
    }

    private boolean isEmpty(ICell iCell) {
        return iCell.getCellType() == 3;
    }

    private class SimpleCellWalkContext implements CellWalkContext {
        public int colNumber;
        public long ordinalNumber;
        public int rowNumber;

        private SimpleCellWalkContext() {
            this.ordinalNumber = 0;
            this.rowNumber = 0;
            this.colNumber = 0;
        }

        public long getOrdinalNumber() {
            return this.ordinalNumber;
        }

        public int getRowNumber() {
            return this.rowNumber;
        }

        public int getColumnNumber() {
            return this.colNumber;
        }
    }
}
