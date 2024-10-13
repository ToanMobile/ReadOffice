package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class SaveRecalcRecord extends StandardRecord {
    public static final short sid = 95;
    private short field_1_recalc;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 95;
    }

    public SaveRecalcRecord() {
    }

    public SaveRecalcRecord(RecordInputStream recordInputStream) {
        this.field_1_recalc = recordInputStream.readShort();
    }

    public void setRecalc(boolean z) {
        int i = 1;
        if (!z) {
            i = 0;
        }
        this.field_1_recalc = (short) i;
    }

    public boolean getRecalc() {
        return this.field_1_recalc == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SAVERECALC]\n");
        stringBuffer.append("    .recalc         = ");
        stringBuffer.append(getRecalc());
        stringBuffer.append("\n");
        stringBuffer.append("[/SAVERECALC]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_recalc);
    }

    public Object clone() {
        SaveRecalcRecord saveRecalcRecord = new SaveRecalcRecord();
        saveRecalcRecord.field_1_recalc = this.field_1_recalc;
        return saveRecalcRecord;
    }
}
