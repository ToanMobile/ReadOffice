package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ResizePalette extends EMFTag {
    private int entries;
    private int index;

    public ResizePalette() {
        super(51, 1);
    }

    public ResizePalette(int i, int i2) {
        this();
        this.index = i;
        this.entries = i2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ResizePalette(eMFInputStream.readDWORD(), eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index) + "\n  entries: " + this.entries;
    }
}
