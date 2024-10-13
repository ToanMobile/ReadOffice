package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFTag;

public abstract class AbstractPolygon extends EMFTag {
    private Rectangle bounds;
    private int numberOfPoints;
    private Point[] points;

    protected AbstractPolygon(int i, int i2) {
        super(i, i2);
    }

    protected AbstractPolygon(int i, int i2, Rectangle rectangle, int i3, Point[] pointArr) {
        super(i, i2);
        this.bounds = rectangle;
        this.numberOfPoints = i3;
        this.points = pointArr;
    }

    public String toString() {
        String str = super.toString() + "\n  bounds: " + this.bounds + "\n  #points: " + this.numberOfPoints;
        if (this.points != null) {
            str = str + "\n  points: ";
            for (int i = 0; i < this.points.length; i++) {
                str = str + "[" + this.points[i].x + "," + this.points[i].y + "]";
                if (i < this.points.length - 1) {
                    str = str + ", ";
                }
            }
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public Rectangle getBounds() {
        return this.bounds;
    }

    /* access modifiers changed from: protected */
    public int getNumberOfPoints() {
        return this.numberOfPoints;
    }

    /* access modifiers changed from: protected */
    public Point[] getPoints() {
        return this.points;
    }
}
