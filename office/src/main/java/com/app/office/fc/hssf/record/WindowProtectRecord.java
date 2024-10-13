package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class WindowProtectRecord extends StandardRecord {
    private static final BitField settingsProtectedFlag = BitFieldFactory.getInstance(1);
    public static final short sid = 25;
    private int _options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 25;
    }

    public WindowProtectRecord(int i) {
        this._options = i;
    }

    public WindowProtectRecord(RecordInputStream recordInputStream) {
        this(recordInputStream.readUShort());
    }

    public WindowProtectRecord(boolean z) {
        this(0);
        setProtect(z);
    }

    public void setProtect(boolean z) {
        this._options = settingsProtectedFlag.setBoolean(this._options, z);
    }

    public boolean getProtect() {
        return settingsProtectedFlag.isSet(this._options);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[WINDOWPROTECT]\n");
        stringBuffer.append("    .options = ");
        stringBuffer.append(HexDump.shortToHex(this._options));
        stringBuffer.append("\n");
        stringBuffer.append("[/WINDOWPROTECT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._options);
    }

    public Object clone() {
        return new WindowProtectRecord(this._options);
    }
}
