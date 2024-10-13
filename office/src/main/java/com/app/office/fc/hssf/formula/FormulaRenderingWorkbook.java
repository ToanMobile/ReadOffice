package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.hssf.formula.ptg.NamePtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;

public interface FormulaRenderingWorkbook {
    EvaluationWorkbook.ExternalSheet getExternalSheet(int i);

    String getNameText(NamePtg namePtg);

    String getSheetNameByExternSheet(int i);

    String resolveNameXText(NameXPtg nameXPtg);
}
