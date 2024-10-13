package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.formula.Formula;
import com.app.office.fc.hssf.formula.ptg.ExpPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.record.ArrayRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.FormulaRecord;
import com.app.office.fc.hssf.record.RecordFormatException;
import com.app.office.fc.hssf.record.SharedFormulaRecord;
import com.app.office.fc.hssf.record.SharedValueRecordBase;
import com.app.office.fc.hssf.record.StringRecord;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import com.app.office.fc.hssf.util.CellRangeAddress8Bit;
import com.app.office.fc.ss.util.CellReference;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;

public final class FormulaRecordAggregate extends RecordAggregate implements CellValueRecordInterface {
    private final FormulaRecord _formulaRecord;
    private SharedFormulaRecord _sharedFormulaRecord;
    private SharedValueManager _sharedValueManager;
    private StringRecord _stringRecord;

    public FormulaRecordAggregate(FormulaRecord formulaRecord, StringRecord stringRecord, SharedValueManager sharedValueManager) {
        if (sharedValueManager != null) {
            if (!formulaRecord.hasCachedResultString()) {
                this._stringRecord = null;
            } else if (stringRecord != null) {
                this._stringRecord = stringRecord;
            } else {
                throw new RecordFormatException("Formula record flag is set but String record was not found");
            }
            this._formulaRecord = formulaRecord;
            this._sharedValueManager = sharedValueManager;
            if (formulaRecord.isSharedFormula()) {
                CellReference expReference = formulaRecord.getFormula().getExpReference();
                if (expReference == null) {
                    handleMissingSharedFormulaRecord(formulaRecord);
                } else {
                    this._sharedFormulaRecord = sharedValueManager.linkSharedFormulaRecord(expReference, this);
                }
            }
        } else {
            throw new IllegalArgumentException("sfm must not be null");
        }
    }

    private static void handleMissingSharedFormulaRecord(FormulaRecord formulaRecord) {
        if (!(formulaRecord.getParsedExpression()[0] instanceof ExpPtg)) {
            formulaRecord.setSharedFormula(false);
            return;
        }
        throw new RecordFormatException("SharedFormulaRecord not found for FormulaRecord with (isSharedFormula=true)");
    }

    public FormulaRecord getFormulaRecord() {
        return this._formulaRecord;
    }

    public StringRecord getStringRecord() {
        return this._stringRecord;
    }

    public short getXFIndex() {
        return this._formulaRecord.getXFIndex();
    }

    public void setXFIndex(short s) {
        this._formulaRecord.setXFIndex(s);
    }

    public void setColumn(short s) {
        this._formulaRecord.setColumn(s);
    }

    public void setRow(int i) {
        this._formulaRecord.setRow(i);
    }

    public short getColumn() {
        return this._formulaRecord.getColumn();
    }

    public int getRow() {
        return this._formulaRecord.getRow();
    }

