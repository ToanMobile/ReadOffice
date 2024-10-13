package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer;

public class ScatterChart extends XYChart {
    private static final float SIZE = 3.0f;
    public static final String TYPE = "Scatter";
    private boolean drawFrame = true;
    private float size = 3.0f;

    public String getChartType() {
        return TYPE;
    }

    ScatterChart() {
    }

    public ScatterChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        this.size = xYMultipleSeriesRenderer.getPointSize();
    }

    /* access modifiers changed from: protected */
    public void setDatasetRenderer(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super.setDatasetRenderer(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        this.size = xYMultipleSeriesRenderer.getPointSize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0046, code lost:
        if (r12 >= r11) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0048, code lost:
        drawSquare(r7, r8, r9[r12], r9[r12 + 1]);
        r12 = r12 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0069, code lost:
        if (r12 >= r11) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006b, code lost:
        drawCircle(r7, r8, r9[r12], r9[r12 + 1]);
        r12 = r12 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0077, code lost:
        if (r12 >= r11) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0079, code lost:
        drawX(r7, r8, r9[r12], r9[r12 + 1]);
        r12 = r12 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawSeries(android.graphics.Canvas r7, android.graphics.Paint r8, float[] r9, com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer r10, float r11, int r12) {
        /*
            r6 = this;
            com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer r10 = (com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer) r10
            int r11 = r10.getColor()
            r8.setColor(r11)
            boolean r11 = r10.isFillPoints()
            if (r11 == 0) goto L_0x0015
            android.graphics.Paint$Style r11 = android.graphics.Paint.Style.FILL
            r8.setStyle(r11)
            goto L_0x001a
        L_0x0015:
            android.graphics.Paint$Style r11 = android.graphics.Paint.Style.STROKE
            r8.setStyle(r11)
        L_0x001a:
            int r11 = r9.length
            int[] r12 = com.app.office.thirdpart.achartengine.chart.ScatterChart.AnonymousClass1.$SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle
            com.app.office.thirdpart.achartengine.chart.PointStyle r10 = r10.getPointStyle()
            int r10 = r10.ordinal()
            r10 = r12[r10]
            r12 = 0
            switch(r10) {
                case 1: goto L_0x0077;
                case 2: goto L_0x0069;
                case 3: goto L_0x0054;
                case 4: goto L_0x0046;
                case 5: goto L_0x0030;
                case 6: goto L_0x002c;
                default: goto L_0x002b;
            }
        L_0x002b:
            goto L_0x0085
        L_0x002c:
            r7.drawPoints(r9, r8)
            goto L_0x0085
        L_0x0030:
            r10 = 8
            float[] r10 = new float[r10]
        L_0x0034:
            if (r12 >= r11) goto L_0x0085
            r4 = r9[r12]
            int r0 = r12 + 1
            r5 = r9[r0]
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r10
            r0.drawDiamond(r1, r2, r3, r4, r5)
            int r12 = r12 + 2
            goto L_0x0034
        L_0x0046:
            if (r12 >= r11) goto L_0x0085
            r10 = r9[r12]
            int r0 = r12 + 1
            r0 = r9[r0]
            r6.drawSquare(r7, r8, r10, r0)
            int r12 = r12 + 2
            goto L_0x0046
        L_0x0054:
            r10 = 6
            float[] r10 = new float[r10]
        L_0x0057:
            if (r12 >= r11) goto L_0x0085
            r4 = r9[r12]
            int r0 = r12 + 1
            r5 = r9[r0]
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r10
            r0.drawTriangle(r1, r2, r3, r4, r5)
            int r12 = r12 + 2
            goto L_0x0057
        L_0x0069:
            if (r12 >= r11) goto L_0x0085
            r10 = r9[r12]
            int r0 = r12 + 1
            r0 = r9[r0]
            r6.drawCircle(r7, r8, r10, r0)
            int r12 = r12 + 2
            goto L_0x0069
        L_0x0077:
            if (r12 >= r11) goto L_0x0085
            r10 = r9[r12]
            int r0 = r12 + 1
            r0 = r9[r0]
            r6.drawX(r7, r8, r10, r0)
            int r12 = r12 + 2
            goto L_0x0077
        L_0x0085:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.achartengine.chart.ScatterChart.drawSeries(android.graphics.Canvas, android.graphics.Paint, float[], com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer, float, int):void");
    }

    /* renamed from: com.app.office.thirdpart.achartengine.chart.ScatterChart$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.app.office.thirdpart.achartengine.chart.PointStyle[] r0 = com.app.office.thirdpart.achartengine.chart.PointStyle.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle = r0
                com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.X     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle     // Catch:{ NoSuchFieldError -> 0x001d }
                com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.CIRCLE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.TRIANGLE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.SQUARE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle     // Catch:{ NoSuchFieldError -> 0x003e }
                com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.DIAMOND     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.app.office.thirdpart.achartengine.chart.PointStyle r1 = com.app.office.thirdpart.achartengine.chart.PointStyle.POINT     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.achartengine.chart.ScatterChart.AnonymousClass1.<clinit>():void");
        }
    }

    public int getLegendShapeWidth(int i) {
        return (int) getRenderer().getLegendTextSize();
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint) {
        XYSeriesRenderer xYSeriesRenderer = (XYSeriesRenderer) simpleSeriesRenderer;
        if (xYSeriesRenderer.isFillPoints()) {
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
        }
        float legendTextSize = f + ((((float) ((int) getRenderer().getLegendTextSize())) * this.mRenderer.getZoomRate()) / 2.0f);
        switch (AnonymousClass1.$SwitchMap$com$reader$office$thirdpart$achartengine$chart$PointStyle[xYSeriesRenderer.getPointStyle().ordinal()]) {
            case 1:
                drawX(canvas, paint, legendTextSize, f2);
                return;
            case 2:
                drawCircle(canvas, paint, legendTextSize, f2);
                return;
            case 3:
                drawTriangle(canvas, paint, new float[6], legendTextSize, f2);
                return;
            case 4:
                drawSquare(canvas, paint, legendTextSize, f2);
                return;
            case 5:
                drawDiamond(canvas, paint, new float[8], legendTextSize, f2);
                return;
            case 6:
                canvas.drawPoint(legendTextSize, f2, paint);
                return;
            default:
                return;
        }
    }

    private void drawX(Canvas canvas, Paint paint, float f, float f2) {
        float zoomRate = this.size * this.mRenderer.getZoomRate();
        float f3 = f - zoomRate;
        float f4 = f + zoomRate;
        Canvas canvas2 = canvas;
        float f5 = f2 - zoomRate;
        float f6 = f2 + zoomRate;
        Paint paint2 = paint;
        canvas2.drawLine(f3, f5, f4, f6, paint2);
        canvas2.drawLine(f4, f5, f3, f6, paint2);
    }

    private void drawCircle(Canvas canvas, Paint paint, float f, float f2) {
        canvas.drawCircle(f, f2, this.size * this.mRenderer.getZoomRate(), paint);
    }

    private void drawTriangle(Canvas canvas, Paint paint, float[] fArr, float f, float f2) {
        float zoomRate = this.size * this.mRenderer.getZoomRate();
        fArr[0] = f;
        fArr[1] = (f2 - zoomRate) - (zoomRate / 2.0f);
        fArr[2] = f - zoomRate;
        fArr[3] = f2 + zoomRate;
        fArr[4] = f + zoomRate;
        fArr[5] = fArr[3];
        drawPath(canvas, fArr, paint, true);
    }

    private void drawSquare(Canvas canvas, Paint paint, float f, float f2) {
        float zoomRate = this.size * this.mRenderer.getZoomRate();
        canvas.drawRect(f - zoomRate, f2 - zoomRate, f + zoomRate, f2 + zoomRate, paint);
    }

    private void drawDiamond(Canvas canvas, Paint paint, float[] fArr, float f, float f2) {
        float zoomRate = this.size * this.mRenderer.getZoomRate();
        fArr[0] = f;
        fArr[1] = f2 - zoomRate;
        fArr[2] = f - zoomRate;
        fArr[3] = f2;
        fArr[4] = f;
        fArr[5] = f2 + zoomRate;
        fArr[6] = f + zoomRate;
        fArr[7] = f2;
        drawPath(canvas, fArr, paint, true);
    }

    public void setDrawFrameFlag(boolean z) {
        this.drawFrame = z;
    }

    public boolean isDrawFrame() {
        return this.drawFrame;
    }
}
