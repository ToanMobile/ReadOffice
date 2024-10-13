package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class VCenterRecord extends StandardRecord {
    public static final short sid = 132;
    private int field_1_vcenter;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 132;
    }

    public VCenterRecord() {
    }

    public VCenterRecord(RecordInputStream recordInputStream) {
        this.field_1_vcenter = recordInputStream.readShort();
    }

    public void setVCenter(boolean z) {
        this.field_1_vcenter = z ? 1 : 0;
    }

    public boolean getVCenter() {
        return this.field_1_vcenter == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[VCENTER]\n");
        stringBuffer.append("    .vcenter        = ");
        stringBuffer.append(getVCenter());
        stringBuffer.append("\n");
        stringBuffer.append("[/VCENTER]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_vcenter);
    }

    public Object clone() {
        VCenterRecord vCenterRecord = new VCenterRecord();
        vCenterRecord.field_1_vcenter = this.field_1_vcenter;
        return vCenterRecord;
    }
}
