package com.app.office.java.awt.geom;

import java.io.Serializable;

public abstract class Rectangle2D extends RectangularShape {
    public static final int OUT_BOTTOM = 8;
    public static final int OUT_LEFT = 1;
    public static final int OUT_RIGHT = 4;
    public static final int OUT_TOP = 2;

    public abstract Rectangle2D createIntersection(Rectangle2D rectangle2D);

    public abstract Rectangle2D createUnion(Rectangle2D rectangle2D);

    public abstract int outcode(double d, double d2);

    public abstract void setRect(double d, double d2, double d3, double d4);

    public static class Float extends Rectangle2D implements Serializable {
        private static final long serialVersionUID = 3798716824173675777L;
        public float height;
        public float width;
        public float x;
        public float y;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4) {
            setRect(f, f2, f3, f4);
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

        public boolean isEmpty() {
            return this.width <= 0.0f || this.height <= 0.0f;
        }

        public void setRect(float f, float f2, float f3, float f4) {
            this.x = f;
            this.y = f2;
            this.width = f3;
            this.height = f4;
        }

        public void setRect(double d, double d2, double d3, double d4) {
            this.x = (float) d;
            this.y = (float) d2;
            this.width = (float) d3;
            this.height = (float) d4;
        }

        public void setRect(Rectangle2D rectangle2D) {
            this.x = (float) rectangle2D.getX();
            this.y = (float) rectangle2D.getY();
            this.width = (float) rectangle2D.getWidth();
            this.height = (float) rectangle2D.getHeight();
        }

        public int outcode(double d, double d2) {
            int i;
            float f = this.width;
            if (f <= 0.0f) {
                i = 5;
            } else {
                float f2 = this.x;
                i = d < ((double) f2) ? 1 : d > ((double) f2) + ((double) f) ? 4 : 0;
            }
            float f3 = this.height;
            if (f3 <= 0.0f) {
                return i | 10;
            }
            float f4 = this.y;
            if (d2 < ((double) f4)) {
                return i | 2;
            }
            return d2 > ((double) f4) + ((double) f3) ? i | 8 : i;
        }

        public Rectangle2D getBounds2D() {
            return new Float(this.x, this.y, this.width, this.height);
        }

        public Rectangle2D createIntersection(Rectangle2D rectangle2D) {
            Rectangle2D rectangle2D2;
            if (rectangle2D instanceof Float) {
                rectangle2D2 = new Float();
            } else {
                rectangle2D2 = new Double();
            }
            Rectangle2D.intersect(this, rectangle2D, rectangle2D2);
            return rectangle2D2;
        }

        public Rectangle2D createUnion(Rectangle2D rectangle2D) {
            Rectangle2D rectangle2D2;
            if (rectangle2D instanceof Float) {
                rectangle2D2 = new Float();
            } else {
                rectangle2D2 = new Double();
            }
            Rectangle2D.union(this, rectangle2D, rectangle2D2);
            return rectangle2D2;
        }

        public String toString() {
            return getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",w=" + this.width + ",h=" + this.height + "]";
        }
    }

    public static class Double extends Rectangle2D implements Serializable {
        private static final long serialVersionUID = 7771313791441850493L;
        public double height;
        public double width;
        public double x;
        public double y;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4) {
            setRect(d, d2, d3, d4);
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

        public boolean isEmpty() {
            return this.width <= 0.0d || this.height <= 0.0d;
        }

        public void setRect(double d, double d2, double d3, double d4) {
            this.x = d;
            this.y = d2;
            this.width = d3;
            this.height = d4;
        }

        public void setRect(Rectangle2D rectangle2D) {
            this.x = rectangle2D.getX();
            this.y = rectangle2D.getY();
            this.width = rectangle2D.getWidth();
            this.height = rectangle2D.getHeight();
        }

        public int outcode(double d, double d2) {
            int i;
            double d3 = this.width;
            if (d3 <= 0.0d) {
                i = 5;
            } else {
                double d4 = this.x;
                i = d < d4 ? 1 : d > d4 + d3 ? 4 : 0;
            }
            double d5 = this.height;
            if (d5 <= 0.0d) {
                return i | 10;
            }
            double d6 = this.y;
            if (d2 < d6) {
                return i | 2;
            }
            return d2 > d6 + d5 ? i | 8 : i;
        }

        public Rectangle2D getBounds2D() {
            return new Double(this.x, this.y, this.width, this.height);
        }

        public Rectangle2D createIntersection(Rectangle2D rectangle2D) {
            Double doubleR = new Double();
            Rectangle2D.intersect(this, rectangle2D, doubleR);
            return doubleR;
        }

        public Rectangle2D createUnion(Rectangle2D rectangle2D) {
            Double doubleR = new Double();
            Rectangle2D.union(this, rectangle2D, doubleR);
            return doubleR;
        }

        public String toString() {
            return getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",w=" + this.width + ",h=" + this.height + "]";
        }
    }

