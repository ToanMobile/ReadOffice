package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class LabelSSTRecord extends CellRecord {
    public static final short sid = 253;
    private int field_4_sst_index;

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "LABELSST";
    }

    public short getSid() {
        return 253;
    }

    /* access modifiers changed from: protected */
    public int getValueDataSize() {
        return 4;
    }

    public LabelSSTRecord() {
    }

    public LabelSSTRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
        this.field_4_sst_index = recordInputStream.readInt();
    }

    public void setSSTIndex(int i) {
        this.field_4_sst_index = i;
    }

    public int getSSTIndex() {
        return this.field_4_sst_index;
    }

    /* access modifiers changed from: protected */
    public void appendValueText(StringBuilder sb) {
        sb.append("  .sstIndex = ");
        sb.append(HexDump.shortToHex(getXFIndex()));
    }

    /* access modifiers changed from: protected */
    public void serializeValue(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(getSSTIndex());
    }

    public Object clone() {
        LabelSSTRecord labelSSTRecord = new LabelSSTRecord();
        copyBaseFields(labelSSTRecord);
        labelSSTRecord.field_4_sst_index = this.field_4_sst_index;
        return labelSSTRecord;
    }
}
