package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Shape;
import com.app.office.java.awt.Stroke;
import java.util.Arrays;

public class BasicStroke implements Stroke {
    public static final int CAP_BUTT = 0;
    public static final int CAP_ROUND = 1;
    public static final int CAP_SQUARE = 2;
    public static final int JOIN_BEVEL = 2;
    public static final int JOIN_MITER = 0;
    public static final int JOIN_ROUND = 1;
    int cap;
    float[] dash;
    float dash_phase;
    int join;
    float miterlimit;
    float width;

    public Shape createStrokedShape(Shape shape) {
        return null;
    }

    public BasicStroke(float f, int i, int i2, float f2, float[] fArr, float f3) {
        if (f >= 0.0f) {
            boolean z = true;
            if (i == 0 || i == 1 || i == 2) {
                if (i2 == 0) {
                    if (f2 < 1.0f) {
                        throw new IllegalArgumentException("miter limit < 1");
                    }
                } else if (!(i2 == 1 || i2 == 2)) {
                    throw new IllegalArgumentException("illegal line join value");
                }
                if (fArr != null) {
                    if (f3 >= 0.0f) {
                        for (float f4 : fArr) {
                            double d = (double) f4;
                            if (d > 0.0d) {
                                z = false;
                            } else if (d < 0.0d) {
                                throw new IllegalArgumentException("negative dash length");
                            }
                        }
                        if (z) {
                            throw new IllegalArgumentException("dash lengths all zero");
                        }
                    } else {
                        throw new IllegalArgumentException("negative dash phase");
                    }
                }
                this.width = f;
                this.cap = i;
                this.join = i2;
                this.miterlimit = f2;
                if (fArr != null) {
                    this.dash = (float[]) fArr.clone();
                }
                this.dash_phase = f3;
                return;
            }
            throw new IllegalArgumentException("illegal end cap value");
        }
        throw new IllegalArgumentException("negative width");
    }

    public BasicStroke(float f, int i, int i2, float f2) {
        this(f, i, i2, f2, (float[]) null, 0.0f);
    }

    public BasicStroke(float f, int i, int i2) {
        this(f, i, i2, 10.0f, (float[]) null, 0.0f);
    }

    public BasicStroke(float f) {
        this(f, 2, 0, 10.0f, (float[]) null, 0.0f);
    }

    public BasicStroke() {
        this(1.0f, 2, 0, 10.0f, (float[]) null, 0.0f);
    }

    public float getLineWidth() {
        return this.width;
    }

    public int getEndCap() {
        return this.cap;
    }

    public int getLineJoin() {
        return this.join;
    }

    public float getMiterLimit() {
        return this.miterlimit;
    }

    public float[] getDashArray() {
        float[] fArr = this.dash;
        if (fArr == null) {
            return null;
        }
        return (float[]) fArr.clone();
    }

    public float getDashPhase() {
        return this.dash_phase;
    }

    public int hashCode() {
        int floatToIntBits = (((((Float.floatToIntBits(this.width) * 31) + this.join) * 31) + this.cap) * 31) + Float.floatToIntBits(this.miterlimit);
        if (this.dash != null) {
            floatToIntBits = (floatToIntBits * 31) + Float.floatToIntBits(this.dash_phase);
            int i = 0;
            while (true) {
                float[] fArr = this.dash;
                if (i >= fArr.length) {
                    break;
                }
                floatToIntBits = (floatToIntBits * 31) + Float.floatToIntBits(fArr[i]);
                i++;
            }
        }
        return floatToIntBits;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BasicStroke)) {
            return false;
        }
        BasicStroke basicStroke = (BasicStroke) obj;
        if (this.width != basicStroke.width || this.join != basicStroke.join || this.cap != basicStroke.cap || this.miterlimit != basicStroke.miterlimit) {
            return false;
        }
        float[] fArr = this.dash;
        if (fArr != null) {
            if (this.dash_phase == basicStroke.dash_phase && Arrays.equals(fArr, basicStroke.dash)) {
                return true;
            }
            return false;
        } else if (basicStroke.dash != null) {
            return false;
        } else {
            return true;
        }
    }
}
