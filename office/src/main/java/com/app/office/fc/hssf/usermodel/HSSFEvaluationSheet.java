package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.formula.EvaluationCell;
import com.app.office.fc.hssf.formula.EvaluationSheet;
import com.app.office.ss.model.XLSModel.ACell;
import com.app.office.ss.model.XLSModel.ARow;
import com.app.office.ss.model.XLSModel.ASheet;

class HSSFEvaluationSheet implements EvaluationSheet {
    private ASheet _hs;

    public HSSFEvaluationSheet(ASheet aSheet) {
        this._hs = aSheet;
    }

    public void setASheet(ASheet aSheet) {
        this._hs = aSheet;
    }

    public ASheet getASheet() {
        return this._hs;
    }

    public EvaluationCell getCell(int i, int i2) {
        ACell aCell;
        ARow aRow = (ARow) this._hs.getRow(i);
        if (aRow == null || (aCell = (ACell) aRow.getCell(i2)) == null) {
            return null;
        }
        return new HSSFEvaluationCell(aCell, this);
    }
}
