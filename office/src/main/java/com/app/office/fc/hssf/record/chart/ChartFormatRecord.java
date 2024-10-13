package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class ChartFormatRecord extends StandardRecord {
    public static final short sid = 4116;
    private static final BitField varyDisplayPattern = BitFieldFactory.getInstance(1);
    private int field1_x_position;
    private int field2_y_position;
    private int field3_width;
    private int field4_height;
    private int field5_grbit;
    private int field6_unknown;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 20;
    }

    public short getSid() {
        return sid;
    }

    public ChartFormatRecord() {
    }

    public ChartFormatRecord(RecordInputStream recordInputStream) {
        this.field1_x_position = recordInputStream.readInt();
        this.field2_y_position = recordInputStream.readInt();
        this.field3_width = recordInputStream.readInt();
        this.field4_height = recordInputStream.readInt();
        this.field5_grbit = recordInputStream.readUShort();
        this.field6_unknown = recordInputStream.readUShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CHARTFORMAT]\n");
        stringBuffer.append("    .xPosition       = ");
        stringBuffer.append(getXPosition());
        stringBuffer.append("\n");
        stringBuffer.append("    .yPosition       = ");
        stringBuffer.append(getYPosition());
        stringBuffer.append("\n");
        stringBuffer.append("    .width           = ");
        stringBuffer.append(getWidth());
        stringBuffer.append("\n");
        stringBuffer.append("    .height          = ");
        stringBuffer.append(getHeight());
        stringBuffer.append("\n");
        stringBuffer.append("    .grBit           = ");
        stringBuffer.append(HexDump.intToHex(this.field5_grbit));
        stringBuffer.append("\n");
        stringBuffer.append("[/CHARTFORMAT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(getXPosition());
        littleEndianOutput.writeInt(getYPosition());
        littleEndianOutput.writeInt(getWidth());
        littleEndianOutput.writeInt(getHeight());
        littleEndianOutput.writeShort(this.field5_grbit);
        littleEndianOutput.writeShort(this.field6_unknown);
    }

    public int getXPosition() {
        return this.field1_x_position;
    }

    public void setXPosition(int i) {
        this.field1_x_position = i;
    }

    public int getYPosition() {
        return this.field2_y_position;
    }

    public void setYPosition(int i) {
        this.field2_y_position = i;
    }

    public int getWidth() {
        return this.field3_width;
    }

    public void setWidth(int i) {
        this.field3_width = i;
    }

    public int getHeight() {
        return this.field4_height;
    }

    public void setHeight(int i) {
        this.field4_height = i;
    }

    public boolean getVaryDisplayPattern() {
        return varyDisplayPattern.isSet(this.field5_grbit);
    }

    public void setVaryDisplayPattern(boolean z) {
        this.field5_grbit = varyDisplayPattern.setBoolean(this.field5_grbit, z);
    }
}
