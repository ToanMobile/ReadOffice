package com.app.office.thirdpart.achartengine.tools;

import com.app.office.thirdpart.achartengine.chart.XYChart;

public class Pan extends AbstractTool {
    public Pan(XYChart xYChart) {
        super(xYChart);
    }

    public void apply(float f, float f2, float f3, float f4) {
        int scalesCount = this.mRenderer.getScalesCount();
        double[] panLimits = this.mRenderer.getPanLimits();
        boolean z = panLimits != null && panLimits.length == 4;
        XYChart xYChart = (XYChart) this.mChart;
        int i = 0;
        while (i < scalesCount) {
            double[] range = getRange(i);
            double[] calcRange = xYChart.getCalcRange(i);
            if (range[0] != range[1] || calcRange[0] != calcRange[1]) {
                if (range[2] != range[3] || calcRange[2] != calcRange[3]) {
                    checkRange(range, i);
                    double[] realPoint = xYChart.toRealPoint(f, f2);
                    double[] realPoint2 = xYChart.toRealPoint(f3, f4);
                    double d = realPoint[0] - realPoint2[0];
                    double d2 = realPoint[1] - realPoint2[1];
                    if (this.mRenderer.isPanXEnabled()) {
                        if (!z) {
                            setXRange(range[0] + d, range[1] + d, i);
                        } else if (panLimits[0] > range[0] + d) {
                            setXRange(panLimits[0], panLimits[0] + (range[1] - range[0]), i);
                        } else if (panLimits[1] < range[1] + d) {
                            setXRange(panLimits[1] - (range[1] - range[0]), panLimits[1], i);
                        } else {
                            setXRange(range[0] + d, range[1] + d, i);
                        }
                    }
                    if (this.mRenderer.isPanYEnabled()) {
                        if (!z) {
                            setYRange(range[2] + d2, range[3] + d2, i);
                        } else if (panLimits[2] > range[2] + d2) {
                            setYRange(panLimits[2], panLimits[2] + (range[3] - range[2]), i);
                        } else if (panLimits[3] < range[3] + d2) {
                            setYRange(panLimits[3] - (range[3] - range[2]), panLimits[3], i);
                        } else {
                            setYRange(range[2] + d2, range[3] + d2, i);
                        }
                    }
                    i++;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }
}
