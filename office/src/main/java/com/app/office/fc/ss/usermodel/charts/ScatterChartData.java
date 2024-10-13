package com.app.office.fc.ss.usermodel.charts;

import com.app.office.fc.ss.util.DataMarker;
import java.util.List;

public interface ScatterChartData extends ChartData {
    ScatterChartSerie addSerie(DataMarker dataMarker, DataMarker dataMarker2);

    List<? extends ScatterChartSerie> getSeries();
}
