package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetICMMode extends EMFTag implements EMFConstants {
    private int mode;

    public void render(EMFRenderer eMFRenderer) {
    }

    public SetICMMode() {
        super(98, 1);
    }

    public SetICMMode(int i) {
        this();
        this.mode = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetICMMode(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  mode: " + this.mode;
    }
}
