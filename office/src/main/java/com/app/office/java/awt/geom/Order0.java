package com.app.office.java.awt.geom;

final class Order0 extends Curve {
    private double x;
    private double y;

    public double TforY(double d) {
        return 0.0d;
    }

    public double XforY(double d) {
        return d;
    }

    public int crossingsFor(double d, double d2) {
        return 0;
    }

    public double dXforT(double d, int i) {
        return 0.0d;
    }

    public double dYforT(double d, int i) {
        return 0.0d;
    }

    public int getOrder() {
        return 0;
    }

    public Curve getReversedCurve() {
        return this;
    }

    public Curve getSubCurve(double d, double d2, int i) {
        return this;
    }

    public double nextVertical(double d, double d2) {
        return d2;
    }

    public Order0(double d, double d2) {
        super(1);
        this.x = d;
        this.y = d2;
    }

    public double getXTop() {
        return this.x;
    }

    public double getYTop() {
        return this.y;
    }

    public double getXBot() {
        return this.x;
    }

    public double getYBot() {
        return this.y;
    }

    public double getXMin() {
        return this.x;
    }

    public double getXMax() {
        return this.x;
    }

    public double getX0() {
        return this.x;
    }

    public double getY0() {
        return this.y;
    }

    public double getX1() {
        return this.x;
    }

    public double getY1() {
        return this.y;
    }

    public double XforT(double d) {
        return this.x;
    }

    public double YforT(double d) {
        return this.y;
    }

    public boolean accumulateCrossings(Crossings crossings) {
        return this.x > crossings.getXLo() && this.x < crossings.getXHi() && this.y > crossings.getYLo() && this.y < crossings.getYHi();
    }

    public void enlarge(Rectangle2D rectangle2D) {
        rectangle2D.add(this.x, this.y);
    }

    public int getSegment(double[] dArr) {
        dArr[0] = this.x;
        dArr[1] = this.y;
        return 0;
    }
}
