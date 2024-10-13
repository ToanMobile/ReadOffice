package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFFileSystem;
import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.IOException;

@Internal
public final class FontTable {
    private static final POILogger _logger = POILogFactory.getLogger(FontTable.class);
    private short _extraDataSz;
    private Ffn[] _fontNames = null;
    private short _stringCount;
    private int fcSttbfffn;
    private int lcbSttbfffn;

    public FontTable(byte[] bArr, int i, int i2) {
        this.lcbSttbfffn = i2;
        this.fcSttbfffn = i;
        this._stringCount = LittleEndian.getShort(bArr, i);
        int i3 = i + 2;
        this._extraDataSz = LittleEndian.getShort(bArr, i3);
        int i4 = i3 + 2;
        this._fontNames = new Ffn[this._stringCount];
        for (int i5 = 0; i5 < this._stringCount; i5++) {
            this._fontNames[i5] = new Ffn(bArr, i4);
            i4 += this._fontNames[i5].getSize();
        }
    }

    public short getStringCount() {
        return this._stringCount;
    }

    public short getExtraDataSz() {
        return this._extraDataSz;
    }

    public Ffn[] getFontNames() {
        return this._fontNames;
    }

    public int getSize() {
        return this.lcbSttbfffn;
    }

    public String getMainFont(int i) {
        if (i < this._stringCount) {
            return this._fontNames[i].getMainFontName();
        }
        _logger.log(POILogger.INFO, (Object) "Mismatch in chpFtc with stringCount");
        return null;
    }

    public String getAltFont(int i) {
        if (i < this._stringCount) {
            return this._fontNames[i].getAltFontName();
        }
        _logger.log(POILogger.INFO, (Object) "Mismatch in chpFtc with stringCount");
        return null;
    }

    public void setStringCount(short s) {
        this._stringCount = s;
    }

    @Deprecated
    public void writeTo(HWPFFileSystem hWPFFileSystem) throws IOException {
        writeTo(hWPFFileSystem.getStream("1Table"));
    }

    public void writeTo(HWPFOutputStream hWPFOutputStream) throws IOException {
        byte[] bArr = new byte[2];
        LittleEndian.putShort(bArr, this._stringCount);
        hWPFOutputStream.write(bArr);
        LittleEndian.putShort(bArr, this._extraDataSz);
        hWPFOutputStream.write(bArr);
        int i = 0;
        while (true) {
            Ffn[] ffnArr = this._fontNames;
            if (i < ffnArr.length) {
                hWPFOutputStream.write(ffnArr[i].toByteArray());
                i++;
            } else {
                return;
            }
        }
    }

    public boolean equals(Object obj) {
        FontTable fontTable = (FontTable) obj;
        if (fontTable.getStringCount() != this._stringCount || fontTable.getExtraDataSz() != this._extraDataSz) {
            return false;
        }
        Ffn[] fontNames = fontTable.getFontNames();
        boolean z = true;
        for (int i = 0; i < this._stringCount; i++) {
            if (!this._fontNames[i].equals(fontNames[i])) {
                z = false;
            }
        }
        return z;
    }
}
