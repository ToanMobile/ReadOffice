package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SelectPalette extends EMFTag {
    private int index;

    public void render(EMFRenderer eMFRenderer) {
    }

    public SelectPalette() {
        super(48, 1);
    }

    public SelectPalette(int i) {
        this();
        this.index = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SelectPalette(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index);
    }
}
