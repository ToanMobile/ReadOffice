package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class RestoreDC extends EMFTag {
    private int savedDC;

    public RestoreDC() {
        super(34, 1);
        this.savedDC = -1;
    }

    public RestoreDC(int i) {
        this();
        this.savedDC = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new RestoreDC(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  savedDC: " + this.savedDC;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.retoreDC();
    }
}
