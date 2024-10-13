package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class SCLRecord extends StandardRecord {
    public static final short sid = 160;
    private short field_1_numerator;
    private short field_2_denominator;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 4;
    }

    public short getSid() {
        return sid;
    }

    public SCLRecord() {
    }

    public SCLRecord(RecordInputStream recordInputStream) {
        this.field_1_numerator = recordInputStream.readShort();
        this.field_2_denominator = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SCL]\n");
        stringBuffer.append("    .numerator            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getNumerator()));
        stringBuffer.append(" (");
        stringBuffer.append(getNumerator());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .denominator          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getDenominator()));
        stringBuffer.append(" (");
        stringBuffer.append(getDenominator());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/SCL]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_numerator);
        littleEndianOutput.writeShort(this.field_2_denominator);
    }

    public Object clone() {
        SCLRecord sCLRecord = new SCLRecord();
        sCLRecord.field_1_numerator = this.field_1_numerator;
        sCLRecord.field_2_denominator = this.field_2_denominator;
        return sCLRecord;
    }

    public short getNumerator() {
        return this.field_1_numerator;
    }

    public void setNumerator(short s) {
        this.field_1_numerator = s;
    }

    public short getDenominator() {
        return this.field_2_denominator;
    }

    public void setDenominator(short s) {
        this.field_2_denominator = s;
    }
}
