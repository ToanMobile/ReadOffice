package com.app.office.java.awt.geom;

import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Point2D;
import com.app.office.java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class QuadCurve2D implements Shape, Cloneable {
    private static final int ABOVE = 2;
    private static final int BELOW = -2;
    private static final int HIGHEDGE = 1;
    private static final int INSIDE = 0;
    private static final int LOWEDGE = -1;

    private static int getTag(double d, double d2, double d3) {
        int i = (d > d2 ? 1 : (d == d2 ? 0 : -1));
        if (i <= 0) {
            return i < 0 ? -2 : -1;
        }
        int i2 = (d > d3 ? 1 : (d == d3 ? 0 : -1));
        if (i2 >= 0) {
            return i2 > 0 ? 2 : 1;
        }
        return 0;
    }

    private static boolean inwards(int i, int i2, int i3) {
        if (i == -1) {
            return i2 >= 0 || i3 >= 0;
        }
        if (i == 0) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        return i2 <= 0 || i3 <= 0;
    }

    public abstract Point2D getCtrlPt();

    public abstract double getCtrlX();

    public abstract double getCtrlY();

    public abstract Point2D getP1();

    public abstract Point2D getP2();

    public abstract double getX1();

    public abstract double getX2();

    public abstract double getY1();

    public abstract double getY2();

    public abstract void setCurve(double d, double d2, double d3, double d4, double d5, double d6);

    public static class Float extends QuadCurve2D implements Serializable {
        private static final long serialVersionUID = -8511188402130719609L;
        public float ctrlx;
        public float ctrly;
        public float x1;
        public float x2;
        public float y1;
        public float y2;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4, float f5, float f6) {
            setCurve(f, f2, f3, f4, f5, f6);
        }

        public double getX1() {
            return (double) this.x1;
        }

        public double getY1() {
            return (double) this.y1;
        }

        public Point2D getP1() {
            return new Point2D.Float(this.x1, this.y1);
        }

        public double getCtrlX() {
            return (double) this.ctrlx;
        }

        public double getCtrlY() {
            return (double) this.ctrly;
        }

        public Point2D getCtrlPt() {
            return new Point2D.Float(this.ctrlx, this.ctrly);
        }

        public double getX2() {
            return (double) this.x2;
        }

        public double getY2() {
            return (double) this.y2;
        }

        public Point2D getP2() {
            return new Point2D.Float(this.x2, this.y2);
        }

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6) {
            this.x1 = (float) d;
            this.y1 = (float) d2;
            this.ctrlx = (float) d3;
            this.ctrly = (float) d4;
            this.x2 = (float) d5;
            this.y2 = (float) d6;
        }

        public void setCurve(float f, float f2, float f3, float f4, float f5, float f6) {
            this.x1 = f;
            this.y1 = f2;
            this.ctrlx = f3;
            this.ctrly = f4;
            this.x2 = f5;
            this.y2 = f6;
        }

        public Rectangle2D getBounds2D() {
            float min = Math.min(Math.min(this.x1, this.x2), this.ctrlx);
            float min2 = Math.min(Math.min(this.y1, this.y2), this.ctrly);
            return new Rectangle2D.Float(min, min2, Math.max(Math.max(this.x1, this.x2), this.ctrlx) - min, Math.max(Math.max(this.y1, this.y2), this.ctrly) - min2);
        }
    }

    public static class Double extends QuadCurve2D implements Serializable {
        private static final long serialVersionUID = 4217149928428559721L;
        public double ctrlx;
        public double ctrly;
        public double x1;
        public double x2;
        public double y1;
        public double y2;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4, double d5, double d6) {
            setCurve(d, d2, d3, d4, d5, d6);
        }

        public double getX1() {
            return this.x1;
        }

        public double getY1() {
            return this.y1;
        }

        public Point2D getP1() {
            return new Point2D.Double(this.x1, this.y1);
        }

        public double getCtrlX() {
            return this.ctrlx;
        }

        public double getCtrlY() {
            return this.ctrly;
        }

        public Point2D getCtrlPt() {
            return new Point2D.Double(this.ctrlx, this.ctrly);
        }

        public double getX2() {
            return this.x2;
        }

        public double getY2() {
            return this.y2;
        }

        public Point2D getP2() {
            return new Point2D.Double(this.x2, this.y2);
        }

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6) {
            this.x1 = d;
            this.y1 = d2;
            this.ctrlx = d3;
            this.ctrly = d4;
            this.x2 = d5;
            this.y2 = d6;
        }

        public Rectangle2D getBounds2D() {
            double min = Math.min(Math.min(this.x1, this.x2), this.ctrlx);
            double min2 = Math.min(Math.min(this.y1, this.y2), this.ctrly);
            return new Rectangle2D.Double(min, min2, Math.max(Math.max(this.x1, this.x2), this.ctrlx) - min, Math.max(Math.max(this.y1, this.y2), this.ctrly) - min2);
        }
    }

    protected QuadCurve2D() {
    }

    public void setCurve(double[] dArr, int i) {
        setCurve(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5]);
    }

    public void setCurve(Point2D point2D, Point2D point2D2, Point2D point2D3) {
        setCurve(point2D.getX(), point2D.getY(), point2D2.getX(), point2D2.getY(), point2D3.getX(), point2D3.getY());
    }

    public void setCurve(Point2D[] point2DArr, int i) {
        int i2 = i + 0;
        double x = point2DArr[i2].getX();
        double y = point2DArr[i2].getY();
        int i3 = i + 1;
        double x2 = point2DArr[i3].getX();
        double y2 = point2DArr[i3].getY();
        int i4 = i + 2;
        setCurve(x, y, x2, y2, point2DArr[i4].getX(), point2DArr[i4].getY());
    }

    public void setCurve(QuadCurve2D quadCurve2D) {
        setCurve(quadCurve2D.getX1(), quadCurve2D.getY1(), quadCurve2D.getCtrlX(), quadCurve2D.getCtrlY(), quadCurve2D.getX2(), quadCurve2D.getY2());
    }

    public static double getFlatnessSq(double d, double d2, double d3, double d4, double d5, double d6) {
        return Line2D.ptSegDistSq(d, d2, d5, d6, d3, d4);
    }

    public static double getFlatness(double d, double d2, double d3, double d4, double d5, double d6) {
        return Line2D.ptSegDist(d, d2, d5, d6, d3, d4);
    }

    public static double getFlatnessSq(double[] dArr, int i) {
        return Line2D.ptSegDistSq(dArr[i + 0], dArr[i + 1], dArr[i + 4], dArr[i + 5], dArr[i + 2], dArr[i + 3]);
    }

    public static double getFlatness(double[] dArr, int i) {
        return Line2D.ptSegDist(dArr[i + 0], dArr[i + 1], dArr[i + 4], dArr[i + 5], dArr[i + 2], dArr[i + 3]);
    }

    public double getFlatnessSq() {
        return Line2D.ptSegDistSq(getX1(), getY1(), getX2(), getY2(), getCtrlX(), getCtrlY());
    }

    public double getFlatness() {
        return Line2D.ptSegDist(getX1(), getY1(), getX2(), getY2(), getCtrlX(), getCtrlY());
    }

    public void subdivide(QuadCurve2D quadCurve2D, QuadCurve2D quadCurve2D2) {
        subdivide(this, quadCurve2D, quadCurve2D2);
    }

    public static void subdivide(QuadCurve2D quadCurve2D, QuadCurve2D quadCurve2D2, QuadCurve2D quadCurve2D3) {
        double x1 = quadCurve2D.getX1();
        double y1 = quadCurve2D.getY1();
        double ctrlX = quadCurve2D.getCtrlX();
        double ctrlY = quadCurve2D.getCtrlY();
        double x2 = quadCurve2D.getX2();
        double y2 = quadCurve2D.getY2();
        double d = (x1 + ctrlX) / 2.0d;
        double d2 = (y1 + ctrlY) / 2.0d;
        double d3 = (x2 + ctrlX) / 2.0d;
        double d4 = (y2 + ctrlY) / 2.0d;
        double d5 = (d + d3) / 2.0d;
        double d6 = (d2 + d4) / 2.0d;
        if (quadCurve2D2 != null) {
            quadCurve2D2.setCurve(x1, y1, d, d2, d5, d6);
        }
        if (quadCurve2D3 != null) {
            quadCurve2D3.setCurve(d5, d6, d3, d4, x2, y2);
        }
    }

    public static void subdivide(double[] dArr, int i, double[] dArr2, int i2, double[] dArr3, int i3) {
        double d = dArr[i + 0];
        double d2 = dArr[i + 1];
        double d3 = dArr[i + 2];
        double d4 = dArr[i + 3];
        double d5 = dArr[i + 4];
        double d6 = dArr[i + 5];
        if (dArr2 != null) {
            dArr2[i2 + 0] = d;
            dArr2[i2 + 1] = d2;
        }
        if (dArr3 != null) {
            dArr3[i3 + 4] = d5;
            dArr3[i3 + 5] = d6;
        }
        double d7 = (d + d3) / 2.0d;
        double d8 = (d2 + d4) / 2.0d;
        double d9 = (d5 + d3) / 2.0d;
        double d10 = (d6 + d4) / 2.0d;
        double d11 = (d7 + d9) / 2.0d;
        double d12 = (d8 + d10) / 2.0d;
        if (dArr2 != null) {
            dArr2[i2 + 2] = d7;
            dArr2[i2 + 3] = d8;
            dArr2[i2 + 4] = d11;
            dArr2[i2 + 5] = d12;
        }
        if (dArr3 != null) {
            dArr3[i3 + 0] = d11;
            dArr3[i3 + 1] = d12;
            dArr3[i3 + 2] = d9;
            dArr3[i3 + 3] = d10;
        }
    }

    public static int solveQuadratic(double[] dArr) {
        return solveQuadratic(dArr, dArr);
    }

    public static int solveQuadratic(double[] dArr, double[] dArr2) {
        double d = dArr[2];
        double d2 = dArr[1];
        double d3 = dArr[0];
        if (d != 0.0d) {
            double d4 = (d2 * d2) - ((4.0d * d) * d3);
            if (d4 < 0.0d) {
                return 0;
            }
            double sqrt = Math.sqrt(d4);
            if (d2 < 0.0d) {
                sqrt = -sqrt;
            }
            double d5 = (d2 + sqrt) / -2.0d;
            dArr2[0] = d5 / d;
            if (d5 != 0.0d) {
                dArr2[1] = d3 / d5;
                return 2;
            }
        } else if (d2 == 0.0d) {
            return -1;
        } else {
            dArr2[0] = (-d3) / d2;
        }
        return 1;
    }

    public boolean contains(double d, double d2) {
        double x1 = getX1();
        double y1 = getY1();
        double ctrlX = getCtrlX();
        double ctrlY = getCtrlY();
        double x2 = getX2();
        double y2 = getY2();
        double d3 = (x1 - (ctrlX * 2.0d)) + x2;
        double d4 = (y1 - (ctrlY * 2.0d)) + y2;
        double d5 = x2 - x1;
        double d6 = y2 - y1;
        double d7 = (((d - x1) * d4) - ((d2 - y1) * d3)) / ((d5 * d4) - (d6 * d3));
        if (d7 < 0.0d || d7 > 1.0d || d7 != d7) {
            return false;
        }
        double d8 = (d3 * d7 * d7) + ((ctrlX - x1) * 2.0d * d7) + x1;
        double d9 = (d4 * d7 * d7) + ((ctrlY - y1) * 2.0d * d7) + y1;
        double d10 = (d5 * d7) + x1;
        double d11 = (d6 * d7) + y1;
        return (d >= d8 && d < d10) || (d >= d10 && d < d8) || ((d2 >= d9 && d2 < d11) || (d2 >= d11 && d2 < d9));
    }

    public boolean contains(Point2D point2D) {
        return contains(point2D.getX(), point2D.getY());
    }

    private static void fillEqn(double[] dArr, double d, double d2, double d3, double d4) {
        dArr[0] = d2 - d;
        dArr[1] = ((d3 + d3) - d2) - d2;
        dArr[2] = ((d2 - d3) - d3) + d4;
    }

    private static int evalQuadratic(double[] dArr, int i, boolean z, boolean z2, double[] dArr2, double d, double d2, double d3) {
        int i2 = 0;
        int i3 = i;
        int i4 = 0;
        while (i2 < i3) {
            double d4 = dArr[i2];
            int i5 = (d4 > 0.0d ? 1 : (d4 == 0.0d ? 0 : -1));
            if (z) {
                if (i5 < 0) {
                    i2++;
                }
            } else if (i5 <= 0) {
                i2++;
            }
            int i6 = (d4 > 1.0d ? 1 : (d4 == 1.0d ? 0 : -1));
            if (z2) {
                if (i6 > 0) {
                    i2++;
                }
            } else if (i6 >= 0) {
                i2++;
            }
            if (dArr2 == null || dArr2[1] + (dArr2[2] * 2.0d * d4) != 0.0d) {
                double d5 = 1.0d - d4;
                dArr[i4] = (d * d5 * d5) + (2.0d * d2 * d4 * d5) + (d3 * d4 * d4);
                i4++;
                i2++;
            } else {
                i2++;
            }
        }
        return i4;
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        double d5;
        int i;
        double[] dArr;
        if (d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        double x1 = getX1();
        double y1 = getY1();
        double d6 = d + d3;
        int tag = getTag(x1, d, d6);
        double d7 = d2 + d4;
        int tag2 = getTag(y1, d2, d7);
        if (tag == 0 && tag2 == 0) {
            return true;
        }
        double x2 = getX2();
        double y2 = getY2();
        int tag3 = getTag(x2, d, d6);
        int tag4 = getTag(y2, d2, d7);
        if (tag3 == 0 && tag4 == 0) {
            return true;
        }
        double ctrlX = getCtrlX();
        double ctrlY = getCtrlY();
        int tag5 = getTag(ctrlX, d, d6);
        int tag6 = getTag(ctrlY, d2, d7);
        if (tag < 0 && tag3 < 0 && tag5 < 0) {
            return false;
        }
        if (tag2 < 0 && tag4 < 0 && tag6 < 0) {
            return false;
        }
        if (tag > 0 && tag3 > 0 && tag5 > 0) {
            return false;
        }
        if (tag2 > 0 && tag4 > 0 && tag6 > 0) {
            return false;
        }
        if (inwards(tag, tag3, tag5) && inwards(tag2, tag4, tag6)) {
            return true;
        }
        if (inwards(tag3, tag, tag5) && inwards(tag4, tag2, tag6)) {
            return true;
        }
        boolean z = tag * tag3 <= 0;
        boolean z2 = tag2 * tag4 <= 0;
        if (tag == 0 && tag3 == 0 && z2) {
            return true;
        }
        if (tag2 == 0 && tag4 == 0 && z) {
            return true;
        }
        double[] dArr2 = new double[3];
        double[] dArr3 = new double[3];
        if (!z2) {
            double[] dArr4 = dArr3;
            double d8 = y1;
            double[] dArr5 = dArr2;
            fillEqn(dArr2, tag2 < 0 ? d2 : d7, d8, ctrlY, y2);
            if (solveQuadratic(dArr5, dArr4) == 2 && evalQuadratic(dArr4, 2, true, true, (double[]) null, x1, ctrlX, x2) == 2) {
                double d9 = d;
                double d10 = d6;
                if (getTag(dArr4[0], d9, d10) * getTag(dArr4[1], d9, d10) <= 0) {
                    return true;
                }
            }
            return false;
        }
        double[] dArr6 = dArr2;
        double[] dArr7 = dArr3;
        if (!z) {
            double d11 = tag < 0 ? d : d6;
            double[] dArr8 = dArr6;
            double[] dArr9 = dArr7;
            fillEqn(dArr6, d11, x1, ctrlX, x2);
            if (solveQuadratic(dArr8, dArr9) == 2 && evalQuadratic(dArr9, 2, true, true, (double[]) null, y1, ctrlY, y2) == 2) {
                double d12 = d2;
                double d13 = d7;
                if (getTag(dArr9[0], d12, d13) * getTag(dArr9[1], d12, d13) <= 0) {
                    return true;
                }
            }
            return false;
        }
        double[] dArr10 = dArr6;
        double[] dArr11 = dArr7;
        double d14 = x2 - x1;
        double d15 = y2 - y1;
        double d16 = (y2 * x1) - (x2 * y1);
        if (tag2 == 0) {
            d5 = y1;
            dArr = dArr11;
            i = tag;
        } else {
            d5 = y1;
            dArr = dArr11;
            i = getTag((d16 + ((tag2 < 0 ? d2 : d7) * d14)) / d15, d, d6);
        }
        if (tag4 != 0) {
            tag3 = getTag((d16 + (d14 * (tag4 < 0 ? d2 : d7))) / d15, d, d6);
        }
        if (i * tag3 <= 0) {
            return true;
        }
        int i2 = i * tag <= 0 ? tag2 : tag4;
        fillEqn(dArr10, tag3 < 0 ? d : d6, x1, ctrlX, x2);
        evalQuadratic(dArr, solveQuadratic(dArr10, dArr), true, true, (double[]) null, d5, ctrlY, y2);
        return i2 * getTag(dArr[0], d2, d7) <= 0;
    }

    public boolean intersects(Rectangle2D rectangle2D) {
        return intersects(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        if (d3 <= 0.0d || d4 <= 0.0d || !contains(d, d2)) {
            return false;
        }
        double d5 = d3 + d;
        if (!contains(d5, d2)) {
            return false;
        }
        double d6 = d2 + d4;
        if (!contains(d5, d6) || !contains(d, d6)) {
            return false;
        }
        return true;
    }

    public boolean contains(Rectangle2D rectangle2D) {
        return contains(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new QuadIterator(this, affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new FlatteningPathIterator(getPathIterator(affineTransform), d);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }
}
