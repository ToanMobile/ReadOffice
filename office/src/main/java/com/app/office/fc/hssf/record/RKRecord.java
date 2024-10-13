package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.util.RKUtil;
import com.app.office.fc.util.LittleEndianOutput;

public final class RKRecord extends CellRecord {
    public static final short RK_IEEE_NUMBER = 0;
    public static final short RK_IEEE_NUMBER_TIMES_100 = 1;
    public static final short RK_INTEGER = 2;
    public static final short RK_INTEGER_TIMES_100 = 3;
    public static final short sid = 638;
    private int field_4_rk_number;

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "RK";
    }

    public short getSid() {
        return sid;
    }

    /* access modifiers changed from: protected */
    public int getValueDataSize() {
        return 4;
    }

    private RKRecord() {
    }

    public RKRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
        this.field_4_rk_number = recordInputStream.readInt();
    }

    public double getRKNumber() {
        return RKUtil.decodeNumber(this.field_4_rk_number);
    }

    /* access modifiers changed from: protected */
    public void appendValueText(StringBuilder sb) {
        sb.append("  .value= ");
        sb.append(getRKNumber());
    }

    /* access modifiers changed from: protected */
    public void serializeValue(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this.field_4_rk_number);
    }

    public Object clone() {
        RKRecord rKRecord = new RKRecord();
        copyBaseFields(rKRecord);
        rKRecord.field_4_rk_number = this.field_4_rk_number;
        return rKRecord;
    }
}
