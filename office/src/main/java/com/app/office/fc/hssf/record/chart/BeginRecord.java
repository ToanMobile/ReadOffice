package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.LittleEndianOutput;

public final class BeginRecord extends StandardRecord {
    public static final short sid = 4147;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return sid;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
    }

    public BeginRecord() {
    }

    public BeginRecord(RecordInputStream recordInputStream) {
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BEGIN]\n");
        stringBuffer.append("[/BEGIN]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        return new BeginRecord();
    }
}
