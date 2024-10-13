package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.ptg.NamePtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.udf.UDFFinder;

public interface EvaluationWorkbook {
    int convertFromExternSheetIndex(int i);

    ExternalName getExternalName(int i, int i2);

    ExternalSheet getExternalSheet(int i);

    Ptg[] getFormulaTokens(EvaluationCell evaluationCell);

    EvaluationName getName(NamePtg namePtg);

    EvaluationName getName(String str, int i);

    EvaluationSheet getSheet(int i);

    int getSheetIndex(EvaluationSheet evaluationSheet);

    int getSheetIndex(String str);

    String getSheetName(int i);

    UDFFinder getUDFFinder();

    String resolveNameXText(NameXPtg nameXPtg);

    public static class ExternalSheet {
        private final String _sheetName;
        private final String _workbookName;

        public ExternalSheet(String str, String str2) {
            this._workbookName = str;
            this._sheetName = str2;
        }

        public String getWorkbookName() {
            return this._workbookName;
        }

        public String getSheetName() {
            return this._sheetName;
        }
    }

    public static class ExternalName {
        private final int _ix;
        private final String _nameName;
        private final int _nameNumber;

        public ExternalName(String str, int i, int i2) {
            this._nameName = str;
            this._nameNumber = i;
            this._ix = i2;
        }

        public String getName() {
            return this._nameName;
        }

        public int getNumber() {
            return this._nameNumber;
        }

        public int getIx() {
            return this._ix;
        }
    }
}
