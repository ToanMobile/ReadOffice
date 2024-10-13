package com.app.office.ss.model.table;

public class SSTableStyle {
    private SSTableCellStyle band1H;
    private SSTableCellStyle band1V;
    private SSTableCellStyle band2H;
    private SSTableCellStyle band2V;
    private SSTableCellStyle firstCol;
    private SSTableCellStyle firstRow;
    private SSTableCellStyle lastCol;
    private SSTableCellStyle lastRow;

    public SSTableCellStyle getBand1H() {
        return this.band1H;
    }

    public void setBand1H(SSTableCellStyle sSTableCellStyle) {
        this.band1H = sSTableCellStyle;
    }

    public SSTableCellStyle getBand2H() {
        return this.band2H;
    }

    public void setBand2H(SSTableCellStyle sSTableCellStyle) {
        this.band2H = sSTableCellStyle;
    }

    public SSTableCellStyle getBand1V() {
        return this.band1V;
    }

    public void setBand1V(SSTableCellStyle sSTableCellStyle) {
        this.band1V = sSTableCellStyle;
    }

    public SSTableCellStyle getBand2V() {
        return this.band2V;
    }

    public void setBand2V(SSTableCellStyle sSTableCellStyle) {
        this.band2V = sSTableCellStyle;
    }

    public SSTableCellStyle getFirstCol() {
        return this.firstCol;
    }

    public void setFirstCol(SSTableCellStyle sSTableCellStyle) {
        this.firstCol = sSTableCellStyle;
    }

    public SSTableCellStyle getLastCol() {
        return this.lastCol;
    }

    public void setLastCol(SSTableCellStyle sSTableCellStyle) {
        this.lastCol = sSTableCellStyle;
    }

    public SSTableCellStyle getFirstRow() {
        return this.firstRow;
    }

    public void setFirstRow(SSTableCellStyle sSTableCellStyle) {
        this.firstRow = sSTableCellStyle;
    }

    public SSTableCellStyle getLastRow() {
        return this.lastRow;
    }

    public void setLastRow(SSTableCellStyle sSTableCellStyle) {
        this.lastRow = sSTableCellStyle;
    }
}
