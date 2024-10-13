package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;

public abstract class AbstractPolyPolygon extends EMFTag {
    private Rectangle bounds;
    private int[] numberOfPoints;
    private Point[][] points;

    protected AbstractPolyPolygon(int i, int i2, Rectangle rectangle, int[] iArr, Point[][] pointArr) {
        super(i, i2);
        this.bounds = rectangle;
        this.numberOfPoints = iArr;
        this.points = pointArr;
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds + "\n  #polys: " + this.numberOfPoints.length;
    }

    /* access modifiers changed from: protected */
    public Rectangle getBounds() {
        return this.bounds;
    }

    /* access modifiers changed from: protected */
    public int[] getNumberOfPoints() {
        return this.numberOfPoints;
    }

    /* access modifiers changed from: protected */
    public Point[][] getPoints() {
        return this.points;
    }

    public void render(EMFRenderer eMFRenderer) {
        render(eMFRenderer, true);
    }

    /* access modifiers changed from: protected */
    public void render(EMFRenderer eMFRenderer, boolean z) {
        GeneralPath generalPath = new GeneralPath(eMFRenderer.getWindingRule());
        for (int i = 0; i < this.numberOfPoints.length; i++) {
            GeneralPath generalPath2 = new GeneralPath(eMFRenderer.getWindingRule());
            for (int i2 = 0; i2 < this.numberOfPoints[i]; i2++) {
                Point point = this.points[i][i2];
                if (i2 > 0) {
                    generalPath2.lineTo((float) point.x, (float) point.y);
                } else {
                    generalPath2.moveTo((float) point.x, (float) point.y);
                }
            }
            if (z) {
                generalPath2.closePath();
            }
            generalPath.append((Shape) generalPath2, false);
        }
        if (z) {
            eMFRenderer.fillAndDrawOrAppend(generalPath);
        } else {
            eMFRenderer.drawOrAppend(generalPath);
        }
    }
}
