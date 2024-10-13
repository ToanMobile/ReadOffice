package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.aggregates.CFRecordsAggregate;
import com.app.office.fc.ss.usermodel.ConditionalFormatting;
import com.app.office.fc.ss.usermodel.ConditionalFormattingRule;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.ss.util.Region;

public final class HSSFConditionalFormatting implements ConditionalFormatting {
    private final HSSFWorkbook _workbook;
    private final CFRecordsAggregate cfAggregate;

    HSSFConditionalFormatting(HSSFWorkbook hSSFWorkbook, CFRecordsAggregate cFRecordsAggregate) {
        if (hSSFWorkbook == null) {
            throw new IllegalArgumentException("workbook must not be null");
        } else if (cFRecordsAggregate != null) {
            this._workbook = hSSFWorkbook;
            this.cfAggregate = cFRecordsAggregate;
        } else {
            throw new IllegalArgumentException("cfAggregate must not be null");
        }
    }

    /* access modifiers changed from: package-private */
    public CFRecordsAggregate getCFRecordsAggregate() {
        return this.cfAggregate;
    }

    public Region[] getFormattingRegions() {
        return Region.convertCellRangesToRegions(getFormattingRanges());
    }

    public HSSFCellRangeAddress[] getFormattingRanges() {
        return this.cfAggregate.getHeader().getCellRanges();
    }

    public void setRule(int i, HSSFConditionalFormattingRule hSSFConditionalFormattingRule) {
        this.cfAggregate.setRule(i, hSSFConditionalFormattingRule.getCfRuleRecord());
    }

    public void setRule(int i, ConditionalFormattingRule conditionalFormattingRule) {
        setRule(i, (HSSFConditionalFormattingRule) conditionalFormattingRule);
    }

    public void addRule(HSSFConditionalFormattingRule hSSFConditionalFormattingRule) {
        this.cfAggregate.addRule(hSSFConditionalFormattingRule.getCfRuleRecord());
    }

    public void addRule(ConditionalFormattingRule conditionalFormattingRule) {
        addRule((HSSFConditionalFormattingRule) conditionalFormattingRule);
    }

    public HSSFConditionalFormattingRule getRule(int i) {
        return new HSSFConditionalFormattingRule(this._workbook, this.cfAggregate.getRule(i));
    }

    public int getNumberOfRules() {
        return this.cfAggregate.getNumberOfRules();
    }

    public String toString() {
        return this.cfAggregate.toString();
    }
}
