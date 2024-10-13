package com.app.office.java.awt.geom;

import java.util.NoSuchElementException;

class CubicIterator implements PathIterator {
    AffineTransform affine;
    CubicCurve2D cubic;
    int index;

    public int getWindingRule() {
        return 1;
    }

    CubicIterator(CubicCurve2D cubicCurve2D, AffineTransform affineTransform) {
        this.cubic = cubicCurve2D;
        this.affine = affineTransform;
    }

    public boolean isDone() {
        return this.index > 1;
    }

    public void next() {
        this.index++;
    }

    public int currentSegment(float[] fArr) {
        if (!isDone()) {
            int i = 0;
            if (this.index == 0) {
                fArr[0] = (float) this.cubic.getX1();
                fArr[1] = (float) this.cubic.getY1();
            } else {
                fArr[0] = (float) this.cubic.getCtrlX1();
                fArr[1] = (float) this.cubic.getCtrlY1();
                fArr[2] = (float) this.cubic.getCtrlX2();
                fArr[3] = (float) this.cubic.getCtrlY2();
                fArr[4] = (float) this.cubic.getX2();
                fArr[5] = (float) this.cubic.getY2();
                i = 3;
            }
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(fArr, 0, fArr, 0, this.index == 0 ? 1 : 3);
            }
            return i;
        }
        throw new NoSuchElementException("cubic iterator iterator out of bounds");
    }

    public int currentSegment(double[] dArr) {
        if (!isDone()) {
            int i = 0;
            if (this.index == 0) {
                dArr[0] = this.cubic.getX1();
                dArr[1] = this.cubic.getY1();
            } else {
                dArr[0] = this.cubic.getCtrlX1();
                dArr[1] = this.cubic.getCtrlY1();
                dArr[2] = this.cubic.getCtrlX2();
                dArr[3] = this.cubic.getCtrlY2();
                dArr[4] = this.cubic.getX2();
                dArr[5] = this.cubic.getY2();
                i = 3;
            }
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(dArr, 0, dArr, 0, this.index == 0 ? 1 : 3);
            }
            return i;
        }
        throw new NoSuchElementException("cubic iterator iterator out of bounds");
    }
}
