package com.app.office.fc.hssf.record;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;
import java.util.Arrays;

public final class WriteAccessRecord extends StandardRecord {
    private static final int DATA_SIZE = 112;
    private static final byte[] PADDING;
    private static final byte PAD_CHAR = 32;
    public static final short sid = 92;
    private String field_1_username;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 112;
    }

    public short getSid() {
        return 92;
    }

    static {
        byte[] bArr = new byte[112];
        PADDING = bArr;
        Arrays.fill(bArr, (byte) 32);
    }

    public WriteAccessRecord() {
        setUsername("");
    }

    public WriteAccessRecord(RecordInputStream recordInputStream) {
        String str;
        int readUShort = recordInputStream.readUShort();
        int readUByte = recordInputStream.readUByte();
        if (readUShort > 112 || (readUByte & TIFFConstants.TIFFTAG_SUBFILETYPE) != 0) {
            int remaining = recordInputStream.remaining() + 3;
            byte[] bArr = new byte[remaining];
            LittleEndian.putUShort(bArr, 0, readUShort);
            LittleEndian.putByte(bArr, 2, readUByte);
            recordInputStream.readFully(bArr, 3, remaining - 3);
            setUsername(new String(bArr).trim());
            return;
        }
        if ((readUByte & 1) == 0) {
            str = StringUtil.readCompressedUnicode(recordInputStream, readUShort);
        } else {
            str = StringUtil.readUnicodeLE(recordInputStream, readUShort);
        }
        this.field_1_username = str.trim();
        for (int remaining2 = recordInputStream.remaining(); remaining2 > 0; remaining2--) {
            recordInputStream.readUByte();
        }
    }

    public void setUsername(String str) {
        if (112 - ((str.length() * (StringUtil.hasMultibyte(str) ? 2 : 1)) + 3) >= 0) {
            this.field_1_username = str;
            return;
        }
        throw new IllegalArgumentException("Name is too long: " + str);
    }

    public String getUsername() {
        return this.field_1_username;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[WRITEACCESS]\n");
        stringBuffer.append("    .name = ");
        stringBuffer.append(this.field_1_username.toString());
        stringBuffer.append("\n");
        stringBuffer.append("[/WRITEACCESS]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        String username = getUsername();
        boolean hasMultibyte = StringUtil.hasMultibyte(username);
        littleEndianOutput.writeShort(username.length());
        littleEndianOutput.writeByte(hasMultibyte ? 1 : 0);
        if (hasMultibyte) {
            StringUtil.putUnicodeLE(username, littleEndianOutput);
        } else {
            StringUtil.putCompressedUnicode(username, littleEndianOutput);
        }
        littleEndianOutput.write(PADDING, 0, 112 - ((username.length() * (hasMultibyte ? 2 : 1)) + 3));
    }
}
