package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class ChartStartObjectRecord extends StandardRecord {
    public static final short sid = 2132;
    private short grbitFrt;
    private short iObjectContext;
    private short iObjectInstance1;
    private short iObjectInstance2;
    private short iObjectKind;
    private short rt;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 12;
    }

    public short getSid() {
        return 2132;
    }

    public ChartStartObjectRecord(RecordInputStream recordInputStream) {
        this.rt = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        this.iObjectKind = recordInputStream.readShort();
        this.iObjectContext = recordInputStream.readShort();
        this.iObjectInstance1 = recordInputStream.readShort();
        this.iObjectInstance2 = recordInputStream.readShort();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.writeShort(this.iObjectKind);
        littleEndianOutput.writeShort(this.iObjectContext);
        littleEndianOutput.writeShort(this.iObjectInstance1);
        littleEndianOutput.writeShort(this.iObjectInstance2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[STARTOBJECT]\n");
        stringBuffer.append("    .rt              =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append(10);
        stringBuffer.append("    .grbitFrt        =");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append(10);
        stringBuffer.append("    .iObjectKind     =");
        stringBuffer.append(HexDump.shortToHex(this.iObjectKind));
        stringBuffer.append(10);
        stringBuffer.append("    .iObjectContext  =");
        stringBuffer.append(HexDump.shortToHex(this.iObjectContext));
        stringBuffer.append(10);
        stringBuffer.append("    .iObjectInstance1=");
        stringBuffer.append(HexDump.shortToHex(this.iObjectInstance1));
        stringBuffer.append(10);
        stringBuffer.append("    .iObjectInstance2=");
        stringBuffer.append(HexDump.shortToHex(this.iObjectInstance2));
        stringBuffer.append(10);
        stringBuffer.append("[/STARTOBJECT]\n");
        return stringBuffer.toString();
    }
}
