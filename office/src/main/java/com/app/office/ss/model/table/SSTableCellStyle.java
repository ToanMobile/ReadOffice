package com.app.office.ss.model.table;

public class SSTableCellStyle {
    private Integer borderColor;
    private Integer fillColor;
    private int fontColor = -16777216;

    public SSTableCellStyle(int i) {
        this.fillColor = Integer.valueOf(i);
    }

    public int getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(int i) {
        this.fontColor = i;
    }

    public Integer getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(int i) {
        this.borderColor = Integer.valueOf(i);
    }

    public Integer getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(int i) {
        this.fillColor = Integer.valueOf(i);
    }
}
