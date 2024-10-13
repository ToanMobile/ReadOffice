package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public class TimeIterateDataAtom extends PositionDependentRecordAtom {
    private static long _type = 61760;
    private byte[] _header;
    private boolean fIterateDirectionPropertyUsed;
    private boolean fIterateIntervalPropertyUsed;
    private boolean fIterateIntervalTypePropertyUsed;
    private boolean fIterateTypePropertyUsed;
    private int iterateDirection;
    private int iterateInterval;
    private int iterateIntervalType;
    private int iterateType;
    private byte[] reserved;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public long getRecordType() {
        return _type;
    }

    protected TimeIterateDataAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.iterateInterval = LittleEndian.getInt(bArr, i + 8);
        this.iterateType = LittleEndian.getInt(bArr, i + 12);
        this.iterateDirection = LittleEndian.getInt(bArr, i + 16);
        this.iterateIntervalType = LittleEndian.getInt(bArr, i + 20);
    }

    public void dispose() {
        this._header = null;
    }
}
