package com.app.office.fc.ss.usermodel;

public interface FormulaEvaluator {
    void clearAllCachedResultValues();

    CellValue evaluate(ICell iCell);

    void evaluateAll();

    int evaluateFormulaCell(ICell iCell);

    ICell evaluateInCell(ICell iCell);

    void notifyDeleteCell(ICell iCell);

    void notifySetFormula(ICell iCell);

    void notifyUpdateCell(ICell iCell);
}
