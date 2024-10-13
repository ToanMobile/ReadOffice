package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class UnitsRecord extends StandardRecord {
    public static final short sid = 4097;
    private short field_1_units;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 4097;
    }

    public UnitsRecord() {
    }

    public UnitsRecord(RecordInputStream recordInputStream) {
        this.field_1_units = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[UNITS]\n");
        stringBuffer.append("    .units                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getUnits()));
        stringBuffer.append(" (");
        stringBuffer.append(getUnits());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/UNITS]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_units);
    }

    public Object clone() {
        UnitsRecord unitsRecord = new UnitsRecord();
        unitsRecord.field_1_units = this.field_1_units;
        return unitsRecord;
    }

    public short getUnits() {
        return this.field_1_units;
    }

    public void setUnits(short s) {
        this.field_1_units = s;
    }
}
