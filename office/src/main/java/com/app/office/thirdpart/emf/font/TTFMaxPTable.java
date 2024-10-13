package com.app.office.thirdpart.emf.font;

import java.io.IOException;

public class TTFMaxPTable extends TTFVersionTable {
    public int maxComponentDepth;
    public int maxComponentElements;
    public int maxCompositeContours;
    public int maxCompositePoints;
    public int maxContours;
    public int maxFunctionDefs;
    public int maxInstructionDefs;
    public int maxPoints;
    public int maxSizeOfInstructions;
    public int maxStackElements;
    public int maxStorage;
    public int maxTwilightPoints;
    public int maxZones;
    public int numGlyphs;

    public String getTag() {
        return "maxp";
    }

    public void readTable() throws IOException {
        readVersion();
        this.numGlyphs = this.ttf.readUShort();
        this.maxPoints = this.ttf.readUShort();
        this.maxContours = this.ttf.readUShort();
        this.maxCompositePoints = this.ttf.readUShort();
        this.maxCompositeContours = this.ttf.readUShort();
        this.maxZones = this.ttf.readUShort();
        this.maxTwilightPoints = this.ttf.readUShort();
        this.maxStorage = this.ttf.readUShort();
        this.maxFunctionDefs = this.ttf.readUShort();
        this.maxInstructionDefs = this.ttf.readUShort();
        this.maxStackElements = this.ttf.readUShort();
        this.maxSizeOfInstructions = this.ttf.readUShort();
        this.maxComponentElements = this.ttf.readUShort();
        this.maxComponentDepth = this.ttf.readUShort();
    }

    public String toString() {
        return super.toString() + "\n  numGlyphs: " + this.numGlyphs;
    }
}
