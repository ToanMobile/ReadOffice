package com.app.office.thirdpart.emf.font;

import com.app.office.java.awt.Rectangle;
import java.io.IOException;
import java.io.PrintStream;

public class TTFHeadTable extends TTFVersionTable {
    public static final int FDH_LEFT_TO_RIGHT = 1;
    public static final int FDH_LEFT_TO_RIGHT_NEUTRAL = 2;
    public static final int FDH_MIXED = 0;
    public static final int FDH_RIGHT_TO_LEFT = -1;
    public static final int FDH_RIGHT_TO_LEFT_NEUTRAL = -2;
    public static final int ITLF_LONG = 1;
    public static final int ITLF_SHORT = 0;
    public boolean baseline0;
    public long checkSumAdjustment;
    public byte[] created = new byte[8];
    public short fontDirectionHint;
    public int fontRevisionMajor;
    public int fontRevisionMinor;
    public boolean forcePPEM2Int;
    public short glyphDataFormat;
    public short indexToLocFormat;
    public boolean instrAlterAdvance;
    public boolean instrDependOnSize;
    public int lowestRecPPEM;
    public boolean macBold;
    public boolean macItalic;
    public long magicNumber;
    public byte[] modified = new byte[8];
    public boolean sidebearing0;
    public int unitsPerEm;
    public short xMax;
    public short xMin;
    public short yMax;
    public short yMin;

    public String getTag() {
        return "head";
    }

    public void readTable() throws IOException {
        readVersion();
        this.fontRevisionMajor = this.ttf.readUShort();
        this.fontRevisionMinor = this.ttf.readUShort();
        this.checkSumAdjustment = this.ttf.readULong();
        this.magicNumber = this.ttf.readULong();
        this.ttf.readUShortFlags();
        this.baseline0 = this.ttf.flagBit(0);
        this.sidebearing0 = this.ttf.flagBit(1);
        this.instrDependOnSize = this.ttf.flagBit(2);
        this.forcePPEM2Int = this.ttf.flagBit(3);
        this.instrAlterAdvance = this.ttf.flagBit(4);
        this.unitsPerEm = this.ttf.readUShort();
        this.ttf.readFully(this.created);
        this.ttf.readFully(this.modified);
        this.xMin = this.ttf.readShort();
        this.yMin = this.ttf.readShort();
        this.xMax = this.ttf.readShort();
        this.yMax = this.ttf.readShort();
        this.ttf.readUShortFlags();
        this.macBold = this.ttf.flagBit(0);
        this.macItalic = this.ttf.flagBit(1);
        this.lowestRecPPEM = this.ttf.readUShort();
        this.fontDirectionHint = this.ttf.readShort();
        short readShort = this.ttf.readShort();
        this.indexToLocFormat = readShort;
        if (!(readShort == 1 || readShort == 0)) {
            PrintStream printStream = System.err;
            printStream.println("Unknown value for indexToLocFormat: " + this.indexToLocFormat);
        }
        this.glyphDataFormat = this.ttf.readShort();
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n  magicNumber: 0x");
        sb.append(Integer.toHexString((int) this.magicNumber));
        sb.append(" (");
        sb.append(this.magicNumber == 1594834165 ? "ok" : "wrong");
        sb.append(")\n");
        String str2 = sb.toString() + "  indexToLocFormat: " + this.indexToLocFormat + " ";
        short s = this.indexToLocFormat;
        if (s == 1) {
            str = str2 + " (long)\n";
        } else if (s == 0) {
            str = str2 + "(short)\n";
        } else {
            str = str2 + "(illegal value)\n";
        }
        return str + "  bbox: (" + this.xMin + "," + this.yMin + ") : (" + this.xMax + "," + this.yMax + ")";
    }

    public Rectangle getMaxCharBounds() {
        short s = this.xMin;
        short s2 = this.yMin;
        return new Rectangle(s, s2, this.xMax - s, this.yMax - s2);
    }
}
