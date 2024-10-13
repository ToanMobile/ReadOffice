package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;

public class EscherChildAnchorRecord extends EscherRecord {
    public static final String RECORD_DESCRIPTION = "MsofbtChildAnchor";
    public static final short RECORD_ID = -4081;
    private int field_1_dx1;
    private int field_2_dy1;
    private int field_3_dx2;
    private int field_4_dy2;

    public void dispose() {
    }

    public short getRecordId() {
        return RECORD_ID;
    }

    public String getRecordName() {
        return "ChildAnchor";
    }

    public int getRecordSize() {
        return 24;
    }

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        readHeader(bArr, i);
        int i2 = i + 8;
        this.field_1_dx1 = LittleEndian.getInt(bArr, i2 + 0);
        this.field_2_dy1 = LittleEndian.getInt(bArr, i2 + 4);
        this.field_3_dx2 = LittleEndian.getInt(bArr, i2 + 8);
        this.field_4_dy2 = LittleEndian.getInt(bArr, i2 + 12);
        return 24;
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        int i2 = i + 2;
        LittleEndian.putShort(bArr, i2, getRecordId());
        int i3 = i2 + 2;
        LittleEndian.putInt(bArr, i3, getRecordSize() - 8);
        int i4 = i3 + 4;
        LittleEndian.putInt(bArr, i4, this.field_1_dx1);
        int i5 = i4 + 4;
        LittleEndian.putInt(bArr, i5, this.field_2_dy1);
        int i6 = i5 + 4;
        LittleEndian.putInt(bArr, i6, this.field_3_dx2);
        int i7 = i6 + 4;
        LittleEndian.putInt(bArr, i7, this.field_4_dy2);
        int i8 = i7 + 4;
        int i9 = i8 - i;
        escherSerializationListener.afterRecordSerialize(i8, getRecordId(), i9, this);
        return i9;
    }

    public String toString() {
        String property = System.getProperty("line.separator");
        return getClass().getName() + ":" + property + "  RecordId: 0x" + HexDump.toHex((short) RECORD_ID) + property + "  Options: 0x" + HexDump.toHex(getOptions()) + property + "  X1: " + this.field_1_dx1 + property + "  Y1: " + this.field_2_dy1 + property + "  X2: " + this.field_3_dx2 + property + "  Y2: " + this.field_4_dy2 + property;
    }

    public int getDx1() {
        return this.field_1_dx1;
    }

    public void setDx1(int i) {
        this.field_1_dx1 = i;
    }

    public int getDy1() {
        return this.field_2_dy1;
    }

    public void setDy1(int i) {
        this.field_2_dy1 = i;
    }

    public int getDx2() {
        return this.field_3_dx2;
    }

    public void setDx2(int i) {
        this.field_3_dx2 = i;
    }

    public int getDy2() {
        return this.field_4_dy2;
    }

    public void setDy2(int i) {
        this.field_4_dy2 = i;
    }
}
