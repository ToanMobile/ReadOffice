package com.app.office.thirdpart.achartengine.tools;

import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.thirdpart.achartengine.chart.RoundChart;
import com.app.office.thirdpart.achartengine.chart.XYChart;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;

public class Zoom extends AbstractTool {
    private boolean mZoomIn;
    private float mZoomRate;

    public Zoom(AbstractChart abstractChart, boolean z, float f) {
        super(abstractChart);
        this.mZoomIn = z;
        setZoomRate(f);
    }

    public void setZoomRate(float f) {
        this.mZoomRate = f;
    }

    public void apply() {
        if (this.mChart instanceof XYChart) {
            int scalesCount = this.mRenderer.getScalesCount();
            for (int i = 0; i < scalesCount; i++) {
                double[] range = getRange(i);
                checkRange(range, i);
                double[] zoomLimits = this.mRenderer.getZoomLimits();
                boolean z = zoomLimits != null && zoomLimits.length == 4;
                double d = (range[0] + range[1]) / 2.0d;
                double d2 = (range[2] + range[3]) / 2.0d;
                double d3 = range[1] - range[0];
                double d4 = range[3] - range[2];
                if (this.mZoomIn) {
                    if (this.mRenderer.isZoomXEnabled()) {
                        d3 /= (double) this.mZoomRate;
                    }
                    if (this.mRenderer.isZoomYEnabled()) {
                        d4 /= (double) this.mZoomRate;
                    }
                } else {
                    if (this.mRenderer.isZoomXEnabled()) {
                        d3 *= (double) this.mZoomRate;
                    }
                    if (this.mRenderer.isZoomYEnabled()) {
                        d4 *= (double) this.mZoomRate;
                    }
                }
                if (this.mRenderer.isZoomXEnabled()) {
                    double d5 = d3 / 2.0d;
                    double d6 = d - d5;
                    double d7 = d + d5;
                    if (!z || (zoomLimits[0] <= d6 && zoomLimits[1] >= d7)) {
                        setXRange(d6, d7, i);
                    }
                }
                if (this.mRenderer.isZoomYEnabled()) {
                    double d8 = d4 / 2.0d;
                    double d9 = d2 - d8;
                    double d10 = d2 + d8;
                    if (!z || (zoomLimits[2] <= d9 && zoomLimits[3] >= d10)) {
                        setYRange(d9, d10, i);
                    }
                }
            }
            return;
        }
        DefaultRenderer renderer = ((RoundChart) this.mChart).getRenderer();
        if (this.mZoomIn) {
            renderer.setScale(renderer.getScale() * this.mZoomRate);
        } else {
            renderer.setScale(renderer.getScale() / this.mZoomRate);
        }
    }
}
