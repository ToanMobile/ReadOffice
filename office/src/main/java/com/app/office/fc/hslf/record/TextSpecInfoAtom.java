package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public final class TextSpecInfoAtom extends RecordAtom {
    private byte[] _data;
    private byte[] _header;

    protected TextSpecInfoAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public long getRecordType() {
        return (long) RecordTypes.TextSpecInfoAtom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        outputStream.write(this._data);
    }

    public void setTextSize(int i) {
        LittleEndian.putInt(this._data, 0, i);
    }

    public void reset(int i) {
        byte[] bArr = new byte[10];
        this._data = bArr;
        LittleEndian.putInt(bArr, 0, i);
        LittleEndian.putInt(this._data, 4, 1);
        LittleEndian.putShort(this._data, 8, 0);
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    public int getCharactersCovered() {
        TextSpecInfoRun[] textSpecInfoRuns = getTextSpecInfoRuns();
        int i = 0;
        for (TextSpecInfoRun textSpecInfoRun : textSpecInfoRuns) {
            i += textSpecInfoRun.len;
        }
        return i;
    }

    public TextSpecInfoRun[] getTextSpecInfoRuns() {
        ArrayList arrayList = new ArrayList();
        int[] iArr = {1, 0, 2};
        int i = 0;
        while (i < this._data.length) {
            TextSpecInfoRun textSpecInfoRun = new TextSpecInfoRun();
            textSpecInfoRun.len = LittleEndian.getInt(this._data, i);
            int i2 = i + 4;
            textSpecInfoRun.mask = LittleEndian.getInt(this._data, i2);
            i = i2 + 4;
            for (int i3 = 0; i3 < 3; i3++) {
                if ((textSpecInfoRun.mask & (1 << iArr[i3])) != 0) {
                    int i4 = iArr[i3];
                    if (i4 == 0) {
                        textSpecInfoRun.spellInfo = LittleEndian.getShort(this._data, i);
                    } else if (i4 == 1) {
                        textSpecInfoRun.langId = LittleEndian.getShort(this._data, i);
                    } else if (i4 == 2) {
                        textSpecInfoRun.altLangId = LittleEndian.getShort(this._data, i);
                    }
                    i += 2;
                }
            }
            arrayList.add(textSpecInfoRun);
        }
        return (TextSpecInfoRun[]) arrayList.toArray(new TextSpecInfoRun[arrayList.size()]);
    }

    public static class TextSpecInfoRun {
        protected short altLangId = -1;
        protected short langId = -1;
        protected int len;
        protected int mask;
        protected short spellInfo = -1;

        public short getSpellInfo() {
            return this.spellInfo;
        }

        public short getLangId() {
            return this.spellInfo;
        }

        public short getAltLangId() {
            return this.altLangId;
        }

        public int length() {
            return this.len;
        }
    }

    public void dispose() {
        this._header = null;
        this._data = null;
    }
}
