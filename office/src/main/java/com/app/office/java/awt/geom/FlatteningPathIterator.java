package com.app.office.java.awt.geom;

import java.util.NoSuchElementException;

public class FlatteningPathIterator implements PathIterator {
    static final int GROW_SIZE = 24;
    double curx;
    double cury;
    boolean done;
    double[] hold;
    int holdEnd;
    int holdIndex;
    int holdType;
    int levelIndex;
    int[] levels;
    int limit;
    double movx;
    double movy;
    double squareflat;
    PathIterator src;

    public FlatteningPathIterator(PathIterator pathIterator, double d) {
        this(pathIterator, d, 10);
    }

    public FlatteningPathIterator(PathIterator pathIterator, double d, int i) {
        this.hold = new double[14];
        if (d < 0.0d) {
            throw new IllegalArgumentException("flatness must be >= 0");
        } else if (i >= 0) {
            this.src = pathIterator;
            this.squareflat = d * d;
            this.limit = i;
            this.levels = new int[(i + 1)];
            next(false);
        } else {
            throw new IllegalArgumentException("limit must be >= 0");
        }
    }

    public double getFlatness() {
        return Math.sqrt(this.squareflat);
    }

    public int getRecursionLimit() {
        return this.limit;
    }

    public int getWindingRule() {
        return this.src.getWindingRule();
    }

    public boolean isDone() {
        return this.done;
    }

    /* access modifiers changed from: package-private */
    public void ensureHoldCapacity(int i) {
        int i2 = this.holdIndex;
        if (i2 - i < 0) {
            double[] dArr = this.hold;
            double[] dArr2 = new double[(dArr.length + 24)];
            System.arraycopy(dArr, i2, dArr2, i2 + 24, dArr.length - i2);
            this.hold = dArr2;
            this.holdIndex += 24;
            this.holdEnd += 24;
        }
    }

    public void next() {
        next(true);
    }

    private void next(boolean z) {
        if (this.holdIndex >= this.holdEnd) {
            if (z) {
                this.src.next();
            }
            if (this.src.isDone()) {
                this.done = true;
                return;
            }
            this.holdType = this.src.currentSegment(this.hold);
            this.levelIndex = 0;
            this.levels[0] = 0;
        }
        int i = this.holdType;
        if (i == 0 || i == 1) {
            double[] dArr = this.hold;
            double d = dArr[0];
            this.curx = d;
            double d2 = dArr[1];
            this.cury = d2;
            if (i == 0) {
                this.movx = d;
                this.movy = d2;
            }
            this.holdIndex = 0;
            this.holdEnd = 0;
        } else if (i == 2) {
            if (this.holdIndex >= this.holdEnd) {
                double[] dArr2 = this.hold;
                int length = dArr2.length - 6;
                this.holdIndex = length;
                this.holdEnd = dArr2.length - 2;
                dArr2[length + 0] = this.curx;
                dArr2[length + 1] = this.cury;
                dArr2[length + 2] = dArr2[0];
                dArr2[length + 3] = dArr2[1];
                double d3 = dArr2[2];
                this.curx = d3;
                dArr2[length + 4] = d3;
                double d4 = dArr2[3];
                this.cury = d4;
                dArr2[length + 5] = d4;
            }
            int i2 = this.levels[this.levelIndex];
            while (i2 < this.limit && QuadCurve2D.getFlatnessSq(this.hold, this.holdIndex) >= this.squareflat) {
                ensureHoldCapacity(4);
                double[] dArr3 = this.hold;
                int i3 = this.holdIndex;
                QuadCurve2D.subdivide(dArr3, i3, dArr3, i3 - 4, dArr3, i3);
                this.holdIndex -= 4;
                i2++;
                int[] iArr = this.levels;
                int i4 = this.levelIndex;
                iArr[i4] = i2;
                int i5 = i4 + 1;
                this.levelIndex = i5;
                iArr[i5] = i2;
            }
            this.holdIndex += 4;
            this.levelIndex--;
        } else if (i == 3) {
            if (this.holdIndex >= this.holdEnd) {
                double[] dArr4 = this.hold;
                int length2 = dArr4.length - 8;
                this.holdIndex = length2;
                this.holdEnd = dArr4.length - 2;
                dArr4[length2 + 0] = this.curx;
                dArr4[length2 + 1] = this.cury;
                dArr4[length2 + 2] = dArr4[0];
                dArr4[length2 + 3] = dArr4[1];
                dArr4[length2 + 4] = dArr4[2];
                dArr4[length2 + 5] = dArr4[3];
                double d5 = dArr4[4];
                this.curx = d5;
                dArr4[length2 + 6] = d5;
                double d6 = dArr4[5];
                this.cury = d6;
                dArr4[length2 + 7] = d6;
            }
            int i6 = this.levels[this.levelIndex];
            while (i6 < this.limit && CubicCurve2D.getFlatnessSq(this.hold, this.holdIndex) >= this.squareflat) {
                ensureHoldCapacity(6);
                double[] dArr5 = this.hold;
                int i7 = this.holdIndex;
                CubicCurve2D.subdivide(dArr5, i7, dArr5, i7 - 6, dArr5, i7);
                this.holdIndex -= 6;
                i6++;
                int[] iArr2 = this.levels;
                int i8 = this.levelIndex;
                iArr2[i8] = i6;
                int i9 = i8 + 1;
                this.levelIndex = i9;
                iArr2[i9] = i6;
            }
            this.holdIndex += 6;
            this.levelIndex--;
        } else if (i == 4) {
            this.curx = this.movx;
            this.cury = this.movy;
            this.holdIndex = 0;
            this.holdEnd = 0;
        }
    }

    public int currentSegment(float[] fArr) {
        if (!isDone()) {
            int i = this.holdType;
            if (i == 4) {
                return i;
            }
            double[] dArr = this.hold;
            int i2 = this.holdIndex;
            fArr[0] = (float) dArr[i2 + 0];
            fArr[1] = (float) dArr[i2 + 1];
            if (i != 0) {
                return 1;
            }
            return i;
        }
        throw new NoSuchElementException("flattening iterator out of bounds");
    }

    public int currentSegment(double[] dArr) {
        if (!isDone()) {
            int i = this.holdType;
            if (i == 4) {
                return i;
            }
            double[] dArr2 = this.hold;
            int i2 = this.holdIndex;
            dArr[0] = dArr2[i2 + 0];
            dArr[1] = dArr2[i2 + 1];
            if (i != 0) {
                return 1;
            }
            return i;
        }
        throw new NoSuchElementException("flattening iterator out of bounds");
    }
}
