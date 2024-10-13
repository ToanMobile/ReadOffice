package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class GridsetRecord extends StandardRecord {
    public static final short sid = 130;
    public short field_1_gridset_flag;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 130;
    }

    public GridsetRecord() {
    }

    public GridsetRecord(RecordInputStream recordInputStream) {
        this.field_1_gridset_flag = recordInputStream.readShort();
    }

    public void setGridset(boolean z) {
        if (z) {
            this.field_1_gridset_flag = 1;
        } else {
            this.field_1_gridset_flag = 0;
        }
    }

    public boolean getGridset() {
        return this.field_1_gridset_flag == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[GRIDSET]\n");
        stringBuffer.append("    .gridset        = ");
        stringBuffer.append(getGridset());
        stringBuffer.append("\n");
        stringBuffer.append("[/GRIDSET]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_gridset_flag);
    }

    public Object clone() {
        GridsetRecord gridsetRecord = new GridsetRecord();
        gridsetRecord.field_1_gridset_flag = this.field_1_gridset_flag;
        return gridsetRecord;
    }
}
