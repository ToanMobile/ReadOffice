package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;

public class EscherBlipRecord extends EscherRecord {
    private static final int HEADER_SIZE = 8;
    public static final String RECORD_DESCRIPTION = "msofbtBlip";
    public static final short RECORD_ID_END = -3817;
    public static final short RECORD_ID_START = -4072;
    protected byte[] field_pictureData;
    private String tempFilePath;

    public String getRecordName() {
        return "Blip";
    }

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        byte[] bArr2 = new byte[readHeader];
        this.field_pictureData = bArr2;
        System.arraycopy(bArr, i + 8, bArr2, 0, readHeader);
        return readHeader + 8;
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        byte[] bArr2 = this.field_pictureData;
        int i2 = i + 4;
        System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        escherSerializationListener.afterRecordSerialize(i2 + this.field_pictureData.length, getRecordId(), this.field_pictureData.length + 4, this);
        return this.field_pictureData.length + 4;
    }

    public int getRecordSize() {
        return this.field_pictureData.length + 8;
    }

    public byte[] getPicturedata() {
        return this.field_pictureData;
    }

    public void setPictureData(byte[] bArr) {
        this.field_pictureData = bArr;
    }

    public String toString() {
        String hex = HexDump.toHex(this.field_pictureData, 32);
        return getClass().getName() + ":" + 10 + "  RecordId: 0x" + HexDump.toHex(getRecordId()) + 10 + "  Options: 0x" + HexDump.toHex(getOptions()) + 10 + "  Extra Data:" + 10 + hex;
    }

    public void dispose() {
        this.field_pictureData = null;
    }

    public String getTempFilePath() {
        return this.tempFilePath;
    }

    public void setTempFilePath(String str) {
        this.tempFilePath = str;
    }
}
