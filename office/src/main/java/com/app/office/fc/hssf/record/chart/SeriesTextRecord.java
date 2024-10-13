package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class SeriesTextRecord extends StandardRecord {
    private static final int MAX_LEN = 255;
    public static final short sid = 4109;
    private int field_1_id;
    private String field_4_text;
    private boolean is16bit;

    public short getSid() {
        return 4109;
    }

    public SeriesTextRecord() {
        this.field_4_text = "";
        this.is16bit = false;
    }

    public SeriesTextRecord(RecordInputStream recordInputStream) {
        this.field_1_id = recordInputStream.readUShort();
        int readUByte = recordInputStream.readUByte();
        boolean z = (recordInputStream.readUByte() & 1) == 0 ? false : true;
        this.is16bit = z;
        if (z) {
            this.field_4_text = recordInputStream.readUnicodeLEString(readUByte);
        } else {
            this.field_4_text = recordInputStream.readCompressedUnicode(readUByte);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SERIESTEXT]\n");
        stringBuffer.append("  .id     =");
        stringBuffer.append(HexDump.shortToHex(getId()));
        stringBuffer.append(10);
        stringBuffer.append("  .textLen=");
        stringBuffer.append(this.field_4_text.length());
        stringBuffer.append(10);
        stringBuffer.append("  .is16bit=");
        stringBuffer.append(this.is16bit);
        stringBuffer.append(10);
        stringBuffer.append("  .text   =");
        stringBuffer.append(" (");
        stringBuffer.append(getText());
        stringBuffer.append(" )");
        stringBuffer.append(10);
        stringBuffer.append("[/SERIESTEXT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_id);
        littleEndianOutput.writeByte(this.field_4_text.length());
        if (this.is16bit) {
            littleEndianOutput.writeByte(1);
            StringUtil.putUnicodeLE(this.field_4_text, littleEndianOutput);
            return;
        }
        littleEndianOutput.writeByte(0);
        StringUtil.putCompressedUnicode(this.field_4_text, littleEndianOutput);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this.field_4_text.length() * (this.is16bit ? 2 : 1)) + 4;
    }

    public Object clone() {
        SeriesTextRecord seriesTextRecord = new SeriesTextRecord();
        seriesTextRecord.field_1_id = this.field_1_id;
        seriesTextRecord.is16bit = this.is16bit;
        seriesTextRecord.field_4_text = this.field_4_text;
        return seriesTextRecord;
    }

    public int getId() {
        return this.field_1_id;
    }

    public void setId(int i) {
        this.field_1_id = i;
    }

    public String getText() {
        return this.field_4_text;
    }

    public void setText(String str) {
        if (str.length() <= 255) {
            this.field_4_text = str;
            this.is16bit = StringUtil.hasMultibyte(str);
            return;
        }
        throw new IllegalArgumentException("Text is too long (" + str.length() + ">" + 255 + ")");
    }
}
