package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class ChartEndObjectRecord extends StandardRecord {
    public static final short sid = 2133;
    private short grbitFrt;
    private short iObjectKind;
    private byte[] reserved = new byte[6];
    private short rt;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 12;
    }

    public short getSid() {
        return 2133;
    }

    public ChartEndObjectRecord(RecordInputStream recordInputStream) {
        this.rt = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        this.iObjectKind = recordInputStream.readShort();
        if (recordInputStream.available() != 0) {
            recordInputStream.readFully(this.reserved);
        }
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.writeShort(this.iObjectKind);
        littleEndianOutput.write(this.reserved);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ENDOBJECT]\n");
        stringBuffer.append("    .rt         =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append(10);
        stringBuffer.append("    .grbitFrt   =");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append(10);
        stringBuffer.append("    .iObjectKind=");
        stringBuffer.append(HexDump.shortToHex(this.iObjectKind));
        stringBuffer.append(10);
        stringBuffer.append("    .reserved   =");
        stringBuffer.append(HexDump.toHex(this.reserved));
        stringBuffer.append(10);
        stringBuffer.append("[/ENDOBJECT]\n");
        return stringBuffer.toString();
    }
}
