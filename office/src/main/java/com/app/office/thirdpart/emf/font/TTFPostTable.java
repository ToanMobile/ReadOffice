package com.app.office.thirdpart.emf.font;

import java.io.IOException;

public class TTFPostTable extends TTFTable {
    public double format;
    public int[] glyphNameIndex;
    public long isFixedPitch;
    public double italicAngle;
    public long maxMemType1;
    public long maxMemType42;
    public long minMemType1;
    public long minMemType42;
    public short underlinePosition;
    public short underlineThickness;

    public String getTag() {
        return "post";
    }

    public void readTable() throws IOException {
        this.format = this.ttf.readFixed();
        this.italicAngle = this.ttf.readFixed();
        this.underlinePosition = this.ttf.readFWord();
        this.underlineThickness = this.ttf.readFWord();
        this.isFixedPitch = this.ttf.readULong();
        this.minMemType42 = this.ttf.readULong();
        this.maxMemType42 = this.ttf.readULong();
        this.minMemType1 = this.ttf.readULong();
        this.maxMemType1 = this.ttf.readULong();
        double d = this.format;
        if (d == 2.0d) {
            this.glyphNameIndex = this.ttf.readUShortArray(this.ttf.readUShort());
        } else if (d == 2.5d) {
            System.err.println("Format 2.5 for post notimplemented yet.");
        }
    }

    public String toString() {
        String str = super.toString() + " format: " + this.format + "\n  italic:" + this.italicAngle + " ulPos:" + this.underlinePosition + " ulThick:" + this.underlineThickness + " isFixed:" + this.isFixedPitch;
        if (this.glyphNameIndex == null) {
            return str;
        }
        String str2 = str + "\n  glyphNamesIndex[" + this.glyphNameIndex.length + "] = {";
        for (int i = 0; i < this.glyphNameIndex.length; i++) {
            if (i % 16 == 0) {
                str2 = str2 + "\n    ";
            }
            str2 = str2 + this.glyphNameIndex[i] + " ";
        }
        return str2 + "\n  }";
    }
}
