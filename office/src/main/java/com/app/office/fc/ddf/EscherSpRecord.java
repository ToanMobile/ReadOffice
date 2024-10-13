package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;

public class EscherSpRecord extends EscherRecord {
    public static final int FLAG_BACKGROUND = 1024;
    public static final int FLAG_CHILD = 2;
    public static final int FLAG_CONNECTOR = 256;
    public static final int FLAG_DELETED = 8;
    public static final int FLAG_FLIPHORIZ = 64;
    public static final int FLAG_FLIPVERT = 128;
    public static final int FLAG_GROUP = 1;
    public static final int FLAG_HASSHAPETYPE = 2048;
    public static final int FLAG_HAVEANCHOR = 512;
    public static final int FLAG_HAVEMASTER = 32;
    public static final int FLAG_OLESHAPE = 16;
    public static final int FLAG_PATRIARCH = 4;
    public static final String RECORD_DESCRIPTION = "MsofbtSp";
    public static final short RECORD_ID = -4086;
    private int field_1_shapeId;
    private int field_2_flags;

    public void dispose() {
    }

    public short getRecordId() {
        return RECORD_ID;
    }

    public String getRecordName() {
        return "Sp";
    }

    public int getRecordSize() {
        return 16;
    }

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        readHeader(bArr, i);
        int i2 = i + 8;
        this.field_1_shapeId = LittleEndian.getInt(bArr, i2 + 0);
        this.field_2_flags = LittleEndian.getInt(bArr, i2 + 4);
        return getRecordSize();
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        LittleEndian.putInt(bArr, i + 4, 8);
        LittleEndian.putInt(bArr, i + 8, this.field_1_shapeId);
        LittleEndian.putInt(bArr, i + 12, this.field_2_flags);
        escherSerializationListener.afterRecordSerialize(i + getRecordSize(), getRecordId(), getRecordSize(), this);
        return 16;
    }

    public String toString() {
        String property = System.getProperty("line.separator");
        return getClass().getName() + ":" + property + "  RecordId: 0x" + HexDump.toHex((short) RECORD_ID) + property + "  Options: 0x" + HexDump.toHex(getOptions()) + property + "  ShapeId: " + this.field_1_shapeId + property + "  Flags: " + decodeFlags(this.field_2_flags) + " (0x" + HexDump.toHex(this.field_2_flags) + ")" + property;
    }

    private String decodeFlags(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";
        stringBuffer.append((i & 1) != 0 ? "|GROUP" : str);
        stringBuffer.append((i & 2) != 0 ? "|CHILD" : str);
        stringBuffer.append((i & 4) != 0 ? "|PATRIARCH" : str);
        stringBuffer.append((i & 8) != 0 ? "|DELETED" : str);
        stringBuffer.append((i & 16) != 0 ? "|OLESHAPE" : str);
        stringBuffer.append((i & 32) != 0 ? "|HAVEMASTER" : str);
        stringBuffer.append((i & 64) != 0 ? "|FLIPHORIZ" : str);
        stringBuffer.append((i & 128) != 0 ? "|FLIPVERT" : str);
        stringBuffer.append((i & 256) != 0 ? "|CONNECTOR" : str);
        stringBuffer.append((i & 512) != 0 ? "|HAVEANCHOR" : str);
        stringBuffer.append((i & 1024) != 0 ? "|BACKGROUND" : str);
        if ((i & 2048) != 0) {
            str = "|HASSHAPETYPE";
        }
        stringBuffer.append(str);
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(0);
        }
        return stringBuffer.toString();
    }

    public int getShapeId() {
        return this.field_1_shapeId;
    }

    public void setShapeId(int i) {
        this.field_1_shapeId = i;
    }

    public int getFlags() {
        return this.field_2_flags;
    }

    public void setFlags(int i) {
        this.field_2_flags = i;
    }
}
