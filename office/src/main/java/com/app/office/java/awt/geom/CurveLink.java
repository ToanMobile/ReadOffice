package com.app.office.java.awt.geom;

final class CurveLink {
    Curve curve;
    int etag;
    CurveLink next;
    double ybot;
    double ytop;

    public CurveLink(Curve curve2, double d, double d2, int i) {
        this.curve = curve2;
        this.ytop = d;
        this.ybot = d2;
        this.etag = i;
        if (d < curve2.getYTop() || this.ybot > curve2.getYBot()) {
            throw new InternalError("bad curvelink [" + this.ytop + "=>" + this.ybot + "] for " + curve2);
        }
    }

    public boolean absorb(CurveLink curveLink) {
        return absorb(curveLink.curve, curveLink.ytop, curveLink.ybot, curveLink.etag);
    }

    public boolean absorb(Curve curve2, double d, double d2, int i) {
        if (this.curve != curve2 || this.etag != i || this.ybot < d || this.ytop > d2) {
            return false;
        }
        if (d < curve2.getYTop() || d2 > curve2.getYBot()) {
            throw new InternalError("bad curvelink [" + d + "=>" + d2 + "] for " + curve2);
        }
        this.ytop = Math.min(this.ytop, d);
        this.ybot = Math.max(this.ybot, d2);
        return true;
    }

    public boolean isEmpty() {
        return this.ytop == this.ybot;
    }

    public Curve getCurve() {
        return this.curve;
    }

    public Curve getSubCurve() {
        if (this.ytop == this.curve.getYTop() && this.ybot == this.curve.getYBot()) {
            return this.curve.getWithDirection(this.etag);
        }
        return this.curve.getSubCurve(this.ytop, this.ybot, this.etag);
    }

    public Curve getMoveto() {
        return new Order0(getXTop(), getYTop());
    }

    public double getXTop() {
        return this.curve.XforY(this.ytop);
    }

    public double getYTop() {
        return this.ytop;
    }

    public double getXBot() {
        return this.curve.XforY(this.ybot);
    }

    public double getYBot() {
        return this.ybot;
    }

    public double getX() {
        return this.curve.XforY(this.ytop);
    }

    public int getEdgeTag() {
        return this.etag;
    }

    public void setNext(CurveLink curveLink) {
        this.next = curveLink;
    }

    public CurveLink getNext() {
        return this.next;
    }
}
