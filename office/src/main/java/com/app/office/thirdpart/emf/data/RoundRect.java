package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.RoundRectangle2D;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class RoundRect extends EMFTag {
    private Rectangle bounds;
    private Dimension corner;

    public RoundRect() {
        super(44, 1);
    }

    public RoundRect(Rectangle rectangle, Dimension dimension) {
        this();
        this.bounds = rectangle;
        this.corner = dimension;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new RoundRect(eMFInputStream.readRECTL(), eMFInputStream.readSIZEL());
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds + "\n  corner: " + this.corner;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.fillAndDrawOrAppend(new RoundRectangle2D.Double((double) this.bounds.x, (double) this.bounds.x, this.bounds.getWidth(), this.bounds.getHeight(), this.corner.getWidth(), this.corner.getHeight()));
    }
}
