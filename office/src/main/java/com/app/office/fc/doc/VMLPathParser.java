package com.app.office.fc.doc;

import android.graphics.Path;
import android.graphics.PointF;
import com.app.office.common.shape.WPAutoShape;
import java.util.ArrayList;
import java.util.List;

public class VMLPathParser {
    public static final byte Command_AngleEllipse = 11;
    public static final byte Command_AngleEllipseTo = 10;
    public static final byte Command_Arc = 13;
    public static final byte Command_ArcTo = 12;
    public static final byte Command_ClockwiseArc = 15;
    public static final byte Command_ClockwiseArcTo = 14;
    public static final byte Command_Close = 3;
    public static final byte Command_CurveTo = 2;
    private static final byte Command_EllipticalQaudrantX = 16;
    private static final byte Command_EllipticalQaudrantY = 17;
    public static final byte Command_End = 4;
    public static final byte Command_Invalid = -1;
    public static final byte Command_LineTo = 1;
    public static final byte Command_MoveTo = 0;
    public static final byte Command_NoFill = 8;
    public static final byte Command_NoStroke = 9;
    private static final byte Command_QuadraticBezier = 18;
    public static final byte Command_RCurveTo = 7;
    public static final byte Command_RLineTo = 6;
    public static final byte Command_RMoveTo = 5;
    private static byte NodeType_End = 2;
    private static byte NodeType_Invalidate = -1;
    private static byte NodeType_Middle = 1;
    private static byte NodeType_Start = 0;
    private static VMLPathParser instance = new VMLPathParser();
    private StringBuilder builder = new StringBuilder();
    private PointF ctrNode1 = new PointF();
    private PointF ctrNode2 = new PointF();
    private byte currentNodeType;
    Path endArrowPath = null;
    private int index;
    private PointF nextNode = new PointF();
    private List<Integer> paraList = new ArrayList();
    private PointF preNode = new PointF();
    private byte preNodeType;
    Path startArrowPath = null;

    private VMLPathParser() {
        byte b = NodeType_Invalidate;
        this.currentNodeType = b;
        this.preNodeType = b;
    }

    public static VMLPathParser instance() {
        return instance;
    }

    public PathWithArrow createPath(WPAutoShape wPAutoShape, String str, int i) {
        Path path;
        boolean z;
        String str2 = str;
        try {
            this.index = 0;
            this.startArrowPath = null;
            this.endArrowPath = null;
            ArrayList arrayList = new ArrayList();
            byte nextCommand = nextCommand(str2);
            this.currentNodeType = NodeType_Start;
            this.preNodeType = NodeType_Invalidate;
            byte b = nextCommand;
            Path path2 = null;
            while (true) {
                boolean z2 = true;
                while (b != -1) {
                    if (b == 4) {
                        byte nextCommand2 = nextCommand(str2);
                        if (nextCommand2 == -1) {
                            this.currentNodeType = NodeType_End;
                        }
                        b = nextCommand2;
                    } else {
                        if (z2) {
                            Path path3 = new Path();
                            arrayList.add(path3);
                            path = path3;
                            z = false;
                        } else {
                            z = z2;
                            path = path2;
                        }
                        Integer[] nextParameters = nextParameters(str2);
                        byte nextCommand3 = nextCommand(str2);
                        if (nextCommand3 == -1 || nextCommand3 == 4) {
                            this.currentNodeType = NodeType_End;
                        }
                        processPath(wPAutoShape, i, path, b, nextParameters);
                        this.preNodeType = this.currentNodeType;
                        this.currentNodeType = NodeType_Middle;
                        z2 = z;
                        path2 = path;
                        b = nextCommand3;
                    }
                }
                PathWithArrow pathWithArrow = new PathWithArrow((Path[]) arrayList.toArray(new Path[arrayList.size()]), this.startArrowPath, this.endArrowPath);
                this.startArrowPath = null;
                this.endArrowPath = null;
                return pathWithArrow;
            }
        } catch (Exception unused) {
            return null;
        }
    }

