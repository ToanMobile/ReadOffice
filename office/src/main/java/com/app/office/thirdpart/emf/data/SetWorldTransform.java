package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetWorldTransform extends EMFTag {
    private AffineTransform transform;

    public SetWorldTransform() {
        super(35, 1);
    }

    public SetWorldTransform(AffineTransform affineTransform) {
        this();
        this.transform = affineTransform;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetWorldTransform(eMFInputStream.readXFORM());
    }

    public String toString() {
        return super.toString() + "\n  transform: " + this.transform;
    }

    public void render(EMFRenderer eMFRenderer) {
        if (eMFRenderer.getPath() != null) {
            eMFRenderer.setPathTransform(this.transform);
            return;
        }
        eMFRenderer.resetTransformation();
        eMFRenderer.transform(this.transform);
    }
}
