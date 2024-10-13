package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.RefEvalBase;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.ptg.AreaI;
import com.app.office.fc.ss.util.CellReference;

final class LazyRefEval extends RefEvalBase {
    private final SheetRefEvaluator _evaluator;

    public LazyRefEval(int i, int i2, SheetRefEvaluator sheetRefEvaluator) {
        super(i, i2);
        if (sheetRefEvaluator != null) {
            this._evaluator = sheetRefEvaluator;
            return;
        }
        throw new IllegalArgumentException("sre must not be null");
    }

    public ValueEval getInnerValueEval() {
        return this._evaluator.getEvalForCell(getRow(), getColumn());
    }

    public AreaEval offset(int i, int i2, int i3, int i4) {
        return new LazyAreaEval(new AreaI.OffsetArea(getRow(), getColumn(), i, i2, i3, i4), this._evaluator);
    }

    public String toString() {
        CellReference cellReference = new CellReference(getRow(), getColumn());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append("[");
        stringBuffer.append(this._evaluator.getSheetName());
        stringBuffer.append('!');
        stringBuffer.append(cellReference.formatAsString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
