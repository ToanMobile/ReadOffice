package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.IEvaluationListener;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

abstract class CellCacheEntry implements IEvaluationListener.ICacheEntry {
    public static final CellCacheEntry[] EMPTY_ARRAY = new CellCacheEntry[0];
    private final FormulaCellCacheEntrySet _consumingCells = new FormulaCellCacheEntrySet();
    private ValueEval _value;

    protected CellCacheEntry() {
    }

    /* access modifiers changed from: protected */
    public final void clearValue() {
        this._value = null;
    }

    public final boolean updateValue(ValueEval valueEval) {
        if (valueEval != null) {
            boolean z = !areValuesEqual(this._value, valueEval);
            this._value = valueEval;
            return z;
        }
        throw new IllegalArgumentException("Did not expect to update to null");
    }

    public final ValueEval getValue() {
        return this._value;
    }

    private static boolean areValuesEqual(ValueEval valueEval, ValueEval valueEval2) {
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

    public final void addConsumingCell(FormulaCellCacheEntry formulaCellCacheEntry) {
        this._consumingCells.add(formulaCellCacheEntry);
    }

    public final FormulaCellCacheEntry[] getConsumingCells() {
        return this._consumingCells.toArray();
    }

    public final void clearConsumingCell(FormulaCellCacheEntry formulaCellCacheEntry) {
        if (!this._consumingCells.remove(formulaCellCacheEntry)) {
            throw new IllegalStateException("Specified formula cell is not consumed by this cell");
        }
    }

    public final void recurseClearCachedFormulaResults(IEvaluationListener iEvaluationListener) {
        if (iEvaluationListener == null) {
            recurseClearCachedFormulaResults();
            return;
        }
        iEvaluationListener.onClearCachedValue(this);
        recurseClearCachedFormulaResults(iEvaluationListener, 1);
    }

    /* access modifiers changed from: protected */
    public final void recurseClearCachedFormulaResults() {
        FormulaCellCacheEntry[] consumingCells = getConsumingCells();
        for (FormulaCellCacheEntry formulaCellCacheEntry : consumingCells) {
            formulaCellCacheEntry.clearFormulaEntry();
            formulaCellCacheEntry.recurseClearCachedFormulaResults();
        }
    }

    /* access modifiers changed from: protected */
    public final void recurseClearCachedFormulaResults(IEvaluationListener iEvaluationListener, int i) {
        FormulaCellCacheEntry[] consumingCells = getConsumingCells();
        iEvaluationListener.sortDependentCachedValues(consumingCells);
        for (FormulaCellCacheEntry formulaCellCacheEntry : consumingCells) {
            iEvaluationListener.onClearDependentCachedValue(formulaCellCacheEntry, i);
            formulaCellCacheEntry.clearFormulaEntry();
            formulaCellCacheEntry.recurseClearCachedFormulaResults(iEvaluationListener, i + 1);
        }
    }
}
