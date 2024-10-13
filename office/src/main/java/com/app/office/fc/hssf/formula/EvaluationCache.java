package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.FormulaCellCache;
import com.app.office.fc.hssf.formula.FormulaUsedBlankCellSet;
import com.app.office.fc.hssf.formula.PlainCellCache;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

final class EvaluationCache {
    final IEvaluationListener _evaluationListener;
    private final FormulaCellCache _formulaCellCache = new FormulaCellCache();
    private final PlainCellCache _plainCellCache = new PlainCellCache();

    EvaluationCache(IEvaluationListener iEvaluationListener) {
        this._evaluationListener = iEvaluationListener;
    }

    public void notifyUpdateCell(int i, int i2, EvaluationCell evaluationCell) {
        FormulaCellCacheEntry formulaCellCacheEntry = this._formulaCellCache.get(evaluationCell);
        int rowIndex = evaluationCell.getRowIndex();
        int columnIndex = evaluationCell.getColumnIndex();
        PlainCellCache.Loc loc = new PlainCellCache.Loc(i, i2, rowIndex, columnIndex);
        PlainValueCellCacheEntry plainValueCellCacheEntry = this._plainCellCache.get(loc);
        if (evaluationCell.getCellType() == 2) {
            if (formulaCellCacheEntry == null) {
                FormulaCellCacheEntry formulaCellCacheEntry2 = new FormulaCellCacheEntry();
                if (plainValueCellCacheEntry == null) {
                    IEvaluationListener iEvaluationListener = this._evaluationListener;
                    if (iEvaluationListener != null) {
                        iEvaluationListener.onChangeFromBlankValue(i2, rowIndex, columnIndex, evaluationCell, formulaCellCacheEntry2);
                    }
                    updateAnyBlankReferencingFormulas(i, i2, rowIndex, columnIndex);
                }
                this._formulaCellCache.put(evaluationCell, formulaCellCacheEntry2);
            } else {
                formulaCellCacheEntry.recurseClearCachedFormulaResults(this._evaluationListener);
                formulaCellCacheEntry.clearFormulaEntry();
            }
            if (plainValueCellCacheEntry != null) {
                plainValueCellCacheEntry.recurseClearCachedFormulaResults(this._evaluationListener);
                this._plainCellCache.remove(loc);
                return;
            }
            return;
        }
        ValueEval valueFromNonFormulaCell = WorkbookEvaluator.getValueFromNonFormulaCell(evaluationCell);
        if (plainValueCellCacheEntry != null) {
            if (plainValueCellCacheEntry.updateValue(valueFromNonFormulaCell)) {
                plainValueCellCacheEntry.recurseClearCachedFormulaResults(this._evaluationListener);
            }
            if (valueFromNonFormulaCell == BlankEval.instance) {
                this._plainCellCache.remove(loc);
            }
        } else if (valueFromNonFormulaCell != BlankEval.instance) {
            PlainValueCellCacheEntry plainValueCellCacheEntry2 = new PlainValueCellCacheEntry(valueFromNonFormulaCell);
            if (formulaCellCacheEntry == null) {
                IEvaluationListener iEvaluationListener2 = this._evaluationListener;
                if (iEvaluationListener2 != null) {
                    iEvaluationListener2.onChangeFromBlankValue(i2, rowIndex, columnIndex, evaluationCell, plainValueCellCacheEntry2);
                }
                updateAnyBlankReferencingFormulas(i, i2, rowIndex, columnIndex);
            }
            this._plainCellCache.put(loc, plainValueCellCacheEntry2);
        }
        if (formulaCellCacheEntry != null) {
            this._formulaCellCache.remove(evaluationCell);
            formulaCellCacheEntry.setSensitiveInputCells((CellCacheEntry[]) null);
            formulaCellCacheEntry.recurseClearCachedFormulaResults(this._evaluationListener);
        }
    }

    private void updateAnyBlankReferencingFormulas(int i, int i2, final int i3, final int i4) {
        final FormulaUsedBlankCellSet.BookSheetKey bookSheetKey = new FormulaUsedBlankCellSet.BookSheetKey(i, i2);
        this._formulaCellCache.applyOperation(new FormulaCellCache.IEntryOperation() {
            public void processEntry(FormulaCellCacheEntry formulaCellCacheEntry) {
                formulaCellCacheEntry.notifyUpdatedBlankCell(bookSheetKey, i3, i4, EvaluationCache.this._evaluationListener);
            }
        });
    }

