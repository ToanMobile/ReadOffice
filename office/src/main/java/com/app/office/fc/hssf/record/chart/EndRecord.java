package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.LittleEndianOutput;

public final class EndRecord extends StandardRecord {
    public static final short sid = 4148;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return sid;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
    }

    public EndRecord() {
    }

    public EndRecord(RecordInputStream recordInputStream) {
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[END]\n");
        stringBuffer.append("[/END]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        return new EndRecord();
    }
}
