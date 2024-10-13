package com.app.office.fc.hssf.record;

import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class PasswordRecord extends StandardRecord {
    public static final short sid = 19;
    private int field_1_password;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 19;
    }

    public PasswordRecord(int i) {
        this.field_1_password = i;
    }

    public PasswordRecord(RecordInputStream recordInputStream) {
        this.field_1_password = recordInputStream.readShort();
    }

    public static short hashPassword(String str) {
        byte[] bytes = str.getBytes();
        byte b = 0;
        if (bytes.length > 0) {
            int length = bytes.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                b = bytes[i] ^ (((b >> 14) & 1) | ((b << 1) & 32767));
                length = i;
            }
            b = (bytes.length ^ (((b >> 14) & 1) | ((b << 1) & 32767))) ^ Field.MERGESEQ;
        }
        return (short) b;
    }

    public void setPassword(int i) {
        this.field_1_password = i;
    }

    public int getPassword() {
        return this.field_1_password;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PASSWORD]\n");
        stringBuffer.append("    .password = ");
        stringBuffer.append(HexDump.shortToHex(this.field_1_password));
        stringBuffer.append("\n");
        stringBuffer.append("[/PASSWORD]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_password);
    }

    public Object clone() {
        return new PasswordRecord(this.field_1_password);
    }
}
