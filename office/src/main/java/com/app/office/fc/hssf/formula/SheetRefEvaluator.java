package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.ptg.FuncVarPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;

final class SheetRefEvaluator {
    private final WorkbookEvaluator _bookEvaluator;
    private EvaluationSheet _sheet;
    private final int _sheetIndex;
    private final EvaluationTracker _tracker;

    public SheetRefEvaluator(WorkbookEvaluator workbookEvaluator, EvaluationTracker evaluationTracker, int i) {
        if (i >= 0) {
            this._bookEvaluator = workbookEvaluator;
            this._tracker = evaluationTracker;
            this._sheetIndex = i;
            return;
        }
        throw new IllegalArgumentException("Invalid sheetIndex: " + i + ".");
    }

    public String getSheetName() {
        return this._bookEvaluator.getSheetName(this._sheetIndex);
    }

    public ValueEval getEvalForCell(int i, int i2) {
        return this._bookEvaluator.evaluateReference(getSheet(), this._sheetIndex, i, i2, this._tracker);
    }

    private EvaluationSheet getSheet() {
        if (this._sheet == null) {
            this._sheet = this._bookEvaluator.getSheet(this._sheetIndex);
        }
        return this._sheet;
    }

    public boolean isSubTotal(int i, int i2) {
        EvaluationCell cell = getSheet().getCell(i, i2);
        if (cell == null || cell.getCellType() != 2) {
            return false;
        }
        for (Ptg ptg : this._bookEvaluator.getWorkbook().getFormulaTokens(cell)) {
            if ((ptg instanceof FuncVarPtg) && "SUBTOTAL".equals(((FuncVarPtg) ptg).getName())) {
                return true;
            }
        }
        return false;
    }
}
