package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class ExMediaAtom extends RecordAtom {
    public static final int fLoop = 1;
    public static final int fNarration = 4;
    public static final int fRewind = 2;
    private byte[] _header;
    private byte[] _recdata;

    protected ExMediaAtom() {
        this._recdata = new byte[8];
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._recdata.length);
    }

    protected ExMediaAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._recdata = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public long getRecordType() {
        return (long) RecordTypes.ExMediaAtom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        outputStream.write(this._recdata);
    }

    public int getObjectId() {
        return LittleEndian.getInt(this._recdata, 0);
    }

    public void setObjectId(int i) {
        LittleEndian.putInt(this._recdata, 0, i);
    }

    public int getMask() {
        return LittleEndian.getInt(this._recdata, 4);
    }

    public void setMask(int i) {
        LittleEndian.putInt(this._recdata, 4, i);
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
        stringBuffer.append("ExMediaAtom\n");
        stringBuffer.append("\tObjectId: " + getObjectId() + "\n");
        stringBuffer.append("\tMask    : " + getMask() + "\n");
        stringBuffer.append("\t  fLoop        : " + getFlag(1) + "\n");
        stringBuffer.append("\t  fRewind   : " + getFlag(2) + "\n");
        stringBuffer.append("\t  fNarration    : " + getFlag(4) + "\n");
        return stringBuffer.toString();
    }

    public void dispose() {
        this._header = null;
        this._recdata = null;
    }
}
