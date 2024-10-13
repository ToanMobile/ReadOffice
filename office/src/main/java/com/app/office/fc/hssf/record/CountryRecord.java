package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class CountryRecord extends StandardRecord {
    public static final short sid = 140;
    private short field_1_default_country;
    private short field_2_current_country;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 4;
    }

    public short getSid() {
        return sid;
    }

    public CountryRecord() {
    }

    public CountryRecord(RecordInputStream recordInputStream) {
        this.field_1_default_country = recordInputStream.readShort();
        this.field_2_current_country = recordInputStream.readShort();
    }

    public void setDefaultCountry(short s) {
        this.field_1_default_country = s;
    }

    public void setCurrentCountry(short s) {
        this.field_2_current_country = s;
    }

    public short getDefaultCountry() {
        return this.field_1_default_country;
    }

    public short getCurrentCountry() {
        return this.field_2_current_country;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[COUNTRY]\n");
        stringBuffer.append("    .defaultcountry  = ");
        stringBuffer.append(Integer.toHexString(getDefaultCountry()));
        stringBuffer.append("\n");
        stringBuffer.append("    .currentcountry  = ");
        stringBuffer.append(Integer.toHexString(getCurrentCountry()));
        stringBuffer.append("\n");
        stringBuffer.append("[/COUNTRY]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getDefaultCountry());
        littleEndianOutput.writeShort(getCurrentCountry());
    }
}
