package com.app.office.java.awt.geom;

import com.app.office.java.awt.geom.Point2D;
import com.app.office.java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Arc2D extends RectangularShape {
    public static final int CHORD = 1;
    public static final int OPEN = 0;
    public static final int PIE = 2;
    private int type;

    public abstract double getAngleExtent();

    public abstract double getAngleStart();

    /* access modifiers changed from: protected */
    public abstract Rectangle2D makeBounds(double d, double d2, double d3, double d4);

    public abstract void setAngleExtent(double d);

    public abstract void setAngleStart(double d);

    public abstract void setArc(double d, double d2, double d3, double d4, double d5, double d6, int i);

    public static class Float extends Arc2D implements Serializable {
        private static final long serialVersionUID = 9130893014586380278L;
        public float extent;
        public float height;
        public float start;
        public float width;
        public float x;
        public float y;

        public Float() {
            super(0);
        }

        public Float(int i) {
            super(i);
        }

        public Float(float f, float f2, float f3, float f4, float f5, float f6, int i) {
            super(i);
            this.x = f;
            this.y = f2;
            this.width = f3;
            this.height = f4;
            this.start = f5;
            this.extent = f6;
        }

        public Float(Rectangle2D rectangle2D, float f, float f2, int i) {
            super(i);
            this.x = (float) rectangle2D.getX();
            this.y = (float) rectangle2D.getY();
            this.width = (float) rectangle2D.getWidth();
            this.height = (float) rectangle2D.getHeight();
            this.start = f;
            this.extent = f2;
        }

        public double getX() {
            return (double) this.x;
        }

        public double getY() {
            return (double) this.y;
        }

        public double getWidth() {
            return (double) this.width;
        }

        public double getHeight() {
            return (double) this.height;
        }

        public double getAngleStart() {
            return (double) this.start;
        }

        public double getAngleExtent() {
            return (double) this.extent;
        }

        public boolean isEmpty() {
            return ((double) this.width) <= 0.0d || ((double) this.height) <= 0.0d;
        }

        public void setArc(double d, double d2, double d3, double d4, double d5, double d6, int i) {
            setArcType(i);
            this.x = (float) d;
            this.y = (float) d2;
            this.width = (float) d3;
            this.height = (float) d4;
            this.start = (float) d5;
            this.extent = (float) d6;
        }

        public void setAngleStart(double d) {
            this.start = (float) d;
        }

        public void setAngleExtent(double d) {
            this.extent = (float) d;
        }

        /* access modifiers changed from: protected */
        public Rectangle2D makeBounds(double d, double d2, double d3, double d4) {
            return new Rectangle2D.Float((float) d, (float) d2, (float) d3, (float) d4);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeByte(getArcType());
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            try {
                setArcType(objectInputStream.readByte());
            } catch (IllegalArgumentException e) {
                throw new InvalidObjectException(e.getMessage());
            }
        }
    }

    public static class Double extends Arc2D implements Serializable {
        private static final long serialVersionUID = 728264085846882001L;
        public double extent;
        public double height;
        public double start;
        public double width;
        public double x;
        public double y;

        public Double() {
            super(0);
        }

        public Double(int i) {
            super(i);
        }

        public Double(double d, double d2, double d3, double d4, double d5, double d6, int i) {
            super(i);
            this.x = d;
            this.y = d2;
            this.width = d3;
            this.height = d4;
            this.start = d5;
            this.extent = d6;
        }

        public Double(Rectangle2D rectangle2D, double d, double d2, int i) {
            super(i);
            this.x = rectangle2D.getX();
            this.y = rectangle2D.getY();
            this.width = rectangle2D.getWidth();
            this.height = rectangle2D.getHeight();
            this.start = d;
            this.extent = d2;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }

        public double getWidth() {
            return this.width;
        }

        public double getHeight() {
            return this.height;
        }

        public double getAngleStart() {
            return this.start;
        }

        public double getAngleExtent() {
            return this.extent;
        }

        public boolean isEmpty() {
            return this.width <= 0.0d || this.height <= 0.0d;
        }

        public void setArc(double d, double d2, double d3, double d4, double d5, double d6, int i) {
            setArcType(i);
            this.x = d;
            this.y = d2;
            this.width = d3;
            this.height = d4;
            this.start = d5;
            this.extent = d6;
        }

        public void setAngleStart(double d) {
            this.start = d;
        }

        public void setAngleExtent(double d) {
            this.extent = d;
        }

        /* access modifiers changed from: protected */
        public Rectangle2D makeBounds(double d, double d2, double d3, double d4) {
            return new Rectangle2D.Double(d, d2, d3, d4);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeByte(getArcType());
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            try {
                setArcType(objectInputStream.readByte());
            } catch (IllegalArgumentException e) {
                throw new InvalidObjectException(e.getMessage());
            }
        }
    }

    Arc2D() {
        this(0);
    }

    protected Arc2D(int i) {
        setArcType(i);
    }

    public int getArcType() {
        return this.type;
    }

    public Point2D getStartPoint() {
        double radians = Math.toRadians(-getAngleStart());
        return new Point2D.Double(getX() + (((Math.cos(radians) * 0.5d) + 0.5d) * getWidth()), getY() + (((Math.sin(radians) * 0.5d) + 0.5d) * getHeight()));
    }

    public Point2D getEndPoint() {
        double radians = Math.toRadians((-getAngleStart()) - getAngleExtent());
        return new Point2D.Double(getX() + (((Math.cos(radians) * 0.5d) + 0.5d) * getWidth()), getY() + (((Math.sin(radians) * 0.5d) + 0.5d) * getHeight()));
    }

    public void setArc(Point2D point2D, Dimension2D dimension2D, double d, double d2, int i) {
        setArc(point2D.getX(), point2D.getY(), dimension2D.getWidth(), dimension2D.getHeight(), d, d2, i);
    }

    public void setArc(Rectangle2D rectangle2D, double d, double d2, int i) {
        setArc(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight(), d, d2, i);
    }

    public void setArc(Arc2D arc2D) {
        setArc(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(), arc2D.getAngleStart(), arc2D.getAngleExtent(), arc2D.type);
    }

    public void setArcByCenter(double d, double d2, double d3, double d4, double d5, int i) {
        double d6 = d3 * 2.0d;
        setArc(d - d3, d2 - d3, d6, d6, d4, d5, i);
    }

    public void setArcByTangent(Point2D point2D, Point2D point2D2, Point2D point2D3, double d) {
        double d2;
        double d3;
        double atan2 = Math.atan2(point2D.getY() - point2D2.getY(), point2D.getX() - point2D2.getX());
        double atan22 = Math.atan2(point2D3.getY() - point2D2.getY(), point2D3.getX() - point2D2.getX());
        double d4 = atan22 - atan2;
        if (d4 > 3.141592653589793d) {
            atan22 -= 6.283185307179586d;
        } else if (d4 < -3.141592653589793d) {
            atan22 += 6.283185307179586d;
        }
        double d5 = (atan2 + atan22) / 2.0d;
        double sin = d / Math.sin(Math.abs(atan22 - d5));
        double x = point2D2.getX() + (Math.cos(d5) * sin);
        double y = point2D2.getY() + (sin * Math.sin(d5));
        if (atan2 < atan22) {
            d3 = atan2 - 1.5707963267948966d;
            d2 = atan22 + 1.5707963267948966d;
        } else {
            d3 = atan2 + 1.5707963267948966d;
            d2 = atan22 - 1.5707963267948966d;
        }
        double degrees = Math.toDegrees(-d3);
        double degrees2 = Math.toDegrees(-d2) - degrees;
        setArcByCenter(x, y, d, degrees, degrees2 < 0.0d ? degrees2 + 360.0d : degrees2 - 360.0d, this.type);
    }

    public void setAngleStart(Point2D point2D) {
        setAngleStart(-Math.toDegrees(Math.atan2(getWidth() * (point2D.getY() - getCenterY()), getHeight() * (point2D.getX() - getCenterX()))));
    }

    public void setAngles(double d, double d2, double d3, double d4) {
        double centerX = getCenterX();
        double centerY = getCenterY();
        double width = getWidth();
        double height = getHeight();
        double atan2 = Math.atan2((centerY - d2) * width, (d - centerX) * height);
        double atan22 = Math.atan2(width * (centerY - d4), height * (d3 - centerX)) - atan2;
        if (atan22 <= 0.0d) {
            atan22 += 6.283185307179586d;
        }
        setAngleStart(Math.toDegrees(atan2));
        setAngleExtent(Math.toDegrees(atan22));
    }

    public void setAngles(Point2D point2D, Point2D point2D2) {
        setAngles(point2D.getX(), point2D.getY(), point2D2.getX(), point2D2.getY());
    }

    public void setArcType(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("invalid type for Arc: " + i);
        }
        this.type = i;
    }

    public void setFrame(double d, double d2, double d3, double d4) {
        setArc(d, d2, d3, d4, getAngleStart(), getAngleExtent(), this.type);
    }

    public Rectangle2D getBounds2D() {
        double d;
        double d2;
        if (isEmpty()) {
            return makeBounds(getX(), getY(), getWidth(), getHeight());
        }
        if (getArcType() == 2) {
            d2 = 0.0d;
            d = 0.0d;
        } else {
            d2 = 1.0d;
            d = -1.0d;
        }
        double d3 = d;
        double d4 = d3;
        double d5 = 0.0d;
        double d6 = d2;
        for (int i = 0; i < 6; i++) {
            if (i < 4) {
                d5 += 90.0d;
                if (!containsAngle(d5)) {
                }
            } else {
                if (i == 4) {
                    d5 = getAngleStart();
                } else {
                    d5 += getAngleExtent();
                }
            }
            double radians = Math.toRadians(-d5);
            double d7 = d5;
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            d2 = Math.min(d2, cos);
            d6 = Math.min(d6, sin);
            double max = Math.max(d3, cos);
            d4 = Math.max(d4, sin);
            d3 = max;
            d5 = d7;
        }
        double width = getWidth();
        double height = getHeight();
        return makeBounds((((d2 * 0.5d) + 0.5d) * width) + getX(), (((d6 * 0.5d) + 0.5d) * height) + getY(), (d3 - d2) * 0.5d * width, (d4 - d6) * 0.5d * height);
    }

    static double normalizeDegrees(double d) {
        if (d > 180.0d) {
            if (d <= 540.0d) {
                return d - 360.0d;
            }
            double IEEEremainder = Math.IEEEremainder(d, 360.0d);
            if (IEEEremainder != -180.0d) {
                return IEEEremainder;
            }
        } else if (d > -180.0d) {
            return d;
        } else {
            if (d > -540.0d) {
                return d + 360.0d;
            }
            double IEEEremainder2 = Math.IEEEremainder(d, 360.0d);
            if (IEEEremainder2 != -180.0d) {
                return IEEEremainder2;
            }
        }
        return 180.0d;
    }

    public boolean containsAngle(double d) {
        double angleExtent = getAngleExtent();
        boolean z = angleExtent < 0.0d;
        if (z) {
            angleExtent = -angleExtent;
        }
        if (angleExtent >= 360.0d) {
            return true;
        }
        double normalizeDegrees = normalizeDegrees(d) - normalizeDegrees(getAngleStart());
        if (z) {
            normalizeDegrees = -normalizeDegrees;
        }
        if (normalizeDegrees < 0.0d) {
            normalizeDegrees += 360.0d;
        }
        if (normalizeDegrees < 0.0d || normalizeDegrees >= angleExtent) {
            return false;
        }
        return true;
    }

    public boolean contains(double d, double d2) {
        double width = getWidth();
        if (width <= 0.0d) {
            return false;
        }
        double x = ((d - getX()) / width) - 0.5d;
        double height = getHeight();
        if (height <= 0.0d) {
            return false;
        }
        double y = ((d2 - getY()) / height) - 0.5d;
        if ((x * x) + (y * y) >= 0.25d) {
            return false;
        }
        double abs = Math.abs(getAngleExtent());
        if (abs >= 360.0d) {
            return true;
        }
        boolean containsAngle = containsAngle(-Math.toDegrees(Math.atan2(y, x)));
        if (this.type == 2) {
            return containsAngle;
        }
        if (containsAngle) {
            if (abs >= 180.0d) {
                return true;
            }
        } else if (abs <= 180.0d) {
            return false;
        }
        double radians = Math.toRadians(-getAngleStart());
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double radians2 = radians + Math.toRadians(-getAngleExtent());
        double d3 = cos;
        double d4 = sin;
        double cos2 = Math.cos(radians2);
        double sin2 = Math.sin(radians2);
        boolean z = Line2D.relativeCCW(d3, d4, cos2, sin2, x * 2.0d, y * 2.0d) * Line2D.relativeCCW(d3, d4, cos2, sin2, 0.0d, 0.0d) >= 0;
        if (!containsAngle) {
            return z;
        }
        if (!z) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0138, code lost:
        if (r1.intersectsLine(r32, r34, r36, r38) == false) goto L_0x013b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean intersects(double r41, double r43, double r45, double r47) {
        /*
            r40 = this;
            r0 = r40
            r10 = r41
            r12 = r43
            double r1 = r40.getWidth()
            double r3 = r40.getHeight()
            r14 = 0
            r5 = 0
            int r7 = (r45 > r5 ? 1 : (r45 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x015c
            int r7 = (r47 > r5 ? 1 : (r47 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x015c
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x015c
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 > 0) goto L_0x0023
            goto L_0x015c
        L_0x0023:
            double r15 = r40.getAngleExtent()
            int r7 = (r15 > r5 ? 1 : (r15 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x002c
            return r14
        L_0x002c:
            double r7 = r40.getX()
            double r17 = r40.getY()
            double r1 = r1 + r7
            double r3 = r17 + r3
            r19 = r15
            double r14 = r10 + r45
            double r5 = r12 + r47
            int r9 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r9 >= 0) goto L_0x015a
            int r9 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r9 >= 0) goto L_0x015a
            int r9 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
            if (r9 <= 0) goto L_0x015a
            int r9 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r9 > 0) goto L_0x004f
            goto L_0x015a
        L_0x004f:
            double r32 = r40.getCenterX()
            double r34 = r40.getCenterY()
            com.app.office.java.awt.geom.Point2D r9 = r40.getStartPoint()
            com.app.office.java.awt.geom.Point2D r16 = r40.getEndPoint()
            double r28 = r9.getX()
            double r30 = r9.getY()
            double r36 = r16.getX()
            double r38 = r16.getY()
            r23 = r3
            r3 = 4640537203540230144(0x4066800000000000, double:180.0)
            r16 = 1
            int r9 = (r34 > r12 ? 1 : (r34 == r12 ? 0 : -1))
            if (r9 < 0) goto L_0x00af
            int r25 = (r34 > r5 ? 1 : (r34 == r5 ? 0 : -1))
            if (r25 > 0) goto L_0x00af
            int r25 = (r28 > r14 ? 1 : (r28 == r14 ? 0 : -1))
            if (r25 >= 0) goto L_0x0098
            int r25 = (r36 > r14 ? 1 : (r36 == r14 ? 0 : -1))
            if (r25 >= 0) goto L_0x0098
            int r25 = (r32 > r14 ? 1 : (r32 == r14 ? 0 : -1))
            if (r25 >= 0) goto L_0x0098
            int r25 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r25 <= 0) goto L_0x0098
            r1 = 0
            boolean r1 = r0.containsAngle(r1)
            if (r1 != 0) goto L_0x00ae
        L_0x0098:
            int r1 = (r28 > r10 ? 1 : (r28 == r10 ? 0 : -1))
            if (r1 <= 0) goto L_0x00af
            int r1 = (r36 > r10 ? 1 : (r36 == r10 ? 0 : -1))
            if (r1 <= 0) goto L_0x00af
            int r1 = (r32 > r10 ? 1 : (r32 == r10 ? 0 : -1))
            if (r1 <= 0) goto L_0x00af
            int r1 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
            if (r1 >= 0) goto L_0x00af
            boolean r1 = r0.containsAngle(r3)
            if (r1 == 0) goto L_0x00af
        L_0x00ae:
            return r16
        L_0x00af:
            int r1 = (r32 > r10 ? 1 : (r32 == r10 ? 0 : -1))
            if (r1 < 0) goto L_0x00ec
            int r1 = (r32 > r14 ? 1 : (r32 == r14 ? 0 : -1))
            if (r1 > 0) goto L_0x00ec
            int r1 = (r30 > r12 ? 1 : (r30 == r12 ? 0 : -1))
            if (r1 <= 0) goto L_0x00d0
            int r1 = (r38 > r12 ? 1 : (r38 == r12 ? 0 : -1))
            if (r1 <= 0) goto L_0x00d0
            if (r9 <= 0) goto L_0x00d0
            int r1 = (r17 > r5 ? 1 : (r17 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x00d0
            r1 = 4636033603912859648(0x4056800000000000, double:90.0)
            boolean r1 = r0.containsAngle(r1)
            if (r1 != 0) goto L_0x00eb
        L_0x00d0:
            int r1 = (r30 > r5 ? 1 : (r30 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x00ec
            int r1 = (r38 > r5 ? 1 : (r38 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x00ec
            int r1 = (r34 > r5 ? 1 : (r34 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x00ec
            int r1 = (r23 > r12 ? 1 : (r23 == r12 ? 0 : -1))
            if (r1 <= 0) goto L_0x00ec
            r1 = 4643457506423603200(0x4070e00000000000, double:270.0)
            boolean r1 = r0.containsAngle(r1)
            if (r1 == 0) goto L_0x00ec
        L_0x00eb:
            return r16
        L_0x00ec:
            com.app.office.java.awt.geom.Rectangle2D$Double r17 = new com.app.office.java.awt.geom.Rectangle2D$Double
            r1 = r17
            r21 = r3
            r2 = r41
            r8 = r5
            r4 = r43
            r6 = r45
            r10 = r8
            r8 = r47
            r1.<init>(r2, r4, r6, r8)
            int r1 = r0.type
            r2 = 2
            if (r1 == r2) goto L_0x011e
            double r1 = java.lang.Math.abs(r19)
            int r3 = (r1 > r21 ? 1 : (r1 == r21 ? 0 : -1))
            if (r3 <= 0) goto L_0x010d
            goto L_0x011e
        L_0x010d:
            r23 = r17
            r24 = r28
            r26 = r30
            r28 = r36
            r30 = r38
            boolean r1 = r23.intersectsLine(r24, r26, r28, r30)
            if (r1 == 0) goto L_0x013b
            return r16
        L_0x011e:
            r23 = r17
            r24 = r32
            r26 = r34
            boolean r1 = r23.intersectsLine(r24, r26, r28, r30)
            if (r1 != 0) goto L_0x0159
            r23 = r17
            r24 = r32
            r26 = r34
            r28 = r36
            r30 = r38
            boolean r1 = r23.intersectsLine(r24, r26, r28, r30)
            if (r1 == 0) goto L_0x013b
            goto L_0x0159
        L_0x013b:
            boolean r1 = r40.contains(r41, r43)
            if (r1 != 0) goto L_0x0159
            boolean r1 = r0.contains(r14, r12)
            if (r1 != 0) goto L_0x0159
            r1 = r41
            r3 = r10
            boolean r1 = r0.contains(r1, r3)
            if (r1 != 0) goto L_0x0159
            boolean r1 = r0.contains(r14, r3)
            if (r1 == 0) goto L_0x0157
            goto L_0x0159
        L_0x0157:
            r1 = 0
            return r1
        L_0x0159:
            return r16
        L_0x015a:
            r1 = 0
            return r1
        L_0x015c:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.Arc2D.intersects(double, double, double, double):boolean");
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        return contains(d, d2, d3, d4, (Rectangle2D) null);
    }

    public boolean contains(Rectangle2D rectangle2D) {
        return contains(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight(), rectangle2D);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v1, resolved type: com.app.office.java.awt.geom.Rectangle2D$Double} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: com.app.office.java.awt.geom.Rectangle2D$Double} */
    /* JADX WARNING: type inference failed for: r12v0 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean contains(double r20, double r22, double r24, double r26, com.app.office.java.awt.geom.Rectangle2D r28) {
        /*
            r19 = this;
            r0 = r19
            r2 = r20
            r4 = r22
            boolean r1 = r19.contains(r20, r22)
            r10 = 0
            if (r1 == 0) goto L_0x00b9
            double r6 = r2 + r24
            boolean r1 = r0.contains(r6, r4)
            if (r1 == 0) goto L_0x00b9
            double r8 = r4 + r26
            boolean r1 = r0.contains(r2, r8)
            if (r1 == 0) goto L_0x00b9
            boolean r1 = r0.contains(r6, r8)
            if (r1 != 0) goto L_0x0025
            goto L_0x00b9
        L_0x0025:
            int r1 = r0.type
            r6 = 2
            r11 = 1
            if (r1 != r6) goto L_0x00b8
            double r6 = r19.getAngleExtent()
            double r6 = java.lang.Math.abs(r6)
            r8 = 4640537203540230144(0x4066800000000000, double:180.0)
            int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r1 > 0) goto L_0x003e
            goto L_0x00b8
        L_0x003e:
            if (r28 != 0) goto L_0x004f
            com.app.office.java.awt.geom.Rectangle2D$Double r12 = new com.app.office.java.awt.geom.Rectangle2D$Double
            r1 = r12
            r2 = r20
            r4 = r22
            r6 = r24
            r8 = r26
            r1.<init>(r2, r4, r6, r8)
            goto L_0x0051
        L_0x004f:
            r12 = r28
        L_0x0051:
            double r1 = r19.getWidth()
            r3 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r1 = r1 / r3
            double r5 = r19.getHeight()
            double r5 = r5 / r3
            double r3 = r19.getX()
            double r3 = r3 + r1
            double r7 = r19.getY()
            double r7 = r7 + r5
            double r13 = r19.getAngleStart()
            double r13 = -r13
            double r13 = java.lang.Math.toRadians(r13)
            double r15 = java.lang.Math.cos(r13)
            double r15 = r15 * r1
            double r15 = r15 + r3
            double r17 = java.lang.Math.sin(r13)
            double r17 = r17 * r5
            double r17 = r7 + r17
            r20 = r12
            r21 = r3
            r23 = r7
            r25 = r15
            r27 = r17
            boolean r9 = r20.intersectsLine(r21, r23, r25, r27)
            if (r9 == 0) goto L_0x0090
            return r10
        L_0x0090:
            double r9 = r19.getAngleExtent()
            double r9 = -r9
            double r9 = java.lang.Math.toRadians(r9)
            double r13 = r13 + r9
            double r9 = java.lang.Math.cos(r13)
            double r1 = r1 * r9
            double r1 = r1 + r3
            double r9 = java.lang.Math.sin(r13)
            double r5 = r5 * r9
            double r5 = r5 + r7
            r20 = r12
            r21 = r3
            r23 = r7
            r25 = r1
            r27 = r5
            boolean r1 = r20.intersectsLine(r21, r23, r25, r27)
            r1 = r1 ^ r11
            return r1
        L_0x00b8:
            return r11
        L_0x00b9:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.Arc2D.contains(double, double, double, double, com.app.office.java.awt.geom.Rectangle2D):boolean");
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new ArcIterator(this, affineTransform);
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(getX()) + (Double.doubleToLongBits(getY()) * 37) + (Double.doubleToLongBits(getWidth()) * 43) + (Double.doubleToLongBits(getHeight()) * 47) + (Double.doubleToLongBits(getAngleStart()) * 53) + (Double.doubleToLongBits(getAngleExtent()) * 59) + ((long) (getArcType() * 61));
        return ((int) doubleToLongBits) ^ ((int) (doubleToLongBits >> 32));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Arc2D)) {
            return false;
        }
        Arc2D arc2D = (Arc2D) obj;
        if (getX() == arc2D.getX() && getY() == arc2D.getY() && getWidth() == arc2D.getWidth() && getHeight() == arc2D.getHeight() && getAngleStart() == arc2D.getAngleStart() && getAngleExtent() == arc2D.getAngleExtent() && getArcType() == arc2D.getArcType()) {
            return true;
        }
        return false;
    }
}
