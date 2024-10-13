package com.app.office.fc.hssf.record.pivottable;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class StreamIDRecord extends StandardRecord {
    public static final short sid = 213;
    private int idstm;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public StreamIDRecord(RecordInputStream recordInputStream) {
        this.idstm = recordInputStream.readShort();
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.idstm);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXIDSTM]\n");
        stringBuffer.append("    .idstm      =");
        stringBuffer.append(HexDump.shortToHex(this.idstm));
        stringBuffer.append(10);
        stringBuffer.append("[/SXIDSTM]\n");
        return stringBuffer.toString();
    }
}
