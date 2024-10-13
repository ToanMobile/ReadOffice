package com.app.office.fc.hslf.record;

public class SlaveContainer extends PositionDependentRecordContainer {
    private static long _type = 61765;
    private byte[] _header;

    public long getRecordType() {
        return _type;
    }

    protected SlaveContainer(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
    }
}
