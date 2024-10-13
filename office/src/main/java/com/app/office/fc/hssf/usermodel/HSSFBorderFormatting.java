package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.CFRuleRecord;
import com.app.office.fc.ss.usermodel.BorderFormatting;

public final class HSSFBorderFormatting implements BorderFormatting {
    private final com.app.office.fc.hssf.record.cf.BorderFormatting borderFormatting;
    private final CFRuleRecord cfRuleRecord;

    protected HSSFBorderFormatting(CFRuleRecord cFRuleRecord) {
        this.cfRuleRecord = cFRuleRecord;
        this.borderFormatting = cFRuleRecord.getBorderFormatting();
    }

    /* access modifiers changed from: protected */
    public com.app.office.fc.hssf.record.cf.BorderFormatting getBorderFormattingBlock() {
        return this.borderFormatting;
    }

    public short getBorderBottom() {
        return (short) this.borderFormatting.getBorderBottom();
    }

    public short getBorderDiagonal() {
        return (short) this.borderFormatting.getBorderDiagonal();
    }

    public short getBorderLeft() {
        return (short) this.borderFormatting.getBorderLeft();
    }

    public short getBorderRight() {
        return (short) this.borderFormatting.getBorderRight();
    }

    public short getBorderTop() {
        return (short) this.borderFormatting.getBorderTop();
    }

    public short getBottomBorderColor() {
        return (short) this.borderFormatting.getBottomBorderColor();
    }

    public short getDiagonalBorderColor() {
        return (short) this.borderFormatting.getDiagonalBorderColor();
    }

    public short getLeftBorderColor() {
        return (short) this.borderFormatting.getLeftBorderColor();
    }

    public short getRightBorderColor() {
        return (short) this.borderFormatting.getRightBorderColor();
    }

    public short getTopBorderColor() {
        return (short) this.borderFormatting.getTopBorderColor();
    }

    public boolean isBackwardDiagonalOn() {
        return this.borderFormatting.isBackwardDiagonalOn();
    }

    public boolean isForwardDiagonalOn() {
        return this.borderFormatting.isForwardDiagonalOn();
    }

    public void setBackwardDiagonalOn(boolean z) {
        this.borderFormatting.setBackwardDiagonalOn(z);
        if (z) {
            this.cfRuleRecord.setTopLeftBottomRightBorderModified(z);
        }
    }

    public void setBorderBottom(short s) {
        this.borderFormatting.setBorderBottom(s);
        if (s != 0) {
            this.cfRuleRecord.setBottomBorderModified(true);
        }
    }

    public void setBorderDiagonal(short s) {
        this.borderFormatting.setBorderDiagonal(s);
        if (s != 0) {
            this.cfRuleRecord.setBottomLeftTopRightBorderModified(true);
            this.cfRuleRecord.setTopLeftBottomRightBorderModified(true);
        }
    }

    public void setBorderLeft(short s) {
        this.borderFormatting.setBorderLeft(s);
        if (s != 0) {
            this.cfRuleRecord.setLeftBorderModified(true);
        }
    }

    public void setBorderRight(short s) {
        this.borderFormatting.setBorderRight(s);
        if (s != 0) {
            this.cfRuleRecord.setRightBorderModified(true);
        }
    }

    public void setBorderTop(short s) {
        this.borderFormatting.setBorderTop(s);
        if (s != 0) {
            this.cfRuleRecord.setTopBorderModified(true);
        }
    }

    public void setBottomBorderColor(short s) {
        this.borderFormatting.setBottomBorderColor(s);
        if (s != 0) {
            this.cfRuleRecord.setBottomBorderModified(true);
        }
    }

    public void setDiagonalBorderColor(short s) {
        this.borderFormatting.setDiagonalBorderColor(s);
        if (s != 0) {
            this.cfRuleRecord.setBottomLeftTopRightBorderModified(true);
            this.cfRuleRecord.setTopLeftBottomRightBorderModified(true);
        }
    }

    public void setForwardDiagonalOn(boolean z) {
        this.borderFormatting.setForwardDiagonalOn(z);
        if (z) {
            this.cfRuleRecord.setBottomLeftTopRightBorderModified(z);
        }
    }

    public void setLeftBorderColor(short s) {
        this.borderFormatting.setLeftBorderColor(s);
        if (s != 0) {
            this.cfRuleRecord.setLeftBorderModified(true);
        }
    }

    public void setRightBorderColor(short s) {
        this.borderFormatting.setRightBorderColor(s);
        if (s != 0) {
            this.cfRuleRecord.setRightBorderModified(true);
        }
    }

    public void setTopBorderColor(short s) {
        this.borderFormatting.setTopBorderColor(s);
        if (s != 0) {
            this.cfRuleRecord.setTopBorderModified(true);
        }
    }
}
