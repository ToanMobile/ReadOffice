package com.app.office.thirdpart.emf.font;

import java.io.IOException;

public abstract class TTFVersionTable extends TTFTable {
    public int majorVersion;
    public int minorVersion;

    public void readVersion() throws IOException {
        this.majorVersion = this.ttf.readUShort();
        this.minorVersion = this.ttf.readUShort();
    }

    public String toString() {
        return super.toString() + " v" + this.majorVersion + "." + this.minorVersion;
    }
}
