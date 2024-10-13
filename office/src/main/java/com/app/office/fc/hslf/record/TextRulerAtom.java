package com.app.office.fc.hslf.record;

import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class TextRulerAtom extends RecordAtom {
    private byte[] _data;
    private byte[] _header;
    private int[] bulletOffsets = {-1, -1, -1, -1, -1};
    private int defaultTabSize;
    private int numLevels;
    private int[] tabStops;
    private int[] textOffsets = {-1, -1, -1, -1, -1};

    public TextRulerAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this._data = new byte[0];
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._data.length);
    }

    protected TextRulerAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
        try {
            read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getRecordType() {
        return (long) RecordTypes.TextRulerAtom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        outputStream.write(this._data);
    }

    private void read() {
        short s = LittleEndian.getShort(this._data);
        int[] iArr = {1, 0, 2, 3, 8, 4, 9, 5, 10, 6, 11, 7, 12};
        int i = 4;
        for (int i2 = 0; i2 < 13; i2++) {
            if (((1 << iArr[i2]) & s) != 0) {
                switch (iArr[i2]) {
                    case 0:
                        this.defaultTabSize = LittleEndian.getShort(this._data, i);
                        break;
                    case 1:
                        this.numLevels = LittleEndian.getShort(this._data, i);
                        break;
                    case 2:
                        short s2 = LittleEndian.getShort(this._data, i);
                        i += 2;
                        this.tabStops = new int[(s2 * 2)];
                        int i3 = 0;
                        while (true) {
                            int[] iArr2 = this.tabStops;
                            if (i3 < iArr2.length) {
                                iArr2[i3] = LittleEndian.getUShort(this._data, i);
                                i += 2;
                                i3++;
                            } else {
                                continue;
                            }
                        }
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        int i4 = LittleEndian.getShort(this._data, i);
                        i += 2;
                        this.bulletOffsets[iArr[i2] - 3] = i4;
                        continue;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        int i5 = LittleEndian.getShort(this._data, i);
                        i += 2;
                        this.textOffsets[iArr[i2] - 8] = i5;
                        continue;
                }
                i += 2;
            }
        }
    }

    public int getDefaultTabSize() {
        return this.defaultTabSize;
    }

    public int getNumberOfLevels() {
        return this.numLevels;
    }

    public int[] getTabStops() {
        return this.tabStops;
    }

    public int[] getTextOffsets() {
        return this.textOffsets;
    }

    public int[] getBulletOffsets() {
        return this.bulletOffsets;
    }

    public static TextRulerAtom getParagraphInstance() {
        return new TextRulerAtom(new byte[]{0, 0, -90, 15, 10, 0, 0, 0, 16, 3, 0, 0, -7, 0, Field.SECTION, 1, Field.SECTION, 1}, 0, 18);
    }

    public void setParagraphIndent(short s, short s2) {
        LittleEndian.putShort(this._data, 4, s);
        LittleEndian.putShort(this._data, 6, s2);
        LittleEndian.putShort(this._data, 8, s2);
    }

    public void dispose() {
        this._header = null;
        this._data = null;
        this.tabStops = null;
        this.textOffsets = null;
        this.bulletOffsets = null;
    }
}
