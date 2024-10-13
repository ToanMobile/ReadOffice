package com.app.office.java.awt.geom;

import java.io.Serializable;

public abstract class Point2D implements Cloneable {
    public static double distanceSq(double d, double d2, double d3, double d4) {
        double d5 = d - d3;
        double d6 = d2 - d4;
        return (d5 * d5) + (d6 * d6);
    }

    public abstract double getX();

    public abstract double getY();

    public abstract void setLocation(double d, double d2);

    public static class Float extends Point2D implements Serializable {
        private static final long serialVersionUID = -2870572449815403710L;
        public float x;
        public float y;

        public Float() {
        }

        public Float(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public double getX() {
            return (double) this.x;
        }

        public double getY() {
            return (double) this.y;
        }

        public void setLocation(double d, double d2) {
            this.x = (float) d;
            this.y = (float) d2;
        }

        public void setLocation(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public String toString() {
            return "Point2D.Float[" + this.x + ", " + this.y + "]";
        }
    }

    public static class Double extends Point2D implements Serializable {
        private static final long serialVersionUID = 6150783262733311327L;
        public double x;
        public double y;

        public Double() {
        }

        public Double(double d, double d2) {
            this.x = d;
            this.y = d2;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }

        public void setLocation(double d, double d2) {
            this.x = d;
            this.y = d2;
        }

        public String toString() {
            return "Point2D.Double[" + this.x + ", " + this.y + "]";
        }
    }

    protected Point2D() {
    }

    public void setLocation(Point2D point2D) {
        setLocation(point2D.getX(), point2D.getY());
    }

    public static double distance(double d, double d2, double d3, double d4) {
        double d5 = d - d3;
        double d6 = d2 - d4;
        return Math.sqrt((d5 * d5) + (d6 * d6));
    }

    public double distanceSq(double d, double d2) {
        double x = d - getX();
        double y = d2 - getY();
        return (x * x) + (y * y);
    }

    public double distanceSq(Point2D point2D) {
        double x = point2D.getX() - getX();
        double y = point2D.getY() - getY();
        return (x * x) + (y * y);
    }

    public double distance(double d, double d2) {
        double x = d - getX();
        double y = d2 - getY();
        return Math.sqrt((x * x) + (y * y));
    }

    public double distance(Point2D point2D) {
        double x = point2D.getX() - getX();
        double y = point2D.getY() - getY();
        return Math.sqrt((x * x) + (y * y));
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(getX()) ^ (Double.doubleToLongBits(getY()) * 31);
        return ((int) doubleToLongBits) ^ ((int) (doubleToLongBits >> 32));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Point2D)) {
            return super.equals(obj);
        }
        Point2D point2D = (Point2D) obj;
        return getX() == point2D.getX() && getY() == point2D.getY();
    }
}
