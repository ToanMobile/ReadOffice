package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public class ExEmbedAtom extends RecordAtom {
    public static final int DOES_NOT_FOLLOW_COLOR_SCHEME = 0;
    public static final int FOLLOWS_ENTIRE_COLOR_SCHEME = 1;
    public static final int FOLLOWS_TEXT_AND_BACKGROUND_SCHEME = 2;
    private byte[] _data;
    private byte[] _header;

    protected ExEmbedAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this._data = new byte[7];
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    protected ExEmbedAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
        if (this._data.length < 7) {
            throw new IllegalArgumentException("The length of the data for a ExEmbedAtom must be at least 4 bytes, but was only " + this._data.length);
        }
    }

    public int getFollowColorScheme() {
        return LittleEndian.getInt(this._data, 0);
    }

    public boolean getCantLockServerB() {
        return this._data[4] != 0;
    }

    public boolean getNoSizeToServerB() {
        return this._data[5] != 0;
    }

    public boolean getIsTable() {
        return this._data[6] != 0;
    }

    public long getRecordType() {
        return (long) RecordTypes.ExEmbedAtom.typeID;
    }

    public void dispose() {
        this._header = null;
        this._data = null;
    }
}
