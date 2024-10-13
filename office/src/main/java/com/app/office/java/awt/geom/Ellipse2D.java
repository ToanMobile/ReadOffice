package com.app.office.java.awt.geom;

import com.app.office.java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class Ellipse2D extends RectangularShape {

    public static class Float extends Ellipse2D implements Serializable {
        private static final long serialVersionUID = -6633761252372475977L;
        public float height;
        public float width;
        public float x;
        public float y;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4) {
            setFrame(f, f2, f3, f4);
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
            return ((double) this.width) <= 0.0d || ((double) this.height) <= 0.0d;
        }

        public void setFrame(float f, float f2, float f3, float f4) {
            this.x = f;
            this.y = f2;
            this.width = f3;
            this.height = f4;
        }

        public void setFrame(double d, double d2, double d3, double d4) {
            this.x = (float) d;
            this.y = (float) d2;
            this.width = (float) d3;
            this.height = (float) d4;
        }

        public Rectangle2D getBounds2D() {
            return new Rectangle2D.Float(this.x, this.y, this.width, this.height);
        }
    }

    public static class Double extends Ellipse2D implements Serializable {
        private static final long serialVersionUID = 5555464816372320683L;
        public double height;
        public double width;
        public double x;
        public double y;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4) {
            setFrame(d, d2, d3, d4);
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

        public void setFrame(double d, double d2, double d3, double d4) {
            this.x = d;
            this.y = d2;
            this.width = d3;
            this.height = d4;
        }

        public Rectangle2D getBounds2D() {
            return new Rectangle2D.Double(this.x, this.y, this.width, this.height);
        }
    }

    protected Ellipse2D() {
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
        if ((x * x) + (y * y) < 0.25d) {
            return true;
        }
        return false;
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        double d5 = 0.0d;
        if (d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        double width = getWidth();
        if (width <= 0.0d) {
            return false;
        }
        double x = ((d - getX()) / width) - 0.5d;
        double d6 = (d3 / width) + x;
        double height = getHeight();
        if (height <= 0.0d) {
            return false;
        }
        double y = ((d2 - getY()) / height) - 0.5d;
        double d7 = (d4 / height) + y;
        if (x <= 0.0d) {
            x = d6 < 0.0d ? d6 : 0.0d;
        }
        if (y > 0.0d) {
            d5 = y;
        } else if (d7 < 0.0d) {
            d5 = d7;
        }
        if ((x * x) + (d5 * d5) < 0.25d) {
            return true;
        }
        return false;
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        if (contains(d, d2)) {
            double d5 = d3 + d;
            if (contains(d5, d2)) {
                double d6 = d2 + d4;
                return contains(d, d6) && contains(d5, d6);
            }
        }
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new EllipseIterator(this, affineTransform);
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(getX()) + (Double.doubleToLongBits(getY()) * 37) + (Double.doubleToLongBits(getWidth()) * 43) + (Double.doubleToLongBits(getHeight()) * 47);
        return ((int) doubleToLongBits) ^ ((int) (doubleToLongBits >> 32));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ellipse2D)) {
            return false;
        }
        Ellipse2D ellipse2D = (Ellipse2D) obj;
        if (getX() == ellipse2D.getX() && getY() == ellipse2D.getY() && getWidth() == ellipse2D.getWidth() && getHeight() == ellipse2D.getHeight()) {
            return true;
        }
        return false;
    }
}
