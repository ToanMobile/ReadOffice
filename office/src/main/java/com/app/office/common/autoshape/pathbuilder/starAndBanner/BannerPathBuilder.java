package com.app.office.common.autoshape.pathbuilder.starAndBanner;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.AutoShape;
import com.app.office.ss.util.ColorUtil;
import java.util.ArrayList;
import java.util.List;

public class BannerPathBuilder {
    private static final int PICTURECOLOR = -1890233003;
    private static final float TINT = -0.3f;
    private static List<ExtendPath> pathExList = new ArrayList(2);
    private static Matrix sm = new Matrix();
    private static RectF tempRect = new RectF();

    public static List<ExtendPath> getFlagExtendPath(AutoShape autoShape, Rect rect) {
        pathExList.clear();
        int shapeType = autoShape.getShapeType();
        if (shapeType == 53) {
            return getRibbonPath(autoShape, rect);
        }
        if (shapeType == 54) {
            return getRibbon2Path(autoShape, rect);
        }
        if (shapeType == 64) {
            return getWavePath(autoShape, rect);
        }
        if (shapeType == 188) {
            return getDoubleWavePath(autoShape, rect);
        }
        if (shapeType == 244) {
            return getLeftRightRibbon(autoShape, rect);
        }
        if (shapeType == 97) {
            return getVerticalScrollPath(autoShape, rect);
        }
        if (shapeType == 98) {
            return getHorizontalScrollPath(autoShape, rect);
        }
        if (shapeType == 107) {
            return getEllipseRibbonPath(autoShape, rect);
        }
        if (shapeType != 108) {
            return null;
        }
        return getEllipseRibbon2Path(autoShape, rect);
    }

