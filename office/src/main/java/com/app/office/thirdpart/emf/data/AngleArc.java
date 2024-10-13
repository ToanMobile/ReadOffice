package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class AngleArc extends EMFTag {
    private Point center;
    private int radius;
    private float startAngle;
    private float sweepAngle;

    public AngleArc() {
        super(41, 1);
    }

    public AngleArc(Point point, int i, float f, float f2) {
        this();
        this.center = point;
        this.radius = i;
        this.startAngle = f;
        this.sweepAngle = f2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new AngleArc(eMFInputStream.readPOINTL(), eMFInputStream.readDWORD(), eMFInputStream.readFLOAT(), eMFInputStream.readFLOAT());
    }

    public String toString() {
        return super.toString() + "\n  center: " + this.center + "\n  radius: " + this.radius + "\n  startAngle: " + this.startAngle + "\n  sweepAngle: " + this.sweepAngle;
    }
}
