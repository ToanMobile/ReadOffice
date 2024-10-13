package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ss.usermodel.AutoFilter;

public final class HSSFAutoFilter implements AutoFilter {
    private HSSFSheet _sheet;

    HSSFAutoFilter(HSSFSheet hSSFSheet) {
        this._sheet = hSSFSheet;
    }
}
