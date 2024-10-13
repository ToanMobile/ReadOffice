package com.app.office.java.awt.geom;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

public abstract class Crossings {
    public static final boolean debug = false;
    int limit = 0;
    private Vector tmp = new Vector();
    double xhi;
    double xlo;
    double yhi;
    double ylo;
    double[] yranges = new double[10];

    public abstract boolean covers(double d, double d2);

    public abstract void record(double d, double d2, int i);

    public Crossings(double d, double d2, double d3, double d4) {
        this.xlo = d;
        this.ylo = d2;
        this.xhi = d3;
        this.yhi = d4;
    }

    public final double getXLo() {
        return this.xlo;
    }

    public final double getYLo() {
        return this.ylo;
    }

    public final double getXHi() {
        return this.xhi;
    }

    public final double getYHi() {
        return this.yhi;
    }

    public void print() {
        System.out.println("Crossings [");
        PrintStream printStream = System.out;
        printStream.println("  bounds = [" + this.ylo + ", " + this.yhi + "]");
        for (int i = 0; i < this.limit; i += 2) {
            PrintStream printStream2 = System.out;
            printStream2.println("  [" + this.yranges[i] + ", " + this.yranges[i + 1] + "]");
        }
        System.out.println("]");
    }

    public final boolean isEmpty() {
        return this.limit == 0;
    }

