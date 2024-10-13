package com.app.office.java.awt.geom;

import java.util.Vector;

final class Order3 extends Curve {
    private double TforY1;
    private double TforY2;
    private double TforY3;
    private double YforT1;
    private double YforT2;
    private double YforT3;
    private double cx0;
    private double cx1;
    private double cy0;
    private double cy1;
    private double x0;
    private double x1;
    private double xcoeff0;
    private double xcoeff1;
    private double xcoeff2;
    private double xcoeff3;
    private double xmax;
    private double xmin;
    private double y0;
    private double y1;
    private double ycoeff0;
    private double ycoeff1;
    private double ycoeff2;
    private double ycoeff3;

    public int getOrder() {
        return 3;
    }

    public static void insert(Vector vector, double[] dArr, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, int i) {
        int i2;
        double[] dArr2 = dArr;
        int i3 = i;
        int horizontalParams = getHorizontalParams(d2, d4, d6, d8, dArr);
        if (horizontalParams == 0) {
            addInstance(vector, d, d2, d3, d4, d5, d6, d7, d8, i);
            return;
        }
        int i4 = 3;
        dArr2[3] = d;
        dArr2[4] = d2;
        dArr2[5] = d3;
        dArr2[6] = d4;
        dArr2[7] = d5;
        dArr2[8] = d6;
        dArr2[9] = d7;
        dArr2[10] = d8;
        double d9 = dArr2[0];
        if (horizontalParams > 1 && d9 > dArr2[1]) {
            dArr2[0] = dArr2[1];
            dArr2[1] = d9;
            d9 = dArr2[0];
        }
        split(dArr2, 3, d9);
        if (horizontalParams > 1) {
            split(dArr2, 9, (dArr2[1] - d9) / (1.0d - d9));
        }
        int i5 = i;
        if (i5 == -1) {
            i4 = 3 + (horizontalParams * 6);
        }
        while (horizontalParams >= 0) {
            int i6 = i2 + 6;
            addInstance(vector, dArr2[i2 + 0], dArr2[i2 + 1], dArr2[i2 + 2], dArr2[i2 + 3], dArr2[i2 + 4], dArr2[i2 + 5], dArr2[i6], dArr2[i2 + 7], i);
            horizontalParams--;
            i2 = i5 == 1 ? i6 : i2 - 6;
        }
    }

    public static void addInstance(Vector vector, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, int i) {
        Vector vector2 = vector;
        if (d2 > d8) {
            Order3 order3 = r1;
            Order3 order32 = new Order3(d7, d8, d5, d6, d3, d4, d, d2, -i);
            vector.add(order3);
            return;
        }
        int i2 = i;
        if (d8 > d2) {
            Order3 order33 = r1;
            Order3 order34 = new Order3(d, d2, d3, d4, d5, d6, d7, d8, i);
            vector.add(order33);
        }
    }

    public static int getHorizontalParams(double d, double d2, double d3, double d4, double[] dArr) {
        if (d <= d2 && d2 <= d3 && d3 <= d4) {
            return 0;
        }
        double d5 = d4 - d3;
        double d6 = d3 - d2;
        double d7 = d2 - d;
        dArr[0] = d7;
        dArr[1] = (d6 - d7) * 2.0d;
        dArr[2] = ((d5 - d6) - d6) + d7;
        int solveQuadratic = QuadCurve2D.solveQuadratic(dArr, dArr);
        int i = 0;
        for (int i2 = 0; i2 < solveQuadratic; i2++) {
            double d8 = dArr[i2];
            if (d8 > 0.0d && d8 < 1.0d) {
                if (i < i2) {
                    dArr[i] = d8;
                }
                i++;
            }
        }
        return i;
    }

