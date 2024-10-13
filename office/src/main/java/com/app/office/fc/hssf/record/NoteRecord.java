package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class NoteRecord extends StandardRecord {
    private static final Byte DEFAULT_PADDING = (byte) 0;
    public static final NoteRecord[] EMPTY_ARRAY = new NoteRecord[0];
    public static final short NOTE_HIDDEN = 0;
    public static final short NOTE_VISIBLE = 2;
    public static final short sid = 28;
    private int field_1_row;
    private int field_2_col;
    private short field_3_flags;
    private int field_4_shapeid;
    private boolean field_5_hasMultibyte;
    private String field_6_author;
    private Byte field_7_padding;

    public short getSid() {
        return 28;
    }

    public NoteRecord() {
        this.field_6_author = "";
        this.field_3_flags = 0;
        this.field_7_padding = DEFAULT_PADDING;
    }

    public NoteRecord(RecordInputStream recordInputStream) {
        this.field_1_row = recordInputStream.readUShort();
        this.field_2_col = recordInputStream.readShort();
        this.field_3_flags = recordInputStream.readShort();
        this.field_4_shapeid = recordInputStream.readUShort();
        short readShort = recordInputStream.readShort();
        boolean z = recordInputStream.readByte() != 0;
        this.field_5_hasMultibyte = z;
        if (z) {
            this.field_6_author = StringUtil.readUnicodeLE(recordInputStream, readShort);
        } else {
            this.field_6_author = StringUtil.readCompressedUnicode(recordInputStream, readShort);
        }
        if (recordInputStream.available() == 1) {
            this.field_7_padding = Byte.valueOf(recordInputStream.readByte());
        }
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_row);
        littleEndianOutput.writeShort(this.field_2_col);
        littleEndianOutput.writeShort(this.field_3_flags);
        littleEndianOutput.writeShort(this.field_4_shapeid);
        littleEndianOutput.writeShort(this.field_6_author.length());
        littleEndianOutput.writeByte(this.field_5_hasMultibyte ? 1 : 0);
        if (this.field_5_hasMultibyte) {
            StringUtil.putUnicodeLE(this.field_6_author, littleEndianOutput);
        } else {
            StringUtil.putCompressedUnicode(this.field_6_author, littleEndianOutput);
        }
        Byte b = this.field_7_padding;
        if (b != null) {
            littleEndianOutput.writeByte(b.intValue());
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        int i = 1;
        int length = (this.field_6_author.length() * (this.field_5_hasMultibyte ? 2 : 1)) + 11;
        if (this.field_7_padding == null) {
            i = 0;
        }
        return length + i;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[NOTE]\n");
        stringBuffer.append("    .row    = ");
        stringBuffer.append(this.field_1_row);
        stringBuffer.append("\n");
        stringBuffer.append("    .col    = ");
        stringBuffer.append(this.field_2_col);
        stringBuffer.append("\n");
        stringBuffer.append("    .flags  = ");
        stringBuffer.append(this.field_3_flags);
        stringBuffer.append("\n");
        stringBuffer.append("    .shapeid= ");
        stringBuffer.append(this.field_4_shapeid);
        stringBuffer.append("\n");
        stringBuffer.append("    .author = ");
        stringBuffer.append(this.field_6_author);
        stringBuffer.append("\n");
        stringBuffer.append("[/NOTE]\n");
        return stringBuffer.toString();
    }

    public int getRow() {
        return this.field_1_row;
    }

    public void setRow(int i) {
        this.field_1_row = i;
    }

    public int getColumn() {
        return this.field_2_col;
    }

    public void setColumn(int i) {
        this.field_2_col = i;
    }

    public short getFlags() {
        return this.field_3_flags;
    }

    public void setFlags(short s) {
        this.field_3_flags = s;
    }

    /* access modifiers changed from: protected */
    public boolean authorIsMultibyte() {
        return this.field_5_hasMultibyte;
    }

    public int getShapeId() {
        return this.field_4_shapeid;
    }

    public void setShapeId(int i) {
        this.field_4_shapeid = i;
    }

    public String getAuthor() {
        return this.field_6_author;
    }

    public void setAuthor(String str) {
        this.field_6_author = str;
        this.field_5_hasMultibyte = StringUtil.hasMultibyte(str);
    }

    public Object clone() {
        NoteRecord noteRecord = new NoteRecord();
        noteRecord.field_1_row = this.field_1_row;
        noteRecord.field_2_col = this.field_2_col;
        noteRecord.field_3_flags = this.field_3_flags;
        noteRecord.field_4_shapeid = this.field_4_shapeid;
        noteRecord.field_6_author = this.field_6_author;
        return noteRecord;
    }
}
