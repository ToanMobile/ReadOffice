package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.formula.FormulaShifter;
import com.app.office.fc.hssf.formula.ptg.AreaErrPtg;
import com.app.office.fc.hssf.formula.ptg.AreaPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.CFHeaderRecord;
import com.app.office.fc.hssf.record.CFRuleRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import java.util.ArrayList;
import java.util.List;

public final class CFRecordsAggregate extends RecordAggregate {
    private static final int MAX_CONDTIONAL_FORMAT_RULES = 3;
    private final CFHeaderRecord header;
    private final List rules;

    private CFRecordsAggregate(CFHeaderRecord cFHeaderRecord, CFRuleRecord[] cFRuleRecordArr) {
        if (cFHeaderRecord == null) {
            throw new IllegalArgumentException("header must not be null");
        } else if (cFRuleRecordArr == null) {
            throw new IllegalArgumentException("rules must not be null");
        } else if (cFRuleRecordArr.length > 3) {
            throw new IllegalArgumentException("No more than 3 rules may be specified");
        } else if (cFRuleRecordArr.length == cFHeaderRecord.getNumberOfConditionalFormats()) {
            this.header = cFHeaderRecord;
            this.rules = new ArrayList(3);
            for (CFRuleRecord add : cFRuleRecordArr) {
                this.rules.add(add);
            }
        } else {
            throw new RuntimeException("Mismatch number of rules");
        }
    }

    public CFRecordsAggregate(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, CFRuleRecord[] cFRuleRecordArr) {
        this(new CFHeaderRecord(hSSFCellRangeAddressArr, cFRuleRecordArr.length), cFRuleRecordArr);
    }

    public static CFRecordsAggregate createCFAggregate(RecordStream recordStream) {
        Record next = recordStream.getNext();
        if (next.getSid() == 432) {
            CFHeaderRecord cFHeaderRecord = (CFHeaderRecord) next;
            int numberOfConditionalFormats = cFHeaderRecord.getNumberOfConditionalFormats();
            CFRuleRecord[] cFRuleRecordArr = new CFRuleRecord[numberOfConditionalFormats];
            for (int i = 0; i < numberOfConditionalFormats; i++) {
                cFRuleRecordArr[i] = (CFRuleRecord) recordStream.getNext();
            }
            return new CFRecordsAggregate(cFHeaderRecord, cFRuleRecordArr);
        }
        throw new IllegalStateException("next record sid was " + next.getSid() + " instead of " + 432 + " as expected");
    }

    public CFRecordsAggregate cloneCFAggregate() {
        int size = this.rules.size();
        CFRuleRecord[] cFRuleRecordArr = new CFRuleRecord[size];
        for (int i = 0; i < size; i++) {
            cFRuleRecordArr[i] = (CFRuleRecord) getRule(i).clone();
        }
        return new CFRecordsAggregate((CFHeaderRecord) this.header.clone(), cFRuleRecordArr);
    }

    public CFHeaderRecord getHeader() {
        return this.header;
    }

    private void checkRuleIndex(int i) {
        if (i < 0 || i >= this.rules.size()) {
            throw new IllegalArgumentException("Bad rule record index (" + i + ") nRules=" + this.rules.size());
        }
    }

    public CFRuleRecord getRule(int i) {
        checkRuleIndex(i);
        return (CFRuleRecord) this.rules.get(i);
    }

    public void setRule(int i, CFRuleRecord cFRuleRecord) {
        if (cFRuleRecord != null) {
            checkRuleIndex(i);
            this.rules.set(i, cFRuleRecord);
            return;
        }
        throw new IllegalArgumentException("r must not be null");
    }

    public void addRule(CFRuleRecord cFRuleRecord) {
        if (cFRuleRecord == null) {
            throw new IllegalArgumentException("r must not be null");
        } else if (this.rules.size() < 3) {
            this.rules.add(cFRuleRecord);
            this.header.setNumberOfConditionalFormats(this.rules.size());
        } else {
            throw new IllegalStateException("Cannot have more than 3 conditional format rules");
        }
    }

    public int getNumberOfRules() {
        return this.rules.size();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CF]\n");
        CFHeaderRecord cFHeaderRecord = this.header;
        if (cFHeaderRecord != null) {
            stringBuffer.append(cFHeaderRecord.toString());
        }
        for (int i = 0; i < this.rules.size(); i++) {
            stringBuffer.append(((CFRuleRecord) this.rules.get(i)).toString());
        }
        stringBuffer.append("[/CF]\n");
        return stringBuffer.toString();
    }

    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        recordVisitor.visitRecord(this.header);
        for (int i = 0; i < this.rules.size(); i++) {
            recordVisitor.visitRecord((CFRuleRecord) this.rules.get(i));
        }
    }

    public boolean updateFormulasAfterCellShift(FormulaShifter formulaShifter, int i) {
        HSSFCellRangeAddress[] cellRanges = this.header.getCellRanges();
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (HSSFCellRangeAddress hSSFCellRangeAddress : cellRanges) {
            HSSFCellRangeAddress shiftRange = shiftRange(formulaShifter, hSSFCellRangeAddress, i);
            if (shiftRange != null) {
                arrayList.add(shiftRange);
                if (shiftRange == hSSFCellRangeAddress) {
                }
            }
            z = true;
        }
        if (z) {
            int size = arrayList.size();
            if (size == 0) {
                return false;
            }
            HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[size];
            arrayList.toArray(hSSFCellRangeAddressArr);
            this.header.setCellRanges(hSSFCellRangeAddressArr);
        }
        for (int i2 = 0; i2 < this.rules.size(); i2++) {
            CFRuleRecord cFRuleRecord = (CFRuleRecord) this.rules.get(i2);
            Ptg[] parsedExpression1 = cFRuleRecord.getParsedExpression1();
            if (parsedExpression1 != null && formulaShifter.adjustFormula(parsedExpression1, i)) {
                cFRuleRecord.setParsedExpression1(parsedExpression1);
            }
            Ptg[] parsedExpression2 = cFRuleRecord.getParsedExpression2();
            if (parsedExpression2 != null && formulaShifter.adjustFormula(parsedExpression2, i)) {
                cFRuleRecord.setParsedExpression2(parsedExpression2);
            }
        }
        return true;
    }

    private static HSSFCellRangeAddress shiftRange(FormulaShifter formulaShifter, HSSFCellRangeAddress hSSFCellRangeAddress, int i) {
        Ptg[] ptgArr = {new AreaPtg(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastColumn(), false, false, false, false)};
        if (!formulaShifter.adjustFormula(ptgArr, i)) {
            return hSSFCellRangeAddress;
        }
        Ptg ptg = ptgArr[0];
        if (ptg instanceof AreaPtg) {
            AreaPtg areaPtg = (AreaPtg) ptg;
            return new HSSFCellRangeAddress(areaPtg.getFirstRow(), areaPtg.getLastRow(), areaPtg.getFirstColumn(), areaPtg.getLastColumn());
        } else if (ptg instanceof AreaErrPtg) {
            return null;
        } else {
            throw new IllegalStateException("Unexpected shifted ptg class (" + ptg.getClass().getName() + ")");
        }
    }
}
