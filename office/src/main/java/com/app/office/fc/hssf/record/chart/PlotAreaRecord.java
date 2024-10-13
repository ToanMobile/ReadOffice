package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.LittleEndianOutput;

public final class PlotAreaRecord extends StandardRecord {
    public static final short sid = 4149;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return sid;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
    }

    public PlotAreaRecord() {
    }

    public PlotAreaRecord(RecordInputStream recordInputStream) {
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PLOTAREA]\n");
        stringBuffer.append("[/PLOTAREA]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        return new PlotAreaRecord();
    }
}