    public PlainValueCellCacheEntry getPlainValueEntry(int i, int i2, int i3, int i4, ValueEval valueEval) {
        PlainCellCache.Loc loc = new PlainCellCache.Loc(i, i2, i3, i4);
        PlainValueCellCacheEntry plainValueCellCacheEntry = this._plainCellCache.get(loc);
        if (plainValueCellCacheEntry == null) {
            plainValueCellCacheEntry = new PlainValueCellCacheEntry(valueEval);
            this._plainCellCache.put(loc, plainValueCellCacheEntry);
            IEvaluationListener iEvaluationListener = this._evaluationListener;
            if (iEvaluationListener != null) {
                iEvaluationListener.onReadPlainValue(i2, i3, i4, plainValueCellCacheEntry);
            }
        } else if (areValuesEqual(plainValueCellCacheEntry.getValue(), valueEval)) {
            IEvaluationListener iEvaluationListener2 = this._evaluationListener;
            if (iEvaluationListener2 != null) {
                iEvaluationListener2.onCacheHit(i2, i3, i4, valueEval);
            }
        } else {
            throw new IllegalStateException("value changed");
        }
        return plainValueCellCacheEntry;
    }

    private boolean areValuesEqual(ValueEval valueEval, ValueEval valueEval2) {
        Class<?> cls;
        if (valueEval == null || (cls = valueEval.getClass()) != valueEval2.getClass()) {
            return false;
        }
        if (valueEval == BlankEval.instance) {
            if (valueEval2 == valueEval) {
                return true;
            }
            return false;
        } else if (cls == NumberEval.class) {
            if (((NumberEval) valueEval).getNumberValue() == ((NumberEval) valueEval2).getNumberValue()) {
                return true;
            }
            return false;
        } else if (cls == StringEval.class) {
            return ((StringEval) valueEval).getStringValue().equals(((StringEval) valueEval2).getStringValue());
        } else {
            if (cls == BoolEval.class) {
                if (((BoolEval) valueEval).getBooleanValue() == ((BoolEval) valueEval2).getBooleanValue()) {
                    return true;
                }
                return false;
            } else if (cls != ErrorEval.class) {
                throw new IllegalStateException("Unexpected value class (" + cls.getName() + ")");
            } else if (((ErrorEval) valueEval).getErrorCode() == ((ErrorEval) valueEval2).getErrorCode()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public FormulaCellCacheEntry getOrCreateFormulaCellEntry(EvaluationCell evaluationCell) {
        FormulaCellCacheEntry formulaCellCacheEntry = this._formulaCellCache.get(evaluationCell);
        if (formulaCellCacheEntry != null) {
            return formulaCellCacheEntry;
        }
        FormulaCellCacheEntry formulaCellCacheEntry2 = new FormulaCellCacheEntry();
        this._formulaCellCache.put(evaluationCell, formulaCellCacheEntry2);
        return formulaCellCacheEntry2;
    }

    public void clear() {
        IEvaluationListener iEvaluationListener = this._evaluationListener;
        if (iEvaluationListener != null) {
            iEvaluationListener.onClearWholeCache();
        }
        this._plainCellCache.clear();
        this._formulaCellCache.clear();
    }

    public void notifyDeleteCell(int i, int i2, EvaluationCell evaluationCell) {
        if (evaluationCell.getCellType() == 2) {
            FormulaCellCacheEntry remove = this._formulaCellCache.remove(evaluationCell);
            if (remove != null) {
                remove.setSensitiveInputCells((CellCacheEntry[]) null);
                remove.recurseClearCachedFormulaResults(this._evaluationListener);
                return;
            }
            return;
        }
        PlainValueCellCacheEntry plainValueCellCacheEntry = this._plainCellCache.get(new PlainCellCache.Loc(i, i2, evaluationCell.getRowIndex(), evaluationCell.getColumnIndex()));
        if (plainValueCellCacheEntry != null) {
            plainValueCellCacheEntry.recurseClearCachedFormulaResults(this._evaluationListener);
        }
    }
}
