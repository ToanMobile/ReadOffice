package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class PolyPolyline16 extends AbstractPolyPolyline {
    private int numberOfPolys;

    public PolyPolyline16() {
        super(90, 1, (Rectangle) null, (int[]) null, (Point[][]) null);
    }

    public PolyPolyline16(Rectangle rectangle, int i, int[] iArr, Point[][] pointArr) {
        super(90, 1, rectangle, iArr, pointArr);
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
        return new PolyPolyline16(readRECTL, readDWORD, iArr, pointArr);
    }
}
