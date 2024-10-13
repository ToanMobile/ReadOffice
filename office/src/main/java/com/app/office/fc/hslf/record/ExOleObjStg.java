package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ExOleObjStg extends RecordAtom implements PositionDependentRecord, PersistRecord {
    private byte[] _data;
    private byte[] _header;
    private int _persistId;
    protected int myLastOnDiskOffset;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public ExOleObjStg() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this._data = new byte[0];
        LittleEndian.putShort(bArr, 0, 16);
        LittleEndian.putShort(this._header, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    protected ExOleObjStg(byte[] bArr, int i, int i2) {
    }

    public int getDataLength() {
        return LittleEndian.getInt(this._data, 0);
    }

    public InputStream getData() {
        byte[] bArr = this._data;
        return new InflaterInputStream(new ByteArrayInputStream(bArr, 4, bArr.length));
    }

    public byte[] getRawData() {
        return this._data;
    }

    public void setData(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[4];
        LittleEndian.putInt(bArr2, bArr.length);
        byteArrayOutputStream.write(bArr2);
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
        deflaterOutputStream.write(bArr, 0, bArr.length);
        deflaterOutputStream.finish();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this._data = byteArray;
        LittleEndian.putInt(this._header, 4, byteArray.length);
    }

    public long getRecordType() {
        return (long) RecordTypes.ExOleObjStg.typeID;
    }

    public int getPersistId() {
        return this._persistId;
    }

    public void setPersistId(int i) {
        this._persistId = i;
    }

    public int getLastOnDiskOffset() {
        return this.myLastOnDiskOffset;
    }

    public void setLastOnDiskOffset(int i) {
        this.myLastOnDiskOffset = i;
    }

    public void dispose() {
        this._header = null;
        this._data = null;
    }
}
