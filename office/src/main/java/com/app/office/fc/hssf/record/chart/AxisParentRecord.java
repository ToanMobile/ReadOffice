package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.hssf.record.UnknownRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.LittleEndianOutput;

public final class AxisParentRecord extends StandardRecord {
    public static final short AXIS_TYPE_MAIN = 0;
    public static final short AXIS_TYPE_SECONDARY = 1;
    public static final short sid = 4161;
    private short field_1_axisType;
    private int field_2_x;
    private int field_3_y;
    private int field_4_width;
    private int field_5_height;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 18;
    }

    public short getSid() {
        return sid;
    }

    public AxisParentRecord() {
    }

    public AxisParentRecord(RecordInputStream recordInputStream) {
        this.field_1_axisType = recordInputStream.readShort();
        this.field_2_x = recordInputStream.readInt();
        this.field_3_y = recordInputStream.readInt();
        this.field_4_width = recordInputStream.readInt();
        this.field_5_height = recordInputStream.readInt();
    }

    public AxisParentRecord(UnknownRecord unknownRecord) {
        if (unknownRecord.getSid() == 4161 && unknownRecord.getData().length == getDataSize()) {
            byte[] data = unknownRecord.getData();
            this.field_1_axisType = LittleEndian.getShort(data, 0);
            this.field_2_x = LittleEndian.getInt(data, 2);
            this.field_3_y = LittleEndian.getInt(data, 6);
            this.field_4_width = LittleEndian.getInt(data, 10);
            this.field_5_height = LittleEndian.getInt(data, 14);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[AXISPARENT]\n");
        stringBuffer.append("    .axisType             = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getAxisType()));
        stringBuffer.append(" (");
        stringBuffer.append(getAxisType());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .x                    = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getX()));
        stringBuffer.append(" (");
        stringBuffer.append(getX());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .y                    = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getY()));
        stringBuffer.append(" (");
        stringBuffer.append(getY());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .width                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getWidth()));
        stringBuffer.append(" (");
        stringBuffer.append(getWidth());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .height               = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getHeight()));
        stringBuffer.append(" (");
        stringBuffer.append(getHeight());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/AXISPARENT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_axisType);
        littleEndianOutput.writeInt(this.field_2_x);
        littleEndianOutput.writeInt(this.field_3_y);
        littleEndianOutput.writeInt(this.field_4_width);
        littleEndianOutput.writeInt(this.field_5_height);
    }

    public Object clone() {
        AxisParentRecord axisParentRecord = new AxisParentRecord();
        axisParentRecord.field_1_axisType = this.field_1_axisType;
        axisParentRecord.field_2_x = this.field_2_x;
        axisParentRecord.field_3_y = this.field_3_y;
        axisParentRecord.field_4_width = this.field_4_width;
        axisParentRecord.field_5_height = this.field_5_height;
        return axisParentRecord;
    }

    public short getAxisType() {
        return this.field_1_axisType;
    }

    public void setAxisType(short s) {
        this.field_1_axisType = s;
    }

    public int getX() {
        return this.field_2_x;
    }

    public void setX(int i) {
        this.field_2_x = i;
    }

    public int getY() {
        return this.field_3_y;
    }

    public void setY(int i) {
        this.field_3_y = i;
    }

    public int getWidth() {
        return this.field_4_width;
    }

    public void setWidth(int i) {
        this.field_4_width = i;
    }

    public int getHeight() {
        return this.field_5_height;
    }

    public void setHeight(int i) {
        this.field_5_height = i;
    }
}
