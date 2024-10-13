package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class PrecisionRecord extends StandardRecord {
    public static final short sid = 14;
    public short field_1_precision;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 14;
    }

    public PrecisionRecord() {
    }

    public PrecisionRecord(RecordInputStream recordInputStream) {
        this.field_1_precision = recordInputStream.readShort();
    }

    public void setFullPrecision(boolean z) {
        if (z) {
            this.field_1_precision = 1;
        } else {
            this.field_1_precision = 0;
        }
    }

    public boolean getFullPrecision() {
        return this.field_1_precision == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PRECISION]\n");
        stringBuffer.append("    .precision       = ");
        stringBuffer.append(getFullPrecision());
        stringBuffer.append("\n");
        stringBuffer.append("[/PRECISION]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_precision);
    }
}
