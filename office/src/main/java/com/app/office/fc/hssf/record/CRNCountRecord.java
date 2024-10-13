package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class CRNCountRecord extends StandardRecord {
    private static final short DATA_SIZE = 4;
    public static final short sid = 89;
    private int field_1_number_crn_records;
    private int field_2_sheet_table_index;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 4;
    }

    public short getSid() {
        return 89;
    }

    public CRNCountRecord() {
        throw new RuntimeException("incomplete code");
    }

    public int getNumberOfCRNs() {
        return this.field_1_number_crn_records;
    }

    public CRNCountRecord(RecordInputStream recordInputStream) {
        short readShort = recordInputStream.readShort();
        this.field_1_number_crn_records = readShort;
        if (readShort < 0) {
            this.field_1_number_crn_records = (short) (-readShort);
        }
        this.field_2_sheet_table_index = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [XCT");
        stringBuffer.append(" nCRNs=");
        stringBuffer.append(this.field_1_number_crn_records);
        stringBuffer.append(" sheetIx=");
        stringBuffer.append(this.field_2_sheet_table_index);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort((short) this.field_1_number_crn_records);
        littleEndianOutput.writeShort((short) this.field_2_sheet_table_index);
    }
}
