package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public class ExOleObjAtom extends RecordAtom {
    public static final int DRAW_ASPECT_DOCPRINT = 8;
    public static final int DRAW_ASPECT_ICON = 4;
    public static final int DRAW_ASPECT_THUMBNAIL = 2;
    public static final int DRAW_ASPECT_VISIBLE = 1;
    public static final int SUBTYPE_CLIPART_GALLERY = 1;
    public static final int SUBTYPE_DEFAULT = 0;
    public static final int SUBTYPE_EQUATION = 6;
    public static final int SUBTYPE_EXCEL = 3;
    public static final int SUBTYPE_EXCEL_CHART = 14;
    public static final int SUBTYPE_GRAPH = 4;
    public static final int SUBTYPE_IMAGE = 9;
    public static final int SUBTYPE_MEDIA_PLAYER = 15;
    public static final int SUBTYPE_NOTEIT = 13;
    public static final int SUBTYPE_ORGANIZATION_CHART = 5;
    public static final int SUBTYPE_POWERPOINT_PRESENTATION = 10;
    public static final int SUBTYPE_POWERPOINT_SLIDE = 11;
    public static final int SUBTYPE_PROJECT = 12;
    public static final int SUBTYPE_SOUND = 8;
    public static final int SUBTYPE_WORDART = 7;
    public static final int SUBTYPE_WORD_TABLE = 2;
    public static final int TYPE_CONTROL = 2;
    public static final int TYPE_EMBEDDED = 0;
    public static final int TYPE_LINKED = 1;
    private byte[] _data;
    private byte[] _header;

    public ExOleObjAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this._data = new byte[24];
        LittleEndian.putShort(bArr, 0, 1);
        LittleEndian.putShort(this._header, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    protected ExOleObjAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
        if (this._data.length < 24) {
            throw new IllegalArgumentException("The length of the data for a ExOleObjAtom must be at least 24 bytes, but was only " + this._data.length);
        }
    }

    public int getDrawAspect() {
        return LittleEndian.getInt(this._data, 0);
    }

    public void setDrawAspect(int i) {
        LittleEndian.putInt(this._data, 0, i);
    }

    public int getType() {
        return LittleEndian.getInt(this._data, 4);
    }

    public void setType(int i) {
        LittleEndian.putInt(this._data, 4, i);
    }

    public int getObjID() {
        return LittleEndian.getInt(this._data, 8);
    }

    public void setObjID(int i) {
        LittleEndian.putInt(this._data, 8, i);
    }

    public int getSubType() {
        return LittleEndian.getInt(this._data, 12);
    }

    public void setSubType(int i) {
        LittleEndian.putInt(this._data, 12, i);
    }

    public int getObjStgDataRef() {
        return LittleEndian.getInt(this._data, 16);
    }

    public void setObjStgDataRef(int i) {
        LittleEndian.putInt(this._data, 16, i);
    }

    public boolean getIsBlank() {
        return LittleEndian.getInt(this._data, 20) != 0;
    }

    public int getOptions() {
        return LittleEndian.getInt(this._data, 20);
    }

    public void setOptions(int i) {
        LittleEndian.putInt(this._data, 20, i);
    }

    public long getRecordType() {
        return (long) RecordTypes.ExOleObjAtom.typeID;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ExOleObjAtom\n");
        stringBuffer.append("  drawAspect: " + getDrawAspect() + "\n");
        stringBuffer.append("  type: " + getType() + "\n");
        stringBuffer.append("  objID: " + getObjID() + "\n");
        stringBuffer.append("  subType: " + getSubType() + "\n");
        stringBuffer.append("  objStgDataRef: " + getObjStgDataRef() + "\n");
        stringBuffer.append("  options: " + getOptions() + "\n");
        return stringBuffer.toString();
    }

    public void dispose() {
        this._header = null;
        this._data = null;
    }
}
