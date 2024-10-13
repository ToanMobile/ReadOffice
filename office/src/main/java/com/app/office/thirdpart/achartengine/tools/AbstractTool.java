package com.app.office.thirdpart.achartengine.tools;

import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.thirdpart.achartengine.chart.XYChart;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;

public abstract class AbstractTool {
    protected AbstractChart mChart;
    protected XYMultipleSeriesRenderer mRenderer;

    public AbstractTool(AbstractChart abstractChart) {
        this.mChart = abstractChart;
        if (abstractChart instanceof XYChart) {
            this.mRenderer = ((XYChart) abstractChart).getRenderer();
        }
    }

    public double[] getRange(int i) {
        return new double[]{this.mRenderer.getXAxisMin(i), this.mRenderer.getXAxisMax(i), this.mRenderer.getYAxisMin(i), this.mRenderer.getYAxisMax(i)};
    }

    public void checkRange(double[] dArr, int i) {
        double[] calcRange;
        AbstractChart abstractChart = this.mChart;
        if ((abstractChart instanceof XYChart) && (calcRange = ((XYChart) abstractChart).getCalcRange(i)) != null) {
            if (!this.mRenderer.isMinXSet(i)) {
                dArr[0] = calcRange[0];
                this.mRenderer.setXAxisMin(dArr[0], i);
            }
            if (!this.mRenderer.isMaxXSet(i)) {
                dArr[1] = calcRange[1];
                this.mRenderer.setXAxisMax(dArr[1], i);
            }
            if (!this.mRenderer.isMinYSet(i)) {
                dArr[2] = calcRange[2];
                this.mRenderer.setYAxisMin(dArr[2], i);
            }
            if (!this.mRenderer.isMaxYSet(i)) {
                dArr[3] = calcRange[3];
                this.mRenderer.setYAxisMax(dArr[3], i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setXRange(double d, double d2, int i) {
        this.mRenderer.setXAxisMin(d, i);
        this.mRenderer.setXAxisMax(d2, i);
    }

    /* access modifiers changed from: protected */
    public void setYRange(double d, double d2, int i) {
        this.mRenderer.setYAxisMin(d, i);
        this.mRenderer.setYAxisMax(d2, i);
    }
}
