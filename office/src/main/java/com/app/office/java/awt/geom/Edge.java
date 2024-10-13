package com.app.office.java.awt.geom;

final class Edge {
    static final int GROW_PARTS = 10;
    static final int INIT_PARTS = 4;
    double activey;
    int ctag;
    Curve curve;
    int equivalence;
    int etag;
    private Edge lastEdge;
    private double lastLimit;
    private int lastResult;

    public Edge(Curve curve2, int i) {
        this(curve2, i, 0);
    }

    public Edge(Curve curve2, int i, int i2) {
        this.curve = curve2;
        this.ctag = i;
        this.etag = i2;
    }

    public Curve getCurve() {
        return this.curve;
    }

    public int getCurveTag() {
        return this.ctag;
    }

    public int getEdgeTag() {
        return this.etag;
    }

    public void setEdgeTag(int i) {
        this.etag = i;
    }

    public int getEquivalence() {
        return this.equivalence;
    }

    public void setEquivalence(int i) {
        this.equivalence = i;
    }

    public int compareTo(Edge edge, double[] dArr) {
        if (edge == this.lastEdge) {
            double d = dArr[0];
            double d2 = this.lastLimit;
            if (d < d2) {
                if (dArr[1] > d2) {
                    dArr[1] = d2;
                }
                return this.lastResult;
            }
        }
        if (this == edge.lastEdge) {
            double d3 = dArr[0];
            double d4 = edge.lastLimit;
            if (d3 < d4) {
                if (dArr[1] > d4) {
                    dArr[1] = d4;
                }
                return 0 - edge.lastResult;
            }
        }
        int compareTo = this.curve.compareTo(edge.curve, dArr);
        this.lastEdge = edge;
        this.lastLimit = dArr[1];
        this.lastResult = compareTo;
        return compareTo;
    }

    public void record(double d, int i) {
        this.activey = d;
        this.etag = i;
    }

    public boolean isActiveFor(double d, int i) {
        return this.etag == i && this.activey >= d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Edge[");
        sb.append(this.curve);
        sb.append(", ");
        sb.append(this.ctag == 0 ? "L" : "R");
        sb.append(", ");
        int i = this.etag;
        sb.append(i == 1 ? "I" : i == -1 ? "O" : "N");
        sb.append("]");
        return sb.toString();
    }
}
