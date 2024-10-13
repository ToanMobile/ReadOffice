package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import java.util.Arrays;

public final class HeaderFooterRecord extends StandardRecord {
    private static final byte[] BLANK_GUID = new byte[16];
    public static final short sid = 2204;
    private byte[] _rawData;

    public short getSid() {
        return sid;
    }

    public HeaderFooterRecord(byte[] bArr) {
        this._rawData = bArr;
    }

    public HeaderFooterRecord(RecordInputStream recordInputStream) {
        this._rawData = recordInputStream.readRemainder();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.write(this._rawData);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this._rawData.length;
    }

    public byte[] getGuid() {
        byte[] bArr = new byte[16];
        byte[] bArr2 = this._rawData;
        System.arraycopy(bArr2, 12, bArr, 0, Math.min(16, bArr2.length - 12));
        return bArr;
    }

    public boolean isCurrentSheet() {
        return Arrays.equals(getGuid(), BLANK_GUID);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("HEADERFOOTER");
        stringBuffer.append("] (0x");
        stringBuffer.append(Integer.toHexString(UnknownRecord.HEADER_FOOTER_089C).toUpperCase() + ")\n");
        stringBuffer.append("  rawData=");
        stringBuffer.append(HexDump.toHex(this._rawData));
        stringBuffer.append("\n");
        stringBuffer.append("[/");
        stringBuffer.append("HEADERFOOTER");
        stringBuffer.append("]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        return cloneViaReserialise();
    }
}
