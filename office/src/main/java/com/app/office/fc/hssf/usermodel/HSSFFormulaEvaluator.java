package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.formula.CollaboratingWorkbooksEnvironment;
import com.app.office.fc.hssf.formula.IStabilityClassifier;
import com.app.office.fc.hssf.formula.WorkbookEvaluator;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.ss.usermodel.CellValue;
import com.app.office.fc.ss.usermodel.FormulaEvaluator;
import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.RichTextString;
import com.app.office.fc.ss.usermodel.Workbook;
import com.app.office.ss.model.XLSModel.ACell;
import com.app.office.ss.model.XLSModel.ASheet;
import com.app.office.ss.model.XLSModel.AWorkbook;

public class HSSFFormulaEvaluator implements FormulaEvaluator {
    private AWorkbook _book;
    private WorkbookEvaluator _bookEvaluator;
    private HSSFEvaluationCell hssfEvaluationCell;

    public static void evaluateAllFormulaCells(Workbook workbook) {
    }

    private static void evaluateAllFormulaCells(Workbook workbook, FormulaEvaluator formulaEvaluator) {
    }

    public ValueEval evaluateFormulaValueEval(HSSFName hSSFName) {
        return null;
    }

    public void setCurrentRow(HSSFRow hSSFRow) {
    }

    public HSSFFormulaEvaluator(ASheet aSheet, AWorkbook aWorkbook) {
        this(aWorkbook);
        this._book = aWorkbook;
    }

    public HSSFFormulaEvaluator(AWorkbook aWorkbook) {
        this(aWorkbook, (IStabilityClassifier) null);
        this._book = aWorkbook;
    }

    public HSSFFormulaEvaluator(AWorkbook aWorkbook, IStabilityClassifier iStabilityClassifier) {
        this(aWorkbook, iStabilityClassifier, (UDFFinder) null);
    }

    private HSSFFormulaEvaluator(AWorkbook aWorkbook, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        this.hssfEvaluationCell = null;
        this._bookEvaluator = new WorkbookEvaluator(HSSFEvaluationWorkbook.create(aWorkbook), iStabilityClassifier, uDFFinder);
    }

    public static HSSFFormulaEvaluator create(AWorkbook aWorkbook, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        return new HSSFFormulaEvaluator(aWorkbook, iStabilityClassifier, uDFFinder);
    }

    public static void setupEnvironment(String[] strArr, HSSFFormulaEvaluator[] hSSFFormulaEvaluatorArr) {
        int length = hSSFFormulaEvaluatorArr.length;
        WorkbookEvaluator[] workbookEvaluatorArr = new WorkbookEvaluator[length];
        for (int i = 0; i < length; i++) {
            workbookEvaluatorArr[i] = hSSFFormulaEvaluatorArr[i]._bookEvaluator;
        }
        CollaboratingWorkbooksEnvironment.setup(strArr, workbookEvaluatorArr);
    }

    public void clearAllCachedResultValues() {
        this._bookEvaluator.clearAllCachedResultValues();
    }

    public void notifyUpdateCell(ACell aCell) {
        this._bookEvaluator.notifyUpdateCell(new HSSFEvaluationCell(aCell));
    }

    public void notifyUpdateCell(ICell iCell) {
        this._bookEvaluator.notifyUpdateCell(new HSSFEvaluationCell((ACell) iCell));
    }

    public void notifyDeleteCell(ACell aCell) {
        this._bookEvaluator.notifyDeleteCell(new HSSFEvaluationCell(aCell));
    }

    public void notifyDeleteCell(ICell iCell) {
        this._bookEvaluator.notifyDeleteCell(new HSSFEvaluationCell((ACell) iCell));
    }

    public void notifySetFormula(ICell iCell) {
        this._bookEvaluator.notifyUpdateCell(new HSSFEvaluationCell((ACell) iCell));
    }

    public CellValue evaluate(ICell iCell) {
        if (iCell == null) {
            return null;
        }
        int cellType = iCell.getCellType();
        if (cellType == 0) {
            return new CellValue(iCell.getNumericCellValue());
        }
        if (cellType == 1) {
            return new CellValue(iCell.getRichStringCellValue().getString());
        }
        if (cellType == 2) {
            return evaluateFormulaCellValue(iCell);
        }
        if (cellType == 3) {
            return null;
        }
        if (cellType == 4) {
            return CellValue.valueOf(iCell.getBooleanCellValue());
        }
        if (cellType == 5) {
            return CellValue.getError(iCell.getErrorCellValue());
        }
        throw new IllegalStateException("Bad cell type (" + iCell.getCellType() + ")");
    }