    public String toString() {
        return this._formulaRecord.toString();
    }

    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        StringRecord stringRecord;
        recordVisitor.visitRecord(this._formulaRecord);
        SharedValueRecordBase recordForFirstCell = this._sharedValueManager.getRecordForFirstCell(this);
        if (recordForFirstCell != null) {
            recordVisitor.visitRecord(recordForFirstCell);
        }
        if (this._formulaRecord.hasCachedResultString() && (stringRecord = this._stringRecord) != null) {
            recordVisitor.visitRecord(stringRecord);
        }
    }

    public String getStringValue() {
        StringRecord stringRecord = this._stringRecord;
        if (stringRecord == null) {
            return null;
        }
        return stringRecord.getString();
    }

    public void setCachedStringResult(String str) {
        if (this._stringRecord == null) {
            this._stringRecord = new StringRecord();
        }
        this._stringRecord.setString(str);
        if (str.length() < 1) {
            this._formulaRecord.setCachedResultTypeEmptyString();
        } else {
            this._formulaRecord.setCachedResultTypeString();
        }
    }

    public void setCachedBooleanResult(boolean z) {
        this._stringRecord = null;
        this._formulaRecord.setCachedResultBoolean(z);
    }

    public void setCachedErrorResult(int i) {
        this._stringRecord = null;
        this._formulaRecord.setCachedResultErrorCode(i);
    }

    public void setCachedDoubleResult(double d) {
        this._stringRecord = null;
        this._formulaRecord.setValue(d);
    }

    public Ptg[] getFormulaTokens() {
        SharedFormulaRecord sharedFormulaRecord = this._sharedFormulaRecord;
        if (sharedFormulaRecord != null) {
            return sharedFormulaRecord.getFormulaTokens(this._formulaRecord);
        }
        CellReference expReference = this._formulaRecord.getFormula().getExpReference();
        if (expReference != null) {
            return this._sharedValueManager.getArrayRecord(expReference.getRow(), expReference.getCol()).getFormulaTokens();
        }
        return this._formulaRecord.getParsedExpression();
    }

    public void setParsedExpression(Ptg[] ptgArr) {
        notifyFormulaChanging();
        this._formulaRecord.setParsedExpression(ptgArr);
    }

    public void unlinkSharedFormula() {
        SharedFormulaRecord sharedFormulaRecord = this._sharedFormulaRecord;
        if (sharedFormulaRecord != null) {
            this._formulaRecord.setParsedExpression(sharedFormulaRecord.getFormulaTokens(this._formulaRecord));
            this._formulaRecord.setSharedFormula(false);
            this._sharedFormulaRecord = null;
            return;
        }
        throw new IllegalStateException("Formula not linked to shared formula");
    }

    public void notifyFormulaChanging() {
        SharedFormulaRecord sharedFormulaRecord = this._sharedFormulaRecord;
        if (sharedFormulaRecord != null) {
            this._sharedValueManager.unlink(sharedFormulaRecord);
        }
    }

    public boolean isPartOfArrayFormula() {
        ArrayRecord arrayRecord;
        if (this._sharedFormulaRecord != null) {
            return false;
        }
        CellReference expReference = this._formulaRecord.getFormula().getExpReference();
        if (expReference == null) {
            arrayRecord = null;
        } else {
            arrayRecord = this._sharedValueManager.getArrayRecord(expReference.getRow(), expReference.getCol());
        }
        if (arrayRecord != null) {
            return true;
        }
        return false;
    }

    public HSSFCellRangeAddress getArrayFormulaRange() {
        if (this._sharedFormulaRecord == null) {
            CellReference expReference = this._formulaRecord.getFormula().getExpReference();
            if (expReference != null) {
                ArrayRecord arrayRecord = this._sharedValueManager.getArrayRecord(expReference.getRow(), expReference.getCol());
                if (arrayRecord != null) {
                    CellRangeAddress8Bit range = arrayRecord.getRange();
                    return new HSSFCellRangeAddress(range.getFirstRow(), range.getLastRow(), range.getFirstColumn(), range.getLastColumn());
                }
                throw new IllegalStateException("ArrayRecord was not found for the locator " + expReference.formatAsString());
            }
            throw new IllegalStateException("not an array formula cell.");
        }
        throw new IllegalStateException("not an array formula cell.");
    }

    public void setArrayFormula(HSSFCellRangeAddress hSSFCellRangeAddress, Ptg[] ptgArr) {
        this._sharedValueManager.addArrayRecord(new ArrayRecord(Formula.create(ptgArr), new CellRangeAddress8Bit(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastColumn())));
    }

    public HSSFCellRangeAddress removeArrayFormula(int i, int i2) {
        CellRangeAddress8Bit removeArrayFormula = this._sharedValueManager.removeArrayFormula(i, i2);
        this._formulaRecord.setParsedExpression((Ptg[]) null);
        return new HSSFCellRangeAddress(removeArrayFormula.getFirstRow(), removeArrayFormula.getLastRow(), removeArrayFormula.getFirstColumn(), removeArrayFormula.getLastColumn());
    }
}
