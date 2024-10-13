package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Arc2D;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;

public abstract class AbstractArc extends EMFTag {
    private Rectangle bounds;
    private Point end;
    private Point start;

    protected AbstractArc(int i, int i2, Rectangle rectangle, Point point, Point point2) {
        super(i, i2);
        this.bounds = rectangle;
        this.start = point;
        this.end = point2;
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds + "\n  start: " + this.start + "\n  end: " + this.end;
    }

    /* access modifiers changed from: protected */
    public double getAngle(Point point) {
        double x = this.bounds.getX() + (this.bounds.getWidth() / 2.0d);
        double y = this.bounds.getY() + (this.bounds.getHeight() / 2.0d);
        double d = (double) point.x;
        double d2 = (double) point.y;
        int i = (d > x ? 1 : (d == x ? 0 : -1));
        if (i > 0) {
            double atan = (Math.atan(Math.abs(d2 - y) / (d - x)) / 3.141592653589793d) * 180.0d;
            if (d2 > y) {
                return 360.0d - atan;
            }
            return atan;
        } else if (i == 0) {
            return d2 < y ? 90.0d : 270.0d;
        } else {
            double atan2 = (Math.atan(Math.abs(d2 - y) / (x - d)) / 3.141592653589793d) * 180.0d;
            return d2 < y ? 180.0d - atan2 : atan2 + 180.0d;
        }
    }

    /* access modifiers changed from: protected */
    public Shape getShape(EMFRenderer eMFRenderer, int i) {
        double d;
        double d2;
        if (eMFRenderer.getArcDirection() == 2) {
            d2 = getAngle(this.end);
            d = getAngle(this.start);
        } else {
            d2 = getAngle(this.start);
            d = getAngle(this.end);
        }
        double d3 = d2;
        return new Arc2D.Double(this.bounds.getX(), this.bounds.getY(), this.bounds.getWidth(), this.bounds.getHeight(), d3, d > d3 ? d - d3 : 360.0d - (d3 - d), i);
    }
}
