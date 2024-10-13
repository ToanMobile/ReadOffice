package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class PasswordRev4Record extends StandardRecord {
    public static final short sid = 444;
    private int field_1_password;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 444;
    }

    public PasswordRev4Record(int i) {
        this.field_1_password = i;
    }

    public PasswordRev4Record(RecordInputStream recordInputStream) {
        this.field_1_password = recordInputStream.readShort();
    }

    public void setPassword(short s) {
        this.field_1_password = s;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PROT4REVPASSWORD]\n");
        stringBuffer.append("    .password = ");
        stringBuffer.append(HexDump.shortToHex(this.field_1_password));
        stringBuffer.append("\n");
        stringBuffer.append("[/PROT4REVPASSWORD]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_password);
    }
}
