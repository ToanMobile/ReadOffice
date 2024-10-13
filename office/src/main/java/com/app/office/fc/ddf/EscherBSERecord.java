package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;

public final class EscherBSERecord extends EscherRecord {
    public static final byte BT_DIB = 7;
    public static final byte BT_EMF = 2;
    public static final byte BT_ERROR = 0;
    public static final byte BT_JPEG = 5;
    public static final byte BT_PICT = 4;
    public static final byte BT_PNG = 6;
    public static final byte BT_UNKNOWN = 1;
    public static final byte BT_WMF = 3;
    public static final String RECORD_DESCRIPTION = "MsofbtBSE";
    public static final short RECORD_ID = -4089;
    private byte[] _remainingData;
    private byte field_10_unused2;
    private byte field_11_unused3;
    private EscherBlipRecord field_12_blipRecord;
    private byte field_1_blipTypeWin32;
    private byte field_2_blipTypeMacOS;
    private byte[] field_3_uid;
    private short field_4_tag;
    private int field_5_size;
    private int field_6_ref;
    private int field_7_offset;
    private byte field_8_usage;
    private byte field_9_name;

    public static String getBlipType(byte b) {
        switch (b) {
            case 0:
                return " ERROR";
            case 1:
                return " UNKNOWN";
            case 2:
                return " EMF";
            case 3:
                return " WMF";
            case 4:
                return " PICT";
            case 5:
                return " JPEG";
            case 6:
                return " PNG";
            case 7:
                return " DIB";
            default:
                return b < 32 ? " NotKnown" : " Client";
        }
    }

