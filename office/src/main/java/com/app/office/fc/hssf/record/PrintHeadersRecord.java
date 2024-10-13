package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class PrintHeadersRecord extends StandardRecord {
    public static final short sid = 42;
    private short field_1_print_headers;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 42;
    }

    public PrintHeadersRecord() {
    }

    public PrintHeadersRecord(RecordInputStream recordInputStream) {
        this.field_1_print_headers = recordInputStream.readShort();
    }

    public void setPrintHeaders(boolean z) {
        if (z) {
            this.field_1_print_headers = 1;
        } else {
            this.field_1_print_headers = 0;
        }
    }

    public boolean getPrintHeaders() {
        return this.field_1_print_headers == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PRINTHEADERS]\n");
        stringBuffer.append("    .printheaders   = ");
        stringBuffer.append(getPrintHeaders());
        stringBuffer.append("\n");
        stringBuffer.append("[/PRINTHEADERS]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_print_headers);
    }

    public Object clone() {
        PrintHeadersRecord printHeadersRecord = new PrintHeadersRecord();
        printHeadersRecord.field_1_print_headers = this.field_1_print_headers;
        return printHeadersRecord;
    }
}
