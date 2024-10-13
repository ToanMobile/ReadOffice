package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class MoveToEx extends EMFTag {
    private Point point;

    public MoveToEx() {
        super(27, 1);
    }

    public MoveToEx(Point point2) {
        this();
        this.point = point2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new MoveToEx(eMFInputStream.readPOINTL());
    }

    public String toString() {
        return super.toString() + "\n  point: " + this.point;
    }

    public void render(EMFRenderer eMFRenderer) {
        GeneralPath generalPath = new GeneralPath(eMFRenderer.getWindingRule());
        generalPath.moveTo((float) this.point.x, (float) this.point.y);
        eMFRenderer.setFigure(generalPath);
    }
}
