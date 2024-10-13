package com.app.office.java.awt.geom;

import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Path2D;
import com.app.office.java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AffineTransform implements Cloneable, Serializable {
    static final int APPLY_IDENTITY = 0;
    static final int APPLY_SCALE = 2;
    static final int APPLY_SHEAR = 4;
    static final int APPLY_TRANSLATE = 1;
    private static final int HI_IDENTITY = 0;
    private static final int HI_SCALE = 16;
    private static final int HI_SHEAR = 32;
    private static final int HI_SHIFT = 3;
    private static final int HI_TRANSLATE = 8;
    public static final int TYPE_FLIP = 64;
    public static final int TYPE_GENERAL_ROTATION = 16;
    public static final int TYPE_GENERAL_SCALE = 4;
    public static final int TYPE_GENERAL_TRANSFORM = 32;
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_MASK_ROTATION = 24;
    public static final int TYPE_MASK_SCALE = 6;
    public static final int TYPE_QUADRANT_ROTATION = 8;
    public static final int TYPE_TRANSLATION = 1;
    public static final int TYPE_UNIFORM_SCALE = 2;
    private static final int TYPE_UNKNOWN = -1;
    private static final int[] rot90conversion = {4, 5, 4, 5, 2, 3, 6, 7};
    private static final long serialVersionUID = 1330973210523860834L;
    double m00;
    double m01;
    double m02;
    double m10;
    double m11;
    double m12;
    transient int state;
    private transient int type;

    private AffineTransform(double d, double d2, double d3, double d4, double d5, double d6, int i) {
        this.m00 = d;
        this.m10 = d2;
        this.m01 = d3;
        this.m11 = d4;
        this.m02 = d5;
        this.m12 = d6;
        this.state = i;
        this.type = -1;
    }

    public AffineTransform() {
        this.m11 = 1.0d;
        this.m00 = 1.0d;
    }

    public AffineTransform(AffineTransform affineTransform) {
        this.m00 = affineTransform.m00;
        this.m10 = affineTransform.m10;
        this.m01 = affineTransform.m01;
        this.m11 = affineTransform.m11;
        this.m02 = affineTransform.m02;
        this.m12 = affineTransform.m12;
        this.state = affineTransform.state;
        this.type = affineTransform.type;
    }

    public AffineTransform(float f, float f2, float f3, float f4, float f5, float f6) {
        this.m00 = (double) f;
        this.m10 = (double) f2;
        this.m01 = (double) f3;
        this.m11 = (double) f4;
        this.m02 = (double) f5;
        this.m12 = (double) f6;
        updateState();
    }

    public AffineTransform(float[] fArr) {
        this.m00 = (double) fArr[0];
        this.m10 = (double) fArr[1];
        this.m01 = (double) fArr[2];
        this.m11 = (double) fArr[3];
        if (fArr.length > 5) {
            this.m02 = (double) fArr[4];
            this.m12 = (double) fArr[5];
        }
        updateState();
    }

    public AffineTransform(double d, double d2, double d3, double d4, double d5, double d6) {
        this.m00 = d;
        this.m10 = d2;
        this.m01 = d3;
        this.m11 = d4;
        this.m02 = d5;
        this.m12 = d6;
        updateState();
    }

    public AffineTransform(double[] dArr) {
        this.m00 = dArr[0];
        this.m10 = dArr[1];
        this.m01 = dArr[2];
        this.m11 = dArr[3];
        if (dArr.length > 5) {
            this.m02 = dArr[4];
            this.m12 = dArr[5];
        }
        updateState();
    }

    public static AffineTransform getTranslateInstance(double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(d, d2);
        return affineTransform;
    }

    public static AffineTransform getRotateInstance(double d) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(d);
        return affineTransform;
    }

    public static AffineTransform getRotateInstance(double d, double d2, double d3) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(d, d2, d3);
        return affineTransform;
    }

    public static AffineTransform getRotateInstance(double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(d, d2);
        return affineTransform;
    }

    public static AffineTransform getRotateInstance(double d, double d2, double d3, double d4) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(d, d2, d3, d4);
        return affineTransform;
    }

    public static AffineTransform getQuadrantRotateInstance(int i) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToQuadrantRotation(i);
        return affineTransform;
    }

    public static AffineTransform getQuadrantRotateInstance(int i, double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToQuadrantRotation(i, d, d2);
        return affineTransform;
    }

    public static AffineTransform getScaleInstance(double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToScale(d, d2);
        return affineTransform;
    }

    public static AffineTransform getShearInstance(double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToShear(d, d2);
        return affineTransform;
    }

    public int getType() {
        if (this.type == -1) {
            calculateType();
        }
        return this.type;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        r13 = r0.m10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        if (r13 < 0.0d) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002d, code lost:
        if (r12 == r8) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
        if (r10 == (-r13)) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0034, code lost:
        r8 = r1 | 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003a, code lost:
        if (r10 == 1.0d) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003e, code lost:
        if (r10 == -1.0d) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0041, code lost:
        r8 = r1 | 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0047, code lost:
        if (r10 != r13) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0049, code lost:
        r8 = r1 | 74;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004d, code lost:
        r8 = r1 | 76;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0054, code lost:
        r10 = r0.m00;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0058, code lost:
        if (r10 < 0.0d) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005a, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005c, code lost:
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005d, code lost:
        r13 = r0.m11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0061, code lost:
        if (r13 < 0.0d) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0063, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0065, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0066, code lost:
        if (r12 != r8) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0068, code lost:
        if (r12 == false) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006c, code lost:
        if (r10 != r13) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006e, code lost:
        r8 = r1 | 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0072, code lost:
        r8 = r1 | 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0078, code lost:
        if (r10 == r13) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x007d, code lost:
        if (r10 == -1.0d) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x007f, code lost:
        r8 = r1 | 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0086, code lost:
        if (r10 != (-r13)) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x008a, code lost:
        if (r10 == 1.0d) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x008e, code lost:
        if (r10 != -1.0d) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0091, code lost:
        r8 = r1 | 66;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0095, code lost:
        r8 = r1 | 64;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0099, code lost:
        r8 = r1 | 68;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00a2, code lost:
        r1 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00a3, code lost:
        r2 = r0.m00;
        r10 = r0.m01;
        r14 = r0.m10;
        r8 = r0.m11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00b3, code lost:
        if (((r2 * r10) + (r14 * r8)) == 0.0d) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b5, code lost:
        r0.type = 32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b9, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00bc, code lost:
        if (r2 < 0.0d) goto L_0x00c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00be, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c0, code lost:
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00c3, code lost:
        if (r8 < 0.0d) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001b, code lost:
        r10 = r0.m01;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00c5, code lost:
        r6 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00c7, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00c8, code lost:
        if (r12 != r6) goto L_0x00e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00cc, code lost:
        if (r2 != r8) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00d1, code lost:
        if (r10 == (-r14)) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00db, code lost:
        if (((r2 * r8) - (r10 * r14)) == 1.0d) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x00dd, code lost:
        r8 = r1 | 18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001f, code lost:
        if (r10 < 0.0d) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x00e0, code lost:
        r8 = r1 | 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x00e3, code lost:
        r8 = r1 | 20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x00e9, code lost:
        if (r2 != (-r8)) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x00ed, code lost:
        if (r10 == r14) goto L_0x00f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x00f7, code lost:
        if (((r2 * r8) - (r10 * r14)) == 1.0d) goto L_0x00fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x00f9, code lost:
        r8 = r1 | 82;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x00fc, code lost:
        r8 = r1 | 80;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x00ff, code lost:
        r8 = r1 | 84;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0101, code lost:
        r0.type = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0103, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        r12 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void calculateType() {
        /*
            r18 = this;
            r0 = r18
            r18.updateState()
            int r1 = r0.state
            r2 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r4 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r6 = 0
            switch(r1) {
                case 0: goto L_0x00a0;
                case 1: goto L_0x009d;
                case 2: goto L_0x0053;
                case 3: goto L_0x0051;
                case 4: goto L_0x001a;
                case 5: goto L_0x0018;
                case 6: goto L_0x0015;
                case 7: goto L_0x00a2;
                default: goto L_0x0010;
            }
        L_0x0010:
            r18.stateError()
            goto L_0x00a2
        L_0x0015:
            r1 = 0
            goto L_0x00a3
        L_0x0018:
            r1 = 1
            goto L_0x001b
        L_0x001a:
            r1 = 0
        L_0x001b:
            double r10 = r0.m01
            int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r12 < 0) goto L_0x0023
            r12 = 1
            goto L_0x0024
        L_0x0023:
            r12 = 0
        L_0x0024:
            double r13 = r0.m10
            int r15 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r15 < 0) goto L_0x002c
            r8 = 1
            goto L_0x002d
        L_0x002c:
            r8 = 0
        L_0x002d:
            if (r12 == r8) goto L_0x0045
            double r6 = -r13
            int r8 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x0038
        L_0x0034:
            r8 = r1 | 12
            goto L_0x0101
        L_0x0038:
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x0041
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0041
            goto L_0x007f
        L_0x0041:
            r8 = r1 | 8
            goto L_0x0101
        L_0x0045:
            int r2 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            if (r2 != 0) goto L_0x004d
            r8 = r1 | 74
            goto L_0x0101
        L_0x004d:
            r8 = r1 | 76
            goto L_0x0101
        L_0x0051:
            r1 = 1
            goto L_0x0054
        L_0x0053:
            r1 = 0
        L_0x0054:
            double r10 = r0.m00
            int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r12 < 0) goto L_0x005c
            r12 = 1
            goto L_0x005d
        L_0x005c:
            r12 = 0
        L_0x005d:
            double r13 = r0.m11
            int r15 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r15 < 0) goto L_0x0065
            r8 = 1
            goto L_0x0066
        L_0x0065:
            r8 = 0
        L_0x0066:
            if (r12 != r8) goto L_0x0083
            if (r12 == 0) goto L_0x0076
            int r2 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            if (r2 != 0) goto L_0x0072
            r8 = r1 | 2
            goto L_0x0101
        L_0x0072:
            r8 = r1 | 4
            goto L_0x0101
        L_0x0076:
            int r4 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            if (r4 == 0) goto L_0x007b
            goto L_0x0034
        L_0x007b:
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0041
        L_0x007f:
            r8 = r1 | 10
            goto L_0x0101
        L_0x0083:
            double r6 = -r13
            int r8 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x0099
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x0095
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x0091
            goto L_0x0095
        L_0x0091:
            r8 = r1 | 66
            goto L_0x0101
        L_0x0095:
            r8 = r1 | 64
            goto L_0x0101
        L_0x0099:
            r8 = r1 | 68
            goto L_0x0101
        L_0x009d:
            r8 = 1
            goto L_0x0101
        L_0x00a0:
            r8 = 0
            goto L_0x0101
        L_0x00a2:
            r1 = 1
        L_0x00a3:
            double r2 = r0.m00
            double r10 = r0.m01
            double r12 = r2 * r10
            double r14 = r0.m10
            double r8 = r0.m11
            double r16 = r14 * r8
            double r12 = r12 + r16
            int r16 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r16 == 0) goto L_0x00ba
            r1 = 32
            r0.type = r1
            return
        L_0x00ba:
            int r12 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r12 < 0) goto L_0x00c0
            r12 = 1
            goto L_0x00c1
        L_0x00c0:
            r12 = 0
        L_0x00c1:
            int r13 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r13 < 0) goto L_0x00c7
            r6 = 1
            goto L_0x00c8
        L_0x00c7:
            r6 = 0
        L_0x00c8:
            if (r12 != r6) goto L_0x00e6
            int r6 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r6 != 0) goto L_0x00e3
            double r6 = -r14
            int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r12 == 0) goto L_0x00d4
            goto L_0x00e3
        L_0x00d4:
            double r2 = r2 * r8
            double r10 = r10 * r14
            double r2 = r2 - r10
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x00e0
            r8 = r1 | 18
            goto L_0x0101
        L_0x00e0:
            r8 = r1 | 16
            goto L_0x0101
        L_0x00e3:
            r8 = r1 | 20
            goto L_0x0101
        L_0x00e6:
            double r6 = -r8
            int r12 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r12 != 0) goto L_0x00ff
            int r6 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r6 == 0) goto L_0x00f0
            goto L_0x00ff
        L_0x00f0:
            double r2 = r2 * r8
            double r10 = r10 * r14
            double r2 = r2 - r10
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x00fc
            r8 = r1 | 82
            goto L_0x0101
        L_0x00fc:
            r8 = r1 | 80
            goto L_0x0101
        L_0x00ff:
            r8 = r1 | 84
        L_0x0101:
            r0.type = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.AffineTransform.calculateType():void");
    }

    public double getDeterminant() {
        switch (this.state) {
            case 0:
            case 1:
                return 1.0d;
            case 2:
            case 3:
                return this.m00 * this.m11;
            case 4:
            case 5:
                return -(this.m01 * this.m10);
            case 6:
            case 7:
                break;
            default:
                stateError();
                break;
        }
        return (this.m00 * this.m11) - (this.m01 * this.m10);
    }

    /* access modifiers changed from: package-private */
    public void updateState() {
        if (this.m01 == 0.0d && this.m10 == 0.0d) {
            if (this.m00 == 1.0d && this.m11 == 1.0d) {
                if (this.m02 == 0.0d && this.m12 == 0.0d) {
                    this.state = 0;
                    this.type = 0;
                    return;
                }
                this.state = 1;
                this.type = 1;
            } else if (this.m02 == 0.0d && this.m12 == 0.0d) {
                this.state = 2;
                this.type = -1;
            } else {
                this.state = 3;
                this.type = -1;
            }
        } else if (this.m00 == 0.0d && this.m11 == 0.0d) {
            if (this.m02 == 0.0d && this.m12 == 0.0d) {
                this.state = 4;
                this.type = -1;
                return;
            }
            this.state = 5;
            this.type = -1;
        } else if (this.m02 == 0.0d && this.m12 == 0.0d) {
            this.state = 6;
            this.type = -1;
        } else {
            this.state = 7;
            this.type = -1;
        }
    }

    private void stateError() {
        throw new InternalError("missing case in transform state switch");
    }

    public void getMatrix(double[] dArr) {
        dArr[0] = this.m00;
        dArr[1] = this.m10;
        dArr[2] = this.m01;
        dArr[3] = this.m11;
        if (dArr.length > 5) {
            dArr[4] = this.m02;
            dArr[5] = this.m12;
        }
    }

    public double getScaleX() {
        return this.m00;
    }

    public double getScaleY() {
        return this.m11;
    }

    public double getShearX() {
        return this.m01;
    }

    public double getShearY() {
        return this.m10;
    }

    public double getTranslateX() {
        return this.m02;
    }

    public double getTranslateY() {
        return this.m12;
    }

    public void translate(double d, double d2) {
        switch (this.state) {
            case 0:
                this.m02 = d;
                this.m12 = d2;
                if (d != 0.0d || d2 != 0.0d) {
                    this.state = 1;
                    this.type = 1;
                    return;
                }
                return;
            case 1:
                double d3 = d + this.m02;
                this.m02 = d3;
                double d4 = d2 + this.m12;
                this.m12 = d4;
                if (d3 == 0.0d && d4 == 0.0d) {
                    this.state = 0;
                    this.type = 0;
                    return;
                }
                return;
            case 2:
                double d5 = d * this.m00;
                this.m02 = d5;
                double d6 = d2 * this.m11;
                this.m12 = d6;
                if (d5 != 0.0d || d6 != 0.0d) {
                    this.state = 3;
                    this.type |= 1;
                    return;
                }
                return;
            case 3:
                double d7 = (d * this.m00) + this.m02;
                this.m02 = d7;
                double d8 = (d2 * this.m11) + this.m12;
                this.m12 = d8;
                if (d7 == 0.0d && d8 == 0.0d) {
                    this.state = 2;
                    int i = this.type;
                    if (i != -1) {
                        this.type = i - 1;
                        return;
                    }
                    return;
                }
                return;
            case 4:
                double d9 = d2 * this.m01;
                this.m02 = d9;
                double d10 = d * this.m10;
                this.m12 = d10;
                if (d9 != 0.0d || d10 != 0.0d) {
                    this.state = 5;
                    this.type |= 1;
                    return;
                }
                return;
            case 5:
                double d11 = (d2 * this.m01) + this.m02;
                this.m02 = d11;
                double d12 = (d * this.m10) + this.m12;
                this.m12 = d12;
                if (d11 == 0.0d && d12 == 0.0d) {
                    this.state = 4;
                    int i2 = this.type;
                    if (i2 != -1) {
                        this.type = i2 - 1;
                        return;
                    }
                    return;
                }
                return;
            case 6:
                double d13 = (this.m00 * d) + (this.m01 * d2);
                this.m02 = d13;
                double d14 = (d * this.m10) + (d2 * this.m11);
                this.m12 = d14;
                if (d13 != 0.0d || d14 != 0.0d) {
                    this.state = 7;
                    this.type |= 1;
                    return;
                }
                return;
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d15 = (this.m00 * d) + (this.m01 * d2) + this.m02;
        this.m02 = d15;
        double d16 = (d * this.m10) + (d2 * this.m11) + this.m12;
        this.m12 = d16;
        if (d15 == 0.0d && d16 == 0.0d) {
            this.state = 6;
            int i3 = this.type;
            if (i3 != -1) {
                this.type = i3 - 1;
            }
        }
    }

    private final void rotate90() {
        double d = this.m00;
        double d2 = this.m01;
        this.m00 = d2;
        this.m01 = -d;
        double d3 = this.m10;
        this.m10 = this.m11;
        double d4 = -d3;
        this.m11 = d4;
        int i = rot90conversion[this.state];
        if ((i & 6) == 2 && d2 == 1.0d && d4 == 1.0d) {
            i -= 2;
        }
        this.state = i;
        this.type = -1;
    }

    private final void rotate180() {
        double d = -this.m00;
        this.m00 = d;
        double d2 = -this.m11;
        this.m11 = d2;
        int i = this.state;
        if ((i & 4) != 0) {
            this.m01 = -this.m01;
            this.m10 = -this.m10;
        } else if (d == 1.0d && d2 == 1.0d) {
            this.state = i & -3;
        } else {
            this.state = i | 2;
        }
        this.type = -1;
    }

    private final void rotate270() {
        double d = this.m00;
        double d2 = -this.m01;
        this.m00 = d2;
        this.m01 = d;
        double d3 = this.m10;
        this.m10 = -this.m11;
        this.m11 = d3;
        int i = rot90conversion[this.state];
        if ((i & 6) == 2 && d2 == 1.0d && d3 == 1.0d) {
            i -= 2;
        }
        this.state = i;
        this.type = -1;
    }

    public void rotate(double d) {
        double sin = Math.sin(d);
        if (sin == 1.0d) {
            rotate90();
        } else if (sin == -1.0d) {
            rotate270();
        } else {
            double cos = Math.cos(d);
            if (cos == -1.0d) {
                rotate180();
            } else if (cos != 1.0d) {
                double d2 = this.m00;
                double d3 = this.m01;
                this.m00 = (cos * d2) + (sin * d3);
                double d4 = -sin;
                this.m01 = (d2 * d4) + (d3 * cos);
                double d5 = this.m10;
                double d6 = this.m11;
                this.m10 = (cos * d5) + (sin * d6);
                this.m11 = (d4 * d5) + (cos * d6);
                updateState();
            }
        }
    }

    public void rotate(double d, double d2, double d3) {
        translate(d2, d3);
        rotate(d);
        translate(-d2, -d3);
    }

    public void rotate(double d, double d2) {
        int i = (d2 > 0.0d ? 1 : (d2 == 0.0d ? 0 : -1));
        if (i == 0) {
            if (d < 0.0d) {
                rotate180();
            }
        } else if (d != 0.0d) {
            double sqrt = Math.sqrt((d * d) + (d2 * d2));
            double d3 = d2 / sqrt;
            double d4 = d / sqrt;
            double d5 = this.m00;
            double d6 = this.m01;
            this.m00 = (d4 * d5) + (d3 * d6);
            double d7 = -d3;
            this.m01 = (d5 * d7) + (d6 * d4);
            double d8 = this.m10;
            double d9 = this.m11;
            this.m10 = (d4 * d8) + (d3 * d9);
            this.m11 = (d7 * d8) + (d4 * d9);
            updateState();
        } else if (i > 0) {
            rotate90();
        } else {
            rotate270();
        }
    }

    public void rotate(double d, double d2, double d3, double d4) {
        translate(d3, d4);
        rotate(d, d2);
        translate(-d3, -d4);
    }

    public void quadrantRotate(int i) {
        int i2 = i & 3;
        if (i2 == 1) {
            rotate90();
        } else if (i2 == 2) {
            rotate180();
        } else if (i2 == 3) {
            rotate270();
        }
    }

    public void quadrantRotate(int i, double d, double d2) {
        int i2 = i & 3;
        if (i2 != 0) {
            if (i2 == 1) {
                double d3 = this.m02;
                double d4 = this.m00;
                double d5 = this.m01;
                this.m02 = d3 + ((d4 - d5) * d) + ((d5 + d4) * d2);
                double d6 = this.m12;
                double d7 = this.m10;
                double d8 = this.m11;
                this.m12 = d6 + (d * (d7 - d8)) + (d2 * (d8 + d7));
                rotate90();
            } else if (i2 == 2) {
                double d9 = this.m02;
                double d10 = this.m00;
                double d11 = this.m01;
                this.m02 = d9 + ((d10 + d10) * d) + ((d11 + d11) * d2);
                double d12 = this.m12;
                double d13 = this.m10;
                double d14 = d * (d13 + d13);
                double d15 = this.m11;
                this.m12 = d12 + d14 + (d2 * (d15 + d15));
                rotate180();
            } else if (i2 == 3) {
                double d16 = this.m02;
                double d17 = this.m00;
                double d18 = this.m01;
                this.m02 = d16 + ((d17 + d18) * d) + ((d18 - d17) * d2);
                double d19 = this.m12;
                double d20 = this.m10;
                double d21 = this.m11;
                this.m12 = d19 + (d * (d20 + d21)) + (d2 * (d21 - d20));
                rotate270();
            }
            if (this.m02 == 0.0d && this.m12 == 0.0d) {
                this.state &= -2;
            } else {
                this.state |= 1;
            }
        }
    }

    public void scale(double d, double d2) {
        int i = this.state;
        int i2 = 0;
        switch (i) {
            case 0:
            case 1:
                this.m00 = d;
                this.m11 = d2;
                if (d != 1.0d || d2 != 1.0d) {
                    this.state = i | 2;
                    this.type = -1;
                    return;
                }
                return;
            case 2:
            case 3:
                double d3 = this.m00 * d;
                this.m00 = d3;
                double d4 = this.m11 * d2;
                this.m11 = d4;
                if (d3 == 1.0d && d4 == 1.0d) {
                    int i3 = i & 1;
                    this.state = i3;
                    if (i3 != 0) {
                        i2 = 1;
                    }
                    this.type = i2;
                    return;
                }
                this.type = -1;
                return;
            case 4:
            case 5:
                break;
            case 6:
            case 7:
                break;
            default:
                stateError();
                break;
        }
        this.m00 *= d;
        this.m11 *= d2;
        double d5 = this.m01 * d2;
        this.m01 = d5;
        double d6 = this.m10 * d;
        this.m10 = d6;
        if (d5 == 0.0d && d6 == 0.0d) {
            int i4 = i & 1;
            if (this.m00 == 1.0d && this.m11 == 1.0d) {
                if (i4 != 0) {
                    i2 = 1;
                }
                this.type = i2;
            } else {
                i4 |= 2;
                this.type = -1;
            }
            this.state = i4;
        }
    }

    public void shear(double d, double d2) {
        int i = this.state;
        switch (i) {
            case 0:
            case 1:
                this.m01 = d;
                this.m10 = d2;
                if (d != 0.0d || d2 != 0.0d) {
                    this.state = i | 2 | 4;
                    this.type = -1;
                    return;
                }
                return;
            case 2:
            case 3:
                double d3 = this.m00 * d;
                this.m01 = d3;
                double d4 = this.m11 * d2;
                this.m10 = d4;
                if (!(d3 == 0.0d && d4 == 0.0d)) {
                    this.state = i | 4;
                }
                this.type = -1;
                return;
            case 4:
            case 5:
                double d5 = this.m01 * d2;
                this.m00 = d5;
                double d6 = this.m10 * d;
                this.m11 = d6;
                if (!(d5 == 0.0d && d6 == 0.0d)) {
                    this.state = i | 2;
                }
                this.type = -1;
                return;
            case 6:
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d7 = this.m00;
        double d8 = this.m01;
        this.m00 = (d8 * d2) + d7;
        this.m01 = (d7 * d) + d8;
        double d9 = this.m10;
        double d10 = this.m11;
        this.m10 = (d2 * d10) + d9;
        this.m11 = (d9 * d) + d10;
        updateState();
    }

    public void setToIdentity() {
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = 0.0d;
        this.m10 = 0.0d;
        this.state = 0;
        this.type = 0;
    }

    public void setToTranslation(double d, double d2) {
        this.m00 = 1.0d;
        this.m10 = 0.0d;
        this.m01 = 0.0d;
        this.m11 = 1.0d;
        this.m02 = d;
        this.m12 = d2;
        if (d == 0.0d && d2 == 0.0d) {
            this.state = 0;
            this.type = 0;
            return;
        }
        this.state = 1;
        this.type = 1;
    }

    public void setToRotation(double d) {
        double d2;
        double sin = Math.sin(d);
        if (sin == 1.0d || sin == -1.0d) {
            this.state = 4;
            this.type = 8;
            d2 = 0.0d;
        } else {
            d2 = Math.cos(d);
            if (d2 == -1.0d) {
                this.state = 2;
                this.type = 8;
            } else if (d2 == 1.0d) {
                this.state = 0;
                this.type = 0;
            } else {
                this.state = 6;
                this.type = 16;
            }
            sin = 0.0d;
        }
        this.m00 = d2;
        this.m10 = sin;
        this.m01 = -sin;
        this.m11 = d2;
        this.m02 = 0.0d;
        this.m12 = 0.0d;
    }

    public void setToRotation(double d, double d2, double d3) {
        setToRotation(d);
        double d4 = this.m10;
        double d5 = 1.0d - this.m00;
        double d6 = (d2 * d5) + (d3 * d4);
        this.m02 = d6;
        double d7 = (d3 * d5) - (d2 * d4);
        this.m12 = d7;
        if (d6 != 0.0d || d7 != 0.0d) {
            this.state |= 1;
            this.type |= 1;
        }
    }

    public void setToRotation(double d, double d2) {
        double d3;
        double d4 = -1.0d;
        int i = (d2 > 0.0d ? 1 : (d2 == 0.0d ? 0 : -1));
        if (i == 0) {
            if (d < 0.0d) {
                this.state = 2;
                this.type = 8;
            } else {
                this.state = 0;
                this.type = 0;
                d4 = 1.0d;
            }
            d3 = 0.0d;
        } else if (d == 0.0d) {
            if (i > 0) {
                d4 = 1.0d;
            }
            this.state = 4;
            this.type = 8;
            d3 = d4;
            d4 = 0.0d;
        } else {
            double sqrt = Math.sqrt((d * d) + (d2 * d2));
            this.state = 6;
            this.type = 16;
            double d5 = d / sqrt;
            d3 = d2 / sqrt;
            d4 = d5;
        }
        this.m00 = d4;
        this.m10 = d3;
        this.m01 = -d3;
        this.m11 = d4;
        this.m02 = 0.0d;
        this.m12 = 0.0d;
    }

    public void setToRotation(double d, double d2, double d3, double d4) {
        setToRotation(d, d2);
        double d5 = this.m10;
        double d6 = 1.0d - this.m00;
        double d7 = (d3 * d6) + (d4 * d5);
        this.m02 = d7;
        double d8 = (d4 * d6) - (d3 * d5);
        this.m12 = d8;
        if (d7 != 0.0d || d8 != 0.0d) {
            this.state |= 1;
            this.type |= 1;
        }
    }

    public void setToQuadrantRotation(int i) {
        int i2 = i & 3;
        if (i2 == 0) {
            this.m00 = 1.0d;
            this.m10 = 0.0d;
            this.m01 = 0.0d;
            this.m11 = 1.0d;
            this.m02 = 0.0d;
            this.m12 = 0.0d;
            this.state = 0;
            this.type = 0;
        } else if (i2 == 1) {
            this.m00 = 0.0d;
            this.m10 = 1.0d;
            this.m01 = -1.0d;
            this.m11 = 0.0d;
            this.m02 = 0.0d;
            this.m12 = 0.0d;
            this.state = 4;
            this.type = 8;
        } else if (i2 == 2) {
            this.m00 = -1.0d;
            this.m10 = 0.0d;
            this.m01 = 0.0d;
            this.m11 = -1.0d;
            this.m02 = 0.0d;
            this.m12 = 0.0d;
            this.state = 2;
            this.type = 8;
        } else if (i2 == 3) {
            this.m00 = 0.0d;
            this.m10 = -1.0d;
            this.m01 = 1.0d;
            this.m11 = 0.0d;
            this.m02 = 0.0d;
            this.m12 = 0.0d;
            this.state = 4;
            this.type = 8;
        }
    }

    public void setToQuadrantRotation(int i, double d, double d2) {
        int i2 = i & 3;
        if (i2 == 0) {
            this.m00 = 1.0d;
            this.m10 = 0.0d;
            this.m01 = 0.0d;
            this.m11 = 1.0d;
            this.m02 = 0.0d;
            this.m12 = 0.0d;
            this.state = 0;
            this.type = 0;
        } else if (i2 == 1) {
            this.m00 = 0.0d;
            this.m10 = 1.0d;
            this.m01 = -1.0d;
            this.m11 = 0.0d;
            double d3 = d + d2;
            this.m02 = d3;
            double d4 = d2 - d;
            this.m12 = d4;
            if (d3 == 0.0d && d4 == 0.0d) {
                this.state = 4;
                this.type = 8;
                return;
            }
            this.state = 5;
            this.type = 9;
        } else if (i2 == 2) {
            this.m00 = -1.0d;
            this.m10 = 0.0d;
            this.m01 = 0.0d;
            this.m11 = -1.0d;
            double d5 = d + d;
            this.m02 = d5;
            double d6 = d2 + d2;
            this.m12 = d6;
            if (d5 == 0.0d && d6 == 0.0d) {
                this.state = 2;
                this.type = 8;
                return;
            }
            this.state = 3;
            this.type = 9;
        } else if (i2 == 3) {
            this.m00 = 0.0d;
            this.m10 = -1.0d;
            this.m01 = 1.0d;
            this.m11 = 0.0d;
            double d7 = d - d2;
            this.m02 = d7;
            double d8 = d2 + d;
            this.m12 = d8;
            if (d7 == 0.0d && d8 == 0.0d) {
                this.state = 4;
                this.type = 8;
                return;
            }
            this.state = 5;
            this.type = 9;
        }
    }

    public void setToScale(double d, double d2) {
        this.m00 = d;
        this.m10 = 0.0d;
        this.m01 = 0.0d;
        this.m11 = d2;
        this.m02 = 0.0d;
        this.m12 = 0.0d;
        if (d == 1.0d && d2 == 1.0d) {
            this.state = 0;
            this.type = 0;
            return;
        }
        this.state = 2;
        this.type = -1;
    }

    public void setToShear(double d, double d2) {
        this.m00 = 1.0d;
        this.m01 = d;
        this.m10 = d2;
        this.m11 = 1.0d;
        this.m02 = 0.0d;
        this.m12 = 0.0d;
        if (d == 0.0d && d2 == 0.0d) {
            this.state = 0;
            this.type = 0;
            return;
        }
        this.state = 6;
        this.type = -1;
    }

    public void setTransform(AffineTransform affineTransform) {
        this.m00 = affineTransform.m00;
        this.m10 = affineTransform.m10;
        this.m01 = affineTransform.m01;
        this.m11 = affineTransform.m11;
        this.m02 = affineTransform.m02;
        this.m12 = affineTransform.m12;
        this.state = affineTransform.state;
        this.type = affineTransform.type;
    }

    public void setTransform(double d, double d2, double d3, double d4, double d5, double d6) {
        this.m00 = d;
        this.m10 = d2;
        this.m01 = d3;
        this.m11 = d4;
        this.m02 = d5;
        this.m12 = d6;
        updateState();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0075, code lost:
        updateState();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0078, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0095, code lost:
        r0.state = r2 | r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0099, code lost:
        r1 = r0.m00;
        r18 = r5;
        r4 = r0.m01;
        r0.m00 = (r16 * r1) + (r12 * r4);
        r0.m01 = (r8 * r1) + (r14 * r4);
        r0.m02 += (r1 * r10) + (r18 * r4);
        r1 = r0.m10;
        r3 = r0.m11;
        r0.m10 = (r16 * r1) + (r12 * r3);
        r0.m11 = (r8 * r1) + (r14 * r3);
        r0.m12 += (r10 * r1) + (r18 * r3);
        r0.type = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00d8, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void concatenate(com.app.office.java.awt.geom.AffineTransform r23) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            int r2 = r0.state
            int r3 = r1.state
            int r4 = r3 << 3
            r4 = r4 | r2
            r5 = 48
            if (r4 == r5) goto L_0x018f
            r5 = 56
            if (r4 == r5) goto L_0x0170
            switch(r4) {
                case 0: goto L_0x016f;
                case 1: goto L_0x016f;
                case 2: goto L_0x016f;
                case 3: goto L_0x016f;
                case 4: goto L_0x016f;
                case 5: goto L_0x016f;
                case 6: goto L_0x016f;
                case 7: goto L_0x016f;
                case 8: goto L_0x0180;
                case 9: goto L_0x0168;
                case 10: goto L_0x0168;
                case 11: goto L_0x0168;
                case 12: goto L_0x0168;
                case 13: goto L_0x0168;
                case 14: goto L_0x0168;
                case 15: goto L_0x0168;
                case 16: goto L_0x0197;
                case 17: goto L_0x0160;
                case 18: goto L_0x0160;
                case 19: goto L_0x0160;
                case 20: goto L_0x0160;
                case 21: goto L_0x0160;
                case 22: goto L_0x0160;
                case 23: goto L_0x0160;
                case 24: goto L_0x0178;
                default: goto L_0x0016;
            }
        L_0x0016:
            r6 = 0
            switch(r4) {
                case 32: goto L_0x014d;
                case 33: goto L_0x013a;
                case 34: goto L_0x011e;
                case 35: goto L_0x011e;
                case 36: goto L_0x0102;
                case 37: goto L_0x0102;
                case 38: goto L_0x00e2;
                case 39: goto L_0x00e2;
                case 40: goto L_0x00d9;
                default: goto L_0x001b;
            }
        L_0x001b:
            double r6 = r1.m00
            double r8 = r1.m01
            double r10 = r1.m02
            double r12 = r1.m10
            double r14 = r1.m11
            r16 = r6
            double r5 = r1.m12
            switch(r2) {
                case 1: goto L_0x0079;
                case 2: goto L_0x0053;
                case 3: goto L_0x0053;
                case 4: goto L_0x0030;
                case 5: goto L_0x0030;
                case 6: goto L_0x0095;
                case 7: goto L_0x0099;
                default: goto L_0x002c;
            }
        L_0x002c:
            r22.stateError()
            goto L_0x0095
        L_0x0030:
            double r1 = r0.m01
            double r12 = r12 * r1
            r0.m00 = r12
            double r14 = r14 * r1
            r0.m01 = r14
            double r3 = r0.m02
            double r5 = r5 * r1
            double r3 = r3 + r5
            r0.m02 = r3
            double r1 = r0.m10
            double r6 = r16 * r1
            r0.m10 = r6
            double r8 = r8 * r1
            r0.m11 = r8
            double r3 = r0.m12
            double r10 = r10 * r1
            double r3 = r3 + r10
            r0.m12 = r3
            goto L_0x0075
        L_0x0053:
            double r1 = r0.m00
            double r3 = r16 * r1
            r0.m00 = r3
            double r8 = r8 * r1
            r0.m01 = r8
            double r3 = r0.m02
            double r10 = r10 * r1
            double r3 = r3 + r10
            r0.m02 = r3
            double r1 = r0.m11
            double r12 = r12 * r1
            r0.m10 = r12
            double r14 = r14 * r1
            r0.m11 = r14
            double r3 = r0.m12
            double r5 = r5 * r1
            double r3 = r3 + r5
            r0.m12 = r3
        L_0x0075:
            r22.updateState()
            return
        L_0x0079:
            r1 = r16
            r0.m00 = r1
            r0.m01 = r8
            double r1 = r0.m02
            double r1 = r1 + r10
            r0.m02 = r1
            r0.m10 = r12
            r0.m11 = r14
            double r1 = r0.m12
            double r1 = r1 + r5
            r0.m12 = r1
            r1 = r3 | 1
            r0.state = r1
            r1 = -1
            r0.type = r1
            return
        L_0x0095:
            r1 = r2 | r3
            r0.state = r1
        L_0x0099:
            double r1 = r0.m00
            r18 = r5
            double r4 = r0.m01
            double r6 = r16 * r1
            double r20 = r12 * r4
            double r6 = r6 + r20
            r0.m00 = r6
            double r6 = r8 * r1
            double r20 = r14 * r4
            double r6 = r6 + r20
            r0.m01 = r6
            double r6 = r0.m02
            double r1 = r1 * r10
            double r3 = r18 * r4
            double r1 = r1 + r3
            double r6 = r6 + r1
            r0.m02 = r6
            double r1 = r0.m10
            double r3 = r0.m11
            double r6 = r16 * r1
            double r12 = r12 * r3
            double r6 = r6 + r12
            r0.m10 = r6
            double r8 = r8 * r1
            double r14 = r14 * r3
            double r8 = r8 + r14
            r0.m11 = r8
            double r5 = r0.m12
            double r10 = r10 * r1
            double r1 = r18 * r3
            double r10 = r10 + r1
            double r5 = r5 + r10
            r0.m12 = r5
            r1 = -1
            r0.type = r1
            return
        L_0x00d9:
            double r4 = r1.m02
            r0.m02 = r4
            double r4 = r1.m12
            r0.m12 = r4
            goto L_0x014d
        L_0x00e2:
            double r2 = r1.m01
            double r5 = r1.m10
            double r7 = r0.m00
            double r9 = r0.m01
            double r9 = r9 * r5
            r0.m00 = r9
            double r7 = r7 * r2
            r0.m01 = r7
            double r7 = r0.m10
            double r9 = r0.m11
            double r9 = r9 * r5
            r0.m10 = r9
            double r7 = r7 * r2
            r0.m11 = r7
            r1 = -1
            r0.type = r1
            return
        L_0x0102:
            double r8 = r0.m01
            double r10 = r1.m10
            double r8 = r8 * r10
            r0.m00 = r8
            r0.m01 = r6
            double r8 = r0.m10
            double r10 = r1.m01
            double r8 = r8 * r10
            r0.m11 = r8
            r0.m10 = r6
            r1 = r2 ^ 6
            r0.state = r1
            r1 = -1
            r0.type = r1
            return
        L_0x011e:
            double r8 = r0.m00
            double r10 = r1.m01
            double r8 = r8 * r10
            r0.m01 = r8
            r0.m00 = r6
            double r8 = r0.m11
            double r10 = r1.m10
            double r8 = r8 * r10
            r0.m10 = r8
            r0.m11 = r6
            r1 = r2 ^ 6
            r0.state = r1
            r2 = -1
            r0.type = r2
            return
        L_0x013a:
            r2 = -1
            r0.m00 = r6
            double r3 = r1.m01
            r0.m01 = r3
            double r3 = r1.m10
            r0.m10 = r3
            r0.m11 = r6
            r1 = 5
            r0.state = r1
            r0.type = r2
            return
        L_0x014d:
            double r4 = r1.m01
            r0.m01 = r4
            double r4 = r1.m10
            r0.m10 = r4
            r0.m11 = r6
            r0.m00 = r6
            r0.state = r3
            int r1 = r1.type
            r0.type = r1
            return
        L_0x0160:
            double r2 = r1.m00
            double r4 = r1.m11
            r0.scale(r2, r4)
            return
        L_0x0168:
            double r2 = r1.m02
            double r4 = r1.m12
            r0.translate(r2, r4)
        L_0x016f:
            return
        L_0x0170:
            double r4 = r1.m01
            r0.m01 = r4
            double r4 = r1.m10
            r0.m10 = r4
        L_0x0178:
            double r4 = r1.m00
            r0.m00 = r4
            double r4 = r1.m11
            r0.m11 = r4
        L_0x0180:
            double r4 = r1.m02
            r0.m02 = r4
            double r4 = r1.m12
            r0.m12 = r4
            r0.state = r3
            int r1 = r1.type
            r0.type = r1
            return
        L_0x018f:
            double r4 = r1.m01
            r0.m01 = r4
            double r4 = r1.m10
            r0.m10 = r4
        L_0x0197:
            double r4 = r1.m00
            r0.m00 = r4
            double r4 = r1.m11
            r0.m11 = r4
            r0.state = r3
            int r1 = r1.type
            r0.type = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.AffineTransform.concatenate(com.app.office.java.awt.geom.AffineTransform):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0074, code lost:
        r0.m02 = r10;
        r0.m12 = r5;
        r1 = r0.m00;
        r0.m00 = r1 * r16;
        r0.m10 = r1 * r12;
        r1 = r0.m11;
        r0.m01 = r8 * r1;
        r0.m11 = r1 * r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00a5, code lost:
        r0.m02 = r10;
        r0.m12 = r5;
        r0.m00 = r16;
        r0.m10 = r12;
        r0.m01 = r8;
        r0.m11 = r14;
        r0.state = r2 | r3;
        r0.type = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00ba, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00bb, code lost:
        r1 = r0.m02;
        r6 = r0.m12;
        r10 = r10 + ((r1 * r4) + (r6 * r8));
        r1 = r18 + ((r1 * r12) + (r6 * r14));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00ce, code lost:
        r0.m02 = r10;
        r0.m12 = r1;
        r1 = r0.m00;
        r6 = r0.m10;
        r0.m00 = (r1 * r4) + (r6 * r8);
        r0.m10 = (r1 * r12) + (r6 * r14);
        r1 = r0.m01;
        r6 = r0.m11;
        r0.m01 = (r1 * r4) + (r8 * r6);
        r0.m11 = (r1 * r12) + (r6 * r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00f7, code lost:
        updateState();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00fa, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0048, code lost:
        r0.m02 = r10;
        r0.m12 = r5;
        r1 = r0.m10;
        r0.m00 = r8 * r1;
        r0.m10 = r1 * r14;
        r1 = r0.m01;
        r0.m01 = r1 * r16;
        r0.m11 = r1 * r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void preConcatenate(com.app.office.java.awt.geom.AffineTransform r25) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            int r2 = r0.state
            int r3 = r1.state
            int r4 = r3 << 3
            r4 = r4 | r2
            switch(r4) {
                case 0: goto L_0x0197;
                case 1: goto L_0x0197;
                case 2: goto L_0x0197;
                case 3: goto L_0x0197;
                case 4: goto L_0x0197;
                case 5: goto L_0x0197;
                case 6: goto L_0x0197;
                case 7: goto L_0x0197;
                case 8: goto L_0x0185;
                case 9: goto L_0x0176;
                case 10: goto L_0x0185;
                case 11: goto L_0x0176;
                case 12: goto L_0x0185;
                case 13: goto L_0x0176;
                case 14: goto L_0x0185;
                case 15: goto L_0x0176;
                case 16: goto L_0x012d;
                case 17: goto L_0x012d;
                case 18: goto L_0x0131;
                case 19: goto L_0x0131;
                case 20: goto L_0x0131;
                case 21: goto L_0x0131;
                case 22: goto L_0x0131;
                case 23: goto L_0x0131;
                default: goto L_0x000e;
            }
        L_0x000e:
            switch(r4) {
                case 32: goto L_0x00fd;
                case 33: goto L_0x00fd;
                case 34: goto L_0x00fd;
                case 35: goto L_0x00fd;
                case 36: goto L_0x00fb;
                case 37: goto L_0x00fb;
                case 38: goto L_0x0101;
                case 39: goto L_0x0101;
                default: goto L_0x0011;
            }
        L_0x0011:
            double r6 = r1.m00
            double r8 = r1.m01
            double r10 = r1.m02
            double r12 = r1.m10
            double r14 = r1.m11
            r16 = r6
            double r5 = r1.m12
            switch(r2) {
                case 0: goto L_0x00a3;
                case 1: goto L_0x008d;
                case 2: goto L_0x0074;
                case 3: goto L_0x0062;
                case 4: goto L_0x0048;
                case 5: goto L_0x0036;
                case 6: goto L_0x0031;
                case 7: goto L_0x002b;
                default: goto L_0x0022;
            }
        L_0x0022:
            r18 = r5
            r4 = r16
            r24.stateError()
            goto L_0x00bb
        L_0x002b:
            r18 = r5
            r4 = r16
            goto L_0x00bb
        L_0x0031:
            r1 = r5
            r4 = r16
            goto L_0x00ce
        L_0x0036:
            double r1 = r0.m02
            double r3 = r0.m12
            double r18 = r1 * r16
            double r20 = r3 * r8
            double r18 = r18 + r20
            double r10 = r10 + r18
            double r1 = r1 * r12
            double r3 = r3 * r14
            double r1 = r1 + r3
            double r5 = r5 + r1
        L_0x0048:
            r0.m02 = r10
            r0.m12 = r5
            double r1 = r0.m10
            double r8 = r8 * r1
            r0.m00 = r8
            double r1 = r1 * r14
            r0.m10 = r1
            double r1 = r0.m01
            double r6 = r1 * r16
            r0.m01 = r6
            double r1 = r1 * r12
            r0.m11 = r1
            goto L_0x00f7
        L_0x0062:
            double r1 = r0.m02
            double r3 = r0.m12
            double r18 = r1 * r16
            double r20 = r3 * r8
            double r18 = r18 + r20
            double r10 = r10 + r18
            double r1 = r1 * r12
            double r3 = r3 * r14
            double r1 = r1 + r3
            double r5 = r5 + r1
        L_0x0074:
            r0.m02 = r10
            r0.m12 = r5
            double r1 = r0.m00
            double r6 = r1 * r16
            r0.m00 = r6
            double r1 = r1 * r12
            r0.m10 = r1
            double r1 = r0.m11
            double r8 = r8 * r1
            r0.m01 = r8
            double r1 = r1 * r14
            r0.m11 = r1
            goto L_0x00f7
        L_0x008d:
            r18 = r5
            double r4 = r0.m02
            double r6 = r0.m12
            double r20 = r4 * r16
            double r22 = r6 * r8
            double r20 = r20 + r22
            double r10 = r10 + r20
            double r4 = r4 * r12
            double r6 = r6 * r14
            double r4 = r4 + r6
            double r5 = r18 + r4
            goto L_0x00a5
        L_0x00a3:
            r18 = r5
        L_0x00a5:
            r0.m02 = r10
            r0.m12 = r5
            r4 = r16
            r0.m00 = r4
            r0.m10 = r12
            r0.m01 = r8
            r0.m11 = r14
            r1 = r2 | r3
            r0.state = r1
            r1 = -1
            r0.type = r1
            return
        L_0x00bb:
            double r1 = r0.m02
            double r6 = r0.m12
            double r16 = r1 * r4
            double r20 = r6 * r8
            double r16 = r16 + r20
            double r10 = r10 + r16
            double r1 = r1 * r12
            double r6 = r6 * r14
            double r1 = r1 + r6
            double r1 = r18 + r1
        L_0x00ce:
            r0.m02 = r10
            r0.m12 = r1
            double r1 = r0.m00
            double r6 = r0.m10
            double r10 = r1 * r4
            double r16 = r6 * r8
            double r10 = r10 + r16
            r0.m00 = r10
            double r1 = r1 * r12
            double r6 = r6 * r14
            double r1 = r1 + r6
            r0.m10 = r1
            double r1 = r0.m01
            double r6 = r0.m11
            double r3 = r1 * r4
            double r8 = r8 * r6
            double r3 = r3 + r8
            r0.m01 = r3
            double r1 = r1 * r12
            double r6 = r6 * r14
            double r1 = r1 + r6
            r0.m11 = r1
        L_0x00f7:
            r24.updateState()
            return
        L_0x00fb:
            r2 = r2 | 2
        L_0x00fd:
            r2 = r2 ^ 4
            r0.state = r2
        L_0x0101:
            double r2 = r1.m01
            double r5 = r1.m10
            double r7 = r0.m00
            double r9 = r0.m10
            double r9 = r9 * r2
            r0.m00 = r9
            double r7 = r7 * r5
            r0.m10 = r7
            double r7 = r0.m01
            double r9 = r0.m11
            double r9 = r9 * r2
            r0.m01 = r9
            double r7 = r7 * r5
            r0.m11 = r7
            double r7 = r0.m02
            double r9 = r0.m12
            double r9 = r9 * r2
            r0.m02 = r9
            double r7 = r7 * r5
            r0.m12 = r7
            r1 = -1
            r0.type = r1
            return
        L_0x012d:
            r3 = r2 | 2
            r0.state = r3
        L_0x0131:
            double r5 = r1.m00
            double r7 = r1.m11
            r1 = r2 & 4
            if (r1 == 0) goto L_0x0156
            double r9 = r0.m01
            double r9 = r9 * r5
            r0.m01 = r9
            double r9 = r0.m10
            double r9 = r9 * r7
            r0.m10 = r9
            r1 = r2 & 2
            if (r1 == 0) goto L_0x0162
            double r9 = r0.m00
            double r9 = r9 * r5
            r0.m00 = r9
            double r9 = r0.m11
            double r9 = r9 * r7
            r0.m11 = r9
            goto L_0x0162
        L_0x0156:
            double r9 = r0.m00
            double r9 = r9 * r5
            r0.m00 = r9
            double r9 = r0.m11
            double r9 = r9 * r7
            r0.m11 = r9
        L_0x0162:
            r1 = r2 & 1
            if (r1 == 0) goto L_0x0172
            double r1 = r0.m02
            double r1 = r1 * r5
            r0.m02 = r1
            double r1 = r0.m12
            double r1 = r1 * r7
            r0.m12 = r1
        L_0x0172:
            r1 = -1
            r0.type = r1
            return
        L_0x0176:
            double r2 = r0.m02
            double r4 = r1.m02
            double r2 = r2 + r4
            r0.m02 = r2
            double r2 = r0.m12
            double r4 = r1.m12
            double r2 = r2 + r4
            r0.m12 = r2
            return
        L_0x0185:
            double r3 = r1.m02
            r0.m02 = r3
            double r3 = r1.m12
            r0.m12 = r3
            r1 = r2 | 1
            r0.state = r1
            int r1 = r0.type
            r1 = r1 | 1
            r0.type = r1
        L_0x0197:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.AffineTransform.preConcatenate(com.app.office.java.awt.geom.AffineTransform):void");
    }

    public AffineTransform createInverse() throws NoninvertibleTransformException {
        switch (this.state) {
            case 0:
                return new AffineTransform();
            case 1:
                return new AffineTransform(1.0d, 0.0d, 0.0d, 1.0d, -this.m02, -this.m12, 1);
            case 2:
                if (this.m00 != 0.0d && this.m11 != 0.0d) {
                    return new AffineTransform(1.0d / this.m00, 0.0d, 0.0d, 1.0d / this.m11, 0.0d, 0.0d, 2);
                }
                throw new NoninvertibleTransformException("Determinant is 0");
            case 3:
                if (this.m00 == 0.0d || this.m11 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                double d = this.m00;
                double d2 = this.m11;
                return new AffineTransform(1.0d / d, 0.0d, 0.0d, 1.0d / d2, (-this.m02) / d, (-this.m12) / d2, 3);
            case 4:
                if (this.m01 != 0.0d && this.m10 != 0.0d) {
                    return new AffineTransform(0.0d, 1.0d / this.m01, 1.0d / this.m10, 0.0d, 0.0d, 0.0d, 4);
                }
                throw new NoninvertibleTransformException("Determinant is 0");
            case 5:
                if (this.m01 == 0.0d || this.m10 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                double d3 = this.m01;
                double d4 = this.m10;
                return new AffineTransform(0.0d, 1.0d / d3, 1.0d / d4, 0.0d, (-this.m12) / d4, (-this.m02) / d3, 5);
            case 6:
                double d5 = (this.m00 * this.m11) - (this.m01 * this.m10);
                if (Math.abs(d5) > Double.MIN_VALUE) {
                    return new AffineTransform(this.m11 / d5, (-this.m10) / d5, (-this.m01) / d5, this.m00 / d5, 0.0d, 0.0d, 6);
                }
                throw new NoninvertibleTransformException("Determinant is " + d5);
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d6 = (this.m00 * this.m11) - (this.m01 * this.m10);
        if (Math.abs(d6) > Double.MIN_VALUE) {
            double d7 = this.m11;
            double d8 = this.m10;
            double d9 = this.m01;
            double d10 = (-d8) / d6;
            double d11 = (-d9) / d6;
            double d12 = this.m00;
            double d13 = d12 / d6;
            double d14 = this.m12;
            double d15 = d9 * d14;
            double d16 = d14;
            double d17 = this.m02;
            return new AffineTransform(d7 / d6, d10, d11, d13, (d15 - (d7 * d17)) / d6, ((d8 * d17) - (d12 * d16)) / d6, 7);
        }
        throw new NoninvertibleTransformException("Determinant is " + d6);
    }

    public void invert() throws NoninvertibleTransformException {
        switch (this.state) {
            case 0:
                return;
            case 1:
                this.m02 = -this.m02;
                this.m12 = -this.m12;
                return;
            case 2:
                double d = this.m00;
                double d2 = this.m11;
                if (d == 0.0d || d2 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                this.m00 = 1.0d / d;
                this.m11 = 1.0d / d2;
                return;
            case 3:
                double d3 = this.m00;
                double d4 = this.m02;
                double d5 = this.m11;
                double d6 = this.m12;
                if (d3 == 0.0d || d5 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                this.m00 = 1.0d / d3;
                this.m11 = 1.0d / d5;
                this.m02 = (-d4) / d3;
                this.m12 = (-d6) / d5;
                return;
            case 4:
                double d7 = this.m01;
                double d8 = this.m10;
                if (d7 == 0.0d || d8 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                this.m10 = 1.0d / d7;
                this.m01 = 1.0d / d8;
                return;
            case 5:
                double d9 = this.m01;
                double d10 = this.m02;
                double d11 = this.m10;
                double d12 = this.m12;
                if (d9 == 0.0d || d11 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                this.m10 = 1.0d / d9;
                this.m01 = 1.0d / d11;
                this.m02 = (-d12) / d11;
                this.m12 = (-d10) / d9;
                return;
            case 6:
                double d13 = this.m00;
                double d14 = this.m01;
                double d15 = this.m10;
                double d16 = this.m11;
                double d17 = (d13 * d16) - (d14 * d15);
                if (Math.abs(d17) > Double.MIN_VALUE) {
                    this.m00 = d16 / d17;
                    this.m10 = (-d15) / d17;
                    this.m01 = (-d14) / d17;
                    this.m11 = d13 / d17;
                    return;
                }
                throw new NoninvertibleTransformException("Determinant is " + d17);
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d18 = this.m00;
        double d19 = this.m01;
        double d20 = this.m02;
        double d21 = this.m10;
        double d22 = this.m11;
        double d23 = this.m12;
        String str = "Determinant is ";
        double d24 = (d18 * d22) - (d19 * d21);
        if (Math.abs(d24) > Double.MIN_VALUE) {
            double d25 = d20;
            this.m00 = d22 / d24;
            this.m10 = (-d21) / d24;
            this.m01 = (-d19) / d24;
            this.m11 = d18 / d24;
            this.m02 = ((d19 * d23) - (d22 * d25)) / d24;
            this.m12 = ((d21 * d25) - (d18 * d23)) / d24;
            return;
        }
        throw new NoninvertibleTransformException(str + d24);
    }

    public Point2D transform(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            if (point2D instanceof Point2D.Double) {
                point2D2 = new Point2D.Double();
            } else {
                point2D2 = new Point2D.Float();
            }
        }
        double x = point2D.getX();
        double y = point2D.getY();
        switch (this.state) {
            case 0:
                point2D2.setLocation(x, y);
                return point2D2;
            case 1:
                point2D2.setLocation(x + this.m02, y + this.m12);
                return point2D2;
            case 2:
                point2D2.setLocation(x * this.m00, y * this.m11);
                return point2D2;
            case 3:
                point2D2.setLocation((x * this.m00) + this.m02, (y * this.m11) + this.m12);
                return point2D2;
            case 4:
                point2D2.setLocation(y * this.m01, x * this.m10);
                return point2D2;
            case 5:
                point2D2.setLocation((y * this.m01) + this.m02, (x * this.m10) + this.m12);
                return point2D2;
            case 6:
                point2D2.setLocation((this.m00 * x) + (this.m01 * y), (x * this.m10) + (y * this.m11));
                return point2D2;
            case 7:
                break;
            default:
                stateError();
                break;
        }
        point2D2.setLocation((this.m00 * x) + (this.m01 * y) + this.m02, (x * this.m10) + (y * this.m11) + this.m12);
        return point2D2;
    }

    public void transform(Point2D[] point2DArr, int i, Point2D[] point2DArr2, int i2, int i3) {
        Point2D point2D;
        int i4 = this.state;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        while (true) {
            i7--;
            if (i7 >= 0) {
                int i8 = i5 + 1;
                Point2D point2D2 = point2DArr[i5];
                double x = point2D2.getX();
                double y = point2D2.getY();
                int i9 = i6 + 1;
                Point2D point2D3 = point2DArr2[i6];
                if (point2D3 == null) {
                    if (point2D2 instanceof Point2D.Double) {
                        point2D = new Point2D.Double();
                    } else {
                        point2D = new Point2D.Float();
                    }
                    point2D3 = point2D;
                    point2DArr2[i9 - 1] = point2D3;
                }
                switch (i4) {
                    case 0:
                        point2D3.setLocation(x, y);
                        continue;
                    case 1:
                        point2D3.setLocation(x + this.m02, y + this.m12);
                        continue;
                    case 2:
                        point2D3.setLocation(x * this.m00, y * this.m11);
                        continue;
                    case 3:
                        point2D3.setLocation((x * this.m00) + this.m02, (y * this.m11) + this.m12);
                        continue;
                    case 4:
                        point2D3.setLocation(y * this.m01, x * this.m10);
                        continue;
                    case 5:
                        point2D3.setLocation((y * this.m01) + this.m02, (x * this.m10) + this.m12);
                        continue;
                    case 6:
                        point2D3.setLocation((this.m00 * x) + (this.m01 * y), (x * this.m10) + (y * this.m11));
                        continue;
                    case 7:
                        break;
                    default:
                        stateError();
                        break;
                }
                point2D3.setLocation((this.m00 * x) + (this.m01 * y) + this.m02, (x * this.m10) + (y * this.m11) + this.m12);
                i5 = i8;
                i6 = i9;
            } else {
                return;
            }
        }
    }

    public void transform(float[] fArr, int i, float[] fArr2, int i2, int i3) {
        float[] fArr3;
        float[] fArr4 = fArr;
        int i4 = i;
        float[] fArr5 = fArr2;
        int i5 = i2;
        if (fArr5 == fArr4 && i5 > i4) {
            int i6 = i3 * 2;
            if (i5 < i4 + i6) {
                System.arraycopy(fArr4, i4, fArr5, i5, i6);
                i4 = i5;
            }
        }
        switch (this.state) {
            case 0:
                float[] fArr6 = fArr4;
                if (fArr6 != fArr5 || i4 != i5) {
                    System.arraycopy(fArr6, i4, fArr5, i5, i3 * 2);
                    return;
                }
                return;
            case 1:
                float[] fArr7 = fArr4;
                double d = this.m02;
                double d2 = this.m12;
                int i7 = i3;
                while (true) {
                    i7--;
                    if (i7 >= 0) {
                        int i8 = i5 + 1;
                        int i9 = i4 + 1;
                        fArr5[i5] = (float) (((double) fArr7[i4]) + d);
                        i5 = i8 + 1;
                        i4 = i9 + 1;
                        fArr5[i8] = (float) (((double) fArr7[i9]) + d2);
                    } else {
                        return;
                    }
                }
            case 2:
                float[] fArr8 = fArr4;
                double d3 = this.m00;
                double d4 = this.m11;
                int i10 = i3;
                while (true) {
                    i10--;
                    if (i10 >= 0) {
                        int i11 = i5 + 1;
                        int i12 = i4 + 1;
                        fArr5[i5] = (float) (((double) fArr8[i4]) * d3);
                        i5 = i11 + 1;
                        i4 = i12 + 1;
                        fArr5[i11] = (float) (((double) fArr8[i12]) * d4);
                    } else {
                        return;
                    }
                }
            case 3:
                float[] fArr9 = fArr4;
                double d5 = this.m00;
                double d6 = this.m02;
                double d7 = this.m11;
                double d8 = this.m12;
                int i13 = i3;
                while (true) {
                    int i14 = i13 - 1;
                    if (i14 >= 0) {
                        int i15 = i5 + 1;
                        int i16 = i4 + 1;
                        fArr5[i5] = (float) ((((double) fArr9[i4]) * d5) + d6);
                        i5 = i15 + 1;
                        fArr5[i15] = (float) ((((double) fArr9[i16]) * d7) + d8);
                        i13 = i14;
                        i4 = i16 + 1;
                    } else {
                        return;
                    }
                }
            case 4:
                double d9 = this.m01;
                double d10 = this.m10;
                int i17 = i3;
                while (true) {
                    i17--;
                    if (i17 >= 0) {
                        int i18 = i4 + 1;
                        float[] fArr10 = fArr;
                        double d11 = (double) fArr10[i4];
                        int i19 = i5 + 1;
                        fArr5[i5] = (float) (((double) fArr10[i18]) * d9);
                        i5 = i19 + 1;
                        fArr5[i19] = (float) (d11 * d10);
                        i4 = i18 + 1;
                    } else {
                        return;
                    }
                }
            case 5:
                double d12 = this.m01;
                double d13 = this.m02;
                double d14 = this.m10;
                double d15 = this.m12;
                int i20 = i4;
                int i21 = i3;
                while (true) {
                    int i22 = i21 - 1;
                    if (i22 >= 0) {
                        int i23 = i20 + 1;
                        double d16 = d15;
                        double d17 = (double) fArr4[i20];
                        int i24 = i5 + 1;
                        fArr5[i5] = (float) ((((double) fArr4[i23]) * d12) + d13);
                        i5 = i24 + 1;
                        fArr5[i24] = (float) ((d17 * d14) + d16);
                        fArr4 = fArr;
                        i21 = i22;
                        d15 = d16;
                        i20 = i23 + 1;
                    } else {
                        return;
                    }
                }
            case 6:
                double d18 = this.m00;
                double d19 = this.m01;
                double d20 = this.m10;
                double d21 = this.m11;
                int i25 = i4;
                int i26 = i3;
                while (true) {
                    i26--;
                    if (i26 >= 0) {
                        int i27 = i25 + 1;
                        double d22 = d21;
                        double d23 = (double) fArr4[i25];
                        double d24 = (double) fArr4[i27];
                        int i28 = i5 + 1;
                        fArr5[i5] = (float) ((d18 * d23) + (d19 * d24));
                        i5 = i28 + 1;
                        fArr5[i28] = (float) ((d23 * d20) + (d22 * d24));
                        i25 = i27 + 1;
                        d21 = d22;
                        d18 = d18;
                    } else {
                        return;
                    }
                }
            case 7:
                fArr3 = fArr4;
                break;
            default:
                fArr3 = fArr4;
                stateError();
                break;
        }
        double d25 = this.m00;
        double d26 = this.m01;
        double d27 = this.m02;
        double d28 = this.m10;
        double d29 = this.m11;
        double d30 = this.m12;
        int i29 = i4;
        int i30 = i3;
        while (true) {
            int i31 = i30 - 1;
            if (i31 >= 0) {
                int i32 = i29 + 1;
                double d31 = d30;
                double d32 = (double) fArr3[i29];
                i29 = i32 + 1;
                int i33 = i31;
                double d33 = (double) fArr3[i32];
                int i34 = i5 + 1;
                fArr5[i5] = (float) ((d25 * d32) + (d26 * d33) + d27);
                i5 = i34 + 1;
                fArr5[i34] = (float) ((d32 * d28) + (d29 * d33) + d31);
                fArr3 = fArr;
                i30 = i33;
                d30 = d31;
                d25 = d25;
            } else {
                return;
            }
        }
    }

    public void transform(double[] dArr, int i, double[] dArr2, int i2, int i3) {
        double[] dArr3 = dArr;
        int i4 = i;
        double[] dArr4 = dArr2;
        int i5 = i2;
        if (dArr4 == dArr3 && i5 > i4) {
            int i6 = i3 * 2;
            if (i5 < i4 + i6) {
                System.arraycopy(dArr3, i4, dArr4, i5, i6);
                i4 = i5;
            }
        }
        switch (this.state) {
            case 0:
                if (dArr3 != dArr4 || i4 != i5) {
                    System.arraycopy(dArr3, i4, dArr4, i5, i3 * 2);
                    return;
                }
                return;
            case 1:
                double d = this.m02;
                double d2 = this.m12;
                int i7 = i4;
                int i8 = i3;
                while (true) {
                    i8--;
                    if (i8 >= 0) {
                        int i9 = i5 + 1;
                        int i10 = i7 + 1;
                        dArr4[i5] = dArr3[i7] + d;
                        i5 = i9 + 1;
                        i7 = i10 + 1;
                        dArr4[i9] = dArr3[i10] + d2;
                    } else {
                        return;
                    }
                }
            case 2:
                double d3 = this.m00;
                double d4 = this.m11;
                int i11 = i4;
                int i12 = i3;
                while (true) {
                    i12--;
                    if (i12 >= 0) {
                        int i13 = i5 + 1;
                        int i14 = i11 + 1;
                        dArr4[i5] = dArr3[i11] * d3;
                        i5 = i13 + 1;
                        i11 = i14 + 1;
                        dArr4[i13] = dArr3[i14] * d4;
                    } else {
                        return;
                    }
                }
            case 3:
                double d5 = this.m00;
                double d6 = this.m02;
                double d7 = this.m11;
                double d8 = this.m12;
                int i15 = i4;
                int i16 = i3;
                while (true) {
                    i16--;
                    if (i16 >= 0) {
                        int i17 = i5 + 1;
                        int i18 = i15 + 1;
                        dArr4[i5] = (dArr3[i15] * d5) + d6;
                        i5 = i17 + 1;
                        i15 = i18 + 1;
                        dArr4[i17] = (dArr3[i18] * d7) + d8;
                    } else {
                        return;
                    }
                }
            case 4:
                double d9 = this.m01;
                double d10 = this.m10;
                int i19 = i4;
                int i20 = i3;
                while (true) {
                    i20--;
                    if (i20 >= 0) {
                        int i21 = i19 + 1;
                        double d11 = dArr3[i19];
                        int i22 = i5 + 1;
                        dArr4[i5] = dArr3[i21] * d9;
                        i5 = i22 + 1;
                        dArr4[i22] = d11 * d10;
                        i19 = i21 + 1;
                    } else {
                        return;
                    }
                }
            case 5:
                double d12 = this.m01;
                double d13 = this.m02;
                double d14 = this.m10;
                double d15 = this.m12;
                int i23 = i4;
                int i24 = i3;
                while (true) {
                    i24--;
                    if (i24 >= 0) {
                        int i25 = i23 + 1;
                        double d16 = dArr3[i23];
                        int i26 = i5 + 1;
                        dArr4[i5] = (dArr3[i25] * d12) + d13;
                        i5 = i26 + 1;
                        dArr4[i26] = (d16 * d14) + d15;
                        i23 = i25 + 1;
                    } else {
                        return;
                    }
                }
            case 6:
                double d17 = this.m00;
                double d18 = this.m01;
                double d19 = this.m10;
                double d20 = this.m11;
                int i27 = i4;
                int i28 = i3;
                while (true) {
                    i28--;
                    if (i28 >= 0) {
                        int i29 = i27 + 1;
                        double d21 = dArr3[i27];
                        i27 = i29 + 1;
                        double d22 = dArr3[i29];
                        int i30 = i5 + 1;
                        dArr4[i5] = (d17 * d21) + (d18 * d22);
                        i5 = i30 + 1;
                        dArr4[i30] = (d21 * d19) + (d22 * d20);
                    } else {
                        return;
                    }
                }
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d23 = this.m00;
        double d24 = this.m01;
        double d25 = this.m02;
        double d26 = this.m10;
        double d27 = this.m11;
        double d28 = this.m12;
        int i31 = i4;
        int i32 = i3;
        while (true) {
            i32--;
            if (i32 >= 0) {
                int i33 = i31 + 1;
                double d29 = dArr3[i31];
                i31 = i33 + 1;
                double d30 = dArr3[i33];
                int i34 = i5 + 1;
                dArr4[i5] = (d23 * d29) + (d24 * d30) + d25;
                i5 = i34 + 1;
                dArr4[i34] = (d29 * d26) + (d30 * d27) + d28;
            } else {
                return;
            }
        }
    }

    public void transform(float[] fArr, int i, double[] dArr, int i2, int i3) {
        switch (this.state) {
            case 0:
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
                while (true) {
                    i6--;
                    if (i6 >= 0) {
                        int i7 = i5 + 1;
                        int i8 = i4 + 1;
                        dArr[i5] = (double) fArr[i4];
                        i5 = i7 + 1;
                        i4 = i8 + 1;
                        dArr[i7] = (double) fArr[i8];
                    } else {
                        return;
                    }
                }
            case 1:
                double d = this.m02;
                double d2 = this.m12;
                int i9 = i;
                int i10 = i2;
                int i11 = i3;
                while (true) {
                    i11--;
                    if (i11 >= 0) {
                        int i12 = i10 + 1;
                        int i13 = i9 + 1;
                        dArr[i10] = ((double) fArr[i9]) + d;
                        i10 = i12 + 1;
                        i9 = i13 + 1;
                        dArr[i12] = ((double) fArr[i13]) + d2;
                    } else {
                        return;
                    }
                }
            case 2:
                double d3 = this.m00;
                double d4 = this.m11;
                int i14 = i;
                int i15 = i2;
                int i16 = i3;
                while (true) {
                    i16--;
                    if (i16 >= 0) {
                        int i17 = i15 + 1;
                        int i18 = i14 + 1;
                        dArr[i15] = ((double) fArr[i14]) * d3;
                        i15 = i17 + 1;
                        i14 = i18 + 1;
                        dArr[i17] = ((double) fArr[i18]) * d4;
                    } else {
                        return;
                    }
                }
            case 3:
                double d5 = this.m00;
                double d6 = this.m02;
                double d7 = this.m11;
                double d8 = this.m12;
                int i19 = i;
                int i20 = i2;
                int i21 = i3;
                while (true) {
                    i21--;
                    if (i21 >= 0) {
                        int i22 = i20 + 1;
                        int i23 = i19 + 1;
                        dArr[i20] = (((double) fArr[i19]) * d5) + d6;
                        i20 = i22 + 1;
                        i19 = i23 + 1;
                        dArr[i22] = (((double) fArr[i23]) * d7) + d8;
                    } else {
                        return;
                    }
                }
            case 4:
                double d9 = this.m01;
                double d10 = this.m10;
                int i24 = i;
                int i25 = i2;
                int i26 = i3;
                while (true) {
                    i26--;
                    if (i26 >= 0) {
                        int i27 = i24 + 1;
                        double d11 = (double) fArr[i24];
                        int i28 = i25 + 1;
                        dArr[i25] = ((double) fArr[i27]) * d9;
                        i25 = i28 + 1;
                        dArr[i28] = d11 * d10;
                        i24 = i27 + 1;
                    } else {
                        return;
                    }
                }
            case 5:
                double d12 = this.m01;
                double d13 = this.m02;
                double d14 = this.m10;
                double d15 = this.m12;
                int i29 = i;
                int i30 = i2;
                int i31 = i3;
                while (true) {
                    int i32 = i31 - 1;
                    if (i32 >= 0) {
                        int i33 = i29 + 1;
                        double d16 = (double) fArr[i29];
                        int i34 = i30 + 1;
                        dArr[i30] = (((double) fArr[i33]) * d12) + d13;
                        i30 = i34 + 1;
                        dArr[i34] = (d16 * d14) + d15;
                        i31 = i32;
                        i29 = i33 + 1;
                    } else {
                        return;
                    }
                }
            case 6:
                double d17 = this.m00;
                double d18 = this.m01;
                double d19 = this.m10;
                double d20 = this.m11;
                int i35 = i;
                int i36 = i2;
                int i37 = i3;
                while (true) {
                    int i38 = i37 - 1;
                    if (i38 >= 0) {
                        int i39 = i35 + 1;
                        double d21 = (double) fArr[i35];
                        i35 = i39 + 1;
                        int i40 = i38;
                        double d22 = (double) fArr[i39];
                        int i41 = i36 + 1;
                        dArr[i36] = (d17 * d21) + (d18 * d22);
                        i36 = i41 + 1;
                        dArr[i41] = (d21 * d19) + (d22 * d20);
                        i37 = i40;
                    } else {
                        return;
                    }
                }
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d23 = this.m00;
        double d24 = this.m01;
        double d25 = this.m02;
        double d26 = this.m10;
        double d27 = this.m11;
        double d28 = this.m12;
        int i42 = i;
        int i43 = i2;
        int i44 = i3;
        while (true) {
            i44--;
            if (i44 >= 0) {
                int i45 = i42 + 1;
                double d29 = d28;
                double d30 = (double) fArr[i42];
                i42 = i45 + 1;
                double d31 = d27;
                double d32 = (double) fArr[i45];
                int i46 = i43 + 1;
                dArr[i43] = (d23 * d30) + (d24 * d32) + d25;
                i43 = i46 + 1;
                dArr[i46] = (d30 * d26) + (d32 * d31) + d29;
                d28 = d29;
                d27 = d31;
            } else {
                return;
            }
        }
    }

    public void transform(double[] dArr, int i, float[] fArr, int i2, int i3) {
        switch (this.state) {
            case 0:
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
                while (true) {
                    i6--;
                    if (i6 >= 0) {
                        int i7 = i5 + 1;
                        int i8 = i4 + 1;
                        fArr[i5] = (float) dArr[i4];
                        i5 = i7 + 1;
                        i4 = i8 + 1;
                        fArr[i7] = (float) dArr[i8];
                    } else {
                        return;
                    }
                }
            case 1:
                double d = this.m02;
                double d2 = this.m12;
                int i9 = i;
                int i10 = i2;
                int i11 = i3;
                while (true) {
                    i11--;
                    if (i11 >= 0) {
                        int i12 = i10 + 1;
                        int i13 = i9 + 1;
                        fArr[i10] = (float) (dArr[i9] + d);
                        i10 = i12 + 1;
                        i9 = i13 + 1;
                        fArr[i12] = (float) (dArr[i13] + d2);
                    } else {
                        return;
                    }
                }
            case 2:
                double d3 = this.m00;
                double d4 = this.m11;
                int i14 = i;
                int i15 = i2;
                int i16 = i3;
                while (true) {
                    i16--;
                    if (i16 >= 0) {
                        int i17 = i15 + 1;
                        int i18 = i14 + 1;
                        fArr[i15] = (float) (dArr[i14] * d3);
                        i15 = i17 + 1;
                        i14 = i18 + 1;
                        fArr[i17] = (float) (dArr[i18] * d4);
                    } else {
                        return;
                    }
                }
            case 3:
                double d5 = this.m00;
                double d6 = this.m02;
                double d7 = this.m11;
                double d8 = this.m12;
                int i19 = i;
                int i20 = i2;
                int i21 = i3;
                while (true) {
                    i21--;
                    if (i21 >= 0) {
                        int i22 = i20 + 1;
                        int i23 = i19 + 1;
                        fArr[i20] = (float) ((dArr[i19] * d5) + d6);
                        i20 = i22 + 1;
                        i19 = i23 + 1;
                        fArr[i22] = (float) ((dArr[i23] * d7) + d8);
                    } else {
                        return;
                    }
                }
            case 4:
                double d9 = this.m01;
                double d10 = this.m10;
                int i24 = i;
                int i25 = i2;
                int i26 = i3;
                while (true) {
                    i26--;
                    if (i26 >= 0) {
                        int i27 = i24 + 1;
                        double d11 = dArr[i24];
                        int i28 = i25 + 1;
                        fArr[i25] = (float) (dArr[i27] * d9);
                        i25 = i28 + 1;
                        fArr[i28] = (float) (d11 * d10);
                        i24 = i27 + 1;
                    } else {
                        return;
                    }
                }
            case 5:
                double d12 = this.m01;
                double d13 = this.m02;
                double d14 = this.m10;
                double d15 = this.m12;
                int i29 = i;
                int i30 = i2;
                int i31 = i3;
                while (true) {
                    i31--;
                    if (i31 >= 0) {
                        int i32 = i29 + 1;
                        double d16 = dArr[i29];
                        int i33 = i30 + 1;
                        fArr[i30] = (float) ((dArr[i32] * d12) + d13);
                        i30 = i33 + 1;
                        fArr[i33] = (float) ((d16 * d14) + d15);
                        i29 = i32 + 1;
                        d12 = d12;
                    } else {
                        return;
                    }
                }
            case 6:
                double d17 = this.m00;
                double d18 = this.m01;
                double d19 = this.m10;
                double d20 = this.m11;
                int i34 = i;
                int i35 = i2;
                int i36 = i3;
                while (true) {
                    i36--;
                    if (i36 >= 0) {
                        int i37 = i34 + 1;
                        double d21 = dArr[i34];
                        i34 = i37 + 1;
                        double d22 = dArr[i37];
                        int i38 = i35 + 1;
                        fArr[i35] = (float) ((d17 * d21) + (d18 * d22));
                        i35 = i38 + 1;
                        fArr[i38] = (float) ((d21 * d19) + (d22 * d20));
                        d17 = d17;
                    } else {
                        return;
                    }
                }
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d23 = this.m00;
        double d24 = this.m01;
        double d25 = this.m02;
        double d26 = this.m10;
        double d27 = this.m11;
        double d28 = this.m12;
        int i39 = i;
        int i40 = i2;
        int i41 = i3;
        while (true) {
            i41--;
            if (i41 >= 0) {
                int i42 = i39 + 1;
                double d29 = dArr[i39];
                i39 = i42 + 1;
                double d30 = dArr[i42];
                int i43 = i40 + 1;
                fArr[i40] = (float) ((d23 * d29) + (d24 * d30) + d25);
                i40 = i43 + 1;
                fArr[i43] = (float) ((d29 * d26) + (d30 * d27) + d28);
                d23 = d23;
            } else {
                return;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0030, code lost:
        if (r7 == 0.0d) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0032, code lost:
        r9 = r11.m10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0036, code lost:
        if (r9 == 0.0d) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0038, code lost:
        r13.setLocation(r2 / r9, r0 / r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003d, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        throw new com.app.office.java.awt.geom.NoninvertibleTransformException("Determinant is 0");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004a, code lost:
        r7 = r11.m00;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004e, code lost:
        if (r7 == 0.0d) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0050, code lost:
        r9 = r11.m11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        if (r9 == 0.0d) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        r13.setLocation(r0 / r7, r2 / r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0061, code lost:
        throw new com.app.office.java.awt.geom.NoninvertibleTransformException("Determinant is 0");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0070, code lost:
        r0 = r0 - r11.m02;
        r2 = r2 - r11.m12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0076, code lost:
        r4 = (r11.m00 * r11.m11) - (r11.m01 * r11.m10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008b, code lost:
        if (java.lang.Math.abs(r4) <= Double.MIN_VALUE) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008d, code lost:
        r13.setLocation(((r11.m11 * r0) - (r11.m01 * r2)) / r4, ((r2 * r11.m00) - (r0 * r11.m10)) / r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a4, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bb, code lost:
        throw new com.app.office.java.awt.geom.NoninvertibleTransformException("Determinant is " + r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002c, code lost:
        r7 = r11.m01;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.java.awt.geom.Point2D inverseTransform(com.app.office.java.awt.geom.Point2D r12, com.app.office.java.awt.geom.Point2D r13) throws com.app.office.java.awt.geom.NoninvertibleTransformException {
        /*
            r11 = this;
            if (r13 != 0) goto L_0x0011
            boolean r13 = r12 instanceof com.app.office.java.awt.geom.Point2D.Double
            if (r13 == 0) goto L_0x000c
            com.app.office.java.awt.geom.Point2D$Double r13 = new com.app.office.java.awt.geom.Point2D$Double
            r13.<init>()
            goto L_0x0011
        L_0x000c:
            com.app.office.java.awt.geom.Point2D$Float r13 = new com.app.office.java.awt.geom.Point2D$Float
            r13.<init>()
        L_0x0011:
            double r0 = r12.getX()
            double r2 = r12.getY()
            int r12 = r11.state
            java.lang.String r4 = "Determinant is 0"
            r5 = 0
            switch(r12) {
                case 0: goto L_0x006c;
                case 1: goto L_0x0062;
                case 2: goto L_0x004a;
                case 3: goto L_0x0044;
                case 4: goto L_0x002c;
                case 5: goto L_0x0026;
                case 6: goto L_0x0076;
                case 7: goto L_0x0070;
                default: goto L_0x0022;
            }
        L_0x0022:
            r11.stateError()
            goto L_0x0070
        L_0x0026:
            double r7 = r11.m02
            double r0 = r0 - r7
            double r7 = r11.m12
            double r2 = r2 - r7
        L_0x002c:
            double r7 = r11.m01
            int r12 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r12 == 0) goto L_0x003e
            double r9 = r11.m10
            int r12 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r12 == 0) goto L_0x003e
            double r2 = r2 / r9
            double r0 = r0 / r7
            r13.setLocation(r2, r0)
            return r13
        L_0x003e:
            com.app.office.java.awt.geom.NoninvertibleTransformException r12 = new com.app.office.java.awt.geom.NoninvertibleTransformException
            r12.<init>(r4)
            throw r12
        L_0x0044:
            double r7 = r11.m02
            double r0 = r0 - r7
            double r7 = r11.m12
            double r2 = r2 - r7
        L_0x004a:
            double r7 = r11.m00
            int r12 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r12 == 0) goto L_0x005c
            double r9 = r11.m11
            int r12 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r12 == 0) goto L_0x005c
            double r0 = r0 / r7
            double r2 = r2 / r9
            r13.setLocation(r0, r2)
            return r13
        L_0x005c:
            com.app.office.java.awt.geom.NoninvertibleTransformException r12 = new com.app.office.java.awt.geom.NoninvertibleTransformException
            r12.<init>(r4)
            throw r12
        L_0x0062:
            double r4 = r11.m02
            double r0 = r0 - r4
            double r4 = r11.m12
            double r2 = r2 - r4
            r13.setLocation(r0, r2)
            return r13
        L_0x006c:
            r13.setLocation(r0, r2)
            return r13
        L_0x0070:
            double r4 = r11.m02
            double r0 = r0 - r4
            double r4 = r11.m12
            double r2 = r2 - r4
        L_0x0076:
            double r4 = r11.m00
            double r6 = r11.m11
            double r4 = r4 * r6
            double r6 = r11.m01
            double r8 = r11.m10
            double r6 = r6 * r8
            double r4 = r4 - r6
            double r6 = java.lang.Math.abs(r4)
            r8 = 1
            int r12 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r12 <= 0) goto L_0x00a5
            double r6 = r11.m11
            double r6 = r6 * r0
            double r8 = r11.m01
            double r8 = r8 * r2
            double r6 = r6 - r8
            double r6 = r6 / r4
            double r8 = r11.m00
            double r2 = r2 * r8
            double r8 = r11.m10
            double r0 = r0 * r8
            double r2 = r2 - r0
            double r2 = r2 / r4
            r13.setLocation(r6, r2)
            return r13
        L_0x00a5:
            com.app.office.java.awt.geom.NoninvertibleTransformException r12 = new com.app.office.java.awt.geom.NoninvertibleTransformException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r0 = "Determinant is "
            r13.append(r0)
            r13.append(r4)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.AffineTransform.inverseTransform(com.app.office.java.awt.geom.Point2D, com.app.office.java.awt.geom.Point2D):com.app.office.java.awt.geom.Point2D");
    }

    public void inverseTransform(double[] dArr, int i, double[] dArr2, int i2, int i3) throws NoninvertibleTransformException {
        double[] dArr3 = dArr;
        int i4 = i;
        double[] dArr4 = dArr2;
        int i5 = i2;
        if (dArr4 == dArr3 && i5 > i4) {
            int i6 = i3 * 2;
            if (i5 < i4 + i6) {
                System.arraycopy(dArr3, i4, dArr4, i5, i6);
                i4 = i5;
            }
        }
        switch (this.state) {
            case 0:
                int i7 = i2;
                if (dArr3 != dArr4 || i4 != i7) {
                    System.arraycopy(dArr3, i4, dArr4, i7, i3 * 2);
                    return;
                }
                return;
            case 1:
                double d = this.m02;
                double d2 = this.m12;
                int i8 = i3;
                int i9 = i4;
                int i10 = i2;
                while (true) {
                    i8--;
                    if (i8 >= 0) {
                        int i11 = i10 + 1;
                        int i12 = i9 + 1;
                        dArr4[i10] = dArr3[i9] - d;
                        i10 = i11 + 1;
                        i9 = i12 + 1;
                        dArr4[i11] = dArr3[i12] - d2;
                    } else {
                        return;
                    }
                }
            case 2:
                double d3 = this.m00;
                double d4 = this.m11;
                if (d3 == 0.0d || d4 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                int i13 = i3;
                int i14 = i4;
                int i15 = i2;
                while (true) {
                    i13--;
                    if (i13 >= 0) {
                        int i16 = i15 + 1;
                        int i17 = i14 + 1;
                        dArr4[i15] = dArr3[i14] / d3;
                        i15 = i16 + 1;
                        i14 = i17 + 1;
                        dArr4[i16] = dArr3[i17] / d4;
                    } else {
                        return;
                    }
                }
                break;
            case 3:
                double d5 = this.m00;
                double d6 = this.m02;
                double d7 = this.m11;
                double d8 = this.m12;
                if (d5 == 0.0d || d7 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                int i18 = i3;
                int i19 = i4;
                int i20 = i2;
                while (true) {
                    i18--;
                    if (i18 >= 0) {
                        int i21 = i20 + 1;
                        int i22 = i19 + 1;
                        dArr4[i20] = (dArr3[i19] - d6) / d5;
                        i20 = i21 + 1;
                        i19 = i22 + 1;
                        dArr4[i21] = (dArr3[i22] - d8) / d7;
                    } else {
                        return;
                    }
                }
                break;
            case 4:
                double d9 = this.m01;
                double d10 = this.m10;
                if (d9 == 0.0d || d10 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                int i23 = i3;
                int i24 = i4;
                int i25 = i2;
                while (true) {
                    i23--;
                    if (i23 >= 0) {
                        int i26 = i24 + 1;
                        double d11 = dArr3[i24];
                        int i27 = i25 + 1;
                        dArr4[i25] = dArr3[i26] / d10;
                        i25 = i27 + 1;
                        dArr4[i27] = d11 / d9;
                        i24 = i26 + 1;
                    } else {
                        return;
                    }
                }
                break;
            case 5:
                double d12 = this.m01;
                double d13 = this.m02;
                double d14 = this.m10;
                double d15 = this.m12;
                if (d12 == 0.0d || d14 == 0.0d) {
                    throw new NoninvertibleTransformException("Determinant is 0");
                }
                int i28 = i3;
                int i29 = i4;
                int i30 = i2;
                while (true) {
                    i28--;
                    if (i28 >= 0) {
                        int i31 = i29 + 1;
                        int i32 = i30 + 1;
                        dArr4[i30] = (dArr3[i31] - d15) / d14;
                        i30 = i32 + 1;
                        dArr4[i32] = (dArr3[i29] - d13) / d12;
                        i29 = i31 + 1;
                    } else {
                        return;
                    }
                }
                break;
            case 6:
                double d16 = this.m00;
                double d17 = this.m01;
                double d18 = this.m10;
                double d19 = this.m11;
                double d20 = (d16 * d19) - (d17 * d18);
                if (Math.abs(d20) > Double.MIN_VALUE) {
                    int i33 = i3;
                    int i34 = i4;
                    int i35 = i2;
                    while (true) {
                        i33--;
                        if (i33 >= 0) {
                            int i36 = i34 + 1;
                            double d21 = dArr3[i34];
                            i34 = i36 + 1;
                            double d22 = dArr3[i36];
                            int i37 = i35 + 1;
                            dArr4[i35] = ((d21 * d19) - (d22 * d17)) / d20;
                            i35 = i37 + 1;
                            dArr4[i37] = ((d22 * d16) - (d21 * d18)) / d20;
                        } else {
                            return;
                        }
                    }
                } else {
                    throw new NoninvertibleTransformException("Determinant is " + d20);
                }
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d23 = this.m00;
        double d24 = this.m01;
        double d25 = this.m02;
        double d26 = this.m10;
        double d27 = this.m11;
        int i38 = i4;
        double d28 = this.m12;
        double d29 = d26;
        double d30 = (d23 * d27) - (d24 * d26);
        if (Math.abs(d30) > Double.MIN_VALUE) {
            int i39 = i2;
            int i40 = i3;
            while (true) {
                i40--;
                if (i40 >= 0) {
                    int i41 = i38 + 1;
                    double d31 = dArr3[i38] - d25;
                    int i42 = i41 + 1;
                    double d32 = dArr3[i41] - d28;
                    int i43 = i39 + 1;
                    dArr2[i39] = ((d31 * d27) - (d32 * d24)) / d30;
                    i39 = i43 + 1;
                    dArr2[i43] = ((d32 * d23) - (d31 * d29)) / d30;
                    i38 = i42;
                } else {
                    return;
                }
            }
        } else {
            throw new NoninvertibleTransformException("Determinant is " + d30);
        }
    }

    public Point2D deltaTransform(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            if (point2D instanceof Point2D.Double) {
                point2D2 = new Point2D.Double();
            } else {
                point2D2 = new Point2D.Float();
            }
        }
        double x = point2D.getX();
        double y = point2D.getY();
        switch (this.state) {
            case 0:
            case 1:
                point2D2.setLocation(x, y);
                return point2D2;
            case 2:
            case 3:
                point2D2.setLocation(x * this.m00, y * this.m11);
                return point2D2;
            case 4:
            case 5:
                point2D2.setLocation(y * this.m01, x * this.m10);
                return point2D2;
            case 6:
            case 7:
                break;
            default:
                stateError();
                break;
        }
        point2D2.setLocation((this.m00 * x) + (this.m01 * y), (x * this.m10) + (y * this.m11));
        return point2D2;
    }

    public void deltaTransform(double[] dArr, int i, double[] dArr2, int i2, int i3) {
        double[] dArr3 = dArr;
        int i4 = i;
        double[] dArr4 = dArr2;
        int i5 = i2;
        if (dArr4 == dArr3 && i5 > i4) {
            int i6 = i3 * 2;
            if (i5 < i4 + i6) {
                System.arraycopy(dArr3, i4, dArr4, i5, i6);
                i4 = i5;
            }
        }
        switch (this.state) {
            case 0:
            case 1:
                if (dArr3 != dArr4 || i4 != i5) {
                    System.arraycopy(dArr3, i4, dArr4, i5, i3 * 2);
                    return;
                }
                return;
            case 2:
            case 3:
                double d = this.m00;
                double d2 = this.m11;
                int i7 = i4;
                int i8 = i3;
                while (true) {
                    i8--;
                    if (i8 >= 0) {
                        int i9 = i5 + 1;
                        int i10 = i7 + 1;
                        dArr4[i5] = dArr3[i7] * d;
                        i5 = i9 + 1;
                        i7 = i10 + 1;
                        dArr4[i9] = dArr3[i10] * d2;
                    } else {
                        return;
                    }
                }
            case 4:
            case 5:
                double d3 = this.m01;
                double d4 = this.m10;
                int i11 = i4;
                int i12 = i3;
                while (true) {
                    i12--;
                    if (i12 >= 0) {
                        int i13 = i11 + 1;
                        double d5 = dArr3[i11];
                        int i14 = i5 + 1;
                        dArr4[i5] = dArr3[i13] * d3;
                        i5 = i14 + 1;
                        dArr4[i14] = d5 * d4;
                        i11 = i13 + 1;
                    } else {
                        return;
                    }
                }
            case 6:
            case 7:
                break;
            default:
                stateError();
                break;
        }
        double d6 = this.m00;
        double d7 = this.m01;
        double d8 = this.m10;
        double d9 = this.m11;
        int i15 = i4;
        int i16 = i3;
        while (true) {
            i16--;
            if (i16 >= 0) {
                int i17 = i15 + 1;
                double d10 = dArr3[i15];
                i15 = i17 + 1;
                double d11 = dArr3[i17];
                int i18 = i5 + 1;
                dArr4[i5] = (d10 * d6) + (d11 * d7);
                i5 = i18 + 1;
                dArr4[i18] = (d10 * d8) + (d11 * d9);
            } else {
                return;
            }
        }
    }

    public Shape createTransformedShape(Shape shape) {
        if (shape == null) {
            return null;
        }
        return new Path2D.Double(shape, this);
    }

    private static double _matround(double d) {
        return Math.rint(d * 1.0E15d) / 1.0E15d;
    }

    public String toString() {
        return "AffineTransform[[" + _matround(this.m00) + ", " + _matround(this.m01) + ", " + _matround(this.m02) + "], [" + _matround(this.m10) + ", " + _matround(this.m11) + ", " + _matround(this.m12) + "]]";
    }

    public boolean isIdentity() {
        return this.state == 0 || getType() == 0;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    public int hashCode() {
        long doubleToLongBits = (((((((((Double.doubleToLongBits(this.m00) * 31) + Double.doubleToLongBits(this.m01)) * 31) + Double.doubleToLongBits(this.m02)) * 31) + Double.doubleToLongBits(this.m10)) * 31) + Double.doubleToLongBits(this.m11)) * 31) + Double.doubleToLongBits(this.m12);
        return ((int) doubleToLongBits) ^ ((int) (doubleToLongBits >> 32));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AffineTransform)) {
            return false;
        }
        AffineTransform affineTransform = (AffineTransform) obj;
        if (this.m00 == affineTransform.m00 && this.m01 == affineTransform.m01 && this.m02 == affineTransform.m02 && this.m10 == affineTransform.m10 && this.m11 == affineTransform.m11 && this.m12 == affineTransform.m12) {
            return true;
        }
        return false;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws ClassNotFoundException, IOException {
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        updateState();
    }
}
