package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class PolyPolygon extends AbstractPolyPolygon {
    private int end;
    private int start;

    public PolyPolygon() {
        super(8, 1, (Rectangle) null, (int[]) null, (Point[][]) null);
    }

    public PolyPolygon(Rectangle rectangle, int i, int i2, int[] iArr, Point[][] pointArr) {
        super(8, 1, rectangle, iArr, pointArr);
        this.start = i;
        this.end = i2;
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
            pointArr[i4] = eMFInputStream.readPOINTL(iArr[i4]);
        }
        return new PolyPolygon(readRECTL, 0, readDWORD - 1, iArr, pointArr);
    }
}
