package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class EMFPolygon extends AbstractPolygon {
    public EMFPolygon() {
        super(3, 1, (Rectangle) null, 0, (Point[]) null);
    }

    public EMFPolygon(Rectangle rectangle, int i, Point[] pointArr) {
        super(3, 1, rectangle, i, pointArr);
    }

    protected EMFPolygon(int i, int i2, Rectangle rectangle, int i3, Point[] pointArr) {
        super(i, i2, rectangle, i3, pointArr);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new EMFPolygon(readRECTL, readDWORD, eMFInputStream.readPOINTL(readDWORD));
    }

    public void render(EMFRenderer eMFRenderer) {
        Point[] points = getPoints();
        if (points.length > 1) {
            GeneralPath generalPath = new GeneralPath(eMFRenderer.getWindingRule());
            generalPath.moveTo((float) points[0].x, (float) points[0].y);
            for (int i = 1; i < points.length; i++) {
                generalPath.lineTo((float) points[i].x, (float) points[i].y);
            }
            generalPath.closePath();
            eMFRenderer.fillAndDrawOrAppend(generalPath);
        }
    }
}
