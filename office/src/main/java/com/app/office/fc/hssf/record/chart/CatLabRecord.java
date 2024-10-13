package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class CatLabRecord extends StandardRecord {
    public static final short sid = 2134;
    private short at;
    private short grbit;
    private short grbitFrt;
    private short rt;
    private Short unused;
    private short wOffset;

    public short getSid() {
        return 2134;
    }

    public CatLabRecord(RecordInputStream recordInputStream) {
        this.rt = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        this.wOffset = recordInputStream.readShort();
        this.at = recordInputStream.readShort();
        this.grbit = recordInputStream.readShort();
        if (recordInputStream.available() == 0) {
            this.unused = null;
        } else {
            this.unused = Short.valueOf(recordInputStream.readShort());
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this.unused == null ? 0 : 2) + 10;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.writeShort(this.wOffset);
        littleEndianOutput.writeShort(this.at);
        littleEndianOutput.writeShort(this.grbit);
        Short sh = this.unused;
        if (sh != null) {
            littleEndianOutput.writeShort(sh.shortValue());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CATLAB]\n");
        stringBuffer.append("    .rt      =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append(10);
        stringBuffer.append("    .grbitFrt=");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append(10);
        stringBuffer.append("    .wOffset =");
        stringBuffer.append(HexDump.shortToHex(this.wOffset));
        stringBuffer.append(10);
        stringBuffer.append("    .at      =");
        stringBuffer.append(HexDump.shortToHex(this.at));
        stringBuffer.append(10);
        stringBuffer.append("    .grbit   =");
        stringBuffer.append(HexDump.shortToHex(this.grbit));
        stringBuffer.append(10);
        stringBuffer.append("    .unused  =");
        stringBuffer.append(HexDump.shortToHex(this.unused.shortValue()));
        stringBuffer.append(10);
        stringBuffer.append("[/CATLAB]\n");
        return stringBuffer.toString();
    }
}
