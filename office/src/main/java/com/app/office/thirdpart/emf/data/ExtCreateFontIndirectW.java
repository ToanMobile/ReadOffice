package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ExtCreateFontIndirectW extends EMFTag {
    private ExtLogFontW font;
    private int index;

    public ExtCreateFontIndirectW() {
        super(82, 1);
    }

    public ExtCreateFontIndirectW(int i, ExtLogFontW extLogFontW) {
        this();
        this.index = i;
        this.font = extLogFontW;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ExtCreateFontIndirectW(eMFInputStream.readDWORD(), new ExtLogFontW(eMFInputStream));
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index) + "\n" + this.font.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.storeGDIObject(this.index, this.font);
    }
}
