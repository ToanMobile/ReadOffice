package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetMapMode extends EMFTag implements EMFConstants {
    private int mode;

    public SetMapMode() {
        super(17, 1);
    }

    public SetMapMode(int i) {
        this();
        this.mode = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetMapMode(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  mode: " + this.mode;
    }

    public void render(EMFRenderer eMFRenderer) {
        int i = this.mode;
        if (i == 8) {
            eMFRenderer.setMapModeIsotropic(false);
        } else if (i == 5) {
            eMFRenderer.setMapModeTransform(AffineTransform.getScaleInstance(0.0254d, 0.0254d));
        } else if (i == 3) {
            eMFRenderer.setMapModeTransform(AffineTransform.getScaleInstance(0.01d, 0.01d));
        } else if (i == 7) {
            eMFRenderer.setMapModeIsotropic(true);
            eMFRenderer.fixViewportSize();
        } else if (i == 4) {
            eMFRenderer.setMapModeTransform(AffineTransform.getScaleInstance(0.254d, 0.254d));
        } else if (i == 2) {
            eMFRenderer.setMapModeTransform(AffineTransform.getScaleInstance(0.1d, 0.1d));
        } else if (i == 1) {
            eMFRenderer.setMapModeTransform(AffineTransform.getScaleInstance(1.0d, -1.0d));
        } else if (i == 6) {
            eMFRenderer.setMapModeTransform(AffineTransform.getScaleInstance(EMFRenderer.TWIP_SCALE, EMFRenderer.TWIP_SCALE));
        }
    }
}
