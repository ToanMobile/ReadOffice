package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class CloseFigure extends EMFTag {
    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return this;
    }

    public CloseFigure() {
        super(61, 1);
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.closeFigure();
    }
}
