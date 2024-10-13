package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetPolyFillMode extends EMFTag implements EMFConstants {
    private int mode;

    private int getWindingRule(int i) {
        return (i != 2 && i == 1) ? 1 : 0;
    }

    public SetPolyFillMode() {
        super(19, 1);
    }

    public SetPolyFillMode(int i) {
        this();
        this.mode = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetPolyFillMode(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  mode: " + this.mode;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setWindingRule(getWindingRule(this.mode));
    }
}
