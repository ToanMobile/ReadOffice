package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class PolyDraw16 extends EMFTag implements EMFConstants {
    private Rectangle bounds;
    private Point[] points;
    private byte[] types;

    public PolyDraw16() {
        super(92, 1);
    }

    public PolyDraw16(Rectangle rectangle, Point[] pointArr, byte[] bArr) {
        this();
        this.bounds = rectangle;
        this.points = pointArr;
        this.types = bArr;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        return new PolyDraw16(readRECTL, eMFInputStream.readPOINTS(readDWORD), eMFInputStream.readBYTE(readDWORD));
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds + "\n  #points: " + this.points.length;
    }
}
