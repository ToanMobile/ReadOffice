package com.app.office.fc.hssf.eventusermodel.dummyrecord;

public final class MissingCellDummyRecord extends DummyRecordBase {
    private int column;
    private int row;

    public /* bridge */ /* synthetic */ int serialize(int i, byte[] bArr) {
        return super.serialize(i, bArr);
    }

    public MissingCellDummyRecord(int i, int i2) {
        this.row = i;
        this.column = i2;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}
