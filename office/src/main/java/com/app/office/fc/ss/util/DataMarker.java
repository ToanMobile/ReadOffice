package com.app.office.fc.ss.util;

import com.app.office.fc.ss.usermodel.Sheet;

public class DataMarker {
    private HSSFCellRangeAddress range;
    private Sheet sheet;

    public String formatAsString() {
        return null;
    }

    public DataMarker(Sheet sheet2, HSSFCellRangeAddress hSSFCellRangeAddress) {
        this.sheet = sheet2;
        this.range = hSSFCellRangeAddress;
    }

    public Sheet getSheet() {
        return this.sheet;
    }

    public void setSheet(Sheet sheet2) {
        this.sheet = sheet2;
    }

    public HSSFCellRangeAddress getRange() {
        return this.range;
    }

    public void setRange(HSSFCellRangeAddress hSSFCellRangeAddress) {
        this.range = hSSFCellRangeAddress;
    }
}
