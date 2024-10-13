package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public class TimeSequenceDataAtom extends PositionDependentRecordAtom {
    private static long _type = 61761;
    private byte[] _header;
    private int concurrency;
    private boolean fConcurrencyPropertyUsed;
    private boolean fNextActionPropertyUsed;
    private boolean fPreviousActionPropertyUsed;
    private int nextAction;
    private int previousAction;
    private int reserved1;
    private byte[] reserved2;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public long getRecordType() {
        return _type;
    }

    protected TimeSequenceDataAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.concurrency = LittleEndian.getInt(bArr, i + 8);
        this.nextAction = LittleEndian.getInt(bArr, i + 12);
        this.previousAction = LittleEndian.getInt(bArr, i + 16);
        this.reserved1 = LittleEndian.getInt(bArr, i + 20);
    }

    public void dispose() {
        this._header = null;
    }
}
