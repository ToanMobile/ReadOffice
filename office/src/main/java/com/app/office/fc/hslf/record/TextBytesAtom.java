package com.app.office.fc.hslf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.StringUtil;

public final class TextBytesAtom extends RecordAtom {
    private static long _type = 4008;
    private byte[] _header;
    private byte[] _text;

    public String getText() {
        byte[] bArr = this._text;
        return StringUtil.getFromCompressedUnicode(bArr, 0, bArr.length);
    }

    public void setText(byte[] bArr) {
        this._text = bArr;
        LittleEndian.putInt(this._header, 4, bArr.length);
    }

    protected TextBytesAtom(byte[] bArr, int i, int i2) {
        i2 = i2 < 8 ? 8 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._text = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public TextBytesAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 0);
        LittleEndian.putUShort(this._header, 2, (int) _type);
        LittleEndian.putInt(this._header, 4, 0);
        this._text = new byte[0];
    }

    public long getRecordType() {
        return _type;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("TextBytesAtom:\n");
        stringBuffer.append(HexDump.dump(this._text, 0, 0));
        return stringBuffer.toString();
    }

    public void dispose() {
        this._header = null;
        this._text = null;
    }
}
