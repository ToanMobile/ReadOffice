package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.StringUtil;

public final class CString extends RecordAtom {
    private static long _type = 4026;
    private byte[] _header;
    private byte[] _text;

    public String getText() {
        return StringUtil.getFromUnicodeLE(this._text);
    }

    public void setText(String str) {
        byte[] bArr = new byte[(str.length() * 2)];
        this._text = bArr;
        StringUtil.putUnicodeLE(str, bArr, 0);
        LittleEndian.putInt(this._header, 4, this._text.length);
    }

    public int getOptions() {
        return LittleEndian.getShort(this._header);
    }

    public void setOptions(int i) {
        LittleEndian.putShort(this._header, (short) i);
    }

    protected CString(byte[] bArr, int i, int i2) {
        i2 = i2 < 8 ? 8 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._text = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public CString() {
        this._header = new byte[]{0, 0, -70, 15, 0, 0, 0, 0};
        this._text = new byte[0];
    }

    public long getRecordType() {
        return _type;
    }

    public String toString() {
        return getText();
    }

    public void dispose() {
        this._header = null;
        this._text = null;
    }
}
