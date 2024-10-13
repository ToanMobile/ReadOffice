package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ExcludeClipRect extends EMFTag {
    private Rectangle bounds;

    public ExcludeClipRect() {
        super(29, 1);
    }

    public ExcludeClipRect(Rectangle rectangle) {
        this();
        this.bounds = rectangle;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ExcludeClipRect(eMFInputStream.readRECTL());
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds;
    }
}
