package com.app.office.common.autoshape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.PaintKit;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.AutoShape;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.pg.animate.ShapeAnimation;
import com.app.office.system.IControl;

public class AutoShapeKit {
    public static int ARROW_WIDTH = 10;
    private static final AutoShapeKit kit = new AutoShapeKit();
    private static Matrix m = new Matrix();
    private static Rect rect = new Rect();

    public static AutoShapeKit instance() {
        return kit;
    }

    private IAnimation getShapeAnimation(AutoShape autoShape) {
        IAnimation animation = autoShape.getAnimation();
        if (animation == null) {
            return null;
        }
        ShapeAnimation shapeAnimation = animation.getShapeAnimation();
        int paragraphBegin = shapeAnimation.getParagraphBegin();
        int paragraphEnd = shapeAnimation.getParagraphEnd();
        if ((paragraphBegin == -2 && paragraphEnd == -2) || (paragraphBegin == -1 && paragraphEnd == -1)) {
            return animation;
        }
        return null;
    }

    private void processShapeRect(Rect rect2, IAnimation iAnimation) {
        if (iAnimation != null) {
            int width = rect2.width();
            int height = rect2.height();
            float alpha = (((float) iAnimation.getCurrentAnimationInfor().getAlpha()) / 255.0f) * 0.5f;
            float centerX = (float) rect2.centerX();
            float f = ((float) width) * alpha;
            float centerY = (float) rect2.centerY();
            float f2 = ((float) height) * alpha;
            rect2.set((int) (centerX - f), (int) (centerY - f2), (int) (centerX + f), (int) (centerY + f2));
        }
    }

