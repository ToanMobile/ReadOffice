package com.app.office.fc.hssf.record;

import com.itextpdf.text.pdf.PdfBoolean;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class FileSharingRecord extends StandardRecord {
    public static final short sid = 91;
    private short field_1_readonly;
    private short field_2_password;
    private byte field_3_username_unicode_options;
    private String field_3_username_value;

    public short getSid() {
        return 91;
    }

    public FileSharingRecord() {
    }

    public FileSharingRecord(RecordInputStream recordInputStream) {
        this.field_1_readonly = recordInputStream.readShort();
        this.field_2_password = recordInputStream.readShort();
        short readShort = recordInputStream.readShort();
        if (readShort > 0) {
            this.field_3_username_unicode_options = recordInputStream.readByte();
            this.field_3_username_value = recordInputStream.readCompressedUnicode(readShort);
            return;
        }
        this.field_3_username_value = "";
    }

    public static short hashPassword(String str) {
        byte[] bytes = str.getBytes();
        byte b = 0;
        if (bytes.length > 0) {
            int length = bytes.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                b = bytes[i] ^ (((b >> 14) & 1) | ((b << 1) & 32767));
                length = i;
            }
            b = (bytes.length ^ (((b >> 14) & 1) | ((b << 1) & 32767))) ^ Field.MERGESEQ;
        }
        return (short) b;
    }

    public void setReadOnly(short s) {
        this.field_1_readonly = s;
    }

    public short getReadOnly() {
        return this.field_1_readonly;
    }

    public void setPassword(short s) {
        this.field_2_password = s;
    }

    public short getPassword() {
        return this.field_2_password;
    }

    public String getUsername() {
        return this.field_3_username_value;
    }

    public void setUsername(String str) {
        this.field_3_username_value = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FILESHARING]\n");
        stringBuffer.append("    .readonly       = ");
        stringBuffer.append(getReadOnly() == 1 ? PdfBoolean.TRUE : PdfBoolean.FALSE);
        stringBuffer.append("\n");
        stringBuffer.append("    .password       = ");
        stringBuffer.append(Integer.toHexString(getPassword()));
        stringBuffer.append("\n");
        stringBuffer.append("    .username       = ");
        stringBuffer.append(getUsername());
        stringBuffer.append("\n");
        stringBuffer.append("[/FILESHARING]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getReadOnly());
        littleEndianOutput.writeShort(getPassword());
        littleEndianOutput.writeShort(this.field_3_username_value.length());
        if (this.field_3_username_value.length() > 0) {
            littleEndianOutput.writeByte(this.field_3_username_unicode_options);
            StringUtil.putCompressedUnicode(getUsername(), littleEndianOutput);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        int length = this.field_3_username_value.length();
        if (length < 1) {
            return 6;
        }
        return length + 7;
    }

    public Object clone() {
        FileSharingRecord fileSharingRecord = new FileSharingRecord();
        fileSharingRecord.setReadOnly(this.field_1_readonly);
        fileSharingRecord.setPassword(this.field_2_password);
        fileSharingRecord.setUsername(this.field_3_username_value);
        return fileSharingRecord;
    }
}
