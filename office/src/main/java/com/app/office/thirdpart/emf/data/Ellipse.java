package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.Ellipse2D;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class Ellipse extends EMFTag {
    private Rectangle bounds;

    public Ellipse(Rectangle rectangle) {
        this();
        this.bounds = rectangle;
    }

    public Ellipse() {
        super(42, 1);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new Ellipse(eMFInputStream.readRECTL());
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.fillAndDrawOrAppend(new Ellipse2D.Double((double) this.bounds.x, (double) this.bounds.y, this.bounds.getWidth(), this.bounds.getHeight()));
    }
}
