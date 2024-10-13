package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.CollaboratingWorkbooksEnvironment;
import com.app.office.fc.hssf.formula.EvaluationCell;
import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.hssf.formula.IStabilityClassifier;
import com.app.office.fc.hssf.formula.WorkbookEvaluator;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.hssf.usermodel.HSSFEvaluationWorkbook;
import com.app.office.fc.ss.usermodel.Workbook;
import com.app.office.ss.model.XLSModel.AWorkbook;

public final class ForkedEvaluator {
    private WorkbookEvaluator _evaluator;
    private ForkedEvaluationWorkbook _sewb;

    private ForkedEvaluator(EvaluationWorkbook evaluationWorkbook, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        ForkedEvaluationWorkbook forkedEvaluationWorkbook = new ForkedEvaluationWorkbook(evaluationWorkbook);
        this._sewb = forkedEvaluationWorkbook;
        this._evaluator = new WorkbookEvaluator(forkedEvaluationWorkbook, iStabilityClassifier, uDFFinder);
    }

    private static EvaluationWorkbook createEvaluationWorkbook(Workbook workbook) {
        if (workbook instanceof AWorkbook) {
            return HSSFEvaluationWorkbook.create((AWorkbook) workbook);
        }
        throw new IllegalArgumentException("Unexpected workbook type (" + workbook.getClass().getName() + ")");
    }

    public static ForkedEvaluator create(Workbook workbook, IStabilityClassifier iStabilityClassifier) {
        return create(workbook, iStabilityClassifier, (UDFFinder) null);
    }

    public static ForkedEvaluator create(Workbook workbook, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        return new ForkedEvaluator(createEvaluationWorkbook(workbook), iStabilityClassifier, uDFFinder);
    }

    public void updateCell(String str, int i, int i2, ValueEval valueEval) {
        ForkedEvaluationCell orCreateUpdatableCell = this._sewb.getOrCreateUpdatableCell(str, i, i2);
        orCreateUpdatableCell.setValue(valueEval);
        this._evaluator.notifyUpdateCell(orCreateUpdatableCell);
    }

    public void copyUpdatedCells(Workbook workbook) {
        this._sewb.copyUpdatedCells(workbook);
    }

    public ValueEval evaluate(String str, int i, int i2) {
        EvaluationCell evaluationCell = this._sewb.getEvaluationCell(str, i, i2);
        int cellType = evaluationCell.getCellType();
        if (cellType == 0) {
            return new NumberEval(evaluationCell.getNumericCellValue());
        }
        if (cellType == 1) {
            return new StringEval(evaluationCell.getStringCellValue());
        }
        if (cellType == 2) {
            return this._evaluator.evaluate(evaluationCell);
        }
        if (cellType == 3) {
            return null;
        }
        if (cellType == 4) {
            return BoolEval.valueOf(evaluationCell.getBooleanCellValue());
        }
        if (cellType == 5) {
            return ErrorEval.valueOf(evaluationCell.getErrorCellValue());
        }
        throw new IllegalStateException("Bad cell type (" + evaluationCell.getCellType() + ")");
    }

    public static void setupEnvironment(String[] strArr, ForkedEvaluator[] forkedEvaluatorArr) {
        int length = forkedEvaluatorArr.length;
        WorkbookEvaluator[] workbookEvaluatorArr = new WorkbookEvaluator[length];
        for (int i = 0; i < length; i++) {
            workbookEvaluatorArr[i] = forkedEvaluatorArr[i]._evaluator;
        }
        CollaboratingWorkbooksEnvironment.setup(strArr, workbookEvaluatorArr);
    }
}
