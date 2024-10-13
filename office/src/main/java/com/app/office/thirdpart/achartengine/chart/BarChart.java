package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.model.XYSeries;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;

public class BarChart extends XYChart {
    public static final String TYPE = "Bar";
    protected Type mType = Type.DEFAULT;

    public enum Type {
        DEFAULT,
        STACKED
    }

    public String getChartType() {
        return TYPE;
    }

    /* access modifiers changed from: protected */
    public float getCoeficient() {
        return 1.0f;
    }

    public double getDefaultMinimum() {
        return 0.0d;
    }

    BarChart() {
    }

    public BarChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        this.mType = type;
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        Paint paint2 = paint;
        float[] fArr2 = fArr;
        int seriesCount = this.mDataset.getSeriesCount();
        int length = fArr2.length;
        paint2.setColor(simpleSeriesRenderer.getColor());
        paint2.setStyle(Paint.Style.FILL);
        float halfDiffX = getHalfDiffX(fArr2, length, seriesCount);
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f2 = fArr2[i2];
            drawBar(canvas, f2, f, f2, fArr2[i2 + 1], halfDiffX, seriesCount, i, paint);
        }
        paint2.setColor(simpleSeriesRenderer.getColor());
    }

    /* access modifiers changed from: protected */
    public void drawBar(Canvas canvas, float f, float f2, float f3, float f4, float f5, int i, int i2, Paint paint) {
        int i3 = i2;
        int scaleNumber = this.mDataset.getSeriesAt(i3).getScaleNumber();
        if (this.mType == Type.STACKED) {
            drawBar(canvas, f - f5, f4, f3 + f5, f2, scaleNumber, i2, paint);
            return;
        }
        float f6 = (f - (((float) i) * f5)) + (((float) (i3 * 2)) * f5);
        drawBar(canvas, f6, f4, f6 + (2.0f * f5), f2, scaleNumber, i2, paint);
    }

    private void drawBar(Canvas canvas, float f, float f2, float f3, float f4, int i, int i2, Paint paint) {
        int i3;
        int i4;
        int i5;
        int i6;
        float f5 = f2;
        float f6 = f4;
        int i7 = i;
        Paint paint2 = paint;
        SimpleSeriesRenderer seriesRendererAt = this.mRenderer.getSeriesRendererAt(i2);
        if (seriesRendererAt.isGradientEnabled()) {
            float f7 = (float) toScreenPoint(new double[]{0.0d, seriesRendererAt.getGradientStopValue()}, i7)[1];
            float f8 = (float) toScreenPoint(new double[]{0.0d, seriesRendererAt.getGradientStartValue()}, i7)[1];
            float max = Math.max(f7, f5);
            float min = Math.min(f8, f6);
            int gradientStopColor = seriesRendererAt.getGradientStopColor();
            int gradientStartColor = seriesRendererAt.getGradientStartColor();
            if (f5 < f7) {
                paint2.setColor(gradientStartColor);
                i3 = gradientStartColor;
                i5 = gradientStopColor;
                canvas.drawRect((float) Math.round(f), (float) Math.round(f2), (float) Math.round(f3), (float) Math.round(max), paint);
                i4 = i3;
            } else {
                i3 = gradientStartColor;
                i5 = gradientStopColor;
                i4 = getGradientPartialColor(i3, i5, (f8 - max) / (f8 - f7));
            }
            if (f6 > f8) {
                paint2.setColor(i5);
                i6 = i5;
                canvas.drawRect((float) Math.round(f), (float) Math.round(min), (float) Math.round(f3), (float) Math.round(f4), paint);
            } else {
                i6 = getGradientPartialColor(i5, i3, (min - f7) / (f8 - f7));
            }
            GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{i6, i4});
            gradientDrawable.setBounds(Math.round(f), Math.round(max), Math.round(f3), Math.round(min));
            gradientDrawable.draw(canvas);
            return;
        }
        Canvas canvas2 = canvas;
        if (Math.abs(f6 - f5) >= 1.0E-7f) {
            canvas.drawRect((float) Math.round(f), (float) Math.round(f2), (float) Math.round(f3), (float) Math.round(f4), paint);
            int color = paint.getColor();
            paint2.setColor(-16777216);
            paint2.setStyle(Paint.Style.STROKE);
            canvas.drawRect((float) Math.round(f), (float) Math.round(f2), (float) Math.round(f3), (float) Math.round(f4), paint);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setColor(color);
        }
    }

    private int getGradientPartialColor(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb(Math.round((((float) Color.alpha(i)) * f) + (((float) Color.alpha(i2)) * f2)), Math.round((((float) Color.red(i)) * f) + (((float) Color.red(i2)) * f2)), Math.round((((float) Color.green(i)) * f) + (((float) Color.green(i2)) * f2)), Math.round((f * ((float) Color.blue(i))) + (f2 * ((float) Color.blue(i2)))));
    }

    /* access modifiers changed from: protected */
    public void drawChartValuesText(Canvas canvas, XYSeries xYSeries, Paint paint, float[] fArr, int i) {
        float[] fArr2 = fArr;
        int seriesCount = this.mDataset.getSeriesCount();
        float halfDiffX = getHalfDiffX(fArr2, fArr2.length, seriesCount);
        for (int i2 = 0; i2 < fArr2.length; i2 += 2) {
            float f = fArr2[i2];
            if (this.mType == Type.DEFAULT) {
                f += (((float) (i * 2)) * halfDiffX) - ((((float) seriesCount) - 1.5f) * halfDiffX);
            }
            XYSeries xYSeries2 = xYSeries;
            String label = getLabel(xYSeries.getY(i2 / 2));
            drawText(canvas, label, f, fArr2[i2 + 1] - 3.5f, paint, 0.0f);
        }
    }

    public int getLegendShapeWidth(int i) {
        return (int) getRenderer().getLegendTextSize();
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint) {
        float legendTextSize = getRenderer().getLegendTextSize() * this.mRenderer.getZoomRate();
        float f3 = legendTextSize / 2.0f;
        float f4 = f + f3;
        Canvas canvas2 = canvas;
        float f5 = f4;
        float f6 = f2 - f3;
        float f7 = legendTextSize + f4;
        float f8 = f2 + f3;
        Paint paint2 = paint;
        canvas2.drawRect(f5, f6, f7, f8, paint2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(-16777216);
        canvas2.drawRect(f5, f6, f7, f8, paint2);
        paint.setStyle(Paint.Style.FILL);
    }

    /* access modifiers changed from: protected */
    public float getHalfDiffX(float[] fArr, int i, int i2) {
        float f = (fArr[i - 2] - fArr[0]) / ((float) (i > 2 ? i - 2 : i));
        if (f == 0.0f) {
            f = (float) (getScreenR().width() / 2);
        }
        if (this.mType != Type.STACKED) {
            f /= (float) (i2 + 1);
        }
        return (float) (((double) f) / (((double) getCoeficient()) * (this.mRenderer.getBarSpacing() + 1.0d)));
    }
}
