package com.app.office.thirdpart.achartengine.tools;

import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import com.app.office.thirdpart.achartengine.chart.RoundChart;
import com.app.office.thirdpart.achartengine.chart.XYChart;
import com.app.office.thirdpart.achartengine.model.XYSeries;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;

public class FitZoom extends AbstractTool {
    public FitZoom(AbstractChart abstractChart) {
        super(abstractChart);
    }

    public void apply() {
        if (!(this.mChart instanceof XYChart)) {
            DefaultRenderer renderer = ((RoundChart) this.mChart).getRenderer();
            renderer.setScale(renderer.getOriginalScale());
        } else if (((XYChart) this.mChart).getDataset() != null) {
            int scalesCount = this.mRenderer.getScalesCount();
            if (this.mRenderer.isInitialRangeSet()) {
                for (int i = 0; i < scalesCount; i++) {
                    if (this.mRenderer.isInitialRangeSet(i)) {
                        this.mRenderer.setRange(this.mRenderer.getInitialRange(i), i);
                    }
                }
                return;
            }
            XYSeries[] series = ((XYChart) this.mChart).getDataset().getSeries();
            int length = series.length;
            if (length > 0) {
                for (int i2 = 0; i2 < scalesCount; i2++) {
                    double[] dArr = {Double.MAX_VALUE, -1.7976931348623157E308d, Double.MAX_VALUE, -1.7976931348623157E308d};
                    for (int i3 = 0; i3 < length; i3++) {
                        if (i2 == series[i3].getScaleNumber()) {
                            dArr[0] = Math.min(dArr[0], series[i3].getMinX());
                            dArr[1] = Math.max(dArr[1], series[i3].getMaxX());
                            dArr[2] = Math.min(dArr[2], series[i3].getMinY());
                            dArr[3] = Math.max(dArr[3], series[i3].getMaxY());
                        }
                    }
                    double abs = Math.abs(dArr[1] - dArr[0]) / 40.0d;
                    double abs2 = Math.abs(dArr[3] - dArr[2]) / 40.0d;
                    this.mRenderer.setRange(new double[]{dArr[0] - abs, dArr[1] + abs, dArr[2] - abs2, dArr[3] + abs2}, i2);
                }
            }
        }
    }
}
