package com.app.office.java.awt.geom;

import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Point2D;
import com.app.office.java.awt.geom.Rectangle2D;
import com.app.office.java.util.Arrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Path2D implements Shape, Cloneable {
    static final int EXPAND_MAX = 500;
    static final int INIT_SIZE = 20;
    private static final byte SEG_CLOSE = 4;
    private static final byte SEG_CUBICTO = 3;
    private static final byte SEG_LINETO = 1;
    private static final byte SEG_MOVETO = 0;
    private static final byte SEG_QUADTO = 2;
    private static final byte SERIAL_PATH_END = 97;
    private static final byte SERIAL_SEG_CLOSE = 96;
    private static final byte SERIAL_SEG_DBL_CUBICTO = 83;
    private static final byte SERIAL_SEG_DBL_LINETO = 81;
    private static final byte SERIAL_SEG_DBL_MOVETO = 80;
    private static final byte SERIAL_SEG_DBL_QUADTO = 82;
    private static final byte SERIAL_SEG_FLT_CUBICTO = 67;
    private static final byte SERIAL_SEG_FLT_LINETO = 65;
    private static final byte SERIAL_SEG_FLT_MOVETO = 64;
    private static final byte SERIAL_SEG_FLT_QUADTO = 66;
    private static final byte SERIAL_STORAGE_DBL_ARRAY = 49;
    private static final byte SERIAL_STORAGE_FLT_ARRAY = 48;
    public static final int WIND_EVEN_ODD = 0;
    public static final int WIND_NON_ZERO = 1;
    transient int numCoords;
    transient int numTypes;
    transient byte[] pointTypes;
    transient int windingRule;

    /* access modifiers changed from: package-private */
    public abstract void append(double d, double d2);

    /* access modifiers changed from: package-private */
    public abstract void append(float f, float f2);

    public abstract void append(PathIterator pathIterator, boolean z);

    public abstract Object clone();

    /* access modifiers changed from: package-private */
    public abstract double[] cloneCoordsDouble(AffineTransform affineTransform);

    /* access modifiers changed from: package-private */
    public abstract float[] cloneCoordsFloat(AffineTransform affineTransform);

    public abstract void curveTo(double d, double d2, double d3, double d4, double d5, double d6);

    /* access modifiers changed from: package-private */
    public abstract Point2D getPoint(int i);

    public abstract void lineTo(double d, double d2);

    public abstract void moveTo(double d, double d2);

    /* access modifiers changed from: package-private */
    public abstract void needRoom(boolean z, int i);

    /* access modifiers changed from: package-private */
    public abstract int pointCrossings(double d, double d2);

    public abstract void quadTo(double d, double d2, double d3, double d4);

    /* access modifiers changed from: package-private */
    public abstract int rectCrossings(double d, double d2, double d3, double d4);

    public abstract void transform(AffineTransform affineTransform);

    Path2D() {
    }

    Path2D(int i, int i2) {
        setWindingRule(i);
        this.pointTypes = new byte[i2];
    }

    public static class Float extends Path2D implements Serializable {
        private static final long serialVersionUID = 6990832515060788886L;
        transient float[] floatCoords;

        public Float() {
            this(1, 20);
        }

        public Float(int i) {
            this(i, 20);
        }

        public Float(int i, int i2) {
            super(i, i2);
            this.floatCoords = new float[(i2 * 2)];
        }

        public Float(Shape shape) {
            this(shape, (AffineTransform) null);
        }

        public Float(Shape shape, AffineTransform affineTransform) {
            if (shape instanceof Path2D) {
                Path2D path2D = (Path2D) shape;
                setWindingRule(path2D.windingRule);
                this.numTypes = path2D.numTypes;
                this.pointTypes = Arrays.copyOf(path2D.pointTypes, path2D.pointTypes.length);
                this.numCoords = path2D.numCoords;
                this.floatCoords = path2D.cloneCoordsFloat(affineTransform);
                return;
            }
            PathIterator pathIterator = shape.getPathIterator(affineTransform);
            setWindingRule(pathIterator.getWindingRule());
            this.pointTypes = new byte[20];
            this.floatCoords = new float[40];
            append(pathIterator, false);
        }

        /* access modifiers changed from: package-private */
        public float[] cloneCoordsFloat(AffineTransform affineTransform) {
            if (affineTransform == null) {
                float[] fArr = this.floatCoords;
                return Arrays.copyOf(fArr, fArr.length);
            }
            float[] fArr2 = this.floatCoords;
            float[] fArr3 = new float[fArr2.length];
            affineTransform.transform(fArr2, 0, fArr3, 0, this.numCoords / 2);
            return fArr3;
        }

        /* access modifiers changed from: package-private */
        public double[] cloneCoordsDouble(AffineTransform affineTransform) {
            float[] fArr = this.floatCoords;
            double[] dArr = new double[fArr.length];
            if (affineTransform == null) {
                for (int i = 0; i < this.numCoords; i++) {
                    dArr[i] = (double) this.floatCoords[i];
                }
            } else {
                affineTransform.transform(fArr, 0, dArr, 0, this.numCoords / 2);
            }
            return dArr;
        }

        /* access modifiers changed from: package-private */
        public void append(float f, float f2) {
            float[] fArr = this.floatCoords;
            int i = this.numCoords;
            this.numCoords = i + 1;
            fArr[i] = f;
            float[] fArr2 = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr2[i2] = f2;
        }

        /* access modifiers changed from: package-private */
        public void append(double d, double d2) {
            float[] fArr = this.floatCoords;
            int i = this.numCoords;
            this.numCoords = i + 1;
            fArr[i] = (float) d;
            float[] fArr2 = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr2[i2] = (float) d2;
        }

        /* access modifiers changed from: package-private */
        public Point2D getPoint(int i) {
            float[] fArr = this.floatCoords;
            return new Point2D.Float(fArr[i], fArr[i + 1]);
        }

        /* access modifiers changed from: package-private */
        public void needRoom(boolean z, int i) {
            if (z) {
                int i2 = this.numTypes;
            }
            int length = this.pointTypes.length;
            if (this.numTypes >= length) {
                int i3 = 500;
                if (length <= 500) {
                    i3 = length;
                }
                this.pointTypes = Arrays.copyOf(this.pointTypes, length + i3);
            }
            int length2 = this.floatCoords.length;
            if (this.numCoords + i > length2) {
                int i4 = 1000;
                if (length2 <= 1000) {
                    i4 = length2;
                }
                if (i4 >= i) {
                    i = i4;
                }
                this.floatCoords = Arrays.copyOf(this.floatCoords, length2 + i);
            }
        }

        public final synchronized void moveTo(double d, double d2) {
            if (this.numTypes <= 0 || this.pointTypes[this.numTypes - 1] != 0) {
                needRoom(false, 2);
                byte[] bArr = this.pointTypes;
                int i = this.numTypes;
                this.numTypes = i + 1;
                bArr[i] = 0;
                float[] fArr = this.floatCoords;
                int i2 = this.numCoords;
                this.numCoords = i2 + 1;
                fArr[i2] = (float) d;
                float[] fArr2 = this.floatCoords;
                int i3 = this.numCoords;
                this.numCoords = i3 + 1;
                fArr2[i3] = (float) d2;
            } else {
                this.floatCoords[this.numCoords - 2] = (float) d;
                this.floatCoords[this.numCoords - 1] = (float) d2;
            }
        }

        public final synchronized void moveTo(float f, float f2) {
            if (this.numTypes <= 0 || this.pointTypes[this.numTypes - 1] != 0) {
                needRoom(false, 2);
                byte[] bArr = this.pointTypes;
                int i = this.numTypes;
                this.numTypes = i + 1;
                bArr[i] = 0;
                float[] fArr = this.floatCoords;
                int i2 = this.numCoords;
                this.numCoords = i2 + 1;
                fArr[i2] = f;
                float[] fArr2 = this.floatCoords;
                int i3 = this.numCoords;
                this.numCoords = i3 + 1;
                fArr2[i3] = f2;
            } else {
                this.floatCoords[this.numCoords - 2] = f;
                this.floatCoords[this.numCoords - 1] = f2;
            }
        }

        public final synchronized void lineTo(double d, double d2) {
            needRoom(true, 2);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 1;
            float[] fArr = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr[i2] = (float) d;
            float[] fArr2 = this.floatCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            fArr2[i3] = (float) d2;
        }

        public final synchronized void lineTo(float f, float f2) {
            needRoom(true, 2);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 1;
            float[] fArr = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr[i2] = f;
            float[] fArr2 = this.floatCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            fArr2[i3] = f2;
        }

        public final synchronized void quadTo(double d, double d2, double d3, double d4) {
            needRoom(true, 4);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 2;
            float[] fArr = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr[i2] = (float) d;
            float[] fArr2 = this.floatCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            fArr2[i3] = (float) d2;
            float[] fArr3 = this.floatCoords;
            int i4 = this.numCoords;
            this.numCoords = i4 + 1;
            fArr3[i4] = (float) d3;
            float[] fArr4 = this.floatCoords;
            int i5 = this.numCoords;
            this.numCoords = i5 + 1;
            fArr4[i5] = (float) d4;
        }

        public final synchronized void quadTo(float f, float f2, float f3, float f4) {
            needRoom(true, 4);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 2;
            float[] fArr = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr[i2] = f;
            float[] fArr2 = this.floatCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            fArr2[i3] = f2;
            float[] fArr3 = this.floatCoords;
            int i4 = this.numCoords;
            this.numCoords = i4 + 1;
            fArr3[i4] = f3;
            float[] fArr4 = this.floatCoords;
            int i5 = this.numCoords;
            this.numCoords = i5 + 1;
            fArr4[i5] = f4;
        }

        public final synchronized void curveTo(double d, double d2, double d3, double d4, double d5, double d6) {
            needRoom(true, 6);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 3;
            float[] fArr = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr[i2] = (float) d;
            float[] fArr2 = this.floatCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            fArr2[i3] = (float) d2;
            float[] fArr3 = this.floatCoords;
            int i4 = this.numCoords;
            this.numCoords = i4 + 1;
            fArr3[i4] = (float) d3;
            float[] fArr4 = this.floatCoords;
            int i5 = this.numCoords;
            this.numCoords = i5 + 1;
            fArr4[i5] = (float) d4;
            float[] fArr5 = this.floatCoords;
            int i6 = this.numCoords;
            this.numCoords = i6 + 1;
            fArr5[i6] = (float) d5;
            float[] fArr6 = this.floatCoords;
            int i7 = this.numCoords;
            this.numCoords = i7 + 1;
            fArr6[i7] = (float) d6;
        }

        public final synchronized void curveTo(float f, float f2, float f3, float f4, float f5, float f6) {
            needRoom(true, 6);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 3;
            float[] fArr = this.floatCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            fArr[i2] = f;
            float[] fArr2 = this.floatCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            fArr2[i3] = f2;
            float[] fArr3 = this.floatCoords;
            int i4 = this.numCoords;
            this.numCoords = i4 + 1;
            fArr3[i4] = f3;
            float[] fArr4 = this.floatCoords;
            int i5 = this.numCoords;
            this.numCoords = i5 + 1;
            fArr4[i5] = f4;
            float[] fArr5 = this.floatCoords;
            int i6 = this.numCoords;
            this.numCoords = i6 + 1;
            fArr5[i6] = f5;
            float[] fArr6 = this.floatCoords;
            int i7 = this.numCoords;
            this.numCoords = i7 + 1;
            fArr6[i7] = f6;
        }

        /* access modifiers changed from: package-private */
        public int pointCrossings(double d, double d2) {
            float[] fArr = this.floatCoords;
            float f = fArr[0];
            float f2 = fArr[1];
            return 0;
        }

        /* access modifiers changed from: package-private */
        public int rectCrossings(double d, double d2, double d3, double d4) {
            float[] fArr = this.floatCoords;
            float f = fArr[0];
            float f2 = fArr[1];
            return 0;
        }

        public final void append(PathIterator pathIterator, boolean z) {
            float[] fArr = new float[6];
            while (!pathIterator.isDone()) {
                int currentSegment = pathIterator.currentSegment(fArr);
                if (currentSegment != 0) {
                    if (currentSegment != 1) {
                        if (currentSegment == 2) {
                            quadTo(fArr[0], fArr[1], fArr[2], fArr[3]);
                        } else if (currentSegment == 3) {
                            curveTo(fArr[0], fArr[1], fArr[2], fArr[3], fArr[4], fArr[5]);
                        } else if (currentSegment == 4) {
                            closePath();
                        }
                        pathIterator.next();
                        z = false;
                    }
                } else if (!z || this.numTypes < 1 || this.numCoords < 1) {
                    moveTo(fArr[0], fArr[1]);
                    pathIterator.next();
                    z = false;
                } else if (this.pointTypes[this.numTypes - 1] != 4 && this.floatCoords[this.numCoords - 2] == fArr[0] && this.floatCoords[this.numCoords - 1] == fArr[1]) {
                    pathIterator.next();
                    z = false;
                }
                lineTo(fArr[0], fArr[1]);
                pathIterator.next();
                z = false;
            }
        }

        public final void transform(AffineTransform affineTransform) {
            float[] fArr = this.floatCoords;
            affineTransform.transform(fArr, 0, fArr, 0, this.numCoords / 2);
        }

        public final synchronized Rectangle2D getBounds2D() {
            float f;
            float f2;
            float f3;
            float f4;
            int i = this.numCoords;
            if (i > 0) {
                float[] fArr = this.floatCoords;
                int i2 = i - 1;
                float f5 = fArr[i2];
                int i3 = i2 - 1;
                f4 = fArr[i3];
                f2 = f5;
                f = f2;
                f3 = f4;
                while (i3 > 0) {
                    float[] fArr2 = this.floatCoords;
                    int i4 = i3 - 1;
                    float f6 = fArr2[i4];
                    i3 = i4 - 1;
                    float f7 = fArr2[i3];
                    if (f7 < f4) {
                        f4 = f7;
                    }
                    if (f6 < f2) {
                        f2 = f6;
                    }
                    if (f7 > f3) {
                        f3 = f7;
                    }
                    if (f6 > f) {
                        f = f6;
                    }
                }
            } else {
                f4 = 0.0f;
                f3 = 0.0f;
                f2 = 0.0f;
                f = 0.0f;
            }
            return new Rectangle2D.Float(f4, f2, f3 - f4, f - f2);
        }

        public PathIterator getPathIterator(AffineTransform affineTransform) {
            if (affineTransform == null) {
                return new CopyIterator(this);
            }
            return new TxIterator(this, affineTransform);
        }

        public final Object clone() {
            return new Float((Shape) this);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Path2D.super.writeObject(objectOutputStream, false);
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            Path2D.super.readObject(objectInputStream, false);
        }

        static class CopyIterator extends Iterator {
            float[] floatCoords;

            CopyIterator(Float floatR) {
                super(floatR);
                this.floatCoords = floatR.floatCoords;
            }

            public int currentSegment(float[] fArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    System.arraycopy(this.floatCoords, this.pointIdx, fArr, 0, i);
                }
                return b;
            }

            public int currentSegment(double[] dArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    for (int i2 = 0; i2 < i; i2++) {
                        dArr[i2] = (double) this.floatCoords[this.pointIdx + i2];
                    }
                }
                return b;
            }
        }

        static class TxIterator extends Iterator {
            AffineTransform affine;
            float[] floatCoords;

            TxIterator(Float floatR, AffineTransform affineTransform) {
                super(floatR);
                this.floatCoords = floatR.floatCoords;
                this.affine = affineTransform;
            }

            public int currentSegment(float[] fArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    this.affine.transform(this.floatCoords, this.pointIdx, fArr, 0, i / 2);
                }
                return b;
            }

            public int currentSegment(double[] dArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    this.affine.transform(this.floatCoords, this.pointIdx, dArr, 0, i / 2);
                }
                return b;
            }
        }
    }

    public static class Double extends Path2D implements Serializable {
        private static final long serialVersionUID = 1826762518450014216L;
        transient double[] doubleCoords;

        public Double() {
            this(1, 20);
        }

        public Double(int i) {
            this(i, 20);
        }

        public Double(int i, int i2) {
            super(i, i2);
            this.doubleCoords = new double[(i2 * 2)];
        }

        public Double(Shape shape) {
            this(shape, (AffineTransform) null);
        }

        public Double(Shape shape, AffineTransform affineTransform) {
            if (shape instanceof Path2D) {
                Path2D path2D = (Path2D) shape;
                setWindingRule(path2D.windingRule);
                this.numTypes = path2D.numTypes;
                this.pointTypes = Arrays.copyOf(path2D.pointTypes, path2D.pointTypes.length);
                this.numCoords = path2D.numCoords;
                this.doubleCoords = path2D.cloneCoordsDouble(affineTransform);
                return;
            }
            PathIterator pathIterator = shape.getPathIterator(affineTransform);
            setWindingRule(pathIterator.getWindingRule());
            this.pointTypes = new byte[20];
            this.doubleCoords = new double[40];
            append(pathIterator, false);
        }

        /* access modifiers changed from: package-private */
        public float[] cloneCoordsFloat(AffineTransform affineTransform) {
            double[] dArr = this.doubleCoords;
            float[] fArr = new float[dArr.length];
            if (affineTransform == null) {
                for (int i = 0; i < this.numCoords; i++) {
                    fArr[i] = (float) this.doubleCoords[i];
                }
            } else {
                affineTransform.transform(dArr, 0, fArr, 0, this.numCoords / 2);
            }
            return fArr;
        }

        /* access modifiers changed from: package-private */
        public double[] cloneCoordsDouble(AffineTransform affineTransform) {
            if (affineTransform == null) {
                double[] dArr = this.doubleCoords;
                return Arrays.copyOf(dArr, dArr.length);
            }
            double[] dArr2 = this.doubleCoords;
            double[] dArr3 = new double[dArr2.length];
            affineTransform.transform(dArr2, 0, dArr3, 0, this.numCoords / 2);
            return dArr3;
        }

        /* access modifiers changed from: package-private */
        public void append(float f, float f2) {
            double[] dArr = this.doubleCoords;
            int i = this.numCoords;
            this.numCoords = i + 1;
            dArr[i] = (double) f;
            double[] dArr2 = this.doubleCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            dArr2[i2] = (double) f2;
        }

        /* access modifiers changed from: package-private */
        public void append(double d, double d2) {
            double[] dArr = this.doubleCoords;
            int i = this.numCoords;
            this.numCoords = i + 1;
            dArr[i] = d;
            double[] dArr2 = this.doubleCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            dArr2[i2] = d2;
        }

        /* access modifiers changed from: package-private */
        public Point2D getPoint(int i) {
            double[] dArr = this.doubleCoords;
            return new Point2D.Double(dArr[i], dArr[i + 1]);
        }

        /* access modifiers changed from: package-private */
        public void needRoom(boolean z, int i) {
            if (z) {
                int i2 = this.numTypes;
            }
            int length = this.pointTypes.length;
            if (this.numTypes >= length) {
                int i3 = 500;
                if (length <= 500) {
                    i3 = length;
                }
                this.pointTypes = Arrays.copyOf(this.pointTypes, length + i3);
            }
            int length2 = this.doubleCoords.length;
            if (this.numCoords + i > length2) {
                int i4 = 1000;
                if (length2 <= 1000) {
                    i4 = length2;
                }
                if (i4 >= i) {
                    i = i4;
                }
                this.doubleCoords = Arrays.copyOf(this.doubleCoords, length2 + i);
            }
        }

        public final synchronized void moveTo(double d, double d2) {
            if (this.numTypes <= 0 || this.pointTypes[this.numTypes - 1] != 0) {
                needRoom(false, 2);
                byte[] bArr = this.pointTypes;
                int i = this.numTypes;
                this.numTypes = i + 1;
                bArr[i] = 0;
                double[] dArr = this.doubleCoords;
                int i2 = this.numCoords;
                this.numCoords = i2 + 1;
                dArr[i2] = d;
                double[] dArr2 = this.doubleCoords;
                int i3 = this.numCoords;
                this.numCoords = i3 + 1;
                dArr2[i3] = d2;
            } else {
                this.doubleCoords[this.numCoords - 2] = d;
                this.doubleCoords[this.numCoords - 1] = d2;
            }
        }

        public final synchronized void lineTo(double d, double d2) {
            needRoom(true, 2);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 1;
            double[] dArr = this.doubleCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            dArr[i2] = d;
            double[] dArr2 = this.doubleCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            dArr2[i3] = d2;
        }

        public final synchronized void quadTo(double d, double d2, double d3, double d4) {
            needRoom(true, 4);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 2;
            double[] dArr = this.doubleCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            dArr[i2] = d;
            double[] dArr2 = this.doubleCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            dArr2[i3] = d2;
            double[] dArr3 = this.doubleCoords;
            int i4 = this.numCoords;
            this.numCoords = i4 + 1;
            dArr3[i4] = d3;
            double[] dArr4 = this.doubleCoords;
            int i5 = this.numCoords;
            this.numCoords = i5 + 1;
            dArr4[i5] = d4;
        }

        public final synchronized void curveTo(double d, double d2, double d3, double d4, double d5, double d6) {
            needRoom(true, 6);
            byte[] bArr = this.pointTypes;
            int i = this.numTypes;
            this.numTypes = i + 1;
            bArr[i] = 3;
            double[] dArr = this.doubleCoords;
            int i2 = this.numCoords;
            this.numCoords = i2 + 1;
            dArr[i2] = d;
            double[] dArr2 = this.doubleCoords;
            int i3 = this.numCoords;
            this.numCoords = i3 + 1;
            dArr2[i3] = d2;
            double[] dArr3 = this.doubleCoords;
            int i4 = this.numCoords;
            this.numCoords = i4 + 1;
            dArr3[i4] = d3;
            double[] dArr4 = this.doubleCoords;
            int i5 = this.numCoords;
            this.numCoords = i5 + 1;
            dArr4[i5] = d4;
            double[] dArr5 = this.doubleCoords;
            int i6 = this.numCoords;
            this.numCoords = i6 + 1;
            dArr5[i6] = d5;
            double[] dArr6 = this.doubleCoords;
            int i7 = this.numCoords;
            this.numCoords = i7 + 1;
            dArr6[i7] = d6;
        }

        /* access modifiers changed from: package-private */
        public int pointCrossings(double d, double d2) {
            double[] dArr = this.doubleCoords;
            double d3 = dArr[0];
            double d4 = dArr[1];
            return 0;
        }

        /* access modifiers changed from: package-private */
        public int rectCrossings(double d, double d2, double d3, double d4) {
            double[] dArr = this.doubleCoords;
            double d5 = dArr[0];
            double d6 = dArr[1];
            return 0;
        }

        public final void append(PathIterator pathIterator, boolean z) {
            double[] dArr = new double[6];
            boolean z2 = z;
            while (!pathIterator.isDone()) {
                int currentSegment = pathIterator.currentSegment(dArr);
                if (currentSegment != 0) {
                    if (currentSegment != 1) {
                        if (currentSegment == 2) {
                            quadTo(dArr[0], dArr[1], dArr[2], dArr[3]);
                        } else if (currentSegment == 3) {
                            curveTo(dArr[0], dArr[1], dArr[2], dArr[3], dArr[4], dArr[5]);
                        } else if (currentSegment == 4) {
                            closePath();
                        }
                        pathIterator.next();
                        z2 = false;
                    }
                } else if (!z2 || this.numTypes < 1 || this.numCoords < 1) {
                    moveTo(dArr[0], dArr[1]);
                    pathIterator.next();
                    z2 = false;
                } else if (this.pointTypes[this.numTypes - 1] != 4 && this.doubleCoords[this.numCoords - 2] == dArr[0] && this.doubleCoords[this.numCoords - 1] == dArr[1]) {
                    pathIterator.next();
                    z2 = false;
                }
                lineTo(dArr[0], dArr[1]);
                pathIterator.next();
                z2 = false;
            }
        }

        public final void transform(AffineTransform affineTransform) {
            double[] dArr = this.doubleCoords;
            affineTransform.transform(dArr, 0, dArr, 0, this.numCoords / 2);
        }

        public final synchronized Rectangle2D getBounds2D() {
            double d;
            double d2;
            double d3;
            double d4;
            Rectangle2D.Double doubleR;
            synchronized (this) {
                int i = this.numCoords;
                if (i > 0) {
                    double[] dArr = this.doubleCoords;
                    int i2 = i - 1;
                    double d5 = dArr[i2];
                    int i3 = i2 - 1;
                    double d6 = dArr[i3];
                    double d7 = d6;
                    d3 = d5;
                    while (i3 > 0) {
                        double[] dArr2 = this.doubleCoords;
                        int i4 = i3 - 1;
                        double d8 = dArr2[i4];
                        i3 = i4 - 1;
                        double d9 = dArr2[i3];
                        if (d9 < d6) {
                            d6 = d9;
                        }
                        if (d8 < d5) {
                            d5 = d8;
                        }
                        if (d9 > d7) {
                            d7 = d9;
                        }
                        if (d8 > d3) {
                            d3 = d8;
                        }
                    }
                    d = d7;
                    double d10 = d5;
                    d4 = d6;
                    d2 = d10;
                } else {
                    d4 = 0.0d;
                    d3 = 0.0d;
                    d2 = 0.0d;
                    d = 0.0d;
                }
                doubleR = new Rectangle2D.Double(d4, d2, d - d4, d3 - d2);
            }
            return doubleR;
        }

        public PathIterator getPathIterator(AffineTransform affineTransform) {
            if (affineTransform == null) {
                return new CopyIterator(this);
            }
            return new TxIterator(this, affineTransform);
        }

        public final Object clone() {
            return new Double((Shape) this);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Path2D.super.writeObject(objectOutputStream, true);
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            Path2D.super.readObject(objectInputStream, true);
        }

        static class CopyIterator extends Iterator {
            double[] doubleCoords;

            CopyIterator(Double doubleR) {
                super(doubleR);
                this.doubleCoords = doubleR.doubleCoords;
            }

            public int currentSegment(float[] fArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    for (int i2 = 0; i2 < i; i2++) {
                        fArr[i2] = (float) this.doubleCoords[this.pointIdx + i2];
                    }
                }
                return b;
            }

            public int currentSegment(double[] dArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    System.arraycopy(this.doubleCoords, this.pointIdx, dArr, 0, i);
                }
                return b;
            }
        }

        static class TxIterator extends Iterator {
            AffineTransform affine;
            double[] doubleCoords;

            TxIterator(Double doubleR, AffineTransform affineTransform) {
                super(doubleR);
                this.doubleCoords = doubleR.doubleCoords;
                this.affine = affineTransform;
            }

            public int currentSegment(float[] fArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    this.affine.transform(this.doubleCoords, this.pointIdx, fArr, 0, i / 2);
                }
                return b;
            }

            public int currentSegment(double[] dArr) {
                byte b = this.path.pointTypes[this.typeIdx];
                int i = curvecoords[b];
                if (i > 0) {
                    this.affine.transform(this.doubleCoords, this.pointIdx, dArr, 0, i / 2);
                }
                return b;
            }
        }
    }

    public final synchronized void closePath() {
        int i = this.numTypes;
        if (i == 0 || this.pointTypes[i - 1] != 4) {
            needRoom(true, 0);
            byte[] bArr = this.pointTypes;
            int i2 = this.numTypes;
            this.numTypes = i2 + 1;
            bArr[i2] = 4;
        }
    }

    public final void append(Shape shape, boolean z) {
        append(shape.getPathIterator((AffineTransform) null), z);
    }

    public final synchronized int getWindingRule() {
        return this.windingRule;
    }

    public final void setWindingRule(int i) {
        if (i == 0 || i == 1) {
            this.windingRule = i;
            return;
        }
        throw new IllegalArgumentException("winding rule must be WIND_EVEN_ODD or WIND_NON_ZERO");
    }

    public final synchronized Point2D getCurrentPoint() {
        int i = this.numCoords;
        int i2 = this.numTypes;
        if (i2 >= 1) {
            if (i >= 1) {
                if (this.pointTypes[i2 - 1] == 4) {
                    for (int i3 = i2 - 2; i3 > 0; i3--) {
                        byte b = this.pointTypes[i3];
                        if (b == 0) {
                            break;
                        }
                        if (b == 1) {
                            i -= 2;
                        } else if (b == 2) {
                            i -= 4;
                        } else if (b == 3) {
                            i -= 6;
                        }
                    }
                }
                return getPoint(i - 2);
            }
        }
        return null;
    }

    public final synchronized void reset() {
        this.numCoords = 0;
        this.numTypes = 0;
    }

    public final synchronized Shape createTransformedShape(AffineTransform affineTransform) {
        Path2D path2D;
        path2D = (Path2D) clone();
        if (affineTransform != null) {
            path2D.transform(affineTransform);
        }
        return path2D;
    }

    public final Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    public static boolean contains(PathIterator pathIterator, double d, double d2) {
        if ((d * 0.0d) + (d2 * 0.0d) != 0.0d) {
            return false;
        }
        return ((pathIterator.getWindingRule() == 1 ? (char) 65535 : 1) & 0) != 0;
    }

    public static boolean contains(PathIterator pathIterator, Point2D point2D) {
        return contains(pathIterator, point2D.getX(), point2D.getY());
    }

    public final boolean contains(double d, double d2) {
        if ((d * 0.0d) + (d2 * 0.0d) != 0.0d || this.numTypes < 2) {
            return false;
        }
        if ((pointCrossings(d, d2) & (this.windingRule == 1 ? -1 : 1)) != 0) {
            return true;
        }
        return false;
    }

    public final boolean contains(Point2D point2D) {
        return contains(point2D.getX(), point2D.getY());
    }

    public static boolean contains(PathIterator pathIterator, double d, double d2, double d3, double d4) {
        if (!Double.isNaN(d + d3) && !Double.isNaN(d2 + d4) && d3 > 0.0d && d4 > 0.0d) {
            int windingRule2 = pathIterator.getWindingRule();
        }
        return false;
    }

    public static boolean contains(PathIterator pathIterator, Rectangle2D rectangle2D) {
        return contains(pathIterator, rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public final boolean contains(double d, double d2, double d3, double d4) {
        double d5 = d + d3;
        if (!Double.isNaN(d5)) {
            double d6 = d2 + d4;
            if (!Double.isNaN(d6)) {
                if (d3 <= 0.0d || d4 <= 0.0d) {
                    return false;
                }
                int i = this.windingRule == 1 ? -1 : 2;
                int rectCrossings = rectCrossings(d, d2, d5, d6);
                if (rectCrossings == 0 || (rectCrossings & i) == 0) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final boolean contains(Rectangle2D rectangle2D) {
        return contains(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public static boolean intersects(PathIterator pathIterator, double d, double d2, double d3, double d4) {
        if (Double.isNaN(d + d3) || Double.isNaN(d2 + d4) || d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        int windingRule2 = pathIterator.getWindingRule();
        return true;
    }

    public static boolean intersects(PathIterator pathIterator, Rectangle2D rectangle2D) {
        return intersects(pathIterator, rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public final boolean intersects(double d, double d2, double d3, double d4) {
        double d5 = d + d3;
        if (!Double.isNaN(d5)) {
            double d6 = d2 + d4;
            if (!Double.isNaN(d6)) {
                if (d3 <= 0.0d || d4 <= 0.0d) {
                    return false;
                }
                int i = this.windingRule == 1 ? -1 : 2;
                int rectCrossings = rectCrossings(d, d2, d5, d6);
                if (rectCrossings == 0 || (rectCrossings & i) != 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public final boolean intersects(Rectangle2D rectangle2D) {
        return intersects(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new FlatteningPathIterator(getPathIterator(affineTransform), d);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0099 A[EDGE_INSN: B:46:0x0099->B:40:0x0099 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void writeObject(java.io.ObjectOutputStream r12, boolean r13) throws java.io.IOException {
        /*
            r11 = this;
            r12.defaultWriteObject()
            r0 = 0
            if (r13 == 0) goto L_0x000c
            r1 = r11
            com.app.office.java.awt.geom.Path2D$Double r1 = (com.app.office.java.awt.geom.Path2D.Double) r1
            double[] r1 = r1.doubleCoords
            goto L_0x0014
        L_0x000c:
            r1 = r11
            com.app.office.java.awt.geom.Path2D$Float r1 = (com.app.office.java.awt.geom.Path2D.Float) r1
            float[] r1 = r1.floatCoords
            r10 = r1
            r1 = r0
            r0 = r10
        L_0x0014:
            int r2 = r11.numTypes
            if (r13 == 0) goto L_0x001b
            r3 = 49
            goto L_0x001d
        L_0x001b:
            r3 = 48
        L_0x001d:
            r12.writeByte(r3)
            r12.writeInt(r2)
            int r3 = r11.numCoords
            r12.writeInt(r3)
            int r3 = r11.windingRule
            byte r3 = (byte) r3
            r12.writeByte(r3)
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x0031:
            if (r4 >= r2) goto L_0x009c
            byte[] r6 = r11.pointTypes
            byte r6 = r6[r4]
            r7 = 3
            r8 = 2
            r9 = 1
            if (r6 == 0) goto L_0x006a
            if (r6 == r9) goto L_0x0062
            if (r6 == r8) goto L_0x0059
            if (r6 == r7) goto L_0x0051
            r7 = 4
            if (r6 != r7) goto L_0x0049
            r6 = 96
            r7 = 0
            goto L_0x0072
        L_0x0049:
            java.lang.InternalError r12 = new java.lang.InternalError
            java.lang.String r13 = "unrecognized path type"
            r12.<init>(r13)
            throw r12
        L_0x0051:
            if (r13 == 0) goto L_0x0056
            r6 = 83
            goto L_0x0072
        L_0x0056:
            r6 = 67
            goto L_0x0072
        L_0x0059:
            if (r13 == 0) goto L_0x005e
            r6 = 82
            goto L_0x0060
        L_0x005e:
            r6 = 66
        L_0x0060:
            r7 = 2
            goto L_0x0072
        L_0x0062:
            if (r13 == 0) goto L_0x0067
            r6 = 81
            goto L_0x0071
        L_0x0067:
            r6 = 65
            goto L_0x0071
        L_0x006a:
            if (r13 == 0) goto L_0x006f
            r6 = 80
            goto L_0x0071
        L_0x006f:
            r6 = 64
        L_0x0071:
            r7 = 1
        L_0x0072:
            r12.writeByte(r6)
        L_0x0075:
            int r7 = r7 + -1
            if (r7 < 0) goto L_0x0099
            if (r13 == 0) goto L_0x008a
            int r6 = r5 + 1
            r8 = r1[r5]
            r12.writeDouble(r8)
            int r5 = r6 + 1
            r8 = r1[r6]
            r12.writeDouble(r8)
            goto L_0x0075
        L_0x008a:
            int r6 = r5 + 1
            r5 = r0[r5]
            r12.writeFloat(r5)
            int r5 = r6 + 1
            r6 = r0[r6]
            r12.writeFloat(r6)
            goto L_0x0075
        L_0x0099:
            int r4 = r4 + 1
            goto L_0x0031
        L_0x009c:
            r13 = 97
            r12.writeByte(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.Path2D.writeObject(java.io.ObjectOutputStream, boolean):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0060, code lost:
        r6 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0063, code lost:
        r5 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0066, code lost:
        r5 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0069, code lost:
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006a, code lost:
        r6 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void readObject(java.io.ObjectInputStream r10, boolean r11) throws java.lang.ClassNotFoundException, java.io.IOException {
        /*
            r9 = this;
            r10.defaultReadObject()
            r10.readByte()
            int r0 = r10.readInt()
            int r1 = r10.readInt()
            byte r2 = r10.readByte()     // Catch:{ IllegalArgumentException -> 0x00c4 }
            r9.setWindingRule(r2)     // Catch:{ IllegalArgumentException -> 0x00c4 }
            if (r0 >= 0) goto L_0x001a
            r2 = 20
            goto L_0x001b
        L_0x001a:
            r2 = r0
        L_0x001b:
            byte[] r2 = new byte[r2]
            r9.pointTypes = r2
            if (r1 >= 0) goto L_0x0023
            r1 = 40
        L_0x0023:
            if (r11 == 0) goto L_0x002d
            r11 = r9
            com.app.office.java.awt.geom.Path2D$Double r11 = (com.app.office.java.awt.geom.Path2D.Double) r11
            double[] r1 = new double[r1]
            r11.doubleCoords = r1
            goto L_0x0034
        L_0x002d:
            r11 = r9
            com.app.office.java.awt.geom.Path2D$Float r11 = (com.app.office.java.awt.geom.Path2D.Float) r11
            float[] r1 = new float[r1]
            r11.floatCoords = r1
        L_0x0034:
            r11 = 0
            r1 = 0
        L_0x0036:
            r2 = 97
            if (r0 < 0) goto L_0x003c
            if (r1 >= r0) goto L_0x006e
        L_0x003c:
            byte r3 = r10.readByte()
            r4 = 96
            r5 = 3
            r6 = 2
            r7 = 1
            if (r3 == r4) goto L_0x0088
            if (r3 == r2) goto L_0x006c
            switch(r3) {
                case 64: goto L_0x0068;
                case 65: goto L_0x0065;
                case 66: goto L_0x0062;
                case 67: goto L_0x005f;
                default: goto L_0x004c;
            }
        L_0x004c:
            switch(r3) {
                case 80: goto L_0x005d;
                case 81: goto L_0x005b;
                case 82: goto L_0x0059;
                case 83: goto L_0x0057;
                default: goto L_0x004f;
            }
        L_0x004f:
            java.io.StreamCorruptedException r10 = new java.io.StreamCorruptedException
            java.lang.String r11 = "unrecognized path type"
            r10.<init>(r11)
            throw r10
        L_0x0057:
            r2 = 1
            goto L_0x0060
        L_0x0059:
            r2 = 1
            goto L_0x0063
        L_0x005b:
            r2 = 1
            goto L_0x0066
        L_0x005d:
            r2 = 1
            goto L_0x0069
        L_0x005f:
            r2 = 0
        L_0x0060:
            r6 = 3
            goto L_0x008b
        L_0x0062:
            r2 = 0
        L_0x0063:
            r5 = 2
            goto L_0x008b
        L_0x0065:
            r2 = 0
        L_0x0066:
            r5 = 1
            goto L_0x006a
        L_0x0068:
            r2 = 0
        L_0x0069:
            r5 = 0
        L_0x006a:
            r6 = 1
            goto L_0x008b
        L_0x006c:
            if (r0 >= 0) goto L_0x0080
        L_0x006e:
            if (r0 < 0) goto L_0x007f
            byte r10 = r10.readByte()
            if (r10 != r2) goto L_0x0077
            goto L_0x007f
        L_0x0077:
            java.io.StreamCorruptedException r10 = new java.io.StreamCorruptedException
            java.lang.String r11 = "missing PATH_END"
            r10.<init>(r11)
            throw r10
        L_0x007f:
            return
        L_0x0080:
            java.io.StreamCorruptedException r10 = new java.io.StreamCorruptedException
            java.lang.String r11 = "unexpected PATH_END"
            r10.<init>(r11)
            throw r10
        L_0x0088:
            r5 = 4
            r2 = 0
            r6 = 0
        L_0x008b:
            if (r5 == 0) goto L_0x008e
            goto L_0x008f
        L_0x008e:
            r7 = 0
        L_0x008f:
            int r3 = r6 * 2
            r9.needRoom(r7, r3)
            if (r2 == 0) goto L_0x00a6
        L_0x0096:
            int r6 = r6 + -1
            if (r6 < 0) goto L_0x00b6
            double r2 = r10.readDouble()
            double r7 = r10.readDouble()
            r9.append((double) r2, (double) r7)
            goto L_0x0096
        L_0x00a6:
            int r6 = r6 + -1
            if (r6 < 0) goto L_0x00b6
            float r2 = r10.readFloat()
            float r3 = r10.readFloat()
            r9.append((float) r2, (float) r3)
            goto L_0x00a6
        L_0x00b6:
            byte[] r2 = r9.pointTypes
            int r3 = r9.numTypes
            int r4 = r3 + 1
            r9.numTypes = r4
            r2[r3] = r5
            int r1 = r1 + 1
            goto L_0x0036
        L_0x00c4:
            r10 = move-exception
            java.io.InvalidObjectException r11 = new java.io.InvalidObjectException
            java.lang.String r10 = r10.getMessage()
            r11.<init>(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.Path2D.readObject(java.io.ObjectInputStream, boolean):void");
    }

    static abstract class Iterator implements PathIterator {
        static final int[] curvecoords = {2, 2, 4, 6, 0};
        Path2D path;
        int pointIdx;
        int typeIdx;

        Iterator(Path2D path2D) {
            this.path = path2D;
        }

        public int getWindingRule() {
            return this.path.getWindingRule();
        }

        public boolean isDone() {
            return this.typeIdx >= this.path.numTypes;
        }

        public void next() {
            byte[] bArr = this.path.pointTypes;
            int i = this.typeIdx;
            this.typeIdx = i + 1;
            this.pointIdx += curvecoords[bArr[i]];
        }
    }
}
