package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class DeltaRecord extends StandardRecord {
    public static final double DEFAULT_VALUE = 0.001d;
    public static final short sid = 16;
    private double field_1_max_change;

    public Object clone() {
        return this;
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return 16;
    }

    public DeltaRecord(double d) {
        this.field_1_max_change = d;
    }

    public DeltaRecord(RecordInputStream recordInputStream) {
        this.field_1_max_change = recordInputStream.readDouble();
    }

    public double getMaxChange() {
        return this.field_1_max_change;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[DELTA]\n");
        stringBuffer.append("    .maxchange = ");
        stringBuffer.append(getMaxChange());
        stringBuffer.append("\n");
        stringBuffer.append("[/DELTA]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeDouble(getMaxChange());
    }
}
