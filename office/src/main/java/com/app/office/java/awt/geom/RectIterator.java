package com.app.office.java.awt.geom;

import java.util.NoSuchElementException;

class RectIterator implements PathIterator {
    AffineTransform affine;
    double h;
    int index;
    double w;
    double x;
    double y;

    public int getWindingRule() {
        return 1;
    }

    RectIterator(Rectangle2D rectangle2D, AffineTransform affineTransform) {
        this.x = rectangle2D.getX();
        this.y = rectangle2D.getY();
        this.w = rectangle2D.getWidth();
        double height = rectangle2D.getHeight();
        this.h = height;
        this.affine = affineTransform;
        if (this.w < 0.0d || height < 0.0d) {
            this.index = 6;
        }
    }

    public boolean isDone() {
        return this.index > 5;
    }

    public void next() {
        this.index++;
    }

    public int currentSegment(float[] fArr) {
        if (!isDone()) {
            int i = this.index;
            if (i == 5) {
                return 4;
            }
            fArr[0] = (float) this.x;
            fArr[1] = (float) this.y;
            if (i == 1 || i == 2) {
                fArr[0] = fArr[0] + ((float) this.w);
            }
            if (i == 2 || i == 3) {
                fArr[1] = fArr[1] + ((float) this.h);
            }
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(fArr, 0, fArr, 0, 1);
            }
            if (this.index == 0) {
                return 0;
            }
            return 1;
        }
        throw new NoSuchElementException("rect iterator out of bounds");
    }

    public int currentSegment(double[] dArr) {
        if (!isDone()) {
            int i = this.index;
            if (i == 5) {
                return 4;
            }
            dArr[0] = this.x;
            dArr[1] = this.y;
            if (i == 1 || i == 2) {
                dArr[0] = dArr[0] + this.w;
            }
            if (i == 2 || i == 3) {
                dArr[1] = dArr[1] + this.h;
            }
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(dArr, 0, dArr, 0, 1);
            }
            if (this.index == 0) {
                return 0;
            }
            return 1;
        }
        throw new NoSuchElementException("rect iterator out of bounds");
    }
}
