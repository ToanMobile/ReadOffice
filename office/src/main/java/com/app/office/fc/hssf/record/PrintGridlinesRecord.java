package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class PrintGridlinesRecord extends StandardRecord {
    public static final short sid = 43;
    private short field_1_print_gridlines;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 43;
    }

    public PrintGridlinesRecord() {
    }

    public PrintGridlinesRecord(RecordInputStream recordInputStream) {
        this.field_1_print_gridlines = recordInputStream.readShort();
    }

    public void setPrintGridlines(boolean z) {
        if (z) {
            this.field_1_print_gridlines = 1;
        } else {
            this.field_1_print_gridlines = 0;
        }
    }

    public boolean getPrintGridlines() {
        return this.field_1_print_gridlines == 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PRINTGRIDLINES]\n");
        stringBuffer.append("    .printgridlines = ");
        stringBuffer.append(getPrintGridlines());
        stringBuffer.append("\n");
        stringBuffer.append("[/PRINTGRIDLINES]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_print_gridlines);
    }

    public Object clone() {
        PrintGridlinesRecord printGridlinesRecord = new PrintGridlinesRecord();
        printGridlinesRecord.field_1_print_gridlines = this.field_1_print_gridlines;
        return printGridlinesRecord;
    }
}
