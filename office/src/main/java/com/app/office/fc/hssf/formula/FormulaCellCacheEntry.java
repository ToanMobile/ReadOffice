package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.FormulaUsedBlankCellSet;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class FormulaCellCacheEntry extends CellCacheEntry {
    private CellCacheEntry[] _sensitiveInputCells;
    private FormulaUsedBlankCellSet _usedBlankCellGroup;

    public boolean isInputSensitive() {
        CellCacheEntry[] cellCacheEntryArr = this._sensitiveInputCells;
        if (cellCacheEntryArr != null && cellCacheEntryArr.length > 0) {
            return true;
        }
        FormulaUsedBlankCellSet formulaUsedBlankCellSet = this._usedBlankCellGroup;
        if (formulaUsedBlankCellSet != null && !formulaUsedBlankCellSet.isEmpty()) {
            return true;
        }
        return false;
    }

    public void setSensitiveInputCells(CellCacheEntry[] cellCacheEntryArr) {
        changeConsumingCells(cellCacheEntryArr == null ? CellCacheEntry.EMPTY_ARRAY : cellCacheEntryArr);
        this._sensitiveInputCells = cellCacheEntryArr;
    }

    public void clearFormulaEntry() {
        CellCacheEntry[] cellCacheEntryArr = this._sensitiveInputCells;
        if (cellCacheEntryArr != null) {
            for (int length = cellCacheEntryArr.length - 1; length >= 0; length--) {
                cellCacheEntryArr[length].clearConsumingCell(this);
            }
        }
        this._sensitiveInputCells = null;
        clearValue();
    }

    private void changeConsumingCells(CellCacheEntry[] cellCacheEntryArr) {
        Set set;
        CellCacheEntry[] cellCacheEntryArr2 = this._sensitiveInputCells;
        for (CellCacheEntry addConsumingCell : cellCacheEntryArr) {
            addConsumingCell.addConsumingCell(this);
        }
        if (cellCacheEntryArr2 != null && (r3 = cellCacheEntryArr2.length) >= 1) {
            if (r1 < 1) {
                set = Collections.emptySet();
            } else {
                HashSet hashSet = new HashSet((r1 * 3) / 2);
                for (CellCacheEntry add : cellCacheEntryArr) {
                    hashSet.add(add);
                }
                set = hashSet;
            }
            for (CellCacheEntry cellCacheEntry : cellCacheEntryArr2) {
                if (!set.contains(cellCacheEntry)) {
                    cellCacheEntry.clearConsumingCell(this);
                }
            }
        }
    }

    public void updateFormulaResult(ValueEval valueEval, CellCacheEntry[] cellCacheEntryArr, FormulaUsedBlankCellSet formulaUsedBlankCellSet) {
        updateValue(valueEval);
        setSensitiveInputCells(cellCacheEntryArr);
        this._usedBlankCellGroup = formulaUsedBlankCellSet;
    }

    public void notifyUpdatedBlankCell(FormulaUsedBlankCellSet.BookSheetKey bookSheetKey, int i, int i2, IEvaluationListener iEvaluationListener) {
        FormulaUsedBlankCellSet formulaUsedBlankCellSet = this._usedBlankCellGroup;
        if (formulaUsedBlankCellSet != null && formulaUsedBlankCellSet.containsCell(bookSheetKey, i, i2)) {
            clearFormulaEntry();
            recurseClearCachedFormulaResults(iEvaluationListener);
        }
    }
}
