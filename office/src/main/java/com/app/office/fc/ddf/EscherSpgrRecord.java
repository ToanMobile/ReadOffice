package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.RecordFormatException;

public class EscherSpgrRecord extends EscherRecord {
    public static final String RECORD_DESCRIPTION = "MsofbtSpgr";
    public static final short RECORD_ID = -4087;
    private int field_1_rectX1;
    private int field_2_rectY1;
    private int field_3_rectX2;
    private int field_4_rectY2;

    public void dispose() {
    }

    public short getRecordId() {
        return RECORD_ID;
    }

    public String getRecordName() {
        return "Spgr";
    }

    public int getRecordSize() {
        return 24;
    }

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        int i2 = i + 8;
        this.field_1_rectX1 = LittleEndian.getInt(bArr, i2 + 0);
        this.field_2_rectY1 = LittleEndian.getInt(bArr, i2 + 4);
        this.field_3_rectX2 = LittleEndian.getInt(bArr, i2 + 8);
        this.field_4_rectY2 = LittleEndian.getInt(bArr, i2 + 12);
        int i3 = readHeader - 16;
        if (i3 == 0) {
            return 24 + i3;
        }
        throw new RecordFormatException("Expected no remaining bytes but got " + i3);
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        LittleEndian.putInt(bArr, i + 4, 16);
        LittleEndian.putInt(bArr, i + 8, this.field_1_rectX1);
        LittleEndian.putInt(bArr, i + 12, this.field_2_rectY1);
        LittleEndian.putInt(bArr, i + 16, this.field_3_rectX2);
        LittleEndian.putInt(bArr, i + 20, this.field_4_rectY2);
        escherSerializationListener.afterRecordSerialize(getRecordSize() + i, getRecordId(), i + getRecordSize(), this);
        return 24;
    }

    public String toString() {
        return getClass().getName() + ":" + 10 + "  RecordId: 0x" + HexDump.toHex((short) RECORD_ID) + 10 + "  Options: 0x" + HexDump.toHex(getOptions()) + 10 + "  RectX: " + this.field_1_rectX1 + 10 + "  RectY: " + this.field_2_rectY1 + 10 + "  RectWidth: " + this.field_3_rectX2 + 10 + "  RectHeight: " + this.field_4_rectY2 + 10;
    }

    public int getRectX1() {
        return this.field_1_rectX1;
    }

    public void setRectX1(int i) {
        this.field_1_rectX1 = i;
    }

    public int getRectY1() {
        return this.field_2_rectY1;
    }

    public void setRectY1(int i) {
        this.field_2_rectY1 = i;
    }

    public int getRectX2() {
        return this.field_3_rectX2;
    }

    public void setRectX2(int i) {
        this.field_3_rectX2 = i;
    }

    public int getRectY2() {
        return this.field_4_rectY2;
    }

    public void setRectY2(int i) {
        this.field_4_rectY2 = i;
    }
}
