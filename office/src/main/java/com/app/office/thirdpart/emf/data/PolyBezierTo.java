package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class PolyBezierTo extends AbstractPolygon {
    public PolyBezierTo() {
        super(5, 1, (Rectangle) null, 0, (Point[]) null);
    }

    public PolyBezierTo(Rectangle rectangle, int i, Point[] pointArr) {
        super(5, 1, rectangle, i, pointArr);
    }

    protected PolyBezierTo(int i, int i2, Rectangle rectangle, int i3, Point[] pointArr) {
        super(i, i2, rectangle, i3, pointArr);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new PolyBezierTo(readRECTL, readDWORD, eMFInputStream.readPOINTL(readDWORD));
    }

    public void render(EMFRenderer eMFRenderer) {
        Point[] points = getPoints();
        int numberOfPoints = getNumberOfPoints();
        GeneralPath figure = eMFRenderer.getFigure();
        if (points != null && points.length > 0) {
            for (int i = 0; i < numberOfPoints; i += 3) {
                Point point = points[i];
                Point point2 = points[i + 1];
                Point point3 = points[i + 2];
                figure.curveTo((float) point.x, (float) point.y, (float) point2.x, (float) point2.y, (float) point3.x, (float) point3.y);
            }
        }
    }
}
