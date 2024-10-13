package com.app.office.fc.hslf.record;

import java.util.Hashtable;

public class TimeColorBehaviorAtom extends PositionDependentRecordAtom {
    private static long _type = 61749;
    private byte[] _header;
    private int flag;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public long getRecordType() {
        return _type;
    }

    protected TimeColorBehaviorAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
    }

    public void dispose() {
        this._header = null;
    }
}