    private byte nextCommand(String str) {
        StringBuilder sb = this.builder;
        sb.delete(0, sb.length());
        while (this.index < str.length() && Character.isLetter(str.charAt(this.index))) {
            StringBuilder sb2 = this.builder;
            int i = this.index;
            this.index = i + 1;
            sb2.append(str.charAt(i));
        }
        String sb3 = this.builder.toString();
        if (sb3.contains("h")) {
            sb3 = sb3.substring(2);
        }
        if ("m".equalsIgnoreCase(sb3)) {
            return 0;
        }
        if ("l".equalsIgnoreCase(sb3)) {
            return 1;
        }
        if ("c".equalsIgnoreCase(sb3)) {
            return 2;
        }
        if ("x".equalsIgnoreCase(sb3)) {
            return 3;
        }
        if ("e".equalsIgnoreCase(sb3)) {
            return 4;
        }
        if ("t".equalsIgnoreCase(sb3)) {
            return 5;
        }
        if ("r".equalsIgnoreCase(sb3)) {
            return 6;
        }
        if ("v".equalsIgnoreCase(sb3)) {
            return 7;
        }
        if ("nf".equalsIgnoreCase(sb3)) {
            return 8;
        }
        if ("ns".equalsIgnoreCase(sb3)) {
            return 9;
        }
        if ("ae".equalsIgnoreCase(sb3)) {
            return 10;
        }
        if ("al".equalsIgnoreCase(sb3)) {
            return 11;
        }
        if ("at".equalsIgnoreCase(sb3)) {
            return 12;
        }
        if ("ar".equalsIgnoreCase(sb3)) {
            return 13;
        }
        if ("wa".equalsIgnoreCase(sb3)) {
            return 14;
        }
        if ("wr".equalsIgnoreCase(sb3)) {
            return 15;
        }
        if ("qx".equalsIgnoreCase(sb3)) {
            return 16;
        }
        if ("qy".equalsIgnoreCase(sb3)) {
            return 17;
        }
        if ("qb".equalsIgnoreCase(sb3)) {
            return 18;
        }
        if (!sb3.contains("x") && !sb3.contains("X")) {
            return -1;
        }
        this.index -= sb3.length() - 1;
        return 3;
    }

    private Integer[] nextParameters(String str) {
        this.paraList.clear();
        while (hasNextPoint(str)) {
            int[] nextPoint = nextPoint(str);
            this.paraList.add(Integer.valueOf(nextPoint[0]));
            this.paraList.add(Integer.valueOf(nextPoint[1]));
        }
        List<Integer> list = this.paraList;
        return (Integer[]) list.toArray(new Integer[list.size()]);
    }

    private boolean hasNextPoint(String str) {
        return this.index < str.length() && !Character.isLetter(str.charAt(this.index));
    }

