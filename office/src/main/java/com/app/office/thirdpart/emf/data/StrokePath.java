package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class StrokePath extends EMFTag {
    private Rectangle bounds;

    public StrokePath() {
        super(64, 1);
    }

    public StrokePath(Rectangle rectangle) {
        this();
        this.bounds = rectangle;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new StrokePath(eMFInputStream.readRECTL());
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds;
    }

    public void render(EMFRenderer eMFRenderer) {
        GeneralPath path = eMFRenderer.getPath();
        if (path != null) {
            eMFRenderer.drawShape(path);
            eMFRenderer.setPath((GeneralPath) null);
        }
    }
}
