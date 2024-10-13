package com.app.office.java.awt;

import com.app.office.java.awt.geom.Dimension2D;
import java.io.Serializable;

public class Dimension extends Dimension2D implements Serializable {
    private static final long serialVersionUID = 4723952579491349524L;
    public int height;
    public int width;

    private static native void initIDs();

    public Dimension() {
        this(0, 0);
    }

    public Dimension(Dimension dimension) {
        this(dimension.width, dimension.height);
    }

    public Dimension(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public double getWidth() {
        return (double) this.width;
    }

    public double getHeight() {
        return (double) this.height;
    }

    public void setSize(double d, double d2) {
        this.width = (int) Math.ceil(d);
        this.height = (int) Math.ceil(d2);
    }

    public Dimension getSize() {
        return new Dimension(this.width, this.height);
    }

    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Dimension)) {
            return false;
        }
        Dimension dimension = (Dimension) obj;
        if (this.width == dimension.width && this.height == dimension.height) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = this.width;
        int i2 = this.height + i;
        return ((i2 * (i2 + 1)) / 2) + i;
    }

    public String toString() {
        return getClass().getName() + "[width=" + this.width + ",height=" + this.height + "]";
    }
}
