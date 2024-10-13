package com.app.office.java.awt.geom;

import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Point2D;
import com.app.office.java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Arrays;

public abstract class CubicCurve2D implements Shape, Cloneable {
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

    public boolean contains(double d, double d2) {
        if ((d * 0.0d) + (d2 * 0.0d) != 0.0d) {
        }
        return false;
    }

    public abstract Point2D getCtrlP1();

    public abstract Point2D getCtrlP2();

    public abstract double getCtrlX1();

    public abstract double getCtrlX2();

    public abstract double getCtrlY1();

    public abstract double getCtrlY2();

    public abstract Point2D getP1();

    public abstract Point2D getP2();

    public abstract double getX1();

    public abstract double getX2();

    public abstract double getY1();

    public abstract double getY2();

    public abstract void setCurve(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8);

    public static class Float extends CubicCurve2D implements Serializable {
        private static final long serialVersionUID = -1272015596714244385L;
        public float ctrlx1;
        public float ctrlx2;
        public float ctrly1;
        public float ctrly2;
        public float x1;
        public float x2;
        public float y1;
        public float y2;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            setCurve(f, f2, f3, f4, f5, f6, f7, f8);
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

        public double getCtrlX1() {
            return (double) this.ctrlx1;
        }

        public double getCtrlY1() {
            return (double) this.ctrly1;
        }

        public Point2D getCtrlP1() {
            return new Point2D.Float(this.ctrlx1, this.ctrly1);
        }

        public double getCtrlX2() {
            return (double) this.ctrlx2;
        }

        public double getCtrlY2() {
            return (double) this.ctrly2;
        }

        public Point2D getCtrlP2() {
            return new Point2D.Float(this.ctrlx2, this.ctrly2);
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

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
            this.x1 = (float) d;
            this.y1 = (float) d2;
            this.ctrlx1 = (float) d3;
            this.ctrly1 = (float) d4;
            this.ctrlx2 = (float) d5;
            this.ctrly2 = (float) d6;
            this.x2 = (float) d7;
            this.y2 = (float) d8;
        }

        public void setCurve(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            this.x1 = f;
            this.y1 = f2;
            this.ctrlx1 = f3;
            this.ctrly1 = f4;
            this.ctrlx2 = f5;
            this.ctrly2 = f6;
            this.x2 = f7;
            this.y2 = f8;
        }

        public Rectangle2D getBounds2D() {
            float min = Math.min(Math.min(this.x1, this.x2), Math.min(this.ctrlx1, this.ctrlx2));
            float min2 = Math.min(Math.min(this.y1, this.y2), Math.min(this.ctrly1, this.ctrly2));
            return new Rectangle2D.Float(min, min2, Math.max(Math.max(this.x1, this.x2), Math.max(this.ctrlx1, this.ctrlx2)) - min, Math.max(Math.max(this.y1, this.y2), Math.max(this.ctrly1, this.ctrly2)) - min2);
        }
    }

    public static class Double extends CubicCurve2D implements Serializable {
        private static final long serialVersionUID = -4202960122839707295L;
        public double ctrlx1;
        public double ctrlx2;
        public double ctrly1;
        public double ctrly2;
        public double x1;
        public double x2;
        public double y1;
        public double y2;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
            setCurve(d, d2, d3, d4, d5, d6, d7, d8);
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

        public double getCtrlX1() {
            return this.ctrlx1;
        }

        public double getCtrlY1() {
            return this.ctrly1;
        }

        public Point2D getCtrlP1() {
            return new Point2D.Double(this.ctrlx1, this.ctrly1);
        }

        public double getCtrlX2() {
            return this.ctrlx2;
        }

        public double getCtrlY2() {
            return this.ctrly2;
        }

        public Point2D getCtrlP2() {
            return new Point2D.Double(this.ctrlx2, this.ctrly2);
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

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
            this.x1 = d;
            this.y1 = d2;
            this.ctrlx1 = d3;
            this.ctrly1 = d4;
            this.ctrlx2 = d5;
            this.ctrly2 = d6;
            this.x2 = d7;
            this.y2 = d8;
        }

        public Rectangle2D getBounds2D() {
            double min = Math.min(Math.min(this.x1, this.x2), Math.min(this.ctrlx1, this.ctrlx2));
            double min2 = Math.min(Math.min(this.y1, this.y2), Math.min(this.ctrly1, this.ctrly2));
            return new Rectangle2D.Double(min, min2, Math.max(Math.max(this.x1, this.x2), Math.max(this.ctrlx1, this.ctrlx2)) - min, Math.max(Math.max(this.y1, this.y2), Math.max(this.ctrly1, this.ctrly2)) - min2);
        }
    }

