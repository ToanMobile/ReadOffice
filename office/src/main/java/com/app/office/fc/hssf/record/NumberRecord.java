package com.app.office.fc.hssf.record;

import com.app.office.fc.ss.util.NumberToTextConverter;
import com.app.office.fc.util.LittleEndianOutput;

public final class NumberRecord extends CellRecord {
    public static final short sid = 515;
    private double field_4_value;

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "NUMBER";
    }

    public short getSid() {
        return 515;
    }

    /* access modifiers changed from: protected */
    public int getValueDataSize() {
        return 8;
    }

    public NumberRecord() {
    }

    public NumberRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
        this.field_4_value = recordInputStream.readDouble();
    }

    public void setValue(double d) {
        this.field_4_value = d;
    }

    public double getValue() {
        return this.field_4_value;
    }

    /* access modifiers changed from: protected */
    public void appendValueText(StringBuilder sb) {
        sb.append("  .value= ");
        sb.append(NumberToTextConverter.toText(this.field_4_value));
    }

    /* access modifiers changed from: protected */
    public void serializeValue(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeDouble(getValue());
    }

    public Object clone() {
        NumberRecord numberRecord = new NumberRecord();
        copyBaseFields(numberRecord);
        numberRecord.field_4_value = this.field_4_value;
        return numberRecord;
    }
}
