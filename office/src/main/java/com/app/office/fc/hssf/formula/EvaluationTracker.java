package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class EvaluationTracker {
    private final EvaluationCache _cache;
    private final Set<FormulaCellCacheEntry> _currentlyEvaluatingCells = new HashSet();
    private final List<CellEvaluationFrame> _evaluationFrames = new ArrayList();

    public EvaluationTracker(EvaluationCache evaluationCache) {
        this._cache = evaluationCache;
    }

    public boolean startEvaluate(FormulaCellCacheEntry formulaCellCacheEntry) {
        if (formulaCellCacheEntry == null) {
            throw new IllegalArgumentException("cellLoc must not be null");
        } else if (this._currentlyEvaluatingCells.contains(formulaCellCacheEntry)) {
            return false;
        } else {
            this._currentlyEvaluatingCells.add(formulaCellCacheEntry);
            this._evaluationFrames.add(new CellEvaluationFrame(formulaCellCacheEntry));
            return true;
        }
    }

    public void updateCacheResult(ValueEval valueEval) {
        int size = this._evaluationFrames.size();
        if (size >= 1) {
            CellEvaluationFrame cellEvaluationFrame = this._evaluationFrames.get(size - 1);
            if (valueEval != ErrorEval.CIRCULAR_REF_ERROR || size <= 1) {
                cellEvaluationFrame.updateFormulaResult(valueEval);
                return;
            }
            return;
        }
        throw new IllegalStateException("Call to endEvaluate without matching call to startEvaluate");
    }

    public void endEvaluate(CellCacheEntry cellCacheEntry) {
        int size = this._evaluationFrames.size();
        if (size >= 1) {
            int i = size - 1;
            if (cellCacheEntry == this._evaluationFrames.get(i).getCCE()) {
                this._evaluationFrames.remove(i);
                this._currentlyEvaluatingCells.remove(cellCacheEntry);
                return;
            }
            throw new IllegalStateException("Wrong cell specified. ");
        }
        throw new IllegalStateException("Call to endEvaluate without matching call to startEvaluate");
    }

    public void acceptFormulaDependency(CellCacheEntry cellCacheEntry) {
        int size = this._evaluationFrames.size() - 1;
        if (size >= 0) {
            this._evaluationFrames.get(size).addSensitiveInputCell(cellCacheEntry);
        }
    }

    public void acceptPlainValueDependency(int i, int i2, int i3, int i4, ValueEval valueEval) {
        int size = this._evaluationFrames.size() - 1;
        if (size >= 0) {
            CellEvaluationFrame cellEvaluationFrame = this._evaluationFrames.get(size);
            if (valueEval == BlankEval.instance) {
                cellEvaluationFrame.addUsedBlankCell(i, i2, i3, i4);
            } else {
                cellEvaluationFrame.addSensitiveInputCell(this._cache.getPlainValueEntry(i, i2, i3, i4, valueEval));
            }
        }
    }
}
