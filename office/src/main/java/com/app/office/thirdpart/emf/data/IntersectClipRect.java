package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class IntersectClipRect extends EMFTag {
    private Rectangle bounds;

    public IntersectClipRect() {
        super(30, 1);
    }

    public IntersectClipRect(Rectangle rectangle) {
        this();
        this.bounds = rectangle;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new IntersectClipRect(eMFInputStream.readRECTL());
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.clip(this.bounds);
    }
}
