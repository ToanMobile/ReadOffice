package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class SlidePersistAtom extends RecordAtom {
    private static long _type = 1011;
    private byte[] _header;
    private boolean hasShapesOtherThanPlaceholders;
    private int numPlaceholderTexts;
    private int refID;
    private byte[] reservedFields;
    private int slideIdentifier;

    public int getRefID() {
        return this.refID;
    }

    public int getSlideIdentifier() {
        return this.slideIdentifier;
    }

    public int getNumPlaceholderTexts() {
        return this.numPlaceholderTexts;
    }

    public boolean getHasShapesOtherThanPlaceholders() {
        return this.hasShapesOtherThanPlaceholders;
    }

    public void setRefID(int i) {
        this.refID = i;
    }

    public void setSlideIdentifier(int i) {
        this.slideIdentifier = i;
    }

    protected SlidePersistAtom(byte[] bArr, int i, int i2) {
        i2 = i2 < 8 ? 8 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.refID = LittleEndian.getInt(bArr, i + 8);
        if (LittleEndian.getInt(bArr, i + 12) == 4) {
            this.hasShapesOtherThanPlaceholders = true;
        } else {
            this.hasShapesOtherThanPlaceholders = false;
        }
        this.numPlaceholderTexts = LittleEndian.getInt(bArr, i + 16);
        this.slideIdentifier = LittleEndian.getInt(bArr, i + 20);
        byte[] bArr3 = new byte[(i2 - 24)];
        this.reservedFields = bArr3;
        System.arraycopy(bArr, i + 24, bArr3, 0, bArr3.length);
    }

    public SlidePersistAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 0);
        LittleEndian.putUShort(this._header, 2, (int) _type);
        LittleEndian.putInt(this._header, 4, 20);
        this.hasShapesOtherThanPlaceholders = true;
        this.reservedFields = new byte[4];
    }

    public long getRecordType() {
        return _type;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        int i = this.hasShapesOtherThanPlaceholders ? 4 : 0;
        writeLittleEndian(this.refID, outputStream);
        writeLittleEndian(i, outputStream);
        writeLittleEndian(this.numPlaceholderTexts, outputStream);
        writeLittleEndian(this.slideIdentifier, outputStream);
        outputStream.write(this.reservedFields);
    }

    public void dispose() {
        this._header = null;
        this.reservedFields = null;
    }
}
