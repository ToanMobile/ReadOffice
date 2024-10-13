package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class HCenterRecord extends StandardRecord {
    public static final short sid = 131;
    private short field_1_hcenter;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 131;
    }

    public HCenterRecord() {
    }

    public HCenterRecord(RecordInputStream recordInputStream) {
        this.field_1_hcenter = recordInputStream.readShort();
    }

    public void setHCenter(boolean z) {
        if (z) {
            this.field_1_hcenter = 1;
        } else {
            this.field_1_hcenter = 0;
        }
    }

    public boolean getHCenter() {
        return this.field_1_hcenter == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[HCENTER]\n");
        stringBuffer.append("    .hcenter        = ");
        stringBuffer.append(getHCenter());
        stringBuffer.append("\n");
        stringBuffer.append("[/HCENTER]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_hcenter);
    }

    public Object clone() {
        HCenterRecord hCenterRecord = new HCenterRecord();
        hCenterRecord.field_1_hcenter = this.field_1_hcenter;
        return hCenterRecord;
    }
}
