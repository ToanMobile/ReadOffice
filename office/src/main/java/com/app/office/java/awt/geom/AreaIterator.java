package com.app.office.java.awt.geom;

import java.util.Vector;

/* compiled from: Area */
class AreaIterator implements PathIterator {
    private Vector curves;
    private int index;
    private Curve prevcurve;
    private Curve thiscurve;
    private AffineTransform transform;

    public int getWindingRule() {
        return 1;
    }

    public AreaIterator(Vector vector, AffineTransform affineTransform) {
        this.curves = vector;
        this.transform = affineTransform;
        if (vector.size() >= 1) {
            this.thiscurve = (Curve) vector.get(0);
        }
    }

    public boolean isDone() {
        return this.prevcurve == null && this.thiscurve == null;
    }

    public void next() {
        if (this.prevcurve != null) {
            this.prevcurve = null;
            return;
        }
        this.prevcurve = this.thiscurve;
        int i = this.index + 1;
        this.index = i;
        if (i < this.curves.size()) {
            Curve curve = (Curve) this.curves.get(this.index);
            this.thiscurve = curve;
            if (curve.getOrder() != 0 && this.prevcurve.getX1() == this.thiscurve.getX0() && this.prevcurve.getY1() == this.thiscurve.getY0()) {
                this.prevcurve = null;
                return;
            }
            return;
        }
        this.thiscurve = null;
    }

    public int currentSegment(float[] fArr) {
        double[] dArr = new double[6];
        int currentSegment = currentSegment(dArr);
        int i = 3;
        if (currentSegment == 4) {
            i = 0;
        } else if (currentSegment == 2) {
            i = 2;
        } else if (currentSegment != 3) {
            i = 1;
        }
        for (int i2 = 0; i2 < i * 2; i2++) {
            fArr[i2] = (float) dArr[i2];
        }
        return currentSegment;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int currentSegment(double[] r10) {
        /*
            r9 = this;
            com.app.office.java.awt.geom.Curve r0 = r9.prevcurve
            r1 = 1
            if (r0 == 0) goto L_0x0024
            com.app.office.java.awt.geom.Curve r0 = r9.thiscurve
            if (r0 == 0) goto L_0x0022
            int r0 = r0.getOrder()
            if (r0 != 0) goto L_0x0010
            goto L_0x0022
        L_0x0010:
            r0 = 0
            com.app.office.java.awt.geom.Curve r2 = r9.thiscurve
            double r2 = r2.getX0()
            r10[r0] = r2
            com.app.office.java.awt.geom.Curve r0 = r9.thiscurve
            double r2 = r0.getY0()
            r10[r1] = r2
            goto L_0x0035
        L_0x0022:
            r10 = 4
            return r10
        L_0x0024:
            com.app.office.java.awt.geom.Curve r0 = r9.thiscurve
            if (r0 == 0) goto L_0x0044
            int r0 = r0.getSegment(r10)
            com.app.office.java.awt.geom.Curve r2 = r9.thiscurve
            int r2 = r2.getOrder()
            r1 = r0
            if (r2 != 0) goto L_0x0037
        L_0x0035:
            r8 = 1
            goto L_0x0038
        L_0x0037:
            r8 = r2
        L_0x0038:
            com.app.office.java.awt.geom.AffineTransform r3 = r9.transform
            if (r3 == 0) goto L_0x0043
            r5 = 0
            r7 = 0
            r4 = r10
            r6 = r10
            r3.transform((double[]) r4, (int) r5, (double[]) r6, (int) r7, (int) r8)
        L_0x0043:
            return r1
        L_0x0044:
            java.util.NoSuchElementException r10 = new java.util.NoSuchElementException
            java.lang.String r0 = "area iterator out of bounds"
            r10.<init>(r0)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.AreaIterator.currentSegment(double[]):int");
    }
}
