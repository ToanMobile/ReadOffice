package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.model.XYSeries;
import com.app.office.thirdpart.achartengine.renderers.BasicStroke;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import com.app.office.thirdpart.achartengine.util.MathHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class XYChart extends AbstractChart {
    protected static final int SHAPE_WIDTH = 12;
    private Map<Integer, double[]> mCalcRange = new HashMap();
    private PointF mCenter;
    protected XYMultipleSeriesDataset mDataset;
    protected XYMultipleSeriesRenderer mRenderer;
    private float mScale;
    private Rect mScreenR;
    private float mTranslate;

    public abstract void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i);

    public abstract String getChartType();

    public double getDefaultMinimum() {
        return Double.MAX_VALUE;
    }

    public ScatterChart getPointsChart() {
        return null;
    }

    public boolean isRenderPoints(SimpleSeriesRenderer simpleSeriesRenderer) {
        return false;
    }

    protected XYChart() {
    }

    public XYChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        this.mDataset = xYMultipleSeriesDataset;
        this.mRenderer = xYMultipleSeriesRenderer;
    }

    /* access modifiers changed from: protected */
    public void setDatasetRenderer(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        this.mDataset = xYMultipleSeriesDataset;
        this.mRenderer = xYMultipleSeriesRenderer;
    }

    public void setZoomRate(float f) {
        this.mRenderer.setZoomRate(f);
    }

    public float getZoomRate() {
        return this.mRenderer.getZoomRate();
    }

    /* access modifiers changed from: protected */
    public void drawSeriesBackgroundAndFrame(XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Canvas canvas, Rect rect, Paint paint) {
        int alpha = paint.getAlpha();
        Path path = new Path();
        path.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CCW);
        BackgroundAndFill seriesBackgroundColor = xYMultipleSeriesRenderer.getSeriesBackgroundColor();
        if (seriesBackgroundColor != null) {
            paint.setStyle(Paint.Style.FILL);
            BackgroundDrawer.drawPathBackground(canvas, (IControl) null, 1, seriesBackgroundColor, rect, (IAnimation) null, 1.0f, path, paint);
            paint.setAlpha(alpha);
        }
        Line seriesFrame = xYMultipleSeriesRenderer.getSeriesFrame();
        if (seriesFrame != null) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2.0f);
            if (seriesFrame.isDash()) {
                paint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 10.0f));
            }
            BackgroundDrawer.drawPathBackground(canvas, (IControl) null, 1, seriesFrame.getBackgroundAndFill(), rect, (IAnimation) null, 1.0f, path, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(alpha);
        }
        paint.reset();
        paint.setAntiAlias(true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ec, code lost:
        if (r12.legendPos == 2) goto L_0x00f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x0a9f, code lost:
        if (r12.legendPos == 3) goto L_0x0aa3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x05ec  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0602  */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0651  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x068f  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x081c  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x0963  */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x0b58  */
    /* JADX WARNING: Removed duplicated region for block: B:285:0x0d41  */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0da2  */
    /* JADX WARNING: Removed duplicated region for block: B:310:0x0ddd  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x0e69  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r73, com.app.office.system.IControl r74, int r75, int r76, int r77, int r78, android.graphics.Paint r79) {
        /*
            r72 = this;
            r12 = r72
            r13 = r73
            r14 = r75
            r15 = r76
            r10 = r77
            r11 = r78
            r8 = r79
            android.graphics.Rect r4 = new android.graphics.Rect
            int r9 = r14 + r10
            int r7 = r15 + r11
            r4.<init>(r14, r15, r9, r7)
            r73.save()
            r13.clipRect(r4)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            boolean r0 = r0.isAntialiasing()
            r8.setAntiAlias(r0)
            int r6 = r79.getColor()
            float r5 = r79.getStrokeWidth()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            r0 = r72
            r2 = r73
            r3 = r74
            r16 = r5
            r5 = r79
            r0.drawBackgroundAndFrame(r1, r2, r3, r4, r5)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getLegendHeight()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            boolean r1 = r1.isShowLegend()
            if (r1 == 0) goto L_0x004f
            if (r0 != 0) goto L_0x004f
            int r0 = r11 / 5
        L_0x004f:
            r17 = r0
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r0 = r12.mDataset
            int r5 = r0.getSeriesCount()
            java.lang.String[] r4 = new java.lang.String[r5]
            r0 = 0
        L_0x005a:
            if (r0 >= r5) goto L_0x006b
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r1 = r12.mDataset
            com.app.office.thirdpart.achartengine.model.XYSeries r1 = r1.getSeriesAt(r0)
            java.lang.String r1 = r1.getTitle()
            r4[r0] = r1
            int r0 = r0 + 1
            goto L_0x005a
        L_0x006b:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            com.app.office.java.awt.Rectangle r2 = r12.getTitleTextAreaSize(r0, r10, r11, r8)
            com.app.office.java.awt.Rectangle r1 = r12.getXTitleTextAreaSize(r10, r11, r8)
            com.app.office.java.awt.Rectangle r0 = r12.getYTitleTextAreaSize(r10, r11, r8)
            if (r2 == 0) goto L_0x0082
            int r3 = r2.height
            int r3 = r11 - r3
            r18 = r3
            goto L_0x0084
        L_0x0082:
            r18 = r11
        L_0x0084:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r3 = r12.mRenderer
            r19 = r6
            r6 = r0
            r0 = r72
            r13 = r1
            r1 = r3
            r3 = r2
            r2 = r4
            r20 = r3
            r8 = 0
            r3 = r77
            r21 = r4
            r4 = r18
            r22 = r5
            r5 = r79
            com.app.office.java.awt.Rectangle r5 = r0.getLegendAutoSize(r1, r2, r3, r4, r5)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            double[] r18 = r0.getMargins()
            r4 = 1
            r0 = r18[r4]
            double r2 = (double) r10
            double r0 = r0 * r2
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r4 = r12.mRenderer
            float r4 = r4.getYTitleTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r8 = r12.mRenderer
            float r8 = r8.getZoomRate()
            float r4 = r4 * r8
            r24 = r7
            double r7 = (double) r4
            double r0 = r0 + r7
            int r0 = (int) r0
            int r0 = r0 + r14
            if (r6 == 0) goto L_0x00c5
            int r1 = r6.width
            int r0 = r0 + r1
        L_0x00c5:
            r1 = 0
            r7 = r18[r1]
            r4 = r0
            double r0 = (double) r11
            double r7 = r7 * r0
            int r7 = (int) r7
            int r7 = r7 + r15
            r8 = r20
            r20 = r6
            if (r8 == 0) goto L_0x00d7
            int r6 = r8.height
            int r7 = r7 + r6
        L_0x00d7:
            r6 = 3
            r25 = r18[r6]
            r27 = r7
            double r6 = r25 * r2
            int r6 = (int) r6
            int r6 = r9 - r6
            if (r5 == 0) goto L_0x00f6
            byte r7 = r12.legendPos
            if (r7 == 0) goto L_0x00ef
            byte r7 = r12.legendPos
            r29 = r2
            r2 = 2
            if (r7 != r2) goto L_0x00f9
            goto L_0x00f2
        L_0x00ef:
            r29 = r2
            r2 = 2
        L_0x00f2:
            int r3 = r5.width
            int r6 = r6 - r3
            goto L_0x00f9
        L_0x00f6:
            r29 = r2
            r2 = 2
        L_0x00f9:
            r31 = r18[r2]
            double r2 = r31 * r0
            int r2 = (int) r2
            int r7 = r24 - r2
            if (r5 == 0) goto L_0x010f
            byte r2 = r12.legendPos
            r3 = 1
            if (r2 == r3) goto L_0x010c
            byte r2 = r12.legendPos
            r3 = 3
            if (r2 != r3) goto L_0x010f
        L_0x010c:
            int r2 = r5.height
            int r7 = r7 - r2
        L_0x010f:
            if (r13 == 0) goto L_0x0114
            int r2 = r13.height
            int r7 = r7 - r2
        L_0x0114:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r2 = r12.mRenderer
            float r2 = r2.getLabelsTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r3 = r12.mRenderer
            float r3 = r3.getZoomRate()
            float r2 = r2 * r3
            r3 = r79
            r23 = r13
            r13 = 0
            r3.setTextSize(r2)
            android.graphics.Paint$FontMetrics r2 = r79.getFontMetrics()
            float r7 = (float) r7
            float r13 = r2.descent
            float r2 = r2.ascent
            float r13 = r13 - r2
            float r7 = r7 - r13
            int r2 = (int) r7
            android.graphics.Typeface r7 = r79.getTypeface()
            if (r7 == 0) goto L_0x0160
            android.graphics.Typeface r7 = r79.getTypeface()
            java.lang.String r7 = r7.toString()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r13 = r12.mRenderer
            java.lang.String r13 = r13.getTextTypefaceName()
            boolean r7 = r7.equals(r13)
            if (r7 == 0) goto L_0x0160
            android.graphics.Typeface r7 = r79.getTypeface()
            int r7 = r7.getStyle()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r13 = r12.mRenderer
            int r13 = r13.getTextTypefaceStyle()
            if (r7 == r13) goto L_0x0173
        L_0x0160:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r7 = r12.mRenderer
            java.lang.String r7 = r7.getTextTypefaceName()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r13 = r12.mRenderer
            int r13 = r13.getTextTypefaceStyle()
            android.graphics.Typeface r7 = android.graphics.Typeface.create(r7, r13)
            r3.setTypeface(r7)
        L_0x0173:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r7 = r12.mRenderer
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r13 = r7.getOrientation()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r7 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.VERTICAL
            if (r13 != r7) goto L_0x0183
            int r6 = r6 - r17
            int r17 = r17 + -20
            int r2 = r2 + r17
        L_0x0183:
            r7 = r2
            int r2 = r13.getAngle()
            r31 = r0
            r0 = 90
            if (r2 != r0) goto L_0x0191
            r17 = 1
            goto L_0x0193
        L_0x0191:
            r17 = 0
        L_0x0193:
            float r1 = (float) r11
            float r0 = (float) r10
            r33 = r5
            float r5 = r1 / r0
            r12.mScale = r5
            int r5 = r10 - r11
            int r5 = java.lang.Math.abs(r5)
            r25 = 2
            int r5 = r5 / 2
            float r5 = (float) r5
            r12.mTranslate = r5
            r34 = r0
            float r0 = r12.mScale
            r35 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r0 > r35 ? 1 : (r0 == r35 ? 0 : -1))
            if (r0 >= 0) goto L_0x01b8
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r5 = r5 * r0
            r12.mTranslate = r5
        L_0x01b8:
            android.graphics.PointF r0 = new android.graphics.PointF
            int r5 = r9 / 2
            float r5 = (float) r5
            r35 = r1
            int r1 = r24 / 2
            float r1 = (float) r1
            r0.<init>(r5, r1)
            r12.mCenter = r0
            if (r17 == 0) goto L_0x01d1
            float r0 = (float) r2
            r5 = r73
            r1 = 0
            r12.transform(r5, r0, r1)
            goto L_0x01d3
        L_0x01d1:
            r5 = r73
        L_0x01d3:
            r0 = -2147483647(0xffffffff80000001, float:-1.4E-45)
            r15 = r22
            r1 = 0
        L_0x01d9:
            if (r1 >= r15) goto L_0x01f0
            r22 = r2
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r2 = r12.mDataset
            com.app.office.thirdpart.achartengine.model.XYSeries r2 = r2.getSeriesAt(r1)
            int r2 = r2.getScaleNumber()
            int r0 = java.lang.Math.max(r0, r2)
            int r1 = r1 + 1
            r2 = r22
            goto L_0x01d9
        L_0x01f0:
            r22 = r2
            r1 = 1
            int r2 = r0 + 1
            if (r2 >= 0) goto L_0x01fb
            r73.restore()
            return
        L_0x01fb:
            double[] r1 = new double[r2]
            double[] r0 = new double[r2]
            double[] r14 = new double[r2]
            r36 = r13
            double[] r13 = new double[r2]
            r37 = r8
            boolean[] r8 = new boolean[r2]
            r38 = r9
            boolean[] r9 = new boolean[r2]
            boolean[] r10 = new boolean[r2]
            boolean[] r11 = new boolean[r2]
            r39 = r15
            r15 = 0
        L_0x0214:
            if (r15 >= r2) goto L_0x027e
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            double r40 = r5.getXAxisMin(r15)
            r1[r15] = r40
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            double r40 = r5.getXAxisMax(r15)
            r0[r15] = r40
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            double r40 = r5.getYAxisMin(r15)
            r14[r15] = r40
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            double r40 = r5.getYAxisMax(r15)
            r13[r15] = r40
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            boolean r5 = r5.isMinXSet(r15)
            r8[r15] = r5
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            boolean r5 = r5.isMaxXSet(r15)
            r9[r15] = r5
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            boolean r5 = r5.isMinYSet(r15)
            r10[r15] = r5
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            boolean r5 = r5.isMaxYSet(r15)
            r11[r15] = r5
            java.util.Map<java.lang.Integer, double[]> r5 = r12.mCalcRange
            r40 = r11
            java.lang.Integer r11 = java.lang.Integer.valueOf(r15)
            java.lang.Object r5 = r5.get(r11)
            if (r5 != 0) goto L_0x0273
            java.util.Map<java.lang.Integer, double[]> r5 = r12.mCalcRange
            java.lang.Integer r11 = java.lang.Integer.valueOf(r15)
            r41 = r10
            r10 = 4
            double[] r10 = new double[r10]
            r5.put(r11, r10)
            goto L_0x0275
        L_0x0273:
            r41 = r10
        L_0x0275:
            int r15 = r15 + 1
            r5 = r73
            r11 = r40
            r10 = r41
            goto L_0x0214
        L_0x027e:
            r41 = r10
            r40 = r11
            java.util.HashMap r15 = new java.util.HashMap
            r15.<init>()
            r5 = 0
        L_0x0288:
            if (r5 >= r2) goto L_0x02db
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r10 = r12.mRenderer
            float r10 = r10.getLabelsTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r11 = r12.mRenderer
            float r11 = r11.getZoomRate()
            float r10 = r10 * r11
            r3.setTextSize(r10)
            android.graphics.Paint$FontMetrics r10 = r79.getFontMetrics()
            float r11 = r10.descent
            float r10 = r10.ascent
            float r11 = r11 - r10
            int r10 = r7 - r27
            float r10 = (float) r10
            float r10 = r10 / r11
            int r10 = (int) r10
            r11 = 2
            int r10 = r10 / r11
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r11 = r12.mRenderer
            int r11 = r11.getYLabels()
            int r10 = java.lang.Math.min(r11, r10)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)
            r43 = r0
            r42 = r1
            r0 = r14[r5]
            r44 = r8
            r45 = r9
            r8 = r13[r5]
            java.util.List r0 = com.app.office.thirdpart.achartengine.util.MathHelper.getLabels(r0, r8, r10)
            java.util.List r0 = r12.getValidLabels(r0)
            r15.put(r11, r0)
            int r5 = r5 + 1
            r1 = r42
            r0 = r43
            r8 = r44
            r9 = r45
            goto L_0x0288
        L_0x02db:
            r43 = r0
            r42 = r1
            r44 = r8
            r45 = r9
            r0 = 0
        L_0x02e4:
            r46 = 0
            if (r0 >= r2) goto L_0x0333
            r8 = r14[r0]
            double r8 = java.lang.Math.abs(r8)
            r10 = 4562254508917369340(0x3f50624dd2f1a9fc, double:0.001)
            int r1 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r1 <= 0) goto L_0x0330
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            java.lang.Object r1 = r15.get(r1)
            java.util.List r1 = (java.util.List) r1
            r5 = 0
            java.lang.Object r8 = r1.get(r5)
            java.lang.Double r8 = (java.lang.Double) r8
            double r8 = r8.doubleValue()
            r10 = 1
            java.lang.Object r11 = r1.get(r10)
            java.lang.Double r11 = (java.lang.Double) r11
            double r10 = r11.doubleValue()
            java.lang.Object r1 = r1.get(r5)
            java.lang.Double r1 = (java.lang.Double) r1
            double r48 = r1.doubleValue()
            double r10 = r10 - r48
            double r8 = r8 - r10
            r10 = r14[r0]
            int r1 = (r10 > r46 ? 1 : (r10 == r46 ? 0 : -1))
            if (r1 <= 0) goto L_0x0330
            int r1 = (r8 > r46 ? 1 : (r8 == r46 ? 0 : -1))
            if (r1 <= 0) goto L_0x0330
            r14[r0] = r8
        L_0x0330:
            int r0 = r0 + 1
            goto L_0x02e4
        L_0x0333:
            r0 = 0
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getLabelsTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r5 = r12.mRenderer
            float r5 = r5.getZoomRate()
            float r1 = r1 * r5
            r3.setTextSize(r1)
            r1 = 0
        L_0x0346:
            if (r1 >= r2) goto L_0x038f
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            java.lang.Object r5 = r15.get(r5)
            java.util.List r5 = (java.util.List) r5
            int r8 = r5.size()
            r9 = 0
        L_0x0357:
            if (r9 >= r8) goto L_0x038a
            java.lang.Object r10 = r5.get(r9)
            java.lang.Double r10 = (java.lang.Double) r10
            double r10 = r10.doubleValue()
            r49 = r5
            r48 = r6
            r5 = r14[r1]
            double r5 = java.lang.Math.min(r5, r10)
            r14[r1] = r5
            r5 = r13[r1]
            double r5 = java.lang.Math.max(r5, r10)
            r13[r1] = r5
            java.lang.String r5 = r12.getLabel(r10)
            float r5 = r3.measureText(r5)
            float r0 = java.lang.Math.max(r0, r5)
            int r9 = r9 + 1
            r6 = r48
            r5 = r49
            goto L_0x0357
        L_0x038a:
            r48 = r6
            int r1 = r1 + 1
            goto L_0x0346
        L_0x038f:
            r48 = r6
            float r1 = (float) r4
            float r1 = r1 + r0
            int r10 = (int) r1
            android.graphics.Rect r0 = r12.mScreenR
            if (r0 != 0) goto L_0x039f
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r12.mScreenR = r0
        L_0x039f:
            android.graphics.Rect r0 = r12.mScreenR
            r8 = r27
            r6 = r48
            r0.set(r10, r8, r6, r7)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            android.graphics.Rect r1 = r12.mScreenR
            r5 = r73
            r12.drawSeriesBackgroundAndFrame(r0, r5, r1, r3)
            double[] r11 = new double[r2]
            double[] r9 = new double[r2]
            r4 = r39
            r0 = 0
        L_0x03b8:
            if (r0 >= r4) goto L_0x047c
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r1 = r12.mDataset
            com.app.office.thirdpart.achartengine.model.XYSeries r1 = r1.getSeriesAt(r0)
            int r27 = r1.getScaleNumber()
            int r39 = r1.getItemCount()
            if (r39 != 0) goto L_0x03d5
            r39 = r4
            r48 = r7
            r49 = r8
            r7 = 3
            r25 = 2
            goto L_0x0470
        L_0x03d5:
            boolean r39 = r44[r27]
            if (r39 != 0) goto L_0x03fd
            r39 = r4
            double r3 = r1.getMinX()
            r48 = r7
            r49 = r8
            r7 = r42[r27]
            double r3 = java.lang.Math.min(r7, r3)
            r42[r27] = r3
            java.util.Map<java.lang.Integer, double[]> r3 = r12.mCalcRange
            java.lang.Integer r4 = java.lang.Integer.valueOf(r27)
            java.lang.Object r3 = r3.get(r4)
            double[] r3 = (double[]) r3
            r7 = r42[r27]
            r4 = 0
            r3[r4] = r7
            goto L_0x0403
        L_0x03fd:
            r39 = r4
            r48 = r7
            r49 = r8
        L_0x0403:
            boolean r3 = r45[r27]
            if (r3 != 0) goto L_0x0424
            double r3 = r1.getMaxX()
            r7 = r43[r27]
            double r3 = java.lang.Math.max(r7, r3)
            r43[r27] = r3
            java.util.Map<java.lang.Integer, double[]> r3 = r12.mCalcRange
            java.lang.Integer r4 = java.lang.Integer.valueOf(r27)
            java.lang.Object r3 = r3.get(r4)
            double[] r3 = (double[]) r3
            r7 = r43[r27]
            r4 = 1
            r3[r4] = r7
        L_0x0424:
            boolean r3 = r41[r27]
            if (r3 != 0) goto L_0x0449
            double r3 = r1.getMinY()
            r7 = r14[r27]
            float r3 = (float) r3
            double r3 = (double) r3
            double r3 = java.lang.Math.min(r7, r3)
            r14[r27] = r3
            java.util.Map<java.lang.Integer, double[]> r3 = r12.mCalcRange
            java.lang.Integer r4 = java.lang.Integer.valueOf(r27)
            java.lang.Object r3 = r3.get(r4)
            double[] r3 = (double[]) r3
            r7 = r14[r27]
            r25 = 2
            r3[r25] = r7
            goto L_0x044b
        L_0x0449:
            r25 = 2
        L_0x044b:
            boolean r3 = r40[r27]
            if (r3 != 0) goto L_0x046f
            double r3 = r1.getMaxY()
            r7 = r13[r27]
            float r1 = (float) r3
            double r3 = (double) r1
            double r3 = java.lang.Math.max(r7, r3)
            r13[r27] = r3
            java.util.Map<java.lang.Integer, double[]> r1 = r12.mCalcRange
            java.lang.Integer r3 = java.lang.Integer.valueOf(r27)
            java.lang.Object r1 = r1.get(r3)
            double[] r1 = (double[]) r1
            r3 = r13[r27]
            r7 = 3
            r1[r7] = r3
            goto L_0x0470
        L_0x046f:
            r7 = 3
        L_0x0470:
            int r0 = r0 + 1
            r3 = r79
            r4 = r39
            r7 = r48
            r8 = r49
            goto L_0x03b8
        L_0x047c:
            r39 = r4
            r48 = r7
            r49 = r8
            r7 = 3
            r25 = 2
            r3 = 0
        L_0x0486:
            if (r3 >= r2) goto L_0x04bb
            r0 = r43[r3]
            r27 = r42[r3]
            double r0 = r0 - r27
            int r4 = (r0 > r46 ? 1 : (r0 == r46 ? 0 : -1))
            if (r4 == 0) goto L_0x049f
            int r0 = r6 - r10
            double r0 = (double) r0
            r27 = r43[r3]
            r40 = r42[r3]
            double r27 = r27 - r40
            double r0 = r0 / r27
            r11[r3] = r0
        L_0x049f:
            r0 = r13[r3]
            r27 = r14[r3]
            double r0 = r0 - r27
            int r4 = (r0 > r46 ? 1 : (r0 == r46 ? 0 : -1))
            if (r4 == 0) goto L_0x04b8
            int r0 = r48 - r49
            double r0 = (double) r0
            r27 = r13[r3]
            r40 = r14[r3]
            double r27 = r27 - r40
            double r0 = r0 / r27
            float r0 = (float) r0
            double r0 = (double) r0
            r9[r3] = r0
        L_0x04b8:
            int r3 = r3 + 1
            goto L_0x0486
        L_0x04bb:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r0 = r0.getZoomRate()
            r27 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r27
            r1 = 1056964608(0x3f000000, float:0.5)
            float r28 = java.lang.Math.max(r0, r1)
            r4 = r39
            r3 = 0
        L_0x04ce:
            if (r3 >= r4) goto L_0x04e1
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r0 = r12.mDataset
            com.app.office.thirdpart.achartengine.model.XYSeries r0 = r0.getSeriesAt(r3)
            int r0 = r0.getItemCount()
            if (r0 <= 0) goto L_0x04de
            r3 = 1
            goto L_0x04e2
        L_0x04de:
            int r3 = r3 + 1
            goto L_0x04ce
        L_0x04e1:
            r3 = 0
        L_0x04e2:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            boolean r0 = r0.isShowLabels()
            if (r0 == 0) goto L_0x04ef
            if (r3 == 0) goto L_0x04ef
            r39 = 1
            goto L_0x04f1
        L_0x04ef:
            r39 = 0
        L_0x04f1:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            boolean r40 = r0.isShowGridH()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            boolean r41 = r0.isShowCustomTextGrid()
            if (r39 != 0) goto L_0x052d
            if (r40 == 0) goto L_0x0502
            goto L_0x052d
        L_0x0502:
            r13 = r75
            r8 = r78
            r64 = r4
            r63 = r10
            r25 = r11
            r59 = r19
            r55 = r22
            r11 = r24
            r67 = r33
            r15 = r36
            r66 = r37
            r20 = r38
            r35 = r42
            r60 = r48
            r68 = r49
            r10 = r77
            r36 = r2
            r24 = r9
            r19 = r14
            r14 = r79
            r9 = r6
            goto L_0x0b6d
        L_0x052d:
            java.lang.String r0 = r72.getChartType()
            java.lang.String r1 = "Scatter"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x055c
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3 = 0
            r44 = r42[r3]
            r50 = 4607182418800017408(0x3ff0000000000000, double:1.0)
        L_0x0543:
            double r44 = r44 + r50
            r52 = r43[r3]
            int r8 = (r44 > r52 ? 1 : (r44 == r52 ? 0 : -1))
            if (r8 > 0) goto L_0x0557
            double r52 = java.lang.Math.floor(r44)
            java.lang.Double r8 = java.lang.Double.valueOf(r52)
            r0.add(r8)
            goto L_0x0543
        L_0x0557:
            r3 = r0
            r45 = r4
            r5 = 1
            goto L_0x059d
        L_0x055c:
            r3 = 0
            r7 = r42[r3]
            r45 = r4
            r4 = r43[r3]
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getXLabels()
            java.util.List r0 = com.app.office.thirdpart.achartengine.util.MathHelper.getLabels(r7, r4, r0)
            java.util.List r0 = r12.getValidLabels(r0)
            java.lang.Object r4 = r0.get(r3)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            r42[r3] = r4
            int r4 = r0.size()
            r5 = 1
            int r4 = r4 - r5
            java.lang.Object r4 = r0.get(r4)
            java.lang.Double r4 = (java.lang.Double) r4
            double r7 = r4.doubleValue()
            r43[r3] = r7
            int r4 = r6 - r10
            double r7 = (double) r4
            r50 = r43[r3]
            r52 = r42[r3]
            double r50 = r50 - r52
            double r7 = r7 / r50
            r11[r3] = r7
            r3 = r0
        L_0x059d:
            if (r39 == 0) goto L_0x05dc
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getLabelsColor()
            r4 = r79
            r4.setColor(r0)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r0 = r0.getLabelsTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r7 = r12.mRenderer
            float r7 = r7.getZoomRate()
            float r0 = r0 * r7
            r4.setTextSize(r0)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            android.graphics.Paint$Align r0 = r0.getXLabelsAlign()
            r4.setTextAlign(r0)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            android.graphics.Paint$Align r0 = r0.getXLabelsAlign()
            android.graphics.Paint$Align r7 = android.graphics.Paint.Align.LEFT
            if (r0 != r7) goto L_0x05de
            float r0 = (float) r10
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r7 = r12.mRenderer
            float r7 = r7.getLabelsTextSize()
            r8 = 1082130432(0x40800000, float:4.0)
            float r7 = r7 / r8
            float r0 = r0 + r7
            int r0 = (int) r0
            r7 = r0
            goto L_0x05df
        L_0x05dc:
            r4 = r79
        L_0x05de:
            r7 = r10
        L_0x05df:
            r8 = r48
            float r0 = (float) r8
            r26 = 0
            r50 = r14[r26]
            int r43 = (r50 > r46 ? 1 : (r50 == r46 ? 0 : -1))
            r48 = r6
            if (r43 >= 0) goto L_0x05f6
            double r5 = (double) r8
            r50 = r9[r26]
            r52 = r14[r26]
            double r50 = r50 * r52
            double r5 = r5 + r50
            float r0 = (float) r5
        L_0x05f6:
            r43 = r0
            java.lang.String r0 = r72.getChartType()
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0651
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            java.lang.Double[] r5 = r0.getXTextLabelLocations()
            r50 = r11[r26]
            r52 = r42[r26]
            r0 = r72
            r6 = r23
            r23 = r35
            r35 = r42
            r1 = r3
            r3 = r22
            r22 = r13
            r13 = r2
            r2 = r5
            r5 = r3
            r3 = r73
            r42 = r45
            r4 = r79
            r55 = r5
            r54 = r33
            r5 = r7
            r56 = r6
            r7 = r19
            r57 = r20
            r58 = r48
            r6 = r49
            r59 = r7
            r60 = r8
            r8 = r24
            r7 = r43
            r61 = r8
            r24 = r9
            r19 = r14
            r62 = r37
            r20 = r38
            r14 = r79
            r8 = r50
            r63 = r10
            r25 = r11
            r10 = r52
            r0.drawXLabels(r1, r2, r3, r4, r5, r6, r7, r8, r10)
            goto L_0x068c
        L_0x0651:
            r60 = r8
            r63 = r10
            r25 = r11
            r59 = r19
            r57 = r20
            r55 = r22
            r56 = r23
            r61 = r24
            r54 = r33
            r23 = r35
            r62 = r37
            r20 = r38
            r35 = r42
            r42 = r45
            r58 = r48
            r24 = r9
            r22 = r13
            r19 = r14
            r13 = r2
            r14 = r4
            r2 = 0
            r0 = 0
            r8 = r25[r0]
            r10 = r35[r0]
            r0 = r72
            r1 = r3
            r3 = r73
            r4 = r79
            r5 = r7
            r6 = r49
            r7 = r43
            r0.drawXLabels(r1, r2, r3, r4, r5, r6, r7, r8, r10)
        L_0x068c:
            r7 = 0
        L_0x068d:
            if (r7 >= r13) goto L_0x0811
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            android.graphics.Paint$Align r0 = r0.getYLabelsAlign(r7)
            r14.setTextAlign(r0)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r7)
            java.lang.Object r0 = r15.get(r0)
            r8 = r0
            java.util.List r8 = (java.util.List) r8
            r0 = 0
            java.lang.Object r1 = r8.get(r0)
            java.lang.Double r1 = (java.lang.Double) r1
            double r1 = r1.doubleValue()
            r3 = r19[r0]
            double r1 = r1 - r3
            double r1 = java.lang.Math.abs(r1)
            r3 = 4517329193096183808(0x3eb0c6f7a0000000, double:9.999999974752427E-7)
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x06c7
            r1 = r19[r0]
            java.lang.Double r0 = java.lang.Double.valueOf(r1)
            r8.add(r0)
        L_0x06c7:
            int r9 = r8.size()
            r10 = 0
        L_0x06cc:
            if (r10 >= r9) goto L_0x07fa
            java.lang.Object r0 = r8.get(r10)
            java.lang.Double r0 = (java.lang.Double) r0
            double r0 = r0.doubleValue()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r2 = r12.mRenderer
            android.graphics.Paint$Align r2 = r2.getYAxisAlign(r7)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r3 = r12.mRenderer
            java.lang.Double r4 = java.lang.Double.valueOf(r0)
            java.lang.String r3 = r3.getYTextLabel(r4, r7)
            r11 = r60
            if (r3 == 0) goto L_0x06ee
            r3 = 1
            goto L_0x06ef
        L_0x06ee:
            r3 = 0
        L_0x06ef:
            double r4 = (double) r11
            r37 = r24[r7]
            r43 = r19[r7]
            double r43 = r0 - r43
            double r37 = r37 * r43
            double r4 = r4 - r37
            float r6 = (float) r4
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r4 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.HORIZONTAL
            r5 = r36
            if (r5 != r4) goto L_0x078e
            if (r39 == 0) goto L_0x0768
            if (r3 != 0) goto L_0x0768
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r3 = r12.mRenderer
            int r3 = r3.getLabelsColor()
            r14.setColor(r3)
            android.graphics.Paint$Align r3 = android.graphics.Paint.Align.LEFT
            if (r2 != r3) goto L_0x0741
            java.lang.String r2 = r12.getLabel(r0)
            r4 = r63
            float r3 = (float) r4
            java.lang.String r0 = r12.getLabel(r0)
            float r0 = r14.measureText(r0)
            float r3 = r3 - r0
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r33 = r0.getYLabelsAngle()
            r0 = r72
            r1 = r73
            r74 = r9
            r9 = r4
            r4 = r6
            r36 = r15
            r15 = r5
            r5 = r79
            r37 = r6
            r6 = r33
            r0.drawText(r1, r2, r3, r4, r5, r6)
            r38 = r8
            r8 = r58
            goto L_0x0775
        L_0x0741:
            r37 = r6
            r74 = r9
            r36 = r15
            r9 = r63
            r15 = r5
            java.lang.String r2 = r12.getLabel(r0)
            r6 = r58
            float r3 = (float) r6
            float r4 = r37 - r27
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r33 = r0.getYLabelsAngle()
            r0 = r72
            r1 = r73
            r5 = r79
            r38 = r8
            r8 = r6
            r6 = r33
            r0.drawText(r1, r2, r3, r4, r5, r6)
            goto L_0x0775
        L_0x0768:
            r37 = r6
            r38 = r8
            r74 = r9
            r36 = r15
            r8 = r58
            r9 = r63
            r15 = r5
        L_0x0775:
            if (r40 == 0) goto L_0x07e5
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getGridColor()
            r14.setColor(r0)
            float r1 = (float) r9
            float r2 = r37 - r28
            float r3 = (float) r8
            float r4 = r37 + r28
            r0 = r73
            r5 = r79
            r0.drawRect(r1, r2, r3, r4, r5)
            goto L_0x07e5
        L_0x078e:
            r37 = r6
            r38 = r8
            r74 = r9
            r36 = r15
            r8 = r58
            r9 = r63
            r15 = r5
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r2 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.VERTICAL
            if (r15 != r2) goto L_0x07e5
            if (r39 == 0) goto L_0x07c4
            if (r3 != 0) goto L_0x07c4
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r2 = r12.mRenderer
            int r2 = r2.getLabelsColor()
            r14.setColor(r2)
            java.lang.String r2 = r12.getLabel(r0)
            int r6 = r8 + 10
            float r3 = (float) r6
            float r4 = r37 - r27
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r6 = r0.getYLabelsAngle()
            r0 = r72
            r1 = r73
            r5 = r79
            r0.drawText(r1, r2, r3, r4, r5, r6)
        L_0x07c4:
            if (r40 == 0) goto L_0x07e5
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getGridColor()
            r14.setColor(r0)
            float r1 = (float) r8
            int r0 = java.lang.Math.round(r37)
            r6 = 1
            int r0 = r0 - r6
            float r2 = (float) r0
            float r3 = (float) r9
            int r0 = java.lang.Math.round(r37)
            float r4 = (float) r0
            r0 = r73
            r5 = r79
            r0.drawRect(r1, r2, r3, r4, r5)
            goto L_0x07e6
        L_0x07e5:
            r6 = 1
        L_0x07e6:
            int r10 = r10 + 1
            r58 = r8
            r63 = r9
            r60 = r11
            r8 = r38
            r9 = r74
            r71 = r36
            r36 = r15
            r15 = r71
            goto L_0x06cc
        L_0x07fa:
            r8 = r58
            r11 = r60
            r9 = r63
            r6 = 1
            r71 = r36
            r36 = r15
            r15 = r71
            int r7 = r7 + 1
            r71 = r36
            r36 = r15
            r15 = r71
            goto L_0x068d
        L_0x0811:
            r15 = r36
            r8 = r58
            r11 = r60
            r9 = r63
            r6 = 1
            if (r39 == 0) goto L_0x095e
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getLabelsColor()
            r14.setColor(r0)
            r7 = 0
        L_0x0826:
            if (r7 >= r13) goto L_0x095e
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            android.graphics.Paint$Align r10 = r0.getYAxisAlign(r7)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            java.lang.Double[] r5 = r0.getYTextLabelLocations(r7)
            int r4 = r5.length
            r3 = 0
        L_0x0836:
            if (r3 >= r4) goto L_0x0954
            r0 = r5[r3]
            r1 = r19[r7]
            double r36 = r0.doubleValue()
            int r33 = (r1 > r36 ? 1 : (r1 == r36 ? 0 : -1))
            if (r33 > 0) goto L_0x0940
            double r1 = r0.doubleValue()
            r36 = r22[r7]
            int r33 = (r1 > r36 ? 1 : (r1 == r36 ? 0 : -1))
            if (r33 > 0) goto L_0x0940
            double r1 = (double) r11
            r36 = r24[r7]
            double r43 = r0.doubleValue()
            r50 = r19[r7]
            double r43 = r43 - r50
            double r36 = r36 * r43
            double r1 = r1 - r36
            float r2 = (float) r1
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            java.lang.String r33 = r1.getYTextLabel(r0, r7)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getLabelsColor()
            r14.setColor(r0)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.HORIZONTAL
            if (r15 != r0) goto L_0x08f1
            android.graphics.Paint$Align r0 = android.graphics.Paint.Align.LEFT
            if (r10 != r0) goto L_0x08a8
            int r0 = r12.getLabelLinePos(r10)
            int r0 = r0 + r9
            float r1 = (float) r0
            float r0 = (float) r9
            r36 = r0
            r0 = r73
            r37 = r2
            r38 = r3
            r3 = r36
            r40 = r4
            r4 = r37
            r43 = r5
            r5 = r79
            r0.drawLine(r1, r2, r3, r4, r5)
            float r4 = r37 - r27
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r44 = r0.getYLabelsAngle()
            r0 = r72
            r1 = r73
            r2 = r33
            r36 = r13
            r13 = 1
            r6 = r44
            r0.drawText(r1, r2, r3, r4, r5, r6)
            goto L_0x08d8
        L_0x08a8:
            r37 = r2
            r38 = r3
            r40 = r4
            r43 = r5
            r36 = r13
            r13 = 1
            float r6 = (float) r8
            int r0 = r12.getLabelLinePos(r10)
            int r0 = r0 + r8
            float r3 = (float) r0
            r0 = r73
            r1 = r6
            r4 = r37
            r5 = r79
            r0.drawLine(r1, r2, r3, r4, r5)
            float r4 = r37 - r27
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r44 = r0.getYLabelsAngle()
            r0 = r72
            r1 = r73
            r2 = r33
            r3 = r6
            r6 = r44
            r0.drawText(r1, r2, r3, r4, r5, r6)
        L_0x08d8:
            if (r41 == 0) goto L_0x0949
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getGridColor()
            r14.setColor(r0)
            float r1 = (float) r9
            float r3 = (float) r8
            r0 = r73
            r2 = r37
            r4 = r37
            r5 = r79
            r0.drawLine(r1, r2, r3, r4, r5)
            goto L_0x0949
        L_0x08f1:
            r37 = r2
            r38 = r3
            r40 = r4
            r43 = r5
            r36 = r13
            r13 = 1
            int r0 = r12.getLabelLinePos(r10)
            int r6 = r8 - r0
            float r1 = (float) r6
            float r6 = (float) r8
            r0 = r73
            r3 = r6
            r4 = r37
            r5 = r79
            r0.drawLine(r1, r2, r3, r4, r5)
            int r0 = r8 + 10
            float r3 = (float) r0
            float r4 = r37 - r27
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r44 = r0.getYLabelsAngle()
            r0 = r72
            r1 = r73
            r2 = r33
            r33 = r6
            r6 = r44
            r0.drawText(r1, r2, r3, r4, r5, r6)
            if (r41 == 0) goto L_0x0949
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getGridColor()
            r14.setColor(r0)
            float r3 = (float) r9
            r0 = r73
            r1 = r33
            r2 = r37
            r4 = r37
            r5 = r79
            r0.drawLine(r1, r2, r3, r4, r5)
            goto L_0x0949
        L_0x0940:
            r38 = r3
            r40 = r4
            r43 = r5
            r36 = r13
            r13 = 1
        L_0x0949:
            int r3 = r38 + 1
            r13 = r36
            r4 = r40
            r5 = r43
            r6 = 1
            goto L_0x0836
        L_0x0954:
            r36 = r13
            r13 = 1
            int r7 = r7 + 1
            r13 = r36
            r6 = 1
            goto L_0x0826
        L_0x095e:
            r36 = r13
            r13 = 1
            if (r39 == 0) goto L_0x0b58
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            int r0 = r0.getLabelsColor()
            r14.setColor(r0)
            android.graphics.Paint$Align r0 = android.graphics.Paint.Align.CENTER
            r14.setTextAlign(r0)
            r14.setFakeBoldText(r13)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.HORIZONTAL
            if (r15 != r0) goto L_0x0ad6
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            boolean r0 = r0.isShowChartTitle()
            if (r0 == 0) goto L_0x09e4
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r0 = r0.getChartTitleTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getZoomRate()
            float r0 = r0 * r1
            r14.setTextSize(r0)
            r10 = r77
            r7 = r78
            com.app.office.java.awt.Rectangle r0 = r12.getMaxTitleAreaSize(r10, r7)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            java.lang.String r2 = r1.getChartTitle()
            int r1 = r10 / 2
            r6 = r75
            int r1 = r1 + r6
            float r4 = (float) r1
            r5 = r76
            r1 = r42
            float r13 = (float) r5
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r3 = r12.mRenderer
            float r3 = r3.getChartTitleTextSize()
            r22 = r1
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getZoomRate()
            float r3 = r3 * r1
            float r3 = r3 * r27
            float r13 = r13 + r3
            int r1 = r0.width
            float r3 = (float) r1
            int r0 = r0.height
            float r1 = (float) r0
            r27 = 0
            r0 = r72
            r64 = r22
            r22 = r1
            r1 = r73
            r33 = r3
            r3 = 1065353216(0x3f800000, float:1.0)
            r5 = r13
            r13 = r6
            r6 = r33
            r7 = r22
            r65 = r8
            r8 = r79
            r60 = r11
            r11 = r9
            r9 = r27
            r0.drawTitle(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x09ef
        L_0x09e4:
            r13 = r75
            r10 = r77
            r65 = r8
            r60 = r11
            r64 = r42
            r11 = r9
        L_0x09ef:
            r22 = 1045220557(0x3e4ccccd, float:0.2)
            r27 = 1061997773(0x3f4ccccd, float:0.8)
            r9 = r57
            if (r9 == 0) goto L_0x0a5c
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r0 = r0.getYTitleTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getZoomRate()
            float r0 = r0 * r1
            r14.setTextSize(r0)
            float r6 = r23 * r27
            float r7 = r34 * r22
            r8 = r62
            if (r8 == 0) goto L_0x0a21
            int r0 = r9.height
            int r1 = (int) r6
            if (r0 != r1) goto L_0x0a21
            int r0 = r8.height
            int r0 = r76 + r0
            r5 = r78
            int r1 = r5 / 2
            int r0 = r0 + r1
            goto L_0x0a27
        L_0x0a21:
            r5 = r78
            int r0 = r5 / 2
            int r0 = r76 + r0
        L_0x0a27:
            float r0 = (float) r0
            r33 = r0
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            java.lang.String r2 = r0.getYTitle()
            r3 = 1065353216(0x3f800000, float:1.0)
            float r0 = (float) r13
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getYTitleTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r4 = r12.mRenderer
            float r4 = r4.getZoomRate()
            float r1 = r1 * r4
            r4 = 1069547520(0x3fc00000, float:1.5)
            float r1 = r1 * r4
            float r4 = r0 + r1
            r37 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r0 = r72
            r1 = r73
            r5 = r33
            r66 = r8
            r8 = r79
            r63 = r11
            r11 = r9
            r9 = r37
            r0.drawTitle(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0a61
        L_0x0a5c:
            r63 = r11
            r66 = r62
            r11 = r9
        L_0x0a61:
            r0 = r56
            if (r0 == 0) goto L_0x0ad0
            float r6 = r34 * r27
            float r7 = r23 * r22
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getXTitleTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r2 = r12.mRenderer
            float r2 = r2.getZoomRate()
            float r1 = r1 * r2
            r14.setTextSize(r1)
            android.graphics.Paint$FontMetrics r1 = r79.getFontMetrics()
            int r2 = r0.height
            r9 = r61
            int r2 = r9 - r2
            float r2 = (float) r2
            if (r11 == 0) goto L_0x0a8d
            int r3 = r11.width
            int r3 = r3 + r10
            r11 = 2
            int r3 = r3 / r11
            goto L_0x0a90
        L_0x0a8d:
            r11 = 2
            int r3 = r10 / 2
        L_0x0a90:
            int r3 = r3 + r13
            float r3 = (float) r3
            r4 = r3
            r8 = r54
            if (r8 == 0) goto L_0x0aac
            byte r3 = r12.legendPos
            r5 = 1
            if (r3 == r5) goto L_0x0aa2
            byte r3 = r12.legendPos
            r5 = 3
            if (r3 != r5) goto L_0x0aad
            goto L_0x0aa3
        L_0x0aa2:
            r5 = 3
        L_0x0aa3:
            int r2 = r8.height
            int r2 = r9 - r2
            int r0 = r0.height
            int r2 = r2 - r0
            float r2 = (float) r2
            goto L_0x0aad
        L_0x0aac:
            r5 = 3
        L_0x0aad:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            java.lang.String r3 = r0.getXTitle()
            r22 = 1065353216(0x3f800000, float:1.0)
            float r0 = r1.descent
            float r23 = r2 + r0
            r27 = 0
            r0 = r72
            r1 = r73
            r2 = r3
            r3 = r22
            r5 = r23
            r67 = r8
            r8 = r79
            r11 = r9
            r9 = r27
            r0.drawTitle(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0b4d
        L_0x0ad0:
            r67 = r54
            r11 = r61
            goto L_0x0b4d
        L_0x0ad6:
            r13 = r75
            r10 = r77
            r65 = r8
            r63 = r9
            r60 = r11
            r64 = r42
            r67 = r54
            r11 = r61
            r66 = r62
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.VERTICAL
            if (r15 != r0) goto L_0x0b4d
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            java.lang.String r2 = r0.getXTitle()
            int r0 = r10 / 2
            int r0 = r0 + r13
            float r3 = (float) r0
            float r4 = (float) r11
            r6 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r0 = r72
            r1 = r73
            r5 = r79
            r0.drawText(r1, r2, r3, r4, r5, r6)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            java.lang.String r2 = r0.getYTitle()
            r9 = r65
            float r0 = (float) r9
            r1 = 1101004800(0x41a00000, float:20.0)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r3 = r12.mRenderer
            float r3 = r3.getZoomRate()
            float r3 = r3 * r1
            float r3 = r3 + r0
            r8 = r78
            int r7 = r8 / 2
            int r0 = r76 + r7
            float r4 = (float) r0
            r6 = 0
            r0 = r72
            r1 = r73
            r0.drawText(r1, r2, r3, r4, r5, r6)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r0 = r0.getChartTitleTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getZoomRate()
            float r0 = r0 * r1
            r14.setTextSize(r0)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            java.lang.String r2 = r0.getChartTitle()
            float r3 = (float) r13
            r6 = r49
            int r7 = r7 + r6
            float r4 = (float) r7
            r7 = 0
            r0 = r72
            r1 = r73
            r68 = r6
            r6 = r7
            r0.drawText(r1, r2, r3, r4, r5, r6)
            goto L_0x0b53
        L_0x0b4d:
            r8 = r78
            r68 = r49
            r9 = r65
        L_0x0b53:
            r0 = 0
            r14.setFakeBoldText(r0)
            goto L_0x0b6d
        L_0x0b58:
            r13 = r75
            r10 = r77
            r63 = r9
            r60 = r11
            r64 = r42
            r68 = r49
            r67 = r54
            r11 = r61
            r66 = r62
            r9 = r8
            r8 = r78
        L_0x0b6d:
            r6 = r64
            r7 = 0
        L_0x0b70:
            if (r7 >= r6) goto L_0x0c8f
            com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset r0 = r12.mDataset
            com.app.office.thirdpart.achartengine.model.XYSeries r5 = r0.getSeriesAt(r7)
            int r22 = r5.getScaleNumber()
            int r0 = r5.getItemCount()
            if (r0 != 0) goto L_0x0b8d
            r39 = r6
            r27 = r7
            r61 = r11
            r10 = r60
            r11 = r8
            goto L_0x0c82
        L_0x0b8d:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer r23 = r0.getSeriesRendererAt(r7)
            int r0 = r5.getItemCount()
            r1 = 2
            int r4 = r0 * 2
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2 = 0
        L_0x0ba0:
            if (r2 >= r4) goto L_0x0c4a
            int r0 = r2 / 2
            double r33 = r5.getY(r0)
            r37 = 9218868437227405311(0x7fefffffffffffff, double:1.7976931348623157E308)
            int r1 = (r33 > r37 ? 1 : (r33 == r37 ? 0 : -1))
            if (r1 == 0) goto L_0x0be4
            r39 = r6
            r27 = r7
            r1 = r63
            double r6 = (double) r1
            r37 = r25[r22]
            double r40 = r5.getX(r0)
            r42 = r35[r22]
            double r40 = r40 - r42
            double r37 = r37 * r40
            double r6 = r6 + r37
            float r0 = (float) r6
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r3.add(r0)
            r7 = r60
            double r0 = (double) r7
            r37 = r24[r22]
            r40 = r19[r22]
            double r33 = r33 - r40
            double r37 = r37 * r33
            double r0 = r0 - r37
            float r0 = (float) r0
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r3.add(r0)
            goto L_0x0c29
        L_0x0be4:
            r39 = r6
            r27 = r7
            r7 = r60
            int r0 = r3.size()
            if (r0 <= 0) goto L_0x0c29
            float r0 = (float) r7
            r6 = r2
            double r1 = (double) r7
            r33 = r24[r22]
            r37 = r19[r22]
            double r33 = r33 * r37
            double r1 = r1 + r33
            float r1 = (float) r1
            float r33 = java.lang.Math.min(r0, r1)
            r0 = r72
            r2 = r63
            r1 = r5
            r34 = r6
            r6 = r2
            r2 = r73
            r37 = r3
            r3 = r79
            r38 = r4
            r4 = r37
            r40 = r5
            r5 = r23
            r61 = r11
            r11 = r6
            r6 = r33
            r10 = r7
            r7 = r27
            r63 = r11
            r11 = r8
            r8 = r15
            r0.drawSeries(r1, r2, r3, r4, r5, r6, r7, r8)
            r37.clear()
            goto L_0x0c35
        L_0x0c29:
            r34 = r2
            r37 = r3
            r38 = r4
            r40 = r5
            r10 = r7
            r61 = r11
            r11 = r8
        L_0x0c35:
            int r2 = r34 + 2
            r60 = r10
            r8 = r11
            r7 = r27
            r3 = r37
            r4 = r38
            r6 = r39
            r5 = r40
            r11 = r61
            r10 = r77
            goto L_0x0ba0
        L_0x0c4a:
            r37 = r3
            r40 = r5
            r39 = r6
            r27 = r7
            r61 = r11
            r10 = r60
            r11 = r8
            int r0 = r37.size()
            if (r0 <= 0) goto L_0x0c82
            float r0 = (float) r10
            double r1 = (double) r10
            r3 = r24[r22]
            r5 = r19[r22]
            double r3 = r3 * r5
            double r1 = r1 + r3
            float r1 = (float) r1
            float r6 = java.lang.Math.min(r0, r1)
            r0 = r72
            r1 = r40
            r2 = r73
            r3 = r79
            r4 = r37
            r5 = r23
            r7 = r27
            r8 = r15
            r0.drawSeries(r1, r2, r3, r4, r5, r6, r7, r8)
            android.graphics.Paint$Style r0 = android.graphics.Paint.Style.FILL
            r14.setStyle(r0)
        L_0x0c82:
            int r7 = r27 + 1
            r60 = r10
            r8 = r11
            r6 = r39
            r11 = r61
            r10 = r77
            goto L_0x0b70
        L_0x0c8f:
            r61 = r11
            r10 = r60
            r11 = r8
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            int r6 = r11 - r10
            r8 = 1
            int r22 = r1.getMarginsColor()
            r0 = r72
            r2 = r73
            r3 = r75
            r4 = r10
            r5 = r77
            r7 = r79
            r58 = r9
            r9 = r22
            r0.drawBackground(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            r0 = 0
            r2 = r18[r0]
            double r2 = r2 * r31
            int r6 = (int) r2
            int r9 = r1.getMarginsColor()
            r0 = r72
            r2 = r73
            r3 = r75
            r4 = r76
            r0.drawBackground(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.HORIZONTAL
            if (r15 != r0) goto L_0x0cfd
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            int r5 = r63 - r13
            int r22 = r11 - r76
            r8 = 1
            int r9 = r1.getMarginsColor()
            r0 = r72
            r2 = r73
            r3 = r75
            r4 = r76
            r6 = r22
            r7 = r79
            r0.drawBackground(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            r9 = 3
            r2 = r18[r9]
            double r2 = r2 * r29
            int r5 = (int) r2
            int r18 = r1.getMarginsColor()
            r2 = r73
            r3 = r58
            r60 = r10
            r10 = 3
            r9 = r18
            r0.drawBackground(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0d39
        L_0x0cfd:
            r60 = r10
            r10 = 3
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.VERTICAL
            if (r15 != r0) goto L_0x0d39
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            r8 = r58
            r9 = r60
            int r5 = r77 - r8
            int r18 = r11 - r76
            r22 = 1
            int r23 = r1.getMarginsColor()
            r0 = r72
            r2 = r73
            r3 = r8
            r4 = r76
            r6 = r18
            r7 = r79
            r69 = r8
            r8 = r22
            r70 = r9
            r9 = r23
            r0.drawBackground(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            int r5 = r63 - r13
            r8 = 1
            int r9 = r1.getMarginsColor()
            r3 = r75
            r0.drawBackground(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0d3d
        L_0x0d39:
            r69 = r58
            r70 = r60
        L_0x0d3d:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.HORIZONTAL
            if (r15 != r0) goto L_0x0da2
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            boolean r0 = r0.isShowLegend()
            if (r0 == 0) goto L_0x0dd0
            r0 = r67
            int r6 = r0.width
            int r0 = r0.height
            int r7 = java.lang.Math.min(r11, r0)
            byte r0 = r72.getLegendPosition()
            if (r0 == 0) goto L_0x0d6f
            r1 = 1
            if (r0 == r1) goto L_0x0d65
            r1 = 2
            if (r0 == r1) goto L_0x0d6f
            if (r0 == r10) goto L_0x0d66
            r5 = r76
            r4 = r13
            goto L_0x0d93
        L_0x0d65:
            r1 = 2
        L_0x0d66:
            int r0 = r77 - r6
            int r0 = r0 / r1
            int r0 = r0 + r13
            int r1 = r61 - r7
        L_0x0d6c:
            r4 = r0
            r5 = r1
            goto L_0x0d93
        L_0x0d6f:
            int r9 = r20 - r6
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r0 = r12.mRenderer
            float r0 = r0.getLegendTextSize()
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            float r1 = r1.getZoomRate()
            float r0 = r0 * r1
            int r0 = (int) r0
            int r0 = r9 - r0
            r1 = r66
            if (r1 == 0) goto L_0x0d8c
            int r1 = r1.height
            int r1 = r1 + r11
            r2 = 2
            int r1 = r1 / r2
            goto L_0x0d90
        L_0x0d8c:
            r2 = 2
            int r1 = r11 - r7
            int r1 = r1 / r2
        L_0x0d90:
            int r1 = r76 + r1
            goto L_0x0d6c
        L_0x0d93:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r2 = r12.mRenderer
            r9 = 0
            r0 = r72
            r1 = r73
            r3 = r21
            r8 = r79
            r0.drawLegend(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0dd0
        L_0x0da2:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.VERTICAL
            if (r15 != r0) goto L_0x0dd0
            r10 = r55
            float r9 = (float) r10
            r8 = r73
            r0 = 1
            r12.transform(r8, r9, r0)
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r2 = r12.mRenderer
            r0 = 2
            int r4 = r13 + 2
            r13 = 0
            r0 = r72
            r1 = r73
            r3 = r21
            r5 = r76
            r6 = r77
            r7 = r78
            r11 = r8
            r8 = r79
            r22 = r10
            r10 = r9
            r9 = r13
            r0.drawLegend(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r0 = 0
            r12.transform(r11, r10, r0)
            goto L_0x0dd5
        L_0x0dd0:
            r11 = r73
            r22 = r55
            r0 = 0
        L_0x0dd5:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            boolean r1 = r1.isShowAxes()
            if (r1 == 0) goto L_0x0e67
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            int r1 = r1.getAxesColor()
            r14.setColor(r1)
            r1 = 1
            r14.setFakeBoldText(r1)
            r2 = r70
            float r6 = (float) r2
            r1 = r19[r0]
            int r3 = (r1 > r46 ? 1 : (r1 == r46 ? 0 : -1))
            if (r3 >= 0) goto L_0x0df7
            r1 = r24[r0]
            r1 = r19[r0]
        L_0x0df7:
            r0 = r63
            float r7 = (float) r0
            int r0 = java.lang.Math.round(r6)
            float r0 = (float) r0
            float r2 = r0 - r28
            r0 = r69
            float r8 = (float) r0
            int r0 = java.lang.Math.round(r6)
            float r0 = (float) r0
            float r4 = r0 + r28
            r0 = r73
            r1 = r7
            r3 = r8
            r5 = r79
            r0.drawRect(r1, r2, r3, r4, r5)
            r0 = r36
            r3 = 0
            r9 = 0
        L_0x0e18:
            if (r3 >= r0) goto L_0x0e2c
            if (r9 != 0) goto L_0x0e2c
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer r1 = r12.mRenderer
            android.graphics.Paint$Align r1 = r1.getYAxisAlign(r3)
            android.graphics.Paint$Align r2 = android.graphics.Paint.Align.RIGHT
            if (r1 != r2) goto L_0x0e28
            r9 = 1
            goto L_0x0e29
        L_0x0e28:
            r9 = 0
        L_0x0e29:
            int r3 = r3 + 1
            goto L_0x0e18
        L_0x0e2c:
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r0 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.HORIZONTAL
            if (r15 != r0) goto L_0x0e50
            float r1 = r7 - r28
            r0 = r68
            float r10 = (float) r0
            float r3 = r7 + r28
            r0 = r73
            r2 = r10
            r4 = r6
            r5 = r79
            r0.drawRect(r1, r2, r3, r4, r5)
            if (r9 == 0) goto L_0x0e63
            float r1 = r8 - r28
            float r3 = r8 + r28
            r0 = r73
            r2 = r10
            r4 = r6
            r5 = r79
            r0.drawRect(r1, r2, r3, r4, r5)
            goto L_0x0e63
        L_0x0e50:
            r0 = r68
            com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer$Orientation r1 = com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer.Orientation.VERTICAL
            if (r15 != r1) goto L_0x0e63
            float r1 = r8 - r28
            float r2 = (float) r0
            float r3 = r8 + r28
            r0 = r73
            r4 = r6
            r5 = r79
            r0.drawRect(r1, r2, r3, r4, r5)
        L_0x0e63:
            r0 = 0
            r14.setFakeBoldText(r0)
        L_0x0e67:
            if (r17 == 0) goto L_0x0e70
            r0 = r22
            float r0 = (float) r0
            r1 = 1
            r12.transform(r11, r0, r1)
        L_0x0e70:
            r0 = r59
            r14.setColor(r0)
            r0 = r16
            r14.setStrokeWidth(r0)
            r73.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.achartengine.chart.XYChart.draw(android.graphics.Canvas, com.app.office.system.IControl, int, int, int, int, android.graphics.Paint):void");
    }

    /* access modifiers changed from: protected */
    public Rect getScreenR() {
        return this.mScreenR;
    }

    /* access modifiers changed from: protected */
    public void setScreenR(Rect rect) {
        this.mScreenR = rect;
    }

    private List<Double> getValidLabels(List<Double> list) {
        ArrayList arrayList = new ArrayList(list);
        for (Double next : list) {
            if (next.isNaN()) {
                arrayList.remove(next);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void drawSeries(XYSeries xYSeries, Canvas canvas, Paint paint, List<Float> list, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i, XYMultipleSeriesRenderer.Orientation orientation) {
        ScatterChart pointsChart;
        Paint paint2 = paint;
        BasicStroke stroke = simpleSeriesRenderer.getStroke();
        Paint.Cap strokeCap = paint.getStrokeCap();
        Paint.Join strokeJoin = paint.getStrokeJoin();
        float strokeMiter = paint.getStrokeMiter();
        PathEffect pathEffect = paint.getPathEffect();
        Paint.Style style = paint.getStyle();
        if (stroke != null) {
            DashPathEffect dashPathEffect = null;
            if (stroke.getIntervals() != null) {
                dashPathEffect = new DashPathEffect(stroke.getIntervals(), stroke.getPhase());
            }
            DashPathEffect dashPathEffect2 = dashPathEffect;
            setStroke(stroke.getCap(), stroke.getJoin(), stroke.getMiter(), Paint.Style.FILL_AND_STROKE, dashPathEffect2, paint);
        }
        float[] floats = MathHelper.getFloats(list);
        drawSeries(canvas, paint, floats, simpleSeriesRenderer, f, i);
        if (isRenderPoints(simpleSeriesRenderer) && (pointsChart = getPointsChart()) != null) {
            pointsChart.drawSeries(canvas, paint, floats, simpleSeriesRenderer, f, i);
        }
        paint2.setTextSize(simpleSeriesRenderer.getChartValuesTextSize());
        if (orientation == XYMultipleSeriesRenderer.Orientation.HORIZONTAL) {
            paint2.setTextAlign(Paint.Align.CENTER);
        } else {
            paint2.setTextAlign(Paint.Align.LEFT);
        }
        if (simpleSeriesRenderer.isDisplayChartValues()) {
            drawChartValuesText(canvas, xYSeries, paint, floats, i);
        }
        if (stroke != null) {
            setStroke(strokeCap, strokeJoin, strokeMiter, style, pathEffect, paint);
        }
    }

    private void setStroke(Paint.Cap cap, Paint.Join join, float f, Paint.Style style, PathEffect pathEffect, Paint paint) {
        paint.setStrokeCap(cap);
        paint.setStrokeJoin(join);
        paint.setStrokeMiter(f);
        paint.setPathEffect(pathEffect);
        paint.setStyle(style);
    }

    /* access modifiers changed from: protected */
    public void drawChartValuesText(Canvas canvas, XYSeries xYSeries, Paint paint, float[] fArr, int i) {
        for (int i2 = 0; i2 < fArr.length; i2 += 2) {
            drawText(canvas, getLabel(xYSeries.getY(i2 / 2)), fArr[i2], fArr[i2 + 1] - 3.5f, paint, 0.0f);
        }
    }

    /* access modifiers changed from: protected */
    public void drawText(Canvas canvas, String str, float f, float f2, Paint paint, float f3) {
        float f4 = ((float) (-this.mRenderer.getOrientation().getAngle())) + f3;
        int i = (f4 > 0.0f ? 1 : (f4 == 0.0f ? 0 : -1));
        if (i != 0) {
            canvas.rotate(f4, f, f2);
        }
        canvas.drawText(str, f, f2, paint);
        if (i != 0) {
            canvas.rotate(-f4, f, f2);
        }
    }

    private Rectangle getXTitleTextAreaSize(int i, int i2, Paint paint) {
        if (this.mRenderer.getXTitle().length() <= 0) {
            return null;
        }
        return getTextSize(this.mRenderer.getXTitle(), this.mRenderer.getXTitleTextSize() * this.mRenderer.getZoomRate(), ((float) i) * 0.8f, ((float) i2) * 0.2f, paint);
    }

    private Rectangle getYTitleTextAreaSize(int i, int i2, Paint paint) {
        if (this.mRenderer.getYTitle().length() <= 0) {
            return null;
        }
        Rectangle textSize = getTextSize(this.mRenderer.getYTitle(), this.mRenderer.getXTitleTextSize() * this.mRenderer.getZoomRate(), ((float) i2) * 0.8f, ((float) i) * 0.2f, paint);
        int i3 = textSize.width;
        textSize.width = textSize.height;
        textSize.height = i3;
        return textSize;
    }

    private void transform(Canvas canvas, float f, boolean z) {
        if (z) {
            float f2 = this.mScale;
            canvas.scale(1.0f / f2, f2);
            float f3 = this.mTranslate;
            canvas.translate(f3, -f3);
            canvas.rotate(-f, this.mCenter.x, this.mCenter.y);
            return;
        }
        canvas.rotate(f, this.mCenter.x, this.mCenter.y);
        float f4 = this.mTranslate;
        canvas.translate(-f4, f4);
        float f5 = this.mScale;
        canvas.scale(f5, 1.0f / f5);
    }

    /* access modifiers changed from: protected */
    public String getLabel(double d) {
        if (d == ((double) Math.round(d))) {
            return Math.round(d) + "";
        }
        return d + "";
    }

    /* access modifiers changed from: protected */
    public void drawXLabels(List<Double> list, Double[] dArr, Canvas canvas, Paint paint, int i, int i2, float f, double d, double d2) {
        int i3;
        float f2;
        boolean z;
        double d3;
        float f3;
        int i4;
        Double d4;
        Double[] dArr2 = dArr;
        Paint paint2 = paint;
        int i5 = i;
        int i6 = i2;
        double d5 = d;
        int size = list.size();
        boolean isShowLabels = this.mRenderer.isShowLabels();
        boolean isShowGridV = this.mRenderer.isShowGridV();
        boolean isShowCustomTextGrid = this.mRenderer.isShowCustomTextGrid();
        float max = Math.max(this.mRenderer.getZoomRate() / 2.0f, 0.5f);
        if (dArr2 == null || dArr2.length == 0) {
            int i7 = 0;
            while (i7 < size) {
                double doubleValue = list.get(i7).doubleValue();
                float f4 = (float) (((double) i5) + ((doubleValue - d2) * d5));
                if (isShowGridV) {
                    Canvas canvas2 = canvas;
                    f2 = f4;
                    float f5 = f4 + max;
                    i3 = size;
                    z = isShowGridV;
                    d3 = doubleValue;
                    canvas2.drawRect(f4 - max, (float) i6, f5, f + (this.mRenderer.getZoomRate() * 4.0f), paint);
                } else {
                    f2 = f4;
                    i3 = size;
                    z = isShowGridV;
                    d3 = doubleValue;
                    Canvas canvas3 = canvas;
                    float f6 = f;
                    canvas3.drawRect(f2 - max, f6, f2 + max, f + (this.mRenderer.getZoomRate() * 4.0f), paint);
                }
                drawText(canvas, getLabel(d3), f2, f + (((this.mRenderer.getLabelsTextSize() * 4.0f) / 3.0f) * this.mRenderer.getZoomRate()), paint, this.mRenderer.getXLabelsAngle());
                if (isShowCustomTextGrid) {
                    paint2.setColor(this.mRenderer.getGridColor());
                    float f7 = f2 + (((float) d5) / 2.0f);
                    canvas.drawRect(f7 - max, f, f7 + max, (float) i6, paint);
                }
                i7++;
                isShowGridV = z;
                size = i3;
            }
        } else if (isShowLabels) {
            paint2.setColor(this.mRenderer.getLabelsColor());
            int length = dArr2.length;
            int i8 = 0;
            while (i8 < length) {
                Double d6 = dArr2[i8];
                float doubleValue2 = (float) (((double) i5) + ((d6.doubleValue() - d2) * d5));
                paint2.setColor(this.mRenderer.getLabelsColor());
                if (isShowGridV) {
                    float f8 = (((float) d5) / 2.0f) + doubleValue2;
                    float f9 = f8 + max;
                    f3 = doubleValue2;
                    float zoomRate = f + (this.mRenderer.getZoomRate() * 4.0f);
                    i4 = i8;
                    d4 = d6;
                    canvas.drawRect(f8 - max, (float) i6, f9, zoomRate, paint);
                } else {
                    f3 = doubleValue2;
                    i4 = i8;
                    d4 = d6;
                    float f10 = f3 + (((float) d5) / 2.0f);
                    canvas.drawRect(f10 - max, f, f10 + max, f + (this.mRenderer.getZoomRate() * 4.0f), paint);
                }
                drawText(canvas, this.mRenderer.getXTextLabel(d4), f3, f + (this.mRenderer.getLabelsTextSize() * this.mRenderer.getZoomRate()), paint, this.mRenderer.getXLabelsAngle());
                if (isShowCustomTextGrid) {
                    paint2.setColor(this.mRenderer.getGridColor());
                    float f11 = f3 + (((float) d5) / 2.0f);
                    canvas.drawRect(f11 - max, f, f11 + max, (float) i6, paint);
                }
                i8 = i4 + 1;
            }
        }
    }

    public XYMultipleSeriesRenderer getRenderer() {
        return this.mRenderer;
    }

    public XYMultipleSeriesDataset getDataset() {
        return this.mDataset;
    }

    public double[] getCalcRange(int i) {
        return this.mCalcRange.get(Integer.valueOf(i));
    }

    public void setCalcRange(double[] dArr, int i) {
        this.mCalcRange.put(Integer.valueOf(i), dArr);
    }

    public double[] toRealPoint(float f, float f2) {
        return toRealPoint(f, f2, 0);
    }

    public double[] toScreenPoint(double[] dArr) {
        return toScreenPoint(dArr, 0);
    }

    private int getLabelLinePos(Paint.Align align) {
        return align == Paint.Align.LEFT ? -4 : 4;
    }

    public double[] toRealPoint(float f, float f2, int i) {
        double xAxisMin = this.mRenderer.getXAxisMin(i);
        double xAxisMax = this.mRenderer.getXAxisMax(i);
        double yAxisMin = this.mRenderer.getYAxisMin(i);
        return new double[]{((((double) (f - ((float) this.mScreenR.left))) * (xAxisMax - xAxisMin)) / ((double) this.mScreenR.width())) + xAxisMin, ((((double) (((float) (this.mScreenR.top + this.mScreenR.height())) - f2)) * (this.mRenderer.getYAxisMax(i) - yAxisMin)) / ((double) this.mScreenR.height())) + yAxisMin};
    }

    public double[] toScreenPoint(double[] dArr, int i) {
        int i2 = i;
        double xAxisMin = this.mRenderer.getXAxisMin(i2);
        double xAxisMax = this.mRenderer.getXAxisMax(i2);
        double yAxisMin = this.mRenderer.getYAxisMin(i2);
        double yAxisMax = this.mRenderer.getYAxisMax(i2);
        if (!this.mRenderer.isMinXSet(i2) || !this.mRenderer.isMaxXSet(i2) || !this.mRenderer.isMinXSet(i2) || !this.mRenderer.isMaxYSet(i2)) {
            double[] calcRange = getCalcRange(i2);
            xAxisMin = calcRange[0];
            xAxisMax = calcRange[1];
            yAxisMin = calcRange[2];
            yAxisMax = calcRange[3];
        }
        return new double[]{(((dArr[0] - xAxisMin) * ((double) this.mScreenR.width())) / (xAxisMax - xAxisMin)) + ((double) this.mScreenR.left), (((yAxisMax - dArr[1]) * ((double) this.mScreenR.height())) / (yAxisMax - yAxisMin)) + ((double) this.mScreenR.top)};
    }
}