    public static Crossings findCrossings(Vector vector, double d, double d2, double d3, double d4) {
        EvenOdd evenOdd = new EvenOdd(d, d2, d3, d4);
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            if (((Curve) elements.nextElement()).accumulateCrossings(evenOdd)) {
                return null;
            }
        }
        return evenOdd;
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [com.app.office.java.awt.geom.Crossings] */
    /* JADX WARNING: type inference failed for: r1v19, types: [com.app.office.java.awt.geom.Crossings$NonZero] */
    /* JADX WARNING: type inference failed for: r1v20, types: [com.app.office.java.awt.geom.Crossings$EvenOdd] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.java.awt.geom.Crossings findCrossings(com.app.office.java.awt.geom.PathIterator r20, double r21, double r23, double r25, double r27) {
        /*
            int r0 = r20.getWindingRule()
            if (r0 != 0) goto L_0x0015
            com.app.office.java.awt.geom.Crossings$EvenOdd r0 = new com.app.office.java.awt.geom.Crossings$EvenOdd
            r1 = r0
            r2 = r21
            r4 = r23
            r6 = r25
            r8 = r27
            r1.<init>(r2, r4, r6, r8)
            goto L_0x0023
        L_0x0015:
            com.app.office.java.awt.geom.Crossings$NonZero r0 = new com.app.office.java.awt.geom.Crossings$NonZero
            r1 = r0
            r2 = r21
            r4 = r23
            r6 = r25
            r8 = r27
            r1.<init>(r2, r4, r6, r8)
        L_0x0023:
            r1 = 23
            double[] r10 = new double[r1]
            r1 = 0
            r4 = r1
            r11 = r4
            r13 = r11
            r2 = r13
        L_0x002d:
            boolean r1 = r20.isDone()
            r15 = 0
            if (r1 != 0) goto L_0x00bc
            r8 = r20
            int r1 = r8.currentSegment((double[]) r10)
            r16 = 0
            r9 = 1
            if (r1 == 0) goto L_0x00a1
            if (r1 == r9) goto L_0x008c
            r6 = 3
            r7 = 2
            if (r1 == r7) goto L_0x0075
            r7 = 4
            if (r1 == r6) goto L_0x005e
            if (r1 == r7) goto L_0x004c
            goto L_0x00b7
        L_0x004c:
            int r1 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x005a
            r1 = r0
            r6 = r11
            r8 = r13
            boolean r1 = r1.accumulateLine(r2, r4, r6, r8)
            if (r1 == 0) goto L_0x005a
            return r15
        L_0x005a:
            r2 = r11
            r4 = r13
            goto L_0x00b7
        L_0x005e:
            r6 = r10[r7]
            r1 = 5
            r8 = r10[r1]
            r21 = r0
            r22 = r2
            r24 = r4
            r26 = r10
            boolean r1 = r21.accumulateCubic(r22, r24, r26)
            if (r1 == 0) goto L_0x0072
            return r15
        L_0x0072:
            r2 = r6
            r4 = r8
            goto L_0x00b7
        L_0x0075:
            r7 = r10[r7]
            r16 = r10[r6]
            r21 = r0
            r22 = r2
            r24 = r4
            r26 = r10
            boolean r1 = r21.accumulateQuad(r22, r24, r26)
            if (r1 == 0) goto L_0x0088
            return r15
        L_0x0088:
            r2 = r7
            r4 = r16
            goto L_0x00b7
        L_0x008c:
            r16 = r10[r16]
            r18 = r10[r9]
            r1 = r0
            r6 = r16
            r8 = r18
            boolean r1 = r1.accumulateLine(r2, r4, r6, r8)
            if (r1 == 0) goto L_0x009c
            return r15
        L_0x009c:
            r2 = r16
            r4 = r18
            goto L_0x00b7
        L_0x00a1:
            int r1 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x00b0
            r1 = r0
            r6 = r11
            r11 = 1
            r8 = r13
            boolean r1 = r1.accumulateLine(r2, r4, r6, r8)
            if (r1 == 0) goto L_0x00b1
            return r15
        L_0x00b0:
            r11 = 1
        L_0x00b1:
            r2 = r10[r16]
            r4 = r10[r11]
            r11 = r2
            r13 = r4
        L_0x00b7:
            r20.next()
            goto L_0x002d
        L_0x00bc:
            int r1 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x00d1
            r20 = r0
            r21 = r2
            r23 = r4
            r25 = r11
            r27 = r13
            boolean r1 = r20.accumulateLine(r21, r23, r25, r27)
            if (r1 == 0) goto L_0x00d1
            return r15
        L_0x00d1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.Crossings.findCrossings(com.app.office.java.awt.geom.PathIterator, double, double, double, double):com.app.office.java.awt.geom.Crossings");
    }

    public boolean accumulateLine(double d, double d2, double d3, double d4) {
        if (d2 <= d4) {
            return accumulateLine(d, d2, d3, d4, 1);
        }
        return accumulateLine(d3, d4, d, d2, -1);
    }

    public boolean accumulateLine(double d, double d2, double d3, double d4, int i) {
        double d5;
        double d6;
        double d7 = this.yhi;
        if (d7 > d2) {
            double d8 = this.ylo;
            if (d8 < d4) {
                double d9 = this.xhi;
                if (d >= d9 && d3 >= d9) {
                    return false;
                }
                if (d2 == d4) {
                    double d10 = this.xlo;
                    if (d >= d10 || d3 >= d10) {
                        return true;
                    }
                    return false;
                }
                double d11 = d3 - d;
                double d12 = d4 - d2;
                if (d2 < d8) {
                    d5 = d + (((d8 - d2) * d11) / d12);
                } else {
                    d5 = d;
                    d8 = d2;
                }
                if (d7 < d4) {
                    d6 = d + (((d7 - d2) * d11) / d12);
                } else {
                    d6 = d3;
                    d7 = d4;
                }
                if (d5 >= d9 && d6 >= d9) {
                    return false;
                }
                double d13 = this.xlo;
                if (d5 > d13 || d6 > d13) {
                    return true;
                }
                record(d8, d7, i);
                return false;
            }
        }
        return false;
    }

    public boolean accumulateQuad(double d, double d2, double[] dArr) {
        double d3 = d2;
        double d4 = this.ylo;
        if (d3 < d4 && dArr[1] < d4 && dArr[3] < d4) {
            return false;
        }
        double d5 = this.yhi;
        if (d3 > d5 && dArr[1] > d5 && dArr[3] > d5) {
            return false;
        }
        double d6 = this.xhi;
        if (d > d6 && dArr[0] > d6 && dArr[2] > d6) {
            return false;
        }
        double d7 = this.xlo;
        if (d >= d7 || dArr[0] >= d7 || dArr[2] >= d7) {
            Curve.insertQuad(this.tmp, d, d2, dArr);
            Enumeration elements = this.tmp.elements();
            while (elements.hasMoreElements()) {
                if (((Curve) elements.nextElement()).accumulateCrossings(this)) {
                    return true;
                }
            }
            this.tmp.clear();
            return false;
        }
        if (d3 < dArr[3]) {
            record(Math.max(d3, d4), Math.min(dArr[3], this.yhi), 1);
        } else if (d3 > dArr[3]) {
            record(Math.max(dArr[3], d4), Math.min(d3, this.yhi), -1);
        }
        return false;
    }

    public boolean accumulateCubic(double d, double d2, double[] dArr) {
        double d3 = d2;
        double d4 = this.ylo;
        if (d3 < d4 && dArr[1] < d4 && dArr[3] < d4 && dArr[5] < d4) {
            return false;
        }
        double d5 = this.yhi;
        if (d3 > d5 && dArr[1] > d5 && dArr[3] > d5 && dArr[5] > d5) {
            return false;
        }
        double d6 = this.xhi;
        if (d > d6 && dArr[0] > d6 && dArr[2] > d6 && dArr[4] > d6) {
            return false;
        }
        double d7 = this.xlo;
        if (d >= d7 || dArr[0] >= d7 || dArr[2] >= d7 || dArr[4] >= d7) {
            Curve.insertCubic(this.tmp, d, d2, dArr);
            Enumeration elements = this.tmp.elements();
            while (elements.hasMoreElements()) {
                if (((Curve) elements.nextElement()).accumulateCrossings(this)) {
                    return true;
                }
            }
            this.tmp.clear();
            return false;
        }
        if (d3 <= dArr[5]) {
            record(Math.max(d3, d4), Math.min(dArr[5], this.yhi), 1);
        } else {
            record(Math.max(dArr[5], d4), Math.min(d3, this.yhi), -1);
        }
        return false;
    }

    public static final class EvenOdd extends Crossings {
        public EvenOdd(double d, double d2, double d3, double d4) {
            super(d, d2, d3, d4);
        }

        public final boolean covers(double d, double d2) {
            return this.limit == 2 && this.yranges[0] <= d && this.yranges[1] >= d2;
        }

        public void record(double d, double d2, int i) {
            int i2;
            if (d < d2) {
                int i3 = 0;
                while (i2 < this.limit && d > this.yranges[i2 + 1]) {
                    i3 = i2 + 2;
                }
                int i4 = i2;
                while (true) {
                    if (i2 >= this.limit) {
                        break;
                    }
                    int i5 = i2 + 1;
                    double d3 = this.yranges[i2];
                    int i6 = i5 + 1;
                    double d4 = this.yranges[i5];
                    if (d2 < d3) {
                        int i7 = i4 + 1;
                        this.yranges[i4] = d;
                        i4 = i7 + 1;
                        this.yranges[i7] = d2;
                        i2 = i6;
                        d = d3;
                    } else {
                        if (d < d3) {
                            double d5 = d;
                            d = d3;
                            d3 = d5;
                        }
                        if (d2 >= d4) {
                            double d6 = d2;
                            d2 = d4;
                            d4 = d6;
                        }
                        int i8 = (d > d2 ? 1 : (d == d2 ? 0 : -1));
                        if (i8 == 0) {
                            d = d3;
                        } else {
                            if (i8 > 0) {
                                double d7 = d;
                                d = d2;
                                d2 = d7;
                            }
                            if (d3 != d) {
                                int i9 = i4 + 1;
                                this.yranges[i4] = d3;
                                i4 = i9 + 1;
                                this.yranges[i9] = d;
                            }
                            d = d2;
                        }
                        if (d >= d4) {
                            i2 = i6;
                            d2 = d4;
                            break;
                        }
                        i2 = i6;
                    }
                    d2 = d4;
                }
                if (i4 < i2 && i2 < this.limit) {
                    System.arraycopy(this.yranges, i2, this.yranges, i4, this.limit - i2);
                }
                int i10 = i4 + (this.limit - i2);
                if (d < d2) {
                    if (i10 >= this.yranges.length) {
                        double[] dArr = new double[(i10 + 10)];
                        System.arraycopy(this.yranges, 0, dArr, 0, i10);
                        this.yranges = dArr;
                    }
                    int i11 = i10 + 1;
                    this.yranges[i10] = d;
                    i10 = i11 + 1;
                    this.yranges[i11] = d2;
                }
                this.limit = i10;
            }
        }
    }

    public static final class NonZero extends Crossings {
        private int[] crosscounts = new int[(this.yranges.length / 2)];

        public NonZero(double d, double d2, double d3, double d4) {
            super(d, d2, d3, d4);
        }

        public final boolean covers(double d, double d2) {
            int i = 0;
            while (i < this.limit) {
                int i2 = i + 1;
                double d3 = this.yranges[i];
                int i3 = i2 + 1;
                double d4 = this.yranges[i2];
                if (d < d4) {
                    if (d < d3) {
                        return false;
                    }
                    if (d2 <= d4) {
                        return true;
                    }
                    d = d4;
                }
                i = i3;
            }
            if (d >= d2) {
                return true;
            }
            return false;
        }

        public void remove(int i) {
            this.limit -= 2;
            int i2 = this.limit - i;
            if (i2 > 0) {
                System.arraycopy(this.yranges, i + 2, this.yranges, i, i2);
                int[] iArr = this.crosscounts;
                int i3 = i / 2;
                System.arraycopy(iArr, i3 + 1, iArr, i3, i2 / 2);
            }
        }

        public void insert(int i, double d, double d2, int i2) {
            int i3 = this.limit - i;
            double[] dArr = this.yranges;
            int[] iArr = this.crosscounts;
            if (this.limit >= this.yranges.length) {
                this.yranges = new double[(this.limit + 10)];
                System.arraycopy(dArr, 0, this.yranges, 0, i);
                int[] iArr2 = new int[((this.limit + 10) / 2)];
                this.crosscounts = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i / 2);
            }
            if (i3 > 0) {
                System.arraycopy(dArr, i, this.yranges, i + 2, i3);
                int i4 = i / 2;
                System.arraycopy(iArr, i4, this.crosscounts, i4 + 1, i3 / 2);
            }
            this.yranges[i + 0] = d;
            this.yranges[i + 1] = d2;
            this.crosscounts[i / 2] = i2;
            this.limit += 2;
        }

        public void record(double d, double d2, int i) {
            double d3;
            int i2;
            double d4;
            double d5;
            int i3;
            double d6;
            int i4;
            double d7;
            double d8 = d2;
            int i5 = i;
            if (d < d8) {
                int i6 = 0;
                while (i6 < this.limit && d > this.yranges[i6 + 1]) {
                    i6 += 2;
                }
                if (i6 < this.limit) {
                    int i7 = i6 / 2;
                    int i8 = this.crosscounts[i7];
                    int i9 = i6 + 0;
                    double d9 = this.yranges[i9];
                    int i10 = i6 + 1;
                    double d10 = this.yranges[i10];
                    if (d10 != d || i8 != i5) {
                        d4 = d;
                        i3 = i8;
                        double d11 = d9;
                        d6 = d10;
                        d5 = d11;
                    } else if (i6 + 2 == this.limit) {
                        this.yranges[i10] = d8;
                        return;
                    } else {
                        remove(i6);
                        int i11 = this.crosscounts[i7];
                        double d12 = this.yranges[i9];
                        double d13 = this.yranges[i10];
                        i3 = i11;
                        d4 = d9;
                        d6 = d13;
                        d5 = d12;
                    }
                    if (d8 < d5) {
                        insert(i6, d4, d2, i);
                        return;
                    } else if (d8 == d5 && i3 == i5) {
                        this.yranges[i6] = d4;
                        return;
                    } else {
                        if (d4 < d5) {
                            double d14 = d4;
                            i4 = i3;
                            insert(i6, d14, d5, i);
                            i6 += 2;
                            double d15 = d5;
                            d7 = d6;
                            d4 = d15;
                        } else {
                            i4 = i3;
                            if (d5 < d4) {
                                double d16 = d5;
                                d7 = d6;
                                insert(i6, d16, d4, i4);
                                i6 += 2;
                            } else {
                                d7 = d6;
                            }
                        }
                        int i12 = i4 + i5;
                        double min = Math.min(d8, d7);
                        if (i12 == 0) {
                            remove(i6);
                        } else {
                            this.crosscounts[i6 / 2] = i12;
                            int i13 = i6 + 1;
                            this.yranges[i6] = d4;
                            i6 = i13 + 1;
                            this.yranges[i13] = min;
                        }
                        if (min < d7) {
                            insert(i6, min, d7, i4);
                        }
                        i2 = i6;
                        d3 = min;
                    }
                } else {
                    d3 = d;
                    i2 = i6;
                }
                if (d3 < d8) {
                    insert(i2, d3, d2, i);
                }
            }
        }
    }
}
