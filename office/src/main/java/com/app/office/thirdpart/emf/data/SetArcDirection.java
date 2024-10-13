package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetArcDirection extends EMFTag implements EMFConstants {
    private int direction;

    public SetArcDirection() {
        super(57, 1);
    }

    public SetArcDirection(int i) {
        this();
        this.direction = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetArcDirection(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  direction: " + this.direction;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setArcDirection(this.direction);
    }
}
