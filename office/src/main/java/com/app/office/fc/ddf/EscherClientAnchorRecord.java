package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class EscherClientAnchorRecord extends EscherRecord {
    public static final String RECORD_DESCRIPTION = "MsofbtClientAnchor";
    public static final short RECORD_ID = -4080;
    private short field_1_flag;
    private short field_2_col1;
    private short field_3_dx1;
    private short field_4_row1;
    private short field_5_dy1;
    private short field_6_col2;
    private short field_7_dx2;
    private short field_8_row2;
    private short field_9_dy2;
    private byte[] remainingData;
    private boolean shortRecord = false;

    public short getRecordId() {
        return RECORD_ID;
    }

    public String getRecordName() {
        return "ClientAnchor";
    }

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        int i2 = i + 8;
        int i3 = 18;
        if (readHeader == 4) {
            i3 = 0;
        } else if (readHeader == 16) {
            this.field_1_flag = LittleEndian.getShort(bArr, i2 + 0);
            this.field_2_col1 = LittleEndian.getShort(bArr, i2 + 4);
            this.field_3_dx1 = LittleEndian.getShort(bArr, i2 + 8);
            this.field_4_row1 = LittleEndian.getShort(bArr, i2 + 12);
            this.shortRecord = false;
            i3 = 16;
        } else {
            this.field_1_flag = LittleEndian.getShort(bArr, i2 + 0);
            this.field_2_col1 = LittleEndian.getShort(bArr, i2 + 2);
            this.field_3_dx1 = LittleEndian.getShort(bArr, i2 + 4);
            this.field_4_row1 = LittleEndian.getShort(bArr, i2 + 6);
            if (readHeader >= 18) {
                this.field_5_dy1 = LittleEndian.getShort(bArr, i2 + 8);
                this.field_6_col2 = LittleEndian.getShort(bArr, i2 + 10);
                this.field_7_dx2 = LittleEndian.getShort(bArr, i2 + 12);
                this.field_8_row2 = LittleEndian.getShort(bArr, i2 + 14);
                this.field_9_dy2 = LittleEndian.getShort(bArr, i2 + 16);
                this.shortRecord = false;
            } else {
                this.shortRecord = true;
                i3 = 8;
            }
        }
        int i4 = readHeader - i3;
        byte[] bArr2 = new byte[i4];
        this.remainingData = bArr2;
        System.arraycopy(bArr, i2 + i3, bArr2, 0, i4);
        return i3 + 8 + i4;
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        if (this.remainingData == null) {
            this.remainingData = new byte[0];
        }
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        int i2 = 8;
        LittleEndian.putInt(bArr, i + 4, this.remainingData.length + (this.shortRecord ? 8 : 18));
        int i3 = i + 8;
        LittleEndian.putShort(bArr, i3, this.field_1_flag);
        LittleEndian.putShort(bArr, i + 10, this.field_2_col1);
        LittleEndian.putShort(bArr, i + 12, this.field_3_dx1);
        LittleEndian.putShort(bArr, i + 14, this.field_4_row1);
        if (!this.shortRecord) {
            LittleEndian.putShort(bArr, i + 16, this.field_5_dy1);
            LittleEndian.putShort(bArr, i + 18, this.field_6_col2);
            LittleEndian.putShort(bArr, i + 20, this.field_7_dx2);
            LittleEndian.putShort(bArr, i + 22, this.field_8_row2);
            LittleEndian.putShort(bArr, i + 24, this.field_9_dy2);
        }
        byte[] bArr2 = this.remainingData;
        System.arraycopy(bArr2, 0, bArr, (this.shortRecord ? 16 : 26) + i, bArr2.length);
        if (!this.shortRecord) {
            i2 = 18;
        }
        int length = i3 + i2 + this.remainingData.length;
        int i4 = length - i;
        escherSerializationListener.afterRecordSerialize(length, getRecordId(), i4, this);
        return i4;
    }

    public int getRecordSize() {
        int i = (this.shortRecord ? 8 : 18) + 8;
        byte[] bArr = this.remainingData;
        return i + (bArr == null ? 0 : bArr.length);
    }

    public String toString() {
        String str;
        String property = System.getProperty("line.separator");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            HexDump.dump(this.remainingData, 0, (OutputStream) byteArrayOutputStream, 0);
            str = byteArrayOutputStream.toString();
        } catch (Exception unused) {
            str = "error\n";
        }
        return getClass().getName() + ":" + property + "  RecordId: 0x" + HexDump.toHex((short) RECORD_ID) + property + "  Options: 0x" + HexDump.toHex(getOptions()) + property + "  Flag: " + this.field_1_flag + property + "  Col1: " + this.field_2_col1 + property + "  DX1: " + this.field_3_dx1 + property + "  Row1: " + this.field_4_row1 + property + "  DY1: " + this.field_5_dy1 + property + "  Col2: " + this.field_6_col2 + property + "  DX2: " + this.field_7_dx2 + property + "  Row2: " + this.field_8_row2 + property + "  DY2: " + this.field_9_dy2 + property + "  Extra Data:" + property + str;
    }

    public short getFlag() {
        return this.field_1_flag;
    }

    public void setFlag(short s) {
        this.field_1_flag = s;
    }

    public short getCol1() {
        return this.field_2_col1;
    }

    public void setCol1(short s) {
        this.field_2_col1 = s;
    }

    public short getDx1() {
        return this.field_3_dx1;
    }

    public void setDx1(short s) {
        this.field_3_dx1 = s;
    }

    public short getRow1() {
        return this.field_4_row1;
    }

    public void setRow1(short s) {
        this.field_4_row1 = s;
    }

    public short getDy1() {
        return this.field_5_dy1;
    }

    public void setDy1(short s) {
        this.shortRecord = false;
        this.field_5_dy1 = s;
    }

    public short getCol2() {
        return this.field_6_col2;
    }

    public void setCol2(short s) {
        this.shortRecord = false;
        this.field_6_col2 = s;
    }

    public short getDx2() {
        return this.field_7_dx2;
    }

    public void setDx2(short s) {
        this.shortRecord = false;
        this.field_7_dx2 = s;
    }

    public short getRow2() {
        return this.field_8_row2;
    }

    public void setRow2(short s) {
        this.shortRecord = false;
        this.field_8_row2 = s;
    }

    public short getDy2() {
        return this.field_9_dy2;
    }

    public void setDy2(short s) {
        this.shortRecord = false;
        this.field_9_dy2 = s;
    }

    public byte[] getRemainingData() {
        return this.remainingData;
    }

    public void setRemainingData(byte[] bArr) {
        this.remainingData = bArr;
    }

    public void dispose() {
        this.remainingData = null;
    }
}
