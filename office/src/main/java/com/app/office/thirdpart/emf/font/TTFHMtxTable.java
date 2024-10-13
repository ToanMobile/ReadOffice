package com.app.office.thirdpart.emf.font;

import java.io.IOException;

public class TTFHMtxTable extends TTFTable {
    public int[] advanceWidth;
    public short[] leftSideBearing;
    public short[] leftSideBearing2;

    public String getTag() {
        return "hmtx";
    }

    public void readTable() throws IOException {
        int i = ((TTFHHeaTable) getTable("hhea")).numberOfHMetrics;
        int i2 = ((TTFMaxPTable) getTable("maxp")).numGlyphs;
        this.advanceWidth = new int[i];
        this.leftSideBearing = new short[i];
        for (int i3 = 0; i3 < i; i3++) {
            this.advanceWidth[i3] = this.ttf.readUFWord();
            this.leftSideBearing[i3] = this.ttf.readFWord();
        }
        this.leftSideBearing2 = this.ttf.readShortArray(i2 - i);
    }

    public String toString() {
        String str = super.toString() + "\n  hMetrics[" + this.advanceWidth.length + "] = {";
        for (int i = 0; i < this.advanceWidth.length; i++) {
            if (i % 8 == 0) {
                str = str + "\n    ";
            }
            str = str + "(" + this.advanceWidth[i] + "," + this.leftSideBearing[i] + ") ";
        }
        String str2 = (str + "\n  }") + "\n  lsb[" + this.leftSideBearing2.length + "] = {";
        for (int i2 = 0; i2 < this.leftSideBearing2.length; i2++) {
            if (i2 % 16 == 0) {
                str2 = str2 + "\n    ";
            }
            str2 = str2 + this.leftSideBearing2[i2] + " ";
        }
        return str2 + "\n  }";
    }
}