    private int[] nextPoint(String str) {
        int[] iArr = new int[2];
        StringBuilder sb = this.builder;
        sb.delete(0, sb.length());
        while (this.index < str.length() && (Character.isDigit(str.charAt(this.index)) || str.charAt(this.index) == '-')) {
            StringBuilder sb2 = this.builder;
            int i = this.index;
            this.index = i + 1;
            sb2.append(str.charAt(i));
        }
        if (this.builder.length() > 0) {
            iArr[0] = Integer.parseInt(this.builder.toString());
        }
        if (this.index < str.length() && str.charAt(this.index) == ',') {
            this.index++;
            StringBuilder sb3 = this.builder;
            sb3.delete(0, sb3.length());
            while (this.index < str.length() && (Character.isDigit(str.charAt(this.index)) || str.charAt(this.index) == '-')) {
                StringBuilder sb4 = this.builder;
                int i2 = this.index;
                this.index = i2 + 1;
                sb4.append(str.charAt(i2));
            }
            if (this.builder.length() > 0) {
                iArr[1] = Integer.parseInt(this.builder.toString());
            }
            if (this.index < str.length() && str.charAt(this.index) == ',') {
                this.index++;
            }
        }
        return iArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x035e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x03b9  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x03e7  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0427  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x044f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processPath(com.app.office.common.shape.WPAutoShape r25, int r26, android.graphics.Path r27, byte r28, java.lang.Integer[] r29) {
        /*
            r24 = this;
            r0 = r24
            r1 = r27
            r2 = r28
            r3 = r29
            byte r4 = r0.preNodeType
            byte r5 = NodeType_Start
            r7 = 7
            r8 = 5
            r9 = 3
            r10 = 6
            r11 = 2
            r12 = 1
            if (r4 != r5) goto L_0x0127
            if (r25 == 0) goto L_0x0127
            boolean r4 = r25.getStartArrowhead()
            if (r4 == 0) goto L_0x0127
            r4 = 0
            if (r2 == r12) goto L_0x0104
            r5 = 4
            if (r2 == r11) goto L_0x00bd
            if (r2 == r10) goto L_0x008e
            if (r2 == r7) goto L_0x0028
            goto L_0x0127
        L_0x0028:
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r13 = r0.nextNode
            float r13 = r13.x
            float r14 = r5 + r13
            r5 = r3[r8]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r13 = r0.nextNode
            float r13 = r13.y
            float r15 = r5 + r13
            r5 = r3[r11]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r13 = r0.nextNode
            float r13 = r13.x
            float r16 = r5 + r13
            r5 = r3[r9]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r13 = r0.nextNode
            float r13 = r13.y
            float r17 = r5 + r13
            r4 = r3[r4]
            int r4 = r4.intValue()
            float r4 = (float) r4
            android.graphics.PointF r5 = r0.nextNode
            float r5 = r5.x
            float r18 = r4 + r5
            r4 = r3[r12]
            int r4 = r4.intValue()
            float r4 = (float) r4
            android.graphics.PointF r5 = r0.nextNode
            float r5 = r5.y
            float r19 = r4 + r5
            android.graphics.PointF r4 = r0.nextNode
            float r4 = r4.x
            android.graphics.PointF r5 = r0.nextNode
            float r5 = r5.y
            com.app.office.common.shape.Arrow r22 = r25.getStartArrow()
            r20 = r4
            r21 = r5
            r23 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            goto L_0x0128
        L_0x008e:
            r4 = r3[r4]
            int r4 = r4.intValue()
            float r4 = (float) r4
            android.graphics.PointF r5 = r0.nextNode
            float r5 = r5.x
            float r13 = r4 + r5
            r4 = r3[r12]
            int r4 = r4.intValue()
            float r4 = (float) r4
            android.graphics.PointF r5 = r0.nextNode
            float r5 = r5.y
            float r14 = r4 + r5
            android.graphics.PointF r4 = r0.nextNode
            float r15 = r4.x
            android.graphics.PointF r4 = r0.nextNode
            float r4 = r4.y
            com.app.office.common.shape.Arrow r17 = r25.getStartArrow()
            r16 = r4
            r18 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r13, r14, r15, r16, r17, r18)
            goto L_0x0128
        L_0x00bd:
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r13 = (float) r5
            r5 = r3[r8]
            int r5 = r5.intValue()
            float r14 = (float) r5
            r5 = r3[r11]
            int r5 = r5.intValue()
            float r15 = (float) r5
            r5 = r3[r9]
            int r5 = r5.intValue()
            float r5 = (float) r5
            r4 = r3[r4]
            int r4 = r4.intValue()
            float r4 = (float) r4
            r16 = r3[r12]
            int r6 = r16.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            android.graphics.PointF r9 = r0.nextNode
            float r9 = r9.y
            com.app.office.common.shape.Arrow r21 = r25.getStartArrow()
            r16 = r5
            r17 = r4
            r18 = r6
            r19 = r8
            r20 = r9
            r22 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            goto L_0x0128
        L_0x0104:
            r4 = r3[r4]
            int r4 = r4.intValue()
            float r13 = (float) r4
            r4 = r3[r12]
            int r4 = r4.intValue()
            float r14 = (float) r4
            android.graphics.PointF r4 = r0.nextNode
            float r15 = r4.x
            android.graphics.PointF r4 = r0.nextNode
            float r4 = r4.y
            com.app.office.common.shape.Arrow r17 = r25.getStartArrow()
            r16 = r4
            r18 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r13, r14, r15, r16, r17, r18)
            goto L_0x0128
        L_0x0127:
            r4 = 0
        L_0x0128:
            byte r5 = r0.currentNodeType
            byte r6 = NodeType_End
            if (r5 != r6) goto L_0x03b6
            if (r25 == 0) goto L_0x03b6
            boolean r5 = r25.getEndArrowhead()
            if (r5 == 0) goto L_0x03b6
            int r5 = r3.length
            if (r2 == r12) goto L_0x035e
            if (r2 == r11) goto L_0x02ad
            if (r2 == r10) goto L_0x0233
            if (r2 == r7) goto L_0x0141
            goto L_0x03b6
        L_0x0141:
            if (r5 <= r10) goto L_0x01c6
            int r6 = r5 + -8
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r13 = r6 + r8
            int r6 = r5 + -7
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.y
            float r14 = r6 + r8
            int r6 = r5 + -6
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r15 = r6 + r8
            int r6 = r5 + -5
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.y
            float r16 = r6 + r8
            int r6 = r5 + -4
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r17 = r6 + r8
            int r6 = r5 + -3
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.y
            float r18 = r6 + r8
            int r6 = r5 + -2
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r19 = r6 + r8
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r6 = r0.nextNode
            float r6 = r6.y
            float r20 = r5 + r6
            com.app.office.common.shape.Arrow r21 = r25.getEndArrow()
            r22 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            goto L_0x03b7
        L_0x01c6:
            android.graphics.PointF r6 = r0.nextNode
            float r13 = r6.x
            android.graphics.PointF r6 = r0.nextNode
            float r14 = r6.y
            int r6 = r5 + -6
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r15 = r6 + r8
            int r6 = r5 + -5
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.y
            float r16 = r6 + r8
            int r6 = r5 + -4
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r17 = r6 + r8
            int r6 = r5 + -3
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.y
            float r18 = r6 + r8
            int r6 = r5 + -2
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r19 = r6 + r8
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r6 = r0.nextNode
            float r6 = r6.y
            float r20 = r5 + r6
            com.app.office.common.shape.Arrow r21 = r25.getEndArrow()
            r22 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            goto L_0x03b7
        L_0x0233:
            if (r5 <= r11) goto L_0x027c
            int r6 = r5 + -4
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r13 = r6 + r8
            int r6 = r5 + -3
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.y
            float r14 = r6 + r8
            int r6 = r5 + -2
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r15 = r6 + r8
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r6 = r0.nextNode
            float r6 = r6.y
            float r16 = r5 + r6
            com.app.office.common.shape.Arrow r17 = r25.getEndArrow()
            r18 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r13, r14, r15, r16, r17, r18)
            goto L_0x03b7
        L_0x027c:
            android.graphics.PointF r6 = r0.nextNode
            float r13 = r6.x
            android.graphics.PointF r6 = r0.nextNode
            float r14 = r6.y
            int r6 = r5 + -2
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            android.graphics.PointF r8 = r0.nextNode
            float r8 = r8.x
            float r15 = r6 + r8
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            android.graphics.PointF r6 = r0.nextNode
            float r6 = r6.y
            float r16 = r5 + r6
            com.app.office.common.shape.Arrow r17 = r25.getEndArrow()
            r18 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r13, r14, r15, r16, r17, r18)
            goto L_0x03b7
        L_0x02ad:
            if (r5 <= r10) goto L_0x030c
            int r6 = r5 + -8
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r13 = (float) r6
            int r6 = r5 + -7
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r14 = (float) r6
            int r6 = r5 + -6
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r15 = (float) r6
            int r6 = r5 + -5
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            int r8 = r5 + -4
            r8 = r3[r8]
            int r8 = r8.intValue()
            float r8 = (float) r8
            int r9 = r5 + -3
            r9 = r3[r9]
            int r9 = r9.intValue()
            float r9 = (float) r9
            int r16 = r5 + -2
            r16 = r3[r16]
            int r7 = r16.intValue()
            float r7 = (float) r7
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            com.app.office.common.shape.Arrow r21 = r25.getEndArrow()
            r16 = r6
            r17 = r8
            r18 = r9
            r19 = r7
            r20 = r5
            r22 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            goto L_0x03b7
        L_0x030c:
            android.graphics.PointF r6 = r0.nextNode
            float r13 = r6.x
            android.graphics.PointF r6 = r0.nextNode
            float r14 = r6.y
            int r6 = r5 + -6
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r15 = (float) r6
            int r6 = r5 + -5
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r6 = (float) r6
            int r7 = r5 + -4
            r7 = r3[r7]
            int r7 = r7.intValue()
            float r7 = (float) r7
            int r8 = r5 + -3
            r8 = r3[r8]
            int r8 = r8.intValue()
            float r8 = (float) r8
            int r9 = r5 + -2
            r9 = r3[r9]
            int r9 = r9.intValue()
            float r9 = (float) r9
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            com.app.office.common.shape.Arrow r21 = r25.getEndArrow()
            r16 = r6
            r17 = r7
            r18 = r8
            r19 = r9
            r20 = r5
            r22 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getCubicBezArrowPath(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            goto L_0x03b7
        L_0x035e:
            if (r5 <= r11) goto L_0x0390
            int r6 = r5 + -4
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r13 = (float) r6
            int r6 = r5 + -3
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r14 = (float) r6
            int r6 = r5 + -2
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r15 = (float) r6
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            com.app.office.common.shape.Arrow r17 = r25.getEndArrow()
            r16 = r5
            r18 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r13, r14, r15, r16, r17, r18)
            goto L_0x03b7
        L_0x0390:
            android.graphics.PointF r6 = r0.nextNode
            float r13 = r6.x
            android.graphics.PointF r6 = r0.nextNode
            float r14 = r6.y
            int r6 = r5 + -2
            r6 = r3[r6]
            int r6 = r6.intValue()
            float r15 = (float) r6
            int r5 = r5 - r12
            r5 = r3[r5]
            int r5 = r5.intValue()
            float r5 = (float) r5
            com.app.office.common.shape.Arrow r17 = r25.getEndArrow()
            r16 = r5
            r18 = r26
            com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getDirectLineArrowPath(r13, r14, r15, r16, r17, r18)
            goto L_0x03b7
        L_0x03b6:
            r6 = 0
        L_0x03b7:
            if (r4 == 0) goto L_0x03e5
            android.graphics.Path r5 = r4.getArrowPath()
            r0.startArrowPath = r5
            r27.reset()
            android.graphics.PointF r5 = r0.nextNode
            float r5 = r5.x
            android.graphics.PointF r7 = r0.nextNode
            float r7 = r7.y
            android.graphics.PointF r8 = r4.getArrowTailCenter()
            float r8 = r8.x
            android.graphics.PointF r4 = r4.getArrowTailCenter()
            float r4 = r4.y
            byte r9 = r25.getStartArrowType()
            android.graphics.PointF r4 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r5, r7, r8, r4, r9)
            float r5 = r4.x
            float r4 = r4.y
            r1.moveTo(r5, r4)
        L_0x03e5:
            if (r6 == 0) goto L_0x0425
            android.graphics.Path r4 = r6.getArrowPath()
            r0.endArrowPath = r4
            int r4 = r3.length
            int r5 = r4 + -2
            r7 = r3[r5]
            int r7 = r7.intValue()
            float r7 = (float) r7
            int r4 = r4 - r12
            r8 = r3[r4]
            int r8 = r8.intValue()
            float r8 = (float) r8
            android.graphics.PointF r9 = r6.getArrowTailCenter()
            float r9 = r9.x
            android.graphics.PointF r6 = r6.getArrowTailCenter()
            float r6 = r6.y
            byte r13 = r25.getEndArrowType()
            android.graphics.PointF r6 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r7, r8, r9, r6, r13)
            float r7 = r6.x
            int r7 = (int) r7
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r3[r5] = r7
            float r5 = r6.y
            int r5 = (int) r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r3[r4] = r5
        L_0x0425:
            if (r2 == 0) goto L_0x044f
            if (r2 == r12) goto L_0x044b
            if (r2 == r11) goto L_0x0447
            r4 = 3
            if (r2 == r4) goto L_0x0443
            r4 = 5
            if (r2 == r4) goto L_0x043f
            if (r2 == r10) goto L_0x043b
            r4 = 7
            if (r2 == r4) goto L_0x0437
            goto L_0x0452
        L_0x0437:
            r0.processCommand_rCurveTo(r1, r3)
            goto L_0x0452
        L_0x043b:
            r0.processCommand_rLineTo(r1, r3)
            goto L_0x0452
        L_0x043f:
            r0.processCommand_rMoveTo(r1, r3)
            goto L_0x0452
        L_0x0443:
            r27.close()
            goto L_0x0452
        L_0x0447:
            r0.processCommand_CurveTo(r1, r3)
            goto L_0x0452
        L_0x044b:
            r0.processCommand_LineTo(r1, r3)
            goto L_0x0452
        L_0x044f:
            r0.processCommand_MoveTo(r1, r3)
        L_0x0452:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.doc.VMLPathParser.processPath(com.app.office.common.shape.WPAutoShape, int, android.graphics.Path, byte, java.lang.Integer[]):void");
    }

    private void processCommand_MoveTo(Path path, Integer[] numArr) {
        float f;
        float f2 = 0.0f;
        if (numArr.length == 2) {
            f2 = (float) numArr[0].intValue();
            f = (float) numArr[1].intValue();
        } else {
            if (numArr.length == 1) {
                f2 = (float) numArr[0].intValue();
            }
            f = 0.0f;
        }
        path.moveTo(f2, f);
        this.nextNode.set(f2, f);
    }

    private void processCommand_LineTo(Path path, Integer[] numArr) {
        for (int i = 0; i < numArr.length - 1; i += 2) {
            int i2 = i + 1;
            path.lineTo((float) numArr[i].intValue(), (float) numArr[i2].intValue());
            this.preNode.set(this.nextNode);
            this.nextNode.set((float) numArr[i].intValue(), (float) numArr[i2].intValue());
        }
    }

    private void processCommand_CurveTo(Path path, Integer[] numArr) {
        for (int i = 0; i < numArr.length - 5; i += 6) {
            int i2 = i + 1;
            int i3 = i + 2;
            int i4 = i + 3;
            int i5 = i + 4;
            int i6 = i + 5;
            path.cubicTo((float) numArr[i].intValue(), (float) numArr[i2].intValue(), (float) numArr[i3].intValue(), (float) numArr[i4].intValue(), (float) numArr[i5].intValue(), (float) numArr[i6].intValue());
            this.preNode.set(this.nextNode);
            this.ctrNode1.set((float) numArr[i].intValue(), (float) numArr[i2].intValue());
            this.ctrNode2.set((float) numArr[i3].intValue(), (float) numArr[i4].intValue());
            this.nextNode.set((float) numArr[i5].intValue(), (float) numArr[i6].intValue());
        }
    }

    private void processCommand_rMoveTo(Path path, Integer[] numArr) {
        if (numArr.length == 2) {
            path.rMoveTo((float) numArr[0].intValue(), (float) numArr[1].intValue());
            this.preNode.set(this.nextNode);
            this.nextNode.offset((float) numArr[0].intValue(), (float) numArr[1].intValue());
        } else if (numArr.length == 1) {
            path.rMoveTo((float) numArr[0].intValue(), 0.0f);
            this.preNode.set(this.nextNode);
            this.nextNode.offset((float) numArr[0].intValue(), 0.0f);
        } else {
            path.rMoveTo(0.0f, 0.0f);
            this.preNode.set(this.nextNode);
            this.nextNode.offset(0.0f, 0.0f);
        }
    }

    private void processCommand_rLineTo(Path path, Integer[] numArr) {
        for (int i = 0; i < numArr.length - 1; i += 2) {
            int i2 = i + 1;
            path.rLineTo((float) numArr[i].intValue(), (float) numArr[i2].intValue());
            this.preNode.set(this.nextNode);
            this.nextNode.offset((float) numArr[i].intValue(), (float) numArr[i2].intValue());
        }
    }

    private void processCommand_rCurveTo(Path path, Integer[] numArr) {
        for (int i = 0; i < numArr.length - 5; i += 6) {
            int i2 = i + 1;
            int i3 = i + 2;
            int i4 = i + 3;
            int i5 = i + 4;
            int i6 = i + 5;
            path.rCubicTo((float) numArr[i].intValue(), (float) numArr[i2].intValue(), (float) numArr[i3].intValue(), (float) numArr[i4].intValue(), (float) numArr[i5].intValue(), (float) numArr[i6].intValue());
            this.preNode.set(this.nextNode);
            this.ctrNode1.offset((float) numArr[i].intValue(), (float) numArr[i2].intValue());
            this.ctrNode2.offset((float) numArr[i3].intValue(), (float) numArr[i4].intValue());
            this.nextNode.offset((float) numArr[i5].intValue(), (float) numArr[i6].intValue());
        }
    }
}
