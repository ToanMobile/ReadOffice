package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.thirdpart.achartengine.chart.ColumnBarChart;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.model.XYSeries;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;

public class RangeBarChart extends ColumnBarChart {
    public static final String TYPE = "RangeBar";

    public String getChartType() {
        return TYPE;
    }

    /* access modifiers changed from: protected */
    public float getCoeficient() {
        return 0.5f;
    }

    RangeBarChart() {
    }

    public RangeBarChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, ColumnBarChart.Type type) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type);
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        Paint paint2 = paint;
        float[] fArr2 = fArr;
        int seriesCount = this.mDataset.getSeriesCount();
        int length = fArr2.length;
        paint2.setColor(simpleSeriesRenderer.getColor());
        paint2.setStyle(Paint.Style.FILL);
        float halfDiffX = getHalfDiffX(fArr2, length, seriesCount);
        for (int i2 = 0; i2 < length; i2 += 4) {
            drawBar(canvas, fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3], halfDiffX, seriesCount, i, paint);
        }
        paint2.setColor(simpleSeriesRenderer.getColor());
    }

    /* access modifiers changed from: protected */
    public void drawChartValuesText(Canvas canvas, XYSeries xYSeries, Paint paint, float[] fArr, int i) {
        XYSeries xYSeries2 = xYSeries;
        float[] fArr2 = fArr;
        int seriesCount = this.mDataset.getSeriesCount();
        float halfDiffX = getHalfDiffX(fArr2, fArr2.length, seriesCount);
        for (int i2 = 0; i2 < fArr2.length; i2 += 4) {
            float f = fArr2[i2];
            if (this.mType == ColumnBarChart.Type.DEFAULT) {
                f += (((float) (i * 2)) * halfDiffX) - ((((float) seriesCount) - 1.5f) * halfDiffX);
            }
            int i3 = i2 / 2;
            float f2 = f;
            Paint paint2 = paint;
            drawText(canvas, getLabel(xYSeries2.getY(i3 + 1)), f2, fArr2[i2 + 3] - 3.0f, paint2, 0.0f);
            drawText(canvas, getLabel(xYSeries2.getY(i3)), f2, fArr2[i2 + 1] + 7.5f, paint2, 0.0f);
        }
    }
}
