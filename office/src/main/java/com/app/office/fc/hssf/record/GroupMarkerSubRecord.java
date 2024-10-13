package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class GroupMarkerSubRecord extends SubRecord {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final short sid = 6;
    private byte[] reserved;

    public short getSid() {
        return 6;
    }

    public GroupMarkerSubRecord() {
        this.reserved = EMPTY_BYTE_ARRAY;
    }

    public GroupMarkerSubRecord(LittleEndianInput littleEndianInput, int i) {
        byte[] bArr = new byte[i];
        littleEndianInput.readFully(bArr);
        this.reserved = bArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("[ftGmo]" + property);
        stringBuffer.append("  reserved = ");
        stringBuffer.append(HexDump.toHex(this.reserved));
        stringBuffer.append(property);
        stringBuffer.append("[/ftGmo]" + property);
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(6);
        littleEndianOutput.writeShort(this.reserved.length);
        littleEndianOutput.write(this.reserved);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this.reserved.length;
    }

    public Object clone() {
        GroupMarkerSubRecord groupMarkerSubRecord = new GroupMarkerSubRecord();
        groupMarkerSubRecord.reserved = new byte[this.reserved.length];
        int i = 0;
        while (true) {
            byte[] bArr = this.reserved;
            if (i >= bArr.length) {
                return groupMarkerSubRecord;
            }
            groupMarkerSubRecord.reserved[i] = bArr[i];
            i++;
        }
    }
}
