package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.formula.EvaluationCell;
import com.app.office.fc.hssf.formula.EvaluationName;
import com.app.office.fc.hssf.formula.EvaluationSheet;
import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.hssf.formula.FormulaParsingWorkbook;
import com.app.office.fc.hssf.formula.FormulaRenderingWorkbook;
import com.app.office.fc.hssf.formula.ptg.NamePtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.NameRecord;
import com.app.office.fc.hssf.record.aggregates.FormulaRecordAggregate;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.ss.model.XLSModel.AWorkbook;
import com.app.office.ss.model.baseModel.Sheet;

public final class HSSFEvaluationWorkbook implements FormulaRenderingWorkbook, EvaluationWorkbook, FormulaParsingWorkbook {
    private final InternalWorkbook _iBook;
    private final AWorkbook _uBook;

    public static HSSFEvaluationWorkbook create(AWorkbook aWorkbook) {
        if (aWorkbook == null) {
            return null;
        }
        return new HSSFEvaluationWorkbook(aWorkbook);
    }

    private HSSFEvaluationWorkbook(AWorkbook aWorkbook) {
        this._uBook = aWorkbook;
        this._iBook = aWorkbook.getInternalWorkbook();
    }

    public int getExternalSheetIndex(String str) {
        return this._iBook.checkExternSheet(this._uBook.getSheetIndex(str));
    }

    public int getExternalSheetIndex(String str, String str2) {
        return this._iBook.getExternalSheetIndex(str, str2);
    }

    public NameXPtg getNameXPtg(String str) {
        return this._iBook.getNameXPtg(str, this._uBook.getUDFFinder());
    }

    public EvaluationName getName(String str, int i) {
        for (int i2 = 0; i2 < this._iBook.getNumNames(); i2++) {
            NameRecord nameRecord = this._iBook.getNameRecord(i2);
            if (nameRecord.getSheetNumber() == i + 1 && str.equalsIgnoreCase(nameRecord.getNameText())) {
                return new Name(nameRecord, i2);
            }
        }
        if (i == -1) {
            return null;
        }
        return getName(str, -1);
    }

    public int getSheetIndex(EvaluationSheet evaluationSheet) {
        return this._uBook.getSheetIndex((Sheet) ((HSSFEvaluationSheet) evaluationSheet).getASheet());
    }

    public int getSheetIndex(String str) {
        return this._uBook.getSheetIndex(str);
    }

    public String getSheetName(int i) {
        return this._uBook.getSheet(i).getSheetName();
    }

    public EvaluationSheet getSheet(int i) {
        return new HSSFEvaluationSheet(this._uBook.getSheetAt(i));
    }

    public int convertFromExternSheetIndex(int i) {
        return this._iBook.getSheetIndexFromExternSheetIndex(i);
    }

    public EvaluationWorkbook.ExternalSheet getExternalSheet(int i) {
        return this._iBook.getExternalSheet(i);
    }

    public EvaluationWorkbook.ExternalName getExternalName(int i, int i2) {
        return this._iBook.getExternalName(i, i2);
    }

    public String resolveNameXText(NameXPtg nameXPtg) {
        return this._iBook.resolveNameXText(nameXPtg.getSheetRefIndex(), nameXPtg.getNameIndex());
    }

    public String getSheetNameByExternSheet(int i) {
        return this._iBook.findSheetNameFromExternSheet(i);
    }

    public String getNameText(NamePtg namePtg) {
        return this._iBook.getNameRecord(namePtg.getIndex()).getNameText();
    }

    public EvaluationName getName(NamePtg namePtg) {
        int index = namePtg.getIndex();
        return new Name(this._iBook.getNameRecord(index), index);
    }

    public EvaluationName getName(NameXPtg nameXPtg) {
        int nameIndex = nameXPtg.getNameIndex();
        return new Name(this._iBook.getNameRecord(nameIndex), nameIndex);
    }

    public Ptg[] getFormulaTokens(EvaluationCell evaluationCell) {
        return ((FormulaRecordAggregate) ((HSSFEvaluationCell) evaluationCell).getACell().getCellValueRecord()).getFormulaTokens();
    }

    public UDFFinder getUDFFinder() {
        return this._uBook.getUDFFinder();
    }

    private static final class Name implements EvaluationName {
        private final int _index;
        private final NameRecord _nameRecord;

        public Name(NameRecord nameRecord, int i) {
            this._nameRecord = nameRecord;
            this._index = i;
        }

        public Ptg[] getNameDefinition() {
            return this._nameRecord.getNameDefinition();
        }

        public String getNameText() {
            return this._nameRecord.getNameText();
        }

        public boolean hasFormula() {
            return this._nameRecord.hasFormula();
        }

        public boolean isFunctionName() {
            return this._nameRecord.isFunctionName();
        }

        public boolean isRange() {
            return this._nameRecord.hasFormula();
        }

        public NamePtg createPtg() {
            return new NamePtg(this._index);
        }
    }

    public SpreadsheetVersion getSpreadsheetVersion() {
        return SpreadsheetVersion.EXCEL97;
    }
}
