package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.LittleEndianOutput;

public final class ChartRecord extends StandardRecord {
    public static final short sid = 4098;
    private int field_1_x;
    private int field_2_y;
    private int field_3_width;
    private int field_4_height;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 16;
    }

    public short getSid() {
        return 4098;
    }

    public ChartRecord() {
    }

    public ChartRecord(RecordInputStream recordInputStream) {
        this.field_1_x = recordInputStream.readInt();
        this.field_2_y = recordInputStream.readInt();
        this.field_3_width = recordInputStream.readInt();
        this.field_4_height = recordInputStream.readInt();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CHART]\n");
        stringBuffer.append("    .x     = ");
        stringBuffer.append(getX());
        stringBuffer.append(10);
        stringBuffer.append("    .y     = ");
        stringBuffer.append(getY());
        stringBuffer.append(10);
        stringBuffer.append("    .width = ");
        stringBuffer.append(getWidth());
        stringBuffer.append(10);
        stringBuffer.append("    .height= ");
        stringBuffer.append(getHeight());
        stringBuffer.append(10);
        stringBuffer.append("[/CHART]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this.field_1_x);
        littleEndianOutput.writeInt(this.field_2_y);
        littleEndianOutput.writeInt(this.field_3_width);
        littleEndianOutput.writeInt(this.field_4_height);
    }

    public Object clone() {
        ChartRecord chartRecord = new ChartRecord();
        chartRecord.field_1_x = this.field_1_x;
        chartRecord.field_2_y = this.field_2_y;
        chartRecord.field_3_width = this.field_3_width;
        chartRecord.field_4_height = this.field_4_height;
        return chartRecord;
    }

    public int getX() {
        return this.field_1_x;
    }

    public void setX(int i) {
        this.field_1_x = i;
    }

    public int getY() {
        return this.field_2_y;
    }

    public void setY(int i) {
        this.field_2_y = i;
    }

    public int getWidth() {
        return this.field_3_width;
    }

    public void setWidth(int i) {
        this.field_3_width = i;
    }

    public int getHeight() {
        return this.field_4_height;
    }

    public void setHeight(int i) {
        this.field_4_height = i;
    }
}
