package com.app.office.pg.model.tableStyle;

public class TableStyle {
    private TableCellStyle band1H;
    private TableCellStyle band1V;
    private TableCellStyle band2H;
    private TableCellStyle band2V;
    private TableCellStyle firstCol;
    private TableCellStyle firstRow;
    private TableCellStyle lastCol;
    private TableCellStyle lastRow;
    private TableCellStyle wholeTable;

    public TableCellStyle getWholeTable() {
        return this.wholeTable;
    }

    public void setWholeTable(TableCellStyle tableCellStyle) {
        this.wholeTable = tableCellStyle;
    }

    public TableCellStyle getBand1H() {
        return this.band1H;
    }

    public void setBand1H(TableCellStyle tableCellStyle) {
        this.band1H = tableCellStyle;
    }

    public TableCellStyle getBand2H() {
        return this.band2H;
    }

    public void setBand2H(TableCellStyle tableCellStyle) {
        this.band2H = tableCellStyle;
    }

    public TableCellStyle getBand1V() {
        return this.band1V;
    }

    public void setBand1V(TableCellStyle tableCellStyle) {
        this.band1V = tableCellStyle;
    }

    public TableCellStyle getBand2V() {
        return this.band2V;
    }

    public void setBand2V(TableCellStyle tableCellStyle) {
        this.band2V = tableCellStyle;
    }

    public TableCellStyle getFirstCol() {
        return this.firstCol;
    }

    public void setFirstCol(TableCellStyle tableCellStyle) {
        this.firstCol = tableCellStyle;
    }

    public TableCellStyle getLastCol() {
        return this.lastCol;
    }

    public void setLastCol(TableCellStyle tableCellStyle) {
        this.lastCol = tableCellStyle;
    }

    public TableCellStyle getFirstRow() {
        return this.firstRow;
    }

    public void setFirstRow(TableCellStyle tableCellStyle) {
        this.firstRow = tableCellStyle;
    }

    public TableCellStyle getLastRow() {
        return this.lastRow;
    }

    public void setLastRow(TableCellStyle tableCellStyle) {
        this.lastRow = tableCellStyle;
    }

    public void dispose() {
        TableCellStyle tableCellStyle = this.wholeTable;
        if (tableCellStyle != null) {
            tableCellStyle.dispose();
            this.wholeTable = null;
        }
        TableCellStyle tableCellStyle2 = this.band1H;
        if (tableCellStyle2 != null) {
            tableCellStyle2.dispose();
            this.band1H = null;
        }
        TableCellStyle tableCellStyle3 = this.band2H;
        if (tableCellStyle3 != null) {
            tableCellStyle3.dispose();
            this.band2H = null;
        }
        TableCellStyle tableCellStyle4 = this.band1V;
        if (tableCellStyle4 != null) {
            tableCellStyle4.dispose();
            this.band1V = null;
        }
        TableCellStyle tableCellStyle5 = this.band2V;
        if (tableCellStyle5 != null) {
            tableCellStyle5.dispose();
            this.band2V = null;
        }
        TableCellStyle tableCellStyle6 = this.firstCol;
        if (tableCellStyle6 != null) {
            tableCellStyle6.dispose();
            this.firstCol = null;
        }
        TableCellStyle tableCellStyle7 = this.lastCol;
        if (tableCellStyle7 != null) {
            tableCellStyle7.dispose();
            this.lastCol = null;
        }
        TableCellStyle tableCellStyle8 = this.firstRow;
        if (tableCellStyle8 != null) {
            tableCellStyle8.dispose();
            this.firstRow = null;
        }
        TableCellStyle tableCellStyle9 = this.lastRow;
        if (tableCellStyle9 != null) {
            tableCellStyle9.dispose();
            this.lastRow = null;
        }
    }
}
