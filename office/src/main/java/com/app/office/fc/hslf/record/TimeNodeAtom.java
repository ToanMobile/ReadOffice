package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public class TimeNodeAtom extends PositionDependentRecordAtom {
    public static final int TNT_Behavior = 2;
    public static final int TNT_Parallel = 0;
    public static final int TNT_Sequential = 1;
    public static final int TNT__Media = 3;
    private static long _type = 61735;
    private byte[] _header;
    private int duration;
    private boolean fDurationProperty;
    private boolean fFillProperty;
    private boolean fGroupingTypeProperty;
    private boolean fRestartProperty;
    private int fill;
    private int reserved1;
    private int reserved2;
    private byte reserved3;
    private boolean reserved4;
    private byte[] reserved5;
    private int restart;
    private int timeNodeType;
    private int unused;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public long getRecordType() {
        return _type;
    }

    protected TimeNodeAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        boolean z = false;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.reserved1 = LittleEndian.getInt(bArr, i + 8);
        this.restart = LittleEndian.getInt(bArr, i + 12);
        this.timeNodeType = LittleEndian.getInt(bArr, i + 16);
        this.fill = LittleEndian.getInt(bArr, i + 20);
        this.duration = LittleEndian.getInt(bArr, i + 32);
        byte b = bArr[i + 36];
        this.fDurationProperty = ((b & 16) >> 4) > 0;
        this.fGroupingTypeProperty = ((b & 8) >> 3) > 0;
        this.fRestartProperty = ((b & 2) >> 1) > 0;
        this.fFillProperty = (b & 1) > 0 ? true : z;
    }

    public void dispose() {
        this._header = null;
    }
}
