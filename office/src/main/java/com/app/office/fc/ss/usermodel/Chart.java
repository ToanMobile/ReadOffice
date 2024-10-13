package com.app.office.fc.ss.usermodel;

import com.app.office.fc.ss.usermodel.charts.ChartAxis;
import com.app.office.fc.ss.usermodel.charts.ChartAxisFactory;
import com.app.office.fc.ss.usermodel.charts.ChartData;
import com.app.office.fc.ss.usermodel.charts.ChartDataFactory;
import com.app.office.fc.ss.usermodel.charts.ChartLegend;
import com.app.office.fc.ss.usermodel.charts.ManuallyPositionable;
import java.util.List;

public interface Chart extends ManuallyPositionable {
    void deleteLegend();

    List<? extends ChartAxis> getAxis();

    ChartAxisFactory getChartAxisFactory();

    ChartDataFactory getChartDataFactory();

    ChartLegend getOrCreateLegend();

    void plot(ChartData chartData, ChartAxis... chartAxisArr);
}
