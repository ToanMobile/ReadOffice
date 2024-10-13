package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetMetaRgn extends EMFTag {
    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return this;
    }

    public void render(EMFRenderer eMFRenderer) {
    }

    public SetMetaRgn() {
        super(28, 1);
    }
}
