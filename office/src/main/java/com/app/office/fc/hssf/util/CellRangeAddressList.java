package com.app.office.fc.hssf.util;

import com.app.office.fc.hssf.record.RecordInputStream;

public class CellRangeAddressList extends com.app.office.fc.ss.util.CellRangeAddressList {
    public CellRangeAddressList(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public CellRangeAddressList() {
    }

    public CellRangeAddressList(RecordInputStream recordInputStream) {
        int readUShort = recordInputStream.readUShort();
        for (int i = 0; i < readUShort; i++) {
            this._list.add(new CellRangeAddress(recordInputStream));
        }
    }
}
