package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class DummyPositionSensitiveRecordWithChildren extends PositionDependentRecordContainer {
    private byte[] _header;
    private long _type = ((long) LittleEndian.getUShort(this._header, 2));

    protected DummyPositionSensitiveRecordWithChildren(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
    }

    public long getRecordType() {
        return this._type;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
    }
}
