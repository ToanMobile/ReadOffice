package com.app.office.fc.hslf.record;

public class TimeRotationBehaviorContainer extends PositionDependentRecordContainer {
    public static long RECORD_ID = 61743;
    private byte[] _header;

    public long getRecordType() {
        return RECORD_ID;
    }

    protected TimeRotationBehaviorContainer(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
    }
}
