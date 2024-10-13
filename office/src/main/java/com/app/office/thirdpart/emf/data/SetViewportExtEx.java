package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Dimension;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetViewportExtEx extends EMFTag {
    private Dimension size;

    public SetViewportExtEx() {
        super(11, 1);
    }

    public SetViewportExtEx(Dimension dimension) {
        this();
        this.size = dimension;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetViewportExtEx(eMFInputStream.readSIZEL());
    }

    public String toString() {
        return super.toString() + "\n  size: " + this.size;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setViewportSize(this.size);
    }
}
