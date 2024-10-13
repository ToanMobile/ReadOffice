package com.app.office.fc.hssf.eventusermodel.dummyrecord;

public final class MissingRowDummyRecord extends DummyRecordBase {
    private int rowNumber;

    public /* bridge */ /* synthetic */ int serialize(int i, byte[] bArr) {
        return super.serialize(i, bArr);
    }

    public MissingRowDummyRecord(int i) {
        this.rowNumber = i;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }
}
