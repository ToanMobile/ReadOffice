package com.app.office.thirdpart.emf.font;

import java.io.IOException;

public class TTFLocaTable extends TTFTable {
    public long[] offset;

    public String getTag() {
        return "loca";
    }

    public void readTable() throws IOException {
        long j;
        short s = ((TTFHeadTable) getTable("head")).indexToLocFormat;
        int i = ((TTFMaxPTable) getTable("maxp")).numGlyphs + 1;
        this.offset = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            long[] jArr = this.offset;
            if (s == 1) {
                j = this.ttf.readULong();
            } else {
                j = (long) (this.ttf.readUShort() * 2);
            }
            jArr[i2] = j;
        }
    }

    public String toString() {
        String tTFTable = super.toString();
        for (int i = 0; i < this.offset.length; i++) {
            if (i % 16 == 0) {
                tTFTable = tTFTable + "\n  ";
            }
            tTFTable = tTFTable + this.offset[i] + " ";
        }
        return tTFTable;
    }
}
