package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class ProtectionRev4Record extends StandardRecord {
    private static final BitField protectedFlag = BitFieldFactory.getInstance(1);
    public static final short sid = 431;
    private int _options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    private ProtectionRev4Record(int i) {
        this._options = i;
    }

    public ProtectionRev4Record(boolean z) {
        this(0);
        setProtect(z);
    }

    public ProtectionRev4Record(RecordInputStream recordInputStream) {
        this(recordInputStream.readUShort());
    }

    public void setProtect(boolean z) {
        this._options = protectedFlag.setBoolean(this._options, z);
    }

    public boolean getProtect() {
        return protectedFlag.isSet(this._options);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PROT4REV]\n");
        stringBuffer.append("    .options = ");
        stringBuffer.append(HexDump.shortToHex(this._options));
        stringBuffer.append("\n");
        stringBuffer.append("[/PROT4REV]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._options);
    }
}
