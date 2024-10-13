package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class EscherBitmapBlip extends EscherBlipRecord {
    private static final int HEADER_SIZE = 8;
    public static final short RECORD_ID_DIB = -4065;
    public static final short RECORD_ID_JPEG = -4067;
    public static final short RECORD_ID_PNG = -4066;
    private byte[] field_1_UID;
    private byte field_2_marker = -1;

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        int i2 = i + 8;
        byte[] bArr2 = new byte[16];
        this.field_1_UID = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, 16);
        int i3 = i2 + 16;
        this.field_2_marker = bArr[i3];
        this.field_pictureData = new byte[(readHeader - 17)];
        System.arraycopy(bArr, i3 + 1, this.field_pictureData, 0, this.field_pictureData.length);
        return readHeader + 8;
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        LittleEndian.putInt(bArr, i + 4, getRecordSize() - 8);
        int i2 = i + 8;
        System.arraycopy(this.field_1_UID, 0, bArr, i2, 16);
        bArr[i2 + 16] = this.field_2_marker;
        System.arraycopy(this.field_pictureData, 0, bArr, i2 + 17, this.field_pictureData.length);
        escherSerializationListener.afterRecordSerialize(i + getRecordSize(), getRecordId(), getRecordSize(), this);
        return this.field_pictureData.length + 25;
    }

    public int getRecordSize() {
        return this.field_pictureData.length + 25;
    }

    public byte[] getUID() {
        return this.field_1_UID;
    }

    public void setUID(byte[] bArr) {
        this.field_1_UID = bArr;
    }

    public byte getMarker() {
        return this.field_2_marker;
    }

    public void setMarker(byte b) {
        this.field_2_marker = b;
    }

    public String toString() {
        String str;
        String property = System.getProperty("line.separator");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            HexDump.dump(this.field_pictureData, 0, (OutputStream) byteArrayOutputStream, 0);
            str = byteArrayOutputStream.toString();
        } catch (Exception e) {
            str = e.toString();
        }
        return getClass().getName() + ":" + property + "  RecordId: 0x" + HexDump.toHex(getRecordId()) + property + "  Options: 0x" + HexDump.toHex(getOptions()) + property + "  UID: 0x" + HexDump.toHex(this.field_1_UID) + property + "  Marker: 0x" + HexDump.toHex(this.field_2_marker) + property + "  Extra Data:" + property + str;
    }
}
