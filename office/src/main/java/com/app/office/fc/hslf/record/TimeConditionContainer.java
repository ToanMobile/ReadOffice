package com.app.office.fc.hslf.record;

public class TimeConditionContainer extends PositionDependentRecordContainer {
    private static long _type = 61733;
    private byte[] _header;

    public long getRecordType() {
        return _type;
    }

    protected TimeConditionContainer(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
    }
}
