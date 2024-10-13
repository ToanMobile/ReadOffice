package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public class TimeConditionAtom extends PositionDependentRecordAtom {
    public static final int TOT_None = 0;
    public static final int TOT_RuntimeNodeRef = 3;
    public static final int TOT_TimeNode = 2;
    public static final int TOT_VisualElement = 1;
    private static long _type = 61736;
    private byte[] _header;
    private int delay;
    private int id;
    private int triggerEvent;
    private int triggerObject;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public long getRecordType() {
        return _type;
    }

    protected TimeConditionAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.triggerObject = LittleEndian.getInt(bArr, i + 8);
        this.triggerEvent = LittleEndian.getInt(bArr, i + 12);
        this.id = LittleEndian.getInt(bArr, i + 16);
        this.delay = LittleEndian.getInt(bArr, i + 20);
    }

    public void dispose() {
        this._header = null;
    }
}