    public void drawAutoShape(Canvas canvas, IControl iControl, int i, AutoShape autoShape, float f) {
        Rectangle bounds = autoShape.getBounds();
        int round = Math.round(((float) bounds.x) * f);
        int round2 = Math.round(((float) bounds.y) * f);
        rect.set(round, round2, Math.round(((float) bounds.width) * f) + round, Math.round(((float) bounds.height) * f) + round2);
        drawAutoShape(canvas, iControl, i, autoShape, rect, f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0167, code lost:
        com.app.office.common.autoshape.pathbuilder.flowChart.FlowChartDrawing.instance().drawFlowChart(r18, r19, r20, r21, r22, r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x017c, code lost:
        r14 = com.app.office.common.autoshape.pathbuilder.starAndBanner.BannerPathBuilder.getFlagExtendPath(r21, r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0180, code lost:
        if (r14 != null) goto L_0x0183;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0182, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0183, code lost:
        r15 = r14.size();
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0188, code lost:
        if (r8 >= r15) goto L_0x02e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x018a, code lost:
        drawShape(r18, r19, r20, r21, r14.get(r8), r22, r13, r23);
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x01a8, code lost:
        r0 = com.app.office.common.autoshape.pathbuilder.wedgecallout.WedgeCalloutDrawing.instance().getWedgeCalloutPath(r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x01b2, code lost:
        if ((r0 instanceof android.graphics.Path) == false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x01b4, code lost:
        drawShape(r18, r19, r20, r21, (android.graphics.Path) r0, r22, r13, r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x01cb, code lost:
        r14 = (java.util.List) r0;
        r15 = r14.size();
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x01d3, code lost:
        if (r8 >= r15) goto L_0x02e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x01d5, code lost:
        drawShape(r18, r19, r20, r21, (com.app.office.common.autoshape.ExtendPath) r14.get(r8), r22, r13, r23);
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01f5, code lost:
        if ((r10 instanceof com.app.office.common.shape.LineShape) == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01f7, code lost:
        r14 = com.app.office.common.autoshape.pathbuilder.line.LinePathBuilder.getLinePath((com.app.office.common.shape.LineShape) r10, r11, r12);
        r15 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0203, code lost:
        if (r15 >= r14.size()) goto L_0x02e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0205, code lost:
        drawShape(r18, r19, r20, r21, new com.app.office.common.autoshape.ExtendPath(r14.get(r15)), r22, r13, r23);
        r15 = r15 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0225, code lost:
        r5 = com.app.office.common.autoshape.pathbuilder.starAndBanner.star.StarPathBuilder.getStarPath(r21, r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0229, code lost:
        if (r5 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x022b, code lost:
        drawShape(r18, r19, r20, r21, r5, r22, r13, r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x023f, code lost:
        drawShape(r18, r19, r20, r21, com.app.office.common.autoshape.pathbuilder.rect.RectPathBuilder.getRectPath(r21, r22), r22, r13, r23);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawAutoShape(android.graphics.Canvas r18, com.app.office.system.IControl r19, int r20, com.app.office.common.shape.AutoShape r21, android.graphics.Rect r22, float r23) {
        /*
            r17 = this;
            r9 = r17
            r10 = r21
            r11 = r22
            r12 = r23
            com.app.office.pg.animate.IAnimation r13 = r9.getShapeAnimation(r10)
            r9.processShapeRect(r11, r13)
            int r0 = r21.getShapeType()
            r1 = 15
            r2 = 0
            if (r0 == r1) goto L_0x02a0
            r1 = 16
            if (r0 == r1) goto L_0x0257
            switch(r0) {
                case 1: goto L_0x023f;
                case 2: goto L_0x023f;
                case 3: goto L_0x0257;
                case 4: goto L_0x0257;
                case 5: goto L_0x0257;
                case 6: goto L_0x0257;
                case 7: goto L_0x0257;
                case 8: goto L_0x0257;
                case 9: goto L_0x0257;
                case 10: goto L_0x0257;
                case 11: goto L_0x0257;
                case 12: goto L_0x0225;
                case 13: goto L_0x02a0;
                default: goto L_0x001f;
            }
        L_0x001f:
            switch(r0) {
                case 19: goto L_0x0257;
                case 20: goto L_0x01f3;
                case 21: goto L_0x0257;
                case 22: goto L_0x0257;
                case 23: goto L_0x0257;
                default: goto L_0x0022;
            }
        L_0x0022:
            switch(r0) {
                case 32: goto L_0x01f3;
                case 33: goto L_0x01f3;
                case 34: goto L_0x01f3;
                default: goto L_0x0025;
            }
        L_0x0025:
            switch(r0) {
                case 37: goto L_0x01f3;
                case 38: goto L_0x01f3;
                case 39: goto L_0x01f3;
                case 40: goto L_0x01f3;
                case 41: goto L_0x01a8;
                case 42: goto L_0x01a8;
                case 43: goto L_0x01a8;
                case 44: goto L_0x01a8;
                case 45: goto L_0x01a8;
                case 46: goto L_0x01a8;
                case 47: goto L_0x01a8;
                case 48: goto L_0x01a8;
                case 49: goto L_0x01a8;
                case 50: goto L_0x01a8;
                case 51: goto L_0x01a8;
                case 52: goto L_0x01a8;
                case 53: goto L_0x017c;
                case 54: goto L_0x017c;
                case 55: goto L_0x02a0;
                case 56: goto L_0x0257;
                case 57: goto L_0x0257;
                case 58: goto L_0x0225;
                case 59: goto L_0x0225;
                case 60: goto L_0x0225;
                case 61: goto L_0x01a8;
                case 62: goto L_0x01a8;
                case 63: goto L_0x01a8;
                case 64: goto L_0x017c;
                case 65: goto L_0x0257;
                case 66: goto L_0x02a0;
                case 67: goto L_0x02a0;
                case 68: goto L_0x02a0;
                case 69: goto L_0x02a0;
                case 70: goto L_0x02a0;
                case 71: goto L_0x0225;
                case 72: goto L_0x0225;
                case 73: goto L_0x0257;
                case 74: goto L_0x0257;
                default: goto L_0x0028;
            }
        L_0x0028:
            switch(r0) {
                case 76: goto L_0x02a0;
                case 77: goto L_0x02a0;
                case 78: goto L_0x02a0;
                case 79: goto L_0x02a0;
                case 80: goto L_0x02a0;
                case 81: goto L_0x02a0;
                case 82: goto L_0x02a0;
                case 83: goto L_0x02a0;
                case 84: goto L_0x0257;
                case 85: goto L_0x0257;
                case 86: goto L_0x0257;
                case 87: goto L_0x0257;
                case 88: goto L_0x0257;
                case 89: goto L_0x02a0;
                case 90: goto L_0x02a0;
                case 91: goto L_0x02a0;
                case 92: goto L_0x0225;
                case 93: goto L_0x02a0;
                case 94: goto L_0x02a0;
                case 95: goto L_0x0257;
                case 96: goto L_0x0257;
                case 97: goto L_0x017c;
                case 98: goto L_0x017c;
                case 99: goto L_0x02a0;
                default: goto L_0x002b;
            }
        L_0x002b:
            switch(r0) {
                case 101: goto L_0x02a0;
                case 102: goto L_0x02a0;
                case 103: goto L_0x02a0;
                case 104: goto L_0x02a0;
                case 105: goto L_0x02a0;
                case 106: goto L_0x01a8;
                case 107: goto L_0x017c;
                case 108: goto L_0x017c;
                case 109: goto L_0x0167;
                case 110: goto L_0x0167;
                case 111: goto L_0x0167;
                case 112: goto L_0x0167;
                case 113: goto L_0x0167;
                case 114: goto L_0x0167;
                case 115: goto L_0x0167;
                case 116: goto L_0x0167;
                case 117: goto L_0x0167;
                case 118: goto L_0x0167;
                case 119: goto L_0x0167;
                case 120: goto L_0x0167;
                case 121: goto L_0x0167;
                case 122: goto L_0x0167;
                case 123: goto L_0x0167;
                case 124: goto L_0x0167;
                case 125: goto L_0x0167;
                case 126: goto L_0x0167;
                case 127: goto L_0x0167;
                case 128: goto L_0x0167;
                default: goto L_0x002e;
            }
        L_0x002e:
            switch(r0) {
                case 130: goto L_0x0167;
                case 131: goto L_0x0167;
                case 132: goto L_0x0167;
                case 133: goto L_0x0167;
                case 134: goto L_0x0167;
                case 135: goto L_0x0167;
                case 136: goto L_0x023f;
                default: goto L_0x0031;
            }
        L_0x0031:
            switch(r0) {
                case 176: goto L_0x0167;
                case 177: goto L_0x0167;
                case 178: goto L_0x01a8;
                case 179: goto L_0x01a8;
                case 180: goto L_0x01a8;
                case 181: goto L_0x01a8;
                case 182: goto L_0x02a0;
                case 183: goto L_0x0257;
                case 184: goto L_0x0257;
                case 185: goto L_0x0257;
                case 186: goto L_0x0257;
                case 187: goto L_0x0225;
                case 188: goto L_0x017c;
                case 189: goto L_0x013b;
                case 190: goto L_0x013b;
                case 191: goto L_0x013b;
                case 192: goto L_0x013b;
                case 193: goto L_0x013b;
                case 194: goto L_0x013b;
                case 195: goto L_0x013b;
                case 196: goto L_0x013b;
                case 197: goto L_0x013b;
                case 198: goto L_0x013b;
                case 199: goto L_0x013b;
                case 200: goto L_0x013b;
                default: goto L_0x0034;
            }
        L_0x0034:
            switch(r0) {
                case 202: goto L_0x023f;
                case 203: goto L_0x0037;
                case 204: goto L_0x0037;
                case 205: goto L_0x0037;
                case 206: goto L_0x0037;
                case 207: goto L_0x0037;
                case 208: goto L_0x0037;
                case 209: goto L_0x0037;
                case 210: goto L_0x023f;
                case 211: goto L_0x023f;
                case 212: goto L_0x023f;
                case 213: goto L_0x023f;
                case 214: goto L_0x023f;
                case 215: goto L_0x023f;
                case 216: goto L_0x023f;
                case 217: goto L_0x0257;
                case 218: goto L_0x0257;
                case 219: goto L_0x0257;
                case 220: goto L_0x0257;
                case 221: goto L_0x0257;
                case 222: goto L_0x0257;
                case 223: goto L_0x0257;
                case 224: goto L_0x0257;
                case 225: goto L_0x0257;
                case 226: goto L_0x0257;
                case 227: goto L_0x0123;
                case 228: goto L_0x0123;
                case 229: goto L_0x0123;
                case 230: goto L_0x0123;
                case 231: goto L_0x0123;
                case 232: goto L_0x0123;
                case 233: goto L_0x00d5;
                case 234: goto L_0x0257;
                case 235: goto L_0x0225;
                case 236: goto L_0x0225;
                case 237: goto L_0x0225;
                case 238: goto L_0x0225;
                case 239: goto L_0x0225;
                case 240: goto L_0x00bb;
                case 241: goto L_0x00bb;
                case 242: goto L_0x00bb;
                case 243: goto L_0x00bb;
                case 244: goto L_0x017c;
                case 245: goto L_0x00bb;
                case 246: goto L_0x00bb;
                case 247: goto L_0x0039;
                case 248: goto L_0x0039;
                case 249: goto L_0x0039;
                default: goto L_0x0037;
            }
        L_0x0037:
            goto L_0x02e6
        L_0x0039:
            android.graphics.Matrix r0 = m
            r0.reset()
            android.graphics.Matrix r0 = m
            r0.postScale(r12, r12)
            r0 = r10
            com.app.office.common.shape.WPAutoShape r0 = (com.app.office.common.shape.WPAutoShape) r0
            java.util.List r14 = r0.getPaths()
            android.graphics.Rect r15 = new android.graphics.Rect
            r15.<init>(r11)
            int r0 = r22.width()
            if (r0 == 0) goto L_0x005b
            int r0 = r22.height()
            if (r0 != 0) goto L_0x007d
        L_0x005b:
            android.graphics.RectF r0 = new android.graphics.RectF
            r0.<init>()
            java.lang.Object r1 = r14.get(r2)
            com.app.office.common.autoshape.ExtendPath r1 = (com.app.office.common.autoshape.ExtendPath) r1
            android.graphics.Path r1 = r1.getPath()
            r3 = 1
            r1.computeBounds(r0, r3)
            float r1 = r0.left
            int r1 = (int) r1
            float r3 = r0.top
            int r3 = (int) r3
            float r4 = r0.right
            int r4 = (int) r4
            float r0 = r0.bottom
            int r0 = (int) r0
            r15.set(r1, r3, r4, r0)
        L_0x007d:
            r8 = 0
        L_0x007e:
            int r0 = r14.size()
            if (r8 >= r0) goto L_0x02e6
            com.app.office.common.autoshape.ExtendPath r5 = new com.app.office.common.autoshape.ExtendPath
            java.lang.Object r0 = r14.get(r8)
            com.app.office.common.autoshape.ExtendPath r0 = (com.app.office.common.autoshape.ExtendPath) r0
            r5.<init>(r0)
            android.graphics.Path r0 = r5.getPath()
            android.graphics.Matrix r1 = m
            r0.transform(r1)
            android.graphics.Path r0 = r5.getPath()
            int r1 = r11.left
            float r1 = (float) r1
            int r2 = r11.top
            float r2 = (float) r2
            r0.offset(r1, r2)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r15
            r7 = r13
            r16 = r8
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (com.app.office.common.autoshape.ExtendPath) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r8 = r16 + 1
            goto L_0x007e
        L_0x00bb:
            android.graphics.Path r5 = com.app.office.common.autoshape.pathbuilder.smartArt.SmartArtPathBuilder.getStarPath(r21, r22)
            if (r5 == 0) goto L_0x02e6
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            goto L_0x02e6
        L_0x00d5:
            android.graphics.Matrix r0 = m
            r0.reset()
            android.graphics.Matrix r0 = m
            r0.postScale(r12, r12)
            r0 = r10
            com.app.office.common.shape.ArbitraryPolygonShape r0 = (com.app.office.common.shape.ArbitraryPolygonShape) r0
            java.util.List r14 = r0.getPaths()
            r15 = 0
        L_0x00e7:
            int r0 = r14.size()
            if (r15 >= r0) goto L_0x02e6
            com.app.office.common.autoshape.ExtendPath r5 = new com.app.office.common.autoshape.ExtendPath
            java.lang.Object r0 = r14.get(r15)
            com.app.office.common.autoshape.ExtendPath r0 = (com.app.office.common.autoshape.ExtendPath) r0
            r5.<init>(r0)
            android.graphics.Path r0 = r5.getPath()
            android.graphics.Matrix r1 = m
            r0.transform(r1)
            android.graphics.Path r0 = r5.getPath()
            int r1 = r11.left
            float r1 = (float) r1
            int r2 = r11.top
            float r2 = (float) r2
            r0.offset(r1, r2)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (com.app.office.common.autoshape.ExtendPath) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r15 = r15 + 1
            goto L_0x00e7
        L_0x0123:
            android.graphics.Path r5 = com.app.office.common.autoshape.pathbuilder.math.MathPathBuilder.getMathPath(r21, r22)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            goto L_0x02e6
        L_0x013b:
            java.util.List r14 = com.app.office.common.autoshape.pathbuilder.actionButton.ActionButtonPathBuilder.getActionButtonExtendPath(r21, r22)
            if (r14 != 0) goto L_0x0142
            return
        L_0x0142:
            int r15 = r14.size()
            r8 = 0
        L_0x0147:
            if (r8 >= r15) goto L_0x02e6
            java.lang.Object r0 = r14.get(r8)
            r5 = r0
            com.app.office.common.autoshape.ExtendPath r5 = (com.app.office.common.autoshape.ExtendPath) r5
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r16 = r8
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (com.app.office.common.autoshape.ExtendPath) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r8 = r16 + 1
            goto L_0x0147
        L_0x0167:
            com.app.office.common.autoshape.pathbuilder.flowChart.FlowChartDrawing r0 = com.app.office.common.autoshape.pathbuilder.flowChart.FlowChartDrawing.instance()
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r6 = r23
            r0.drawFlowChart(r1, r2, r3, r4, r5, r6)
            goto L_0x02e6
        L_0x017c:
            java.util.List r14 = com.app.office.common.autoshape.pathbuilder.starAndBanner.BannerPathBuilder.getFlagExtendPath(r21, r22)
            if (r14 != 0) goto L_0x0183
            return
        L_0x0183:
            int r15 = r14.size()
            r8 = 0
        L_0x0188:
            if (r8 >= r15) goto L_0x02e6
            java.lang.Object r0 = r14.get(r8)
            r5 = r0
            com.app.office.common.autoshape.ExtendPath r5 = (com.app.office.common.autoshape.ExtendPath) r5
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r16 = r8
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (com.app.office.common.autoshape.ExtendPath) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r8 = r16 + 1
            goto L_0x0188
        L_0x01a8:
            com.app.office.common.autoshape.pathbuilder.wedgecallout.WedgeCalloutDrawing r0 = com.app.office.common.autoshape.pathbuilder.wedgecallout.WedgeCalloutDrawing.instance()
            java.lang.Object r0 = r0.getWedgeCalloutPath(r10, r11)
            boolean r1 = r0 instanceof android.graphics.Path
            if (r1 == 0) goto L_0x01cb
            r5 = r0
            android.graphics.Path r5 = (android.graphics.Path) r5
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            goto L_0x02e6
        L_0x01cb:
            r14 = r0
            java.util.List r14 = (java.util.List) r14
            int r15 = r14.size()
            r8 = 0
        L_0x01d3:
            if (r8 >= r15) goto L_0x02e6
            java.lang.Object r0 = r14.get(r8)
            r5 = r0
            com.app.office.common.autoshape.ExtendPath r5 = (com.app.office.common.autoshape.ExtendPath) r5
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r16 = r8
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (com.app.office.common.autoshape.ExtendPath) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r8 = r16 + 1
            goto L_0x01d3
        L_0x01f3:
            boolean r0 = r10 instanceof com.app.office.common.shape.LineShape
            if (r0 == 0) goto L_0x02e6
            r0 = r10
            com.app.office.common.shape.LineShape r0 = (com.app.office.common.shape.LineShape) r0
            java.util.List r14 = com.app.office.common.autoshape.pathbuilder.line.LinePathBuilder.getLinePath(r0, r11, r12)
            r15 = 0
        L_0x01ff:
            int r0 = r14.size()
            if (r15 >= r0) goto L_0x02e6
            com.app.office.common.autoshape.ExtendPath r5 = new com.app.office.common.autoshape.ExtendPath
            java.lang.Object r0 = r14.get(r15)
            com.app.office.common.autoshape.ExtendPath r0 = (com.app.office.common.autoshape.ExtendPath) r0
            r5.<init>(r0)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (com.app.office.common.autoshape.ExtendPath) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r15 = r15 + 1
            goto L_0x01ff
        L_0x0225:
            android.graphics.Path r5 = com.app.office.common.autoshape.pathbuilder.starAndBanner.star.StarPathBuilder.getStarPath(r21, r22)
            if (r5 == 0) goto L_0x02e6
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            goto L_0x02e6
        L_0x023f:
            android.graphics.Path r5 = com.app.office.common.autoshape.pathbuilder.rect.RectPathBuilder.getRectPath(r21, r22)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            goto L_0x02e6
        L_0x0257:
            java.lang.Object r0 = com.app.office.common.autoshape.pathbuilder.baseshape.BaseShapePathBuilder.getBaseShapePath(r21, r22)
            boolean r1 = r0 instanceof android.graphics.Path
            if (r1 == 0) goto L_0x0276
            r5 = r0
            android.graphics.Path r5 = (android.graphics.Path) r5
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            goto L_0x02e6
        L_0x0276:
            r14 = r0
            java.util.List r14 = (java.util.List) r14
            r15 = 0
        L_0x027a:
            int r0 = r14.size()
            if (r15 >= r0) goto L_0x02e6
            com.app.office.common.autoshape.ExtendPath r5 = new com.app.office.common.autoshape.ExtendPath
            java.lang.Object r0 = r14.get(r15)
            com.app.office.common.autoshape.ExtendPath r0 = (com.app.office.common.autoshape.ExtendPath) r0
            r5.<init>(r0)
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (com.app.office.common.autoshape.ExtendPath) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r15 = r15 + 1
            goto L_0x027a
        L_0x02a0:
            java.lang.Object r0 = com.app.office.common.autoshape.pathbuilder.arrow.ArrowPathBuilder.getArrowPath(r21, r22)
            boolean r1 = r0 instanceof android.graphics.Path
            if (r1 == 0) goto L_0x02be
            r5 = r0
            android.graphics.Path r5 = (android.graphics.Path) r5
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            goto L_0x02e6
        L_0x02be:
            r14 = r0
            java.util.List r14 = (java.util.List) r14
            int r15 = r14.size()
            r8 = 0
        L_0x02c6:
            if (r8 >= r15) goto L_0x02e6
            java.lang.Object r0 = r14.get(r8)
            r5 = r0
            android.graphics.Path r5 = (android.graphics.Path) r5
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r13
            r16 = r8
            r8 = r23
            r0.drawShape((android.graphics.Canvas) r1, (com.app.office.system.IControl) r2, (int) r3, (com.app.office.common.shape.AutoShape) r4, (android.graphics.Path) r5, (android.graphics.Rect) r6, (com.app.office.pg.animate.IAnimation) r7, (float) r8)
            int r8 = r16 + 1
            goto L_0x02c6
        L_0x02e6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.AutoShapeKit.drawAutoShape(android.graphics.Canvas, com.app.office.system.IControl, int, com.app.office.common.shape.AutoShape, android.graphics.Rect, float):void");
    }

    private void processCanvas(Canvas canvas, Rect rect2, float f, boolean z, boolean z2, IAnimation iAnimation) {
        if (iAnimation != null) {
            f += (float) iAnimation.getCurrentAnimationInfor().getAngle();
        }
        if (z2) {
            canvas.translate((float) rect2.left, (float) rect2.bottom);
            canvas.scale(1.0f, -1.0f);
            canvas.translate((float) (-rect2.left), (float) (-rect2.top));
            f = -f;
        }
        if (z) {
            canvas.translate((float) rect2.right, (float) rect2.top);
            canvas.scale(-1.0f, 1.0f);
            canvas.translate((float) (-rect2.left), (float) (-rect2.top));
            f = -f;
        }
        if (f != 0.0f) {
            canvas.rotate(f, (float) rect2.centerX(), (float) rect2.centerY());
        }
    }

    public void drawShape(Canvas canvas, IControl iControl, int i, AutoShape autoShape, ExtendPath extendPath, Rect rect2, IAnimation iAnimation, float f) {
        canvas.save();
        Paint paint = PaintKit.instance().getPaint();
        processCanvas(canvas, rect2, autoShape.getRotation(), autoShape.getFlipHorizontal(), autoShape.getFlipVertical(), iAnimation);
        int alpha = paint.getAlpha();
        BackgroundAndFill backgroundAndFill = extendPath.getBackgroundAndFill();
        if (backgroundAndFill != null) {
            paint.setStyle(Paint.Style.FILL);
            BackgroundDrawer.drawPathBackground(canvas, iControl, i, backgroundAndFill, rect2, iAnimation, f, extendPath.getPath(), paint);
            paint.setAlpha(alpha);
        }
        if (extendPath.hasLine()) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(((float) extendPath.getLine().getLineWidth()) * f);
            if (extendPath.getLine().isDash() && !extendPath.isArrowPath()) {
                float f2 = 5.0f * f;
                paint.setPathEffect(new DashPathEffect(new float[]{f2, f2}, 10.0f));
            }
            BackgroundDrawer.drawPathBackground(canvas, iControl, i, extendPath.getLine().getBackgroundAndFill(), rect2, iAnimation, f, extendPath.getPath(), paint);
            paint.setAlpha(alpha);
        }
        canvas.restore();
    }

    public void drawShape(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Path path, Rect rect2, float f) {
        drawShape(canvas, iControl, i, autoShape, path, rect2, getShapeAnimation(autoShape), f);
    }

    public void drawShape(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Path path, Rect rect2, IAnimation iAnimation, float f) {
        if (path != null) {
            canvas.save();
            Paint paint = PaintKit.instance().getPaint();
            int color = paint.getColor();
            Paint.Style style = paint.getStyle();
            int alpha = paint.getAlpha();
            processCanvas(canvas, rect2, autoShape.getRotation(), autoShape.getFlipHorizontal(), autoShape.getFlipVertical(), iAnimation);
            BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
            if (backgroundAndFill != null) {
                paint.setStyle(Paint.Style.FILL);
                BackgroundDrawer.drawPathBackground(canvas, iControl, i, backgroundAndFill, rect2, iAnimation, f, path, paint);
                paint.setAlpha(alpha);
            }
            if (autoShape.hasLine()) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(((float) autoShape.getLine().getLineWidth()) * f);
                if (autoShape.getLine().isDash()) {
                    float f2 = 5.0f * f;
                    paint.setPathEffect(new DashPathEffect(new float[]{f2, f2}, 10.0f));
                }
                BackgroundDrawer.drawPathBackground(canvas, iControl, i, autoShape.getLine().getBackgroundAndFill(), rect2, iAnimation, f, path, paint);
                paint.setAlpha(alpha);
            }
            paint.setAlpha(alpha);
            paint.setColor(color);
            paint.setStyle(style);
            canvas.restore();
        }
    }
}