    public static void split(double[] dArr, int i, double d) {
        int i2 = i + 6;
        double d2 = dArr[i2];
        dArr[i + 12] = d2;
        int i3 = i + 7;
        double d3 = dArr[i3];
        dArr[i + 13] = d3;
        int i4 = i + 4;
        double d4 = dArr[i4];
        int i5 = i + 5;
        double d5 = dArr[i5];
        double d6 = ((d2 - d4) * d) + d4;
        double d7 = ((d3 - d5) * d) + d5;
        double d8 = dArr[i + 0];
        double d9 = dArr[i + 1];
        int i6 = i + 2;
        double d10 = dArr[i6];
        int i7 = i + 3;
        double d11 = dArr[i7];
        double d12 = d8 + ((d10 - d8) * d);
        double d13 = d9 + ((d11 - d9) * d);
        double d14 = d10 + ((d4 - d10) * d);
        double d15 = d11 + ((d5 - d11) * d);
        double d16 = d14 + ((d6 - d14) * d);
        double d17 = d15 + ((d7 - d15) * d);
        double d18 = d12 + ((d14 - d12) * d);
        double d19 = d13 + ((d15 - d13) * d);
        dArr[i6] = d12;
        dArr[i7] = d13;
        dArr[i4] = d18;
        dArr[i5] = d19;
        dArr[i2] = d18 + ((d16 - d18) * d);
        dArr[i3] = d19 + ((d17 - d19) * d);
        dArr[i + 8] = d16;
        dArr[i + 9] = d17;
        dArr[i + 10] = d6;
        dArr[i + 11] = d7;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Order3(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, int i) {
        super(i);
        double d9 = d;
        double d10 = d2;
        double d11 = d3;
        double d12 = d5;
        double d13 = d7;
        double d14 = d8;
        double d15 = d4 < d10 ? d10 : d4;
        d14 = d6 <= d14 ? d6 : d14;
        this.x0 = d9;
        this.y0 = d10;
        this.cx0 = d11;
        this.cy0 = d15;
        this.cx1 = d12;
        this.cy1 = d14;
        this.x1 = d13;
        this.y1 = d8;
        this.xmin = Math.min(Math.min(d9, d13), Math.min(d11, d12));
        this.xmax = Math.max(Math.max(d9, d13), Math.max(d11, d12));
        this.xcoeff0 = d9;
        this.xcoeff1 = (d11 - d9) * 3.0d;
        double d16 = d12 - d11;
        this.xcoeff2 = ((d16 - d11) + d9) * 3.0d;
        this.xcoeff3 = (d13 - (d16 * 3.0d)) - d9;
        this.ycoeff0 = d10;
        double d17 = d15;
        this.ycoeff1 = (d17 - d10) * 3.0d;
        double d18 = d14 - d17;
        this.ycoeff2 = ((d18 - d17) + d10) * 3.0d;
        this.ycoeff3 = (d8 - (d18 * 3.0d)) - d10;
        this.YforT3 = d10;
        this.YforT2 = d10;
        this.YforT1 = d10;
    }

    public double getXTop() {
        return this.x0;
    }

    public double getYTop() {
        return this.y0;
    }

    public double getXBot() {
        return this.x1;
    }

    public double getYBot() {
        return this.y1;
    }

    public double getXMin() {
        return this.xmin;
    }

    public double getXMax() {
        return this.xmax;
    }

    public double getX0() {
        return this.direction == 1 ? this.x0 : this.x1;
    }

    public double getY0() {
        return this.direction == 1 ? this.y0 : this.y1;
    }

    public double getCX0() {
        return this.direction == 1 ? this.cx0 : this.cx1;
    }

    public double getCY0() {
        return this.direction == 1 ? this.cy0 : this.cy1;
    }

    public double getCX1() {
        return this.direction == -1 ? this.cx0 : this.cx1;
    }

    public double getCY1() {
        return this.direction == -1 ? this.cy0 : this.cy1;
    }

    public double getX1() {
        return this.direction == -1 ? this.x0 : this.x1;
    }

    public double getY1() {
        return this.direction == -1 ? this.y0 : this.y1;
    }

    public double TforY(double d) {
        double d2;
        double d3;
        double d4 = d;
        if (d4 <= this.y0) {
            return 0.0d;
        }
        double d5 = 1.0d;
        if (d4 >= this.y1) {
            return 1.0d;
        }
        if (d4 == this.YforT1) {
            return this.TforY1;
        }
        if (d4 == this.YforT2) {
            return this.TforY2;
        }
        if (d4 == this.YforT3) {
            return this.TforY3;
        }
        double d6 = this.ycoeff3;
        if (d6 == 0.0d) {
            return Order2.TforY(d, this.ycoeff0, this.ycoeff1, this.ycoeff2);
        }
        double d7 = this.ycoeff2 / d6;
        double d8 = this.ycoeff1 / d6;
        double d9 = (this.ycoeff0 - d4) / d6;
        double d10 = ((d7 * d7) - (d8 * 3.0d)) / 9.0d;
        double d11 = (((((d7 * 2.0d) * d7) * d7) - ((9.0d * d7) * d8)) + (27.0d * d9)) / 54.0d;
        double d12 = d11 * d11;
        double d13 = d10 * d10 * d10;
        double d14 = d7 / 3.0d;
        if (d12 < d13) {
            double acos = Math.acos(d11 / Math.sqrt(d13));
            double sqrt = Math.sqrt(d10) * -2.0d;
            d2 = refine(d7, d8, d9, d, (Math.cos(acos / 3.0d) * sqrt) - d14);
            if (d2 < 0.0d) {
                d2 = refine(d7, d8, d9, d, (Math.cos((acos + 6.283185307179586d) / 3.0d) * sqrt) - d14);
            }
            if (d2 < 0.0d) {
                d2 = refine(d7, d8, d9, d, (sqrt * Math.cos((acos - 6.283185307179586d) / 3.0d)) - d14);
            }
        } else {
            boolean z = d11 < 0.0d;
            double sqrt2 = Math.sqrt(d12 - d13);
            if (z) {
                d11 = -d11;
            }
            double pow = Math.pow(d11 + sqrt2, 0.3333333333333333d);
            if (!z) {
                pow = -pow;
            }
            d2 = refine(d7, d8, d9, d, (pow + (pow == 0.0d ? 0.0d : d10 / pow)) - d14);
        }
        if (d2 < 0.0d) {
            double d15 = 0.0d;
            while (true) {
                d3 = (d15 + d5) / 2.0d;
                if (d3 != d15 && d3 != d5) {
                    double YforT = YforT(d3);
                    if (YforT >= d4) {
                        if (YforT <= d4) {
                            break;
                        }
                        d5 = d3;
                    } else {
                        d15 = d3;
                    }
                } else {
                    break;
                }
            }
            d2 = d3;
        }
        if (d2 >= 0.0d) {
            this.TforY3 = this.TforY2;
            this.YforT3 = this.YforT2;
            this.TforY2 = this.TforY1;
            this.YforT2 = this.YforT1;
            this.TforY1 = d2;
            this.YforT1 = d4;
        }
        return d2;
    }

    public double refine(double d, double d2, double d3, double d4, double d5) {
        double d6;
        double d7;
        double d8 = d5;
        if (d8 < -0.1d || d8 > 1.1d) {
            return -1.0d;
        }
        double YforT = YforT(d8);
        if (YforT < d4) {
            d7 = d8;
            d6 = 1.0d;
        } else {
            d6 = d8;
            d7 = 0.0d;
        }
        boolean z = true;
        while (YforT != d4) {
            if (!z) {
                double d9 = (d7 + d6) / 2.0d;
                if (d9 == d7 || d9 == d6) {
                    break;
                }
                d8 = d9;
            } else {
                double dYforT = dYforT(d8, 1);
                if (dYforT != 0.0d) {
                    double d10 = d8 + ((d4 - YforT) / dYforT);
                    if (d10 != d8 && d10 > d7 && d10 < d6) {
                        d8 = d10;
                    }
                }
                z = false;
            }
            YforT = YforT(d8);
            if (YforT >= d4) {
                if (YforT <= d4) {
                    break;
                }
                d6 = d8;
            } else {
                d7 = d8;
            }
        }
        if (d8 > 1.0d) {
            return -1.0d;
        }
        return d8;
    }

    public double XforY(double d) {
        if (d <= this.y0) {
            return this.x0;
        }
        if (d >= this.y1) {
            return this.x1;
        }
        return XforT(TforY(d));
    }

    public double XforT(double d) {
        return (((((this.xcoeff3 * d) + this.xcoeff2) * d) + this.xcoeff1) * d) + this.xcoeff0;
    }

    public double YforT(double d) {
        return (((((this.ycoeff3 * d) + this.ycoeff2) * d) + this.ycoeff1) * d) + this.ycoeff0;
    }

    public double dXforT(double d, int i) {
        double d2;
        double d3;
        if (i == 0) {
            return (((((this.xcoeff3 * d) + this.xcoeff2) * d) + this.xcoeff1) * d) + this.xcoeff0;
        }
        if (i == 1) {
            d2 = ((this.xcoeff3 * 3.0d * d) + (this.xcoeff2 * 2.0d)) * d;
            d3 = this.xcoeff1;
        } else if (i == 2) {
            d2 = this.xcoeff3 * 6.0d * d;
            d3 = this.xcoeff2 * 2.0d;
        } else if (i != 3) {
            return 0.0d;
        } else {
            return this.xcoeff3 * 6.0d;
        }
        return d2 + d3;
    }

    public double dYforT(double d, int i) {
        double d2;
        double d3;
        if (i == 0) {
            return (((((this.ycoeff3 * d) + this.ycoeff2) * d) + this.ycoeff1) * d) + this.ycoeff0;
        }
        if (i == 1) {
            d2 = ((this.ycoeff3 * 3.0d * d) + (this.ycoeff2 * 2.0d)) * d;
            d3 = this.ycoeff1;
        } else if (i == 2) {
            d2 = this.ycoeff3 * 6.0d * d;
            d3 = this.ycoeff2 * 2.0d;
        } else if (i != 3) {
            return 0.0d;
        } else {
            return this.ycoeff3 * 6.0d;
        }
        return d2 + d3;
    }

    public double nextVertical(double d, double d2) {
        double[] dArr = {this.xcoeff1, this.xcoeff2 * 2.0d, this.xcoeff3 * 3.0d};
        int solveQuadratic = QuadCurve2D.solveQuadratic(dArr, dArr);
        for (int i = 0; i < solveQuadratic; i++) {
            if (dArr[i] > d && dArr[i] < d2) {
                d2 = dArr[i];
            }
        }
        return d2;
    }

    public void enlarge(Rectangle2D rectangle2D) {
        rectangle2D.add(this.x0, this.y0);
        double[] dArr = {this.xcoeff1, this.xcoeff2 * 2.0d, this.xcoeff3 * 3.0d};
        int solveQuadratic = QuadCurve2D.solveQuadratic(dArr, dArr);
        for (int i = 0; i < solveQuadratic; i++) {
            double d = dArr[i];
            if (d > 0.0d && d < 1.0d) {
                rectangle2D.add(XforT(d), YforT(d));
            }
        }
        rectangle2D.add(this.x1, this.y1);
    }

    public Curve getSubCurve(double d, double d2, int i) {
        int i2;
        double d3 = d2;
        if (d <= this.y0 && d3 >= this.y1) {
            return getWithDirection(i);
        }
        int i3 = i;
        double[] dArr = new double[14];
        double TforY = TforY(d);
        double TforY4 = TforY(d3);
        dArr[0] = this.x0;
        dArr[1] = this.y0;
        dArr[2] = this.cx0;
        dArr[3] = this.cy0;
        dArr[4] = this.cx1;
        dArr[5] = this.cy1;
        dArr[6] = this.x1;
        dArr[7] = this.y1;
        if (TforY <= TforY4) {
            double d4 = TforY;
            TforY = TforY4;
            TforY4 = d4;
        }
        if (TforY < 1.0d) {
            split(dArr, 0, TforY);
        }
        if (TforY4 <= 0.0d) {
            i2 = 0;
        } else {
            split(dArr, 0, TforY4 / TforY);
            i2 = 6;
        }
        return new Order3(dArr[i2 + 0], d, dArr[i2 + 2], dArr[i2 + 3], dArr[i2 + 4], dArr[i2 + 5], dArr[i2 + 6], d2, i);
    }

    public Curve getReversedCurve() {
        return new Order3(this.x0, this.y0, this.cx0, this.cy0, this.cx1, this.cy1, this.x1, this.y1, -this.direction);
    }

    public int getSegment(double[] dArr) {
        if (this.direction == 1) {
            dArr[0] = this.cx0;
            dArr[1] = this.cy0;
            dArr[2] = this.cx1;
            dArr[3] = this.cy1;
            dArr[4] = this.x1;
            dArr[5] = this.y1;
        } else {
            dArr[0] = this.cx1;
            dArr[1] = this.cy1;
            dArr[2] = this.cx0;
            dArr[3] = this.cy0;
            dArr[4] = this.x0;
            dArr[5] = this.y0;
        }
        return 3;
    }

    public String controlPointString() {
        return "(" + round(getCX0()) + ", " + round(getCY0()) + "), " + "(" + round(getCX1()) + ", " + round(getCY1()) + "), ";
    }
}
