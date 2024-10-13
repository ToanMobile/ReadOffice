package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class FrameRecord extends StandardRecord {
    public static final short BORDER_TYPE_REGULAR = 0;
    public static final short BORDER_TYPE_SHADOW = 1;
    private static final BitField autoPosition = BitFieldFactory.getInstance(2);
    private static final BitField autoSize = BitFieldFactory.getInstance(1);
    public static final short sid = 4146;
    private short field_1_borderType;
    private short field_2_options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 4;
    }

    public short getSid() {
        return sid;
    }

    public FrameRecord() {
    }

    public FrameRecord(RecordInputStream recordInputStream) {
        this.field_1_borderType = recordInputStream.readShort();
        this.field_2_options = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FRAME]\n");
        stringBuffer.append("    .borderType           = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getBorderType()));
        stringBuffer.append(" (");
        stringBuffer.append(getBorderType());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .options              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getOptions()));
        stringBuffer.append(" (");
        stringBuffer.append(getOptions());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .autoSize                 = ");
        stringBuffer.append(isAutoSize());
        stringBuffer.append(10);
        stringBuffer.append("         .autoPosition             = ");
        stringBuffer.append(isAutoPosition());
        stringBuffer.append(10);
        stringBuffer.append("[/FRAME]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_borderType);
        littleEndianOutput.writeShort(this.field_2_options);
    }

    public Object clone() {
        FrameRecord frameRecord = new FrameRecord();
        frameRecord.field_1_borderType = this.field_1_borderType;
        frameRecord.field_2_options = this.field_2_options;
        return frameRecord;
    }

    public short getBorderType() {
        return this.field_1_borderType;
    }

    public void setBorderType(short s) {
        this.field_1_borderType = s;
    }

    public short getOptions() {
        return this.field_2_options;
    }

    public void setOptions(short s) {
        this.field_2_options = s;
    }

    public void setAutoSize(boolean z) {
        this.field_2_options = autoSize.setShortBoolean(this.field_2_options, z);
    }

    public boolean isAutoSize() {
        return autoSize.isSet(this.field_2_options);
    }

    public void setAutoPosition(boolean z) {
        this.field_2_options = autoPosition.setShortBoolean(this.field_2_options, z);
    }

    public boolean isAutoPosition() {
        return autoPosition.isSet(this.field_2_options);
    }
}
