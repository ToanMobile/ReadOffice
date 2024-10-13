package com.app.office.fc.hssf.formula.eval;

public interface RefEval extends ValueEval {
    int getColumn();

    ValueEval getInnerValueEval();

    int getRow();

    AreaEval offset(int i, int i2, int i3, int i4);
}
