package com.app.office.fc.hssf.formula.eval;

public final class MissingArgEval implements ValueEval {
    public static final MissingArgEval instance = new MissingArgEval();

    private MissingArgEval() {
    }
}
