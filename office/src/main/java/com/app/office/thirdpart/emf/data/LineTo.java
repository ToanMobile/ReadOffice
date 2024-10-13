package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class LineTo extends EMFTag {
    private Point point;

    public LineTo() {
        super(54, 1);
    }

    public LineTo(Point point2) {
        this();
        this.point = point2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new LineTo(eMFInputStream.readPOINTL());
    }

    public String toString() {
        return super.toString() + "\n  point: " + this.point;
    }

    public void render(EMFRenderer eMFRenderer) {
        GeneralPath figure = eMFRenderer.getFigure();
        if (figure != null) {
            figure.lineTo((float) this.point.x, (float) this.point.y);
            eMFRenderer.drawShape(figure);
            return;
        }
        GeneralPath generalPath = new GeneralPath(eMFRenderer.getWindingRule());
        generalPath.moveTo((float) this.point.x, (float) this.point.y);
        eMFRenderer.setFigure(generalPath);
    }
}
