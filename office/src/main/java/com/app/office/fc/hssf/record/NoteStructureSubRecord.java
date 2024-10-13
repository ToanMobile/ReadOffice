package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class NoteStructureSubRecord extends SubRecord {
    private static final int ENCODED_SIZE = 22;
    public static final short sid = 13;
    private byte[] reserved;

    public short getSid() {
        return 13;
    }

    public NoteStructureSubRecord() {
        this.reserved = new byte[22];
    }

    public NoteStructureSubRecord(LittleEndianInput littleEndianInput, int i) {
        if (i == 22) {
            byte[] bArr = new byte[i];
            littleEndianInput.readFully(bArr);
            this.reserved = bArr;
            return;
        }
        throw new RecordFormatException("Unexpected size (" + i + ")");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ftNts ]");
        stringBuffer.append("\n");
        stringBuffer.append("  size     = ");
        stringBuffer.append(getDataSize());
        stringBuffer.append("\n");
        stringBuffer.append("  reserved = ");
        stringBuffer.append(HexDump.toHex(this.reserved));
        stringBuffer.append("\n");
        stringBuffer.append("[/ftNts ]");
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(13);
        littleEndianOutput.writeShort(this.reserved.length);
        littleEndianOutput.write(this.reserved);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this.reserved.length;
    }

    public Object clone() {
        NoteStructureSubRecord noteStructureSubRecord = new NoteStructureSubRecord();
        byte[] bArr = this.reserved;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        noteStructureSubRecord.reserved = bArr2;
        return noteStructureSubRecord;
    }
}
