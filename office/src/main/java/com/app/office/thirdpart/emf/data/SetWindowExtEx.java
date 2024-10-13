package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Dimension;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetWindowExtEx extends EMFTag {
    private Dimension size;

    public SetWindowExtEx() {
        super(9, 1);
    }

    public SetWindowExtEx(Dimension dimension) {
        this();
        this.size = dimension;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetWindowExtEx(eMFInputStream.readSIZEL());
    }

    public String toString() {
        return super.toString() + "\n  size: " + this.size;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setWindowSize(this.size);
    }
}
