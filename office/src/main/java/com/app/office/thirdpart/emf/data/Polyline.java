package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class Polyline extends AbstractPolygon {
    public Polyline() {
        super(4, 1, (Rectangle) null, 0, (Point[]) null);
    }

    public Polyline(Rectangle rectangle, int i, Point[] pointArr) {
        super(4, 1, rectangle, i, pointArr);
    }

    protected Polyline(int i, int i2, Rectangle rectangle, int i3, Point[] pointArr) {
        super(i, i2, rectangle, i3, pointArr);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new Polyline(readRECTL, readDWORD, eMFInputStream.readPOINTL(readDWORD));
    }

    public void render(EMFRenderer eMFRenderer) {
        Point[] points = getPoints();
        int numberOfPoints = getNumberOfPoints();
        if (points != null && points.length > 0) {
            GeneralPath generalPath = new GeneralPath(eMFRenderer.getWindingRule());
            for (int i = 0; i < numberOfPoints; i++) {
                Point point = points[i];
                if (i > 0) {
                    generalPath.lineTo((float) point.x, (float) point.y);
                } else {
                    generalPath.moveTo((float) point.x, (float) point.y);
                }
            }
            eMFRenderer.drawOrAppend(generalPath);
        }
    }
}
