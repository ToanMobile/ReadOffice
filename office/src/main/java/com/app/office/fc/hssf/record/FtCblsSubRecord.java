package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class FtCblsSubRecord extends SubRecord {
    private static final int ENCODED_SIZE = 20;
    public static final short sid = 12;
    private byte[] reserved;

    public short getSid() {
        return 12;
    }

    public FtCblsSubRecord() {
        this.reserved = new byte[20];
    }

    public FtCblsSubRecord(LittleEndianInput littleEndianInput, int i) {
        if (i == 20) {
            byte[] bArr = new byte[i];
            littleEndianInput.readFully(bArr);
            this.reserved = bArr;
            return;
        }
        throw new RecordFormatException("Unexpected size (" + i + ")");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FtCbls ]");
        stringBuffer.append("\n");
        stringBuffer.append("  size     = ");
        stringBuffer.append(getDataSize());
        stringBuffer.append("\n");
        stringBuffer.append("  reserved = ");
        stringBuffer.append(HexDump.toHex(this.reserved));
        stringBuffer.append("\n");
        stringBuffer.append("[/FtCbls ]");
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(12);
        littleEndianOutput.writeShort(this.reserved.length);
        littleEndianOutput.write(this.reserved);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this.reserved.length;
    }

    public Object clone() {
        FtCblsSubRecord ftCblsSubRecord = new FtCblsSubRecord();
        byte[] bArr = this.reserved;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        ftCblsSubRecord.reserved = bArr2;
        return ftCblsSubRecord;
    }
}
