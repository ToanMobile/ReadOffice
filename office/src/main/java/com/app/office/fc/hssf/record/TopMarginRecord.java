package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class TopMarginRecord extends StandardRecord implements Margin {
    public static final short sid = 40;
    private double field_1_margin;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return 40;
    }

    public TopMarginRecord() {
    }

    public TopMarginRecord(RecordInputStream recordInputStream) {
        this.field_1_margin = recordInputStream.readDouble();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TopMargin]\n");
        stringBuffer.append("    .margin               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getMargin());
        stringBuffer.append(" )\n");
        stringBuffer.append("[/TopMargin]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeDouble(this.field_1_margin);
    }

    public double getMargin() {
        return this.field_1_margin;
    }

    public void setMargin(double d) {
        this.field_1_margin = d;
    }

    public Object clone() {
        TopMarginRecord topMarginRecord = new TopMarginRecord();
        topMarginRecord.field_1_margin = this.field_1_margin;
        return topMarginRecord;
    }
}
