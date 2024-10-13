package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class TickRecord extends StandardRecord {
    private static final BitField autoTextBackground = BitFieldFactory.getInstance(2);
    private static final BitField autoTextColor = BitFieldFactory.getInstance(1);
    private static final BitField autorotate = BitFieldFactory.getInstance(32);
    private static final BitField rotation = BitFieldFactory.getInstance(28);
    public static final short sid = 4126;
    private short field_10_options;
    private short field_11_tickColor;
    private short field_12_zero5;
    private byte field_1_majorTickType;
    private byte field_2_minorTickType;
    private byte field_3_labelPosition;
    private byte field_4_background;
    private int field_5_labelColorRgb;
    private int field_6_zero1;
    private int field_7_zero2;
    private int field_8_zero3;
    private int field_9_zero4;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 30;
    }

    public short getSid() {
        return sid;
    }

    public TickRecord() {
    }

    public TickRecord(RecordInputStream recordInputStream) {
        this.field_1_majorTickType = recordInputStream.readByte();
        this.field_2_minorTickType = recordInputStream.readByte();
        this.field_3_labelPosition = recordInputStream.readByte();
        this.field_4_background = recordInputStream.readByte();
        this.field_5_labelColorRgb = recordInputStream.readInt();
        this.field_6_zero1 = recordInputStream.readInt();
        this.field_7_zero2 = recordInputStream.readInt();
        this.field_8_zero3 = recordInputStream.readInt();
        this.field_9_zero4 = recordInputStream.readInt();
        this.field_10_options = recordInputStream.readShort();
        this.field_11_tickColor = recordInputStream.readShort();
        this.field_12_zero5 = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TICK]\n");
        stringBuffer.append("    .majorTickType        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMajorTickType()));
        stringBuffer.append(" (");
        stringBuffer.append(getMajorTickType());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .minorTickType        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMinorTickType()));
        stringBuffer.append(" (");
        stringBuffer.append(getMinorTickType());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .labelPosition        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLabelPosition()));
        stringBuffer.append(" (");
        stringBuffer.append(getLabelPosition());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .background           = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getBackground()));
        stringBuffer.append(" (");
        stringBuffer.append(getBackground());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .labelColorRgb        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLabelColorRgb()));
        stringBuffer.append(" (");
        stringBuffer.append(getLabelColorRgb());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .zero1                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getZero1()));
        stringBuffer.append(" (");
        stringBuffer.append(getZero1());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .zero2                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getZero2()));
        stringBuffer.append(" (");
        stringBuffer.append(getZero2());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .options              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getOptions()));
        stringBuffer.append(" (");
        stringBuffer.append(getOptions());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .autoTextColor            = ");
        stringBuffer.append(isAutoTextColor());
        stringBuffer.append(10);
        stringBuffer.append("         .autoTextBackground       = ");
        stringBuffer.append(isAutoTextBackground());
        stringBuffer.append(10);
        stringBuffer.append("         .rotation                 = ");
        stringBuffer.append(getRotation());
        stringBuffer.append(10);
        stringBuffer.append("         .autorotate               = ");
        stringBuffer.append(isAutorotate());
        stringBuffer.append(10);
        stringBuffer.append("    .tickColor            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getTickColor()));
        stringBuffer.append(" (");
        stringBuffer.append(getTickColor());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .zero3                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getZero3()));
        stringBuffer.append(" (");
        stringBuffer.append(getZero3());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/TICK]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(this.field_1_majorTickType);
        littleEndianOutput.writeByte(this.field_2_minorTickType);
        littleEndianOutput.writeByte(this.field_3_labelPosition);
        littleEndianOutput.writeByte(this.field_4_background);
        littleEndianOutput.writeInt(this.field_5_labelColorRgb);
        littleEndianOutput.writeInt(this.field_6_zero1);
        littleEndianOutput.writeInt(this.field_7_zero2);
        littleEndianOutput.writeInt(this.field_8_zero3);
        littleEndianOutput.writeInt(this.field_9_zero4);
        littleEndianOutput.writeShort(this.field_10_options);
        littleEndianOutput.writeShort(this.field_11_tickColor);
        littleEndianOutput.writeShort(this.field_12_zero5);
    }

    public Object clone() {
        TickRecord tickRecord = new TickRecord();
        tickRecord.field_1_majorTickType = this.field_1_majorTickType;
        tickRecord.field_2_minorTickType = this.field_2_minorTickType;
        tickRecord.field_3_labelPosition = this.field_3_labelPosition;
        tickRecord.field_4_background = this.field_4_background;
        tickRecord.field_5_labelColorRgb = this.field_5_labelColorRgb;
        tickRecord.field_6_zero1 = this.field_6_zero1;
        tickRecord.field_7_zero2 = this.field_7_zero2;
        tickRecord.field_8_zero3 = this.field_8_zero3;
        tickRecord.field_9_zero4 = this.field_9_zero4;
        tickRecord.field_10_options = this.field_10_options;
        tickRecord.field_11_tickColor = this.field_11_tickColor;
        tickRecord.field_12_zero5 = this.field_12_zero5;
        return tickRecord;
    }

    public byte getMajorTickType() {
        return this.field_1_majorTickType;
    }

    public void setMajorTickType(byte b) {
        this.field_1_majorTickType = b;
    }

    public byte getMinorTickType() {
        return this.field_2_minorTickType;
    }

    public void setMinorTickType(byte b) {
        this.field_2_minorTickType = b;
    }

    public byte getLabelPosition() {
        return this.field_3_labelPosition;
    }

    public void setLabelPosition(byte b) {
        this.field_3_labelPosition = b;
    }

    public byte getBackground() {
        return this.field_4_background;
    }

    public void setBackground(byte b) {
        this.field_4_background = b;
    }

    public int getLabelColorRgb() {
        return this.field_5_labelColorRgb;
    }

    public void setLabelColorRgb(int i) {
        this.field_5_labelColorRgb = i;
    }

    public int getZero1() {
        return this.field_6_zero1;
    }

    public void setZero1(int i) {
        this.field_6_zero1 = i;
    }

    public int getZero2() {
        return this.field_7_zero2;
    }

    public void setZero2(int i) {
        this.field_7_zero2 = i;
    }

    public short getOptions() {
        return this.field_10_options;
    }

    public void setOptions(short s) {
        this.field_10_options = s;
    }

    public short getTickColor() {
        return this.field_11_tickColor;
    }

    public void setTickColor(short s) {
        this.field_11_tickColor = s;
    }

    public short getZero3() {
        return this.field_12_zero5;
    }

    public void setZero3(short s) {
        this.field_12_zero5 = s;
    }

    public void setAutoTextColor(boolean z) {
        this.field_10_options = autoTextColor.setShortBoolean(this.field_10_options, z);
    }

    public boolean isAutoTextColor() {
        return autoTextColor.isSet(this.field_10_options);
    }

    public void setAutoTextBackground(boolean z) {
        this.field_10_options = autoTextBackground.setShortBoolean(this.field_10_options, z);
    }

    public boolean isAutoTextBackground() {
        return autoTextBackground.isSet(this.field_10_options);
    }

    public void setRotation(short s) {
        this.field_10_options = rotation.setShortValue(this.field_10_options, s);
    }

    public short getRotation() {
        return rotation.getShortValue(this.field_10_options);
    }

    public void setAutorotate(boolean z) {
        this.field_10_options = autorotate.setShortBoolean(this.field_10_options, z);
    }

    public boolean isAutorotate() {
        return autorotate.isSet(this.field_10_options);
    }
}
