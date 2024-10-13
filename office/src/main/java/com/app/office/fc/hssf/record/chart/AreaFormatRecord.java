package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class AreaFormatRecord extends StandardRecord {
    private static final BitField automatic = BitFieldFactory.getInstance(1);
    private static final BitField invert = BitFieldFactory.getInstance(2);
    public static final short sid = 4106;
    private int field_1_foregroundColor;
    private int field_2_backgroundColor;
    private short field_3_pattern;
    private short field_4_formatFlags;
    private short field_5_forecolorIndex;
    private short field_6_backcolorIndex;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 16;
    }

    public short getSid() {
        return 4106;
    }

    public AreaFormatRecord() {
    }

    public AreaFormatRecord(RecordInputStream recordInputStream) {
        this.field_1_foregroundColor = recordInputStream.readInt();
        this.field_2_backgroundColor = recordInputStream.readInt();
        this.field_3_pattern = recordInputStream.readShort();
        this.field_4_formatFlags = recordInputStream.readShort();
        this.field_5_forecolorIndex = recordInputStream.readShort();
        this.field_6_backcolorIndex = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[AREAFORMAT]\n");
        stringBuffer.append("    .foregroundColor      = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getForegroundColor()));
        stringBuffer.append(" (");
        stringBuffer.append(getForegroundColor());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .backgroundColor      = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getBackgroundColor()));
        stringBuffer.append(" (");
        stringBuffer.append(getBackgroundColor());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .pattern              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getPattern()));
        stringBuffer.append(" (");
        stringBuffer.append(getPattern());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .formatFlags          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getFormatFlags()));
        stringBuffer.append(" (");
        stringBuffer.append(getFormatFlags());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .automatic                = ");
        stringBuffer.append(isAutomatic());
        stringBuffer.append(10);
        stringBuffer.append("         .invert                   = ");
        stringBuffer.append(isInvert());
        stringBuffer.append(10);
        stringBuffer.append("    .forecolorIndex       = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getForecolorIndex()));
        stringBuffer.append(" (");
        stringBuffer.append(getForecolorIndex());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .backcolorIndex       = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getBackcolorIndex()));
        stringBuffer.append(" (");
        stringBuffer.append(getBackcolorIndex());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/AREAFORMAT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this.field_1_foregroundColor);
        littleEndianOutput.writeInt(this.field_2_backgroundColor);
        littleEndianOutput.writeShort(this.field_3_pattern);
        littleEndianOutput.writeShort(this.field_4_formatFlags);
        littleEndianOutput.writeShort(this.field_5_forecolorIndex);
        littleEndianOutput.writeShort(this.field_6_backcolorIndex);
    }

    public Object clone() {
        AreaFormatRecord areaFormatRecord = new AreaFormatRecord();
        areaFormatRecord.field_1_foregroundColor = this.field_1_foregroundColor;
        areaFormatRecord.field_2_backgroundColor = this.field_2_backgroundColor;
        areaFormatRecord.field_3_pattern = this.field_3_pattern;
        areaFormatRecord.field_4_formatFlags = this.field_4_formatFlags;
        areaFormatRecord.field_5_forecolorIndex = this.field_5_forecolorIndex;
        areaFormatRecord.field_6_backcolorIndex = this.field_6_backcolorIndex;
        return areaFormatRecord;
    }

    public int getForegroundColor() {
        return this.field_1_foregroundColor;
    }

    public void setForegroundColor(int i) {
        this.field_1_foregroundColor = i;
    }

    public int getBackgroundColor() {
        return this.field_2_backgroundColor;
    }

    public void setBackgroundColor(int i) {
        this.field_2_backgroundColor = i;
    }

    public short getPattern() {
        return this.field_3_pattern;
    }

    public void setPattern(short s) {
        this.field_3_pattern = s;
    }

    public short getFormatFlags() {
        return this.field_4_formatFlags;
    }

    public void setFormatFlags(short s) {
        this.field_4_formatFlags = s;
    }

    public short getForecolorIndex() {
        return this.field_5_forecolorIndex;
    }

    public void setForecolorIndex(short s) {
        this.field_5_forecolorIndex = s;
    }

    public short getBackcolorIndex() {
        return this.field_6_backcolorIndex;
    }

    public void setBackcolorIndex(short s) {
        this.field_6_backcolorIndex = s;
    }

    public void setAutomatic(boolean z) {
        this.field_4_formatFlags = automatic.setShortBoolean(this.field_4_formatFlags, z);
    }

    public boolean isAutomatic() {
        return automatic.isSet(this.field_4_formatFlags);
    }

    public void setInvert(boolean z) {
        this.field_4_formatFlags = invert.setShortBoolean(this.field_4_formatFlags, z);
    }

    public boolean isInvert() {
        return invert.isSet(this.field_4_formatFlags);
    }
}
