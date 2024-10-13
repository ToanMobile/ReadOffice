package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.EvaluationCell;
import com.app.office.fc.hssf.formula.EvaluationSheet;
import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.ss.usermodel.Sheet;
import com.app.office.fc.ss.util.CellReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

final class ForkedEvaluationSheet implements EvaluationSheet {
    private final EvaluationSheet _masterSheet;
    private final Map<RowColKey, ForkedEvaluationCell> _sharedCellsByRowCol = new HashMap();

    public ForkedEvaluationSheet(EvaluationSheet evaluationSheet) {
        this._masterSheet = evaluationSheet;
    }

    public EvaluationCell getCell(int i, int i2) {
        ForkedEvaluationCell forkedEvaluationCell = this._sharedCellsByRowCol.get(new RowColKey(i, i2));
        return forkedEvaluationCell == null ? this._masterSheet.getCell(i, i2) : forkedEvaluationCell;
    }

    public ForkedEvaluationCell getOrCreateUpdatableCell(int i, int i2) {
        RowColKey rowColKey = new RowColKey(i, i2);
        ForkedEvaluationCell forkedEvaluationCell = this._sharedCellsByRowCol.get(rowColKey);
        if (forkedEvaluationCell != null) {
            return forkedEvaluationCell;
        }
        EvaluationCell cell = this._masterSheet.getCell(i, i2);
        if (cell != null) {
            ForkedEvaluationCell forkedEvaluationCell2 = new ForkedEvaluationCell(this, cell);
            this._sharedCellsByRowCol.put(rowColKey, forkedEvaluationCell2);
            return forkedEvaluationCell2;
        }
        CellReference cellReference = new CellReference(i, i2);
        throw new UnsupportedOperationException("Underlying cell '" + cellReference.formatAsString() + "' is missing in master sheet.");
    }

    public void copyUpdatedCells(Sheet sheet) {
        RowColKey[] rowColKeyArr = new RowColKey[this._sharedCellsByRowCol.size()];
        this._sharedCellsByRowCol.keySet().toArray(rowColKeyArr);
        Arrays.sort(rowColKeyArr);
    }

    public int getSheetIndex(EvaluationWorkbook evaluationWorkbook) {
        return evaluationWorkbook.getSheetIndex(this._masterSheet);
    }

    private static final class RowColKey implements Comparable<RowColKey> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int _columnIndex;
        private final int _rowIndex;

        static {
            Class<ForkedEvaluationSheet> cls = ForkedEvaluationSheet.class;
        }

        public RowColKey(int i, int i2) {
            this._rowIndex = i;
            this._columnIndex = i2;
        }

        public boolean equals(Object obj) {
            RowColKey rowColKey = (RowColKey) obj;
            return this._rowIndex == rowColKey._rowIndex && this._columnIndex == rowColKey._columnIndex;
        }

        public int hashCode() {
            return this._rowIndex ^ this._columnIndex;
        }

        public int compareTo(RowColKey rowColKey) {
            int i = this._rowIndex - rowColKey._rowIndex;
            if (i != 0) {
                return i;
            }
            return this._columnIndex - rowColKey._columnIndex;
        }

        public int getRowIndex() {
            return this._rowIndex;
        }

        public int getColumnIndex() {
            return this._columnIndex;
        }
    }
}
