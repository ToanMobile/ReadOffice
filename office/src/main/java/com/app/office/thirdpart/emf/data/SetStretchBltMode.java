package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetStretchBltMode extends EMFTag implements EMFConstants {
    private int mode;

    private int getScaleMode(int i) {
        if (i == 3) {
            return 2;
        }
        if (i == 4) {
            return 4;
        }
        return (i == 1 || i == 2) ? 8 : 1;
    }

    public SetStretchBltMode() {
        super(21, 1);
    }

    public SetStretchBltMode(int i) {
        this();
        this.mode = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetStretchBltMode(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  mode: " + this.mode;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setScaleMode(getScaleMode(this.mode));
    }
}
