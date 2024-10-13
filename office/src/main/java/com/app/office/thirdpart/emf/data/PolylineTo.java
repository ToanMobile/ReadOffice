package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class PolylineTo extends AbstractPolygon {
    public PolylineTo() {
        super(6, 1, (Rectangle) null, 0, (Point[]) null);
    }

    public PolylineTo(Rectangle rectangle, int i, Point[] pointArr) {
        this(6, 1, rectangle, i, pointArr);
    }

    protected PolylineTo(int i, int i2, Rectangle rectangle, int i3, Point[] pointArr) {
        super(i, i2, rectangle, i3, pointArr);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new PolylineTo(readRECTL, readDWORD, eMFInputStream.readPOINTL(readDWORD));
    }

    public void render(EMFRenderer eMFRenderer) {
        Point[] points = getPoints();
        int numberOfPoints = getNumberOfPoints();
        GeneralPath figure = eMFRenderer.getFigure();
        if (points != null) {
            for (int i = 0; i < numberOfPoints; i++) {
                figure.lineTo((float) points[i].x, (float) points[i].y);
            }
        }
    }
}
