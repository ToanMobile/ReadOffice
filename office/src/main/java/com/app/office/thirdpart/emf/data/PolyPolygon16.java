package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class PolyPolygon16 extends AbstractPolyPolygon {
    private int numberOfPolys;

    public PolyPolygon16() {
        super(91, 1, (Rectangle) null, (int[]) null, (Point[][]) null);
    }

    public PolyPolygon16(Rectangle rectangle, int i, int[] iArr, Point[][] pointArr) {
        super(91, 1, rectangle, iArr, pointArr);
        this.numberOfPolys = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        int[] iArr = new int[readDWORD];
        Point[][] pointArr = new Point[readDWORD][];
        for (int i3 = 0; i3 < readDWORD; i3++) {
            iArr[i3] = eMFInputStream.readDWORD();
            pointArr[i3] = new Point[iArr[i3]];
        }
        for (int i4 = 0; i4 < readDWORD; i4++) {
            pointArr[i4] = eMFInputStream.readPOINTS(iArr[i4]);
        }
        return new PolyPolygon16(readRECTL, readDWORD, iArr, pointArr);
    }
}
