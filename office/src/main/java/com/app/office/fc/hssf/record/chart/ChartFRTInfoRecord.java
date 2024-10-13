package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class ChartFRTInfoRecord extends StandardRecord {
    public static final short sid = 2128;
    private short grbitFrt;
    private CFRTID[] rgCFRTID;
    private short rt;
    private byte verOriginator;
    private byte verWriter;

    public short getSid() {
        return sid;
    }

    private static final class CFRTID {
        public static final int ENCODED_SIZE = 4;
        private int rtFirst;
        private int rtLast;

        public CFRTID(LittleEndianInput littleEndianInput) {
            this.rtFirst = littleEndianInput.readShort();
            this.rtLast = littleEndianInput.readShort();
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this.rtFirst);
            littleEndianOutput.writeShort(this.rtLast);
        }
    }

    public ChartFRTInfoRecord(RecordInputStream recordInputStream) {
        this.rt = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        this.verOriginator = recordInputStream.readByte();
        this.verWriter = recordInputStream.readByte();
        int readShort = recordInputStream.readShort();
        this.rgCFRTID = new CFRTID[readShort];
        for (int i = 0; i < readShort; i++) {
            this.rgCFRTID[i] = new CFRTID(recordInputStream);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this.rgCFRTID.length * 4) + 8;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.writeByte(this.verOriginator);
        littleEndianOutput.writeByte(this.verWriter);
        littleEndianOutput.writeShort(r0);
        for (CFRTID serialize : this.rgCFRTID) {
            serialize.serialize(littleEndianOutput);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CHARTFRTINFO]\n");
        stringBuffer.append("    .rt           =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append(10);
        stringBuffer.append("    .grbitFrt     =");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append(10);
        stringBuffer.append("    .verOriginator=");
        stringBuffer.append(HexDump.byteToHex(this.verOriginator));
        stringBuffer.append(10);
        stringBuffer.append("    .verWriter    =");
        stringBuffer.append(HexDump.byteToHex(this.verOriginator));
        stringBuffer.append(10);
        stringBuffer.append("    .nCFRTIDs     =");
        stringBuffer.append(HexDump.shortToHex(this.rgCFRTID.length));
        stringBuffer.append(10);
        stringBuffer.append("[/CHARTFRTINFO]\n");
        return stringBuffer.toString();
    }
}
