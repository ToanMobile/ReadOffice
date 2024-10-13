package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class Polyline16 extends Polyline {
    public Polyline16() {
        super(87, 1, (Rectangle) null, 0, (Point[]) null);
    }

    public Polyline16(Rectangle rectangle, int i, Point[] pointArr) {
        super(87, 1, rectangle, i, pointArr);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new Polyline16(readRECTL, readDWORD, eMFInputStream.readPOINTS(readDWORD));
    }
}
