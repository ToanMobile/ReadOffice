package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class PolyBezier extends AbstractPolygon {
    public PolyBezier() {
        super(2, 1, (Rectangle) null, 0, (Point[]) null);
    }

    public PolyBezier(Rectangle rectangle, int i, Point[] pointArr) {
        super(2, 1, rectangle, i, pointArr);
    }

    protected PolyBezier(int i, int i2, Rectangle rectangle, int i3, Point[] pointArr) {
        super(i, i2, rectangle, i3, pointArr);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new PolyBezier(readRECTL, readDWORD, eMFInputStream.readPOINTL(readDWORD));
    }

    public void render(EMFRenderer eMFRenderer) {
        Point[] points = getPoints();
        int numberOfPoints = getNumberOfPoints();
        if (points != null && points.length > 0) {
            GeneralPath generalPath = new GeneralPath(eMFRenderer.getWindingRule());
            Point point = points[0];
            generalPath.moveTo((float) point.x, (float) point.y);
            for (int i = 1; i < numberOfPoints; i += 3) {
                Point point2 = points[i];
                Point point3 = points[i + 1];
                Point point4 = points[i + 2];
                if (i > 0) {
                    generalPath.curveTo((float) point2.x, (float) point2.y, (float) point3.x, (float) point3.y, (float) point4.x, (float) point4.y);
                }
            }
            eMFRenderer.fillAndDrawOrAppend(generalPath);
        }
    }
}
