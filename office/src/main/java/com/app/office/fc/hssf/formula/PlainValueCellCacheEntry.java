package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.eval.ValueEval;

final class PlainValueCellCacheEntry extends CellCacheEntry {
    public PlainValueCellCacheEntry(ValueEval valueEval) {
        updateValue(valueEval);
    }
}
