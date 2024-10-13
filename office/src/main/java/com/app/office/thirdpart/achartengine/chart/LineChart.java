package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYSeriesRenderer;

public class LineChart extends XYChart {
    private static final int SHAPE_WIDTH = 30;
    public static final String TYPE = "Line";
    private ScatterChart pointsChart;

    public String getChartType() {
        return TYPE;
    }

    LineChart() {
    }

    public LineChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        this.pointsChart = new ScatterChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    /* access modifiers changed from: protected */
    public void setDatasetRenderer(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super.setDatasetRenderer(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        this.pointsChart = new ScatterChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        int length = fArr.length;
        XYSeriesRenderer xYSeriesRenderer = (XYSeriesRenderer) simpleSeriesRenderer;
        float strokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(Math.max(xYSeriesRenderer.getLineWidth() * this.mRenderer.getZoomRate(), 1.0f));
        if (xYSeriesRenderer.isFillBelowLine()) {
            paint.setColor(xYSeriesRenderer.getFillBelowLineColor());
            float[] fArr2 = new float[(fArr.length + 4)];
            System.arraycopy(fArr, 0, fArr2, 0, length);
            fArr2[0] = fArr[0] + 1.0f;
            fArr2[length] = fArr2[length - 2];
            int i2 = length + 1;
            fArr2[i2] = f;
            fArr2[length + 2] = fArr2[0];
            fArr2[length + 3] = fArr2[i2];
            paint.setStyle(Paint.Style.FILL);
            drawPath(canvas, fArr2, paint, true);
        }
        paint.setColor(simpleSeriesRenderer.getColor());
        paint.setStyle(Paint.Style.STROKE);
        drawPath(canvas, fArr, paint, false);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(strokeWidth);
    }

    public int getLegendShapeWidth(int i) {
        return (int) getRenderer().getLegendTextSize();
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint) {
        if (isRenderPoints(simpleSeriesRenderer)) {
            this.pointsChart.setDrawFrameFlag(false);
            this.pointsChart.drawLegendShape(canvas, simpleSeriesRenderer, f, f2, i, paint);
        }
    }

    public boolean isRenderPoints(SimpleSeriesRenderer simpleSeriesRenderer) {
        return ((XYSeriesRenderer) simpleSeriesRenderer).getPointStyle() != PointStyle.POINT;
    }

    public ScatterChart getPointsChart() {
        return this.pointsChart;
    }
}