    private static List<ExtendPath> getRibbon2Path(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int width = rect.width();
        int height = rect.height();
        int i4 = width / 8;
        if (!autoShape.isAutoShape07()) {
            if (adjustData == null || adjustData.length < 1) {
                int round = Math.round(((float) height) * 0.125f);
                i3 = Math.round(((float) width) * 0.25f);
                i = round;
            } else {
                if (adjustData[0] != null) {
                    i3 = Math.round(((float) width) * (0.5f - adjustData[0].floatValue()));
                } else {
                    i3 = Math.round(((float) width) * 0.25f);
                }
                if (adjustData.length < 2 || adjustData[1] == null) {
                    i = Math.round(((float) height) * 0.125f);
                } else {
                    i = Math.round(((float) height) * (1.0f - adjustData[1].floatValue()));
                }
            }
            i2 = i3;
        } else if (adjustData == null || adjustData.length != 2) {
            i = Math.round(((float) height) * 0.16667f);
            i2 = Math.round(((float) (width / 2)) * 0.5f);
        } else {
            i = Math.round(((float) height) * adjustData[0].floatValue());
            i2 = Math.round(((float) (width / 2)) * adjustData[1].floatValue());
        }
        float f = (float) (i4 / 4);
        float f2 = (float) (i / 4);
        ExtendPath extendPath = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath.setLine(autoShape.getLine());
            extendPath.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path = new Path();
        path.moveTo((float) rect2.left, (float) (rect2.top + i));
        int i5 = (height - i) / 2;
        path.lineTo((float) (rect2.left + i4), (float) (rect2.bottom - i5));
        path.lineTo((float) rect2.left, (float) rect2.bottom);
        float f3 = f * 3.0f;
        path.lineTo(((float) (rect.centerX() - i2)) + f3, (float) rect2.bottom);
        float f4 = f * 2.0f;
        float f5 = 2.0f * f2;
        float f6 = f3;
        tempRect.set(((float) (rect.centerX() - i2)) + f4, ((float) rect2.bottom) - f5, (float) ((rect.centerX() - i2) + i4), (float) rect2.bottom);
        path.arcTo(tempRect, 90.0f, -180.0f);
        path.lineTo(((float) (rect.centerX() - i2)) + f, ((float) rect2.bottom) - f5);
        float f7 = f2 * 4.0f;
        tempRect.set((float) (rect.centerX() - i2), ((float) rect2.bottom) - f7, ((float) (rect.centerX() - i2)) + f4, ((float) rect2.bottom) - f5);
        path.arcTo(tempRect, 90.0f, 90.0f);
        path.lineTo((float) (rect.centerX() - i2), (float) (rect2.top + i));
        path.close();
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath2.setLine(autoShape.getLine());
            extendPath2.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path2 = new Path();
        path2.moveTo((float) rect2.right, (float) (rect2.top + i));
        path2.lineTo((float) (rect2.right - i4), (float) (rect2.bottom - i5));
        path2.lineTo((float) rect2.right, (float) rect2.bottom);
        path2.lineTo(((float) (rect.centerX() + i2)) - f6, (float) rect2.bottom);
        float f8 = 4.0f * f;
        tempRect.set(((float) (rect.centerX() + i2)) - f8, ((float) rect2.bottom) - f5, ((float) (rect.centerX() + i2)) - f4, (float) rect2.bottom);
        path2.arcTo(tempRect, 90.0f, 180.0f);
        path2.lineTo(((float) (rect.centerX() + i2)) - f, ((float) rect2.bottom) - f5);
        tempRect.set(((float) (rect.centerX() + i2)) - f4, ((float) rect2.bottom) - f7, (float) (rect.centerX() + i2), ((float) rect2.bottom) - f5);
        path2.arcTo(tempRect, 90.0f, -90.0f);
        path2.lineTo((float) (rect.centerX() + i2), (float) (rect2.top + i));
        path2.close();
        extendPath2.setPath(path2);
        extendPath2.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath2);
        ExtendPath extendPath3 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath3.setLine(autoShape.getLine());
            extendPath3.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path3 = new Path();
        path3.moveTo((float) (rect.centerX() - i2), ((float) rect2.top) + f2);
        tempRect.set((float) (rect.centerX() - i2), (float) rect2.top, ((float) (rect.centerX() - i2)) + f4, ((float) rect2.top) + f5);
        path3.arcTo(tempRect, 180.0f, 90.0f);
        path3.lineTo(((float) (rect.centerX() + i2)) - f, (float) rect2.top);
        tempRect.set(((float) (rect.centerX() + i2)) - f4, (float) rect2.top, (float) (rect.centerX() + i2), ((float) rect2.top) + f5);
        path3.arcTo(tempRect, 270.0f, 90.0f);
        path3.lineTo((float) (rect.centerX() + i2), ((float) rect2.bottom) - (f2 * 3.0f));
        tempRect.set(((float) (rect.centerX() + i2)) - f4, ((float) rect2.bottom) - f7, (float) (rect.centerX() + i2), ((float) rect2.bottom) - f5);
        path3.arcTo(tempRect, 0.0f, -90.0f);
        path3.lineTo(((float) (rect.centerX() - i2)) + f, ((float) rect2.bottom) - f7);
        tempRect.set((float) (rect.centerX() - i2), ((float) rect2.bottom) - f7, ((float) (rect.centerX() - i2)) + f4, ((float) rect2.bottom) - f5);
        path3.arcTo(tempRect, 270.0f, -90.0f);
        path3.close();
        extendPath3.setPath(path3);
        extendPath3.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath3);
        ExtendPath extendPath4 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath4.setLine(autoShape.getLine());
            extendPath4.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path4 = new Path();
        path4.moveTo((float) ((rect.centerX() - i2) + i4), ((float) rect2.bottom) - f7);
        path4.lineTo(((float) (rect.centerX() - i2)) + f, ((float) rect2.bottom) - f7);
        tempRect.set((float) (rect.centerX() - i2), ((float) rect2.bottom) - f7, ((float) (rect.centerX() - i2)) + f4, ((float) rect2.bottom) - f5);
        path4.arcTo(tempRect, 270.0f, -180.0f);
        path4.lineTo(((float) (rect.centerX() - i2)) + f6, ((float) rect2.bottom) - f5);
        tempRect.set(((float) (rect.centerX() - i2)) + f4, ((float) rect2.bottom) - f5, ((float) (rect.centerX() - i2)) + f8, (float) rect2.bottom);
        path4.arcTo(tempRect, 270.0f, 90.0f);
        path4.close();
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath4.setBackgroundAndFill(backgroundAndFill);
        extendPath4.setPath(path4);
        pathExList.add(extendPath4);
        ExtendPath extendPath5 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath5.setLine(autoShape.getLine());
            extendPath5.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path5 = new Path();
        path5.moveTo((float) ((rect.centerX() + i2) - i4), ((float) rect2.bottom) - f7);
        path5.lineTo(((float) (rect.centerX() + i2)) - f, ((float) rect2.bottom) - f7);
        tempRect.set(((float) (rect.centerX() + i2)) - f4, ((float) rect2.bottom) - f7, (float) (rect.centerX() + i2), ((float) rect2.bottom) - f5);
        path5.arcTo(tempRect, 270.0f, 180.0f);
        path5.lineTo(((float) (rect.centerX() + i2)) - f6, ((float) rect2.bottom) - f5);
        tempRect.set(((float) (rect.centerX() + i2)) - f8, ((float) rect2.bottom) - f5, ((float) (rect.centerX() + i2)) - f4, (float) rect2.bottom);
        path5.arcTo(tempRect, 270.0f, -90.0f);
        path5.close();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath5.setBackgroundAndFill(backgroundAndFill);
        extendPath5.setPath(path5);
        pathExList.add(extendPath5);
        return pathExList;
    }

    private static List<ExtendPath> getRibbonPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        float round;
        int i;
        int i2;
        int i3;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int width = rect.width();
        int height = rect.height();
        int i4 = width / 8;
        if (autoShape.isAutoShape07()) {
            if (adjustData == null || adjustData.length != 2) {
                f = (float) Math.round(((float) height) * 0.16667f);
                i3 = Math.round(((float) (width / 2)) * 0.5f);
            } else {
                f = (float) Math.round(((float) height) * adjustData[0].floatValue());
                i3 = Math.round(((float) (width / 2)) * adjustData[1].floatValue());
            }
            f2 = (float) i3;
        } else {
            if (adjustData == null || adjustData.length < 1) {
                f3 = (float) Math.round(((float) width) * 0.25f);
                round = (float) Math.round(((float) height) * 0.125f);
            } else {
                if (adjustData[0] != null) {
                    i = Math.round(((float) width) * (0.5f - adjustData[0].floatValue()));
                } else {
                    i = Math.round(((float) width) * 0.25f);
                }
                f3 = (float) i;
                if (adjustData.length < 2 || adjustData[1] == null) {
                    i2 = Math.round(((float) height) * 0.125f);
                } else {
                    i2 = Math.round(((float) height) * adjustData[1].floatValue());
                }
                round = (float) i2;
            }
            f2 = f3;
        }
        float f4 = (float) (i4 / 4);
        float f5 = f / 4.0f;
        ExtendPath extendPath = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath.setLine(autoShape.getLine());
            extendPath.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path = new Path();
        path.moveTo((float) rect2.left, (float) rect2.top);
        float f6 = ((float) height) - f;
        float f7 = f6 / 2.0f;
        path.lineTo((float) (rect2.left + i4), ((float) rect2.top) + f7);
        path.lineTo((float) rect2.left, ((float) rect2.top) + f6);
        path.lineTo(((float) rect.centerX()) - f2, ((float) rect2.top) + f6);
        float f8 = f5 * 2.0f;
        float f9 = 2.0f * f4;
        float f10 = f6;
        float f11 = f5 * 4.0f;
        tempRect.set(((float) rect.centerX()) - f2, ((float) rect2.top) + f8, (((float) rect.centerX()) - f2) + f9, ((float) rect2.top) + f11);
        path.arcTo(tempRect, 180.0f, 90.0f);
        float f12 = f4 * 3.0f;
        path.lineTo((((float) rect.centerX()) - f2) + f12, ((float) rect2.top) + f8);
        float f13 = 4.0f * f4;
        float f14 = f13;
        tempRect.set((((float) rect.centerX()) - f2) + f9, (float) rect2.top, (((float) rect.centerX()) - f2) + f13, ((float) rect2.top) + f8);
        path.arcTo(tempRect, 90.0f, -180.0f);
        path.close();
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath2.setLine(autoShape.getLine());
            extendPath2.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path2 = new Path();
        path2.moveTo((((float) rect.centerX()) - f2) + f4, (float) rect2.bottom);
        tempRect.set(((float) rect.centerX()) - f2, ((float) rect2.bottom) - f8, (((float) rect.centerX()) - f2) + f9, (float) rect2.bottom);
        path2.arcTo(tempRect, 90.0f, 90.0f);
        path2.lineTo(((float) rect.centerX()) - f2, ((float) rect2.top) + (f5 * 3.0f));
        tempRect.set(((float) rect.centerX()) - f2, ((float) rect2.top) + f8, (((float) rect.centerX()) - f2) + f9, ((float) rect2.top) + f11);
        path2.arcTo(tempRect, 180.0f, -90.0f);
        path2.lineTo((((float) rect.centerX()) + f2) - f4, ((float) rect2.top) + f11);
        tempRect.set((((float) rect.centerX()) + f2) - f9, ((float) rect2.top) + f8, ((float) rect.centerX()) + f2, ((float) rect2.top) + f11);
        path2.arcTo(tempRect, 90.0f, -90.0f);
        path2.lineTo(((float) rect.centerX()) + f2, ((float) rect2.bottom) - f5);
        tempRect.set((((float) rect.centerX()) + f2) - f9, ((float) rect2.bottom) - f8, ((float) rect.centerX()) + f2, (float) rect2.bottom);
        path2.arcTo(tempRect, 0.0f, 90.0f);
        path2.close();
        extendPath2.setPath(path2);
        extendPath2.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath2);
        ExtendPath extendPath3 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath3.setLine(autoShape.getLine());
            extendPath3.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path3 = new Path();
        path3.moveTo((float) rect2.right, (float) rect2.top);
        path3.lineTo((float) (rect2.right - i4), ((float) rect2.top) + f7);
        path3.lineTo((float) rect2.right, ((float) rect2.top) + f10);
        path3.lineTo(((float) rect.centerX()) + f2, ((float) rect2.top) + f10);
        tempRect.set((((float) rect.centerX()) + f2) - f9, ((float) rect2.top) + f8, ((float) rect.centerX()) + f2, ((float) rect2.top) + f11);
        path3.arcTo(tempRect, 0.0f, -90.0f);
        path3.lineTo((((float) rect.centerX()) + f2) - f12, ((float) rect2.top) + f8);
        tempRect.set((((float) rect.centerX()) + f2) - f14, (float) rect2.top, (((float) rect.centerX()) + f2) - f9, ((float) rect2.top) + f8);
        path3.arcTo(tempRect, 90.0f, 180.0f);
        path3.close();
        extendPath3.setPath(path3);
        extendPath3.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath3);
        ExtendPath extendPath4 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath4.setLine(autoShape.getLine());
            extendPath4.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path4 = new Path();
        path4.moveTo((((float) rect.centerX()) - f2) + f4, ((float) rect2.top) + f11);
        tempRect.set(((float) rect.centerX()) - f2, ((float) rect2.top) + f8, (((float) rect.centerX()) - f2) + f9, ((float) rect2.top) + f11);
        path4.arcTo(tempRect, 90.0f, 180.0f);
        path4.lineTo((((float) rect.centerX()) - f2) + f12, ((float) rect2.top) + f8);
        tempRect.set((((float) rect.centerX()) - f2) + f9, (float) rect2.top, (((float) rect.centerX()) - f2) + f14, ((float) rect2.top) + f8);
        path4.arcTo(tempRect, 90.0f, -90.0f);
        float f15 = (float) i4;
        path4.lineTo((((float) rect.centerX()) - f2) + f15, ((float) rect2.top) + f11);
        path4.close();
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath4.setBackgroundAndFill(backgroundAndFill);
        extendPath4.setPath(path4);
        pathExList.add(extendPath4);
        ExtendPath extendPath5 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath5.setLine(autoShape.getLine());
            extendPath5.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path5 = new Path();
        path5.moveTo((((float) rect.centerX()) + f2) - f4, ((float) rect2.top) + f11);
        tempRect.set((((float) rect.centerX()) + f2) - f9, ((float) rect2.top) + f8, ((float) rect.centerX()) + f2, ((float) rect2.top) + f11);
        path5.arcTo(tempRect, 90.0f, -180.0f);
        path5.lineTo((((float) rect.centerX()) + f2) - f12, ((float) rect2.top) + f8);
        tempRect.set((((float) rect.centerX()) + f2) - f14, (float) rect2.top, (((float) rect.centerX()) + f2) - f9, ((float) rect2.top) + f8);
        path5.arcTo(tempRect, 90.0f, 90.0f);
        path5.lineTo((((float) rect.centerX()) + f2) - f15, ((float) rect2.top) + f11);
        path5.close();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath5.setBackgroundAndFill(backgroundAndFill);
        extendPath5.setPath(path5);
        pathExList.add(extendPath5);
        return pathExList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0200  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getEllipseRibbon2Path(com.app.office.common.shape.AutoShape r27, android.graphics.Rect r28) {
        /*
            r0 = r28
            java.lang.Float[] r1 = r27.getAdjustData()
            int r2 = r28.width()
            int r3 = r28.height()
            int r2 = java.lang.Math.min(r2, r3)
            float r2 = (float) r2
            boolean r3 = r27.isAutoShape07()
            r12 = 1065353216(0x3f800000, float:1.0)
            r4 = 3
            r5 = 1056964608(0x3f000000, float:0.5)
            r6 = 2
            r13 = 1040187392(0x3e000000, float:0.125)
            r7 = 1048576000(0x3e800000, float:0.25)
            r14 = 1073741824(0x40000000, float:2.0)
            r15 = 0
            r11 = 1
            if (r3 == 0) goto L_0x00a5
            if (r1 == 0) goto L_0x0090
            int r3 = r1.length
            if (r3 != r4) goto L_0x0090
            r3 = r1[r15]
            float r3 = r3.floatValue()
            r4 = r1[r6]
            float r4 = r4.floatValue()
            float r3 = r3 - r4
            r4 = 1045220557(0x3e4ccccd, float:0.2)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x004d
            r3 = r1[r15]
            float r3 = r3.floatValue()
            float r3 = r3 - r4
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            r1[r6] = r3
        L_0x004d:
            r3 = r1[r11]
            float r3 = r3.floatValue()
            r4 = 1061158912(0x3f400000, float:0.75)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x005f
            java.lang.Float r3 = java.lang.Float.valueOf(r4)
            r1[r11] = r3
        L_0x005f:
            r3 = r1[r11]
            float r3 = r3.floatValue()
            float r3 = r3 / r14
            float r5 = r5 - r3
            r3 = r1[r15]
            float r3 = r3.floatValue()
            float r3 = r3 * r2
            int r3 = java.lang.Math.round(r3)
            float r4 = r2 / r14
            r7 = r1[r11]
            float r7 = r7.floatValue()
            float r4 = r4 * r7
            java.lang.Math.round(r4)
            r1 = r1[r6]
            float r1 = r1.floatValue()
            float r1 = r1 * r2
            int r1 = java.lang.Math.round(r1)
            r16 = r5
            goto L_0x0117
        L_0x0090:
            float r1 = r2 * r7
            int r3 = java.lang.Math.round(r1)
            float r1 = r2 / r14
            float r1 = r1 * r5
            java.lang.Math.round(r1)
            float r1 = r2 * r13
            int r1 = java.lang.Math.round(r1)
            goto L_0x0115
        L_0x00a5:
            if (r1 == 0) goto L_0x0102
            int r3 = r1.length
            if (r3 < r11) goto L_0x0102
            r3 = r1[r15]
            if (r3 == 0) goto L_0x00c1
            r3 = r1[r15]
            float r3 = r3.floatValue()
            r8 = r1[r15]
            float r8 = r8.floatValue()
            float r5 = r5 - r8
            float r5 = r5 * r2
            java.lang.Math.round(r5)
            goto L_0x00c8
        L_0x00c1:
            float r3 = r2 * r7
            java.lang.Math.round(r3)
            r3 = 1048576000(0x3e800000, float:0.25)
        L_0x00c8:
            int r5 = r1.length
            if (r5 < r6) goto L_0x00de
            r5 = r1[r11]
            if (r5 == 0) goto L_0x00de
            r5 = r1[r11]
            float r5 = r5.floatValue()
            float r5 = r12 - r5
            float r5 = r5 * r2
            int r5 = java.lang.Math.round(r5)
            goto L_0x00e4
        L_0x00de:
            float r7 = r7 * r2
            int r5 = java.lang.Math.round(r7)
        L_0x00e4:
            int r7 = r1.length
            if (r7 < r4) goto L_0x00f8
            r4 = r1[r6]
            if (r4 == 0) goto L_0x00f8
            r1 = r1[r6]
            float r1 = r1.floatValue()
            float r1 = r1 * r2
            int r1 = java.lang.Math.round(r1)
            goto L_0x00fe
        L_0x00f8:
            float r1 = r2 * r13
            int r1 = java.lang.Math.round(r1)
        L_0x00fe:
            r16 = r3
            r3 = r5
            goto L_0x0117
        L_0x0102:
            float r1 = r2 * r7
            int r3 = java.lang.Math.round(r1)
            float r1 = r2 / r14
            float r1 = r1 * r5
            java.lang.Math.round(r1)
            float r1 = r2 * r13
            int r1 = java.lang.Math.round(r1)
        L_0x0115:
            r16 = 1048576000(0x3e800000, float:0.25)
        L_0x0117:
            android.graphics.Matrix r4 = sm
            r4.reset()
            android.graphics.Matrix r4 = sm
            int r5 = r28.width()
            float r5 = (float) r5
            float r5 = r5 / r2
            int r6 = r28.height()
            float r6 = (float) r6
            float r6 = r6 / r2
            r4.postScale(r5, r6)
            r10 = 0
            if (r1 < r3) goto L_0x0200
            r1 = 0
            float r12 = (float) r3
            float r9 = r2 / r14
            r8 = 0
            r16 = 1056964608(0x3f000000, float:0.5)
            r3 = r1
            r4 = r12
            r5 = r2
            r6 = r12
            r7 = r9
            r1 = r9
            r9 = r16
            java.util.List r9 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            com.app.office.common.autoshape.ExtendPath r8 = new com.app.office.common.autoshape.ExtendPath
            r8.<init>()
            boolean r3 = r27.hasLine()
            if (r3 == 0) goto L_0x0160
            com.app.office.common.borders.Line r3 = r27.getLine()
            r8.setLine((com.app.office.common.borders.Line) r3)
            com.app.office.common.borders.Line r3 = r27.getLine()
            com.app.office.common.bg.BackgroundAndFill r3 = r3.getBackgroundAndFill()
            r8.setBackgroundAndFill(r3)
        L_0x0160:
            android.graphics.Path r7 = new android.graphics.Path
            r7.<init>()
            r7.moveTo(r10, r12)
            java.lang.Object r3 = r9.get(r15)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.x
            float r3 = r3 + r1
            float r4 = r3 / r14
            java.lang.Object r3 = r9.get(r15)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r5 = r3.y
            java.lang.Object r3 = r9.get(r11)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.x
            float r3 = r3 + r1
            float r6 = r3 / r14
            java.lang.Object r3 = r9.get(r11)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r10 = r3.y
            r3 = r7
            r15 = r7
            r7 = r10
            r10 = r8
            r8 = r2
            r14 = r9
            r9 = r12
            r3.cubicTo(r4, r5, r6, r7, r8, r9)
            float r13 = r13 * r2
            float r3 = r2 - r13
            r15.lineTo(r3, r1)
            r15.lineTo(r2, r2)
            java.lang.Object r3 = r14.get(r11)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.x
            float r3 = r3 + r1
            r4 = 1073741824(0x40000000, float:2.0)
            float r5 = r3 / r4
            java.lang.Object r3 = r14.get(r11)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.y
            float r3 = r3 + r2
            float r6 = r3 - r12
            r3 = 0
            java.lang.Object r7 = r14.get(r3)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.x
            float r7 = r7 + r1
            float r7 = r7 / r4
            java.lang.Object r3 = r14.get(r3)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.y
            float r3 = r3 + r2
            float r8 = r3 - r12
            r9 = 0
            r3 = r15
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r2
            r3.cubicTo(r4, r5, r6, r7, r8, r9)
            r15.lineTo(r13, r1)
            r15.close()
            android.graphics.Matrix r1 = sm
            r15.transform(r1)
            int r1 = r0.left
            float r1 = (float) r1
            int r0 = r0.top
            float r0 = (float) r0
            r15.offset(r1, r0)
            r10.setPath(r15)
            com.app.office.common.bg.BackgroundAndFill r0 = r27.getBackgroundAndFill()
            r10.setBackgroundAndFill(r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = pathExList
            r0.add(r10)
            goto L_0x05de
        L_0x0200:
            r4 = 0
            float r14 = (float) r3
            r5 = 1073741824(0x40000000, float:2.0)
            float r15 = r2 / r5
            int r3 = r3 - r1
            float r8 = (float) r3
            r9 = 1056964608(0x3f000000, float:0.5)
            r3 = r4
            r4 = r14
            r5 = r2
            r6 = r14
            r7 = r15
            java.util.List r9 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            r3 = 0
            r4 = 0
            java.lang.Object r5 = r9.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r15
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            java.lang.Object r7 = r9.get(r4)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            java.lang.Object r4 = r9.get(r11)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            float r4 = r4 + r15
            float r8 = r4 / r6
            java.lang.Object r4 = r9.get(r11)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r6 = r4.y
            r17 = 1040187392(0x3e000000, float:0.125)
            r4 = r14
            r18 = r6
            r6 = r7
            r7 = r8
            r8 = r18
            r12 = r9
            r9 = r2
            r10 = r14
            r13 = 1
            r11 = r17
            android.graphics.PointF r11 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r4 = 0
            java.lang.Object r5 = r12.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r15
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            java.lang.Object r7 = r12.get(r4)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            java.lang.Object r4 = r12.get(r13)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            float r4 = r4 + r15
            float r8 = r4 / r6
            java.lang.Object r4 = r12.get(r13)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r9 = r4.y
            r4 = r14
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r2
            r24 = r11
            r11 = r16
            BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r4 = 0
            java.lang.Object r5 = r12.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r15
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            java.lang.Object r7 = r12.get(r4)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            java.lang.Object r4 = r12.get(r13)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            float r4 = r4 + r15
            float r8 = r4 / r6
            java.lang.Object r4 = r12.get(r13)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r9 = r4.y
            r4 = 1040187392(0x3e000000, float:0.125)
            float r12 = r16 + r4
            r4 = r14
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r2
            r11 = r12
            android.graphics.PointF r11 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r17 = 0
            float r3 = r11.x
            float r4 = r11.y
            r10 = r24
            float r5 = r10.x
            float r6 = r10.y
            r7 = 1040187392(0x3e000000, float:0.125)
            float r12 = r7 / r12
            r18 = r14
            r19 = r3
            r20 = r4
            r21 = r5
            r22 = r6
            r23 = r12
            java.util.List r3 = computeBezierCtrPoint(r17, r18, r19, r20, r21, r22, r23)
            com.app.office.common.autoshape.ExtendPath r9 = new com.app.office.common.autoshape.ExtendPath
            r9.<init>()
            boolean r4 = r27.hasLine()
            if (r4 == 0) goto L_0x02f6
            com.app.office.common.borders.Line r4 = r27.getLine()
            r9.setLine((com.app.office.common.borders.Line) r4)
            com.app.office.common.borders.Line r4 = r27.getLine()
            com.app.office.common.bg.BackgroundAndFill r4 = r4.getBackgroundAndFill()
            r9.setBackgroundAndFill(r4)
        L_0x02f6:
            android.graphics.Path r8 = new android.graphics.Path
            r8.<init>()
            r4 = 0
            r8.moveTo(r4, r14)
            r4 = 0
            java.lang.Object r5 = r3.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            java.lang.Object r6 = r3.get(r4)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r4 = r6.y
            java.lang.Object r6 = r3.get(r13)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            java.lang.Object r7 = r3.get(r13)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            float r13 = r11.x
            r24 = r9
            float r9 = r11.y
            r17 = r8
            r18 = r5
            r19 = r4
            r20 = r6
            r21 = r7
            r22 = r13
            r23 = r9
            r17.cubicTo(r18, r19, r20, r21, r22, r23)
            float r4 = r11.x
            float r5 = r11.y
            float r5 = r5 + r2
            float r5 = r5 - r14
            r8.lineTo(r4, r5)
            r4 = 1
            java.lang.Object r5 = r3.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            java.lang.Object r6 = r3.get(r4)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r4 = r6.y
            float r4 = r4 + r2
            float r6 = r4 - r14
            r4 = 0
            java.lang.Object r7 = r3.get(r4)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.x
            java.lang.Object r3 = r3.get(r4)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.y
            float r3 = r3 + r2
            float r9 = r3 - r14
            r13 = 0
            r3 = r8
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r9
            r9 = r8
            r8 = r13
            r25 = r9
            r13 = r24
            r9 = r2
            r3.cubicTo(r4, r5, r6, r7, r8, r9)
            r3 = 0
            float r4 = r2 + r14
            r5 = 1073741824(0x40000000, float:2.0)
            float r17 = r4 / r5
            float r1 = (float) r1
            float r8 = r17 - r1
            r9 = 1056964608(0x3f000000, float:0.5)
            r4 = r17
            r5 = r2
            r6 = r17
            r7 = r15
            java.util.List r3 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            r4 = 0
            r5 = 0
            java.lang.Object r6 = r3.get(r5)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            float r6 = r6 + r15
            r7 = 1073741824(0x40000000, float:2.0)
            float r6 = r6 / r7
            java.lang.Object r8 = r3.get(r5)
            android.graphics.PointF r8 = (android.graphics.PointF) r8
            float r8 = r8.y
            r5 = 1
            java.lang.Object r9 = r3.get(r5)
            android.graphics.PointF r9 = (android.graphics.PointF) r9
            float r9 = r9.x
            float r9 = r9 + r15
            float r9 = r9 / r7
            java.lang.Object r3 = r3.get(r5)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r7 = r3.y
            r18 = 1040187392(0x3e000000, float:0.125)
            r3 = r4
            r4 = r17
            r5 = r6
            r6 = r8
            r8 = r7
            r7 = r9
            r9 = r2
            r26 = r10
            r10 = r17
            r24 = r15
            r15 = r11
            r11 = r18
            android.graphics.PointF r10 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            float r3 = r10.x
            float r4 = r10.y
            r11 = r25
            r11.lineTo(r3, r4)
            r11.close()
            float r3 = r15.x
            float r3 = r2 - r3
            float r4 = r15.y
            r5 = r26
            float r6 = r5.x
            float r7 = r2 - r6
            float r8 = r5.y
            r5 = 1065353216(0x3f800000, float:1.0)
            float r9 = r5 - r12
            r5 = r2
            r6 = r14
            java.util.List r12 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            float r3 = r15.x
            float r3 = r2 - r3
            float r4 = r15.y
            r11.moveTo(r3, r4)
            r3 = 0
            java.lang.Object r4 = r12.get(r3)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            java.lang.Object r5 = r12.get(r3)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.y
            r3 = 1
            java.lang.Object r6 = r12.get(r3)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            java.lang.Object r7 = r12.get(r3)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            r3 = r11
            r8 = r2
            r9 = r14
            r3.cubicTo(r4, r5, r6, r7, r8, r9)
            float r3 = r10.x
            float r3 = r2 - r3
            float r4 = r10.y
            r11.lineTo(r3, r4)
            r11.lineTo(r2, r2)
            r3 = 1
            java.lang.Object r4 = r12.get(r3)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            java.lang.Object r5 = r12.get(r3)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r3 = r5.y
            float r3 = r3 + r2
            float r19 = r3 - r14
            r3 = 0
            java.lang.Object r5 = r12.get(r3)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            java.lang.Object r6 = r12.get(r3)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r3 = r6.y
            float r3 = r3 + r2
            float r21 = r3 - r14
            float r3 = r15.x
            float r22 = r2 - r3
            float r3 = r15.y
            float r3 = r3 + r2
            float r23 = r3 - r14
            r17 = r11
            r18 = r4
            r20 = r5
            r17.cubicTo(r18, r19, r20, r21, r22, r23)
            r11.close()
            android.graphics.Matrix r3 = sm
            r11.transform(r3)
            int r3 = r0.left
            float r3 = (float) r3
            int r4 = r0.top
            float r4 = (float) r4
            r11.offset(r3, r4)
            r13.setPath(r11)
            com.app.office.common.bg.BackgroundAndFill r3 = r27.getBackgroundAndFill()
            r13.setBackgroundAndFill(r3)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r3 = pathExList
            r3.add(r13)
            r3 = 0
            r8 = 0
            r9 = 1056964608(0x3f000000, float:0.5)
            r4 = r1
            r5 = r2
            r6 = r1
            r7 = r24
            java.util.List r3 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            r4 = 0
            r5 = 0
            java.lang.Object r6 = r3.get(r5)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            float r6 = r6 + r24
            r7 = 1073741824(0x40000000, float:2.0)
            float r6 = r6 / r7
            java.lang.Object r8 = r3.get(r5)
            android.graphics.PointF r8 = (android.graphics.PointF) r8
            float r8 = r8.y
            r5 = 1
            java.lang.Object r9 = r3.get(r5)
            android.graphics.PointF r9 = (android.graphics.PointF) r9
            float r9 = r9.x
            float r9 = r9 + r24
            float r9 = r9 / r7
            java.lang.Object r3 = r3.get(r5)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r10 = r3.y
            r3 = r4
            r4 = r1
            r5 = r6
            r6 = r8
            r7 = r9
            r8 = r10
            r9 = r2
            r10 = r1
            r11 = r16
            android.graphics.PointF r1 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            float r3 = r1.x
            float r4 = r1.y
            float r5 = r1.x
            float r19 = r2 - r5
            float r5 = r1.y
            r22 = 0
            r23 = 1056964608(0x3f000000, float:0.5)
            r17 = r3
            r18 = r4
            r20 = r5
            r21 = r24
            java.util.List r3 = computeBezierCtrPoint(r17, r18, r19, r20, r21, r22, r23)
            com.app.office.common.autoshape.ExtendPath r4 = new com.app.office.common.autoshape.ExtendPath
            r4.<init>()
            boolean r5 = r27.hasLine()
            if (r5 == 0) goto L_0x0506
            com.app.office.common.borders.Line r5 = r27.getLine()
            r4.setLine((com.app.office.common.borders.Line) r5)
            com.app.office.common.borders.Line r5 = r27.getLine()
            com.app.office.common.bg.BackgroundAndFill r5 = r5.getBackgroundAndFill()
            r4.setBackgroundAndFill(r5)
        L_0x0506:
            android.graphics.Path r5 = new android.graphics.Path
            r5.<init>()
            float r6 = r1.x
            float r7 = r1.y
            r5.moveTo(r6, r7)
            r6 = 0
            java.lang.Object r7 = r3.get(r6)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.x
            float r7 = r7 + r24
            r8 = 1073741824(0x40000000, float:2.0)
            float r7 = r7 / r8
            java.lang.Object r9 = r3.get(r6)
            android.graphics.PointF r9 = (android.graphics.PointF) r9
            float r9 = r9.y
            r6 = 1
            java.lang.Object r10 = r3.get(r6)
            android.graphics.PointF r10 = (android.graphics.PointF) r10
            float r10 = r10.x
            float r10 = r10 + r24
            float r10 = r10 / r8
            java.lang.Object r8 = r3.get(r6)
            android.graphics.PointF r8 = (android.graphics.PointF) r8
            float r11 = r8.y
            float r6 = r1.x
            float r12 = r2 - r6
            float r13 = r1.y
            r6 = r5
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r6.cubicTo(r7, r8, r9, r10, r11, r12)
            float r6 = r1.x
            float r6 = r2 - r6
            float r7 = r1.y
            float r7 = r7 + r2
            float r7 = r7 - r14
            r5.lineTo(r6, r7)
            r6 = 1
            java.lang.Object r7 = r3.get(r6)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.x
            float r7 = r7 + r24
            r8 = 1073741824(0x40000000, float:2.0)
            float r7 = r7 / r8
            java.lang.Object r6 = r3.get(r6)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.y
            float r6 = r6 + r2
            float r9 = r6 - r14
            r6 = 0
            java.lang.Object r10 = r3.get(r6)
            android.graphics.PointF r10 = (android.graphics.PointF) r10
            float r10 = r10.x
            float r10 = r10 + r24
            float r10 = r10 / r8
            java.lang.Object r3 = r3.get(r6)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.y
            float r3 = r3 + r2
            float r3 = r3 - r14
            float r11 = r1.x
            float r6 = r1.y
            float r6 = r6 + r2
            float r12 = r6 - r14
            r6 = r5
            r8 = r9
            r9 = r10
            r10 = r3
            r6.cubicTo(r7, r8, r9, r10, r11, r12)
            r5.close()
            float r3 = r1.x
            float r6 = r1.y
            float r6 = r6 + r2
            float r6 = r6 - r14
            r5.moveTo(r3, r6)
            float r3 = r15.x
            float r6 = r15.y
            float r6 = r6 + r2
            float r6 = r6 - r14
            r5.lineTo(r3, r6)
            float r3 = r1.x
            float r3 = r2 - r3
            float r1 = r1.y
            float r1 = r1 + r2
            float r1 = r1 - r14
            r5.moveTo(r3, r1)
            float r1 = r15.x
            float r1 = r2 - r1
            float r3 = r15.y
            float r3 = r3 + r2
            float r3 = r3 - r14
            r5.lineTo(r1, r3)
            r4.setPath(r5)
            android.graphics.Matrix r1 = sm
            r5.transform(r1)
            int r1 = r0.left
            float r1 = (float) r1
            int r0 = r0.top
            float r0 = (float) r0
            r5.offset(r1, r0)
            r4.setPath(r5)
            com.app.office.common.bg.BackgroundAndFill r0 = r27.getBackgroundAndFill()
            r4.setBackgroundAndFill(r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = pathExList
            r0.add(r4)
        L_0x05de:
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = pathExList
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.starAndBanner.BannerPathBuilder.getEllipseRibbon2Path(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0203  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getEllipseRibbonPath(com.app.office.common.shape.AutoShape r36, android.graphics.Rect r37) {
        /*
            r0 = r37
            java.lang.Float[] r1 = r36.getAdjustData()
            int r2 = r37.width()
            int r3 = r37.height()
            int r2 = java.lang.Math.min(r2, r3)
            float r2 = (float) r2
            boolean r3 = r36.isAutoShape07()
            r12 = 1065353216(0x3f800000, float:1.0)
            r4 = 3
            r5 = 1056964608(0x3f000000, float:0.5)
            r6 = 2
            r13 = 1040187392(0x3e000000, float:0.125)
            r7 = 1048576000(0x3e800000, float:0.25)
            r14 = 1073741824(0x40000000, float:2.0)
            r15 = 0
            r11 = 1
            if (r3 == 0) goto L_0x00a6
            if (r1 == 0) goto L_0x0091
            int r3 = r1.length
            if (r3 != r4) goto L_0x0091
            r3 = r1[r15]
            float r3 = r3.floatValue()
            r4 = r1[r6]
            float r4 = r4.floatValue()
            float r3 = r3 - r4
            r4 = 1045220557(0x3e4ccccd, float:0.2)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x004d
            r3 = r1[r15]
            float r3 = r3.floatValue()
            float r3 = r3 - r4
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            r1[r6] = r3
        L_0x004d:
            r3 = r1[r11]
            float r3 = r3.floatValue()
            r4 = 1061158912(0x3f400000, float:0.75)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x005f
            java.lang.Float r3 = java.lang.Float.valueOf(r4)
            r1[r11] = r3
        L_0x005f:
            r3 = r1[r11]
            float r3 = r3.floatValue()
            float r3 = r3 / r14
            float r5 = r5 - r3
            r3 = r1[r15]
            float r3 = r3.floatValue()
            float r3 = r3 * r2
            int r3 = java.lang.Math.round(r3)
            float r4 = r2 / r14
            r7 = r1[r11]
            float r7 = r7.floatValue()
            float r4 = r4 * r7
            java.lang.Math.round(r4)
            r1 = r1[r6]
            float r1 = r1.floatValue()
            float r1 = r1 * r2
            int r1 = java.lang.Math.round(r1)
            r10 = r3
            r16 = r5
            goto L_0x0119
        L_0x0091:
            float r1 = r2 * r7
            int r3 = java.lang.Math.round(r1)
            float r1 = r2 / r14
            float r1 = r1 * r5
            java.lang.Math.round(r1)
            float r1 = r2 * r13
            int r1 = java.lang.Math.round(r1)
            goto L_0x0116
        L_0x00a6:
            if (r1 == 0) goto L_0x0103
            int r3 = r1.length
            if (r3 < r11) goto L_0x0103
            r3 = r1[r15]
            if (r3 == 0) goto L_0x00c2
            r3 = r1[r15]
            float r3 = r3.floatValue()
            r8 = r1[r15]
            float r8 = r8.floatValue()
            float r5 = r5 - r8
            float r5 = r5 * r2
            java.lang.Math.round(r5)
            goto L_0x00c9
        L_0x00c2:
            float r3 = r2 * r7
            java.lang.Math.round(r3)
            r3 = 1048576000(0x3e800000, float:0.25)
        L_0x00c9:
            int r5 = r1.length
            if (r5 < r6) goto L_0x00dd
            r5 = r1[r11]
            if (r5 == 0) goto L_0x00dd
            r5 = r1[r11]
            float r5 = r5.floatValue()
            float r5 = r5 * r2
            int r5 = java.lang.Math.round(r5)
            goto L_0x00e3
        L_0x00dd:
            float r7 = r7 * r2
            int r5 = java.lang.Math.round(r7)
        L_0x00e3:
            int r7 = r1.length
            if (r7 < r4) goto L_0x00f9
            r4 = r1[r6]
            if (r4 == 0) goto L_0x00f9
            r1 = r1[r6]
            float r1 = r1.floatValue()
            float r1 = r12 - r1
            float r1 = r1 * r2
            int r1 = java.lang.Math.round(r1)
            goto L_0x00ff
        L_0x00f9:
            float r1 = r2 * r13
            int r1 = java.lang.Math.round(r1)
        L_0x00ff:
            r16 = r3
            r10 = r5
            goto L_0x0119
        L_0x0103:
            float r1 = r2 * r7
            int r3 = java.lang.Math.round(r1)
            float r1 = r2 / r14
            float r1 = r1 * r5
            java.lang.Math.round(r1)
            float r1 = r2 * r13
            int r1 = java.lang.Math.round(r1)
        L_0x0116:
            r10 = r3
            r16 = 1048576000(0x3e800000, float:0.25)
        L_0x0119:
            android.graphics.Matrix r3 = sm
            r3.reset()
            android.graphics.Matrix r3 = sm
            int r4 = r37.width()
            float r4 = (float) r4
            float r4 = r4 / r2
            int r5 = r37.height()
            float r5 = (float) r5
            float r5 = r5 / r2
            r3.postScale(r4, r5)
            r9 = 0
            if (r1 < r10) goto L_0x0203
            r3 = 0
            r4 = 0
            r6 = 0
            float r1 = r2 / r14
            float r10 = (float) r10
            r12 = 1056964608(0x3f000000, float:0.5)
            r5 = r2
            r7 = r1
            r8 = r10
            r13 = 0
            r9 = r12
            java.util.List r12 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            com.app.office.common.autoshape.ExtendPath r9 = new com.app.office.common.autoshape.ExtendPath
            r9.<init>()
            boolean r3 = r36.hasLine()
            if (r3 == 0) goto L_0x0160
            com.app.office.common.borders.Line r3 = r36.getLine()
            r9.setLine((com.app.office.common.borders.Line) r3)
            com.app.office.common.borders.Line r3 = r36.getLine()
            com.app.office.common.bg.BackgroundAndFill r3 = r3.getBackgroundAndFill()
            r9.setBackgroundAndFill(r3)
        L_0x0160:
            android.graphics.Path r8 = new android.graphics.Path
            r8.<init>()
            r8.moveTo(r13, r13)
            java.lang.Object r3 = r12.get(r15)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.x
            float r3 = r3 + r1
            float r4 = r3 / r14
            java.lang.Object r3 = r12.get(r15)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r5 = r3.y
            java.lang.Object r3 = r12.get(r11)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.x
            float r3 = r3 + r1
            float r6 = r3 / r14
            java.lang.Object r3 = r12.get(r11)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r7 = r3.y
            r13 = 0
            r3 = r8
            r15 = r8
            r8 = r2
            r24 = r9
            r9 = r13
            r3.cubicTo(r4, r5, r6, r7, r8, r9)
            r3 = 1040187392(0x3e000000, float:0.125)
            float r13 = r2 * r3
            float r3 = r2 - r13
            r15.lineTo(r3, r1)
            float r3 = r2 - r10
            r15.lineTo(r2, r3)
            java.lang.Object r4 = r12.get(r11)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            float r4 = r4 + r1
            float r18 = r4 / r14
            java.lang.Object r4 = r12.get(r11)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.y
            float r4 = r4 + r2
            float r19 = r4 - r10
            r4 = 0
            java.lang.Object r5 = r12.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r1
            float r20 = r5 / r14
            java.lang.Object r4 = r12.get(r4)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.y
            float r4 = r4 + r2
            float r21 = r4 - r10
            r22 = 0
            r17 = r15
            r23 = r3
            r17.cubicTo(r18, r19, r20, r21, r22, r23)
            r15.lineTo(r13, r1)
            r15.close()
            android.graphics.Matrix r1 = sm
            r15.transform(r1)
            int r1 = r0.left
            float r1 = (float) r1
            int r0 = r0.top
            float r0 = (float) r0
            r15.offset(r1, r0)
            r0 = r24
            r0.setPath(r15)
            com.app.office.common.bg.BackgroundAndFill r1 = r36.getBackgroundAndFill()
            r0.setBackgroundAndFill(r1)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r1 = pathExList
            r1.add(r0)
            goto L_0x05c0
        L_0x0203:
            r13 = 0
            r3 = 0
            r4 = 0
            r6 = 0
            float r15 = r2 / r14
            float r1 = (float) r1
            r9 = 1056964608(0x3f000000, float:0.5)
            r5 = r2
            r7 = r15
            r8 = r1
            java.util.List r9 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            r5 = 0
            java.lang.Object r6 = r9.get(r5)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            float r6 = r6 + r15
            float r6 = r6 / r14
            java.lang.Object r7 = r9.get(r5)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            java.lang.Object r5 = r9.get(r11)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r15
            float r8 = r5 / r14
            java.lang.Object r5 = r9.get(r11)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.y
            r18 = 0
            r19 = 1040187392(0x3e000000, float:0.125)
            r20 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r20
            r12 = r9
            r9 = r2
            r25 = r10
            r10 = r18
            r13 = 1
            r11 = r19
            android.graphics.PointF r11 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r5 = 0
            java.lang.Object r6 = r12.get(r5)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            float r6 = r6 + r15
            float r6 = r6 / r14
            java.lang.Object r7 = r12.get(r5)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            java.lang.Object r5 = r12.get(r13)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r15
            float r8 = r5 / r14
            java.lang.Object r5 = r12.get(r13)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r9 = r5.y
            r10 = 0
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r2
            r26 = r11
            r11 = r16
            BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r5 = 0
            java.lang.Object r6 = r12.get(r5)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            float r6 = r6 + r15
            float r6 = r6 / r14
            java.lang.Object r7 = r12.get(r5)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            java.lang.Object r5 = r12.get(r13)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r15
            float r8 = r5 / r14
            java.lang.Object r5 = r12.get(r13)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r9 = r5.y
            r5 = 1040187392(0x3e000000, float:0.125)
            float r12 = r16 + r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r2
            r11 = r12
            android.graphics.PointF r11 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            float r5 = r11.x
            float r6 = r11.y
            r10 = r26
            float r7 = r10.x
            float r8 = r10.y
            r9 = 1040187392(0x3e000000, float:0.125)
            float r12 = r9 / r12
            r9 = r12
            java.util.List r3 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            com.app.office.common.autoshape.ExtendPath r9 = new com.app.office.common.autoshape.ExtendPath
            r9.<init>()
            boolean r4 = r36.hasLine()
            if (r4 == 0) goto L_0x02e8
            com.app.office.common.borders.Line r4 = r36.getLine()
            r9.setLine((com.app.office.common.borders.Line) r4)
            com.app.office.common.borders.Line r4 = r36.getLine()
            com.app.office.common.bg.BackgroundAndFill r4 = r4.getBackgroundAndFill()
            r9.setBackgroundAndFill(r4)
        L_0x02e8:
            android.graphics.Path r8 = new android.graphics.Path
            r8.<init>()
            r4 = 0
            r8.moveTo(r4, r4)
            r4 = 0
            java.lang.Object r5 = r3.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            java.lang.Object r6 = r3.get(r4)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r4 = r6.y
            java.lang.Object r6 = r3.get(r13)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            java.lang.Object r7 = r3.get(r13)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            float r14 = r11.x
            float r13 = r11.y
            r26 = r8
            r27 = r5
            r28 = r4
            r29 = r6
            r30 = r7
            r31 = r14
            r32 = r13
            r26.cubicTo(r27, r28, r29, r30, r31, r32)
            float r4 = r11.x
            float r5 = r11.y
            float r5 = r5 + r2
            r6 = r25
            float r13 = (float) r6
            float r5 = r5 - r13
            r8.lineTo(r4, r5)
            r4 = 1
            java.lang.Object r5 = r3.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            java.lang.Object r6 = r3.get(r4)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r4 = r6.y
            float r4 = r4 + r2
            float r28 = r4 - r13
            r4 = 0
            java.lang.Object r6 = r3.get(r4)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            java.lang.Object r3 = r3.get(r4)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.y
            float r3 = r3 + r2
            float r30 = r3 - r13
            r31 = 0
            float r14 = r2 - r13
            r27 = r5
            r29 = r6
            r32 = r14
            r26.cubicTo(r27, r28, r29, r30, r31, r32)
            r3 = 0
            r4 = 1073741824(0x40000000, float:2.0)
            float r19 = r14 / r4
            float r21 = r19 + r1
            r22 = 1056964608(0x3f000000, float:0.5)
            r4 = r19
            r5 = r2
            r6 = r19
            r7 = r15
            r33 = r8
            r8 = r21
            r34 = r9
            r9 = r22
            java.util.List r3 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            r4 = 0
            r5 = 0
            java.lang.Object r6 = r3.get(r5)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            float r6 = r6 + r15
            r7 = 1073741824(0x40000000, float:2.0)
            float r6 = r6 / r7
            java.lang.Object r8 = r3.get(r5)
            android.graphics.PointF r8 = (android.graphics.PointF) r8
            float r8 = r8.y
            r5 = 1
            java.lang.Object r9 = r3.get(r5)
            android.graphics.PointF r9 = (android.graphics.PointF) r9
            float r9 = r9.x
            float r9 = r9 + r15
            float r9 = r9 / r7
            java.lang.Object r3 = r3.get(r5)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r7 = r3.y
            r21 = 1040187392(0x3e000000, float:0.125)
            r3 = r4
            r4 = r19
            r5 = r6
            r6 = r8
            r8 = r7
            r7 = r9
            r9 = r2
            r35 = r10
            r10 = r19
            r19 = r15
            r15 = r11
            r11 = r21
            android.graphics.PointF r10 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            float r3 = r10.x
            float r4 = r10.y
            r11 = r33
            r11.lineTo(r3, r4)
            r11.close()
            float r3 = r15.x
            float r3 = r2 - r3
            float r4 = r15.y
            r6 = 0
            r5 = r35
            float r7 = r5.x
            float r7 = r2 - r7
            float r8 = r5.y
            r5 = 1065353216(0x3f800000, float:1.0)
            float r9 = r5 - r12
            r5 = r2
            java.util.List r12 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            float r3 = r15.x
            float r3 = r2 - r3
            float r4 = r15.y
            r11.moveTo(r3, r4)
            r3 = 0
            java.lang.Object r4 = r12.get(r3)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            java.lang.Object r5 = r12.get(r3)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.y
            r3 = 1
            java.lang.Object r6 = r12.get(r3)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.x
            java.lang.Object r7 = r12.get(r3)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            r9 = 0
            r3 = r11
            r8 = r2
            r3.cubicTo(r4, r5, r6, r7, r8, r9)
            float r3 = r10.x
            float r3 = r2 - r3
            float r4 = r10.y
            r11.lineTo(r3, r4)
            r11.lineTo(r2, r14)
            r3 = 1
            java.lang.Object r4 = r12.get(r3)
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            float r4 = r4.x
            java.lang.Object r5 = r12.get(r3)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r3 = r5.y
            float r3 = r3 + r2
            float r28 = r3 - r13
            r3 = 0
            java.lang.Object r5 = r12.get(r3)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            java.lang.Object r6 = r12.get(r3)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r3 = r6.y
            float r3 = r3 + r2
            float r30 = r3 - r13
            float r3 = r15.x
            float r31 = r2 - r3
            float r3 = r15.y
            float r3 = r3 + r2
            float r32 = r3 - r13
            r26 = r11
            r27 = r4
            r29 = r5
            r26.cubicTo(r27, r28, r29, r30, r31, r32)
            r11.close()
            android.graphics.Matrix r3 = sm
            r11.transform(r3)
            int r3 = r0.left
            float r3 = (float) r3
            int r4 = r0.top
            float r4 = (float) r4
            r11.offset(r3, r4)
            r3 = r34
            r3.setPath(r11)
            com.app.office.common.bg.BackgroundAndFill r4 = r36.getBackgroundAndFill()
            r3.setBackgroundAndFill(r4)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r4 = pathExList
            r4.add(r3)
            r3 = 0
            float r10 = r2 - r1
            r9 = 1056964608(0x3f000000, float:0.5)
            r4 = r10
            r5 = r2
            r6 = r10
            r7 = r19
            java.util.List r1 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            r4 = 0
            java.lang.Object r5 = r1.get(r4)
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r5 = r5.x
            float r5 = r5 + r19
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            java.lang.Object r7 = r1.get(r4)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.y
            r4 = 1
            java.lang.Object r8 = r1.get(r4)
            android.graphics.PointF r8 = (android.graphics.PointF) r8
            float r8 = r8.x
            float r8 = r8 + r19
            float r8 = r8 / r6
            java.lang.Object r1 = r1.get(r4)
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            float r1 = r1.y
            r4 = r10
            r6 = r7
            r7 = r8
            r8 = r1
            r9 = r2
            r11 = r16
            android.graphics.PointF r1 = BezierComputePoint(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            float r3 = r1.x
            float r4 = r1.y
            float r5 = r1.x
            float r5 = r2 - r5
            float r6 = r1.y
            r9 = 1056964608(0x3f000000, float:0.5)
            r7 = r19
            r8 = r2
            java.util.List r3 = computeBezierCtrPoint(r3, r4, r5, r6, r7, r8, r9)
            com.app.office.common.autoshape.ExtendPath r4 = new com.app.office.common.autoshape.ExtendPath
            r4.<init>()
            boolean r5 = r36.hasLine()
            if (r5 == 0) goto L_0x04f3
            com.app.office.common.borders.Line r5 = r36.getLine()
            r4.setLine((com.app.office.common.borders.Line) r5)
            com.app.office.common.borders.Line r5 = r36.getLine()
            com.app.office.common.bg.BackgroundAndFill r5 = r5.getBackgroundAndFill()
            r4.setBackgroundAndFill(r5)
        L_0x04f3:
            android.graphics.Path r5 = new android.graphics.Path
            r5.<init>()
            float r6 = r1.x
            float r7 = r1.y
            r5.moveTo(r6, r7)
            r6 = 0
            java.lang.Object r7 = r3.get(r6)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.x
            float r7 = r7 + r19
            r8 = 1073741824(0x40000000, float:2.0)
            float r7 = r7 / r8
            java.lang.Object r9 = r3.get(r6)
            android.graphics.PointF r9 = (android.graphics.PointF) r9
            float r9 = r9.y
            r6 = 1
            java.lang.Object r10 = r3.get(r6)
            android.graphics.PointF r10 = (android.graphics.PointF) r10
            float r10 = r10.x
            float r10 = r10 + r19
            float r10 = r10 / r8
            java.lang.Object r8 = r3.get(r6)
            android.graphics.PointF r8 = (android.graphics.PointF) r8
            float r11 = r8.y
            float r6 = r1.x
            float r12 = r2 - r6
            float r13 = r1.y
            r6 = r5
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r6.cubicTo(r7, r8, r9, r10, r11, r12)
            float r6 = r1.x
            float r6 = r2 - r6
            float r7 = r1.y
            float r7 = r7 - r14
            r5.lineTo(r6, r7)
            r6 = 1
            java.lang.Object r7 = r3.get(r6)
            android.graphics.PointF r7 = (android.graphics.PointF) r7
            float r7 = r7.x
            float r7 = r7 + r19
            r8 = 1073741824(0x40000000, float:2.0)
            float r7 = r7 / r8
            java.lang.Object r6 = r3.get(r6)
            android.graphics.PointF r6 = (android.graphics.PointF) r6
            float r6 = r6.y
            float r9 = r6 - r14
            r6 = 0
            java.lang.Object r10 = r3.get(r6)
            android.graphics.PointF r10 = (android.graphics.PointF) r10
            float r10 = r10.x
            float r10 = r10 + r19
            float r10 = r10 / r8
            java.lang.Object r3 = r3.get(r6)
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.y
            float r3 = r3 - r14
            float r11 = r1.x
            float r6 = r1.y
            float r12 = r6 - r14
            r6 = r5
            r8 = r9
            r9 = r10
            r10 = r3
            r6.cubicTo(r7, r8, r9, r10, r11, r12)
            r5.close()
            float r3 = r1.x
            float r6 = r1.y
            float r6 = r6 - r14
            r5.moveTo(r3, r6)
            float r3 = r15.x
            float r6 = r15.y
            r5.lineTo(r3, r6)
            float r3 = r1.x
            float r3 = r2 - r3
            float r1 = r1.y
            float r1 = r1 - r14
            r5.moveTo(r3, r1)
            float r1 = r15.x
            float r2 = r2 - r1
            float r1 = r15.y
            r5.lineTo(r2, r1)
            r4.setPath(r5)
            android.graphics.Matrix r1 = sm
            r5.transform(r1)
            int r1 = r0.left
            float r1 = (float) r1
            int r0 = r0.top
            float r0 = (float) r0
            r5.offset(r1, r0)
            r4.setPath(r5)
            com.app.office.common.bg.BackgroundAndFill r0 = r36.getBackgroundAndFill()
            r4.setBackgroundAndFill(r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = pathExList
            r0.add(r4)
        L_0x05c0:
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = pathExList
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.starAndBanner.BannerPathBuilder.getEllipseRibbonPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    private static List<PointF> computeBezierCtrPoint(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        ArrayList arrayList = new ArrayList(2);
        PointF pointF = new PointF();
        PointF pointF2 = new PointF();
        arrayList.add(0, pointF);
        arrayList.add(1, pointF2);
        pointF.x = ((((f * -5.0f) + (f3 * 18.0f)) - (f5 * 9.0f)) + (f7 * 2.0f)) / 6.0f;
        pointF.y = ((((-5.0f * f2) + (f4 * 18.0f)) - (f6 * 9.0f)) + (f8 * 2.0f)) / 6.0f;
        pointF2.x = ((((f * 2.0f) - (f3 * 9.0f)) + (f5 * 18.0f)) - (f7 * 5.0f)) / 6.0f;
        pointF2.y = ((((f2 * 2.0f) - (f4 * 9.0f)) + (f6 * 18.0f)) - (5.0f * f8)) / 6.0f;
        return arrayList;
    }

    private static List<PointF> computeBezierCtrPoint(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = f7;
        if (f8 < 1.0E-5f && ((double) f8) - 1.0d > 9.999999747378752E-6d) {
            return null;
        }
        ArrayList arrayList = new ArrayList(2);
        PointF pointF = new PointF();
        PointF pointF2 = new PointF();
        arrayList.add(0, pointF);
        arrayList.add(1, pointF2);
        float f9 = 1.0f - f8;
        float f10 = f8 * 3.0f;
        float f11 = f10 * f9 * f9;
        float f12 = f10 * f8 * f9;
        float f13 = f9 * f9 * f9;
        float f14 = f8 * f8 * f8;
        float f15 = f8 / f9;
        float f16 = (3.0f * f14) + f11;
        float f17 = (((f5 - (f13 * f)) - (f11 * f)) - (f12 * f3)) - (f14 * f3);
        int i = (f16 > 1.0E-5f ? 1 : (f16 == 1.0E-5f ? 0 : -1));
        if (i < 0) {
            return null;
        }
        pointF.x = (f17 / f16) + f;
        pointF2.x = ((f17 * f15) / f16) + f3;
        float f18 = (((f6 - (f13 * f2)) - (f11 * f2)) - (f12 * f4)) - (f14 * f4);
        if (i < 0) {
            return null;
        }
        pointF.y = (f18 / f16) + f2;
        pointF2.y = ((f15 * f18) / f16) + f4;
        return arrayList;
    }

    private static PointF BezierComputePoint(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        PointF pointF = new PointF();
        float f10 = 1.0f - f9;
        float f11 = f10 * f10 * f10;
        pointF.x = f * f11;
        pointF.y = f11 * f2;
        float f12 = 3.0f * f9;
        float f13 = f12 * f10 * f10;
        pointF.x += f3 * f13;
        pointF.y += f13 * f4;
        float f14 = f12 * f9 * f10;
        pointF.x += f5 * f14;
        pointF.y += f14 * f6;
        float f15 = f9 * f9 * f9;
        pointF.x += f7 * f15;
        pointF.y += f15 * f8;
        return pointF;
    }

    private static List<ExtendPath> getVerticalScrollPath(AutoShape autoShape, Rect rect) {
        int i;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            i = Math.round(((float) min) * 0.125f);
        } else {
            i = Math.round(((float) min) * adjustData[0].floatValue());
        }
        float f = ((float) i) / 2.0f;
        ExtendPath extendPath = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath.setLine(autoShape.getLine());
            extendPath.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path = new Path();
        path.moveTo(((float) rect2.left) + f, (float) rect2.bottom);
        tempRect.set((float) rect2.left, (float) (rect2.bottom - i), (float) (rect2.left + i), (float) rect2.bottom);
        path.arcTo(tempRect, 90.0f, -90.0f);
        path.lineTo((float) (rect2.left + i), ((float) rect2.top) + f);
        int i2 = i * 2;
        tempRect.set((float) (rect2.left + i), (float) rect2.top, (float) (rect2.left + i2), (float) (rect2.top + i));
        path.arcTo(tempRect, 180.0f, 270.0f);
        path.lineTo((float) (rect2.right - i), (float) (rect2.top + i));
        path.lineTo((float) (rect2.right - i), ((float) rect2.bottom) - f);
        tempRect.set((float) (rect2.right - i2), (float) (rect2.bottom - i), (float) (rect2.right - i), (float) rect2.bottom);
        path.arcTo(tempRect, 0.0f, 90.0f);
        path.close();
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath2.setLine(autoShape.getLine());
            extendPath2.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path2 = new Path();
        path2.moveTo(((float) rect2.left) + (3.0f * f), (float) rect2.top);
        tempRect.set((float) (rect2.left + i), (float) rect2.top, (float) (rect2.left + i2), (float) (rect2.top + i));
        path2.arcTo(tempRect, 270.0f, 180.0f);
        path2.lineTo(((float) rect2.right) - f, (float) (rect2.top + i));
        tempRect.set((float) (rect2.right - i), (float) rect2.top, (float) rect2.right, (float) (rect2.top + i));
        path2.arcTo(tempRect, 90.0f, -180.0f);
        path2.close();
        extendPath2.setPath(path2);
        extendPath2.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath2);
        ExtendPath extendPath3 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath3.setLine(autoShape.getLine());
            extendPath3.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path3 = new Path();
        path3.moveTo((float) (rect2.left + i), (float) (rect2.bottom - i));
        path3.lineTo((float) (rect2.left + i), ((float) rect2.bottom) - f);
        path3.lineTo(((float) rect2.left) + f, ((float) rect2.bottom) - f);
        float f2 = 0.5f * f;
        float f3 = 1.5f * f;
        tempRect.set(((float) rect2.left) + f2, (float) (rect2.bottom - i), ((float) rect2.left) + f3, ((float) rect2.bottom) - f);
        path3.arcTo(tempRect, 90.0f, -180.0f);
        path3.close();
        extendPath3.setPath(path3);
        extendPath3.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath3);
        ExtendPath extendPath4 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath4.setLine(autoShape.getLine());
            extendPath4.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path4 = new Path();
        path4.moveTo((float) (rect2.left + i), ((float) rect2.bottom) - f);
        tempRect.set((float) rect2.left, (float) (rect2.bottom - i), (float) (rect2.left + i), (float) rect2.bottom);
        path4.arcTo(tempRect, 0.0f, 270.0f);
        tempRect.set(((float) rect2.left) + f2, (float) (rect2.bottom - i), ((float) rect2.left) + f3, ((float) rect2.bottom) - f);
        path4.arcTo(tempRect, 270.0f, 180.0f);
        path4.close();
        extendPath4.setPath(path4);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath4.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath4);
        ExtendPath extendPath5 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath5.setLine(autoShape.getLine());
            extendPath5.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path5 = new Path();
        path5.moveTo((float) (rect2.left + i2), ((float) rect2.top) + f);
        tempRect.set((float) (rect2.left + i), (float) rect2.top, (float) (rect2.left + i2), (float) (rect2.top + i));
        path5.arcTo(tempRect, 0.0f, 90.0f);
        tempRect.set(((float) (rect2.left + i)) + f2, ((float) rect2.top) + f, ((float) (rect2.left + i)) + f3, (float) (rect2.top + i));
        path5.arcTo(tempRect, 90.0f, 180.0f);
        path5.close();
        extendPath5.setPath(path5);
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath5.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath5);
        return pathExList;
    }

    private static List<ExtendPath> getHorizontalScrollPath(AutoShape autoShape, Rect rect) {
        int i;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            i = Math.round(((float) min) * 0.125f);
        } else {
            i = Math.round(((float) min) * adjustData[0].floatValue());
        }
        float f = ((float) i) / 2.0f;
        ExtendPath extendPath = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath.setLine(autoShape.getLine());
            extendPath.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path = new Path();
        float f2 = 3.0f * f;
        path.moveTo((float) rect2.left, ((float) rect2.top) + f2);
        int i2 = i * 2;
        tempRect.set((float) rect2.left, (float) (rect2.top + i), (float) (rect2.left + i), (float) (rect2.top + i2));
        path.arcTo(tempRect, 180.0f, -180.0f);
        path.lineTo((float) (rect2.left + i), ((float) rect2.bottom) - f);
        tempRect.set((float) rect2.left, (float) (rect2.bottom - i), (float) (rect2.left + i), (float) rect2.bottom);
        path.arcTo(tempRect, 0.0f, 180.0f);
        path.close();
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath2.setLine(autoShape.getLine());
            extendPath2.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path2 = new Path();
        path2.moveTo((float) (rect2.left + i), ((float) rect2.top) + f2);
        tempRect.set((float) rect2.left, (float) (rect2.top + i), (float) (rect2.left + i), (float) (rect2.top + i2));
        path2.arcTo(tempRect, 0.0f, 270.0f);
        path2.lineTo(((float) rect2.right) - f, (float) (rect2.top + i));
        tempRect.set((float) (rect2.right - i), (float) rect2.top, (float) rect2.right, (float) (rect2.top + i));
        path2.arcTo(tempRect, 90.0f, -90.0f);
        path2.lineTo((float) rect2.right, ((float) (rect2.bottom - i)) - f);
        tempRect.set((float) (rect2.right - i), (float) (rect2.bottom - i2), (float) rect2.right, (float) (rect2.bottom - i));
        path2.arcTo(tempRect, 0.0f, 90.0f);
        path2.lineTo((float) (rect2.left + i), (float) (rect2.bottom - i));
        path2.close();
        extendPath2.setPath(path2);
        extendPath2.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath2);
        ExtendPath extendPath3 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath3.setLine(autoShape.getLine());
            extendPath3.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path3 = new Path();
        path3.moveTo((float) (rect2.right - i), ((float) rect2.top) + f);
        float f3 = 0.5f * f;
        float f4 = 1.5f * f;
        tempRect.set((float) (rect2.right - i), ((float) rect2.top) + f3, ((float) rect2.right) - f, ((float) rect2.top) + f4);
        path3.arcTo(tempRect, 180.0f, -180.0f);
        path3.lineTo(((float) rect2.right) - f, (float) (rect2.top + i));
        path3.lineTo((float) (rect2.right - i), (float) (rect2.top + i));
        path3.close();
        extendPath3.setPath(path3);
        extendPath3.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath3);
        ExtendPath extendPath4 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath4.setLine(autoShape.getLine());
            extendPath4.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path4 = new Path();
        path4.moveTo(((float) rect2.left) + f, (float) (rect2.top + i2));
        tempRect.set((float) rect2.left, (float) (rect2.top + i), (float) (rect2.left + i), (float) (rect2.top + i2));
        path4.arcTo(tempRect, 90.0f, -90.0f);
        tempRect.set(((float) rect2.left) + f, ((float) (rect2.top + i)) + f3, (float) (rect2.left + i), ((float) (rect2.top + i)) + f4);
        path4.arcTo(tempRect, 0.0f, -180.0f);
        path4.close();
        extendPath4.setPath(path4);
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath4.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath4);
        ExtendPath extendPath5 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath5.setLine(autoShape.getLine());
            extendPath5.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path5 = new Path();
        path5.moveTo(((float) rect2.right) - f, ((float) rect2.top) + f);
        tempRect.set((float) (rect2.right - i), ((float) rect2.top) + f3, ((float) rect2.right) - f, ((float) rect2.top) + f4);
        path5.arcTo(tempRect, 0.0f, 180.0f);
        tempRect.set((float) (rect2.right - i), (float) rect2.top, (float) rect2.right, (float) (rect2.top + i));
        path5.arcTo(tempRect, 180.0f, 270.0f);
        path5.close();
        extendPath5.setPath(path5);
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath5.setBackgroundAndFill(backgroundAndFill);
        pathExList.add(extendPath5);
        return pathExList;
    }

    private static List<ExtendPath> getWavePath(AutoShape autoShape, Rect rect) {
        int i;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int width = rect.width();
        int height = rect.height();
        int i2 = 0;
        if (autoShape.isAutoShape07()) {
            if (adjustData == null || adjustData.length != 2) {
                i = Math.round(((float) height) * 0.125f);
            } else {
                i = Math.round(((float) height) * adjustData[0].floatValue());
                i2 = Math.round(((float) width) * adjustData[1].floatValue());
            }
        } else if (adjustData == null || adjustData.length < 1) {
            i = Math.round(((float) height) * 0.125f);
        } else {
            if (adjustData[0] != null) {
                i = Math.round(((float) height) * adjustData[0].floatValue());
            } else {
                i = Math.round(((float) height) * 0.125f);
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                i2 = Math.round(((float) width) * (adjustData[1].floatValue() - 0.5f));
            }
        }
        int abs = width - Math.abs(i2 * 2);
        ExtendPath extendPath = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath.setLine(autoShape.getLine());
            extendPath.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path = new Path();
        if (i2 > 0) {
            path.moveTo((float) rect2.left, (float) (rect2.top + i));
            float f = (float) abs;
            float f2 = (float) i;
            float f3 = f2 * 3.3333f;
            float f4 = f * 0.6667f;
            Path path2 = path;
            path2.cubicTo(((float) rect2.left) + (0.3333f * f), ((float) (rect2.top + i)) - f3, ((float) rect2.left) + f4, ((float) (rect2.top + i)) + f3, (float) (rect2.left + abs), (float) (rect2.top + i));
            path.lineTo((float) rect2.right, (float) (rect2.bottom - i));
            Path path3 = path;
            path3.cubicTo(((float) rect2.right) - (f * 0.333f), ((float) (rect2.bottom - i)) + f3, ((float) rect2.right) - f4, ((float) (rect2.bottom - i)) - (f2 * 3.333f), (float) (rect2.right - abs), (float) (rect2.bottom - i));
            path.close();
        } else {
            path.moveTo((float) (rect2.right - abs), (float) (rect2.top + i));
            float f5 = (float) abs;
            float f6 = f5 * 0.6667f;
            float f7 = 3.333f * ((float) i);
            Path path4 = path;
            path4.cubicTo(((float) rect2.right) - f6, ((float) (rect2.top + i)) - f7, ((float) rect2.right) - (0.3333f * f5), ((float) (rect2.top + i)) + f7, (float) rect2.right, (float) (rect2.top + i));
            path.lineTo((float) (rect2.left + abs), (float) (rect2.bottom - i));
            path4.cubicTo(((float) rect2.left) + f6, ((float) (rect2.bottom - i)) + f7, ((float) rect2.left) + (f5 * 0.333f), ((float) (rect2.bottom - i)) - f7, (float) rect2.left, (float) (rect2.bottom - i));
            path.close();
        }
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        return pathExList;
    }

    private static List<ExtendPath> getDoubleWavePath(AutoShape autoShape, Rect rect) {
        int i;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int width = rect.width();
        int height = rect.height();
        int i2 = 0;
        if (autoShape.isAutoShape07()) {
            if (adjustData == null || adjustData.length != 2) {
                i = Math.round(((float) height) * 0.125f);
            } else {
                i = Math.round(((float) height) * adjustData[0].floatValue());
                i2 = Math.round(((float) width) * adjustData[1].floatValue());
            }
        } else if (adjustData == null || adjustData.length < 1) {
            i = Math.round(((float) height) * 0.125f);
        } else {
            if (adjustData[0] != null) {
                i = Math.round(((float) height) * adjustData[0].floatValue());
            } else {
                i = Math.round(((float) height) * 0.125f);
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                i2 = Math.round(((float) width) * (adjustData[1].floatValue() - 0.5f));
            }
        }
        int abs = (width - Math.abs(i2 * 2)) / 2;
        ExtendPath extendPath = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath.setLine(autoShape.getLine());
            extendPath.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path = new Path();
        if (i2 > 0) {
            path.moveTo((float) rect2.left, (float) (rect2.top + i));
            float f = (float) abs;
            float f2 = 0.3333f * f;
            float f3 = ((float) i) * 3.333f;
            float f4 = 0.6667f * f;
            Path path2 = path;
            float f5 = f;
            path2.cubicTo(((float) rect2.left) + f2, ((float) (rect2.top + i)) - f3, ((float) rect2.left) + f4, ((float) (rect2.top + i)) + f3, (float) (rect2.left + abs), (float) (rect2.top + i));
            float f6 = 1.3333f * f5;
            float f7 = 1.6667f * f5;
            int i3 = abs * 2;
            path2.cubicTo(((float) rect2.left) + f6, ((float) (rect2.top + i)) - f3, ((float) rect2.left) + f7, ((float) (rect2.top + i)) + f3, (float) (rect2.left + i3), (float) (rect2.top + i));
            path.lineTo((float) rect2.right, (float) (rect2.bottom - i));
            Path path3 = path;
            path3.cubicTo(((float) rect2.right) - f2, ((float) (rect2.bottom - i)) + f3, ((float) rect2.right) - f4, ((float) (rect2.bottom - i)) - f3, (float) (rect2.right - abs), (float) (rect2.bottom - i));
            path3.cubicTo(((float) rect2.right) - f6, ((float) (rect2.bottom - i)) + f3, ((float) rect2.right) - f7, ((float) (rect2.bottom - i)) - f3, (float) (rect2.right - i3), (float) (rect2.bottom - i));
            path.close();
        } else {
            int i4 = abs * 2;
            path.moveTo((float) (rect2.right - i4), (float) (rect2.top + i));
            float f8 = (float) abs;
            float f9 = 1.6667f * f8;
            float f10 = ((float) i) * 3.333f;
            float f11 = 1.3333f * f8;
            Path path4 = path;
            float f12 = f8;
            path4.cubicTo(((float) rect2.right) - f9, ((float) (rect2.top + i)) - f10, ((float) rect2.right) - f11, ((float) (rect2.top + i)) + f10, (float) (rect2.right - abs), (float) (rect2.top + i));
            float f13 = 0.6667f * f12;
            float f14 = 0.3333f * f12;
            path4.cubicTo(((float) rect2.right) - f13, ((float) (rect2.top + i)) - f10, ((float) rect2.right) - f14, ((float) (rect2.top + i)) + f10, (float) rect2.right, (float) (rect2.top + i));
            path.lineTo((float) (rect2.left + i4), (float) (rect2.bottom - i));
            Path path5 = path;
            path5.cubicTo(((float) rect2.left) + f9, ((float) (rect2.bottom - i)) + f10, ((float) rect2.left) + f11, ((float) (rect2.bottom - i)) - f10, (float) (rect2.left + abs), (float) (rect2.bottom - i));
            path5.cubicTo(((float) rect2.left) + f13, ((float) (rect2.bottom - i)) + f10, ((float) rect2.left) + f14, ((float) (rect2.bottom - i)) - f10, (float) rect2.left, (float) (rect2.bottom - i));
            path.close();
        }
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        return pathExList;
    }

    private static List<ExtendPath> getLeftRightRibbon(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        int height = rect.height();
        if (!autoShape.isAutoShape07()) {
            return null;
        }
        if (adjustData == null || adjustData.length != 3) {
            float f = (float) height;
            i2 = Math.round(f * 0.5f);
            i3 = Math.round(((float) min) * 0.5f);
            int round = Math.round(((float) rect.width()) * 0.16667f);
            i4 = Math.round(f * 0.16667f);
            i = round;
        } else {
            float f2 = (float) height;
            i2 = Math.round(adjustData[0].floatValue() * f2);
            i3 = Math.round(((float) min) * adjustData[1].floatValue());
            i = Math.round(((float) rect.width()) * adjustData[2].floatValue());
            i4 = Math.round(f2 * adjustData[2].floatValue());
        }
        int i5 = height - i4;
        ExtendPath extendPath = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath.setLine(autoShape.getLine());
            extendPath.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path = new Path();
        int i6 = i5 / 2;
        path.moveTo((float) rect2.left, (float) (rect2.top + i6));
        path.lineTo((float) (rect2.left + i3), (float) rect2.top);
        int i7 = (i5 - i2) / 2;
        path.lineTo((float) (rect2.left + i3), (float) (rect2.top + i7));
        path.lineTo((float) rect.centerX(), (float) (rect2.top + i7));
        int i8 = i / 4;
        int i9 = i4 / 2;
        path.arcTo(new RectF((float) (rect.centerX() - i8), (float) (rect2.top + i7), (float) (rect.centerX() + i8), (float) (rect2.top + i7 + i9)), 270.0f, 180.0f);
        path.arcTo(new RectF((float) (rect.centerX() - i8), (float) (rect2.top + i7 + i9), (float) (rect.centerX() + i8), (float) (rect2.top + i7 + i4)), 270.0f, -180.0f);
        path.lineTo((float) (rect2.right - i3), (float) ((rect2.bottom - i7) - i2));
        path.lineTo((float) (rect2.right - i3), (float) (rect2.bottom - i5));
        path.lineTo((float) rect2.right, (float) (rect2.bottom - i6));
        path.lineTo((float) (rect2.right - i3), (float) rect2.bottom);
        path.lineTo((float) (rect2.right - i3), (float) (rect2.bottom - i7));
        path.arcTo(new RectF((float) (rect.centerX() - i8), (float) ((rect2.bottom - i7) - i9), (float) (rect.centerX() + i8), (float) (rect2.bottom - i7)), 90.0f, 90.0f);
        path.lineTo((float) (rect.centerX() - i8), (float) (rect2.top + i7 + i2));
        path.lineTo((float) (rect2.left + i3), (float) (rect2.top + i7 + i2));
        path.lineTo((float) (rect2.left + i3), (float) (rect2.top + i5));
        path.close();
        extendPath.setPath(path);
        extendPath.setBackgroundAndFill(autoShape.getBackgroundAndFill());
        pathExList.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        if (autoShape.hasLine()) {
            extendPath2.setLine(autoShape.getLine());
            extendPath2.setBackgroundAndFill(autoShape.getLine().getBackgroundAndFill());
        }
        Path path2 = new Path();
        path2.arcTo(new RectF((float) (rect.centerX() - i8), (float) (rect2.top + i7 + i9), (float) (rect.centerX() + i8), (float) (rect2.top + i7 + i4)), 270.0f, -180.0f);
        path2.close();
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 == null || backgroundAndFill2.getFillType() != 0) {
            backgroundAndFill.setForegroundColor(PICTURECOLOR);
        } else {
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.30000001192092896d));
        }
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        extendPath2.setPath(path2);
        pathExList.add(extendPath2);
        return pathExList;
    }
}
