package com.app.office.java.awt.geom;

import java.io.PrintStream;
import java.util.Vector;

public abstract class Curve {
    public static final int DECREASING = -1;
    public static final int INCREASING = 1;
    public static final int RECT_INTERSECTS = Integer.MIN_VALUE;
    public static final double TMIN = 0.001d;
    protected int direction;

    public static int orderof(double d, double d2) {
        if (d < d2) {
            return -1;
        }
        return d > d2 ? 1 : 0;
    }

    public static int pointCrossingsForLine(double d, double d2, double d3, double d4, double d5, double d6) {
        if (d2 < d4 && d2 < d6) {
            return 0;
        }
        if (d2 >= d4 && d2 >= d6) {
            return 0;
        }
        if (d >= d3 && d >= d5) {
            return 0;
        }
        if (d < d3 && d < d5) {
            return d4 < d6 ? 1 : -1;
        }
        if (d >= d3 + (((d2 - d4) * (d5 - d3)) / (d6 - d4))) {
            return 0;
        }
        return d4 < d6 ? 1 : -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00a2 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int rectCrossingsForLine(int r10, double r11, double r13, double r15, double r17, double r19, double r21, double r23, double r25) {
        /*
            int r0 = (r21 > r17 ? 1 : (r21 == r17 ? 0 : -1))
            if (r0 < 0) goto L_0x0009
            int r1 = (r25 > r17 ? 1 : (r25 == r17 ? 0 : -1))
            if (r1 < 0) goto L_0x0009
            return r10
        L_0x0009:
            int r1 = (r21 > r13 ? 1 : (r21 == r13 ? 0 : -1))
            if (r1 > 0) goto L_0x0012
            int r2 = (r25 > r13 ? 1 : (r25 == r13 ? 0 : -1))
            if (r2 > 0) goto L_0x0012
            return r10
        L_0x0012:
            int r2 = (r19 > r11 ? 1 : (r19 == r11 ? 0 : -1))
            if (r2 > 0) goto L_0x001b
            int r2 = (r23 > r11 ? 1 : (r23 == r11 ? 0 : -1))
            if (r2 > 0) goto L_0x001b
            return r10
        L_0x001b:
            int r2 = (r19 > r15 ? 1 : (r19 == r15 ? 0 : -1))
            if (r2 < 0) goto L_0x0049
            int r2 = (r23 > r15 ? 1 : (r23 == r15 ? 0 : -1))
            if (r2 < 0) goto L_0x0049
            int r2 = (r21 > r25 ? 1 : (r21 == r25 ? 0 : -1))
            if (r2 >= 0) goto L_0x0034
            if (r1 > 0) goto L_0x002c
            int r0 = r10 + 1
            goto L_0x002d
        L_0x002c:
            r0 = r10
        L_0x002d:
            int r1 = (r25 > r17 ? 1 : (r25 == r17 ? 0 : -1))
            if (r1 < 0) goto L_0x0048
            int r0 = r0 + 1
            goto L_0x0048
        L_0x0034:
            int r1 = (r25 > r21 ? 1 : (r25 == r21 ? 0 : -1))
            if (r1 >= 0) goto L_0x0047
            int r1 = (r25 > r13 ? 1 : (r25 == r13 ? 0 : -1))
            if (r1 > 0) goto L_0x003f
            int r1 = r10 + -1
            goto L_0x0040
        L_0x003f:
            r1 = r10
        L_0x0040:
            if (r0 < 0) goto L_0x0045
            int r0 = r1 + -1
            goto L_0x0048
        L_0x0045:
            r0 = r1
            goto L_0x0048
        L_0x0047:
            r0 = r10
        L_0x0048:
            return r0
        L_0x0049:
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            int r3 = (r19 > r11 ? 1 : (r19 == r11 ? 0 : -1))
            if (r3 <= 0) goto L_0x005b
            int r3 = (r19 > r15 ? 1 : (r19 == r15 ? 0 : -1))
            if (r3 >= 0) goto L_0x005b
            int r3 = (r21 > r13 ? 1 : (r21 == r13 ? 0 : -1))
            if (r3 <= 0) goto L_0x005b
            int r3 = (r21 > r17 ? 1 : (r21 == r17 ? 0 : -1))
            if (r3 < 0) goto L_0x006b
        L_0x005b:
            int r3 = (r23 > r11 ? 1 : (r23 == r11 ? 0 : -1))
            if (r3 <= 0) goto L_0x006c
            int r3 = (r23 > r15 ? 1 : (r23 == r15 ? 0 : -1))
            if (r3 >= 0) goto L_0x006c
            int r3 = (r25 > r13 ? 1 : (r25 == r13 ? 0 : -1))
            if (r3 <= 0) goto L_0x006c
            int r3 = (r25 > r17 ? 1 : (r25 == r17 ? 0 : -1))
            if (r3 >= 0) goto L_0x006c
        L_0x006b:
            return r2
        L_0x006c:
            if (r1 >= 0) goto L_0x007a
            double r3 = r13 - r21
        L_0x0070:
            double r5 = r23 - r19
            double r3 = r3 * r5
            double r5 = r25 - r21
            double r3 = r3 / r5
            double r3 = r19 + r3
            goto L_0x0081
        L_0x007a:
            if (r0 <= 0) goto L_0x007f
            double r3 = r17 - r21
            goto L_0x0070
        L_0x007f:
            r3 = r19
        L_0x0081:
            int r5 = (r25 > r13 ? 1 : (r25 == r13 ? 0 : -1))
            if (r5 >= 0) goto L_0x0091
            double r6 = r13 - r25
        L_0x0087:
            double r8 = r19 - r23
            double r6 = r6 * r8
            double r8 = r21 - r25
            double r6 = r6 / r8
            double r6 = r23 + r6
            goto L_0x009a
        L_0x0091:
            int r6 = (r25 > r17 ? 1 : (r25 == r17 ? 0 : -1))
            if (r6 <= 0) goto L_0x0098
            double r6 = r17 - r25
            goto L_0x0087
        L_0x0098:
            r6 = r23
        L_0x009a:
            int r8 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r8 > 0) goto L_0x00a3
            int r8 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            if (r8 > 0) goto L_0x00a3
            return r10
        L_0x00a3:
            int r8 = (r3 > r15 ? 1 : (r3 == r15 ? 0 : -1))
            if (r8 < 0) goto L_0x00cf
            int r3 = (r6 > r15 ? 1 : (r6 == r15 ? 0 : -1))
            if (r3 < 0) goto L_0x00cf
            int r2 = (r21 > r25 ? 1 : (r21 == r25 ? 0 : -1))
            if (r2 >= 0) goto L_0x00bc
            if (r1 > 0) goto L_0x00b4
            int r0 = r10 + 1
            goto L_0x00b5
        L_0x00b4:
            r0 = r10
        L_0x00b5:
            int r1 = (r25 > r17 ? 1 : (r25 == r17 ? 0 : -1))
            if (r1 < 0) goto L_0x00ce
            int r0 = r0 + 1
            goto L_0x00ce
        L_0x00bc:
            int r1 = (r25 > r21 ? 1 : (r25 == r21 ? 0 : -1))
            if (r1 >= 0) goto L_0x00cd
            if (r5 > 0) goto L_0x00c5
            int r1 = r10 + -1
            goto L_0x00c6
        L_0x00c5:
            r1 = r10
        L_0x00c6:
            if (r0 < 0) goto L_0x00cb
            int r0 = r1 + -1
            goto L_0x00ce
        L_0x00cb:
            r0 = r1
            goto L_0x00ce
        L_0x00cd:
            r0 = r10
        L_0x00ce:
            return r0
        L_0x00cf:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.Curve.rectCrossingsForLine(int, double, double, double, double, double, double, double, double):int");
    }

    public static double round(double d) {
        return d;
    }

    public abstract double TforY(double d);

    public abstract double XforT(double d);

    public abstract double XforY(double d);

    public abstract double YforT(double d);

    public String controlPointString() {
        return "";
    }

    public abstract double dXforT(double d, int i);

    public abstract double dYforT(double d, int i);

    public abstract void enlarge(Rectangle2D rectangle2D);

    public abstract int getOrder();

    public abstract Curve getReversedCurve();

    public abstract int getSegment(double[] dArr);

    public abstract Curve getSubCurve(double d, double d2, int i);

    public abstract double getX0();

    public abstract double getX1();

    public abstract double getXBot();

    public abstract double getXMax();

    public abstract double getXMin();

    public abstract double getXTop();

    public abstract double getY0();

    public abstract double getY1();

    public abstract double getYBot();

    public abstract double getYTop();

    public abstract double nextVertical(double d, double d2);

    public static void insertMove(Vector vector, double d, double d2) {
        vector.add(new Order0(d, d2));
    }

    public static void insertLine(Vector vector, double d, double d2, double d3, double d4) {
        Vector vector2 = vector;
        if (d2 < d4) {
            vector.add(new Order1(d, d2, d3, d4, 1));
        } else if (d2 > d4) {
            vector.add(new Order1(d3, d4, d, d2, -1));
        }
    }

    public static void insertQuad(Vector vector, double d, double d2, double[] dArr) {
        double d3 = dArr[3];
        int i = (d2 > d3 ? 1 : (d2 == d3 ? 0 : -1));
        if (i > 0) {
            Order2.insert(vector, dArr, dArr[2], d3, dArr[0], dArr[1], d, d2, -1);
        } else if (i != 0 || d2 != dArr[1]) {
            Order2.insert(vector, dArr, d, d2, dArr[0], dArr[1], dArr[2], d3, 1);
        }
    }

    public static void insertCubic(Vector vector, double d, double d2, double[] dArr) {
        double d3 = dArr[5];
        int i = (d2 > d3 ? 1 : (d2 == d3 ? 0 : -1));
        if (i > 0) {
            Order3.insert(vector, dArr, dArr[4], d3, dArr[2], dArr[3], dArr[0], dArr[1], d, d2, -1);
        } else if (i != 0 || d2 != dArr[1] || d2 != dArr[3]) {
            Order3.insert(vector, dArr, d, d2, dArr[0], dArr[1], dArr[2], dArr[3], dArr[4], d3, 1);
        }
    }

    public static int pointCrossingsForPath(PathIterator pathIterator, double d, double d2) {
        PathIterator pathIterator2 = pathIterator;
        if (pathIterator.isDone()) {
            return 0;
        }
        double[] dArr = new double[6];
        if (pathIterator2.currentSegment(dArr) == 0) {
            pathIterator.next();
            double d3 = dArr[0];
            double d4 = dArr[1];
            double d5 = d3;
            double d6 = d4;
            int i = 0;
            while (!pathIterator.isDone()) {
                int currentSegment = pathIterator2.currentSegment(dArr);
                if (currentSegment != 0) {
                    if (currentSegment == 1) {
                        double d7 = dArr[0];
                        double d8 = dArr[1];
                        i += pointCrossingsForLine(d, d2, d5, d6, d7, d8);
                        d5 = d7;
                        d6 = d8;
                    } else if (currentSegment == 2) {
                        double d9 = dArr[2];
                        double d10 = dArr[3];
                        i += pointCrossingsForQuad(d, d2, d5, d6, dArr[0], dArr[1], d9, d10, 0);
                        d5 = d9;
                        d6 = d10;
                    } else if (currentSegment == 3) {
                        double d11 = dArr[4];
                        double d12 = dArr[5];
                        i += pointCrossingsForCubic(d, d2, d5, d6, dArr[0], dArr[1], dArr[2], dArr[3], d11, d12, 0);
                        d5 = d11;
                        d6 = d12;
                    } else if (currentSegment == 4) {
                        if (d6 != d4) {
                            i += pointCrossingsForLine(d, d2, d5, d6, d3, d4);
                        }
                    }
                    pathIterator.next();
                } else {
                    if (d6 != d4) {
                        i += pointCrossingsForLine(d, d2, d5, d6, d3, d4);
                    }
                    d3 = dArr[0];
                    d4 = dArr[1];
                }
                d5 = d3;
                d6 = d4;
                pathIterator.next();
            }
            return d6 != d4 ? i + pointCrossingsForLine(d, d2, d5, d6, d3, d4) : i;
        }
        throw new IllegalPathStateException("missing initial moveto in path definition");
    }

    public static int pointCrossingsForQuad(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, int i) {
        int i2 = i;
        if (d2 < d4 && d2 < d6 && d2 < d8) {
            return 0;
        }
        int i3 = (d2 > d4 ? 1 : (d2 == d4 ? 0 : -1));
        if (i3 >= 0 && d2 >= d6 && d2 >= d8) {
            return 0;
        }
        if (d >= d3 && d >= d5 && d >= d7) {
            return 0;
        }
        if (d < d3 && d < d5 && d < d7) {
            if (i3 >= 0) {
                if (d2 < d8) {
                    return 1;
                }
            } else if (d2 >= d8) {
                return -1;
            }
            return 0;
        } else if (i2 > 52) {
            return pointCrossingsForLine(d, d2, d3, d4, d7, d8);
        } else {
            double d9 = (d3 + d5) / 2.0d;
            double d10 = (d4 + d6) / 2.0d;
            double d11 = (d5 + d7) / 2.0d;
            double d12 = (d6 + d8) / 2.0d;
            double d13 = (d9 + d11) / 2.0d;
            double d14 = (d10 + d12) / 2.0d;
            if (Double.isNaN(d13) || Double.isNaN(d14)) {
                return 0;
            }
            int i4 = i2 + 1;
            return pointCrossingsForQuad(d, d2, d3, d4, d9, d10, d13, d14, i4) + pointCrossingsForQuad(d, d2, d13, d14, d11, d12, d7, d8, i4);
        }
    }

    public static int pointCrossingsForCubic(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, int i) {
        int i2 = i;
        if (d2 < d4 && d2 < d6 && d2 < d8 && d2 < d10) {
            return 0;
        }
        int i3 = (d2 > d4 ? 1 : (d2 == d4 ? 0 : -1));
        if (i3 >= 0 && d2 >= d6 && d2 >= d8 && d2 >= d10) {
            return 0;
        }
        if (d >= d3 && d >= d5 && d >= d7 && d >= d9) {
            return 0;
        }
        if (d < d3 && d < d5 && d < d7 && d < d9) {
            if (i3 >= 0) {
                if (d2 < d10) {
                    return 1;
                }
            } else if (d2 >= d10) {
                return -1;
            }
            return 0;
        } else if (i2 > 52) {
            return pointCrossingsForLine(d, d2, d3, d4, d9, d10);
        } else {
            double d11 = (d5 + d7) / 2.0d;
            double d12 = (d6 + d8) / 2.0d;
            double d13 = (d3 + d5) / 2.0d;
            double d14 = (d4 + d6) / 2.0d;
            double d15 = (d7 + d9) / 2.0d;
            double d16 = (d8 + d10) / 2.0d;
            double d17 = (d13 + d11) / 2.0d;
            double d18 = (d14 + d12) / 2.0d;
            double d19 = (d11 + d15) / 2.0d;
            double d20 = (d12 + d16) / 2.0d;
            double d21 = (d17 + d19) / 2.0d;
            double d22 = (d18 + d20) / 2.0d;
            if (Double.isNaN(d21) || Double.isNaN(d22)) {
                return 0;
            }
            int i4 = i2 + 1;
            return pointCrossingsForCubic(d, d2, d3, d4, d13, d14, d17, d18, d21, d22, i4) + pointCrossingsForCubic(d, d2, d21, d22, d19, d20, d15, d16, d9, d10, i4);
        }
    }

    public static int rectCrossingsForPath(PathIterator pathIterator, double d, double d2, double d3, double d4) {
        double[] dArr;
        PathIterator pathIterator2 = pathIterator;
        if (d3 <= d || d4 <= d2 || pathIterator.isDone()) {
            return 0;
        }
        double[] dArr2 = new double[6];
        if (pathIterator2.currentSegment(dArr2) == 0) {
            pathIterator.next();
            int i = 1;
            double d5 = dArr2[0];
            double d6 = d5;
            double d7 = dArr2[1];
            double d8 = d7;
            int i2 = 0;
            while (i2 != Integer.MIN_VALUE && !pathIterator.isDone()) {
                int currentSegment = pathIterator2.currentSegment(dArr2);
                if (currentSegment == 0) {
                    dArr = dArr2;
                    if (!(d5 == d6 && d7 == d8)) {
                        i2 = rectCrossingsForLine(i2, d, d2, d3, d4, d5, d7, d6, d8);
                    }
                    d5 = dArr[0];
                    d7 = dArr[1];
                    d6 = d5;
                    d8 = d7;
                } else if (currentSegment == i) {
                    dArr = dArr2;
                    double d9 = dArr[0];
                    double d10 = dArr[1];
                    i2 = rectCrossingsForLine(i2, d, d2, d3, d4, d5, d7, d9, d10);
                    d5 = d9;
                    d7 = d10;
                } else if (currentSegment == 2) {
                    dArr = dArr2;
                    double d11 = dArr[2];
                    double d12 = dArr[3];
                    double d13 = d;
                    double d14 = d2;
                    double d15 = d3;
                    double d16 = d4;
                    double d17 = d7;
                    i2 = rectCrossingsForQuad(i2, d13, d14, d15, d16, d5, d17, dArr[0], dArr[1], d11, d12, 0);
                    d5 = d11;
                    d7 = d12;
                } else if (currentSegment == 3) {
                    dArr = dArr2;
                    double d18 = dArr[4];
                    double d19 = dArr[5];
                    double d20 = d;
                    double d21 = d2;
                    double d22 = d3;
                    double d23 = d4;
                    double d24 = d7;
                    i2 = rectCrossingsForCubic(i2, d20, d21, d22, d23, d5, d24, dArr[0], dArr[1], dArr[2], dArr[3], d18, d19, 0);
                    d5 = d18;
                    d7 = d19;
                } else if (currentSegment != 4) {
                    dArr = dArr2;
                } else {
                    if (d5 == d6 && d7 == d8) {
                        dArr = dArr2;
                    } else {
                        dArr = dArr2;
                        i2 = rectCrossingsForLine(i2, d, d2, d3, d4, d5, d7, d6, d8);
                    }
                    d5 = d6;
                    d7 = d8;
                }
                pathIterator.next();
                dArr2 = dArr;
                i = 1;
            }
            if (i2 == Integer.MIN_VALUE) {
                return i2;
            }
            if (d5 == d6 && d7 == d8) {
                return i2;
            }
            return rectCrossingsForLine(i2, d, d2, d3, d4, d5, d7, d6, d8);
        }
        throw new IllegalPathStateException("missing initial moveto in path definition");
    }

    public static int rectCrossingsForQuad(int i, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, int i2) {
        int i3 = i2;
        int i4 = (d6 > d4 ? 1 : (d6 == d4 ? 0 : -1));
        if (i4 >= 0 && d8 >= d4 && d10 >= d4) {
            return i;
        }
        int i5 = (d6 > d2 ? 1 : (d6 == d2 ? 0 : -1));
        if (i5 <= 0 && d8 <= d2 && d10 <= d2) {
            return i;
        }
        if (d5 <= d && d7 <= d && d9 <= d) {
            return i;
        }
        if (d5 < d3 || d7 < d3 || d9 < d3) {
            if ((d5 < d3 && d5 > d && d6 < d4 && d6 > d2) || (d9 < d3 && d9 > d && d10 < d4 && d10 > d2)) {
                return Integer.MIN_VALUE;
            }
            if (i3 > 52) {
                return rectCrossingsForLine(i, d, d2, d3, d4, d5, d6, d9, d10);
            }
            double d11 = (d5 + d7) / 2.0d;
            double d12 = (d6 + d8) / 2.0d;
            double d13 = (d7 + d9) / 2.0d;
            double d14 = (d8 + d10) / 2.0d;
            double d15 = (d11 + d13) / 2.0d;
            double d16 = (d12 + d14) / 2.0d;
            if (Double.isNaN(d15) || Double.isNaN(d16)) {
                return 0;
            }
            int i6 = i3 + 1;
            int rectCrossingsForQuad = rectCrossingsForQuad(i, d, d2, d3, d4, d5, d6, d11, d12, d15, d16, i6);
            return rectCrossingsForQuad != Integer.MIN_VALUE ? rectCrossingsForQuad(rectCrossingsForQuad, d, d2, d3, d4, d15, d16, d13, d14, d9, d10, i6) : rectCrossingsForQuad;
        } else if (d6 < d10) {
            int i7 = (i5 > 0 || d10 <= d2) ? i : i + 1;
            return (d6 >= d4 || d10 < d4) ? i7 : i7 + 1;
        } else if (d10 >= d6) {
            return i;
        } else {
            int i8 = (d10 > d2 || d6 <= d2) ? i : i - 1;
            return (d10 >= d4 || i4 < 0) ? i8 : i8 - 1;
        }
    }

    public static int rectCrossingsForCubic(int i, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, int i2) {
        int i3 = i2;
        int i4 = (d6 > d4 ? 1 : (d6 == d4 ? 0 : -1));
        if (i4 >= 0 && d8 >= d4 && d10 >= d4 && d12 >= d4) {
            return i;
        }
        int i5 = (d6 > d2 ? 1 : (d6 == d2 ? 0 : -1));
        if (i5 <= 0 && d8 <= d2 && d10 <= d2 && d12 <= d2) {
            return i;
        }
        if (d5 <= d && d7 <= d && d9 <= d && d11 <= d) {
            return i;
        }
        if (d5 < d3 || d7 < d3 || d9 < d3 || d11 < d3) {
            if ((d5 > d && d5 < d3 && d6 > d2 && d6 < d4) || (d11 > d && d11 < d3 && d12 > d2 && d12 < d4)) {
                return Integer.MIN_VALUE;
            }
            if (i3 > 52) {
                return rectCrossingsForLine(i, d, d2, d3, d4, d5, d6, d11, d12);
            }
            double d13 = (d7 + d9) / 2.0d;
            double d14 = (d8 + d10) / 2.0d;
            double d15 = (d5 + d7) / 2.0d;
            double d16 = (d6 + d8) / 2.0d;
            double d17 = (d9 + d11) / 2.0d;
            double d18 = (d10 + d12) / 2.0d;
            double d19 = (d15 + d13) / 2.0d;
            double d20 = (d16 + d14) / 2.0d;
            double d21 = (d13 + d17) / 2.0d;
            double d22 = (d14 + d18) / 2.0d;
            double d23 = (d19 + d21) / 2.0d;
            double d24 = (d20 + d22) / 2.0d;
            if (Double.isNaN(d23) || Double.isNaN(d24)) {
                return 0;
            }
            int i6 = i3 + 1;
            int rectCrossingsForCubic = rectCrossingsForCubic(i, d, d2, d3, d4, d5, d6, d15, d16, d19, d20, d23, d24, i6);
            return rectCrossingsForCubic != Integer.MIN_VALUE ? rectCrossingsForCubic(rectCrossingsForCubic, d, d2, d3, d4, d23, d24, d21, d22, d17, d18, d11, d12, i6) : rectCrossingsForCubic;
        } else if (d6 < d12) {
            int i7 = (i5 > 0 || d12 <= d2) ? i : i + 1;
            return (d6 >= d4 || d12 < d4) ? i7 : i7 + 1;
        } else if (d12 >= d6) {
            return i;
        } else {
            int i8 = (d12 > d2 || d6 <= d2) ? i : i - 1;
            return (d12 >= d4 || i4 < 0) ? i8 : i8 - 1;
        }
    }

    public Curve(int i) {
        this.direction = i;
    }

    public final int getDirection() {
        return this.direction;
    }

    public final Curve getWithDirection(int i) {
        return this.direction == i ? this : getReversedCurve();
    }

    public static long signeddiffbits(double d, double d2) {
        return Double.doubleToLongBits(d) - Double.doubleToLongBits(d2);
    }

    public static long diffbits(double d, double d2) {
        return Math.abs(Double.doubleToLongBits(d) - Double.doubleToLongBits(d2));
    }

    public static double prev(double d) {
        return Double.longBitsToDouble(Double.doubleToLongBits(d) - 1);
    }

    public static double next(double d) {
        return Double.longBitsToDouble(Double.doubleToLongBits(d) + 1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Curve[");
        sb.append(getOrder());
        sb.append(", ");
        sb.append("(");
        sb.append(round(getX0()));
        sb.append(", ");
        sb.append(round(getY0()));
        sb.append("), ");
        sb.append(controlPointString());
        sb.append("(");
        sb.append(round(getX1()));
        sb.append(", ");
        sb.append(round(getY1()));
        sb.append("), ");
        sb.append(this.direction == 1 ? "D" : "U");
        sb.append("]");
        return sb.toString();
    }

    public int crossingsFor(double d, double d2) {
        if (d2 < getYTop() || d2 >= getYBot() || d >= getXMax()) {
            return 0;
        }
        return (d < getXMin() || d < XforY(d2)) ? 1 : 0;
    }

    public boolean accumulateCrossings(Crossings crossings) {
        double d;
        double d2;
        double d3;
        double d4;
        double xHi = crossings.getXHi();
        if (getXMin() >= xHi) {
            return false;
        }
        double xLo = crossings.getXLo();
        double yLo = crossings.getYLo();
        double yHi = crossings.getYHi();
        double yTop = getYTop();
        double yBot = getYBot();
        if (yTop < yLo) {
            if (yBot <= yLo) {
                return false;
            }
            d2 = TforY(yLo);
            d = yLo;
        } else if (yTop >= yHi) {
            return false;
        } else {
            d = yTop;
            d2 = 0.0d;
        }
        if (yBot > yHi) {
            d4 = TforY(yHi);
            d3 = yHi;
        } else {
            d4 = 1.0d;
            d3 = yBot;
        }
        boolean z = false;
        boolean z2 = false;
        while (true) {
            double XforT = XforT(d2);
            if (XforT < xHi) {
                if (z2 || XforT > xLo) {
                    return true;
                }
                z = true;
            } else if (z) {
                return true;
            } else {
                z2 = true;
            }
            if (d2 >= d4) {
                if (z) {
                    crossings.record(d, d3, this.direction);
                }
                return false;
            }
            d2 = nextVertical(d2, d4);
        }
        return true;
    }

    public Curve getSubCurve(double d, double d2) {
        return getSubCurve(d, d2, this.direction);
    }

    public int compareTo(Curve curve, double[] dArr) {
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        Curve curve2 = this;
        Curve curve3 = curve;
        double d7 = dArr[0];
        double min = Math.min(Math.min(dArr[1], getYBot()), curve.getYBot());
        String str = "=>";
        if (min > dArr[0]) {
            dArr[1] = min;
            if (getXMax() <= curve.getXMin()) {
                if (getXMin() == curve.getXMax()) {
                    return 0;
                }
                return -1;
            } else if (getXMin() >= curve.getXMax()) {
                return 1;
            } else {
                double TforY = curve2.TforY(d7);
                double YforT = curve2.YforT(TforY);
                if (YforT < d7) {
                    TforY = refineTforY(TforY, YforT, d7);
                    YforT = curve2.YforT(TforY);
                }
                double d8 = TforY;
                double d9 = YforT;
                double TforY2 = curve2.TforY(min);
                if (curve2.YforT(TforY2) < d7) {
                    TforY2 = refineTforY(TforY2, curve2.YforT(TforY2), d7);
                }
                double d10 = TforY2;
                double TforY3 = curve3.TforY(d7);
                double YforT2 = curve3.YforT(TforY3);
                if (YforT2 < d7) {
                    d = d10;
                    TforY3 = curve.refineTforY(TforY3, YforT2, d7);
                    YforT2 = curve3.YforT(TforY3);
                } else {
                    d = d10;
                }
                double d11 = TforY3;
                double d12 = YforT2;
                double TforY4 = curve3.TforY(min);
                if (curve3.YforT(TforY4) < d7) {
                    d2 = min;
                    d3 = d11;
                    TforY4 = curve.refineTforY(TforY4, curve3.YforT(TforY4), d7);
                } else {
                    d2 = min;
                    d3 = d11;
                }
                double d13 = TforY4;
                double XforT = curve2.XforT(d8);
                double XforT2 = curve3.XforT(d3);
                double d14 = d3;
                double d15 = d8;
                double max = Math.max(Math.max(Math.abs(d7), Math.abs(d2)) * 1.0E-14d, 1.0E-300d);
                if (curve2.fairlyClose(XforT, XforT2)) {
                    d5 = XforT;
                    double min2 = Math.min(1.0E13d * max, (d2 - d7) * 0.1d);
                    double d16 = d7 + max;
                    double d17 = max;
                    while (true) {
                        if (d16 > d2) {
                            d4 = d13;
                            d6 = XforT2;
                            break;
                        }
                        d6 = XforT2;
                        d4 = d13;
                        if (curve2.fairlyClose(curve2.XforY(d16), curve3.XforY(d16))) {
                            d17 *= 2.0d;
                            if (d17 > min2) {
                                d17 = min2;
                            }
                            d16 += d17;
                            XforT2 = d6;
                            d13 = d4;
                        } else {
                            d16 -= d17;
                            while (true) {
                                d17 /= 2.0d;
                                double d18 = d16 + d17;
                                if (d18 <= d16) {
                                    break;
                                } else if (curve2.fairlyClose(curve2.XforY(d18), curve3.XforY(d18))) {
                                    d16 = d18;
                                }
                            }
                        }
                    }
                    if (d16 > d7) {
                        if (d16 < d2) {
                            dArr[1] = d16;
                        }
                        return 0;
                    }
                } else {
                    d5 = XforT;
                    d4 = d13;
                    d6 = XforT2;
                }
                if (max <= 0.0d) {
                    PrintStream printStream = System.out;
                    printStream.println("ymin = " + max);
                }
                double d19 = d9;
                double d20 = d12;
                double d21 = d14;
                double d22 = d15;
                double d23 = d6;
                double d24 = d;
                double d25 = d5;
                while (true) {
                    if (d22 >= d24 || d21 >= d4) {
                        break;
                    }
                    double d26 = d19;
                    double nextVertical = curve2.nextVertical(d22, d24);
                    double XforT3 = curve2.XforT(nextVertical);
                    double YforT3 = curve2.YforT(nextVertical);
                    double d27 = d4;
                    double nextVertical2 = curve3.nextVertical(d21, d27);
                    double XforT4 = curve3.XforT(nextVertical2);
                    double d28 = d24;
                    double YforT4 = curve3.YforT(nextVertical2);
                    double d29 = d20;
                    double d30 = nextVertical;
                    double d31 = d26;
                    double d32 = d27;
                    double d33 = YforT3;
                    double d34 = d22;
                    double d35 = d21;
                    double d36 = max;
                    double d37 = nextVertical2;
                    String str2 = str;
                    try {
                        if (findIntersect(curve, dArr, max, 0, 0, d34, d25, d31, d30, XforT3, d33, d35, d23, d29, d37, XforT4, YforT4)) {
                            break;
                        }
                        double d38 = YforT4;
                        double d39 = d33;
                        if (d39 < d38) {
                            if (d39 <= dArr[0]) {
                                d19 = d39;
                                d25 = XforT3;
                                d20 = d29;
                                d22 = d30;
                                d21 = d35;
                            } else if (d39 < dArr[1]) {
                                dArr[1] = d39;
                            }
                        } else if (d38 <= dArr[0]) {
                            d20 = d38;
                            d23 = XforT4;
                            d19 = d31;
                            d22 = d34;
                            d21 = d37;
                        } else if (d38 < dArr[1]) {
                            dArr[1] = d38;
                        }
                        curve2 = this;
                        d24 = d28;
                        d4 = d32;
                        max = d36;
                        str = str2;
                    } catch (Throwable th) {
                        Object obj = th;
                        PrintStream printStream2 = System.err;
                        printStream2.println("Error: " + obj);
                        PrintStream printStream3 = System.err;
                        StringBuilder sb = new StringBuilder();
                        sb.append("y range was ");
                        sb.append(dArr[0]);
                        String str3 = str2;
                        sb.append(str3);
                        sb.append(dArr[1]);
                        printStream3.println(sb.toString());
                        PrintStream printStream4 = System.err;
                        printStream4.println("s y range is " + d31 + str3 + d33);
                        PrintStream printStream5 = System.err;
                        printStream5.println("t y range is " + d29 + str3 + YforT4);
                        PrintStream printStream6 = System.err;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("ymin is ");
                        sb2.append(d36);
                        printStream6.println(sb2.toString());
                        return 0;
                    }
                }
                double d40 = (dArr[0] + dArr[1]) / 2.0d;
                return orderof(XforY(d40), curve3.XforY(d40));
            }
        } else {
            PrintStream printStream7 = System.err;
            printStream7.println("this == " + curve2);
            PrintStream printStream8 = System.err;
            printStream8.println("that == " + curve3);
            PrintStream printStream9 = System.out;
            printStream9.println("target range = " + dArr[0] + str + dArr[1]);
            throw new InternalError("backstepping from " + dArr[0] + " to " + min);
        }
    }

    public boolean findIntersect(Curve curve, double[] dArr, double d, int i, int i2, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, double d13) {
        double d14;
        double d15;
        double d16;
        Curve curve2 = curve;
        double d17 = d2;
        double d18 = d3;
        double d19 = d5;
        double d20 = d6;
        double d21 = d8;
        double d22 = d9;
        double d23 = d11;
        double d24 = d12;
        if (d4 > d13 || d10 > d7) {
            return false;
        } else if (Math.min(d18, d20) > Math.max(d22, d24) || Math.max(d18, d20) < Math.min(d22, d24)) {
            return false;
        } else {
            double d25 = d19 - d17;
            if (d25 > 0.001d) {
                double d26 = (d17 + d19) / 2.0d;
                double XforT = XforT(d26);
                double YforT = YforT(d26);
                if (d26 == d17 || d26 == d19) {
                    PrintStream printStream = System.out;
                    printStream.println("s0 = " + d2);
                    PrintStream printStream2 = System.out;
                    printStream2.println("s1 = " + d5);
                    throw new InternalError("no s progress!");
                } else if (d11 - d21 > 0.001d) {
                    double d27 = (d21 + d11) / 2.0d;
                    Curve curve3 = curve;
                    double d28 = d11;
                    double XforT2 = curve3.XforT(d27);
                    double YforT2 = curve3.YforT(d27);
                    if (d27 == d21 || d27 == d28) {
                        PrintStream printStream3 = System.out;
                        printStream3.println("t0 = " + d8);
                        PrintStream printStream4 = System.out;
                        printStream4.println("t1 = " + d11);
                        throw new InternalError("no t progress!");
                    }
                    if (YforT < d10 || YforT2 < d4) {
                        Curve curve4 = curve3;
                        d16 = d26;
                        d15 = d27;
                    } else {
                        double d29 = d21;
                        Curve curve5 = curve3;
                        d16 = d26;
                        d15 = d27;
                        if (findIntersect(curve, dArr, d, i + 1, i2 + 1, d2, d3, d4, d16, XforT, YforT, d8, d9, d10, d15, XforT2, YforT2)) {
                            return true;
                        }
                    }
                    if (YforT >= YforT2) {
                        if (findIntersect(curve, dArr, d, i + 1, i2 + 1, d2, d3, d4, d16, XforT, YforT, d15, XforT2, YforT2, d11, d12, d13)) {
                            return true;
                        }
                    }
                    if (YforT2 >= YforT) {
                        if (findIntersect(curve, dArr, d, i + 1, i2 + 1, d16, XforT, YforT, d5, d6, d7, d8, d9, d10, d15, XforT2, YforT2)) {
                            return true;
                        }
                    }
                    if (d7 >= YforT2 && d13 >= YforT) {
                        if (findIntersect(curve, dArr, d, i + 1, i2 + 1, d16, XforT, YforT, d5, d6, d7, d15, XforT2, YforT2, d11, d12, d13)) {
                            return true;
                        }
                    }
                } else {
                    Curve curve6 = curve;
                    double d30 = d11;
                    double d31 = d21;
                    double d32 = d26;
                    if (YforT >= d10) {
                        if (findIntersect(curve, dArr, d, i + 1, i2, d2, d3, d4, d32, XforT, YforT, d8, d9, d10, d11, d12, d13)) {
                            return true;
                        }
                    }
                    if (d13 >= YforT) {
                        if (findIntersect(curve, dArr, d, i + 1, i2, d32, XforT, YforT, d5, d6, d7, d8, d9, d10, d11, d12, d13)) {
                            return true;
                        }
                    }
                }
            } else {
                Curve curve7 = curve;
                double d33 = d11;
                double d34 = d19;
                double d35 = d21;
                double d36 = d33 - d35;
                if (d36 > 0.001d) {
                    double d37 = (d35 + d33) / 2.0d;
                    double XforT3 = curve7.XforT(d37);
                    double YforT3 = curve7.YforT(d37);
                    if (d37 == d35 || d37 == d33) {
                        PrintStream printStream5 = System.out;
                        printStream5.println("t0 = " + d8);
                        PrintStream printStream6 = System.out;
                        printStream6.println("t1 = " + d11);
                        throw new InternalError("no t progress!");
                    }
                    if (YforT3 >= d4) {
                        d14 = d37;
                        Curve curve8 = curve7;
                        if (findIntersect(curve, dArr, d, i, i2 + 1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d14, XforT3, YforT3)) {
                            return true;
                        }
                    } else {
                        d14 = d37;
                        Curve curve9 = curve7;
                    }
                    if (d7 >= YforT3) {
                        try {
                            if (findIntersect(curve, dArr, d, i, i2 + 1, d2, d3, d4, d5, d6, d7, d14, XforT3, YforT3, d11, d12, d13)) {
                                return true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } else {
                    double d38 = d35;
                    Curve curve10 = curve7;
                    double d39 = d6 - d3;
                    double d40 = d7 - d4;
                    double d41 = d12 - d9;
                    double d42 = d13 - d10;
                    double d43 = d9 - d3;
                    double d44 = d10 - d4;
                    double d45 = (d41 * d40) - (d42 * d39);
                    if (d45 != 0.0d) {
                        double d46 = 1.0d / d45;
                        double d47 = ((d41 * d44) - (d42 * d43)) * d46;
                        double d48 = ((d39 * d44) - (d40 * d43)) * d46;
                        if (d47 >= 0.0d && d47 <= 1.0d && d48 >= 0.0d && d48 <= 1.0d) {
                            double d49 = d2 + (d47 * d25);
                            double d50 = d38 + (d48 * d36);
                            if (d49 < 0.0d || d49 > 1.0d || d50 < 0.0d || d50 > 1.0d) {
                                System.out.println("Uh oh!");
                            }
                            double YforT4 = (YforT(d49) + curve10.YforT(d50)) / 2.0d;
                            if (YforT4 <= dArr[1] && YforT4 > dArr[0]) {
                                dArr[1] = YforT4;
                                return true;
                            }
                            return false;
                        }
                    }
                }
            }
            return false;
        }
    }

    public double refineTforY(double d, double d2, double d3) {
        double d4 = 1.0d;
        while (true) {
            double d5 = (d + d4) / 2.0d;
            if (d5 != d && d5 != d4) {
                double YforT = YforT(d5);
                if (YforT >= d3) {
                    if (YforT <= d3) {
                        break;
                    }
                    d4 = d5;
                } else {
                    d = d5;
                }
            } else {
                break;
            }
        }
        return d4;
    }

    public boolean fairlyClose(double d, double d2) {
        return Math.abs(d - d2) < Math.max(Math.abs(d), Math.abs(d2)) * 1.0E-10d;
    }
}
