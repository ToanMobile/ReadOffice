package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.EvaluationCell;
import com.app.office.fc.hssf.formula.EvaluationSheet;
import com.app.office.fc.ss.usermodel.ICell;

final class ForkedEvaluationCell implements EvaluationCell {
    private boolean _booleanValue;
    private int _cellType;
    private int _errorValue;
    private final EvaluationCell _masterCell;
    private double _numberValue;
    private final EvaluationSheet _sheet;
    private String _stringValue;

    public ForkedEvaluationCell(ForkedEvaluationSheet forkedEvaluationSheet, EvaluationCell evaluationCell) {
        this._sheet = forkedEvaluationSheet;
        this._masterCell = evaluationCell;
        setValue(BlankEval.instance);
    }

    public Object getIdentityKey() {
        return this._masterCell.getIdentityKey();
    }

    public void setValue(ValueEval valueEval) {
        Class<?> cls = valueEval.getClass();
        if (cls == NumberEval.class) {
            this._cellType = 0;
            this._numberValue = ((NumberEval) valueEval).getNumberValue();
        } else if (cls == StringEval.class) {
            this._cellType = 1;
            this._stringValue = ((StringEval) valueEval).getStringValue();
        } else if (cls == BoolEval.class) {
            this._cellType = 4;
            this._booleanValue = ((BoolEval) valueEval).getBooleanValue();
        } else if (cls == ErrorEval.class) {
            this._cellType = 5;
            this._errorValue = ((ErrorEval) valueEval).getErrorCode();
        } else if (cls == BlankEval.class) {
            this._cellType = 3;
        } else {
            throw new IllegalArgumentException("Unexpected value class (" + cls.getName() + ")");
        }
    }

    public void copyValue(ICell iCell) {
        int i = this._cellType;
        if (i == 0) {
            iCell.setCellValue(this._numberValue);
        } else if (i == 1) {
            iCell.setCellValue(this._stringValue);
        } else if (i == 3) {
            iCell.setCellType(3);
        } else if (i == 4) {
            iCell.setCellValue(this._booleanValue);
        } else if (i == 5) {
            iCell.setCellErrorValue((byte) this._errorValue);
        } else {
            throw new IllegalStateException("Unexpected data type (" + this._cellType + ")");
        }
    }

    private void checkCellType(int i) {
        if (this._cellType != i) {
            throw new RuntimeException("Wrong data type (" + this._cellType + ")");
        }
    }

    public int getCellType() {
        return this._cellType;
    }

    public boolean getBooleanCellValue() {
        checkCellType(4);
        return this._booleanValue;
    }

    public int getErrorCellValue() {
        checkCellType(5);
        return this._errorValue;
    }

    public double getNumericCellValue() {
        checkCellType(0);
        return this._numberValue;
    }

    public String getStringCellValue() {
        checkCellType(1);
        return this._stringValue;
    }

    public EvaluationSheet getSheet() {
        return this._sheet;
    }

    public int getRowIndex() {
        return this._masterCell.getRowIndex();
    }

    public int getColumnIndex() {
        return this._masterCell.getColumnIndex();
    }
}
