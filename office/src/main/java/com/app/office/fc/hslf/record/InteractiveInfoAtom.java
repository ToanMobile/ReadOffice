package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public class InteractiveInfoAtom extends RecordAtom {
    public static final byte ACTION_CUSTOMSHOW = 7;
    public static final byte ACTION_HYPERLINK = 4;
    public static final byte ACTION_JUMP = 3;
    public static final byte ACTION_MACRO = 1;
    public static final byte ACTION_MEDIA = 6;
    public static final byte ACTION_NONE = 0;
    public static final byte ACTION_OLE = 5;
    public static final byte ACTION_RUNPROGRAM = 2;
    public static final byte JUMP_ENDSHOW = 6;
    public static final byte JUMP_FIRSTSLIDE = 3;
    public static final byte JUMP_LASTSLIDE = 4;
    public static final byte JUMP_LASTSLIDEVIEWED = 5;
    public static final byte JUMP_NEXTSLIDE = 1;
    public static final byte JUMP_NONE = 0;
    public static final byte JUMP_PREVIOUSSLIDE = 2;
    public static final byte LINK_CustomShow = 6;
    public static final byte LINK_FirstSlide = 2;
    public static final byte LINK_LastSlide = 3;
    public static final byte LINK_NULL = -1;
    public static final byte LINK_NextSlide = 0;
    public static final byte LINK_OtherFile = 10;
    public static final byte LINK_OtherPresentation = 9;
    public static final byte LINK_PreviousSlide = 1;
    public static final byte LINK_SlideNumber = 7;
    public static final byte LINK_Url = 8;
    private byte[] _data;
    private byte[] _header;

    protected InteractiveInfoAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this._data = new byte[16];
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    protected InteractiveInfoAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
        if (this._data.length < 16) {
            throw new IllegalArgumentException("The length of the data for a InteractiveInfoAtom must be at least 16 bytes, but was only " + this._data.length);
        }
    }

    public int getHyperlinkID() {
        return LittleEndian.getInt(this._data, 4);
    }

    public void setHyperlinkID(int i) {
        LittleEndian.putInt(this._data, 4, i);
    }

    public int getSoundRef() {
        return LittleEndian.getInt(this._data, 0);
    }

    public void setSoundRef(int i) {
        LittleEndian.putInt(this._data, 0, i);
    }

    public byte getAction() {
        return this._data[8];
    }

    public void setAction(byte b) {
        this._data[8] = b;
    }

    public byte getOleVerb() {
        return this._data[9];
    }

    public void setOleVerb(byte b) {
        this._data[9] = b;
    }

    public byte getJump() {
        return this._data[10];
    }

    public void setJump(byte b) {
        this._data[10] = b;
    }

    public byte getFlags() {
        return this._data[11];
    }

    public void setFlags(byte b) {
        this._data[11] = b;
    }

    public byte getHyperlinkType() {
        return this._data[12];
    }

    public void setHyperlinkType(byte b) {
        this._data[12] = b;
    }

    public long getRecordType() {
        return (long) RecordTypes.InteractiveInfoAtom.typeID;
    }

    public void dispose() {
        this._header = null;
        this._data = null;
    }
}
