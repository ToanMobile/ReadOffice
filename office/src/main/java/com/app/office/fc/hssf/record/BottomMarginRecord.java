package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class BottomMarginRecord extends StandardRecord implements Margin {
    public static final short sid = 41;
    private double field_1_margin;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return 41;
    }

    public BottomMarginRecord() {
    }

    public BottomMarginRecord(RecordInputStream recordInputStream) {
        this.field_1_margin = recordInputStream.readDouble();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BottomMargin]\n");
        stringBuffer.append("    .margin               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getMargin());
        stringBuffer.append(" )\n");
        stringBuffer.append("[/BottomMargin]\n");
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
        BottomMarginRecord bottomMarginRecord = new BottomMarginRecord();
        bottomMarginRecord.field_1_margin = this.field_1_margin;
        return bottomMarginRecord;
    }
}
