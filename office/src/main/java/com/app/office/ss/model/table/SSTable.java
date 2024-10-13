package com.app.office.ss.model.table;

import com.app.office.ss.model.CellRangeAddress;

public class SSTable {
    private int headerRowBorderDxfId = -1;
    private int headerRowDxfId = -1;
    private boolean headerRowShown = true;
    private String name;
    private CellRangeAddress ref;
    private boolean showColumnStripes = false;
    private boolean showFirstColumn = false;
    private boolean showLastColumn = false;
    private boolean showRowStripes = false;
    private int tableBorderDxfId = -1;
    private boolean totalRowShown = false;
    private int totalsRowBorderDxfId = -1;
    private int totalsRowDxfId = -1;

    public CellRangeAddress getTableReference() {
        return this.ref;
    }

    public void setTableReference(CellRangeAddress cellRangeAddress) {
        this.ref = cellRangeAddress;
    }

    public boolean isHeaderRowShown() {
        return this.headerRowShown;
    }

    public void setHeaderRowShown(boolean z) {
        this.headerRowShown = z;
    }

    public boolean isTotalRowShown() {
        return this.totalRowShown;
    }

    public void setTotalRowShown(boolean z) {
        this.totalRowShown = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public boolean isShowFirstColumn() {
        return this.showFirstColumn;
    }

    public void setShowFirstColumn(boolean z) {
        this.showFirstColumn = z;
    }

    public boolean isShowLastColumn() {
        return this.showLastColumn;
    }

    public void setShowLastColumn(boolean z) {
        this.showLastColumn = z;
    }

    public boolean isShowRowStripes() {
        return this.showRowStripes;
    }

    public void setShowRowStripes(boolean z) {
        this.showRowStripes = z;
    }

    public boolean isShowColumnStripes() {
        return this.showColumnStripes;
    }

    public void setShowColumnStripes(boolean z) {
        this.showColumnStripes = z;
    }

    public int getTableBorderDxfId() {
        return this.tableBorderDxfId;
    }

    public void setTableBorderDxfId(int i) {
        this.tableBorderDxfId = i;
    }

    public int getHeaderRowDxfId() {
        return this.headerRowDxfId;
    }

    public void setHeaderRowDxfId(int i) {
        this.headerRowDxfId = i;
    }

    public int getHeaderRowBorderDxfId() {
        return this.headerRowBorderDxfId;
    }

    public void setHeaderRowBorderDxfId(int i) {
        this.headerRowBorderDxfId = i;
    }

    public int getTotalsRowDxfId() {
        return this.totalsRowDxfId;
    }

    public void setTotalsRowDxfId(int i) {
        this.totalsRowDxfId = i;
    }

    public int getTotalsRowBorderDxfId() {
        return this.totalsRowBorderDxfId;
    }

    public void setTotalsRowBorderDxfId(int i) {
        this.totalsRowBorderDxfId = i;
    }
}
