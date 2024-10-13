package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class CategorySeriesAxisRecord extends StandardRecord {
    private static final BitField crossesFarRight = BitFieldFactory.getInstance(2);
    private static final BitField reversed = BitFieldFactory.getInstance(4);
    public static final short sid = 4128;
    private static final BitField valueAxisCrossing = BitFieldFactory.getInstance(1);
    private short field_1_crossingPoint;
    private short field_2_labelFrequency;
    private short field_3_tickMarkFrequency;
    private short field_4_options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return sid;
    }

    public CategorySeriesAxisRecord() {
    }

    public CategorySeriesAxisRecord(RecordInputStream recordInputStream) {
        this.field_1_crossingPoint = recordInputStream.readShort();
        this.field_2_labelFrequency = recordInputStream.readShort();
        this.field_3_tickMarkFrequency = recordInputStream.readShort();
        this.field_4_options = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CATSERRANGE]\n");
        stringBuffer.append("    .crossingPoint        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getCrossingPoint()));
        stringBuffer.append(" (");
        stringBuffer.append(getCrossingPoint());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .labelFrequency       = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLabelFrequency()));
        stringBuffer.append(" (");
        stringBuffer.append(getLabelFrequency());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .tickMarkFrequency    = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getTickMarkFrequency()));
        stringBuffer.append(" (");
        stringBuffer.append(getTickMarkFrequency());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .options              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getOptions()));
        stringBuffer.append(" (");
        stringBuffer.append(getOptions());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .valueAxisCrossing        = ");
        stringBuffer.append(isValueAxisCrossing());
        stringBuffer.append(10);
        stringBuffer.append("         .crossesFarRight          = ");
        stringBuffer.append(isCrossesFarRight());
        stringBuffer.append(10);
        stringBuffer.append("         .reversed                 = ");
        stringBuffer.append(isReversed());
        stringBuffer.append(10);
        stringBuffer.append("[/CATSERRANGE]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_crossingPoint);
        littleEndianOutput.writeShort(this.field_2_labelFrequency);
        littleEndianOutput.writeShort(this.field_3_tickMarkFrequency);
        littleEndianOutput.writeShort(this.field_4_options);
    }

    public Object clone() {
        CategorySeriesAxisRecord categorySeriesAxisRecord = new CategorySeriesAxisRecord();
        categorySeriesAxisRecord.field_1_crossingPoint = this.field_1_crossingPoint;
        categorySeriesAxisRecord.field_2_labelFrequency = this.field_2_labelFrequency;
        categorySeriesAxisRecord.field_3_tickMarkFrequency = this.field_3_tickMarkFrequency;
        categorySeriesAxisRecord.field_4_options = this.field_4_options;
        return categorySeriesAxisRecord;
    }

    public short getCrossingPoint() {
        return this.field_1_crossingPoint;
    }

    public void setCrossingPoint(short s) {
        this.field_1_crossingPoint = s;
    }

    public short getLabelFrequency() {
        return this.field_2_labelFrequency;
    }

    public void setLabelFrequency(short s) {
        this.field_2_labelFrequency = s;
    }

    public short getTickMarkFrequency() {
        return this.field_3_tickMarkFrequency;
    }

    public void setTickMarkFrequency(short s) {
        this.field_3_tickMarkFrequency = s;
    }

    public short getOptions() {
        return this.field_4_options;
    }

    public void setOptions(short s) {
        this.field_4_options = s;
    }

    public void setValueAxisCrossing(boolean z) {
        this.field_4_options = valueAxisCrossing.setShortBoolean(this.field_4_options, z);
    }

    public boolean isValueAxisCrossing() {
        return valueAxisCrossing.isSet(this.field_4_options);
    }

    public void setCrossesFarRight(boolean z) {
        this.field_4_options = crossesFarRight.setShortBoolean(this.field_4_options, z);
    }

    public boolean isCrossesFarRight() {
        return crossesFarRight.isSet(this.field_4_options);
    }

    public void setReversed(boolean z) {
        this.field_4_options = reversed.setShortBoolean(this.field_4_options, z);
    }

    public boolean isReversed() {
        return reversed.isSet(this.field_4_options);
    }
}
