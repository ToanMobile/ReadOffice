package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class BackupRecord extends StandardRecord {
    public static final short sid = 64;
    private short field_1_backup;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 64;
    }

    public BackupRecord() {
    }

    public BackupRecord(RecordInputStream recordInputStream) {
        this.field_1_backup = recordInputStream.readShort();
    }

    public void setBackup(short s) {
        this.field_1_backup = s;
    }

    public short getBackup() {
        return this.field_1_backup;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BACKUP]\n");
        stringBuffer.append("    .backup          = ");
        stringBuffer.append(Integer.toHexString(getBackup()));
        stringBuffer.append("\n");
        stringBuffer.append("[/BACKUP]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getBackup());
    }
}
