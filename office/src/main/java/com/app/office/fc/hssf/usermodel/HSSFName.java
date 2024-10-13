package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.NameCommentRecord;
import com.app.office.fc.hssf.record.NameRecord;
import com.app.office.fc.ss.usermodel.Name;
import com.app.office.ss.model.XLSModel.AWorkbook;

public final class HSSFName implements Name {
    private AWorkbook _book;
    private NameCommentRecord _commentRec;
    private NameRecord _definedNameRec;

    public String getRefersToFormula() {
        return null;
    }

    public void setRefersToFormula(String str) {
    }

    HSSFName(AWorkbook aWorkbook, NameRecord nameRecord) {
        this(aWorkbook, nameRecord, (NameCommentRecord) null);
    }

    public HSSFName(AWorkbook aWorkbook, NameRecord nameRecord, NameCommentRecord nameCommentRecord) {
        this._book = aWorkbook;
        this._definedNameRec = nameRecord;
        this._commentRec = nameCommentRecord;
    }

    public String getSheetName() {
        return this._book.getInternalWorkbook().findSheetNameFromExternSheet(this._definedNameRec.getExternSheetNumber());
    }

    public String getNameName() {
        return this._definedNameRec.getNameText();
    }

    public void setNameName(String str) {
        validateName(str);
        InternalWorkbook internalWorkbook = this._book.getInternalWorkbook();
        this._definedNameRec.setNameText(str);
        int sheetNumber = this._definedNameRec.getSheetNumber();
        int numNames = internalWorkbook.getNumNames() - 1;
        while (numNames >= 0) {
            NameRecord nameRecord = internalWorkbook.getNameRecord(numNames);
            if (nameRecord == this._definedNameRec || !nameRecord.getNameText().equalsIgnoreCase(str) || sheetNumber != nameRecord.getSheetNumber()) {
                numNames--;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("The ");
                sb.append(sheetNumber == 0 ? "workbook" : "sheet");
                sb.append(" already contains this name: ");
                sb.append(str);
                String sb2 = sb.toString();
                this._definedNameRec.setNameText(str + "(2)");
                throw new IllegalArgumentException(sb2);
            }
        }
        NameCommentRecord nameCommentRecord = this._commentRec;
        if (nameCommentRecord != null) {
            nameCommentRecord.getNameText();
            this._commentRec.setNameText(str);
            this._book.getInternalWorkbook().updateNameCommentRecordCache(this._commentRec);
        }
    }

    private static void validateName(String str) {
        if (str.length() != 0) {
            char charAt = str.charAt(0);
            if ((charAt != '_' && !Character.isLetter(charAt)) || str.indexOf(32) != -1) {
                throw new IllegalArgumentException("Invalid name: '" + str + "'; Names must begin with a letter or underscore and not contain spaces");
            }
            return;
        }
        throw new IllegalArgumentException("Name cannot be blank");
    }

    public String getReference() {
        return getRefersToFormula();
    }

    public void setReference(String str) {
        setRefersToFormula(str);
    }

    public Ptg[] getRefersToFormulaDefinition() {
        if (!this._definedNameRec.isFunctionName()) {
            return this._definedNameRec.getNameDefinition();
        }
        throw new IllegalStateException("Only applicable to named ranges");
    }

    public boolean isDeleted() {
        return Ptg.doesFormulaReferToDeletedCell(this._definedNameRec.getNameDefinition());
    }

    public boolean isFunctionName() {
        return this._definedNameRec.isFunctionName();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(this._definedNameRec.getNameText());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public void setSheetIndex(int i) {
        String str;
        int numberOfSheets = this._book.getNumberOfSheets() - 1;
        if (i < -1 || i > numberOfSheets) {
            StringBuilder sb = new StringBuilder();
            sb.append("Sheet index (");
            sb.append(i);
            sb.append(") is out of range");
            if (numberOfSheets == -1) {
                str = "";
            } else {
                str = " (0.." + numberOfSheets + ")";
            }
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
        this._definedNameRec.setSheetNumber(i + 1);
    }

    public int getSheetIndex() {
        return this._definedNameRec.getSheetNumber() - 1;
    }

    public String getComment() {
        NameCommentRecord nameCommentRecord = this._commentRec;
        if (nameCommentRecord == null || nameCommentRecord.getCommentText() == null || this._commentRec.getCommentText().length() <= 0) {
            return this._definedNameRec.getDescriptionText();
        }
        return this._commentRec.getCommentText();
    }

    public void setComment(String str) {
        this._definedNameRec.setDescriptionText(str);
        NameCommentRecord nameCommentRecord = this._commentRec;
        if (nameCommentRecord != null) {
            nameCommentRecord.setCommentText(str);
        }
    }

    public void setFunction(boolean z) {
        this._definedNameRec.setFunction(z);
    }

    public void dispose() {
        this._book = null;
        this._definedNameRec = null;
        this._commentRec = null;
    }
}
