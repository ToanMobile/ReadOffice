package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.CFRuleRecord;
import com.app.office.fc.ss.usermodel.PatternFormatting;

public class HSSFPatternFormatting implements PatternFormatting {
    private final CFRuleRecord cfRuleRecord;
    private final com.app.office.fc.hssf.record.cf.PatternFormatting patternFormatting;

    protected HSSFPatternFormatting(CFRuleRecord cFRuleRecord) {
        this.cfRuleRecord = cFRuleRecord;
        this.patternFormatting = cFRuleRecord.getPatternFormatting();
    }

    /* access modifiers changed from: protected */
    public com.app.office.fc.hssf.record.cf.PatternFormatting getPatternFormattingBlock() {
        return this.patternFormatting;
    }

    public short getFillBackgroundColor() {
        return (short) this.patternFormatting.getFillBackgroundColor();
    }

    public short getFillForegroundColor() {
        return (short) this.patternFormatting.getFillForegroundColor();
    }

    public short getFillPattern() {
        return (short) this.patternFormatting.getFillPattern();
    }

    public void setFillBackgroundColor(short s) {
        this.patternFormatting.setFillBackgroundColor(s);
        if (s != 0) {
            this.cfRuleRecord.setPatternBackgroundColorModified(true);
        }
    }

    public void setFillForegroundColor(short s) {
        this.patternFormatting.setFillForegroundColor(s);
        if (s != 0) {
            this.cfRuleRecord.setPatternColorModified(true);
        }
    }

    public void setFillPattern(short s) {
        this.patternFormatting.setFillPattern(s);
        if (s != 0) {
            this.cfRuleRecord.setPatternStyleModified(true);
        }
    }
}
