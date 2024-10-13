package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.CFRuleRecord;
import com.app.office.fc.hssf.record.aggregates.CFRecordsAggregate;
import com.app.office.fc.hssf.record.aggregates.ConditionalFormattingTable;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.ss.usermodel.ConditionalFormatting;
import com.app.office.fc.ss.usermodel.ConditionalFormattingRule;
import com.app.office.fc.ss.usermodel.SheetConditionalFormatting;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.ss.util.Region;

public final class HSSFSheetConditionalFormatting implements SheetConditionalFormatting {
    private final ConditionalFormattingTable _conditionalFormattingTable;
    private final HSSFSheet _sheet;

    public HSSFConditionalFormattingRule createConditionalFormattingRule(byte b, String str) {
        return null;
    }

    public HSSFConditionalFormattingRule createConditionalFormattingRule(byte b, String str, String str2) {
        return null;
    }

    HSSFSheetConditionalFormatting(HSSFSheet hSSFSheet) {
        this._sheet = hSSFSheet;
        this._conditionalFormattingTable = hSSFSheet.getSheet().getConditionalFormattingTable();
    }

    public HSSFConditionalFormattingRule createConditionalFormattingRule(String str) {
        this._sheet.getWorkbook();
        return null;
    }

    public int addConditionalFormatting(HSSFConditionalFormatting hSSFConditionalFormatting) {
        return this._conditionalFormattingTable.add(hSSFConditionalFormatting.getCFRecordsAggregate().cloneCFAggregate());
    }

    public int addConditionalFormatting(ConditionalFormatting conditionalFormatting) {
        return addConditionalFormatting((HSSFConditionalFormatting) conditionalFormatting);
    }

    public int addConditionalFormatting(Region[] regionArr, HSSFConditionalFormattingRule[] hSSFConditionalFormattingRuleArr) {
        return addConditionalFormatting(Region.convertRegionsToCellRanges(regionArr), hSSFConditionalFormattingRuleArr);
    }

    public int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, HSSFConditionalFormattingRule[] hSSFConditionalFormattingRuleArr) {
        if (hSSFCellRangeAddressArr != null) {
            for (HSSFCellRangeAddress validate : hSSFCellRangeAddressArr) {
                validate.validate(SpreadsheetVersion.EXCEL97);
            }
            if (hSSFConditionalFormattingRuleArr == null) {
                throw new IllegalArgumentException("cfRules must not be null");
            } else if (hSSFConditionalFormattingRuleArr.length == 0) {
                throw new IllegalArgumentException("cfRules must not be empty");
            } else if (hSSFConditionalFormattingRuleArr.length <= 3) {
                CFRuleRecord[] cFRuleRecordArr = new CFRuleRecord[hSSFConditionalFormattingRuleArr.length];
                for (int i = 0; i != hSSFConditionalFormattingRuleArr.length; i++) {
                    cFRuleRecordArr[i] = hSSFConditionalFormattingRuleArr[i].getCfRuleRecord();
                }
                return this._conditionalFormattingTable.add(new CFRecordsAggregate(hSSFCellRangeAddressArr, cFRuleRecordArr));
            } else {
                throw new IllegalArgumentException("Number of rules must not exceed 3");
            }
        } else {
            throw new IllegalArgumentException("regions must not be null");
        }
    }

    public int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, ConditionalFormattingRule[] conditionalFormattingRuleArr) {
        HSSFConditionalFormattingRule[] hSSFConditionalFormattingRuleArr;
        if (conditionalFormattingRuleArr instanceof HSSFConditionalFormattingRule[]) {
            hSSFConditionalFormattingRuleArr = (HSSFConditionalFormattingRule[]) conditionalFormattingRuleArr;
        } else {
            int length = conditionalFormattingRuleArr.length;
            HSSFConditionalFormattingRule[] hSSFConditionalFormattingRuleArr2 = new HSSFConditionalFormattingRule[length];
            System.arraycopy(conditionalFormattingRuleArr, 0, hSSFConditionalFormattingRuleArr2, 0, length);
            hSSFConditionalFormattingRuleArr = hSSFConditionalFormattingRuleArr2;
        }
        return addConditionalFormatting(hSSFCellRangeAddressArr, hSSFConditionalFormattingRuleArr);
    }

    public int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, HSSFConditionalFormattingRule hSSFConditionalFormattingRule) {
        HSSFConditionalFormattingRule[] hSSFConditionalFormattingRuleArr;
        if (hSSFConditionalFormattingRule == null) {
            hSSFConditionalFormattingRuleArr = null;
        } else {
            hSSFConditionalFormattingRuleArr = new HSSFConditionalFormattingRule[]{hSSFConditionalFormattingRule};
        }
        return addConditionalFormatting(hSSFCellRangeAddressArr, hSSFConditionalFormattingRuleArr);
    }

    public int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, ConditionalFormattingRule conditionalFormattingRule) {
        return addConditionalFormatting(hSSFCellRangeAddressArr, (HSSFConditionalFormattingRule) conditionalFormattingRule);
    }

    public int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, HSSFConditionalFormattingRule hSSFConditionalFormattingRule, HSSFConditionalFormattingRule hSSFConditionalFormattingRule2) {
        return addConditionalFormatting(hSSFCellRangeAddressArr, new HSSFConditionalFormattingRule[]{hSSFConditionalFormattingRule, hSSFConditionalFormattingRule2});
    }

    public int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, ConditionalFormattingRule conditionalFormattingRule, ConditionalFormattingRule conditionalFormattingRule2) {
        return addConditionalFormatting(hSSFCellRangeAddressArr, (HSSFConditionalFormattingRule) conditionalFormattingRule, (HSSFConditionalFormattingRule) conditionalFormattingRule2);
    }

    public HSSFConditionalFormatting getConditionalFormattingAt(int i) {
        CFRecordsAggregate cFRecordsAggregate = this._conditionalFormattingTable.get(i);
        if (cFRecordsAggregate == null) {
            return null;
        }
        return new HSSFConditionalFormatting(this._sheet.getWorkbook(), cFRecordsAggregate);
    }

    public int getNumConditionalFormattings() {
        return this._conditionalFormattingTable.size();
    }

    public void removeConditionalFormatting(int i) {
        this._conditionalFormattingTable.remove(i);
    }
}
