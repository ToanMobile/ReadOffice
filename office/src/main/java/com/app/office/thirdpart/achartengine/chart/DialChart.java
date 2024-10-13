package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.model.CategorySeries;
import com.app.office.thirdpart.achartengine.renderers.DialRenderer;

public class DialChart extends RoundChart {
    private static final int NEEDLE_RADIUS = 10;
    private DialRenderer mRenderer;

    public DialChart(CategorySeries categorySeries, DialRenderer dialRenderer) {
        super(categorySeries, dialRenderer);
        this.mRenderer = dialRenderer;
    }

    public void draw(Canvas canvas, IControl iControl, int i, int i2, int i3, int i4, Paint paint) {
        Paint paint2 = paint;
        paint2.setAntiAlias(this.mRenderer.isAntialiasing());
        paint2.setStyle(Paint.Style.FILL);
        paint2.setTextSize(this.mRenderer.getLabelsTextSize());
        int legendHeight = this.mRenderer.getLegendHeight();
        if (this.mRenderer.isShowLegend() && legendHeight == 0) {
            legendHeight = i4 / 5;
        }
        int i5 = i + i3;
        int itemCount = this.mDataset.getItemCount();
        String[] strArr = new String[itemCount];
        for (int i6 = 0; i6 < itemCount; i6++) {
            this.mDataset.getValue(i6);
            strArr[i6] = this.mDataset.getCategory(i6);
        }
        if (this.mRenderer.isFitLegend()) {
            legendHeight = drawLegend(canvas, this.mRenderer, strArr, i, i2, i3, i4, paint, true);
        }
        int i7 = (i2 + i4) - legendHeight;
        drawBackground(this.mRenderer, canvas, i, i2, i3, i4, paint, false, 0);
        int min = (int) (((double) Math.min(Math.abs(i5 - i), Math.abs(i7 - i2))) * 0.35d * ((double) this.mRenderer.getScale()));
        int i8 = (i + i5) / 2;
        int i9 = (i7 + i2) / 2;
        float f = (float) min;
        float f2 = f * 0.9f;
        float f3 = f * 1.1f;
        double minValue = this.mRenderer.getMinValue();
        double maxValue = this.mRenderer.getMaxValue();
        double angleMin = this.mRenderer.getAngleMin();
        double angleMax = this.mRenderer.getAngleMax();
        if (!this.mRenderer.isMinValueSet() || !this.mRenderer.isMaxValueSet()) {
            int seriesRendererCount = this.mRenderer.getSeriesRendererCount();
            for (int i10 = 0; i10 < seriesRendererCount; i10++) {
                double value = this.mDataset.getValue(i10);
                if (!this.mRenderer.isMinValueSet()) {
                    minValue = Math.min(minValue, value);
                }
                if (!this.mRenderer.isMaxValueSet()) {
                    maxValue = Math.max(maxValue, value);
                }
            }
        }
        if (minValue == maxValue) {
            minValue *= 0.5d;
            maxValue *= 1.5d;
        }
        double d = minValue;
        double d2 = maxValue;
        paint2.setColor(this.mRenderer.getLabelsColor());
        double minorTicksSpacing = this.mRenderer.getMinorTicksSpacing();
        double majorTicksSpacing = this.mRenderer.getMajorTicksSpacing();
        if (minorTicksSpacing == Double.MAX_VALUE) {
            minorTicksSpacing = (d2 - d) / 30.0d;
        }
        double d3 = minorTicksSpacing;
        double d4 = majorTicksSpacing == Double.MAX_VALUE ? (d2 - d) / 10.0d : majorTicksSpacing;
        double d5 = (double) f3;
        String[] strArr2 = strArr;
        drawTicks(canvas, d, d2, angleMin, angleMax, i8, i9, d5, (double) min, d3, paint, false);
        double d6 = (double) f2;
        drawTicks(canvas, d, d2, angleMin, angleMax, i8, i9, d5, d6, d4, paint, true);
        int seriesRendererCount2 = this.mRenderer.getSeriesRendererCount();
        for (int i11 = 0; i11 < seriesRendererCount2; i11++) {
            double angleForValue = getAngleForValue(this.mDataset.getValue(i11), angleMin, angleMax, d, d2);
            paint.setColor(this.mRenderer.getSeriesRendererAt(i11).getColor());
            drawNeedle(canvas, angleForValue, i8, i9, d6, this.mRenderer.getVisualTypeForIndex(i11) == DialRenderer.Type.ARROW, paint);
        }
        Paint paint3 = paint;
        drawLegend(canvas, this.mRenderer, strArr2, i, i2, i3, i4, paint, false);
    }

    private double getAngleForValue(double d, double d2, double d3, double d4, double d5) {
        return Math.toRadians(d2 + (((d - d4) * (d3 - d2)) / (d5 - d4)));
    }

    private void drawTicks(Canvas canvas, double d, double d2, double d3, double d4, int i, int i2, double d5, double d6, double d7, Paint paint, boolean z) {
        Paint paint2 = paint;
        double d8 = d;
        while (d8 <= d2) {
            double angleForValue = getAngleForValue(d8, d3, d4, d, d2);
            double sin = Math.sin(angleForValue);
            double cos = Math.cos(angleForValue);
            float f = (float) i;
            int round = Math.round(((float) (d6 * sin)) + f);
            float f2 = (float) i2;
            int round2 = Math.round(((float) (d6 * cos)) + f2);
            int round3 = Math.round(f + ((float) (sin * d5)));
            int round4 = Math.round(f2 + ((float) (cos * d5)));
            float f3 = (float) round;
            float f4 = (float) round2;
            double d9 = d8;
            canvas.drawLine(f3, f4, (float) round3, (float) round4, paint);
            if (z) {
                paint2.setTextAlign(Paint.Align.LEFT);
                if (round <= round3) {
                    paint2.setTextAlign(Paint.Align.RIGHT);
                }
                String str = d9 + "";
                long j = (long) d9;
                if (Math.round(d9) == j) {
                    str = j + "";
                }
                canvas.drawText(str, f3, f4, paint2);
            } else {
                Canvas canvas2 = canvas;
            }
            d8 = d9 + d7;
        }
    }

    private void drawNeedle(Canvas canvas, double d, int i, int i2, double d2, boolean z, Paint paint) {
        float[] fArr;
        int i3 = i;
        int i4 = i2;
        Paint paint2 = paint;
        double radians = d - Math.toRadians(90.0d);
        int sin = (int) (Math.sin(radians) * 10.0d);
        int cos = (int) (Math.cos(radians) * 10.0d);
        int sin2 = ((int) (Math.sin(d) * d2)) + i3;
        int cos2 = ((int) (Math.cos(d) * d2)) + i4;
        if (z) {
            double d3 = 0.85d * d2;
            int sin3 = ((int) (d3 * Math.sin(d))) + i3;
            int cos3 = ((int) (d3 * Math.cos(d))) + i4;
            float f = (float) sin2;
            float f2 = (float) cos2;
            fArr = new float[]{(float) (sin3 - sin), (float) (cos3 - cos), f, f2, (float) (sin3 + sin), (float) (cos3 + cos)};
            float strokeWidth = paint.getStrokeWidth();
            paint2.setStrokeWidth(5.0f);
            canvas.drawLine((float) i3, (float) i4, f, f2, paint);
            paint2.setStrokeWidth(strokeWidth);
        } else {
            fArr = new float[]{(float) (i3 - sin), (float) (i4 - cos), (float) sin2, (float) cos2, (float) (i3 + sin), (float) (i4 + cos)};
        }
        drawPath(canvas, fArr, paint2, true);
    }
}