    public String getRecordName() {
        return "BSE";
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x008b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int fillFields(byte[] r6, int r7, com.app.office.fc.ddf.EscherRecordFactory r8) {
        /*
            r5 = this;
            int r0 = r5.readHeader(r6, r7)
            int r7 = r7 + 8
            byte r1 = r6[r7]
            r5.field_1_blipTypeWin32 = r1
            int r1 = r7 + 1
            byte r1 = r6[r1]
            r5.field_2_blipTypeMacOS = r1
            int r1 = r7 + 2
            r2 = 16
            byte[] r3 = new byte[r2]
            r5.field_3_uid = r3
            r4 = 0
            java.lang.System.arraycopy(r6, r1, r3, r4, r2)
            int r1 = r7 + 18
            short r1 = com.app.office.fc.util.LittleEndian.getShort(r6, r1)
            r5.field_4_tag = r1
            int r1 = r7 + 20
            int r1 = com.app.office.fc.util.LittleEndian.getInt(r6, r1)
            r5.field_5_size = r1
            int r1 = r7 + 24
            int r1 = com.app.office.fc.util.LittleEndian.getInt(r6, r1)
            r5.field_6_ref = r1
            int r1 = r7 + 28
            int r1 = com.app.office.fc.util.LittleEndian.getInt(r6, r1)
            r5.field_7_offset = r1
            int r1 = r7 + 32
            byte r1 = r6[r1]
            r5.field_8_usage = r1
            int r1 = r7 + 33
            byte r1 = r6[r1]
            r5.field_9_name = r1
            int r1 = r7 + 34
            byte r1 = r6[r1]
            r5.field_10_unused2 = r1
            int r1 = r7 + 35
            byte r1 = r6[r1]
            r5.field_11_unused3 = r1
            int r0 = r0 + -36
            if (r0 <= 0) goto L_0x0076
            int r1 = r7 + 36
            com.app.office.fc.ddf.EscherRecord r2 = r8.createRecord(r6, r1)
            boolean r3 = r2 instanceof com.app.office.fc.ddf.EscherBlipRecord
            if (r3 == 0) goto L_0x006b
            com.app.office.fc.ddf.EscherBlipRecord r2 = (com.app.office.fc.ddf.EscherBlipRecord) r2
            r5.field_12_blipRecord = r2
            int r8 = r2.fillFields(r6, r1, r8)
            goto L_0x0077
        L_0x006b:
            boolean r3 = r2 instanceof com.app.office.fc.ddf.EscherBSERecord
            if (r3 == 0) goto L_0x0076
            com.app.office.fc.ddf.EscherBSERecord r2 = (com.app.office.fc.ddf.EscherBSERecord) r2
            int r6 = r5.fillFields(r6, r1, r8)
            return r6
        L_0x0076:
            r8 = 0
        L_0x0077:
            int r1 = r8 + 36
            int r7 = r7 + r1
            int r0 = r0 - r8
            byte[] r8 = new byte[r0]
            r5._remainingData = r8
            java.lang.System.arraycopy(r6, r7, r8, r4, r0)
            int r0 = r0 + 8
            int r0 = r0 + 36
            com.app.office.fc.ddf.EscherBlipRecord r6 = r5.field_12_blipRecord
            if (r6 != 0) goto L_0x008b
            goto L_0x008f
        L_0x008b:
            int r4 = r6.getRecordSize()
        L_0x008f:
            int r0 = r0 + r4
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ddf.EscherBSERecord.fillFields(byte[], int, com.app.office.fc.ddf.EscherRecordFactory):int");
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        if (this._remainingData == null) {
            this._remainingData = new byte[0];
        }
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        if (this._remainingData == null) {
            this._remainingData = new byte[0];
        }
        EscherBlipRecord escherBlipRecord = this.field_12_blipRecord;
        LittleEndian.putInt(bArr, i + 4, this._remainingData.length + 36 + (escherBlipRecord == null ? 0 : escherBlipRecord.getRecordSize()));
        int i2 = i + 8;
        bArr[i2] = this.field_1_blipTypeWin32;
        bArr[i + 9] = this.field_2_blipTypeMacOS;
        for (int i3 = 0; i3 < 16; i3++) {
            bArr[i + 10 + i3] = this.field_3_uid[i3];
        }
        LittleEndian.putShort(bArr, i + 26, this.field_4_tag);
        LittleEndian.putInt(bArr, i + 28, this.field_5_size);
        LittleEndian.putInt(bArr, i + 32, this.field_6_ref);
        LittleEndian.putInt(bArr, i + 36, this.field_7_offset);
        bArr[i + 40] = this.field_8_usage;
        bArr[i + 41] = this.field_9_name;
        bArr[i + 42] = this.field_10_unused2;
        bArr[i + 43] = this.field_11_unused3;
        EscherBlipRecord escherBlipRecord2 = this.field_12_blipRecord;
        int serialize = escherBlipRecord2 != null ? escherBlipRecord2.serialize(i + 44, bArr, new NullEscherSerializationListener()) : 0;
        if (this._remainingData == null) {
            this._remainingData = new byte[0];
        }
        byte[] bArr2 = this._remainingData;
        System.arraycopy(bArr2, 0, bArr, i + 44 + serialize, bArr2.length);
        int length = i2 + 36 + this._remainingData.length + serialize;
        int i4 = length - i;
        escherSerializationListener.afterRecordSerialize(length, getRecordId(), i4, this);
        return i4;
    }

    public int getRecordSize() {
        EscherBlipRecord escherBlipRecord = this.field_12_blipRecord;
        int i = 0;
        int recordSize = escherBlipRecord != null ? escherBlipRecord.getRecordSize() : 0;
        byte[] bArr = this._remainingData;
        if (bArr != null) {
            i = bArr.length;
        }
        return recordSize + 44 + i;
    }

    public byte getBlipTypeWin32() {
        return this.field_1_blipTypeWin32;
    }

    public void setBlipTypeWin32(byte b) {
        this.field_1_blipTypeWin32 = b;
    }

    public byte getBlipTypeMacOS() {
        return this.field_2_blipTypeMacOS;
    }

    public void setBlipTypeMacOS(byte b) {
        this.field_2_blipTypeMacOS = b;
    }

    public byte[] getUid() {
        return this.field_3_uid;
    }

    public void setUid(byte[] bArr) {
        this.field_3_uid = bArr;
    }

    public short getTag() {
        return this.field_4_tag;
    }

    public void setTag(short s) {
        this.field_4_tag = s;
    }

    public int getSize() {
        return this.field_5_size;
    }

    public void setSize(int i) {
        this.field_5_size = i;
    }

    public int getRef() {
        return this.field_6_ref;
    }

    public void setRef(int i) {
        this.field_6_ref = i;
    }

    public int getOffset() {
        return this.field_7_offset;
    }

    public void setOffset(int i) {
        this.field_7_offset = i;
    }

    public byte getUsage() {
        return this.field_8_usage;
    }

    public void setUsage(byte b) {
        this.field_8_usage = b;
    }

    public byte getName() {
        return this.field_9_name;
    }

    public void setName(byte b) {
        this.field_9_name = b;
    }

    public byte getUnused2() {
        return this.field_10_unused2;
    }

    public void setUnused2(byte b) {
        this.field_10_unused2 = b;
    }

    public byte getUnused3() {
        return this.field_11_unused3;
    }

    public void setUnused3(byte b) {
        this.field_11_unused3 = b;
    }

    public EscherBlipRecord getBlipRecord() {
        return this.field_12_blipRecord;
    }

    public void setBlipRecord(EscherBlipRecord escherBlipRecord) {
        this.field_12_blipRecord = escherBlipRecord;
    }

    public byte[] getRemainingData() {
        return this._remainingData;
    }

    public void setRemainingData(byte[] bArr) {
        this._remainingData = bArr;
    }

    public String toString() {
        byte[] bArr = this._remainingData;
        String hex = bArr == null ? null : HexDump.toHex(bArr, 32);
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(":");
        sb.append(10);
        sb.append("  RecordId: 0x");
        sb.append(HexDump.toHex((short) RECORD_ID));
        sb.append(10);
        sb.append("  Options: 0x");
        sb.append(HexDump.toHex(getOptions()));
        sb.append(10);
        sb.append("  BlipTypeWin32: ");
        sb.append(this.field_1_blipTypeWin32);
        sb.append(10);
        sb.append("  BlipTypeMacOS: ");
        sb.append(this.field_2_blipTypeMacOS);
        sb.append(10);
        sb.append("  SUID: ");
        byte[] bArr2 = this.field_3_uid;
        sb.append(bArr2 == null ? "" : HexDump.toHex(bArr2));
        sb.append(10);
        sb.append("  Tag: ");
        sb.append(this.field_4_tag);
        sb.append(10);
        sb.append("  Size: ");
        sb.append(this.field_5_size);
        sb.append(10);
        sb.append("  Ref: ");
        sb.append(this.field_6_ref);
        sb.append(10);
        sb.append("  Offset: ");
        sb.append(this.field_7_offset);
        sb.append(10);
        sb.append("  Usage: ");
        sb.append(this.field_8_usage);
        sb.append(10);
        sb.append("  Name: ");
        sb.append(this.field_9_name);
        sb.append(10);
        sb.append("  Unused2: ");
        sb.append(this.field_10_unused2);
        sb.append(10);
        sb.append("  Unused3: ");
        sb.append(this.field_11_unused3);
        sb.append(10);
        sb.append("  blipRecord: ");
        sb.append(this.field_12_blipRecord);
        sb.append(10);
        sb.append("  Extra Data:");
        sb.append(10);
        sb.append(hex);
        return sb.toString();
    }

    public void dispose() {
        this.field_3_uid = null;
        this._remainingData = null;
        EscherBlipRecord escherBlipRecord = this.field_12_blipRecord;
        if (escherBlipRecord != null) {
            escherBlipRecord.dispose();
            this.field_12_blipRecord = null;
        }
    }
}