    protected CubicCurve2D() {
    }

    public void setCurve(double[] dArr, int i) {
        setCurve(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5], dArr[i + 6], dArr[i + 7]);
    }

    public void setCurve(Point2D point2D, Point2D point2D2, Point2D point2D3, Point2D point2D4) {
        setCurve(point2D.getX(), point2D.getY(), point2D2.getX(), point2D2.getY(), point2D3.getX(), point2D3.getY(), point2D4.getX(), point2D4.getY());
    }

    public void setCurve(Point2D[] point2DArr, int i) {
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 2;
        int i5 = i + 3;
        setCurve(point2DArr[i2].getX(), point2DArr[i2].getY(), point2DArr[i3].getX(), point2DArr[i3].getY(), point2DArr[i4].getX(), point2DArr[i4].getY(), point2DArr[i5].getX(), point2DArr[i5].getY());
    }

    public void setCurve(CubicCurve2D cubicCurve2D) {
        setCurve(cubicCurve2D.getX1(), cubicCurve2D.getY1(), cubicCurve2D.getCtrlX1(), cubicCurve2D.getCtrlY1(), cubicCurve2D.getCtrlX2(), cubicCurve2D.getCtrlY2(), cubicCurve2D.getX2(), cubicCurve2D.getY2());
    }

    public static double getFlatnessSq(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return Math.max(Line2D.ptSegDistSq(d, d2, d7, d8, d3, d4), Line2D.ptSegDistSq(d, d2, d7, d8, d5, d6));
    }

    public static double getFlatness(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return Math.sqrt(getFlatnessSq(d, d2, d3, d4, d5, d6, d7, d8));
    }

    public static double getFlatnessSq(double[] dArr, int i) {
        return getFlatnessSq(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5], dArr[i + 6], dArr[i + 7]);
    }

    public static double getFlatness(double[] dArr, int i) {
        return getFlatness(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5], dArr[i + 6], dArr[i + 7]);
    }

    public double getFlatnessSq() {
        return getFlatnessSq(getX1(), getY1(), getCtrlX1(), getCtrlY1(), getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }

    public double getFlatness() {
        return getFlatness(getX1(), getY1(), getCtrlX1(), getCtrlY1(), getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }

    public void subdivide(CubicCurve2D cubicCurve2D, CubicCurve2D cubicCurve2D2) {
        subdivide(this, cubicCurve2D, cubicCurve2D2);
    }

    public static void subdivide(CubicCurve2D cubicCurve2D, CubicCurve2D cubicCurve2D2, CubicCurve2D cubicCurve2D3) {
        double x1 = cubicCurve2D.getX1();
        double y1 = cubicCurve2D.getY1();
        double ctrlX1 = cubicCurve2D.getCtrlX1();
        double ctrlY1 = cubicCurve2D.getCtrlY1();
        double ctrlX2 = cubicCurve2D.getCtrlX2();
        double ctrlY2 = cubicCurve2D.getCtrlY2();
        double x2 = cubicCurve2D.getX2();
        double y2 = cubicCurve2D.getY2();
        double d = (ctrlX1 + ctrlX2) / 2.0d;
        double d2 = (ctrlY1 + ctrlY2) / 2.0d;
        double d3 = (ctrlX1 + x1) / 2.0d;
        double d4 = (ctrlY1 + y1) / 2.0d;
        double d5 = (x2 + ctrlX2) / 2.0d;
        double d6 = (y2 + ctrlY2) / 2.0d;
        double d7 = (d3 + d) / 2.0d;
        double d8 = (d4 + d2) / 2.0d;
        double d9 = (d5 + d) / 2.0d;
        double d10 = (d6 + d2) / 2.0d;
        double d11 = (d7 + d9) / 2.0d;
        double d12 = (d8 + d10) / 2.0d;
        if (cubicCurve2D2 != null) {
            cubicCurve2D2.setCurve(x1, y1, d3, d4, d7, d8, d11, d12);
        }
        if (cubicCurve2D3 != null) {
            cubicCurve2D3.setCurve(d11, d12, d9, d10, d5, d6, x2, y2);
        }
    }

    public static void subdivide(double[] dArr, int i, double[] dArr2, int i2, double[] dArr3, int i3) {
        double d = dArr[i + 0];
        double d2 = dArr[i + 1];
        double d3 = dArr[i + 2];
        double d4 = dArr[i + 3];
        double d5 = dArr[i + 4];
        double d6 = dArr[i + 5];
        double d7 = dArr[i + 6];
        double d8 = dArr[i + 7];
        if (dArr2 != null) {
            dArr2[i2 + 0] = d;
            dArr2[i2 + 1] = d2;
        }
        if (dArr3 != null) {
            dArr3[i3 + 6] = d7;
            dArr3[i3 + 7] = d8;
        }
        double d9 = (d + d3) / 2.0d;
        double d10 = (d2 + d4) / 2.0d;
        double d11 = (d7 + d5) / 2.0d;
        double d12 = (d8 + d6) / 2.0d;
        double d13 = (d3 + d5) / 2.0d;
        double d14 = (d4 + d6) / 2.0d;
        double d15 = (d9 + d13) / 2.0d;
        double d16 = (d10 + d14) / 2.0d;
        double d17 = (d13 + d11) / 2.0d;
        double d18 = (d14 + d12) / 2.0d;
        double d19 = (d15 + d17) / 2.0d;
        double d20 = (d16 + d18) / 2.0d;
        if (dArr2 != null) {
            dArr2[i2 + 2] = d9;
            dArr2[i2 + 3] = d10;
            dArr2[i2 + 4] = d15;
            dArr2[i2 + 5] = d16;
            dArr2[i2 + 6] = d19;
            dArr2[i2 + 7] = d20;
        }
        if (dArr3 != null) {
            dArr3[i3 + 0] = d19;
            dArr3[i3 + 1] = d20;
            dArr3[i3 + 2] = d17;
            dArr3[i3 + 3] = d18;
            dArr3[i3 + 4] = d11;
            dArr3[i3 + 5] = d12;
        }
    }

    public static int solveCubic(double[] dArr) {
        return solveCubic(dArr, dArr);
    }

    public static int solveCubic(double[] dArr, double[] dArr2) {
        double[] dArr3 = dArr;
        double[] dArr4 = dArr2;
        double d = dArr3[3];
        double d2 = 0.0d;
        if (d == 0.0d) {
            return QuadCurve2D.solveQuadratic(dArr, dArr2);
        }
        double d3 = dArr3[2] / d;
        double d4 = dArr3[1] / d;
        double d5 = dArr3[0] / d;
        double d6 = ((d3 * d3) - (d4 * 3.0d)) / 9.0d;
        double d7 = (((((2.0d * d3) * d3) * d3) - ((9.0d * d3) * d4)) + (d5 * 27.0d)) / 54.0d;
        double d8 = d7 * d7;
        double d9 = d6 * d6 * d6;
        double d10 = d3 / 3.0d;
        if (d8 < d9) {
            double acos = Math.acos(d7 / Math.sqrt(d9));
            double sqrt = Math.sqrt(d6) * -2.0d;
            if (dArr4 == dArr3) {
                double[] dArr5 = new double[4];
                System.arraycopy(dArr4, 0, dArr5, 0, 4);
                dArr3 = dArr5;
            }
            dArr4[0] = (Math.cos(acos / 3.0d) * sqrt) - d10;
            dArr4[1] = (Math.cos((acos + 6.283185307179586d) / 3.0d) * sqrt) - d10;
            dArr4[2] = (sqrt * Math.cos((acos - 6.283185307179586d) / 3.0d)) - d10;
            fixRoots(dArr4, dArr3);
            return 3;
        }
        boolean z = d7 < 0.0d;
        double sqrt2 = Math.sqrt(d8 - d9);
        if (z) {
            d7 = -d7;
        }
        double pow = Math.pow(d7 + sqrt2, 0.3333333333333333d);
        if (!z) {
            pow = -pow;
        }
        if (pow != 0.0d) {
            d2 = d6 / pow;
        }
        dArr4[0] = (pow + d2) - d10;
        return 1;
    }

    private static void fixRoots(double[] dArr, double[] dArr2) {
        for (int i = 0; i < 3; i++) {
            double d = dArr[i];
            if (Math.abs(d) < 1.0E-5d) {
                dArr[i] = findZero(d, 0.0d, dArr2);
            } else if (Math.abs(d - 1.0d) < 1.0E-5d) {
                dArr[i] = findZero(d, 1.0d, dArr2);
            }
        }
    }

    private static double solveEqn(double[] dArr, int i, double d) {
        double d2 = dArr[i];
        while (true) {
            i--;
            if (i < 0) {
                return d2;
            }
            d2 = (d2 * d) + dArr[i];
        }
    }

    private static double findZero(double d, double d2, double[] dArr) {
        double d3;
        double d4;
        double d5;
        double[] dArr2 = dArr;
        double[] dArr3 = {dArr2[1], dArr2[2] * 2.0d, dArr2[3] * 3.0d};
        double d6 = d;
        double d7 = 0.0d;
        while (true) {
            double solveEqn = solveEqn(dArr3, 2, d6);
            if (solveEqn == 0.0d) {
                return d6;
            }
            double solveEqn2 = solveEqn(dArr2, 3, d6);
            if (solveEqn2 == 0.0d) {
                return d6;
            }
            double d8 = -(solveEqn2 / solveEqn);
            double d9 = d7 == 0.0d ? d8 : d7;
            if (d6 < d2) {
                if (d8 < 0.0d) {
                    return d6;
                }
            } else if (d6 <= d2) {
                return d8 > 0.0d ? d2 + Double.MIN_VALUE : d2 - Double.MIN_VALUE;
            } else {
                if (d8 > 0.0d) {
                    return d6;
                }
            }
            double d10 = d6 + d8;
            if (d6 == d10) {
                return d6;
            }
            if (d8 * d9 < 0.0d) {
                if (d < d6) {
                    d5 = d2;
                    d4 = d;
                    d3 = d6;
                } else {
                    d5 = d2;
                    d4 = d6;
                    d3 = d;
                }
                if (getTag(d5, d4, d3) != 0) {
                    return (d + d6) / 2.0d;
                }
                d6 = d2;
            } else {
                d6 = d10;
            }
            d7 = d9;
        }
    }

    public boolean contains(Point2D point2D) {
        return contains(point2D.getX(), point2D.getY());
    }

    private static void fillEqn(double[] dArr, double d, double d2, double d3, double d4, double d5) {
        dArr[0] = d2 - d;
        dArr[1] = (d3 - d2) * 3.0d;
        dArr[2] = (((d4 - d3) - d3) + d2) * 3.0d;
        dArr[3] = (d5 + ((d3 - d4) * 3.0d)) - d2;
    }

    private static int evalCubic(double[] dArr, int i, boolean z, boolean z2, double[] dArr2, double d, double d2, double d3, double d4) {
        int i2 = 0;
        int i3 = i;
        int i4 = 0;
        while (i2 < i3) {
            double d5 = dArr[i2];
            int i5 = (d5 > 0.0d ? 1 : (d5 == 0.0d ? 0 : -1));
            if (z) {
                if (i5 < 0) {
                    i2++;
                }
            } else if (i5 <= 0) {
                i2++;
            }
            int i6 = (d5 > 1.0d ? 1 : (d5 == 1.0d ? 0 : -1));
            if (z2) {
                if (i6 > 0) {
                    i2++;
                }
            } else if (i6 >= 0) {
                i2++;
            }
            if (dArr2 == null || dArr2[1] + (((dArr2[2] * 2.0d) + (dArr2[3] * 3.0d * d5)) * d5) != 0.0d) {
                double d6 = 1.0d - d5;
                dArr[i4] = (d * d6 * d6 * d6) + (d2 * 3.0d * d5 * d6 * d6) + (3.0d * d3 * d5 * d5 * d6) + (d4 * d5 * d5 * d5);
                i4++;
                i2++;
            } else {
                i2++;
            }
        }
        return i4;
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        int i;
        if (d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        double x1 = getX1();
        double y1 = getY1();
        double d5 = d + d3;
        int tag = getTag(x1, d, d5);
        double d6 = d2 + d4;
        int tag2 = getTag(y1, d2, d6);
        if (tag == 0 && tag2 == 0) {
            return true;
        }
        double x2 = getX2();
        double y2 = getY2();
        int tag3 = getTag(x2, d, d5);
        int tag4 = getTag(y2, d2, d6);
        if (tag3 == 0 && tag4 == 0) {
            return true;
        }
        double ctrlX1 = getCtrlX1();
        double ctrlY1 = getCtrlY1();
        double ctrlX2 = getCtrlX2();
        double ctrlY2 = getCtrlY2();
        int tag5 = getTag(ctrlX1, d, d5);
        int tag6 = getTag(ctrlY1, d2, d6);
        int tag7 = getTag(ctrlX2, d, d5);
        int tag8 = getTag(ctrlY2, d2, d6);
        if (tag < 0 && tag3 < 0 && tag5 < 0 && tag7 < 0) {
            return false;
        }
        if (tag2 < 0 && tag4 < 0 && tag6 < 0 && tag8 < 0) {
            return false;
        }
        if (tag > 0 && tag3 > 0 && tag5 > 0 && tag7 > 0) {
            return false;
        }
        if (tag2 > 0 && tag4 > 0 && tag6 > 0 && tag8 > 0) {
            return false;
        }
        if (inwards(tag, tag3, tag5) && inwards(tag2, tag4, tag6)) {
            return true;
        }
        if (inwards(tag3, tag, tag7) && inwards(tag4, tag2, tag8)) {
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
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        if (!z2) {
            double d7 = y1;
            double[] dArr3 = dArr2;
            fillEqn(dArr, tag2 < 0 ? d2 : d6, d7, ctrlY1, ctrlY2, y2);
            double[] dArr4 = dArr3;
            if (evalCubic(dArr3, solveCubic(dArr, dArr3), true, true, (double[]) null, x1, ctrlX1, ctrlX2, x2) == 2) {
                double d8 = d;
                double d9 = d5;
                if (getTag(dArr4[0], d8, d9) * getTag(dArr4[1], d8, d9) <= 0) {
                    return true;
                }
            }
            return false;
        }
        boolean z3 = z;
        double[] dArr5 = dArr;
        double[] dArr6 = dArr2;
        if (!z3) {
            double[] dArr7 = dArr6;
            fillEqn(dArr5, tag < 0 ? d : d5, x1, ctrlX1, ctrlX2, x2);
            if (evalCubic(dArr7, solveCubic(dArr5, dArr7), true, true, (double[]) null, y1, ctrlY1, ctrlY2, y2) == 2) {
                double d10 = d2;
                double d11 = d6;
                if (getTag(dArr7[0], d10, d11) * getTag(dArr7[1], d10, d11) <= 0) {
                    return true;
                }
            }
            return false;
        }
        double d12 = x2 - x1;
        double d13 = y2 - y1;
        double d14 = (y2 * x1) - (x2 * y1);
        if (tag2 == 0) {
            i = tag;
        } else {
            i = getTag((d14 + ((tag2 < 0 ? d2 : d6) * d12)) / d13, d, d5);
        }
        if (tag4 != 0) {
            tag3 = getTag((d14 + (d12 * (tag4 < 0 ? d2 : d6))) / d13, d, d5);
        }
        if (i * tag3 <= 0) {
            return true;
        }
        int i2 = i * tag <= 0 ? tag2 : tag4;
        double[] dArr8 = dArr6;
        fillEqn(dArr5, tag3 < 0 ? d : d5, x1, ctrlX1, ctrlX2, x2);
        int evalCubic = evalCubic(dArr8, solveCubic(dArr5, dArr8), true, true, (double[]) null, y1, ctrlY1, ctrlY2, y2);
        int[] iArr = new int[(evalCubic + 1)];
        for (int i3 = 0; i3 < evalCubic; i3++) {
            iArr[i3] = getTag(dArr8[i3], d2, d6);
        }
        iArr[evalCubic] = i2;
        Arrays.sort(iArr);
        return (evalCubic >= 1 && iArr[0] * iArr[1] <= 0) || (evalCubic >= 3 && iArr[2] * iArr[3] <= 0);
    }

    public boolean intersects(Rectangle2D rectangle2D) {
        return intersects(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        double d5 = d;
        double d6 = d2;
        if (d3 <= 0.0d || d4 <= 0.0d || !contains(d, d2)) {
            return false;
        }
        double d7 = d5 + d3;
        if (!contains(d7, d6)) {
            return false;
        }
        double d8 = d6 + d4;
        if (!contains(d7, d8) || !contains(d5, d8)) {
            return false;
        }
        Rectangle2D.Double doubleR = new Rectangle2D.Double(d, d2, d3, d4);
        return !doubleR.intersectsLine(getX1(), getY1(), getX2(), getY2());
    }

    public boolean contains(Rectangle2D rectangle2D) {
        return contains(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new CubicIterator(this, affineTransform);
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
