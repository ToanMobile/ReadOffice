package com.app.office.fc.hssf.formula.eval;

import com.app.office.fc.hssf.formula.EvaluationCell;
import com.app.office.fc.hssf.formula.EvaluationName;
import com.app.office.fc.hssf.formula.EvaluationSheet;
import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.hssf.formula.ptg.NamePtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.ss.usermodel.Workbook;
import java.util.HashMap;
import java.util.Map;

final class ForkedEvaluationWorkbook implements EvaluationWorkbook {
    private final EvaluationWorkbook _masterBook;
    private final Map<String, ForkedEvaluationSheet> _sharedSheetsByName = new HashMap();

    public void copyUpdatedCells(Workbook workbook) {
    }

    public ForkedEvaluationWorkbook(EvaluationWorkbook evaluationWorkbook) {
        this._masterBook = evaluationWorkbook;
    }

    public ForkedEvaluationCell getOrCreateUpdatableCell(String str, int i, int i2) {
        return getSharedSheet(str).getOrCreateUpdatableCell(i, i2);
    }

    public EvaluationCell getEvaluationCell(String str, int i, int i2) {
        return getSharedSheet(str).getCell(i, i2);
    }

    private ForkedEvaluationSheet getSharedSheet(String str) {
        ForkedEvaluationSheet forkedEvaluationSheet = this._sharedSheetsByName.get(str);
        if (forkedEvaluationSheet != null) {
            return forkedEvaluationSheet;
        }
        EvaluationWorkbook evaluationWorkbook = this._masterBook;
        ForkedEvaluationSheet forkedEvaluationSheet2 = new ForkedEvaluationSheet(evaluationWorkbook.getSheet(evaluationWorkbook.getSheetIndex(str)));
        this._sharedSheetsByName.put(str, forkedEvaluationSheet2);
        return forkedEvaluationSheet2;
    }

    public int convertFromExternSheetIndex(int i) {
        return this._masterBook.convertFromExternSheetIndex(i);
    }

    public EvaluationWorkbook.ExternalSheet getExternalSheet(int i) {
        return this._masterBook.getExternalSheet(i);
    }

    public Ptg[] getFormulaTokens(EvaluationCell evaluationCell) {
        if (!(evaluationCell instanceof ForkedEvaluationCell)) {
            return this._masterBook.getFormulaTokens(evaluationCell);
        }
        throw new RuntimeException("Updated formulas not supported yet");
    }

    public EvaluationName getName(NamePtg namePtg) {
        return this._masterBook.getName(namePtg);
    }

    public EvaluationName getName(String str, int i) {
        return this._masterBook.getName(str, i);
    }

    public EvaluationSheet getSheet(int i) {
        return getSharedSheet(getSheetName(i));
    }

    public EvaluationWorkbook.ExternalName getExternalName(int i, int i2) {
        return this._masterBook.getExternalName(i, i2);
    }

    public int getSheetIndex(EvaluationSheet evaluationSheet) {
        if (evaluationSheet instanceof ForkedEvaluationSheet) {
            return ((ForkedEvaluationSheet) evaluationSheet).getSheetIndex(this._masterBook);
        }
        return this._masterBook.getSheetIndex(evaluationSheet);
    }

    public int getSheetIndex(String str) {
        return this._masterBook.getSheetIndex(str);
    }

    public String getSheetName(int i) {
        return this._masterBook.getSheetName(i);
    }

    public String resolveNameXText(NameXPtg nameXPtg) {
        return this._masterBook.resolveNameXText(nameXPtg);
    }

    public UDFFinder getUDFFinder() {
        return this._masterBook.getUDFFinder();
    }

    private static final class OrderedSheet implements Comparable<OrderedSheet> {
        private final int _index;
        private final String _sheetName;

        public OrderedSheet(String str, int i) {
            this._sheetName = str;
            this._index = i;
        }

        public String getSheetName() {
            return this._sheetName;
        }

        public int compareTo(OrderedSheet orderedSheet) {
            return this._index - orderedSheet._index;
        }
    }
}
