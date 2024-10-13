package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.model.XYValueSeries;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer;

public class BubbleChart extends XYChart {
    private static final int MAX_BUBBLE_SIZE = 20;
    private static final int MIN_BUBBLE_SIZE = 2;
    private static final int SHAPE_WIDTH = 10;
    public static final String TYPE = "Bubble";

    public String getChartType() {
        return TYPE;
    }

    public int getLegendShapeWidth(int i) {
        return 10;
    }

    BubbleChart() {
    }

    public BubbleChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        Paint paint2 = paint;
        float[] fArr2 = fArr;
        paint2.setColor(((XYSeriesRenderer) simpleSeriesRenderer).getColor());
        paint2.setStyle(Paint.Style.FILL);
        int length = fArr2.length;
        XYValueSeries xYValueSeries = (XYValueSeries) this.mDataset.getSeriesAt(i);
        double maxValue = 20.0d / xYValueSeries.getMaxValue();
        for (int i2 = 0; i2 < length; i2 += 2) {
            drawCircle(canvas, paint, fArr2[i2], fArr2[i2 + 1], (float) ((xYValueSeries.getValue(i2 / 2) * maxValue) + 2.0d));
        }
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        drawCircle(canvas, paint, f + 10.0f, f2, 3.0f);
    }

    private void drawCircle(Canvas canvas, Paint paint, float f, float f2, float f3) {
        canvas.drawCircle(f, f2, f3, paint);
    }
}
