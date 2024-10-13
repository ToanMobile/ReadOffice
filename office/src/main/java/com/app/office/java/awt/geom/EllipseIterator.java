package com.app.office.java.awt.geom;

import java.util.NoSuchElementException;

class EllipseIterator implements PathIterator {
    public static final double CtrlVal = 0.5522847498307933d;
    private static double[][] ctrlpts = {new double[]{1.0d, pcv, pcv, 1.0d, 0.5d, 1.0d}, new double[]{ncv, 1.0d, 0.0d, pcv, 0.0d, 0.5d}, new double[]{0.0d, ncv, ncv, 0.0d, 0.5d, 0.0d}, new double[]{pcv, 0.0d, 1.0d, ncv, 1.0d, 0.5d}};
    private static final double ncv = 0.22385762508460333d;
    private static final double pcv = 0.7761423749153966d;
    AffineTransform affine;
    double h;
    int index;
    double w;
    double x;
    double y;

    public int getWindingRule() {
        return 1;
    }

    EllipseIterator(Ellipse2D ellipse2D, AffineTransform affineTransform) {
        this.x = ellipse2D.getX();
        this.y = ellipse2D.getY();
        this.w = ellipse2D.getWidth();
        double height = ellipse2D.getHeight();
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
            if (i == 0) {
                double[] dArr = ctrlpts[3];
                fArr[0] = (float) (this.x + (dArr[4] * this.w));
                fArr[1] = (float) (this.y + (dArr[5] * this.h));
                AffineTransform affineTransform = this.affine;
                if (affineTransform != null) {
                    affineTransform.transform(fArr, 0, fArr, 0, 1);
                }
                return 0;
            }
            double[] dArr2 = ctrlpts[i - 1];
            double d = this.x;
            double d2 = dArr2[0];
            double d3 = this.w;
            fArr[0] = (float) ((d2 * d3) + d);
            double d4 = this.y;
            double d5 = dArr2[1];
            double d6 = this.h;
            fArr[1] = (float) ((d5 * d6) + d4);
            fArr[2] = (float) ((dArr2[2] * d3) + d);
            fArr[3] = (float) ((dArr2[3] * d6) + d4);
            fArr[4] = (float) (d + (dArr2[4] * d3));
            fArr[5] = (float) (d4 + (dArr2[5] * d6));
            AffineTransform affineTransform2 = this.affine;
            if (affineTransform2 != null) {
                affineTransform2.transform(fArr, 0, fArr, 0, 3);
            }
            return 3;
        }
        throw new NoSuchElementException("ellipse iterator out of bounds");
    }

    public int currentSegment(double[] dArr) {
        if (!isDone()) {
            int i = this.index;
            if (i == 5) {
                return 4;
            }
            if (i == 0) {
                double[] dArr2 = ctrlpts[3];
                dArr[0] = this.x + (dArr2[4] * this.w);
                dArr[1] = this.y + (dArr2[5] * this.h);
                AffineTransform affineTransform = this.affine;
                if (affineTransform != null) {
                    affineTransform.transform(dArr, 0, dArr, 0, 1);
                }
                return 0;
            }
            double[] dArr3 = ctrlpts[i - 1];
            double d = this.x;
            double d2 = dArr3[0];
            double d3 = this.w;
            dArr[0] = (d2 * d3) + d;
            double d4 = this.y;
            double d5 = dArr3[1];
            double d6 = this.h;
            dArr[1] = (d5 * d6) + d4;
            dArr[2] = (dArr3[2] * d3) + d;
            dArr[3] = (dArr3[3] * d6) + d4;
            dArr[4] = d + (dArr3[4] * d3);
            dArr[5] = d4 + (dArr3[5] * d6);
            AffineTransform affineTransform2 = this.affine;
            if (affineTransform2 != null) {
                affineTransform2.transform(dArr, 0, dArr, 0, 3);
            }
            return 3;
        }
        throw new NoSuchElementException("ellipse iterator out of bounds");
    }
}
