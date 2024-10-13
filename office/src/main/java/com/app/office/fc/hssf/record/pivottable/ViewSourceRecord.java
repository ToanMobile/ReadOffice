package com.app.office.fc.hssf.record.pivottable;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class ViewSourceRecord extends StandardRecord {
    public static final short sid = 227;
    private int vs;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public ViewSourceRecord(RecordInputStream recordInputStream) {
        this.vs = recordInputStream.readShort();
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.vs);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXVS]\n");
        stringBuffer.append("    .vs      =");
        stringBuffer.append(HexDump.shortToHex(this.vs));
        stringBuffer.append(10);
        stringBuffer.append("[/SXVS]\n");
        return stringBuffer.toString();
    }
}
