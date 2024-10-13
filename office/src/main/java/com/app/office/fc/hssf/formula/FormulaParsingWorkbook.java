package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.ss.SpreadsheetVersion;

public interface FormulaParsingWorkbook {
    int getExternalSheetIndex(String str);

    int getExternalSheetIndex(String str, String str2);

    EvaluationName getName(String str, int i);

    NameXPtg getNameXPtg(String str);

    SpreadsheetVersion getSpreadsheetVersion();
}
