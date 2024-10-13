package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class EMFRectangle extends EMFTag {
    private Rectangle bounds;

    public EMFRectangle() {
        super(43, 1);
    }

    public EMFRectangle(Rectangle rectangle) {
        this();
        this.bounds = rectangle;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new EMFRectangle(eMFInputStream.readRECTL());
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.fillAndDrawOrAppend(this.bounds);
    }
}
