package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class NameCommentRecord extends StandardRecord {
    public static final short sid = 2196;
    private final short field_1_record_type;
    private final short field_2_frt_cell_ref_flag;
    private final long field_3_reserved;
    private String field_6_name_text;
    private String field_7_comment_text;

    public short getSid() {
        return sid;
    }

    public NameCommentRecord(String str, String str2) {
        this.field_1_record_type = 0;
        this.field_2_frt_cell_ref_flag = 0;
        this.field_3_reserved = 0;
        this.field_6_name_text = str;
        this.field_7_comment_text = str2;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        int length = this.field_6_name_text.length();
        int length2 = this.field_7_comment_text.length();
        littleEndianOutput.writeShort(this.field_1_record_type);
        littleEndianOutput.writeShort(this.field_2_frt_cell_ref_flag);
        littleEndianOutput.writeLong(this.field_3_reserved);
        littleEndianOutput.writeShort(length);
        littleEndianOutput.writeShort(length2);
        littleEndianOutput.writeByte(0);
        StringUtil.putCompressedUnicode(this.field_6_name_text, littleEndianOutput);
        littleEndianOutput.writeByte(0);
        StringUtil.putCompressedUnicode(this.field_7_comment_text, littleEndianOutput);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this.field_6_name_text.length() + 18 + this.field_7_comment_text.length();
    }

    public NameCommentRecord(RecordInputStream recordInputStream) {
        this.field_1_record_type = recordInputStream.readShort();
        this.field_2_frt_cell_ref_flag = recordInputStream.readShort();
        this.field_3_reserved = recordInputStream.readLong();
        short readShort = recordInputStream.readShort();
        short readShort2 = recordInputStream.readShort();
        recordInputStream.readByte();
        this.field_6_name_text = StringUtil.readCompressedUnicode(recordInputStream, readShort);
        recordInputStream.readByte();
        this.field_7_comment_text = StringUtil.readCompressedUnicode(recordInputStream, readShort2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[NAMECMT]\n");
        stringBuffer.append("    .record type            = ");
        stringBuffer.append(HexDump.shortToHex(this.field_1_record_type));
        stringBuffer.append("\n");
        stringBuffer.append("    .frt cell ref flag      = ");
        stringBuffer.append(HexDump.byteToHex(this.field_2_frt_cell_ref_flag));
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved               = ");
        stringBuffer.append(this.field_3_reserved);
        stringBuffer.append("\n");
        stringBuffer.append("    .name length            = ");
        stringBuffer.append(this.field_6_name_text.length());
        stringBuffer.append("\n");
        stringBuffer.append("    .comment length         = ");
        stringBuffer.append(this.field_7_comment_text.length());
        stringBuffer.append("\n");
        stringBuffer.append("    .name                   = ");
        stringBuffer.append(this.field_6_name_text);
        stringBuffer.append("\n");
        stringBuffer.append("    .comment                = ");
        stringBuffer.append(this.field_7_comment_text);
        stringBuffer.append("\n");
        stringBuffer.append("[/NAMECMT]\n");
        return stringBuffer.toString();
    }

    public String getNameText() {
        return this.field_6_name_text;
    }

    public void setNameText(String str) {
        this.field_6_name_text = str;
    }

    public String getCommentText() {
        return this.field_7_comment_text;
    }

    public void setCommentText(String str) {
        this.field_7_comment_text = str;
    }

    public short getRecordType() {
        return this.field_1_record_type;
    }
}
