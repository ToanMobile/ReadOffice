package com.app.office.fc.hssf.util;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;

public class CellRangeAddress extends HSSFCellRangeAddress {
    public CellRangeAddress(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public CellRangeAddress(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }
}
