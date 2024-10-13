package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class BeginPath extends EMFTag {
    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return this;
    }

    public BeginPath() {
        super(59, 1);
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setPath(new GeneralPath(eMFRenderer.getWindingRule()));
        eMFRenderer.setPathTransform(new AffineTransform());
    }
}
