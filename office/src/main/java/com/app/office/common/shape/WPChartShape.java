package com.app.office.common.shape;

import com.app.office.thirdpart.achartengine.chart.AbstractChart;

public class WPChartShape extends WPAutoShape {
    private AbstractChart chart;

    public AbstractChart getAChart() {
        return this.chart;
    }

    public void setAChart(AbstractChart abstractChart) {
        this.chart = abstractChart;
    }
}
