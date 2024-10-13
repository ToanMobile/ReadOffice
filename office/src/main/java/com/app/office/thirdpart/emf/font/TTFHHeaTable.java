package com.app.office.thirdpart.emf.font;

import java.io.IOException;

public class TTFHHeaTable extends TTFVersionTable {
    public int advanceWidthMax;
    public short ascender;
    public short caretSlopeRise;
    public short caretSlopeRun;
    public short descender;
    public short lineGap;
    public short metricDataFormat;
    public short minLeftSideBearing;
    public short minRightSideBearing;
    public int numberOfHMetrics;
    public short xMaxExtent;

    public String getTag() {
        return "hhea";
    }

    public void readTable() throws IOException {
        readVersion();
        this.ascender = this.ttf.readFWord();
        this.descender = this.ttf.readFWord();
        this.lineGap = this.ttf.readFWord();
        this.advanceWidthMax = this.ttf.readUFWord();
        this.minLeftSideBearing = this.ttf.readFWord();
        this.minRightSideBearing = this.ttf.readFWord();
        this.xMaxExtent = this.ttf.readFWord();
        this.caretSlopeRise = this.ttf.readShort();
        this.caretSlopeRun = this.ttf.readShort();
        for (int i = 0; i < 5; i++) {
            this.ttf.checkShortZero();
        }
        this.metricDataFormat = this.ttf.readShort();
        this.numberOfHMetrics = this.ttf.readUShort();
    }

    public String toString() {
        return (super.toString() + "\n  asc:" + this.ascender + " desc:" + this.descender + " lineGap:" + this.lineGap + " maxAdvance:" + this.advanceWidthMax) + "\n  metricDataFormat:" + this.metricDataFormat + " #HMetrics:" + this.numberOfHMetrics;
    }
}
