package com.app.office.fc.hssf.record;

import com.app.office.constant.fc.ConstantValueParser;
import com.app.office.fc.util.LittleEndianOutput;

public final class CRNRecord extends StandardRecord {
    public static final short sid = 90;
    private int field_1_last_column_index;
    private int field_2_first_column_index;
    private int field_3_row_index;
    private Object[] field_4_constant_values;

    public short getSid() {
        return 90;
    }

    public CRNRecord() {
        throw new RuntimeException("incomplete code");
    }

    public int getNumberOfCRNs() {
        return this.field_1_last_column_index;
    }

    public CRNRecord(RecordInputStream recordInputStream) {
        this.field_1_last_column_index = recordInputStream.readUByte();
        this.field_2_first_column_index = recordInputStream.readUByte();
        this.field_3_row_index = recordInputStream.readShort();
        this.field_4_constant_values = ConstantValueParser.parse(recordInputStream, (this.field_1_last_column_index - this.field_2_first_column_index) + 1);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [CRN");
        stringBuffer.append(" rowIx=");
        stringBuffer.append(this.field_3_row_index);
        stringBuffer.append(" firstColIx=");
        stringBuffer.append(this.field_2_first_column_index);
        stringBuffer.append(" lastColIx=");
        stringBuffer.append(this.field_1_last_column_index);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return ConstantValueParser.getEncodedSize(this.field_4_constant_values) + 4;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(this.field_1_last_column_index);
        littleEndianOutput.writeByte(this.field_2_first_column_index);
        littleEndianOutput.writeShort(this.field_3_row_index);
        ConstantValueParser.encode(littleEndianOutput, this.field_4_constant_values);
    }
}
