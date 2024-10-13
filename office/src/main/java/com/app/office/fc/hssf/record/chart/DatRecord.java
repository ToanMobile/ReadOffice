package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class DatRecord extends StandardRecord {
    private static final BitField border = BitFieldFactory.getInstance(4);
    private static final BitField horizontalBorder = BitFieldFactory.getInstance(1);
    private static final BitField showSeriesKey = BitFieldFactory.getInstance(8);
    public static final short sid = 4195;
    private static final BitField verticalBorder = BitFieldFactory.getInstance(2);
    private short field_1_options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public DatRecord() {
    }

    public DatRecord(RecordInputStream recordInputStream) {
        this.field_1_options = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[DAT]\n");
        stringBuffer.append("    .options              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getOptions()));
        stringBuffer.append(" (");
        stringBuffer.append(getOptions());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .horizontalBorder         = ");
        stringBuffer.append(isHorizontalBorder());
        stringBuffer.append(10);
        stringBuffer.append("         .verticalBorder           = ");
        stringBuffer.append(isVerticalBorder());
        stringBuffer.append(10);
        stringBuffer.append("         .border                   = ");
        stringBuffer.append(isBorder());
        stringBuffer.append(10);
        stringBuffer.append("         .showSeriesKey            = ");
        stringBuffer.append(isShowSeriesKey());
        stringBuffer.append(10);
        stringBuffer.append("[/DAT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_options);
    }

    public Object clone() {
        DatRecord datRecord = new DatRecord();
        datRecord.field_1_options = this.field_1_options;
        return datRecord;
    }

    public short getOptions() {
        return this.field_1_options;
    }

    public void setOptions(short s) {
        this.field_1_options = s;
    }

    public void setHorizontalBorder(boolean z) {
        this.field_1_options = horizontalBorder.setShortBoolean(this.field_1_options, z);
    }

    public boolean isHorizontalBorder() {
        return horizontalBorder.isSet(this.field_1_options);
    }

    public void setVerticalBorder(boolean z) {
        this.field_1_options = verticalBorder.setShortBoolean(this.field_1_options, z);
    }

    public boolean isVerticalBorder() {
        return verticalBorder.isSet(this.field_1_options);
    }

    public void setBorder(boolean z) {
        this.field_1_options = border.setShortBoolean(this.field_1_options, z);
    }

    public boolean isBorder() {
        return border.isSet(this.field_1_options);
    }

    public void setShowSeriesKey(boolean z) {
        this.field_1_options = showSeriesKey.setShortBoolean(this.field_1_options, z);
    }

    public boolean isShowSeriesKey() {
        return showSeriesKey.isSet(this.field_1_options);
    }
}
