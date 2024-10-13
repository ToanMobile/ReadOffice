package com.app.office.thirdpart.emf.font;

import java.io.IOException;

public class TTFOS_2Table extends TTFVersionTable {
    public byte[] achVendID = new byte[4];
    public int fsSelection;
    public short fsType;
    public byte[] panose = new byte[10];
    public short sFamilyClass;
    public int sTypoAscender;
    public int sTypoLineGap;
    public int sTzpoDescender;
    public long[] ulCodePageRange = new long[2];
    public long[] ulUnicode = new long[4];
    public int usFirstCharIndex;
    public int usLastCharIndes;
    public int usWeightClass;
    public int usWidthClass;
    public int usWinAscent;
    public int usWinDescent;
    public int version;
    public short xAvgCharWidth;
    public short yStrikeoutPosition;
    public short yStrikeoutSize;
    public short ySubscriptXOffset;
    public short ySubscriptXSize;
    public short ySubscriptYOffset;
    public short ySubscriptYSize;
    public short ySuperscriptXOffset;
    public short ySuperscriptXSize;
    public short ySuperscriptYOffset;
    public short ySuperscriptYSize;

    public String getTag() {
        return "OS/2";
    }

    public void readTable() throws IOException {
        this.version = this.ttf.readUShort();
        this.xAvgCharWidth = this.ttf.readShort();
        this.usWeightClass = this.ttf.readUShort();
        this.usWidthClass = this.ttf.readUShort();
        this.fsType = this.ttf.readShort();
        this.ySubscriptXSize = this.ttf.readShort();
        this.ySubscriptYSize = this.ttf.readShort();
        this.ySubscriptXOffset = this.ttf.readShort();
        this.ySubscriptYOffset = this.ttf.readShort();
        this.ySuperscriptXSize = this.ttf.readShort();
        this.ySuperscriptYSize = this.ttf.readShort();
        this.ySuperscriptXOffset = this.ttf.readShort();
        this.ySuperscriptYOffset = this.ttf.readShort();
        this.yStrikeoutSize = this.ttf.readShort();
        this.yStrikeoutPosition = this.ttf.readShort();
        this.sFamilyClass = this.ttf.readShort();
        this.ttf.readFully(this.panose);
        int i = 0;
        while (true) {
            long[] jArr = this.ulUnicode;
            if (i < jArr.length) {
                jArr[i] = this.ttf.readULong();
                i++;
            } else {
                this.ttf.readFully(this.achVendID);
                this.fsSelection = this.ttf.readUShort();
                this.usFirstCharIndex = this.ttf.readUShort();
                this.usLastCharIndes = this.ttf.readUShort();
                this.sTypoAscender = this.ttf.readUShort();
                this.sTzpoDescender = this.ttf.readUShort();
                this.sTypoLineGap = this.ttf.readUShort();
                this.usWinAscent = this.ttf.readUShort();
                this.usWinDescent = this.ttf.readUShort();
                this.ulCodePageRange[0] = this.ttf.readULong();
                this.ulCodePageRange[1] = this.ttf.readULong();
                return;
            }
        }
    }

    public String getAchVendID() {
        return new String(this.achVendID);
    }

    public String toString() {
        return super.toString() + "\n  version: " + this.version + "\n  vendor: " + getAchVendID();
    }
}
