package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ModifyWorldTransform extends EMFTag implements EMFConstants {
    private int mode;
    private AffineTransform transform;

    public ModifyWorldTransform() {
        super(36, 1);
    }

    public ModifyWorldTransform(AffineTransform affineTransform, int i) {
        this();
        this.transform = affineTransform;
        this.mode = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ModifyWorldTransform(eMFInputStream.readXFORM(), eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  transform: " + this.transform + "\n  mode: " + this.mode;
    }

    public void render(EMFRenderer eMFRenderer) {
        int i = this.mode;
        if (i == 1) {
            if (eMFRenderer.getPath() != null) {
                eMFRenderer.setPathTransform(new AffineTransform());
            } else {
                eMFRenderer.resetTransformation();
            }
        } else if (i != 2) {
        } else {
            if (eMFRenderer.getPath() != null) {
                eMFRenderer.getPathTransform().concatenate(this.transform);
                eMFRenderer.transform(this.transform);
                return;
            }
            eMFRenderer.transform(this.transform);
        }
    }
}
