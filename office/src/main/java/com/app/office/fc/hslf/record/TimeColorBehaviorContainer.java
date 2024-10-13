package com.app.office.fc.hslf.record;

public class TimeColorBehaviorContainer extends PositionDependentRecordContainer {
    public static long RECORD_ID = 61740;
    private byte[] _header;
    private TimeColorBehaviorAtom colorBehaviorAtom;

    public long getRecordType() {
        return RECORD_ID;
    }

    protected TimeColorBehaviorContainer(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i + 8;
        this.colorBehaviorAtom = new TimeColorBehaviorAtom(bArr, i3, 60);
        this._children = Record.findChildRecords(bArr, i3, i2 - 8);
    }
}
