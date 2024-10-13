package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class LegendRecord extends StandardRecord {
    public static final byte SPACING_CLOSE = 0;
    public static final byte SPACING_MEDIUM = 1;
    public static final byte SPACING_OPEN = 2;
    public static final byte TYPE_BOTTOM = 0;
    public static final byte TYPE_CORNER = 1;
    public static final byte TYPE_LEFT = 4;
    public static final byte TYPE_RIGHT = 3;
    public static final byte TYPE_TOP = 2;
    public static final byte TYPE_UNDOCKED = 7;
    private static final BitField autoPosition = BitFieldFactory.getInstance(1);
    private static final BitField autoSeries = BitFieldFactory.getInstance(2);
    private static final BitField autoXPositioning = BitFieldFactory.getInstance(4);
    private static final BitField autoYPositioning = BitFieldFactory.getInstance(8);
    private static final BitField dataTable = BitFieldFactory.getInstance(32);
    public static final short sid = 4117;
    private static final BitField vertical = BitFieldFactory.getInstance(16);
    private int field_1_xAxisUpperLeft;
    private int field_2_yAxisUpperLeft;
    private int field_3_xSize;
    private int field_4_ySize;
    private byte field_5_type;
    private byte field_6_spacing;
    private short field_7_options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 20;
    }

    public short getSid() {
        return sid;
    }

    public LegendRecord() {
    }

    public LegendRecord(RecordInputStream recordInputStream) {
        this.field_1_xAxisUpperLeft = recordInputStream.readInt();
        this.field_2_yAxisUpperLeft = recordInputStream.readInt();
        this.field_3_xSize = recordInputStream.readInt();
        this.field_4_ySize = recordInputStream.readInt();
        this.field_5_type = recordInputStream.readByte();
        this.field_6_spacing = recordInputStream.readByte();
        this.field_7_options = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[LEGEND]\n");
        stringBuffer.append("    .xAxisUpperLeft       = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getXAxisUpperLeft()));
        stringBuffer.append(" (");
        stringBuffer.append(getXAxisUpperLeft());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .yAxisUpperLeft       = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getYAxisUpperLeft()));
        stringBuffer.append(" (");
        stringBuffer.append(getYAxisUpperLeft());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .xSize                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getXSize()));
        stringBuffer.append(" (");
        stringBuffer.append(getXSize());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .ySize                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getYSize()));
        stringBuffer.append(" (");
        stringBuffer.append(getYSize());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .type                 = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getType()));
        stringBuffer.append(" (");
        stringBuffer.append(getType());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .spacing              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getSpacing()));
        stringBuffer.append(" (");
        stringBuffer.append(getSpacing());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .options              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getOptions()));
        stringBuffer.append(" (");
        stringBuffer.append(getOptions());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .autoPosition             = ");
        stringBuffer.append(isAutoPosition());
        stringBuffer.append(10);
        stringBuffer.append("         .autoSeries               = ");
        stringBuffer.append(isAutoSeries());
        stringBuffer.append(10);
        stringBuffer.append("         .autoXPositioning         = ");
        stringBuffer.append(isAutoXPositioning());
        stringBuffer.append(10);
        stringBuffer.append("         .autoYPositioning         = ");
        stringBuffer.append(isAutoYPositioning());
        stringBuffer.append(10);
        stringBuffer.append("         .vertical                 = ");
        stringBuffer.append(isVertical());
        stringBuffer.append(10);
        stringBuffer.append("         .dataTable                = ");
        stringBuffer.append(isDataTable());
        stringBuffer.append(10);
        stringBuffer.append("[/LEGEND]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this.field_1_xAxisUpperLeft);
        littleEndianOutput.writeInt(this.field_2_yAxisUpperLeft);
        littleEndianOutput.writeInt(this.field_3_xSize);
        littleEndianOutput.writeInt(this.field_4_ySize);
        littleEndianOutput.writeByte(this.field_5_type);
        littleEndianOutput.writeByte(this.field_6_spacing);
        littleEndianOutput.writeShort(this.field_7_options);
    }

    public Object clone() {
        LegendRecord legendRecord = new LegendRecord();
        legendRecord.field_1_xAxisUpperLeft = this.field_1_xAxisUpperLeft;
        legendRecord.field_2_yAxisUpperLeft = this.field_2_yAxisUpperLeft;
        legendRecord.field_3_xSize = this.field_3_xSize;
        legendRecord.field_4_ySize = this.field_4_ySize;
        legendRecord.field_5_type = this.field_5_type;
        legendRecord.field_6_spacing = this.field_6_spacing;
        legendRecord.field_7_options = this.field_7_options;
        return legendRecord;
    }

    public int getXAxisUpperLeft() {
        return this.field_1_xAxisUpperLeft;
    }

    public void setXAxisUpperLeft(int i) {
        this.field_1_xAxisUpperLeft = i;
    }

    public int getYAxisUpperLeft() {
        return this.field_2_yAxisUpperLeft;
    }

    public void setYAxisUpperLeft(int i) {
        this.field_2_yAxisUpperLeft = i;
    }

    public int getXSize() {
        return this.field_3_xSize;
    }

    public void setXSize(int i) {
        this.field_3_xSize = i;
    }

    public int getYSize() {
        return this.field_4_ySize;
    }

    public void setYSize(int i) {
        this.field_4_ySize = i;
    }

    public byte getType() {
        return this.field_5_type;
    }

    public void setType(byte b) {
        this.field_5_type = b;
    }

    public byte getSpacing() {
        return this.field_6_spacing;
    }

    public void setSpacing(byte b) {
        this.field_6_spacing = b;
    }

    public short getOptions() {
        return this.field_7_options;
    }

    public void setOptions(short s) {
        this.field_7_options = s;
    }

    public void setAutoPosition(boolean z) {
        this.field_7_options = autoPosition.setShortBoolean(this.field_7_options, z);
    }

    public boolean isAutoPosition() {
        return autoPosition.isSet(this.field_7_options);
    }

    public void setAutoSeries(boolean z) {
        this.field_7_options = autoSeries.setShortBoolean(this.field_7_options, z);
    }

    public boolean isAutoSeries() {
        return autoSeries.isSet(this.field_7_options);
    }

    public void setAutoXPositioning(boolean z) {
        this.field_7_options = autoXPositioning.setShortBoolean(this.field_7_options, z);
    }

    public boolean isAutoXPositioning() {
        return autoXPositioning.isSet(this.field_7_options);
    }

    public void setAutoYPositioning(boolean z) {
        this.field_7_options = autoYPositioning.setShortBoolean(this.field_7_options, z);
    }

    public boolean isAutoYPositioning() {
        return autoYPositioning.isSet(this.field_7_options);
    }

    public void setVertical(boolean z) {
        this.field_7_options = vertical.setShortBoolean(this.field_7_options, z);
    }

    public boolean isVertical() {
        return vertical.isSet(this.field_7_options);
    }

    public void setDataTable(boolean z) {
        this.field_7_options = dataTable.setShortBoolean(this.field_7_options, z);
    }

    public boolean isDataTable() {
        return dataTable.isSet(this.field_7_options);
    }
}
