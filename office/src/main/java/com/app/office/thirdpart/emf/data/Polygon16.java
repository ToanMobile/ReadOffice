package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class Polygon16 extends EMFPolygon {
    public Polygon16() {
        super(86, 1, (Rectangle) null, 0, (Point[]) null);
    }

    public Polygon16(Rectangle rectangle, int i, Point[] pointArr) {
        super(86, 1, rectangle, i, pointArr);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new Polygon16(readRECTL, readDWORD, eMFInputStream.readPOINTS(readDWORD));
    }
}