    public int evaluateFormulaCell(ICell iCell) {
        if (iCell == null || iCell.getCellType() != 2) {
            return -1;
        }
        CellValue evaluateFormulaCellValue = evaluateFormulaCellValue(iCell);
        setCellValue(iCell, evaluateFormulaCellValue);
        return evaluateFormulaCellValue.getCellType();
    }

    public HSSFCell evaluateInCell(ICell iCell) {
        if (iCell == null) {
            return null;
        }
        HSSFCell hSSFCell = (HSSFCell) iCell;
        if (iCell.getCellType() == 2) {
            CellValue evaluateFormulaCellValue = evaluateFormulaCellValue(iCell);
            setCellValue(iCell, evaluateFormulaCellValue);
            setCellType(iCell, evaluateFormulaCellValue);
        }
        return hSSFCell;
    }

    private static void setCellType(ICell iCell, CellValue cellValue) {
        int cellType = cellValue.getCellType();
        if (cellType == 0 || cellType == 1 || cellType == 4 || cellType == 5) {
            iCell.setCellType(cellType);
            return;
        }
        throw new IllegalStateException("Unexpected cell value type (" + cellType + ")");
    }

    private static void setCellValue(ICell iCell, CellValue cellValue) {
        int cellType = cellValue.getCellType();
        if (cellType == 0) {
            iCell.setCellValue(cellValue.getNumberValue());
        } else if (cellType == 1) {
            iCell.setCellValue((RichTextString) new HSSFRichTextString(cellValue.getStringValue()));
        } else if (cellType == 4) {
            iCell.setCellValue(cellValue.getBooleanValue());
        } else if (cellType == 5) {
            iCell.setCellErrorValue(cellValue.getErrorValue());
        } else {
            throw new IllegalStateException("Unexpected cell value type (" + cellType + ")");
        }
    }

    public static void evaluateAllFormulaCells(AWorkbook aWorkbook) {
        evaluateAllFormulaCells(aWorkbook, new HSSFFormulaEvaluator(aWorkbook));
    }

    public void evaluateAll() {
        evaluateAllFormulaCells(this._book, this);
    }

    public CellValue evaluateFormulaCellValue(ICell iCell) {
        HSSFEvaluationCell hSSFEvaluationCell = this.hssfEvaluationCell;
        if (hSSFEvaluationCell != null) {
            hSSFEvaluationCell.setHSSFCell((ACell) iCell);
        } else {
            this.hssfEvaluationCell = new HSSFEvaluationCell((ACell) iCell);
        }
        this._bookEvaluator.clearAllCachedResultValues();
        ValueEval evaluate = this._bookEvaluator.evaluate(this.hssfEvaluationCell);
        if (evaluate instanceof NumberEval) {
            return new CellValue(((NumberEval) evaluate).getNumberValue());
        }
        if (evaluate instanceof BoolEval) {
            return CellValue.valueOf(((BoolEval) evaluate).getBooleanValue());
        }
        if (evaluate instanceof StringEval) {
            return new CellValue(((StringEval) evaluate).getStringValue());
        }
        if (evaluate instanceof ErrorEval) {
            return CellValue.getError(((ErrorEval) evaluate).getErrorCode());
        }
        if (evaluate == null) {
            return null;
        }
        throw new RuntimeException("Unexpected eval class (" + evaluate.getClass().getName() + ")");
    }

    public ValueEval evaluateFormulaValueEval(ACell aCell) {
        HSSFEvaluationCell hSSFEvaluationCell = this.hssfEvaluationCell;
        if (hSSFEvaluationCell != null) {
            hSSFEvaluationCell.setHSSFCell(aCell);
        } else {
            this.hssfEvaluationCell = new HSSFEvaluationCell(aCell);
        }
        this._bookEvaluator.clearAllCachedResultValues();
        return this._bookEvaluator.evaluate(this.hssfEvaluationCell);
    }
}
