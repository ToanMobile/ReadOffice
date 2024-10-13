package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.record.CFRuleRecord;
import com.app.office.fc.hssf.record.cf.BorderFormatting;
import com.app.office.fc.hssf.record.cf.FontFormatting;
import com.app.office.fc.hssf.record.cf.PatternFormatting;
import com.app.office.fc.ss.usermodel.ConditionalFormattingRule;

public final class HSSFConditionalFormattingRule implements ConditionalFormattingRule {
    private static final byte CELL_COMPARISON = 1;
    private final CFRuleRecord cfRuleRecord;
    private final HSSFWorkbook workbook;

    private String toFormulaString(Ptg[] ptgArr) {
        return null;
    }

    HSSFConditionalFormattingRule(HSSFWorkbook hSSFWorkbook, CFRuleRecord cFRuleRecord) {
        if (hSSFWorkbook == null) {
            throw new IllegalArgumentException("pWorkbook must not be null");
        } else if (cFRuleRecord != null) {
            this.workbook = hSSFWorkbook;
            this.cfRuleRecord = cFRuleRecord;
        } else {
            throw new IllegalArgumentException("pRuleRecord must not be null");
        }
    }

    /* access modifiers changed from: package-private */
    public CFRuleRecord getCfRuleRecord() {
        return this.cfRuleRecord;
    }

    private HSSFFontFormatting getFontFormatting(boolean z) {
        FontFormatting fontFormatting = this.cfRuleRecord.getFontFormatting();
        if (fontFormatting != null) {
            this.cfRuleRecord.setFontFormatting(fontFormatting);
            return new HSSFFontFormatting(this.cfRuleRecord);
        } else if (!z) {
            return null;
        } else {
            this.cfRuleRecord.setFontFormatting(new FontFormatting());
            return new HSSFFontFormatting(this.cfRuleRecord);
        }
    }

    public HSSFFontFormatting getFontFormatting() {
        return getFontFormatting(false);
    }

    public HSSFFontFormatting createFontFormatting() {
        return getFontFormatting(true);
    }

    private HSSFBorderFormatting getBorderFormatting(boolean z) {
        BorderFormatting borderFormatting = this.cfRuleRecord.getBorderFormatting();
        if (borderFormatting != null) {
            this.cfRuleRecord.setBorderFormatting(borderFormatting);
            return new HSSFBorderFormatting(this.cfRuleRecord);
        } else if (!z) {
            return null;
        } else {
            this.cfRuleRecord.setBorderFormatting(new BorderFormatting());
            return new HSSFBorderFormatting(this.cfRuleRecord);
        }
    }

    public HSSFBorderFormatting getBorderFormatting() {
        return getBorderFormatting(false);
    }

    public HSSFBorderFormatting createBorderFormatting() {
        return getBorderFormatting(true);
    }

    private HSSFPatternFormatting getPatternFormatting(boolean z) {
        PatternFormatting patternFormatting = this.cfRuleRecord.getPatternFormatting();
        if (patternFormatting != null) {
            this.cfRuleRecord.setPatternFormatting(patternFormatting);
            return new HSSFPatternFormatting(this.cfRuleRecord);
        } else if (!z) {
            return null;
        } else {
            this.cfRuleRecord.setPatternFormatting(new PatternFormatting());
            return new HSSFPatternFormatting(this.cfRuleRecord);
        }
    }

    public HSSFPatternFormatting getPatternFormatting() {
        return getPatternFormatting(false);
    }

    public HSSFPatternFormatting createPatternFormatting() {
        return getPatternFormatting(true);
    }

    public byte getConditionType() {
        return this.cfRuleRecord.getConditionType();
    }

    public byte getComparisonOperation() {
        return this.cfRuleRecord.getComparisonOperation();
    }

    public String getFormula1() {
        return toFormulaString(this.cfRuleRecord.getParsedExpression1());
    }

    public String getFormula2() {
        if (this.cfRuleRecord.getConditionType() != 1) {
            return null;
        }
        byte comparisonOperation = this.cfRuleRecord.getComparisonOperation();
        if (comparisonOperation == 1 || comparisonOperation == 2) {
            return toFormulaString(this.cfRuleRecord.getParsedExpression2());
        }
        return null;
    }
}
