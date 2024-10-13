package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public class VisualShapeAtom extends PositionDependentRecordAtom {
    public static final int ET_ShapeType = 1;
    public static final int ET_SoundType = 2;
    public static long RECORD_ID = 11003;
    public static final int TVET_AllTextRange = 8;
    public static final int TVET_Audio = 3;
    public static final int TVET_ChartElement = 5;
    public static final int TVET_Page = 1;
    public static final int TVET_Shape = 0;
    public static final int TVET_ShapeOnly = 6;
    public static final int TVET_TextRange = 2;
    public static final int TVET_Video = 4;
    private byte[] _header;
    private int animType;
    private int data1;
    private int data2;
    private int refType;
    private int shapeIdRef;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public long getRecordType() {
        return RECORD_ID;
    }

    protected VisualShapeAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.animType = LittleEndian.getInt(bArr, i + 8);
        this.refType = LittleEndian.getInt(bArr, i + 12);
        this.shapeIdRef = LittleEndian.getInt(bArr, i + 16);
        this.data1 = LittleEndian.getInt(bArr, i + 20);
        this.data2 = LittleEndian.getInt(bArr, i + 24);
    }

    public int getTargetElementType() {
        return this.animType;
    }

    public int getTargetElementID() {
        return this.shapeIdRef;
    }

    public int getData1() {
        return this.data1;
    }

    public int getData2() {
        return this.data2;
    }

    public void dispose() {
        this._header = null;
    }
}
