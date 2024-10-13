package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.StringUtil;
import java.util.Hashtable;

public class TimeVariant extends PositionDependentRecordAtom {
    public static final byte TPID_AfterEffect = 13;
    public static final byte TPID_Display = 2;
    public static final byte TPID_EffectDir = 10;
    public static final byte TPID_EffectType = 11;
    public static final byte TPID_HideWhenStopped = 18;
    public static final byte TPID_MasterPos = 5;
    public static final byte TPID_MediaMute = 23;
    public static final byte TPID_PlaceholderNode = 21;
    public static final byte TPID_SlaveType = 6;
    public static final byte TPID_SlideCount = 15;
    public static final byte TPID__EffectID = 9;
    public static final byte TPID__EffectNodeType = 20;
    public static final byte TPID__EventFilter = 17;
    public static final byte TPID__GroupID = 19;
    public static final byte TPID__MediaVolume = 22;
    public static final byte TPID__TimeFilter = 16;
    public static final byte TPID__ZoomToFullScreen = 26;
    private static final byte TVT_Bool = 0;
    private static final byte TVT_Int = 1;
    private static final byte TVT_String = 3;
    private static final byte TVT_TVT_Float = 2;
    public static final byte TimeEffectType__ActionVerb = 5;
    public static final byte TimeEffectType__Emphasis = 3;
    public static final byte TimeEffectType__Entrance = 1;
    public static final byte TimeEffectType__Exit = 2;
    public static final byte TimeEffectType__MediaCommand = 6;
    public static final byte TimeEffectType__MotionPath = 4;
    private static long _type = 61762;
    private byte[] _header;
    private int tpID = ((LittleEndian.getShort(this._header, 0) & 65520) >> 4);
    private Object value;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public long getRecordType() {
        return _type;
    }

    public TimeVariant(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        boolean z = false;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        byte b = bArr[i + 8];
        if (b == 0) {
            this.value = Boolean.valueOf(bArr[i + 9] == 1 ? true : z);
        } else if (b == 1) {
            this.value = Integer.valueOf(LittleEndian.getInt(bArr, i + 9));
        } else if (b == 2) {
            this.value = Float.valueOf(LittleEndian.getFloat(bArr, i + 9));
        } else if (b == 3) {
            int i3 = LittleEndian.getInt(this._header, 4) - 1;
            byte[] bArr3 = new byte[i3];
            System.arraycopy(bArr, i + 9, bArr3, 0, i3);
            this.value = StringUtil.getFromUnicodeLE(bArr3);
        }
    }

    public int getAttributeType() {
        return this.tpID;
    }

    public Object getValue() {
        return this.value;
    }

    public void dispose() {
        this._header = null;
    }
}
