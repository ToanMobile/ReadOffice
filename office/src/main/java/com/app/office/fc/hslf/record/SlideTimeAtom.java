package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public class SlideTimeAtom extends PositionDependentRecordAtom {
    private static long _type = 12011;
    private byte[] _header;
    private long fileTime;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    protected SlideTimeAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.fileTime = LittleEndian.getLong(bArr, i + 8);
    }

    public long getSlideCreateTime() {
        return this.fileTime;
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
    }
}
