package com.app.office.fc.ss.usermodel.charts;

import com.app.office.fc.ss.usermodel.Chart;

public interface ChartData {
    void fillChart(Chart chart, ChartAxis... chartAxisArr);
}
