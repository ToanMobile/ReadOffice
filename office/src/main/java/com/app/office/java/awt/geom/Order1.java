package com.app.office.java.awt.geom;

final class Order1 extends Curve {
    private double x0;
    private double x1;
    private double xmax;
    private double xmin;
    private double y0;
    private double y1;

    public int getOrder() {
        return 1;
    }

    public double nextVertical(double d, double d2) {
        return d2;
    }

    public Order1(double d, double d2, double d3, double d4, int i) {
        super(i);
        this.x0 = d;
        this.y0 = d2;
        this.x1 = d3;
        this.y1 = d4;
        if (d < d3) {
            this.xmin = d;
            this.xmax = d3;
            return;
        }
        this.xmin = d3;
        this.xmax = d;
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

    public double getX1() {
        return this.direction == -1 ? this.x0 : this.x1;
    }

    public double getY1() {
        return this.direction == -1 ? this.y0 : this.y1;
    }

    public double XforY(double d) {
        double d2 = this.x0;
        double d3 = this.x1;
        if (d2 == d3) {
            return d2;
        }
        double d4 = this.y0;
        if (d <= d4) {
            return d2;
        }
        double d5 = this.y1;
        return d >= d5 ? d3 : d2 + (((d - d4) * (d3 - d2)) / (d5 - d4));
    }

    public double TforY(double d) {
        double d2 = this.y0;
        if (d <= d2) {
            return 0.0d;
        }
        double d3 = this.y1;
        if (d >= d3) {
            return 1.0d;
        }
        return (d - d2) / (d3 - d2);
    }

    public double XforT(double d) {
        double d2 = this.x0;
        return d2 + (d * (this.x1 - d2));
    }

    public double YforT(double d) {
        double d2 = this.y0;
        return d2 + (d * (this.y1 - d2));
    }

    public double dXforT(double d, int i) {
        if (i == 0) {
            double d2 = this.x0;
            return d2 + (d * (this.x1 - d2));
        } else if (i != 1) {
            return 0.0d;
        } else {
            return this.x1 - this.x0;
        }
    }

    public double dYforT(double d, int i) {
        if (i == 0) {
            double d2 = this.y0;
            return d2 + (d * (this.y1 - d2));
        } else if (i != 1) {
            return 0.0d;
        } else {
            return this.y1 - this.y0;
        }
    }

    public boolean accumulateCrossings(Crossings crossings) {
        double d;
        double d2;
        double d3;
        double d4;
        double xLo = crossings.getXLo();
        double yLo = crossings.getYLo();
        double xHi = crossings.getXHi();
        double yHi = crossings.getYHi();
        if (this.xmin >= xHi) {
            return false;
        }
        double d5 = this.y0;
        if (d5 < yLo) {
            if (this.y1 <= yLo) {
                return false;
            }
            d2 = XforY(yLo);
            d = yLo;
        } else if (d5 >= yHi) {
            return false;
        } else {
            d = d5;
            d2 = this.x0;
        }
        double d6 = this.y1;
        if (d6 > yHi) {
            d4 = XforY(yHi);
            d3 = yHi;
        } else {
            d3 = d6;
            d4 = this.x1;
        }
        if (d2 >= xHi && d4 >= xHi) {
            return false;
        }
        if (d2 > xLo || d4 > xLo) {
            return true;
        }
        crossings.record(d, d3, this.direction);
        return false;
    }

    public void enlarge(Rectangle2D rectangle2D) {
        rectangle2D.add(this.x0, this.y0);
        rectangle2D.add(this.x1, this.y1);
    }

    public Curve getSubCurve(double d, double d2, int i) {
        double d3 = this.y0;
        if (d == d3 && d2 == this.y1) {
            return getWithDirection(i);
        }
        int i2 = i;
        double d4 = this.x0;
        double d5 = this.x1;
        if (d4 == d5) {
            return new Order1(d4, d, d5, d2, i);
        }
        double d6 = d4 - d5;
        double d7 = d3 - this.y1;
        return new Order1((((d - d3) * d6) / d7) + d4, d, d4 + (((d2 - d3) * d6) / d7), d2, i);
    }

    public Curve getReversedCurve() {
        return new Order1(this.x0, this.y0, this.x1, this.y1, -this.direction);
    }

    public int compareTo(Curve curve, double[] dArr) {
        double d;
        Curve curve2 = curve;
        if (!(curve2 instanceof Order1)) {
            return super.compareTo(curve, dArr);
        }
        Order1 order1 = (Order1) curve2;
        if (dArr[1] > dArr[0]) {
            dArr[1] = Math.min(Math.min(dArr[1], this.y1), order1.y1);
            if (dArr[1] <= dArr[0]) {
                throw new InternalError("backstepping from " + dArr[0] + " to " + dArr[1]);
            } else if (this.xmax <= order1.xmin) {
                if (this.xmin == order1.xmax) {
                    return 0;
                }
                return -1;
            } else if (this.xmin >= order1.xmax) {
                return 1;
            } else {
                double d2 = this.x1;
                double d3 = this.x0;
                double d4 = this.y1;
                double d5 = this.y0;
                double d6 = d4 - d5;
                double d7 = order1.x1;
                double d8 = d3;
                double d9 = order1.x0;
                double d10 = d7 - d9;
                double d11 = d2 - d3;
                double d12 = order1.y1;
                double d13 = d4;
                double d14 = order1.y0;
                double d15 = d12 - d14;
                double d16 = (d10 * d6) - (d11 * d15);
                if (d16 != 0.0d) {
                    double d17 = (((((d8 - d9) * d6) * d15) - ((d5 * d11) * d15)) + ((d10 * d14) * d6)) / d16;
                    if (d17 <= dArr[0]) {
                        d = Math.min(d13, d12);
                    } else {
                        if (d17 < dArr[1]) {
                            dArr[1] = d17;
                        }
                        d = Math.max(d5, d14);
                    }
                } else {
                    d = Math.max(d5, d14);
                }
                return orderof(XforY(d), order1.XforY(d));
            }
        } else {
            throw new InternalError("yrange already screwed up...");
        }
    }

    public int getSegment(double[] dArr) {
        if (this.direction == 1) {
            dArr[0] = this.x1;
            dArr[1] = this.y1;
        } else {
            dArr[0] = this.x0;
            dArr[1] = this.y0;
        }
        return 1;
    }
}
