package com.app.office.fc.hslf.record;

import com.app.office.fc.util.POILogger;
import java.util.ArrayList;
import java.util.List;

public final class FontCollection extends RecordContainer {
    private byte[] _header;
    private List<String> fonts = new ArrayList();

    protected FontCollection(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        for (int i3 = 0; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof FontEntityAtom) {
                this.fonts.add(((FontEntityAtom) this._children[i3]).getFontName());
            } else {
                POILogger pOILogger = this.logger;
                int i4 = POILogger.WARN;
                pOILogger.log(i4, (Object) "Warning: FontCollection child wasn't a FontEntityAtom, was " + this._children[i3]);
            }
        }
    }

    public long getRecordType() {
        return (long) RecordTypes.FontCollection.typeID;
    }

    public int addFont(String str) {
        int fontIndex = getFontIndex(str);
        if (fontIndex != -1) {
            return fontIndex;
        }
        return addFont(str, 0, 0, 4, 34);
    }

    public int addFont(String str, int i, int i2, int i3, int i4) {
        FontEntityAtom fontEntityAtom = new FontEntityAtom();
        fontEntityAtom.setFontIndex(this.fonts.size() << 4);
        fontEntityAtom.setFontName(str);
        fontEntityAtom.setCharSet(i);
        fontEntityAtom.setFontFlags(i2);
        fontEntityAtom.setFontType(i3);
        fontEntityAtom.setPitchAndFamily(i4);
        this.fonts.add(str);
        appendChildRecord(fontEntityAtom);
        return this.fonts.size() - 1;
    }

    public int getFontIndex(String str) {
        for (int i = 0; i < this.fonts.size(); i++) {
            if (this.fonts.get(i).equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public int getNumberOfFonts() {
        return this.fonts.size();
    }

    public String getFontWithId(int i) {
        if (i >= this.fonts.size()) {
            return null;
        }
        return this.fonts.get(i);
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        List<String> list = this.fonts;
        if (list != null) {
            list.clear();
            this.fonts = null;
        }
    }
}
