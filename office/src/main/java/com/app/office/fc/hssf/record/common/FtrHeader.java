package com.app.office.fc.hssf.record.common;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.util.LittleEndianOutput;

public final class FtrHeader {
    private short grbitFrt;
    private short recordType;
    private byte[] reserved;

    public static int getDataSize() {
        return 12;
    }

    public FtrHeader() {
        this.reserved = new byte[8];
    }

    public FtrHeader(RecordInputStream recordInputStream) {
        this.recordType = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        byte[] bArr = new byte[8];
        this.reserved = bArr;
        recordInputStream.read(bArr, 0, 8);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" [FUTURE HEADER]\n");
        stringBuffer.append("   Type " + this.recordType);
        stringBuffer.append("   Flags " + this.grbitFrt);
        stringBuffer.append(" [/FUTURE HEADER]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.recordType);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.write(this.reserved);
    }

    public short getRecordType() {
        return this.recordType;
    }

    public void setRecordType(short s) {
        this.recordType = s;
    }

    public short getGrbitFrt() {
        return this.grbitFrt;
    }

    public void setGrbitFrt(short s) {
        this.grbitFrt = s;
    }

    public byte[] getReserved() {
        return this.reserved;
    }

    public void setReserved(byte[] bArr) {
        this.reserved = bArr;
    }
}
