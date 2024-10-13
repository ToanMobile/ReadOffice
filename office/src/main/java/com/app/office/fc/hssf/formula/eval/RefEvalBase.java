package com.app.office.fc.hssf.formula.eval;

public abstract class RefEvalBase implements RefEval {
    private final int _columnIndex;
    private final int _rowIndex;

    protected RefEvalBase(int i, int i2) {
        this._rowIndex = i;
        this._columnIndex = i2;
    }

    public final int getRow() {
        return this._rowIndex;
    }

    public final int getColumn() {
        return this._columnIndex;
    }
}