    protected Rectangle2D() {
    }

    public void setRect(Rectangle2D rectangle2D) {
        setRect(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public boolean intersectsLine(double d, double d2, double d3, double d4) {
        double d5 = d3;
        double d6 = d4;
        int outcode = outcode(d5, d6);
        if (outcode == 0) {
            return true;
        }
        double d7 = d;
        double d8 = d2;
        while (true) {
            int outcode2 = outcode(d7, d8);
            if (outcode2 == 0) {
                return true;
            }
            if ((outcode2 & outcode) != 0) {
                return false;
            }
            if ((outcode2 & 5) != 0) {
                double x = getX();
                if ((outcode2 & 4) != 0) {
                    x += getWidth();
                }
                d8 += ((x - d7) * (d6 - d8)) / (d5 - d7);
                d7 = x;
            } else {
                double y = getY();
                if ((outcode2 & 8) != 0) {
                    y += getHeight();
                }
                d7 += ((y - d8) * (d5 - d7)) / (d6 - d8);
                d8 = y;
            }
        }
    }

    public boolean intersectsLine(Line2D line2D) {
        return intersectsLine(line2D.getX1(), line2D.getY1(), line2D.getX2(), line2D.getY2());
    }

    public int outcode(Point2D point2D) {
        return outcode(point2D.getX(), point2D.getY());
    }

    public void setFrame(double d, double d2, double d3, double d4) {
        setRect(d, d2, d3, d4);
    }

    public Rectangle2D getBounds2D() {
        return (Rectangle2D) clone();
    }

    public boolean contains(double d, double d2) {
        double x = getX();
        double y = getY();
        return d >= x && d2 >= y && d < x + getWidth() && d2 < y + getHeight();
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        if (isEmpty() || d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        double x = getX();
        double y = getY();
        if (d3 + d <= x || d4 + d2 <= y || d >= x + getWidth() || d2 >= y + getHeight()) {
            return false;
        }
        return true;
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        if (isEmpty() || d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        double x = getX();
        double y = getY();
        if (d < x || d2 < y || d + d3 > x + getWidth() || d2 + d4 > y + getHeight()) {
            return false;
        }
        return true;
    }

    public static void intersect(Rectangle2D rectangle2D, Rectangle2D rectangle2D2, Rectangle2D rectangle2D3) {
        double max = Math.max(rectangle2D.getMinX(), rectangle2D2.getMinX());
        double max2 = Math.max(rectangle2D.getMinY(), rectangle2D2.getMinY());
        Rectangle2D rectangle2D4 = rectangle2D3;
        rectangle2D4.setFrame(max, max2, Math.min(rectangle2D.getMaxX(), rectangle2D2.getMaxX()) - max, Math.min(rectangle2D.getMaxY(), rectangle2D2.getMaxY()) - max2);
    }

    public static void union(Rectangle2D rectangle2D, Rectangle2D rectangle2D2, Rectangle2D rectangle2D3) {
        rectangle2D3.setFrameFromDiagonal(Math.min(rectangle2D.getMinX(), rectangle2D2.getMinX()), Math.min(rectangle2D.getMinY(), rectangle2D2.getMinY()), Math.max(rectangle2D.getMaxX(), rectangle2D2.getMaxX()), Math.max(rectangle2D.getMaxY(), rectangle2D2.getMaxY()));
    }

    public void add(double d, double d2) {
        double min = Math.min(getMinX(), d);
        double max = Math.max(getMaxX(), d);
        double min2 = Math.min(getMinY(), d2);
        setRect(min, min2, max - min, Math.max(getMaxY(), d2) - min2);
    }

    public void add(Point2D point2D) {
        add(point2D.getX(), point2D.getY());
    }

    public void add(Rectangle2D rectangle2D) {
        double min = Math.min(getMinX(), rectangle2D.getMinX());
        double max = Math.max(getMaxX(), rectangle2D.getMaxX());
        double min2 = Math.min(getMinY(), rectangle2D.getMinY());
        setRect(min, min2, max - min, Math.max(getMaxY(), rectangle2D.getMaxY()) - min2);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new RectIterator(this, affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new RectIterator(this, affineTransform);
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(getX()) + (Double.doubleToLongBits(getY()) * 37) + (Double.doubleToLongBits(getWidth()) * 43) + (Double.doubleToLongBits(getHeight()) * 47);
        return ((int) doubleToLongBits) ^ ((int) (doubleToLongBits >> 32));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Rectangle2D)) {
            return false;
        }
        Rectangle2D rectangle2D = (Rectangle2D) obj;
        if (getX() == rectangle2D.getX() && getY() == rectangle2D.getY() && getWidth() == rectangle2D.getWidth() && getHeight() == rectangle2D.getHeight()) {
            return true;
        }
        return false;
    }
}
