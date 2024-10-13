package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.model.XYSeries;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import java.util.List;

public class CombinedXYChart extends XYChart {
    private XYChart[] mCharts;
    private Class[] xyChartTypes = {TimeChart.class, LineChart.class, ColumnBarChart.class, BubbleChart.class, LineChart.class, ScatterChart.class, RangeBarChart.class};

    public String getChartType() {
        return "Combined";
    }

    public CombinedXYChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String[] strArr) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        int i = 0;
        int length = strArr.length;
        this.mCharts = new XYChart[length];
        while (i < length) {
            try {
                this.mCharts[i] = getXYChart(strArr[i]);
            } catch (Exception unused) {
            }
            if (this.mCharts[i] != null) {
                XYMultipleSeriesDataset xYMultipleSeriesDataset2 = new XYMultipleSeriesDataset();
                xYMultipleSeriesDataset2.addSeries(xYMultipleSeriesDataset.getSeriesAt(i));
                XYMultipleSeriesRenderer xYMultipleSeriesRenderer2 = new XYMultipleSeriesRenderer();
                xYMultipleSeriesRenderer2.setBarSpacing(xYMultipleSeriesRenderer.getBarSpacing());
                xYMultipleSeriesRenderer2.setPointSize(xYMultipleSeriesRenderer.getPointSize());
                int scaleNumber = xYMultipleSeriesDataset.getSeriesAt(i).getScaleNumber();
                if (xYMultipleSeriesRenderer.isMinXSet(scaleNumber)) {
                    xYMultipleSeriesRenderer2.setXAxisMin(xYMultipleSeriesRenderer.getXAxisMin(scaleNumber));
                }
                if (xYMultipleSeriesRenderer.isMaxXSet(scaleNumber)) {
                    xYMultipleSeriesRenderer2.setXAxisMax(xYMultipleSeriesRenderer.getXAxisMax(scaleNumber));
                }
                if (xYMultipleSeriesRenderer.isMinYSet(scaleNumber)) {
                    xYMultipleSeriesRenderer2.setYAxisMin(xYMultipleSeriesRenderer.getYAxisMin(scaleNumber));
                }
                if (xYMultipleSeriesRenderer.isMaxYSet(scaleNumber)) {
                    xYMultipleSeriesRenderer2.setYAxisMax(xYMultipleSeriesRenderer.getYAxisMax(scaleNumber));
                }
                xYMultipleSeriesRenderer2.addSeriesRenderer(xYMultipleSeriesRenderer.getSeriesRendererAt(i));
                this.mCharts[i].setDatasetRenderer(xYMultipleSeriesDataset2, xYMultipleSeriesRenderer2);
                i++;
            } else {
                throw new IllegalArgumentException("Unknown chart type " + strArr[i]);
            }
        }
    }

    private XYChart getXYChart(String str) throws IllegalAccessException, InstantiationException {
        int length = this.xyChartTypes.length;
        XYChart xYChart = null;
        for (int i = 0; i < length && xYChart == null; i++) {
            XYChart xYChart2 = (XYChart) this.xyChartTypes[i].newInstance();
            if (str.equals(xYChart2.getChartType())) {
                xYChart = xYChart2;
            }
        }
        return xYChart;
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        this.mCharts[i].setScreenR(getScreenR());
        this.mCharts[i].setCalcRange(getCalcRange(this.mDataset.getSeriesAt(i).getScaleNumber()), 0);
        this.mCharts[i].drawSeries(canvas, paint, fArr, simpleSeriesRenderer, f, 0);
    }

    /* access modifiers changed from: protected */
    public void drawSeries(XYSeries xYSeries, Canvas canvas, Paint paint, List<Float> list, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i, XYMultipleSeriesRenderer.Orientation orientation) {
        int i2 = i;
        this.mCharts[i2].setScreenR(getScreenR());
        this.mCharts[i2].setCalcRange(getCalcRange(this.mDataset.getSeriesAt(i2).getScaleNumber()), 0);
        this.mCharts[i2].drawSeries(xYSeries, canvas, paint, list, simpleSeriesRenderer, f, 0, orientation);
    }

    public int getLegendShapeWidth(int i) {
        return this.mCharts[i].getLegendShapeWidth(0);
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint) {
        this.mCharts[i].drawLegendShape(canvas, simpleSeriesRenderer, f, f2, 0, paint);
    }
}
