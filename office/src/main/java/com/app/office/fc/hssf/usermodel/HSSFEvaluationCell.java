package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.formula.EvaluationCell;
import com.app.office.fc.hssf.formula.EvaluationSheet;
import com.app.office.ss.model.XLSModel.ACell;
import com.app.office.ss.model.XLSModel.ASheet;

final class HSSFEvaluationCell implements EvaluationCell {
    private ACell _cell;
    private EvaluationSheet _evalSheet;

    public HSSFEvaluationCell(ACell aCell, EvaluationSheet evaluationSheet) {
        this._cell = aCell;
        this._evalSheet = evaluationSheet;
    }

    public HSSFEvaluationCell(ACell aCell) {
        this(aCell, new HSSFEvaluationSheet((ASheet) aCell.getSheet()));
    }

    public Object getIdentityKey() {
        return this._cell;
    }

    public void setHSSFCell(ACell aCell) {
        this._cell = aCell;
        EvaluationSheet evaluationSheet = this._evalSheet;
        if (evaluationSheet != null) {
            ((HSSFEvaluationSheet) evaluationSheet).setASheet((ASheet) aCell.getSheet());
        } else {
            this._evalSheet = new HSSFEvaluationSheet((ASheet) aCell.getSheet());
        }
    }

    public ACell getACell() {
        return this._cell;
    }

    public boolean getBooleanCellValue() {
        return this._cell.getBooleanCellValue();
    }

    public int getCellType() {
        return this._cell.getCellType();
    }

    public int getColumnIndex() {
        return this._cell.getColNumber();
    }

    public int getErrorCellValue() {
        return this._cell.getErrorCellValue();
    }

    public double getNumericCellValue() {
        return this._cell.getNumericCellValue();
    }

    public int getRowIndex() {
        return this._cell.getRowNumber();
    }

    public EvaluationSheet getSheet() {
        return this._evalSheet;
    }

    public String getStringCellValue() {
        return this._cell.getStringCellValue();
    }
}
