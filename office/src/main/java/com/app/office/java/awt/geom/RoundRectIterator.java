package com.app.office.java.awt.geom;

import java.util.NoSuchElementException;

class RoundRectIterator implements PathIterator {
    private static final double a;
    private static final double acv;
    private static final double angle = 0.7853981633974483d;
    private static final double b;
    private static final double c;
    private static double[][] ctrlpts;
    private static final double cv;
    private static int[] types = {0, 1, 3, 1, 3, 1, 3, 1, 3, 4};
    AffineTransform affine;
    double ah;
    double aw;
    double h;
    int index;
    double w;
    double x;
    double y;

    public int getWindingRule() {
        return 1;
    }

    RoundRectIterator(RoundRectangle2D roundRectangle2D, AffineTransform affineTransform) {
        this.x = roundRectangle2D.getX();
        this.y = roundRectangle2D.getY();
        this.w = roundRectangle2D.getWidth();
        this.h = roundRectangle2D.getHeight();
        this.aw = Math.min(this.w, Math.abs(roundRectangle2D.getArcWidth()));
        double min = Math.min(this.h, Math.abs(roundRectangle2D.getArcHeight()));
        this.ah = min;
        this.affine = affineTransform;
        if (this.aw < 0.0d || min < 0.0d) {
            this.index = ctrlpts.length;
        }
    }

    public boolean isDone() {
        return this.index >= ctrlpts.length;
    }

    public void next() {
        this.index++;
    }

    static {
        double cos = 1.0d - Math.cos(angle);
        a = cos;
        double tan = Math.tan(angle);
        b = tan;
        double sqrt = (Math.sqrt((tan * tan) + 1.0d) - 1.0d) + cos;
        c = sqrt;
        double d = ((cos * 1.3333333333333333d) * tan) / sqrt;
        cv = d;
        double d2 = (1.0d - d) / 2.0d;
        acv = d2;
        ctrlpts = new double[][]{new double[]{0.0d, 0.0d, 0.0d, 0.5d}, new double[]{0.0d, 0.0d, 1.0d, -0.5d}, new double[]{0.0d, 0.0d, 1.0d, -d2, 0.0d, d2, 1.0d, 0.0d, 0.0d, 0.5d, 1.0d, 0.0d}, new double[]{1.0d, -0.5d, 1.0d, 0.0d}, new double[]{1.0d, -d2, 1.0d, 0.0d, 1.0d, 0.0d, 1.0d, -d2, 1.0d, 0.0d, 1.0d, -0.5d}, new double[]{1.0d, 0.0d, 0.0d, 0.5d}, new double[]{1.0d, 0.0d, 0.0d, d2, 1.0d, -d2, 0.0d, 0.0d, 1.0d, -0.5d, 0.0d, 0.0d}, new double[]{0.0d, 0.5d, 0.0d, 0.0d}, new double[]{0.0d, d2, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, d2, 0.0d, 0.0d, 0.0d, 0.5d}, new double[0]};
    }

    public int currentSegment(float[] fArr) {
        if (!isDone()) {
            double[] dArr = ctrlpts[this.index];
            int i = 0;
            for (int i2 = 0; i2 < dArr.length; i2 += 4) {
                int i3 = i + 1;
                fArr[i] = (float) (this.x + (dArr[i2 + 0] * this.w) + (dArr[i2 + 1] * this.aw));
                i = i3 + 1;
                fArr[i3] = (float) (this.y + (dArr[i2 + 2] * this.h) + (dArr[i2 + 3] * this.ah));
            }
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(fArr, 0, fArr, 0, i / 2);
            }
            return types[this.index];
        }
        throw new NoSuchElementException("roundrect iterator out of bounds");
    }

    public int currentSegment(double[] dArr) {
        if (!isDone()) {
            double[] dArr2 = ctrlpts[this.index];
            int i = 0;
            for (int i2 = 0; i2 < dArr2.length; i2 += 4) {
                int i3 = i + 1;
                dArr[i] = this.x + (dArr2[i2 + 0] * this.w) + (dArr2[i2 + 1] * this.aw);
                i = i3 + 1;
                dArr[i3] = this.y + (dArr2[i2 + 2] * this.h) + (dArr2[i2 + 3] * this.ah);
            }
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(dArr, 0, dArr, 0, i / 2);
            }
            return types[this.index];
        }
        throw new NoSuchElementException("roundrect iterator out of bounds");
    }
}
