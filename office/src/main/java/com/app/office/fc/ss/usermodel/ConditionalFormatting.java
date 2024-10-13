package com.app.office.fc.ss.usermodel;

import com.app.office.fc.ss.util.HSSFCellRangeAddress;

public interface ConditionalFormatting {
    void addRule(ConditionalFormattingRule conditionalFormattingRule);

    HSSFCellRangeAddress[] getFormattingRanges();

    int getNumberOfRules();

    ConditionalFormattingRule getRule(int i);

    void setRule(int i, ConditionalFormattingRule conditionalFormattingRule);
}
