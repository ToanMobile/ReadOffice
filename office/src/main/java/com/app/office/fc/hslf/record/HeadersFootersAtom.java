package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class HeadersFootersAtom extends RecordAtom {
    public static final int fHasDate = 1;
    public static final int fHasFooter = 32;
    public static final int fHasHeader = 16;
    public static final int fHasSlideNumber = 8;
    public static final int fHasTodayDate = 2;
    public static final int fHasUserDate = 4;
    private byte[] _header;
    private byte[] _recdata;

    protected HeadersFootersAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._recdata = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public HeadersFootersAtom() {
        this._recdata = new byte[4];
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._recdata.length);
    }

    public long getRecordType() {
        return (long) RecordTypes.HeadersFootersAtom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        outputStream.write(this._recdata);
    }

    public int getFormatId() {
        return LittleEndian.getShort(this._recdata, 0);
    }

    public void setFormatId(int i) {
        LittleEndian.putUShort(this._recdata, 0, i);
    }

    public int getMask() {
        return LittleEndian.getShort(this._recdata, 2);
    }

    public void setMask(int i) {
        LittleEndian.putUShort(this._recdata, 2, i);
    }

    public boolean getFlag(int i) {
        return (i & getMask()) != 0;
    }

    public void setFlag(int i, boolean z) {
        int mask = getMask();
        setMask(z ? i | mask : (~i) & mask);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("HeadersFootersAtom\n");
        stringBuffer.append("\tFormatId: " + getFormatId() + "\n");
        stringBuffer.append("\tMask    : " + getMask() + "\n");
        stringBuffer.append("\t  fHasDate        : " + getFlag(1) + "\n");
        stringBuffer.append("\t  fHasTodayDate   : " + getFlag(2) + "\n");
        stringBuffer.append("\t  fHasUserDate    : " + getFlag(4) + "\n");
        stringBuffer.append("\t  fHasSlideNumber : " + getFlag(8) + "\n");
        stringBuffer.append("\t  fHasHeader      : " + getFlag(16) + "\n");
        stringBuffer.append("\t  fHasFooter      : " + getFlag(32) + "\n");
        return stringBuffer.toString();
    }

    public void dispose() {
        this._header = null;
        this._recdata = null;
    }
}
