package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class LineFormatRecord extends StandardRecord {
    public static final short LINE_PATTERN_DARK_GRAY_PATTERN = 6;
    public static final short LINE_PATTERN_DASH = 1;
    public static final short LINE_PATTERN_DASH_DOT = 3;
    public static final short LINE_PATTERN_DASH_DOT_DOT = 4;
    public static final short LINE_PATTERN_DOT = 2;
    public static final short LINE_PATTERN_LIGHT_GRAY_PATTERN = 8;
    public static final short LINE_PATTERN_MEDIUM_GRAY_PATTERN = 7;
    public static final short LINE_PATTERN_NONE = 5;
    public static final short LINE_PATTERN_SOLID = 0;
    public static final short WEIGHT_HAIRLINE = -1;
    public static final short WEIGHT_MEDIUM = 1;
    public static final short WEIGHT_NARROW = 0;
    public static final short WEIGHT_WIDE = 2;
    private static final BitField auto = BitFieldFactory.getInstance(1);
    private static final BitField drawTicks = BitFieldFactory.getInstance(4);
    public static final short sid = 4103;
    private static final BitField unknown = BitFieldFactory.getInstance(4);
    private int field_1_lineColor;
    private short field_2_linePattern;
    private short field_3_weight;
    private short field_4_format;
    private short field_5_colourPaletteIndex;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 12;
    }

    public short getSid() {
        return 4103;
    }

    public LineFormatRecord() {
    }

    public LineFormatRecord(RecordInputStream recordInputStream) {
        this.field_1_lineColor = recordInputStream.readInt();
        this.field_2_linePattern = recordInputStream.readShort();
        this.field_3_weight = recordInputStream.readShort();
        this.field_4_format = recordInputStream.readShort();
        this.field_5_colourPaletteIndex = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LINEFORMAT]\n");
        stringBuffer.append("    .lineColor            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLineColor()));
        stringBuffer.append(" (");
        stringBuffer.append(getLineColor());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .linePattern          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLinePattern()));
        stringBuffer.append(" (");
        stringBuffer.append(getLinePattern());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .weight               = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getWeight()));
        stringBuffer.append(" (");
        stringBuffer.append(getWeight());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .format               = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getFormat()));
        stringBuffer.append(" (");
        stringBuffer.append(getFormat());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .auto                     = ");
        stringBuffer.append(isAuto());
        stringBuffer.append(10);
        stringBuffer.append("         .drawTicks                = ");
        stringBuffer.append(isDrawTicks());
        stringBuffer.append(10);
        stringBuffer.append("         .unknown                  = ");
        stringBuffer.append(isUnknown());
        stringBuffer.append(10);
        stringBuffer.append("    .colourPaletteIndex   = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getColourPaletteIndex()));
        stringBuffer.append(" (");
        stringBuffer.append(getColourPaletteIndex());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/LINEFORMAT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this.field_1_lineColor);
        littleEndianOutput.writeShort(this.field_2_linePattern);
        littleEndianOutput.writeShort(this.field_3_weight);
        littleEndianOutput.writeShort(this.field_4_format);
        littleEndianOutput.writeShort(this.field_5_colourPaletteIndex);
    }

    public Object clone() {
        LineFormatRecord lineFormatRecord = new LineFormatRecord();
        lineFormatRecord.field_1_lineColor = this.field_1_lineColor;
        lineFormatRecord.field_2_linePattern = this.field_2_linePattern;
        lineFormatRecord.field_3_weight = this.field_3_weight;
        lineFormatRecord.field_4_format = this.field_4_format;
        lineFormatRecord.field_5_colourPaletteIndex = this.field_5_colourPaletteIndex;
        return lineFormatRecord;
    }

    public int getLineColor() {
        return this.field_1_lineColor;
    }

    public void setLineColor(int i) {
        this.field_1_lineColor = i;
    }

    public short getLinePattern() {
        return this.field_2_linePattern;
    }

    public void setLinePattern(short s) {
        this.field_2_linePattern = s;
    }

    public short getWeight() {
        return this.field_3_weight;
    }

    public void setWeight(short s) {
        this.field_3_weight = s;
    }

    public short getFormat() {
        return this.field_4_format;
    }

    public void setFormat(short s) {
        this.field_4_format = s;
    }

    public short getColourPaletteIndex() {
        return this.field_5_colourPaletteIndex;
    }

    public void setColourPaletteIndex(short s) {
        this.field_5_colourPaletteIndex = s;
    }

    public void setAuto(boolean z) {
        this.field_4_format = auto.setShortBoolean(this.field_4_format, z);
    }

    public boolean isAuto() {
        return auto.isSet(this.field_4_format);
    }

    public void setDrawTicks(boolean z) {
        this.field_4_format = drawTicks.setShortBoolean(this.field_4_format, z);
    }

    public boolean isDrawTicks() {
        return drawTicks.isSet(this.field_4_format);
    }

    public void setUnknown(boolean z) {
        this.field_4_format = unknown.setShortBoolean(this.field_4_format, z);
    }

    public boolean isUnknown() {
        return unknown.isSet(this.field_4_format);
    }
}
