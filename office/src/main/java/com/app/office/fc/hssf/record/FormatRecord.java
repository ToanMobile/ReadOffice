package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class FormatRecord extends StandardRecord {
    public static final short sid = 1054;
    private final int field_1_index_code;
    private final boolean field_3_hasMultibyte;
    private final String field_4_formatstring;

    public Object clone() {
        return this;
    }

    public short getSid() {
        return sid;
    }

    public FormatRecord(int i, String str) {
        this.field_1_index_code = i;
        this.field_4_formatstring = str;
        this.field_3_hasMultibyte = StringUtil.hasMultibyte(str);
    }

    public FormatRecord(RecordInputStream recordInputStream) {
        this.field_1_index_code = recordInputStream.readShort();
        int readUShort = recordInputStream.readUShort();
        boolean z = (recordInputStream.readByte() & 1) == 0 ? false : true;
        this.field_3_hasMultibyte = z;
        if (z) {
            this.field_4_formatstring = recordInputStream.readUnicodeLEString(readUShort);
        } else {
            this.field_4_formatstring = recordInputStream.readCompressedUnicode(readUShort);
        }
    }

    public int getIndexCode() {
        return this.field_1_index_code;
    }

    public String getFormatString() {
        return this.field_4_formatstring;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FORMAT]\n");
        stringBuffer.append("    .indexcode       = ");
        stringBuffer.append(HexDump.shortToHex(getIndexCode()));
        stringBuffer.append("\n");
        stringBuffer.append("    .isUnicode       = ");
        stringBuffer.append(this.field_3_hasMultibyte);
        stringBuffer.append("\n");
        stringBuffer.append("    .formatstring    = ");
        stringBuffer.append(getFormatString());
        stringBuffer.append("\n");
        stringBuffer.append("[/FORMAT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        String formatString = getFormatString();
        littleEndianOutput.writeShort(getIndexCode());
        littleEndianOutput.writeShort(formatString.length());
        littleEndianOutput.writeByte(this.field_3_hasMultibyte ? 1 : 0);
        if (this.field_3_hasMultibyte) {
            StringUtil.putUnicodeLE(formatString, littleEndianOutput);
        } else {
            StringUtil.putCompressedUnicode(formatString, littleEndianOutput);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (getFormatString().length() * (this.field_3_hasMultibyte ? 2 : 1)) + 5;
    }
}
