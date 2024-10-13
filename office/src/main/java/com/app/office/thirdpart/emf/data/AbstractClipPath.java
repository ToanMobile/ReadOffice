package com.app.office.thirdpart.emf.data;

import android.graphics.Matrix;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Area;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;

public abstract class AbstractClipPath extends EMFTag {
    private int mode;

    protected AbstractClipPath(int i, int i2, int i3) {
        super(i, i2);
        this.mode = i3;
    }

    public String toString() {
        return super.toString() + "\n  mode: " + this.mode;
    }

    public int getMode() {
        return this.mode;
    }

    public void render(EMFRenderer eMFRenderer, Shape shape) {
        if (shape != null) {
            int i = this.mode;
            if (i == 1) {
                eMFRenderer.clip(shape);
            } else if (i == 5) {
                Matrix matrix = eMFRenderer.getMatrix();
                eMFRenderer.resetTransformation();
                eMFRenderer.setClip(eMFRenderer.getInitialClip());
                eMFRenderer.setMatrix(matrix);
                eMFRenderer.clip(shape);
            } else if (i == 4) {
                Shape clip = eMFRenderer.getClip();
                if (clip != null) {
                    Area area = new Area(shape);
                    area.subtract(new Area(clip));
                    eMFRenderer.setClip(area);
                } else {
                    eMFRenderer.setClip(shape);
                }
            } else if (i == 2) {
                GeneralPath generalPath = new GeneralPath(shape);
                Shape clip2 = eMFRenderer.getClip();
                if (clip2 != null) {
                    generalPath.append(clip2, false);
                }
                eMFRenderer.setClip(generalPath);
            } else if (i == 3) {
                Shape clip3 = eMFRenderer.getClip();
                if (clip3 != null) {
                    Area area2 = new Area(shape);
                    area2.exclusiveOr(new Area(clip3));
                    eMFRenderer.setClip(area2);
                } else {
                    eMFRenderer.setClip(shape);
                }
            }
        }
        eMFRenderer.setPath((GeneralPath) null);
    }
}
