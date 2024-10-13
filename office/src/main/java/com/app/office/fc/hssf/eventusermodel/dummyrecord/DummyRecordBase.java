package com.app.office.fc.hssf.eventusermodel.dummyrecord;

import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordFormatException;

abstract class DummyRecordBase extends Record {
    public final short getSid() {
        return -1;
    }

    protected DummyRecordBase() {
    }

    public int serialize(int i, byte[] bArr) {
        throw new RecordFormatException("Cannot serialize a dummy record");
    }

    public final int getRecordSize() {
        throw new RecordFormatException("Cannot serialize a dummy record");
    }
}
