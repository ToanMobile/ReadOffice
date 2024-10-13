package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;

public final class LabelRecord extends Record implements CellValueRecordInterface {
    public static final short sid = 516;
    private int field_1_row;
    private short field_2_column;
    private short field_3_xf_index;
    private short field_4_string_len;
    private byte field_5_unicode_flag;
    private String field_6_value;

    public short getSid() {
        return 516;
    }

    public void setColumn(short s) {
    }

    public void setRow(int i) {
    }

    public void setXFIndex(short s) {
    }

    public LabelRecord() {
    }

    public LabelRecord(RecordInputStream recordInputStream) {
        this.field_1_row = recordInputStream.readUShort();
        this.field_2_column = recordInputStream.readShort();
        this.field_3_xf_index = recordInputStream.readShort();
        this.field_4_string_len = recordInputStream.readShort();
        this.field_5_unicode_flag = recordInputStream.readByte();
        if (this.field_4_string_len <= 0) {
            this.field_6_value = "";
        } else if (isUnCompressedUnicode()) {
            this.field_6_value = recordInputStream.readUnicodeLEString(this.field_4_string_len);
        } else {
            this.field_6_value = recordInputStream.readCompressedUnicode(this.field_4_string_len);
        }
    }

    public int getRow() {
        return this.field_1_row;
    }

    public short getColumn() {
        return this.field_2_column;
    }

    public short getXFIndex() {
        return this.field_3_xf_index;
    }

    public short getStringLength() {
        return this.field_4_string_len;
    }

    public boolean isUnCompressedUnicode() {
        return this.field_5_unicode_flag == 1;
    }

    public String getValue() {
        return this.field_6_value;
    }

    public int serialize(int i, byte[] bArr) {
        throw new RecordFormatException("Label Records are supported READ ONLY...convert to LabelSST");
    }

    public int getRecordSize() {
        throw new RecordFormatException("Label Records are supported READ ONLY...convert to LabelSST");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LABEL]\n");
        stringBuffer.append("    .row       = ");
        stringBuffer.append(HexDump.shortToHex(getRow()));
        stringBuffer.append("\n");
        stringBuffer.append("    .column    = ");
        stringBuffer.append(HexDump.shortToHex(getColumn()));
        stringBuffer.append("\n");
        stringBuffer.append("    .xfindex   = ");
        stringBuffer.append(HexDump.shortToHex(getXFIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("    .string_len= ");
        stringBuffer.append(HexDump.shortToHex(this.field_4_string_len));
        stringBuffer.append("\n");
        stringBuffer.append("    .unicode_flag= ");
        stringBuffer.append(HexDump.byteToHex(this.field_5_unicode_flag));
        stringBuffer.append("\n");
        stringBuffer.append("    .value       = ");
        stringBuffer.append(getValue());
        stringBuffer.append("\n");
        stringBuffer.append("[/LABEL]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        LabelRecord labelRecord = new LabelRecord();
        labelRecord.field_1_row = this.field_1_row;
        labelRecord.field_2_column = this.field_2_column;
        labelRecord.field_3_xf_index = this.field_3_xf_index;
        labelRecord.field_4_string_len = this.field_4_string_len;
        labelRecord.field_5_unicode_flag = this.field_5_unicode_flag;
        labelRecord.field_6_value = this.field_6_value;
        return labelRecord;
    }
}
